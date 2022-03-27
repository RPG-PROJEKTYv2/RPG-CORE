package rpg.rpgcore.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.HashMap;

public class PlayerInventoryClickListener implements Listener {

    private final RPGCORE rpgcore;

    public PlayerInventoryClickListener(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClick(final InventoryClickEvent e) {
        final Player player = (Player) e.getWhoClicked();
        final HashMap<Integer, ItemStack> itemMapToRemove = new HashMap<>();
        //                      GUI OD BAO                  \\
        if (e.getClickedInventory().getName().equals(Utils.format("&6&lSTOL MAGII"))) {

            if (e.getSlot() == 16) {
                if (!(player.getInventory().containsAtLeast(rpgcore.getBaoManager().getItemDoLosowania(), 1))) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie posiadasz &3&lKamien Bao&c!"));
                    player.closeInventory();
                    player.getInventory().addItem(rpgcore.getBaoManager().getItemDoLosowania());
                    player.getInventory().addItem(rpgcore.getBaoManager().getItemDoZmianki());
                    return;
                }
                rpgcore.getBaoManager().losujNoweBonusy(player.getUniqueId());
                itemMapToRemove.put(0, rpgcore.getBaoManager().getItemDoLosowania());
                player.getInventory().removeItem(itemMapToRemove.get(0));
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie zmieniles swoje bonusy w &6Stole Magi"));
                player.closeInventory();


                e.setCancelled(true);
            }
            e.setCancelled(true);
            player.closeInventory();
        }
    }
}
