package rpg.rpgcore.npc.mistrz_yang.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.npc.mistrz_yang.objects.MistrzYangUser;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.expowiska.Bossy;

import java.util.UUID;

public class MistrzYangInventoryClickListener implements Listener {
    final RPGCORE rpgcore = RPGCORE.getInstance();

    @EventHandler(priority = EventPriority.LOWEST)
    public void onClick(final InventoryClickEvent e) {
        final Inventory clickedInventory = e.getClickedInventory();
        final Player player = (Player) e.getWhoClicked();
        final UUID uuid = player.getUniqueId();

        if (e.getClickedInventory() == null || e.getInventory() == null) {
            return;
        }

        final String title = Utils.removeColor(clickedInventory.getTitle());
        final int slot = e.getSlot();

        if (title.equals("Mistrz Yang")) {
            e.setCancelled(true);
            if (slot != 4) return;
            final MistrzYangUser user = this.rpgcore.getMistrzYangNPC().find(uuid);
            if (user.getProgress() < user.getReqAmount()) return;
            player.sendMessage(Utils.format("&e&lMistrz Yang &8>> &fOto twoja nagroda!"));
            player.getInventory().addItem(Bossy.I5_2.getItemStack().clone());
            user.reset();
            this.rpgcore.getMistrzYangNPC().save(user);
            player.closeInventory();
        }
    }
}
