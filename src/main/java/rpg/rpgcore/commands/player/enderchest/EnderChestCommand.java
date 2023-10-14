package rpg.rpgcore.commands.player.enderchest;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.commands.api.CommandAPI;
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
            if (args.length == 1 && user.getRankUser().isHighStaff()) {
                final Player target = Bukkit.getPlayer(args[0]);

                if (target == null) {
                    final User targetUser = RPGCORE.getInstance().getUserManager().find(args[0]);
                    if (targetUser == null) {
                        player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie znaleziono gracza o nicku &6" + args[0] + "&c!"));
                        return;
                    }
                    final Inventory ec = Bukkit.createInventory(null, InventoryType.ENDER_CHEST, Utils.format("&5&lEnderChest &7&lgracza &5&l" + targetUser.getName()));
                    ec.setContents(Utils.itemStackArrayFromBase64(targetUser.getInventoriesUser().getEnderchest()));
                    player.openInventory(ec);
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&aOtworzono enderchest gracza &6" + targetUser.getName() + "&a!"));
                    return;
                }

                player.openInventory(target.getEnderChest());
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&aOtworzono enderchest gracza &6" + target.getName() + "&a!"));
                return;
            }

            if (args.length != 0) {
                player.sendMessage(Utils.poprawneUzycie("enderchest"));
                return;
            }

            player.openInventory(player.getEnderChest());
            return;
        }
        if (user.getRankPlayerUser().getRankType().getPriority() < RankTypePlayer.VIP.getPriority()) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Ta komenda jest przeznaczona dla graczy z ranga &e&lVip&7 lub wyzsza!"));
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
