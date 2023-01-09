package rpg.rpgcore.playerInventory.tasks;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.ItemBuilder;

import java.util.Arrays;

public class PlayerInventoryTask implements Runnable {

    public PlayerInventoryTask(final RPGCORE rpgcore) {
        rpgcore.getServer().getScheduler().runTaskTimerAsynchronously(rpgcore, this, 20L, 5L);
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getGameMode() == GameMode.SURVIVAL) {
                if (player.getOpenInventory().getType().equals(InventoryType.CRAFTING)) {
                    if (player.getOpenInventory().getTopInventory().getItem(0) == null) {
                        player.getOpenInventory().getTopInventory().setItem(0, new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setSkullOwner(player.getName()).setName("&6Profil").setLore(Arrays.asList("&8Kliknij, aby otworzyc swoj profil")).toItemStack().clone());
                    }
                    if (player.getOpenInventory().getTopInventory().getItem(1) == null) {
                        player.getOpenInventory().getTopInventory().setItem(1, new ItemBuilder(Material.CHEST).setName("&6Magazyny").setLore(Arrays.asList("&8Kliknij, aby otworzyc liste magazynow")).toItemStack());
                    }
                    if (player.getOpenInventory().getTopInventory().getItem(2) == null) {
                        player.getOpenInventory().getTopInventory().setItem(2, new ItemBuilder(Material.ITEM_FRAME).setName("&6Akcesoria Podstawowe").setLore(Arrays.asList("&8Kliknij, aby otworzyc menu podstawowego akcesorium")).toItemStack());
                    }
                    if (player.getOpenInventory().getTopInventory().getItem(3) == null) {
                        player.getOpenInventory().getTopInventory().setItem(3, new ItemBuilder(Material.FLOWER_POT_ITEM).setName("&cKosz").setLore(Arrays.asList("&8Kliknij, aby otworzyc kosz")).toItemStack());
                    }
                    if (player.getOpenInventory().getTopInventory().getItem(4) == null) {
                        player.getOpenInventory().getTopInventory().setItem(4, new ItemBuilder(Material.WORKBENCH).setName("&6Craftingi").setLore(Arrays.asList("&8Kliknij, aby otworzyc menu craftingow")).toItemStack());
                    }
                    player.updateInventory();
                }
            }
        }
    }
}
