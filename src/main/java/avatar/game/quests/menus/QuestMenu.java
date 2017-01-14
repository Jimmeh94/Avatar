package avatar.game.quests.menus;

import avatar.Avatar;
import avatar.game.quests.quests.Quest;
import avatar.user.UserPlayer;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.InventoryArchetypes;
import org.spongepowered.api.item.inventory.property.InventoryTitle;
import org.spongepowered.api.text.Text;

import java.util.Optional;

public class QuestMenu {

    /*
     * To be the quest menu that players open to view their currently owned quests
     */

    private Inventory menu;
    private UserPlayer owner;

    public QuestMenu(UserPlayer owner){
        this.owner = owner;
    }

    /*
     * Default building of quests menus
     * Player can't have more than 54 currently owned quests
     */

    public void build(){
        Inventory inv = Inventory.builder().of(InventoryArchetypes.DOUBLE_CHEST)
                .property(InventoryTitle.PROPERTY_NAME, InventoryTitle.of(Text.of("Quests")))
                .build(Avatar.INSTANCE);
        for(Quest quest: owner.getQuestManager().getQuests()){
            inv.set(quest.getItemRepresentation());
        }

        menu = inv;
    }

    public Optional<Quest> findClickedQuest(int slot) {
        if(owner.getQuestManager().getQuests().get(slot) != null){
            return Optional.of(owner.getQuestManager().getQuests().get(slot));
        }
        return Optional.empty();
    }

    public Inventory getMenu() {
        return menu;
    }
}
