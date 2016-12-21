package avatar.game.combatlog.entries;

import org.spongepowered.api.text.Text;

import java.util.UUID;

public class CombatEntry {

    //Entries are created when something is attacked,
    //These are for non-player entities

    private double damage;
    private Text attacker, attack;
    private UUID owner;

    public CombatEntry(double damage, Text attack, Text attacker, UUID owner){
        this.damage = damage;
        this.attack = attack;
        this.attacker = attacker;
        this.owner = owner;
    }

    public double getDamage() {
        return damage;
    }

    public Text getAttack() {
        return attack;
    }

    public Text getAttacker() {
        return attacker;
    }

    public UUID getOwner() {
        return owner;
    }
}
