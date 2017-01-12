package avatar.managers;

import avatar.user.Account;
import avatar.user.UserPlayer;

import java.util.Optional;

public class EconomyManager extends Manager<Account>{

    public Optional<Account> findAccount(UserPlayer userPlayer){
        for(Account account: objects){
            if(account.getOwner() == userPlayer){
                return Optional.of(account);
            }
        }
        return Optional.empty();
    }

}
