package rpg.rpgcore.npc.pustelnik.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.npc.pustelnik.enums.PustelnikMissions;
import rpg.rpgcore.npc.pustelnik.objects.PustelnikUser;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.ItemShop;
import rpg.rpgcore.utils.globalitems.expowiska.Bossy;

import java.util.Objects;
import java.util.UUID;

public class PustelnikInventoryClickListener implements Listener {
    private final RPGCORE rpgcore;

    public PustelnikInventoryClickListener(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

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

        if (title.equals("Pustelnik")) {
            e.setCancelled(true);
            final PustelnikUser user = this.rpgcore.getPustelnikNPC().find(uuid);
            if (slot == 2) {
                if (user.getProgress() < Objects.requireNonNull(PustelnikMissions.getById(user.getMissionId())).getReqAmount()) return;
                player.sendMessage(Utils.format("&e&lPustelnik &8>> &fOto twoja nagroda!"));
                player.getInventory().addItem(Bossy.I3_1.getItemStack().clone());
                user.setMissionId(0);
                user.setProgress(0);
                this.rpgcore.getPustelnikNPC().save(user);
                player.closeInventory();
                return;
            }

            if (slot == 4) {
                if (!player.getInventory().containsAtLeast(ItemShop.IS21.getItems().get(0).clone(), 1)) return;
                player.getInventory().removeItem(new ItemBuilder(ItemShop.IS21.getItems().get(0).clone()).setAmount(1).toItemStack().clone());
                player.sendMessage(Utils.format("&e&lPustelnik &8>> &fTwoje zadanie zostalo zresetowane"));

                user.setMissionId(0);
                user.setProgress(0);
                this.rpgcore.getPustelnikNPC().save(user);
                player.closeInventory();
            }
        }

    }
}
