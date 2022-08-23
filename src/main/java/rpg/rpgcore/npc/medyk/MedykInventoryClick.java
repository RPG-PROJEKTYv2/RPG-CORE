package rpg.rpgcore.npc.medyk;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

public class MedykInventoryClick implements Listener {

    private final RPGCORE rpgcore;

    public MedykInventoryClick(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }


    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClick(final InventoryClickEvent e){

        final Inventory clickedInventory = e.getClickedInventory();
        final Player player = (Player) e.getWhoClicked();
        final UUID playerUUID = player.getUniqueId();

        if (e.getClickedInventory() == null) {
            return;
        }

        final String title = clickedInventory.getTitle();
        final int slot = e.getSlot();

        if (Utils.removeColor(title).contains("Medyk")) {
            e.setCancelled(true);
            final MedykUser medykUser = rpgcore.getMedykNPC().find(playerUUID).getMedykUser();

            if (slot == 0) {
                medykUser.setProgress(7000);
                return;
            }

            if (slot == 3 && player.getItemOnCursor().equals(new ItemBuilder(Material.REDSTONE).setName("&8Test").toItemStack())
                    && player.getItemOnCursor().getAmount() == 1 && medykUser.getBonus() <= 50 && !medykUser.isDone() && medykUser.getProgress() >= 7000) {
                e.setCancelled(false);
                rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> {clickedInventory.getItem(3).setType(Material.AIR);
                rpgcore.getMedykNPC().runAnimation(player);} , 1L);
                return;
            }

            if (medykUser.isDone() && slot == 1) {
                e.setCancelled(false);
                player.getInventory().addItem(clickedInventory.getItem(1).clone());
                e.setCancelled(true);
                rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> clickedInventory.setItem(1, rpgcore.getMedykNPC().getMedykPotionNotDone()), 1L);
                medykUser.setDone(false);
                return;
            }

        }
    }
}
