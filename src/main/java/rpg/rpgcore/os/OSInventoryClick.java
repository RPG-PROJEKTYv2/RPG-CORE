package rpg.rpgcore.os;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.globalitems.OsRewards;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

public class OSInventoryClick implements Listener {

    private final RPGCORE rpgcore;

    public OSInventoryClick(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void osInventoryClick(final InventoryClickEvent e) {

        final Inventory clickedInventory = e.getClickedInventory();
        final Player player = (Player) e.getWhoClicked();
        final UUID playerUUID = player.getUniqueId();

        if (e.getClickedInventory() == null || e.getInventory() == null) {
            return;
        }

        final String title = clickedInventory.getTitle();
        final ItemStack item = e.getCurrentItem();
        final int slot = e.getSlot();

        if (Utils.removeColor(title).equals("OsiagnieciaCommand")) {
            e.setCancelled(true);

            if (item.getType().equals(Material.STAINED_GLASS_PANE)) {
                return;
            }

            rpgcore.getOsManager().openOsGuiCategory(player, slot);
        }

        if (Utils.removeColor(title).equals("OsiagnieciaCommand - Zabici Gracze")) {
            e.setCancelled(true);

            if (item.getType().equals(Material.AIR)) {
                return;
            }

            final OsUser osUser = rpgcore.getOsManager().find(playerUUID).getOsUser();

            if (item.getItemMeta().getLore().contains(Utils.format("&a&lWykonano!"))) {
                return;
            }

            if (slot != 0 && !clickedInventory.getItem(slot - 1).getItemMeta().getLore().contains(Utils.format("&a&lWykonano!"))) {
                return;
            }

            if (osUser.getPlayerKills() < rpgcore.getOsManager().getReqForPlayerKills().get(slot + 1)) {
                osUser.setPlayerKills(10);
                return;
            }

            osUser.setPlayerKillsProgress(osUser.getPlayerKillsProgress() + 1);
            player.getInventory().addItem(OsRewards.getItem("P" + slot, 1));
            rpgcore.getOsManager().openOsGuiCategory(player, 0);
            Bukkit.broadcastMessage(Utils.format("&6&lOsiagniecia &8>> &7Gracz &6" + player.getName() + " &7odebral osiagniecie " + item.getItemMeta().getDisplayName()));

        }

        if (Utils.removeColor(title).equals("OsiagnieciaCommand - Zabite Stwory")) {
            e.setCancelled(true);

            if (item.getType().equals(Material.AIR)) {
                return;
            }

            final OsUser osUser = rpgcore.getOsManager().find(playerUUID).getOsUser();

            if (item.getItemMeta().getLore().contains(Utils.format("&a&lWykonano!"))) {
                return;
            }

            if (slot != 0 && !clickedInventory.getItem(slot - 1).getItemMeta().getLore().contains(Utils.format("&a&lWykonano!"))) {
                return;
            }

            if (osUser.getMobKills() < rpgcore.getOsManager().getReqForMobKills().get(slot + 1)) {
                return;
            }

            osUser.setMobKillsProgress(osUser.getMobKillsProgress() + 1);
            player.getInventory().addItem(OsRewards.getItem("M" + slot, 1));
            rpgcore.getOsManager().openOsGuiCategory(player, 1);
            Bukkit.broadcastMessage(Utils.format("&6&lOsiagniecia &8>> &7Gracz &6" + player.getName() + " &7odebral osiagniecie " + item.getItemMeta().getDisplayName()));

        }


        if (Utils.removeColor(title).equals("OsiagnieciaCommand - Spedzony Czas")) {
            e.setCancelled(true);

            if (item.getType().equals(Material.AIR)) {
                return;
            }

            final OsUser osUser = rpgcore.getOsManager().find(playerUUID).getOsUser();

            if (item.getItemMeta().getLore().contains(Utils.format("&a&lWykonano!"))) {
                return;
            }

            if (slot != 0 && !clickedInventory.getItem(slot - 1).getItemMeta().getLore().contains(Utils.format("&a&lWykonano!"))) {
                return;
            }

            if (osUser.getTimeSpent() < rpgcore.getOsManager().getReqForTimeSpent().get(slot + 1)) {
                return;
            }

            osUser.setTimeSpentProgress(osUser.getTimeSpentProgress() + 1);
            player.getInventory().addItem(OsRewards.getItem("T" + slot, 1));
            rpgcore.getOsManager().openOsGuiCategory(player, 2);
            Bukkit.broadcastMessage(Utils.format("&6&lOsiagniecia &8>> &7Gracz &6" + player.getName() + " &7odebral osiagniecie " + item.getItemMeta().getDisplayName()));

        }

        if (Utils.removeColor(title).equals("OsiagnieciaCommand - Wykopane Bloki")) {
            e.setCancelled(true);

            if (item.getType().equals(Material.AIR)) {
                return;
            }

            final OsUser osUser = rpgcore.getOsManager().find(playerUUID).getOsUser();

            if (item.getItemMeta().getLore().contains(Utils.format("&a&lWykonano!"))) {
                return;
            }

            if (slot != 0 && !clickedInventory.getItem(slot - 1).getItemMeta().getLore().contains(Utils.format("&a&lWykonano!"))) {
                return;
            }

            if (osUser.getMinedBlocks() < rpgcore.getOsManager().getReqForMinedBlocks().get(slot + 1)) {
                return;
            }

            osUser.setMinedBlocksProgress(osUser.getMinedBlocksProgress() + 1);
            player.getInventory().addItem(OsRewards.getItem("B" + slot, 1));
            rpgcore.getOsManager().openOsGuiCategory(player, 3);
            Bukkit.broadcastMessage(Utils.format("&6&lOsiagniecia &8>> &7Gracz &6" + player.getName() + " &7odebral osiagniecie " + item.getItemMeta().getDisplayName()));

        }

        if (Utils.removeColor(title).equals("OsiagnieciaCommand - Udane Polowy")) {
            e.setCancelled(true);

            if (item.getType().equals(Material.AIR)) {
                return;
            }

            final OsUser osUser = rpgcore.getOsManager().find(playerUUID).getOsUser();

            if (item.getItemMeta().getLore().contains(Utils.format("&a&lWykonano!"))) {
                return;
            }

            if (slot != 0 && !clickedInventory.getItem(slot - 1).getItemMeta().getLore().contains(Utils.format("&a&lWykonano!"))) {
                return;
            }

            if (osUser.getFishedItems() < rpgcore.getOsManager().getReqForFishedItems().get(slot + 1)) {
                return;
            }

            osUser.setFishedItemsProgress(osUser.getFishedItemsProgress() + 1);
            player.getInventory().addItem(OsRewards.getItem("F" + slot, 1));
            rpgcore.getOsManager().openOsGuiCategory(player, 4);
            Bukkit.broadcastMessage(Utils.format("&6&lOsiagniecia &8>> &7Gracz &6" + player.getName() + " &7odebral osiagniecie " + item.getItemMeta().getDisplayName()));

        }

        if (Utils.removeColor(title).equals("OsiagnieciaCommand - Otwarte Skrzynki")) {
            e.setCancelled(true);

            if (item.getType().equals(Material.AIR)) {
                return;
            }

            final OsUser osUser = rpgcore.getOsManager().find(playerUUID).getOsUser();

            if (item.getItemMeta().getLore().contains(Utils.format("&a&lWykonano!"))) {
                return;
            }

            if (slot != 0 && !clickedInventory.getItem(slot - 1).getItemMeta().getLore().contains(Utils.format("&a&lWykonano!"))) {
                return;
            }

            if (osUser.getOpenedChests() < rpgcore.getOsManager().getReqForOpenedChests().get(slot + 1)) {
                return;
            }

            osUser.setOpenedChestsProgress(osUser.getOpenedChestsProgress() + 1);
            player.getInventory().addItem(OsRewards.getItem("C" + slot, 1));
            rpgcore.getOsManager().openOsGuiCategory(player, 5);
            Bukkit.broadcastMessage(Utils.format("&6&lOsiagniecia &8>> &7Gracz &6" + player.getName() + " &7odebral osiagniecie " + item.getItemMeta().getDisplayName()));

        }

        if (Utils.removeColor(title).equals("OsiagnieciaCommand - Pomyslne Ulepszenia")) {
            e.setCancelled(true);

            if (item.getType().equals(Material.AIR)) {
                return;
            }

            final OsUser osUser = rpgcore.getOsManager().find(playerUUID).getOsUser();

            if (item.getItemMeta().getLore().contains(Utils.format("&a&lWykonano!"))) {
                return;
            }

            if (slot != 0 && !clickedInventory.getItem(slot - 1).getItemMeta().getLore().contains(Utils.format("&a&lWykonano!"))) {
                return;
            }

            if (osUser.getPositiveUpgrades() < rpgcore.getOsManager().getReqForPositiveUpgrades().get(slot + 1)) {
                return;
            }

            osUser.setPositiveUpgradesProgress(osUser.getPositiveUpgradesProgress() + 1);
            player.getInventory().addItem(OsRewards.getItem("U" + slot, 1));
            rpgcore.getOsManager().openOsGuiCategory(player, 6);
            Bukkit.broadcastMessage(Utils.format("&6&lOsiagniecia &8>> &7Gracz &6" + player.getName() + " &7odebral osiagniecie " + item.getItemMeta().getDisplayName()));

        }

        if (Utils.removeColor(title).equals("OsiagnieciaCommand - Znalezione Niesy")) {
            e.setCancelled(true);

            if (item.getType().equals(Material.AIR)) {
                return;
            }

            final OsUser osUser = rpgcore.getOsManager().find(playerUUID).getOsUser();

            if (item.getItemMeta().getLore().contains(Utils.format("&a&lWykonano!"))) {
                return;
            }

            if (slot != 0 && !clickedInventory.getItem(slot - 1).getItemMeta().getLore().contains(Utils.format("&a&lWykonano!"))) {
                return;
            }

            if (osUser.getPickedUpNies() < rpgcore.getOsManager().getReqForPickedUpNies().get(slot + 1)) {
                return;
            }

            osUser.setPickedUpNiesProgress(osUser.getPickedUpNiesProgress() + 1);
            player.getInventory().addItem(OsRewards.getItem("N" + slot, 1));
            rpgcore.getOsManager().openOsGuiCategory(player, 7);
            Bukkit.broadcastMessage(Utils.format("&6&lOsiagniecia &8>> &7Gracz &6" + player.getName() + " &7odebral osiagniecie " + item.getItemMeta().getDisplayName()));

        }

        if (Utils.removeColor(title).equals("OsiagnieciaCommand - Zniszczone Metiny")) {
            e.setCancelled(true);

            if (item.getType().equals(Material.AIR)) {
                return;
            }

            final OsUser osUser = rpgcore.getOsManager().find(playerUUID).getOsUser();

            if (item.getItemMeta().getLore().contains(Utils.format("&a&lWykonano!"))) {
                return;
            }

            if (slot != 0 && !clickedInventory.getItem(slot - 1).getItemMeta().getLore().contains(Utils.format("&a&lWykonano!"))) {
                return;
            }

            if (osUser.getDestroyedMetins() < rpgcore.getOsManager().getReqForDestroyedMetins().get(slot + 1)) {
                return;
            }

            osUser.setDestroyedMetinsProgress(osUser.getDestroyedMetinsProgress() + 1);
            player.getInventory().addItem(OsRewards.getItem("D" + slot, 1));
            rpgcore.getOsManager().openOsGuiCategory(player, 8);
            Bukkit.broadcastMessage(Utils.format("&6&lOsiagniecia &8>> &7Gracz &6" + player.getName() + " &7odebral osiagniecie " + item.getItemMeta().getDisplayName()));

        }

        if (Utils.removeColor(title).equals("OsiagnieciaCommand - Wykopane Drewno")) {
            e.setCancelled(true);

            if (item.getType().equals(Material.AIR)) {
                return;
            }

            final OsUser osUser = rpgcore.getOsManager().find(playerUUID).getOsUser();

            if (item.getItemMeta().getLore().contains(Utils.format("&a&lWykonano!"))) {
                return;
            }

            if (slot != 0 && !clickedInventory.getItem(slot - 1).getItemMeta().getLore().contains(Utils.format("&a&lWykonano!"))) {
                return;
            }

            if (osUser.getMinedTrees() < rpgcore.getOsManager().getReqForMinedTrees().get(slot + 1)) {
                return;
            }

            osUser.setMinedTreesProogress(osUser.getMinedTreesProogress() + 1);
            player.getInventory().addItem(OsRewards.getItem("L" + slot, 1));
            rpgcore.getOsManager().openOsGuiCategory(player, 9);
            Bukkit.broadcastMessage(Utils.format("&6&lOsiagniecia &8>> &7Gracz &6" + player.getName() + " &7odebral osiagniecie " + item.getItemMeta().getDisplayName()));

        }

    }

}
