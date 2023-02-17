package rpg.rpgcore.database;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import rpg.rpgcore.utils.Utils;

import java.util.ArrayList;

public class MongoConnectionPoolManager {

    private final MongoClient client;

    private final MongoCollection<Document> hellrpg_spawn;
    private final MongoCollection<Document> hellrpg_gracze;
    private final MongoCollection<Document> hellrpg_gildie;
    private final MongoCollection<Document> hellrpg_dodatki;
    private final MongoCollection<Document> hellrpg_metiny;
    private final MongoCollection<Document> hellrpg_targi;
    private final MongoCollection<Document> hellrpg_osiagniecia;
    private final MongoCollection<Document> hellrpg_bao;
    private final MongoCollection<Document> hellrpg_rybak;
    private final MongoCollection<Document> hellrpg_kolekcjoner;
    private final MongoCollection<Document> hellrpg_magazynier;
    private final MongoCollection<Document> hellrpg_metinolog;
    private final MongoCollection<Document> hellrpg_other;
    private final MongoCollection<Document> hellrpg_klasy;
    private final MongoCollection<Document> hellrpg_medyk;
    private final MongoCollection<Document> hellrpg_gornik;
    private final MongoCollection<Document> hellrpg_duszolog;
    private final MongoCollection<Document> hellrpg_przyrodnik;
    private final MongoCollection<Document> hellrpg_bonuses;
    private final MongoCollection<Document> hellrpg_chatUsers;
    private final MongoCollection<Document> hellrpg_lowca;
    private final MongoCollection<Document> hellrpg_lesnik;
    private final MongoCollection<Document> hellrpg_pety;
    private final MongoCollection<Document> hellrpg_userPets;
    private final MongoCollection<Document> hellrpg_oreLocations;
    private final MongoCollection<Document> hellrpg_wyslannik;
    //private final MongoCollection<Document> hellrpg_przykladowyNPC; // TU TWORZYSZ ZMIENNA DO KOLEKCJI ZEBY MOC SIE DO NIEJ ODOWLAC !!!!
    private final MongoCollection<Document> hellrpg_kociolki;
    private final MongoCollection<Document> hellrpg_serwerWhiteList;
    private final MongoCollection<Document> hellrpg_artefaktyZaLvL;
    private final MongoCollection<Document> hellrpg_handlarz;
    private final MongoCollection<Document> hellrpg_wyszkolenie;


