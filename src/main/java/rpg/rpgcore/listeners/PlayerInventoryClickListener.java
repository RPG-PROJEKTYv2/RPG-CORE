package rpg.rpgcore.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.HashMap;
import java.util.UUID;

public class PlayerInventoryClickListener implements Listener {

    private final RPGCORE rpgcore;

    public PlayerInventoryClickListener(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClick(final InventoryClickEvent e) {
        final Player player = (Player) e.getWhoClicked();
        final UUID playerUUID = player.getUniqueId();
        final HashMap<Integer, ItemStack> itemMapToRemove = new HashMap<>();

        if (e.getClickedInventory() == null) {
            player.closeInventory();
            return;
        }
        //                      GUI OD BAO                  \\
        if (e.getClickedInventory().getName().equals(Utils.format("&6&lSTOL MAGII"))) {

            if (e.getSlot() == 16) {
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

        if (e.getClickedInventory().getName().equalsIgnoreCase(rpgcore.getBaoManager().ksiegaMagiiGUI(playerUUID).getName())) {
            e.setCancelled(true);
            if (e.getSlot() == 11) {
                rpgcore.getBaoManager().losujNowyBonus1(playerUUID);
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&6Twoj nowy bonus to: &c" + rpgcore.getBaoManager().getBaoBonusy(playerUUID).split(",")[e.getSlot() - 11] + " " + rpgcore.getBaoManager().getBaoBonusyWartosci(playerUUID).split(",")[e.getSlot() - 11]) + " %");
            }
            if (e.getSlot() == 12) {
                rpgcore.getBaoManager().losujNowyBonus2(playerUUID);
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&6Twoj nowy bonus to: &c" + rpgcore.getBaoManager().getBaoBonusy(playerUUID).split(",")[e.getSlot() - 11] + " " + rpgcore.getBaoManager().getBaoBonusyWartosci(playerUUID).split(",")[e.getSlot() - 11]) + " %");
            }
            if (e.getSlot() == 13) {
                rpgcore.getBaoManager().losujNowyBonus3(playerUUID);
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&6Twoj nowy bonus to: &c" + rpgcore.getBaoManager().getBaoBonusy(playerUUID).split(",")[e.getSlot() - 11] + " " + rpgcore.getBaoManager().getBaoBonusyWartosci(playerUUID).split(",")[e.getSlot() - 11]) + " %");
            }
            if (e.getSlot() == 14) {
                rpgcore.getBaoManager().losujNowyBonus4(playerUUID);
                if (rpgcore.getBaoManager().getBaoBonusy(playerUUID).split(",")[3].equalsIgnoreCase("dodatkowe obrazenia")) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&6Twoj nowy bonus to: &c" + rpgcore.getBaoManager().getBaoBonusy(playerUUID).split(",")[e.getSlot() - 11] + " " + rpgcore.getBaoManager().getBaoBonusyWartosci(playerUUID).split(",")[e.getSlot() - 11]) + " DMG");
                } else {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&6Twoj nowy bonus to: &c" + rpgcore.getBaoManager().getBaoBonusy(playerUUID).split(",")[e.getSlot() - 11] + " " + rpgcore.getBaoManager().getBaoBonusyWartosci(playerUUID).split(",")[e.getSlot() - 11]) + " %");
                }
            }
            if (e.getSlot() == 15) {
                rpgcore.getBaoManager().losujNowyBonus5(playerUUID);
                if (rpgcore.getBaoManager().getBaoBonusy(playerUUID).split(",")[4].equalsIgnoreCase("dodatkowe hp")) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&6Twoj nowy bonus to: &c" + rpgcore.getBaoManager().getBaoBonusy(playerUUID).split(",")[e.getSlot() - 11] + " " + rpgcore.getBaoManager().getBaoBonusyWartosci(playerUUID).split(",")[e.getSlot() - 11]) + " HP");
                } else {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&6Twoj nowy bonus to: &c" + rpgcore.getBaoManager().getBaoBonusy(playerUUID).split(",")[e.getSlot() - 11] + " " + rpgcore.getBaoManager().getBaoBonusyWartosci(playerUUID).split(",")[e.getSlot() - 11]) + " %");
                }
            }
            player.closeInventory();
            return;
        }

        if (e.getClickedInventory().getName().equals(rpgcore.getOsManager().osGuiMain().getName())) {
            e.setCancelled(true);
            if (e.getCurrentItem().getType() == Material.GOLD_SWORD){
                player.openInventory(rpgcore.getOsManager().osMobyGui(playerUUID));
                return;
            }
            if (e.getCurrentItem().getType() == Material.DIAMOND_SWORD) {
                player.openInventory(rpgcore.getOsManager().osGraczeGUI(playerUUID));
                return;
            }
            if (e.getCurrentItem().getType() == Material.EXP_BOTTLE) {
                player.openInventory(rpgcore.getOsManager().osSakwyGUI(playerUUID));
                return;
            }
            if (e.getCurrentItem().getType() == Material.DIAMOND_BLOCK) {
                player.openInventory(rpgcore.getOsManager().osNiesyGUI(playerUUID));
                return;
            }
            if (e.getCurrentItem().getType() == Material.FISHING_ROD) {
                player.openInventory(rpgcore.getOsManager().osRybakGUI(playerUUID));
                return;
            }
            if (e.getCurrentItem().getType() == Material.DIAMOND_AXE) {
                player.openInventory(rpgcore.getOsManager().osDrwalGUI(playerUUID));
                return;
            }
            if (e.getCurrentItem().getType() == Material.GOLD_PICKAXE) {
                player.openInventory(rpgcore.getOsManager().osGornikGUI(playerUUID));
                return;
            }
        }

        if (e.getClickedInventory().getName().equals(rpgcore.getOsManager().osMobyGui(playerUUID).getName())) {
            final int slot = e.getSlot();
            if (e.getCurrentItem().getType() == Material.AIR) {
                e.setCancelled(true);
                return;
            }

            String[] accepted = rpgcore.getPlayerManager().getOsMobyAccept(playerUUID).split(",");

            if (accepted[slot].equalsIgnoreCase("true")) {
                e.setCancelled(true);
                return;
            }

            if (slot != 0 && accepted[slot-1].equalsIgnoreCase("false")) {
                e.setCancelled(true);
                return;
            }

            final int killedMobs = rpgcore.getPlayerManager().getPlayerOsMoby(playerUUID);

            if (killedMobs < rpgcore.getOsManager().getRequiredForOsMoby().get(slot+1)) {
                return;
            }

            accepted[slot] = "true";
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
            Bukkit.getServer().broadcastMessage(Utils.format(Utils.SERVERNAME + "&3Gracz &f" + player.getName() + " &3odebral osiagniecie &f" + Utils.removeColor(e.getCurrentItem().getItemMeta().getDisplayName())));
            noweOsMobyAccepted.setLength(0);
            return;
        }

        if (e.getClickedInventory().getName().equals(rpgcore.getOsManager().osGraczeGUI(playerUUID).getName())) {
            final int slot = e.getSlot();
            if (e.getCurrentItem().getType() == Material.AIR) {
                e.setCancelled(true);
                return;
            }

            String[] accepted = rpgcore.getPlayerManager().getOsLudzieAccept(playerUUID).split(",");

            if (accepted[slot].equalsIgnoreCase("true")) {
                e.setCancelled(true);
                return;
            }

            if (slot != 0 && accepted[slot-1].equalsIgnoreCase("false")) {
                e.setCancelled(true);
                return;
            }

            final int killedPlayers = rpgcore.getPlayerManager().getPlayerOsLudzie(playerUUID);

            if (killedPlayers < rpgcore.getOsManager().getRequiredForOsLudzie().get(slot+1)) {
                e.setCancelled(true);
                return;
            }


            accepted[slot] = "true";
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
            Bukkit.getServer().broadcastMessage(Utils.format(Utils.SERVERNAME + "&3Gracz &f" + player.getName() + " &3odebral osiagniecie &f" + Utils.removeColor(e.getCurrentItem().getItemMeta().getDisplayName())));
            noweOsLudzieAccepted.setLength(0);
            return;
        }

        if (e.getClickedInventory().getName().equals(rpgcore.getOsManager().osSakwyGUI(playerUUID).getName())) {
            final int slot = e.getSlot();
            if (e.getCurrentItem().getType() == Material.AIR) {
                e.setCancelled(true);
                return;
            }

            String[] accepted = rpgcore.getPlayerManager().getOsSakwyAccept(playerUUID).split(",");

            if (accepted[slot].equalsIgnoreCase("true")) {
                e.setCancelled(true);
                return;
            }

            if (slot != 0 && accepted[slot-1].equalsIgnoreCase("false")) {
                e.setCancelled(true);
                return;
            }

            final int dropped = rpgcore.getPlayerManager().getPlayerOsSakwy(playerUUID);

            if (dropped < rpgcore.getOsManager().getRequiredForOsSakwy().get(slot+1)) {
                e.setCancelled(true);
                return;
            }

            accepted[slot] = "true";
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
            Bukkit.getServer().broadcastMessage(Utils.format(Utils.SERVERNAME + "&3Gracz &f" + player.getName() + " &3odebral osiagniecie &f" + Utils.removeColor(e.getCurrentItem().getItemMeta().getDisplayName())));
            noweOsSakwyAccepted.setLength(0);
            return;
        }

        if (e.getClickedInventory().getName().equals(rpgcore.getOsManager().osNiesyGUI(playerUUID).getName())) {
            final int slot = e.getSlot();
            if (e.getCurrentItem().getType() == Material.AIR) {
                e.setCancelled(true);
                return;
            }

            String[] accepted = rpgcore.getPlayerManager().getOsNiesyAccept(playerUUID).split(",");

            if (accepted[slot].equalsIgnoreCase("true")) {
                e.setCancelled(true);
                return;
            }

            if (slot != 0 && accepted[slot-1].equalsIgnoreCase("false")) {
                e.setCancelled(true);
                return;
            }

            final int dropped = rpgcore.getPlayerManager().getPlayerOsNiesy(playerUUID);

            if (dropped < rpgcore.getOsManager().getRequiredForOsNiesy().get(slot+1)) {
                e.setCancelled(true);
                return;
            }

            accepted[slot] = "true";
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
            Bukkit.getServer().broadcastMessage(Utils.format(Utils.SERVERNAME + "&3Gracz &f" + player.getName() + " &3odebral osiagniecie &f" + Utils.removeColor(e.getCurrentItem().getItemMeta().getDisplayName())));
            noweOsNiesyAccepted.setLength(0);
            return;
        }

        if (e.getClickedInventory().getName().equals(rpgcore.getOsManager().osRybakGUI(playerUUID).getName())) {
            final int slot = e.getSlot();
            if (e.getCurrentItem().getType() == Material.AIR) {
                e.setCancelled(true);
                return;
            }

            String[] accepted = rpgcore.getPlayerManager().getOsRybakAccept(playerUUID).split(",");

            if (accepted[slot].equalsIgnoreCase("true")) {
                e.setCancelled(true);
                return;
            }

            if (slot != 0 && accepted[slot-1].equalsIgnoreCase("false")) {
                e.setCancelled(true);
                return;
            }

            final int dropped = rpgcore.getPlayerManager().getPlayerOsRybak(playerUUID);

            if (dropped < rpgcore.getOsManager().getRequiredForOsRybak().get(slot+1)) {
                e.setCancelled(true);
                return;
            }

            accepted[slot] = "true";
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
            Bukkit.getServer().broadcastMessage(Utils.format(Utils.SERVERNAME + "&3Gracz &f" + player.getName() + " &3odebral osiagniecie &f" + Utils.removeColor(e.getCurrentItem().getItemMeta().getDisplayName())));
            noweOsRybakAccepted.setLength(0);
            return;
        }

        if (e.getClickedInventory().getName().equals(rpgcore.getOsManager().osDrwalGUI(playerUUID).getName())) {
            final int slot = e.getSlot();
            if (e.getCurrentItem().getType() == Material.AIR) {
                e.setCancelled(true);
                return;
            }

            String[] accepted = rpgcore.getPlayerManager().getOsDrwalAccept(playerUUID).split(",");

            if (accepted[slot].equalsIgnoreCase("true")) {
                e.setCancelled(true);
                return;
            }

            if (slot != 0 && accepted[slot-1].equalsIgnoreCase("false")) {
                e.setCancelled(true);
                return;
            }

            final int dropped = rpgcore.getPlayerManager().getPlayerOsDrwal(playerUUID);

            if (dropped < rpgcore.getOsManager().getRequiredForOsDrwal().get(slot+1)) {
                e.setCancelled(true);
                return;
            }

            accepted[slot] = "true";
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
            Bukkit.getServer().broadcastMessage(Utils.format(Utils.SERVERNAME + "&3Gracz &f" + player.getName() + " &3odebral osiagniecie &f" + Utils.removeColor(e.getCurrentItem().getItemMeta().getDisplayName())));
            noweOsDrwalAccepted.setLength(0);
            return;
        }

        if (e.getClickedInventory().getName().equals(rpgcore.getOsManager().osGornikGUI(playerUUID).getName())) {
            final int slot = e.getSlot();
            if (e.getCurrentItem().getType() == Material.AIR) {
                e.setCancelled(true);
                return;
            }

            String[] accepted = rpgcore.getPlayerManager().getOsGornikAccept(playerUUID).split(",");

            if (accepted[slot].equalsIgnoreCase("true")) {
                e.setCancelled(true);
                return;
            }

            if (slot != 0 && accepted[slot-1].equalsIgnoreCase("false")) {
                e.setCancelled(true);
                return;
            }

            final int dropped = rpgcore.getPlayerManager().getPlayerOsGornik(playerUUID);

            if (dropped < rpgcore.getOsManager().getRequiredForOsGornik().get(slot+1)) {
                e.setCancelled(true);
                return;
            }

            accepted[slot] = "true";
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
            Bukkit.getServer().broadcastMessage(Utils.format(Utils.SERVERNAME + "&3Gracz &f" + player.getName() + " &3odebral osiagniecie &f" + Utils.removeColor(e.getCurrentItem().getItemMeta().getDisplayName())));
            noweOsGornikAccepted.setLength(0);
        }
    }
}
