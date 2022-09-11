package rpg.rpgcore.commands.admin.teleport;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.Utils;

import java.util.Arrays;

public class TeleportCommand extends CommandAPI {

    private final RPGCORE rpgcore;

    public TeleportCommand(RPGCORE rpgcore) {
        super("teleport");
        this.setAliases(Arrays.asList("tp"));
        this.setRankLevel(RankType.HELPER);
        this.setRestrictedForPlayer(true);
        this.rpgcore = rpgcore;
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) {
        final Player player = (Player) sender;
        if (args.length < 1) {
            player.sendMessage(Utils.poprawneUzycie("teleport <gracz/x> [y] [z]"));
            return;
        }
        if (args.length == 1) {
            final Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Podany gracz nie jest online!"));
                return;
            }

            if (target == player) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Nie mozesz sie teleportowac do siebie!"));
                return;
            }

            this.rpgcore.getTeleportManager().setBeforeTeleportLocation(player.getUniqueId(), player.getLocation());
            player.teleport(target);
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&aTeleportowano do gracza &6" + target.getName() + "&a!"));
        }
        if (args.length == 3) {
            try {
                player.teleport(new Location(player.getWorld(), Double.parseDouble(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2])));
            } catch (final NumberFormatException e) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Podane wartosci musza byc liczbami"));
                return;
            }
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&aTeleportowano na x: &6" + args[0] + " &ay:&6 " + args[1] + " &az:&6 " + args[2] + " &aw &6" + player.getWorld().getName() +"&a!"));
        }
    }
}
