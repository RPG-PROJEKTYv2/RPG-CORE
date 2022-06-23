package rpg.rpgcore.commands.kosz;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.utils.Utils;

public class Kosz implements CommandExecutor {


    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.NIEGRACZ);
            return false;
        }

        final Player player = (Player) sender;

        if (!player.hasPermission("rpg.kosz")) {
            player.sendMessage(Utils.permisje("rpg.kosz"));
            return false;
        }

        if (args.length != 0) {
            player.sendMessage(Utils.poprawneUzycie("kosz"));
            return false;
        }

        this.openKosz(player);

        return false;
    }



    private void openKosz(final Player player) {
        final Inventory kosz = Bukkit.createInventory(null, 54, Utils.format("&4&lKosz"));
        player.openInventory(kosz);
    }
}
