package rpg.rpgcore.BACKUP.database;

import com.mongodb.client.MongoCollection;
import lombok.Getter;
import org.bson.Document;
import rpg.rpgcore.BACKUP.objects.Backup;
import rpg.rpgcore.RPGCORE;

import java.util.*;

public class BackupMongoManager {

    @Getter
    private final BackupMongoConnectionPoolManager pool;

    public BackupMongoManager(final RPGCORE rpgcore) {
        this.pool = new BackupMongoConnectionPoolManager();
    }

    //dd3d637b-aff4-4fa5-8484-d120ed492d43 - Mires
    //c166a38d-6ddf-47cb-8aed-2b05fb502051 - Chytryy
    //672d510e-083b-39f8-9681-4d8bc892586d - Orzel

    //4d335d52-df9f-479c-8d0a-57de4a4cb2fe - Fabi

   public Map<UUID, List<Backup>> loadAll() {
        final Map<UUID, List<Backup>> backups = new HashMap<>();
        for (final String name : this.getPool().getDatabase().listCollectionNames()) {
            final MongoCollection<Document> collection = this.getPool().getDatabase().getCollection(name);
            final List<Backup> backupList = new ArrayList<>();
            for (final Document document : collection.find()) {
                final Backup backup = new Backup(name.replaceAll("_", "-"), document);
                backupList.add(backup);
            }
            backups.put(UUID.fromString(name.replaceAll("_", "-")), backupList);
        }
        return backups;
   }

   public void save(final Backup backup) {
        final MongoCollection<Document> collection = this.getPool().getCollection(backup.getUuid());
//       if (collection.estimatedDocumentCount() > 25) {
//           final List<Date> dates = collection.find().into(new ArrayList<>()).stream().map(document -> {
//               try {
//                   return backup.getFormat().parse(document.getString("_id"));
//               } catch (ParseException e) {
//                   throw new RuntimeException(e);
//               }
//           }).sorted().collect(Collectors.toList());
//           System.out.println(dates);
//           // ZWRACA ODWROTNIE CZYLI 14:30, 15:30, itd.
//           //TODO zrobic usuwanie najstarszego backupu
//       }
       collection.insertOne(backup.toDocument());
   }

   public void remove(final Backup backup) {
        this.getPool().getCollection(backup.getUuid()).deleteOne(backup.toDocument());
   }


    public void onDisable() {
        pool.closePool();
    }


}
