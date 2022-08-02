package rpg.rpgcore.database;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.spawn.SpawnManager;
import rpg.rpgcore.utils.Utils;

import javax.print.Doc;
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

        Document document = new Document();

        document.append("_id", "spawn");
        document.append("world", w);
        document.append("x", x);
        document.append("y", y);
        document.append("z", z);
        document.append("yaw", 0.0);
        document.append("pitch", 0.0);

        pool.getSpawn().insertOne(document);

        final Location newLocspawn = new Location(Bukkit.getWorld(w), x, y, z, 0.0F, 0.0F);
        rpgcore.getSpawnManager().setSpawn(newLocspawn);


    }

    private void repairMiresXD() {
        Document doc = new Document("_id", "7193813f-c9c3-37e6-b72b-4272a3898b80").append("nick", "Mires_").append("punishmentHistory", "").append("level", 130).append("exp", 0.0).append("kasa", "98897845000.00").append("hellcoins", 0).append("pierscien_doswiadczenia", false).append("pierscien_doswiadczenia_czas", 0);
        pool.getGracze().findOneAndReplace(new Document("_id", "7193813f-c9c3-37e6-b72b-4272a3898b80"), doc);
    }

    public void loadAll() {
        repairMiresXD();
        MongoCursor<Document> result;

        Document objSpawn = pool.getSpawn().find(new Document("_id", "spawn")).first();
        if (objSpawn != null) {
            String world = (String) objSpawn.get("world");
            double x = (double) objSpawn.get("x");
            double y = (double) objSpawn.get("y");
            double z = (double) objSpawn.get("z");
            float yaw = Float.parseFloat(String.valueOf((double) objSpawn.get("yaw")));
            float pitch = Float.parseFloat(String.valueOf((double) objSpawn.get("pitch")));
            this.setSpawn(new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch));
        } else {
            this.setFirstSpawn();
        }

        result = pool.getGracze().find().cursor();
        while (result.hasNext()) {
            Document obj = result.next();
            String nick = (String) obj.get("nick");
            UUID uuid = UUID.fromString(obj.get("_id").toString());
            String punishmentHistory = (String) obj.get("punishmentHistory");
            int lvl = Integer.parseInt(String.format("%.0f", Double.valueOf(String.valueOf(obj.get("level")))));
            double exp = Double.parseDouble(String.valueOf(obj.get("exp")));
            double kasa = Double.parseDouble(String.valueOf(obj.get("kasa")));
            int hellCoins = Integer.parseInt(String.format("%.0f", Double.valueOf(String.valueOf(obj.get("hellcoins")))));
            boolean pierscienDoswiadczenia = Boolean.parseBoolean(String.valueOf(obj.get("pierscien_doswiadczenia")));
            Date pierscienDoswiadczeniaCzas = new Date(Long.parseLong(String.valueOf(obj.get("pierscien_doswiadczenia_czas"))));

            obj = pool.getBany().find(new Document("_id", uuid.toString())).first();
            String banInfo = (String) obj.get("banInfo");

            obj = pool.getMuty().find(new Document("_id", uuid.toString())).first();
            String muteInfo = (String) obj.get("muteInfo");

            obj = pool.getOsiagniecia().find(new Document("_id", uuid.toString())).first();
            int osMoby;
            int osLudzie;
            int osSakwy;
            int osNiesy;
            int osRybak;
            int osDrwal;
            int osGornik;
            String osMobyAccept;
            String osLudzieAccept;
            String osSakwyAccept;
            String osNiesyAccept;
            String osRybakAccept;
            String osDrwalAccept;
            String osGornikAccept;
            if (obj == null) {
                osMoby = 0;
                osLudzie = 0;
                osSakwy = 0;
                osNiesy = 0;
                osRybak = 0;
                osDrwal = 0;
                osGornik = 0;
                osMobyAccept = "false,false,false,false,false,false,false,false,false,false";
                osLudzieAccept = "false,false,false,false,false,false,false,false,false,false";
                osSakwyAccept = "false,false,false,false,false,false,false,false,false,false";
                osNiesyAccept = "false,false,false,false,false,false,false,false,false,false";
                osRybakAccept = "false,false,false,false,false,false,false,false,false,false";
                osDrwalAccept = "false,false,false,false,false,false,false,false,false,false";
                osGornikAccept = "false,false,false,false,false,false,false,false,false,false";
            } else {
                osMoby = Integer.parseInt(String.format("%.0f", Double.valueOf(String.valueOf(obj.get("osMoby")))));
                osLudzie = Integer.parseInt(String.format("%.0f", Double.valueOf(String.valueOf(obj.get("osLudzie")))));
                osSakwy = Integer.parseInt(String.format("%.0f", Double.valueOf(String.valueOf(obj.get("osSakwy")))));
                osNiesy = Integer.parseInt(String.format("%.0f", Double.valueOf(String.valueOf(obj.get("osNiesy")))));
                osRybak = Integer.parseInt(String.format("%.0f", Double.valueOf(String.valueOf(obj.get("osRybak")))));
                osDrwal = Integer.parseInt(String.format("%.0f", Double.valueOf(String.valueOf(obj.get("osDrwal")))));
                osGornik = Integer.parseInt(String.format("%.0f", Double.valueOf(String.valueOf(obj.get("osGornik")))));
                osMobyAccept = (String) obj.get("osMobyAccept");
                osLudzieAccept = (String) obj.get("osLudzieAccept");
                osSakwyAccept = (String) obj.get("osSakwyAccept");
                osNiesyAccept = (String) obj.get("osNiesyAccept");
                osRybakAccept = (String) obj.get("osRybakAccept");
                osDrwalAccept = (String) obj.get("osDrwalAccept");
                osGornikAccept = (String) obj.get("osGornikAccept");
            }
            rpgcore.getPlayerManager().createPlayer(nick, uuid, banInfo, muteInfo, punishmentHistory, lvl, exp, osMoby, osLudzie, osSakwy, osNiesy, osRybak, osDrwal, osGornik,
                    osMobyAccept, osLudzieAccept, osSakwyAccept, osNiesyAccept, osRybakAccept, osDrwalAccept, osGornikAccept, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, kasa);

            obj = pool.getBao().find(new Document("_id", uuid.toString())).first();
            rpgcore.getBaoManager().updateBaoBonusy(uuid, String.valueOf(obj.get("BAO_BONUSY")));
            rpgcore.getBaoManager().updateBaoBonusyWartosci(uuid, String.valueOf(obj.get("BAO_WARTOSCI")));

            obj = pool.getRybak().find(new Document("_id", uuid.toString())).first();
            rpgcore.getRybakNPC().setPlayerRybakMisje(uuid, String.valueOf(obj.get("RYBAK_MISJE")));
            rpgcore.getRybakNPC().setPlayerPostep(uuid, Integer.parseInt(String.format("%.0f", Double.valueOf(String.valueOf(obj.get("RYBAK_POSTEP"))))));
            rpgcore.getRybakNPC().setPlayerRybakSredniDMG(uuid, Double.parseDouble(String.valueOf(obj.get("RYBAK_SRDMG"))));
            rpgcore.getRybakNPC().setPlayerRybakSredniDef(uuid, Double.parseDouble(String.valueOf(obj.get("RYBAK_SRDEF"))));
            rpgcore.getRybakNPC().setPlayerRybakDodatkowyDMG(uuid, Double.parseDouble(String.valueOf(obj.get("RYBAK_DDMG"))));
            rpgcore.getRybakNPC().setPlayerRybakBlok(uuid, Double.parseDouble(String.valueOf(obj.get("RYBAK_BLOK"))));

            try {
                obj = pool.getAkcesoria().find(new Document("_id", uuid.toString())).first();
                rpgcore.getAkcesoriaManager().createAkcesoriaGUI(uuid, Utils.itemStackArrayFromBase64(String.valueOf(obj.get("Akcesoria"))));

                obj = pool.getTargi().find(new Document("_id", uuid.toString())).first();
                rpgcore.getTargManager().putPlayerInTargMap(uuid, Utils.fromBase64(String.valueOf(obj.get("Targ")), "&f&lTarg gracza &3" + rpgcore.getPlayerManager().getPlayerName(uuid)));

                obj = pool.getMagazyny().find(new Document("_id", uuid.toString())).first();
                rpgcore.getMagazynierNPC().loadAll(uuid, (String) obj.get("Magazyny"));
            } catch (final IOException e) {
                e.printStackTrace();
            }

            obj = pool.getKolekcjoner().find(new Document("_id", uuid.toString())).first();
            rpgcore.getKolekcjonerNPC().loadAll(
                    uuid,
                    (String) obj.get("kolekcjonerPostep"),
                    Integer.parseInt(String.format("%.0f", Double.valueOf(String.valueOf(obj.get("postepMisji"))))),
                    Double.parseDouble(String.valueOf(obj.get("kolekcjonerSredniDMG"))),
                    Double.parseDouble(String.valueOf(obj.get("kolekcjonerSredniDef"))),
                    Double.parseDouble(String.valueOf(obj.get("kolekcjonerSredniKryt"))));
        }

        result = pool.getGildie().find().cursor();
        while (result.hasNext()) {
            Document obj = result.next();
            final String guildName = obj.get("_id").toString();

            final List<UUID> members = new ArrayList<>();

            for (final String member : String.valueOf(obj.get("membersList")).split(",")) {
                members.add(UUID.fromString(member));
            }

            final Map<UUID, Integer> killsMap = new HashMap<>();
            Document kills = (Document) obj.get("killsMap");
            for (final String key : kills.keySet()) {
                killsMap.put(UUID.fromString(key), kills.getInteger(key));
            }

            final Map<UUID, Integer> deathsMap = new HashMap<>();
            Document deaths = (Document) obj.get("deathsMap");
            for (final String key : deaths.keySet()) {
                deathsMap.put(UUID.fromString(key), deaths.getInteger(key));
            }

            final Map<UUID, Double> expEarnedMap = new HashMap<>();
            Document expEarned = (Document) obj.get("expEarnedMap");
            for (final String key : expEarned.keySet()) {
                expEarnedMap.put(UUID.fromString(key), expEarned.getDouble(key));
            }

            final Map<UUID, Date> lastOnlineMap = new HashMap<>();
            Document lastOnline = (Document) obj.get("lastOnlineMap");
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

    }


    public void setSpawn(final Location spawn) {

        final String w = spawn.getWorld().getName();
        final double x = spawn.getX();
        final double y = spawn.getY();
        final double z = spawn.getZ();
        final float yaw = spawn.getYaw();
        final float pitch = spawn.getPitch();

        Document query = new Document();
        query.append("_id", "spawn");

        Document document = new Document();
        document.append("_id", "spawn");
        document.append("world", w);
        document.append("x", x);
        document.append("y", y);
        document.append("z", z);
        document.append("yaw", yaw);
        document.append("pitch", pitch);

        pool.getSpawn().findOneAndReplace(query, document);

        rpgcore.getSpawnManager().setSpawn(spawn);
    }

    public void createPlayer(final Player player, final String nick, final UUID uuid, final String banInfo, final String muteInfo) {
        rpgcore.getMagazynierNPC().createAll(uuid);

        Document document = new Document();

        document.append("_id", uuid.toString());
        document.append("nick", nick);
        document.append("punishmentHistory", nick);
        document.append("level", 1);
        document.append("exp", 0.0);
        document.append("kasa", 100.0);
        document.append("hellcoins", 0);
        document.append("pierscien_doswiadczenia", false);
        document.append("pierscien_doswiadczenia_czas", 0);
        pool.getGracze().insertOne(document);

        document = new Document();
        document.append("_id", uuid.toString());
        document.append("banInfo", banInfo);
        pool.getBany().insertOne(document);

        document = new Document();
        document.append("_id", uuid.toString());
        document.append("muteInfo", muteInfo);
        pool.getMuty().insertOne(document);

        document = new Document();
        document.append("_id", uuid.toString());
        document.append("ekwipunek", Utils.toBase64(player.getInventory()));
        pool.getEkwipunek().insertOne(document);

        document = new Document();
        document.append("_id", uuid.toString());
        document.append("enderchest", Utils.toBase64(player.getEnderChest()));
        pool.getEnderchest().insertOne(document);

        document = new Document();
        document.append("_id", uuid.toString());
        document.append("zbroja", Utils.itemStackArrayToBase64(player.getInventory().getArmorContents()));
        pool.getZbroja().insertOne(document);

        document = new Document();
        document.append("_id", uuid.toString());
        document.append("osMoby", 0);
        document.append("osLudzie", 0);
        document.append("osSakwy", 0);
        document.append("osNiesy", 0);
        document.append("osRybak", 0);
        document.append("osDrwal", 0);
        document.append("osGornik", 0);
        document.append("osMobyAccept", "false,false,false,false,false,false,false,false,false,false");
        document.append("osLudzieAccept", "false,false,false,false,false,false,false,false,false,false");
        document.append("osSakwyAccept", "false,false,false,false,false,false,false,false,false,false");
        document.append("osNiesyAccept", "false,false,false,false,false,false,false,false,false,false");
        document.append("osRybakAccept", "false,false,false,false,false,false,false,false,false,false");
        document.append("osDrwalAccept", "false,false,false,false,false,false,false,false,false,false");
        document.append("osGornikAccept", "false,false,false,false,false,false,false,false,false,false");
        pool.getOsiagniecia().insertOne(document);

        document = new Document();
        document.append("_id", uuid.toString());
        document.append("BAO_BONUSY", "Brak Bonusu,Brak Bonusu,Brak Bonusu,Brak Bonusu,Brak Bonusu");
        document.append("BAO_WARTOSCI", "0,0,0,0,0");
        pool.getBao().insertOne(document);


        document = new Document();
        document.append("_id", uuid.toString());
        document.append("RYBAK_MISJE", "false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false");
        document.append("RYBAK_POSTEP", 0);
        document.append("RYBAK_SRDMG", 0.0);
        document.append("RYBAK_SRDEF", 0.0);
        document.append("RYBAK_DDMG", 0.0);
        document.append("RYBAK_BLOK", 0.0);
        pool.getRybak().insertOne(document);

        document = new Document();
        document.append("_id", uuid.toString());
        document.append("Akcesoria", Utils.itemStackArrayToBase64(rpgcore.getAkcesoriaManager().getAllAkcesoria(uuid)));
        pool.getAkcesoria().insertOne(document);

        document = new Document();
        document.append("_id", uuid.toString());
        document.append("Targ", Utils.toBase64(rpgcore.getTargManager().getPlayerTarg(uuid)));
        pool.getTargi().insertOne(document);

        document = new Document();
        document.append("_id", uuid.toString());
        document.append("Magazyny", rpgcore.getMagazynierNPC().getPlayerAllMagazyny(uuid));
        pool.getMagazyny().insertOne(document);

        document = new Document();
        document.append("_id", uuid.toString());
        document.append("kolekcjonerPostep", rpgcore.getKolekcjonerNPC().getKolekcjonerPostepInString(uuid));
        document.append("postepMisji", rpgcore.getKolekcjonerNPC().getPostepMisji(uuid));
        document.append("kolekcjonerSredniDMG", rpgcore.getKolekcjonerNPC().getKolekcjonerSrednieDMG(uuid));
        document.append("kolekcjonerSredniDef", rpgcore.getKolekcjonerNPC().getKolekcjonerSredniDef(uuid));
        document.append("kolekcjonerSredniKryt", rpgcore.getKolekcjonerNPC().getKolekcjonerKryt(uuid));
        pool.getKolekcjoner().insertOne(document);


        rpgcore.getPlayerManager().createPlayer(nick, uuid, "false", "false", "", 1, 0.0, 0, 0, 0, 0, 0, 0, 0, "false,false,false,false,false,false,false,false,false,false", "false,false,false,false,false,false,false,false,false,false", "false,false,false,false,false,false,false,false,false,false", "false,false,false,false,false,false,false,false,false,false", "false,false,false,false,false,false,false,false,false,false", "false,false,false,false,false,false,false,false,false,false", "false,false,false,false,false,false,false,false,false,false", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 100.0);

        rpgcore.getBaoManager().updateBaoBonusy(uuid, "Brak Bonusu,Brak Bonusu,Brak Bonusu,Brak Bonusu,Brak Bonusu");
        rpgcore.getBaoManager().updateBaoBonusyWartosci(uuid, "0,0,0,0,0");
        rpgcore.getRybakNPC().setPlayerRybakMisje(uuid, "false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false");
        rpgcore.getRybakNPC().setPlayerPostep(uuid, 0);
        rpgcore.getRybakNPC().setPlayerRybakSredniDMG(uuid, 0.0);
        rpgcore.getRybakNPC().setPlayerRybakSredniDef(uuid, 0.0);
        rpgcore.getRybakNPC().setPlayerRybakBlok(uuid, 0.0);
        rpgcore.getRybakNPC().setPlayerRybakDodatkowyDMG(uuid, 0.0);


    }

    public void banPlayer(final UUID uuid, final String banInfo) {
        Document query = new Document();
        query.append("_id", uuid.toString());

        pool.getBany().findOneAndReplace(query, new Document("_id", uuid.toString()).append("banInfo", banInfo));


    }

    public void mutePlayer(final UUID uuid, final String muteInfo) {
        Document query = new Document();
        query.append("_id", uuid.toString());

        pool.getMuty().findOneAndReplace(query, new Document("_id", uuid.toString()).append("muteInfo", muteInfo));


    }

    public void unBanPlayer(final UUID uuid) {
        Document query = new Document();
        query.append("_id", uuid.toString());

        pool.getBany().findOneAndReplace(query, new Document("_id", uuid.toString()).append("banInfo", "false"));

        rpgcore.getPlayerManager().updatePlayerBanInfo(uuid, "false");


    }

    public void unMutePlayer(final UUID uuid) {
        Document query = new Document();
        query.append("_id", uuid.toString());

        pool.getMuty().findOneAndReplace(query, new Document("_id", uuid.toString()).append("muteInfo", "false"));

        rpgcore.getPlayerManager().updatePlayerMuteInfo(uuid, "false");


    }

    public void setPunishmentHistory(final UUID uuid, final String punishmentHistory) {
        Document query = new Document();
        query.append("_id", uuid.toString());

        pool.getGracze().findOneAndReplace(query, new Document("_id", uuid.toString()).append("punishmentHistory", punishmentHistory));

        rpgcore.getPlayerManager().updatePlayerPunishmentHistory(uuid, punishmentHistory);

    }

    public void updatePlayer(final UUID uuid) {
        Document query = new Document();
        query.append("_id", uuid.toString());

        //Document document = this.getPlayerDocument(uuid);


        //pool.getGracz().findOneAndReplace(query, update);


    }

    public void tempUpdate() {
        for (UUID uuid : rpgcore.getPlayerManager().getPlayers()) {
            savePlayer(Bukkit.getPlayer(uuid), uuid);
        }
    }

    public void savePlayer(final Player player, final UUID uuid) {
        try {
            this.savePlayerDocument(uuid, pool.getGracze());
            this.saveBanDocument(uuid, pool.getBany());
            this.saveMuteDocument(uuid, pool.getMuty());
            //this.savePlayerInventory(uuid, database.getCollection("hellrpg_inventory"));
            this.saveOsDocument(uuid, pool.getOsiagniecia());
            this.saveBaoDocument(uuid, pool.getBao());
            this.saveRybakDocument(uuid, pool.getRybak());
            this.saveKolekcjonerDocument(uuid, pool.getKolekcjoner());
            this.saveMagazynyDocument(uuid, pool.getMagazyny());
            this.saveTargDocument(uuid, pool.getTargi());
            this.saveAkcesoriaDocument(uuid, pool.getAkcesoria());
            this.saveEkwipunekDocument(player, pool.getEkwipunek());
            this.saveEnderchestDocument(player, pool.getEnderchest());
            this.saveZbrojaDocument(player, pool.getZbroja());


            Utils.sendToAdministration("&aPomyslnie zapisano gracza: &6" + rpgcore.getPlayerManager().getPlayerName(uuid));
            System.out.println("§8[§4lHell§8§lRPG§c§lCore§8] §aPomyslnie zapisano gracza: §6" + rpgcore.getPlayerManager().getPlayerName(uuid));
        } catch (final Exception e) {
            Utils.sendToAdministration("&cWystapil blad podczas zapisu gracza: &6" + rpgcore.getPlayerManager().getPlayerName(uuid));
            System.out.println("§8[§4lHell§8§lRPG§c§lCore§8] §cWystapil blad podczas zapisu gracza: §6" + rpgcore.getPlayerManager().getPlayerName(uuid));
            e.printStackTrace();
        }
    }

    private void savePlayerDocument(final UUID uuid, final MongoCollection<Document> collection) {
        Document query = new Document();
        query.append("_id", uuid.toString());

        Document document = new Document();
        document.append("_id", uuid.toString());
        document.append("nick", rpgcore.getPlayerManager().getPlayerName(uuid));
        document.append("punishmentHistory", rpgcore.getPlayerManager().getPlayerPunishmentHistory(uuid));
        document.append("level", rpgcore.getPlayerManager().getPlayerLvl(uuid));
        document.append("exp", rpgcore.getPlayerManager().getPlayerExp(uuid));
        document.append("kasa", String.format("%.2f", rpgcore.getPlayerManager().getPlayerKasa(uuid)));
        document.append("hellcoins", 0);
        document.append("pierscien_doswiadczenia", false);
        document.append("pierscien_doswiadczenia_czas", 0);

        collection.findOneAndReplace(query, document);
    }

    private void saveBanDocument(final UUID uuid, final MongoCollection<Document> collection) {
        Document query = new Document();
        query.append("_id", uuid.toString());

        Document document = new Document();
        document.append("_id", uuid.toString());
        document.append("banInfo", rpgcore.getPlayerManager().getPlayerBanInfo(uuid));

        collection.findOneAndReplace(query, document);
    }

    private void saveMuteDocument(final UUID uuid, final MongoCollection<Document> collection) {
        Document query = new Document();
        query.append("_id", uuid.toString());

        Document document = new Document();
        document.append("_id", uuid.toString());
        document.append("muteInfo", rpgcore.getPlayerManager().getPlayerMuteInfo(uuid));

        collection.findOneAndReplace(query, document);
    }

    private void saveOsDocument(final UUID uuid, final MongoCollection<Document> collection) {
        Document query = new Document();
        query.append("_id", uuid.toString());

        Document document = new Document();
        document.append("_id", uuid.toString());
        document.append("osMoby", rpgcore.getPlayerManager().getPlayerOsMoby(uuid));
        document.append("osMobyAccept", rpgcore.getPlayerManager().getOsMobyAccept(uuid));
        document.append("osLudzie", rpgcore.getPlayerManager().getPlayerOsLudzie(uuid));
        document.append("osLudzieAccept", rpgcore.getPlayerManager().getOsLudzieAccept(uuid));
        document.append("osSakwy", rpgcore.getPlayerManager().getPlayerOsSakwy(uuid));
        document.append("osSakwyAccept", rpgcore.getPlayerManager().getOsSakwyAccept(uuid));
        document.append("osNiesy", rpgcore.getPlayerManager().getPlayerOsNiesy(uuid));
        document.append("osNiesyAccept", rpgcore.getPlayerManager().getOsNiesyAccept(uuid));
        document.append("osRybak", rpgcore.getPlayerManager().getPlayerOsRybak(uuid));
        document.append("osRybakAccept", rpgcore.getPlayerManager().getOsRybakAccept(uuid));
        document.append("osDrwal", rpgcore.getPlayerManager().getPlayerOsDrwal(uuid));
        document.append("osDrwalAccept", rpgcore.getPlayerManager().getOsDrwalAccept(uuid));
        document.append("osGornik", rpgcore.getPlayerManager().getPlayerOsGornik(uuid));
        document.append("osGornikAccept", rpgcore.getPlayerManager().getOsGornikAccept(uuid));

        collection.findOneAndReplace(query, document);
    }

    private void saveBaoDocument(final UUID uuid, final MongoCollection<Document> collection) {
        Document query = new Document();
        query.append("_id", uuid.toString());

        Document document = new Document();
        document.append("_id", uuid.toString());
        document.append("BAO_BONUSY", rpgcore.getBaoManager().getBaoBonusy(uuid));
        document.append("BAO_WARTOSCI", rpgcore.getBaoManager().getBaoBonusyWartosci(uuid));

        collection.findOneAndReplace(query, document);
    }

    private void saveRybakDocument(final UUID uuid, final MongoCollection<Document> collection) {
        Document query = new Document();
        query.append("_id", uuid.toString());

        Document document = new Document();
        document.append("_id", uuid.toString());
        document.append("RYBAK_MISJE", rpgcore.getRybakNPC().getPlayerRybakMisje(uuid));
        document.append("RYBAK_POSTEP", rpgcore.getRybakNPC().getPlayerPostep(uuid));
        document.append("RYBAK_SRDMG", rpgcore.getRybakNPC().getPlayerRybakSredniDMG(uuid));
        document.append("RYBAK_SRDEF", rpgcore.getRybakNPC().getPlayerRybakSredniDef(uuid));
        document.append("RYBAK_DDMG", rpgcore.getRybakNPC().getPlayerRybakDodatkowyDMG(uuid));
        document.append("RYBAK_BLOK", rpgcore.getRybakNPC().getPlayerRybakBlok(uuid));

        collection.findOneAndReplace(query, document);
    }

    private void saveKolekcjonerDocument(final UUID uuid, final MongoCollection<Document> collection) {
        Document query = new Document();
        query.append("_id", uuid.toString());

        Document document = new Document();
        document.append("_id", uuid.toString());
        document.append("kolekcjonerPostep", rpgcore.getKolekcjonerNPC().getKolekcjonerPostepInString(uuid));
        document.append("postepMisji", rpgcore.getKolekcjonerNPC().getPostepMisji(uuid));
        document.append("kolekcjonerSredniDMG", rpgcore.getKolekcjonerNPC().getKolekcjonerSrednieDMG(uuid));
        document.append("kolekcjonerSredniDef", rpgcore.getKolekcjonerNPC().getKolekcjonerSredniDef(uuid));
        document.append("kolekcjonerSredniKryt", rpgcore.getKolekcjonerNPC().getKolekcjonerKryt(uuid));

        collection.findOneAndReplace(query, document);
    }

    private void saveMagazynyDocument(final UUID uuid, final MongoCollection<Document> collection) {
        Document query = new Document();
        query.append("_id", uuid.toString());

        Document document = new Document();
        document.append("_id", uuid.toString());
        document.append("Magazyny", rpgcore.getMagazynierNPC().getPlayerAllMagazyny(uuid));

        collection.findOneAndReplace(query, document);
    }

    private void saveTargDocument(final UUID uuid, final MongoCollection<Document> collection) {
        Document query = new Document();
        query.append("_id", uuid.toString());

        Document document = new Document();
        document.append("_id", uuid.toString());
        document.append("Targ", Utils.toBase64(rpgcore.getTargManager().getPlayerTarg(uuid)));

        collection.findOneAndReplace(query, document);
    }

    private void saveAkcesoriaDocument(final UUID uuid, final MongoCollection<Document> collection) {
        Document query = new Document();
        query.append("_id", uuid.toString());

        Document document = new Document();
        document.append("_id", uuid.toString());
        document.append("Akcesoria", Utils.itemStackArrayToBase64(rpgcore.getAkcesoriaManager().getAllAkcesoria(uuid)));

        collection.findOneAndReplace(query, document);
    }

    private void saveEkwipunekDocument(final Player player, final MongoCollection<Document> collection) {
        Document obj = collection.find(new Document("_id", player.getUniqueId().toString())).first();
        if (obj != null) {
            Document query = new Document();
            query.append("_id", player.getUniqueId().toString());

            Document document = new Document();
            document.append("_id", player.getUniqueId().toString());
            document.append("ekwipunek", Utils.toBase64(player.getInventory()));

            collection.findOneAndReplace(query, document);
        } else {
            Document document = new Document();
            document.append("_id", player.getUniqueId().toString());
            document.append("ekwipunek", Utils.toBase64(player.getInventory()));
            collection.insertOne(document);
        }
        System.out.println("Zapisano ekwipunek gracza " + player.getName());
    }

    private void saveEnderchestDocument(final Player player, final MongoCollection<Document> collection) {
        Document obj = collection.find(new Document("_id", player.getUniqueId().toString())).first();
        if (obj != null) {
            Document query = new Document();
            query.append("_id", player.getUniqueId().toString());

            Document document = new Document();
            document.append("_id", player.getUniqueId().toString());
            document.append("enderchest", Utils.toBase64(player.getEnderChest()));

            collection.findOneAndReplace(query, document);
        } else {
            Document document = new Document();
            document.append("_id", player.getUniqueId().toString());
            document.append("enderchest", Utils.toBase64(player.getEnderChest()));
            collection.insertOne(document);
        }
    }

    private void saveZbrojaDocument(final Player player, final MongoCollection<Document> collection) {
        Document obj = collection.find(new Document("_id", player.getUniqueId().toString())).first();
        if (obj != null) {
            Document query = new Document();
            query.append("_id", player.getUniqueId().toString());

            Document document = new Document();
            document.append("_id", player.getUniqueId().toString());
            document.append("zbroja", Utils.itemStackArrayToBase64(player.getInventory().getArmorContents()));

            collection.findOneAndReplace(query, document);
        } else {
            Document document = new Document();
            document.append("_id", player.getUniqueId().toString());
            document.append("zbroja", Utils.itemStackArrayToBase64(player.getInventory().getArmorContents()));
            collection.insertOne(document);
        }
    }

    public void saveGuildFirst(final String tag) {
        Document guild = createGuildDocument(tag);
        pool.getGildie().insertOne(guild);
    }

    public void saveGuild() {
        String tag2 = "";
        try {
            for (String tag : rpgcore.getGuildManager().getListOfGuilds()) {
                tag2 = tag;
                Document query = new Document();
                query.append("_id", tag);

                Document guild = createGuildDocument(tag);

                pool.getGildie().findOneAndReplace(query, guild);

                Utils.sendToAdministration("&aPomyslnie zapisano gilde: &6" + tag);
                System.out.println("§8[§4lHell§8§lRPG§c§lCore§8] §aPomyslnie zapisano klan: §6" + tag);
            }
        } catch (final Exception e) {
            Utils.sendToAdministration("&cWystapil blad podczas zapisu klanu &6:" + tag2);
            System.out.println("§8[§4lHell§8§lRPG§c§lCore§8] §cWystapil blad podczas zapisu klanu: §6" + tag2);
            e.printStackTrace();
        }
    }

    private Document createGuildDocument(final String tag) {
        final StringBuilder sb = new StringBuilder();
        Document guild = new Document();

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

        Document killsMap = new Document();
        for (Map.Entry<UUID, Integer> entry : rpgcore.getGuildManager().getGuildKills(tag).entrySet()) {
            killsMap.put(String.valueOf(entry.getKey()), entry.getValue());
        }

        guild.put("killsMap", killsMap);

        Document deathsMap = new Document();
        for (Map.Entry<UUID, Integer> entry : rpgcore.getGuildManager().getGuildDeaths(tag).entrySet()) {
            deathsMap.put(String.valueOf(entry.getKey()), entry.getValue());
        }

        guild.put("deathsMap", deathsMap);

        Document expEarnedMap = new Document();
        for (Map.Entry<UUID, Double> entry : rpgcore.getGuildManager().getGuildExpEarned(tag).entrySet()) {
            expEarnedMap.put(String.valueOf(entry.getKey()), entry.getValue());
        }

        guild.put("expEarnedMap", expEarnedMap);

        Document lastOnlineMap = new Document();
        for (Map.Entry<UUID, Date> entry : rpgcore.getGuildManager().getGuildLastOnline(tag).entrySet()) {
            lastOnlineMap.put(String.valueOf(entry.getKey()), entry.getValue().getTime());
        }

        guild.put("lastOnlineMap", lastOnlineMap);
        return guild;
    }

    public void removeGuild(final String tag) {
        Document toRemove = new Document("_id", tag);
        pool.getGildie().deleteOne(toRemove);
    }

    public void onDisable() {
        pool.closePool();
    }


}
