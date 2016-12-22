package avatar.game.abilities;

import avatar.game.abilities.properties.*;

public enum AbilityPropertyReference {

    COLLISION_LOGIC(AbilityPropertyCollisionLogic.class),
    COST(AbilityPropertyCost.class),
    DAMAGE(AbilityPropertyDamage.class),
    DURATION(AbilityPropertyDuration.class),
    HEAL(AbilityPropertyHeal.class),
    RANGE(AbilityPropertyRange.class),
    TARGETING(AbilityPropertyTargeting.class);

    private Class<? extends AbilityProperty> classType;

    AbilityPropertyReference(Class<? extends AbilityProperty> c){this.classType = c;}

    public Class<? extends AbilityProperty> getClassType() {
        return classType;
    }
}
