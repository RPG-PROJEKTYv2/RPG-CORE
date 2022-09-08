package rpg.rpgcore.akcesoria;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.ItemHelper;
import rpg.rpgcore.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Akcesoria implements CommandExecutor {

    private final RPGCORE rpgcore;

    public Akcesoria(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }


    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.NIEGRACZ);
            return false;
        }

        final Player player = (Player) sender;

        if (args.length == 0) {
            rpgcore.getAkcesoriaManager().openAkcesoriaGUI(player);
            //player.getInventory().addItem(ItemHelper.createArmor("&8Testowa Zbroja", Material.DIAMOND_CHESTPLATE, 100, 20, false, true), ItemHelper.createSword("&7&lTestowy Miecz", Material.STONE_SWORD, 250, 100, false, true));
            return false;
        }


        return false;
    }
}
