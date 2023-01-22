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

    @Override
    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;
        if (args.length == 0) {
            if (player.getInventory().getContents().length == 0) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie masz nic w ekwipunku!"));
                return;
            }
            player.getInventory().clear();
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&cWyczysciles swoj ekwipunek!"));
            return;
        }
        if (args.length == 1) {
            final Player target = Bukkit.getPlayer(args[0]);

            if (target == null) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie znaleziono gracza o nicku &6" + args[0] + "&c!"));
                return;
            }
            if (target.getInventory().getContents().length == 0) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&cGracz &6" + target.getName() + " &cnie ma nic w ekwipunku!"));
                return;
            }
            target.getInventory().clear();
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&cWyczyszczono ekwipunek gracza &6" + target.getName() + "&c!"));
        }
    }
}