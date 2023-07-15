package rpg.rpgcore.commands.player;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.Arrays;

public class KontaktCommand extends CommandAPI {
    public KontaktCommand() {
        super("kontakt");
        this.setAliases(Arrays.asList("socjale", "www", "strona", "discord", "dc", "facebook", "fb"));
        this.setRankLevel(RankType.GRACZ);
        this.setRestrictedForPlayer(true);
    }
    @Override
    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;
        if (args.length > 0) {
            player.sendMessage(Utils.poprawneUzycie("kontakt"));
            return;
        }
        player.sendMessage(" ");
        player.sendMessage(Utils.format("&7Kliknij:"));
        player.sendMessage(Utils.format("&a&lDiscord: &6&ldc.hellrpg.pl"));
        player.sendMessage(Utils.format("&a&lFacebook: &6&lfb.hellrpg.pl"));
        player.sendMessage(Utils.format("&a&lStrona &6&lwww.hellrpg.pl"));
        player.sendMessage(Utils.format("&a&lYouTube &6&lyoutube.com/@hellrpg-minecraft"));
        player.sendMessage(" ");
    }
}
