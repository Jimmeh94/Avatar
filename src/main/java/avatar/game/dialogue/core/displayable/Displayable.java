package avatar.game.dialogue.core.displayable;

import org.spongepowered.api.entity.living.player.Player;

public interface Displayable {
    /*
     * What's stored in the Dialogue container. These are typically ChoiceWheels or Sentences
     */

    void display(Player player);

}
