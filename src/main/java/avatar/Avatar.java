package avatar;

import com.google.inject.Inject;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GameStoppingEvent;
import org.spongepowered.api.plugin.Plugin;

import java.util.logging.Logger;

@Plugin(id = "avatar", name="Avatar", version="1.0.0")
public class Avatar {

    public static Avatar INSTANCE;

    @Inject
    private Logger logger;

    @Listener
    public void onServerStart(GameInitializationEvent event){
        INSTANCE = this;
    }

    @Listener
    public void onServerStopping(GameStoppingEvent event){

    }

    public Logger getLogger() {
        return logger;
    }
}
