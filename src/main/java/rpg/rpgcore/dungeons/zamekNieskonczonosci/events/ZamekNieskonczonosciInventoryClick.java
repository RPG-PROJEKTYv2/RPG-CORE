package rpg.rpgcore.dungeons.zamekNieskonczonosci.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.dungeons.DungeonStatus;
import rpg.rpgcore.dungeons.zamekNieskonczonosci.ZamekNieskonczonosciManager;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

public class ZamekNieskonczonosciInventoryClick implements Listener {

    private final ZamekNieskonczonosciManager manager;

    public ZamekNieskonczonosciInventoryClick() {
        this.manager = RPGCORE.getInstance().getZamekNieskonczonosciManager();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClick(final InventoryClickEvent e) {

        if (e.getInventory() == null || e.getClickedInventory() == null) {
            return;
        }

        final Inventory gui = e.getClickedInventory();
        final Player player = (Player) e.getWhoClicked();
        final String title = Utils.removeColor(gui.getTitle());
        final ItemStack item = e.getCurrentItem();
        final int slot = e.getSlot();

        if (title.equals("Zaginiony Wladca")) {
            e.setCancelled(true);
            if (manager.status != DungeonStatus.STARTED) {
                System.out.println(1);
                return;
            }
            if (item == null || item.getType().equals(Material.SKULL_ITEM)) {
                System.out.println(2);
                return;
            }

            if (item.getType().equals(Material.STAINED_GLASS_PANE) && item.getDurability() == 0) {
                System.out.println(3);
                return;
            }

            if (!gui.getItem(slot - 9).getItemMeta().getDisplayName().contains(player.getName())) {
                System.out.println(4);
                return;
            }

            if (item.getDurability() == 14) {
                gui.setItem(slot, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 5).setName("&aGotowy!").toItemStack());
                manager.ready++;
                for (Player player1 : manager.players) {
                    player1.sendMessage(Utils.format("&7Gracz &6" + player.getName() + " &7jest &agotowy&7!"));
                }
                if (manager.ready == 2) {
                    for (Player player1 : manager.players) {
                        if (player1.getOpenInventory() != null && player1.getOpenInventory().getTopInventory() != null &&
                                player1.getOpenInventory().getTopInventory().getTitle() != null && player1.getOpenInventory().getTopInventory().getTitle().contains("Zaginiony Wladca")) {
                            player1.closeInventory();
                        }
                    }
                    manager.startDungeon(manager.party);
                    return;
                } else {
                    for (Player player1 : manager.players) {
                        if (player1.getOpenInventory() != null && player1.getOpenInventory().getTopInventory() != null &&
                                player1.getOpenInventory().getTopInventory().getTitle() != null && player1.getOpenInventory().getTopInventory().getTitle().contains("Zaginiony Wladca")) {
                            player1.getOpenInventory().getTopInventory().setItem(slot, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 5).setName("&aGotowy!").toItemStack());
                        }
                    }
                    return;
                }
            }
            if (item.getDurability() == 5) {
                gui.setItem(slot, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName("&cNie gotowy!").toItemStack());
                manager.ready--;
                for (Player player1 : manager.players) {
                    if (player1.getOpenInventory() != null && player1.getOpenInventory().getTopInventory() != null &&
                            player1.getOpenInventory().getTopInventory().getTitle() != null && player1.getOpenInventory().getTopInventory().getTitle().contains("Zaginiony Wladca")) {
                        player1.getOpenInventory().getTopInventory().setItem(slot, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName("&cNie gotowy!").toItemStack());
                    }
                }
                for (Player player1 : manager.players) {
                    player1.sendMessage(Utils.format("&7Gracz &6" + player.getName() + " &7jest &cnie gotowy&7!"));
                }
            }

        }
    }
}
