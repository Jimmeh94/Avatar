package avatar.game.ability;

import org.spongepowered.api.item.inventory.ItemStack;

public abstract class AbilityRepresentation {

    private ItemStack itemStack;
    private Element element;

    public abstract Ability startAbility();

    public AbilityRepresentation(ItemStack itemStack, Element element){
        this.itemStack = itemStack;
        this.element = element;
    }



}
