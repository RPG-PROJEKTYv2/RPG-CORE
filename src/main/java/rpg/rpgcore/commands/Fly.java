package rpg.rpgcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.utils.Utils;

public class Fly implements CommandExecutor {


    @Override
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.NIEGRACZ);
            return false;
        }

        final Player player = (Player) sender;

        if (args.length >= 2) {
            player.sendMessage(Utils.poprawneUzycie("fly [gracz]"));
            return false;
        }

        if (args.length == 0) {
            fly(player);
            return false;
        }

        if (Bukkit.getPlayer(args[0]) == null) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie znaleziono podanego gracza"));
            return false;
        }
        final Player target = Bukkit.getPlayer(args[0]);

        fly(target);
        return false;

    }


    private void fly(Player p) {
        if (!(p.isFlying())) {
            p.setAllowFlight(true);
            p.setFlying(true);
            p.sendMessage(Utils.format(Utils.SERVERNAME + "&aWlaczano &7fly"));
        } else {
            p.setAllowFlight(false);
            p.setFlying(false);
            p.sendMessage(Utils.format(Utils.SERVERNAME + "&cWylaczono &7fly"));
        }
    }
}
