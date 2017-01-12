package avatar.game.areas;

import org.spongepowered.api.text.Text;

public class HostileArea extends Area {

    public HostileArea(AreaShape shape, Text displayName) {
        super(shape, displayName, AreaReferences.TEST_HOSTILE);
    }
}
