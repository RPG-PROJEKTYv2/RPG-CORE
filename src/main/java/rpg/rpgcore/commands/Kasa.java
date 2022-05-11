package rpg.rpgcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

public class Kasa implements CommandExecutor {

    private final RPGCORE rpgcore;

    public Kasa(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.NIEGRACZ);
            return false;
        }

        final Player player = (Player) sender;

        if (!(player.hasPermission("rpg.kasa"))) {
            player.sendMessage(Utils.permisje("rpg.kasa"));
            return false;
        }

        if (args.length == 0) {
            player.sendMessage(Utils.format("&2Twoj aktualny stan konta wynosi: &6&o" + Utils.spaceNumber(String.valueOf(Utils.kasaFormat.format(rpgcore.getPlayerManager().getPlayerKasa(player.getUniqueId())))) + "&2$"));
            return false;
        }

        if (args.length == 1) {
            if (args[0].equals("daj")) {
                if (!player.hasPermission("admin.rpg.dajkase")) {
                    player.sendMessage(Utils.permisje("admin.rpg.dajkase"));
                    return false;
                }
                rpgcore.getPlayerManager().updatePlayerKasa(player.getUniqueId(), 100000000000.0);
                return false;
            }
        }

        player.sendMessage(Utils.poprawneUzycie("kasa"));
    return false;
    }
}
