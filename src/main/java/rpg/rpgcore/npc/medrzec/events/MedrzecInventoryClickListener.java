package rpg.rpgcore.npc.medrzec.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.GlobalItem;

import java.util.UUID;

public class MedrzecInventoryClickListener implements Listener {
    private final RPGCORE rpgcore;

    public MedrzecInventoryClickListener(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onClick(final InventoryClickEvent e){
        if (e.getInventory() == null || e.getClickedInventory() == null) return;

        final Inventory gui = e.getClickedInventory();
        final String title = Utils.removeColor(gui.getName());
        final Player player = (Player) e.getWhoClicked();
        final UUID uuid = player.getUniqueId();
        final int slot = e.getSlot();
        final ItemStack item = e.getCurrentItem();

        if (title.equals("Medrzec")) {
            e.setCancelled(true);

            if (item == null || item.getType() == Material.STAINED_GLASS_PANE) return;

            if (slot == 1) {
                final User user = rpgcore.getUserManager().find(uuid);
                if (!player.getInventory().containsAtLeast(GlobalItem.getItem("ZNISZCZONE_RUBINOWE_SERCE", 1), 4) || user.getKasa() < 1_000_000) {
                    player.sendMessage(Utils.format("&4&lMedrzec &8>> &cCzy ty probujesz mnie oszukac?!"));
                    player.closeInventory();
                    return;
                }
                player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("ZNISZCZONE_RUBINOWE_SERCE", 1).clone()).setAmount(4).toItemStack().clone());
                user.setKasa(user.getKasa() - 1_000_000);
                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataUser(uuid, user));
            }
        }
    }
}
