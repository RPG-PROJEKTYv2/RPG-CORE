package rpg.rpgcore.os;

import org.bukkit.Material;
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

        if (e.getClickedInventory() == null) {
            return;
        }

        final String title = clickedInventory.getTitle();
        final ItemStack item = e.getCurrentItem();
        final int slot = e.getSlot();

        if (Utils.removeColor(title).equals("Osiagniecia")) {
            e.setCancelled(true);

            if (item.getType().equals(Material.STAINED_GLASS_PANE)) {
                return;
            }

            rpgcore.getOsManager().openOsGuiCategory(player, slot);
        }

        if (Utils.removeColor(title).equals("Osiagniecia - Zabici Gracze")) {
            e.setCancelled(true);
        }

        /*
        if (clickedInventoryTitle.equals(rpgcore.getOsManager().osGuiMain().getName())) {
            e.setCancelled(true);
            if (clickedItem.getType() == Material.GOLD_SWORD){
                player.openInventory(rpgcore.getOsManager().osMobyGui(playerUUID));
                return;
            }
            if (clickedItem.getType() == Material.DIAMOND_SWORD) {
                player.openInventory(rpgcore.getOsManager().osGraczeGUI(playerUUID));
                return;
            }
            if (clickedItem.getType() == Material.NETHER_STAR) {
                player.openInventory(rpgcore.getOsManager().osMetinyGUI(playerUUID));
                return;
            }
            if (clickedItem.getType() == Material.EXP_BOTTLE) {
                player.openInventory(rpgcore.getOsManager().osSakwyGUI(playerUUID));
                return;
            }
            if (clickedItem.getType() == Material.DIAMOND_BLOCK) {
                player.openInventory(rpgcore.getOsManager().osNiesyGUI(playerUUID));
                return;
            }
            if (clickedItem.getType() == Material.FISHING_ROD) {
                player.openInventory(rpgcore.getOsManager().osRybakGUI(playerUUID));
                return;
            }
            if (clickedItem.getType() == Material.DIAMOND_AXE) {
                player.openInventory(rpgcore.getOsManager().osDrwalGUI(playerUUID));
                return;
            }
            if (clickedItem.getType() == Material.GOLD_PICKAXE) {
                player.openInventory(rpgcore.getOsManager().osGornikGUI(playerUUID));
                return;
            }
        }

        if (clickedInventoryTitle.equals(rpgcore.getOsManager().osMobyGui(playerUUID).getName())) {
            e.setCancelled(true);
            if (clickedItem.getType() == Material.AIR) {
                return;
            }

            String[] accepted = rpgcore.getPlayerManager().getOsMobyAccept(playerUUID).split(",");

            if (accepted[clickedSlot].equalsIgnoreCase("true")) {
                return;
            }

            if (clickedSlot != 0 && accepted[clickedSlot-1].equalsIgnoreCase("false")) {
                return;
            }

            final int killedMobs = rpgcore.getPlayerManager().getPlayerOsMoby(playerUUID);

            if (killedMobs < rpgcore.getOsManager().getRequiredForOsMoby().get(clickedSlot+1)) {
                return;
            }

            accepted[clickedSlot] = "true";
            StringBuilder noweOsMobyAccepted = new StringBuilder();
            for (int i = 0; i < accepted.length; i++) {
                noweOsMobyAccepted.append(accepted[i]);
                if (!(i+1 >= accepted.length)) {
                    noweOsMobyAccepted.append(",");
                }
            }
            player.sendMessage(String.valueOf(noweOsMobyAccepted));
            rpgcore.getPlayerManager().updatePlayerOsMobyAccepted(playerUUID, String.valueOf(noweOsMobyAccepted));

            player.closeInventory();
            Bukkit.getServer().broadcastMessage(Utils.format(Utils.SERVERNAME + "&3Gracz &f" + player.getName() + " &3odebral osiagniecie &f" + Utils.removeColor(clickedItem.getItemMeta().getDisplayName())));
            noweOsMobyAccepted.setLength(0);
            return;
        }

        if (clickedInventoryTitle.equals(rpgcore.getOsManager().osGraczeGUI(playerUUID).getName())) {

            e.setCancelled(true);
            if (clickedItem.getType() == Material.AIR) {
                return;
            }

            String[] accepted = rpgcore.getPlayerManager().getOsLudzieAccept(playerUUID).split(",");

            if (accepted[clickedSlot].equalsIgnoreCase("true")) {
                return;
            }

            if (clickedSlot != 0 && accepted[clickedSlot-1].equalsIgnoreCase("false")) {
                return;
            }

            final int killedPlayers = rpgcore.getPlayerManager().getPlayerOsLudzie(playerUUID);

            if (killedPlayers < rpgcore.getOsManager().getReqForPlayerKills().get(clickedSlot+1)) {
                return;
            }


            accepted[clickedSlot] = "true";
            StringBuilder noweOsLudzieAccepted = new StringBuilder();
            for (int i = 0; i < accepted.length; i++) {
                noweOsLudzieAccepted.append(accepted[i]);
                if (!(i+1 >= accepted.length)) {
                    noweOsLudzieAccepted.append(",");
                }
            }
            player.sendMessage(String.valueOf(noweOsLudzieAccepted));
            rpgcore.getPlayerManager().updatePlayerOsLudzieAccepted(playerUUID, String.valueOf(noweOsLudzieAccepted));

            player.closeInventory();
            Bukkit.getServer().broadcastMessage(Utils.format(Utils.SERVERNAME + "&3Gracz &f" + player.getName() + " &3odebral osiagniecie &f" + Utils.removeColor(clickedItem.getItemMeta().getDisplayName())));
            noweOsLudzieAccepted.setLength(0);
            return;
        }

        if (clickedInventoryTitle.equals(rpgcore.getOsManager().osMetinyGUI(playerUUID).getName())) {

            e.setCancelled(true);
            if (clickedItem.getType() == Material.AIR) {
                return;
            }

            String[] accepted = rpgcore.getPlayerManager().getOsMetinyAccept(playerUUID).split(",");

            if (accepted[clickedSlot].equalsIgnoreCase("true")) {
                return;
            }

            if (clickedSlot != 0 && accepted[clickedSlot-1].equalsIgnoreCase("false")) {
                return;
            }

            final int killedPlayers = rpgcore.getPlayerManager().getPlayerOsMetiny(playerUUID);

            if (killedPlayers < rpgcore.getOsManager().getRequiredForOsMetiny().get(clickedSlot+1)) {
                return;
            }


            accepted[clickedSlot] = "true";
            StringBuilder noweOsMetinyAccept = new StringBuilder();
            for (int i = 0; i < accepted.length; i++) {
                noweOsMetinyAccept.append(accepted[i]);
                if (!(i+1 >= accepted.length)) {
                    noweOsMetinyAccept.append(",");
                }
            }
            player.sendMessage(String.valueOf(noweOsMetinyAccept));
            rpgcore.getPlayerManager().updatePlayerOsLudzieAccepted(playerUUID, String.valueOf(noweOsMetinyAccept));

            player.closeInventory();
            Bukkit.getServer().broadcastMessage(Utils.format(Utils.SERVERNAME + "&3Gracz &f" + player.getName() + " &3odebral osiagniecie &f" + Utils.removeColor(clickedItem.getItemMeta().getDisplayName())));
            return;
        }

        if (clickedInventoryTitle.equals(rpgcore.getOsManager().osSakwyGUI(playerUUID).getName())) {

            e.setCancelled(true);
            if (clickedItem.getType() == Material.AIR) {
                return;
            }

            String[] accepted = rpgcore.getPlayerManager().getOsSakwyAccept(playerUUID).split(",");

            if (accepted[clickedSlot].equalsIgnoreCase("true")) {
                return;
            }

            if (clickedSlot != 0 && accepted[clickedSlot-1].equalsIgnoreCase("false")) {
                return;
            }

            final int dropped = rpgcore.getPlayerManager().getPlayerOsSakwy(playerUUID);

            if (dropped < rpgcore.getOsManager().getRequiredForOsSakwy().get(clickedSlot+1)) {
                return;
            }

            accepted[clickedSlot] = "true";
            StringBuilder noweOsSakwyAccepted = new StringBuilder();
            for (int i = 0; i < accepted.length; i++) {
                noweOsSakwyAccepted.append(accepted[i]);
                if (!(i+1 >= accepted.length)) {
                    noweOsSakwyAccepted.append(",");
                }
            }
            player.sendMessage(String.valueOf(noweOsSakwyAccepted));
            rpgcore.getPlayerManager().updatePlayerOsSakwyAccepted(playerUUID, String.valueOf(noweOsSakwyAccepted));

            player.closeInventory();
            Bukkit.getServer().broadcastMessage(Utils.format(Utils.SERVERNAME + "&3Gracz &f" + player.getName() + " &3odebral osiagniecie &f" + Utils.removeColor(clickedItem.getItemMeta().getDisplayName())));
            noweOsSakwyAccepted.setLength(0);
            return;
        }

        if (clickedInventoryTitle.equals(rpgcore.getOsManager().osNiesyGUI(playerUUID).getName())) {

            e.setCancelled(true);
            if (clickedItem.getType() == Material.AIR) {
                return;
            }

            String[] accepted = rpgcore.getPlayerManager().getOsNiesyAccept(playerUUID).split(",");

            if (accepted[clickedSlot].equalsIgnoreCase("true")) {
                return;
            }

            if (clickedSlot != 0 && accepted[clickedSlot-1].equalsIgnoreCase("false")) {
                return;
            }

            final int dropped = rpgcore.getPlayerManager().getPlayerOsNiesy(playerUUID);

            if (dropped < rpgcore.getOsManager().getRequiredForOsNiesy().get(clickedSlot+1)) {
                return;
            }

            accepted[clickedSlot] = "true";
            StringBuilder noweOsNiesyAccepted = new StringBuilder();
            for (int i = 0; i < accepted.length; i++) {
                noweOsNiesyAccepted.append(accepted[i]);
                if (!(i+1 >= accepted.length)) {
                    noweOsNiesyAccepted.append(",");
                }
            }
            player.sendMessage(String.valueOf(noweOsNiesyAccepted));
            rpgcore.getPlayerManager().updatePlayerOsNiesyAccepted(playerUUID, String.valueOf(noweOsNiesyAccepted));

            player.closeInventory();
            Bukkit.getServer().broadcastMessage(Utils.format(Utils.SERVERNAME + "&3Gracz &f" + player.getName() + " &3odebral osiagniecie &f" + Utils.removeColor(clickedItem.getItemMeta().getDisplayName())));
            noweOsNiesyAccepted.setLength(0);
            return;
        }

        if (clickedInventoryTitle.equals(rpgcore.getOsManager().osRybakGUI(playerUUID).getName())) {

            e.setCancelled(true);
            if (clickedItem.getType() == Material.AIR) {
                return;
            }

            String[] accepted = rpgcore.getPlayerManager().getOsRybakAccept(playerUUID).split(",");

            if (accepted[clickedSlot].equalsIgnoreCase("true")) {
                return;
            }

            if (clickedSlot != 0 && accepted[clickedSlot-1].equalsIgnoreCase("false")) {
                return;
            }

            final int dropped = rpgcore.getPlayerManager().getPlayerOsRybak(playerUUID);

            if (dropped < rpgcore.getOsManager().getRequiredForOsRybak().get(clickedSlot+1)) {
                return;
            }

            accepted[clickedSlot] = "true";
            StringBuilder noweOsRybakAccepted = new StringBuilder();
            for (int i = 0; i < accepted.length; i++) {
                noweOsRybakAccepted.append(accepted[i]);
                if (!(i+1 >= accepted.length)) {
                    noweOsRybakAccepted.append(",");
                }
            }
            player.sendMessage(String.valueOf(noweOsRybakAccepted));
            rpgcore.getPlayerManager().updatePlayerOsRybakAccepted(playerUUID, String.valueOf(noweOsRybakAccepted));

            player.closeInventory();
            Bukkit.getServer().broadcastMessage(Utils.format(Utils.SERVERNAME + "&3Gracz &f" + player.getName() + " &3odebral osiagniecie &f" + Utils.removeColor(clickedItem.getItemMeta().getDisplayName())));
            noweOsRybakAccepted.setLength(0);
            return;
        }

        if (clickedInventoryTitle.equals(rpgcore.getOsManager().osDrwalGUI(playerUUID).getName())) {

            e.setCancelled(true);
            if (clickedItem.getType() == Material.AIR) {
                return;
            }

            String[] accepted = rpgcore.getPlayerManager().getOsDrwalAccept(playerUUID).split(",");

            if (accepted[clickedSlot].equalsIgnoreCase("true")) {
                return;
            }

            if (clickedSlot != 0 && accepted[clickedSlot-1].equalsIgnoreCase("false")) {
                return;
            }

            final int dropped = rpgcore.getPlayerManager().getPlayerOsDrwal(playerUUID);

            if (dropped < rpgcore.getOsManager().getRequiredForOsDrwal().get(clickedSlot+1)) {
                return;
            }

            accepted[clickedSlot] = "true";
            StringBuilder noweOsDrwalAccepted = new StringBuilder();
            for (int i = 0; i < accepted.length; i++) {
                noweOsDrwalAccepted.append(accepted[i]);
                if (!(i+1 >= accepted.length)) {
                    noweOsDrwalAccepted.append(",");
                }
            }
            player.sendMessage(String.valueOf(noweOsDrwalAccepted));
            rpgcore.getPlayerManager().updatePlayerOsDrwalAccepted(playerUUID, String.valueOf(noweOsDrwalAccepted));

            player.closeInventory();
            Bukkit.getServer().broadcastMessage(Utils.format(Utils.SERVERNAME + "&3Gracz &f" + player.getName() + " &3odebral osiagniecie &f" + Utils.removeColor(clickedItem.getItemMeta().getDisplayName())));
            noweOsDrwalAccepted.setLength(0);
            return;
        }

        if (clickedInventoryTitle.equals(rpgcore.getOsManager().osGornikGUI(playerUUID).getName())) {

            e.setCancelled(true);
            if (clickedItem.getType() == Material.AIR) {
                return;
            }

            String[] accepted = rpgcore.getPlayerManager().getOsGornikAccept(playerUUID).split(",");

            if (accepted[clickedSlot].equalsIgnoreCase("true")) {
                return;
            }

            if (clickedSlot != 0 && accepted[clickedSlot-1].equalsIgnoreCase("false")) {
                return;
            }

            final int dropped = rpgcore.getPlayerManager().getPlayerOsGornik(playerUUID);

            if (dropped < rpgcore.getOsManager().getRequiredForOsGornik().get(clickedSlot+1)) {
                return;
            }

            accepted[clickedSlot] = "true";
            StringBuilder noweOsGornikAccepted = new StringBuilder();
            for (int i = 0; i < accepted.length; i++) {
                noweOsGornikAccepted.append(accepted[i]);
                if (!(i+1 >= accepted.length)) {
                    noweOsGornikAccepted.append(",");
                }
            }
            player.sendMessage(String.valueOf(noweOsGornikAccepted));
            rpgcore.getPlayerManager().updatePlayerOsGornikAccepted(playerUUID, String.valueOf(noweOsGornikAccepted));

            player.closeInventory();
            Bukkit.getServer().broadcastMessage(Utils.format(Utils.SERVERNAME + "&3Gracz &f" + player.getName() + " &3odebral osiagniecie &f" + Utils.removeColor(clickedItem.getItemMeta().getDisplayName())));
            noweOsGornikAccepted.setLength(0);
        }*/
    }

}
