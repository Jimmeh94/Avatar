package avatar.game.ability.property;

import avatar.Avatar;
import avatar.event.custom.AbilityEvent;
import avatar.game.ability.Ability;
import avatar.manager.ListenerManager;
import avatar.game.user.User;
import avatar.game.user.UserPlayer;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.EventListener;
import org.spongepowered.api.event.Order;

import java.util.ArrayList;
import java.util.List;

public class AbilityPropertyCollisionLogic extends AbilityProperty implements EventListener<AbilityEvent.UpdateTick>{

    private List<Ability> collidedAbilities = new ArrayList<>();
    private List<User> collidedUsers = new ArrayList<>();

    public AbilityPropertyCollisionLogic(String displayName, Ability ability) {
        super(displayName, ability);
    }

    @Override
    public void handle(AbilityEvent.UpdateTick updateTick) throws Exception {
        collidedAbilities.clear();
        collidedUsers.clear();

        List<Ability> nearby = Avatar.INSTANCE.getAbilityManager().getNearbyAbilitiesInChunk(ability);
        for(Ability ability: nearby){
            if(this.ability.getHitbox().intersects(ability.getHitbox())){
                collidedAbilities.add(ability);
            }
        }

        //check for nearby entities next

        if(collidedAbilities.size() > 0){
            Sponge.getEventManager().post(new AbilityEvent.Hit(ability, ListenerManager.getDefaultCause(), collidedAbilities));
        }
    }

    @Override
    protected void register() {
        ListenerManager.register(AbilityEvent.UpdateTick.class, Order.FIRST, this);
    }

    @Override
    public void printFailMessage(UserPlayer user) {

    }

    public List<User> getCollidedUsers() {
        return collidedUsers;
    }

    public List<Ability> getCollidedAbilities() {
        return collidedAbilities;
    }
}