package rpg.rpgcore.npc.wyslannik;

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
import rpg.rpgcore.npc.wyslannik.enums.WyslannikMissionKillBoss;
import rpg.rpgcore.npc.wyslannik.enums.WyslannikMissionKillMob;
import rpg.rpgcore.npc.wyslannik.enums.WyslannikMissionOpen;
import rpg.rpgcore.npc.wyslannik.objects.WyslannikUser;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

public class WyslannikInventoryClickListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClick(final InventoryClickEvent e) {

        if (e.getInventory() == null || e.getClickedInventory() == null) {
            return;
        }

        final Inventory clickedInventory = e.getClickedInventory();
        final Player player = (Player) e.getWhoClicked();
        final UUID uuid = player.getUniqueId();
        final String title = Utils.removeColor(clickedInventory.getTitle());
        final ItemStack item = e.getCurrentItem();
        final int slot = e.getSlot();

        if (title.equals("Wyslannik")) {
            e.setCancelled(true);

            if (item == null || item.getType() == Material.BARRIER || item.getType() == Material.STAINED_GLASS_PANE) {
                return;
            }
            final WyslannikUser user = RPGCORE.getInstance().getWyslannikNPC().find(uuid);
            if (slot == 0) {
                final WyslannikMissionKillMob mission = WyslannikMissionKillMob.getByMission(user.getKillMobsMission());
                assert mission != null;
                if (user.getKillMobsMissionProgress() >= mission.getReqAmount()) {
                    user.addKillMobsMission();
                    user.resetKillMobsMissionProgress();
                    player.getInventory().addItem(mission.getReward());
                    RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataWyslannik(uuid, user));
                    Bukkit.broadcastMessage(Utils.format("&c&lWyslannik &8>> &7Gracz &c" + player.getName() + " &7ukonczyl &c" + mission.getMission() + " &7misje! &8(moby)"));
                    RPGCORE.getInstance().getWyslannikNPC().openGUI(player);
                    return;
                }
                return;
            }
            if (slot == 2) {
                final WyslannikMissionKillBoss mission = WyslannikMissionKillBoss.getByMission(user.getKillBossMission());
                assert mission != null;
                if (user.getKillBossMissionProgress() >= mission.getReqAmount()) {
                    user.addKillBossMission();
                    user.resetKillBossMissionProgress();
                    player.getInventory().addItem(mission.getReward());
                    RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataWyslannik(uuid, user));
                    Bukkit.broadcastMessage(Utils.format("&c&lWyslannik &8>> &7Gracz &c" + player.getName() + " &7ukonczyl &c" + mission.getMission() + " &7misje! &8(bossy)"));
                    RPGCORE.getInstance().getWyslannikNPC().openGUI(player);
                    return;
                }
                return;
            }

            if (slot == 4) {
                final WyslannikMissionOpen mission = WyslannikMissionOpen.getByMission(user.getOpenChestMission());
                assert mission != null;
                if (user.getOpenChestMissionProgress() >= mission.getReqAmount()) {
                    user.addOpenChestMission();
                    user.resetOpenChestMissionProgress();
                    player.getInventory().addItem(mission.getReward());
                    RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataWyslannik(uuid, user));
                    Bukkit.broadcastMessage(Utils.format("&c&lWyslannik &8>> &7Gracz &c" + player.getName() + " &7ukonczyl &c" + mission.getMission() + " &7misje! &8(skrzynie)"));
                    RPGCORE.getInstance().getWyslannikNPC().openGUI(player);
                }
            }
        }
    }
}
