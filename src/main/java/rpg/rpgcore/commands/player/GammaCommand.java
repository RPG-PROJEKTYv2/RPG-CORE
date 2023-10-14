package rpg.rpgcore.commands.player;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.commands.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.Arrays;

public class GammaCommand extends CommandAPI {
    public GammaCommand() {
        super("gamma");
        this.setRestrictedForPlayer(true);
        this.setRankLevel(RankType.GRACZ);
        this.setAliases(Arrays.asList("nv", "nightvision"));
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;

        if (args.length != 0) {
            player.sendMessage(Utils.poprawneUzycie("gamma"));
            return;
        }

        if (player.hasPotionEffect(org.bukkit.potion.PotionEffectType.NIGHT_VISION)) {
            player.removePotionEffect(org.bukkit.potion.PotionEffectType.NIGHT_VISION);
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&cWylaczono &7tryb gamma!"));
        } else {
            player.addPotionEffect(new org.bukkit.potion.PotionEffect(org.bukkit.potion.PotionEffectType.NIGHT_VISION, 999999, 1));
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&aWlaczono &7tryb gamma!"));
        }
    }
}
