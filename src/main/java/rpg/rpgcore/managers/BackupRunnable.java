package rpg.rpgcore.managers;

import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

public class BackupRunnable implements Runnable {

    private final RPGCORE rpgcore;

    public BackupRunnable(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @Override
    public void run() {

        rpgcore.getServer().broadcastMessage(Utils.format(Utils.SERVERNAME + "&6Rozpoczeto update do bazy daynch..."));

        for (final UUID uuid : rpgcore.getPlayerManager().getPlayers()) {

            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getSQLManager().updatePlayerExp(uuid, rpgcore.getPlayerManager().getPlayerExp(uuid)));

            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getSQLManager().updatePlayerLvl(uuid, rpgcore.getPlayerManager().getPlayerLvl(uuid)));

            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getSQLManager().updatePlayerBaoBonusy(uuid, rpgcore.getBaoManager().getBaoBonusy(uuid)));

            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getSQLManager().updatePlayerBaoWartosci(uuid, rpgcore.getBaoManager().getBaoBonusyWartosci(uuid)));

            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getSQLManager().updatePlayerOsAccept(uuid, rpgcore.getPlayerManager().getOsMobyAccept(uuid)));
        }

        rpgcore.getServer().broadcastMessage(Utils.format(Utils.SERVERNAME + "&aUpdate zakonczony pomyslnie!"));

    }
}
