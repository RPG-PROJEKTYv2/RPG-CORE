package rpg.rpgcore.playerInventory;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
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
    public void onItemClick(final PlayerInteractEvent e) {
        if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) return;
        if (!RPGCORE.getInstance().getUserManager().find(e.getPlayer().getUniqueId()).isHellCodeLogin() && !RPGCORE.getInstance().getChatManager().find(e.getPlayer().getUniqueId()).getHellcodeUser().isInventoryInteract()) {
            e.setCancelled(true);
            e.setUseItemInHand(Event.Result.DENY);
            e.setUseInteractedBlock(Event.Result.DENY);
            e.getPlayer().sendMessage(Utils.format(Utils.SERVERNAME + "&7Przed zrobieniem tego zaloguj sie swoim HellCode. Uzyj: &c/hellcode <kod>"));
        }
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventory(final InventoryClickEvent e) {
        if (e.getClickedInventory() == null || e.getInventory() == null) return;
        if (e.getWhoClicked().getGameMode() == GameMode.SURVIVAL) {
            if (e.getClickedInventory().getType().equals(InventoryType.CRAFTING)) {
                final Player player = (Player) e.getWhoClicked();
                if (e.getSlotType().equals(InventoryType.SlotType.CRAFTING)) {
                    e.setCancelled(true);
                    final int slot = e.getSlot();
                    if (slot == 3) {
                        player.performCommand("spawn");
                        return;
                    }
                    if (!RPGCORE.getInstance().getUserManager().find(player.getUniqueId()).isHellCodeLogin()) {
                        player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Przed uzyciem tej komendy zaloguj sie swoim HellCode. Uzyj: &c/hellcode <kod>"));
                        return;
                    }
                    if (slot == 1) {
                        player.performCommand("magazyny");
                        return;
                    }
                    if (slot == 2) {
                        RPGCORE.getInstance().getDodatkiManager().openAkcePodsGUI(player, player.getUniqueId());
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
                new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setSkullOwnerByURL("14c901fa-c245-46fe-b638-72b25f150675", "skin95605713",
                        "ewogICJ0aW1lc3RhbXAiIDogMTYyMDE0NDkwMzk5NCwKICAicHJvZmlsZUlkIiA6ICIxNGM5MDFmYWMyNDU0NmZlYjYzODcyYjI1ZjE1MDY3NSIsCiAgInByb2ZpbGVOYW1lIiA6I" +
                                "CJFYXJ0aCIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzL" +
                                "m1pbmVjcmFmdC5uZXQvdGV4dHVyZS9jZjU5MjFjOTNhMDA2Y2FhNTc0YzNhNjQyYmU0MzE3MmQzNTRkNzYzMTI3MzU2ZTNlNDExNzllYTczODUyNTRjIgogICAgfSwKICAgICJDQ" +
                                "VBFIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS85NTNjYWM4Yjc3OWZlNDEzODNlNjc1ZWUyYjg2MDcxYTcxNjU4ZjIxO" +
                                "DBmNTZmYmNlOGFhMzE1ZWE3MGUyZWQ2IgogICAgfQogIH0KfQ==",
                        "GA3WnPHWlKwPd5uNmGzxM8qlto1BIl4Khh/yr5v2uPdoi+OPEbBawGWA7xHMu6Qq0BwrrNrb/YKHghBF90uHu9R8eGKyc4DBhUo2l5NZe020HtER7T8J77SYg9TkNtVinUnVEw2" +
                                "qYEitMR6fx8aiE5Bd+XS+on5vvEaqKGN77lPlqdiPiAhnG/bCCXOaiI5U6CR+Z6pxlvmQABSGznPvubuhONldIsPO5yNV1pSD3x11tei/X/fuWxIENcW2yE4KOEmtfbsxYpRMGde" +
                                "+2RbBKddkU6jZtvJA7w6MSHnzAerTwBPSatQtVar1/r7LY6Uq5L73dK1YefD20a0B10lSQKTw28TBblrJWg4YfGM/3aCKMbgmcgPojWaolr70KsGACGiQoegqtjqs5Xmgi47j8xG" +
                                "xS4aaGKmxZdSbfi3UE5R8o23EJY+SCRyPC/JhcyZafQyc8Dj0LZS7etXE4+G0Mks+0LWQOW4CNaFd+Dki+UlPdtVAP0cYsLtHzZRwxVxKPgX7qgNSmq41jaaXczFHqCDEWctdXSx" +
                                "+2s7Su4xHyok5k3zJSAvEdvnTQIUkA7b1mYHo7l/hPO5GUxpQa32fobCnqvmfJNtkph49Uev/S8zae8t0V+MJ2mKFKIgQG4PA2yyTxmS8erApim8ljg/vurLt1y5gQvoBTmPIaQ/G7Y0="
                        ).setName("&bSpawn").setLore(Arrays.asList("&8Kliknij, aby teleportowac sie na spawn")).toItemStack(),
                new ItemBuilder(Material.REDSTONE_TORCH_ON).setName("&3&lStatus Wyszkolenia").setLore(Arrays.asList("&8Kliknij, aby otworzyc sciezke rozwoju postaci")).toItemStack());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityRespawn(final PlayerRespawnEvent e) {
        e.getPlayer().getInventory().removeItem(new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setSkullOwner(e.getPlayer().getName()).setName("&6Profil").setLore(Arrays.asList("&8Kliknij, aby otworzyc swoj profil")).toItemStack().clone(),
                new ItemBuilder(Material.CHEST).setName("&6Magazyny").setLore(Arrays.asList("&8Kliknij, aby otworzyc liste magazynow")).toItemStack(),
                new ItemBuilder(Material.ITEM_FRAME).setName("&6Akcesoria Podstawowe").setLore(Arrays.asList("&8Kliknij, aby otworzyc menu podstawowego akcesorium")).toItemStack(),
                new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setSkullOwnerByURL("14c901fa-c245-46fe-b638-72b25f150675", "skin95605713",
                        "ewogICJ0aW1lc3RhbXAiIDogMTYyMDE0NDkwMzk5NCwKICAicHJvZmlsZUlkIiA6ICIxNGM5MDFmYWMyNDU0NmZlYjYzODcyYjI1ZjE1MDY3NSIsCiAgInByb2ZpbGVOYW1lIiA6I" +
                                "CJFYXJ0aCIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzL" +
                                "m1pbmVjcmFmdC5uZXQvdGV4dHVyZS9jZjU5MjFjOTNhMDA2Y2FhNTc0YzNhNjQyYmU0MzE3MmQzNTRkNzYzMTI3MzU2ZTNlNDExNzllYTczODUyNTRjIgogICAgfSwKICAgICJDQ" +
                                "VBFIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS85NTNjYWM4Yjc3OWZlNDEzODNlNjc1ZWUyYjg2MDcxYTcxNjU4ZjIxO" +
                                "DBmNTZmYmNlOGFhMzE1ZWE3MGUyZWQ2IgogICAgfQogIH0KfQ==",
                        "GA3WnPHWlKwPd5uNmGzxM8qlto1BIl4Khh/yr5v2uPdoi+OPEbBawGWA7xHMu6Qq0BwrrNrb/YKHghBF90uHu9R8eGKyc4DBhUo2l5NZe020HtER7T8J77SYg9TkNtVinUnVEw2" +
                                "qYEitMR6fx8aiE5Bd+XS+on5vvEaqKGN77lPlqdiPiAhnG/bCCXOaiI5U6CR+Z6pxlvmQABSGznPvubuhONldIsPO5yNV1pSD3x11tei/X/fuWxIENcW2yE4KOEmtfbsxYpRMGde" +
                                "+2RbBKddkU6jZtvJA7w6MSHnzAerTwBPSatQtVar1/r7LY6Uq5L73dK1YefD20a0B10lSQKTw28TBblrJWg4YfGM/3aCKMbgmcgPojWaolr70KsGACGiQoegqtjqs5Xmgi47j8xG" +
                                "xS4aaGKmxZdSbfi3UE5R8o23EJY+SCRyPC/JhcyZafQyc8Dj0LZS7etXE4+G0Mks+0LWQOW4CNaFd+Dki+UlPdtVAP0cYsLtHzZRwxVxKPgX7qgNSmq41jaaXczFHqCDEWctdXSx" +
                                "+2s7Su4xHyok5k3zJSAvEdvnTQIUkA7b1mYHo7l/hPO5GUxpQa32fobCnqvmfJNtkph49Uev/S8zae8t0V+MJ2mKFKIgQG4PA2yyTxmS8erApim8ljg/vurLt1y5gQvoBTmPIaQ/G7Y0="
                ).setName("&bSpawn").setLore(Arrays.asList("&8Kliknij, aby teleportowac sie na spawn")).toItemStack(),
                new ItemBuilder(Material.REDSTONE_TORCH_ON).setName("&3&lStatus Wyszkolenia").setLore(Arrays.asList("&8Kliknij, aby otworzyc sciezke rozwoju postaci")).toItemStack());
    }

}
