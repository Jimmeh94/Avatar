package avatar.managers;

import avatar.game.chat.channel.ChatChannel;
import avatar.game.user.UserPlayer;
import avatar.utilities.text.Messager;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.Optional;

public class ChatChannelManager extends Manager<ChatChannel> {

    public boolean isKeyAvailable(String key){
        return !findChannel(key).isPresent();
    }

    private Optional<ChatChannel> findChannel(String key){
        for(ChatChannel chatChannel: objects){
            if(chatChannel.getKey().equalsIgnoreCase(key)){
                return Optional.of(chatChannel);
            }
        }
        return Optional.empty();
    }

    public void setToDefault(UserPlayer userPlayer){
        userPlayer.setChatChannel(ChatChannel.GLOBAL);
    }

    public void setChannel(UserPlayer userPlayer, String key){
        Optional<ChatChannel> channel = findChannel(key);

        if(channel.isPresent() && channel.get() != userPlayer.getChatChannel()){
            userPlayer.setChatChannel(channel.get());
            Messager.sendMessage(userPlayer.getPlayer().get(), Text.of("Moved to chat channel: " + channel.get().getKey()));
        } else {
            Messager.sendMessage(userPlayer.getPlayer().get(), Text.of(TextColors.RED, "The channel doesn't exist or you are already in it: " + key));
        }
    }

}
