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
    private final MongoCollection<Document> hellrpg_bany;
    private final MongoCollection<Document> hellrpg_muty;
    private final MongoCollection<Document> hellrpg_akcesoria;
    private final MongoCollection<Document> hellrpg_bony;
    private final MongoCollection<Document> hellrpg_magazyny;
    private final MongoCollection<Document> hellrpg_metiny;
    private final MongoCollection<Document> hellrpg_targi;
    private final MongoCollection<Document> hellrpg_osiagniecia;
    private final MongoCollection<Document> hellrpg_bao;
    private final MongoCollection<Document> hellrpg_ekwipunek;
    private final MongoCollection<Document> hellrpg_enderchest;
    private final MongoCollection<Document> hellrpg_zbroja;
    private final MongoCollection<Document> hellrpg_rybak;
    private final MongoCollection<Document> hellrpg_kolekcjoner;
    private final MongoCollection<Document> hellrpg_magazynier;
    private final MongoCollection<Document> hellrpg_trener;
    private final MongoCollection<Document> hellrpg_metinolog;
    private final MongoCollection<Document> hellrpg_other;
    private final MongoCollection<Document> hellrpg_klasy;

    private final MongoCollection<Document> hellrpg_medyk;
    private final MongoCollection<Document> hellrpg_gornik;


    public MongoConnectionPoolManager() {
        this.client = MongoClients.create("mongodb://u7id5em5uspjam4butns:3ws4TKngK0iIgoE0lMSY@n1-c2-mongodb-clevercloud-customers.services.clever-cloud.com:27017,n2-c2-mongodb-clevercloud-customers.services.clever-cloud.com:27017/biyowrjyqcvr1sa?replicaSet=rs0");
        MongoDatabase database = this.client.getDatabase("biyowrjyqcvr1sa");
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
        if (!collections.contains("hellrpg_bany")) {
            database.createCollection("hellrpg_bany");
        }
        if (!collections.contains("hellrpg_muty")) {
            database.createCollection("hellrpg_muty");
        }
        if (!collections.contains("hellrpg_akcesoria")) {
            database.createCollection("hellrpg_akcesoria");
        }
        if (!collections.contains("hellrpg_bony")) {
            database.createCollection("hellrpg_bony");
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
        if (!collections.contains("hellrpg_ekwipunek")) {
            database.createCollection("hellrpg_ekwipunek");
        }
        if (!collections.contains("hellrpg_enderchest")) {
            database.createCollection("hellrpg_enderchest");
        }
        if (!collections.contains("hellrpg_zbroja")) {
            database.createCollection("hellrpg_zbroja");
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
        if (!collections.contains("hellrpg_trener")) {
            database.createCollection("hellrpg_trener");
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
        this.hellrpg_spawn = database.getCollection("hellrpg_spawn");
        this.hellrpg_gracze = database.getCollection("hellrpg_gracze");
        this.hellrpg_gildie = database.getCollection("hellrpg_gildie");
        this.hellrpg_bany = database.getCollection("hellrpg_bany");
        this.hellrpg_muty = database.getCollection("hellrpg_muty");
        this.hellrpg_akcesoria = database.getCollection("hellrpg_akcesoria");
        this.hellrpg_bony = database.getCollection("hellrpg_bony");
        this.hellrpg_magazyny = database.getCollection("hellrpg_magazyny");
        this.hellrpg_metiny = database.getCollection("hellrpg_metiny");
        this.hellrpg_targi = database.getCollection("hellrpg_targi");
        this.hellrpg_osiagniecia = database.getCollection("hellrpg_osiagniecia");
        this.hellrpg_bao = database.getCollection("hellrpg_bao");
        this.hellrpg_ekwipunek = database.getCollection("hellrpg_ekwipunek");
        this.hellrpg_enderchest = database.getCollection("hellrpg_enderchest");
        this.hellrpg_zbroja = database.getCollection("hellrpg_zbroja");
        this.hellrpg_rybak = database.getCollection("hellrpg_rybak");
        this.hellrpg_kolekcjoner = database.getCollection("hellrpg_kolekcjoner");
        this.hellrpg_magazynier = database.getCollection("hellrpg_magazynier");
        this.hellrpg_trener = database.getCollection("hellrpg_trener");
        this.hellrpg_metinolog = database.getCollection("hellrpg_metinolog");
        this.hellrpg_other = database.getCollection("hellrpg_other");
        this.hellrpg_klasy = database.getCollection("hellrpg_klasy");
        this.hellrpg_medyk = database.getCollection("hellrpg_medyk");
        this.hellrpg_gornik = database.getCollection("hellrpg_gornik");
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

    public MongoCollection<Document> getBany() {
        return this.hellrpg_bany;
    }

    public MongoCollection<Document> getMuty() {
        return this.hellrpg_muty;
    }

    public MongoCollection<Document> getAkcesoria() {
        return this.hellrpg_akcesoria;
    }

    public MongoCollection<Document> getBony() {
        return this.hellrpg_bony;
    }

    public MongoCollection<Document> getMagazyny() {
        return this.hellrpg_magazyny;
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

    public MongoCollection<Document> getEkwipunek() {
        return this.hellrpg_ekwipunek;
    }

    public MongoCollection<Document> getEnderchest() {
        return this.hellrpg_enderchest;
    }

    public MongoCollection<Document> getZbroja() {
        return this.hellrpg_zbroja;
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

    public MongoCollection<Document> getTrener() {
        return this.hellrpg_trener;
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

    public void closePool() {
        this.client.close();
    }
}
