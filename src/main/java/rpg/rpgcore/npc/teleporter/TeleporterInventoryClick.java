package rpg.rpgcore.npc.teleporter;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.Random;
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
        final String clickedInventoryTitle = clickedInventory.getTitle();
        final ItemStack clickedItem = e.getCurrentItem();
        final int clickedSlot = e.getSlot();

        if (Utils.removeColor(clickedInventoryTitle).equals("TELEPORTER")) {
            e.setCancelled(true);
            rpgcore.getTeleporterNPC().openTeleporterEXPOWISKA(player);
        }
        /*if (Utils.removeColor(clickedInventoryTitle).equals("TELEPORTER - WYBOR")) {
            e.setCancelled(true);
            if (clickedSlot == 11) {
                rpgcore.getTeleporterNPC().openTeleporterEXPOWISKA(player);
            }
            if (clickedSlot == 15) {
                rpgcore.getTeleporterNPC().openTeleporterDODATKOWEMAPY(player);
            }
        }*/
        if (Utils.removeColor(clickedInventoryTitle).equals("TELEPORTER - EXPOWISKA")) {
            e.setCancelled(true);
            int randomtp = new Random().nextInt(3);
            if (clickedSlot == 0) {
                player.closeInventory();
                if (randomtp == 0) {
                    player.teleport(new Location(Bukkit.getWorld("1-10map"), -378, 74, -68, (float)-128.7, (float)2.1));
                }
                if (randomtp == 1) {
                    player.teleport(new Location(Bukkit.getWorld("1-10map"), -183, 72, -146, (float)-50.4, (float)4.2));
                }
                if (randomtp == 2) {
                    player.teleport(new Location(Bukkit.getWorld("1-10map"), -87, 70, -31, (float)-165.6, (float)0.4));
                }
            }
            if (rpgcore.getUserManager().find(uuid).getLvl() > 9) {
                if (clickedSlot == 1) {
                    player.closeInventory();
                    if (randomtp == 0) {
                        player.teleport(new Location(Bukkit.getWorld("10-20map"), 26, 75, 90, (float)-164.1, (float)-0.4));
                    }
                    if (randomtp == 1) {
                        player.teleport(new Location(Bukkit.getWorld("10-20map"), 149, 77, 193, (float)1.2, (float)0.5));
                    }
                    if (randomtp == 2) {
                        player.teleport(new Location(Bukkit.getWorld("10-20map"), 246, 75, 335, (float)81.7, (float)-0.3));
                    }
                }
            }
            if (rpgcore.getUserManager().find(uuid).getLvl() > 19) {
                if (clickedSlot == 2) {
                    player.closeInventory();
                    if (randomtp == 0) {
                        player.teleport(new Location(Bukkit.getWorld("20-30map"), 63, 75, 123, (float)-43.8, (float)-0.1));
                    }
                    if (randomtp == 1) {
                        player.teleport(new Location(Bukkit.getWorld("20-30map"), 188, 75, 78, (float)56.6, (float)0.2));
                    }
                    if (randomtp == 2) {
                        player.teleport(new Location(Bukkit.getWorld("20-30map"), 159, 75, 173, (float)164.0, (float)0.2));
                    }
                }
            }
            if (rpgcore.getUserManager().find(uuid).getLvl() > 29) {
                if (clickedSlot == 3) {
                    player.closeInventory();
                    player.teleport(new Location(Bukkit.getWorld("30-40exp"), 27, 78, -123, (float)-83.5, (float)-0.3));
                }
            }
            if (rpgcore.getUserManager().find(uuid).getLvl() > 39) {
                if (clickedSlot == 4) {
                    player.closeInventory();
                    if (randomtp == 0) {
                        player.teleport(new Location(Bukkit.getWorld("40-50map"), -90, 68, -489, (float)-0.2, (float)0.2));
                    }
                    if (randomtp == 1) {
                        player.teleport(new Location(Bukkit.getWorld("40-50map"), -90, 68, -27, (float)180.0, (float)0.0));
                    }
                }
            }
            if (rpgcore.getUserManager().find(uuid).getLvl() > 49) {
                if (clickedSlot == 5) {
                    player.closeInventory();
                    if (randomtp == 0) {
                        player.teleport(new Location(Bukkit.getWorld("50-60exp"), -37.472, 8, -49.002, (float) -110.7, (float) 0.1));
                    }
                    if (randomtp == 1) {
                        player.teleport(new Location(Bukkit.getWorld("50-60exp"), 87.667, 9, -91.197, (float) 21.8, (float) -0.1));
                    }
                }
            }
            if (rpgcore.getUserManager().find(uuid).getLvl() > 59) {
                if (clickedSlot == 6) {
                    player.closeInventory();
                    if (randomtp == 0) {
                        player.teleport(new Location(Bukkit.getWorld("60-70exp"), 19.449, 10, -28.7, (float) -28.7, (float) -0.3));
                    }
                    if (randomtp == 1) {
                        player.teleport(new Location(Bukkit.getWorld("60-70exp"), 94.487, 10, 56.263, (float) 15.0, (float) -0.3));
                    }
                    if (randomtp == 2) {
                        player.teleport(new Location(Bukkit.getWorld("60-70exp"), 104.301, 12, 228.545, (float) 78.0, (float) -0.3));
                    }
                }
            }
            if (rpgcore.getUserManager().find(uuid).getLvl() > 69) {
                if (clickedSlot == 7) {
                    player.closeInventory();
                    if (randomtp == 0) {
                        player.teleport(new Location(Bukkit.getWorld("70-80exp"), -30.311, 9, -116.057, (float) -52.1, (float) 1.7));
                    }
                    if (randomtp == 1) {
                        player.teleport(new Location(Bukkit.getWorld("70-80exp"), -6.604, 12, 67.549, (float) -139.8, (float) 0.1));
                    }
                    if (randomtp == 2) {
                        player.teleport(new Location(Bukkit.getWorld("70-80exp"), 67.894, 13, -103.741, (float) 46.0, (float) 1.9));
                    }
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
                    player.teleport(new Location(Bukkit.getWorld("demontower"), -258, 14, 107, (float) -82.9, (float) 0.4));
                }
            }
        }*/
    }
}
