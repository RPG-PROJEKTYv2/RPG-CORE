package rpg.rpgcore.bao.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.bao.BaoUser;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

public class BAOInventoryClick implements Listener {

    private final RPGCORE rpgcore;

    public BAOInventoryClick(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void baoInventoryClickEvent(final InventoryClickEvent e) {
        final Inventory clickedInventory = e.getInventory();
        final Player player = (Player) e.getWhoClicked();
        final UUID playerUUID = player.getUniqueId();

        if (e.getClickedInventory() == null || e.getInventory() == null) {
            return;
        }

        final String clickedInventoryTitle = clickedInventory.getTitle();
        final int clickedSlot = e.getSlot();

        if (clickedInventoryTitle.equals(Utils.format("&6&lSTOL MAGII"))) {

            if (clickedSlot == 16) {
                if (!(player.getInventory().containsAtLeast(rpgcore.getBaoManager().getItemDoLosowania(), 1))) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie posiadasz &3&lKamien Bao&c!"));
                    player.closeInventory();
                    player.getInventory().addItem(rpgcore.getBaoManager().getItemDoLosowania());
                    player.getInventory().addItem(rpgcore.getBaoManager().getItemDoZmianki());
                    return;
                }
                rpgcore.getBaoManager().losujNoweBonusy(playerUUID);
                player.getInventory().removeItem(rpgcore.getBaoManager().getItemDoLosowania());
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie zmieniles swoje bonusy w &6Stole Magi"));
                player.closeInventory();


                e.setCancelled(true);
            }
            e.setCancelled(true);
            player.closeInventory();
        }

        if (Utils.removeColor(clickedInventoryTitle).equalsIgnoreCase("ksiega magii")) {
            e.setCancelled(true);
            final BaoUser user = rpgcore.getBaoManager().find(playerUUID).getBaoUser();
            if (clickedSlot == 11) {
                rpgcore.getBaoManager().losujNowyBonus1(playerUUID);
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&6Twoj nowy bonus to: &c" + user.getBonus1() + " " + user.getValue1() + " %"));
            }
            if (clickedSlot == 12) {
                rpgcore.getBaoManager().losujNowyBonus2(playerUUID);
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&6Twoj nowy bonus to: &c" + user.getBonus2() + " " + user.getValue2() + " %"));
            }
            if (clickedSlot == 13) {
                rpgcore.getBaoManager().losujNowyBonus3(playerUUID);
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&6Twoj nowy bonus to: &c" + user.getBonus3() + " " + user.getValue3() + " %"));
            }
            if (clickedSlot == 14) {
                rpgcore.getBaoManager().losujNowyBonus4(playerUUID);
                if (user.getBonus4().equalsIgnoreCase("dodatkowe obrazenia")) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&6Twoj nowy bonus to: &c" + user.getBonus4() + " " + user.getValue4() + " DMG"));
                } else {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&6Twoj nowy bonus to: &c" + user.getBonus4() + " " + user.getValue4() + " %"));
                }
            }
            if (clickedSlot == 15) {
                rpgcore.getBaoManager().losujNowyBonus5(playerUUID);
                if (user.getBonus5().equalsIgnoreCase("dodatkowe hp")) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&6Twoj nowy bonus to: &c" + user.getBonus5() + " " + user.getValue5() + " HP"));
                } else {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&6Twoj nowy bonus to: &c" + user.getBonus5() + " " + user.getValue5() + " %"));
                }
            }
            player.closeInventory();
        }
    }

}
