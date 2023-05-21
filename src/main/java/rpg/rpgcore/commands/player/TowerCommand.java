package rpg.rpgcore.commands.player;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.dungeons.maps.icetower.IceTowerManager;
import rpg.rpgcore.dungeons.maps.icetower.ResetType;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.Utils;

import java.util.Arrays;

public class TowerCommand extends CommandAPI {
    public TowerCommand() {
        super("tower");
        setRestrictedForPlayer(true);
        setAliases(Arrays.asList("dt", "demontower"));
    }

    public void executeCommand(final CommandSender sender, final String[] args) {
        final Player player = (Player) sender;

        if (RPGCORE.getInstance().getUserManager().find(player.getUniqueId()).getRankUser().getRankType().getPriority() >= RankType.ADMIN.getPriority()) {
            if (args.length == 1) {
                if (args[0].equals("reset")) {
                    IceTowerManager.resetIceTower(ResetType.BYPASS);
                    return;
                }
            }
        }

        if (args.length != 0) {
            player.sendMessage(Utils.poprawneUzycie("tower"));
            return;
        }

        player.sendMessage(Utils.format("&b&oAktualnie Lodowa Wieze przechodza: "));
        for (Player p : Bukkit.getWorld("demontower").getPlayers()) {
            player.sendMessage(Utils.format("&8- &b&o" + p.getName()));
        }
    }
}
