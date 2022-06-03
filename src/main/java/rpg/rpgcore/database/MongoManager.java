package rpg.rpgcore.database;

import com.mongodb.*;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

public class MongoManager {

    private final RPGCORE rpgcore;
    private final MongoConnectionPoolManager pool;



    public MongoManager(final RPGCORE rpgcore) {
        this.pool = new MongoConnectionPoolManager(rpgcore);
        this.rpgcore = rpgcore;
    }


    /*public void tempMoveAll() {
        DB database = pool.getPool().getDB("minecraft");
        DBCollection collection = database.getCollection("players");
        for (UUID uuid : rpgcore.getPlayerManager().getPlayers()) {
            BasicDBObject document = new BasicDBObject();
            document.put("_id", uuid.toString());
            document.put("nick", rpgcore.getPlayerManager().getPlayerName(uuid));
            document.put("level", rpgcore.getPlayerManager().getPlayerLvl(uuid));
            document.put("exp", rpgcore.getPlayerManager().getPlayerExp(uuid));
            document.put("kasa", String.format("“%.2f”", rpgcore.getPlayerManager().getPlayerKasa(uuid)));
            document.put("banInfo", rpgcore.getPlayerManager().getPlayerBanInfo(uuid));
            document.put("muteInfo", rpgcore.getPlayerManager().getPlayerMuteInfo(uuid));
            document.put("punishmentHistory", rpgcore.getPlayerManager().getPlayerPunishmentHistory(uuid));
            document.put("osMoby", rpgcore.getPlayerManager().getPlayerOsMoby(uuid));
            document.put("osMobyAccept", rpgcore.getPlayerManager().getOsMobyAccept(uuid));
            document.put("osLudzie", rpgcore.getPlayerManager().getPlayerOsLudzie(uuid));
            document.put("osLudzieAccept", rpgcore.getPlayerManager().getOsLudzieAccept(uuid));
            document.put("osSakwy", rpgcore.getPlayerManager().getPlayerOsSakwy(uuid));
            document.put("osSakwyAccept", rpgcore.getPlayerManager().getOsSakwyAccept(uuid));
            document.put("osNiesy", rpgcore.getPlayerManager().getPlayerOsNiesy(uuid));
            document.put("osNiesyAccept", rpgcore.getPlayerManager().getOsNiesyAccept(uuid));
            document.put("osRybak", rpgcore.getPlayerManager().getPlayerOsRybak(uuid));
            document.put("osRybakAccept", rpgcore.getPlayerManager().getOsRybakAccept(uuid));
            document.put("osDrwal", rpgcore.getPlayerManager().getPlayerOsDrwal(uuid));
            document.put("osDrwalAccept", rpgcore.getPlayerManager().getOsDrwalAccept(uuid));
            document.put("osGornik", rpgcore.getPlayerManager().getPlayerOsGornik(uuid));
            document.put("osGornikAccept", rpgcore.getPlayerManager().getOsGornikAccept(uuid));
            document.put("BAO_BONUSY", rpgcore.getBaoManager().getBaoBonusy(uuid));
            document.put("BAO_WARTOSCI", rpgcore.getBaoManager().getBaoBonusyWartosci(uuid));
            document.put("RYBAK_MISJE", rpgcore.getRybakNPC().getPlayerRybakMisje(uuid));
            document.put("RYBAK_POSTEP", rpgcore.getRybakNPC().getPlayerPostep(uuid));
            document.put("RYBAK_SRDEF", rpgcore.getRybakNPC().getPlayerRybakSredniDef(uuid));
            document.put("RYBAK_DDMG", rpgcore.getRybakNPC().getPlayerRybakDodatkowyDMG(uuid));
            document.put("RYBAK_BLOK", rpgcore.getRybakNPC().getPlayerRybakBlok(uuid));
            document.put("Akcesoria", Utils.itemStackArrayToBase64(rpgcore.getAkcesoriaManager().getAllAkcesoria(uuid)));
            document.put("Targ", Utils.toBase64(rpgcore.getTargManager().getPlayerTarg(uuid)));
            collection.insert(document);
        }
        collection = database.getCollection("spawn");
        BasicDBObject document = new BasicDBObject();
        document.put("world", rpgcore.getSpawnManager().getSpawn().getWorld().getName());
        document.put("x", rpgcore.getSpawnManager().getSpawn().getX());
        document.put("y", rpgcore.getSpawnManager().getSpawn().getY());
        document.put("z", rpgcore.getSpawnManager().getSpawn().getZ());
        document.put("yaw", rpgcore.getSpawnManager().getSpawn().getYaw());
        document.put("pitch", rpgcore.getSpawnManager().getSpawn().getPitch());
        collection.insert(document);
        pool.closePool();
    }*/
}
