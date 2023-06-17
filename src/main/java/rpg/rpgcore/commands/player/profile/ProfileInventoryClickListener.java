package rpg.rpgcore.commands.player.profile;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

public class ProfileInventoryClickListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    @Deprecated
    public void onClick(final InventoryClickEvent e) {
        if (e.getInventory() == null || e.getClickedInventory() == null) {
            return;
        }

        if (Utils.removeColor(e.getClickedInventory().getTitle()).contains("Profil ")) {
            e.setCancelled(true);
            final int slot = e.getSlot();
            if (slot != 13 && slot != 40 && slot != 48 && slot != 49 && slot != 50) {
                return;
            }
            final Player player = (Player) e.getWhoClicked();
            Player target = Bukkit.getPlayer(Utils.removeColor(e.getClickedInventory().getTitle()).replace("Profil ", "").replace(" (Admin)", ""));
            if (target == null) {
                final OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(Utils.removeColor(e.getClickedInventory().getTitle()).replace("Profil ", "").replace(" (Admin)", ""));
                if (slot == 13) Bukkit.getServer().dispatchCommand(player, "misje " + offlinePlayer.getName());
                if (slot == 40) RPGCORE.getInstance().getMagazynierNPC().openMagazynyList(player, offlinePlayer.getUniqueId());
                if (slot == 48) RPGCORE.getInstance().getDodatkiManager().openAkcePodsGUI(player, offlinePlayer.getUniqueId());
                if (slot == 49) RPGCORE.getInstance().getDodatkiManager().openBonyGUI(player, offlinePlayer.getUniqueId());
                if (slot == 50) RPGCORE.getInstance().getDodatkiManager().openAkceDodaGUI(player, offlinePlayer.getUniqueId());
                return;
            }
            if (target.getName().equals(player.getName())) {
                if (slot == 40) return;
                if (slot == 13) Bukkit.getServer().dispatchCommand(player, "misje");
                if (slot == 48) RPGCORE.getInstance().getDodatkiManager().openAkcePodsGUI(player, player.getUniqueId());
                if (slot == 49) RPGCORE.getInstance().getDodatkiManager().openBonyGUI(player, player.getUniqueId());
                if (slot == 50) RPGCORE.getInstance().getDodatkiManager().openAkceDodaGUI(player, player.getUniqueId());
            } else {
                if (slot == 13) Bukkit.getServer().dispatchCommand(player, "misje " + target.getName());
                if (slot == 40) RPGCORE.getInstance().getMagazynierNPC().openMagazynyList(player, target.getUniqueId());
                if (slot == 48) RPGCORE.getInstance().getDodatkiManager().openAkcePodsGUI(player, target.getUniqueId());
                if (slot == 49) RPGCORE.getInstance().getDodatkiManager().openBonyGUI(player, target.getUniqueId());
                if (slot == 50) RPGCORE.getInstance().getDodatkiManager().openAkceDodaGUI(player, target.getUniqueId());
            }
        }
    }
}
