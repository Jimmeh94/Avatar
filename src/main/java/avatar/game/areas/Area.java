package avatar.game.areas;

import avatar.user.User;
import org.spongepowered.api.entity.living.player.Player;
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

    public Area(AreaShape shape, String displayName){
        this.shape = shape;
        this.displayName = displayName;
    }

    public boolean has(Location location) {
        for(Location l: shape.threshold){
            if(l.getBlockX() == location.getBlockX() &&
                    l.getBlockY() == location.getBlockY() &&
                    l.getBlockZ() == location.getBlockZ()){
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

            if(targetEntity instanceof Player);
            //show display name somewhere
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
        }
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean isMember(Player player){
        return members.contains(player);
    }

    public static abstract class AreaShape {
        private Location center;
        protected List<Location> threshold = new ArrayList<>();
        private double height;

        protected abstract void calculateThreshold();

        public AreaShape(Location center, double height){
            this.center = center;
            this.height = height;
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
                        && player.getLocation().getY() < location.getY() + height){
                    return true;
                }
            }
            return false;
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
        protected void calculateThreshold() {
            //Should be a 1 block thick ring around the outside of the circle
            /*
            Given a radius length r and an angle t in radians and a circle's center (h,k),
            you can calculate the coordinates of a point on the circumference as follows
             */

            for(int i = 0; i < 360; i++){
                //2 radians = 360 degrees
                double t = i * (Math.PI / 180);
                double x = radius * Math.cos(t) + getCenter().getX();
                double z = radius * Math.sin(t) + getCenter().getY();
                threshold.add(new Location(getCenter().getExtent(), x, getHeight(), z));
            }
        }
    }

    public static class AreaRectangle extends AreaShape{

        private Location first, second;

        public AreaRectangle(Location firstCorner, Location secondCorner, double height) {
            super(null, height);

            this.first = firstCorner;
            this.second = secondCorner;
        }

        @Override
        protected void calculateThreshold() {
            int xCoefficient = first.getX() > second.getX() ? -1 : 1, zCoefficient = first.getZ() > second.getZ() ? -1 : 1;

            Location current = first.copy();
            threshold.add(current);
            while(current.getBlockX() != second.getBlockX()){
                current.add(xCoefficient * 1, 0, 0);
                threshold.add(current);
            }
            while(current.getBlockZ() != second.getBlockZ()){
                current.add(0, 0, zCoefficient * 1);
                threshold.add(current);
            }

            xCoefficient *= -1;
            zCoefficient *= -1;
            while(current.getBlockX() != first.getBlockX()){
                current.add(xCoefficient * 1, 0, 0);
                threshold.add(current);
            }
            while(current.getBlockZ() != first.getBlockZ()){
                current.add(0, 0, zCoefficient * 1);
                threshold.add(current);
            }
            threshold.add(second);
        }
    }


}
