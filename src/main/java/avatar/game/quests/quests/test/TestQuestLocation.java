package avatar.game.quests.quests.test;

import avatar.Avatar;
import avatar.game.areas.Area;
import avatar.game.areas.AreaReferences;
import avatar.game.quests.quests.IQuestInitiator;
import avatar.game.quests.quests.Quest;
import avatar.game.quests.quests.Reward;
import avatar.game.quests.quests.builders.CheckpointBuilder;
import avatar.game.quests.quests.builders.QuestBuilder;
import avatar.game.quests.quests.conditions.*;
import avatar.game.user.UserPlayer;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.ArrayList;

public class TestQuestLocation implements IQuestInitiator{

    @Override
    public Quest createLocationTest(UserPlayer userPlayer){
        Location use = new Location<World>((new ArrayList<>(Sponge.getServer().getWorlds()).get(0)), 55, 55, 55);
        Location use2 = new Location((new ArrayList<>(Sponge.getServer().getWorlds()).get(0)), 51, 55, 51);
        QuestBuilder questBuilder = Avatar.INSTANCE.getQuestBuilder();
        CheckpointBuilder checkpointBuilder = questBuilder.getCheckpointBuilder();

        checkpointBuilder.description("Go to a location")
                .targetLocation(use)
                .condition(new ReachLocation(use, 1.5))
                .condition(new TimeLimit(60))
                .buildCheckpoint();
        checkpointBuilder.description("Stay within quest region and reach target location")
                .targetLocation(use2)
                .condition(new BoundRadius(10.0, use))
                .condition(new ReachLocation(use2, 1.5))
                .buildCheckpoint();
        checkpointBuilder.description("Talk to Old Man")
                .targetLocation(use2)
                .condition(new ClickDialogueChoice("test.with"))
                .buildCheckpoint();

        Area test2 = Avatar.INSTANCE.getAreaManager().getAreaByReference(AreaReferences.TEST2).get();
        checkpointBuilder.description("Reach Test2")
                .targetLocation(test2.getCenter())
                .condition(new ReachArea(test2))
                .buildCheckpoint();


        Quest quest = questBuilder.name("Test").description("This is a test quest").level(1).setID("test").checkpoints()
                .itemType(ItemTypes.PAPER).reward(new TestReward()).owner(userPlayer).build();
        questBuilder.reset();
        return quest;
    }

    private static class TestReward implements Reward {
        @Override
        public Text getDescription() {
            return Text.of("Hammer of Doom");
        }

        @Override
        public void giveAward(Player player) {
            player.getInventory().offer(ItemStack.of(ItemTypes.ACACIA_DOOR, 1));
        }
    }

}
