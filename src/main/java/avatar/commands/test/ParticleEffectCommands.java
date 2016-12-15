package avatar.commands.test;

import avatar.Avatar;
import avatar.utilities.particles.ParticleUtils;
import avatar.utilities.particles.effects.AbstractEffect;
import avatar.utilities.particles.effects.AtomEffect;
import avatar.utilities.particles.effects.EffectData;
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
                .user(Avatar.INSTANCE.getUserManager().find(player.getUniqueId()).get())
                .center(player.getLocation().copy().add(0, 1, 0))
                .taskInfo(0L, 5L, 1200L)
                .amount(50)
                .offsets(1.0, 1.0, 1.0)
                .particle(ParticleTypes.REDSTONE_DUST)
                .playParticles((data, target) -> ParticleUtils.PlayerBased.displayParticles(data.setDisplayAt(target)))
                .randomizeOffsets(true)
                .build();

        switch (effect){
            case "atom": abstractEffect = new AtomEffect(effectData, 3.5, 1.5, 0.0);
        }

        abstractEffect.start();

        return CommandResult.success();
    }
}
