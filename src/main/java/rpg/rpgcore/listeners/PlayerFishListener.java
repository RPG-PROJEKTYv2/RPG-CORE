package rpg.rpgcore.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.util.Vector;
import rpg.rpgcore.RPGCORE;

public class PlayerFishListener implements Listener {

    private final RPGCORE rpgcore;

    public PlayerFishListener(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }


    @EventHandler(priority = EventPriority.LOWEST)
    public void onFish(final PlayerFishEvent e) {

        e.setExpToDrop(0);
        if (e.getState() == PlayerFishEvent.State.CAUGHT_FISH || e.getState() == PlayerFishEvent.State.CAUGHT_ENTITY) {

            Location hook = e.getHook().getLocation();

            Bukkit.getServer().getScheduler().runTaskLater(rpgcore, () -> {
                Entity mob = Bukkit.getWorld(e.getPlayer().getWorld().getName()).spawnEntity(hook, EntityType.ZOMBIE);

                Vector lookingAt = e.getPlayer().getLocation().getDirection().normalize();
                e.getPlayer().sendMessage(lookingAt + " - tu patrzysz");

                double pushX = e.getPlayer().getLocation().getDirection().normalize().getX() * -2;
                double pushY = e.getPlayer().getLocation().getDirection().normalize().getY() * -2;
                double pushZ = e.getPlayer().getLocation().getDirection().normalize().getZ() * -2;


                Vector push = new Vector(pushX, pushY, pushZ);

                mob.setVelocity(push);



            }, 1L);



            e.getPlayer().sendMessage("dzial?");
            e.getCaught().remove();
            return;
        }
    }
}
