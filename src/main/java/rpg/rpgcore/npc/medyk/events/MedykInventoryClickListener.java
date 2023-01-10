package rpg.rpgcore.npc.medyk.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.npc.medyk.objects.MedykUser;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.GlobalItem;

import java.util.UUID;

public class MedykInventoryClickListener implements Listener {

    private final RPGCORE rpgcore;

    public MedykInventoryClickListener(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }


    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClick(final InventoryClickEvent e) {

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
                return;
            }

            if (medykUser.getBonus() == 50) {
                return;
            }

            if (slot == 3) {
                if (clickedInventory.getItem(3) != null && clickedInventory.getItem(3).equals(GlobalItem.getItem("I57", 1))) {
                    player.getInventory().addItem(GlobalItem.getItem("I57", 1));
                    clickedInventory.setItem(3, new ItemStack(Material.AIR));
                    return;
                }
                if (!e.getCursor().equals(GlobalItem.getItem("I57", 1))) return;
                if (e.getCursor().getAmount() > 1) return;
                if (medykUser.isDone()) return;
                if (medykUser.getProgress() < Utils.getTagInt(clickedInventory.getItem(0), "req")) return;
                clickedInventory.setItem(3, e.getCursor().clone());
                e.setCursor(null);

                if (clickedInventory.getItem(1) != null && clickedInventory.getItem(1).equals(GlobalItem.getItem("I56", 1)) && clickedInventory.getItem(3) != null && clickedInventory.getItem(3).equals(GlobalItem.getItem("I57", 1))) {
                    rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> {
                        clickedInventory.setItem(3, new ItemStack(Material.AIR));
                        clickedInventory.setItem(1, new ItemStack(Material.AIR));
                        rpgcore.getMedykNPC().runAnimation(player, Utils.getTagInt(clickedInventory.getItem(0), "req"));
                    }, 1L);
                }
                return;
            }

            if (slot == 1) {
                if (medykUser.isDone()) {
                    player.getInventory().addItem(clickedInventory.getItem(1).clone());
                    clickedInventory.setItem(1, new ItemStack(Material.AIR));
                    player.closeInventory();
                    medykUser.setDone(false);
                } else {
                    if (clickedInventory.getItem(1) != null && clickedInventory.getItem(1).equals(GlobalItem.getItem("I56", 1))) {
                        player.getInventory().addItem(GlobalItem.getItem("I56", 1));
                        clickedInventory.setItem(1, new ItemStack(Material.AIR));
                        return;
                    }
                    if (!e.getCursor().equals(GlobalItem.getItem("I56", 1))) return;
                    if (e.getCursor().getAmount() > 1) return;
                    if (medykUser.getProgress() < Utils.getTagInt(clickedInventory.getItem(0), "req")) return;
                    clickedInventory.setItem(1, e.getCursor().clone());
                    e.setCursor(null);
                    if (clickedInventory.getItem(1) != null && clickedInventory.getItem(1).equals(GlobalItem.getItem("I56", 1)) &&
                    clickedInventory.getItem(3) != null && clickedInventory.getItem(3).equals(GlobalItem.getItem("I57", 1))) {
                        rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> {
                            clickedInventory.setItem(3, new ItemStack(Material.AIR));
                            clickedInventory.setItem(1, new ItemStack(Material.AIR));
                            rpgcore.getMedykNPC().runAnimation(player, Utils.getTagInt(clickedInventory.getItem(0), "req"));
                        }, 1L);
                    }
                }
            }

        }
    }
}
