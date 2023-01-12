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
    private final RPGCORE rpgcore;

    public AdminPanelInventoryClick(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onCLick(final InventoryClickEvent e) {
        final Inventory gui = e.getClickedInventory();
        final Player player = (Player) e.getWhoClicked();

        if (e.getClickedInventory() == null || e.getInventory() == null) {
            return;
        }

        final String title = Utils.removeColor(gui.getTitle());
        final int slot = e.getSlot();

        if (title.equals("Panel Administracyjny")) {
            e.setCancelled(true);
            if (slot == 10) {
                rpgcore.getAdminPanelManager().openAllCaseGUI(player);
                return;
            }
            if (slot == 11) {
                rpgcore.getAdminPanelManager().openNiesamowiteGUI(player);
                return;
            }
            if (slot == 12) {
                rpgcore.getAdminPanelManager().openBonyGUI(player);
                return;
            }
            if (slot == 13) {
                rpgcore.getAdminPanelManager().openItemShopGUI(player);
                return;
            }
            if (slot == 14) {
                rpgcore.getAdminPanelManager().openUlepszaczeGUI(player);
                return;
            }
            if (slot == 19) {
                rpgcore.getAdminPanelManager().openGornikItemsGUI(player);
                return;
            }
            if (slot == 20) {
                rpgcore.getAdminPanelManager().openPrzedmiotySpecjalneGui(player);
                return;
            }
        }

        if (title.equals("Skrzynki - ADMINISTRACJA")) {
            e.setCancelled(true);
            if (slot == 12) {
                rpgcore.getAdminPanelManager().openMobyCaseGUI(player);
                return;
            }
            if (slot == 13) {
                rpgcore.getAdminPanelManager().openBossyCaseGUI(player);
                return;
            }
            if (slot == 14) {
                rpgcore.getAdminPanelManager().openDungeonyCaseGUI(player);
                return;
            }
            if (slot == 22) {
                rpgcore.getAdminPanelManager().openInneCaseGUI(player);
                return;
            }
            if (slot == 26) {
                rpgcore.getAdminPanelManager().openAdminPanelGUI(player);
                return;
            }
        }
        if (title.equals("Skrzynki MOBY - ADMINISTRACJA") || title.equals("Skrzynki BOSSY - ADMINISTRACJA") || title.equals("Skrzynki DUNGEONY - ADMINISTRACJA")) {
            if (slot == 26) {
                e.setCancelled(true);
                rpgcore.getAdminPanelManager().openAllCaseGUI(player);
                return;
            }
        }
        if (title.equals("Skrzynki INNE - ADMINISTRACJA")) {
            if (slot == 17) {
                e.setCancelled(true);
                rpgcore.getAdminPanelManager().openAllCaseGUI(player);
                return;
            }
        }
        if (title.equals("Ulepszacze - ADMINISTRACJA")) {
            if (slot == 17) {
                e.setCancelled(true);
                rpgcore.getAdminPanelManager().openAdminPanelGUI(player);
                return;
            }
        }
        if (title.equals("Bony - ADMINISTRACJA")) {
            if (slot == 26) {
                e.setCancelled(true);
                rpgcore.getAdminPanelManager().openAdminPanelGUI(player);
                return;
            }
        }
        if (title.equals("ItemShop - ADMINISTRACJA")) {
            if (slot == 35) {
                e.setCancelled(true);
                rpgcore.getAdminPanelManager().openAdminPanelGUI(player);
                return;
            }
        }
        if (title.equals("Niesamowite - ADMINISTRACJA")) {
            if (slot == 17) {
                e.setCancelled(true);
                rpgcore.getAdminPanelManager().openAdminPanelGUI(player);
                return;
            }
        }
        if (title.equals("GORNIK - ADMINISTRACJA")) {
            if (slot == 53) {
                e.setCancelled(true);
                rpgcore.getAdminPanelManager().openAdminPanelGUI(player);
            }
        }
        if (title.equals("Przedmioty Specjalne - ADMINISRTACJA")) {
            if (slot == 26) {
                e.setCancelled(true);
                rpgcore.getAdminPanelManager().openPrzedmiotySpecjalneGui(player);
            }
        }
    }
}
