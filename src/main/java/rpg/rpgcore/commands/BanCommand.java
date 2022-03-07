package rpg.rpgcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;


public class BanCommand implements CommandExecutor {

    private final RPGCORE rpgcore;

    public BanCommand(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

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

        final StringBuilder powod = new StringBuilder();
        powod.setLength(0);

        if (args.length == 1) {

            Player target = Bukkit.getPlayer(args[0]);

            if (args[0].equalsIgnoreCase("help") ||
                    args[0].equalsIgnoreCase("pomoc")) {
                help(p);
                return false;
            }

            powod.append("Brak Powodu");

            if (target == null) {
                final UUID uuidTarget = rpgcore.getPlayerManager().getPlayerUUID(args[0]);
                if (uuidTarget != null) {
                    target = (Player) Bukkit.getOfflinePlayer(uuidTarget);
                    if (target == null) {
                        p.sendMessage(Utils.format(Utils.BANPREFIX + "&cNie znaleziono podanego gracz"));
                        return true;
                    }
                    return false;
                }
                p.sendMessage(Utils.format(Utils.BANPREFIX + "&cNie znaleziono podanego gracz"));
                return false;
            }
            final Player finalTarget = target;
            Bukkit.getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getSQLManager().banujGracza(p, finalTarget, false, powod.toString()));
            return false;
        }

        if (args.length >= 2) {

            for (int i = 1; i < args.length; i++) {
                if (!(args[1].equalsIgnoreCase("[-s]"))) {
                    powod.append(args[i]);
                }
                if (!(i > args.length + 1)) {
                    powod.append(" ");
                }
            }
            if (powod.length() == 0) {
                powod.append("Brak Powodu");
            }

            Player target = Bukkit.getPlayer(args[0]);

            if (target == null) {
                final UUID uuidTarget = rpgcore.getPlayerManager().getPlayerUUID(args[0]);
                if (uuidTarget != null) {
                    target = (Player) Bukkit.getOfflinePlayer(uuidTarget);
                    if (target == null) {
                        p.sendMessage(Utils.format(Utils.BANPREFIX + "&cNie znaleziono podanego gracz"));
                        return true;
                    }
                    return false;
                }
                p.sendMessage(Utils.format(Utils.BANPREFIX + "&cNie znaleziono podanego gracz"));
                return false;
            }

            final Player finalTarget = target;

            Bukkit.getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getSQLManager().banujGracza(p, finalTarget, true, powod.toString()));

            if (!(args[1].equalsIgnoreCase("[-s]"))) {
                Bukkit.broadcastMessage("Zbanowano gracza");
                return true;
            }

            return true;
        }
        help(p);
        return false;
    }
}
