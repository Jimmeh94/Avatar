package avatar.game.quests.quests;

import avatar.Avatar;
import avatar.events.custom.QuestEvent;
import avatar.game.user.UserPlayer;
import avatar.utilities.directional.PlayerDirection;
import avatar.utilities.text.AltCodes;
import avatar.utilities.text.Messager;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Quest {



    /*
     * General quest container.
     */

    private Text title, description;
    private Optional<UserPlayer> owner = Optional.empty();
    private int recommendedLvl = 0;
    String id;
    private boolean active = false;
    private List<Checkpoint> checkpoints;
    private int currentCheckpoint = 0;
    private ItemStack itemRepresentation;
    private Reward reward;


    /*
     * All quests are loaded on server start up from a database and stored as "templates"
     * That's what this constructor is for
     */
    public Quest(String title, String description, int lvl, String id, List<Checkpoint> checkpoints, ItemType itemType, Reward reward){
        this.title = Text.of(TextColors.GOLD, title);
        this.description = Text.of(TextColors.WHITE, description);
        recommendedLvl = lvl;
        this.id = id;
        this.checkpoints = checkpoints;
        this.itemRepresentation = ItemStack.builder().itemType(itemType).build();
        itemRepresentation.offer(avatar.data.Keys.QUEST_ID, this.id);
        itemRepresentation.offer(Keys.DISPLAY_NAME, getTitle());
        this.reward = reward;
    }

    /*
     * Use this constructor to give a quest to a player.
     * We set the player here due to how we have the quest load structure set up
     * Currently, quests are being loaded from a database, so once quests are loaded on server
     * initialization, we don't pass in any "personal" info. The quests are stored as "templates"
     * and we then pass personal info to these templates once the player needs a specific quest
     */
    public Quest(Quest quest, UserPlayer playerInfo){
        title = quest.getTitle();
        description = quest.getDescription();
        owner = Optional.of(playerInfo);
        id = quest.getID();
        checkpoints = new ArrayList<>(quest.getCheckpoints());
        this.itemRepresentation = quest.getItemRepresentation().copy();
        setLore();
        for(Checkpoint c: checkpoints){
            c.setPlayer(owner.get().getPlayer().get());
        }
        this.reward = quest.getReward();
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

            Sponge.getEventManager().post(new QuestEvent.CheckpointComplete(Avatar.INSTANCE.getDefaultCause(), owner.get(), this, checkpoints.get(currentCheckpoint)));

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
            Messager.sendActionBarMessage(owner.get().getPlayer().get(), Text.builder().append(Text.of(TextColors.GRAY, checkpoints.get(currentCheckpoint).getDescription().get() + " "))
                    .append(Text.of(TextColors.GOLD, String.valueOf(distance) + " "))
                    .append(PlayerDirection.getDesiredDirection(owner.get().getPlayer().get(), checkpoints.get(currentCheckpoint).getTargetLocation().get())).build());
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
        Messager.sendTitleAndSubTitle(owner.get().getPlayer().get(), Text.of(TextColors.GOLD, getTitle()), Text.of(TextColors.GREEN, "Completed"));
        if(reward != null)
            reward.giveAward(owner.get().getPlayer().get());

        Sponge.getEventManager().post(new QuestEvent.Complete(Avatar.INSTANCE.getDefaultCause(), owner.get(), this));

        active = false;
    }

    public String getID(){return id;}

    public Text getTitle() {
        return title;
    }

    public Text getDescription() {
        return description;
    }

    public Optional<UserPlayer> getOwner() {
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
            Messager.sendTitleAndSubTitle(owner.get().getPlayer().get(), getTitle(), getDescription());

            Sponge.getEventManager().post(new QuestEvent.Start(Avatar.INSTANCE.getDefaultCause(), owner.get(), this));
        }
    }

    public List<Checkpoint> getCheckpoints() {
        return checkpoints;
    }

    public ItemStack getItemRepresentation() {
        return itemRepresentation;
    }
}
