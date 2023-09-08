package rpg.rpgcore.BACKUP.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import org.bson.Document;

import java.util.ArrayList;
import java.util.UUID;

public class BackupMongoConnectionPoolManager {
    @Getter
    private final MongoClient client;
    @Getter
    private final MongoDatabase database;




    public BackupMongoConnectionPoolManager() {
        this.client = MongoClients.create("mongodb://localhost/backup");
        this.database = this.client.getDatabase("backup");
        System.out.println(" ");
        System.out.println("[RPG-CORE] >> Pomyslnie podlaczono do zapasowej bazy danych  << [RPG-CORE]");
        System.out.println(" ");
    }

    public void firstJoin(final UUID uuid) {
        if (this.database.listCollectionNames().into(new ArrayList<>()).contains("hellrpg_" + uuid.toString().replaceAll("-", "_"))) {
            return;
        }
        this.database.createCollection("hellrpg_" + uuid.toString().replaceAll("-", "_"));
    }

    public MongoCollection<Document> getCollection(final UUID uuid) {
        if (!this.database.listCollectionNames().into(new ArrayList<>()).contains("hellrpg_" + uuid.toString().replaceAll("-", "_"))) {
            this.database.createCollection("hellrpg_" + uuid.toString().replaceAll("-", "_"));
            return this.database.getCollection("hellrpg_" + uuid.toString().replaceAll("-", "_"));
        }
        return this.database.getCollection("hellrpg_" + uuid.toString().replaceAll("-", "_"));
    }



    public void closePool() {
        this.client.close();
    }
}
