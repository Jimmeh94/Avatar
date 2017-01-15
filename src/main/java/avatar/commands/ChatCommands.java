package avatar.commands;

import avatar.Avatar;
import avatar.game.chat.channel.ChatChannel;
import avatar.utilities.text.Messager;
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
import org.spongepowered.api.text.format.TextColors;

public class ChatCommands implements CommandExecutor {

    public ChatCommands(){
        //chat channel name
        CommandSpec commandSpec = CommandSpec.builder()
                .description(Text.of("Do chat stuff"))
                .executor(this)
                .arguments(GenericArguments.string(Text.of("action")), GenericArguments.string(Text.of("id")))
                .build();
        Sponge.getCommandManager().register(Avatar.INSTANCE, commandSpec, "chat");
    }

    @Override
    public CommandResult execute(CommandSource commandSource, CommandContext args) throws CommandException {
        Player player = (Player) commandSource;
        String action = args.<String>getOne("action").get().toLowerCase();
        String id = args.<String>getOne("id").get();
        Avatar a = Avatar.INSTANCE;

        if(action.equalsIgnoreCase("join")){
            a.getChatChannelManager().setChannel(a.getUserManager().findUserPlayer(player).get(), id);
        } else if(action.equalsIgnoreCase("create")){
            if(a.getChatChannelManager().isKeyAvailable(id)){
                new ChatChannel(ChatChannel.Type.PARTY, id, a.getUserManager().findUserPlayer(player).get());
            } else {
                Messager.sendMessage(player, Text.of(TextColors.RED, "That name is already taken: " + id));
            }
        } else if(action.equalsIgnoreCase("leave")){
            if(a.getUserManager().findUserPlayer(player).get().getChatChannel().getType() != ChatChannel.Type.GLOBAL){
                a.getChatChannelManager().setChannel(a.getUserManager().findUserPlayer(player).get(), "Global");
            }
        }
        return CommandResult.success();
    }
}
