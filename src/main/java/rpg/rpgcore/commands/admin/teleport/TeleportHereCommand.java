package rpg.rpgcore.commands.admin.teleport;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.commands.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.Utils;

import java.util.Arrays;

public class TeleportHereCommand extends CommandAPI {

    private final RPGCORE rpgcore;

    public TeleportHereCommand(RPGCORE rpgcore) {
        super("teleporthere");
        this.setRankLevel(RankType.ADMIN);
        this.setAliases(Arrays.asList("tphere", "tph", "s"));
        this.setRestrictedForPlayer(true);
        this.rpgcore = rpgcore;
    }


    @Override
    public void executeCommand(CommandSender sender, String[] args) {
        final Player player = (Player) sender;
        if (args.length < 1) {
            player.sendMessage(Utils.poprawneUzycie("tphere <gracz>"));
            return;
        }

        final Player target = Bukkit.getPlayerExact(args[0]);
        if (target == null) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Podany gracz nie jest online!"));
            return;
        }

        if (target == player) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Nie mozesz sie teleportowac do siebie!"));
            return;
        }
        this.rpgcore.getTeleportManager().setBeforeTeleportLocation(target.getUniqueId(), target.getLocation());
        target.teleport(player);
        player.sendMessage(Utils.format(Utils.SERVERNAME + "&aTeleportowano gracza &6" + target.getName() + " &ado siebie!"));
        target.sendMessage(Utils.format(Utils.SERVERNAME + "&aZostales teleportowany do gracza &6" + player.getName() + "&a!"));
    }
}
