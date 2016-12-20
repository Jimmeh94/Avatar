package avatar;

import avatar.commands.test.AreaCommands;
import avatar.commands.test.ParticleEffectCommands;
import avatar.events.AreaEvents;
import avatar.events.PlayerConnection;
import avatar.game.dialogue.core.builders.DialogueBuilder;
import avatar.game.quests.quests.builders.QuestBuilder;
import avatar.managers.AreaManager;
import avatar.managers.DialogueManager;
import avatar.managers.QuestManager;
import avatar.managers.UserManager;
import avatar.runnables.GameTimer;
import avatar.utilities.database.MongoUtils;
import com.google.inject.Inject;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartingServerEvent;
import org.spongepowered.api.event.game.state.GameStoppingEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;

import java.util.logging.Logger;

@Plugin(id = "avatar", name="Avatar", version="1.0.0")
public class Avatar {

    public static Avatar INSTANCE;

    private MongoUtils mongoUtils;

    //--- managers ---
    private UserManager userManager;
    private AreaManager areaManager;
    private QuestManager questManager;
    private DialogueManager dialogueManager;

    //--- Runnables ---
    private GameTimer gameTimer;

    //--- misc ---
    private final QuestBuilder questBuilder = new QuestBuilder();
    private final DialogueBuilder dialogueBuilder = new DialogueBuilder();

    @Inject
    private Logger logger;

    @Listener
    public void onGameInit(GameInitializationEvent event){
        INSTANCE = this;

        mongoUtils = new MongoUtils("", "", "");
    }

    @Listener
    public void onServerStarting(GameStartingServerEvent event){
        userManager = new UserManager();
        areaManager = new AreaManager();
        questManager = new QuestManager();
        dialogueManager = new DialogueManager();

        registerListeners();
        registerCommands();
        registerRunnables();

        questManager.loadQuests();
        dialogueManager.loadDialogue();
    }

    @Listener
    public void onServerStopping(GameStoppingEvent event){

    }

    private void registerRunnables(){gameTimer = new GameTimer(10L);}

    private void registerCommands() {
        new ParticleEffectCommands();
        new AreaCommands();
    }

    private void registerListeners(){
        Sponge.getEventManager().registerListeners(this, new AreaEvents());
        Sponge.getEventManager().registerListeners(this, new PlayerConnection());
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

    public QuestManager getQuestManager() {
        return questManager;
    }

    public DialogueManager getDialogueManager(){return dialogueManager;}

    public DialogueBuilder getDialogueBuilder(){return dialogueBuilder;}
}
