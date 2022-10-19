package rpg.rpgcore.npc.gornik.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

public class GornikInventoryCloseListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onClose(final InventoryCloseEvent e) {
        if (Utils.removeColor(e.getInventory().getTitle()).equals("Osadzanie Krysztalow")) {
            //TODO NAPRAWIC PRZY WSADZANIU KRYSZTALU WPIERDALA SIE DO PETLI
            if (e.getInventory().getSize() == 54){
                if (e.getInventory().getItem(13) != null) {
                    RPGCORE.getInstance().getServer().getScheduler().runTaskLater(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getGornikNPC().openOsadzanieKrysztalow((Player) e.getPlayer(), e.getInventory().getItem(13)), 1L);
                    e.getPlayer().sendMessage(Utils.format("&cMusisz najpier wyjac swoj przedmiot!"));
                }
            }
        }
    }
}
