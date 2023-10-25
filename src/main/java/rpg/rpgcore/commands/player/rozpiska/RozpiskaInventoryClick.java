package rpg.rpgcore.commands.player.rozpiska;

import org.bukkit.Material;
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

public class RozpiskaInventoryClick implements Listener {
    private final RPGCORE rpgcore;

    public RozpiskaInventoryClick(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onCLick(final InventoryClickEvent e) {
        final Inventory gui = e.getClickedInventory();
        final Player player = (Player) e.getWhoClicked();
        final UUID uuid = player.getUniqueId();
        final ItemStack item = e.getCurrentItem();

        if (e.getClickedInventory() == null || e.getInventory() == null) {
            return;
        }

        final String title = Utils.removeColor(gui.getTitle());
        final int slot = e.getSlot();

        if (title.equals("Expowisko * (1-10)") || title.equals("Expowisko * (10-20)") || title.equals("Expowisko * (20-30)") ||
                title.equals("Expowisko * (30-40)") || title.equals("Expowisko * (40-50)") || title.equals("Expowisko * (50-60)") ||
                title.equals("Expowisko * (60-70)") || title.equals("Expowisko * (70-80)") || title.equals("Expowisko * (80-90)") ||
                title.equals("Expowisko * (90-100)") || title.equals("Expowisko * (100-110)") || title.equals("Expowisko * (110-120)") ||
                title.equals("Expowisko * (120-130)")) {
            e.setCancelled(true);
            if (slot == 44) {
                rpgcore.getRozpiskaManager().openROZPISKAGUI(player);
            }
        }
        if (title.equals("Rozpiska - menu")) {
            e.setCancelled(true);
            if (slot == 11) {
                rpgcore.getRozpiskaManager().openFIRSTexp(player);
                return;
            }
            if (slot == 12) {
                if (rpgcore.getUserManager().find(uuid).getLvl() > 9) {
                    rpgcore.getRozpiskaManager().openSECONDexp(player);
                    return;
                } else {
                    player.closeInventory();
                    player.sendMessage(Utils.format("&cTwoj poziom jest zbyt niski."));
                }
                return;
            }
            if (slot == 13) {
                if (rpgcore.getUserManager().find(uuid).getLvl() > 19) {
                    rpgcore.getRozpiskaManager().openTHIRDexp(player);
                    return;
                } else {
                    player.closeInventory();
                    player.sendMessage(Utils.format("&cTwoj poziom jest zbyt niski."));
                }
                return;
            }
            if (slot == 14) {
                if (rpgcore.getUserManager().find(uuid).getLvl() > 29) {
                    rpgcore.getRozpiskaManager().openFOURTHexp(player);
                    return;
                } else {
                    player.closeInventory();
                    player.sendMessage(Utils.format("&cTwoj poziom jest zbyt niski."));
                }
                return;
            }
            if (slot == 15) {
                if (rpgcore.getUserManager().find(uuid).getLvl() > 39) {
                    rpgcore.getRozpiskaManager().openFIFHTexp(player);
                    return;
                } else {
                    player.closeInventory();
                    player.sendMessage(Utils.format("&cTwoj poziom jest zbyt niski."));
                }
                return;
            }
            if (slot == 28) {
                if (rpgcore.getUserManager().find(uuid).getLvl() > 49) {
                    rpgcore.getRozpiskaManager().openSIXTHexp(player);
                    return;
                } else {
                    player.closeInventory();
                    player.sendMessage(Utils.format("&cTwoj poziom jest zbyt niski."));
                }
                return;
            }
            if (slot == 29) {
                if (rpgcore.getUserManager().find(uuid).getLvl() > 59) {
                    rpgcore.getRozpiskaManager().openSEVENTHexp(player);
                    return;
                } else {
                    player.closeInventory();
                    player.sendMessage(Utils.format("&cTwoj poziom jest zbyt niski."));
                }
                return;
            }
            if (slot == 30) {
                if (rpgcore.getUserManager().find(uuid).getLvl() > 69) {
                    rpgcore.getRozpiskaManager().openEIGHTHexp(player);
                    return;
                } else {
                    player.closeInventory();
                    player.sendMessage(Utils.format("&cTwoj poziom jest zbyt niski."));
                }
                return;
            }
            if (slot == 31) {
                if (rpgcore.getUserManager().find(uuid).getLvl() > 79) {
                    rpgcore.getRozpiskaManager().openNINTHexp(player);
                    return;
                } else {
                    player.closeInventory();
                    player.sendMessage(Utils.format("&cTwoj poziom jest zbyt niski."));
                }
                return;
            }
            if (slot == 32) {
                if (rpgcore.getUserManager().find(uuid).getLvl() > 89) {
                    rpgcore.getRozpiskaManager().openTENTHexp(player);
                    return;
                } else {
                    player.closeInventory();
                    player.sendMessage(Utils.format("&cTwoj poziom jest zbyt niski."));
                }
                return;
            }
            if (slot == 33) {
                if (rpgcore.getUserManager().find(uuid).getLvl() > 99) {
                    rpgcore.getRozpiskaManager().openELEVENTHexp(player);
                    return;
                } else {
                    player.closeInventory();
                    player.sendMessage(Utils.format("&cTwoj poziom jest zbyt niski."));
                }
                return;
            }
            if (slot == 34) {
                if (rpgcore.getUserManager().find(uuid).getLvl() > 109) {
                    rpgcore.getRozpiskaManager().openTWELVTHexp(player);
                    return;
                } else {
                    player.closeInventory();
                    player.sendMessage(Utils.format("&cTwoj poziom jest zbyt niski."));
                }
                return;
            }
            /*if (slot == 40) {
                if (rpgcore.getUserManager().find(uuid).getLvl() > 119) {
                }
                return;
            }*/

        }
    }
}
