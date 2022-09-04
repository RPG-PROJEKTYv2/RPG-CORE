package rpg.rpgcore.dungeons.niebiosa;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

public class NiebiosaManager {

    private final RPGCORE rpgcore;
    private boolean isActive;
    private final BukkitScheduler scheduler = Bukkit.getServer().getScheduler();

    public NiebiosaManager(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
        this.isActive = false;
    }

    public void start(final Player player) {
        final World w = Bukkit.getWorld("60-70exp");
        Bukkit.getServer().broadcastMessage(Utils.format("&b&lBrama do Niebios zostala wlasnie otwarta przez &f" + player.getName()));
        this.removeGate(w);
    }


    private void removeGate(final World w) {
        scheduler.runTaskLater(rpgcore, () -> {
            w.getBlockAt(new Location(w, 3, 4, 1)).setType(Material.AIR);
            w.getBlockAt(new Location(w, 2, 4, 1)).setType(Material.AIR);
            scheduler.runTaskLater(rpgcore, () -> {
                w.getBlockAt(new Location(w, 3, 5, 1)).setType(Material.AIR);
                w.getBlockAt(new Location(w, 2, 5, 1)).setType(Material.AIR);
                scheduler.runTaskLater(rpgcore, () -> {
                    w.getBlockAt(new Location(w, 3, 6, 1)).setType(Material.AIR);
                    w.getBlockAt(new Location(w, 2, 6, 1)).setType(Material.AIR);
                    //this.setActive(true);
                    rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> this.setGate(w), 200L);
                }, 20L);
            }, 20L);
        }, 20L);
    }

    @Deprecated
    private void setGate(final World w) {
        scheduler.runTaskLater(rpgcore, () -> {
            w.getBlockAt(new Location(w, 3, 6, 1)).setType(Material.COBBLE_WALL);
            w.getBlockAt(new Location(w, 3, 6, 1)).setData((byte) 1);
            w.getBlockAt(new Location(w, 2, 6, 1)).setType(Material.COBBLE_WALL);
            scheduler.runTaskLater(rpgcore, () -> {
                w.getBlockAt(new Location(w, 3, 5, 1)).setType(Material.COBBLE_WALL);
                w.getBlockAt(new Location(w, 3, 5, 1)).setData((byte) 1);
                w.getBlockAt(new Location(w, 2, 5, 1)).setType(Material.COBBLE_WALL);
                scheduler.runTaskLater(rpgcore, () -> {
                    w.getBlockAt(new Location(w, 3, 4, 1)).setType(Material.COBBLE_WALL);
                    w.getBlockAt(new Location(w, 2, 4, 1)).setType(Material.COBBLE_WALL);
                    w.getBlockAt(new Location(w, 2, 4, 1)).setData((byte) 1);
                    Bukkit.broadcastMessage(Utils.format("&b&lBrama do Niebios zostala zamknieta"));
                }, 20L);
            }, 20L);
        }, 20L);
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
}
