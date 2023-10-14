package rpg.rpgcore.commands.admin;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.commands.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.Utils;

import java.util.Arrays;

public class EnchantCommand extends CommandAPI {
    public EnchantCommand() {
        super("enchant");
        this.setRankLevel(RankType.ADMIN);
        this.setAliases(Arrays.asList("ench"));
        this.setRestrictedForPlayer(true);
    }

    public void executeCommand(final CommandSender sender, final String[] args) {
        final Player player = (Player) sender;
        if (args.length < 2) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&cPoprawne uzycie: /enchant <zaklecie> <poziom>"));
            return;
        }
        final Enchantment enchantment = Enchantment.getByName(args[0].toUpperCase());
        if (enchantment == null) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&cPodane zaklecie nie istnieje."));
            return;
        }
        final ItemStack item = player.getItemInHand();
        if (item == null || item.getType() == Material.AIR) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie posiadasz zadnego przedmiotu w rece."));
            return;
        }
        final int level = Integer.parseInt(args[1]);
        item.addUnsafeEnchantment(enchantment, level);
        player.sendMessage(Utils.format(Utils.SERVERNAME + "&aZaklecie zostało &fpomyslnie nadane &ana przedmiot w twojej rece."));
    }
}
