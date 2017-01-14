package avatar.events;

import avatar.Avatar;
import avatar.user.UserPlayer;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.item.inventory.ClickInventoryEvent;
import org.spongepowered.api.item.inventory.property.SlotPos;

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

            SlotPos slotPos = event.getTransactions().get(0).getSlot().getProperty(SlotPos.class, event.getTransactions().get(0).getSlot()).get();

            int location = slotPos.getY() * 9 + slotPos.getX();

            if(location > playerInfo.getQuestManager().getQuests().size())
                return;

            Avatar.INSTANCE.getQuestManager().setActiveQuest(playerInfo,
                    playerInfo.getQuestManager().getQuestMenu().findClickedQuest(location).get().getID());

            player.closeInventory(Cause.of(NamedCause.of("Server Action", this)));
        }
    }

}
