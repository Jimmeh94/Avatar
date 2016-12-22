package avatar.utilities.text;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.chat.ChatTypes;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.text.title.Title;

import java.util.Optional;

public class Messager {

    private static Optional<Text> prefix = Optional.empty();

    /*
     * Sets the prefix to append every message to
     */
    public static void setPrefix(String string, TextColor colors){
        prefix = Optional.of(Text.of(colors, string));
    }

    /*
     * Use if wanting multiple styles or colors
     */
    public static void setPrefix(Text text){prefix = Optional.of(text);}

    /*
     * Resets the prefix. Doing this will make sure no prefix is present in sent messages
     */
    public static void resetPrefix(){prefix = Optional.empty();}

    /*
     * Sends a basic message. Applies prefix if present
     */
    public static void sendMessage(Player player, String string, TextColor colors){
        sendMessage(player, Text.of(colors, string));
    }

    /*
     * Use if wanting multiple styles or colors. Applies prefix if present
     */
    public static void sendMessage(Player player, Text text){
        if(prefix.isPresent()){
            Text use = Text.builder().append(prefix.get()).append(text).build();
            player.sendMessage(use);
        } else {
            player.sendMessage(text);
        }
        player.sendMessage(Text.of(" "));
    }

    public static void sendActionBarMessage(Player player, String string, TextColor colors){
        Text text;
        if(prefix.isPresent()){
            text = Text.builder().append(prefix.get()).append(Text.of(colors, string)).build();
        } else {
            text = Text.of(colors, string);
        }
        sendActionBarMessage(player, text);
    }

    public static void sendActionBarMessage(Player player, Text text){
        player.sendMessage(ChatTypes.ACTION_BAR, text);
    }

    public static void sendTitleMessage(Player player, Text text){player.sendTitle(Title.of(text));}

    public static void sendTitleAndSubTitle(Player player, Text title, Text subtitle){player.sendTitle(Title.of(title, subtitle));}

    /*
     * Sends a message to all online players. Applies prefix if present
     */
    public static void broadcastMessage(String string, TextColor colors){
        Text text;
        if(prefix.isPresent()){
            text = Text.builder().append(prefix.get()).append(Text.of(colors, string)).build();
        } else {
            text = Text.of(colors, string);
        }
        broadcastMessage(text);
    }

    /*
     * Use this if you want a multi-colored/styled message. Applies prefix if present
     */
    public static void broadcastMessage(Text text){
        if(prefix.isPresent()){
            text = Text.builder().append(prefix.get()).append(text).build();
        }
        for(Player p: Sponge.getServer().getOnlinePlayers()){
            p.sendMessage(text);
            p.sendMessage(Text.of(" "));
        }

    }

}