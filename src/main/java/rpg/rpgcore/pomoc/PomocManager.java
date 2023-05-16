package rpg.rpgcore.pomoc;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.Arrays;

public class PomocManager {
    public void openPomocInventory(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, InventoryType.HOPPER, Utils.format("&e&lPomoc"));
        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (byte) 15).setName(" ").toItemStack());
        }

        gui.setItem(0, new ItemBuilder(Material.EXP_BOTTLE, 1).setName("&e&lPoradnik").setLore(Arrays.asList(" ", "&7Kliknij aby przeczytac.", " ")).addGlowing().toItemStack().clone());
        gui.setItem(2, new ItemBuilder(Material.PAPER, 1).setName("&f&lSociale").setLore(Arrays.asList(" ", "&7Kliknij aby dostac link.", " ")).addGlowing().toItemStack().clone());
        gui.setItem(4, new ItemBuilder(Material.ENDER_PEARL, 1).setName("&b&lSpis komend").setLore(Arrays.asList(" ", "&7Kliknij aby zobaczyc liste.", " ")).addGlowing().toItemStack().clone());

        player.openInventory(gui);
    }
    public void openAllKomendyInventory(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 36, Utils.format("&b&lSpis komend"));
        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (byte) 15).setName(" ").toItemStack());
        }

        gui.setItem(0, new ItemBuilder(Material.DEAD_BUSH, 1).setName("&e/listanpc").addGlowing().toItemStack().clone());
        gui.setItem(1, new ItemBuilder(Material.DEAD_BUSH, 1).setName("&e/bossy").addGlowing().toItemStack().clone());
        gui.setItem(2, new ItemBuilder(Material.DEAD_BUSH, 1).setName("&e/money, /wyplac").addGlowing().toItemStack().clone());
        gui.setItem(3, new ItemBuilder(Material.DEAD_BUSH, 1).setName("&e/targ, /wystaw").addGlowing().toItemStack().clone());
        gui.setItem(4, new ItemBuilder(Material.DEAD_BUSH, 1).setName("&e/klan").addGlowing().toItemStack().clone());
        gui.setItem(5, new ItemBuilder(Material.DEAD_BUSH, 1).setName("&e/profil").addGlowing().toItemStack().clone());
        gui.setItem(6, new ItemBuilder(Material.DEAD_BUSH, 1).setName("&e/chatpanel").addGlowing().toItemStack().clone());
        gui.setItem(7, new ItemBuilder(Material.DEAD_BUSH, 1).setName("&e/msg <gracz> <wiadomosc>, /r <wiadomosc>").addGlowing().toItemStack().clone());
        gui.setItem(8, new ItemBuilder(Material.DEAD_BUSH, 1).setName("&e/itemshop").addGlowing().toItemStack().clone());
        gui.setItem(9, new ItemBuilder(Material.DEAD_BUSH, 1).setName("&e/tower").addGlowing().toItemStack().clone());
        gui.setItem(10, new ItemBuilder(Material.DEAD_BUSH, 1).setName("&e/enderchest").addGlowing().toItemStack().clone());
        gui.setItem(11, new ItemBuilder(Material.DEAD_BUSH, 1).setName("&e/hellcode").addGlowing().toItemStack().clone());
        gui.setItem(12, new ItemBuilder(Material.DEAD_BUSH, 1).setName("&e/lvl, /lvl <gracz>").addGlowing().toItemStack().clone());
        gui.setItem(13, new ItemBuilder(Material.DEAD_BUSH, 1).setName("&e/misje").addGlowing().toItemStack().clone());
        gui.setItem(14, new ItemBuilder(Material.DEAD_BUSH, 1).setName("&e/ping, /ping <gracz>").addGlowing().toItemStack().clone());
        gui.setItem(15, new ItemBuilder(Material.DEAD_BUSH, 1).setName("&e/rangi").addGlowing().toItemStack().clone());
        gui.setItem(16, new ItemBuilder(Material.DEAD_BUSH, 1).setName("&e/bony, /akcesoria, /dodatki").addGlowing().toItemStack().clone());
        gui.setItem(17, new ItemBuilder(Material.DEAD_BUSH, 1).setName("&e/artefakty").addGlowing().toItemStack().clone());
        gui.setItem(18, new ItemBuilder(Material.DEAD_BUSH, 1).setName("&e/administracja").addGlowing().toItemStack().clone());
        gui.setItem(19, new ItemBuilder(Material.DEAD_BUSH, 1).setName("&e/craftingi").addGlowing().toItemStack().clone());
        gui.setItem(20, new ItemBuilder(Material.DEAD_BUSH, 1).setName("&e/kosz").addGlowing().toItemStack().clone());
        gui.setItem(21, new ItemBuilder(Material.DEAD_BUSH, 1).setName("&e/topki").addGlowing().toItemStack().clone());
        gui.setItem(22, new ItemBuilder(Material.DEAD_BUSH, 1).setName("&e/helpop").addGlowing().toItemStack().clone());
        gui.setItem(23, new ItemBuilder(Material.DEAD_BUSH, 1).setName("&e/listagraczy").addGlowing().toItemStack().clone());
        gui.setItem(24, new ItemBuilder(Material.DEAD_BUSH, 1).setName("&e/piersciendoswiadczenia").addGlowing().toItemStack().clone());
        gui.setItem(25, new ItemBuilder(Material.DEAD_BUSH, 1).setName("&e/sprawdzefekty <gracz>").addGlowing().toItemStack().clone());
        gui.setItem(26, new ItemBuilder(Material.DEAD_BUSH, 1).setName("&e[item] &f- interakcja gui z chatem").addGlowing().toItemStack().clone());
        gui.setItem(27, new ItemBuilder(Material.DEAD_BUSH, 1).setName("&e/ranktime").addGlowing().toItemStack().clone());
        gui.setItem(28, new ItemBuilder(Material.DEAD_BUSH, 1).setName("&e/rozpiska").addGlowing().toItemStack().clone());


        gui.setItem(35, new ItemBuilder(Material.ARROW,1 ).setName("&cPowrot").addGlowing().toItemStack().clone());
        player.openInventory(gui);
    }
}
