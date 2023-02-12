package rpg.rpgcore.wyszkolenie.objects;

import lombok.Getter;
import lombok.Setter;
import org.bson.Document;

@Getter
@Setter
public class DrzewkoWyszkoleniaUser {
    private boolean d1;

    public DrzewkoWyszkoleniaUser() {
        this.d1 = false;
    }

    public DrzewkoWyszkoleniaUser(final Document document) {
        this.d1 = document.getBoolean("d1");
    }

    public Document toDocument() {
        return new Document("_id", "drzewkoWyszkolenia")
                .append("d1", this.d1);
    }
}
