package avatar.game.quests.menus;

import avatar.Avatar;
import avatar.game.quests.quests.Quest;
import avatar.user.UserPlayer;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.InventoryArchetypes;
import org.spongepowered.api.item.inventory.property.InventoryTitle;
import org.spongepowered.api.text.Text;

import java.util.ArrayList;
import java.util.List;

public class QuestMenu {

    /*
     * To be the quest menu that players open to view their currently owned quests
     */

    private List<Inventory> menus = new ArrayList<>();
    private UserPlayer owner;

    public QuestMenu(UserPlayer owner){
        this.owner = owner;
        build();
    }

    /*
     * Default building of quests menus
     * 27 quests can fit on each menu page
     */

    private void build(){
        int pages = owner.getQuests().size() / 27;
        if(owner.getQuests().size() % 27 > 0)
            pages++;

        int counter = 0;
        for(int i = 0; i < pages; i++){
            Inventory inv = Inventory.builder().of(InventoryArchetypes.DOUBLE_CHEST)
                    .property(InventoryTitle.PROPERTY_NAME, InventoryTitle.of(Text.of("Quests: Page " + (i + 1))))
                    .build(Avatar.INSTANCE);
            for(int j = 0; j < 27 && counter < owner.getQuests().size(); j++, counter++){
                inv.set(owner.getQuests().get(counter).getItemRepresentation());
            }
            menus.add(inv);
        }
    }

    public Inventory getPage(int i) {
        return menus.get(i);
    }

    public Quest findClickedQuest(Text quest) {
        for(Quest quest1: owner.getQuests()){
            if(quest1.getTitle().equals(quest))
                return quest1;
        }
        return null;
    }
}
