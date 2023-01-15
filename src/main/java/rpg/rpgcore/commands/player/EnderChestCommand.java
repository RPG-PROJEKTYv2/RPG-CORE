package rpg.rpgcore.commands.player;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankTypePlayer;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.Arrays;

public class EnderChestCommand extends CommandAPI {
    public EnderChestCommand() {
        super("enderchest");
        this.setAliases(Arrays.asList("ec"));
        this.setRestrictedForPlayer(true);
    }

    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;
        final User user = RPGCORE.getInstance().getUserManager().find(player.getUniqueId());
        if (user.isAdminCodeLogin() && user.getRankUser().isStaff()) {
            if (args.length != 0) {
                player.sendMessage(Utils.poprawneUzycie("enderchest"));
                return;
            }
            player.openInventory(player.getEnderChest());
            return;
        }
        if (user.getRankPlayerUser().getRankType().getPriority() < RankTypePlayer.VIP.getPriority()) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Ta komenda jest przeznaczona dla graczy z ranga &6&lS&e&lvip&7 lub wyzsza!"));
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&5Enderchesty &7mozesz znalezc na spawnie."));
            return;
        }
        if (args.length != 0) {
            player.sendMessage(Utils.poprawneUzycie("enderchest"));
            return;
        }
        player.openInventory(player.getEnderChest());
    }
}
