package avatar;

import avatar.commands.DialogueCommands;
import avatar.commands.QuestCommands;
import avatar.commands.test.AreaCommands;
import avatar.commands.test.ParticleEffectCommands;
import avatar.events.InventoryClick;
import avatar.events.PlayerConnection;
import avatar.game.dialogue.core.DialogueBuilder;
import avatar.game.quests.quests.builders.QuestBuilder;
import avatar.managers.*;
import avatar.runnables.GameTimer;
import avatar.utilities.database.MongoUtils;
import com.google.inject.Inject;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartingServerEvent;
import org.spongepowered.api.event.game.state.GameStoppingEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;

import java.util.logging.Logger;

@Plugin(id = "avatar", name="Avatar", version="1.0.0")
public class Avatar {

    //TODO fix how the quest checkpoints reset
    //TODO fix quest inventory click
    //TODO work on instances for areas

    public static Avatar INSTANCE;

    //--- managers ---
    private UserManager userManager;
    private AreaManager areaManager;
    private AbilityManager abilityManager;
    private ChatChannelManager chatChannelManager;
    private EconomyManager economyManager;

    //--- Runnables ---
    private GameTimer gameTimer;

    //--- misc ---
    private final int combatInterval = 5; //how many seconds out of combat needed to be switched to out of combat
    private MongoUtils mongoUtils;
    private final QuestBuilder questBuilder = new QuestBuilder();
    private final DialogueBuilder dialogueBuilder = new DialogueBuilder();

    @Inject
    private Logger logger;

    @Listener
    public void onGameInit(GameInitializationEvent event){
        INSTANCE = this;

        mongoUtils = new MongoUtils("", "", "");

        registerData();
    }

    @Listener
    public void onServerStarting(GameStartingServerEvent event){
        userManager = new UserManager();
        areaManager = new AreaManager();
        abilityManager = new AbilityManager();
        chatChannelManager = new ChatChannelManager();
        economyManager = new EconomyManager();

        registerListeners();
        registerCommands();
        registerRunnables();
    }

    @Listener
    public void onServerStopping(GameStoppingEvent event){

    }

    private void registerData(){
        //Sponge.getDataManager().register(QuestData.class, QuestData.Immutable.class, QuestData.Builder.class);
    }

    private void registerRunnables(){gameTimer = new GameTimer(5L);}

    private void registerCommands() {
        new ParticleEffectCommands();
        new AreaCommands();
        new DialogueCommands();
        new QuestCommands();
    }

    private void registerListeners(){
        Sponge.getEventManager().registerListeners(this, new PlayerConnection());
        Sponge.getEventManager().registerListeners(this, new InventoryClick());
    }

    public PluginContainer getPluginContainer(){return Sponge.getPluginManager().fromInstance(this).get();}

    public MongoUtils getMongoUtils() {
        return mongoUtils;
    }

    public Logger getLogger() {
        return logger;
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public AreaManager getAreaManager() {
        return areaManager;
    }

    public QuestBuilder getQuestBuilder(){return questBuilder;}

    public DialogueBuilder getDialogueBuilder(){return dialogueBuilder;}

    public AbilityManager getAbilityManager() {
        return abilityManager;
    }

    public int getCombatInterval() {
        return combatInterval;
    }

    public Cause getDefaultCause(){return Cause.source(Avatar.INSTANCE.getPluginContainer()).build();}

    public ChatChannelManager getChatChannelManager() {
        return chatChannelManager;
    }

    public EconomyManager getEconomyManager() {
        return economyManager;
    }
}
