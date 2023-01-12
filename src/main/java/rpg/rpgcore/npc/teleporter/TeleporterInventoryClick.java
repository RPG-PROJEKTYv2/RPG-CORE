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


        if (Utils.removeColor(title).equals("TELEPORTER")) {
            e.setCancelled(true);
            if (slot == 11) {
                player.closeInventory();
                rpgcore.getTeleporterNPC().teleportExp1(player);
            }
            if (rpgcore.getUserManager().find(uuid).getLvl() > 9) {
                if (slot == 12) {
                    player.closeInventory();
                    rpgcore.getTeleporterNPC().teleportExp2(player);
                }
            }
            if (rpgcore.getUserManager().find(uuid).getLvl() > 19) {
                if (slot == 13) {
                    player.closeInventory();
                    rpgcore.getTeleporterNPC().teleportExp3(player);
                }
            }
            if (rpgcore.getUserManager().find(uuid).getLvl() > 29) {
                if (slot == 14) {
                    player.closeInventory();
                    rpgcore.getTeleporterNPC().teleportExp4(player);
                }
            }
            if (rpgcore.getUserManager().find(uuid).getLvl() > 39) {
                if (slot == 15) {
                    player.closeInventory();
                    rpgcore.getTeleporterNPC().teleportExp5(player);
                }
            }
            if (rpgcore.getUserManager().find(uuid).getLvl() > 49) {
                if (slot == 28) {
                    player.closeInventory();
                    rpgcore.getTeleporterNPC().teleportExp6(player);
                }
            }
            if (rpgcore.getUserManager().find(uuid).getLvl() > 59) {
                if (slot == 29) {
                    player.closeInventory();
                    rpgcore.getTeleporterNPC().teleportExp7(player);
                }
            }
            if (rpgcore.getUserManager().find(uuid).getLvl() > 69) {
                if (slot == 30) {
                    player.closeInventory();
                    rpgcore.getTeleporterNPC().teleportExp8(player);
                }
            }
            if (rpgcore.getUserManager().find(uuid).getLvl() > 79) {
                if (slot == 31) {
                    player.closeInventory();
                    rpgcore.getTeleporterNPC().teleportExp9(player);
                }
            }
            if (rpgcore.getUserManager().find(uuid).getLvl() > 89) {
                if (slot == 32) {
                    player.closeInventory();
                    rpgcore.getTeleporterNPC().teleportExp10(player);
                }
            }
            if (rpgcore.getUserManager().find(uuid).getLvl() > 99) {
                if (slot == 33) {
                    player.closeInventory();
                    rpgcore.getTeleporterNPC().teleportExp11(player);
                }
            }
            if (rpgcore.getUserManager().find(uuid).getLvl() > 109) {
                if (slot == 34) {
                    player.closeInventory();
                    player.sendMessage(Utils.format(Utils.SERVERNAME+ "&cTo expowisko zostalo zablokowane przez administracje."));
                    //rpgcore.getTeleporterNPC().teleportExp12(player);
                }
            }
            if (rpgcore.getUserManager().find(uuid).getLvl() > 119) {
                if (slot == 40) {
                    player.closeInventory();
                    player.sendMessage(Utils.format(Utils.SERVERNAME+ "&cTo expowisko zostalo zablokowane przez administracje."));
                    //rpgcore.getTeleporterNPC().teleportExp13(player);
                }
            }
        }
    }
}
