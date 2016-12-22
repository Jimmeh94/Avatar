package avatar.game.quests.quests.test;

import avatar.Avatar;
import avatar.game.quests.quests.Quest;
import avatar.game.quests.quests.builders.CheckpointBuilder;
import avatar.game.quests.quests.builders.QuestBuilder;
import avatar.game.quests.quests.conditions.BoundRadius;
import avatar.game.quests.quests.conditions.ReachLocation;
import avatar.game.quests.quests.conditions.TimeLimit;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.ArrayList;

public class TestQuestLocation {

    public static Quest createLocationTest(){
        Location use = new Location<World>((new ArrayList<>(Sponge.getServer().getWorlds()).get(0)), 55, 55, 55);
        Location use2 = new Location((new ArrayList<>(Sponge.getServer().getWorlds()).get(0)), 51, 55, 51);
        QuestBuilder questBuilder = Avatar.INSTANCE.getQuestBuilder();
        CheckpointBuilder checkpointBuilder = questBuilder.getCheckpointBuilder();

        checkpointBuilder.description("Go to a location")
                .targetLocation(use)
                .condition(new ReachLocation(false, use, 1.5))
                .condition(new TimeLimit(true, 60))
                .buildCheckpoint();
        checkpointBuilder.description("Stay within quest region and reach target location")
                .targetLocation(use2)
                .condition(new BoundRadius(true, 10.0, use))
                .condition(new ReachLocation(false, use2, 1.5))
                .buildCheckpoint();

        Quest quest = questBuilder.name("Test").description("This is a test quest").level(1).setID("test").checkpoints().itemType(ItemTypes.PAPER).reward(new TestReward()).build();
        questBuilder.reset();
        return quest;
    }

}
