package rpg.rpgcore.database;

import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.bao.BaoObject;
import rpg.rpgcore.bonuses.Bonuses;
import rpg.rpgcore.npc.czarownica.objects.CzarownicaUser;
import rpg.rpgcore.npc.mistrz_yang.objects.MistrzYangUser;
import rpg.rpgcore.bossy.objects.BossyUser;
import rpg.rpgcore.chat.ChatUser;
import rpg.rpgcore.commands.admin.serverwhitelist.objects.SerwerWhiteList;
import rpg.rpgcore.dodatki.DodatkiUser;
import rpg.rpgcore.dodatki.akcesoriaD.objects.AkcesoriaDodatUser;
import rpg.rpgcore.dodatki.akcesoriaP.objects.AkcesoriaPodstUser;
import rpg.rpgcore.dodatki.bony.objects.BonyUser;
import rpg.rpgcore.guilds.GuildObject;
import rpg.rpgcore.kociolki.KociolkiUser;
import rpg.rpgcore.lvl.artefaktyZaLvL.ArtefaktyZaLvl;
import rpg.rpgcore.managers.disabled.Disabled;
import rpg.rpgcore.metiny.Metiny;
import rpg.rpgcore.npc.duszolog.DuszologObject;
import rpg.rpgcore.npc.gornik.GornikObject;
import rpg.rpgcore.npc.gornik.ore.Ore;
import rpg.rpgcore.npc.handlarz.objects.HandlarzUser;
import rpg.rpgcore.npc.kolekcjoner.KolekcjonerObject;
import rpg.rpgcore.npc.lesnik.LesnikObject;
import rpg.rpgcore.npc.lowca.LowcaObject;
import rpg.rpgcore.npc.magazynier.objects.MagazynierUser;
import rpg.rpgcore.npc.medrzec.objects.MedrzecUser;
import rpg.rpgcore.npc.metinolog.objects.MetinologObject;
import rpg.rpgcore.npc.przyrodnik.PrzyrodnikObject;
import rpg.rpgcore.npc.pustelnik.objects.PustelnikUser;
import rpg.rpgcore.npc.rybak.objects.RybakObject;
import rpg.rpgcore.npc.wyslannik.objects.WyslannikObject;
import rpg.rpgcore.osiagniecia.objects.OsUser;
import rpg.rpgcore.pets.PetObject;
import rpg.rpgcore.pets.UserPets;
import rpg.rpgcore.ranks.types.RankTypePlayer;
import rpg.rpgcore.server.ServerUser;
import rpg.rpgcore.spawn.SpawnManager;
import rpg.rpgcore.user.User;
import rpg.rpgcore.user.WWWUser;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.wyszkolenie.objects.WyszkolenieUser;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
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
        for (User user : rpgcore.getUserManager().getUserObjects()) {
            final UUID uuid = user.getId();

            this.addDataUser(rpgcore.getUserManager().find(uuid));
            this.addDataDodatki(rpgcore.getDodatkiManager().find(uuid));
            this.addDataBonuses(rpgcore.getBonusesManager().find(uuid));
            this.addDataBao(rpgcore.getBaoManager().find(uuid));
            //this.saveKlasyData(uuid, rpgcore.getklasyHelper().find(uuid));
            this.addDataDuszolog(rpgcore.getDuszologNPC().find(uuid));
            this.addDataKolekcjner(rpgcore.getKolekcjonerNPC().find(uuid));
            this.addDataMedrzec(rpgcore.getMedrzecNPC().find(uuid));
            this.addDataMetinolog(rpgcore.getMetinologNPC().find(uuid));
            this.addDataPrzyrodnik(rpgcore.getPrzyrodnikNPC().find(uuid));
            this.addDataRybak(rpgcore.getRybakNPC().find(uuid));
            this.addDataOs(rpgcore.getOsManager().find(uuid));
            this.addDataMagazynier(rpgcore.getMagazynierNPC().find(uuid));
            this.addDataLowca(rpgcore.getLowcaNPC().find(uuid));
            this.addDataWyslannik(rpgcore.getWyslannikNPC().find(uuid));
            this.addDataLesnik(rpgcore.getLesnikNPC().find(uuid));
            this.addDataUserPets(rpgcore.getPetyManager().findUserPets(uuid));
            this.addDataActivePets(rpgcore.getPetyManager().findActivePet(uuid));

        }
    }

    public void clearDatabase(final UUID uuid) {
        Document document;
        if (pool.getGracze().find(new Document("_id", uuid.toString())).first() != null) {
            pool.getGracze().deleteOne(new Document("_id", uuid.toString()));
        }
        if (pool.getBonuses().find(new Document("_id", uuid.toString())).first() != null) {
            pool.getBonuses().deleteOne(new Document("_id", uuid.toString()));
        }

        if (pool.getOsiagniecia().find(new Document("_id", uuid.toString())).first() != null) {
            pool.getOsiagniecia().deleteOne(new Document("_id", uuid.toString()));
        }
        if (pool.getKolekcjoner().find(new Document("_id", uuid.toString())).first() != null) {
            pool.getKolekcjoner().deleteOne(new Document("_id", uuid.toString()));

        }

        if (this.pool.getBao().find(new Document("_id", uuid.toString())).first() != null) {
            this.pool.getBao().deleteOne(new Document("_id", uuid.toString()));
        }

        if (this.pool.getRybak().find(new Document("_id", uuid.toString())).first() != null) {
            this.pool.getRybak().deleteOne(new Document("_id", uuid.toString()));
        }

        if (this.pool.getDodatki().find(new Document("_id", uuid.toString())).first() != null) {
            this.pool.getDodatki().deleteOne(new Document("_id", uuid.toString()));
        }

        pool.getTargi().deleteOne(new Document("_id", uuid.toString()));

        if (pool.getMetinolog().find(new Document("_id", uuid.toString())).first() != null) {
            pool.getMetinolog().deleteOne(new Document("_id", uuid.toString()));
        }
        if (pool.getKlasy().find(new Document("_id", uuid.toString())).first() != null) {
            pool.getKlasy().deleteOne(new Document("_id", uuid.toString()));
        }
        if (pool.getMedrzec().find(new Document("_id", uuid.toString())).first() != null) {
            pool.getMedrzec().deleteOne(new Document("_id", uuid.toString()));
        }
        if (pool.getGornik().find(new Document("_id", uuid.toString())).first() != null) {
            pool.getGornik().deleteOne(new Document("_id", uuid.toString()));

        }
        if (pool.getDuszolog().find(new Document("_id", uuid.toString())).first() != null) {
            pool.getDuszolog().deleteOne(new Document("_id", uuid.toString()));
        }
        if (pool.getPrzyrodnik().find(new Document("_id", uuid.toString())).first() != null) {
            pool.getPrzyrodnik().deleteOne(new Document("_id", uuid.toString()));
        }
        if (pool.getChatUsers().find(new Document("_id", uuid.toString())).first() != null) {
            pool.getChatUsers().deleteOne(new Document("_id", uuid.toString()));
        }
        if (pool.getMagazynier().find(new Document("_id", uuid.toString())).first() != null) {
            pool.getMagazynier().deleteOne(new Document("_id", uuid.toString()));

        }
        if (pool.getLowca().find(new Document("_id", uuid.toString())).first() != null) {
            pool.getLowca().deleteOne(new Document("_id", uuid.toString()));
        }
        if (pool.getLesnik().find(new Document("_id", uuid.toString())).first() != null) {
            pool.getLesnik().deleteOne(new Document("_id", uuid.toString()));
        }
        if (pool.getPety().find(new Document("_id", uuid.toString())).first() != null) {
            pool.getPety().deleteOne(new Document("_id", uuid.toString()));
        }
        if (pool.getUserPets().find(new Document("_id", uuid.toString())).first() != null) {
            pool.getUserPets().deleteOne(new Document("_id", uuid.toString()));
        }
        if (pool.getWyslannik().find(new Document("_id", uuid.toString())).first() == null) {
            pool.getWyslannik().deleteOne(new Document("_id", uuid.toString()));
        }
        if (pool.getHandlarz().find(new Document("_id", uuid.toString())).first() == null) {
            pool.getHandlarz().deleteOne(new Document("_id", uuid.toString()));
        }
    }
    //dd3d637b-aff4-4fa5-8484-d120ed492d43 - Mires
    //c166a38d-6ddf-47cb-8aed-2b05fb502051 - Chytryy
    //672d510e-083b-39f8-9681-4d8bc892586d - Orzel

    //4d335d52-df9f-479c-8d0a-57de4a4cb2fe - Fabi

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

        if (pool.getOther().find(new Document("_id", "disabled")).first() == null) {
            final Disabled disabled = new Disabled();
            this.addDataDisabled(disabled);
            rpgcore.getDisabledManager().set(disabled);
        }

        if (pool.getOther().find(new Document("_id", "bossy")).first() == null) {
            final BossyUser bossy = new BossyUser();
            this.addDataBossy(bossy);
            rpgcore.getBossyManager().set(bossy);
        }

        for (Document obj : pool.getGracze().find()) {
            UUID uuid = UUID.fromString(obj.get("_id").toString());
            System.out.println(uuid);

            if (pool.getBonuses().find(new Document("_id", uuid.toString())).first() == null) {
                final Bonuses bonuses = new Bonuses(uuid);
                this.addDataBonuses(bonuses);
                rpgcore.getBonusesManager().add(bonuses);
            }

            if (pool.getOsiagniecia().find(new Document("_id", uuid.toString())).first() == null) {
                final OsUser user = new OsUser(uuid);
                this.addDataOs(user);
                rpgcore.getOsManager().add(user);
            }
            if (pool.getKolekcjoner().find(new Document("_id", uuid.toString())).first() == null) {
                final KolekcjonerObject user = new KolekcjonerObject(uuid);
                this.addDataKolekcjner(user);
                rpgcore.getKolekcjonerNPC().add(user);
            }

            if (this.pool.getBao().find(new Document("_id", uuid.toString())).first() == null) {
                final BaoObject user = new BaoObject(uuid);
                this.addDataBao(user);
                rpgcore.getBaoManager().add(user);
            }

            if (this.pool.getRybak().find(new Document("_id", uuid.toString())).first() == null) {
                final RybakObject user = new RybakObject(uuid);
                this.addDataRybak(user);
                rpgcore.getRybakNPC().add(user);
            }

            if (this.pool.getDodatki().find(new Document("_id", uuid.toString())).first() == null) {
                final DodatkiUser user = new DodatkiUser(uuid);
                this.addDataDodatki(user);
                rpgcore.getDodatkiManager().add(user);
            }

            try {
                obj = pool.getTargi().find(new Document("_id", uuid.toString())).first();
                if (obj != null) {
                    if (!obj.getString("Targ").isEmpty()) {
                        rpgcore.getTargManager().putPlayerInTargMap(uuid, Utils.fromBase64(obj.getString("Targ"), "&f&lTarg gracza &3" + rpgcore.getUserManager().find(uuid).getName()));
                    }
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }

            if (pool.getMetinolog().find(new Document("_id", uuid.toString())).first() == null) {
                final MetinologObject user = new MetinologObject(uuid);
                this.addDataMetinolog(user);
                rpgcore.getMetinologNPC().add(user);
            }
            /*if (pool.getKlasy().find(new Document("_id", uuid.toString())).first() == null) {
                final Klasy user = new Klasy(uuid);
                this.addKlasyData(user);
                rpgcore.getklasyHelper().add(user);
            }*/
            if (pool.getMedrzec().find(new Document("_id", uuid.toString())).first() == null) {
                final MedrzecUser user = new MedrzecUser(uuid);
                this.addDataMedrzec(user);
                rpgcore.getMedrzecNPC().add(user);
            }
            if (pool.getGornik().find(new Document("_id", uuid.toString())).first() == null) {
                final GornikObject user = new GornikObject(uuid);
                this.addDataGornik(user);
                rpgcore.getGornikNPC().add(user);
            }
            if (pool.getDuszolog().find(new Document("_id", uuid.toString())).first() == null) {
                final DuszologObject user = new DuszologObject(uuid);
                this.addDataDuszolog(user);
                rpgcore.getDuszologNPC().add(user);
            }
            if (pool.getPrzyrodnik().find(new Document("_id", uuid.toString())).first() == null) {
                final PrzyrodnikObject user = new PrzyrodnikObject(uuid);
                this.addDataPrzyrodnik(user);
                rpgcore.getPrzyrodnikNPC().add(user);
            }
            if (pool.getChatUsers().find(new Document("_id", uuid.toString())).first() == null) {
                final ChatUser user = new ChatUser(uuid);
                this.addDataChatUsers(user);
                rpgcore.getChatManager().add(user);
            }
            if (pool.getMagazynier().find(new Document("_id", uuid.toString())).first() == null) {
                final MagazynierUser user = new MagazynierUser(uuid);
                this.addDataMagazynier(user);
                rpgcore.getMagazynierNPC().add(user);

            }
            if (pool.getLowca().find(new Document("_id", uuid.toString())).first() == null) {
                final LowcaObject user = new LowcaObject(uuid);
                this.addDataLowca(user);
                rpgcore.getLowcaNPC().add(user);
            }
            if (pool.getLesnik().find(new Document("_id", uuid.toString())).first() == null) {
                final LesnikObject user = new LesnikObject(uuid);
                this.addDataLesnik(user);
                rpgcore.getLesnikNPC().add(user);
            }
            if (pool.getPety().find(new Document("_id", uuid.toString())).first() == null) {
                final PetObject user = new PetObject(uuid);
                this.addDataActivePets(user);
                rpgcore.getPetyManager().addToActivePet(user);
            }
            if (pool.getUserPets().find(new Document("_id", uuid.toString())).first() == null) {
                final UserPets user = new UserPets(uuid);
                this.addDataUserPets(user);
                rpgcore.getPetyManager().addToUserPets(user);
            }
            if (pool.getWyslannik().find(new Document("_id", uuid.toString())).first() == null) {
                final WyslannikObject user = new WyslannikObject(uuid);
                this.addDataWyslannik(user);
                rpgcore.getWyslannikNPC().add(user);
            }
            if (pool.getKociolki().find(new Document("_id", uuid.toString())).first() == null) {
                final KociolkiUser user = new KociolkiUser(uuid);
                this.addDataKociolki(user);
                rpgcore.getKociolkiManager().add(user);
            }
            if (pool.getHandlarz().find(new Document("_id", uuid.toString())).first() == null) {
                final HandlarzUser user = new HandlarzUser(uuid);
                this.addDataHandlarz(user);
                rpgcore.getHandlarzNPC().add(user);
            }
            if (pool.getWyszkolenie().find(new Document("_id", uuid.toString())).first() == null) {
                final WyszkolenieUser user = new WyszkolenieUser(uuid);
                this.addDataWyszkolenie(user);
                rpgcore.getWyszkolenieManager().add(user);
            }
            if (pool.getJSON().find(new Document("_id", uuid.toString())).first() == null) {
                final WWWUser user = new WWWUser(uuid);
                this.addDataWWWUser(user);
                rpgcore.getUserManager().addWWWUser(user);
            }
            // TUTAJ ROBISZ ZABEZPIECZENIE JAKBY SIE COS WYJEBALO NA PLECY I NIE STWORZYLO USERA W BAZIE JAK GRACZ WBIL NA SERWER
            // WIEC JESLI NIE MA JEGO DOKUMENTU W KOLEKCJI TO GO TWORZY I DODAJE DO PAMIECI PODRECZNEJ SERWERA
            // TEZ BARDZO WAZEN I NIE ZAPOMNIEC O TYM.
            /*if (pool.getPrzykladowyNPC().find(new Document("_id", uuid.toString())).first() == null) {
                final TestUser user = new TestUser(uuid);
                this.addDataTest(user);
                rpgcore.getTestNPC().add(user);
            }*/
            if (pool.getPustelnik().find(new Document("_id", uuid.toString())).first() == null) {
                final PustelnikUser user = new PustelnikUser(uuid);
                this.addDataPustelnik(user);
                rpgcore.getPustelnikNPC().add(user);
            }
            if (pool.getMistrzYang().find(new Document("_id", uuid.toString())).first() == null) {
                final MistrzYangUser user = new MistrzYangUser(uuid);
                this.addDataMistrzYang(user);
                rpgcore.getMistrzYangNPC().add(user);
            }
            if (pool.getCzarownica().find(new Document("_id", uuid.toString())).first() == null) {
                final CzarownicaUser user = new CzarownicaUser(uuid);
                this.addDataCzarownica(user);
                rpgcore.getCzarownicaNPC().add(user);
            }
        }
        if (pool.getOther().find(new Document("_id", "dodatkowyExp")).first() == null) {
            final ServerUser user = new ServerUser("dodatkowyExp");
            addOtherData(user);
            rpgcore.getServerManager().add(user);
        }
    }

    public void changeAuthUserRank(final UUID uuid, final String rankName) {
        final Document user = this.pool.getAuthUsers().find(new Document("_id", uuid.toString())).first();
        assert user != null;
        user.remove("rankType");
        user.append("rankType", rankName);
        this.pool.getAuthUsers().findOneAndReplace(new Document("_id", uuid.toString()), user);
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

        /*final Klasy klasy = new Klasy(uuid);
        this.addKlasyData(klasy);
        rpgcore.getklasyHelper().add(klasy);*/

        final DuszologObject duszologObject = new DuszologObject(uuid);
        this.addDataDuszolog(duszologObject);
        rpgcore.getDuszologNPC().add(duszologObject);

        final KolekcjonerObject kolekcjonerObject = new KolekcjonerObject(uuid);
        this.addDataKolekcjner(kolekcjonerObject);
        rpgcore.getKolekcjonerNPC().add(kolekcjonerObject);

        final MedrzecUser medrzecUser = new MedrzecUser(uuid);
        this.addDataMedrzec(medrzecUser);
        rpgcore.getMedrzecNPC().add(medrzecUser);

        final MetinologObject metinologObject = new MetinologObject(uuid);
        this.addDataMetinolog(metinologObject);
        rpgcore.getMetinologNPC().add(metinologObject);

        final PrzyrodnikObject przyrodnikObject = new PrzyrodnikObject(uuid);
        this.addDataPrzyrodnik(przyrodnikObject);
        rpgcore.getPrzyrodnikNPC().add(przyrodnikObject);

        final RybakObject rybakObject = new RybakObject(uuid);
        this.addDataRybak(rybakObject);
        rpgcore.getRybakNPC().add(rybakObject);

        final OsUser osObject = new OsUser(uuid);
        this.addDataOs(osObject);
        rpgcore.getOsManager().add(osObject);

        final MagazynierUser magazynierUser = new MagazynierUser(uuid);
        this.addDataMagazynier(magazynierUser);
        rpgcore.getMagazynierNPC().add(magazynierUser);

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

        final WyslannikObject wyslannikObject = new WyslannikObject(uuid);
        this.addDataWyslannik(wyslannikObject);
        rpgcore.getWyslannikNPC().add(wyslannikObject);

        final KociolkiUser kociolkiUser = new KociolkiUser(uuid);
        this.addDataKociolki(kociolkiUser);
        rpgcore.getKociolkiManager().add(kociolkiUser);

        final HandlarzUser handlarzUser = new HandlarzUser(uuid);
        this.addDataHandlarz(handlarzUser);
        rpgcore.getHandlarzNPC().add(handlarzUser);

        final WyszkolenieUser wyszkolenieUser = new WyszkolenieUser(uuid);
        this.addDataWyszkolenie(wyszkolenieUser);
        rpgcore.getWyszkolenieManager().add(wyszkolenieUser);

        final WWWUser wwwUser = new WWWUser(uuid);
        this.addDataWWWUser(wwwUser);
        rpgcore.getUserManager().addWWWUser(wwwUser);

        final PustelnikUser pustelnikUser = new PustelnikUser(uuid);
        this.addDataPustelnik(pustelnikUser);
        rpgcore.getPustelnikNPC().add(pustelnikUser);

        final MistrzYangUser mistrzYangUser = new MistrzYangUser(uuid);
        this.addDataMistrzYang(mistrzYangUser);
        rpgcore.getMistrzYangNPC().add(mistrzYangUser);

        final CzarownicaUser czarownicaUser = new CzarownicaUser(uuid);
        this.addDataCzarownica(czarownicaUser);
        rpgcore.getCzarownicaNPC().add(czarownicaUser);

        // TUTAJ TWORZYSZ USERA JAK NOWY GRACZ WEJDZIE NA SERWER
        // TEZ NIE ZAPOMNIEC BO NIE BEDZIE DZIALAL NPC
        // PATRZ loadALL() DALEJ

        /*final TestUser testUser = new TestUser(uuid);
        this.addDataTest(testUser);
        rpgcore.getTestNPC().add(testUser);*/

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
                        if (user.isTworca()) {
                            user.getRankPlayerUser().setRank(RankTypePlayer.TWORCA);
                            user.getRankPlayerUser().setTime(-1L);
                        }
                    }
                }
            }
            if (user.getPierscienDoswiadczeniaTime() <= System.currentTimeMillis() && user.getPierscienDoswiadczenia() != 0) {
                final Bonuses bonuses = RPGCORE.getInstance().getBonusesManager().find(user.getId());
                bonuses.getBonusesUser().setDodatkowyExp(bonuses.getBonusesUser().getDodatkowyExp() - user.getPierscienDoswiadczenia());
                user.setPierscienDoswiadczenia(0);
                user.setPierscienDoswiadczeniaTime(0L);
                RPGCORE.getInstance().getNmsManager().sendTitleAndSubTitle(player, RPGCORE.getInstance().getNmsManager().makeTitle("&8&l[&4&l!&8&l]", 5, 20, 5), RPGCORE.getInstance().getNmsManager().makeSubTitle("&cTwoj &ePierscien Doswiadczenia &cdobiegl konca", 5, 20, 5));
                RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> {
                    RPGCORE.getInstance().getMongoManager().saveDataUser(user.getId(), user);
                    RPGCORE.getInstance().getMongoManager().saveDataBonuses(user.getId(), bonuses);
                });
            }

            final WWWUser wwwUser = rpgcore.getUserManager().findWWWUser(uuid);
            wwwUser.setArmorJSON(Utils.itemStackArrayToJSON(armor));
            wwwUser.setInventoryJSON(Utils.itemStackArrayToJSON(inventory));
            wwwUser.setEnderchestJSON(Utils.itemStackArrayToJSON(enderchest));

            final DodatkiUser dodatkiUser = rpgcore.getDodatkiManager().find(uuid);
            final BonyUser bonyUser = dodatkiUser.getBony();
            final AkcesoriaPodstUser akcesoriaPodstUser = dodatkiUser.getAkcesoriaPodstawowe();
            final AkcesoriaDodatUser akcesoriaDodatUser = dodatkiUser.getAkcesoriaDodatkowe();

            wwwUser.setBonyJSON(Utils.itemStackArrayToJSON(Utils.deserializeStringsToItemStackArray(
                    bonyUser.getDmg5(),
                    bonyUser.getDmg10(),
                    bonyUser.getDmg15(),
                    bonyUser.getDef5(),
                    bonyUser.getDef10(),
                    bonyUser.getDef15(),
                    bonyUser.getKryt5(),
                    bonyUser.getKryt10(),
                    bonyUser.getKryt15(),
                    bonyUser.getWzmkryt10(),
                    bonyUser.getBlok20(),
                    bonyUser.getPrzeszywka20(),
                    bonyUser.getPredkosc25(),
                    bonyUser.getPredkosc50(),
                    bonyUser.getHp10(),
                    bonyUser.getHp20(),
                    bonyUser.getHp35(),
                    bonyUser.getDmgMetiny()
            )));

            wwwUser.setAkcesoriaPodstawoweJSON(Utils.itemStackArrayToJSON(Utils.deserializeStringsToItemStackArray(
                    akcesoriaPodstUser.getTarcza(),
                    akcesoriaPodstUser.getNaszyjnik(),
                    akcesoriaPodstUser.getKolczyki(),
                    akcesoriaPodstUser.getPierscien(),
                    akcesoriaPodstUser.getDiadem()
            )));

            wwwUser.setAkcesoriaDodatkoweJSON(Utils.itemStackArrayToJSON(Utils.deserializeStringsToItemStackArray(
                    akcesoriaDodatUser.getSzarfa(),
                    akcesoriaDodatUser.getPas(),
                    akcesoriaDodatUser.getMedalion(),
                    akcesoriaDodatUser.getEnergia()
            )));

            wwwUser.setUserPetyJSON(Utils.itemStackArrayToJSON(rpgcore.getPetyManager().findUserPets(uuid).getPety()));
            wwwUser.setActivePet(Utils.itemStackToJSON(rpgcore.getPetyManager().findActivePet(uuid).getPet().getItem()));

            final MagazynierUser magazynierUser = rpgcore.getMagazynierNPC().find(uuid);
            wwwUser.setMagazyn1JSON(Utils.itemStackArrayToJSON(Utils.itemStackArrayFromBase64(magazynierUser.getMagazyn1())));
            wwwUser.setMagazyn2JSON(Utils.itemStackArrayToJSON(Utils.itemStackArrayFromBase64(magazynierUser.getMagazyn2())));
            wwwUser.setMagazyn3JSON(Utils.itemStackArrayToJSON(Utils.itemStackArrayFromBase64(magazynierUser.getMagazyn3())));
            wwwUser.setMagazyn4JSON(Utils.itemStackArrayToJSON(Utils.itemStackArrayFromBase64(magazynierUser.getMagazyn4())));
            wwwUser.setMagazyn5JSON(Utils.itemStackArrayToJSON(Utils.itemStackArrayFromBase64(magazynierUser.getMagazyn5())));


            this.saveDataUser(uuid, rpgcore.getUserManager().find(uuid));
            this.saveDataDodatki(uuid, dodatkiUser);
            this.saveDataBonuses(uuid, rpgcore.getBonusesManager().find(uuid));
            this.saveDataBao(uuid, rpgcore.getBaoManager().find(uuid));
            //this.saveKlasyData(uuid, rpgcore.getklasyHelper().find(uuid));
            this.saveDataDuszolog(uuid, rpgcore.getDuszologNPC().find(uuid));
            this.saveDataKolekcjoner(uuid, rpgcore.getKolekcjonerNPC().find(uuid));
            this.saveDataMedrzec(uuid, rpgcore.getMedrzecNPC().find(uuid));
            this.saveDataMetinolog(uuid, rpgcore.getMetinologNPC().find(uuid));
            this.saveDataPrzyrodnik(uuid, rpgcore.getPrzyrodnikNPC().find(uuid));
            this.saveDataRybak(uuid, rpgcore.getRybakNPC().find(uuid));
            this.saveDataOs(uuid, rpgcore.getOsManager().find(uuid));
            this.saveDataMagazynier(uuid, magazynierUser);
            this.saveDataLowca(uuid, rpgcore.getLowcaNPC().find(uuid));
            this.saveDataWyslannik(uuid, rpgcore.getWyslannikNPC().find(uuid));
            this.saveDataKociolki(uuid, rpgcore.getKociolkiManager().find(uuid));

            this.saveDataLesnik(uuid, rpgcore.getLesnikNPC().find(uuid));
            this.saveDataUserPets(uuid, rpgcore.getPetyManager().findUserPets(uuid));
            this.saveDataActivePets(uuid, rpgcore.getPetyManager().findActivePet(uuid));
            this.saveDataGornik(uuid, rpgcore.getGornikNPC().find(uuid));
            this.saveDataChatUsers(uuid, rpgcore.getChatManager().find(uuid));
            this.saveDataTarg(uuid, user.getName());
            this.saveDataHandlarz(uuid, rpgcore.getHandlarzNPC().find(uuid));
            this.saveDataWyszkolenie(uuid, rpgcore.getWyszkolenieManager().find(uuid));
            this.saveDataWWWUser(uuid, rpgcore.getUserManager().findWWWUser(uuid));
            this.saveDataPustelnik(uuid, rpgcore.getPustelnikNPC().find(uuid));
            this.saveDataMistrzYang(uuid, rpgcore.getMistrzYangNPC().find(uuid));
            this.saveDataCzarownica(uuid, rpgcore.getCzarownicaNPC().find(uuid));
            // TU ZAPISUJESZ USERA PRZY TYCH BACKUPACH CO 5 MIN i PRZY WYLACZENIU SERWERA
            // TEZ NIE ZAPOMNIEC BO SIE WYPIERODLI JAK WYSLANNIK :D
            //this.saveDataTest(uuid, rpgcore.getTestNPC().find(uuid));


            Utils.sendToHighStaff("&aPomyslnie zapisano gracza: &6" + rpgcore.getUserManager().find(uuid).getName() + " &a w czasie: &6" + (System.currentTimeMillis() - start) + "ms");
            System.out.println("[HellRPGCore] Pomyslnie zapisano gracza: " + rpgcore.getUserManager().find(uuid).getName() + " a w czasie: &6" + (System.currentTimeMillis() - start) + "ms");
        } catch (final Exception e) {
            Utils.sendToHighStaff("&cWystapil blad podczas zapisu gracza: &6" + rpgcore.getUserManager().find(uuid).getName());
            System.out.println("[HellRPGCore] Wystapil blad podczas zapisu gracza: " + rpgcore.getUserManager().find(uuid).getName());
            e.printStackTrace();
        }
    }

    public Map<Location, Metiny> loadMetins() {
        Map<Location, Metiny> metiny = new ConcurrentHashMap<>();
        for (Document document : pool.getMetiny().find()) {
            final Metiny metiny1 = new Metiny(document);
            metiny.put(metiny1.getLocation(), metiny1);
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
        if (this.pool.getMetinolog().find(new Document("_id", metinolog.getID().toString())).first() != null) {
            this.pool.getMetinolog().deleteOne(new Document("_id", metinolog.getID().toString()));
        }
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
        for (Document document : this.pool.getOther().find(new Document("_id", "dodatkowyExp"))) {
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

    /*public Map<UUID, Klasy> loadAllKlasy() {
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
    }*/


    // MEDYK
    public Map<UUID, MedrzecUser> loadAllMedrzec() {
        Map<UUID, MedrzecUser> medrzec = new ConcurrentHashMap<>();
        for (Document document : this.pool.getMedrzec().find()) {
            MedrzecUser medrzecUser = new MedrzecUser(document);
            medrzec.put(medrzecUser.getUuid(), medrzecUser);
        }
        return medrzec;
    }

    public void addDataMedrzec(final MedrzecUser medrzecUser) {
        if (this.pool.getMedrzec().find(new Document("_id", medrzecUser.getUuid().toString())).first() != null) {
            this.pool.getMedrzec().deleteOne(new Document("_id", medrzecUser.getUuid().toString()));
        }
        this.pool.getMedrzec().insertOne(medrzecUser.toDocument());
    }

    public void saveDataMedrzec(final UUID id, final MedrzecUser medrzecUser) {
        this.pool.getMedrzec().findOneAndReplace(new Document("_id", id.toString()), medrzecUser.toDocument());
    }

    public void saveAllMedrzec() {
        for (MedrzecUser medrzecUser : rpgcore.getMedrzecNPC().getMedrzecUsers()) {
            this.saveDataMedrzec(medrzecUser.getUuid(), medrzecUser);
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
    public Map<UUID, OsUser> loadAllOs() {
        Map<UUID, OsUser> osUserMap = new ConcurrentHashMap<>();
        for (Document document : this.pool.getOsiagniecia().find()) {
            OsUser osUser = new OsUser(document);
            osUserMap.put(osUser.getUuid(), osUser);
        }
        return osUserMap;
    }

    public void addDataOs(final OsUser osUser) {
        if (this.pool.getOsiagniecia().find(new Document("_id", osUser.getUuid().toString())).first() != null) {
            this.pool.getOsiagniecia().deleteOne(new Document("_id", osUser.getUuid().toString()));
        }
        this.pool.getOsiagniecia().insertOne(osUser.toDocument());
    }

    public void saveDataOs(final UUID id, final OsUser osUser) {
        this.pool.getOsiagniecia().findOneAndReplace(new Document("_id", id.toString()), osUser.toDocument());
    }

    public void saveAllOs() {
        for (OsUser osUser : rpgcore.getOsManager().getOsUsers()) {
            this.saveDataOs(osUser.getUuid(), osUser);
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
        if (this.pool.getKolekcjoner().find(new Document("_id", kolekcjonerObject.getID().toString())).first() != null) {
            this.pool.getKolekcjoner().deleteOne(new Document("_id", kolekcjonerObject.getID().toString()));
        }
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
        if (this.pool.getDuszolog().find(new Document("_id", duszologObject.getID().toString())).first() != null) {
            this.pool.getDuszolog().deleteOne(new Document("_id", duszologObject.getID().toString()));
        }
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
        if (this.pool.getPrzyrodnik().find(new Document("_id", przyrodnikObject.getId().toString())).first() != null) {
            this.pool.getPrzyrodnik().deleteOne(new Document("_id", przyrodnikObject.getId().toString()));
        }
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
        if (this.pool.getBao().find(new Document("_id", baoObject.getId().toString())).first() != null) {
            this.pool.getBao().deleteOne(new Document("_id", baoObject.getId().toString()));
        }
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
        if (this.pool.getDodatki().find(new Document("_id", dodatkiUser.getUuid().toString())).first() != null) {
            this.pool.getDodatki().deleteOne(new Document("_id", dodatkiUser.getUuid().toString()));
        }
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
            document.append("tworca", false);
            document.remove("rankChestCooldown");
            this.pool.getGracze().findOneAndReplace(new Document("_id", document.getString("_id")), document);
            final User user = new User(document);
            users.put(user.getId(), user);
        }
        return users;
    }

    public void addDataUser(final User user) {
        if (this.pool.getGracze().find(new Document("_id", user.getId().toString())).first() != null) {
            this.pool.getGracze().deleteOne(new Document("_id", user.getId().toString()));
        }
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
        if (this.pool.getRybak().find(new Document("_id", rybakObject.getId().toString())).first() != null) {
            this.pool.getRybak().deleteOne(new Document("_id", rybakObject.getId().toString()));
        }
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
        if (this.pool.getBonuses().find(new Document("_id", bonuses.getId().toString())).first() != null) {
            this.pool.getBonuses().deleteOne(new Document("_id", bonuses.getId().toString()));
        }
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
            document.append("dmgHologramsVisable", true);
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

    // MAGAZYNIER
    public Map<UUID, MagazynierUser> loadAllMagazynier() {
        Map<UUID, MagazynierUser> magazynier = new HashMap<>();
        for (Document document : this.pool.getMagazynier().find()) {
            MagazynierUser magazynierUser = new MagazynierUser(document);
            magazynier.put(magazynierUser.getUuid(), magazynierUser);
        }
        return magazynier;
    }

    public void addDataMagazynier(final MagazynierUser magazynierUser) {
        if (this.pool.getMagazynier().find(new Document("_id", magazynierUser.getUuid().toString())).first() != null) {
            this.pool.getMagazynier().deleteOne(new Document("_id", magazynierUser.getUuid().toString()));
        }
        this.pool.getMagazynier().insertOne(magazynierUser.toDocument());
    }

    public void saveDataMagazynier(final UUID id, final MagazynierUser magazynierUser) {
        this.pool.getMagazynier().findOneAndReplace(new Document("_id", id.toString()), magazynierUser.toDocument());
    }

    public void saveAllMagazyny() {
        for (final MagazynierUser magazynierUser : rpgcore.getMagazynierNPC().getUsers()) {
            this.saveDataMagazynier(magazynierUser.getUuid(), magazynierUser);
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
        if (this.pool.getLowca().find(new Document("_id", lowcaObject.getId().toString())).first() != null) {
            this.pool.getLowca().deleteOne(new Document("_id", lowcaObject.getId().toString()));
        }
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
        if (this.pool.getLesnik().find(new Document("_id", lesnikObject.getId().toString())).first() != null) {
            this.pool.getLesnik().deleteOne(new Document("_id", lesnikObject.getId().toString()));
        }
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
        if (this.pool.getPety().find(new Document("_id", petObject.getId().toString())).first() != null) {
            this.pool.getPety().deleteOne(new Document("_id", petObject.getId().toString()));
        }
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
        if (this.pool.getUserPets().find(new Document("_id", userPets.getUuid().toString())).first() != null) {
            this.pool.getUserPets().deleteOne(new Document("_id", userPets.getUuid().toString()));
        }
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
        this.pool.getOreLocations().findOneAndReplace(new Document("_id", ore.getId()), ore.toDocument());
    }

    public void saveAllOreLocations() {
        for (Ore ore : rpgcore.getOreManager().getOreLocations()) {
            this.saveDataOreLocation(ore);
        }
    }

    // WYSLANNIK
    public Map<UUID, WyslannikObject> loadAllWyslannik() {
        Map<UUID, WyslannikObject> userMap = new HashMap<>();
        for (Document document : this.pool.getWyslannik().find()) {
            final WyslannikObject wyslannikObject = new WyslannikObject(document);
            userMap.put(wyslannikObject.getUuid(), wyslannikObject);
        }
        return userMap;
    }

    public void addDataWyslannik(final WyslannikObject wyslannikObject) {
        if (this.pool.getWyslannik().find(new Document("_id", wyslannikObject.getUuid().toString())).first() != null) {
            this.pool.getWyslannik().deleteOne(new Document("_id", wyslannikObject.getUuid().toString()));
        }
        this.pool.getWyslannik().insertOne(wyslannikObject.toDocument());
    }

    public void saveDataWyslannik(final UUID uuid, final WyslannikObject wyslannikObject) {
        this.pool.getWyslannik().findOneAndReplace(new Document("_id", uuid.toString()), wyslannikObject.toDocument());
    }

    public void saveAllWyslannik() {
        for (final WyslannikObject wyslannikObject : rpgcore.getWyslannikNPC().getWyslannikObjects()) {
            this.saveDataWyslannik(wyslannikObject.getUuid(), wyslannikObject);
        }
    }

    // TARGI
    public void addDataTarg(final UUID uuid, final Inventory playerTarg) {
        this.pool.getTargi().insertOne(new Document("_id", uuid.toString()).append("Targ", Utils.toBase64(playerTarg)));
    }

    public void saveDataTarg(final UUID uuid, final String playerName) {
        this.pool.getTargi().findOneAndReplace(new Document("_id", uuid.toString()), new Document("_id", uuid.toString()).append("Targ", (rpgcore.getNewTargManager().getPlayerTargItems(uuid)).isEmpty() ? "" : Utils.toBase64(rpgcore.getNewTargManager().getPlayerTarg(playerName, uuid))));
    }

    // PRZYKLADOWY NPC
    // TO JEST PODSTAWA DO ZAPISU I ODCZYTY USERA Z BAZY, ZMIENIASZ TYLKO NAZWY FUNKCJI WEDLUG WZORU (PATRZ POPRZEDNIE) I TO JAKIE PRZYJMUJA ZMIENNE I GDZIE ZAPISUJE
    // PAMIETAJ ZEBY ZMIENIC KOLEKCJE NP. getPrzykladowyNPC() NA getTwojNPC() BO INACZEJ SIE WSZYTSKO SPIERDOLI :D
    /*public Map<UUID, TestUser> loadAllTestUser() {
        Map<UUID, TestUser> userMap = new HashMap<>();
        for (Document document : this.pool.getPrzykladowyNPC().find()) {
            final TestUser testUser = new TestUser(document);
            userMap.put(testUser.getUuid(), testUser);
        }
        return userMap;
    }

    public void addDataTest(final TestUser testUser) {
        this.pool.getPrzykladowyNPC().insertOne(testUser.toDocument());
    }

    public void saveDataTest(final UUID uuid, final TestUser testUser) {
        this.pool.getPrzykladowyNPC().findOneAndReplace(new Document("_id", uuid.toString()), testUser.toDocument());
    }

    public void saveAllTest() {
        for (final TestUser testUser : rpgcore.getTestNPC().getTestUsers()) {
            this.saveDataTest(testUser.getUuid(), testUser);
        }
    }*/

    // KOCIOLKI
    public Map<UUID, KociolkiUser> loadAllKociolki() {
        Map<UUID, KociolkiUser> userMap = new HashMap<>();
        for (Document document : this.pool.getKociolki().find()) {
            final KociolkiUser kociolkiUser = new KociolkiUser(document);
            userMap.put(kociolkiUser.getUuid(), kociolkiUser);
        }
        return userMap;
    }

    public void addDataKociolki(final KociolkiUser kociolkiUser) {
        if (this.pool.getKociolki().find(new Document("_id", kociolkiUser.getUuid().toString())).first() != null) {
            this.pool.getKolekcjoner().deleteOne(new Document("_id", kociolkiUser.getUuid().toString()));
        }
        this.pool.getKociolki().insertOne(kociolkiUser.toDocument());
    }

    public void saveDataKociolki(final UUID uuid, final KociolkiUser kociolkiUser) {
        this.pool.getKociolki().findOneAndReplace(new Document("_id", uuid.toString()), kociolkiUser.toDocument());
    }

    public void saveAllKociolki() {
        for (final KociolkiUser kociolkiUser : rpgcore.getKociolkiManager().getKociolkiUsers()) {
            this.saveDataKociolki(kociolkiUser.getUuid(), kociolkiUser);
        }
    }

    // SERVER WHITELIST
    public SerwerWhiteList loadSerwerWhiteList() {
        Document document = this.pool.getSerwerWhiteList().find().first();
        if (document == null) {
            final SerwerWhiteList serwerWhiteList = new SerwerWhiteList();
            this.pool.getSerwerWhiteList().insertOne(serwerWhiteList.toDocument());
            return serwerWhiteList;
        }
        return new SerwerWhiteList(document);
    }

    public void saveSerwerWhiteList(final SerwerWhiteList serwerWhiteList) {
        this.pool.getSerwerWhiteList().findOneAndReplace(new Document("_id", "SerwerWhiteList"), serwerWhiteList.toDocument());
    }

    // ARTEFAKTY ZA LVL
    public ArtefaktyZaLvl loadArtefaktyZaLvl() {
        final Document document = this.pool.getArtefaktyZaLvL().find().first();
        if (document == null) {
            final ArtefaktyZaLvl artefaktyZaLvl = new ArtefaktyZaLvl();
            this.pool.getArtefaktyZaLvL().insertOne(artefaktyZaLvl.toDocument());
            return artefaktyZaLvl;
        }
        return new ArtefaktyZaLvl(document);
    }

    public void saveArtefaktyZaLvl(final ArtefaktyZaLvl artefaktyZaLvl) {
        this.pool.getArtefaktyZaLvL().findOneAndReplace(new Document("_id", "artefaktyZaLvl"), artefaktyZaLvl.toDocument());
    }


    // HANDLARZ
    public Map<UUID, HandlarzUser> loadAllHandlarz() {
        Map<UUID, HandlarzUser> userMap = new HashMap<>();
        for (Document document : this.pool.getHandlarz().find()) {
            final HandlarzUser handlarzUser = new HandlarzUser(document);
            userMap.put(handlarzUser.getUuid(), handlarzUser);
        }
        return userMap;
    }

    public void addDataHandlarz(final HandlarzUser handlarzUser) {
        if (this.pool.getHandlarz().find(new Document("_id", handlarzUser.getUuid().toString())).first() != null) {
            this.pool.getHandlarz().deleteOne(new Document("_id", handlarzUser.getUuid().toString()));
        }
        this.pool.getHandlarz().insertOne(handlarzUser.toDocument());
    }

    public void saveDataHandlarz(final UUID uuid, final HandlarzUser handlarzUser) {
        this.pool.getHandlarz().findOneAndReplace(new Document("_id", uuid.toString()), handlarzUser.toDocument());
    }

    public void saveAllHandlarz() {
        for (final HandlarzUser handlarzUser : rpgcore.getHandlarzNPC().getHandlarzUsers()) {
            this.saveDataHandlarz(handlarzUser.getUuid(), handlarzUser);
        }
    }

    // DISABLED
    public Disabled loadAllDisabled() {
        final Document document = this.pool.getOther().find(new Document("_id", "disabled")).first();
        if (document == null) {
            final Disabled disabled = new Disabled();
            this.addDataDisabled(disabled);
            return disabled;
        }
        return new Disabled(document);
    }

    public void addDataDisabled(final Disabled disabled) {
        if (this.pool.getOther().find(new Document("_id", "disabled")).first() != null) {
            this.pool.getOther().deleteOne(new Document("_id", "disabled"));
        }
        this.pool.getOther().insertOne(disabled.toDocument());
    }

    public void saveDataDisabled(final Disabled disabled) {
        this.pool.getOther().findOneAndReplace(new Document("_id", "disabled"), disabled.toDocument());
    }

    public void saveAllDisabled() {
        this.saveDataDisabled(rpgcore.getDisabledManager().getDisabled());
    }

    // WYSZKOLENIE

    public Map<UUID, WyszkolenieUser> loadAllWyszkolenie() {
        Map<UUID, WyszkolenieUser> userMap = new HashMap<>();
        for (Document document : this.pool.getWyszkolenie().find()) {
            final WyszkolenieUser wyszkolenieUser = new WyszkolenieUser(document);
            userMap.put(wyszkolenieUser.getUuid(), wyszkolenieUser);
        }
        return userMap;
    }

    public void addDataWyszkolenie(final WyszkolenieUser wyszkolenieUser) {
        if (this.pool.getWyszkolenie().find(new Document("_id", wyszkolenieUser.getUuid().toString())).first() != null) {
            this.pool.getWyszkolenie().deleteOne(new Document("_id", wyszkolenieUser.getUuid().toString()));
        }
        this.pool.getWyszkolenie().insertOne(wyszkolenieUser.toDocument());
    }

    public void saveDataWyszkolenie(final UUID uuid, final WyszkolenieUser wyszkolenieUser) {
        this.pool.getWyszkolenie().findOneAndReplace(new Document("_id", uuid.toString()), wyszkolenieUser.toDocument());
    }

    public void saveAllWyszkolenie() {
        for (final WyszkolenieUser wyszkolenieUser : rpgcore.getWyszkolenieManager().getWyszkolenieUsers()) {
            this.saveDataWyszkolenie(wyszkolenieUser.getUuid(), wyszkolenieUser);
        }
    }

    // WWW USER

    public Map<UUID, WWWUser> loadAllWWWUsers() {
        Map<UUID, WWWUser> userMap = new HashMap<>();
        for (Document document : this.pool.getJSON().find()) {
            final WWWUser wwwUser = new WWWUser(document);
            userMap.put(wwwUser.getUuid(), wwwUser);
        }
        return userMap;
    }

    public void addDataWWWUser(final WWWUser wwwUser) {
        if (this.pool.getJSON().find(new Document("_id", wwwUser.getUuid().toString())).first() != null) {
            this.pool.getJSON().deleteOne(new Document("_id", wwwUser.getUuid().toString()));
        }
        this.pool.getJSON().insertOne(wwwUser.toDocument());
    }

    public void saveDataWWWUser(final UUID uuid, final WWWUser wwwUser) {
        this.pool.getJSON().findOneAndReplace(new Document("_id", uuid.toString()), wwwUser.toDocument());
    }

    public void saveAllWWWUsers() {
        for (final WWWUser wwwUser : rpgcore.getUserManager().getWWWUsers()) {
            this.saveDataWWWUser(wwwUser.getUuid(), wwwUser);
        }
    }

    // PUSTELNIK

    public Map<UUID, PustelnikUser> loadAllPustelnik() {
        Map<UUID, PustelnikUser> userMap = new HashMap<>();
        for (Document document : this.pool.getPustelnik().find()) {
            final PustelnikUser pustelnikUser = new PustelnikUser(document);
            userMap.put(pustelnikUser.getUuid(), pustelnikUser);
        }
        return userMap;
    }

    public void addDataPustelnik(final PustelnikUser pustelnikUser) {
        if (this.pool.getPustelnik().find(new Document("_id", pustelnikUser.getUuid().toString())).first() != null) {
            this.pool.getPustelnik().deleteOne(new Document("_id", pustelnikUser.getUuid().toString()));
        }
        this.pool.getPustelnik().insertOne(pustelnikUser.toDocument());
    }

    public void saveDataPustelnik(final UUID uuid, final PustelnikUser pustelnikUser) {
        this.pool.getPustelnik().findOneAndReplace(new Document("_id", uuid.toString()), pustelnikUser.toDocument());
    }

    public void saveAllPustelnik() {
        for (final PustelnikUser pustelnikUser : rpgcore.getPustelnikNPC().getPustelnikUsers()) {
            this.saveDataPustelnik(pustelnikUser.getUuid(), pustelnikUser);
        }
    }

    // BOSSY
    public BossyUser loadAllBossy() {
        final Document document = this.pool.getOther().find(new Document("_id", "bossy")).first();
        if (document == null) {
            final BossyUser bossyUser = new BossyUser();
            this.addDataBossy(bossyUser);
            return bossyUser;
        }
        return new BossyUser(document);
    }

    public void addDataBossy(final BossyUser bossyUser) {
        if (this.pool.getOther().find(new Document("_id", "bossy")).first() != null) {
            this.pool.getOther().deleteOne(new Document("_id", "bossy"));
        }
        this.pool.getOther().insertOne(bossyUser.toDocument());
    }

    public void saveDataBossy() {
        this.pool.getOther().findOneAndReplace(new Document("_id", "bossy"), rpgcore.getBossyManager().getBossyUser().toDocument());
    }

    // BOSSY 120-130
    public Map<UUID, MistrzYangUser> loadAllMistrzYang() {
        Map<UUID, MistrzYangUser> userMap = new HashMap<>();
        for (Document document : this.pool.getMistrzYang().find()) {
            final MistrzYangUser mistrzYangUser = new MistrzYangUser(document);
            userMap.put(mistrzYangUser.getUuid(), mistrzYangUser);
        }
        return userMap;
    }

    public void addDataMistrzYang(final MistrzYangUser mistrzYangUser) {
        if (this.pool.getMistrzYang().find(new Document("_id", mistrzYangUser.getUuid().toString())).first() != null) {
            this.pool.getMistrzYang().deleteOne(new Document("_id", mistrzYangUser.getUuid().toString()));
        }
        this.pool.getMistrzYang().insertOne(mistrzYangUser.toDocument());
    }

    public void saveDataMistrzYang(final UUID uuid, final MistrzYangUser mistrzYangUser) {
        this.pool.getMistrzYang().findOneAndReplace(new Document("_id", uuid.toString()), mistrzYangUser.toDocument());
    }

    public void saveAllMistrzYang() {
        for (final MistrzYangUser mistrzYangUser : rpgcore.getMistrzYangNPC().getMistrzYangUsers()) {
            this.saveDataMistrzYang(mistrzYangUser.getUuid(), mistrzYangUser);
        }
    }

    public Map<UUID, CzarownicaUser> loadAllCzarownica() {
        Map<UUID, CzarownicaUser> userMap = new HashMap<>();
        for (Document document : this.pool.getCzarownica().find()) {
            final CzarownicaUser czarownicaUser = new CzarownicaUser(document);
            userMap.put(czarownicaUser.getUuid(), czarownicaUser);
        }
        return userMap;
    }

    public void addDataCzarownica(final CzarownicaUser czarownicaUser) {
        if (this.pool.getCzarownica().find(new Document("_id", czarownicaUser.getUuid().toString())).first() != null) {
            this.pool.getCzarownica().deleteOne(new Document("_id", czarownicaUser.getUuid().toString()));
        }
        this.pool.getCzarownica().insertOne(czarownicaUser.toDocument());
    }

    public void saveDataCzarownica(final UUID uuid, final CzarownicaUser czarownicaUser) {
        this.pool.getCzarownica().findOneAndReplace(new Document("_id", uuid.toString()), czarownicaUser.toDocument());
    }

    public void saveAllCzarownica() {
        for (final CzarownicaUser czarownicaUser : rpgcore.getCzarownicaNPC().getCzarownicaUsers()) {
            this.saveDataCzarownica(czarownicaUser.getUuid(), czarownicaUser);
        }
    }



    public void onDisable() {
        pool.closePool();
    }


}
