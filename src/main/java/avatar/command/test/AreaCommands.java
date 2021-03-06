package avatar.command.test;

import avatar.Avatar;
import avatar.game.area.Area;
import avatar.game.area.AreaReferences;
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

public class AreaCommands implements CommandExecutor {

    public AreaCommands(){
        //particle effectName
        CommandSpec commandSpec = CommandSpec.builder()
                .description(Text.of("Do area stuff"))
                .executor(this)
                .arguments(GenericArguments.string(Text.of("type")), GenericArguments.integer(Text.of("size")))
                .build();
        Sponge.getCommandManager().register(Avatar.INSTANCE, commandSpec, "area");
    }

    @Override
    public CommandResult execute(CommandSource commandSource, CommandContext args) throws CommandException {
        Player player = (Player) commandSource;
        String type = args.<String>getOne("type").get().toLowerCase();
        int size = args.<Integer>getOne("size").get();

        switch (type){
            case "circle": Avatar.INSTANCE.getAreaManager().add(new Area(AreaReferences.TEST));
                break;
            case "square": Avatar.INSTANCE.getAreaManager().add(new Area(AreaReferences.TEST));
        }
        return CommandResult.success();
    }
}
