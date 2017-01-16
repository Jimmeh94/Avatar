package avatar.util.text;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.chat.ChatTypes;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;
import org.spongepowered.api.text.title.Title;

import java.util.Optional;

public class Messager {

    public static void sendMessage(Player player, Text text, Optional<Prefix> prefix){
        if(prefix.isPresent()){
            Text use = Text.builder().append(prefix.get().getText()).append(text).build();
            player.sendMessage(use);
        } else {
            player.sendMessage(text);
        }
        player.sendMessage(Text.of(" "));
    }

    public static void sendActionBarMessage(Player player, Text text){
        player.sendMessage(ChatTypes.ACTION_BAR, text);
    }

    public static void sendTitleMessage(Player player, Text text){player.sendTitle(Title.of(text));}

    public static void sendTitleAndSubTitle(Player player, Text title, Text subtitle){player.sendTitle(Title.of(title, subtitle));}

    public static void broadcastMessage(Text text, Optional<Prefix> prefix){
        if(prefix.isPresent()){
            text = Text.builder().append(prefix.get().getText()).append(text).build();
        }
        for(Player p: Sponge.getServer().getOnlinePlayers()){
            p.sendMessage(text);
            p.sendMessage(Text.of(" "));
        }

    }

    public enum Prefix{
        ERROR(Text.of(TextColors.RED, TextStyles.BOLD, "[" + AltCodes.THICK_X.getSign() + "] ")),
        INFO(Text.of(TextColors.GOLD, TextStyles.BOLD, "[!] ")),
        SUCCESS(Text.of(TextColors.GREEN, TextStyles.BOLD, "[" + AltCodes.CHECKMARK.getSign() + "] "));

        private Text text;

        Prefix(Text text){this.text = text;}

        public Text getText() {
            return text;
        }
    }

}
