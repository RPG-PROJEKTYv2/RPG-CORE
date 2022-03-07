package rpg.rpgcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;


public class BanCommand implements CommandExecutor {

    private final RPGCORE rpgcore;

    public BanCommand (RPGCORE rpgcore){
        this.rpgcore = rpgcore;
    }

    private void help(final Player p) {
        p.sendMessage(Utils.format("&8-_-_-_-_-_-_-_-_-_-_-{&4&lBAN&8}-_-_-_-_-_-_-_-_-_-_-"));
        p.sendMessage(Utils.format("&c/ban <gracz> [-s] [powod] &7- blokuje gracza na zawsze za podany powod, [-s] jesli ma sie nie pokazywac na chacie"));
        p.sendMessage(Utils.format("&8-_-_-_-_-_-_-_-_-_-_-{&4&lBAN&8}-_-_-_-_-_-_-_-_-_-_-"));
    }

    @Deprecated
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
        StringBuilder powod = new StringBuilder();
        powod.setLength(0);
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("help") ||
                    args[0].equalsIgnoreCase("pomoc")) {
                help(p);
                return false;
            }
            powod.append("Brak Powodu");
            if (Bukkit.getPlayer(args[0]) == null){
                p.sendMessage(Utils.format(Utils.BANPREFIX + "&cNie znaleziono podanego gracz"));
                return false;
            }
            if (Bukkit.getPlayer(args[0]) != null && !(Bukkit.getPlayer(args[0]).isOnline())){
                OfflinePlayer targetOffline = Bukkit.getOfflinePlayer(args[0]);
                Bukkit.getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getSQLManager().banujGracza(p, targetOffline, false, powod.toString()));
                return false;
            }
            Player target = Bukkit.getPlayer(args[0]);
            Bukkit.getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getSQLManager().banujGracza(p, target, false, powod.toString()));
            return false;

        }
        if (args.length >= 2){

            for (int i = 1; i < args.length; i++){
                if (!(args[1].equalsIgnoreCase("[-s]"))){
                    powod.append(args[i]);
                }
                if (!(i > args.length +1)){
                    powod.append(" ");
                }
            }
            if (powod.length() == 0){
                powod.append("Brak Powodu");
            }
            if (args[1].equalsIgnoreCase("[-s]")){
                if (Bukkit.getPlayer(args[0]) == null){
                    p.sendMessage(Utils.format(Utils.BANPREFIX + "&cNie znaleziono podanego gracz"));
                    return false;
                }

                if (Bukkit.getPlayer(args[0]) != null && !(Bukkit.getPlayer(args[0]).isOnline())){
                    OfflinePlayer targetOffline = Bukkit.getOfflinePlayer(args[0]);
                    Bukkit.getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getSQLManager().banujGracza(p, targetOffline, true, powod.toString()));
                    return false;
                }

                Player target = Bukkit.getPlayer(args[0]);
                Bukkit.getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getSQLManager().banujGracza(p, target, true, powod.toString()));
                return false;
            }

            if (Bukkit.getPlayer(args[0]) == null){
                p.sendMessage(Utils.format(Utils.BANPREFIX + "&cNie znaleziono podanego gracz"));
                return false;
            }

            if (Bukkit.getPlayer(args[0]) != null && !(Bukkit.getPlayer(args[0]).isOnline())){
                OfflinePlayer targetOffline = Bukkit.getOfflinePlayer(args[0]);
                Bukkit.getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getSQLManager().banujGracza(p, targetOffline, false, powod.toString()));

                return false;
            }

            Player target = Bukkit.getPlayer(args[0]);
            Bukkit.getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getSQLManager().banujGracza(p, target, false, powod.toString()));
            return false;


        }
        help(p);
        return false;
    }
}
