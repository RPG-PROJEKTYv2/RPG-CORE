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
import rpg.rpgcore.utils.GlobalItem;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

public class DropFromChestsListener implements Listener {

    private final RPGCORE rpgcore;

    public DropFromChestsListener(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onRightClick(final PlayerInteractEvent e) {
        final Player player = e.getPlayer();
        final UUID uuid = player.getUniqueId();

        if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
            return;
        }

        if (player.getItemInHand().getType().equals(Material.CHEST)) {
            if (player.getItemInHand().getItemMeta().getDisplayName() == null) {
                player.getInventory().addItem(GlobalItem.getItem("I22", 1));
                return;
            }

            final ItemStack playerItem = player.getItemInHand();

            if (playerItem.getItemMeta().getDisplayName().equals(Utils.format(GlobalItem.getByName("I22").getItemStack().getItemMeta().getDisplayName()))) {
                if (!player.getCanPickupItems()) {
                    player.getInventory().removeItem(GlobalItem.getItem("I22", 1));
                    final Items item = rpgcore.getRoznosciManager().getDrawnItems(player);
                    // TODO ZROBIC OS ZA SKRZYNKI XD
                    if (item == null) {
                        return;
                    }
                    final ItemStack is = item.getRewardItem();
                    is.setAmount(item.getAmount());
                    player.getInventory().addItem(is);
                    return;
                }
            }

            // I TU DAJESZ KOLEJNE IF'Y
        }
    }
}
