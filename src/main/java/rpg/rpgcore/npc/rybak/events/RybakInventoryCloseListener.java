package rpg.rpgcore.npc.rybak.events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

public class RybakInventoryCloseListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClose(final InventoryCloseEvent e) {
        if (Utils.removeColor(e.getInventory().getTitle()).equals("Rybak » Anty-AFK")) {
            if (e.getInventory().contains(Material.AIR)) {
                return;
            }
            RPGCORE.getInstance().getRybakNPC().addFailedAttempt(e.getPlayer().getUniqueId());
            e.getPlayer().sendMessage(Utils.format("&6&lRybak &8>> &cNie udalo sie przeslac weryfikacji Anty-AFK &4(" + RPGCORE.getInstance().getRybakNPC().getFailedAttempts(e.getPlayer().getUniqueId()) + "/3)"));
            if (RPGCORE.getInstance().getRybakNPC().getFailedAttempts(e.getPlayer().getUniqueId()) >= 3) {
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "tempban " + e.getPlayer().getName() + "6 h Afk na lowienie (skrypt?)");
            }
        }
    }
}
