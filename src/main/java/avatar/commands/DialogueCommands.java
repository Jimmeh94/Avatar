package avatar.commands;

import avatar.Avatar;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

public class DialogueCommands implements CommandExecutor {

    public DialogueCommands(){
        //particle effectName
        CommandSpec commandSpec = CommandSpec.builder()
                .description(Text.of("Do dialogue stuff"))
                .executor(this)
                .arguments(GenericArguments.string(Text.of("id")))
                .build();
        Sponge.getCommandManager().register(Avatar.INSTANCE, commandSpec, "dialogue");
    }

    @Override
    public CommandResult execute(CommandSource commandSource, CommandContext args) throws CommandException {
        Player player = (Player) commandSource;
        String action = args.<String>getOne("id").get().toLowerCase();
        Avatar a = Avatar.INSTANCE;

        if(action.equalsIgnoreCase("test")){
            a.getUserManager().findUserPlayer(player).get().giveDialogue(action);
        }
        return CommandResult.success();
    }
}
