package rpg.rpgcore.BACKUP.objects;

import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.bao.objects.BaoObject;
import rpg.rpgcore.bonuses.Bonuses;
import rpg.rpgcore.bossy.effects.PrzekletyCzarnoksieznik.PrzekletyCzarnoksieznikUser;
import rpg.rpgcore.chat.objects.ChatUser;
import rpg.rpgcore.dodatki.objects.DodatkiUser;
import rpg.rpgcore.klasy.objects.Klasa;
import rpg.rpgcore.kociolki.KociolkiUser;
import rpg.rpgcore.newTarg.objects.Targ;
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
import rpg.rpgcore.npc.mrozny_stroz.objects.MroznyStrozUser;
import rpg.rpgcore.npc.przyrodnik.objects.PrzyrodnikObject;
import rpg.rpgcore.npc.pustelnik.objects.PustelnikUser;
import rpg.rpgcore.npc.rybak.objects.RybakUser;
import rpg.rpgcore.npc.wyslannik.objects.WyslannikUser;
import rpg.rpgcore.osiagniecia.objects.OsUser;
import rpg.rpgcore.pets.objects.PetObject;
import rpg.rpgcore.pets.objects.UserPets;
import rpg.rpgcore.user.User;
import rpg.rpgcore.user.WWWUser;
import rpg.rpgcore.wyszkolenie.objects.WyszkolenieUser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class Backup implements Cloneable {
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
    private final HandlarzUser handlarz;
    private final KociolkiUser kociolki;
    private final WyszkolenieUser wyszkolenie;
    private final WWWUser wwwUser;
    private final PustelnikUser pustelnik;
    private final MistrzYangUser mistrzYang;
    private final CzarownicaUser czarownica;
    private final WyslannikUser wyslannik;
    private final PrzekletyCzarnoksieznikUser przekletyCzarnoksieznik;
    private final Targ targ;
    private final MroznyStrozUser mroznyStroz;



    public Backup(final UUID uuid) {
        this.uuid = uuid;
        this.date = format.format(new Date());
        this.user = this.rpgcore.getUserManager().find(uuid).clone();
        this.bonuses = this.rpgcore.getBonusesManager().find(uuid).clone();
        this.os = this.rpgcore.getOsManager().find(uuid).clone();
        this.kolekcjoner = this.rpgcore.getKolekcjonerNPC().find(uuid).clone();
        this.bao = this.rpgcore.getBaoManager().find(uuid).clone();
        this.rybak = this.rpgcore.getRybakNPC().find(uuid).clone();
        this.dodatki = this.rpgcore.getDodatkiManager().find(uuid).clone();
        this.metinolog = this.rpgcore.getMetinologNPC().find(uuid).clone();
        this.klasa = this.rpgcore.getKlasyManager().find(uuid).clone();
        this.medrzec = this.rpgcore.getMedrzecNPC().find(uuid).clone();
        this.gornik = this.rpgcore.getGornikNPC().find(uuid).clone();
        this.duszolog = this.rpgcore.getDuszologNPC().find(uuid).clone();
        this.przyrodnik = this.rpgcore.getPrzyrodnikNPC().find(uuid).clone();
        this.chat = this.rpgcore.getChatManager().find(uuid).clone();
        this.magazynier = this.rpgcore.getMagazynierNPC().find(uuid).clone();
        this.lowca = this.rpgcore.getLowcaNPC().find(uuid).clone();
        this.lesnik = this.rpgcore.getLesnikNPC().find(uuid).clone();
        this.pet = this.rpgcore.getPetyManager().findActivePet(uuid).clone();
        this.userPets = this.rpgcore.getPetyManager().findUserPets(uuid).clone();
        this.handlarz = this.rpgcore.getHandlarzNPC().find(uuid).clone();
        this.kociolki = this.rpgcore.getKociolkiManager().find(uuid).clone();
        this.wyszkolenie = this.rpgcore.getWyszkolenieManager().find(uuid).clone();
        this.wwwUser = this.rpgcore.getUserManager().findWWWUser(uuid).clone();
        this.pustelnik = this.rpgcore.getPustelnikNPC().find(uuid).clone();
        this.mistrzYang = this.rpgcore.getMistrzYangNPC().find(uuid).clone();
        this.czarownica = this.rpgcore.getCzarownicaNPC().find(uuid).clone();
        this.wyslannik = this.rpgcore.getWyslannikNPC().find(uuid).clone();
        this.przekletyCzarnoksieznik = this.rpgcore.getPrzekletyCzarnoksieznikBossManager().find(uuid).clone();
        this.targ = this.rpgcore.getNewTargManager().find(uuid).clone();
        this.mroznyStroz = this.rpgcore.getMroznyStrozNPC().find(uuid).clone();
    }

    public Backup(final UUID uuid, final String date) {
        this.uuid = uuid;
        this.date = date;
        this.user = this.rpgcore.getUserManager().find(uuid).clone();
        this.bonuses = this.rpgcore.getBonusesManager().find(uuid).clone();
        this.os = this.rpgcore.getOsManager().find(uuid).clone();
        this.kolekcjoner = this.rpgcore.getKolekcjonerNPC().find(uuid).clone();
        this.bao = this.rpgcore.getBaoManager().find(uuid).clone();
        this.rybak = this.rpgcore.getRybakNPC().find(uuid).clone();
        this.dodatki = this.rpgcore.getDodatkiManager().find(uuid).clone();
        this.metinolog = this.rpgcore.getMetinologNPC().find(uuid).clone();
        this.klasa = this.rpgcore.getKlasyManager().find(uuid).clone();
        this.medrzec = this.rpgcore.getMedrzecNPC().find(uuid).clone();
        this.gornik = this.rpgcore.getGornikNPC().find(uuid).clone();
        this.duszolog = this.rpgcore.getDuszologNPC().find(uuid).clone();
        this.przyrodnik = this.rpgcore.getPrzyrodnikNPC().find(uuid).clone();
        this.chat = this.rpgcore.getChatManager().find(uuid).clone();
        this.magazynier = this.rpgcore.getMagazynierNPC().find(uuid).clone();
        this.lowca = this.rpgcore.getLowcaNPC().find(uuid).clone();
        this.lesnik = this.rpgcore.getLesnikNPC().find(uuid).clone();
        this.pet = this.rpgcore.getPetyManager().findActivePet(uuid).clone();
        this.userPets = this.rpgcore.getPetyManager().findUserPets(uuid).clone();
        this.handlarz = this.rpgcore.getHandlarzNPC().find(uuid).clone();
        this.kociolki = this.rpgcore.getKociolkiManager().find(uuid).clone();
        this.wyszkolenie = this.rpgcore.getWyszkolenieManager().find(uuid).clone();
        this.wwwUser = this.rpgcore.getUserManager().findWWWUser(uuid).clone();
        this.pustelnik = this.rpgcore.getPustelnikNPC().find(uuid).clone();
        this.mistrzYang = this.rpgcore.getMistrzYangNPC().find(uuid).clone();
        this.czarownica = this.rpgcore.getCzarownicaNPC().find(uuid).clone();
        this.wyslannik = this.rpgcore.getWyslannikNPC().find(uuid).clone();
        this.przekletyCzarnoksieznik = this.rpgcore.getPrzekletyCzarnoksieznikBossManager().find(uuid).clone();
        this.targ = this.rpgcore.getNewTargManager().find(uuid).clone();
        this.mroznyStroz = this.rpgcore.getMroznyStrozNPC().find(uuid).clone();
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
        if (document.containsKey("wyslannik")) this.wyslannik = new WyslannikUser(document.get("wyslannik", Document.class));
        else this.wyslannik = new WyslannikUser(this.uuid);
        if (document.containsKey("przekletyCzarnoksieznik")) this.przekletyCzarnoksieznik = new PrzekletyCzarnoksieznikUser(document.get("przekletyCzarnoksieznik", Document.class));
        else this.przekletyCzarnoksieznik = new PrzekletyCzarnoksieznikUser(this.uuid);
        if (document.containsKey("targ")) this.targ = new Targ(document.get("targ", Document.class));
        else this.targ = new Targ(this.uuid);
        if (document.containsKey("mroznyStroz")) this.mroznyStroz = new MroznyStrozUser(document.get("mroznyStroz", Document.class));
        else this.mroznyStroz = new MroznyStrozUser(this.uuid);
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
                .append("handlarz", this.handlarz.toDocument())
                .append("kociolki", this.kociolki.toDocument())
                .append("wyszkolenie", this.wyszkolenie.toDocument())
                .append("wwwUser", this.wwwUser.toDocument())
                .append("pustelnik", this.pustelnik.toDocument())
                .append("mistrzYang", this.mistrzYang.toDocument())
                .append("czarownica", this.czarownica.toDocument())
                .append("wyslannik", this.wyslannik.toDocument())
                .append("przekletyCzarnoksieznik", this.przekletyCzarnoksieznik.toDocument())
                .append("targ", this.targ.toDocument()
                .append("mroznyStroz", this.mroznyStroz.toDocument()));
    }

    public boolean equals(final Backup backup) {
        return this.uuid.equals(backup.getUuid()) && this.date.equals(backup.getDate());
    }

    @Override
    public Backup clone() {
        try {
            return (Backup) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
