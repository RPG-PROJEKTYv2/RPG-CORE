package rpg.rpgcore.bossy.effects.PrzekletyCzarnoksieznik;

public enum Bonus70_80 {
    BS(1, 25, 1, 25);

    private final int dmgmobyMIN;
    private final int dmgmobyMAX;
    private final int defmobyMIN;
    private final int defmobyMAX;

    Bonus70_80(final int dmgmobyMIN, final int dmgmobyMAX, final int defmobyMIN, final int defmobyMAX) {
        this.dmgmobyMIN = dmgmobyMIN;
        this.dmgmobyMAX = dmgmobyMAX;
        this.defmobyMIN = defmobyMIN;
        this.defmobyMAX = defmobyMAX;
    }
    public int getDmgmobyMIN() {
        return dmgmobyMIN;
    }
    public int getDefmobyMIN() {
        return defmobyMIN;
    }
    public int getDmgmobyMAX() {
        return dmgmobyMAX;
    }
    public int getDefmobyMAX() {
        return defmobyMAX;
    }
}
