package rpg.rpgcore.admin.vanish;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.ArrayList;
import java.util.UUID;

public class VanishManager {

    private final RPGCORE rpgcore;
    private final ArrayList<UUID> vanishList = new ArrayList<>();
    private BukkitTask task;

    public VanishManager(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    public ArrayList<UUID> getVanishList() {
        return vanishList;
    }

    public boolean isVisible(final UUID uuid) {
        return vanishList.contains(uuid);
    }

    public void showPlayer(final Player target) {
        for (Player restOfTheServer : Bukkit.getOnlinePlayers()) {
            restOfTheServer.showPlayer(target);
        }
        task.cancel();
        this.vanishList.remove(target.getUniqueId());
        target.sendMessage(Utils.format(Utils.SERVERNAME + "&cWylaczono &7vanisha"));
    }

    public void showPlayer(final Player sender, final Player target) {
        for (Player restOfTheServer : Bukkit.getOnlinePlayers()) {
            restOfTheServer.showPlayer(target);
        }
        this.vanishList.remove(target.getUniqueId());
        task.cancel();
        sender.sendMessage(Utils.format(Utils.SERVERNAME + "&cWylaczono &7vanisha dla gracza: " + target.getName()));
        target.sendMessage(Utils.format(Utils.SERVERNAME + "&cWylaczono &7vanisha"));
    }

    public void hidePlayer(final Player target) {
        for (Player restOfTheServer : Bukkit.getOnlinePlayers()) {
            if (!(restOfTheServer.hasPermission("rpg.vanish.see_hidden_players"))) {
                restOfTheServer.hidePlayer(target);
            }
        }
        rpgcore.getNmsManager().sendActionBar(target, "&3&lVanish");
        task = Bukkit.getScheduler().runTaskTimerAsynchronously(rpgcore, new SendVanishBar(rpgcore, target), 0L, 40L);
        this.vanishList.add(target.getUniqueId());
        target.sendMessage(Utils.format(Utils.SERVERNAME + "&aWlaczono &7vanisha"));
    }

    public void hidePlayer(final Player sender, final Player target) {
        for (Player restOfTheServer : Bukkit.getOnlinePlayers()) {
            if (!(restOfTheServer.hasPermission("rpg.vanish.see_hidden_players"))) {
                restOfTheServer.hidePlayer(target);
            }
        }
        this.vanishList.add(target.getUniqueId());
        rpgcore.getNmsManager().sendActionBar(target, "&3&lVanish");
        task = Bukkit.getScheduler().runTaskTimerAsynchronously(rpgcore, new SendVanishBar(rpgcore, target), 0L, 40L);
        target.sendMessage(Utils.format(Utils.SERVERNAME + "&aWlaczono &7vanisha"));
        sender.sendMessage(Utils.format(Utils.SERVERNAME + "&aWlaczono &7vanisha dla gracza: " + target.getName()));
    }
}
