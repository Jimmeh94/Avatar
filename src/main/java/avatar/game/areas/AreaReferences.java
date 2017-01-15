package avatar.game.areas;

import avatar.game.chat.channel.ChatChannel;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public enum AreaReferences {

    //List children first, then parent after

    //Children of GLOBAL
    TEST(new Area.AreaCircle(new Location(Sponge.getServer().getWorlds().toArray(new World[]{})[0], 50, 50, 50), 10, 256), Text.of("Test Area"), null),
    TEST2(new Area.AreaRectangle(new Location(Sponge.getServer().getWorlds().toArray(new World[]{})[0], 75, 50, 75),
            new Location(Sponge.getServer().getWorlds().toArray(new World[]{})[0], 100, 50, 100), 256), Text.of("Test 2"), new ChatChannel(ChatChannel.Type.AREA, "Test2")),
    GLOBAL(null, Text.of("Global Wilderness"), ChatChannel.GLOBAL, TEST, TEST2);
    //--------------------

    private Area.AreaShape shape;
    private Text displayName;
    private ChatChannel chatChannel;
    private AreaReferences[] children;

    AreaReferences(Area.AreaShape shape, Text displayName, ChatChannel chatChannel, AreaReferences... children){
        this.shape = shape;
        this.displayName = displayName;
        this.chatChannel = chatChannel;
        this.children = children;
    }

    public AreaReferences setShape(Area.AreaShape shape) {
        this.shape = shape;
        return this;
    }

    public AreaReferences setDisplayName(Text displayName) {
        this.displayName = displayName;
        return this;
    }

    public AreaReferences setChildren(AreaReferences[] children) {
        this.children = children;
        return this;
    }

    public Area.AreaShape getShape() {
        return shape;
    }

    public Text getDisplayName() {
        return displayName;
    }

    public AreaReferences[] getChildren() {
        return children;
    }

    public ChatChannel getChatChannel() {
        return chatChannel;
    }
}
