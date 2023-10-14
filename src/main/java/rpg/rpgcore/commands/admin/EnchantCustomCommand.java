package rpg.rpgcore.commands.admin;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.commands.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.ItemHelper;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.Arrays;

public class EnchantCustomCommand extends CommandAPI {
    public EnchantCustomCommand() {
        super("enchantcustom");
        this.setRankLevel(RankType.ADMIN);
        this.setRestrictedForPlayer(true);
        this.setAliases(Arrays.asList("cenchant", "enchc"));
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;
        if (args.length < 3) {
            player.sendMessage(Utils.poprawneUzycie("enchantcustom <zbroja/miecz> <prot/dmg> <ciernie/moby>"));
            return;
        }
        if (player.getItemInHand() == null && player.getItemInHand().getType() == Material.AIR) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&cMusisz trzymac przedmiot w rece!"));
            return;
        }
        player.setItemInHand(ItemHelper.setEnchants(player.getItemInHand(), args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2])));
        player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie dodano enchanty!"));
    }
}
