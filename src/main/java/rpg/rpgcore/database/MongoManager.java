package rpg.rpgcore.database;

import com.mongodb.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
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
        DBCollection collection = database.getCollection("hellrpg_spawn");

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

        collection = database.getCollection("hellrpg_spawn");
        result = collection.find();
        if (result.hasNext()) {
            DBObject obj = result.next();
            this.setSpawn(new Location(Bukkit.getWorld((String) obj.get("world")), (double) obj.get("x"), (double) obj.get("y"), (double) obj.get("z"), Float.parseFloat(obj.get("yaw").toString()), Float.parseFloat(obj.get("pitch").toString())));
        } else {
            this.setFirstSpawn();
        }

        collection = database.getCollection("hellrpg_gracze");
        result = collection.find();
        while (result.hasNext()) {
            DBObject obj = result.next();
            String nick = (String) obj.get("nick");
            UUID uuid = UUID.fromString(obj.get("_id").toString());
            System.out.println(uuid);
            String punishmentHistory = (String) obj.get("punishmentHistory");
            int lvl = Integer.parseInt(String.format("%.0f", Double.valueOf(String.valueOf(obj.get("level")))));
            double exp = Double.parseDouble(String.valueOf(obj.get("exp")));
            double kasa = Double.parseDouble(String.valueOf(obj.get("kasa")));
            int hellCoins = Integer.parseInt(String.format("%.0f", Double.valueOf(String.valueOf(obj.get("hellcoins")))));
            boolean pierscienDoswiadczenia = Boolean.parseBoolean(String.valueOf(obj.get("pierscien_doswiadczenia")));
            Date pierscienDoswiadczeniaCzas = new Date(Long.parseLong(String.valueOf(obj.get("pierscien_doswiadczenia_czas"))));

            collection = database.getCollection("hellrpg_bany");
            obj = collection.find(new BasicDBObject("_id", uuid.toString())).next();
            String banInfo = (String) obj.get("banInfo");

            collection = database.getCollection("hellrpg_muty");
            obj = collection.findOne(new BasicDBObject("_id", uuid.toString()));
            String muteInfo = (String) obj.get("muteInfo");

            collection = database.getCollection("hellrpg_osiagniecia");
            obj = collection.findOne(new BasicDBObject("_id", uuid.toString()));
            int osMoby = Integer.parseInt(String.format("%.0f", Double.valueOf(String.valueOf(obj.get("osMoby")))));
            int osLudzie = Integer.parseInt(String.format("%.0f", Double.valueOf(String.valueOf(obj.get("osLudzie")))));
            int osSakwy = Integer.parseInt(String.format("%.0f", Double.valueOf(String.valueOf(obj.get("osSakwy")))));
            int osNiesy = Integer.parseInt(String.format("%.0f", Double.valueOf(String.valueOf(obj.get("osNiesy")))));
            int osRybak = Integer.parseInt(String.format("%.0f", Double.valueOf(String.valueOf(obj.get("osRybak")))));
            int osDrwal = Integer.parseInt(String.format("%.0f", Double.valueOf(String.valueOf(obj.get("osDrwal")))));
            int osGornik = Integer.parseInt(String.format("%.0f", Double.valueOf(String.valueOf(obj.get("osGornik")))));
            String osMobyAccept = (String) obj.get("osMobyAccept");
            String osLudzieAccept = (String) obj.get("osLudzieAccept");
            String osSakwyAccept = (String) obj.get("osSakwyAccept");
            String osNiesyAccept = (String) obj.get("osNiesyAccept");
            String osRybakAccept = (String) obj.get("osRybakAccept");
            String osDrwalAccept = (String) obj.get("osDrwalAccept");
            String osGornikAccept = (String) obj.get("osGornikAccept");
            rpgcore.getPlayerManager().createPlayer(nick, uuid, banInfo, muteInfo, punishmentHistory, lvl, exp, osMoby, osLudzie, osSakwy, osNiesy, osRybak, osDrwal, osGornik,
                    osMobyAccept, osLudzieAccept, osSakwyAccept, osNiesyAccept, osRybakAccept, osDrwalAccept, osGornikAccept, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, kasa);

            collection = database.getCollection("hellrpg_bao");
            obj = collection.findOne(new BasicDBObject("_id", uuid.toString()));
            rpgcore.getBaoManager().updateBaoBonusy(uuid, String.valueOf(obj.get("BAO_BONUSY")));
            rpgcore.getBaoManager().updateBaoBonusyWartosci(uuid, String.valueOf(obj.get("BAO_WARTOSCI")));

            collection = database.getCollection("hellrpg_rybak");
            obj = collection.findOne(new BasicDBObject("_id", uuid.toString()));
            rpgcore.getRybakNPC().setPlayerRybakMisje(uuid, String.valueOf(obj.get("RYBAK_MISJE")));
            rpgcore.getRybakNPC().setPlayerPostep(uuid, Integer.parseInt(String.format("%.0f", Double.valueOf(String.valueOf(obj.get("RYBAK_POSTEP"))))));
            rpgcore.getRybakNPC().setPlayerRybakSredniDMG(uuid, Double.parseDouble(String.valueOf(obj.get("RYBAK_SRDMG"))));
            rpgcore.getRybakNPC().setPlayerRybakSredniDef(uuid, Double.parseDouble(String.valueOf(obj.get("RYBAK_SRDEF"))));
            rpgcore.getRybakNPC().setPlayerRybakDodatkowyDMG(uuid, Double.parseDouble(String.valueOf(obj.get("RYBAK_DDMG"))));
            rpgcore.getRybakNPC().setPlayerRybakBlok(uuid, Double.parseDouble(String.valueOf(obj.get("RYBAK_BLOK"))));

            try {
                collection = database.getCollection("hellrpg_akcesoria");
                obj = collection.findOne(new BasicDBObject("_id", uuid.toString()));
                rpgcore.getAkcesoriaManager().createAkcesoriaGUI(uuid, Utils.itemStackArrayFromBase64(String.valueOf(obj.get("Akcesoria"))));

                collection = database.getCollection("hellrpg_targi");
                obj = collection.findOne(new BasicDBObject("_id", uuid.toString()));
                rpgcore.getTargManager().putPlayerInTargMap(uuid, Utils.fromBase64(String.valueOf(obj.get("Targ")), "&f&lTarg gracza &3" + rpgcore.getPlayerManager().getPlayerName(uuid)));

                collection = database.getCollection("hellrpg_magazyny");
                obj = collection.findOne(new BasicDBObject("_id", uuid.toString()));
                rpgcore.getMagazynierNPC().loadAll(uuid, (String) obj.get("Magazyny"));
            } catch (final IOException e) {
                e.printStackTrace();
            }

            collection = database.getCollection("hellrpg_kolekcjoner");
            obj = collection.findOne(new BasicDBObject("_id", uuid.toString()));
            rpgcore.getKolekcjonerNPC().loadAll(
                    uuid,
                    (String) obj.get("kolekcjonerPostep"),
                    Integer.parseInt(String.format("%.0f", Double.valueOf(String.valueOf(obj.get("postepMisji"))))),
                    Double.parseDouble(String.valueOf(obj.get("kolekcjonerSredniDMG"))),
                    Double.parseDouble(String.valueOf(obj.get("kolekcjonerSredniDef"))),
                    Double.parseDouble(String.valueOf(obj.get("kolekcjonerSredniKryt"))));
        }

        collection = database.getCollection("hellrpg_gildie");
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
                    Boolean.parseBoolean(String.valueOf(obj.get("pvp"))),
                    Integer.parseInt(String.format("%.0f", Double.valueOf(String.valueOf(obj.get("points"))))),
                    Integer.parseInt(String.format("%.0f", Double.valueOf(String.valueOf(obj.get("lvl"))))),
                    Double.parseDouble(String.valueOf(obj.get("exp"))),
                    Integer.parseInt(String.format("%.0f", Double.valueOf(String.valueOf(obj.get("balance"))))),
                    Double.parseDouble(String.valueOf(obj.get("dodatkowyExp"))),
                    Double.parseDouble(String.valueOf(obj.get("sredniDmg"))),
                    Double.parseDouble(String.valueOf(obj.get("sredniDef"))),
                    Double.parseDouble(String.valueOf(obj.get("silnyNaLudzi"))),
                    Double.parseDouble(String.valueOf(obj.get("defNaLudzi"))),
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
        DBCollection collection = database.getCollection("hellrpg_spawn");

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

    public void createPlayer(final Player player, final String nick, final UUID uuid, final String banInfo, final String muteInfo) {
        rpgcore.getAkcesoriaManager().createAkcesoriaGUINew(uuid);
        rpgcore.getTargManager().createPlayerTargGUI(uuid);
        rpgcore.getMagazynierNPC().createAll(uuid);

        DB database = pool.getPool().getDB("minecraft");
        DBCollection collection = database.getCollection("hellrpg_gracze");

        BasicDBObject document = new BasicDBObject();

        document.put("_id", uuid.toString());
        document.put("nick", nick);
        document.put("punishmentHistory", nick);
        document.put("level", 1);
        document.put("exp", 0.0);
        document.put("kasa", 100.0);
        document.put("hellcoins", 0);
        document.put("pierscien_doswiadczenia", false);
        document.put("pierscien_doswiadczenia_czas", 0);
        collection.insert(document);

        collection = database.getCollection("hellrpg_bany");
        document = new BasicDBObject();
        document.put("_id", uuid.toString());
        document.put("banInfo", banInfo);
        collection.insert(document);

        collection = database.getCollection("hellrpg_muty");
        document = new BasicDBObject();
        document.put("_id", uuid.toString());
        document.put("muteInfo", muteInfo);
        collection.insert(document);

        collection = database.getCollection("hellrpg_ekwipunek");
        document = new BasicDBObject();
        document.put("_id", uuid.toString());
        document.put("ekwipunek", Utils.toBase64(player.getInventory()));
        collection.insert(document);

        collection = database.getCollection("hellrpg_enderchest");
        document = new BasicDBObject();
        document.put("_id", uuid.toString());
        document.put("enderchest", Utils.toBase64(player.getEnderChest()));
        collection.insert(document);

        collection = database.getCollection("hellrpg_zbroja");
        document = new BasicDBObject();
        document.put("_id", uuid.toString());
        document.put("zbroja", Utils.itemStackArrayToBase64(player.getInventory().getArmorContents()));
        collection.insert(document);

        collection = database.getCollection("hellrpg_osiagniecia");
        document = new BasicDBObject();
        document.put("_id", uuid.toString());
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
        collection.insert(document);

        collection = database.getCollection("hellrpg_bao");
        document = new BasicDBObject();
        document.put("_id", uuid.toString());
        document.put("BAO_BONUSY", "Brak Bonusu,Brak Bonusu,Brak Bonusu,Brak Bonusu,Brak Bonusu");
        document.put("BAO_WARTOSCI", "0,0,0,0,0");
        collection.insert(document);


        collection = database.getCollection("hellrpg_rybak");
        document = new BasicDBObject();
        document.put("_id", uuid.toString());
        document.put("RYBAK_MISJE", "false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false");
        document.put("RYBAK_POSTEP", 0);
        document.put("RYBAK_SRDMG", 0.0);
        document.put("RYBAK_SRDEF", 0.0);
        document.put("RYBAK_DDMG", 0.0);
        document.put("RYBAK_BLOK", 0.0);
        collection.insert(document);

        collection = database.getCollection("hellrpg_akcesoria");
        document = new BasicDBObject();
        document.put("_id", uuid.toString());
        document.put("Akcesoria", Utils.itemStackArrayToBase64(rpgcore.getAkcesoriaManager().getAllAkcesoria(uuid)));
        collection.insert(document);

        collection = database.getCollection("hellrpg_targi");
        document = new BasicDBObject();
        document.put("_id", uuid.toString());
        document.put("Targ", Utils.toBase64(rpgcore.getTargManager().getPlayerTarg(uuid)));
        collection.insert(document);

        collection = database.getCollection("hellrpg_magazyny");
        document = new BasicDBObject();
        document.put("_id", uuid.toString());
        document.put("Magazyny", rpgcore.getMagazynierNPC().getPlayerAllMagazyny(uuid));
        collection.insert(document);

        collection = database.getCollection("hellrpg_kolekcjoner");
        document = new BasicDBObject();
        document.put("_id", uuid.toString());
        document.put("kolekcjonerPostep", rpgcore.getKolekcjonerNPC().getKolekcjonerPostepInString(uuid));
        document.put("postepMisji", rpgcore.getKolekcjonerNPC().getPostepMisji(uuid));
        document.put("kolekcjonerSredniDMG", rpgcore.getKolekcjonerNPC().getKolekcjonerSrednieDMG(uuid));
        document.put("kolekcjonerSredniDef", rpgcore.getKolekcjonerNPC().getKolekcjonerSredniDef(uuid));
        document.put("kolekcjonerSredniKryt", rpgcore.getKolekcjonerNPC().getKolekcjonerKryt(uuid));
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
        DBCollection collection = database.getCollection("hellrpg_bany");

        BasicDBObject query = new BasicDBObject();
        query.put("_id", uuid.toString());

        BasicDBObject update = new BasicDBObject();
        update.put("$set", new BasicDBObject("banInfo", banInfo));

        collection.update(query, update);

        pool.closePool();
    }

    public void mutePlayer(final UUID uuid, final String muteInfo) {
        DB database = pool.getPool().getDB("minecraft");
        DBCollection collection = database.getCollection("hellrpg_muty");

        BasicDBObject query = new BasicDBObject();
        query.put("_id", uuid.toString());

        BasicDBObject update = new BasicDBObject();
        update.put("$set", new BasicDBObject("muteInfo", muteInfo));

        collection.update(query, update);

        pool.closePool();
    }

    public void unBanPlayer(final UUID uuid) {
        DB database = pool.getPool().getDB("minecraft");
        DBCollection collection = database.getCollection("hellrpg_bany");

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
        DBCollection collection = database.getCollection("hellrpg_muty");

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
        DBCollection collection = database.getCollection("hellrpg_gracze");

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
        DBCollection collection = database.getCollection("hellrpg_gracze");

        BasicDBObject query = new BasicDBObject();
        query.put("_id", uuid.toString());

        //BasicDBObject document = this.getPlayerDocument(uuid);

        BasicDBObject update = new BasicDBObject();
        // update.put("$set", document);

        collection.update(query, update);

        pool.closePool();
    }

    public void tempUpdate() {
        for (UUID uuid : rpgcore.getPlayerManager().getPlayers()) {
            savePlayer(Bukkit.getPlayer(uuid), uuid);
        }
    }

    public void savePlayer(final Player player, final UUID uuid) {
        try {
            DB database = pool.getPool().getDB("minecraft");

            this.savePlayerDocument(uuid, database.getCollection("hellrpg_gracze"));
            this.saveBanDocument(uuid, database.getCollection("hellrpg_bany"));
            this.saveMuteDocument(uuid, database.getCollection("hellrpg_muty"));
            //this.savePlayerInventory(uuid, database.getCollection("hellrpg_inventory"));
            this.saveOsDocument(uuid, database.getCollection("hellrpg_osiagniecia"));
            this.saveBaoDocument(uuid, database.getCollection("hellrpg_bao"));
            this.saveRybakDocument(uuid, database.getCollection("hellrpg_rybak"));
            this.saveKolekcjonerDocument(uuid, database.getCollection("hellrpg_kolekcjoner"));
            this.saveMagazynyDocument(uuid, database.getCollection("hellrpg_magazyny"));
            this.saveTargDocument(uuid, database.getCollection("hellrpg_targi"));
            this.saveAkcesoriaDocument(uuid, database.getCollection("hellrpg_akcesoria"));
            this.saveEkwipunekDocument(player, database.getCollection("hellrpg_ekwipunek"));
            this.saveEnderchestDocument(player, database.getCollection("hellrpg_enderchest"));
            this.saveZbrojaDocument(player, database.getCollection("hellrpg_zbroja"));


            pool.closePool();

            Utils.sendToAdministration("&aPomyslnie zapisano gracza: &6" + rpgcore.getPlayerManager().getPlayerName(uuid));
            System.out.println("§8[§4lHell§8§lRPG§c§lCore§8] §aPomyslnie zapisano gracza: §6" + rpgcore.getPlayerManager().getPlayerName(uuid));
        } catch (final Exception e) {
            Utils.sendToAdministration("&cWystapil blad podczas zapisu gracza: &6" + rpgcore.getPlayerManager().getPlayerName(uuid));
            System.out.println("§8[§4lHell§8§lRPG§c§lCore§8] §cWystapil blad podczas zapisu gracza: §6" + rpgcore.getPlayerManager().getPlayerName(uuid));
            e.printStackTrace();
        } finally {
            pool.closePool();
        }
    }

    private void savePlayerDocument(final UUID uuid, final DBCollection collection) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", uuid.toString());

        BasicDBObject document = new BasicDBObject();
        document.put("_id", uuid.toString());
        document.put("nick", rpgcore.getPlayerManager().getPlayerName(uuid));
        document.put("punishmentHistory", rpgcore.getPlayerManager().getPlayerPunishmentHistory(uuid));
        document.put("level", rpgcore.getPlayerManager().getPlayerLvl(uuid));
        document.put("exp", rpgcore.getPlayerManager().getPlayerExp(uuid));
        document.put("kasa", String.format("%.2f", rpgcore.getPlayerManager().getPlayerKasa(uuid)));
        document.put("hellcoins", 0);
        document.put("pierscien_doswiadczenia", false);
        document.put("pierscien_doswiadczenia_czas", 0);

        BasicDBObject update = new BasicDBObject();
        update.put("$set", document);

        collection.update(query, update);
    }

    private void saveBanDocument(final UUID uuid, final DBCollection collection) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", uuid.toString());

        BasicDBObject document = new BasicDBObject();
        document.put("_id", uuid.toString());
        document.put("banInfo", rpgcore.getPlayerManager().getPlayerBanInfo(uuid));

        BasicDBObject update = new BasicDBObject();
        update.put("$set", document);

        collection.update(query, update);
    }

    private void saveMuteDocument(final UUID uuid, final DBCollection collection) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", uuid.toString());

        BasicDBObject document = new BasicDBObject();
        document.put("_id", uuid.toString());
        document.put("muteInfo", rpgcore.getPlayerManager().getPlayerMuteInfo(uuid));

        BasicDBObject update = new BasicDBObject();
        update.put("$set", document);

        collection.update(query, update);
    }

    private void saveOsDocument(final UUID uuid, final DBCollection collection) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", uuid.toString());

        BasicDBObject document = new BasicDBObject();
        document.put("_id", uuid.toString());
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

        BasicDBObject update = new BasicDBObject();
        update.put("$set", document);

        collection.update(query, update);
    }

    private void saveBaoDocument(final UUID uuid, final DBCollection collection) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", uuid.toString());

        BasicDBObject document = new BasicDBObject();
        document.put("_id", uuid.toString());
        document.put("BAO_BONUSY", rpgcore.getBaoManager().getBaoBonusy(uuid));
        document.put("BAO_WARTOSCI", rpgcore.getBaoManager().getBaoBonusyWartosci(uuid));

        BasicDBObject update = new BasicDBObject();
        update.put("$set", document);

        collection.update(query, update);
    }

    private void saveRybakDocument(final UUID uuid, final DBCollection collection) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", uuid.toString());

        BasicDBObject document = new BasicDBObject();
        document.put("_id", uuid.toString());
        document.put("RYBAK_MISJE", rpgcore.getRybakNPC().getPlayerRybakMisje(uuid));
        document.put("RYBAK_POSTEP", rpgcore.getRybakNPC().getPlayerPostep(uuid));
        document.put("RYBAK_SRDMG", rpgcore.getRybakNPC().getPlayerRybakSredniDMG(uuid));
        document.put("RYBAK_SRDEF", rpgcore.getRybakNPC().getPlayerRybakSredniDef(uuid));
        document.put("RYBAK_DDMG", rpgcore.getRybakNPC().getPlayerRybakDodatkowyDMG(uuid));
        document.put("RYBAK_BLOK", rpgcore.getRybakNPC().getPlayerRybakBlok(uuid));

        BasicDBObject update = new BasicDBObject();
        update.put("$set", document);

        collection.update(query, update);
    }

    private void saveKolekcjonerDocument(final UUID uuid, final DBCollection collection) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", uuid.toString());

        BasicDBObject document = new BasicDBObject();
        document.put("_id", uuid.toString());
        document.put("kolekcjonerPostep", rpgcore.getKolekcjonerNPC().getKolekcjonerPostepInString(uuid));
        document.put("postepMisji", rpgcore.getKolekcjonerNPC().getPostepMisji(uuid));
        document.put("kolekcjonerSredniDMG", rpgcore.getKolekcjonerNPC().getKolekcjonerSrednieDMG(uuid));
        document.put("kolekcjonerSredniDef", rpgcore.getKolekcjonerNPC().getKolekcjonerSredniDef(uuid));
        document.put("kolekcjonerSredniKryt", rpgcore.getKolekcjonerNPC().getKolekcjonerKryt(uuid));

        BasicDBObject update = new BasicDBObject();
        update.put("$set", document);

        collection.update(query, update);
    }

    private void saveMagazynyDocument(final UUID uuid, final DBCollection collection) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", uuid.toString());

        BasicDBObject document = new BasicDBObject();
        document.put("_id", uuid.toString());
        document.put("Magazyny", rpgcore.getMagazynierNPC().getPlayerAllMagazyny(uuid));

        BasicDBObject update = new BasicDBObject();
        update.put("$set", document);

        collection.update(query, update);
    }

    private void saveTargDocument(final UUID uuid, final DBCollection collection) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", uuid.toString());

        BasicDBObject document = new BasicDBObject();
        document.put("_id", uuid.toString());
        document.put("Targ", Utils.toBase64(rpgcore.getTargManager().getPlayerTarg(uuid)));

        BasicDBObject update = new BasicDBObject();
        update.put("$set", document);

        collection.update(query, update);
    }

    private void saveAkcesoriaDocument(final UUID uuid, final DBCollection collection) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", uuid.toString());

        BasicDBObject document = new BasicDBObject();
        document.put("_id", uuid.toString());
        document.put("Akcesoria", Utils.itemStackArrayToBase64(rpgcore.getAkcesoriaManager().getAllAkcesoria(uuid)));

        BasicDBObject update = new BasicDBObject();
        update.put("$set", document);

        collection.update(query, update);
    }

    private void saveEkwipunekDocument(final Player player, final DBCollection collection) {
        DBCursor cursor = collection.find(new BasicDBObject("_id", player.getUniqueId().toString()));
        if (cursor.hasNext()) {
            BasicDBObject query = new BasicDBObject();
            query.put("_id", player.getUniqueId().toString());

            BasicDBObject document = new BasicDBObject();
            document.put("_id", player.getUniqueId().toString());
            document.put("ekwipunek", Utils.toBase64(player.getInventory()));

            BasicDBObject update = new BasicDBObject();
            update.put("$set", document);

            collection.update(query, update);
        } else {
            BasicDBObject document = new BasicDBObject();
            document.put("_id", player.getUniqueId().toString());
            document.put("ekwipunek", Utils.toBase64(player.getInventory()));
            collection.insert(document);
        }
        System.out.println("Zapisano ekwipunek gracza " + player.getName());
    }

    private void saveEnderchestDocument(final Player player, final DBCollection collection) {
        DBCursor cursor = collection.find(new BasicDBObject("_id", player.getUniqueId().toString()));
        if (cursor.hasNext()) {
            BasicDBObject query = new BasicDBObject();
            query.put("_id", player.getUniqueId().toString());

            BasicDBObject document = new BasicDBObject();
            document.put("_id", player.getUniqueId().toString());
            document.put("enderchest", Utils.toBase64(player.getEnderChest()));

            BasicDBObject update = new BasicDBObject();
            update.put("$set", document);

            collection.update(query, update);
        } else {
            BasicDBObject document = new BasicDBObject();
            document.put("_id", player.getUniqueId().toString());
            document.put("enderchest", Utils.toBase64(player.getEnderChest()));
            collection.insert(document);
        }
    }

    private void saveZbrojaDocument(final Player player, final DBCollection collection) {
        DBCursor cursor = collection.find(new BasicDBObject("_id", player.getUniqueId().toString()));
        if (cursor.hasNext()) {
            BasicDBObject query = new BasicDBObject();
            query.put("_id", player.getUniqueId().toString());

            BasicDBObject document = new BasicDBObject();
            document.put("_id", player.getUniqueId().toString());
            document.put("zbroja", Utils.itemStackArrayToBase64(player.getInventory().getArmorContents()));

            BasicDBObject update = new BasicDBObject();
            update.put("$set", document);

            collection.update(query, update);
        } else {
            BasicDBObject document = new BasicDBObject();
            document.put("_id", player.getUniqueId().toString());
            document.put("zbroja", Utils.itemStackArrayToBase64(player.getInventory().getArmorContents()));
            collection.insert(document);
        }
    }

    public void saveGuildFirst(final String tag) {
        DB database = pool.getPool().getDB("minecraft");
        DBCollection collection = database.getCollection("hellrpg_gildie");
        BasicDBObject guild = createGuildDocument(tag);
        collection.insert(guild);
        pool.closePool();
    }

    public void saveGuild() {
        String tag2 = "";
        try {
            for (String tag : rpgcore.getGuildManager().getListOfGuilds()) {
                tag2 = tag;
                DB database = pool.getPool().getDB("minecraft");
                DBCollection collection = database.getCollection("hellrpg_gildie");
                BasicDBObject query = new BasicDBObject();
                query.put("_id", tag);

                BasicDBObject guild = createGuildDocument(tag);

                BasicDBObject update = new BasicDBObject();
                update.put("$set", guild);

                collection.update(query, update);
                pool.closePool();

                Utils.sendToAdministration("&aPomyslnie zapisano gilde: &6" + tag);
                System.out.println("§8[§4lHell§8§lRPG§c§lCore§8] §aPomyslnie zapisano klan: §6" + tag);
            }
        } catch (final Exception e) {
            Utils.sendToAdministration("&cWystapil blad podczas zapisu klanu &6:" + tag2);
            System.out.println("§8[§4lHell§8§lRPG§c§lCore§8] §cWystapil blad podczas zapisu klanu: §6" + tag2);
            e.printStackTrace();
        } finally {
            pool.closePool();
        }
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
            lastOnlineMap.put(String.valueOf(entry.getKey()), entry.getValue().getTime());
        }

        guild.put("lastOnlineMap", lastOnlineMap);
        return guild;
    }

    public void removeGuild(final String tag) {

        DB database = pool.getPool().getDB("minecraft");
        DBCollection collection = database.getCollection("hellrpg_gildie");
        BasicDBObject toRemove = new BasicDBObject("_id", tag);
        collection.remove(toRemove);

        pool.closePool();
    }

    public void onDisable() {
        pool.closePool();
    }


}
