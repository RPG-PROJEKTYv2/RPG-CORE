package rpg.rpgcore.user;

import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import rpg.rpgcore.inventories.InventoriesUser;
import rpg.rpgcore.ranks.RankPlayerUser;
import rpg.rpgcore.ranks.RankUser;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.ranks.types.RankTypePlayer;
import rpg.rpgcore.utils.DoubleUtils;
import rpg.rpgcore.utils.Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class User implements Cloneable {
    private final UUID id;
    private String name;
    private String banInfo;
    private String muteInfo;
    private String punishmentHistory;
    private RankUser rankUser;
    private RankPlayerUser rankPlayerUser;
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
    private InventoriesUser inventoriesUser;
    private int pierscienDoswiadczenia;
    private long pierscienDoswiadczeniaTime;
    private double krytyk;
    private long kitCooldown;
    private boolean tworca;
    private boolean firstTime;
    private int serverJoins;
    private Map<String, Integer> mobMap;
    private UUID lastMsgUUID;
    private int sezonowiecPoints;
    private long rankLeftForUpdate;

    public User(final UUID id, final String name) {
        this.id = id;
        this.name = name;
        this.banInfo = "false";
        this.muteInfo = "false";
        this.punishmentHistory = "";
        this.rankUser = new RankUser(RankType.GRACZ);
        this.rankPlayerUser = new RankPlayerUser(RankTypePlayer.GRACZ, 0L);
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
        this.kitCooldown = 0;
        this.tworca = false;
        this.firstTime = true;
        this.serverJoins = 0;
        this.mobMap = new HashMap<>();
        this.sezonowiecPoints = 0;
        this.rankLeftForUpdate = 0L;
    }

    public User(final Document document) {
        this.id = UUID.fromString(document.getString("_id"));
        this.name = document.getString("name");
        this.banInfo = document.getString("banInfo");
        this.muteInfo = document.getString("muteInfo");
        this.punishmentHistory = document.getString("punishmentHistory");
        this.rankUser = new RankUser(RankType.valueOf(document.getString("rankName")));
        long rankPlayerTime;
        try {
            rankPlayerTime = document.getLong("rankPlayerTime");
        } catch (ClassCastException e) {
            rankPlayerTime = (long) document.getInteger("rankPlayerTime");
        }
        this.rankPlayerUser = new RankPlayerUser(RankTypePlayer.valueOf(document.getString("rankPlayerName").toUpperCase()), rankPlayerTime);
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
        long pierscienDoswiadczeniaTime;
        try {
            pierscienDoswiadczeniaTime = document.getLong("pierscienDoswiadczeniaTime");
        } catch (ClassCastException e) {
            pierscienDoswiadczeniaTime = (long) document.getInteger("pierscienDoswiadczeniaTime");
        }
        this.pierscienDoswiadczeniaTime = pierscienDoswiadczeniaTime;
        this.krytyk = document.getDouble("krytyk");
        long kitCooldown;
        try {
            kitCooldown = document.getLong("kitCooldown");
        } catch (ClassCastException e) {
            kitCooldown = (long) document.getInteger("kitCooldown");
        }
        this.kitCooldown = kitCooldown;
        this.tworca = document.getBoolean("tworca");
        this.firstTime = (document.containsKey("firstTime") ? document.getBoolean("firstTime") : true);
        this.serverJoins = (document.containsKey("serverJoins") ? document.getInteger("serverJoins") : 0);
        this.sezonowiecPoints = (document.containsKey("sezonowiecPoints") ? document.getInteger("sezonowiecPoints") : 0);
        this.mobMap = new HashMap<>();
        if (document.containsKey("rankLeftForUpdate")) {
            try {
                this.rankLeftForUpdate = document.getLong("rankLeftForUpdate");
            } catch (ClassCastException e) {
                this.rankLeftForUpdate = (long) document.getInteger("rankLeftForUpdate");
            }
        } else {
            this.rankLeftForUpdate = this.rankPlayerUser.getTime() - System.currentTimeMillis();
        }
        final Document mobMapD = (document.containsKey("mobMap") ? (Document) document.get("mobMap") : new Document());
        if (!mobMapD.isEmpty()) {
            for (final String key : mobMapD.keySet()) {
                if (key.equals("_id")) continue;
                this.mobMap.put(key.replaceAll("_", " ").replaceAll("Lvl", "Lvl."), mobMapD.getInteger(key));
            }
        }
    }

    public boolean isBanned() {
        return !(banInfo.equalsIgnoreCase("false"));
    }

    public boolean isMuted() {
        return !(muteInfo.equalsIgnoreCase("false"));
    }

    public void giveKitCooldown() {
        this.kitCooldown = System.currentTimeMillis() + 86_400_000L;
    }

    public boolean hasKitCooldown() {
        return this.kitCooldown > System.currentTimeMillis();
    }

    public String getKitCooldown() {
        return Utils.durationToString(this.kitCooldown - System.currentTimeMillis(), true);
    }

    public void incrementServerJoins() {
        this.serverJoins++;
    }

    public void incrementMobKill(final String mobName) {
        if (this.mobMap.containsKey(mobName)) {
            this.mobMap.put(mobName, this.mobMap.get(mobName) + 1);
        } else {
            this.mobMap.put(mobName, 1);
        }
    }

    public void incrementSezonowiecPoints() {
        this.sezonowiecPoints++;
    }

    public Document toDocument() {
        final Document mobMapD = new Document("_id", "mobMap");
        for (final String key : this.mobMap.keySet()) {
            mobMapD.append(key.replaceAll(" ", "_").replaceAll("Lvl\\.", "Lvl"), this.mobMap.get(key));
        }
        return new Document().append("_id", this.id.toString())
                .append("name", this.name)
                .append("banInfo", this.banInfo)
                .append("muteInfo", this.muteInfo)
                .append("punishmentHistory", this.punishmentHistory)
                .append("rankName", this.rankUser.getRankType().getName())
                .append("rankPlayerName", this.rankPlayerUser.getRankType().getName())
                .append("rankPlayerTime", this.rankPlayerUser.getTime())
                .append("lvl", this.lvl)
                .append("exp", DoubleUtils.round(this.exp, 2))
                .append("kasa", DoubleUtils.round(this.kasa, 2))
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
                .append("krytyk", this.krytyk)
                .append("kitCooldown", this.kitCooldown)
                .append("tworca", this.tworca)
                .append("firstTime", this.firstTime)
                .append("serverJoins", this.serverJoins)
                .append("sezonowiecPoints", this.sezonowiecPoints)
                .append("rankLeftForUpdate", this.rankLeftForUpdate)
                .append("mobMap", mobMapD);
    }


    @Override
    public User clone() {
        try {
            final User user = (User) super.clone();
            user.setRankUser(this.rankUser.clone());
            user.setRankPlayerUser(this.rankPlayerUser.clone());
            user.setInventoriesUser(this.inventoriesUser.clone());
            return user;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
