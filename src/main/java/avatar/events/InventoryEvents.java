package avatar.events;

import avatar.Avatar;
import avatar.game.menu.ConfirmationMenu;
import avatar.game.quests.quests.Quest;
import avatar.game.user.UserPlayer;
import avatar.managers.ConfirmationMenuManager;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.item.inventory.ClickInventoryEvent;
import org.spongepowered.api.event.item.inventory.InteractInventoryEvent;

import java.util.Optional;

public class InventoryEvents {

    /*
     * Current problems are clicking on the item multiple times. It works the first time, but the second+
     * says there's no value present. Also, closing the inventory doesn't work. need to wait for Sponge
     * to update
     */

    @Listener
    public void onClick(ClickInventoryEvent.Primary event, @First Player player){
        if(event.getTargetInventory().getName().get().contains("Quests")){
            event.setCancelled(true);

            UserPlayer playerInfo = Avatar.INSTANCE.getUserManager().findUserPlayer(player).get();

            for(Quest quest: playerInfo.getQuestManager().getQuests()){
                if(quest.getTitle().compareTo(event.getCursorTransaction().getFinal().get(Keys.DISPLAY_NAME).get()) == 0){
                    quest.toggleActive();
                    player.closeInventory(Cause.of(NamedCause.of("Server Action", this)));
                    return;
                }
            }
        } else if(Avatar.INSTANCE.getConfirmationMenuManager().has(event.getTargetInventory())){
            ConfirmationMenuManager manager = Avatar.INSTANCE.getConfirmationMenuManager();

            //TODO pass in the clicked item and do the callback

            Optional<ConfirmationMenu> optional = manager.find(event.getTargetInventory());
            if(optional.isPresent()){
                manager.remove(optional.get());
            }
        }
    }

    @Listener
    public void onClose(InteractInventoryEvent.Close event){
        ConfirmationMenuManager manager = Avatar.INSTANCE.getConfirmationMenuManager();

        if(manager.has(event.getTargetInventory())){
            Optional<ConfirmationMenu> optional = manager.find(event.getTargetInventory());
            if(optional.isPresent()){
                manager.remove(optional.get());
            }
        }
    }

}
