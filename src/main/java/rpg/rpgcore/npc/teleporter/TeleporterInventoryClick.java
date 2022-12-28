package rpg.rpgcore.npc.teleporter;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

public class TeleporterInventoryClick implements Listener {

    private final RPGCORE rpgcore;

    public TeleporterInventoryClick(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void teleporterInventoryClick(final InventoryClickEvent e) {

        if (e.getClickedInventory() == null) {
            return;
        }

        final Inventory clickedInventory = e.getClickedInventory();
        final Player player = (Player) e.getWhoClicked();
        final UUID uuid = player.getUniqueId();
        final String title = clickedInventory.getTitle();
        final int slot = e.getSlot();


        if (Utils.removeColor(title).equals("TELEPORTER - EXPOWISKA")) {
            e.setCancelled(true);
            if (slot == 0) {
                player.closeInventory();
                rpgcore.getTeleporterNPC().teleportExp1(player);
            }
            if (rpgcore.getUserManager().find(uuid).getLvl() > 9) {
                if (slot == 1) {
                    player.closeInventory();
                    rpgcore.getTeleporterNPC().teleportExp2(player);
                }
            }
            if (rpgcore.getUserManager().find(uuid).getLvl() > 19) {
                if (slot == 2) {
                    player.closeInventory();
                    rpgcore.getTeleporterNPC().teleportExp3(player);
                }
            }
            if (rpgcore.getUserManager().find(uuid).getLvl() > 29) {
                if (slot == 3) {
                    player.closeInventory();
                    rpgcore.getTeleporterNPC().teleportExp4(player);
                }
            }
            if (rpgcore.getUserManager().find(uuid).getLvl() > 39) {
                if (slot == 4) {
                    player.closeInventory();
                    rpgcore.getTeleporterNPC().teleportExp5(player);
                }
            }
            if (rpgcore.getUserManager().find(uuid).getLvl() > 49) {
                if (slot == 5) {
                    player.sendMessage(Utils.format("&CTo expowisko zostalo wylaczone przez Administratora!"));
                    player.closeInventory();
                    //rpgcore.getTeleporterNPC().teleportExp6(player);
                }
            }
            if (rpgcore.getUserManager().find(uuid).getLvl() > 59) {
                if (slot == 6) {
                    player.sendMessage(Utils.format("&CTo expowisko zostalo wylaczone przez Administratora!"));
                    player.closeInventory();
                    //rpgcore.getTeleporterNPC().teleportExp7(player);
                }
            }
            if (rpgcore.getUserManager().find(uuid).getLvl() > 69) {
                if (slot == 7) {
                    player.sendMessage(Utils.format("&CTo expowisko zostalo wylaczone przez Administratora!"));
                    player.closeInventory();
                    //rpgcore.getTeleporterNPC().teleportExp8(player);
                }
            }
        }
        /*if (Utils.removeColor(clickedInventoryTitle).equals("TELEPORTER - INNE")) {
            e.setCancelled(true);
            if (rpgcore.getUserManager().find(uuid).getLvl() > 59) {
                if (clickedSlot == 11) {
                    player.closeInventory();
                    player.teleport(new Location(Bukkit.getWorld("miasto1"), -258, 14, 107, (float) -82.9, (float) 0.4));
                }
            }
            if (rpgcore.getUserManager().find(uuid).getLvl() > 49) {
                if (clickedSlot == 15) {
                    player.closeInventory();
                    player.teleport(new Location(Bukkit.getWorld("icetower"), -258, 14, 107, (float) -82.9, (float) 0.4));
                }
            }
        }*/
    }
}
