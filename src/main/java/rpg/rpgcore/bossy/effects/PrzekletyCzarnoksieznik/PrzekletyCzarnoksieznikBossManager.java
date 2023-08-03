package rpg.rpgcore.bossy.effects.PrzekletyCzarnoksieznik;

import com.google.common.collect.ImmutableSet;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.bonuses.Bonuses;
import rpg.rpgcore.utils.ChanceHelper;
import rpg.rpgcore.utils.Utils;

import java.util.Map;
import java.util.UUID;

public class PrzekletyCzarnoksieznikBossManager {
    private final RPGCORE rpgcore;
    private final Map<UUID, PrzekletyCzarnoksieznikUser> userMap;

    public PrzekletyCzarnoksieznikBossManager(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
        this.userMap = rpgcore.getMongoManager().loadAllPrzekletyCzarnoksieznikUser();
    }

    public void LosBONUS(final Player player) {
        UUID uuid = player.getUniqueId();
        final PrzekletyCzarnoksieznikUser user = this.find(uuid);
        final Bonuses bonuses = rpgcore.getBonusesManager().find(uuid);
        bonuses.getBonusesUser().setSilnynapotwory(bonuses.getBonusesUser().getSilnynapotwory() - user.getDmgMOB());
        bonuses.getBonusesUser().setDefnamoby(bonuses.getBonusesUser().getDefnamoby() - user.getDefMOB());
        final int defMOB = ChanceHelper.getRandInt(Bonus70_80.BS.getDefmobyMIN(), Bonus70_80.BS.getDefmobyMAX());
        final int dmgMOB = ChanceHelper.getRandInt(Bonus70_80.BS.getDmgmobyMIN(), Bonus70_80.BS.getDmgmobyMAX());
        user.setDefMOB(defMOB);
        user.setDmgMOB(dmgMOB);
        Bukkit.broadcastMessage(" ");
        Bukkit.broadcastMessage(Utils.format("&8&l>> &5&lPrzekleta Moc Czarnoksieznika &8&l<<"));
        Bukkit.broadcastMessage(Utils.format("&e" + player.getName() + " &7wylosowal swoja &5&lPrzekleta Moc&7."));
        Bukkit.broadcastMessage(Utils.format("&cSilny Na Potwory: &4+" + dmgMOB + "&4%&7/&c" + Bonus70_80.BS.getDmgmobyMAX() + "%"));
        Bukkit.broadcastMessage(Utils.format("&cDefensywa Przeciwko Potworom: &4+" + defMOB + "&4%&7/&c" + Bonus70_80.BS.getDefmobyMAX() + "%"));
        Bukkit.broadcastMessage(Utils.format("&8&l>> &5&lPrzekleta Moc Czarnoksieznika &8&l<<"));
        Bukkit.broadcastMessage(" ");
        bonuses.getBonusesUser().setSilnynapotwory(bonuses.getBonusesUser().getSilnynapotwory() + dmgMOB);
        bonuses.getBonusesUser().setDefnamoby(bonuses.getBonusesUser().getDefnamoby() + defMOB);
    }

    public PrzekletyCzarnoksieznikUser find(final UUID uuid) {
        return userMap.get(uuid);
    }
    public void add(final PrzekletyCzarnoksieznikUser przekletyCzarnoksieznikUser) {
        userMap.put(przekletyCzarnoksieznikUser.getUuid(), przekletyCzarnoksieznikUser);
    }
    public ImmutableSet<PrzekletyCzarnoksieznikUser> getPrzekletyCzarnoksieznikUser() {
        return ImmutableSet.copyOf(userMap.values());
    }
}
