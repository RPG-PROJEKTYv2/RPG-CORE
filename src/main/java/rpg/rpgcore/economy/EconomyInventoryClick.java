package rpg.rpgcore.economy;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.HashMap;
import java.util.UUID;

public class EconomyInventoryClick implements Listener {

    final HashMap<Integer, ItemStack> itemMapToRemove = new HashMap<>();

    private final RPGCORE rpgcore;

    public EconomyInventoryClick(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void economyInventoryClick(final PlayerInteractEvent e) {

        final ItemStack eventItem = e.getItem();
        final Player player = e.getPlayer();
        final UUID uuid = player.getUniqueId();

        if (e.getAction() == Action.RIGHT_CLICK_AIR) {

            if (eventItem.getType().equals(Material.DOUBLE_PLANT) && eventItem.getItemMeta().getDisplayName().contains("Czek na ")) {
                final double kwotaZCzeku = Double.parseDouble(Utils.removeColor(eventItem.getItemMeta().getDisplayName()).replace("Czek na ", "").replaceAll(" ", "").replace("$", "").trim());

                itemMapToRemove.clear();
                itemMapToRemove.put(0, eventItem);
                player.getInventory().removeItem(itemMapToRemove.get(0));
                itemMapToRemove.clear();
                rpgcore.getPlayerManager().updatePlayerKasa(uuid, rpgcore.getPlayerManager().getPlayerKasa(uuid) + kwotaZCzeku);
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie zwiekszono stan twojego konta o &6" + Utils.spaceNumber(Utils.kasaFormat.format(kwotaZCzeku)) + " &2$"));
                return;
            }
            return;
        }

        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (e.getClickedBlock() == null) {
                return;
            }

            if (eventItem == null) {
                return;
            }


            if (eventItem.getType().equals(Material.DOUBLE_PLANT) && eventItem.getItemMeta().getDisplayName().contains("Czek na ")) {
                final double kwotaZCzeku = Double.parseDouble(Utils.removeColor(eventItem.getItemMeta().getDisplayName()).replace("Czek na ", "").replaceAll(" ", "").replace("$", "").trim());

                itemMapToRemove.clear();
                itemMapToRemove.put(0, eventItem);
                player.getInventory().removeItem(itemMapToRemove.get(0));
                itemMapToRemove.clear();
                rpgcore.getPlayerManager().updatePlayerKasa(uuid, rpgcore.getPlayerManager().getPlayerKasa(uuid) + kwotaZCzeku);
                player.sendMessage(Utils.format(Utils.SERVERNAME +  "&aPomyslnie zwiekszono stan twojego konta o &6" + Utils.spaceNumber(Utils.kasaFormat.format(kwotaZCzeku)) + " &2$"));
            }
        }

    }

}
