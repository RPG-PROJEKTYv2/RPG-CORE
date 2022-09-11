package rpg.rpgcore.npc.magazynier;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

public class MagazynierInventoryClose implements Listener {

    private final RPGCORE rpgcore;

    public MagazynierInventoryClose(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void magazynierInventoryClose (final InventoryCloseEvent e) {

        if (e.getInventory() == null) {
            return;
        }

        final Inventory closedInventory = e.getInventory();
        final String closedInventoryTitle = closedInventory.getTitle();
        final Player player = (Player) e.getPlayer();
        final UUID uuid = player.getUniqueId();

        if (closedInventoryTitle.contains("MagazynCommand #")) {
            final int nrMagazynu = Integer.parseInt(Utils.removeColor(closedInventoryTitle).replaceAll("Magazyn #", "").trim());
            rpgcore.getMagazynierNPC().updatePlayerMagazynContent(uuid, nrMagazynu, closedInventory);
        }

    }

}
