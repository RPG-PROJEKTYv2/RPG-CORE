package rpg.rpgcore.npc.metinolog;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.GlobalItems.npc.MetinologItems;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

public class MetinologInventoryClick implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void metinologInventoryClick(final InventoryClickEvent e) {

        final Inventory clickedInventory = e.getClickedInventory();
        final Player player = (Player) e.getWhoClicked();
        final UUID playerUUID = player.getUniqueId();

        if (e.getClickedInventory() == null) {
            return;
        }

        final String title = clickedInventory.getTitle();
        final ItemStack item = e.getCurrentItem();

        if (Utils.removeColor(title).equals("Metinolog")) {
            e.setCancelled(true);

            if (item == null) {
                return;
            }

            final MetinologUser ms = RPGCORE.getInstance().getMetinologNPC().find(player.getUniqueId()).getMetinologUser();

            if (item.getType().equals(Material.PRISMARINE_SHARD)) {
                final String[] mission = RPGCORE.getInstance().getMetinologNPC().getGiveMissions().get(ms.getPostepGive() + 1).split(";");
                if (ms.getPostepMisjiGive() >= Integer.parseInt(mission[1])) {
                    ms.setPostepMisjiGive(0);
                    ms.setValue1(ms.getValue1() + Double.parseDouble(mission[4]));
                    ms.setValue2(ms.getValue2() + Double.parseDouble(mission[5]));
                    ms.setPostepGive(ms.getPostepGive() + 1);
                    RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataMetinolog(playerUUID, RPGCORE.getInstance().getMetinologNPC().find(playerUUID)));
                    RPGCORE.getInstance().getServer().broadcastMessage(Utils.format("&b&lMetinolog &8>> &f" + player.getName() + " &bukonczyl moja &f" + ms.getPostepGive() + " &bmisje (&fODDAJ&b)"));
                    player.closeInventory();
                    return;
                }

                if (!player.getInventory().containsAtLeast(MetinologItems.getItem("I" + mission[0], 1), 1)) {
                    return;
                }

                player.getInventory().removeItem(MetinologItems.getItem("I" + mission[0], 1));
                ms.setPostepMisjiGive(ms.getPostepMisjiGive() + 1);
                RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataMetinolog(playerUUID, RPGCORE.getInstance().getMetinologNPC().find(playerUUID)));
                player.closeInventory();
                RPGCORE.getInstance().getMetinologNPC().openMetinologGUI(player);
                return;
            }

            if (item.getType().equals(Material.DIAMOND_SWORD)) {
                final String[] mission = RPGCORE.getInstance().getMetinologNPC().getKillMissions().get(ms.getPostepKill() + 1).split(";");

                if (ms.getPostepMisjiKill() >= Integer.parseInt(mission[1])) {
                    ms.setPostepMisjiKill(0);
                    ms.setValue3(ms.getValue3() + Double.parseDouble(mission[2]));
                    ms.setValue4(ms.getValue4() + Double.parseDouble(mission[3]));
                    ms.setPostepKill(ms.getPostepKill() + 1);
                    RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataMetinolog(playerUUID, RPGCORE.getInstance().getMetinologNPC().find(playerUUID)));
                    RPGCORE.getInstance().getServer().broadcastMessage(Utils.format("&b&lMetinolog &8>> &f" + player.getName() + " &bukonczyl moja &f" + ms.getPostepKill() + " &bmisje (&fZNISZCZ&b)"));
                    player.closeInventory();
                }
            }

        }
    }
}
