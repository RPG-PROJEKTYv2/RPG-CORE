package rpg.rpgcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

public class UnBan implements CommandExecutor {

    private final RPGCORE rpgcore;

    public UnBan(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    private static void poprawnyUnBan(Player player) {
        player.sendMessage(Utils.format(Utils.UNBANPREFIX + "&7Poprawne uzycie to &c/unban <gracz>"));
    }

    @Deprecated
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.NIEGRACZ);
            return false;
        }

        final Player p = (Player) sender;

        if (args.length == 0) {
            poprawnyUnBan(p);
            return false;
        }

        if (args.length == 1){

            if (Bukkit.getOfflinePlayer(args[0]) == null){
                p.sendMessage(Utils.format(Utils.UNBANPREFIX + "&cNie znaleziono podanego gracza"));
                return false;
            }

            final OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
            Bukkit.getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getSQLManager().unBanPlayer(p, target));
            return false;
        }

        poprawnyUnBan(p);
        return false;
    }
}
