package rpg.rpgcore.npc.kowal;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.GlobalItems;
import rpg.rpgcore.utils.Utils;

import java.util.HashMap;
import java.util.UUID;

public class KowalInventoryClick implements Listener {

    private final RPGCORE rpgcore;

    public KowalInventoryClick(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void kowalInventoryClick(final InventoryClickEvent e) {

        if (e.getClickedInventory() == null) {
            return;
        }

        final Inventory clickedInventory = e.getClickedInventory();
        final Player player = (Player) e.getWhoClicked();
        final UUID playerUUID = player.getUniqueId();

        final String clickedInventoryTitle = clickedInventory.getTitle();
        final ItemStack clickedItem = e.getCurrentItem();
        final int clickedSlot = e.getSlot();

        if (Utils.removeColor(clickedInventoryTitle).equals("Kowal")) {
            e.setCancelled(true);

            if (clickedItem.getType().equals(Material.ANVIL)) {
                if (rpgcore.getKowalNPC().hasAlreadyUpgraded(playerUUID)) {
                    player.sendMessage(Utils.format("&4&lKowal &8>> &7Przedmiot mozesz ulepszyc tylko raz na jedno &4DT"));
                    player.closeInventory();
                    return;
                }
                rpgcore.getKowalNPC().openKowalUlepszanieGui(player);
                return;
            }

            if (clickedItem.getType().equals(Material.REDSTONE_TORCH_ON)) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + " &cDalsze &4DT &7bedzie dostepne w pozniejszym terminie"));
                rpgcore.getKowalNPC().resetUpgradeList();
                return;
            }

            return;
        }

        if (clickedInventory.getType().equals(InventoryType.PLAYER) && Utils.removeColor(player.getOpenInventory().getTopInventory().getTitle()).equals("Kowal - Ulepszanie")) {
            e.setCancelled(true);
            final String clickedItemType = String.valueOf(clickedItem.getType());
            if (!(clickedItemType.contains("HELMET") || clickedItemType.contains("CHESTPLATE") || clickedItemType.contains("LEGGINGS") || clickedItemType.contains("BOOTS")
                    || clickedItemType.contains("SWORD") || clickedItemType.equals("BOOK")) || clickedItem.getItemMeta().getDisplayName() == null || clickedItem.getItemMeta().getLore() == null) {
                return;
            }
            final ItemStack itemToGui = clickedItem.clone();
            final String itemDisplayName = itemToGui.getItemMeta().getDisplayName();

            if (itemDisplayName.contains("+3")) {
                return;
            }

            if (itemToGui.getType().equals(Material.BOOK) && !player.getOpenInventory().getTopInventory().getItem(14).equals(rpgcore.getKowalNPC().getPlaceForZwoj())) {
                return;
            }

            if (itemToGui.getType().equals(Material.BOOK)) {
                player.getInventory().getItem(clickedSlot).setAmount(player.getInventory().getItem(clickedSlot).getAmount() - 1);
                itemToGui.setAmount(1);
                player.getOpenInventory().getTopInventory().setItem(14, itemToGui);
                rpgcore.getKowalNPC().addOstatniUlepszonyItem(playerUUID, itemToGui);
                return;
            }

            if (!player.getOpenInventory().getTopInventory().getItem(12).equals(rpgcore.getKowalNPC().getPlaceForItem())) {
                return;
            }

            player.getInventory().removeItem(clickedItem);
            player.getOpenInventory().getTopInventory().setItem(12, itemToGui);
            rpgcore.getKowalNPC().addOstatniUlepszonyItem(playerUUID, itemToGui);
            return;
        }

        if (Utils.removeColor(clickedInventoryTitle).equals("Kowal - Ulepszanie")) {
            e.setCancelled(true);

            if (clickedSlot == 12 && !clickedInventory.getItem(12).equals(rpgcore.getKowalNPC().getPlaceForItem())) {
                final ItemStack itemToGiveBack = clickedItem.clone();
                player.getInventory().addItem(itemToGiveBack);
                clickedInventory.setItem(12, rpgcore.getKowalNPC().getPlaceForItem());
                rpgcore.getKowalNPC().removeOstatniUlepszonyItem(playerUUID, itemToGiveBack);
                return;
            }

            if (clickedSlot == 14 && !clickedInventory.getItem(14).equals(rpgcore.getKowalNPC().getPlaceForZwoj())) {
                final ItemStack itemToGiveBack = clickedItem.clone();
                player.getInventory().addItem(itemToGiveBack);
                clickedInventory.setItem(14, rpgcore.getKowalNPC().getPlaceForZwoj());
                rpgcore.getKowalNPC().removeOstatniUlepszonyItem(playerUUID, itemToGiveBack);
                return;
            }

            if (clickedSlot == 22 && !clickedInventory.getItem(12).equals(rpgcore.getKowalNPC().getPlaceForItem())) {
                boolean hasZwoj = false;

                if (clickedInventory.getItem(14).equals(GlobalItems.getZwojBlogoslawienstwa(1))) {
                    hasZwoj = true;
                }
                rpgcore.getKowalNPC().upgradeItem(player, clickedInventory.getItem(12).clone(), hasZwoj);
                player.closeInventory();
                return;
            }
            return;
        }

    }
}
