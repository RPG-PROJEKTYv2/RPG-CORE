package rpg.rpgcore.database;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.bao.BaoObject;
import rpg.rpgcore.klasy.objects.Klasy;
import rpg.rpgcore.npc.duszolog.DuszologObject;
import rpg.rpgcore.npc.gornik.GornikObject;
import rpg.rpgcore.npc.kolekcjoner.KolekcjonerObject;
import rpg.rpgcore.npc.medyk.MedykObject;
import rpg.rpgcore.npc.przyrodnik.PrzyrodnikObject;
import rpg.rpgcore.os.OsObject;
import rpg.rpgcore.server.ServerUser;
import rpg.rpgcore.metiny.Metiny;
import rpg.rpgcore.npc.metinolog.MetinologObject;
import rpg.rpgcore.spawn.SpawnManager;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MongoManager {

    private final RPGCORE rpgcore;
    private final MongoConnectionPoolManager pool;

    public MongoManager(final RPGCORE rpgcore) {
        this.pool = new MongoConnectionPoolManager();
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

    public void fix() {
    }


    public void loadAll() {
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
            System.out.println(uuid);
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

            rpgcore.getPlayerManager().createPlayer(nick, uuid, banInfo, muteInfo, punishmentHistory, lvl, exp,0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, kasa);


            if (pool.getOsiagniecia().find(new Document("_id", uuid.toString())).first() == null) {
                this.addDataOs(new OsObject(uuid));
            }
            if (pool.getKolekcjoner().find(new Document("_id", uuid.toString())).first() == null) {
                this.addDataKolekcjner(new KolekcjonerObject(uuid));
            }

            if (this.pool.getBao().find(new Document("_id", uuid.toString())).first() == null) {
                this.addDataBao(new BaoObject(uuid));
            }

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

            obj = pool.getTrener().find(new Document("_id", uuid.toString())).first();
            if (obj == null) {
                obj = rpgcore.getTrenerNPC().toDocument(uuid);
                this.addTrenerData(uuid);
            }
            rpgcore.getTrenerNPC().fromDocument(obj);

            if (pool.getMetinolog().find(new Document("_id", uuid.toString())).first() == null) {
                this.addDataMetinolog(new MetinologObject(uuid));
            }
            if (pool.getKlasy().find(new Document("_id", uuid.toString())).first() == null) {
                this.addKlasyData(new Klasy(uuid));
            }
            if (pool.getMedyk().find(new Document("_id", uuid.toString())).first() == null) {
                this.addDataMedyk(new MedykObject(uuid));
            }
            if (pool.getGornik().find(new Document("_id", uuid.toString())).first() == null) {
                this.addDataGornik(new GornikObject(uuid));
            }
            if (pool.getDuszolog().find(new Document("_id", uuid.toString())).first() == null) {
                this.addDataDuszolog(new DuszologObject(uuid));
            }
            if (pool.getPrzyrodnik().find(new Document("_id", uuid.toString())).first() == null) {
                this.addDataPrzyrodnik(new PrzyrodnikObject(uuid));
            }
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
        if (pool.getOther().find(new Document("_id", "dodatkowyExp")).first() == null) {
            addOtherData(new ServerUser("dodatkowyExp"));
        }
    }

    public Map<Integer, Metiny> loadMetins() {
        Map<Integer, Metiny> metiny = new ConcurrentHashMap<>();
        for (Document document : pool.getMetiny().find()) {
            Metiny metiny1 = new Metiny(document);
            metiny.put(metiny1.getId(), metiny1);
        }
        return metiny;
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

        this.addDataOs(new OsObject(uuid));

        this.addDataBao(new BaoObject(uuid));


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

        this.addDataKolekcjner(new KolekcjonerObject(uuid));

        pool.getTrener().insertOne(rpgcore.getTrenerNPC().toDocument(uuid));

        this.addDataMetinolog(new MetinologObject(uuid));
        this.addKlasyData(new Klasy(uuid));
        this.addDataDuszolog(new DuszologObject(uuid));


        rpgcore.getPlayerManager().createPlayer(nick, uuid, "false", "false", "", 1, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 100.0);

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
        rpgcore.getPlayerManager().updatePlayerBanInfo(uuid, banInfo);
    }

    public void mutePlayer(final UUID uuid, final String muteInfo) {
        Document query = new Document();
        query.append("_id", uuid.toString());

        pool.getMuty().findOneAndReplace(query, new Document("_id", uuid.toString()).append("muteInfo", muteInfo));
        rpgcore.getPlayerManager().updatePlayerMuteInfo(uuid, muteInfo);
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

        Document update = new Document("$set", new Document("punishmentHistory", punishmentHistory));


        pool.getGracze().findOneAndUpdate(query, update);

        rpgcore.getPlayerManager().updatePlayerPunishmentHistory(uuid, punishmentHistory);

    }

    public void savePlayer(final Player player, final UUID uuid) {
        try {
            this.savePlayerDocument(uuid, pool.getGracze());
            this.saveBanDocument(uuid, pool.getBany());
            this.saveMuteDocument(uuid, pool.getMuty());
            //this.savePlayerInventory(uuid, database.getCollection("hellrpg_inventory"));
            //this.saveOsDocument(uuid, pool.getOsiagniecia());
            this.saveRybakDocument(uuid, pool.getRybak());
            this.saveDataKolekcjoner(uuid, rpgcore.getKolekcjonerNPC().find(uuid));
            this.saveMagazynyDocument(uuid, pool.getMagazyny());
            this.saveTargDocument(uuid, pool.getTargi());
            this.saveAkcesoriaDocument(uuid, pool.getAkcesoria());
            this.saveEkwipunekDocument(player, pool.getEkwipunek());
            this.saveEnderchestDocument(player, pool.getEnderchest());
            this.saveZbrojaDocument(player, pool.getZbroja());

            pool.getTrener().findOneAndReplace(new Document("_id", uuid.toString()), rpgcore.getTrenerNPC().toDocument(uuid));
            this.saveDataMetinolog(uuid, rpgcore.getMetinologNPC().find(uuid));
            this.saveKlasyData(uuid);


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

    public void addDataMetins(Metiny metiny) {
        this.pool.getMetiny().insertOne(metiny.toDocument());
    }

    public void saveDataMetins(final int id, final Metiny metiny) {
        this.pool.getMetiny().findOneAndReplace(new Document("_id", id), metiny.toDocument());
    }

    public void removeDataMetins(final int id) {
        Document querry = new Document("_id", id);
        this.pool.getMetiny().deleteMany(querry);
    }

    public void saveAllMetins() {
        for (Metiny metiny : rpgcore.getMetinyManager().getMetins()) {
            this.saveDataMetins(metiny.getId(), metiny);
        }
    }

    public Map<UUID, MetinologObject> loadAllMetinolog() {
        Map<UUID, MetinologObject> metinolog = new ConcurrentHashMap<>();
        for (Document document : this.pool.getMetinolog().find()) {
            MetinologObject metinologObject = new MetinologObject(document);
            metinolog.put(metinologObject.getID(), metinologObject);
        }
        return metinolog;
    }

    public void addDataMetinolog(final MetinologObject metinolog) {
        this.pool.getMetinolog().insertOne(metinolog.toDocument());
    }

    public void saveDataMetinolog(final UUID id, final MetinologObject metinolog) {
        this.pool.getMetinolog().findOneAndReplace(new Document("_id", id.toString()), metinolog.toDocument());
    }

    public void saveAllMetinolog() {
        for (MetinologObject metinolog : rpgcore.getMetinologNPC().getMetinologObject()) {
            this.saveDataMetinolog(metinolog.getID(), metinolog);
        }
    }

    public Map<String, ServerUser> loadAllServer() {
        Map<String, ServerUser> server = new ConcurrentHashMap<>();
        for (Document document : this.pool.getOther().find()) {
            ServerUser serverUser = new ServerUser(document);
            server.put(serverUser.getName(), serverUser);
        }
        return server;
    }
    public void addOtherData(ServerUser serverUser) {
        this.pool.getOther().insertOne(serverUser.toDocument());
    }

    public void saveOtherData(String name) {
        pool.getOther().findOneAndReplace(new Document("_id", "dodatkowyExp"), rpgcore.getServerManager().find(name).toDocument());
    }

    public Map<UUID, Klasy> loadAllKlasy() {
        Map<UUID, Klasy> klasy = new ConcurrentHashMap<>();
        for (Document document : this.pool.getKlasy().find()) {
            Klasy klasyUser = new Klasy(document);
            klasy.put(klasyUser.getId(), klasyUser);
        }
        return klasy;
    }
    public void addKlasyData(Klasy klasy) {
        this.pool.getKlasy().insertOne(klasy.toDocument());
    }

    public void saveKlasyData(UUID uuid) {
        pool.getKlasy().findOneAndReplace(new Document("_id", uuid.toString()), rpgcore.getklasyHelper().find(uuid).toDocument());
    }

    public void addTrenerData(final UUID uuid) {
        this.pool.getTrener().insertOne(rpgcore.getTrenerNPC().toDocument(uuid));
    }


    // MEDYK
    public Map<UUID, MedykObject> loadAllMedyk() {
        Map<UUID, MedykObject> medyk = new ConcurrentHashMap<>();
        for (Document document : this.pool.getMedyk().find()) {
            MedykObject medykObject = new MedykObject(document);
            medyk.put(medykObject.getID(), medykObject);
        }
        return medyk;
    }

    public void addDataMedyk(final MedykObject medyk) {
        this.pool.getMedyk().insertOne(medyk.toDocument());
    }

    public void saveDataMedyk(final UUID id, final MedykObject medyk) {
        this.pool.getMedyk().findOneAndReplace(new Document("_id", id.toString()), medyk.toDocument());
    }

    public void saveAllMedyk() {
        for (MedykObject medykObject : rpgcore.getMedykNPC().getMedykObject()) {
            this.saveDataMedyk(medykObject.getID(), medykObject);
        }
    }


    // GORNIK
    public Map<UUID, GornikObject> loadAllGornik() {
        Map<UUID, GornikObject> gornik = new ConcurrentHashMap<>();
        for (Document document : this.pool.getGornik().find()) {
            GornikObject gornikObject = new GornikObject(document);
            gornik.put(gornikObject.getID(), gornikObject);
        }
        return gornik;
    }

    public void addDataGornik(final GornikObject gornikObject) {
        this.pool.getGornik().insertOne(gornikObject.toDocument());
    }

    public void saveDataGornik(final UUID id, final GornikObject gornikObject) {
        this.pool.getGornik().findOneAndReplace(new Document("_id", id.toString()), gornikObject.toDocument());
    }

    public void saveAllGornik() {
        for (GornikObject gornikObject : rpgcore.getGornikNPC().getGornikObject()) {
            this.saveDataGornik(gornikObject.getID(), gornikObject);
        }
    }


    // Osiagniecia
    public Map<UUID, OsObject> loadAllOs() {
        Map<UUID, OsObject> gornik = new ConcurrentHashMap<>();
        for (Document document : this.pool.getOsiagniecia().find()) {
            OsObject osObject = new OsObject(document);
            gornik.put(osObject.getID(), osObject);
        }
        return gornik;
    }

    public void addDataOs(final OsObject osObject) {
        this.pool.getOsiagniecia().insertOne(osObject.toDocument());
    }

    public void saveDataOs(final UUID id, final OsObject osObject) {
        this.pool.getOsiagniecia().findOneAndReplace(new Document("_id", id.toString()), osObject.toDocument());
    }

    public void saveAllOs() {
        for (OsObject osObject : rpgcore.getOsManager().getOsObject()) {
            this.saveDataOs(osObject.getID(), osObject);
        }
    }

    // Kolekcjoner
    public Map<UUID, KolekcjonerObject> loadAllKolekcjoner() {
        Map<UUID, KolekcjonerObject> kolekcjoner = new ConcurrentHashMap<>();
        for (Document document : this.pool.getKolekcjoner().find()) {
            KolekcjonerObject kolekcjonerObject = new KolekcjonerObject(document);
            kolekcjoner.put(kolekcjonerObject.getID(), kolekcjonerObject);
        }
        return kolekcjoner;
    }

    public void addDataKolekcjner(final KolekcjonerObject kolekcjonerObject) {
        this.pool.getKolekcjoner().insertOne(kolekcjonerObject.toDocument());
    }

    public void saveDataKolekcjoner(final UUID id, final KolekcjonerObject kolekcjonerObject) {
        this.pool.getKolekcjoner().findOneAndReplace(new Document("_id", id.toString()), kolekcjonerObject.toDocument());
    }

    public void saveAllKolekcjoner() {
        for (KolekcjonerObject kolekcjonerObject : rpgcore.getKolekcjonerNPC().getKolekcjonerObject()) {
            this.saveDataKolekcjoner(kolekcjonerObject.getID(), kolekcjonerObject);
        }
    }

    // Duszolog
    public Map<UUID, DuszologObject> loadAllDuszolog() {
        Map<UUID, DuszologObject> duszolog = new ConcurrentHashMap<>();
        for (Document document : this.pool.getDuszolog().find()) {
            DuszologObject duszologObject = new DuszologObject(document);
            duszolog.put(duszologObject.getID(), duszologObject);
        }
        return duszolog;
    }

    public void addDataDuszolog(final DuszologObject duszologObject) {
        this.pool.getDuszolog().insertOne(duszologObject.toDocument());
    }

    public void saveDataDuszolog(final UUID id, final DuszologObject duszologObject) {
        this.pool.getDuszolog().findOneAndReplace(new Document("_id", id.toString()), duszologObject.toDocument());
    }

    public void saveAllDuszolog() {
        for (DuszologObject duszologObject : rpgcore.getDuszologNPC().getDuszologObject()) {
            this.saveDataDuszolog(duszologObject.getID(), duszologObject);
        }
    }


    // Przyrodnik
    public Map<UUID, PrzyrodnikObject> loadAllPrzyrodnik() {
        Map<UUID, PrzyrodnikObject> przyrodnik = new ConcurrentHashMap<>();
        for (Document document : this.pool.getPrzyrodnik().find()) {
            PrzyrodnikObject przyrodnikObject = new PrzyrodnikObject(document);
            przyrodnik.put(przyrodnikObject.getId(), przyrodnikObject);
        }
        return przyrodnik;
    }

    public void addDataPrzyrodnik(final PrzyrodnikObject przyrodnikObject) {
        this.pool.getPrzyrodnik().insertOne(przyrodnikObject.toDocument());
    }

    public void saveDataPrzyrodnik(final UUID id, final PrzyrodnikObject przyrodnikObject) {
        this.pool.getPrzyrodnik().findOneAndReplace(new Document("_id", id.toString()), przyrodnikObject.toDocument());
    }

    public void saveAllPrzyrodnik() {
        for (PrzyrodnikObject przyrodnikObject : rpgcore.getPrzyrodnikNPC().getPrzyrodnikObjects()) {
            this.saveDataPrzyrodnik(przyrodnikObject.getId(), przyrodnikObject);
        }
    }



    // BAO
    public Map<UUID, BaoObject> loadAllBao() {
        Map<UUID, BaoObject> bao = new ConcurrentHashMap<>();
        for (Document document : this.pool.getBao().find()) {
            BaoObject baoObject = new BaoObject(document);
            bao.put(baoObject.getId(), baoObject);
        }
        return bao;
    }

    public void addDataBao(final BaoObject baoObject) {
        this.pool.getBao().insertOne(baoObject.toDocument());
    }

    public void saveDataBao(final UUID id, final BaoObject baoObject) {
        this.pool.getBao().findOneAndReplace(new Document("_id", id.toString()), baoObject.toDocument());
    }

    public void saveAllBao() {
        for (BaoObject baoObject : rpgcore.getBaoManager().getBaoObjects()) {
            this.saveDataBao(baoObject.getId(), baoObject);
        }
    }



    public void onDisable() {
        pool.closePool();
    }


}
