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
        gui.setItem(1, new ItemBuilder(Material.INK_SACK, 1,(short)1).setName("&cExpowisko").setLore(Arrays.asList(
                "",
                "&6Rozbojnik: &c15 &7/ &c18 &7/ &c24",
                "&2Goblin: &c40 &7/ &c55 &7/ &c70 ",
                "&7Goryl: &c75 &7/ &c85 &7/ &c100",
                "&8Zjawa: &c100 &7/ &c150 &7/ &c210",
                "&3Straznik Swiatyni: &c300 &7/ &c360 &7/ &c500",
                "&bMrozny Wilk: &c720 &7/ &c780 &7/ &c860",
                "&6Zywiolak Ognia: &c4000 &7/ &c5000 &7/ &c6500",
                "&fMroczna Dusza: &c11500 &7/ &c14500 &7/ &c19000",
                "&6Pustynny Ptasznik: &c31000 &7/ &c38000 &7/ &c50000",
                "&5Podziemna Lowczyni: &c85000 &7/ &c110000 &7/ &c145000",
                ""
                )).toItemStack().clone());

        gui.setItem(3, new ItemBuilder(Material.INK_SACK, 1,(short)6).setName("&cMapy Specjalne").setLore(Arrays.asList(
                "",
                "&bLodowy Sluga: &c1400",
                "&cOgnisty Duch: &c8000",
                "&eZapomniany Wojownik: &c16000 &7/ &c20000",
                "&7Truposz: &c40000 &7/ &c50000",
                "&cDemoniczy Lowca: &c245000",
                ""
        )).toItemStack().clone());
        player.openInventory(gui);
    }
}
