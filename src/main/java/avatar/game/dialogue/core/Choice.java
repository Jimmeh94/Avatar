package avatar.game.dialogue.core;

import avatar.Avatar;
import avatar.events.custom.DialogueEvent;
import avatar.user.UserPlayer;
import avatar.utilities.text.AltCodes;
import avatar.utilities.text.Messager;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class Choice implements Consumer<CommandSource>{

    /*
     * This is a clickable choice that is linked to an action
     * These are stored within the ChoiceWheel
     */

    private List<DialogueAction> actions;
    private List<Condition> conditions;
    private Text sentence;
    private Player player;
    private String id;
    private Optional<Text> hover = Optional.empty();

    public Choice(Text text, Text hover, List<DialogueAction> action, String id){
        this.sentence = text;
        this.actions = action;
        if(hover != null){
            this.hover = Optional.of(hover);
        }
        this.id = id;
    }

    public Choice(Choice choice, Player player, List<Condition> condition){
        this.actions = choice.getAction();
        this.sentence = Text.of(choice.getSentence());
        this.player = player;
        this.conditions = condition;
        this.id = choice.getId();
        this.hover = choice.hover;

        if(hover.isPresent()){
            sentence = Text.builder().append(Text.of(TextColors.GREEN, TextStyles.BOLD, AltCodes.ARROW_RIGHT.getSign() + " "), sentence)
                    .onClick(TextActions.executeCallback(this)).onHover(TextActions.showText(hover.get())).build();
        } else {
            sentence = Text.builder().append(Text.of(TextColors.GOLD, TextStyles.BOLD, AltCodes.ARROW_RIGHT.getSign() + " "), sentence)
                    .onClick(TextActions.executeCallback(this)).build();
        }
    }

    public void display(Player player) {
        Messager.sendMessage(player, sentence);
    }

    public List<DialogueAction> getAction() {
        return actions;
    }

    public Text getSentence() {
        return sentence;
    }

    @Override
    public void accept(CommandSource commandSource) {
        Optional<UserPlayer> temp = Avatar.INSTANCE.getUserManager().findUserPlayer(this.player);
        if(temp.isPresent() && temp.get().getCurrentDialogue() != null && temp.get().getCurrentDialogue().hasChoiceID(this.id)) {
            for(Condition condition: conditions){
                if(!condition.isValid(player)){
                    condition.sendErrorMessage(player);
                    return;
                }
            }
            Avatar.INSTANCE.getDialogueManager().removeDialogue(this.player);

            //if all conditions are valid, continue with action
            for (DialogueAction action : this.actions)
                action.doWork(player);

            Sponge.getEventManager().post(new DialogueEvent.ChoiceClicked(Cause.source(Avatar.INSTANCE.getPluginContainer()).build(), this.getId(), temp.get()));
        }

    }

    public String getId() {
        return id;
    }
}
