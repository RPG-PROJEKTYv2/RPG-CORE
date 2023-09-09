package rpg.rpgcore.npc.kowal.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.globalitems.GlobalItem;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

public class KowalInventoryClickListener implements Listener {

    private final RPGCORE rpgcore;

    public KowalInventoryClickListener(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void kowalInventoryClick(final InventoryClickEvent e) {

        if (e.getClickedInventory() == null) {
            return;
        }

        final Inventory inventory = e.getClickedInventory();
        final Player player = (Player) e.getWhoClicked();
        final UUID uuid = player.getUniqueId();

        final String title = Utils.removeColor(inventory.getTitle());
        final ItemStack item = e.getCurrentItem();
        final int slot = e.getSlot();

        if (title.equals("Kowal")) {
            e.setCancelled(true);

            if (item.getType().equals(Material.ANVIL)) {
                if (rpgcore.getKowalNPC().hasAlreadyUpgraded(uuid)) {
                    player.sendMessage(Utils.format("&4&lKowal &8>> &7Przedmiot mozesz ulepszyc tylko raz!"));
                    player.closeInventory();
                    return;
                }
                rpgcore.getKowalNPC().openKowalUlepszanieGui(player);
                return;
            }

            if (item.getType().equals(Material.REDSTONE_TORCH_ON)) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + " &cDalsze etapy &b&lIce Tower &cbeda dostepne w pozniejszym terminie"));
                return;
            }

