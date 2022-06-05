package rpg.rpgcore.bao;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.HashMap;
import java.util.UUID;

public class BAOInventoryClick implements Listener {

    private final RPGCORE rpgcore;

    public BAOInventoryClick(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void baoInventoryClickEvent(final InventoryClickEvent e) {
        final Inventory clickedInventory = e.getClickedInventory();
        final Player player = (Player) e.getWhoClicked();
        final UUID playerUUID = player.getUniqueId();
        final HashMap<Integer, ItemStack> itemMapToRemove = new HashMap<>();

        if (e.getClickedInventory() == null) {
            return;
        }

        final String clickedInventoryTitle = clickedInventory.getTitle();
        final ItemStack clickedItem = e.getCurrentItem();
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
                itemMapToRemove.put(0, rpgcore.getBaoManager().getItemDoLosowania());
                player.getInventory().removeItem(itemMapToRemove.get(0));
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie zmieniles swoje bonusy w &6Stole Magi"));
                player.closeInventory();


                e.setCancelled(true);
            }
            e.setCancelled(true);
            player.closeInventory();
        }

        if (clickedInventoryTitle.equalsIgnoreCase(rpgcore.getBaoManager().ksiegaMagiiGUI(playerUUID).getName())) {
            e.setCancelled(true);
            if (clickedSlot == 11) {
                rpgcore.getBaoManager().losujNowyBonus1(playerUUID);
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&6Twoj nowy bonus to: &c" + rpgcore.getBaoManager().getBaoBonusy(playerUUID).split(",")[clickedSlot - 11] + " " + rpgcore.getBaoManager().getBaoBonusyWartosci(playerUUID).split(",")[clickedSlot - 11]) + " %");
            }
            if (clickedSlot == 12) {
                rpgcore.getBaoManager().losujNowyBonus2(playerUUID);
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&6Twoj nowy bonus to: &c" + rpgcore.getBaoManager().getBaoBonusy(playerUUID).split(",")[clickedSlot - 11] + " " + rpgcore.getBaoManager().getBaoBonusyWartosci(playerUUID).split(",")[clickedSlot - 11]) + " %");
            }
            if (clickedSlot == 13) {
                rpgcore.getBaoManager().losujNowyBonus3(playerUUID);
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&6Twoj nowy bonus to: &c" + rpgcore.getBaoManager().getBaoBonusy(playerUUID).split(",")[clickedSlot - 11] + " " + rpgcore.getBaoManager().getBaoBonusyWartosci(playerUUID).split(",")[clickedSlot - 11]) + " %");
            }
            if (clickedSlot == 14) {
                rpgcore.getBaoManager().losujNowyBonus4(playerUUID);
                if (rpgcore.getBaoManager().getBaoBonusy(playerUUID).split(",")[3].equalsIgnoreCase("dodatkowe obrazenia")) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&6Twoj nowy bonus to: &c" + rpgcore.getBaoManager().getBaoBonusy(playerUUID).split(",")[clickedSlot - 11] + " " + rpgcore.getBaoManager().getBaoBonusyWartosci(playerUUID).split(",")[clickedSlot - 11]) + " DMG");
                } else {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&6Twoj nowy bonus to: &c" + rpgcore.getBaoManager().getBaoBonusy(playerUUID).split(",")[clickedSlot - 11] + " " + rpgcore.getBaoManager().getBaoBonusyWartosci(playerUUID).split(",")[clickedSlot - 11]) + " %");
                }
            }
            if (clickedSlot == 15) {
                rpgcore.getBaoManager().losujNowyBonus5(playerUUID);
                if (rpgcore.getBaoManager().getBaoBonusy(playerUUID).split(",")[4].equalsIgnoreCase("dodatkowe hp")) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&6Twoj nowy bonus to: &c" + rpgcore.getBaoManager().getBaoBonusy(playerUUID).split(",")[clickedSlot - 11] + " " + rpgcore.getBaoManager().getBaoBonusyWartosci(playerUUID).split(",")[clickedSlot - 11]) + " HP");
                } else {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&6Twoj nowy bonus to: &c" + rpgcore.getBaoManager().getBaoBonusy(playerUUID).split(",")[clickedSlot - 11] + " " + rpgcore.getBaoManager().getBaoBonusyWartosci(playerUUID).split(",")[clickedSlot - 11]) + " %");
                }
            }
            player.closeInventory();
        }
    }

}
