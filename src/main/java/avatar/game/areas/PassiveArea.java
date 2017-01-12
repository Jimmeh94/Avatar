package avatar.game.areas;

import org.spongepowered.api.text.Text;

public class PassiveArea extends Area {

    public PassiveArea(AreaShape shape, Text displayName) {
        super(shape, displayName, AreaReferences.TEST_PASSIVE);
    }
}
