package avatar.game.dialogue.core;

import org.spongepowered.api.entity.living.player.Player;

public interface IDialogueInitiator {

    static String getIDPrefix(DialogueBuilder builder){
        return builder.getStringID() + ".";
    }

    Dialogue build(Player player);

}
