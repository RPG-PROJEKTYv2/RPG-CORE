package rpg.rpgcore.database;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import redis.clients.jedis.Jedis;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.managers.SpawnManager;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

public class RedisManager {

    private final RPGCORE rpgcore;
    private final RedisPoolManager pool;

    public RedisManager(final RPGCORE rpgcore) {
        this.pool = new RedisPoolManager(rpgcore);
        this.rpgcore = rpgcore;
    }

    public void test2() {
        Jedis j = null;
        try {
            j = pool.getPool();
            j.set("Test", "test");
        } finally {
            pool.getPool().close();
        }
    }

    private void setFirstSpawn() {

        final String w = "world";
        final double x = SpawnManager.defaultSpawnX;
        final double y = SpawnManager.defaultSpawnY;
        final double z = SpawnManager.defaultSpawnZ;

        Jedis j = null;

        try {

            j = pool.getPool();

            j.hset("spawn", "world", w);
            j.hset("spawn", "x", String.valueOf(x));
            j.hset("spawn", "y", String.valueOf(y));
            j.hset("spawn", "z", String.valueOf(z));
            j.hset("spawn", "yaw", String.valueOf(0.0F));
            j.hset("spawn", "pitch", String.valueOf(0.0F));

            final Location newLocspawn = new Location(Bukkit.getWorld(w), x, y, z, 0.0F, 0.0F);
            rpgcore.getSpawnManager().setSpawn(newLocspawn);



        } finally {
            pool.getPool().close();
        }

    }


    public void loadAll() {
        Jedis j = pool.getPool();

        try {

            if (j.hexists("spawn", "world")) {
                final Map<String, String> spawn = j.hgetAll("spawn");
                new Location(Bukkit.getWorld(spawn.get("world")), Double.parseDouble(spawn.get("x")), Double.parseDouble(spawn.get("y")), Double.parseDouble(spawn.get("z")), Float.parseFloat(spawn.get("yaw")), Float.parseFloat(spawn.get("pitch")));
            } else {
                this.setFirstSpawn();
            }

            Map<String, String> playerMap = j.hgetAll("players");
            for (Map.Entry<String, String> entry : playerMap.entrySet()) {
                UUID playerUUID = UUID.fromString(entry.getValue());
                System.out.println(playerUUID);
                Map<String, String> singlePlayerStats = j.hgetAll(entry.getValue());

                rpgcore.getPlayerManager().createPlayer(
                        singlePlayerStats.get("nick"),
                        playerUUID,
                        singlePlayerStats.get("banInfo"),
                        singlePlayerStats.get("muteInfo"),
                        singlePlayerStats.get("punishmentHistory"),
                        Integer.parseInt(singlePlayerStats.get("lvl")),
                        Double.parseDouble(singlePlayerStats.get("exp")),
                        Integer.parseInt(singlePlayerStats.get("osMoby")),
                        Integer.parseInt(singlePlayerStats.get("osLudzie")),
                        Integer.parseInt(singlePlayerStats.get("osSakwy")),
                        Integer.parseInt(singlePlayerStats.get("osNiesy")),
                        Integer.parseInt(singlePlayerStats.get("osRybak")),
                        Integer.parseInt(singlePlayerStats.get("osDrwal")),
                        Integer.parseInt(singlePlayerStats.get("osDrwal")),
                        singlePlayerStats.get("osMobyAccept"),
                        singlePlayerStats.get("osLudzieAccept"),
                        singlePlayerStats.get("osSakwyAccept"),
                        singlePlayerStats.get("osNiesyAccept"),
                        singlePlayerStats.get("osRybakAccept"),
                        singlePlayerStats.get("osDrwalAccept"),
                        singlePlayerStats.get("osGornikAccept"),
                        0.0,
                        0.0,
                        0.0,
                        0.0,
                        0.0,
                        0.0,
                        0.0,
                        0.0,
                        0.0,
                        0.0,
                        0.0,
                        0.0,
                        0.0,
                        Double.parseDouble(singlePlayerStats.get("kasa")));

                rpgcore.getBaoManager().updateBaoBonusy(
                        playerUUID,
                        singlePlayerStats.get("BAO_BONUSY"));

                rpgcore.getBaoManager().updateBaoBonusyWartosci(
                        playerUUID,
                        singlePlayerStats.get("BAO_WARTOSCI"));

                rpgcore.getRybakNPC().setPlayerRybakMisje(
                        playerUUID,
                        singlePlayerStats.get("RYBAK_MISJE"));

                rpgcore.getRybakNPC().setPlayerPostep(
                        playerUUID,
                        Integer.parseInt(singlePlayerStats.get("RYBAK_POSTEP")));

                rpgcore.getRybakNPC().setPlayerRybakSredniDMG(
                        playerUUID,
                        Double.parseDouble(singlePlayerStats.get("RYBAK_SRDMG")));

                rpgcore.getRybakNPC().setPlayerRybakSredniDef(
                        playerUUID,
                        Double.parseDouble(singlePlayerStats.get("RYBAK_SRDEF")));

                rpgcore.getRybakNPC().setPlayerRybakDodatkowyDMG(
                        playerUUID,
                        Double.parseDouble(singlePlayerStats.get("RYBAK_DDMG")));

                rpgcore.getRybakNPC().setPlayerRybakBlok(
                        playerUUID,
                        Double.parseDouble(singlePlayerStats.get("RYBAK_BLOK")));

                rpgcore.getAkcesoriaManager().createAkcesoriaGUI(playerUUID, Utils.itemStackArrayFromBase64(singlePlayerStats.get("Akcesoria")));
                rpgcore.getTargManager().putPlayerInTargMap(playerUUID, Utils.fromBase64(singlePlayerStats.get("Targ"), "&f&lTarg gracza &3" + rpgcore.getPlayerManager().getPlayerName(playerUUID)));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            pool.getPool().close();
        }


    }
}
