package rpg.rpgcore.inventory;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.ItemBuilder;
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
            final ItemStack[] contents = p.getInventory().getContents();
            final ItemStack[] armor = p.getInventory().getArmorContents();
            gui.setContents(contents);
            gui.setItem(36, armor[0]);
            gui.setItem(37, armor[1]);
            gui.setItem(38, armor[2]);
            gui.setItem(39, armor[3]);
            for (int i = 40 ; i < 45 ; i++) {
                gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setName(" ").toItemStack());
            }
            RPGCORE.getInstance().getInvseeManager().addInventory(p.getUniqueId(), contents);
            RPGCORE.getInstance().getInvseeManager().addArmor(p.getUniqueId(), armor);
            player.openInventory(gui);
            return;
        }
        final User user = RPGCORE.getInstance().getUserManager().find(target);
        ItemStack[] contents;
        try {
            contents = Utils.itemStackArrayFromBase64(user.getInventoriesUser().getInventory());
            gui.setContents(contents);
            final ItemStack[] armor = Utils.itemStackArrayFromBase64(user.getInventoriesUser().getArmor());
            gui.setItem(36, armor[0]);
            gui.setItem(37, armor[1]);
            gui.setItem(38, armor[2]);
            gui.setItem(39, armor[3]);
            for (int i = 40 ; i < 45 ; i++) {
                gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setName(" ").toItemStack());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        RPGCORE.getInstance().getInvseeManager().addInventory(user.getId(), contents);
        RPGCORE.getInstance().getInvseeManager().addArmor(user.getId(), new ItemStack[] {
                gui.getItem(36),
                gui.getItem(37),
                gui.getItem(38),
                gui.getItem(39)
        });
        player.openInventory(gui);
    }
}
