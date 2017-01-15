package avatar.game.areas;

import avatar.game.user.User;

import java.util.ArrayList;
import java.util.List;

public class Instance {

    private List<User> members;

    public Instance(){
        members = new ArrayList<>();
    }

    public void add(User user){
        members.add(user);
    }

    public void remove(User user){
        members.remove(user);
    }

    public boolean has(User user){
        return members.contains(user);
    }

}
