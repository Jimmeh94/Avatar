package avatar.game.chatchannels;

import avatar.Avatar;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageReceiver;
import org.spongepowered.api.text.channel.MutableMessageChannel;
import org.spongepowered.api.text.chat.ChatType;

import java.util.*;

public abstract class ChatChannel implements MutableMessageChannel {

    private String key;
    protected Set<MessageReceiver> members;

    protected abstract Optional<Text> messageTransform(Object sender, MessageReceiver recipient, Text original, ChatType type);

    public ChatChannel(String key) {
        this(Collections.emptySet(), key);
    }

    public ChatChannel(Collection<MessageReceiver> members, String key) {
        this.members = Collections.newSetFromMap(new WeakHashMap<>());
        this.members.addAll(members);
        this.key = key;

        Avatar.INSTANCE.getChatChannelManager().add(this);
    }

    public String getKey() {
        return key;
    }

    @Override
    public Collection<MessageReceiver> getMembers() {
        return Collections.unmodifiableSet(this.members);
    }

    @Override
    public boolean addMember(MessageReceiver member) {
        if(members.contains(member))
            return false;
        return this.members.add(member);
    }

    @Override
    public boolean removeMember(MessageReceiver member) {
        return this.members.remove(member);
    }

    @Override
    public void clearMembers() {
        this.members.clear();
    }

    @Override
    public Optional<Text> transformMessage(Object sender, MessageReceiver recipient, Text original, ChatType type) {
        return messageTransform(sender, recipient, original, type);
    }
}
