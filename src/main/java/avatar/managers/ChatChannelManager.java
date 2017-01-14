package avatar.managers;

import avatar.game.chatchannels.ChatChannel;
import avatar.game.user.UserPlayer;
import org.spongepowered.api.entity.living.player.Player;

import java.util.Optional;

public class ChatChannelManager extends Manager<ChatChannel> {

    public Optional<ChatChannel> findChannel(String key){
        for(ChatChannel chatChannel: objects){
            if(chatChannel.getKey().equals(key)){
                return Optional.of(chatChannel);
            }
        }
        return Optional.empty();
    }

    public void setToDefault(UserPlayer userPlayer){
        Optional<Player> player = userPlayer.getPlayer();
        if(player.isPresent()){
            player.get().setMessageChannel(null);
        }
    }

    public boolean setChannel(UserPlayer userPlayer, String key){
        Optional<ChatChannel> channel = findChannel(key);

        if(channel.isPresent()){
            userPlayer.getPlayer().get().setMessageChannel(channel.get());
            return true;
        } else {
            setToDefault(userPlayer);
            return false;
        }
    }

}
