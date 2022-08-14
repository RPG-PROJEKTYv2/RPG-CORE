package rpg.rpgcore.dungeons.niebiosa;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

public class NiebiosaManager {

    private final RPGCORE rpgcore;
    private boolean isActive;

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
        w.getBlockAt(new Location(w, 1, 4, 0)).setType(Material.AIR);
        w.getBlockAt(new Location(w, 0, 4, 0)).setType(Material.AIR);
        w.getBlockAt(new Location(w, 1, 5, 0)).setType(Material.AIR);
        w.getBlockAt(new Location(w, 0, 5, 0)).setType(Material.AIR);
        w.getBlockAt(new Location(w, 1, 6, 0)).setType(Material.AIR);
        w.getBlockAt(new Location(w, 0, 6, 0)).setType(Material.AIR);
        this.setActive(true);
        rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> this.setGate(w), 200L);
    }

    private void setGate(final World w) {
        w.getBlockAt(new Location(w, 1, 4, 0)).setType(Material.COBBLE_WALL);
        w.getBlockAt(new Location(w, 0, 4, 0)).setType(Material.COBBLE_WALL);
        w.getBlockAt(new Location(w, 1, 5, 0)).setType(Material.COBBLE_WALL);
        w.getBlockAt(new Location(w, 0, 5, 0)).setType(Material.COBBLE_WALL);
        w.getBlockAt(new Location(w, 1, 6, 0)).setType(Material.COBBLE_WALL);
        w.getBlockAt(new Location(w, 0, 6, 0)).setType(Material.COBBLE_WALL);
        Bukkit.broadcastMessage(Utils.format("&b&lBrama do Niebios zostala zamknieta"));
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
}
