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
        ItemBuilder itemGUI = new ItemBuilder(Material.MAP, 1);
        gui.setItem(0, itemGUI.setName("&e/listanpc").addGlowing().toItemStack().clone());
        gui.setItem(1, itemGUI.setName("&e/bossy").addGlowing().toItemStack().clone());
        gui.setItem(2, itemGUI.setName("&e/money, /wyplac").addGlowing().toItemStack().clone());
        gui.setItem(3, itemGUI.setName("&e/targ, /wystaw").addGlowing().toItemStack().clone());
        gui.setItem(4, itemGUI.setName("&e/klan").addGlowing().toItemStack().clone());
        gui.setItem(5, itemGUI.setName("&e/profil").addGlowing().toItemStack().clone());
        gui.setItem(6, itemGUI.setName("&e/chatpanel").addGlowing().toItemStack().clone());
        gui.setItem(7, itemGUI.setName("&e/msg <gracz> <wiadomosc>, /r <wiadomosc>").addGlowing().toItemStack().clone());
        gui.setItem(8, itemGUI.setName("&e/hs").addGlowing().toItemStack().clone());
        gui.setItem(9, itemGUI.setName("&e/icetower").addGlowing().toItemStack().clone());
        gui.setItem(10, itemGUI.setName("&e/enderchest").addGlowing().toItemStack().clone());
        gui.setItem(11, itemGUI.setName("&e/hellcode").addGlowing().toItemStack().clone());
        gui.setItem(12, itemGUI.setName("&e/lvl, /lvl <gracz>").addGlowing().toItemStack().clone());
        gui.setItem(13, itemGUI.setName("&e/misje").addGlowing().toItemStack().clone());
        gui.setItem(14, itemGUI.setName("&e/ping, /ping <gracz>").addGlowing().toItemStack().clone());
        gui.setItem(15, itemGUI.setName("&e/rangi").addGlowing().toItemStack().clone());
        gui.setItem(16, itemGUI.setName("&e/bony, /akcesoria, /dodatki").addGlowing().toItemStack().clone());
        gui.setItem(17, itemGUI.setName("&e/artefakty").addGlowing().toItemStack().clone());
        gui.setItem(18, itemGUI.setName("&e/administracja").addGlowing().toItemStack().clone());
        gui.setItem(19, itemGUI.setName("&e/craftingi").addGlowing().toItemStack().clone());
        gui.setItem(20, itemGUI.setName("&e/kosz").addGlowing().toItemStack().clone());
        gui.setItem(21, itemGUI.setName("&e/topki").addGlowing().toItemStack().clone());
        gui.setItem(22, itemGUI.setName("&e/helpop").addGlowing().toItemStack().clone());
        gui.setItem(23, itemGUI.setName("&e/listagraczy").addGlowing().toItemStack().clone());
        gui.setItem(24, itemGUI.setName("&e/piersciendoswiadczenia, /pd").addGlowing().toItemStack().clone());
        gui.setItem(25, itemGUI.setName("&e[item] &f- interakcja gui z chatem").addGlowing().toItemStack().clone());
        gui.setItem(26, itemGUI.setName("&e/ranktime").addGlowing().toItemStack().clone());
        gui.setItem(27, itemGUI.setName("&e/rozpiska").addGlowing().toItemStack().clone());
        gui.setItem(28, itemGUI.setName("&e/gamma").addGlowing().toItemStack().clone());
        gui.setItem(29, itemGUI.setName("&e/live").addGlowing().toItemStack().clone());
        gui.setItem(30, itemGUI.setName("&e/osiagniecia, /os").addGlowing().toItemStack().clone());
        gui.setItem(31, itemGUI.setName("&e/kontakt").addGlowing().toItemStack().clone());
        gui.setItem(32, itemGUI.setName("&e/hp").addGlowing().toItemStack().clone());
        gui.setItem(33, itemGUI.setName("&e@gracz -> oznaczanie gracza na czacie").addGlowing().toItemStack().clone());
        gui.setItem(35, Utils.powrot());
        player.openInventory(gui);
    }
}
