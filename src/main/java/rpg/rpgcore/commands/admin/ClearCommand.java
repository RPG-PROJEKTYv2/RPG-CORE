package rpg.rpgcore.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;

public class ClearCommand extends CommandAPI {

    public ClearCommand() {
        super("clear");
        this.setRankLevel(RankType.ADMIN);
        this.setRestrictedForPlayer(true);
    }
    // METODY EQ
    // if (player.getInventory().firstEmpty() == -1) { ===== masz full eq
    @Override
    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;

        if (args.length > 0) {
            player.sendMessage(Utils.poprawneUzycie("clear"));
            return;
        }
        player.sendMessage(Utils.format(Utils.SERVERNAME + "&cWyczysciles swoj ekwipunek!"));
        player.getInventory().clear();
    }
}