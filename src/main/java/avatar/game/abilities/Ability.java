package avatar.game.abilities;

import avatar.game.abilities.properties.AbilityProperty;
import avatar.user.User;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.util.AABB;
import org.spongepowered.api.world.Location;

import java.util.List;

public abstract class Ability {

    private double baseDamage;
    private User owner;
    private Location center;
    private AABB hitbox;
    private Element element;
    private Text displayName;
    private int id;
    private List<AbilityProperty> properties;

    public Ability(){

    }

    public boolean canFire(){
        for(AbilityProperty property: properties){
            if(property.check(owner) == false){
                return false;
            }
        }
        return true;
    }

}
