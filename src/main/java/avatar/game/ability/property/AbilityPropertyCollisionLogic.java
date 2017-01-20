package avatar.game.ability.property;

import avatar.event.custom.AbilityEvent;
import avatar.game.ability.type.Ability;
import avatar.game.ability.AbilityStage;
import avatar.game.user.User;
import avatar.manager.ListenerManager;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.text.Text;

import java.util.ArrayList;
import java.util.List;

public class AbilityPropertyCollisionLogic extends AbilityProperty{

    private List<Ability> collidedAbilities = new ArrayList<>();
    private List<User> collidedUsers = new ArrayList<>();

    public AbilityPropertyCollisionLogic(String displayName, Ability ability) {
        super(displayName, ability, AbilityStage.UPDATE);
    }

    public List<User> getCollidedUsers() {
        return collidedUsers;
    }

    public List<Ability> getCollidedAbilities() {
        return collidedAbilities;
    }

    @Override
    public boolean validate() {
        collidedAbilities.clear();
        collidedUsers.clear();

        List<Ability> nearby = ability.getArea().getAbilityManager().getNearbyAbilitiesInChunk(ability);
        for(Ability ability: nearby){
            if(this.ability.getHitbox().intersects(ability.getHitbox())){
                collidedAbilities.add(ability);
            }
        }

        //check for nearby entities next

        if(collidedAbilities.size() > 0){
            Sponge.getEventManager().post(new AbilityEvent.Hit.Ability(ability, ListenerManager.getDefaultCause(), collidedAbilities));
        }

        if(collidedUsers.size() > 0){
            Sponge.getEventManager().post(new AbilityEvent.Hit.User(ability, ListenerManager.getDefaultCause(), collidedUsers));
        }

        return true;
    }

    @Override
    public Text getFailMessage() {
        return null;
    }
}
