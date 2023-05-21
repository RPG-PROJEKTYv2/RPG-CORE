package rpg.rpgcore.dungeons.custom;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.Arrays;

public class DungeonsManager {
    public void openDungeonMenu(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 9, Utils.format("&4&lDungeony"));
        gui.setItem(0, new ItemBuilder(Material.SMOOTH_BRICK).setName("&4Zamek Nieskonczonosci").setLore(Arrays.asList(
                "&7Wymagania:",
                "&8- &7Ilosc Osob: &64",
                "&8- &7Poziom: &670",
                "&8- &61&7x &cKlucz Do Wrot Zamku Nieskonczonosci",
                " ",
                "&7Dodatkowe Informacje:",
                "&8 - &7Ilosc Etapow: &c5",
                "&8 - &7Sredni Czas Ukonczenia: &c~20min",
                "&8 - &7Czas Na Przejscie: &c30min"
        )).toItemStack());

        player.openInventory(gui);
    }
}
