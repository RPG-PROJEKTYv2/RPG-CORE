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
        gui.setItem(0, new ItemBuilder(Material.PAPER).setName("&c&lDowodca Rozbojnikow").setLore(Arrays.asList(
                " ",
                "&eLokalizacja obok gracza &8&l(&4&lPRZYWOLANIE&8&l)"
        )).addGlowing().toItemStack().clone());
        gui.setItem(1, new ItemBuilder(Material.PAPER).setName("&a&lWodz Goblinow").setLore(Arrays.asList(
                " ",
                "&eLokalizacja obok gracza &8&l(&4&lPRZYWOLANIE&8&l)"
        )).addGlowing().toItemStack().clone());
        gui.setItem(2, new ItemBuilder(Material.PAPER).setName("&f&lKrol Goryli").setLore(Arrays.asList(
                " ",
                "&eLokalizacja obok gracza &8&l(&4&lPRZYWOLANIE&8&l)"
        )).addGlowing().toItemStack().clone());
        gui.setItem(3, new ItemBuilder(Material.PAPER).setName("&7&lPrzekleta Dusza").setLore(Arrays.asList(
                " ",
                "&eLokalizacja obok gracza &8&l(&4&lPRZYWOLANIE&8&l)"
        )).addGlowing().toItemStack().clone());
        gui.setItem(4, new ItemBuilder(Material.PAPER).setName("&e&lTryton").setLore(Arrays.asList(
                " ",
                "&eLokalizacja obok gracza &8&l(&4&lPRZYWOLANIE&8&l)"
        )).addGlowing().toItemStack().clone());
        gui.setItem(5, new ItemBuilder(Material.PAPER, 1).setName("&b&lKrol Lodu").setLore(Arrays.asList(" ", "&eLokalizacja XYZ: &6&l-4 | 66 | 122 &8&l(&f&lICETOWER&8&l)")).addGlowing().toItemStack().clone());
        gui.setItem(6, new ItemBuilder(Material.PAPER,1).setName("&c&lPiekielny Rycerz").setLore(Arrays.asList(
                " ",
                "&eLokalizacja obok gracza &8&l(&4&lPRZYWOLANIE&8&l)"
        )).addGlowing().toItemStack().clone());
        gui.setItem(7, this.makeWBOSSY("&5&lPrzeklety  Czarnoksieznik", -0,83,248));
        gui.setItem(8, this.makeWBOSSY("&e&lMityczny Pajak", -40,67,146));
        gui.setItem(9, this.makeWBOSSY("&5&lPodziemny Rozpruwacz", 407,101,69));
        gui.setItem(10, this.makeWBOSSY("&b&lMityczny Kraken", 77,77,221));
        gui.setItem(11, this.makeWBOSSY("&1&lKrysztalowy Wladca", -0,76, -155));
        gui.setItem(12, this.makeWBOSSY("&5&lStarozytny Smoczy Cesarz", 000,000,000));

        player.openInventory(gui);
    }
    
    private ItemStack makeWBOSSY(final String nazwabossa, int x, int y, int z) {
        return new ItemBuilder(Material.PAPER, 1).setName(Utils.format(nazwabossa)).setLore(Arrays.asList(
                " ",
                "&eLokalizacja XYZ: &6&l" + x + " | " + y + " | " + z
        )).addGlowing().toItemStack().clone();
    }
}
