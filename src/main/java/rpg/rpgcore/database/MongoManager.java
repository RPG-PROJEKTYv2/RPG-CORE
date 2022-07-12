package rpg.rpgcore.database;

import com.mongodb.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.spawn.SpawnManager;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.*;

public class MongoManager {

    private final RPGCORE rpgcore;
    private final MongoConnectionPoolManager pool;

    public MongoManager(final RPGCORE rpgcore) {
        this.pool = new MongoConnectionPoolManager(rpgcore);
        this.rpgcore = rpgcore;
    }

    public void setFirstSpawn() {
        final String w = "world";
        final double x = SpawnManager.defaultSpawnX;
        final double y = SpawnManager.defaultSpawnY;
        final double z = SpawnManager.defaultSpawnZ;

        DB database = pool.getPool().getDB("minecraft");
        DBCollection collection = database.getCollection("spawn");

        BasicDBObject document = new BasicDBObject();

        document.put("_id", "spawn");
        document.put("world", w);
        document.put("x", x);
        document.put("y", y);
        document.put("z", z);
        document.put("yaw", 0.0);
        document.put("pitch", 0.0);

        collection.insert(document);

        final Location newLocspawn = new Location(Bukkit.getWorld(w), x, y, z, 0.0F, 0.0F);
        rpgcore.getSpawnManager().setSpawn(newLocspawn);

        pool.closePool();
    }

