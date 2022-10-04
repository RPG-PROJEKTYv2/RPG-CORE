package rpg.rpgcore.guilds;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class Guild {
    private String tag;
    private String description;
    private UUID owner;
    private UUID coOwner;
    private List<UUID> members;
    private int points;
    private int level;
    private double exp;
    private int balance;
    private boolean pvp;
    private double dodatkowyExp;
    private double sredniDmg;
    private double sredniDef;
    private double silnyNaLudzi;
    private double defNaLudzi;
    private Map<UUID, Integer> kills;
    private Map<UUID, Integer> deaths;
    private Map<UUID, Double> expEarned;
    private Map<UUID, Date> lastOnline;

    public Guild(String tag, String description, UUID owner, UUID coOwner, List<UUID> members, int points, int level, double exp, int balance, boolean pvp, double dodatkowyExp, double sredniDmg, double sredniDef, double silnyNaLudzi, double defNaLudzi, Map<UUID, Integer> kills, Map<UUID, Integer> deaths, Map<UUID, Double> expEarned, Map<UUID, Date> lastOnline) {
        this.tag = tag;
        this.description = description;
        this.owner = owner;
        this.coOwner = coOwner;
        this.members = members;
        this.points = points;
        this.level = level;
        this.exp = exp;
        this.balance = balance;
        this.pvp = pvp;
        this.dodatkowyExp = dodatkowyExp;
        this.sredniDmg = sredniDmg;
        this.sredniDef = sredniDef;
        this.silnyNaLudzi = silnyNaLudzi;
        this.defNaLudzi = defNaLudzi;
        this.kills = kills;
        this.deaths = deaths;
        this.expEarned = expEarned;
        this.lastOnline = lastOnline;
    }
}
