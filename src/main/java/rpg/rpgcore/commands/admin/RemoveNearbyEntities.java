package rpg.rpgcore.commands.admin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import rpg.rpgcore.utils.Utils;

public class RemoveNearbyEntities implements CommandExecutor {

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.NIEGRACZ);
            return false;
        }

        final Player player = (Player) sender;

        if (!(player.hasPermission("admin.rpg.removenearbyentities"))) {
            player.sendMessage(Utils.permisje("admin.rpg.removenearbyentities"));
            return false;
        }

        if (args.length == 0) {
            player.sendMessage(Utils.poprawneUzycie("removenearbyentities"));
            return false;
        }

        if (args.length == 1) {
            try {
                final double radius = Double.parseDouble(args[0]);

                for (Entity entity : player.getWorld().getNearbyEntities(player.getLocation(), radius, radius, radius)) {
                    if (entity instanceof ArmorStand) {
                        entity.remove();
                    }
                }

                player.sendMessage(Utils.format(Utils.SERVERNAME + "&aUsunieto wszystkie armorstandy w poblizu &6" + radius + " &ablokow"));
            } catch (final NumberFormatException e) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&cPodaj odleglosc"));
                return false;
            }
        }
        player.sendMessage(Utils.poprawneUzycie("removenearbyentities [radius]"));
        return false;
    }
}