    public void loadAll() {

        DB database = pool.getPool().getDB("minecraft");
        DBCollection collection;
        DBCursor result;

        collection = database.getCollection("spawn");
        result = collection.find();
        if (result.hasNext()) {
            DBObject obj = result.next();
            this.setSpawn(new Location(Bukkit.getWorld((String) obj.get("world")), (double) obj.get("x"), (double) obj.get("y"), (double) obj.get("z"), Float.parseFloat(obj.get("yaw").toString()), Float.parseFloat(obj.get("pitch").toString())));
        } else {
            this.setFirstSpawn();
        }


        collection = database.getCollection("players");
        result = collection.find();
        while (result.hasNext()) {
            DBObject obj = result.next();
            UUID uuid = UUID.fromString(obj.get("_id").toString());
            System.out.println(uuid);
            rpgcore.getPlayerManager().createPlayer(
                    (String) obj.get("nick"),
                    uuid,
                    (String) obj.get("banInfo"),
                    (String) obj.get("muteInfo"),
                    (String) obj.get("punishmentHistory"),
                    Integer.parseInt(String.format("%.0f", Double.valueOf(String.valueOf(obj.get("level"))))),
                    (double) obj.get("exp"),
                    (int) obj.get("osMoby"),
                    (int) obj.get("osLudzie"),
                    (int) obj.get("osSakwy"),
                    (int) obj.get("osNiesy"),
                    (int) obj.get("osRybak"),
                    (int) obj.get("osDrwal"),
                    (int) obj.get("osGornik"),
                    (String) obj.get("osMobyAccept"),
                    (String) obj.get("osLudzieAccept"),
                    (String) obj.get("osSakwyAccept"),
                    (String) obj.get("osNiesyAccept"),
                    (String) obj.get("osRybakAccept"),
                    (String) obj.get("osDrwalAccept"),
                    (String) obj.get("osGornikAccept"),
                    0.0,
                    0.0,
                    0.0,
                    0.0,
                    0.0,
                    0.0,
                    0.0,
                    0.0,
                    0.0,
                    0.0,
                    0.0,
                    0.0,
                    0.0,
                    Double.parseDouble((String) obj.get("kasa"))
            );
            rpgcore.getBaoManager().updateBaoBonusy(uuid, (String) obj.get("BAO_BONUSY"));
            rpgcore.getBaoManager().updateBaoBonusyWartosci(uuid, (String) obj.get("BAO_WARTOSCI"));
            rpgcore.getRybakNPC().setPlayerRybakMisje(uuid, (String) obj.get("RYBAK_MISJE"));
            rpgcore.getRybakNPC().setPlayerPostep(uuid, (int) obj.get("RYBAK_POSTEP"));
            rpgcore.getRybakNPC().setPlayerRybakSredniDMG(uuid, (double) obj.get("RYBAK_SRDMG"));
            rpgcore.getRybakNPC().setPlayerRybakSredniDef(uuid, (double) obj.get("RYBAK_SRDEF"));
            rpgcore.getRybakNPC().setPlayerRybakDodatkowyDMG(uuid, (double) obj.get("RYBAK_DDMG"));
            rpgcore.getRybakNPC().setPlayerRybakBlok(uuid, (double) obj.get("RYBAK_BLOK"));

            try {
                rpgcore.getAkcesoriaManager().createAkcesoriaGUI(uuid, Utils.itemStackArrayFromBase64((String) obj.get("Akcesoria")));
                rpgcore.getTargManager().putPlayerInTargMap(uuid, Utils.fromBase64((String) obj.get("Targ"), "&f&lTarg gracza &3" + rpgcore.getPlayerManager().getPlayerName(uuid)));
                //rpgcore.getMagazynierNPC().loadAll(uuid, (String) obj.get("Magazyny"));
            } catch (final IOException e) {
                e.printStackTrace();
            }

        }

        collection = database.getCollection("guilds");
        result = collection.find();
        while (result.hasNext()) {
            DBObject obj = result.next();
            final String guildName = obj.get("_id").toString();

            final List<UUID> members = new ArrayList<>();

            for (final String member : String.valueOf(obj.get("membersList")).split(",")) {
                members.add(UUID.fromString(member));
            }

            final Map<UUID, Integer> killsMap = new HashMap<>();
            BasicDBObject kills = (BasicDBObject) obj.get("killsMap");
            for (final String key : kills.keySet()) {
                killsMap.put(UUID.fromString(key), kills.getInt(key));
            }

            final Map<UUID, Integer> deathsMap = new HashMap<>();
            BasicDBObject deaths = (BasicDBObject) obj.get("deathsMap");
            for (final String key : deaths.keySet()) {
                deathsMap.put(UUID.fromString(key), deaths.getInt(key));
            }

            final Map<UUID, Double> expEarnedMap = new HashMap<>();
            BasicDBObject expEarned = (BasicDBObject) obj.get("expEarnedMap");
            for (final String key : expEarned.keySet()) {
                expEarnedMap.put(UUID.fromString(key), expEarned.getDouble(key));
            }

            final Map<UUID, Date> lastOnlineMap = new HashMap<>();
            BasicDBObject lastOnline = (BasicDBObject) obj.get("lastOnlineMap");
            for (final String key : lastOnline.keySet()) {
                lastOnlineMap.put(UUID.fromString(key), new Date(lastOnline.getLong(key)));
            }

            rpgcore.getGuildManager().loadGuild(
                    guildName,
                    (String) obj.get("description"),
                    UUID.fromString((String) obj.get("owner")),
                    (String) obj.get("coOwner"),
                    members,
                    (boolean) obj.get("pvp"),
                    (int) obj.get("points"),
                    (int) obj.get("lvl"),
                    (double) obj.get("exp"),
                    (int) obj.get("balance"),
                    (double) obj.get("dodatkowyExp"),
                    (double) obj.get("sredniDmg"),
                    (double) obj.get("sredniDef"),
                    (double) obj.get("silnyNaLudzi"),
                    (double) obj.get("defNaLudzi"),
                    killsMap,
                    deathsMap,
                    expEarnedMap,
                    lastOnlineMap);
        }
        pool.closePool();
    }


    public void setSpawn(final Location spawn) {

        final String w = spawn.getWorld().getName();
        final double x = spawn.getX();
        final double y = spawn.getY();
        final double z = spawn.getZ();
        final float yaw = spawn.getYaw();
        final float pitch = spawn.getPitch();

        DB database = pool.getPool().getDB("minecraft");
        DBCollection collection = database.getCollection("spawn");

        BasicDBObject query = new BasicDBObject();
        query.put("_id", "spawn");

        BasicDBObject document = new BasicDBObject();
        document.put("world", w);
        document.put("x", x);
        document.put("y", y);
        document.put("z", z);
        document.put("yaw", yaw);
        document.put("pitch", pitch);

        BasicDBObject update = new BasicDBObject();
        update.put("$set", document);

        collection.update(query, update);

        rpgcore.getSpawnManager().setSpawn(spawn);

        pool.closePool();
    }

