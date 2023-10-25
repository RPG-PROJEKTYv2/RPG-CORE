package rpg.rpgcore.commands.player.mobshp;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.commands.api.CommandAPI;
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
                "&6Rozbojnik: &c35",
                "&2Goblin: &c100",
                "&7Goryl: &c225",
                "&8Zjawa: &c400",
                "&3Straznik Swiatyni: &c740",
                "&bMrozny Wilk: &c1 400",
                "&6Zywiolak Ognia: &c6 000",
                "&fMroczna Dusza: &c45 000",
                "&6Pustynny Ptasznik: &c80 000",
                "&5Podziemna Lowczyni: &c250 000",
                "&bPodwodny Straznik: &c750 000",
                "&fMrozny Stroz: &c2 000 000 &7/ &c2 250 000 &7/ &c2 500 000",
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
