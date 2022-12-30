package rpg.rpgcore.commands.player.profile;

import org.bukkit.Bukkit;
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
            if (slot != 13 && slot != 48 && slot != 49 && slot != 50) {
                return;
            }
            final Player player = (Player) e.getWhoClicked();
            Player target = Bukkit.getPlayer(Utils.removeColor(e.getClickedInventory().getTitle()).replace("Profil ", ""));
            if (target == null) {
                target = Bukkit.getOfflinePlayer(Utils.removeColor(e.getClickedInventory().getTitle()).replace("Profil ", "")).getPlayer();
            }
            if (target.getName().equals(player.getName())) {
                if (slot == 13) Bukkit.getServer().dispatchCommand(player, "misje");
                if (slot == 48) RPGCORE.getInstance().getDodatkiManager().openAkcePodsGUI(player, player);
                if (slot == 49) RPGCORE.getInstance().getDodatkiManager().openBonyGUI(player, player);
                if (slot == 50) RPGCORE.getInstance().getDodatkiManager().openAkceDodaGUI(player, player);
            } else {
                if (slot == 13) Bukkit.getServer().dispatchCommand(player, "misje " + target.getName());
                if (slot == 48) RPGCORE.getInstance().getDodatkiManager().openAkcePodsGUI(player, target);
                if (slot == 49) RPGCORE.getInstance().getDodatkiManager().openBonyGUI(player, target);
                if (slot == 50) RPGCORE.getInstance().getDodatkiManager().openAkceDodaGUI(player, target);
            }
        }
    }
}
