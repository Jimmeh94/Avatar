package avatar;

import avatar.managers.UserManager;
import avatar.utilities.database.MongoUtils;
import com.google.inject.Inject;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GameStoppingEvent;
import org.spongepowered.api.plugin.Plugin;

import java.util.logging.Logger;

@Plugin(id = "avatar", name="Avatar", version="1.0.0")
public class Avatar {

    public static Avatar INSTANCE;

    private UserManager userManager;
    private MongoUtils mongoUtils;

    @Inject
    private Logger logger;

    @Listener
    public void onServerStart(GameInitializationEvent event){
        INSTANCE = this;
        userManager = new UserManager();

        mongoUtils = new MongoUtils("", "", "");
    }

    @Listener
    public void onServerStopping(GameStoppingEvent event){

    }

    public MongoUtils getMongoUtils() {
        return mongoUtils;
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public Logger getLogger() {
        return logger;
    }
}
