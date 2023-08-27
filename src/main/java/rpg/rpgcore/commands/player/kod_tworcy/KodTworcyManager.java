package rpg.rpgcore.commands.player.kod_tworcy;

import org.bson.Document;
import rpg.rpgcore.RPGCORE;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class KodTworcyManager {

    private final RPGCORE rpgcore;
    private final Map<UUID, List<UUID>> tworcyMap;

    public KodTworcyManager(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
        this.tworcyMap = rpgcore.getMongoManager().loadAllTworcy();
    }


    public boolean isTworca(final UUID uuid) {
        if (!rpgcore.getUserManager().find(uuid).isTworca()) return false;
        return tworcyMap.containsKey(uuid);
    }

    public void addTworca(final UUID uuid) {
        tworcyMap.put(uuid, new ArrayList<>());
    }

    public void addUser(final UUID uuid, final UUID user) {
        tworcyMap.get(uuid).add(user);
    }

    public boolean isUser(final UUID user) {
        return tworcyMap.values().stream().anyMatch(list -> list.contains(user));
    }

    public String getTworcy() {
        return tworcyMap.keySet().stream().map(
                uuid -> rpgcore.getUserManager().find(uuid) == null ?
                                "" : "&8- &6" + rpgcore.getUserManager().find(uuid).getName()).collect(Collectors.joining("&7, \n"));
    }

    public Document toDocument() {
        final Document document = new Document("_id", "tworcy");
        tworcyMap.forEach((uuid, list) -> document.append(uuid.toString(), list.stream().map(UUID::toString).collect(Collectors.joining())));
        return document;
    }
}
