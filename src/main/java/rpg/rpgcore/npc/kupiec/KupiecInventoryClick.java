package rpg.rpgcore.npc.kupiec;

import com.google.common.collect.Multimap;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.npc.kupiec.enums.KupiecItems;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

public class KupiecInventoryClick implements Listener {

    private final RPGCORE rpgcore;

    public KupiecInventoryClick(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void kupiecInventoryClick(final InventoryClickEvent e) {

        if (e.getClickedInventory() == null) {
            return;
        }

        final Inventory clickedInventory = e.getClickedInventory();
        final Player player = (Player) e.getWhoClicked();
        final UUID playerUUID = player.getUniqueId();
        final String clickedInventoryTitle = clickedInventory.getTitle();
        final ItemStack clickedItem = e.getCurrentItem();
        final int clickedSlot = e.getSlot();

        if (Utils.removeColor(player.getOpenInventory().getTopInventory().getTitle()).equals("Kupiec")) {
            if (e.getClick().isShiftClick() || e.getClick().isKeyboardClick() || e.getClick().isCreativeAction()) {
                e.setCancelled(true);
                return;
            }
        }

        if (Utils.removeColor(clickedInventoryTitle).equals("Kupiec")) {
            final Multimap<ItemStack, Double> playerItems = rpgcore.getKupiecNPC().getPlayerItemStackList(playerUUID);
            if (clickedItem.getType().equals(Material.STAINED_GLASS_PANE) || clickedItem.getType().equals(Material.PAPER)) {
                e.setCancelled(true);
                return;
            }

            if (e.getClick().isShiftClick() || e.getClick().isKeyboardClick() || e.getClick().isCreativeAction()) {
                e.setCancelled(true);
                return;
            }

            if (clickedSlot == 49) {
                e.setCancelled(true);

                if (playerItems.isEmpty()) {
                    return;
                }

                if (!e.getCursor().getType().equals(Material.AIR)) {
                    return;
                }

                final double moneyToAdd = rpgcore.getKupiecNPC().getPlayerSellValueItems(playerUUID);
                rpgcore.getUserManager().find(playerUUID).setKasa(rpgcore.getUserManager().find(playerUUID).getKasa() + moneyToAdd);
                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataUser(playerUUID, rpgcore.getUserManager().find(playerUUID)));

                for (ItemStack is : playerItems.keys()) {
                    clickedInventory.remove(is);
                }
                rpgcore.getKupiecNPC().resetPlayerSellValueItems(playerUUID);
                rpgcore.getKupiecNPC().resetPlayerItemStack(playerUUID);
                rpgcore.getServer().getScheduler().runTaskLater(rpgcore, player::closeInventory, 1L);
                rpgcore.getKupiecNPC().addMoneyEarnedPerDay(moneyToAdd);
                player.sendMessage(Utils.format(Utils.KUPIEC + "Pomyslnie sprzedano wszystkie przedmioty za &6" + Utils.spaceNumber(String.format("%.2f", moneyToAdd)) + " &2$"));
                return;
            }

            if (e.getCursor().getType().equals(Material.AIR)) {
                if (clickedItem.getType().equals(Material.AIR)) {
                    return;
                }
                double itemPrice = KupiecItems.getItemPrice(Utils.removeColor(clickedItem.getItemMeta().getDisplayName())) * clickedItem.getAmount();
                final String type = clickedItem.getType().toString();
                if (type.contains("_HELMET") || type.contains("_CHESTPLATE") || type.contains("_LEGGINGS") || type.contains("_BOOTS")) {
                    itemPrice += getProtMoney(clickedItem);
                    itemPrice += getThornsMoney(clickedItem);
                } else if (type.contains("_SWORD")) {
                    itemPrice += getMobyMoney(clickedItem);
                    itemPrice += getSharpnessMoney(clickedItem);
                }
                rpgcore.getKupiecNPC().removePlayerItemStack(playerUUID, clickedItem, itemPrice);
                rpgcore.getKupiecNPC().removePlayerSellValueItems(playerUUID, itemPrice);
                clickedInventory.setItem(49, rpgcore.getKupiecNPC().getSellItem(rpgcore.getKupiecNPC().getPlayerSellValueItems(playerUUID)));
            } else {
                double itemPrice = KupiecItems.getItemPrice(Utils.removeColor(e.getCursor().getItemMeta().getDisplayName())) * e.getCursor().getAmount();
                final String type = e.getCursor().getType().toString();
                if (type.contains("_HELMET") || type.contains("_CHESTPLATE") || type.contains("_LEGGINGS") || type.contains("_BOOTS")) {
                    itemPrice += getProtMoney(e.getCursor());
                    itemPrice += getThornsMoney(e.getCursor());
                } else if (type.contains("_SWORD")) {
                    itemPrice += getMobyMoney(e.getCursor());
                    itemPrice += getSharpnessMoney(e.getCursor());
                }
                rpgcore.getKupiecNPC().addPlayerItemStack(playerUUID, e.getCursor().clone(), itemPrice);
                rpgcore.getKupiecNPC().addPlayerSellValueItems(playerUUID, itemPrice);

                clickedInventory.setItem(49, rpgcore.getKupiecNPC().getSellItem(rpgcore.getKupiecNPC().getPlayerSellValueItems(playerUUID)));
            }
        }
    }

