package rpg.rpgcore.database;

import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.bao.objects.BaoObject;
import rpg.rpgcore.bonuses.Bonuses;
import rpg.rpgcore.bossy.effects.PrzekletyCzarnoksieznik.PrzekletyCzarnoksieznikUser;
import rpg.rpgcore.dungeons.maps.tajemniczePiaski.objects.RdzenPiaszczystychWydm;
import rpg.rpgcore.events.headHuntEvent.objects.HeadHuntPricePoolUser;
import rpg.rpgcore.events.headHuntEvent.objects.HeadHuntUser;
import rpg.rpgcore.klasy.objects.Klasa;
import rpg.rpgcore.newTarg.objects.Targ;
import rpg.rpgcore.npc.alchemik.objects.AlchemikUser;
import rpg.rpgcore.npc.czarownica.objects.CzarownicaUser;
import rpg.rpgcore.npc.duszolog.objects.DuszologUser;
import rpg.rpgcore.npc.gornik.objects.GornikUser;
import rpg.rpgcore.npc.gornik.ore.objects.Ore;
import rpg.rpgcore.npc.mistrz_yang.objects.MistrzYangUser;
import rpg.rpgcore.bossy.objects.BossyUser;
import rpg.rpgcore.chat.objects.ChatUser;
import rpg.rpgcore.commands.admin.serverwhitelist.objects.SerwerWhiteList;
import rpg.rpgcore.dodatki.objects.DodatkiUser;
import rpg.rpgcore.dodatki.akcesoriaD.objects.AkcesoriaDodatUser;
import rpg.rpgcore.dodatki.akcesoriaP.objects.AkcesoriaPodstUser;
import rpg.rpgcore.dodatki.bony.objects.BonyUser;
import rpg.rpgcore.guilds.objects.GuildObject;
import rpg.rpgcore.kociolki.KociolkiUser;
import rpg.rpgcore.lvl.artefaktyZaLvL.ArtefaktyZaLvl;
import rpg.rpgcore.managers.disabled.Disabled;
import rpg.rpgcore.metiny.Metiny;
import rpg.rpgcore.npc.handlarz.objects.HandlarzUser;
import rpg.rpgcore.npc.kolekcjoner.objects.KolekcjonerObject;
import rpg.rpgcore.npc.lesnik.objects.LesnikObject;
import rpg.rpgcore.npc.lowca.objects.LowcaObject;
import rpg.rpgcore.npc.magazynier.objects.MagazynierUser;
import rpg.rpgcore.npc.medrzec.objects.MedrzecUser;
import rpg.rpgcore.npc.metinolog.objects.MetinologObject;
import rpg.rpgcore.npc.mrozny_stroz.objects.MroznyStrozUser;
import rpg.rpgcore.npc.nereus.objects.NereusUser;
import rpg.rpgcore.npc.przyrodnik.objects.PrzyrodnikObject;
import rpg.rpgcore.npc.pustelnik.objects.PustelnikUser;
import rpg.rpgcore.npc.rybak.objects.RybakUser;
import rpg.rpgcore.npc.summonblade.objects.SummonbladeUser;
import rpg.rpgcore.npc.wyslannik.objects.WyslannikUser;
import rpg.rpgcore.osiagniecia.objects.OsUser;
import rpg.rpgcore.pets.objects.PetObject;
import rpg.rpgcore.pets.objects.UserPets;
import rpg.rpgcore.ranks.types.RankTypePlayer;
import rpg.rpgcore.server.ServerUser;
import rpg.rpgcore.spawn.SpawnManager;
import rpg.rpgcore.user.User;
import rpg.rpgcore.user.WWWUser;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.wyszkolenie.objects.WyszkolenieUser;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

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
            this.addDataLesnik(rpgcore.getLesnikNPC().find(uuid));
            this.addDataUserPets(rpgcore.getPetyManager().findUserPets(uuid));
            this.addDataActivePets(rpgcore.getPetyManager().findActivePet(uuid));

        }
    }
    public void clearDatabase() {
        final List<Document> toRemove = new ArrayList<>();
        for (final Document doc : this.pool.getGracze().find()) {
            final UUID uuid = UUID.fromString(doc.getString("_id"));
            if (uuid.toString().equals("b3efaecc-53f1-46b6-846c-5e455a267b8b") || uuid.toString().equals("d4d989aa-2377-4048-a9d9-10d703de9f93")
                    || uuid.toString().equals("2eea1b3b-838c-4d5c-bd3e-a2b9b63653de")) continue;
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
            if (pool.getHandlarz().find(new Document("_id", uuid.toString())).first() != null) {
                pool.getHandlarz().deleteOne(new Document("_id", uuid.toString()));
            }
            if (pool.getKociolki().find(new Document("_id", uuid.toString())).first() != null) {
                pool.getKociolki().deleteOne(new Document("_id", uuid.toString()));
            }
            if (pool.getWyszkolenie().find(new Document("_id", uuid.toString())).first() != null) {
                pool.getWyszkolenie().deleteOne(new Document("_id", uuid.toString()));
            }
            if (pool.getJSON().find(new Document("_id", uuid.toString())).first() != null) {
                pool.getJSON().deleteOne(new Document("_id", uuid.toString()));
            }
            if (pool.getPustelnik().find(new Document("_id", uuid.toString())).first() != null) {
                pool.getPustelnik().deleteOne(new Document("_id", uuid.toString()));
            }
            if (pool.getMistrzYang().find(new Document("_id", uuid.toString())).first() != null) {
                pool.getMistrzYang().deleteOne(new Document("_id", uuid.toString()));
            }
            if (pool.getCzarownica().find(new Document("_id", uuid.toString())).first() != null) {
                pool.getCzarownica().deleteOne(new Document("_id", uuid.toString()));
            }
            if (pool.getWyslannik().find(new Document("_id", uuid.toString())).first() != null) {
                pool.getCzarownica().deleteOne(new Document("_id", uuid.toString()));
            }
            if (pool.getPrzekletyCzarnoksieznikEffect().find(new Document("_id", uuid.toString())).first() != null) {
                pool.getPrzekletyCzarnoksieznikEffect().deleteOne(new Document("_id", uuid.toString()));
            }
            if (pool.getMroznyStroz().find(new Document("_id", uuid.toString())).first() != null) {
                pool.getMroznyStroz().deleteOne(new Document("_id", uuid.toString()));
            }
            if (pool.getSummonblade().find(new Document("_id", uuid.toString())).first() != null) {
                pool.getSummonblade().deleteOne(new Document("_id", uuid.toString()));
            }
            if (pool.getAlchemik().find(new Document("_id", uuid.toString())).first() != null) {
                pool.getAlchemik().deleteOne(new Document("_id", uuid.toString()));
            }
            if (pool.getNereus().find(new Document("_id", uuid.toString())).first() != null) {
                pool.getNereus().deleteOne(new Document("_id", uuid.toString()));
            }
            if (pool.getHeadHuntEvent().find(new Document("_id", uuid.toString())).first() != null) {
                pool.getHeadHuntEvent().deleteOne(new Document("_id", uuid.toString()));
            }
            toRemove.add(doc);
            rpgcore.getBackupMongoManager().getPool().getDatabase().getCollection(uuid.toString().replace("-", "_")).drop();
        }
        for (Document doc : toRemove) {
            pool.getGracze().deleteOne(doc);
        }
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
                final RybakUser user = new RybakUser(uuid);
                this.addDataRybak(user);
                rpgcore.getRybakNPC().add(user);
            }

            if (this.pool.getDodatki().find(new Document("_id", uuid.toString())).first() == null) {
                final DodatkiUser user = new DodatkiUser(uuid);
                this.addDataDodatki(user);
                rpgcore.getDodatkiManager().add(user);
            }


            if (pool.getMetinolog().find(new Document("_id", uuid.toString())).first() == null) {
                final MetinologObject user = new MetinologObject(uuid);
                this.addDataMetinolog(user);
                rpgcore.getMetinologNPC().add(user);
            }
            if (pool.getKlasy().find(new Document("_id", uuid.toString())).first() == null) {
                final Klasa user = new Klasa(uuid);
                this.addDataKlasy(user);
                rpgcore.getKlasyManager().add(user);
            }
            if (pool.getMedrzec().find(new Document("_id", uuid.toString())).first() == null) {
                final MedrzecUser user = new MedrzecUser(uuid);
                this.addDataMedrzec(user);
                rpgcore.getMedrzecNPC().add(user);
            }
            if (pool.getGornik().find(new Document("_id", uuid.toString())).first() == null) {
                final GornikUser user = new GornikUser(uuid);
                this.addDataGornik(user);
                rpgcore.getGornikNPC().add(user);
            }
            if (pool.getDuszolog().find(new Document("_id", uuid.toString())).first() == null) {
                final DuszologUser user = new DuszologUser(uuid);
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
            if (pool.getWyslannik().find(new Document("_id", uuid.toString())).first() == null) {
                final WyslannikUser wyslannikUser = new WyslannikUser(uuid);
                this.addDataWyslannik(wyslannikUser);
                rpgcore.getWyslannikNPC().add(wyslannikUser);
            }
            // TUTAJ ROBISZ ZABEZPIECZENIE JAKBY SIE COS WYJEBALO NA PLECY I NIE STWORZYLO USERA W BAZIE JAK GRACZ WBIL NA SERWER
            // WIEC JESLI NIE MA JEGO DOKUMENTU W KOLEKCJI TO GO TWORZY I DODAJE DO PAMIECI PODRECZNEJ SERWERA
            // TEZ BARDZO WAZEN I NIE ZAPOMNIEC O TYM.
            /*if (pool.getPrzykladowyNPC().find(new Document("_id", uuid.toString())).first() == null) {
                final TestUser user = new TestUser(uuid);
                this.addDataTest(user);
                rpgcore.getTestNPC().add(user);
            }*/
            if (pool.getPrzekletyCzarnoksieznikEffect().find(new Document("_id", uuid.toString())).first() == null) {
                final PrzekletyCzarnoksieznikUser przekletyCzarnoksieznikUser = new PrzekletyCzarnoksieznikUser(uuid);
                this.addDataPrzekletyCzarnoksieznikEffect(przekletyCzarnoksieznikUser);
                rpgcore.getPrzekletyCzarnoksieznikBossManager().add(przekletyCzarnoksieznikUser);
            }
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
            if (pool.getTargi().find(new Document("_id", uuid.toString())).first() == null) {
                final Targ user = new Targ(uuid);
                addDataTarg(user);
                rpgcore.getNewTargManager().add(user);
            }
            if (pool.getMroznyStroz().find(new Document("_id", uuid.toString())).first() == null) {
                final MroznyStrozUser user = new MroznyStrozUser(uuid);
                addDataMroznyStroz(user);
                rpgcore.getMroznyStrozNPC().add(user);
            }
            if (pool.getSummonblade().find(new Document("_id", uuid.toString())).first() == null) {
                final SummonbladeUser user = new SummonbladeUser(uuid);
                addDataSummonblade(user);
                rpgcore.getSummonbladeNPC().add(user);
            }
            if (pool.getAlchemik().find(new Document("_id", uuid.toString())).first() == null) {
                final AlchemikUser user = new AlchemikUser(uuid);
                addDataAlchemik(user);
                rpgcore.getAlchemikNPC().add(user);
            }
            if (pool.getNereus().find(new Document("_id", uuid.toString())).first() == null) {
                final NereusUser user = new NereusUser(uuid);
                addDataNereus(user);
                rpgcore.getNereusNPC().add(user);
            }
            if (pool.getHeadHuntEvent().find(new Document("_id", uuid.toString())).first() == null) {
                final HeadHuntUser user = new HeadHuntUser(uuid);

                final Location glowka1 = rpgcore.getEventManager().getHeadHuntManager().getRandom(uuid, 1);
                user.setGlowka1(glowka1);
                user.getGlowka1PrevLocations().add(glowka1);

                //...

                addDataHeadHuntEvent(user);
                rpgcore.getEventManager().getHeadHuntManager().add(user);
            }
        }
        if (pool.getOther().find(new Document("_id", "dodatkowyExp")).first() == null) {
            final ServerUser user = new ServerUser("dodatkowyExp");
            addOtherData(user);
            rpgcore.getServerManager().add(user);
        }
    }


    public void createPlayer(final Player player, final UUID uuid, final String nick, final ItemStack[] inventory, final ItemStack[] armor, final ItemStack[] enderchest) {

        final User user = new User(uuid, nick);
        user.getInventoriesUser().setInventory(Utils.itemStackArrayToBase64(inventory));
        user.getInventoriesUser().setArmor(Utils.itemStackArrayToBase64(armor));
        user.getInventoriesUser().setEnderchest(Utils.itemStackArrayToBase64(enderchest));
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

        final Klasa klasa = new Klasa(uuid);
        this.addDataKlasy(klasa);
        rpgcore.getKlasyManager().add(klasa);

        final DuszologUser duszologUser = new DuszologUser(uuid);
        this.addDataDuszolog(duszologUser);
        rpgcore.getDuszologNPC().add(duszologUser);

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

        final RybakUser rybakUser = new RybakUser(uuid);
        this.addDataRybak(rybakUser);
        rpgcore.getRybakNPC().add(rybakUser);

        final GornikUser gornikUser = new GornikUser(uuid);
        this.addDataGornik(gornikUser);
        rpgcore.getGornikNPC().add(gornikUser);

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

        final Targ targ = new Targ(uuid);
        this.addDataTarg(targ);
        rpgcore.getNewTargManager().add(targ);

        final PrzekletyCzarnoksieznikUser przekletyCzarnoksieznikUser = new PrzekletyCzarnoksieznikUser(uuid);
        this.addDataPrzekletyCzarnoksieznikEffect(przekletyCzarnoksieznikUser);
        rpgcore.getPrzekletyCzarnoksieznikBossManager().add(przekletyCzarnoksieznikUser);

        final WyslannikUser wyslannikUser = new WyslannikUser(uuid);
        this.addDataWyslannik(wyslannikUser);
        rpgcore.getWyslannikNPC().add(wyslannikUser);

        final MroznyStrozUser mroznyStrozUser = new MroznyStrozUser(uuid);
        this.addDataMroznyStroz(mroznyStrozUser);
        rpgcore.getMroznyStrozNPC().add(mroznyStrozUser);

        final SummonbladeUser summonbladeUser = new SummonbladeUser(uuid);
        this.addDataSummonblade(summonbladeUser);
        rpgcore.getSummonbladeNPC().add(summonbladeUser);

        final AlchemikUser alchemikUser = new AlchemikUser(uuid);
        this.addDataAlchemik(alchemikUser);
        rpgcore.getAlchemikNPC().add(alchemikUser);


        final NereusUser nereusUser = new NereusUser(uuid);
        this.addDataNereus(nereusUser);
        rpgcore.getNereusNPC().add(nereusUser);

        final HeadHuntUser headHuntUser = new HeadHuntUser(uuid);

        final Location glowka1 = rpgcore.getEventManager().getHeadHuntManager().getRandom(uuid, 1);
        headHuntUser.setGlowka1(glowka1);
        headHuntUser.getGlowka1PrevLocations().add(glowka1);

        //...

        this.addDataHeadHuntEvent(headHuntUser);
        rpgcore.getEventManager().getHeadHuntManager().add(headHuntUser);


        // TUTAJ TWORZYSZ USERA JAK NOWY GRACZ WEJDZIE NA SERWER
        // TEZ NIE ZAPOMNIEC BO NIE BEDZIE DZIALAL NPC
        // PATRZ loadALL() DALEJ

        /*final TestUser testUser = new TestUser(uuid);
        this.addDataTest(testUser);
        rpgcore.getTestNPC().add(testUser);*/

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
            }

            final WWWUser wwwUser = rpgcore.getUserManager().findWWWUser(uuid);
