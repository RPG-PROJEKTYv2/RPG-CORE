package rpg.rpgcore.guilds;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.Locale;

public class Guild implements CommandExecutor {

    private final RPGCORE rpgcore;

    public Guild(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.NIEGRACZ);
            return false;
        }

        final Player player = (Player) sender;

        if (!player.hasPermission("rpg.guilds")) {
            player.sendMessage(Utils.permisje("rpg.guilds"));
            return false;
        }

        if (args.length == 0) {
            rpgcore.getGuildManager().listAllCommands(player);
            return false;
        }

        if (args.length == 1){
            //TODO statystyki i sortowanie od najwyzszej
        }

        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("info")) {
                final String tag = args[1].toUpperCase(Locale.ROOT);
                rpgcore.getGuildManager().showInfo(tag, player);
                return false;
            }
        }

        if (args.length > 2) {
            if (args[0].equalsIgnoreCase("zaloz")) {
                final StringBuilder sb = new StringBuilder();
                final String tag = args[1].toUpperCase(Locale.ROOT);
                args[0] = "";
                args[1] = "";
                args[2] = args[2].replaceFirst(" ", "");
                for (String s : args) {
                    sb.append(s).append(" ");
                }
                final String description = sb.toString().trim();
                rpgcore.getGuildManager().createGuild(tag, description, player.getUniqueId());
                rpgcore.getServer().broadcastMessage(Utils.format(Utils.GUILDSPREFIX + "Klan &6" + tag + " - " + description + " &7zostal zalozony przez &6" + player.getName() + " &6&lGratulacje!"));
                return false;
            }
        }


        rpgcore.getGuildManager().listAllCommands(player);
        return false;
    }

}
