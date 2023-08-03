package rpg.rpgcore.BACKUP.objects;

import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.bao.objects.BaoObject;
import rpg.rpgcore.bonuses.Bonuses;
import rpg.rpgcore.chat.ChatUser;
import rpg.rpgcore.dodatki.DodatkiUser;
import rpg.rpgcore.klasy.objects.Klasa;
import rpg.rpgcore.kociolki.KociolkiUser;
import rpg.rpgcore.npc.czarownica.objects.CzarownicaUser;
import rpg.rpgcore.npc.duszolog.objects.DuszologObject;
import rpg.rpgcore.npc.gornik.objects.GornikUser;
import rpg.rpgcore.npc.handlarz.objects.HandlarzUser;
import rpg.rpgcore.npc.kolekcjoner.objects.KolekcjonerObject;
import rpg.rpgcore.npc.lesnik.objects.LesnikObject;
import rpg.rpgcore.npc.lowca.objects.LowcaObject;
import rpg.rpgcore.npc.magazynier.objects.MagazynierUser;
import rpg.rpgcore.npc.medrzec.objects.MedrzecUser;
import rpg.rpgcore.npc.metinolog.objects.MetinologObject;
import rpg.rpgcore.npc.mistrz_yang.objects.MistrzYangUser;
import rpg.rpgcore.npc.przyrodnik.objects.PrzyrodnikObject;
import rpg.rpgcore.npc.pustelnik.objects.PustelnikUser;
import rpg.rpgcore.npc.rybak.objects.RybakUser;
import rpg.rpgcore.npc.oldwyslannik.objects.WyslannikObject;
import rpg.rpgcore.osiagniecia.objects.OsUser;
import rpg.rpgcore.pets.PetObject;
import rpg.rpgcore.pets.UserPets;
import rpg.rpgcore.user.User;
import rpg.rpgcore.user.WWWUser;
import rpg.rpgcore.wyszkolenie.objects.WyszkolenieUser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class Backup {
    private final RPGCORE rpgcore = RPGCORE.getInstance();
    private final SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    private final UUID uuid;
    private final String date;

    private final User user;
    private final Bonuses bonuses;
    private final OsUser os;
    private final KolekcjonerObject kolekcjoner;
    private final BaoObject bao;
    private final RybakUser rybak;
    private final DodatkiUser dodatki;
    private final MetinologObject metinolog;
    private final Klasa klasa;
    private final MedrzecUser medrzec;
    private final GornikUser gornik;
    private final DuszologObject duszolog;
    private final PrzyrodnikObject przyrodnik;
    private final ChatUser chat;
    private final MagazynierUser magazynier;
    private final LowcaObject lowca;
    private final LesnikObject lesnik;
    private final PetObject pet;
    private final UserPets userPets;
    private final WyslannikObject wyslannik;
    private final HandlarzUser handlarz;
    private final KociolkiUser kociolki;
    private final WyszkolenieUser wyszkolenie;
    private final WWWUser wwwUser;
    private final PustelnikUser pustelnik;
    private final MistrzYangUser mistrzYang;
    private final CzarownicaUser czarownica;



    public Backup(final UUID uuid) {
        this.uuid = uuid;
        this.date = format.format(new Date());
        this.user = this.rpgcore.getUserManager().find(uuid);
        this.bonuses = this.rpgcore.getBonusesManager().find(uuid);
        this.os = this.rpgcore.getOsManager().find(uuid);
        this.kolekcjoner = this.rpgcore.getKolekcjonerNPC().find(uuid);
        this.bao = this.rpgcore.getBaoManager().find(uuid);
        this.rybak = this.rpgcore.getRybakNPC().find(uuid);
        this.dodatki = this.rpgcore.getDodatkiManager().find(uuid);
        this.metinolog = this.rpgcore.getMetinologNPC().find(uuid);
        this.klasa = this.rpgcore.getKlasyManager().find(uuid);
        this.medrzec = this.rpgcore.getMedrzecNPC().find(uuid);
        this.gornik = this.rpgcore.getGornikNPC().find(uuid);
        this.duszolog = this.rpgcore.getDuszologNPC().find(uuid);
        this.przyrodnik = this.rpgcore.getPrzyrodnikNPC().find(uuid);
        this.chat = this.rpgcore.getChatManager().find(uuid);
        this.magazynier = this.rpgcore.getMagazynierNPC().find(uuid);
        this.lowca = this.rpgcore.getLowcaNPC().find(uuid);
        this.lesnik = this.rpgcore.getLesnikNPC().find(uuid);
        this.pet = this.rpgcore.getPetyManager().findActivePet(uuid);
        this.userPets = this.rpgcore.getPetyManager().findUserPets(uuid);
        this.wyslannik = this.rpgcore.getWyslannikNPC().find(uuid);
        this.handlarz = this.rpgcore.getHandlarzNPC().find(uuid);
        this.kociolki = this.rpgcore.getKociolkiManager().find(uuid);
        this.wyszkolenie = this.rpgcore.getWyszkolenieManager().find(uuid);
        this.wwwUser = this.rpgcore.getUserManager().findWWWUser(uuid);
        this.pustelnik = this.rpgcore.getPustelnikNPC().find(uuid);
        this.mistrzYang = this.rpgcore.getMistrzYangNPC().find(uuid);
        this.czarownica = this.rpgcore.getCzarownicaNPC().find(uuid);
    }

    public Backup(final String collectionName, final Document document) {
        this.uuid = UUID.fromString(collectionName);
        this.date = document.getString("_id");
        this.user = new User(document.get("user", Document.class));
        if (document.containsKey("bonuses")) this.bonuses = new Bonuses(document.get("bonuses", Document.class));
        else this.bonuses = new Bonuses(this.uuid);
        if (document.containsKey("os")) this.os = new OsUser(document.get("os", Document.class));
        else this.os = new OsUser(this.uuid);
        if (document.containsKey("kolekcjoner")) this.kolekcjoner = new KolekcjonerObject(document.get("kolekcjoner", Document.class));
        else this.kolekcjoner = new KolekcjonerObject(this.uuid);
        if (document.containsKey("bao")) this.bao = new BaoObject(document.get("bao", Document.class));
        else this.bao = new BaoObject(this.uuid);
        if (document.containsKey("rybak")) this.rybak = new RybakUser(document.get("rybak", Document.class));
        else this.rybak = new RybakUser(this.uuid);
        if (document.containsKey("dodatki")) this.dodatki = new DodatkiUser(document.get("dodatki", Document.class));
        else this.dodatki = new DodatkiUser(this.uuid);
        if (document.containsKey("metinolog")) this.metinolog = new MetinologObject(document.get("metinolog", Document.class));
        else this.metinolog = new MetinologObject(this.uuid);
        if (document.containsKey("klasa")) this.klasa = new Klasa(document.get("klasa", Document.class));
        else this.klasa = new Klasa(this.uuid);
        if (document.containsKey("medrzec")) this.medrzec = new MedrzecUser(document.get("medrzec", Document.class));
        else this.medrzec = new MedrzecUser(this.uuid);
        if (document.containsKey("gornik")) this.gornik = new GornikUser(document.get("gornik", Document.class));
        else this.gornik = new GornikUser(this.uuid);
        if (document.containsKey("duszolog")) this.duszolog = new DuszologObject(document.get("duszolog", Document.class));
        else this.duszolog = new DuszologObject(this.uuid);
        if (document.containsKey("przyrodnik")) this.przyrodnik = new PrzyrodnikObject(document.get("przyrodnik", Document.class));
        else this.przyrodnik = new PrzyrodnikObject(this.uuid);
        if (document.containsKey("chat")) this.chat = new ChatUser(document.get("chat", Document.class));
        else this.chat = new ChatUser(this.uuid);
        if (document.containsKey("magazynier")) this.magazynier = new MagazynierUser(document.get("magazynier", Document.class));
        else this.magazynier = new MagazynierUser(this.uuid);
        if (document.containsKey("lowca")) this.lowca = new LowcaObject(document.get("lowca", Document.class));
        else this.lowca = new LowcaObject(this.uuid);
        if (document.containsKey("lesnik")) this.lesnik = new LesnikObject(document.get("lesnik", Document.class));
        else this.lesnik = new LesnikObject(this.uuid);
        if (document.containsKey("pet")) this.pet = new PetObject(document.get("pet", Document.class));
        else this.pet = new PetObject(this.uuid);
        if (document.containsKey("userPets")) this.userPets = new UserPets(document.get("userPets", Document.class));
        else this.userPets = new UserPets(this.uuid);
        if (document.containsKey("wyslannik")) this.wyslannik = new WyslannikObject(document.get("wyslannik", Document.class));
        else this.wyslannik = new WyslannikObject(this.uuid);
        if (document.containsKey("handlarz")) this.handlarz = new HandlarzUser(document.get("handlarz", Document.class));
        else this.handlarz = new HandlarzUser(this.uuid);
        if (document.containsKey("kociolki")) this.kociolki = new KociolkiUser(document.get("kociolki", Document.class));
        else this.kociolki = new KociolkiUser(this.uuid);
        if (document.containsKey("wyszkolenie")) this.wyszkolenie = new WyszkolenieUser(document.get("wyszkolenie", Document.class));
        else this.wyszkolenie = new WyszkolenieUser(this.uuid);
        if (document.containsKey("wwwUser")) this.wwwUser = new WWWUser(document.get("wwwUser", Document.class));
        else this.wwwUser = new WWWUser(this.uuid);
        if (document.containsKey("pustelnik")) this.pustelnik = new PustelnikUser(document.get("pustelnik", Document.class));
        else this.pustelnik = new PustelnikUser(this.uuid);
        if (document.containsKey("mistrzYang")) this.mistrzYang = new MistrzYangUser(document.get("mistrzYang", Document.class));
        else this.mistrzYang = new MistrzYangUser(this.uuid);
        if (document.containsKey("czarownica")) this.czarownica = new CzarownicaUser(document.get("czarownica", Document.class));
        else this.czarownica = new CzarownicaUser(this.uuid);
    }

    public Date getDateToCompare() {
        try {
            return this.getFormat().parse(this.date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void save() {
        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getBackupMongoManager().save(this));
    }

    public Document toDocument() {
        return new Document("_id", this.date)
                .append("user", this.user.toDocument())
                .append("bonuses", this.bonuses.toDocument())
                .append("os", this.os.toDocument())
                .append("kolekcjoner", this.kolekcjoner.toDocument())
                .append("bao", this.bao.toDocument())
                .append("rybak", this.rybak.toDocument())
                .append("dodatki", this.dodatki.toDocument())
                .append("metinolog", this.metinolog.toDocument())
                .append("klasa", this.klasa.toDocument())
                .append("medrzec", this.medrzec.toDocument())
                .append("gornik", this.gornik.toDocument())
                .append("duszolog", this.duszolog.toDocument())
                .append("przyrodnik", this.przyrodnik.toDocument())
                .append("chat", this.chat.toDocument())
                .append("magazynier", this.magazynier.toDocument())
                .append("lowca", this.lowca.toDocument())
                .append("lesnik", this.lesnik.toDocument())
                .append("pet", this.pet.toDocument())
                .append("userPets", this.userPets.toDocument())
                .append("wyslannik", this.wyslannik.toDocument())
                .append("handlarz", this.handlarz.toDocument())
                .append("kociolki", this.kociolki.toDocument())
                .append("wyszkolenie", this.wyszkolenie.toDocument())
                .append("wwwUser", this.wwwUser.toDocument())
                .append("pustelnik", this.pustelnik.toDocument())
                .append("mistrzYang", this.mistrzYang.toDocument())
                .append("czarownica", this.czarownica.toDocument());
    }
}
