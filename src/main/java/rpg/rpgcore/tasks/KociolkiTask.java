package rpg.rpgcore.tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.bonuses.Bonuses;
import rpg.rpgcore.kociolki.KociolkiUser;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.Utils;

public class KociolkiTask implements Runnable {
    private final RPGCORE rpgcore;

    public KociolkiTask(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
        this.rpgcore.getServer().getScheduler().scheduleSyncRepeatingTask(rpgcore, this, 0L, 20L);
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            final KociolkiUser kociolkiUser = rpgcore.getKociolkiManager().find(player.getUniqueId());
            if (kociolkiUser.isEliksirObroncy()) {
                if (kociolkiUser.getEliksirObroncyTime() > 0) {
                    kociolkiUser.setEliksirObroncyTime(kociolkiUser.getEliksirObroncyTime() - 1);
                    if (kociolkiUser.getEliksirObroncyTime() == 0) {
                        kociolkiUser.setEliksirObroncy(false);
                        rpgcore.getBonusesManager().find(player.getUniqueId()).getBonusesUser().setSredniadefensywa(rpgcore.getBonusesManager().find(player.getUniqueId()).getBonusesUser().getSredniadefensywa() - 10);
                        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
                            rpgcore.getMongoManager().saveDataBonuses(player.getUniqueId(), rpgcore.getBonusesManager().find(player.getUniqueId()));
                            rpgcore.getMongoManager().saveDataKociolki(player.getUniqueId(), kociolkiUser);
                        });
                        player.sendMessage(Utils.format("&4&lArtefakty &8>> &7Twoj bonus z &6&lEliksiru Obroncy &7dobiegl konca"));
                    }
                }
            }
            if (kociolkiUser.isEliksirPotegi()) {
                if (kociolkiUser.getEliksirPotegiTime() > 0) {
                    kociolkiUser.setEliksirPotegiTime(kociolkiUser.getEliksirPotegiTime() - 1);
                    if (kociolkiUser.getEliksirPotegiTime() == 0) {
                        kociolkiUser.setEliksirPotegi(false);
                        rpgcore.getBonusesManager().find(player.getUniqueId()).getBonusesUser().setSrednieobrazenia(rpgcore.getBonusesManager().find(player.getUniqueId()).getBonusesUser().getSrednieobrazenia() - 10);
                        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
                            rpgcore.getMongoManager().saveDataBonuses(player.getUniqueId(), rpgcore.getBonusesManager().find(player.getUniqueId()));
                            rpgcore.getMongoManager().saveDataKociolki(player.getUniqueId(), kociolkiUser);
                        });
                        player.sendMessage(Utils.format("&4&lArtefakty &8>> &7Twoj bonus z &6&lEliksiru Potegi &7dobiegl konca"));
                    }
                }
            }
            if (kociolkiUser.isEgzekutor()) {
                if (kociolkiUser.getEgzekutorTime() > 0) {
                    kociolkiUser.setEgzekutorTime(kociolkiUser.getEgzekutorTime() - 1);
                    if (kociolkiUser.getEgzekutorTime() == 0) {
                        kociolkiUser.setEgzekutor(false);
                        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataKociolki(player.getUniqueId(), kociolkiUser));
                        player.sendMessage(Utils.format("&4&lArtefakty &8>> &7Twoj bonus z &c&lEgzekutora &7dobiegl konca"));
                    }
                }
            }
            final User user = this.rpgcore.getUserManager().find(player.getUniqueId());
            if (user.getPierscienDoswiadczeniaTime() <= System.currentTimeMillis() && user.getPierscienDoswiadczenia() != 0) {
                final Bonuses bonuses = RPGCORE.getInstance().getBonusesManager().find(user.getId());
                bonuses.getBonusesUser().setDodatkowyExp(bonuses.getBonusesUser().getDodatkowyExp() - user.getPierscienDoswiadczenia());
                user.setPierscienDoswiadczenia(0);
                user.setPierscienDoswiadczeniaTime(0L);
                RPGCORE.getInstance().getNmsManager().sendTitleAndSubTitle(player, RPGCORE.getInstance().getNmsManager().makeTitle("&8&l[&4&l!&8&l]", 5, 20, 5), RPGCORE.getInstance().getNmsManager().makeSubTitle("&cTwoj &ePierscien Doswiadczenia &cdobiegl konca", 5, 20, 5));
                RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> {
                    RPGCORE.getInstance().getMongoManager().saveDataUser(user.getId(), user);
                    RPGCORE.getInstance().getMongoManager().saveDataBonuses(user.getId(), bonuses);
                });
            }
        }
    }
}
