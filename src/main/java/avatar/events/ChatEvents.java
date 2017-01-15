package avatar.events;

import avatar.Avatar;
import avatar.game.chat.ChatColorTemplate;
import avatar.game.user.UserPlayer;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.message.MessageChannelEvent;
import org.spongepowered.api.text.Text;

public class ChatEvents {

    @Listener
    public void onChat(MessageChannelEvent.Chat event, @First Player player){
        event.setCancelled(true);
        UserPlayer userPlayer = Avatar.INSTANCE.getUserManager().findUserPlayer(player).get();
        userPlayer.getChatChannel().displayMessage(build(event.getRawMessage().toPlain(), userPlayer));
    }

    private Text build(String message, UserPlayer userPlayer){
        ChatColorTemplate color = userPlayer.getChatColorTemplate();
        return Text.builder().append(Text.of(color.getPrefix(), userPlayer.getTitle().getDisplay()))
                .append(Text.of(color.getName(), userPlayer.getPlayer().get().getName() + ": "))
                .append(Text.of(color.getMessage(), message)).build();
    }

}
