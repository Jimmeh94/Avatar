package avatar.game.menu;

import avatar.Avatar;
import avatar.utilities.misc.Callback;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.InventoryArchetypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.property.InventoryTitle;
import org.spongepowered.api.item.inventory.property.SlotPos;
import org.spongepowered.api.item.inventory.type.GridInventory;
import org.spongepowered.api.text.Text;

public class ConfirmationMenu {

    private Callback confirm, cancel;
    private Inventory inventory;

    public ConfirmationMenu(ItemStack c, ItemStack no, Text title, Callback confirm, Callback cancel){
        this.confirm = confirm;
        this.cancel = cancel;
        build(c, no, title);

        Avatar.INSTANCE.getConfirmationMenuManager().add(this);
    }

    private void build(ItemStack yes, ItemStack no, Text title){
        inventory = Inventory.builder().of(InventoryArchetypes.CHEST)
                .property(InventoryTitle.PROPERTY_NAME, InventoryTitle.of(title))
                .build(Avatar.INSTANCE);
        GridInventory inv = inventory.query(GridInventory.class);
        inv.set(new SlotPos(2, 0), yes);
        inv.set(new SlotPos(6, 0), no);
        inventory = inv;
    }

    public void display(Player player){
        player.openInventory(inventory, Cause.of(NamedCause.of("Server Action", this)));
    }

    public Inventory getInventory(){
        return inventory;
    }

    //make a data object that contains the callbacks

}
