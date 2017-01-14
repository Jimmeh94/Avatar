package avatar.game.scoreboard.presets;

import avatar.game.user.UserPlayer;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import java.util.ArrayList;
import java.util.List;

public class DefaultPreset extends ScoreboardPreset {

    /*
     * Server name
     * ============
     * Area the player is in
     *
     * Bounty
     *
     * Gold
     *
     * Element Equipped
     */

    public DefaultPreset(UserPlayer statistics){
        super(statistics);
        //updateScores();
    }

    @Override
    public void updateScores() {
        UserPlayer owner = getOwner();
        List<Text> strings = new ArrayList<>();

        strings.add(Text.of(TextStyles.BOLD, "Server Name"));
        strings.add(Text.of("=============="));
        strings.add(owner.getPresentArea().getDisplayName());
        strings.add(Text.of(TextColors.RED));
        strings.add(Text.of("Bounty: 0"));
        strings.add(Text.of(TextColors.RED, TextColors.AQUA));
        strings.add(Text.of("Gold: " + owner.getAccount().getBalance()));
        strings.add(Text.of(TextColors.RED, TextColors.BLACK));
        strings.add(Text.of("Element: Fire"));

        setScores(strings);
    }
}
