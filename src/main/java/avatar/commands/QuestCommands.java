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

public class QuestCommands implements CommandExecutor {

    public QuestCommands(){
        //particle effectName
        CommandSpec commandSpec = CommandSpec.builder()
                .description(Text.of("Do quest stuff"))
                .executor(this)
                .arguments(GenericArguments.string(Text.of("action")), GenericArguments.string(Text.of("id")))
                .build();
        Sponge.getCommandManager().register(Avatar.INSTANCE, commandSpec, "quests");
    }

    @Override
    public CommandResult execute(CommandSource commandSource, CommandContext args) throws CommandException {
        Player player = (Player) commandSource;
        String action = args.<String>getOne("action").get().toLowerCase();
        String id = args.<String>getOne("id").get();
        Avatar a = Avatar.INSTANCE;

        if(action.equalsIgnoreCase("start")){
            System.out.println("Inside start");
            a.getQuestManager().giveQuest(a.getUserManager().findUserPlayer(player).get(), id);
            a.getQuestManager().setActiveQuest(a.getUserManager().findUserPlayer(player).get(), id);
            a.getUserManager().findUserPlayer(player).get().generateQuestMenu();
        }
        return CommandResult.success();
    }
}