    private double getSharpnessMoney(final ItemStack item) {
        final int sharp = Utils.getTagInt(item, "dmg");
        int mnoznik = 0;
        if (sharp < 20) {
            mnoznik = 50;
        }
        if (sharp < 60) {
            mnoznik = 100;
        }
        if (sharp < 250) {
            mnoznik = 200;
        }
        if (sharp < 500) {
            mnoznik = 300;
        }
        if (sharp < 1000) {
            mnoznik = 400;
        }
        if (sharp < 2000) {
            mnoznik = 500;
        }
        if (sharp <= 2500) {
            mnoznik = 600;
        }
        return sharp * mnoznik;
    }
    private double getMobyMoney(final ItemStack item) {
        final int moby = Utils.getTagInt(item, "moby");
        int mnoznik = 0;
        if (moby < 20) {
            mnoznik = 50;
        }
        if (moby < 60) {
            mnoznik = 100;
        }
        if (moby < 250) {
            mnoznik = 200;
        }
        if (moby < 500) {
            mnoznik = 300;
        }
        if (moby < 1000) {
            mnoznik = 400;
        }
        if (moby < 2000) {
            mnoznik = 500;
        }
        if (moby <= 2500) {
            mnoznik = 600;
        }
        return moby * mnoznik;
    }
    private double getProtMoney(final ItemStack item) {
        final int prot = Utils.getTagInt(item, "prot");
        int mnoznik = 0;
        if (prot < 20) {
            mnoznik = 50;
        }
        if (prot < 50) {
            mnoznik = 75;
        }
        if (prot < 60) {
            mnoznik = 100;
        }
        if (prot < 80) {
            mnoznik = 125;
        }
        if (prot < 100) {
            mnoznik = 150;
        }
        if (prot < 120) {
            mnoznik = 175;
        }
        if (prot < 140) {
            mnoznik = 200;
        }
        if (prot < 160) {
            mnoznik = 225;
        }
        if (prot < 180) {
            mnoznik = 250;
        }
        if (prot < 200) {
            mnoznik = 275;
        }
        if (prot < 220) {
            mnoznik = 300;
        }
        if (prot < 240) {
            mnoznik = 325;
        }
        if (prot <= 250) {
            mnoznik = 350;
        }
        return prot * mnoznik;
    }
    private double getThornsMoney(final ItemStack item) {
        final double thorns = Utils.getTagDouble(item, "thorns");
        int mnoznik = 0;
        if (thorns < 10) {
            mnoznik = 50;
        }
        if (thorns < 20) {
            mnoznik = 100;
        }
        if (thorns < 30) {
            mnoznik = 150;
        }
        if (thorns < 40) {
            mnoznik = 200;
        }
        if (thorns <= 50) {
            mnoznik = 250;
        }
        return thorns * mnoznik;
    }
}
