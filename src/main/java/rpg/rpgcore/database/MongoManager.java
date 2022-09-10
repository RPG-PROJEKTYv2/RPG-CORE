package rpg.rpgcore.database;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.akcesoria.AkcesoriaObject;
import rpg.rpgcore.bao.BaoObject;
import rpg.rpgcore.bonuses.Bonuses;
import rpg.rpgcore.klasy.objects.Klasy;
import rpg.rpgcore.npc.duszolog.DuszologObject;
import rpg.rpgcore.npc.gornik.GornikObject;
import rpg.rpgcore.npc.kolekcjoner.KolekcjonerObject;
import rpg.rpgcore.npc.medyk.MedykObject;
import rpg.rpgcore.npc.przyrodnik.PrzyrodnikObject;
import rpg.rpgcore.npc.rybak.RybakObject;
import rpg.rpgcore.os.OsObject;
import rpg.rpgcore.ranks.RankPlayerUser;
import rpg.rpgcore.ranks.RankUser;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.ranks.types.RankTypePlayer;
import rpg.rpgcore.server.ServerUser;
import rpg.rpgcore.metiny.Metiny;
import rpg.rpgcore.npc.metinolog.MetinologObject;
import rpg.rpgcore.spawn.SpawnManager;
import rpg.rpgcore.user.User;
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

    public void fixMires() {
        final User user = rpgcore.getUserManager().find("Mires_");
        user.getRankUser().setRank(RankType.DEV);
        this.saveDataUser(UUID.fromString("7193813f-c9c3-37e6-b72b-4272a3898b80"), user);
    }

    public void fix(Document document) {
        final Document obj = document;
        this.pool.getGracze().deleteOne(document);

        this.pool.getRybak().insertOne(
                new Document("_id", obj.getString("_id"))
                        .append("mission", 0)
                        .append("progress", 0)
                        .append("value1", 0.0)
                        .append("value2", 0.0)
                        .append("value3", 0.0)
                        .append("value4", 0.0)

        );
    }


    public void loadAll() {

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


        for (Document obj : pool.getGracze().find()){
            UUID uuid = UUID.fromString(obj.get("_id").toString());
            System.out.println(uuid);

            if (pool.getBonuses().find(new Document("_id", uuid.toString())).first() == null) {
                this.addDataBonuses(new Bonuses(uuid));
            }

            if (pool.getOsiagniecia().find(new Document("_id", uuid.toString())).first() == null) {
                this.addDataOs(new OsObject(uuid));
            }
            if (pool.getKolekcjoner().find(new Document("_id", uuid.toString())).first() == null) {
                this.addDataKolekcjner(new KolekcjonerObject(uuid));
            }

            if (this.pool.getBao().find(new Document("_id", uuid.toString())).first() == null) {
                this.addDataBao(new BaoObject(uuid));
            }

            if (this.pool.getRybak().find(new Document("_id", uuid.toString())).first() == null) {
                this.addDataRybak(new RybakObject(uuid));
            }

            if (this.pool.getAkcesoria().find(new Document("_id", uuid.toString())).first() == null) {
                this.addDataAkcesoria(new AkcesoriaObject(uuid));
            }

            try {
                obj = pool.getTargi().find(new Document("_id", uuid.toString())).first();
                rpgcore.getTargManager().putPlayerInTargMap(uuid, Utils.fromBase64(String.valueOf(obj.get("Targ")), "&f&lTarg gracza &3" + rpgcore.getUserManager().find(uuid).getName()));

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

        for (Document document : pool.getGildie().find()){
            final String guildName = document.get("_id").toString();

            final List<UUID> members = new ArrayList<>();

            for (final String member : String.valueOf(document.get("membersList")).split(",")) {
                members.add(UUID.fromString(member));
            }

            final Map<UUID, Integer> killsMap = new HashMap<>();
            Document kills = (Document) document.get("killsMap");
            for (final String key : kills.keySet()) {
                killsMap.put(UUID.fromString(key), kills.getInteger(key));
            }

            final Map<UUID, Integer> deathsMap = new HashMap<>();
            Document deaths = (Document) document.get("deathsMap");
            for (final String key : deaths.keySet()) {
                deathsMap.put(UUID.fromString(key), deaths.getInteger(key));
            }

            final Map<UUID, Double> expEarnedMap = new HashMap<>();
            Document expEarned = (Document) document.get("expEarnedMap");
            for (final String key : expEarned.keySet()) {
                expEarnedMap.put(UUID.fromString(key), expEarned.getDouble(key));
            }

            final Map<UUID, Date> lastOnlineMap = new HashMap<>();
            Document lastOnline = (Document) document.get("lastOnlineMap");
            for (final String key : lastOnline.keySet()) {
                lastOnlineMap.put(UUID.fromString(key), new Date(lastOnline.getLong(key)));
            }

            rpgcore.getGuildManager().loadGuild(
                    guildName,
                    (String) document.get("description"),
                    UUID.fromString((String) document.get("owner")),
                    (String) document.get("coOwner"),
                    members,
                    Boolean.parseBoolean(String.valueOf(document.get("pvp"))),
                    Integer.parseInt(String.format("%.0f", Double.valueOf(String.valueOf(document.get("points"))))),
                    Integer.parseInt(String.format("%.0f", Double.valueOf(String.valueOf(document.get("lvl"))))),
                    Double.parseDouble(String.valueOf(document.get("exp"))),
                    Integer.parseInt(String.format("%.0f", Double.valueOf(String.valueOf(document.get("balance"))))),
                    Double.parseDouble(String.valueOf(document.get("dodatkowyExp"))),
                    Double.parseDouble(String.valueOf(document.get("sredniDmg"))),
                    Double.parseDouble(String.valueOf(document.get("sredniDef"))),
                    Double.parseDouble(String.valueOf(document.get("silnyNaLudzi"))),
                    Double.parseDouble(String.valueOf(document.get("defNaLudzi"))),
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

    public void createPlayer(final UUID uuid, final String nick) {

        final User user = new User(uuid, nick);
        this.addDataUser(user);
        rpgcore.getUserManager().add(user);

        final AkcesoriaObject akcesoriaObject = new AkcesoriaObject(uuid);
        this.addDataAkcesoria(akcesoriaObject);
        rpgcore.getAkcesoriaManager().add(akcesoriaObject);

        final Bonuses bonuses = new Bonuses(uuid);
        this.addDataBonuses(bonuses);
        rpgcore.getBonusesManager().add(bonuses);

        final BaoObject baoObject = new BaoObject(uuid);
        this.addDataBao(baoObject);
        rpgcore.getBaoManager().add(baoObject);

        final Klasy klasy = new Klasy(uuid);
        this.addKlasyData(klasy);
        rpgcore.getklasyHelper().add(klasy);

        final DuszologObject duszologObject = new DuszologObject(uuid);
        this.addDataDuszolog(duszologObject);
        rpgcore.getDuszologNPC().add(duszologObject);

        final KolekcjonerObject kolekcjonerObject = new KolekcjonerObject(uuid);
        this.addDataKolekcjner(kolekcjonerObject);
        rpgcore.getKolekcjonerNPC().add(kolekcjonerObject);

        final MedykObject medykObject = new MedykObject(uuid);
        this.addDataMedyk(medykObject);
        rpgcore.getMedykNPC().add(medykObject);

        final MetinologObject metinologObject = new MetinologObject(uuid);
        this.addDataMetinolog(metinologObject);
        rpgcore.getMetinologNPC().add(metinologObject);

        final PrzyrodnikObject przyrodnikObject = new PrzyrodnikObject(uuid);
        this.addDataPrzyrodnik(przyrodnikObject);
        rpgcore.getPrzyrodnikNPC().add(przyrodnikObject);

        final RybakObject rybakObject = new RybakObject(uuid);
        this.addDataRybak(rybakObject);
        rpgcore.getRybakNPC().add(rybakObject);

        final OsObject osObject = new OsObject(uuid);
        this.addDataOs(osObject);
        rpgcore.getOsManager().add(osObject);

        Document document;

        document = new Document();
        document.append("_id", uuid.toString());
        document.append("Targ", Utils.toBase64(rpgcore.getTargManager().getPlayerTarg(uuid)));
        pool.getTargi().insertOne(document);

        document = new Document();
        document.append("_id", uuid.toString());
        document.append("Magazyny", rpgcore.getMagazynierNPC().getPlayerAllMagazyny(uuid));
        pool.getMagazyny().insertOne(document);
    }

    public void savePlayer(final Player player, final UUID uuid) {
        try {
            final long start = System.currentTimeMillis();
            final User user = rpgcore.getUserManager().find(uuid);
            ItemStack[] inventory = player.getInventory().getContents();
            ItemStack[] armor = player.getInventory().getArmorContents();
            ItemStack[] enderchest = player.getEnderChest().getContents();

            user.getInventoriesUser().setInventory(Utils.itemStackArrayToBase64(inventory));
            user.getInventoriesUser().setArmor(Utils.itemStackArrayToBase64(armor));
            user.getInventoriesUser().setEnderchest(Utils.itemStackArrayToBase64(enderchest));

            if (!user.getRankPlayerUser().getRankType().equals(RankTypePlayer.GRACZ)) {
                if (user.getRankPlayerUser().getTime() != -1) {
                    if (user.getRankPlayerUser().getTime() <= System.currentTimeMillis()) {
                        user.getRankPlayerUser().setRank(RankTypePlayer.GRACZ);
                        user.getRankPlayerUser().setTime(0L);
                        rpgcore.getNmsManager().sendTitleAndSubTitle(player, rpgcore.getNmsManager().makeTitle("&8&l[&4&l!&8&l]", 5, 20, 5), rpgcore.getNmsManager().makeSubTitle("&cTwoja ranga wlasnie wygasla!", 5, 20, 5));
                    }
                }
            }

            this.saveDataUser(uuid, rpgcore.getUserManager().find(uuid));
            this.saveDataAkcesoria(uuid, rpgcore.getAkcesoriaManager().find(uuid));
            this.saveDataBonuses(uuid, rpgcore.getBonusesManager().find(uuid));
            this.saveDataBao(uuid, rpgcore.getBaoManager().find(uuid));
            this.saveKlasyData(uuid, rpgcore.getklasyHelper().find(uuid));
            this.saveDataDuszolog(uuid, rpgcore.getDuszologNPC().find(uuid));
            this.saveDataKolekcjoner(uuid, rpgcore.getKolekcjonerNPC().find(uuid));
            this.saveDataMedyk(uuid, rpgcore.getMedykNPC().find(uuid));
            this.saveDataMetinolog(uuid, rpgcore.getMetinologNPC().find(uuid));
            this.saveDataPrzyrodnik(uuid, rpgcore.getPrzyrodnikNPC().find(uuid));
            this.saveDataRybak(uuid, rpgcore.getRybakNPC().find(uuid));
            this.saveDataOs(uuid, rpgcore.getOsManager().find(uuid));

            pool.getTrener().findOneAndReplace(new Document("_id", uuid.toString()), rpgcore.getTrenerNPC().toDocument(uuid));
            this.saveDataMetinolog(uuid, rpgcore.getMetinologNPC().find(uuid));


            Utils.sendToAdministration("&aPomyslnie zapisano gracza: &6" + rpgcore.getUserManager().find(uuid).getName() + " &a w czasie: &6" + (System.currentTimeMillis() - start) + "ms");
            System.out.println("[HellRPGCore] Pomyslnie zapisano gracza: " + rpgcore.getUserManager().find(uuid).getName() + " &a w czasie: &6" + (System.currentTimeMillis() - start) + "ms");
        } catch (final Exception e) {
            Utils.sendToAdministration("&cWystapil blad podczas zapisu gracza: &6" + rpgcore.getUserManager().find(uuid).getName());
            System.out.println("[HellRPGCore] Wystapil blad podczas zapisu gracza: " + rpgcore.getUserManager().find(uuid).getName());
            e.printStackTrace();
        }
    }

    public void banPlayer(final UUID uuid, final String banInfo) {
        Document query = new Document();
        query.append("_id", uuid.toString());

        Document update = new Document("$set", new Document("banInfo", banInfo));


        pool.getGracze().findOneAndUpdate(query, update);
        rpgcore.getUserManager().find(uuid).setBanInfo(banInfo);
    }

    public void mutePlayer(final UUID uuid, final String muteInfo) {
        Document query = new Document();
        query.append("_id", uuid.toString());

        Document update = new Document("$set", new Document("muteInfo", muteInfo));


        pool.getGracze().findOneAndUpdate(query, update);
        rpgcore.getUserManager().find(uuid).setMuteInfo(muteInfo);
    }

    public void unBanPlayer(final UUID uuid) {
        Document query = new Document();
        query.append("_id", uuid.toString());

        Document update = new Document("$set", new Document("banInfo", "false"));


        pool.getGracze().findOneAndUpdate(query, update);

        rpgcore.getUserManager().find(uuid).setBanInfo("false");
    }

    public void unMutePlayer(final UUID uuid) {
        Document query = new Document();
        query.append("_id", uuid.toString());

        Document update = new Document("$set", new Document("muteInfo", "false"));


        pool.getGracze().findOneAndUpdate(query, update);

        rpgcore.getUserManager().find(uuid).setMuteInfo("false");


    }

    public void setPunishmentHistory(final UUID uuid, final String punishmentHistory) {
        Document query = new Document();
        query.append("_id", uuid.toString());

        Document update = new Document("$set", new Document("punishmentHistory", punishmentHistory));


        pool.getGracze().findOneAndUpdate(query, update);

        rpgcore.getUserManager().find(uuid).setPunishmentHistory(punishmentHistory);

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

    public void saveKlasyData(UUID uuid, Klasy klasy) {
        pool.getKlasy().findOneAndReplace(new Document("_id", uuid.toString()), klasy.toDocument());
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
        Map<UUID, BaoObject> bao = new HashMap<>();
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

    // AKCEOSRIA
    public Map<UUID, AkcesoriaObject> loadAllAkcesoria() {
        Map<UUID, AkcesoriaObject> akcesoria = new HashMap<>();
        for (Document document : this.pool.getAkcesoria().find()) {
            AkcesoriaObject akcesoriaObject = new AkcesoriaObject(document);
            akcesoria.put(akcesoriaObject.getId(), akcesoriaObject);
        }
        return akcesoria;
    }

    public void addDataAkcesoria(final AkcesoriaObject akcesoriaObject) {
        this.pool.getAkcesoria().insertOne(akcesoriaObject.toDocument());
    }

    public void saveDataAkcesoria(final UUID id, final AkcesoriaObject akcesoriaObject) {
        this.pool.getAkcesoria().findOneAndReplace(new Document("_id", id.toString()), akcesoriaObject.toDocument());
    }

    public void saveAllAkcesoria() {
        for (AkcesoriaObject akcesoriaObject : rpgcore.getAkcesoriaManager().getAkcesoriaObjects()) {
            this.saveDataAkcesoria(akcesoriaObject.getId(), akcesoriaObject);
        }
    }


    // USERS
    public Map<UUID, User> loadAllUsers() {
        Map<UUID, User> users = new HashMap<>();
        for (Document document : this.pool.getGracze().find()) {
            User user = new User(document);
            users.put(user.getId(), user);
        }
        return users;
    }

    public void addDataUser(final User user) {
        this.pool.getGracze().insertOne(user.toDocument());
    }

    public void saveDataUser(final UUID id, final User user) {
        this.pool.getGracze().findOneAndReplace(new Document("_id", id.toString()), user.toDocument());
    }

    public void saveAllUsers() {
        for (User user : rpgcore.getUserManager().getUserObjects()) {
            this.saveDataUser(user.getId(), user);
        }
    }


    // RYBAK
    public Map<UUID, RybakObject> loadAllRybak() {
        Map<UUID, RybakObject> rybak = new HashMap<>();
        for (Document document : this.pool.getRybak().find()) {
            RybakObject rybakObject = new RybakObject(document);
            rybak.put(rybakObject.getId(), rybakObject);
        }
        return rybak;
    }

    public void addDataRybak(final RybakObject rybakObject) {
        this.pool.getRybak().insertOne(rybakObject.toDocument());
    }

    public void saveDataRybak(final UUID id, final RybakObject rybakObject) {
        this.pool.getRybak().findOneAndReplace(new Document("_id", id.toString()), rybakObject.toDocument());
    }

    public void saveAllRybak() {
        for (RybakObject rybakObject : rpgcore.getRybakNPC().getRybakObjects()) {
            this.saveDataRybak(rybakObject.getId(), rybakObject);
        }
    }


    // BONUSES
    public Map<UUID, Bonuses> loadAllBonuses() {
        Map<UUID, Bonuses> bonuses1 = new HashMap<>();
        for (Document document : this.pool.getBonuses().find()) {
            Bonuses bonuses = new Bonuses(document);
            bonuses1.put(bonuses.getId(), bonuses);
        }
        return bonuses1;
    }

    public void addDataBonuses(final Bonuses bonuses) {
        this.pool.getBonuses().insertOne(bonuses.toDocument());
    }

    public void saveDataBonuses(final UUID id, final Bonuses bonuses) {
        this.pool.getBonuses().findOneAndReplace(new Document("_id", id.toString()), bonuses.toDocument());
    }

    public void saveAllBonuses() {
        for (Bonuses bonuses : rpgcore.getBonusesManager().getBonusesObjects()) {
            this.saveDataBonuses(bonuses.getId(), bonuses);
        }
    }



    public void onDisable() {
        pool.closePool();
    }


}
