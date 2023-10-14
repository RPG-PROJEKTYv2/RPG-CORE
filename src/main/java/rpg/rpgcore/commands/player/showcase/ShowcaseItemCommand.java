package rpg.rpgcore.commands.player.showcase;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.commands.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;

public class ShowcaseItemCommand extends CommandAPI {
    public ShowcaseItemCommand() {
        super("showcaseitem");
        this.setRankLevel(RankType.GRACZ);
        this.setRestrictedForPlayer(true);
    }

    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;
        if (args.length < 2) {
            return;
        }

        this.openShowcaseGUI(player, args[0], args[1]);
    }

    private void openShowcaseGUI(final Player player, final String ownerName, final String itemName) {
        final Inventory gui = Bukkit.createInventory(null, 45, Utils.format("&5&lPokazywanie Przedmiotu"));
        if (!RPGCORE.getInstance().getShowcaseItemManager().showcaseItemCache.asMap().containsKey(ownerName)) {
            player.sendMessage(Utils.format("&cNiestety nie znaleziono podanego przedmiotu."));
            return;
        }

        if (!RPGCORE.getInstance().getShowcaseItemManager().showcaseItemCache.asMap().get(ownerName).containsKey(itemName)) {
            player.sendMessage(Utils.format("&cNiestety nie znaleziono podanego przedmiotu."));
            return;
        }

        final ItemStack is = RPGCORE.getInstance().getShowcaseItemManager().showcaseItemCache.asMap().get(ownerName).get(itemName);

        if (is == null || is.getType() == Material.AIR) {
            player.sendMessage(Utils.format("&cCos poszlo nie tak i nie udalo sie zaladowac przedmiotu!"));
            return;
        }

        for (int i = 0; i < 45; ++i) {
            if (i % 2 == 0) {
                gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 2).setName(" ").addGlowing().toItemStack());
            } else {
                gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 6).setName(" ").addGlowing().toItemStack());
            }
        }

        gui.setItem(22, is.clone());

        player.openInventory(gui);
    }
}
