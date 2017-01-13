package avatar.user;

import avatar.Avatar;
import avatar.events.custom.DialogueEvent;
import avatar.game.areas.Area;
import avatar.game.areas.AreaReferences;
import avatar.game.dialogue.core.containers.Dialogue;
import avatar.game.quests.menus.QuestMenu;
import avatar.game.quests.quests.Quest;
import avatar.game.scoreboard.Scoreboard;
import avatar.user.stats.IStatsPreset;
import avatar.utilities.misc.LocationUtils;
import avatar.utilities.particles.ParticleUtils;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.world.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserPlayer extends User {

    private ParticleUtils.ParticleModifier particleModifier = ParticleUtils.ParticleModifier.NORMAL;
    private Optional<Location> lastBlockLocation = Optional.empty();

    private List<Quest> quests = new ArrayList<>();
    private QuestMenu questMenu;

    private Dialogue currentDialogue;
    private Account account;
    private Scoreboard scoreboard;

    public UserPlayer(UUID user) {
        super(user);

        account = new Account(this);
    }

    public UserPlayer(UUID user, IStatsPreset preset){
        super(user, preset);

        //TODO populate quests
        //questMenu = new QuestMenu(this);

        account = new Account(this);
    }

    public void init(){
        scoreboard = new Scoreboard(this);

        Optional<Area> area = Avatar.INSTANCE.getAreaManager().getAreaByContainedLocation(getPlayer().get().getLocation());
        if(area.isPresent())
            enterArea(area.get());
    }

    @Override
    public void enterArea(Area area){
        super.enterArea(area);

        scoreboard.updateScoreboard();
    }
    
    @Override
    public void tick(){
        super.tick();

        //area checks
        Player player = getPlayer().get();
        boolean doWork = true;

        //If they left an area and where they went to isn't a defined area, put them into a "global" area
        if(getPresentArea() == null){
            Optional<Area> area = Avatar.INSTANCE.getAreaManager().getAreaByReference(AreaReferences.GLOBAL);
            if(area.isPresent()){
                enterArea(area.get());
            }
        }

        if(getLastBlockLocation().isPresent()){
            if(getLastBlockLocation().get().getPosition().distance(player.getLocation().getPosition()) < 1) {
                //traveled less than 1 block
                doWork = false;
            }
        } else {
            setLastBlockLocation(getPlayer().get().getLocation());
            doWork = false;
        }

        if(doWork) {
            //Get a connecting path of locations from where they were to where they are
            List<Location> traveled = LocationUtils.getConnectingLine(getLastBlockLocation().get(), player.getLocation());

            if (traveled.size() > 1) {
                //Where they started
                Optional<Area> temp = Avatar.INSTANCE.getAreaManager().getAreaByContainedLocation(traveled.get(0));
                //Where they are
                Optional<Area> temp2 = Avatar.INSTANCE.getAreaManager().getAreaByContainedLocation(traveled.get(traveled.size() - 1));

                if(temp.isPresent() && temp2.isPresent()){
                    if(temp.get() != temp2.get()){
                        enterArea(temp2.get());
                    }
                }

                /*if (temp.isPresent()) {
                    //the player started inside the area
                    if (!temp2.isPresent()) {
                        //the player ended outside the area
                        leaveArea();
                    }
                    //else they're still inside and nothing to do
                } else {
                    //started outside the area
                    if (temp2.isPresent()) {
                        //ended inside the area
                        enterArea(temp2.get());
                    }
                    //else they're still outside the area
                }*/
            }
            setLastBlockLocation(player.getLocation());
        }

        //scoreboard update
        scoreboard.updateScoreboard();
    }

    @Override
    public void cleanUp(){
        super.cleanUp();
    }

    public void displayQuestMenu(){
        getPlayer().get().openInventory(questMenu.getPage(0), Cause.builder().named(NamedCause.of("Server Action", this)).build());
    }

    /*
     * For testing purposes
     */
    public void generateQuestMenu(){questMenu = new QuestMenu(this);}

    public void resetDialogue() {
        currentDialogue = null;
    }

    public void startDialogue() {
        currentDialogue.displayNext();

        Sponge.getEventManager().post(new DialogueEvent.Displayed(Cause.source(Avatar.INSTANCE.getPluginContainer()).build(), this));
    }

    //--- Getters ---

    public Optional<Player> getPlayer(){return Sponge.getServer().getPlayer(getUUID());}

    public ParticleUtils.ParticleModifier getParticleModifier() {
        return particleModifier;
    }

    public Optional<Location> getLastBlockLocation() {
        return lastBlockLocation;
    }

    public QuestMenu getQuestMenu() {return questMenu;}

    public List<Quest> getQuests(){return quests;}

    public Dialogue getCurrentDialogue() {
        return currentDialogue;
    }

    public Account getAccount() {
        return account;
    }

    //--- Setters ---

    public void setParticleModifier(ParticleUtils.ParticleModifier particleModifier) {
        this.particleModifier = particleModifier;
    }

    public void setLastBlockLocation(Location lastBlockLocation) {
        this.lastBlockLocation = Optional.of(lastBlockLocation);
    }

    public void setCurrentDialogue(Dialogue currentDialogue) {
        this.currentDialogue = currentDialogue;
    }
}
