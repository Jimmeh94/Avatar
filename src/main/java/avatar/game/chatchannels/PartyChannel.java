package avatar.game.chatchannels;

import avatar.game.user.UserPlayer;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageReceiver;
import org.spongepowered.api.text.chat.ChatType;

import java.util.Collection;
import java.util.Optional;

public class PartyChannel extends ChatChannel {

    private static String getKey(UserPlayer userPlayer){
        return "Party" + userPlayer.getPlayer().get().getName();
    }

    public PartyChannel(UserPlayer userPlayer) {
        super(getKey(userPlayer));
    }
    public PartyChannel(Collection<MessageReceiver> members, String key) {
        super(members, key);
    }

    @Override
    protected Optional<Text> messageTransform(Object sender, MessageReceiver recipient, Text original, ChatType type) {
        Text text = original;
        if(this.members.contains(recipient)) {

        }
        return Optional.of(text);
    }

}
