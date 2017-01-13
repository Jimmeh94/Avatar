package avatar.game.scoreboard;

import avatar.game.scoreboard.presets.DefaultPreset;
import avatar.game.scoreboard.presets.ScoreboardPreset;
import avatar.user.UserPlayer;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scoreboard.critieria.Criteria;
import org.spongepowered.api.scoreboard.displayslot.DisplaySlots;
import org.spongepowered.api.scoreboard.objective.Objective;
import org.spongepowered.api.text.Text;

import java.util.Arrays;

public class Scoreboard {

    private UserPlayer owner;
    private ScoreboardPreset preset;

    public Scoreboard(UserPlayer player){
        owner = player;

        org.spongepowered.api.scoreboard.Scoreboard scoreboard = org.spongepowered.api.scoreboard.Scoreboard.builder()
                .objectives(Arrays.asList(Objective.builder().name("Side").criterion(Criteria.DUMMY).displayName(Text.EMPTY).build())).build();
        scoreboard.updateDisplaySlot(scoreboard.getObjective("Side").get(), DisplaySlots.SIDEBAR);

        //scoreboard.registerNewObjective("prefix", "dummy").setDisplaySlots(DisplaySlots.PLAYER_LIST);
        //May have to loop through every online player and add this team to their scoreboard

        //scoreboard.registerNewTeam("Red").setDisplayName(ChatColor.RED + "Red");
        //scoreboard.registerNewTeam("Blue").setDisplayName(ChatColor.DARK_AQUA + "Blue");
        owner.getPlayer().get().setScoreboard(scoreboard);
        //setPrefix();

        preset = new DefaultPreset(owner);
        //updateScoreboard();
    }

   /* public void setPrefix(){
        updateTeams();
    }*/

    private void updatePreset(){ //use this before updating scoreboard
        preset.takeSnapshot();
        preset.updateScores();
    }

    public void setPreset(ScoreboardPreset preset){
        preset.takeSnapshot();
        this.preset = preset;
        updateScoreboard();
    }

    public void unregisterScoreboard(){
        Player owner = this.owner.getPlayer().get();
        owner.setScoreboard(null);
    }

    public void updateScoreboard(){//sidebar scoreboard
        updatePreset();
        Objective objective = owner.getPlayer().get().getScoreboard().getObjective(DisplaySlots.SIDEBAR).get();
        objective.setDisplayName(preset.getScore(0));

        for(int i = 0; i < preset.getOldSnapshot().size(); i++){
            objective.removeScore(preset.getOldSnapshot().get(i));
        }
        for(int i = 1; i < preset.getScores().size(); i++){
            objective.getOrCreateScore(preset.getScore(i)).setScore(16 - i);
        }
    }

    /*private void updateTeams() {
        GameArena arena = Core.getInstance().getGameArenaManager().getArena(owner.getPlayer().getUniqueId());
        for(PlayerInfo playerInfo: arena.getMembers()){
            org.bukkit.scoreboard.Scoreboard scoreboard = playerInfo.getPlayer().getScoreboard();
            for(AdvancedEntity playerInfo1: arena.getRedTeam().getMembers()){
                if(playerInfo1 instanceof PlayerInfo)
                    scoreboard.getTeam("Red").addEntry(playerInfo1.getPlayer().getName());
            }
            for(AdvancedEntity playerInfo1: arena.getBlueTeam().getMembers()){
                if(playerInfo1 instanceof PlayerInfo)
                    scoreboard.getTeam("Blue").addEntry(playerInfo1.getPlayer().getName());
            }
            playerInfo.getPlayer().setScoreboard(scoreboard);
        }
    }*/

    public ScoreboardPreset getPreset() {
        return preset;
    }
}