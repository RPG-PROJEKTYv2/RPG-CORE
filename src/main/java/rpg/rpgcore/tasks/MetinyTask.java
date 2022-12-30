package rpg.rpgcore.tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.bonuses.Bonuses;
import rpg.rpgcore.dungeons.icetower.IceTowerManager;
import rpg.rpgcore.dungeons.icetower.ResetType;
import rpg.rpgcore.metiny.MetinyHelper;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.Utils;

public class MetinyTask implements Runnable{
    private final RPGCORE rpgcore;

    public MetinyTask(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
        this.rpgcore.getServer().getScheduler().runTaskTimer(this.rpgcore, this, 20L, 6000L);
    }

    @Override
    public void run() {
        MetinyHelper.respAllMetins();
        IceTowerManager.resetIceTower(ResetType.NORMAL);
        this.rpgcore.getServer().broadcastMessage(Utils.format("&b&lMetiny zostaly zresetowane!"));
        for (Player player : Bukkit.getOnlinePlayers()) {
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
