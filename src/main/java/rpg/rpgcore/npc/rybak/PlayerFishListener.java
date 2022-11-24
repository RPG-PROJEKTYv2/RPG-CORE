package rpg.rpgcore.npc.rybak;

import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

public class PlayerFishListener implements Listener {

    private final RPGCORE rpgcore;

    public PlayerFishListener(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onFish(final PlayerFishEvent e) {
        e.setExpToDrop(0);
        int a = rpgcore.getServer().getScheduler().scheduleSyncRepeatingTask(rpgcore, () -> {
            if (e.getState() == PlayerFishEvent.State.FAILED_ATTEMPT) {
                rpgcore.getServer().getScheduler().cancelTask(rpgcore.getRybakNPC().getTaskId(e.getPlayer().getUniqueId()));
                rpgcore.getRybakNPC().removeTaskId(e.getPlayer().getUniqueId());
                return;
            }
            e.getPlayer().sendMessage(e.getHook().getBiteChance() + "");
            e.getPlayer().sendMessage(e.getState().name());
        }, 1L, 60L);
        rpgcore.getRybakNPC().addTaskId(e.getPlayer().getUniqueId(), a);
        if (e.getState() == PlayerFishEvent.State.FISHING) {
            //final ArmorStand as = (ArmorStand) Bukkit.getWorld(e.getPlayer().getWorld().getName()).spawnEntity(e.getHook().getLocation(), org.bukkit.entity.EntityType.ARMOR_STAND);
            e.getHook().setCustomName(Utils.format("&4&lSIEMA"));
           // rpgcore.getRybakNPC().addArmorStand(e.getPlayer().getUniqueId(), as);
        }
        if (e.getState() == PlayerFishEvent.State.CAUGHT_FISH) {
            e.getHook().setCustomName(Utils.format("&a&lZlapano!"));
        }
    }
}
