package avatar.game.areas;

import avatar.user.User;
import avatar.user.UserPlayer;
import avatar.utilities.misc.LocationUtils;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * General "area" of the map.
 * Could be used in quests too, such as reach the city of _____
 */

public abstract class Area {

    private AreaShape shape;
    private String displayName;
    private List<User> members = new ArrayList<>();
    private AreaReferences reference;

    public Area(AreaShape shape, String displayName, AreaReferences reference){
        this.shape = shape;
        this.displayName = displayName;
        this.reference = reference;
    }

    public boolean is(int ID){return this.reference.getIntID() == ID;}

    public boolean is(String ID){return this.reference.getStringID().toLowerCase() == ID.toLowerCase();}

    public boolean has(Location location) {
        for(Location l: shape.threshold){
            if(l.getBlockX() == location.getBlockX() &&
                    l.getBlockZ() == location.getBlockZ()){
                if(location.getBlockY() <= l.getBlockY() + shape.height)
                    return true;
            }
        }
        return false;
    }

    /**
     * You should not call this directly if you have a User entering this area
     * Use the User#enterArea(Area area) method instead
     * @param targetEntity
     */
    public void entering(User targetEntity) {
        if(!members.contains(targetEntity)){
            members.add(targetEntity);

            if(targetEntity instanceof UserPlayer){
                ((UserPlayer)targetEntity).getPlayer().get().sendMessage(Text.of("Entering " + displayName));
            }
        }
    }

    /**
     * You should not call this direclty if you have a User leaving an area
     * use the User#leaveArea() method instead
     * @param targetEntity
     */
    public void leaving(User targetEntity) {
        if(members.contains(targetEntity)){
            members.remove(targetEntity);

            if(targetEntity instanceof UserPlayer){
                ((UserPlayer)targetEntity).getPlayer().get().sendMessage(Text.of("Leaving " + displayName));
            }
        }
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean isMember(Entity entity){
        return members.contains(entity);
    }

    public List<UserPlayer> getPlayersFromMembers() {
        List<UserPlayer> players = new ArrayList<>();
        for(User user: members){
            if(user.isPlayer()){
                players.add(((UserPlayer)user));
            }
        }

        return players;
    }

    public boolean inside(UserPlayer player){
        Location nearest = shape.findNearestThreshold(player.getPlayer().get());

        double thresholdDistance = shape.center.getPosition().distance(nearest.getPosition());
        double playerDistance = shape.center.getPosition().distance(player.getPlayer().get().getLocation().getPosition());

        return thresholdDistance >= playerDistance;
    }

    public static abstract class AreaShape {
        private Location center;
        protected List<Location> threshold = new ArrayList<>();
        private double height;

        protected abstract void calculateThreshold();
        protected abstract double getRadius();

        public AreaShape(Location center, double height){
            this.center = center;
            this.height = height;
        }

        protected Location findNearestThreshold(Entity entity){
            Location give = null;
            for(Location location: threshold){
                if(give == null || location.getPosition().distance(entity.getLocation().getPosition()) < give.getPosition().distance(entity.getLocation().getPosition())){
                    give = location.copy();
                }
            }
            return give;
        }

        public Location getCenter() {
            return center;
        }

        public double getHeight() {
            return height;
        }

        public boolean crossedThreshold(Player player) {
            for(Location location: threshold){
                if(Math.max(player.getLocation().getX(), location.getX()) - Math.min(player.getLocation().getX(), location.getX()) <= 1.5
                        && Math.max(player.getLocation().getZ(), location.getZ()) - Math.min(player.getLocation().getZ(), location.getZ()) <= 1.5
                        && player.getLocation().getY() <= location.getY() + height){
                    return true;
                }
            }
            return false;
        }

        protected void setCenter(Location location) {
            this.center = location;
        }
    }

    public static class AreaCircle extends AreaShape{

        private double radius;

        public AreaCircle(Location center, double radius, double height) {
            super(center, height);

            this.radius = radius;
            calculateThreshold();
        }

        @Override
        protected double getRadius(){return radius;}

        @Override
        protected void calculateThreshold() {
            threshold = LocationUtils.getCircleOutline(getCenter(), getRadius(), false);
        }
    }

    public static class AreaRectangle extends AreaShape{

        private Location first, second;

        public AreaRectangle(Location firstCorner, Location secondCorner, double height) {
            super(null, height);

            this.setCenter(LocationUtils.getMidPointLocation(firstCorner, secondCorner));
            this.first = firstCorner;
            this.second = secondCorner;
            calculateThreshold();
        }

        @Override
        protected double getRadius(){return (first.getPosition().distance(second.getPosition())) / 2;}

        @Override
        protected void calculateThreshold() {
            threshold = LocationUtils.getSquareOutline(first, second);
        }
    }


}
