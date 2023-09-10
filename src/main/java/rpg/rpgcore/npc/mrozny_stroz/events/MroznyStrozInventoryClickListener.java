package rpg.rpgcore.npc.mrozny_stroz.events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.npc.mrozny_stroz.enums.MroznyStrozMissions;
import rpg.rpgcore.npc.mrozny_stroz.objects.MroznyStrozUser;
import rpg.rpgcore.utils.Utils;

public class MroznyStrozInventoryClickListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onClick(final InventoryClickEvent e) {
        final Inventory gui = e.getClickedInventory();
        final Player player = (Player) e.getWhoClicked();

        if (e.getClickedInventory() == null || e.getInventory() == null) {
            return;
        }

        final String title = Utils.removeColor(gui.getTitle());
        final ItemStack item = e.getCurrentItem();

        if (title.equals("Mrozny Stroz")) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
            if (item == null || item.getType() == Material.STAINED_GLASS_PANE || item.getItemMeta().getLore().stream().anyMatch(s -> s.contains("WYKONANA!"))) return;
            final MroznyStrozUser user = RPGCORE.getInstance().getMroznyStrozNPC().find(player.getUniqueId());
            final MroznyStrozMissions mission = MroznyStrozMissions.getMissionById(user.getMission());

            if (user.getProgress() >= mission.getReqAmount()) {
                user.setProgress(0);
                user.setMission(user.getMission() + 1);
                player.getInventory().addItem(mission.getReward());
                if (user.getMission() == 9) {
                    Bukkit.broadcastMessage(Utils.format("&b&lMrozny Stroz &8>> &fGracz &b" + player.getName() + " &fukonczyl moje &4&lWSZYSTKIE misje!"));
                } else {
                    Bukkit.broadcastMessage(Utils.format("&b&lMrozny Stroz &8>> &fGracz &b" + player.getName() + " &fukonczyl moja &b" + mission.getMissionId() + " &f misje!"));
                }
                RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataMroznyStroz(user.getUuid(), user));
                player.closeInventory();
            }
        }
    }
}
