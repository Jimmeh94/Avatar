package avatar.managers;

import avatar.user.User;

import java.util.Optional;
import java.util.UUID;

public class UserManager extends Manager<User>{

    public Optional<User> find(UUID uuid){
        Optional<User> give = Optional.empty();

        for(User user: this.objects){
            if(user.getUUID().equals(uuid)){
                give = Optional.of(user);
            }
        }

        return give;
    }

}
