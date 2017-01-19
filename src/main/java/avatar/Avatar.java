package avatar;

import avatar.command.ChatCommands;
import avatar.command.DialogueCommands;
import avatar.command.QuestCommands;
import avatar.command.test.AreaCommands;
import avatar.command.test.ParticleEffectCommands;
import avatar.data.tags.ConfirmationButtonData;
import avatar.data.tags.QuestData;
import avatar.event.ChatEvents;
import avatar.event.InventoryEvents;
import avatar.event.PlayerConnectionEvents;
import avatar.game.dialogue.core.DialogueBuilder;
import avatar.game.quest.builder.QuestBuilder;
import avatar.manager.*;
import avatar.runnable.GameTimer;
import avatar.util.database.MongoUtils;
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
    //TODO work on instances for area

    public static Avatar INSTANCE;

    //--- manager ---
    private UserManager userManager;
    private AreaManager areaManager;
    private ChatChannelManager chatChannelManager;
    private EconomyManager economyManager;
    private ConfirmationMenuManager confirmationMenuManager;

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
        chatChannelManager = new ChatChannelManager();
        areaManager = new AreaManager();
        economyManager = new EconomyManager();
        confirmationMenuManager = new ConfirmationMenuManager();

        registerListeners();
        registerCommands();
        registerRunnables();
    }

    @Listener
    public void onServerStopping(GameStoppingEvent event){

    }

    private void registerData(){
        Sponge.getDataManager().register(QuestData.class, QuestData.Immutable.class, new QuestData.Builder(QuestData.class, 1));
        Sponge.getDataManager().register(ConfirmationButtonData.class, ConfirmationButtonData.Immutable.class, new ConfirmationButtonData.Builder(ConfirmationButtonData.class, 1));
    }

    private void registerRunnables(){gameTimer = new GameTimer(5L);}

    private void registerCommands() {
        new ParticleEffectCommands();
        new AreaCommands();
        new DialogueCommands();
        new QuestCommands();
        new ChatCommands();
    }

    private void registerListeners(){
        Sponge.getEventManager().registerListeners(this, new PlayerConnectionEvents());
        Sponge.getEventManager().registerListeners(this, new InventoryEvents());
        Sponge.getEventManager().registerListeners(this, new ChatEvents());
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

    public ConfirmationMenuManager getConfirmationMenuManager() {
        return confirmationMenuManager;
    }
}
