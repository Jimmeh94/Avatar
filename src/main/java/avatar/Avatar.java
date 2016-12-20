package avatar;

import avatar.commands.test.AreaCommands;
import avatar.commands.test.ParticleEffectCommands;
import avatar.events.AreaEvents;
import avatar.events.PlayerConnection;
import avatar.managers.AreaManager;
import avatar.managers.UserManager;
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

        registerListeners();
        registerCommands();
    }

    @Listener
    public void onServerStopping(GameStoppingEvent event){

    }

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
}
