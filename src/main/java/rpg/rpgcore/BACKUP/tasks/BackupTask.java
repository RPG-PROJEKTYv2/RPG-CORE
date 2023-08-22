package rpg.rpgcore.BACKUP.tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import rpg.rpgcore.BACKUP.objects.Backup;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BackupTask implements Runnable {

    public BackupTask(RPGCORE rpgcore) {
        rpgcore.getServer().getScheduler().scheduleAsyncRepeatingTask(rpgcore, this, 0L, 36_000L);
    }

    @Override
    public void run() {
        final String date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
        final long start = System.currentTimeMillis();
        for (final Player player : Bukkit.getOnlinePlayers()) {
            final Backup backup = new Backup(player.getUniqueId(), date);
            backup.save();
        }
        Utils.sendToHighStaff("&6&lBACKUP &8>> &aBackup wszystkich online graczy zostal wykonany w &e" + (System.currentTimeMillis() - start) + "ms&a.");
    }
}
