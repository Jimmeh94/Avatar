package avatar.game.ability.abilities.fire;

import avatar.game.ability.AbilityRunnable;
import avatar.game.user.User;

public class Fireball extends AbilityRunnable {

    public Fireball(User owner, long period, long delay, double x, double y, double z) {
        super(owner, period, delay, x, y, z);
    }

    public Fireball(User owner, long period, long delay, int cycleLifetime, double x, double y, double z) {
        super(owner, period, delay, cycleLifetime, x, y, z);
    }

    @Override
    protected void initiateAbility() {

    }

    @Override
    protected void adjustCenter() {

    }

    @Override
    public void displayEndingEffect() {

    }
}
