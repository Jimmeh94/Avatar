package avatar.game.areas;

import avatar.events.custom.AreaEvent;
import avatar.managers.ListenerManager;
import avatar.user.User;
import avatar.user.UserPlayer;
import avatar.utilities.misc.LocationUtils;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * General "area" of the map.
 * Could be used in quests too, such as reach the city of _____
 */

public class Area {

    private AreaShape shape;
    private Text displayName;
    private List<User> members = new ArrayList<>();
    private AreaReferences reference;
    private List<Area> children = new ArrayList<>();

    public Area(AreaReferences reference){
        this.shape = reference.getShape();
        this.displayName = reference.getDisplayName();
        this.reference = reference;

        for(AreaReferences r: reference.getChildren()){
            children.add(new Area(r));
        }
    }

    public boolean hasChild(AreaReferences reference){
        for(Area area: children){
            if(area.is(reference))
                return true;
        }
        return false;
    }

    public Optional<Area> getChild(AreaReferences reference){
        for(Area area: children){
            if(area.is(reference))
                return Optional.of(area);
        }
        return Optional.empty();
    }

    public List<Area> getChildren() {
        return children;
    }

    public Location getCenter(){return shape.getCenter();}

    public boolean is(AreaReferences reference){return this.reference == reference;}

    public boolean contains(Location location){
        boolean has;

        if(shape != null && getCenter() != null){
            double x1 = location.getX(), x2 = shape.center.getX();
            double z1 = location.getZ(), z2 = shape.center.getZ();
            double y = location.getY();

            has = LocationUtils.getDistance(x1, x2) <= shape.radius && LocationUtils.getDistance(z1, z2) <= shape.radius
                    && y >= shape.center.getY() && y <= shape.center.getY() + shape.getHeight();

            if(!has) //parent doesn't contain this location, no need searching children
                return false;
        } else has = true; //This means it's a global area

        if(!has) {
            for (Area area : children) {
                has = area.contains(location);
            }
        }

        return has;
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
                ((UserPlayer)targetEntity).getPlayer().get().sendMessage(Text.builder().append(Text.of("Entering ")).append(displayName).build());
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

            /*if(targetEntity instanceof UserPlayer){
                ((UserPlayer)targetEntity).getPlayer().get().sendMessage(Text.builder().append(Text.of("Leaving ")).append(displayName).build());
            }*/

            AreaEvent event = new AreaEvent.Exit(targetEntity, targetEntity.getPresentArea(), ListenerManager.getDefaultCause());
            Sponge.getEventManager().post(event);
        }
    }

    public Text getDisplayName() {
        return displayName;
    }

    public boolean isMember(User entity){
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

    /**
     * We assume you've checked Area#contains(location) and that it returned true
     * @param location
     * @return
     */
    public Area getAreaThatContains(Location location) {
        Area give = this;

        for(Area area: children){
            if(area.contains(location))
                give = area.getAreaThatContains(location);
        }

        return give;
    }

    public AreaShape getShape() {
        return shape;
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

        public boolean isYWithinBounds(double y){
            return center.getY() <= y && y <= (center.getY() + height);
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
            super(LocationUtils.getMidPointLocation(firstCorner, secondCorner), height,
                    (firstCorner.getPosition().distance(LocationUtils.getMidPointLocation(firstCorner, secondCorner).getPosition())));

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
