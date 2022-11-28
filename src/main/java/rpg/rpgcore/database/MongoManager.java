package rpg.rpgcore.database;

import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.bao.BaoObject;
import rpg.rpgcore.bonuses.Bonuses;
import rpg.rpgcore.chat.ChatUser;
import rpg.rpgcore.dodatki.DodatkiUser;
import rpg.rpgcore.guilds.GuildObject;
import rpg.rpgcore.klasy.objects.Klasy;
import rpg.rpgcore.magazyn.MagazynObject;
import rpg.rpgcore.npc.duszolog.DuszologObject;
import rpg.rpgcore.npc.gornik.GornikObject;
import rpg.rpgcore.npc.gornik.ore.Ore;
import rpg.rpgcore.npc.kolekcjoner.KolekcjonerObject;
import rpg.rpgcore.npc.lesnik.LesnikObject;
import rpg.rpgcore.npc.lowca.LowcaObject;
import rpg.rpgcore.npc.medyk.MedykObject;
import rpg.rpgcore.npc.przyrodnik.PrzyrodnikObject;
import rpg.rpgcore.npc.rybak.RybakObject;
import rpg.rpgcore.npc.trener.TrenerObject;
import rpg.rpgcore.os.OsObject;
import rpg.rpgcore.pets.PetObject;
import rpg.rpgcore.pets.UserPets;
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

    /*public void fixMires(final String old, final String uuid) {
        Document document = this.pool.getGracze().find(new Document("_id", old)).first(); //dd3d637b-aff4-4fa5-8484-d120ed492d43 7193813f-c9c3-37e6-b72b-4272a3898b80
        this.pool.getGracze().deleteOne(document);
        document.replace("_id", uuid);
        this.pool.getGracze().insertOne(document);
    }

    public void fix(final UUID uuid, final String newUUID) {
        Document document = null;
        if (pool.getBonuses().find(new Document("_id", uuid.toString())).first() != null) {
            document = pool.getBonuses().find(new Document("_id", uuid.toString())).first();
            pool.getBonuses().deleteOne(document);
            document.replace("_id", newUUID);

        }

        if (pool.getOsiagniecia().find(new Document("_id", uuid.toString())).first() != null) {
            document = pool.getOsiagniecia().find(new Document("_id", uuid.toString())).first();
            pool.getOsiagniecia().deleteOne(document);
            document.replace("_id", newUUID);
        }
        if (pool.getKolekcjoner().find(new Document("_id", uuid.toString())).first() != null) {
            document = pool.getKolekcjoner().find(new Document("_id", uuid.toString())).first();
            pool.getKolekcjoner().deleteOne(document);
            document.replace("_id", newUUID);
        }

        if (this.pool.getBao().find(new Document("_id", uuid.toString())).first() != null) {
            document = this.pool.getBao().find(new Document("_id", uuid.toString())).first();
            this.pool.getBao().deleteOne(document);
            document.replace("_id", newUUID);
        }

        if (this.pool.getRybak().find(new Document("_id", uuid.toString())).first() != null) {
            document = this.pool.getRybak().find(new Document("_id", uuid.toString())).first();
            this.pool.getRybak().deleteOne(document);
            document.replace("_id", newUUID);
        }

        if (this.pool.getAkcesoria().find(new Document("_id", uuid.toString())).first() != null) {
            document = this.pool.getAkcesoria().find(new Document("_id", uuid.toString())).first();
            this.pool.getAkcesoria().deleteOne(document);
            document.replace("_id", newUUID);
        }

        document = pool.getTargi().find(new Document("_id", uuid.toString())).first();
        pool.getTargi().deleteOne(document);
        document.replace("_id", newUUID);


        if (pool.getTrener().find(new Document("_id", uuid.toString())).first() != null) {
            document = pool.getTrener().find(new Document("_id", uuid.toString())).first();
            pool.getTrener().deleteOne(document);
            document.replace("_id", newUUID);
        }
        if (pool.getMetinolog().find(new Document("_id", uuid.toString())).first() != null) {
            document = pool.getMetinolog().find(new Document("_id", uuid.toString())).first();
            pool.getMetinolog().deleteOne(document);
            document.replace("_id", newUUID);
        }
        if (pool.getKlasy().find(new Document("_id", uuid.toString())).first() != null) {
            document = pool.getKlasy().find(new Document("_id", uuid.toString())).first();
            pool.getKlasy().deleteOne(document);
            document.replace("_id", newUUID);
        }
        if (pool.getMedyk().find(new Document("_id", uuid.toString())).first() != null) {
            document = pool.getMedyk().find(new Document("_id", uuid.toString())).first();
            pool.getMedyk().deleteOne(document);
            document.replace("_id", newUUID);
        }
        if (pool.getGornik().find(new Document("_id", uuid.toString())).first() != null) {
            document = pool.getGornik().find(new Document("_id", uuid.toString())).first();
            pool.getGornik().deleteOne(document);
            document.replace("_id", newUUID);
        }
        if (pool.getDuszolog().find(new Document("_id", uuid.toString())).first() != null) {
            document = pool.getDuszolog().find(new Document("_id", uuid.toString())).first();
            pool.getDuszolog().deleteOne(document);
            document.replace("_id", newUUID);
        }
        if (pool.getPrzyrodnik().find(new Document("_id", uuid.toString())).first() != null) {
            document = pool.getPrzyrodnik().find(new Document("_id", uuid.toString())).first();
            pool.getPrzyrodnik().deleteOne(document);
            document.replace("_id", newUUID);
        }
        if (pool.getChatUsers().find(new Document("_id", uuid.toString())).first() != null) {
            document = pool.getChatUsers().find(new Document("_id", uuid.toString())).first();
            pool.getChatUsers().deleteOne(document);
            document.replace("_id", newUUID);
        }
        if (pool.getMagazyny().find(new Document("_id", uuid.toString())).first() != null) {
            document = pool.getMagazyny().find(new Document("_id", uuid.toString())).first();
            pool.getMagazyny().deleteOne(document);
            document.replace("_id", newUUID);
        }
        if (pool.getLowca().find(new Document("_id", uuid.toString())).first() != null) {
            document = pool.getLowca().find(new Document("_id", uuid.toString())).first();
            pool.getLowca().deleteOne(document);
            document.replace("_id", newUUID);
        }
        if (pool.getLesnik().find(new Document("_id", uuid.toString())).first() != null) {
            document = pool.getLesnik().find(new Document("_id", uuid.toString())).first();
            pool.getLesnik().deleteOne(document);
            document.replace("_id", newUUID);
        }
        if (pool.getPety().find(new Document("_id", uuid.toString())).first() != null) {
            document = pool.getPety().find(new Document("_id", uuid.toString())).first();
            pool.getPety().deleteOne(document);
            document.replace("_id", newUUID);
        }
        if (pool.getUserPets().find(new Document("_id", uuid.toString())).first() != null) {
            document = pool.getUserPets().find(new Document("_id", uuid.toString())).first();
            pool.getUserPets().deleteOne(document);
            document.replace("_id", newUUID);
        }
    }*/


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

            if (this.pool.getDodatki().find(new Document("_id", uuid.toString())).first() == null) {
                this.addDataDodatki(new DodatkiUser(uuid));
            }

            try {
                obj = pool.getTargi().find(new Document("_id", uuid.toString())).first();
                if (obj != null) rpgcore.getTargManager().putPlayerInTargMap(uuid, Utils.fromBase64(String.valueOf(obj.get("Targ")), "&f&lTarg gracza &3" + rpgcore.getUserManager().find(uuid).getName()));
            } catch (final IOException e) {
                e.printStackTrace();
            }

            if (pool.getTrener().find(new Document("_id", uuid.toString())).first() == null) {
                this.addDataTrener(new TrenerObject(uuid));
            }
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
            if (pool.getChatUsers().find(new Document("_id", uuid.toString())).first() == null) {
                this.addDataChatUsers(new ChatUser(uuid));
            }
            if (pool.getMagazyny().find(new Document("_id", uuid.toString())).first() == null) {
                this.addDataMagazyny(new MagazynObject(uuid));
            }
            if (pool.getLowca().find(new Document("_id", uuid.toString())).first() == null) {
                this.addDataLowca(new LowcaObject(uuid));
            }
            if (pool.getLesnik().find(new Document("_id", uuid.toString())).first() == null) {
                this.addDataLesnik(new LesnikObject(uuid));
            }
            if (pool.getPety().find(new Document("_id", uuid.toString())).first() == null) {
                this.addDataActivePets(new PetObject(uuid));
            }
            if (pool.getUserPets().find(new Document("_id", uuid.toString())).first() == null) {
                this.addDataUserPets(new UserPets(uuid));
            }
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

    public void createPlayer(final Player player, final UUID uuid, final String nick) {

        final User user = new User(uuid, nick);
        user.getInventoriesUser().setInventory(Utils.itemStackArrayToBase64(player.getInventory().getContents()));
        user.getInventoriesUser().setArmor(Utils.itemStackArrayToBase64(player.getInventory().getArmorContents()));
        user.getInventoriesUser().setEnderchest(Utils.itemStackArrayToBase64(player.getEnderChest().getContents()));
        this.addDataUser(user);
        rpgcore.getUserManager().add(user);

        final DodatkiUser dodatkiUser = new DodatkiUser(uuid);
        this.addDataDodatki(dodatkiUser);
        rpgcore.getDodatkiManager().add(dodatkiUser);

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

        final MagazynObject magazynObject = new MagazynObject(uuid);
        this.addDataMagazyny(magazynObject);
        rpgcore.getMagazynManager().add(magazynObject);

        final LowcaObject lowcaObject = new LowcaObject(uuid);
        this.addDataLowca(lowcaObject);
        rpgcore.getLowcaNPC().add(lowcaObject);

        final LesnikObject lesnikObject = new LesnikObject(uuid);
        this.addDataLesnik(lesnikObject);
        rpgcore.getLesnikNPC().add(lesnikObject);

        final PetObject petObject = new PetObject(uuid);
        this.addDataActivePets(petObject);
        rpgcore.getPetyManager().addToActivePet(petObject);

        final UserPets userPets = new UserPets(uuid);
        this.addDataUserPets(userPets);
        rpgcore.getPetyManager().addToUserPets(userPets);

        final TrenerObject trenerObject = new TrenerObject(uuid);
        this.addDataTrener(trenerObject);
        rpgcore.getTrenerNPC().add(trenerObject);

        Document document;

        document = new Document();
        document.append("_id", uuid.toString());
        document.append("Targ", Utils.toBase64(rpgcore.getTargManager().getPlayerTarg(uuid)));
        pool.getTargi().insertOne(document);
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
            this.saveDataDodatki(uuid, rpgcore.getDodatkiManager().find(uuid));
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
            this.saveDataMagazyny(uuid, rpgcore.getMagazynManager().find(uuid));
            this.saveDataLowca(uuid, rpgcore.getLowcaNPC().find(uuid));

            this.saveDataTrener(uuid, rpgcore.getTrenerNPC().find(uuid));
            this.saveDataMetinolog(uuid, rpgcore.getMetinologNPC().find(uuid));
            this.saveDataLesnik(uuid, rpgcore.getLesnikNPC().find(uuid));
            this.saveDataUserPets(uuid, rpgcore.getPetyManager().findUserPets(uuid));
            this.saveDataActivePets(uuid, rpgcore.getPetyManager().findActivePet(uuid));


            Utils.sendToHighStaff("&aPomyslnie zapisano gracza: &6" + rpgcore.getUserManager().find(uuid).getName() + " &a w czasie: &6" + (System.currentTimeMillis() - start) + "ms");
            System.out.println("[HellRPGCore] Pomyslnie zapisano gracza: " + rpgcore.getUserManager().find(uuid).getName() + " a w czasie: &6" + (System.currentTimeMillis() - start) + "ms");
        } catch (final Exception e) {
            Utils.sendToHighStaff("&cWystapil blad podczas zapisu gracza: &6" + rpgcore.getUserManager().find(uuid).getName());
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


    // OsiagnieciaCommand
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

    // DODATKI
    public Map<UUID, DodatkiUser> loadAllDodatki() {
        Map<UUID, DodatkiUser> dodatki = new HashMap<>();
        for (Document document : this.pool.getDodatki().find()) {
            final DodatkiUser dodatkiUser = new DodatkiUser(document);
            dodatki.put(dodatkiUser.getUuid(), dodatkiUser);
        }
        return dodatki;
    }

    public void addDataDodatki(final DodatkiUser dodatkiUser) {
        this.pool.getDodatki().insertOne(dodatkiUser.toDocument());
    }

    public void saveDataDodatki(final UUID id, final DodatkiUser dodatkiUser) {
        this.pool.getDodatki().findOneAndReplace(new Document("_id", id.toString()), dodatkiUser.toDocument());
    }

    public void saveAllDodatki() {
        for (DodatkiUser dodatkiUser : rpgcore.getDodatkiManager().getDodatkiUsers()) {
            this.saveDataDodatki(dodatkiUser.getUuid(), dodatkiUser);
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

    // CHAT
    public Map<UUID, ChatUser> loadAllChatUsers() {
        Map<UUID, ChatUser> chat = new HashMap<>();
        for (Document document : this.pool.getChatUsers().find()) {
            ChatUser chatUser = new ChatUser(document);
            chat.put(chatUser.getUuid(), chatUser);
        }
        return chat;
    }

    public void addDataChatUsers(final ChatUser chatUser) {
        this.pool.getChatUsers().insertOne(chatUser.toDocument());
    }

    public void saveDataChatUsers(final UUID id, final ChatUser chatUser) {
        this.pool.getChatUsers().findOneAndReplace(new Document("_id", id.toString()), chatUser.toDocument());
    }

    public void saveAllChatUsers() {
        for (ChatUser chatUser : rpgcore.getChatManager().getChatUsersObjects()) {
            this.saveDataChatUsers(chatUser.getUuid(), chatUser);
        }
    }

    // CHAT
    public Map<UUID, MagazynObject> loadAllMagazyny() {
        Map<UUID, MagazynObject> magazyny = new HashMap<>();
        for (Document document : this.pool.getMagazyny().find()) {
            MagazynObject magazynObject = new MagazynObject(document);
            magazyny.put(magazynObject.getId(), magazynObject);
        }
        return magazyny;
    }

    public void addDataMagazyny(final MagazynObject magazynObject) {
        this.pool.getMagazyny().insertOne(magazynObject.toDocument());
    }

    public void saveDataMagazyny(final UUID id, final MagazynObject magazynObject) {
        this.pool.getMagazyny().findOneAndReplace(new Document("_id", id.toString()), magazynObject.toDocument());
    }

    public void saveAllMagazyny() {
        for (MagazynObject magazynObject : rpgcore.getMagazynManager().getMagazynObjects()) {
            this.saveDataMagazyny(magazynObject.getId(), magazynObject);
        }
    }

    // CHAT
    public Map<UUID, LowcaObject> loadAllLowca() {
        Map<UUID, LowcaObject> lowca = new HashMap<>();
        for (Document document : this.pool.getLowca().find()) {
            LowcaObject lowcaObject = new LowcaObject(document);
            lowca.put(lowcaObject.getId(), lowcaObject);
        }
        return lowca;
    }

    public void addDataLowca(final LowcaObject lowcaObject) {
        this.pool.getLowca().insertOne(lowcaObject.toDocument());
    }

    public void saveDataLowca(final UUID id, final LowcaObject lowcaObject) {
        this.pool.getLowca().findOneAndReplace(new Document("_id", id.toString()), lowcaObject.toDocument());
    }

    public void saveAllLowca() {
        for (LowcaObject lowcaObject : rpgcore.getLowcaNPC().getLowcaObjects()) {
            this.saveDataLowca(lowcaObject.getId(), lowcaObject);
        }
    }

    // LESNIK
    public Map<UUID, LesnikObject> loadAllLesnik() {
        Map<UUID, LesnikObject> lesnik = new HashMap<>();
        for (Document document : this.pool.getLesnik().find()) {
            LesnikObject lesnikObject = new LesnikObject(document);
            lesnik.put(lesnikObject.getId(), lesnikObject);
        }
        return lesnik;
    }

    public void addDataLesnik(final LesnikObject lesnikObject) {
        this.pool.getLesnik().insertOne(lesnikObject.toDocument());
    }

    public void saveDataLesnik(final UUID id, final LesnikObject lesnikObject) {
        this.pool.getLesnik().findOneAndReplace(new Document("_id", id.toString()), lesnikObject.toDocument());
    }

    public void saveAllLesnik() {
        for (LesnikObject lesnikObject : rpgcore.getLesnikNPC().getLesnikObjects()) {
            this.saveDataLesnik(lesnikObject.getId(), lesnikObject);
        }
    }


    // TRENER
    public Map<UUID, TrenerObject> loadAllTrener() {
        Map<UUID, TrenerObject> trener = new HashMap<>();
        for (Document document : this.pool.getTrener().find()) {
            TrenerObject trenerObject = new TrenerObject(document);
            trener.put(trenerObject.getId(), trenerObject);
        }
        return trener;
    }

    public void addDataTrener(final TrenerObject trenerObject) {
        this.pool.getTrener().insertOne(trenerObject.toDocument());
    }

    public void saveDataTrener(final UUID id, final TrenerObject trenerObject) {
        this.pool.getTrener().findOneAndReplace(new Document("_id", id.toString()), trenerObject.toDocument());
    }

    public void saveAllTrener() {
        for (TrenerObject trenerObject : rpgcore.getTrenerNPC().getTrenerObjects()) {
            this.saveDataTrener(trenerObject.getId(), trenerObject);
        }
    }


    // PETY
    public Map<UUID, PetObject> loadAllActivePets() {
        Map<UUID, PetObject> pety = new HashMap<>();
        for (Document document : this.pool.getPety().find()) {
            PetObject petObject = new PetObject(document);
            pety.put(petObject.getId(), petObject);
        }
        return pety;
    }

    public void addDataActivePets(final PetObject petObject) {
        this.pool.getPety().insertOne(petObject.toDocument());
    }

    public void saveDataActivePets(final UUID id, final PetObject petObject) {
        this.pool.getPety().findOneAndReplace(new Document("_id", id.toString()), petObject.toDocument());
    }

    public void saveAllActivePets() {
        for (PetObject petObject : rpgcore.getPetyManager().getAllActivePets()) {
            this.saveDataActivePets(petObject.getId(), petObject);
        }
    }

    public Map<UUID, UserPets> loadAllUserPets() {
        Map<UUID, UserPets> pety = new HashMap<>();
        for (Document document : this.pool.getUserPets().find()) {
            UserPets userPets = new UserPets(document);
            pety.put(userPets.getUuid(), userPets);
        }
        return pety;
    }

    public void addDataUserPets(final UserPets userPets) {
        this.pool.getUserPets().insertOne(userPets.toDocument());
    }

    public void saveDataUserPets(final UUID id, final UserPets userPets) {
        this.pool.getUserPets().findOneAndReplace(new Document("_id", id.toString()), userPets.toDocument());
    }

    public void saveAllUserPets() {
        for (UserPets userPets : rpgcore.getPetyManager().getAllUserPets()) {
            this.saveDataUserPets(userPets.getUuid(), userPets);
        }
    }

    // GILDIE
    public Map<String, GuildObject> loadAllGuilds() {
        Map<String, GuildObject> guilds = new HashMap<>();
        for (Document document : this.pool.getGildie().find()) {
            GuildObject guildObject = new GuildObject(document);
            guilds.put(guildObject.getTag(), guildObject);
        }
        return guilds;
    }

    public void addDataGuild(final GuildObject guildObject) {
        this.pool.getGildie().insertOne(guildObject.toDocument());
    }

    public void removeDataGuild(final String tag) {
        this.pool.getGildie().findOneAndDelete(new Document("_id", tag));
    }

    public void saveDataGuild(final String tag, final GuildObject guildObject) {
        this.pool.getGildie().findOneAndReplace(new Document("_id", tag), guildObject.toDocument());
    }

    public void saveAllGuilds() {
        for (GuildObject guildObject : rpgcore.getGuildManager().getGuilds()) {
            this.saveDataGuild(guildObject.getTag(), guildObject);
        }
    }


    // RUDY DO GORNIKA
    public Map<Location, Ore> loadAllOreLocations() {
        Map<Location, Ore> oreMap = new HashMap<>();
        for (Document document : this.pool.getOreLocations().find()) {
            Ore ore = new Ore(document);
            oreMap.put(ore.getLocation(), ore);
        }
        return oreMap;
    }

    public void addDataOreLocation(final Ore ore) {
        this.pool.getOreLocations().insertOne(ore.toDocument());
    }

    public void removeDataOreLocation(final Ore ore) {
        this.pool.getOreLocations().findOneAndDelete(new Document("_id", ore.getId()));
    }

    public void saveDataOreLocation(final Ore ore) {
        this.pool.getGildie().findOneAndReplace(new Document("_id", ore.getId()), ore.toDocument());
    }

    public void saveAllOreLocations() {
        for (Ore ore : rpgcore.getOreManager().getOreLocations()) {
            this.saveDataOreLocation(ore);
        }
    }



    public void onDisable() {
        pool.closePool();
    }


}
