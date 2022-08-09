package rpg.rpgcore.tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.server.ServerUser;
import rpg.rpgcore.utils.Utils;

public class ActionBarTask implements Runnable {

    public ActionBarTask(final RPGCORE rpgcore) {
        rpgcore.getServer().getScheduler().runTaskTimer(rpgcore, this, 20L, 20L);
    }

    @Override
    public void run() {
        String actionbar = "";
        if (RPGCORE.getInstance().getServerManager().isServerUser("dodatkowyExp")) {
            ServerUser serverUser = RPGCORE.getInstance().getServerManager().find("dodatkowyExp");
            if (serverUser.getServer().isAktywny()) {
                if (serverUser.getServer().getCzas() < System.currentTimeMillis()) {
                    serverUser.getServer().setAktywny(false);
                    serverUser.getServer().setDodatkowyExp(0);
                    serverUser.getServer().setCzas(0L);
                    RPGCORE.getInstance().getMongoManager().saveOtherData("dodatkowyExp");
                    RPGCORE.getInstance().getServer().broadcastMessage(Utils.format("&8===============&6EXP&8==============="));
                    RPGCORE.getInstance().getServer().broadcastMessage(Utils.format("&7Dodatkowy exp zostal &cwylaczony&7!"));
                    RPGCORE.getInstance().getServer().broadcastMessage(Utils.format("&8===============&6EXP&8==============="));
                }
                for (Player player : Bukkit.getOnlinePlayers()) {
                    RPGCORE.getInstance().getNmsManager().sendActionBar(player, "&b&lDodatkowyExp &9&l" + serverUser.getServer().getDodatkowyExp() + "% &b&ltrwa jeszcze: &f&l" + Utils.durationToString(serverUser.getServer().getCzas() - System.currentTimeMillis(), true));
                }
            }
        }
    }
}

