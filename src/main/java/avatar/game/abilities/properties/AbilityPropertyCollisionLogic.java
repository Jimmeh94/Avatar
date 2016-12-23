package avatar.game.abilities.properties;

import avatar.Avatar;
import avatar.events.custom.AbilityEvent;
import avatar.game.abilities.Ability;
import avatar.managers.ListenerManager;
import avatar.user.User;
import avatar.user.UserPlayer;
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
