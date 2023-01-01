package rpg.rpgcore.commands.admin.adminpanel;

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

public class AdminPanelInventoryClick implements Listener {
    private RPGCORE rpgcore;

    public AdminPanelInventoryClick(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onCLick(final InventoryClickEvent e) {
        final Inventory gui = e.getClickedInventory();
        final Player player = (Player) e.getWhoClicked();
        final UUID uuid = player.getUniqueId();

        if (e.getClickedInventory() == null || e.getInventory() == null) {
            return;
        }

        final String title = Utils.removeColor(gui.getTitle());
        final int slot = e.getSlot();
        final ItemStack item = e.getCurrentItem();

        if (title.equals("Panel Administracyjny")) {
            e.setCancelled(true);
            if (slot == 10) {
                e.setCancelled(true);
                rpgcore.getAdminPanelManager().openAllCaseGUI(player);
            }
            if (slot == 11) {
                e.setCancelled(true);
                rpgcore.getAdminPanelManager().openNiesamowiteGUI(player);
            }
            if (slot == 12) {
                e.setCancelled(true);
                rpgcore.getAdminPanelManager().openBonyGUI(player);
            }
            if (slot == 13) {
                e.setCancelled(true);
                rpgcore.getAdminPanelManager().openItemShopGUI(player);
            }
            if (slot == 14) {
                e.setCancelled(true);
                rpgcore.getAdminPanelManager().openUlepszaczeGUI(player);
            }
        }

        if (title.equals("Skrzynki - ADMINISTRACJA")) {
            e.setCancelled(true);
            if (slot == 12) {
                e.setCancelled(true);
                rpgcore.getAdminPanelManager().openMobyCaseGUI(player);
            }
            if (slot == 13) {
                e.setCancelled(true);
                rpgcore.getAdminPanelManager().openBossyCaseGUI(player);
            }
            if (slot == 14) {
                e.setCancelled(true);
                rpgcore.getAdminPanelManager().openDungeonyCaseGUI(player);
            }
            if (slot == 22) {
                e.setCancelled(true);
                rpgcore.getAdminPanelManager().openInneCaseGUI(player);
            }
        }
        if (title.equals("Skrzynki MOBY - ADMINISTRACJA") || title.equals("Skrzynki BOSSY - ADMINISTRACJA") || title.equals("Skrzynki DUNGEONY - ADMINISTRACJA")) {
            if (slot == 26) {
                e.setCancelled(true);
                rpgcore.getAdminPanelManager().openAllCaseGUI(player);
            }
        }
        if (title.equals("Skrzynki INNE - ADMINISTRACJA")) {
            if (slot == 17) {
                e.setCancelled(true);
                rpgcore.getAdminPanelManager().openAllCaseGUI(player);
            }
        }
        if (title.equals("Ulepszacze - ADMINISTRACJA")) {
            if (slot == 17) {
                e.setCancelled(true);
                rpgcore.getAdminPanelManager().openAdminPanelGUI(player);
            }
        }
        if (title.equals("Bony - ADMINISTRACJA")) {
            if (slot == 26) {
                e.setCancelled(true);
                rpgcore.getAdminPanelManager().openAdminPanelGUI(player);
            }
        }
        if (title.equals("ItemShop - ADMINISTRACJA")) {
            if (slot == 35) {
                e.setCancelled(true);
                rpgcore.getAdminPanelManager().openAdminPanelGUI(player);
            }
        }
        if (title.equals("Niesamowite - ADMINISTRACJA")) {
            if (slot == 17) {
                e.setCancelled(true);
                rpgcore.getAdminPanelManager().openAdminPanelGUI(player);
            }
        }
    }
}