    public void createPlayer(final String nick, final UUID uuid, final String banInfo, final String muteInfo) {
        rpgcore.getAkcesoriaManager().createAkcesoriaGUINew(uuid);
        rpgcore.getTargManager().createPlayerTargGUI(uuid);
        rpgcore.getMagazynierNPC().createAll(uuid);

        DB database = pool.getPool().getDB("minecraft");
        DBCollection collection = database.getCollection("players");

        BasicDBObject document = new BasicDBObject();

        document.put("_id", uuid.toString());
        document.put("nick", nick);
        document.put("banInfo", banInfo);
        document.put("muteInfo", muteInfo);
        document.put("level", 1);
        document.put("exp", 0);
        document.put("osMoby", 0);
        document.put("osLudzie", 0);
        document.put("osSakwy", 0);
        document.put("osNiesy", 0);
        document.put("osRybak", 0);
        document.put("osDrwal", 0);
        document.put("osGornik", 0);
        document.put("osMobyAccept", "false,false,false,false,false,false,false,false,false,false");
        document.put("osLudzieAccept", "false,false,false,false,false,false,false,false,false,false");
        document.put("osSakwyAccept", "false,false,false,false,false,false,false,false,false,false");
        document.put("osNiesyAccept", "false,false,false,false,false,false,false,false,false,false");
        document.put("osRybakAccept", "false,false,false,false,false,false,false,false,false,false");
        document.put("osDrwalAccept", "false,false,false,false,false,false,false,false,false,false");
        document.put("osGornikAccept", "false,false,false,false,false,false,false,false,false,false");
        document.put("kasa", 100.0);
        document.put("BAO_BONUSY", "Brak Bonusu,Brak Bonusu,Brak Bonusu,Brak Bonusu,Brak Bonusu");
        document.put("BAO_WARTOSCI", "0,0,0,0,0");
        document.put("RYBAK_MISJE","false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false");
        document.put("RYBAK_POSTEP", 0);
        document.put("RYBAK_SRDMG", 0.0);
        document.put("RYBAK_SRDEF", 0.0);
        document.put("RYBAK_DDMG", 0.0);
        document.put("RYBAK_BLOK", 0.0);
        document.put("Akcesoria", Utils.itemStackArrayToBase64(rpgcore.getAkcesoriaManager().getAllAkcesoria(uuid)));
        document.put("Targ", Utils.toBase64(rpgcore.getTargManager().getPlayerTarg(uuid)));
        document.put("Magazyny", rpgcore.getMagazynierNPC().getPlayerAllMagazyny(uuid));

        collection.insert(document);

        rpgcore.getPlayerManager().createPlayer(nick, uuid, "false", "false", "", 1, 0.0, 0, 0, 0, 0, 0, 0, 0, "false,false,false,false,false,false,false,false,false,false", "false,false,false,false,false,false,false,false,false,false", "false,false,false,false,false,false,false,false,false,false", "false,false,false,false,false,false,false,false,false,false", "false,false,false,false,false,false,false,false,false,false", "false,false,false,false,false,false,false,false,false,false", "false,false,false,false,false,false,false,false,false,false", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 100.0);

        rpgcore.getBaoManager().updateBaoBonusy(uuid, "Brak Bonusu,Brak Bonusu,Brak Bonusu,Brak Bonusu,Brak Bonusu");
        rpgcore.getBaoManager().updateBaoBonusyWartosci(uuid, "0,0,0,0,0");
        rpgcore.getRybakNPC().setPlayerRybakMisje(uuid, "false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false");
        rpgcore.getRybakNPC().setPlayerPostep(uuid, 0);
        rpgcore.getRybakNPC().setPlayerRybakSredniDMG(uuid, 0.0);
        rpgcore.getRybakNPC().setPlayerRybakSredniDef(uuid, 0.0);
        rpgcore.getRybakNPC().setPlayerRybakBlok(uuid, 0.0);
        rpgcore.getRybakNPC().setPlayerRybakDodatkowyDMG(uuid, 0.0);

        pool.closePool();
    }

    public void banPlayer(final UUID uuid, final String banInfo) {
        DB database = pool.getPool().getDB("minecraft");
        DBCollection collection = database.getCollection("players");

        BasicDBObject query = new BasicDBObject();
        query.put("_id", uuid.toString());

        BasicDBObject update = new BasicDBObject();
        update.put("$set", new BasicDBObject("banInfo", banInfo));

        collection.update(query, update);

        pool.closePool();
    }

    public void mutePlayer(final UUID uuid, final String muteInfo) {
        DB database = pool.getPool().getDB("minecraft");
        DBCollection collection = database.getCollection("players");

        BasicDBObject query = new BasicDBObject();
        query.put("_id", uuid.toString());

        BasicDBObject update = new BasicDBObject();
        update.put("$set", new BasicDBObject("muteInfo", muteInfo));

        collection.update(query, update);

        pool.closePool();
    }

