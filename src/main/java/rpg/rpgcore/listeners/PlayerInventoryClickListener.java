package rpg.rpgcore.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.Random;

public class PlayerInventoryClickListener implements Listener {

    private final RPGCORE rpgcore;

    public PlayerInventoryClickListener(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClick(final InventoryClickEvent e) {
        final Player player = (Player) e.getWhoClicked();
        //                      GUI OD BAO                  \\
        if (e.getClickedInventory().getName().equals(Utils.format("&6&lSTOL MAGII"))) {

            if (e.getSlot() == 16) {
                System.out.println(player.getInventory().contains(rpgcore.getBaoManager().getItemDoLosowania()));
                if (!(player.getInventory().contains(rpgcore.getBaoManager().getItemDoLosowania()))) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie posiadasz &3&lKamien Bao&c!"));
                    player.closeInventory();
                    player.getInventory().addItem(rpgcore.getBaoManager().getItemDoLosowania());
                    return;
                }
                rpgcore.getBaoManager().losujNoweBonusy(player.getUniqueId());
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie zmieniles swoje bonusy w &6Stole Magi"));
                player.closeInventory();


                e.setCancelled(true);
            }
            e.setCancelled(true);
            player.closeInventory();
        }
    }
}
