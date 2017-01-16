package avatar.manager;

import avatar.game.menu.ConfirmationMenu;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.type.GridInventory;

import java.util.Optional;

public class ConfirmationMenuManager extends Manager<ConfirmationMenu> {

    public boolean has(Inventory inventory){
        if(inventory instanceof GridInventory){
            for(ConfirmationMenu confirmationMenu: objects){
                if(confirmationMenu.getInventory() == inventory){
                    return true;
                }
            }
        }
        return false;
    }

    public Optional<ConfirmationMenu> find(Inventory inventory){
        if(inventory instanceof GridInventory){
            for(ConfirmationMenu confirmationMenu: objects){
                if(confirmationMenu.getInventory() == inventory){
                    return Optional.of(confirmationMenu);
                }
            }
        }
        return Optional.empty();
    }

}