    public void unBanPlayer(final UUID uuid) {
        DB database = pool.getPool().getDB("minecraft");
        DBCollection collection = database.getCollection("players");

        BasicDBObject query = new BasicDBObject();
        query.put("_id", uuid.toString());

        BasicDBObject update = new BasicDBObject();
        update.put("$set", new BasicDBObject("banInfo", "false"));

        collection.update(query, update);

        rpgcore.getPlayerManager().updatePlayerBanInfo(uuid, "false");

        pool.closePool();
    }

    public void unMutePlayer(final UUID uuid) {
        DB database = pool.getPool().getDB("minecraft");
        DBCollection collection = database.getCollection("players");

        BasicDBObject query = new BasicDBObject();
        query.put("_id", uuid.toString());

        BasicDBObject update = new BasicDBObject();
        update.put("$set", new BasicDBObject("muteInfo", "false"));

        collection.update(query, update);

        rpgcore.getPlayerManager().updatePlayerMuteInfo(uuid, "false");

        pool.closePool();
    }

    public void setPunishmentHistory(final UUID uuid, final String punishmentHistory) {
        DB database = pool.getPool().getDB("minecraft");
        DBCollection collection = database.getCollection("players");

        BasicDBObject query = new BasicDBObject();
        query.put("_id", uuid.toString());

        BasicDBObject update = new BasicDBObject();
        update.put("$set", new BasicDBObject("punishmentHistory", punishmentHistory));

        collection.update(query, update);

        rpgcore.getPlayerManager().updatePlayerPunishmentHistory(uuid, punishmentHistory);

        pool.closePool();
    }

    public void updatePlayer(final UUID uuid) {
        DB database = pool.getPool().getDB("minecraft");
        DBCollection collection = database.getCollection("players");

        BasicDBObject query = new BasicDBObject();
        query.put("_id", uuid.toString());

        BasicDBObject document = new BasicDBObject();
        document.put("_id", uuid.toString());
        document.put("nick", rpgcore.getPlayerManager().getPlayerName(uuid));
        document.put("level", rpgcore.getPlayerManager().getPlayerLvl(uuid));
        document.put("exp", rpgcore.getPlayerManager().getPlayerExp(uuid));
        document.put("kasa", String.format("%.2f", rpgcore.getPlayerManager().getPlayerKasa(uuid)));
        document.put("osMoby", rpgcore.getPlayerManager().getPlayerOsMoby(uuid));
        document.put("osMobyAccept", rpgcore.getPlayerManager().getOsMobyAccept(uuid));
        document.put("osLudzie", rpgcore.getPlayerManager().getPlayerOsLudzie(uuid));
        document.put("osLudzieAccept", rpgcore.getPlayerManager().getOsLudzieAccept(uuid));
        document.put("osSakwy", rpgcore.getPlayerManager().getPlayerOsSakwy(uuid));
        document.put("osSakwyAccept", rpgcore.getPlayerManager().getOsSakwyAccept(uuid));
        document.put("osNiesy", rpgcore.getPlayerManager().getPlayerOsNiesy(uuid));
        document.put("osNiesyAccept", rpgcore.getPlayerManager().getOsNiesyAccept(uuid));
        document.put("osRybak", rpgcore.getPlayerManager().getPlayerOsRybak(uuid));
        document.put("osRybakAccept", rpgcore.getPlayerManager().getOsRybakAccept(uuid));
        document.put("osDrwal", rpgcore.getPlayerManager().getPlayerOsDrwal(uuid));
        document.put("osDrwalAccept", rpgcore.getPlayerManager().getOsDrwalAccept(uuid));
        document.put("osGornik", rpgcore.getPlayerManager().getPlayerOsGornik(uuid));
        document.put("osGornikAccept", rpgcore.getPlayerManager().getOsGornikAccept(uuid));
        document.put("BAO_BONUSY", rpgcore.getBaoManager().getBaoBonusy(uuid));
        document.put("BAO_WARTOSCI", rpgcore.getBaoManager().getBaoBonusyWartosci(uuid));
        document.put("RYBAK_MISJE", rpgcore.getRybakNPC().getPlayerRybakMisje(uuid));
        document.put("RYBAK_POSTEP", rpgcore.getRybakNPC().getPlayerPostep(uuid));
        document.put("RYBAK_SRDMG", rpgcore.getRybakNPC().getPlayerRybakSredniDMG(uuid));
        document.put("RYBAK_SRDEF", rpgcore.getRybakNPC().getPlayerRybakSredniDef(uuid));
        document.put("RYBAK_DDMG", rpgcore.getRybakNPC().getPlayerRybakDodatkowyDMG(uuid));
        document.put("RYBAK_BLOK", rpgcore.getRybakNPC().getPlayerRybakBlok(uuid));
        document.put("Akcesoria", Utils.itemStackArrayToBase64(rpgcore.getAkcesoriaManager().getAllAkcesoria(uuid)));
        document.put("Targ", Utils.toBase64(rpgcore.getTargManager().getPlayerTarg(uuid)));
        document.put("Magazyny", rpgcore.getMagazynierNPC().getPlayerAllMagazyny(uuid));

        BasicDBObject update = new BasicDBObject();
        update.put("$set", document);

        collection.update(query, update);

        pool.closePool();
    }

