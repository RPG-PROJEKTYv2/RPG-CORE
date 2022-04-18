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

            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getSQLManager().updatePlayerAkcesoria(uuid, Utils.itemStackArrayToBase64(rpgcore.getAkcesoriaManager().getAllAkcesoria(uuid))));
        }

        rpgcore.getServer().broadcastMessage(Utils.format(Utils.SERVERNAME + "&aUpdate zakonczony pomyslnie!"));

    }


    /*
    public class SaveDataBaseTask {

    private final ConcurrentMap<String, Integer> playerMap;
    private final Main main;

    public SaveDataBaseTask(final Main main) {
        this.playerMap = new ConcurrentHashMap<String, Integer>();
        this.main = main;
        this.onLoad();
    }

    private void onLoad() {
        new BukkitRunnable() {
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    User user = SaveDataBaseTask.this.main.getUserManager().find(player.getUniqueId());
                    if (user.isVerify()) {
                        int seconds = SaveDataBaseTask.this.playerMap.getOrDefault(user.getName(), 300);
                        if (--seconds == 0) {
                            long time = System.currentTimeMillis();

                            time = System.currentTimeMillis() - time;

                            System.out.println("[HypeRPGCore] saved player: " + user.getName() + " " + time + "ms");

                            //MessageHelper.build("&8[&2!&8] &aPomyslnie zapisano Twoj ekwipunek. &f" + DateUtil.getTime()).send(user.getPlayer());
                            seconds = 300;
                        }
                        SaveDataBaseTask.this.playerMap.put(user.getPlayer().getName(), seconds);
                    }
                }
            }
        }.runTaskTimerAsynchronously(this.main, 20L, 20L);
    }
}
     */
}