            return;
        }

        if (inventory.getType().equals(InventoryType.PLAYER) && Utils.removeColor(player.getOpenInventory().getTopInventory().getTitle()).equals("Kowal - Ulepszanie")) {
            e.setCancelled(true);
            final String clickedItemType = String.valueOf(item.getType());
            if (!(clickedItemType.contains("_HELMET") || clickedItemType.contains("_CHESTPLATE") || clickedItemType.contains("_LEGGINGS") || clickedItemType.contains("_BOOTS")
                    || clickedItemType.contains("_SWORD") || (item.getType().equals(Material.IRON_INGOT) && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().contains("Stal Kowalska"))
                    || (item.getType().equals(Material.BOOK) && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().contains("Podrecznik Kowala"))
                    || item.getItemMeta().getDisplayName() == null || item.getItemMeta().getLore() == null)) {
                return;
            }
            final ItemStack itemToGui = item.clone();
            String itemDisplayName = "";
            if (!itemToGui.getItemMeta().hasDisplayName()) {
                itemDisplayName = itemToGui.getType().name();
            } else {
                itemDisplayName = itemToGui.getItemMeta().getDisplayName();
            }

            if (itemDisplayName.contains("+3")) {
                return;
            }

            if (((itemToGui.getType().equals(Material.BOOK) && itemToGui.getItemMeta().hasDisplayName() && itemToGui.getItemMeta().getDisplayName().contains("Podrecznik Kowala")) ||
                    (itemToGui.getType().equals(Material.IRON_INGOT) && itemToGui.getItemMeta().hasDisplayName() && itemToGui.getItemMeta().getDisplayName().contains("Stal Kowalska")))
                    && !player.getOpenInventory().getTopInventory().getItem(14).equals(rpgcore.getKowalNPC().getPlaceForZwoj())) {
                return;
            }

            if ((itemToGui.getType().equals(Material.BOOK) && itemToGui.getItemMeta().hasDisplayName() && itemToGui.getItemMeta().getDisplayName().contains("Podrecznik Kowala")) ||
                    (itemToGui.getType().equals(Material.IRON_INGOT) && itemToGui.getItemMeta().hasDisplayName() && itemToGui.getItemMeta().getDisplayName().contains("Stal Kowalska"))) {
                player.getInventory().removeItem(new ItemBuilder(itemToGui.clone()).setAmount(1).toItemStack());
                itemToGui.setAmount(1);
                player.getOpenInventory().getTopInventory().setItem(14, itemToGui);
                rpgcore.getKowalNPC().addOstatniUlepszonyItem(uuid, itemToGui);
                return;
            }

            if (!player.getOpenInventory().getTopInventory().getItem(12).equals(rpgcore.getKowalNPC().getPlaceForItem())) {
                return;
            }

            player.getInventory().removeItem(item);
            player.getOpenInventory().getTopInventory().setItem(12, itemToGui);
            rpgcore.getKowalNPC().addOstatniUlepszonyItem(uuid, itemToGui);
            return;
        }

        if (title.equals("Kowal - Ulepszanie")) {
            e.setCancelled(true);

            if (slot == 12 && !inventory.getItem(12).equals(rpgcore.getKowalNPC().getPlaceForItem())) {
                final ItemStack itemToGiveBack = item.clone();
                player.getInventory().addItem(itemToGiveBack);
                inventory.setItem(12, rpgcore.getKowalNPC().getPlaceForItem());
                rpgcore.getKowalNPC().removeOstatniUlepszonyItem(uuid, itemToGiveBack);
                return;
            }

            if (slot == 14 && !inventory.getItem(14).equals(rpgcore.getKowalNPC().getPlaceForZwoj())) {
                final ItemStack itemToGiveBack = item.clone();
                player.getInventory().addItem(itemToGiveBack);
                inventory.setItem(14, rpgcore.getKowalNPC().getPlaceForZwoj());
                rpgcore.getKowalNPC().removeOstatniUlepszonyItem(uuid, itemToGiveBack);
                return;
            }

            if (slot == 22 && !inventory.getItem(12).equals(rpgcore.getKowalNPC().getPlaceForItem())) {
                int zwoj = 0;
                if (inventory.getItem(14).equals(GlobalItem.getItem("I_METAL", 1))) {
                    zwoj = 2;
                } else if (inventory.getItem(14).equals(GlobalItem.getItem("I10", 1))) {
                    zwoj = 1;
                }

                rpgcore.getKowalNPC().upgradeItem(player, inventory.getItem(12).clone(), zwoj);
                player.closeInventory();
            }
        }

        if (inventory.getType().equals(InventoryType.PLAYER) && Utils.removeColor(player.getOpenInventory().getTopInventory().getTitle()).equals("Oczyszczanie Przedmiotu")) {
            e.setCancelled(true);
            return;
        }

        if (title.equals("Oczyszczanie Przedmiotu")) {
            e.setCancelled(true);

            if (slot == 4) {
                final User user = rpgcore.getUserManager().find(uuid);
                if (!player.getInventory().containsAtLeast(GlobalItem.getItem("I_OCZYSZCZENIE", 1), 1) || user.getKasa() < 250_000) {
                    player.sendMessage(Utils.format(" &4&lKowal &8>> &7Nie posiadasz wymaganych przedmiotow!"));
                    player.closeInventory();
                    return;
                }
                player.getInventory().removeItem(GlobalItem.getItem("I_OCZYSZCZENIE", 1));
                user.setKasa(user.getKasa() - 250_000);
                final ItemStack playerItem = player.getItemInHand();
                final ItemMeta itemMeta = playerItem.getItemMeta();

                if (itemMeta.getDisplayName().contains("+3")) {
                    itemMeta.setDisplayName(itemMeta.getDisplayName().replace("+3", "+2"));
                } else if (itemMeta.getDisplayName().contains("+2")) {
                    itemMeta.setDisplayName(itemMeta.getDisplayName().replace("+2", "+1"));
                } else if (itemMeta.getDisplayName().contains("+1")) {
                    itemMeta.setDisplayName(itemMeta.getDisplayName().replace(Utils.format(" &8&l+1"), ""));
                    player.closeInventory();
                }

                playerItem.setItemMeta(itemMeta);
                player.setItemInHand(playerItem);
                player.sendMessage(Utils.format(" &4&lKowal &8>> &aPrzedmiot zostal pomyslnie oczyszczony!"));
            }
        }
    }
}
