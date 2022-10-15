package rpg.rpgcore.chests;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.globalitems.GlobalItem;
import rpg.rpgcore.utils.globalitems.expowiska.Map1Items;
import rpg.rpgcore.utils.Utils;


public class DropFromChestsListener implements Listener {

    private final RPGCORE rpgcore;

    public DropFromChestsListener(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onRightClick(final PlayerInteractEvent e) {
        final Player player = e.getPlayer();

        if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
            return;
        }

        if (player.getItemInHand().getType().equals(Material.CHEST) || player.getItemInHand().getType().equals(Material.ENDER_CHEST)) {

            final ItemStack playerItem = player.getItemInHand();

            // SKRZYNIA Z ROZNOSCIAMI
            if (playerItem.getItemMeta().hasDisplayName()) {
                if (playerItem.getItemMeta().getDisplayName().equals(Utils.format(GlobalItem.getByName("I5").getItemStack().getItemMeta().getDisplayName()))) {
                    if (!player.getCanPickupItems()) {
                        player.getInventory().removeItem(GlobalItem.getItem("I5", 1));
                        final Items item = rpgcore.getRoznosciManager().getDrawnItems(player);
                        if (item == null) {
                            return;
                        }
                        final ItemStack is = item.getRewardItem();
                        is.setAmount(item.getAmount());
                        player.getInventory().addItem(is);
                        return;
                    }
                }
                // TAJEMNICZA SKRZYNIA
                if (playerItem.getItemMeta().getDisplayName().equals(Utils.format(GlobalItem.getByName("I4").getItemStack().getItemMeta().getDisplayName()))) {
                    if (!player.getCanPickupItems()) {
                        player.getInventory().removeItem(GlobalItem.getItem("I4", 1));
                        final Items item = rpgcore.getTajemniczaManager().getDrawnItems(player);
                        if (item == null) {
                            return;
                        }
                        final ItemStack is = item.getRewardItem();
                        is.setAmount(item.getAmount());
                        player.getInventory().addItem(is);
                        return;
                    }
                }
                // unikatowe...

                // ... ZE ZWIERZAKAMI
                if (playerItem.getItemMeta().getDisplayName().equals(Utils.format(GlobalItem.getByName("I3").getItemStack().getItemMeta().getDisplayName()))) {
                    if (!player.getCanPickupItems()) {
                        player.getInventory().removeItem(GlobalItem.getItem("I3", 1));
                        final ItemStack item = rpgcore.getZwierzakiManager().getDrawnItems(player);
                        if (item == null) {
                            return;
                        }
                        player.getInventory().addItem(item);
                        return;
                    }
                }

                //TODO Skrzynia Gornika

                // soon...
                // Expowisko 1
                //  Skrzynia Wygnanca
                if (playerItem.getItemMeta().getDisplayName().equals(Utils.format(Map1Items.getByName("I1").getItemStack().getItemMeta().getDisplayName()))) {
                    if (!player.getCanPickupItems()) {
                        player.getInventory().removeItem(Map1Items.getItem("I1", 1));
                        final Items item = rpgcore.getWygnaniecManager().getDrawnItems(player);
                        if (item == null) {
                            return;
                        }
                        final ItemStack is = item.getRewardItem();
                        is.setAmount(item.getAmount());
                        player.getInventory().addItem(is);
                        return;
                    }
                }
                // Skrzynia Najemnika
                if (playerItem.getItemMeta().getDisplayName().equals(Utils.format(Map1Items.getByName("I2").getItemStack().getItemMeta().getDisplayName()))) {
                    if (!player.getCanPickupItems()) {
                        player.getInventory().removeItem(Map1Items.getItem("I2", 1));
                        final Items item = rpgcore.getNajemnikManager().getDrawnItems(player);
                        if (item == null) {
                            return;
                        }
                        final ItemStack is = item.getRewardItem();
                        is.setAmount(item.getAmount());
                        player.getInventory().addItem(is);
                    }
                }
                // Expowisko 2
                // soon...

            }
        }
    }
}
