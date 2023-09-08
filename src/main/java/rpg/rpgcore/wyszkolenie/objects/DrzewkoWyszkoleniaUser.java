package rpg.rpgcore.wyszkolenie.objects;

import lombok.Getter;
import lombok.Setter;
import org.bson.Document;

@Getter
public class DrzewkoWyszkoleniaUser implements Cloneable {
    private D1 d1;
    private D2 d2;
    private DL1 dl1;
    private DL2 dl2;
    private DL3 dl3;
    private DL4 dl4;
    private DL5 dl5;
    private DL6 dl6;
    private DP1 dp1;
    private DP2 dp2;
    private DP3 dp3;
    private DP4 dp4;
    private DP5 dp5;
    private DP6 dp6;
    private DG1 dg1;
    private DG2 dg2;
    private DG3 dg3;

    public DrzewkoWyszkoleniaUser() {
        this.d1 = new D1();
        this.d2 = new D2();
        this.dl1 = new DL1();
        this.dl2 = new DL2();
        this.dl3 = new DL3();
        this.dl4 = new DL4();
        this.dl5 = new DL5();
        this.dl6 = new DL6();
        this.dp1 = new DP1();
        this.dp2 = new DP2();
        this.dp3 = new DP3();
        this.dp4 = new DP4();
        this.dp5 = new DP5();
        this.dp6 = new DP6();
        this.dg1 = new DG1();
        this.dg2 = new DG2();
        this.dg3 = new DG3();
    }

    public DrzewkoWyszkoleniaUser(final Document document) {
        this.d1 = new D1(document.get("d1", Document.class));
        this.d2 = new D2(document.get("d2", Document.class));
        this.dl1 = new DL1(document.get("dl1", Document.class));
        this.dl2 = new DL2(document.get("dl2", Document.class));
        this.dl3 = new DL3(document.get("dl3", Document.class));
        this.dl4 = new DL4(document.get("dl4", Document.class));
        this.dl5 = new DL5(document.get("dl5", Document.class));
        this.dl6 = new DL6(document.get("dl6", Document.class));
        this.dp1 = new DP1(document.get("dp1", Document.class));
        this.dp2 = new DP2(document.get("dp2", Document.class));
        this.dp3 = new DP3(document.get("dp3", Document.class));
        this.dp4 = new DP4(document.get("dp4", Document.class));
        this.dp5 = new DP5(document.get("dp5", Document.class));
        this.dp6 = new DP6(document.get("dp6", Document.class));
        this.dg1 = new DG1(document.get("dg1", Document.class));
        this.dg2 = new DG2(document.get("dg2", Document.class));
        this.dg3 = new DG3(document.get("dg3", Document.class));
    }

    public Document toDocument() {
        return new Document("_id", "drzewkoWyszkolenia")
                .append("d1", this.d1.toDocument())
                .append("d2", this.d2.toDocument())
                .append("dl1", this.dl1.toDocument())
                .append("dl2", this.dl2.toDocument())
                .append("dl3", this.dl3.toDocument())
                .append("dl4", this.dl4.toDocument())
                .append("dl5", this.dl5.toDocument())
                .append("dl6", this.dl6.toDocument())
                .append("dp1", this.dp1.toDocument())
                .append("dp2", this.dp2.toDocument())
                .append("dp3", this.dp3.toDocument())
                .append("dp4", this.dp4.toDocument())
                .append("dp5", this.dp5.toDocument())
                .append("dp6", this.dp6.toDocument())
                .append("dg1", this.dg1.toDocument())
                .append("dg2", this.dg2.toDocument())
                .append("dg3", this.dg3.toDocument());
    }