    public void saveAllPlayers() {
        DB database = pool.getPool().getDB("minecraft");
        DBCollection collection = database.getCollection("players");
        for (UUID uuid : rpgcore.getPlayerManager().getPlayers()) {
            BasicDBObject query = new BasicDBObject();
            query.put("_id", uuid.toString());

            BasicDBObject document = new BasicDBObject();
            document.put("_id", uuid.toString());
            document.put("nick", rpgcore.getPlayerManager().getPlayerName(uuid));
            document.put("level", rpgcore.getPlayerManager().getPlayerLvl(uuid));
            document.put("exp", rpgcore.getPlayerManager().getPlayerExp(uuid));
            document.put("kasa", String.format("%.2f", rpgcore.getPlayerManager().getPlayerKasa(uuid)));
            document.put("osMoby", rpgcore.getPlayerManager().getPlayerOsMoby(uuid));
            document.put("osMobyAccept", rpgcore.getPlayerManager().getOsMobyAccept(uuid));
            document.put("osLudzie", rpgcore.getPlayerManager().getPlayerOsLudzie(uuid));
            document.put("osLudzieAccept", rpgcore.getPlayerManager().getOsLudzieAccept(uuid));
            document.put("osSakwy", rpgcore.getPlayerManager().getPlayerOsSakwy(uuid));
            document.put("osSakwyAccept", rpgcore.getPlayerManager().getOsSakwyAccept(uuid));
            document.put("osNiesy", rpgcore.getPlayerManager().getPlayerOsNiesy(uuid));
            document.put("osNiesyAccept", rpgcore.getPlayerManager().getOsNiesyAccept(uuid));
            document.put("osRybak", rpgcore.getPlayerManager().getPlayerOsRybak(uuid));
            document.put("osRybakAccept", rpgcore.getPlayerManager().getOsRybakAccept(uuid));
            document.put("osDrwal", rpgcore.getPlayerManager().getPlayerOsDrwal(uuid));
            document.put("osDrwalAccept", rpgcore.getPlayerManager().getOsDrwalAccept(uuid));
            document.put("osGornik", rpgcore.getPlayerManager().getPlayerOsGornik(uuid));
            document.put("osGornikAccept", rpgcore.getPlayerManager().getOsGornikAccept(uuid));
            document.put("BAO_BONUSY", rpgcore.getBaoManager().getBaoBonusy(uuid));
            document.put("BAO_WARTOSCI", rpgcore.getBaoManager().getBaoBonusyWartosci(uuid));
            document.put("RYBAK_MISJE", rpgcore.getRybakNPC().getPlayerRybakMisje(uuid));
            document.put("RYBAK_POSTEP", rpgcore.getRybakNPC().getPlayerPostep(uuid));
            document.put("RYBAK_SRDMG", rpgcore.getRybakNPC().getPlayerRybakSredniDMG(uuid));
            document.put("RYBAK_SRDEF", rpgcore.getRybakNPC().getPlayerRybakSredniDef(uuid));
            document.put("RYBAK_DDMG", rpgcore.getRybakNPC().getPlayerRybakDodatkowyDMG(uuid));
            document.put("RYBAK_BLOK", rpgcore.getRybakNPC().getPlayerRybakBlok(uuid));
            document.put("Akcesoria", Utils.itemStackArrayToBase64(rpgcore.getAkcesoriaManager().getAllAkcesoria(uuid)));
            document.put("Targ", Utils.toBase64(rpgcore.getTargManager().getPlayerTarg(uuid)));
            document.put("Magazyny", rpgcore.getMagazynierNPC().getPlayerAllMagazyny(uuid));

            BasicDBObject update = new BasicDBObject();
            update.put("$set", document);

            collection.update(query, update);
        }
        pool.closePool();
    }

