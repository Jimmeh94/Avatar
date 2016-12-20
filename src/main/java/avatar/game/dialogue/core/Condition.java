package avatar.game.dialogue.core;

import org.spongepowered.api.entity.living.player.Player;

public interface Condition {

    /*
     * A condition that must be met to keep the conversation going, i.e. stay in the range of the character
     */

    /*
     * Making sure that the current action is still valid to perform
     */
    boolean isValid(Player player);

    /*
     * Send an error message if isValid returns false
     */
    void sendErrorMessage(Player player);

}
