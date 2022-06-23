package rpg.rpgcore.database;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.net.UnknownHostException;

public class MongoConnectionPoolManager {

    private MongoClient mongoClient;

    public MongoConnectionPoolManager(final RPGCORE rpgcore) {
        try {
            mongoClient = new MongoClient(new MongoClientURI("mongodb://127.0.0.1:27017/?&authSource=admin&compressors=disabled&gssapiServiceName=mongodb"));
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
