package rpg.rpgcore.managers.god;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.ArrayList;
import java.util.UUID;

public class GodManager {

    private final RPGCORE rpgcore;
    private final ArrayList<UUID> godList = new ArrayList<>();
    private BukkitTask task;

    public GodManager(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    public boolean containsPlayer(final UUID uuid) {
        return godList.contains(uuid);
    }

    public void addUserToGodList(final UUID uuid) {
        this.godList.add(uuid);
    }

    public void removeUserFromGodList(final UUID uuid) {
        this.godList.remove(uuid);
    }

    public void changeStatusGod(final Player p) {

        final UUID uuid = p.getUniqueId();

        if (!(this.containsPlayer(uuid))) {
            p.sendMessage(Utils.format("&aWlaczono&7 goda"));
            task = Bukkit.getScheduler().runTaskTimerAsynchronously(rpgcore, new SendGodBar(rpgcore, p), 0L, 40L);
            this.addUserToGodList(uuid);
            return;
        }

        p.sendMessage(Utils.format("&cWylaczono&7 goda"));
        task.cancel();
        this.removeUserFromGodList(uuid);

    }

}
