package avatar.game.dialogue.core;

import org.spongepowered.api.entity.living.player.Player;

public abstract class DialogueAction {

    /*
     * An action linked to a Choice
     */

    public abstract void doWork(Player player);

}
