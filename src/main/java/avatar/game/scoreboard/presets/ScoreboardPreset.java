package avatar.game.scoreboard.presets;

import avatar.game.user.UserPlayer;
import org.spongepowered.api.text.Text;

import java.util.ArrayList;
import java.util.List;

public class ScoreboardPreset {

    protected static Text BLANK_LINE = Text.of(" ");

    private List<Text> scores = new ArrayList<>();
    private UserPlayer owner;
    private List<Text> oldSnapshot = new ArrayList<>();
    /*
     * First string should be the title
     * Second string begins the information to display
     * Max of 15 entries, starting at the 2nd string
     */

    public ScoreboardPreset(UserPlayer owner){
        this.owner = owner;
    }

    public void updateScores(){} //if scores need to be updated on a timer, event, etc

    public List<Text> getScores(){
        return scores;
    }

    public Text getScore(int i){
        return scores.get(i);
    }

    public void setScores(List<Text> strings){ //should only use when instantiating or needing to manually manipulate scores
        scores = strings;
    }

    public UserPlayer getOwner() {
        return owner;
    }

    public void takeSnapshot() {
        oldSnapshot = new ArrayList<>(scores);
    }

    public List<Text> getOldSnapshot() {
        return oldSnapshot;
    }
}