    public void saveGuildFirst(final String tag) {
        DB database = pool.getPool().getDB("minecraft");
        DBCollection collection = database.getCollection("guilds");
        BasicDBObject guild = createGuildDocument(tag);
        collection.insert(guild);
        pool.closePool();
    }

    public void saveGuild(final String tag) {
        DB database = pool.getPool().getDB("minecraft");
        DBCollection collection = database.getCollection("guilds");
        BasicDBObject query = new BasicDBObject();
        query.put("_id", tag);

        BasicDBObject guild = createGuildDocument(tag);

        BasicDBObject update = new BasicDBObject();
        update.put("$set", guild);

        collection.update(query, update);
        pool.closePool();
    }

    private BasicDBObject createGuildDocument(final String tag) {
        final StringBuilder sb = new StringBuilder();
        BasicDBObject guild = new BasicDBObject();

        guild.put("_id", tag);
        guild.put("description", rpgcore.getGuildManager().getGuildDescription(tag));
        guild.put("owner", rpgcore.getGuildManager().getGuildOwner(tag).toString());
        guild.put("coOwner", rpgcore.getGuildManager().getGuildCoOwner(tag));
        for (UUID uuid : rpgcore.getGuildManager().getGuildMembers(tag)) {
            sb.append(uuid.toString());
            sb.append(",");
        }
        String members = String.valueOf(sb);
        int lastIndex = members.lastIndexOf(",");
        members = members.substring(0, lastIndex);
        System.out.println(members);
        guild.put("membersList", members);
        guild.put("pvp", rpgcore.getGuildManager().getGuildPvPStatus(tag));
        guild.put("points", rpgcore.getGuildManager().getGuildPoints(tag));
        guild.put("lvl", rpgcore.getGuildManager().getGuildLvl(tag));
        guild.put("exp", rpgcore.getGuildManager().getGuildExp(tag));
        guild.put("balance", rpgcore.getGuildManager().getGuildBalance(tag));
        guild.put("dodatkowyExp", rpgcore.getGuildManager().getGuildDodatkowyExp(tag));
        guild.put("sredniDmg", rpgcore.getGuildManager().getGuildSredniDmg(tag));
        guild.put("sredniDef", rpgcore.getGuildManager().getGuildSredniDef(tag));
        guild.put("silnyNaLudzi", rpgcore.getGuildManager().getGuildSilnyNaLudzi(tag));
        guild.put("defNaLudzi", rpgcore.getGuildManager().getGuildDefNaLudzi(tag));

        BasicDBObject killsMap = new BasicDBObject();
        for (Map.Entry<UUID, Integer> entry : rpgcore.getGuildManager().getGuildKills(tag).entrySet()) {
            killsMap.put(String.valueOf(entry.getKey()), entry.getValue());
        }

        guild.put("killsMap", killsMap);

        BasicDBObject deathsMap = new BasicDBObject();
        for (Map.Entry<UUID, Integer> entry : rpgcore.getGuildManager().getGuildDeaths(tag).entrySet()) {
            deathsMap.put(String.valueOf(entry.getKey()), entry.getValue());
        }

        guild.put("deathsMap", deathsMap);

        BasicDBObject expEarnedMap = new BasicDBObject();
        for (Map.Entry<UUID, Double> entry : rpgcore.getGuildManager().getGuildExpEarned(tag).entrySet()) {
            expEarnedMap.put(String.valueOf(entry.getKey()), entry.getValue());
        }

        guild.put("expEarnedMap", expEarnedMap);

        BasicDBObject lastOnlineMap = new BasicDBObject();
        for (Map.Entry<UUID, Date> entry : rpgcore.getGuildManager().getGuildLastOnline(tag).entrySet()) {
            lastOnlineMap.put(String.valueOf(entry.getKey()), entry.getValue());
        }

        guild.put("lastOnlineMap", lastOnlineMap);
        return guild;
    }

    public void removeGuild(final String tag) {

        DB database = pool.getPool().getDB("minecraft");
        DBCollection collection = database.getCollection("guilds");
        BasicDBObject toRemove = new BasicDBObject("_id", tag);
        collection.remove(toRemove);

        pool.closePool();
    }

    public void onDisable() {
        pool.closePool();
    }




}
