package rpg.rpgcore.lvl.artefaktyZaLvL;

import lombok.Getter;
import lombok.Setter;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ArtefaktyZaLvl {

    private Poziom50 poziom50;
    private Poziom60 poziom60;
    private Poziom70 poziom70;
    private Poziom80 poziom80;
    private Poziom90 poziom90;
    private Poziom100 poziom100;
    private Poziom110 poziom110;
    private Poziom120 poziom120;
    private Poziom130 poziom130;
    private Rybak rybak;
    private Gornik gornik;

    public ArtefaktyZaLvl() {
        poziom50 = new Poziom50();
        poziom60 = new Poziom60();
        poziom70 = new Poziom70();
        poziom80 = new Poziom80();
        poziom90 = new Poziom90();
        poziom100 = new Poziom100();
        poziom110 = new Poziom110();
        poziom120 = new Poziom120();
        poziom130 = new Poziom130();
        rybak = new Rybak();
        gornik = new Gornik();
    }

    public ArtefaktyZaLvl(Document document) {
        poziom50 = new Poziom50(document.get("poziom50", Document.class));
        poziom60 = new Poziom60(document.get("poziom60", Document.class));
        poziom70 = new Poziom70(document.get("poziom70", Document.class));
        poziom80 = new Poziom80(document.get("poziom80", Document.class));
        poziom90 = new Poziom90(document.get("poziom90", Document.class));
        poziom100 = new Poziom100(document.get("poziom100", Document.class));
        poziom110 = new Poziom110(document.get("poziom110", Document.class));
        poziom120 = new Poziom120(document.get("poziom120", Document.class));
        poziom130 = new Poziom130(document.get("poziom130", Document.class));
        rybak = new Rybak(document.get("rybak", Document.class));
        gornik = new Gornik(document.get("gornik", Document.class));
    }

    public Document toDocument() {
        return new Document("_id", "artefaktyZaLvl")
                .append("poziom50", poziom50.toDocument())
                .append("poziom60", poziom60.toDocument())
                .append("poziom70", poziom70.toDocument())
                .append("poziom80", poziom80.toDocument())
                .append("poziom90", poziom90.toDocument())
                .append("poziom100", poziom100.toDocument())
                .append("poziom110", poziom110.toDocument())
                .append("poziom120", poziom120.toDocument())
                .append("poziom130", poziom130.toDocument())
                .append("rybak", rybak.toDocument())
                .append("gornik", gornik.toDocument());
    }


    @Getter
    @Setter
    public static class Poziom50 {
        private int nadanych;
        private List<String> gracze;

        public Poziom50() {
            nadanych = 0;
            gracze = new ArrayList<>(5);
        }

        public Poziom50(final Document document) {
            nadanych = document.getInteger("nadanych");
            gracze = document.getList("gracze", String.class);
        }

        public Document toDocument() {
            final Document document = new Document("_id", "Poziom50");
            document.append("nadanych", nadanych);
            document.append("gracze", gracze);
            return document;
        }
    }

    @Getter
    @Setter
    public static class Poziom60 {
        private int nadanych;
        private List<String> gracze;

        public Poziom60() {
            nadanych = 0;
            gracze = new ArrayList<>(5);
        }

        public Poziom60(final Document document) {
            nadanych = document.getInteger("nadanych");
            gracze = document.getList("gracze", String.class);
        }

        public Document toDocument() {
            final Document document = new Document("_id", "Poziom60");
            document.append("nadanych", nadanych);
            document.append("gracze", gracze);
            return document;
        }
    }

    @Getter
    @Setter
    public static class Poziom70 {
        private int nadanych;
        private List<String> gracze;

        public Poziom70() {
            nadanych = 0;
            gracze = new ArrayList<>(5);
        }

        public Poziom70(final Document document) {
            nadanych = document.getInteger("nadanych");
            gracze = document.getList("gracze", String.class);
        }

        public Document toDocument() {
            final Document document = new Document("_id", "Poziom70");
            document.append("nadanych", nadanych);
            document.append("gracze", gracze);
            return document;
        }
    }

    @Getter
    @Setter
    public static class Poziom80 {
        private int nadanych;
        private List<String> gracze;

        public Poziom80() {
            nadanych = 0;
            gracze = new ArrayList<>(4);
        }

        public Poziom80(final Document document) {
            nadanych = document.getInteger("nadanych");
            gracze = document.getList("gracze", String.class);
        }

        public Document toDocument() {
            final Document document = new Document("_id", "Poziom80");
            document.append("nadanych", nadanych);
            document.append("gracze", gracze);
            return document;
        }
    }

    @Getter
    @Setter
    public static class Poziom90 {
        private int nadanych;
        private List<String> gracze;

        public Poziom90() {
            nadanych = 0;
            gracze = new ArrayList<>(4);
        }

        public Poziom90(final Document document) {
            nadanych = document.getInteger("nadanych");
            gracze = document.getList("gracze", String.class);
        }

        public Document toDocument() {
            final Document document = new Document("_id", "Poziom90");
            document.append("nadanych", nadanych);
            document.append("gracze", gracze);
            return document;
        }
    }

    @Getter
    @Setter
    public static class Poziom100 {
        private int nadanych;
        private List<String> gracze;

        public Poziom100() {
            nadanych = 0;
            gracze = new ArrayList<>(4);
        }

        public Poziom100(final Document document) {
            nadanych = document.getInteger("nadanych");
            gracze = document.getList("gracze", String.class);
        }

        public Document toDocument() {
            final Document document = new Document("_id", "Poziom100");
            document.append("nadanych", nadanych);
            document.append("gracze", gracze);
            return document;
        }
    }

    @Getter
    @Setter
    public static class Poziom110 {
        private int nadanych;
        private List<String> gracze;

        public Poziom110() {
            nadanych = 0;
            gracze = new ArrayList<>(4);
        }

        public Poziom110(final Document document) {
            nadanych = document.getInteger("nadanych");
            gracze = document.getList("gracze", String.class);
        }

        public Document toDocument() {
            final Document document = new Document("_id", "Poziom110");
            document.append("nadanych", nadanych);
            document.append("gracze", gracze);
            return document;
        }
    }

    @Getter
    @Setter
    public static class Poziom120 {
        private int nadanych;
        private List<String> gracze;

        public Poziom120() {
            nadanych = 0;
            gracze = new ArrayList<>(4);
        }

        public Poziom120(final Document document) {
            nadanych = document.getInteger("nadanych");
            gracze = document.getList("gracze", String.class);
        }

        public Document toDocument() {
            final Document document = new Document("_id", "Poziom120");
            document.append("nadanych", nadanych);
            document.append("gracze", gracze);
            return document;
        }
    }

    @Getter
    @Setter
    public static class Poziom130 {
        private int nadanych;
        private List<String> gracze;

        public Poziom130() {
            nadanych = 0;
            gracze = new ArrayList<>(4);
        }

        public Poziom130(final Document document) {
            nadanych = document.getInteger("nadanych");
            gracze = document.getList("gracze", String.class);
        }

        public Document toDocument() {
            final Document document = new Document("_id", "Poziom130");
            document.append("nadanych", nadanych);
            document.append("gracze", gracze);
            return document;
        }
    }

    @Getter
    @Setter
    public static class Rybak {
        private int nadanych;
        private List<String> gracze;

        public Rybak() {
            nadanych = 0;
            gracze = new ArrayList<>(4);
        }

        public Rybak(final Document document) {
            nadanych = document.getInteger("nadanych");
            gracze = document.getList("gracze", String.class);
        }

        public Document toDocument() {
            final Document document = new Document("_id", "rybak");
            document.append("nadanych", nadanych);
            document.append("gracze", gracze);
            return document;
        }
    }

    @Getter
    @Setter
    public static class Gornik {
        private int nadanych;
        private List<String> gracze;

        public Gornik() {
            nadanych = 0;
            gracze = new ArrayList<>(4);
        }

        public Gornik(final Document document) {
            nadanych = document.getInteger("nadanych");
            gracze = document.getList("gracze", String.class);
        }

        public Document toDocument() {
            final Document document = new Document("_id", "gornik");
            document.append("nadanych", nadanych);
            document.append("gracze", gracze);
            return document;
        }
    }
}
