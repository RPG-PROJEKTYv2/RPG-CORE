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
                player.getOpenInventory().getBottomInventory().removeItem(
                        new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setSkullOwner(player.getName()).setName("&6Profil").setLore(Arrays.asList("&8Kliknij, aby otworzyc swoj profil")).toItemStack().clone(),
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
                        new ItemBuilder(Material.REDSTONE_TORCH_ON).setName("&3&lStatus Wyszkolenia").setLore(Arrays.asList("&8Kliknij, aby otworzyc sciezke rozwoju postaci")).toItemStack()
                        );
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
                        player.getOpenInventory().getTopInventory().setItem(3, new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setSkullOwnerByURL("14c901fa-c245-46fe-b638-72b25f150675", "skin95605713",
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
                        ).setName("&bSpawn").setLore(Arrays.asList("&8Kliknij, aby teleportowac sie na spawn")).toItemStack());
                    }
                    if (player.getOpenInventory().getTopInventory().getItem(4) == null) {
                        player.getOpenInventory().getTopInventory().setItem(4, new ItemBuilder(Material.REDSTONE_TORCH_ON).setName("&3&lStatus Wyszkolenia").setLore(Arrays.asList("&8Kliknij, aby otworzyc sciezke rozwoju postaci")).toItemStack());
                    }
                    player.updateInventory();
                }
            }
        }
    }
}
