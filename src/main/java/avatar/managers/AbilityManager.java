package avatar.managers;

import avatar.game.abilities.Ability;

import java.util.ArrayList;
import java.util.List;

public class AbilityManager extends Manager<Ability> {

    public List<Ability> getNearbyAbilitiesInChunk(Ability ability){
        List<Ability> give = new ArrayList<>();

        for(Ability a: objects){
            if(a == ability)
                continue;
            else if(a.getLocationChunk() == ability.getLocationChunk()){
                give.add(a);
            }
        }

        return give;
    }

}
