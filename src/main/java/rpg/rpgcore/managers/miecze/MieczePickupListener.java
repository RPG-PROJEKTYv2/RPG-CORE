package rpg.rpgcore.managers.miecze;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.GlobalItem;

public class MieczePickupListener implements Listener {

    private final RPGCORE rpgcore;

    public MieczePickupListener(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPickUp(final PlayerPickupItemEvent e) {
        if (e.getItem() == null) return;

        final Player player = e.getPlayer();
        final ItemStack item = e.getItem().getItemStack();

        if (item == null) return;

        if (item.getType() == Material.STONE) {
            player.getInventory().addItem(GlobalItem.getItem("SAKWA", 1));
        }

        if (item.getType() != Material.FLOWER_POT_ITEM || !item.hasItemMeta() || !item.getItemMeta().hasDisplayName() || !item.getItemMeta().getDisplayName().equals(Utils.format("&8✦&eSakiewka&8✦"))) return;

        for (int i = 0; i < item.getAmount(); i++) {
            MieczeManager.getRandomMiecz(player, rpgcore.getBonusesManager().find(player.getUniqueId()).getBonusesUser().getSzczescie());
        }
    }

}