    public MongoConnectionPoolManager() {
        this.client = MongoClients.create("mongodb://localhost/minecraft"); //mongodb://u7id5em5uspjam4butns:3ws4TKngK0iIgoE0lMSY@n1-c2-mongodb-clevercloud-customers.services.clever-cloud.com:27017,n2-c2-mongodb-clevercloud-customers.services.clever-cloud.com:27017/biyowrjyqcvr1sa?replicaSet=rs0
        MongoDatabase database = this.client.getDatabase("minecraft");
        final ArrayList<String> collections = database.listCollectionNames().into(new ArrayList<>());
        if (!collections.contains("hellrpg_spawn")) {
            database.createCollection("hellrpg_spawn");
        }
        if (!collections.contains("hellrpg_gracze")) {
            database.createCollection("hellrpg_gracze");
        }
        if (!collections.contains("hellrpg_gildie")) {
            database.createCollection("hellrpg_gildie");
        }
        if (!collections.contains("hellrpg_dodatki")) {
            database.createCollection("hellrpg_dodatki");
        }
        if (!collections.contains("hellrpg_magazyny")) {
            database.createCollection("hellrpg_magazyny");
        }
        if (!collections.contains("hellrpg_metiny")) {
            database.createCollection("hellrpg_metiny");
        }
        if (!collections.contains("hellrpg_targi")) {
            database.createCollection("hellrpg_targi");
        }
        if (!collections.contains("hellrpg_osiagniecia")) {
            database.createCollection("hellrpg_osiagniecia");
        }
        if (!collections.contains("hellrpg_bao")) {
            database.createCollection("hellrpg_bao");
        }
        if (!collections.contains("hellrpg_rybak")) {
            database.createCollection("hellrpg_rybak");
        }
        if (!collections.contains("hellrpg_kolekcjoner")) {
            database.createCollection("hellrpg_kolekcjoner");
        }
        if (!collections.contains("hellrpg_magazynier")) {
            database.createCollection("hellrpg_magazynier");
        }
        if (!collections.contains("hellrpg_metinolog")) {
            database.createCollection("hellrpg_metinolog");
        }
        if (!collections.contains("hellrpg_other")) {
            database.createCollection("hellrpg_other");
        }
        if (!collections.contains("hellrpg_klasy")) {
            database.createCollection("hellrpg_klasy");
        }
        if (!collections.contains("hellrpg_medyk")) {
            database.createCollection("hellrpg_medyk");
        }
        if (!collections.contains("hellrpg_gornik")) {
            database.createCollection("hellrpg_gornik");
        }
        if (!collections.contains("hellrpg_duszolog")) {
            database.createCollection("hellrpg_duszolog");
        }
        if (!collections.contains("hellrpg_przyrodnik")) {
            database.createCollection("hellrpg_przyrodnik");
        }
        if (!collections.contains("hellrpg_bonuses")) {
            database.createCollection("hellrpg_bonuses");
        }
        if (!collections.contains("hellrpg_chatUsers")) {
            database.createCollection("hellrpg_chatUsers");
        }
        if (!collections.contains("hellrpg_lowca")) {
            database.createCollection("hellrpg_lowca");
        }
        if (!collections.contains("hellrpg_lesnik")) {
            database.createCollection("hellrpg_lesnik");
        }
        if (!collections.contains("hellrpg_pety")) {
            database.createCollection("hellrpg_pety");
        }
        if (!collections.contains("hellrpg_userPets")) {
            database.createCollection("hellrpg_userPets");
        }
        if (!collections.contains("hellrpg_oreLocations")) {
            database.createCollection("hellrpg_oreLocations");
        }
        if (!collections.contains("hellrpg_wyslannik")) {
            database.createCollection("hellrpg_wyslannik");
        }
        if (!collections.contains("hellrpg_kociolki")) {
            database.createCollection("hellrpg_kociolki");
        }
        if (!collections.contains("hellrpg_serwerWhiteList")) {
            database.createCollection("hellrpg_serwerWhiteList");
        }
        if (!collections.contains("hellrpg_artefaktyZaLvL")) {
            database.createCollection("hellrpg_artefaktyZaLvL");
        }
        if (!collections.contains("hellrpg_handlarz")) {
            database.createCollection("hellrpg_handlarz");
        }
        if (!collections.contains("hellrpg_wyszkolenie")) {
            database.createCollection("hellrpg_wyszkolenie");
        }
        // TU TWORZYSZ KOLEKCJE JESLI JEJ NIE MA W BAZIE DANYCH (TAKA SZUFLADA NA UZYTKOWNIKOW)
        /*if (!collections.contains("hellrpg_przykladowyNPC")) {
            database.createCollection("hellrpg_przykladowyNPC");
        }*/
        this.hellrpg_spawn = database.getCollection("hellrpg_spawn");
        this.hellrpg_gracze = database.getCollection("hellrpg_gracze");
        this.hellrpg_gildie = database.getCollection("hellrpg_gildie");
        this.hellrpg_dodatki = database.getCollection("hellrpg_dodatki");
        this.hellrpg_metiny = database.getCollection("hellrpg_metiny");
        this.hellrpg_targi = database.getCollection("hellrpg_targi");
        this.hellrpg_osiagniecia = database.getCollection("hellrpg_osiagniecia");
        this.hellrpg_bao = database.getCollection("hellrpg_bao");
        this.hellrpg_rybak = database.getCollection("hellrpg_rybak");
        this.hellrpg_kolekcjoner = database.getCollection("hellrpg_kolekcjoner");
        this.hellrpg_magazynier = database.getCollection("hellrpg_magazynier");
        this.hellrpg_metinolog = database.getCollection("hellrpg_metinolog");
        this.hellrpg_other = database.getCollection("hellrpg_other");
        this.hellrpg_klasy = database.getCollection("hellrpg_klasy");
        this.hellrpg_medyk = database.getCollection("hellrpg_medyk");
        this.hellrpg_gornik = database.getCollection("hellrpg_gornik");
        this.hellrpg_duszolog = database.getCollection("hellrpg_duszolog");
        this.hellrpg_przyrodnik = database.getCollection("hellrpg_przyrodnik");
        this.hellrpg_bonuses = database.getCollection("hellrpg_bonuses");
        this.hellrpg_chatUsers = database.getCollection("hellrpg_chatUsers");
        this.hellrpg_lowca = database.getCollection("hellrpg_lowca");
        this.hellrpg_lesnik = database.getCollection("hellrpg_lesnik");
        this.hellrpg_pety = database.getCollection("hellrpg_pety");
        this.hellrpg_userPets = database.getCollection("hellrpg_userPets");
        this.hellrpg_oreLocations = database.getCollection("hellrpg_oreLocations");
        this.hellrpg_wyslannik = database.getCollection("hellrpg_wyslannik");
        this.hellrpg_kociolki = database.getCollection("hellrpg_kociolki");
        this.hellrpg_serwerWhiteList = database.getCollection("hellrpg_serwerWhiteList");
        this.hellrpg_artefaktyZaLvL = database.getCollection("hellrpg_artefaktyZaLvL");
        this.hellrpg_handlarz = database.getCollection("hellrpg_handlarz");
        this.hellrpg_wyszkolenie = database.getCollection("hellrpg_wyszkolenie");
        // TU PRZYPISUJESZ KOLEKCJE DO ZMIENNEJ
        //this.hellrpg_przykladowyNPC = database.getCollection("hellrpg_przykladowyNPC");
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            if (player.hasPermission("*")) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&a&lPomyslnie podlaczono do bazy danych"));
            }
        }
    }

    public MongoCollection<Document> getSpawn() {
        return this.hellrpg_spawn;
    }
    public MongoCollection<Document> getGracze() {
        return this.hellrpg_gracze;
    }
    public MongoCollection<Document> getGildie() {
        return this.hellrpg_gildie;
    }
    public MongoCollection<Document> getDodatki() {
        return this.hellrpg_dodatki;
    }
    public MongoCollection<Document> getMetiny() {
        return this.hellrpg_metiny;
    }
    public MongoCollection<Document> getTargi() {
        return this.hellrpg_targi;
    }
    public MongoCollection<Document> getOsiagniecia() {
        return this.hellrpg_osiagniecia;
    }
    public MongoCollection<Document> getBao() {
        return this.hellrpg_bao;
    }
    public MongoCollection<Document> getRybak() {
        return this.hellrpg_rybak;
    }
    public MongoCollection<Document> getKolekcjoner() {
        return this.hellrpg_kolekcjoner;
    }
    public MongoCollection<Document> getMagazynier() {
        return this.hellrpg_magazynier;
    }
    public MongoCollection<Document> getMetinolog() {
        return this.hellrpg_metinolog;
    }
    public MongoCollection<Document> getOther() {
        return this.hellrpg_other;
    }
    public MongoCollection<Document> getKlasy() {
        return this.hellrpg_klasy;
    }
    public MongoCollection<Document> getMedyk() {
        return this.hellrpg_medyk;
    }
    public MongoCollection<Document> getGornik() {
        return this.hellrpg_gornik;
    }
    public MongoCollection<Document> getDuszolog() {
        return this.hellrpg_duszolog;
    }
    public MongoCollection<Document> getPrzyrodnik() {
        return this.hellrpg_przyrodnik;
    }
    public MongoCollection<Document> getBonuses() {
        return this.hellrpg_bonuses;
    }
    public MongoCollection<Document> getChatUsers() {
        return this.hellrpg_chatUsers;
    }
    public MongoCollection<Document> getLowca() {
        return this.hellrpg_lowca;
    }
    public MongoCollection<Document> getLesnik() {
        return this.hellrpg_lesnik;
    }
    public MongoCollection<Document> getPety() {
        return this.hellrpg_pety;
    }
    public MongoCollection<Document> getUserPets() {
        return this.hellrpg_userPets;
    }
    public MongoCollection<Document> getOreLocations() {
        return this.hellrpg_oreLocations;
    }
    public MongoCollection<Document> getWyslannik() {
        return this.hellrpg_wyslannik;
    }
    public MongoCollection<Document> getKociolki() {
        return this.hellrpg_kociolki;
    }
    public MongoCollection<Document> getSerwerWhiteList() {
        return this.hellrpg_serwerWhiteList;
    }
    public MongoCollection<Document> getArtefaktyZaLvL() {
        return this.hellrpg_artefaktyZaLvL;
    }

    public MongoCollection<Document> getHandlarz() {
        return hellrpg_handlarz;
    }

    public MongoCollection<Document> getWyszkolenie() {
        return hellrpg_wyszkolenie;
    }

    // TU ROBISZ MOZWLIOSC ODWOLANIA SIE DO KOLEKCJI
    /*public MongoCollection<Document> getPrzykladowyNPC() {
        return hellrpg_przykladowyNPC;
    }*/

    public void closePool() {
        this.client.close();
    }
}
