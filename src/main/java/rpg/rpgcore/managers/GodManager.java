package rpg.rpgcore.managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.ArrayList;
import java.util.UUID;

public class GodManager {

    private final RPGCORE rpgcore;

    public GodManager(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    private final ArrayList<UUID> godList = new ArrayList<>();
    private int taskID;

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
            taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(rpgcore, new SendGodBar(rpgcore, p), 0L, 40L);
            this.addUserToGodList(uuid);
            return;
        }

        p.sendMessage(Utils.format("&cWylaczono&7 goda"));
        Bukkit.getScheduler().cancelTask(taskID);
        this.removeUserFromGodList(uuid);

    }

}
