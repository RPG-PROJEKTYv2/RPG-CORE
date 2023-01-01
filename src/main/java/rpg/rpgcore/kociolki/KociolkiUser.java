package rpg.rpgcore.kociolki;

import lombok.Getter;
import lombok.Setter;
import org.bson.Document;

import java.util.UUID;

@Getter
@Setter
public class KociolkiUser {
    private final UUID uuid;
    private int eliksirPotegiTime, eliksirObroncyTime, talizmanPogromcyTime, talizmanObroncyTime, talizmanZabojcyTime, egzekutorTime;
    private boolean eliksirPotegi, eliksirObroncy, talizmanPogromcy, talizmanObroncy, talizmanZabojcy, egzekutor;

    public KociolkiUser(UUID uuid) {
        this.uuid = uuid;
        this.eliksirPotegi = false;
        this.eliksirObroncy = false;
        this.talizmanPogromcy = false;
        this.talizmanObroncy = false;
        this.talizmanZabojcy = false;
        this.egzekutor = false;
        this.eliksirPotegiTime = 0;
        this.eliksirObroncyTime = 0;
        this.talizmanPogromcyTime = 0;
        this.talizmanObroncyTime = 0;
        this.talizmanZabojcyTime = 0;
        this.egzekutorTime = 0;
    }

    public KociolkiUser(final Document document) {
        this.uuid = UUID.fromString(document.getString("_id"));
        this.eliksirPotegi = document.getBoolean("eliksirPotegi");
        this.eliksirObroncy = document.getBoolean("eliksirObroncy");
        this.talizmanPogromcy = document.getBoolean("talizmanPogromcy");
        this.talizmanObroncy = document.getBoolean("talizmanObroncy");
        this.talizmanZabojcy = document.getBoolean("talizmanZabojcy");
        this.egzekutor = document.getBoolean("egzekutor");
        this.eliksirPotegiTime = document.getInteger("eliksirPotegiTime");
        this.eliksirObroncyTime = document.getInteger("eliksirObroncyTime");
        this.talizmanPogromcyTime = document.getInteger("talizmanPogromcyTime");
        this.talizmanObroncyTime = document.getInteger("talizmanObroncyTime");
        this.talizmanZabojcyTime = document.getInteger("talizmanZabojcyTime");
        this.egzekutorTime = document.getInteger("egzekutorTime");
    }

    public Document toDocument() {
        return new Document("_id", uuid.toString())
                .append("eliksirPotegi", eliksirPotegi)
                .append("eliksirObroncy", eliksirObroncy)
                .append("talizmanPogromcy", talizmanPogromcy)
                .append("talizmanObroncy", talizmanObroncy)
                .append("talizmanZabojcy", talizmanZabojcy)
                .append("egzekutor", egzekutor)
                .append("eliksirPotegiTime", eliksirPotegiTime)
                .append("eliksirObroncyTime", eliksirObroncyTime)
                .append("talizmanPogromcyTime", talizmanPogromcyTime)
                .append("talizmanObroncyTime", talizmanObroncyTime)
                .append("talizmanZabojcyTime", talizmanZabojcyTime)
                .append("egzekutorTime", egzekutorTime);
    }
}
