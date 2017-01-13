package avatar.game.areas;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public enum AreaReferences {

    //List children first, then parent after

    //Children of GLOBAL
    TEST(new Area.AreaCircle(new Location(Sponge.getServer().getWorlds().toArray(new World[]{})[0], 50, 50, 50), 10, 256), Text.of("Test Area")),
    GLOBAL(null, Text.of("Global Wilderness"), AreaReferences.TEST);
    //--------------------

    private Area.AreaShape shape;
    private Text displayName;
    private AreaReferences[] children;

    AreaReferences(Area.AreaShape shape, Text displayName, AreaReferences... children){
        this.shape = shape;
        this.displayName = displayName;
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
}
