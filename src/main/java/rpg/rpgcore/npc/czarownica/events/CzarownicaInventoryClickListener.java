package rpg.rpgcore.npc.czarownica.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.npc.czarownica.objects.CzarownicaUser;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

public class CzarownicaInventoryClickListener implements Listener {
    private final RPGCORE rpgcore;

    public CzarownicaInventoryClickListener(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onClick(final InventoryClickEvent e) {
        if (e.getClickedInventory() == null || e.getInventory() == null) {
            return;
        }

        final Inventory inv = e.getClickedInventory();
        final Player player = (Player) e.getWhoClicked();
        final UUID uuid = player.getUniqueId();
        final String title = Utils.removeColor(inv.getTitle());
        final int slot = e.getSlot();
        final CzarownicaUser user = rpgcore.getCzarownicaNPC().find(uuid);

        if (title.equals("Czarownica")) {
            e.setCancelled(true);
            if (slot == 12) {
                rpgcore.getCzarownicaNPC().openKampania(player);
            }
            if (slot == 14) {
                if (!user.isUnlocked()) return;
                rpgcore.getCzarownicaNPC().openKampania(player);
            }
            return;
        }
        if (title.equals("Czarownica - Kampania")) {
            e.setCancelled(true);
            return;
        }
        if (title.equals("Czarownica - Crafting")) {
            e.setCancelled(true);
        }
    }
}
