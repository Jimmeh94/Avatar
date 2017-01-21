package avatar.game.quest;

import avatar.Avatar;
import avatar.event.custom.QuestEvent;
import avatar.game.user.UserPlayer;
import avatar.util.misc.PlayerDirection;
import avatar.util.text.AltCodes;
import avatar.util.text.Messager;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import java.util.ArrayList;
import java.util.List;

public class Quest {

    /*
     * General quest container.
     */

    private Text title, description;
    private UserPlayer owner;
    private int recommendedLvl = 0;
    String id;
    private boolean active = false;
    private List<Checkpoint> checkpoints;
    private int currentCheckpoint = 0;
    private ItemStack itemRepresentation;
    private Reward reward;

    public Quest(String title, String description, int lvl, String id, List<Checkpoint> checkpoints, ItemType itemType, Reward reward, UserPlayer userPlayer){
        this.title = Text.of(TextColors.GOLD, title);
        this.description = Text.of(TextColors.WHITE, description);
        recommendedLvl = lvl;
        this.id = id;
        this.checkpoints = checkpoints;
        this.itemRepresentation = ItemStack.builder().itemType(itemType).build();
        itemRepresentation.offer(avatar.data.Keys.QUEST_ID, this.id);
        itemRepresentation.offer(Keys.DISPLAY_NAME, getTitle());
        this.reward = reward;
        setLore();
        this.owner = userPlayer;
        for(Checkpoint c: checkpoints){
            c.setPlayer(owner.getPlayer().get());
        }
    }

    /**
     * This is for the item representation
     */
    public void setLore(){
        List<Text> temp = new ArrayList<>();
        temp.add(Text.of(TextStyles.ITALIC, description));
        temp.add(Text.of(" "));
        temp.add(Text.of(TextColors.GRAY, "Objectives:"));
        temp.add(Text.of(" "));
        for(Checkpoint checkpoint: checkpoints){
            if(checkpoint.isCompleted()){
                temp.add(Text.of(TextColors.GREEN, AltCodes.FILLED_CIRCLE.getSign() + " " + checkpoint.getDescription().get()));
            } else temp.add(Text.of(TextColors.GRAY, AltCodes.FILLED_CIRCLE.getSign() + " " + checkpoint.getDescription().get()));
            temp.add(Text.of(" "));
        }
        if(active){
            temp.add(0, Text.of(" "));
            temp.add(1, Text.of(TextColors.GREEN, "Active"));
            temp.add(2, Text.of(" "));
        }
        itemRepresentation.offer(Keys.ITEM_LORE, temp);
    }

    /*
     * Function that makes things happen.
     * Check if checkpoint is reached
     * if so, is final checkpoint?
     * if so, give reward and finishing dialogue
     * if complete and not final, load next checkpoint and start()
     * Update quest tracker
     */
    public boolean tick(){
        if (checkpoints.get(currentCheckpoint).isComplete()) {
            checkpoints.get(currentCheckpoint).printCompletionMsg();

            Sponge.getEventManager().post(new QuestEvent.CheckpointComplete(Avatar.INSTANCE.getDefaultCause(), owner, this, checkpoints.get(currentCheckpoint)));

            checkpoints.get(currentCheckpoint).deactivate();
            setLore();
            currentCheckpoint++;

            if (currentCheckpoint <= checkpoints.size() - 1) {
                checkpoints.get(currentCheckpoint).start();
            } else {
                completeQuest();
                setLore();
                return true;
            }
        }
        //update tracker
        //get distance from player to target, get arrow direction, send message
        if (checkpoints.get(currentCheckpoint).getTargetLocation().isPresent()) {
            int distance = getTrackerDistance();
            Messager.sendActionBarMessage(owner.getPlayer().get(), Text.builder().append(Text.of(TextColors.GRAY, checkpoints.get(currentCheckpoint).getDescription().get() + " "))
                    .append(Text.of(TextColors.GOLD, String.valueOf(distance) + " "))
                    .append(PlayerDirection.getDesiredDirection(owner.getPlayer().get(), checkpoints.get(currentCheckpoint).getTargetLocation().get())).build());
        }
        return false;
    }

    protected int getTrackerDistance(){
        return checkpoints.get(currentCheckpoint).getTrackerDistance();
    }

    public Reward getReward() {
        return reward;
    }

    private void completeQuest(){
        Messager.sendTitleAndSubTitle(owner.getPlayer().get(), Text.of(TextColors.GOLD, getTitle()), Text.of(TextColors.GREEN, "Completed"));
        if(reward != null)
            reward.giveAward(owner.getPlayer().get());

        Sponge.getEventManager().post(new QuestEvent.Complete(Avatar.INSTANCE.getDefaultCause(), owner, this));

        active = false;
    }

    public String getID(){return id;}

    public Text getTitle() {
        return title;
    }

    public Text getDescription() {
        return description;
    }

    public UserPlayer getOwner() {
        return owner;
    }

    public boolean isActive() {
        return active;
    }

    public void toggleActive() {
        if(active){
            active = false;
            setLore();

            for(Checkpoint checkpoint: checkpoints){
                checkpoint.deactivate();
            }
        } else {
            active = true;
            setLore();
            checkpoints.get(currentCheckpoint).start();
            Messager.sendTitleAndSubTitle(owner.getPlayer().get(), getTitle(), getDescription());

            Sponge.getEventManager().post(new QuestEvent.Start(Avatar.INSTANCE.getDefaultCause(), owner, this));
        }
    }

    public List<Checkpoint> getCheckpoints() {
        return checkpoints;
    }

    public ItemStack getItemRepresentation() {
        return itemRepresentation;
    }
}
