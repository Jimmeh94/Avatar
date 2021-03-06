package avatar.util.particles.effects;

import avatar.game.area.Area;
import avatar.game.user.User;
import avatar.game.user.UserPlayer;
import com.flowpowered.math.vector.Vector3d;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.effect.particle.ParticleOption;
import org.spongepowered.api.effect.particle.ParticleType;
import org.spongepowered.api.effect.particle.ParticleTypes;
import org.spongepowered.api.scheduler.Scheduler;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.world.Location;

import java.util.List;

public class EffectData {

    public static EffectData.EffectDataBuilder builder() {
        return new EffectDataBuilder();
    }

    private Location center, displayAt;
    private UserPlayer owner;
    private int amount = 10;
    protected ParticleType particle = ParticleTypes.FLAME;
    private double xOffset = 0, yOffset = 0, zOffset = 0;
    private Scheduler scheduler = Sponge.getScheduler();
    private Task.Builder taskBuilder = scheduler.createTaskBuilder();
    private Task task;
    private long delay = 0, interval = 5, cancel = 1; //cancel is currently how many ticks, not how many intervals have passed
    private IPlayParticles playParticles;
    private boolean randomizeOffsets;
    private List<ParticleOption> particleOptions;
    private Vector3d velocity;
    private double displayRadius;
    private Area displayArea;

    private EffectData(EffectDataBuilder builder){
        this.center = builder.center;
        this.displayAt = builder.center.copy();
        this.owner = builder.owner;
        this.amount = builder.amount;
        this.particle = builder.particle;
        this.xOffset = builder.xOffset;
        this.yOffset = builder.yOffset;
        this.zOffset = builder.zOffset;
        this.delay = builder.delay;
        this.interval = builder.interval;
        this.cancel = builder.cancel;
        this.playParticles = builder.playParticles;
        this.randomizeOffsets = builder.randomizeOffsets;
        this.particleOptions = builder.particleOptions;
        this.velocity = builder.velocity;
        this.displayRadius = builder.displayRadius;
        this.displayArea = builder.displayArea;
    }

    public EffectData setDisplayAt(Location displayAt){
        this.displayAt = displayAt;
        return this;
    }

    public EffectData setTask(Task task) {
        this.task = task;
        return this;
    }

    public EffectData setCenter(Location center) {
        this.center = center;
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

    public void setRandomizeOffsets(boolean randomizeOffsets) {
        this.randomizeOffsets = randomizeOffsets;
    }

    public EffectData setLocation(Location location) {
        this.center = location;
        return this;
    }

    public EffectData setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public boolean isRandomizeOffsets() {
        return randomizeOffsets;
    }

    public EffectData setPlayParticles(IPlayParticles playParticles) {
        this.playParticles = playParticles;
        return this;
    }

    public Location getCenter() {
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

    public List<ParticleOption> getParticleOptions() {
        return particleOptions;
    }

    public Vector3d getVelocity() {
        return velocity;
    }

    public Location getDisplayAt() {
        if(displayAt == null)
            displayAt = center.copy();
        return displayAt;
    }

    public Area getDisplayArea() {
        return displayArea;
    }

    public double getDisplayRadius() {
        return displayRadius;
    }

    public static class EffectDataBuilder{
        private Location center;
        private UserPlayer owner;
        private long delay, interval, cancel; //cancel is currently how many ticks, not how many intervals have passed
        private int amount = 10;
        protected ParticleType particle = ParticleTypes.FLAME;
        private double xOffset = 0, yOffset = 0, zOffset = 0;
        private Scheduler scheduler = Sponge.getScheduler();
        private Task.Builder taskBuilder = scheduler.createTaskBuilder();
        private Task task;
        private IPlayParticles playParticles;
        private boolean randomizeOffsets = false;
        private List<ParticleOption> particleOptions;
        private Vector3d velocity;
        private double displayRadius;
        private Area displayArea;

        public EffectDataBuilder displayRadius(double displayRadius){
            this.displayRadius = displayRadius;
            return this;
        }

        public EffectDataBuilder displayArea(Area displayArea){
            this.displayArea = displayArea;
            return this;
        }

        public EffectDataBuilder particleOptions(List<ParticleOption> particleOptions){
            this.particleOptions = particleOptions;
            return this;
        }

        public EffectDataBuilder velocity(Vector3d vector3d){
            this.velocity = vector3d;
            return this;
        }

        public EffectDataBuilder taskInfo(long delay, long interval, long cancel){
            this.delay = delay;
            this.interval = interval;
            this.cancel = cancel;
            return this;
        }

        public EffectDataBuilder randomizeOffsets(boolean a){
            randomizeOffsets = a;
            return this;
        }

        public EffectDataBuilder center(Location center){
            this.center = center;
            return this;
        }

        public EffectDataBuilder user(UserPlayer owner){
            this.owner = owner;
            return this;
        }

        public EffectData build(){
            return new EffectData(this);
        }

        public EffectDataBuilder amount(int amount){
            this.amount = amount;
            return this;
        }

        public EffectDataBuilder particle(ParticleType type){
            this.particle = type;
            return this;
        }

        public EffectDataBuilder offsets(double x, double y, double z){
            this.xOffset = x;
            this.yOffset = y;
            this.zOffset = z;
            return this;
        }

        public EffectDataBuilder playParticles(IPlayParticles iPlayParticles){
            this.playParticles = iPlayParticles;
            return this;
        }
    }
}
