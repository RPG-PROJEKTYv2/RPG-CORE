package rpg.rpgcore.commands.admin.ustawieniakonta.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

public class UstawieniaKontaInventoryClickListener implements Listener {
    private final RPGCORE rpgcore;

    public UstawieniaKontaInventoryClickListener(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onClick(final InventoryClickEvent e) {
        final Inventory gui = e.getClickedInventory();
        final Player player = (Player) e.getWhoClicked();

        if (e.getClickedInventory() == null || e.getInventory() == null) {
            return;
        }

        final String title = Utils.removeColor(gui.getTitle());
        final int slot = e.getSlot();

        if (title.contains("Konto - ")) {
            e.setCancelled(true);
            switch (slot) {
                case 10:
                    this.rpgcore.getUstawieniaKontaManager().clearInventory(player, this.getUUIDFromTitle(title), e.getCurrentItem().clone());
                    return;
                case 11:
                    this.rpgcore.getUstawieniaKontaManager().clearEnderchest(player, this.getUUIDFromTitle(title), e.getCurrentItem().clone());
                    return;
                case 12:
                    this.rpgcore.getUstawieniaKontaManager().openMagazyny(player, this.getUUIDFromTitle(title));
                    return;
                case 13:
                    this.rpgcore.getUstawieniaKontaManager().openOsiagniecia(player, this.getUUIDFromTitle(title));
                    return;
                case 14:
                    this.rpgcore.getUstawieniaKontaManager().openMisje(player, this.getUUIDFromTitle(title));
                    return;
                case 15:
                    this.rpgcore.getUstawieniaKontaManager().openLvlIExp(player, this.getUUIDFromTitle(title));
                    return;
                case 16:
                    this.rpgcore.getUstawieniaKontaManager().openKasaIHC(player, this.getUUIDFromTitle(title));
            }
        }
    }

    private UUID getUUIDFromTitle(final String title) {
        final String[] split = title.split(" ");
        return UUID.fromString(split[split.length - 1]);
    }
}
