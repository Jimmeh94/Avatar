package avatar.utilities.particles;

import avatar.Avatar;
import avatar.game.areas.Area;
import avatar.game.user.User;
import avatar.game.user.UserPlayer;
import avatar.utilities.misc.LocationUtils;
import avatar.utilities.particles.effects.EffectData;
import com.flowpowered.math.vector.Vector3d;
import org.spongepowered.api.effect.particle.ParticleEffect;
import org.spongepowered.api.effect.particle.ParticleOption;
import org.spongepowered.api.effect.particle.ParticleType;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.Location;

import java.util.*;

public class ParticleUtils {

    public static class PlayerBased {

        public static void displayParticles(EffectData effectData){
            displayParticles(effectData, Arrays.asList(((UserPlayer)effectData.getOwner()).getPlayer().get()));
        }

        public static void displayParticles(EffectData effectData, Collection<Player> players){
            displayParticles(players, effectData.getParticle(), effectData.getAmount(), effectData.getDisplayAt(), effectData.getxOffset(), effectData.getyOffset(), effectData.getzOffset(),
                    effectData.isRandomizeOffsets(), effectData.getParticleOptions(), effectData.getVelocity());
        }

        public static void displayParticles(Collection<Player> players, ParticleType particleType, int amount, Location location, double xOffset, double yOffset, double zOffset, boolean randomizeOffset, List<ParticleOption> particleOptions, Vector3d velocity) {
            double factor = 1.0;
            for (Player player : players) {
                Optional<User> user = Avatar.INSTANCE.getUserManager().find(player.getUniqueId());
                if(user.isPresent()){
                    if(user.get().isPlayer()){
                        factor = ((UserPlayer)user.get()).getParticleModifier().factor;
                    }
                }

                ParticleEffect.Builder builder = ParticleEffect.builder();

                builder.quantity((int) (amount * factor)).type(particleType);

                if (randomizeOffset) {
                    builder.offset(new Vector3d(xOffset * LocationUtils.getRandomNegOrPos(),
                            yOffset * LocationUtils.getRandomNegOrPos(),
                            zOffset * LocationUtils.getRandomNegOrPos()));
                } else {
                    builder.offset(new Vector3d(xOffset, yOffset, zOffset));
                }

                if (particleOptions != null) {
                    for (ParticleOption option : particleOptions) {
                        builder.option(option, option.getValueType());
                    }
                }

                if (velocity != null) {
                    builder.velocity(velocity);
                }

                player.spawnParticles(builder.build(), location.getPosition());
            }
        }
    }

    public static class AreaBased{
        public static void displayParticles(EffectData effectData) {
            displayParticles(effectData.getDisplayArea(), effectData.getParticle(), effectData.getAmount(), effectData.getDisplayAt(),
                    effectData.getxOffset(), effectData.getyOffset(), effectData.getzOffset(), effectData.isRandomizeOffsets(),
                    effectData.getParticleOptions(), effectData.getVelocity());
        }

        public static void displayParticles(Area area, ParticleType particleType, int amount, Location location, double xOffset, double yOffset, double zOffset, boolean randomizeOffset, List<ParticleOption> particleOptions, Vector3d velocity) {
            for (UserPlayer player : area.getPlayersFromMembers()) {
                Optional<Player> p = player.getPlayer();
                if(!p.isPresent()){
                    continue;
                }

                ParticleEffect.Builder builder = ParticleEffect.builder();

                builder.quantity((int) (amount * player.getParticleModifier().getFactor())).type(particleType);

                if (randomizeOffset) {
                    builder.offset(new Vector3d(location.getX() + xOffset * LocationUtils.getRandomNegOrPos(),
                            location.getY() + yOffset * LocationUtils.getRandomNegOrPos(),
                            location.getZ() + zOffset * LocationUtils.getRandomNegOrPos()));
                } else {
                    builder.offset(new Vector3d(location.getX() + xOffset,
                            location.getY() + yOffset,
                            location.getZ() + zOffset));
                }

                if (particleOptions != null) {
                    for (ParticleOption option : particleOptions) {
                        builder.option(option, option.getValueType());
                    }
                }

                if (velocity != null) {
                    builder.velocity(velocity);
                }

                p.get().spawnParticles(builder.build(), location.getPosition());
            }
        }
    }

    public static class LocationBased{

        public static void displayParticles(EffectData effectData) {
            displayParticles(effectData.getParticle(), effectData.getAmount(), effectData.getDisplayAt(), effectData.getDisplayRadius(),
                    effectData.getxOffset(), effectData.getyOffset(), effectData.getzOffset(), effectData.isRandomizeOffsets(),
                    effectData.getParticleOptions(), effectData.getVelocity());
        }

        public static void displayParticles(ParticleType particleType, int amount, Location location, double radius, double xOffset, double yOffset, double zOffset, boolean randomizeOffset, List<ParticleOption> particleOptions, Vector3d velocity) {
            List<Entity> entities = new ArrayList<>();
            Entity origin = null;

            //find nearest entity
            for(Entity entity: location.getExtent().getEntities()){
                if(entity.getLocation().getPosition().distance(location.getPosition()) > radius)
                    continue;
                if(origin == null){
                    origin = entity;
                } else {
                    if(origin.getLocation().getPosition().distance(location.getPosition()) > entity.getLocation().getPosition().distance(location.getPosition())){
                        origin = entity;
                    }
                }
            }

            //get nearby entities
            if(origin == null){
                return;
            } else {
                entities.addAll(origin.getNearbyEntities(radius));
            }

            for (Entity entity: entities) {
                if(!(entity instanceof Player)){
                    continue;
                }

                UserPlayer player = (UserPlayer) Avatar.INSTANCE.getUserManager().find(entity.getUniqueId()).get();

                Optional<Player> p = player.getPlayer();
                if(!p.isPresent()){
                    continue;
                }

                ParticleEffect.Builder builder = ParticleEffect.builder();

                builder.quantity((int) (amount * player.getParticleModifier().getFactor())).type(particleType);

                if (randomizeOffset) {
                    builder.offset(new Vector3d(location.getX() + xOffset * LocationUtils.getRandomNegOrPos(),
                            location.getY() + yOffset * LocationUtils.getRandomNegOrPos(),
                            location.getZ() + zOffset * LocationUtils.getRandomNegOrPos()));
                } else {
                    builder.offset(new Vector3d(location.getX() + xOffset,
                            location.getY() + yOffset,
                            location.getZ() + zOffset));
                }

                if (particleOptions != null) {
                    for (ParticleOption option : particleOptions) {
                        builder.option(option, option.getValueType());
                    }
                }

                if (velocity != null) {
                    builder.velocity(velocity);
                }

                p.get().spawnParticles(builder.build(), location.getPosition());
            }
        }
    }

    /**
     * An option every player can set to increase or reduce the particle amounts they see.
     */
    public enum ParticleModifier{
        LOW(0.25),
        MEDIUM(0.5),
        NORMAL(1.0),
        HIGH(1.25),
        EXTREME(1.5);

        private double factor;

        ParticleModifier(double factor){this.factor = factor;}

        public double getFactor() {
            return factor;
        }
    }

}
