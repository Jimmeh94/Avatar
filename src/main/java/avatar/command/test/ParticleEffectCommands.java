package avatar.command.test;

import avatar.Avatar;
import avatar.util.particles.ParticleUtils;
import avatar.util.particles.effects.*;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.effect.particle.ParticleTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

public class ParticleEffectCommands implements CommandExecutor {

    public ParticleEffectCommands(){
        //particle effectName
        CommandSpec commandSpec = CommandSpec.builder()
                .description(Text.of("Play a particle effect"))
                .executor(this)
                .arguments(GenericArguments.string(Text.of("effect")))
                .build();
        Sponge.getCommandManager().register(Avatar.INSTANCE, commandSpec, "particle");
    }

    @Override
    public CommandResult execute(CommandSource commandSource, CommandContext args) throws CommandException {
        Player player = (Player) commandSource;
        String effect = args.<String>getOne("effect").get().toLowerCase();
        AbstractEffect abstractEffect = null;
        EffectData effectData = EffectData.builder()
                .user(Avatar.INSTANCE.getUserManager().findUserPlayer(player).get())
                .center(player.getLocation().copy().add(0, 1, 0))
                .taskInfo(0L, 8L, 200L)
                .amount(5)
                .offsets(0.25, 0.25, 0.25)
                .particle(ParticleTypes.CLOUD)
                .playParticles((data, target) -> ParticleUtils.PlayerBased.displayParticles(data))
                .randomizeOffsets(false)
                .build();

        switch (effect){
            case "atom": abstractEffect = new AtomEffect(effectData, 1, 0.5, 0.0);
                break;
            case "helix": abstractEffect = new HelixEffect(effectData, 7.5, .25, 0.75, 15);
                break;
            case "line": abstractEffect = new LineEffect(effectData, player.getLocation().copy().add(5, 1, 5));
                break;
            case "sphere": abstractEffect = new SphereEffect(effectData, 5.0);
                break;
            case "tornado": abstractEffect = new TornadoEffect(effectData, 10, 0.5, 6.5, 50);
                break;
            case "test": abstractEffect = new TestEffect(effectData);
        }

        abstractEffect.start();

        return CommandResult.success();
    }
}
