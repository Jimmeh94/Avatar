package avatar.game.ability.property;

import avatar.Avatar;
import avatar.game.ability.AbilityStage;
import avatar.game.ability.type.Ability;
import avatar.game.user.User;
import avatar.util.misc.LocationUtils;
import com.flowpowered.math.vector.Vector3d;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.util.AABB;
import org.spongepowered.api.world.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbilityPropertyCollisionLogic extends AbilityProperty{

    protected List<Ability> collidedAbilities = new ArrayList<>();
    protected List<User> collidedUsers = new ArrayList<>();

    protected abstract boolean collides(Ability ability);

    public AbilityPropertyCollisionLogic(String displayName, Ability ability) {
        super(displayName, ability, AbilityStage.UPDATE);
    }

    public List<User> getCollidedUsers() {
        return collidedUsers;
    }

    public List<Ability> getCollidedAbilities() {
        return collidedAbilities;
    }

    /* Shape */
    public static class DomeCollisionLogic extends AbilityPropertyCollisionLogic{

        public enum DomeDirection{
            UP(1),
            DOWN(-1);

            protected int delta;

            DomeDirection(int d){this.delta = d;}
        }

        protected double radius;
        protected DomeDirection domeDirection;

        public DomeCollisionLogic(String displayName, Ability ability, double radius, DomeDirection direction) {
            super(displayName, ability);

            this.radius = radius;
            this.domeDirection = direction;
        }

        protected double getExtendedY(){
            return ability.getCenter().getY() + radius * domeDirection.delta;
        }

        @Override
        protected boolean collides(Ability ability) {
            if(ability.getProperty(SquareCollisionLogic.class).isPresent()){
                SquareCollisionLogic logic = (SquareCollisionLogic) ability.getProperty(SquareCollisionLogic.class).get();
                if(domeDirection == DomeDirection.UP){
                    if(ability.getCenter().getY() >= this.ability.getCenter().getY() && ability.getCenter().getY() <= getExtendedY()){
                        return ability.getCenter().getPosition().distance(this.ability.getCenter().getPosition()) <= radius;
                    }
                } else if(domeDirection == DomeDirection.DOWN){
                    if(ability.getCenter().getY() <= this.ability.getCenter().getY() && ability.getCenter().getY() >= getExtendedY()){
                        return ability.getCenter().getPosition().distance(this.ability.getCenter().getPosition()) <= radius;
                    }
                }

            } else if(ability.getProperty(SphereCollisionLogic.class).isPresent()){
                SphereCollisionLogic logic = (SphereCollisionLogic) ability.getProperty(SphereCollisionLogic.class).get();
                if(domeDirection == DomeDirection.UP){
                    if(ability.getCenter().getY() >= this.ability.getCenter().getY() && ability.getCenter().getY() <= getExtendedY()){
                        if(LocationUtils.getConnectingLine(this.ability.getCenter(), ability.getCenter()).size() <= this.radius + logic.radius){
                            return true;
                        }
                    }
                } else if(domeDirection == DomeDirection.DOWN){
                    if(ability.getCenter().getY() <= this.ability.getCenter().getY() && ability.getCenter().getY() >= getExtendedY()){
                        if(LocationUtils.getConnectingLine(this.ability.getCenter(), ability.getCenter()).size() <= this.radius + logic.radius){
                            return true;
                        }
                    }
                }

            } else if(ability.getProperty(DomeCollisionLogic.class).isPresent()){
                DomeCollisionLogic logic = (DomeCollisionLogic) ability.getProperty(DomeCollisionLogic.class).get();
            }
            return false;
        }

        @Override
        public boolean validate() {
            collidedAbilities.clear();
            collidedUsers.clear();

            for(Ability ability: this.ability.getArea().getAbilityManager().getNearbyAbilitiesInChunk(this.ability)){
                if(collides(ability)){
                    collidedAbilities.add(ability);
                }
            }

            //check for nearby entities next
            Optional<User> user;
            for(Entity entity: ability.getCenter().getExtent().getEntities()){
                if(entity.getLocation().getPosition().distance(ability.getCenter().getPosition()) <= radius){
                    user = Avatar.INSTANCE.getUserManager().findUser(entity);
                    if(user.isPresent()){
                        collidedUsers.add(user.get());
                    }
                }
            }

            if(collidedAbilities.size() > 0 || collidedUsers.size() > 0){
                //do something here, such as damage the things
                //if ability should expire after, return false
                return false;
            } else return true;
        }

        @Override
        public Text getFailMessage() {
            return null;
        }
    }

    //*** Sphere ***
    public static class SphereCollisionLogic extends AbilityPropertyCollisionLogic{

        protected double radius;

        public SphereCollisionLogic(String displayName, Ability ability, double radius) {
            super(displayName, ability);

            this.radius = radius;
        }

        @Override
        public boolean validate() {
            collidedAbilities.clear();
            collidedUsers.clear();

            for(Ability ability: this.ability.getArea().getAbilityManager().getNearbyAbilitiesInChunk(this.ability)){
                if(collides(ability)){
                    collidedAbilities.add(ability);
                }
            }

            //check for nearby entities next
            Optional<User> user;
            for(Entity entity: ability.getCenter().getExtent().getEntities()){
                if(entity.getLocation().getPosition().distance(ability.getCenter().getPosition()) <= radius){
                    user = Avatar.INSTANCE.getUserManager().findUser(entity);
                    if(user.isPresent()){
                        collidedUsers.add(user.get());
                    }
                }
            }

            if(collidedAbilities.size() > 0 || collidedUsers.size() > 0){
                //do something here, such as damage the things
                //if ability should expire after, return false
                return false;
            } else return true;
        }

        @Override
        protected boolean collides(Ability ability) {
            if(ability.getProperty(SquareCollisionLogic.class).isPresent()){
                SquareCollisionLogic logic = (SquareCollisionLogic) ability.getProperty(SquareCollisionLogic.class).get();
                for(Vector3d vector3d: logic.getBoxLocations()){
                    if(vector3d.distance(this.ability.getCenter().getPosition()) <= radius){
                        return true;
                    }
                }

            } else if(ability.getProperty(SphereCollisionLogic.class).isPresent()){
                SphereCollisionLogic logic = (SphereCollisionLogic) ability.getProperty(SphereCollisionLogic.class).get();
                if(LocationUtils.getConnectingLine(this.ability.getCenter(), ability.getCenter()).size() <= this.radius + logic.radius){
                    return true;
                }

            } else if(ability.getProperty(DomeCollisionLogic.class).isPresent()){

            }
            return false;
        }

        @Override
        public Text getFailMessage() {
            return null;
        }
    }

    //*** Square ***
    public static class SquareCollisionLogic extends AbilityPropertyCollisionLogic{

        private AABB hitbox;

        public SquareCollisionLogic(String displayName, Ability ability, double x, double y, double z){
            super(displayName, ability);

            Location temp = ability.getOwner().getEntity().get().getLocation().add(0, 1, 0);
            this.hitbox = new AABB(temp.getX() - x/2, temp.getY() - y/2, temp.getZ() - z/2,
                    temp.getX() + x/2, temp.getY() + y/2, temp.getZ() + z/2);
        }

        public List<Vector3d> getBoxLocations(){
            List<Vector3d> give = new ArrayList<>();
            for(double y = new Double(hitbox.getMin().getY()); y <= hitbox.getMax().getY(); y += .01){
                for(double x = new Double(hitbox.getMin().getX()); x <= hitbox.getMax().getX(); x += .01){
                    for(double z = new Double(hitbox.getMin().getZ()); z <= hitbox.getMax().getZ(); z += .01){
                        give.add(new Vector3d(x, y, z));
                    }
                }
            }
            return give;
        }

        @Override
        public boolean validate() {
            collidedAbilities.clear();
            collidedUsers.clear();

            for(Ability ability: this.ability.getArea().getAbilityManager().getNearbyAbilitiesInChunk(this.ability)){
                if(collides(ability)){
                    collidedAbilities.add(ability);
                }
            }

            //check for nearby entities next
            Optional<User> user;
            for(Entity entity: ability.getCenter().getExtent().getEntities()){
                if(this.hitbox.contains(entity.getLocation().getPosition())){
                    user = Avatar.INSTANCE.getUserManager().findUser(entity);
                    if(user.isPresent()){
                        collidedUsers.add(user.get());
                    }
                }
            }

            if(collidedAbilities.size() > 0 || collidedUsers.size() > 0){
                //do something here, such as damage the things
                //if ability should expire after, return false
                return false;
            } else return true;
        }

        @Override
        public Text getFailMessage() {
            return null;
        }

        @Override
        public boolean collides(Ability ability) {
            if(ability.getProperty(SquareCollisionLogic.class).isPresent()){
                SquareCollisionLogic logic = (SquareCollisionLogic) ability.getProperty(SquareCollisionLogic.class).get();
                if(this.hitbox.intersects(logic.hitbox)){
                    return true;
                }
            } else if(ability.getProperty(SphereCollisionLogic.class).isPresent()){
                SphereCollisionLogic logic = (SphereCollisionLogic) ability.getProperty(SphereCollisionLogic.class).get();
                for(Vector3d vector3d: getBoxLocations()){
                    if(vector3d.distance(this.ability.getCenter().getPosition()) <= logic.radius){
                        return true;
                    }
                }

            } else if(ability.getProperty(DomeCollisionLogic.class).isPresent()){

            }
            return false;
        }

        public void offset(Location oldCenter, Location center) {
            this.hitbox = hitbox.offset(LocationUtils.getOffsetBetween(oldCenter, center));
        }
    }
}
