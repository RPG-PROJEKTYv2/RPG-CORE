package rpg.rpgcore.commands.player.rozpiska;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

public class RozpiskaInventoryClick implements Listener {
    private final RPGCORE rpgcore;

    public RozpiskaInventoryClick(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onCLick(final InventoryClickEvent e) {
        final Inventory gui = e.getClickedInventory();
        final Player player = (Player) e.getWhoClicked();

        if (e.getClickedInventory() == null || e.getInventory() == null) {
            return;
        }

        final String title = Utils.removeColor(gui.getTitle());
        final int slot = e.getSlot();

        if (title.equals("Rozpiska * (1-10)") || title.equals("Rozpiska * (10-20)") || title.equals("Rozpiska * (20-30)") ||
                title.equals("Rozpiska * (30-40)") || title.equals("Rozpiska * (40-50)") || title.equals("Rozpiska * (50-60)") ||
                title.equals("Rozpiska * (60-70)") || title.equals("Rozpiska * (70-80)") || title.equals("Rozpiska * (80-90)") ||
                title.equals("Rozpiska * (90-100)") || title.equals("Rozpiska * (100-110)") || title.equals("Rozpiska * (110-120)") ||
                title.equals("Rozpiska * (120-130)")){
            e.setCancelled(true);
            if (slot == 44) {
                rpgcore.getRozpiskaManager().openROZPISKAGUI(player);
                return;
            }
        }
        if (title.equals("Rozpiska - menu")) {
            e.setCancelled(true);
            if (slot == 11) {
                rpgcore.getRozpiskaManager().openFIRSTexp(player);
                return;
            }
            if (slot == 12) {
                //rpgcore.getRozpiskaManager().openSECONDexp(player);
                return;
            }
            if (slot == 13) {
                //rpgcore.getRozpiskaManager().openTHIRDexp(player);
                return;
            }
            return;
        }
    }
}
