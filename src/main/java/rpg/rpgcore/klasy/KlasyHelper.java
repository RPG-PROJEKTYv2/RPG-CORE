package rpg.rpgcore.klasy;

import com.google.common.collect.ImmutableSet;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.klasy.objects.Klasy;

import java.util.Map;
import java.util.UUID;

public class KlasyHelper {
    private final Map<UUID, Klasy> klasy;

    public KlasyHelper(RPGCORE rpgcore) {
        this.klasy = rpgcore.getMongoManager().loadAllKlasy();
    }

    public void add(Klasy klasy) {
        this.klasy.put(klasy.getId(), klasy);
    }

    public Klasy find(UUID uuid) {
        return this.klasy.get(uuid);
    }

    public boolean isKlasy(UUID uuid) {
        return this.klasy.containsKey(uuid);
    }

    public ImmutableSet<Klasy> getKlasy() {
        return ImmutableSet.copyOf(this.klasy.values());
    }

    public void remove(Klasy klasy) {
        this.klasy.remove(klasy.getId(), klasy);
    }
}
