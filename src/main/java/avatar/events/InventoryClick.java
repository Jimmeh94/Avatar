package avatar.events;

import avatar.Avatar;
import avatar.user.UserPlayer;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.item.inventory.ClickInventoryEvent;

public class InventoryClick {

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

            Avatar.INSTANCE.getQuestManager().setActiveQuest(playerInfo,
                    playerInfo.getQuestMenu()
                            .findClickedQuest(event.getCursorTransaction().getFinal()
                                    .createStack().get(Keys.DISPLAY_NAME).get()));

            player.closeInventory(Cause.of(NamedCause.of("Server Action", this)));
        }
    }

}
