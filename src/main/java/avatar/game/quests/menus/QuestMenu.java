package avatar.game.quests.menus;

import avatar.Avatar;
import avatar.game.quests.quests.Quest;
import avatar.game.user.UserPlayer;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.InventoryArchetypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.property.InventoryTitle;
import org.spongepowered.api.text.Text;

import java.util.ArrayList;
import java.util.List;
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
        List<ItemStack> temp = new ArrayList<>();
        Inventory inv = Inventory.builder().of(InventoryArchetypes.DOUBLE_CHEST)
                .property(InventoryTitle.PROPERTY_NAME, InventoryTitle.of(Text.of("Quests")))
                .build(Avatar.INSTANCE);
        for(Quest quest: owner.getQuestManager().getQuests()){
            if(quest.isActive())
                temp.add(0, quest.getItemRepresentation());
            else temp.add(quest.getItemRepresentation());
        }
        for(ItemStack itemStack: temp){
            inv.set(itemStack);
        }

        menu = inv;
    }

    public Optional<Quest> findClickedQuest(int id) {
        if (owner.getQuestManager().getQuests().get(id) != null) {
            return Optional.of(owner.getQuestManager().getQuests().get(id));
        }
        return Optional.empty();
    }

    public Optional<Quest> findClickedQuest(String id) {
        for(Quest quest: owner.getQuestManager().getQuests()){
            if(quest.getID().equalsIgnoreCase(id)){
                return Optional.of(quest);
            }
        }
        return Optional.empty();
    }

    public Inventory getMenu() {
        return menu;
    }
}
