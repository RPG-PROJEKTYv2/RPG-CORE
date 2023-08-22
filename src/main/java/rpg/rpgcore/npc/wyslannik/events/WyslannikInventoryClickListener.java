package rpg.rpgcore.npc.wyslannik.events;

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
import rpg.rpgcore.bonuses.Bonuses;
import rpg.rpgcore.npc.wyslannik.enums.WyslannikMissionKillBoss;
import rpg.rpgcore.npc.wyslannik.enums.WyslannikMissionKillMob;
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

            final WyslannikUser wyslannikUser = RPGCORE.getInstance().getWyslannikNPC().find(uuid);
            if (slot == 0) {
                final Bonuses bonuses = RPGCORE.getInstance().getBonusesManager().find(uuid);
                final WyslannikMissionKillMob wyslannikMissionKillMob = WyslannikMissionKillMob.getByMission(wyslannikUser.getKillMobsMission());
                assert wyslannikMissionKillMob != null;
                if (wyslannikUser.getKillMobsMissionProgress() >= wyslannikMissionKillMob.getMobsAmount()) {
                    wyslannikUser.setKillMobsMission(wyslannikUser.getKillMobsMission() + 1);
                    wyslannikUser.setKillMobsMissionProgress(0);
                    wyslannikUser.setSredniDMG(wyslannikUser.getSredniDMG() + wyslannikMissionKillMob.getSredniDMG());
                    bonuses.getBonusesUser().setSrednieobrazenia(bonuses.getBonusesUser().getSrednieobrazenia() + wyslannikUser.getSredniDMG());
                    RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> {
                        RPGCORE.getInstance().getMongoManager().saveDataWyslannik(uuid, RPGCORE.getInstance().getWyslannikNPC().find(uuid));
                        RPGCORE.getInstance().getMongoManager().saveDataBonuses(uuid, bonuses);
                    });
                    Bukkit.broadcastMessage(Utils.format("&c&lWyslannik &8>> &7Gracz &c" + player.getName() + " &7ukonczyl &c" + wyslannikMissionKillMob.getMission() + " &7misje! &8(moby)"));
                    RPGCORE.getInstance().getWyslannikNPC().openGUI(player);
                    return;
                }
                return;
            }
            if (slot == 4) {
                final Bonuses bonuses = RPGCORE.getInstance().getBonusesManager().find(uuid);
                final WyslannikMissionKillBoss wyslannikMissionKillBoss = WyslannikMissionKillBoss.getByMission(wyslannikUser.getKillBossMission());
                assert wyslannikMissionKillBoss != null;
                if (wyslannikUser.getKillBossMissionProgress() >= wyslannikMissionKillBoss.getBossAmount()) {
                    wyslannikUser.setKillBossMission(wyslannikUser.getKillBossMission() + 1);
                    wyslannikUser.setKillBossMissionProgress(0);
                    wyslannikUser.setSredniDEF(wyslannikUser.getSredniDEF() + wyslannikMissionKillBoss.getSredniDEF());
                    bonuses.getBonusesUser().setSredniadefensywa(bonuses.getBonusesUser().getSredniadefensywa() + wyslannikUser.getSredniDEF());
                    RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> {
                        RPGCORE.getInstance().getMongoManager().saveDataWyslannik(uuid, RPGCORE.getInstance().getWyslannikNPC().find(uuid));
                        RPGCORE.getInstance().getMongoManager().saveDataBonuses(uuid, bonuses);
                    });
                    Bukkit.broadcastMessage(Utils.format("&c&lWyslannik &8>> &7Gracz &c" + player.getName() + " &7ukonczyl &c" + wyslannikMissionKillBoss.getMission() + " &7misje! &8(bossy)"));
                    RPGCORE.getInstance().getWyslannikNPC().openGUI(player);
                }
            }
        }
    }
}
