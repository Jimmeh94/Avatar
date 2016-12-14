package avatar.user;

import avatar.user.stats.IStatsPreset;
import avatar.utilities.particles.ParticleUtils;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;

import java.util.Optional;
import java.util.UUID;

public class UserPlayer extends User {

    private ParticleUtils.ParticleModifier particleModifier = ParticleUtils.ParticleModifier.NORMAL;

    public UserPlayer(UUID user) {
        super(user);
    }

    public UserPlayer(UUID user, IStatsPreset preset){
        super(user, preset);
    }

    @Override
    public void cleanUp(){
        super.cleanUp();


    }

    //--- Getters ---

    public Optional<Player> getPlayer(){return Sponge.getServer().getPlayer(getUUID());}

    public ParticleUtils.ParticleModifier getParticleModifier() {
        return particleModifier;
    }

    //--- Setters ---

    public void setParticleModifier(ParticleUtils.ParticleModifier particleModifier) {
        this.particleModifier = particleModifier;
    }
}
