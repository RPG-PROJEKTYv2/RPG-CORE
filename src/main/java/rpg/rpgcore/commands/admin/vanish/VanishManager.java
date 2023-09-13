package rpg.rpgcore.commands.admin.vanish;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.ArrayList;
import java.util.UUID;

public class VanishManager {

    private final RPGCORE rpgcore;
    private final ArrayList<UUID> vanishList = new ArrayList<>();

    public VanishManager(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    public ArrayList<UUID> getVanishList() {
        return vanishList;
    }

    public boolean isVanished(final UUID uuid) {
        return vanishList.contains(uuid);
    }

    public void showPlayer(final Player target) {
        for (Player restOfTheServer : Bukkit.getOnlinePlayers()) {
            restOfTheServer.showPlayer(target);
        }
        this.vanishList.remove(target.getUniqueId());
        target.sendMessage(Utils.format(Utils.SERVERNAME + "&cWylaczono &7vanisha"));
    }

    public void showPlayer(final Player sender, final Player target) {
        for (Player restOfTheServer : Bukkit.getOnlinePlayers()) {
            restOfTheServer.showPlayer(target);
        }
        this.vanishList.remove(target.getUniqueId());
        sender.sendMessage(Utils.format(Utils.SERVERNAME + "&cWylaczono &7vanisha dla gracza: " + target.getName()));
        target.sendMessage(Utils.format(Utils.SERVERNAME + "&cWylaczono &7vanisha"));
    }

    public void hidePlayer(final Player target) {
        for (Player restOfTheServer : Bukkit.getOnlinePlayers()) {
            if (rpgcore.getUserManager().find(restOfTheServer.getUniqueId()).getRankUser().isHighStaff() && rpgcore.getUserManager().find(restOfTheServer.getUniqueId()).isAdminCodeLogin()) {
                continue;
            }
            restOfTheServer.hidePlayer(target);
        }
        if (!this.vanishList.contains(target.getUniqueId())) {
            this.vanishList.add(target.getUniqueId());
            target.sendMessage(Utils.format(Utils.SERVERNAME + "&aWlaczono &7vanisha"));
        }
    }

    public void hidePlayer(final Player sender, final Player target) {
        for (Player restOfTheServer : Bukkit.getOnlinePlayers()) {
            if (rpgcore.getUserManager().find(restOfTheServer.getUniqueId()).getRankUser().isHighStaff() && rpgcore.getUserManager().find(restOfTheServer.getUniqueId()).isAdminCodeLogin()) {
                continue;
            }
            restOfTheServer.hidePlayer(target);
        }
        this.vanishList.add(target.getUniqueId());
        target.sendMessage(Utils.format(Utils.SERVERNAME + "&aWlaczono &7vanisha"));
        sender.sendMessage(Utils.format(Utils.SERVERNAME + "&aWlaczono &7vanisha dla gracza: " + target.getName()));
    }
}
