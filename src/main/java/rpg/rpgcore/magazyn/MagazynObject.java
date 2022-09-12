package rpg.rpgcore.magazyn;

import org.bson.Document;

import java.util.UUID;

public class MagazynObject {
    private final UUID uuid;
    private final MagazynUser magazynUser;

    public MagazynObject(final UUID uuid) {
        this.uuid = uuid;
        this.magazynUser = new MagazynUser("", "", "", "", "", true, false, false, false, false);
    }

    public MagazynObject(Document document) {
        this.uuid = UUID.fromString(document.getString("_id"));
        this.magazynUser = new MagazynUser(document.getString("magazyn1"), document.getString("magazyn2"), document.getString("magazyn3"), document.getString("magazyn4"), document.getString("magazyn5"), document.getBoolean("unlocked1"), document.getBoolean("unlocked2"), document.getBoolean("unlocked3"), document.getBoolean("unlocked4"), document.getBoolean("unlocked5"));
    }

    public UUID getId() {
        return uuid;
    }

    public MagazynUser getMagazynUser() {
        return magazynUser;
    }

    public Document toDocument() {
        return new Document("_id", this.uuid.toString())
                .append("magazyn1", this.magazynUser.getMagazyn1())
                .append("magazyn2", this.magazynUser.getMagazyn2())
                .append("magazyn3", this.magazynUser.getMagazyn3())
                .append("magazyn4", this.magazynUser.getMagazyn4())
                .append("magazyn5", this.magazynUser.getMagazyn5())
                .append("unlocked1", this.magazynUser.isUnlocked1())
                .append("unlocked2", this.magazynUser.isUnlocked2())
                .append("unlocked3", this.magazynUser.isUnlocked3())
                .append("unlocked4", this.magazynUser.isUnlocked4())
                .append("unlocked5", this.magazynUser.isUnlocked5());
    }
}
