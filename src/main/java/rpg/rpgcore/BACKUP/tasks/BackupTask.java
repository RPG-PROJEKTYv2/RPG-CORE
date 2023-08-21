package rpg.rpgcore.BACKUP.tasks;

import rpg.rpgcore.BACKUP.objects.Backup;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BackupTask implements Runnable {
    private final RPGCORE rpgcore;

    public BackupTask(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
        this.rpgcore.getServer().getScheduler().scheduleAsyncRepeatingTask(this.rpgcore, this, 0L, 36_000L);
    }

    @Override
    public void run() {
        final String date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
        final long start = System.currentTimeMillis();
        for (final User user : rpgcore.getUserManager().getUserObjects()) {
            final Backup backup = new Backup(user.getId(), date);
            backup.save();
        }
        Utils.sendToHighStaff("&6&lBACKUP &8>> &aBackup wszystkich graczy zostal wykonany w &e" + (System.currentTimeMillis() - start) + "ms&a.");
    }
}