    @Override
    public DrzewkoWyszkoleniaUser clone() {
        try {
            DrzewkoWyszkoleniaUser clone = (DrzewkoWyszkoleniaUser) super.clone();
            clone.d1 = this.d1.clone();
            clone.d2 = this.d2.clone();
            clone.dl1 = this.dl1.clone();
            clone.dl2 = this.dl2.clone();
            clone.dl3 = this.dl3.clone();
            clone.dl4 = this.dl4.clone();
            clone.dl5 = this.dl5.clone();
            clone.dl6 = this.dl6.clone();
            clone.dp1 = this.dp1.clone();
            clone.dp2 = this.dp2.clone();
            clone.dp3 = this.dp3.clone();
            clone.dp4 = this.dp4.clone();
            clone.dp5 = this.dp5.clone();
            clone.dp6 = this.dp6.clone();
            clone.dg1 = this.dg1.clone();
            clone.dg2 = this.dg2.clone();
            clone.dg3 = this.dg3.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }


    @Getter
    @Setter
    public static class D1 implements Cloneable {
        private final String name = "&6Niewzruszona Potega";
        private final int reqLvl = 50;
        private final String reqItem = "I1";
        private final int maxUpgradeLvl = 5;
        private final double perLvl = 1;
        private boolean unlocked;
        private int upgradeLvl;
        private double srDmg;

        public D1() {
            this.unlocked = false;
            this.upgradeLvl = 0;
            this.srDmg = 0;
        }

        public D1(final Document document) {
            this.unlocked = document.getBoolean("unlocked");
            this.upgradeLvl = document.getInteger("upgradeLvl");
            this.srDmg = document.getDouble("srDmg");
        }

        public Document toDocument() {
            return new Document("_id", "D1")
                    .append("unlocked", this.unlocked)
                    .append("upgradeLvl", this.upgradeLvl)
                    .append("srDmg", this.srDmg);
        }

        @Override
        public D1 clone() {
            try {
                // TODO: copy mutable state here, so the clone can't change the internals of the original
                return (D1) super.clone();
            } catch (CloneNotSupportedException e) {
                throw new AssertionError();
            }
        }
    }

    @Getter
    @Setter
    public static class D2 implements Cloneable {
        private final String name = "&6Ukryty Talent";
        private final int reqLvl = 55;
        private final String reqItem = "I2";
        private final int maxUpgradeLvl = 5;
        private final int perLvl = 1;
        private boolean unlocked;
        private int upgradeLvl;
        private int szczescie;

        public D2() {
            this.unlocked = false;
            this.upgradeLvl = 0;
            this.szczescie = 0;
        }

        public D2(final Document document) {
            this.unlocked = document.getBoolean("unlocked");
            this.upgradeLvl = document.getInteger("upgradeLvl");
            this.szczescie = document.getInteger("szczescie");
        }

        public Document toDocument() {
            return new Document("_id", "D2")
                    .append("unlocked", this.unlocked)
                    .append("upgradeLvl", this.upgradeLvl)
                    .append("szczescie", this.szczescie);
        }

        @Override
        public D2 clone() {
            try {
                // TODO: copy mutable state here, so the clone can't change the internals of the original
                return (D2) super.clone();
            } catch (CloneNotSupportedException e) {
                throw new AssertionError();
            }
        }
    }

    @Getter
    @Setter
    public static class DP1 implements Cloneable {
        private final String name = "&6Silne Cialo";
        private final int reqLvl = 60;
        private final String reqItem = "I3";
        private final int maxUpgradeLvl = 5;
        private final int perLvl = 1;
        private boolean unlocked;
        private int upgradeLvl;
        private int hp;

        public DP1() {
            this.unlocked = false;
            this.upgradeLvl = 0;
            this.hp = 0;
        }

        public DP1(final Document document) {
            this.unlocked = document.getBoolean("unlocked");
            this.upgradeLvl = document.getInteger("upgradeLvl");
            this.hp = document.getInteger("hp");
        }

        public Document toDocument() {
            return new Document("_id", "DP1")
                    .append("unlocked", this.unlocked)
                    .append("upgradeLvl", this.upgradeLvl)
                    .append("hp", this.hp);
        }

        @Override
        public DP1 clone() {
            try {
                // TODO: copy mutable state here, so the clone can't change the internals of the original
                return (DP1) super.clone();
            } catch (CloneNotSupportedException e) {
                throw new AssertionError();
            }
        }
    }

    @Getter
    @Setter
    public static class DP2 implements Cloneable {
        private final String name = "&6Wyzwalajaca Sila";
        private final int reqLvl = 75;
        private final String reqItem = "I4";
        private final int maxUpgradeLvl = 5;
        private final int perLvl = 100;
        private boolean unlocked;
        private int upgradeLvl;
        private int dodatkowyDmg;

        public DP2() {
            this.unlocked = false;
            this.upgradeLvl = 0;
            this.dodatkowyDmg = 0;
        }

        public DP2(final Document document) {
            this.unlocked = document.getBoolean("unlocked");
            this.upgradeLvl = document.getInteger("upgradeLvl");
            this.dodatkowyDmg = document.getInteger("dodatkowyDmg");
        }

        public Document toDocument() {
            return new Document("_id", "DP2")
                    .append("unlocked", this.unlocked)
                    .append("upgradeLvl", this.upgradeLvl)
                    .append("dodatkowyDmg", this.dodatkowyDmg);
        }

        @Override
        public DP2 clone() {
            try {
                // TODO: copy mutable state here, so the clone can't change the internals of the original
                return (DP2) super.clone();
            } catch (CloneNotSupportedException e) {
                throw new AssertionError();
            }
        }
    }

    @Getter
    @Setter
    public static class DP3 implements Cloneable {
        private final String name = "&6Pogromca Potworow";
        private final int reqLvl = 90;
        private final String reqItem = "I5";
        private final int maxUpgradeLvl = 5;
        private final double perLvl = 1;
        private boolean unlocked;
        private int upgradeLvl;
        private double silnyNaMoby;

        public DP3() {
            this.unlocked = false;
            this.upgradeLvl = 0;
            this.silnyNaMoby = 0;
        }

        public DP3(final Document document) {
            this.unlocked = document.getBoolean("unlocked");
            this.upgradeLvl = document.getInteger("upgradeLvl");
            this.silnyNaMoby = document.getDouble("silnyNaMoby");
        }

        public Document toDocument() {
            return new Document("_id", "DP3")
                    .append("unlocked", this.unlocked)
                    .append("upgradeLvl", this.upgradeLvl)
                    .append("silnyNaMoby", this.silnyNaMoby);
        }

        @Override
        public DP3 clone() {
            try {
                // TODO: copy mutable state here, so the clone can't change the internals of the original
                return (DP3) super.clone();
            } catch (CloneNotSupportedException e) {
                throw new AssertionError();
            }
        }
    }

    @Getter
    @Setter
    public static class DP4 implements Cloneable {
        private final String name = "&6Niewzruszona Stal";
        private final int reqLvl = 105;
        private final String reqItem = "I6";
        private final int maxUpgradeLvl = 5;
        private final double perLvl = 1;
        private boolean unlocked;
        private int upgradeLvl;
        private double odpornoscNaGraczy;

        public DP4() {
            this.unlocked = false;
            this.upgradeLvl = 0;
            this.odpornoscNaGraczy = 0;
        }

        public DP4(final Document document) {
            this.unlocked = document.getBoolean("unlocked");
            this.upgradeLvl = document.getInteger("upgradeLvl");
            this.odpornoscNaGraczy = document.getDouble("odpornoscNaGraczy");
        }

        public Document toDocument() {
            return new Document("_id", "DP4")
                    .append("unlocked", this.unlocked)
                    .append("upgradeLvl", this.upgradeLvl)
                    .append("odpornoscNaGraczy", this.odpornoscNaGraczy);
        }

        @Override
        public DP4 clone() {
            try {
                // TODO: copy mutable state here, so the clone can't change the internals of the original
                return (DP4) super.clone();
            } catch (CloneNotSupportedException e) {
                throw new AssertionError();
            }
        }
    }

    @Getter
    @Setter
    public static class DP5 implements Cloneable {
        private final String name = "&6Szlachecki Talent";
        private final int reqLvl = 115;
        private final String reqItem = "I7";
        private final int maxUpgradeLvl = 5;
        private final int perLvl = 1;
        private boolean unlocked;
        private int upgradeLvl;
        private int szczescie;

        public DP5() {
            this.unlocked = false;
            this.upgradeLvl = 0;
            this.szczescie = 0;
        }

        public DP5(final Document document) {
            this.unlocked = document.getBoolean("unlocked");
            this.upgradeLvl = document.getInteger("upgradeLvl");
            this.szczescie = document.getInteger("szczescie");
        }

        public Document toDocument() {
            return new Document("_id", "DP5")
                    .append("unlocked", this.unlocked)
                    .append("upgradeLvl", this.upgradeLvl)
                    .append("szczescie", this.szczescie);
        }

        @Override
        public DP5 clone() {
            try {
                // TODO: copy mutable state here, so the clone can't change the internals of the original
                return (DP5) super.clone();
            } catch (CloneNotSupportedException e) {
                throw new AssertionError();
            }
        }
    }

    @Getter
    @Setter
    public static class DP6 implements Cloneable {
        private final String name = "&6Szlachecka Defensywa";
        private final int reqLvl = 125;
        private final String reqItem = "I8";
        private final int maxUpgradeLvl = 5;
        private final double perLvl = 1;
        private boolean unlocked;
        private int upgradeLvl;
        private double srDef;

        public DP6() {
            this.unlocked = false;
            this.upgradeLvl = 0;
            this.srDef = 0;
        }

        public DP6(final Document document) {
            this.unlocked = document.getBoolean("unlocked");
            this.upgradeLvl = document.getInteger("upgradeLvl");
            this.srDef = document.getDouble("srDef");
        }

        public Document toDocument() {
            return new Document("_id", "DP6")
                    .append("unlocked", this.unlocked)
                    .append("upgradeLvl", this.upgradeLvl)
                    .append("srDef", this.srDef);
        }

        @Override
        public DP6 clone() {
            try {
                // TODO: copy mutable state here, so the clone can't change the internals of the original
                return (DP6) super.clone();
            } catch (CloneNotSupportedException e) {
                throw new AssertionError();
            }
        }
    }

    @Getter
    @Setter
    public static class DG1 implements Cloneable {
        private final String name = "&6Niepokonany Wojownik";
        private final int reqLvl = 65;
        private final String reqItem = "I9";
        private final int maxUpgradeLvl = 5;
        private final double perLvl = 1;
        private boolean unlocked;
        private int upgradeLvl;
        private double odpornoscNaMoby;

        public DG1() {
            this.unlocked = false;
            this.upgradeLvl = 0;
            this.odpornoscNaMoby = 0;
        }

        public DG1(final Document document) {
            this.unlocked = document.getBoolean("unlocked");
            this.upgradeLvl = document.getInteger("upgradeLvl");
            this.odpornoscNaMoby = document.getDouble("odpornoscNaMoby");
        }

        public Document toDocument() {
            return new Document("_id", "DG1")
                    .append("unlocked", this.unlocked)
                    .append("upgradeLvl", this.upgradeLvl)
                    .append("odpornoscNaMoby", this.odpornoscNaMoby);
        }

        @Override
        public DG1 clone() {
            try {
                // TODO: copy mutable state here, so the clone can't change the internals of the original
                return (DG1) super.clone();
            } catch (CloneNotSupportedException e) {
                throw new AssertionError();
            }
        }
    }

    @Getter
    @Setter
    public static class DG2 implements Cloneable {
        private final String name = "&6Niszczyciel Tarcz";
        private final int reqLvl = 80;
        private final String reqItem = "I10";
        private final int maxUpgradeLvl = 5;
        private final double perLvl = 1;
        private boolean unlocked;
        private int upgradeLvl;
        private double przeszywka;

        public DG2() {
            this.unlocked = false;
            this.upgradeLvl = 0;
            this.przeszywka = 0;
        }

        public DG2(final Document document) {
            this.unlocked = document.getBoolean("unlocked");
            this.upgradeLvl = document.getInteger("upgradeLvl");
            this.przeszywka = document.getDouble("przeszywka");
        }

        public Document toDocument() {
            return new Document("_id", "DG2")
                    .append("unlocked", this.unlocked)
                    .append("upgradeLvl", this.upgradeLvl)
                    .append("przeszywka", this.przeszywka);
        }

        @Override
        public DG2 clone() {
            try {
                // TODO: copy mutable state here, so the clone can't change the internals of the original
                return (DG2) super.clone();
            } catch (CloneNotSupportedException e) {
                throw new AssertionError();
            }
        }
    }

    @Getter
    @Setter
    public static class DG3 implements Cloneable {
        private final String name = "&6Szlachecki Blok";
        private final int reqLvl = 95;
        private final String reqItem = "I11";
        private final int maxUpgradeLvl = 5;
        private final double perLvl = 1;
        private boolean unlocked;
        private int upgradeLvl;
        private double blok;

        public DG3() {
            this.unlocked = false;
            this.upgradeLvl = 0;
            this.blok = 0;
        }

        public DG3(final Document document) {
            this.unlocked = document.getBoolean("unlocked");
            this.upgradeLvl = document.getInteger("upgradeLvl");
            this.blok = document.getDouble("blok");
        }

        public Document toDocument() {
            return new Document("_id", "DG3")
                    .append("unlocked", this.unlocked)
                    .append("upgradeLvl", this.upgradeLvl)
                    .append("blok", this.blok);
        }

        @Override
        public DG3 clone() {
            try {
                // TODO: copy mutable state here, so the clone can't change the internals of the original
                return (DG3) super.clone();
            } catch (CloneNotSupportedException e) {
                throw new AssertionError();
            }
        }
    }

    @Getter
    @Setter
    public static class DL1 implements Cloneable {
        private final String name = "&6Barbarzynca";
        private final int reqLvl = 70;
        private final String reqItem = "I12";
        private final int maxUpgradeLvl = 5;
        private final double perLvl = 1;
        private boolean unlocked;
        private int upgradeLvl;
        private double krytyk;

        public DL1() {
            this.unlocked = false;
            this.upgradeLvl = 0;
            this.krytyk = 0;
        }

        public DL1(final Document document) {
            this.unlocked = document.getBoolean("unlocked");
            this.upgradeLvl = document.getInteger("upgradeLvl");
            this.krytyk = document.getDouble("krytyk");
        }

        public Document toDocument() {
            return new Document("_id", "DL1")
                    .append("unlocked", this.unlocked)
                    .append("upgradeLvl", this.upgradeLvl)
                    .append("krytyk", this.krytyk);
        }

        @Override
        public DL1 clone() {
            try {
                // TODO: copy mutable state here, so the clone can't change the internals of the original
                return (DL1) super.clone();
            } catch (CloneNotSupportedException e) {
                throw new AssertionError();
            }
        }
    }

    @Getter
    @Setter
    public static class DL2 implements Cloneable {
        private final String name = "&6Stalowy Wojownik";
        private final int reqLvl = 85;
        private final String reqItem = "I13";
        private final int maxUpgradeLvl = 5;
        private final double perLvl = 1;
        private boolean unlocked;
        private int upgradeLvl;
        private double srDef;

        public DL2() {
            this.unlocked = false;
            this.upgradeLvl = 0;
            this.srDef = 0;
        }

        public DL2(final Document document) {
            this.unlocked = document.getBoolean("unlocked");
            this.upgradeLvl = document.getInteger("upgradeLvl");
            this.srDef = document.getDouble("srDef");
        }

        public Document toDocument() {
            return new Document("_id", "DL2")
                    .append("unlocked", this.unlocked)
                    .append("upgradeLvl", this.upgradeLvl)
                    .append("srDef", this.srDef);
        }

        @Override
        public DL2 clone() {
            try {
                // TODO: copy mutable state here, so the clone can't change the internals of the original
                return (DL2) super.clone();
            } catch (CloneNotSupportedException e) {
                throw new AssertionError();
            }
        }
    }

    @Getter
    @Setter
    public static class DL3 implements Cloneable {
        private final String name = "&6Zabójcze Ostrze";
        private final int reqLvl = 100;
        private final String reqItem = "I14";
        private final int maxUpgradeLvl = 5;
        private final double perLvl = 1;
        private boolean unlocked;
        private int upgradeLvl;
        private double silnyNaLudzi;

        public DL3() {
            this.unlocked = false;
            this.upgradeLvl = 0;
            this.silnyNaLudzi = 0;
        }

        public DL3(final Document document) {
            this.unlocked = document.getBoolean("unlocked");
            this.upgradeLvl = document.getInteger("upgradeLvl");
            this.silnyNaLudzi = document.getDouble("silnyNaLudzi");
        }

        public Document toDocument() {
            return new Document("_id", "DL3")
                    .append("unlocked", this.unlocked)
                    .append("upgradeLvl", this.upgradeLvl)
                    .append("silnyNaLudzi", this.silnyNaLudzi);
        }

        @Override
        public DL3 clone() {
            try {
                // TODO: copy mutable state here, so the clone can't change the internals of the original
                return (DL3) super.clone();
            } catch (CloneNotSupportedException e) {
                throw new AssertionError();
            }
        }
    }

    @Getter
    @Setter
    public static class DL4 implements Cloneable {
        private final String name = "&6Niezniszczalny Mur";
        private final int reqLvl = 110;
        private final String reqItem = "I15";
        private final int maxUpgradeLvl = 5;
        private final double perLvl = 1;
        private boolean unlocked;
        private int upgradeLvl;
        private double blok;

        public DL4() {
            this.unlocked = false;
            this.upgradeLvl = 0;
            this.blok = 0;
        }

        public DL4(final Document document) {
            this.unlocked = document.getBoolean("unlocked");
            this.upgradeLvl = document.getInteger("upgradeLvl");
            this.blok = document.getDouble("blok");
        }

        public Document toDocument() {
            return new Document("_id", "DL4")
                    .append("unlocked", this.unlocked)
                    .append("upgradeLvl", this.upgradeLvl)
                    .append("blok", this.blok);
        }

        @Override
        public DL4 clone() {
            try {
                // TODO: copy mutable state here, so the clone can't change the internals of the original
                return (DL4) super.clone();
            } catch (CloneNotSupportedException e) {
                throw new AssertionError();
            }
        }
    }

    @Getter
    @Setter
    public static class DL5 implements Cloneable {
        private final String name = "&6Szlacheckie Zdrowie";
        private final int reqLvl = 120;
        private final String reqItem = "I16";
        private final int maxUpgradeLvl = 5;
        private final int perLvl = 1;
        private boolean unlocked;
        private int upgradeLvl;
        private int hp;

        public DL5() {
            this.unlocked = false;
            this.upgradeLvl = 0;
            this.hp = 0;
        }

        public DL5(final Document document) {
            this.unlocked = document.getBoolean("unlocked");
            this.upgradeLvl = document.getInteger("upgradeLvl");
            this.hp = document.getInteger("hp");
        }

        public Document toDocument() {
            return new Document("_id", "DL5")
                    .append("unlocked", this.unlocked)
                    .append("upgradeLvl", this.upgradeLvl)
                    .append("hp", this.hp);
        }

        @Override
        public DL5 clone() {
            try {
                // TODO: copy mutable state here, so the clone can't change the internals of the original
                return (DL5) super.clone();
            } catch (CloneNotSupportedException e) {
                throw new AssertionError();
            }
        }
    }

    @Getter
    @Setter
    public static class DL6 implements Cloneable {
        private final String name = "&6Szlacheckie Obrazenia";
        private final int reqLvl = 130;
        private final String reqItem = "I17";
        private final int maxUpgradeLvl = 5;
        private final double perLvl = 1;
        private boolean unlocked;
        private int upgradeLvl;
        private double srDmg;

        public DL6() {
            this.unlocked = false;
            this.upgradeLvl = 0;
            this.srDmg = 0;
        }

        public DL6(final Document document) {
            this.unlocked = document.getBoolean("unlocked");
            this.upgradeLvl = document.getInteger("upgradeLvl");
            this.srDmg = document.getDouble("srDmg");
        }

        public Document toDocument() {
            return new Document("_id", "DL6")
                    .append("unlocked", this.unlocked)
                    .append("upgradeLvl", this.upgradeLvl)
                    .append("srDmg", this.srDmg);
        }

        @Override
        public DL6 clone() {
            try {
                // TODO: copy mutable state here, so the clone can't change the internals of the original
                return (DL6) super.clone();
            } catch (CloneNotSupportedException e) {
                throw new AssertionError();
            }
        }
    }
}
