package avatar.game.dialogue.core.test;

import avatar.Avatar;
import avatar.game.dialogue.core.actions.DisplayDialogue;
import avatar.game.dialogue.core.builders.DialogueBuilder;
import avatar.game.dialogue.core.builders.DisplayableBuilder;
import avatar.game.dialogue.core.conditions.RangeBound;
import com.flowpowered.math.vector.Vector3d;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;

public class TestDialogue {

    public TestDialogue(){
        DialogueBuilder dialogueBuilder = Avatar.INSTANCE.getDialogueBuilder();
        DisplayableBuilder displayableBuilder = dialogueBuilder.displayableBuilder();

        dialogueBuilder.stringID("test")
                .loadDialogue(displayableBuilder
                        .setChoiceWheel(false)
                        .addSentence(Text.of("[Old Man] Will you help me?")))
                .loadDialogue(displayableBuilder
                        .setChoiceWheel(true)//56,72,53
                        .addCondition(new RangeBound(new Location(Sponge.getServer().getWorld("world").get(), new Vector3d(56f, 72f, 53f))))
                        .addChoice(Text.of("Yes I will"), Text.of("Get quest"), new DisplayDialogue("testYes"))
                        .addChoice(Text.of("No I won't"), Text.of("Deny quest"), new DisplayDialogue("testNo"))
                        .addChoice(Text.of("With what?"), Text.of("Get more info"), new DisplayDialogue("testWith")))
                .build();

        dialogueBuilder.stringID("testYes")
                .loadDialogue(displayableBuilder
                        .setChoiceWheel(false)
                        .addSentence(Text.of("Oh thank you!")))
                .build();

        dialogueBuilder.stringID("testNo")
                .loadDialogue(displayableBuilder
                        .setChoiceWheel(false)
                        .addSentence(Text.of("Well up yours buddy!")))
                .build();

        dialogueBuilder.stringID("testWith")
                .loadDialogue(displayableBuilder
                        .setChoiceWheel(false)
                        .addSentence(Text.of("I lost some candy in my van... ;)")))
                .loadDialogue(displayableBuilder
                        .setChoiceWheel(true)
                        .addChoice(Text.of("Sure, I loooooove candy"), Text.of("Get quest"), new DisplayDialogue("testYes"))
                        .addChoice(Text.of("Stranger danger!"), null, new DisplayDialogue("testNo")))
                .build();
    }

}
