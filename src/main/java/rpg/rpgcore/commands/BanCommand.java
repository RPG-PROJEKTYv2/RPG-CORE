package rpg.rpgcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.utils.Utils;

public class BanCommand implements CommandExecutor {

    private void help(final Player p) {
        p.sendMessage(Utils.format("&8-_-_-_-_-_-_-_-_-_-_-{&4&lBAN&8}-_-_-_-_-_-_-_-_-_-_-"));
        p.sendMessage(Utils.format("&c/ban <gracz> [-s] [powod] &7- blokuje gracza na zawsze za podany powod, [-s] jesli ma sie nie pokazywac na chacie"));
        p.sendMessage(Utils.format("&8-_-_-_-_-_-_-_-_-_-_-{&4&lBAN&8}-_-_-_-_-_-_-_-_-_-_-"));
    }

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.NIEGRACZ);
            return false;
        }
        final Player p = (Player) sender;

        if (!(p.hasPermission("rpg.ban"))) {
            Utils.permisje("rpg.ban");
            return false;
        }

        if (args.length == 0) {
            p.sendMessage(Utils.poprawneUzycie("ban"));
            return false;
        }

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("help") ||
                    args[0].equalsIgnoreCase("pomoc")) {
                help(p);
            }
        }

        return false;
    }
}
