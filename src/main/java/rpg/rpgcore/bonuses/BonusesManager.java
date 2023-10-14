package rpg.rpgcore.bonuses;

import com.google.common.collect.ImmutableSet;
import org.bukkit.Material;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.dodatki.akcesoriaP.objects.AkcesoriaPodstUser;
import rpg.rpgcore.npc.lesnik.objects.LesnikUser;
import rpg.rpgcore.npc.metinolog.objects.MetinologUser;
import rpg.rpgcore.npc.rybak.objects.MlodszyRybakUser;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.wyszkolenie.objects.DrzewkoWyszkoleniaUser;

import java.util.Map;
import java.util.UUID;

public class BonusesManager {
    private final Map<UUID, Bonuses> userMap;

    public BonusesManager(final RPGCORE rpgcore) {
        this.userMap = rpgcore.getMongoManager().loadAllBonuses();
    }

    public void add(final Bonuses bonuses) {
        this.userMap.put(bonuses.getId(), bonuses);
    }

    public void set(final UUID uuid, final Bonuses bonuses) {
        this.userMap.replace(uuid, bonuses);
    }

    public Bonuses find(final UUID uuid) {
        userMap.computeIfAbsent(uuid, k -> new Bonuses(uuid));
        return this.userMap.get(uuid);
    }


    public void fix() {
        this.userMap.values().forEach(bonuses -> {
            final UUID uuid = bonuses.getId();
            final LesnikUser lesnikUser = RPGCORE.getInstance().getLesnikNPC().find(uuid).getUser();
            final MetinologUser metinologUser = RPGCORE.getInstance().getMetinologNPC().find(uuid).getMetinologUser();
            final MlodszyRybakUser mlodszyRybakUser = RPGCORE.getInstance().getRybakNPC().find(uuid).getMlodszyRybakUser();
            final DrzewkoWyszkoleniaUser drzewkoWyszkoleniaUser = RPGCORE.getInstance().getWyszkolenieManager().find(uuid).getDrzewkoWyszkoleniaUser();
            final AkcesoriaPodstUser akcesoriaPodstUser = RPGCORE.getInstance().getDodatkiManager().find(uuid).getAkcesoriaPodstawowe();

            double przeszywka = 0;
            przeszywka += lesnikUser.getPrzeszycie();
            przeszywka += metinologUser.getPrzeszycie();
            przeszywka += mlodszyRybakUser.getPrzeszywka();
            przeszywka += drzewkoWyszkoleniaUser.getDg2().getPrzeszywka();
            if (!akcesoriaPodstUser.getPierscien().getType().equals(Material.AIR)) przeszywka += Utils.getTagDouble(akcesoriaPodstUser.getPierscien(), "przebicie");

            bonuses.getBonusesUser().setPrzeszyciebloku(przeszywka);
        });
    }

    public ImmutableSet<Bonuses> getBonusesObjects() {
        return ImmutableSet.copyOf(this.userMap.values());
    }
}
