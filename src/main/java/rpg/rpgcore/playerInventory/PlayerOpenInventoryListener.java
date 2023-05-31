package rpg.rpgcore.playerInventory;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.Arrays;


public class PlayerOpenInventoryListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventory(final InventoryClickEvent e) {
        if (e.getClickedInventory() == null || e.getInventory() == null) return;
        if (e.getWhoClicked().getGameMode() == GameMode.SURVIVAL) {
            if (e.getClickedInventory().getType().equals(InventoryType.CRAFTING)) {
                final Player player = (Player) e.getWhoClicked();
                if (e.getSlotType().equals(InventoryType.SlotType.CRAFTING)) {
                    e.setCancelled(true);
                    if (!RPGCORE.getInstance().getUserManager().find(player.getUniqueId()).isHellCodeLogin()) {
                        player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Przed uzyciem tej komendy zaloguj sie swoim HellCode. Uzyj: &c/hellcode <kod>"));
                        return;
                    }
                    final int slot = e.getSlot();
                    if (slot == 1) {
                        player.performCommand("magazyny");
                        return;
                    }
                    if (slot == 2) {
                        RPGCORE.getInstance().getDodatkiManager().openAkcePodsGUI(player, player.getUniqueId());
                        return;
                    }
                    if (slot == 3) {
                        player.performCommand("kosz");
                        return;
                    }
                    if (slot == 4) {
                        RPGCORE.getInstance().getWyszkolenieManager().openWyszkolenieGUI(player);
                        return;
                    }
                    return;
                }
                if (e.getSlotType().equals(InventoryType.SlotType.RESULT)) {
                    e.setCancelled(true);
                    if (!RPGCORE.getInstance().getUserManager().find(player.getUniqueId()).isHellCodeLogin()) {
                        player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Przed uzyciem tej komendy zaloguj sie swoim HellCode. Uzyj: &c/hellcode <kod>"));
                        return;
                    }

                    if (e.getSlot() == 0) {
                        player.performCommand("profile");
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerJoin(final PlayerJoinEvent e) {
        if (e.getPlayer().getOpenInventory().getTopInventory().getType().equals(InventoryType.CRAFTING)) {
            e.getPlayer().getOpenInventory().getTopInventory().clear();
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(final PlayerQuitEvent e) {
        if (e.getPlayer().getOpenInventory().getTopInventory().getType().equals(InventoryType.CRAFTING)) {
            e.getPlayer().getOpenInventory().getTopInventory().clear();
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClose(final InventoryCloseEvent e) {
        if (e.getPlayer().getGameMode() == GameMode.SURVIVAL) {
            if (e.getInventory().getType().equals(InventoryType.CRAFTING)) {
                e.getPlayer().getOpenInventory().getTopInventory().clear();
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onGameModeChange(final PlayerGameModeChangeEvent e) {
        if (e.getPlayer().getOpenInventory().getType().equals(InventoryType.CRAFTING)) {
            e.getPlayer().getOpenInventory().getTopInventory().clear();
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onTeleport(final PlayerTeleportEvent e) {
        if (e.getPlayer().getGameMode() == GameMode.SURVIVAL) {
            if (e.getPlayer().getOpenInventory().getTopInventory().getType().equals(InventoryType.CRAFTING)) {
                e.getPlayer().closeInventory();
                e.getPlayer().getOpenInventory().getTopInventory().clear();
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDeath(final PlayerDeathEvent e) {
        e.getDrops().clear();
        e.getEntity().closeInventory();
        e.getEntity().closeInventory();
        e.getEntity().getInventory().removeItem(new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setSkullOwner(e.getEntity().getName()).setName("&6Profil").setLore(Arrays.asList("&8Kliknij, aby otworzyc swoj profil")).toItemStack().clone(),
                new ItemBuilder(Material.CHEST).setName("&6Magazyny").setLore(Arrays.asList("&8Kliknij, aby otworzyc liste magazynow")).toItemStack(),
                new ItemBuilder(Material.ITEM_FRAME).setName("&6Akcesoria Podstawowe").setLore(Arrays.asList("&8Kliknij, aby otworzyc menu podstawowego akcesorium")).toItemStack(),
                new ItemBuilder(Material.FLOWER_POT_ITEM).setName("&cKosz").setLore(Arrays.asList("&8Kliknij, aby otworzyc kosz")).toItemStack(),
                new ItemBuilder(Material.REDSTONE_TORCH_ON).setName("&3&lStatus Wyszkolenia").setLore(Arrays.asList("&8Kliknij, aby otworzyc sciezke rozwoju postaci")).toItemStack());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityRespawn(final PlayerRespawnEvent e) {
        e.getPlayer().getInventory().removeItem(new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setSkullOwner(e.getPlayer().getName()).setName("&6Profil").setLore(Arrays.asList("&8Kliknij, aby otworzyc swoj profil")).toItemStack().clone(),
                new ItemBuilder(Material.CHEST).setName("&6Magazyny").setLore(Arrays.asList("&8Kliknij, aby otworzyc liste magazynow")).toItemStack(),
                new ItemBuilder(Material.ITEM_FRAME).setName("&6Akcesoria Podstawowe").setLore(Arrays.asList("&8Kliknij, aby otworzyc menu podstawowego akcesorium")).toItemStack(),
                new ItemBuilder(Material.FLOWER_POT_ITEM).setName("&cKosz").setLore(Arrays.asList("&8Kliknij, aby otworzyc kosz")).toItemStack(),
                new ItemBuilder(Material.REDSTONE_TORCH_ON).setName("&3&lStatus Wyszkolenia").setLore(Arrays.asList("&8Kliknij, aby otworzyc sciezke rozwoju postaci")).toItemStack());
    }

}
