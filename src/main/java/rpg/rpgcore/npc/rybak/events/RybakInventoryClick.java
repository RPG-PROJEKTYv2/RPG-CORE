package rpg.rpgcore.npc.rybak.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

public class RybakInventoryClick implements Listener {

    private final RPGCORE rpgcore;

    public RybakInventoryClick(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void rybakInventoryClick(final InventoryClickEvent e) {

        final Inventory clickedInventory = e.getClickedInventory();
        final Player player = (Player) e.getWhoClicked();
        final UUID uuid = player.getUniqueId();

        if (e.getClickedInventory() == null || e.getInventory() == null) {
            return;
        }

        final String title = Utils.removeColor(clickedInventory.getTitle());
        final ItemStack item = e.getCurrentItem();


        if (title.equals("Rybak » Anty-AFK")) {
            e.setCancelled(true);
            if (item != null && item.getItemMeta().hasItemFlag(ItemFlag.HIDE_ENCHANTS)) {
                rpgcore.getRybakNPC().getPassed().add(uuid);
                rpgcore.getRybakNPC().resetFishingCount(uuid);
                rpgcore.getRybakNPC().resetFailedAttempt(uuid);
                Bukkit.getScheduler().cancelTask(rpgcore.getRybakNPC().getTaskMap().get(uuid));
                player.closeInventory();
                player.sendMessage(Utils.format("&7Ochrona &cAnty-AFK"));
                player.sendMessage(Utils.format("&aPomyslnie przeszedles/-as weryfikacje &cAnty-AFK&a!"));
                player.sendMessage(Utils.format("&7Ochrona &cAnty-AFK"));
                return;
            }
            Bukkit.getScheduler().cancelTask(rpgcore.getRybakNPC().getTaskMap().get(uuid));
            player.closeInventory();
        }


    }

}
