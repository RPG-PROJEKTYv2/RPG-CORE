package rpg.rpgcore.bossy.enums;

public enum Stage70_80 {
    NOT_SPAWNED("", (short) 15),
    STAGE1("&1&lKto smie mnie wzywac &7(&11&7/&14&7)", (short) 11),
    STAGE2("&b&lKto smie mnie wzywac &7(&b2&7/&b4&7)", (short) 3),
    STAGE3("&d&lKto smie mnie wzywac &7(&d3&7/&d4&7)", (short) 2),
    SPAWNING("&5&lPrzybywam na wasze wezwanie! &7(&54&7/&54&7)", (short) 2),
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
