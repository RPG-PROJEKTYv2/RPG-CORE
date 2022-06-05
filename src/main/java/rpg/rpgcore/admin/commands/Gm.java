package rpg.rpgcore.admin.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.utils.Utils;

public class Gm implements CommandExecutor {

    public static void mode(final CommandSender sender, final Player target, final GameMode tryb) {
        if (target != sender) {
            sender.sendMessage(Utils.format("&7>> &7Zmieniono tryb gracza: &e" + target.getName() + " &7na: &e" + tryb.toString().toLowerCase()));
        }
        target.setGameMode(tryb);
        target.sendMessage(Utils.format("&7>> &7Zmieniono twoj tryb gry na: &e" + tryb.toString().toLowerCase()));
    }


    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {

        if (!(sender.hasPermission("rpg.gm"))) {
            sender.sendMessage(Utils.permisje("rpg.gm"));
            return false;
        }

        if (args.length == 2) {

            final Player target = Bukkit.getPlayer(args[1]);

            if (target == null) {
                sender.sendMessage(Utils.offline(args[1]));
                return false;
            }

            if (args[0].equalsIgnoreCase("0") || args[0].equalsIgnoreCase("survival")) {
                mode(sender, target, GameMode.SURVIVAL);
                return false;
            } else if (args[0].equalsIgnoreCase("1") || args[0].equalsIgnoreCase("creative")) {
                mode(sender, target, GameMode.CREATIVE);
                return false;
            } else if (args[0].equalsIgnoreCase("2") || args[0].equalsIgnoreCase("adventure")) {
                mode(sender, target, GameMode.ADVENTURE);
                return false;
            } else if (args[0].equalsIgnoreCase("3") || args[0].equalsIgnoreCase("spectator")) {
                mode(sender, target, GameMode.SPECTATOR);
                return false;
            }
            sender.sendMessage(Utils.format("&7>> &7Poprawne użycie: &e/gm [0,1,2,3] [gracz]"));
        } else if (args.length == 1) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(Utils.format("&cTylko dla gracza!"));
                return false;
            }

            final Player p = (Player) sender;

            if (args[0].equalsIgnoreCase("0") || args[0].equalsIgnoreCase("survival")) {
                mode(p, p, GameMode.SURVIVAL);
                return false;
            } else if (args[0].equalsIgnoreCase("1") || args[0].equalsIgnoreCase("creative")) {
                mode(p, p, GameMode.CREATIVE);
                return false;
            } else if (args[0].equalsIgnoreCase("2") || args[0].equalsIgnoreCase("adventure")) {
                mode(p, p, GameMode.ADVENTURE);
                return false;
            } else if (args[0].equalsIgnoreCase("3") || args[0].equalsIgnoreCase("spectator")) {
                mode(p, p, GameMode.SPECTATOR);
                return false;
            }
        }
        sender.sendMessage(Utils.poprawneUzycie("gm <0-3 / survival / creative / adventure / spectator> [gracz]"));
        return false;
    }
}
