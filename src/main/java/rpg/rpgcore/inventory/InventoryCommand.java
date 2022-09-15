package rpg.rpgcore.inventory;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.Arrays;

public class InventoryCommand extends CommandAPI {

    public InventoryCommand() {
        super("inventory");
        this.setAliases(Arrays.asList("inv", "invsee"));
        this.setRankLevel(RankType.ADMIN);
        this.setRestrictedForPlayer(true);
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;
        if (args.length < 1) {
            player.sendMessage(Utils.poprawneUzycie("inventory <gracz>"));
            return;
        }
        if (!RPGCORE.getInstance().getUserManager().isUserName(args[0])) {
            player.sendMessage(Utils.NIEMATAKIEGOGRACZA);
            return;
        }
        this.openPlayerGUI(player, args[0]);
        player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie otwarto ekwipunek gracza &6" + args[0]));
    }

    private void openPlayerGUI(final Player player, final String target) {
        final Inventory gui = Bukkit.createInventory(null, 45, Utils.format("&cEkwipunek gracza &6" + target));
        if (Bukkit.getPlayer(target) != null && Bukkit.getPlayer(target).isOnline()) {
            final Player p = Bukkit.getPlayer(target);
            player.openInventory(p.getInventory());
            player.sendMessage(p.getInventory().getName());
            player.sendMessage(p.getInventory().getHolder().getName());
            return;
            /*gui.setContents(p.getInventory().getContents());
            gui.setItem(36, p.getInventory().getHelmet());
            gui.setItem(37, p.getInventory().getChestplate());
            gui.setItem(38, p.getInventory().getLeggings());
            gui.setItem(39, p.getInventory().getBoots());
            player.openInventory(gui);
            return;*/
        }
        final User user = RPGCORE.getInstance().getUserManager().find(target);
        try {
            gui.setContents(Utils.itemStackArrayFromBase64(user.getInventoriesUser().getInventory()));
            final ItemStack[] armor = Utils.itemStackArrayFromBase64(user.getInventoriesUser().getArmor());
            gui.setItem(36, armor[0]);
            gui.setItem(37, armor[1]);
            gui.setItem(38, armor[2]);
            gui.setItem(39, armor[3]);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        player.openInventory(gui);
    }
}
