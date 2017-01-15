package avatar.game.dialogue.core.test;

import avatar.Avatar;
import avatar.game.dialogue.core.displayable.Choice;
import avatar.game.dialogue.core.IDialogueInitiator;
import avatar.game.dialogue.core.displayable.Sentence;
import avatar.game.dialogue.core.actions.DisplayDialogue;
import avatar.game.dialogue.core.actions.GiveQuest;
import avatar.game.dialogue.core.DialogueBuilder;
import avatar.game.dialogue.core.displayable.ChoiceWheel;
import avatar.game.dialogue.core.Dialogue;
import avatar.game.quests.QuestReference;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

public class TestDialogue implements IDialogueInitiator{

    @Override
    public Dialogue build(Player player) {
        DialogueBuilder dialogueBuilder = Avatar.INSTANCE.getDialogueBuilder();

        return dialogueBuilder.stringID("test")
                .addDisplayable(new Sentence(Text.of("[Old Man] Will you help me?")))
                .addDisplayable(new ChoiceWheel(
                        //.addCondition(new RangeBound(new Location(Sponge.getServer().getWorld("world").get(), new Vector3d(100f, 100f, 100f)), 100))
                        new Choice(Text.of("Yes I will"), Text.of("Get quest"), IDialogueInitiator.getIDPrefix(dialogueBuilder) + "yes", player, new GiveQuest(QuestReference.TEST), new DisplayDialogue(){
                            @Override
                            public Dialogue buildToDisplay(Player player) {
                                return dialogueBuilder.stringID("testYes").addDisplayable(new Sentence(Text.of("Oh thank you!"))).build(player);
                            }
                        }),
                        new Choice(Text.of("No I won't"), Text.of("Deny quest"), IDialogueInitiator.getIDPrefix(dialogueBuilder) + "no", player, new DisplayDialogue() {
                            @Override
                            public Dialogue buildToDisplay(Player player) {
                                return dialogueBuilder.stringID("testNo")
                                        .addDisplayable(new Sentence(Text.of("Well up yours buddy!"))).build(player);
                            }
                        }),
                        new Choice(Text.of("With what?"), Text.of("Get more info"), IDialogueInitiator.getIDPrefix(dialogueBuilder) + "with", player, new DisplayDialogue() {
                            @Override
                            public Dialogue buildToDisplay(Player player) {
                                return dialogueBuilder.stringID("testWith")
                                        .addDisplayable(new Sentence((Text.of("I lost some candy in my van... ;)"))))
                                        .addDisplayable(new ChoiceWheel(
                                                new Choice(Text.of("Sure, I loooooove candy"), Text.of("Get quest"), IDialogueInitiator.getIDPrefix(dialogueBuilder)+ "sure", player,
                                                        new GiveQuest(QuestReference.TEST), new DisplayDialogue(){
                                                    @Override
                                                    public Dialogue buildToDisplay(Player player) {
                                                        return dialogueBuilder.stringID("testYes").addDisplayable(new Sentence(Text.of("Oh thank you!"))).build(player);
                                                    }
                                                }),
                                                new Choice(Text.of("Stranger danger!"), null, IDialogueInitiator.getIDPrefix(dialogueBuilder)
                                                        + "danger", player, new DisplayDialogue(){
                                                    @Override
                                                    public Dialogue buildToDisplay(Player player) {
                                                        return dialogueBuilder.stringID("testNo")
                                                                .addDisplayable(new Sentence(Text.of("Well up yours buddy!"))).build(player);
                                                    }
                                                })))
                                        .build(player);
                            }
                        })).addConditions(null))
                .build(player);

        /*@Override
        public Dialogue buildToDisplay(Player player) {
            return dialogueBuilder.stringID("testNo")
                    .addDisplayable(displayableBuilder
                            .setChoiceWheel(false)
                            .addSentence(Text.of("Well up yours buddy!")))
                    .build(player);
        }*/
    }
}
