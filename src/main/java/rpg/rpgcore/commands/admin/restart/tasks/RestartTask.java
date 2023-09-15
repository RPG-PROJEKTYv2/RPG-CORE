package rpg.rpgcore.commands.admin.restart.tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.commands.admin.restart.RestartManager;
import rpg.rpgcore.commands.admin.restart.objects.Restart;
import rpg.rpgcore.utils.Utils;

public class RestartTask implements Runnable {
    private final Restart restart;
    private final RPGCORE rpgcore;
    public RestartTask(final RPGCORE rpgcore) {
        this.restart = RestartManager.restart;
        this.rpgcore = rpgcore;
        this.restart.setTaskId(this.rpgcore.getServer().getScheduler().scheduleSyncRepeatingTask(this.rpgcore, this, 0L, 20L));
    }

    @Override
    public void run() {
        if (!this.restart.isRestarting()) {
            if (this.restart.getTaskId() == -1) return;
            this.rpgcore.getServer().getScheduler().cancelTask(this.restart.getTaskId());
            return;
        }
        if (this.restart.getSeconds() == 0) {
            this.rpgcore.getServer().getScheduler().cancelTask(this.restart.getTaskId());
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.kickPlayer(Utils.format(Utils.CLEANSERVERNAME + "\n&cAktualnie Trwa Restart Serwera!\n&7Zapraszamy ponownie za chwile"));
            }
            System.out.println(Utils.format(Utils.SERVERNAME + "&aZapisano graczy i wylogowano"));
            System.out.println(Utils.format(Utils.SERVERNAME + "&aSerwer zostanie wylaczony za 10 sekundy..."));
            RPGCORE.getInstance().getSerwerWhiteListManager().getWhitelist().setEnabled(true);
            Bukkit.getServer().getScheduler().runTaskLater(RPGCORE.getInstance(), () -> Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "restart"), 200L);
            return;
        }
        if (this.restart.getSeconds() == 180 || this.restart.getSeconds() == 120 || this.restart.getSeconds() == 60 || this.restart.getSeconds() == 30 || this.restart.getSeconds() == 10 || this.restart.getSeconds() <= 5) {
            Bukkit.broadcastMessage(Utils.format("&8&l[&4&lRESTART&8&l] &cZa &b" + this.restart.getSeconds() + " &csekund odbedzie sie restart serwera!"));
        }
        this.restart.setSeconds(this.restart.getSeconds() - 1);
    }
}
