package avatar.user;

import avatar.Avatar;
import avatar.events.custom.DialogueEvent;
import avatar.game.dialogue.core.containers.Dialogue;
import avatar.game.quests.menus.QuestMenu;
import avatar.game.quests.quests.Quest;
import avatar.user.stats.IStatsPreset;
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

    public UserPlayer(UUID user) {
        super(user);
    }

    public UserPlayer(UUID user, IStatsPreset preset){
        super(user, preset);

        //TODO populate quests
        //questMenu = new QuestMenu(this);
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
