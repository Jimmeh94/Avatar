package avatar.command.test;

import avatar.Avatar;
import avatar.game.ability.abilities.fire.Fireball;
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

public class AbilityCommands implements CommandExecutor {

    public AbilityCommands(){
        //particle effectName
        CommandSpec commandSpec = CommandSpec.builder()
                .description(Text.of("Do ability stuff"))
                .executor(this)
                .arguments(GenericArguments.string(Text.of("type")))
                .build();
        Sponge.getCommandManager().register(Avatar.INSTANCE, commandSpec, "ability");
    }

    @Override
    public CommandResult execute(CommandSource commandSource, CommandContext args) throws CommandException {
        Player player = (Player) commandSource;
        String type = args.<String>getOne("type").get().toLowerCase();
        int size = args.<Integer>getOne("size").get();

        switch (type){
            case "fireball": new Fireball(Avatar.INSTANCE.getUserManager().findUserPlayer(player).get(), 1.0, 1.0, 1.0, 0.2, 5L);
        }
        return CommandResult.success();
    }
}
