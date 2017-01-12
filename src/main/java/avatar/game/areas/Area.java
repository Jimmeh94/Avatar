package avatar.game.areas;

import avatar.events.custom.AreaEvent;
import avatar.managers.ListenerManager;
import avatar.user.User;
import avatar.user.UserPlayer;
import avatar.utilities.misc.LocationUtils;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.Entity;
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
    private Text displayName;
    private List<User> members = new ArrayList<>();
    private AreaReferences reference;

    public Area(AreaShape shape, Text displayName, AreaReferences reference){
        this.shape = shape;
        this.displayName = displayName;
        this.reference = reference;
    }

    public boolean is(int ID){return this.reference.getIntID() == ID;}

    public boolean is(String ID){return this.reference.getStringID().toLowerCase() == ID.toLowerCase();}

    public boolean contains(Location location){
        double x1 = location.getX(), x2 = shape.center.getX();
        double z1 = location.getZ(), z2 = shape.center.getZ();
        double y = location.getY();

        return LocationUtils.getDistance(x1, x2) <= shape.radius && LocationUtils.getDistance(z1, z2) <= shape.radius
                && y >= shape.center.getY() && y <= shape.center.getY() + shape.getHeight();
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

            AreaEvent event = new AreaEvent.Enter(targetEntity, this, ListenerManager.getDefaultCause());
            Sponge.getEventManager().post(event);
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

            AreaEvent event = new AreaEvent.Exit(targetEntity, targetEntity.getPresentArea(), ListenerManager.getDefaultCause());
            Sponge.getEventManager().post(event);
        }
    }

    public Text getDisplayName() {
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

    public static abstract class AreaShape {
        private Location center;
        protected double height, radius;

        public AreaShape(Location center, double height, double radius){
            this.center = center;
            this.height = height;
            this.radius = radius;
        }

        public Location getCenter() {
            return center;
        }

        public double getHeight() {
            return height;
        }

        public double getRadius() {
            return radius;
        }
    }

    public static class AreaCircle extends AreaShape{

        public AreaCircle(Location center, double radius, double height) {
            super(center, height, radius);
        }
    }

    public static class AreaRectangle extends AreaShape{

        private Location first, second;
        public AreaRectangle(Location firstCorner, Location secondCorner, double height) {
            super(LocationUtils.getMidPointLocation(firstCorner, secondCorner), height, (firstCorner.getPosition().distance(secondCorner.getPosition())) / 2);

            this.first = firstCorner;
            this.second = secondCorner;
        }

        public Location getFirst() {
            return first;
        }

        public Location getSecond() {
            return second;
        }
    }


}
