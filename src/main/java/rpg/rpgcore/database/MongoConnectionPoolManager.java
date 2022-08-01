package rpg.rpgcore.database;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Set;

public class MongoConnectionPoolManager {

    private MongoClient mongoClient;
    private DB database;

    public MongoConnectionPoolManager(final RPGCORE rpgcore) {
        try {
            mongoClient = new MongoClient(new MongoClientURI("mongodb://127.0.0.1:27017/?&authSource=admin&compressors=disabled&gssapiServiceName=mongodb"));
            database = mongoClient.getDB("minecraft");
            /*final Set<String> collections = database.getCollectionNames();
            if (!database.collectionExists("hellrpg_spawn")) {
                database.createCollection("hellrpg_spawn", new BasicDBObject());
            }
            if (!database.collectionExists("hellrpg_gracze")) {
                database.createCollection("hellrpg_gracze", new BasicDBObject());
            }
            if (!database.collectionExists("hellrpg_gildie")) {
                database.createCollection("hellrpg_gildie", new BasicDBObject());
            }
            if (!database.collectionExists("hellrpg_bany")) {
                database.createCollection("hellrpg_bany", new BasicDBObject());
            }
            if (!database.collectionExists("hellrpg_muty")) {
                database.createCollection("hellrpg_muty", new BasicDBObject());
            }
            if (!database.collectionExists("hellrpg_akcesoria")) {
                database.createCollection("hellrpg_akcesoria", new BasicDBObject());
            }
            if (!database.collectionExists("hellrpg_bony")) {
                database.createCollection("hellrpg_bony", new BasicDBObject());
            }
            if (!database.collectionExists("hellrpg_magazyny")) {
                database.createCollection("hellrpg_magazyny", new BasicDBObject());
            }
            if (!database.collectionExists("hellrpg_metiny")) {
                database.createCollection("hellrpg_metiny", new BasicDBObject());
            }
            if (!database.collectionExists("hellrpg_targi")) {
                database.createCollection("hellrpg_targi", new BasicDBObject());
            }
            if (!database.collectionExists("hellrpg_osiagniecia")) {
                database.createCollection("hellrpg_osiagniecia", new BasicDBObject());
            }
            if (!database.collectionExists("hellrpg_bao")) {
                database.createCollection("hellrpg_bao", new BasicDBObject());
            }
            if (!database.collectionExists("hellrpg_ekwipunek")) {
                database.createCollection("hellrpg_ekwipunek", new BasicDBObject());
            }
            if (!database.collectionExists("hellrpg_enderchest")) {
                database.createCollection("hellrpg_enderchest", new BasicDBObject());
            }
            if (!database.collectionExists("hellrpg_zbroja")) {
                database.createCollection("hellrpg_zbroja", new BasicDBObject());
            }
            if (!database.collectionExists("hellrpg_rybak")) {
                database.createCollection("hellrpg_rybak", new BasicDBObject());
            }
            if (!database.collectionExists("hellrpg_kolekcjoner")) {
                database.createCollection("hellrpg_kolekcjoner", new BasicDBObject());
            }
            if (!database.collectionExists("hellrpg_magazynier")) {
                database.createCollection("hellrpg_magazynier", new BasicDBObject());
            }*/

            for (Player player : rpgcore.getServer().getOnlinePlayers()) {
                if (player.hasPermission("*")) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&a&lPomyslnie podlaczono do bazy danych"));
                }
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public MongoClient getPool() {
        try {
            mongoClient = new MongoClient(new MongoClientURI("mongodb://127.0.0.1:27017/?&authSource=admin&compressors=disabled&gssapiServiceName=mongodb"));
            return mongoClient;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void closePool() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
}
