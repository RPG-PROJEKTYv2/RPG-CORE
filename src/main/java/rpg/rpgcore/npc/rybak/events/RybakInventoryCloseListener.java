package rpg.rpgcore.npc.rybak.events;

import org.bukkit.Bukkit;
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
            if (RPGCORE.getInstance().getRybakNPC().getPassed().contains(e.getPlayer().getUniqueId())) {
                RPGCORE.getInstance().getRybakNPC().getPassed().remove(e.getPlayer().getUniqueId());
                return;
            }
            RPGCORE.getInstance().getRybakNPC().addFailedAttempt(e.getPlayer().getUniqueId());
            e.getPlayer().sendMessage(Utils.format("&7Ochrona &cAnty-AFK"));
            e.getPlayer().sendMessage(Utils.format("&cNie udalo sie przeslac weryfikacji Anty-AFK &4(" + RPGCORE.getInstance().getRybakNPC().getFailedAttempts(e.getPlayer().getUniqueId()) + "/5)"));
            e.getPlayer().sendMessage(Utils.format("&7Ochrona &cAnty-AFK"));
            if (RPGCORE.getInstance().getRybakNPC().getFailedAttempts(e.getPlayer().getUniqueId()) >= 5) {
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "tempban " + e.getPlayer().getName() + " 6 h Afk na lowienie (skrypt?)");
            }
        }
    }
}