//            wwwUser.setArmorJSON(Utils.itemStackArrayToJSON(armor));
//            wwwUser.setInventoryJSON(Utils.itemStackArrayToJSON(inventory));
//            wwwUser.setEnderchestJSON(Utils.itemStackArrayToJSON(enderchest));
//
//            final DodatkiUser dodatkiUser = rpgcore.getDodatkiManager().find(uuid);
//            final BonyUser bonyUser = dodatkiUser.getBony();
//            final AkcesoriaPodstUser akcesoriaPodstUser = dodatkiUser.getAkcesoriaPodstawowe();
//            final AkcesoriaDodatUser akcesoriaDodatUser = dodatkiUser.getAkcesoriaDodatkowe();
//
//            wwwUser.setBonyJSON(Utils.itemStackArrayToJSON(Arrays.asList(
//                    bonyUser.getDmg5(),
//                    bonyUser.getDmg10(),
//                    bonyUser.getDmg15(),
//                    bonyUser.getDef5(),
//                    bonyUser.getDef10(),
//                    bonyUser.getDef15(),
//                    bonyUser.getKryt5(),
//                    bonyUser.getKryt10(),
//                    bonyUser.getKryt15(),
//                    bonyUser.getWzmkryt10(),
//                    bonyUser.getBlok20(),
//                    bonyUser.getPrzeszywka20(),
//                    bonyUser.getPredkosc25(),
//                    bonyUser.getPredkosc50(),
//                    bonyUser.getHp10(),
//                    bonyUser.getHp20(),
//                    bonyUser.getHp35(),
//                    bonyUser.getDmgMetiny()
//            )));
//
//            wwwUser.setAkcesoriaPodstawoweJSON(Utils.itemStackArrayToJSON(Arrays.asList(
//                    akcesoriaPodstUser.getTarcza(),
//                    akcesoriaPodstUser.getNaszyjnik(),
//                    akcesoriaPodstUser.getKolczyki(),
//                    akcesoriaPodstUser.getPierscien(),
//                    akcesoriaPodstUser.getDiadem()
//            )));
//
//            wwwUser.setAkcesoriaDodatkoweJSON(Utils.itemStackArrayToJSON(Arrays.asList(
//                    akcesoriaDodatUser.getSzarfa(),
//                    akcesoriaDodatUser.getPas(),
//                    akcesoriaDodatUser.getMedalion(),
//                    akcesoriaDodatUser.getEnergia()
//            )));
//
//            wwwUser.setUserPetyJSON(Utils.itemStackArrayToJSON(rpgcore.getPetyManager().findUserPets(uuid).getPety()));
//            wwwUser.setActivePet(Utils.itemStackToJSON(rpgcore.getPetyManager().findActivePet(uuid).getPet().getItem()));
//
//            final MagazynierUser magazynierUser = rpgcore.getMagazynierNPC().find(uuid);
//            wwwUser.setMagazyn1JSON(Utils.itemStackArrayToJSON(Utils.itemStackArrayFromBase64(magazynierUser.getMagazyn1())));
//            wwwUser.setMagazyn2JSON(Utils.itemStackArrayToJSON(Utils.itemStackArrayFromBase64(magazynierUser.getMagazyn2())));
//            wwwUser.setMagazyn3JSON(Utils.itemStackArrayToJSON(Utils.itemStackArrayFromBase64(magazynierUser.getMagazyn3())));
//            wwwUser.setMagazyn4JSON(Utils.itemStackArrayToJSON(Utils.itemStackArrayFromBase64(magazynierUser.getMagazyn4())));
//            wwwUser.setMagazyn5JSON(Utils.itemStackArrayToJSON(Utils.itemStackArrayFromBase64(magazynierUser.getMagazyn5())));


            this.saveDataUser(uuid, rpgcore.getUserManager().find(uuid));
            this.saveDataDodatki(uuid, rpgcore.getDodatkiManager().find(uuid));
            this.saveDataBonuses(uuid, rpgcore.getBonusesManager().find(uuid));
            this.saveDataBao(uuid, rpgcore.getBaoManager().find(uuid));
            this.saveDataKlasy(uuid, rpgcore.getKlasyManager().find(uuid));
            this.saveDataDuszolog(uuid, rpgcore.getDuszologNPC().find(uuid));
            this.saveDataKolekcjoner(uuid, rpgcore.getKolekcjonerNPC().find(uuid));
            this.saveDataMedrzec(uuid, rpgcore.getMedrzecNPC().find(uuid));
            this.saveDataMetinolog(uuid, rpgcore.getMetinologNPC().find(uuid));
            this.saveDataPrzyrodnik(uuid, rpgcore.getPrzyrodnikNPC().find(uuid));
            this.saveDataRybak(uuid, rpgcore.getRybakNPC().find(uuid));
            this.saveDataOs(uuid, rpgcore.getOsManager().find(uuid));
            this.saveDataMagazynier(uuid, rpgcore.getMagazynierNPC().find(uuid));
            this.saveDataLowca(uuid, rpgcore.getLowcaNPC().find(uuid));
            this.saveDataKociolki(uuid, rpgcore.getKociolkiManager().find(uuid));

            this.saveDataLesnik(uuid, rpgcore.getLesnikNPC().find(uuid));
            this.saveDataUserPets(uuid, rpgcore.getPetyManager().findUserPets(uuid));
            this.saveDataActivePets(uuid, rpgcore.getPetyManager().findActivePet(uuid));
            this.saveDataGornik(uuid, rpgcore.getGornikNPC().find(uuid));
            this.saveDataChatUsers(uuid, rpgcore.getChatManager().find(uuid));
            this.saveDataTarg(uuid, rpgcore.getNewTargManager().find(uuid));
            this.saveDataHandlarz(uuid, rpgcore.getHandlarzNPC().find(uuid));
            this.saveDataWyszkolenie(uuid, rpgcore.getWyszkolenieManager().find(uuid));
            this.saveDataWWWUser(uuid, rpgcore.getUserManager().findWWWUser(uuid));
            this.saveDataPustelnik(uuid, rpgcore.getPustelnikNPC().find(uuid));
            this.saveDataMistrzYang(uuid, rpgcore.getMistrzYangNPC().find(uuid));
            this.saveDataCzarownica(uuid, rpgcore.getCzarownicaNPC().find(uuid));
            this.saveDataPrzekletyCzarnoksieznikEffect(uuid, rpgcore.getPrzekletyCzarnoksieznikBossManager().find(uuid));
            this.saveDataWyslannik(uuid, rpgcore.getWyslannikNPC().find(uuid));
            this.saveDataMroznyStroz(uuid, rpgcore.getMroznyStrozNPC().find(uuid));
            this.saveDataSummonblade(uuid, rpgcore.getSummonbladeNPC().find(uuid));
            this.saveDataAlchemik(uuid, rpgcore.getAlchemikNPC().find(uuid));
            this.saveDataNereus(uuid, rpgcore.getNereusNPC().find(uuid));
            this.saveDataHeadHuntEvent(uuid, rpgcore.getEventManager().getHeadHuntManager().find(uuid));
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

    public void savePlayer(final UUID uuid) {
        try {
            final long start = System.currentTimeMillis();
            final User user = rpgcore.getUserManager().find(uuid);



            if (!user.getRankPlayerUser().getRankType().equals(RankTypePlayer.GRACZ)) {
                if (user.getRankPlayerUser().getTime() != -1) {
                    if (user.getRankPlayerUser().getTime() <= System.currentTimeMillis()) {
                        user.getRankPlayerUser().setRank(RankTypePlayer.GRACZ);
                        user.getRankPlayerUser().setTime(0L);
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
                RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> {
                    RPGCORE.getInstance().getMongoManager().saveDataUser(user.getId(), user);
                    RPGCORE.getInstance().getMongoManager().saveDataBonuses(user.getId(), bonuses);
                });
            }

            final WWWUser wwwUser = rpgcore.getUserManager().findWWWUser(uuid);
            wwwUser.setArmorJSON(Utils.itemStackArrayToJSON(Utils.itemStackArrayFromBase64(user.getInventoriesUser().getArmor())));
            wwwUser.setInventoryJSON(Utils.itemStackArrayToJSON(Utils.itemStackArrayFromBase64(user.getInventoriesUser().getInventory())));
            wwwUser.setEnderchestJSON(Utils.itemStackArrayToJSON(Utils.itemStackArrayFromBase64(user.getInventoriesUser().getEnderchest())));

            final DodatkiUser dodatkiUser = rpgcore.getDodatkiManager().find(uuid);
            final BonyUser bonyUser = dodatkiUser.getBony();
            final AkcesoriaPodstUser akcesoriaPodstUser = dodatkiUser.getAkcesoriaPodstawowe();
            final AkcesoriaDodatUser akcesoriaDodatUser = dodatkiUser.getAkcesoriaDodatkowe();

            wwwUser.setBonyJSON(Utils.itemStackArrayToJSON(Arrays.asList(
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

            wwwUser.setAkcesoriaPodstawoweJSON(Utils.itemStackArrayToJSON(Arrays.asList(
                    akcesoriaPodstUser.getTarcza(),
                    akcesoriaPodstUser.getNaszyjnik(),
                    akcesoriaPodstUser.getKolczyki(),
                    akcesoriaPodstUser.getPierscien(),
                    akcesoriaPodstUser.getDiadem()
            )));

            wwwUser.setAkcesoriaDodatkoweJSON(Utils.itemStackArrayToJSON(Arrays.asList(
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
            this.saveDataKlasy(uuid, rpgcore.getKlasyManager().find(uuid));
            this.saveDataDuszolog(uuid, rpgcore.getDuszologNPC().find(uuid));
            this.saveDataKolekcjoner(uuid, rpgcore.getKolekcjonerNPC().find(uuid));
            this.saveDataMedrzec(uuid, rpgcore.getMedrzecNPC().find(uuid));
            this.saveDataMetinolog(uuid, rpgcore.getMetinologNPC().find(uuid));
            this.saveDataPrzyrodnik(uuid, rpgcore.getPrzyrodnikNPC().find(uuid));
            this.saveDataRybak(uuid, rpgcore.getRybakNPC().find(uuid));
            this.saveDataOs(uuid, rpgcore.getOsManager().find(uuid));
            this.saveDataMagazynier(uuid, magazynierUser);
            this.saveDataLowca(uuid, rpgcore.getLowcaNPC().find(uuid));
            this.saveDataKociolki(uuid, rpgcore.getKociolkiManager().find(uuid));

            this.saveDataLesnik(uuid, rpgcore.getLesnikNPC().find(uuid));
            this.saveDataUserPets(uuid, rpgcore.getPetyManager().findUserPets(uuid));
            this.saveDataActivePets(uuid, rpgcore.getPetyManager().findActivePet(uuid));
            this.saveDataGornik(uuid, rpgcore.getGornikNPC().find(uuid));
            this.saveDataChatUsers(uuid, rpgcore.getChatManager().find(uuid));
            this.saveDataTarg(uuid, rpgcore.getNewTargManager().find(uuid));
            this.saveDataHandlarz(uuid, rpgcore.getHandlarzNPC().find(uuid));
            this.saveDataWyszkolenie(uuid, rpgcore.getWyszkolenieManager().find(uuid));
            this.saveDataWWWUser(uuid, rpgcore.getUserManager().findWWWUser(uuid));
            this.saveDataPustelnik(uuid, rpgcore.getPustelnikNPC().find(uuid));
            this.saveDataMistrzYang(uuid, rpgcore.getMistrzYangNPC().find(uuid));
            this.saveDataCzarownica(uuid, rpgcore.getCzarownicaNPC().find(uuid));
            this.saveDataPrzekletyCzarnoksieznikEffect(uuid, rpgcore.getPrzekletyCzarnoksieznikBossManager().find(uuid));
            this.saveDataWyslannik(uuid, rpgcore.getWyslannikNPC().find(uuid));
            this.saveDataMroznyStroz(uuid, rpgcore.getMroznyStrozNPC().find(uuid));
            this.saveDataSummonblade(uuid, rpgcore.getSummonbladeNPC().find(uuid));
            this.saveDataAlchemik(uuid, rpgcore.getAlchemikNPC().find(uuid));
            this.saveDataNereus(uuid, rpgcore.getNereusNPC().find(uuid));
            this.saveDataHeadHuntEvent(uuid, rpgcore.getEventManager().getHeadHuntManager().find(uuid));
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
        Map<UUID, MetinologObject> metinolog = new HashMap<>();
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

    // KLASY

    public Map<UUID, Klasa> loadAllKlasy() {
        Map<UUID, Klasa> klasy = new HashMap<>();
        for (Document document : this.pool.getKlasy().find()) {
            Klasa klasa = new Klasa(document);
            klasy.put(klasa.getUuid(), klasa);
        }
        return klasy;
    }
    public void addDataKlasy(final Klasa klasa) {
        if (this.pool.getKlasy().find(new Document("_id", klasa.getUuid().toString())).first() != null) {
            this.pool.getKlasy().deleteOne(new Document("_id", klasa.getUuid().toString()));
        }
        this.pool.getKlasy().insertOne(klasa.toDocument());
    }

    public void saveDataKlasy(final UUID uuid, final Klasa klasa) {
        pool.getKlasy().findOneAndReplace(new Document("_id", uuid.toString()), klasa.toDocument());
    }

    public void saveAllKlasy() {
        for (Klasa klasa : rpgcore.getKlasyManager().getKlasy()) {
            this.saveDataKlasy(klasa.getUuid(), klasa);
        }
    }


    // MEDYK
    public Map<UUID, MedrzecUser> loadAllMedrzec() {
        Map<UUID, MedrzecUser> medrzec = new HashMap<>();
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
    public Map<UUID, GornikUser> loadAllGornik() {
        Map<UUID, GornikUser> gornik = new HashMap<>();
        for (final Document document : this.pool.getGornik().find()) {
            final GornikUser gornikUser = new GornikUser(document);
            gornik.put(gornikUser.getUuid(), gornikUser);
        }
        return gornik;
    }

    public void addDataGornik(final GornikUser gornikUser) {
        if (this.pool.getGornik().find(new Document("_id", gornikUser.getUuid().toString())).first() != null) {
            this.removeDataGornik(gornikUser);
        }
        this.pool.getGornik().insertOne(gornikUser.toDocument());
    }

    public void removeDataGornik(final GornikUser gornikUser) {
        this.pool.getGornik().deleteOne(new Document("_id", gornikUser.getUuid().toString()));
    }

    public void saveDataGornik(final UUID uuid, final GornikUser gornikUser) {
        this.pool.getGornik().findOneAndReplace(new Document("_id", uuid.toString()), gornikUser.toDocument());
    }

    public void saveAllGornik() {
        for (final GornikUser gornikUser : rpgcore.getGornikNPC().getGornikUsers()) {
            this.saveDataGornik(gornikUser.getUuid(), gornikUser);
        }
    }

    // ORE
    public Map<Location, Ore> loadAllOre() {
        final Map<Location, Ore> ores = new HashMap<>();
        for (final Document document : this.pool.getOreLocations().find()) {
            final Ore ore = new Ore(document);
            ores.put(ore.getLocation(), ore);
        }
        return ores;
    }

    public void addDataOre(final Ore ore) {
        if (this.pool.getOreLocations().find(new Document("_id", ore.getId())).first() != null) {
            this.removeDataOre(ore);
        }
        this.pool.getOreLocations().insertOne(ore.toDocument());
    }

    public void removeDataOre(final Ore ore) {
        this.pool.getOreLocations().deleteOne(new Document("_id", ore.getId()));
    }

    public void saveDataOre(final int id, final Ore ore) {
        if (this.pool.getOreLocations().find(new Document("_id", id)).first() == null) {
            this.addDataOre(ore);
            return;
        }
        this.pool.getOreLocations().findOneAndReplace(new Document("_id", id), ore.toDocument());
    }

    public void saveAllOre() {
        for (final Ore ore : rpgcore.getOreManager().getOres()) {
            ore.setCurrentHp(ore.getMaxHp());
            this.saveDataOre(ore.getId(), ore);
        }
    }


    // OsiagnieciaCommand
    public Map<UUID, OsUser> loadAllOs() {
        Map<UUID, OsUser> osUserMap = new HashMap<>();
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
        Map<UUID, KolekcjonerObject> kolekcjoner = new HashMap<>();
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
    public Map<UUID, DuszologUser> loadAllDuszolog() {
        Map<UUID, DuszologUser> duszolog = new HashMap<>();
        for (Document document : this.pool.getDuszolog().find()) {
            final DuszologUser duszologUser = new DuszologUser(document);
            duszolog.put(duszologUser.getUuid(), duszologUser);
        }
        return duszolog;
    }

    public void addDataDuszolog(final DuszologUser duszologUser) {
        if (this.pool.getDuszolog().find(new Document("_id", duszologUser.getUuid().toString())).first() != null) {
            this.pool.getDuszolog().deleteOne(new Document("_id", duszologUser.getUuid().toString()));
        }
        this.pool.getDuszolog().insertOne(duszologUser.toDocument());
    }

    public void saveDataDuszolog(final UUID id, final DuszologUser duszologUser) {
        this.pool.getDuszolog().findOneAndReplace(new Document("_id", id.toString()), duszologUser.toDocument());
    }

    public void saveAllDuszolog() {
        for (DuszologUser duszologUser : rpgcore.getDuszologNPC().getDuszologObject()) {
            this.saveDataDuszolog(duszologUser.getUuid(), duszologUser);
        }
    }


    // Przyrodnik
    public Map<UUID, PrzyrodnikObject> loadAllPrzyrodnik() {
        Map<UUID, PrzyrodnikObject> przyrodnik = new HashMap<>();
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

    // BAO ARMORSTANDS
    public Map<Integer, ArmorStand> loadAllBaoArmorStands() {
        Map<Integer, ArmorStand> armorStands = new HashMap<>();
        for (Document document : this.pool.getBaoArmorStands().find()) {
            final Location location = new Location(
                    Bukkit.getWorld(document.getString("world")),
                    document.getDouble("x"),
                    document.getDouble("y"),
                    document.getDouble("z"),
                    Float.parseFloat(String.valueOf(document.getDouble("yaw"))),
                    Float.parseFloat(String.valueOf(document.getDouble("pitch"))));
            final ArmorStand as = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
            final EulerAngle headPose = new EulerAngle(
                    Math.toRadians(document.getDouble("headX")),
                    Math.toRadians(document.getDouble("headY")),
                    Math.toRadians(document.getDouble("headZ")));
            final EulerAngle bodyPose = new EulerAngle(
                    Math.toRadians(document.getDouble("bodyX")),
                    Math.toRadians(document.getDouble("bodyY")),
                    Math.toRadians(document.getDouble("bodyZ")));
            final EulerAngle leftArmPose = new EulerAngle(
                    Math.toRadians(document.getDouble("leftArmX")),
                    Math.toRadians(document.getDouble("leftArmY")),
                    Math.toRadians(document.getDouble("leftArmZ")));
            final EulerAngle rightArmPose = new EulerAngle(
                    Math.toRadians(document.getDouble("rightArmX")),
                    Math.toRadians(document.getDouble("rightArmY")),
                    Math.toRadians(document.getDouble("rightArmZ")));
            final EulerAngle leftLegPose = new EulerAngle(
                    Math.toRadians(document.getDouble("leftLegX")),
                    Math.toRadians(document.getDouble("leftLegY")),
                    Math.toRadians(document.getDouble("leftLegZ")));
            final EulerAngle rightLegPose = new EulerAngle(
                    Math.toRadians(document.getDouble("rightLegX")),
                    Math.toRadians(document.getDouble("rightLegY")),
                    Math.toRadians(document.getDouble("rightLegZ")));
            as.setHeadPose(headPose);
            as.setBodyPose(bodyPose);
            as.setLeftArmPose(leftArmPose);
            as.setRightArmPose(rightArmPose);
            as.setLeftLegPose(leftLegPose);
            as.setRightLegPose(rightLegPose);
            as.setBasePlate(document.getBoolean("basePlate"));
            as.setArms(document.getBoolean("arms"));
            as.setCustomNameVisible(document.getBoolean("customNameVisible"));
            if (as.isCustomNameVisible()) as.setCustomName(Utils.format(document.getString("customName")));
            as.setSmall(document.getBoolean("small"));
            as.setVisible(document.getBoolean("visible"));
            as.setGravity(false);
            if (!document.getString("itemInHand").isEmpty()) as.setItemInHand(new ItemStack(Material.valueOf(document.getString("itemInHand"))));
            if (!document.getString("helmet").isEmpty()) {
                if (document.getString("helmet").equals("WOOD_STEP")) as.setHelmet(new ItemBuilder(Material.valueOf(document.getString("helmet")), 1, (short) 1).toItemStack().clone());
                else as.setHelmet(new ItemStack(Material.valueOf(document.getString("helmet"))));
            }
            if (!document.getString("chestplate").isEmpty()) as.setChestplate(new ItemStack(Material.valueOf(document.getString("chestplate"))));
            if (!document.getString("leggings").isEmpty()) as.setLeggings(new ItemStack(Material.valueOf(document.getString("leggings"))));
            if (!document.getString("boots").isEmpty()) as.setBoots(new ItemStack(Material.valueOf(document.getString("boots"))));
            as.setCanPickupItems(false);

            armorStands.put(document.getInteger("_id"), as);
        }
        return armorStands;
    }

    public void addDataBaoArmorStand(final int id, final ArmorStand as) {
        if (this.pool.getBaoArmorStands().find(new Document("_id", id)).first() != null) {
            System.out.println("BaoArmorStand o id: " + id + " już istnieje w bazie danych!");
            return;
        }
        this.pool.getBaoArmorStands().insertOne(rpgcore.getBaoManager().armorStandToDocument(id, as));
    }

    public void removeDataBaoArmorStand(final int id) {
        if (this.pool.getBaoArmorStands().find(new Document("_id", id)).first() == null) {
            System.out.println("BaoArmorStand o id: " + id + " nie istnieje w bazie danych!");
            return;
        }
        this.pool.getBaoArmorStands().deleteOne(new Document("_id", id));
    }

    public void saveDataBaoArmorStand(final int id, final ArmorStand as) {
        if (this.pool.getBaoArmorStands().find(new Document("_id", id)).first() == null) {
            this.addDataBaoArmorStand(id, as);
            return;
        }
        this.pool.getBaoArmorStands().findOneAndReplace(new Document("_id", id), rpgcore.getBaoManager().armorStandToDocument(id, as));
    }

    public void saveAllBaoArmorStands() {
        for (Map.Entry<Integer, ArmorStand> entry : rpgcore.getBaoManager().getArmorStands().entrySet()) {
            this.saveDataBaoArmorStand(entry.getKey(), entry.getValue());
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
    public Map<UUID, RybakUser> loadAllRybak() {
        Map<UUID, RybakUser> rybak = new HashMap<>();
        for (Document document : this.pool.getRybak().find()) {
            if (document.getString("_id").equals("asLocations")) continue;
            RybakUser rybakUser = new RybakUser(document);
            rybak.put(rybakUser.getUuid(), rybakUser);
        }
        return rybak;
    }

    public Map<Integer, ArmorStand> loadAllRybakArmorStands() {
        final Map<Integer, ArmorStand> armorStands = new HashMap<>();
        final Document doc = this.pool.getRybakArmorStands().find(new Document("_id", "asLocations")).first();
        assert doc != null;
        for (final String s : doc.keySet()) {
            if (s.equals("_id")) continue;
            final Document document = doc.get(s, Document.class);
            final Location location = new Location(
                    Bukkit.getWorld(document.getString("world")),
                    document.getDouble("x"),
                    document.getDouble("y"),
                    document.getDouble("z"),
                    Float.parseFloat(String.valueOf(document.getDouble("yaw"))),
                    Float.parseFloat(String.valueOf(document.getDouble("pitch"))));
            final ArmorStand as = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
            final EulerAngle headPose = new EulerAngle(
                    Math.toRadians(document.getDouble("headX")),
                    Math.toRadians(document.getDouble("headY")),
                    Math.toRadians(document.getDouble("headZ")));
            final EulerAngle bodyPose = new EulerAngle(
                    Math.toRadians(document.getDouble("bodyX")),
                    Math.toRadians(document.getDouble("bodyY")),
                    Math.toRadians(document.getDouble("bodyZ")));
            final EulerAngle leftArmPose = new EulerAngle(
                    Math.toRadians(document.getDouble("leftArmX")),
                    Math.toRadians(document.getDouble("leftArmY")),
                    Math.toRadians(document.getDouble("leftArmZ")));
            final EulerAngle rightArmPose = new EulerAngle(
                    Math.toRadians(document.getDouble("rightArmX")),
                    Math.toRadians(document.getDouble("rightArmY")),
                    Math.toRadians(document.getDouble("rightArmZ")));
            final EulerAngle leftLegPose = new EulerAngle(
                    Math.toRadians(document.getDouble("leftLegX")),
                    Math.toRadians(document.getDouble("leftLegY")),
                    Math.toRadians(document.getDouble("leftLegZ")));
            final EulerAngle rightLegPose = new EulerAngle(
                    Math.toRadians(document.getDouble("rightLegX")),
                    Math.toRadians(document.getDouble("rightLegY")),
                    Math.toRadians(document.getDouble("rightLegZ")));
            as.setHeadPose(headPose);
            as.setBodyPose(bodyPose);
            as.setLeftArmPose(leftArmPose);
            as.setRightArmPose(rightArmPose);
            as.setLeftLegPose(leftLegPose);
            as.setRightLegPose(rightLegPose);
            as.setBasePlate(document.getBoolean("basePlate"));
            as.setArms(document.getBoolean("arms"));
            as.setCustomNameVisible(document.getBoolean("customNameVisible"));
            if (as.isCustomNameVisible()) as.setCustomName(Utils.format(document.getString("customName")));
            as.setSmall(document.getBoolean("small"));
            as.setVisible(document.getBoolean("visible"));
            as.setGravity(false);
            if (!document.getString("itemInHand").isEmpty())
                as.setItemInHand(new ItemStack(Material.valueOf(document.getString("itemInHand"))));
            if (!document.getString("helmet").isEmpty()) {
                if (document.getString("helmet").equals("WOOD_STEP"))
                    as.setHelmet(new ItemBuilder(Material.valueOf(document.getString("helmet")), 1, (short) 1).toItemStack().clone());
                else as.setHelmet(new ItemStack(Material.valueOf(document.getString("helmet"))));
            }
            if (!document.getString("chestplate").isEmpty())
                as.setChestplate(new ItemStack(Material.valueOf(document.getString("chestplate"))));
            if (!document.getString("leggings").isEmpty())
                as.setLeggings(new ItemStack(Material.valueOf(document.getString("leggings"))));
            if (!document.getString("boots").isEmpty())
                as.setBoots(new ItemStack(Material.valueOf(document.getString("boots"))));
            as.setCanPickupItems(false);

            armorStands.put(document.getInteger("_id"), as);
        }

        return armorStands;
    }

    public void addDataRybak(final RybakUser rybakUser) {
        if (this.pool.getRybak().find(new Document("_id", rybakUser.getUuid().toString())).first() != null) {
            this.pool.getRybak().deleteOne(new Document("_id", rybakUser.getUuid().toString()));
        }
        this.pool.getRybak().insertOne(rybakUser.toDocument());
    }

    public void saveDataRybak(final UUID id, final RybakUser rybakUser) {
        this.pool.getRybak().findOneAndReplace(new Document("_id", id.toString()), rybakUser.toDocument());
    }

    public void saveAllRybak() {
        for (RybakUser rybakUser : rpgcore.getRybakNPC().getRybakObjects()) {
            this.saveDataRybak(rybakUser.getUuid(), rybakUser);
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

    // TARGI
    public Map<UUID, Targ> loadAllTarg() {
        Map<UUID, Targ> userMap = new HashMap<>();
        for (Document document : this.pool.getTargi().find()) {
            final Targ targ = new Targ(document);
            userMap.put(targ.getUuid(), targ);
        }
        return userMap;
    }

    public void addDataTarg(final Targ targ) {
        if (this.pool.getTargi().find(new Document("_id", targ.getUuid().toString())).first() != null) {
            this.pool.getTargi().deleteOne(new Document("_id", targ.getUuid().toString()));
        }
        this.pool.getTargi().insertOne(targ.toDocument());
    }

    public void saveDataTarg(final UUID uuid, final Targ targ) {
        this.pool.getTargi().findOneAndReplace(new Document("_id", uuid.toString()), targ.toDocument());
    }

    public void saveAllTarg() {
        for (final Targ targ : rpgcore.getNewTargManager().getTargs()) {
            this.saveDataTarg(targ.getUuid(), targ);
        }
    }

    // BOSSY EFFECTS
    // PRZEKELTY CZARNOKSIEZNIK

    public Map<UUID, PrzekletyCzarnoksieznikUser> loadAllPrzekletyCzarnoksieznikUser() {
        Map<UUID, PrzekletyCzarnoksieznikUser> userMap = new HashMap<>();
        for (Document document : this.pool.getPrzekletyCzarnoksieznikEffect().find()) {
            final PrzekletyCzarnoksieznikUser przekletyCzarnoksieznikUser = new PrzekletyCzarnoksieznikUser(document);
            userMap.put(przekletyCzarnoksieznikUser.getUuid(), przekletyCzarnoksieznikUser);
        }
        return userMap;
    }
    public void addDataPrzekletyCzarnoksieznikEffect(final PrzekletyCzarnoksieznikUser przekletyCzarnoksieznikUser) {
        if (this.pool.getPrzekletyCzarnoksieznikEffect().find(new Document("_id", przekletyCzarnoksieznikUser.getUuid().toString())).first() != null) {
            this.pool.getPrzekletyCzarnoksieznikEffect().deleteOne(new Document("_id", przekletyCzarnoksieznikUser.getUuid().toString()));
        }
        this.pool.getPrzekletyCzarnoksieznikEffect().insertOne(przekletyCzarnoksieznikUser.toDocument());
    }
    public void saveDataPrzekletyCzarnoksieznikEffect(final UUID uuid, final PrzekletyCzarnoksieznikUser przekletyCzarnoksieznikUser) {
        this.pool.getPrzekletyCzarnoksieznikEffect().findOneAndReplace(new Document("_id", uuid.toString()), przekletyCzarnoksieznikUser.toDocument());
    }
    public void saveAllPrzekletyCzarnoksieznikEffect() {
        for (final PrzekletyCzarnoksieznikUser przekletyCzarnoksieznikUser : rpgcore.getPrzekletyCzarnoksieznikBossManager().getPrzekletyCzarnoksieznikUser()) {
            this.saveDataPrzekletyCzarnoksieznikEffect(przekletyCzarnoksieznikUser.getUuid(), przekletyCzarnoksieznikUser);
        }
    }

    public Map<UUID, WyslannikUser> loadAllWyslannik() {
        Map<UUID, WyslannikUser> userMap = new HashMap<>();
        for (Document document : this.pool.getWyslannik().find()) {
            final WyslannikUser wyslannikUser = new WyslannikUser(document);
            userMap.put(wyslannikUser.getUuid(), wyslannikUser);
        }
        return userMap;
    }
    public void addDataWyslannik(final WyslannikUser wyslannikUser) {
        if (this.pool.getWyslannik().find(new Document("_id", wyslannikUser.getUuid().toString())).first() != null) {
            this.pool.getWyslannik().deleteOne(new Document("_id", wyslannikUser.getUuid().toString()));
        }
        this.pool.getWyslannik().insertOne(wyslannikUser.toDocument());
    }
    public void saveDataWyslannik(final UUID uuid, final WyslannikUser wyslannikUser) {
        this.pool.getWyslannik().findOneAndReplace(new Document("_id", uuid.toString()), wyslannikUser.toDocument());
    }
    public void saveAllWyslannik() {
        for (final WyslannikUser wyslannikUser : rpgcore.getWyslannikNPC().getWyslannikUser()) {
            this.saveDataWyslannik(wyslannikUser.getUuid(), wyslannikUser);
        }
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

    public Map<UUID, List<UUID>> loadAllTworcy() {
        Map<UUID, List<UUID>> userMap = new HashMap<>();
        final Document document = this.pool.getTworcy().find(new Document("_id", "tworcy")).first();
        if (document == null) {
            this.pool.getTworcy().insertOne(new Document("_id", "tworcy"));
            return userMap;
        }
        for (final String key : document.keySet()) {
            if (key.equals("_id")) continue;
            userMap.put(UUID.fromString(key), document.getList(key, String.class).stream().map(UUID::fromString).collect(Collectors.toList()));
        }
        return userMap;
    }

    public Map<Location, RdzenPiaszczystychWydm> loadAllRdzeniePiaszczystychWydm() {
        final Map<Location, RdzenPiaszczystychWydm> rdzenie = new HashMap<>();
        final Document document = this.pool.getDungeons().find(new Document("_id", "dungeon80-90")).first();
        if (document == null) return rdzenie;
        for (final String key : document.keySet()) {
            if (key.equals("_id")) continue;
            final Document rdzenDocument = document.get(key, Document.class);
            final RdzenPiaszczystychWydm rdzen = new RdzenPiaszczystychWydm(rdzenDocument);
            rdzenie.put(Utils.locationFromString(rdzenDocument.getString("hologramLocation")), rdzen);
        }
        return rdzenie;
    }

    public void addDataDungeons(final Document document) {
        if (this.pool.getDungeons().find(new Document("_id", document.getString("_id"))).first() != null) {
            this.pool.getDungeons().deleteOne(new Document("_id", document.getString("_id")));
        }
        this.pool.getDungeons().insertOne(document);
    }

    public Map<UUID, MroznyStrozUser> loadAllMroznyStroz() {
        Map<UUID, MroznyStrozUser> userMap = new HashMap<>();
        for (Document document : this.pool.getMroznyStroz().find()) {
            final MroznyStrozUser mroznyStrozUser = new MroznyStrozUser(document);
            userMap.put(mroznyStrozUser.getUuid(), mroznyStrozUser);
        }
        return userMap;
    }

    public void addDataMroznyStroz(final MroznyStrozUser mroznyStrozUser) {
        if (this.pool.getMroznyStroz().find(new Document("_id", mroznyStrozUser.getUuid().toString())).first() != null) {
            this.pool.getMroznyStroz().deleteOne(new Document("_id", mroznyStrozUser.getUuid().toString()));
        }
        this.pool.getMroznyStroz().insertOne(mroznyStrozUser.toDocument());
    }

    public void saveDataMroznyStroz(final UUID uuid, final MroznyStrozUser mroznyStrozUser) {
        this.pool.getMroznyStroz().findOneAndReplace(new Document("_id", uuid.toString()), mroznyStrozUser.toDocument());
    }

    public void saveAllMroznyStroz() {
        for (final MroznyStrozUser mroznyStrozUser : rpgcore.getMroznyStrozNPC().getMroznyStrozUsers()) {
            this.saveDataMroznyStroz(mroznyStrozUser.getUuid(), mroznyStrozUser);
        }
    }

    public Map<UUID, SummonbladeUser> loadAllSummonblade() {
        Map<UUID, SummonbladeUser> userMap = new HashMap<>();
        for (Document document : this.pool.getSummonblade().find()) {
            final SummonbladeUser summonbladeUser = new SummonbladeUser(document);
            userMap.put(summonbladeUser.getUuid(), summonbladeUser);
        }
        return userMap;
    }

    public void addDataSummonblade(final SummonbladeUser summonbladeUser) {
        if (this.pool.getSummonblade().find(new Document("_id", summonbladeUser.getUuid().toString())).first() != null) {
            this.pool.getSummonblade().deleteOne(new Document("_id", summonbladeUser.getUuid().toString()));
        }
        this.pool.getSummonblade().insertOne(summonbladeUser.toDocument());
    }

    public void saveDataSummonblade(final UUID uuid, final SummonbladeUser summonbladeUser) {
        this.pool.getSummonblade().findOneAndReplace(new Document("_id", uuid.toString()), summonbladeUser.toDocument());
    }

    public void saveAllSummonblade() {
        for (final SummonbladeUser summonbladeUser : rpgcore.getSummonbladeNPC().getSummonbladeUsers()) {
            this.saveDataSummonblade(summonbladeUser.getUuid(), summonbladeUser);
        }
    }

    public Map<UUID, AlchemikUser> loadAllAlchemik() {
        Map<UUID, AlchemikUser> userMap = new HashMap<>();
        for (Document document : this.pool.getAlchemik().find()) {
            final AlchemikUser alchemikUser = new AlchemikUser(document);
            userMap.put(alchemikUser.getUuid(), alchemikUser);
        }
        return userMap;
    }

    public void addDataAlchemik(final AlchemikUser alchemikUser) {
        if (this.pool.getAlchemik().find(new Document("_id", alchemikUser.getUuid().toString())).first() != null) {
            this.pool.getAlchemik().deleteOne(new Document("_id", alchemikUser.getUuid().toString()));
        }
        this.pool.getAlchemik().insertOne(alchemikUser.toDocument());
    }

    public void saveDataAlchemik(final UUID uuid, final AlchemikUser alchemikUser) {
        this.pool.getAlchemik().findOneAndReplace(new Document("_id", uuid.toString()), alchemikUser.toDocument());
    }

    public void saveAllAlchemik() {
        for (final AlchemikUser alchemikUser : rpgcore.getAlchemikNPC().getAlchemikUsers()) {
            this.saveDataAlchemik(alchemikUser.getUuid(), alchemikUser);
        }
    }

    public Map<UUID, NereusUser> loadAllNereus() {
        Map<UUID, NereusUser> userMap = new HashMap<>();
        for (Document document : this.pool.getNereus().find()) {
            final NereusUser nereusUser = new NereusUser(document);
            userMap.put(nereusUser.getUuid(), nereusUser);
        }
        return userMap;
    }

    public void addDataNereus(final NereusUser nereusUser) {
        if (this.pool.getNereus().find(new Document("_id", nereusUser.getUuid().toString())).first() != null) {
            this.pool.getNereus().deleteOne(new Document("_id", nereusUser.getUuid().toString()));
        }
        this.pool.getNereus().insertOne(nereusUser.toDocument());
    }

    public void saveDataNereus(final UUID uuid, final NereusUser nereusUser) {
        this.pool.getNereus().findOneAndReplace(new Document("_id", uuid.toString()), nereusUser.toDocument());
    }

    public void saveAllNereus() {
        for (final NereusUser nereusUser : rpgcore.getNereusNPC().getNereusUsers()) {
            this.saveDataNereus(nereusUser.getUuid(), nereusUser);
        }
    }


    public Map<UUID, HeadHuntUser> loadAllHeadHuntEvent() {
        Map<UUID, HeadHuntUser> userMap = new HashMap<>();
        for (Document document : this.pool.getHeadHuntEvent().find()) {
            if (document.getString("_id").equals("HeadHuntPricePool")) continue;
            final HeadHuntUser headHuntUser = new HeadHuntUser(document);
            userMap.put(headHuntUser.getUuid(), headHuntUser);
        }
        return userMap;
    }

    public void addDataHeadHuntEvent(final HeadHuntUser headHuntUser) {
        if (this.pool.getHeadHuntEvent().find(new Document("_id", headHuntUser.getUuid().toString())).first() != null) {
            this.pool.getHeadHuntEvent().deleteOne(new Document("_id", headHuntUser.getUuid().toString()));
        }
        this.pool.getHeadHuntEvent().insertOne(headHuntUser.toDocument());
    }

    public void saveDataHeadHuntEvent(final UUID uuid, final HeadHuntUser headHuntUser) {
        this.pool.getHeadHuntEvent().findOneAndReplace(new Document("_id", uuid.toString()), headHuntUser.toDocument());
    }

    public void saveAllHeadHuntEvent() {
        for (final HeadHuntUser headHuntUser : rpgcore.getEventManager().getHeadHuntManager().getHeadHuntUsers()) {
            this.saveDataHeadHuntEvent(headHuntUser.getUuid(), headHuntUser);
        }
    }

    public HeadHuntPricePoolUser loadHeadHuntPricePool() {
        final Document document = this.pool.getHeadHuntEvent().find(new Document("_id", "HeadHuntPricePool")).first();
        if (document == null) {
            final HeadHuntPricePoolUser user = new HeadHuntPricePoolUser();
            this.pool.getHeadHuntEvent().insertOne(user.toDocument());
            return new HeadHuntPricePoolUser();
        }
        return new HeadHuntPricePoolUser(document);
    }

    public void saveHeadHuntPricePool() {
        this.pool.getHeadHuntEvent().findOneAndReplace(new Document("_id", "HeadHuntPricePool"), rpgcore.getEventManager().getHeadHuntManager().getPricePool().toDocument());
    }



    public void onDisable() {
        pool.closePool();
    }


}
