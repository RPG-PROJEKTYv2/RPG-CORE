package rpg.rpgcore.bossy.enums;

public enum Stage70_80 {
    NOT_SPAWNED("", (short) 15),
    STAGE1("&4&lBOSS &6&l70-80 &8>> &eKto budzi mnie ze snu!? &8(&f1&8/&f4&8)", (short) 11),
    STAGE2("&4&lBOSS &6&l70-80 &8>> &eKto budzi mnie ze snu!? &8(&f2&8/&f4&8)", (short) 3),
    STAGE3("&4&lBOSS &6&l70-80 &8>> &eKto budzi mnie ze snu!? &8(&f3&8/&f4&8)", (short) 2),
    SPAWNING("&4&lBOSS &6&l70-80 &8>> &ePrzybywam Na Wasze Wezwanie &8(&f4&8/&f4&8)", (short) 2),
    SPAWNED("&8&l(&4&lBOSS&8&l) &8>> &5&lPrzeklety Czarnoksieznik &fzostal przywolany przez: ", (short) 10);

    private final String message;
    private final short data;

    Stage70_80(final String message, final short data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return this.message;
    }

    public short getData() {
        return this.data;
    }
}
