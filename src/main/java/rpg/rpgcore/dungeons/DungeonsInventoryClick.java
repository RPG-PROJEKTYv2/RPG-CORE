package rpg.rpgcore.dungeons;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

public class DungeonsInventoryClick implements Listener {

    private final RPGCORE rpgcore;

    public DungeonsInventoryClick(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClick(final InventoryClickEvent e) {

        if (e.getInventory() == null || e.getClickedInventory() == null) {
            return;
        }

        final Inventory clickedInventory = e.getClickedInventory();
        final Player player = (Player) e.getWhoClicked();
        final UUID uuid = player.getUniqueId();
        final String title = Utils.removeColor(clickedInventory.getTitle());
        final int slot = e.getSlot();

        if (title.equalsIgnoreCase("Dungeony")) {
            e.setCancelled(true);
            if (slot == 0) {
                if (!rpgcore.getPartyManager().hasParty(uuid)) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&cMusisz posiadac swoje party zeby zaczac ten dungeon!"));
                    return;
                }
                //TODO Zrobic sprawdzanie 4 osob, klucza, lvli itd.
                if (!rpgcore.getPartyManager().findPartyByMember(uuid).getLeaderUUID().equals(uuid)) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&cMusisz byc liderem party zeby zaczac ten dungeon!"));
                    return;
                }
                rpgcore.getZamekNieskonczonosciManager().preDungeon(rpgcore.getPartyManager().find(uuid));
            }
        }
    }
}
