package rpg.rpgcore.commands.player.mobshp;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.Arrays;

public class MobsHpCommand extends CommandAPI {
    public MobsHpCommand() {
        super("hp");
        this.setAliases(Arrays.asList("mobhp", "hpmob"));
        this.setRankLevel(RankType.GRACZ);
        this.setRestrictedForPlayer(true);
    }
    @Override
    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;
        if (args.length > 0) {
            player.sendMessage(Utils.poprawneUzycie("hp"));
            return;
        }
        this.openHP(player);
    }

    private void openHP(Player player) {
        final Inventory gui = Bukkit.createInventory(null, InventoryType.HOPPER, Utils.format("&c&lHP &7potworow"));
        gui.setItem(0, new ItemBuilder(Material.STAINED_GLASS_PANE,1, (short)15).toItemStack().clone());
        gui.setItem(1, new ItemBuilder(Material.INK_SACK, 1,(short)1).setName("&cExpowisko").setLore(Arrays.asList(
                "",
                "&6Rozbojnik: &c24 &7/ &c30 &7/ &c34",
                "&2Goblin: &c125 &7/ &c160 &7/ &c200",
                "&7Goryl: &c280 &7/ &c320 &7/ &c380",
                "&8Zjawa: &c580 &7/ &c640 &7/ &c740",
                "&3Straznik Swiatyni: &c820 &7/ &c1 000 &7/ &c1 400",
                "&bMrozny Wilk: &c2 000 &7/ &c2 250 &7/ &c2 500",
                "&6Zywiolak Ognia: &c6 000 &7/ &c7 500 &7/ &c9 000",
                "&fMroczna Dusza: &c37 500 &7/ &c45 000 &7/ &c50 000",
                "&6Pustynny Ptasznik: &c65 000 &7/ &c80 000 &7/ &c125 000",
                "&5Podziemna Lowczyni: &c310 000 &7/ &c380 000 &7/ &c435 000",
                "&bPodwodny Straznik: &c750 000 &7/ &c825 000 &7/ &c925 000",
                ""
                )).toItemStack().clone());
        gui.setItem(2, new ItemBuilder(Material.STAINED_GLASS_PANE,1, (short)15).toItemStack().clone());
        gui.setItem(3, new ItemBuilder(Material.INK_SACK, 1,(short)6).setName("&cMapy Specjalne").setLore(Arrays.asList(
                "",
                "&bLodowy Sluga: &c2 250 &7/ &c2 450 &7/ 2 650",
                "&cOgnisty Duch: &c4 250",
                "&eZapomniany Wojownik: &c27 000 &7/ &c28 000",
                "&7Truposz: &c75 000 &7/ &c85 000",
                "&cDemoniczy Lowca: &c415 000",
                ""
        )).toItemStack().clone());
        gui.setItem(4, new ItemBuilder(Material.STAINED_GLASS_PANE,1, (short)15).toItemStack().clone());
        player.openInventory(gui);
    }
}
