package rpg.rpgcore.commands.player;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.commands.api.CommandAPI;
import rpg.rpgcore.utils.Utils;

import java.util.Arrays;

public class TowerCommand extends CommandAPI {
    public TowerCommand() {
        super("tower");
        setRestrictedForPlayer(true);
        setAliases(Arrays.asList("dt", "demontower", "icetower"));
    }

    public void executeCommand(final CommandSender sender, final String[] args) {
        final Player player = (Player) sender;

        if (args.length != 0) {
            player.sendMessage(Utils.poprawneUzycie("tower"));
            return;
        }

        player.sendMessage(Utils.format("&b&oAktualnie Lodowa Wieze przechodza: "));
        for (Player p : Bukkit.getWorld("DemonTower").getPlayers()) {
            player.sendMessage(Utils.format("&8- &b&o" + p.getName()));
        }
    }
}
