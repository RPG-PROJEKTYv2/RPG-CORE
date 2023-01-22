package rpg.rpgcore.commands.player.bossy;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


public class BossyCommand extends CommandAPI {
    public BossyCommand() {
        super("bossy");
        this.setRankLevel(RankType.GRACZ);
        this.setRestrictedForPlayer(true);
    }


    
    @Override
    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;
        if (args.length > 0) {
            player.sendMessage(Utils.poprawneUzycie("bossy"));
            return;
        }
        this.openBossyInventory(player);
    }

    private void openBossyInventory(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 18, Utils.format("&3&lLista bossow"));

        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (byte) 15).setName(" ").toItemStack());
        }
        // czasowe
        gui.setItem(0, new ItemBuilder(Material.PAPER).setName("&9&lKrol Wygnancow").setLore(Arrays.asList(
                " ",
                "&7Lokalizacja XYZ: &6&l158 | 73 | 243",
                "&7Lokalizacja XYZ: &6&l59 | 73 | 59",
                "&7Czas odrodzenia: &6&l1 minuta"
        )).addGlowing().toItemStack().clone());
        gui.setItem(1, this.makeBOSSY("&a&lWodz Goblinow", 39, 75, 88, 5));
        gui.setItem(2, this.makeBOSSY("&f&lKrol Goryli", 146, 75, 126, 8));
        gui.setItem(3, this.makeBOSSY("&7&lPrzekleta Dusza", 211, 78, -14, 15));
        gui.setItem(4, this.makeBOSSY("&e&lTryton", -89, 68, -248, 30));
        // przepustki
        gui.setItem(5, new ItemBuilder(Material.PAPER, 1).setName("&b&lMrozny Wladca").setLore(Arrays.asList(" ", "&7Lokalizacja XYZ: &6&l56 | 34 | 76 &8&l(&f&lICETOWER&8&l)")).addGlowing().toItemStack().clone());
        gui.setItem(6, this.makeWBOSSY("&1&lPiekielny Rycerz", 43,79,106));
        gui.setItem(7, this.makeWBOSSY("&5&lPrzeklety  Czarnoksieznik", 2,32,15));
        gui.setItem(8, this.makeWBOSSY("&e&lMityczny Pajak", -40,67,146));
        gui.setItem(9, this.makeWBOSSY("&2&lCesarz Mroku", 366,79,235));

        player.openInventory(gui);
    }


    private ItemStack makeBOSSY(final String nazwabossa, int x, int y, int z, int czas) {
        return new ItemBuilder(Material.PAPER, 1).setName(Utils.format(nazwabossa)).setLore(Arrays.asList(
        " ",
        "&7Lokalizacja XYZ: &6&l" + x + " | " + y + " | " + z,
        "&7Czas odrodzenia: &6&l" + (czas > 5 ? czas + " minut" : czas + " minuty")
        )).addGlowing().toItemStack().clone();
    }
    private ItemStack makeWBOSSY(final String nazwabossa, int x, int y, int z) {
        return new ItemBuilder(Material.PAPER, 1).setName(Utils.format(nazwabossa)).setLore(Arrays.asList(
                " ",
                "&7Lokalizacja XYZ: &6&l" + x + " | " + y + " | " + z
        )).addGlowing().toItemStack().clone();
    }
}
