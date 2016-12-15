package avatar.utilities.particles.effects;

import avatar.user.User;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.effect.particle.ParticleType;
import org.spongepowered.api.effect.particle.ParticleTypes;
import org.spongepowered.api.scheduler.Scheduler;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.world.Location;

public class EffectData {

    private Location center;
    private User owner;
    private int amount = 10;
    protected ParticleType particle = ParticleTypes.FLAME;
    private double xOffset = 0, yOffset = 0, zOffset = 0;
    private Scheduler scheduler = Sponge.getScheduler();
    private Task.Builder taskBuilder = scheduler.createTaskBuilder();
    private Task task;
    private long delay = 0, interval = 5, cancel = 1; //cancel is currently how many ticks, not how many intervals have passed
    private IPlayParticles playParticles;

    public EffectData(User owner){this.owner = owner;}

    public EffectData setTask(Task task) {
        this.task = task;
        return this;
    }

    public EffectData setCenter(Location center) {
        this.center = center;
        return this;
    }

    public EffectData setDelay(long delay) {
        this.delay = delay;
        return this;
    }

    public EffectData setInterval(long interval) {
        this.interval = interval;
        return this;
    }

    public EffectData setCancel(long cancel) {
        this.cancel = cancel;
        return this;
    }

    public EffectData setzOffset(double zOffset) {
        this.zOffset = zOffset;
        return this;
    }

    public EffectData setyOffset(double yOffset) {
        this.yOffset = yOffset;
        return this;
    }

    public EffectData setxOffset(double xOffset) {
        this.xOffset = xOffset;
        return this;
    }

    public EffectData setParticle(ParticleType particle) {
        this.particle = particle;
        return this;
    }

    public EffectData setLocation(Location location) {
        this.center = location;
        return this;
    }

    public EffectData setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public EffectData setPlayParticles(IPlayParticles playParticles) {
        this.playParticles = playParticles;
        return this;
    }

    public Location getLocation() {
        return center;
    }

    public User getOwner() {
        return owner;
    }

    public int getAmount() {
        return amount;
    }

    public ParticleType getParticle() {
        return particle;
    }

    public double getxOffset() {
        return xOffset;
    }

    public double getyOffset() {
        return yOffset;
    }

    public double getzOffset() {
        return zOffset;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public Task.Builder getTaskBuilder() {
        return taskBuilder;
    }

    public Task getTask() {
        return task;
    }

    public long getDelay() {
        return delay;
    }

    public long getInterval() {
        return interval;
    }

    public long getCancel() {
        return cancel;
    }

    public IPlayParticles getPlayParticles() {
        return playParticles;
    }
}
