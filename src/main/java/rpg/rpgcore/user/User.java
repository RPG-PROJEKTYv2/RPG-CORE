package rpg.rpgcore.user;

import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import rpg.rpgcore.inventories.InventoriesUser;
import rpg.rpgcore.ranks.RankPlayerUser;
import rpg.rpgcore.ranks.RankUser;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.ranks.types.RankTypePlayer;

import java.util.UUID;

@Getter
@Setter
public class User {
    private final UUID id;
    private String name;
    private String banInfo;
    private String muteInfo;
    private String punishmentHistory;
    private final RankUser rankUser;
    private final RankPlayerUser rankPlayerUser;
    private long rankChestCooldown;
    private int lvl;
    private double exp;
    private double kasa;
    private int hellcoins;
    private boolean verify = false;
    private boolean click = false;
    private boolean msgOff;
    private double polimorfiabonus = 0.0;
    private long polimorfiaTime = 0L;
    private String adminCode;
    private boolean adminCodeLogin;
    private String hellCode;
    private boolean hellCodeLogin;
    private final InventoriesUser inventoriesUser;
    private int pierscienDoswiadczenia;
    private long pierscienDoswiadczeniaTime;
    private double krytyk;

    public User(final UUID id, final String name) {
        this.id = id;
        this.name = name;
        this.banInfo = "false";
        this.muteInfo = "false";
        this.punishmentHistory = "";
        this.rankUser = new RankUser(RankType.GRACZ);
        this.rankPlayerUser = new RankPlayerUser(RankTypePlayer.GRACZ, 0L);
        this.rankChestCooldown = 0L;
        this.lvl = 1;
        this.exp = 0;
        this.kasa = 100;
        this.hellcoins = 0;
        this.msgOff = false;
        this.adminCode = "";
        this.adminCodeLogin = false;
        this.hellCode = "";
        this.hellCodeLogin = false;
        this.inventoriesUser = new InventoriesUser("", "", "");
        this.pierscienDoswiadczenia = 0;
        this.pierscienDoswiadczeniaTime = 0L;
        this.krytyk = 0.0;
    }

    public User(final Document document) {
        this.id = UUID.fromString(document.getString("_id"));
        this.name = document.getString("name");
        this.banInfo = document.getString("banInfo");
        this.muteInfo = document.getString("muteInfo");
        this.punishmentHistory = document.getString("punishmentHistory");
        this.rankUser = new RankUser(RankType.valueOf(document.getString("rankName")));
        this.rankPlayerUser = new RankPlayerUser(RankTypePlayer.valueOf(document.getString("rankPlayerName")), document.getLong("rankPlayerTime"));
        this.rankChestCooldown = document.getLong("rankChestCooldown");
        this.lvl = document.getInteger("lvl");
        this.exp = document.getDouble("exp");
        this.kasa = document.getDouble("kasa");
        this.hellcoins = document.getInteger("hellcoins");
        this.msgOff = document.getBoolean("msgOff");
        this.adminCode = document.getString("adminCode");
        this.adminCodeLogin = document.getBoolean("adminCodeLogin");
        this.hellCode = document.getString("hellCode");
        this.hellCodeLogin = document.getBoolean("hellCodeLogin");
        this.inventoriesUser = new InventoriesUser(document.getString("inventory"), document.getString("enderchest"), document.getString("armor"));
        this.pierscienDoswiadczenia = document.getInteger("pierscienDoswiadczenia");
        this.pierscienDoswiadczeniaTime = document.getLong("pierscienDoswiadczeniaTime");
        this.krytyk = document.getDouble("krytyk");
    }

    public boolean isBanned() {
        return !(banInfo.equalsIgnoreCase("false"));
    }

    public boolean isMuted() {
        return !(muteInfo.equalsIgnoreCase("false"));
    }

    public Document toDocument() {
        return new Document().append("_id", this.id.toString())
                .append("name", this.name)
                .append("banInfo", this.banInfo)
                .append("muteInfo", this.muteInfo)
                .append("punishmentHistory", this.punishmentHistory)
                .append("rankName", this.rankUser.getRankType().getName())
                .append("rankPlayerName", this.rankPlayerUser.getRankType().getName())
                .append("rankPlayerTime", this.rankPlayerUser.getTime())
                .append("rankChestCooldown", this.rankChestCooldown)
                .append("lvl", this.lvl)
                .append("exp", this.exp)
                .append("kasa", this.kasa)
                .append("hellcoins", this.hellcoins)
                .append("msgOff", this.msgOff)
                .append("adminCode", this.adminCode)
                .append("adminCodeLogin", this.adminCodeLogin)
                .append("hellCode", this.hellCode)
                .append("hellCodeLogin", this.hellCodeLogin)
                .append("inventory", this.inventoriesUser.getInventory())
                .append("enderchest", this.inventoriesUser.getEnderchest())
                .append("armor", this.inventoriesUser.getArmor())
                .append("pierscienDoswiadczenia", this.pierscienDoswiadczenia)
                .append("pierscienDoswiadczeniaTime", this.pierscienDoswiadczeniaTime)
                .append("krytyk", this.krytyk);
    }


}
