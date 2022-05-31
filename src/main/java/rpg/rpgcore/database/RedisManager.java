package rpg.rpgcore.database;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.managers.SpawnManager;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
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
                this.setSpawn(new Location(Bukkit.getWorld(spawn.get("world")), Double.parseDouble(spawn.get("x")), Double.parseDouble(spawn.get("y")), Double.parseDouble(spawn.get("z")), Float.parseFloat(spawn.get("yaw")), Float.parseFloat(spawn.get("pitch"))));
            } else {
                this.setFirstSpawn();
            }

            List<String> playerMap = j.lrange("players", 0, j.llen("players") - 1);
            for (String s : playerMap) {
                UUID playerUUID = UUID.fromString(s);
                System.out.println(playerUUID);
                Map<String, String> singlePlayerStats = j.hgetAll(s);


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

    public void setSpawn(final Location spawn) {

        final String w = spawn.getWorld().getName();
        final double x = spawn.getX();
        final double y = spawn.getY();
        final double z = spawn.getZ();
        final float yaw = spawn.getYaw();
        final float pitch = spawn.getPitch();

        Jedis j = null;
        try {
            j = pool.getPool();

            j.hset("spawn", "world", w);
            j.hset("spawn", "x", String.valueOf(x));
            j.hset("spawn", "y", String.valueOf(y));
            j.hset("spawn", "z", String.valueOf(z));
            j.hset("spawn", "yaw", String.valueOf(yaw));
            j.hset("spawn", "pitch", String.valueOf(pitch));

            final Location newLocspawn = new Location(Bukkit.getWorld(w), x, y, z, yaw, pitch);

            rpgcore.getSpawnManager().setSpawn(newLocspawn);
        } finally {
            pool.getPool().close();
        }
    }

    public void createPlayer(final String nick, final UUID uuid, final String banInfo, final String muteInfo) {
        Jedis j = null;
        try {
            j = pool.getPool();
            String uuidToString = String.valueOf(uuid);
            j.rpush("players", uuidToString);

            Map<String, String> playerInfoMap = new HashMap<>();

            playerInfoMap.put("nick", nick);
            playerInfoMap.put("kasa", String.valueOf(100.0));
            playerInfoMap.put("lvl", String.valueOf(1));
            playerInfoMap.put("exp", String.valueOf(0.0));
            playerInfoMap.put("banInfo", String.valueOf(banInfo));
            playerInfoMap.put("muteInfo", String.valueOf(muteInfo));
            playerInfoMap.put("punishmentHistory", "");
            playerInfoMap.put("osMoby", String.valueOf(0));
            playerInfoMap.put("osMobyAccept", "false,false,false,false,false,false,false,false,false,false");
            playerInfoMap.put("osLudzie", String.valueOf(0));
            playerInfoMap.put("osLudzieAccept", "false,false,false,false,false,false,false,false,false,false");
            playerInfoMap.put("osSakwy", String.valueOf(0));
            playerInfoMap.put("osSakwyAccept", "false,false,false,false,false,false,false,false,false,false");
            playerInfoMap.put("osNiesy", String.valueOf(0));
            playerInfoMap.put("osNiesyAccept", "false,false,false,false,false,false,false,false,false,false");
            playerInfoMap.put("osRybak", String.valueOf(0));
            playerInfoMap.put("osRybakAccept", "false,false,false,false,false,false,false,false,false,false");
            playerInfoMap.put("osDrwal", String.valueOf(0));
            playerInfoMap.put("osDrwalAccept", "false,false,false,false,false,false,false,false,false,false");
            playerInfoMap.put("osGornik", String.valueOf(0));
            playerInfoMap.put("osGornikAccept", "false,false,false,false,false,false,false,false,false,false");
            playerInfoMap.put("BAO_BONUSY", "Brak Bonusu,Brak Bonusu,Brak Bonusu,Brak Bonusu");
            playerInfoMap.put("BAO_WARTOSCI", "0,0,0,0,0");
            playerInfoMap.put("RYBAK_MISJE", "false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false");
            playerInfoMap.put("RYBAK_POSTEP", String.valueOf(0));
            playerInfoMap.put("RYBAK_SRDMG", String.valueOf(0.0));
            playerInfoMap.put("RYBAK_SRDEF", String.valueOf(0.0));
            playerInfoMap.put("RYBAK_DDMG", String.valueOf(0.0));
            playerInfoMap.put("RYBAK_BLOK", String.valueOf(0.0));
            playerInfoMap.put("Akcesoria", Utils.itemStackArrayToBase64(rpgcore.getAkcesoriaManager().getAllAkcesoria(uuid)));
            playerInfoMap.put("Targ", Utils.toBase64(rpgcore.getTargManager().getPlayerTarg(uuid)));

            j.hset(uuidToString, playerInfoMap);

            rpgcore.getPlayerManager().createPlayer(nick, uuid, "false", "false", "", 1, 0.0, 0, 0, 0, 0, 0, 0, 0, "false,false,false,false,false,false,false,false,false,false", "false,false,false,false,false,false,false,false,false,false", "false,false,false,false,false,false,false,false,false,false", "false,false,false,false,false,false,false,false,false,false", "false,false,false,false,false,false,false,false,false,false", "false,false,false,false,false,false,false,false,false,false", "false,false,false,false,false,false,false,false,false,false", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 100.0);


            rpgcore.getBaoManager().updateBaoBonusy(uuid, "Brak Bonusu,Brak Bonusu,Brak Bonusu,Brak Bonusu,Brak Bonusu");
            rpgcore.getBaoManager().updateBaoBonusyWartosci(uuid, "0,0,0,0,0");
            rpgcore.getRybakNPC().setPlayerRybakMisje(uuid, "false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false");
            rpgcore.getRybakNPC().setPlayerPostep(uuid, 0);
            rpgcore.getRybakNPC().setPlayerRybakSredniDMG(uuid, 0.0);
            rpgcore.getRybakNPC().setPlayerRybakSredniDef(uuid, 0.0);
            rpgcore.getRybakNPC().setPlayerRybakBlok(uuid, 0.0);
            rpgcore.getRybakNPC().setPlayerRybakDodatkowyDMG(uuid, 0.0);

        } finally {
            pool.getPool().close();
        }
    }

    public void banPlayer(final UUID uuid, final String banInfo) {
        Jedis j = null;
        try {
            j = pool.getPool();

            j.hset(String.valueOf(uuid), "banInfo", banInfo);

            rpgcore.getPlayerManager().updatePlayerBanInfo(uuid, banInfo);
        } finally {
            pool.getPool().close();
        }
    }


    public void mutePlayer(final UUID uuid, final String muteInfo) {
        Jedis j = null;
        try {
            j = pool.getPool();

            j.hset(String.valueOf(uuid), "muteInfo", muteInfo);

            rpgcore.getPlayerManager().updatePlayerMuteInfo(uuid, muteInfo);
        } finally {
            pool.getPool().close();
        }
    }

    public void unBanPlayer(final UUID uuid) {
        Jedis j = null;
        try {
            j = pool.getPool();

            j.hset(String.valueOf(uuid), "banInfo", "false");

            rpgcore.getPlayerManager().updatePlayerBanInfo(uuid, "false");
        } finally {
            pool.getPool().close();
        }
    }


    public void unMutePlayer(final UUID uuid) {
        Jedis j = null;
        try {
            j = pool.getPool();

            j.hset(String.valueOf(uuid), "muteInfo", "false");
            rpgcore.getPlayerManager().updatePlayerMuteInfo(uuid, "false");
        } finally {
            pool.getPool().close();
        }
    }

    public void setPunishmentHistory(final UUID uuid, final String punishmentHistory) {
        Jedis j = null;
        try {
            j = pool.getPool();

            j.hset(String.valueOf(uuid), "punishmentHistory", punishmentHistory);

            rpgcore.getPlayerManager().updatePlayerPunishmentHistory(uuid, punishmentHistory);
        } finally {
            pool.getPool().close();
        }
    }

    public void updatePlayerLvl(final UUID uuid, final int lvl) {
        Jedis j = null;
        try {
            j = pool.getPool();

            j.hset(String.valueOf(uuid), "lvl", String.valueOf(lvl));
        } finally {
            pool.getPool().close();
        }
    }

    public void updatePlayerExp(final UUID uuid, final double exp) {
        Jedis j = null;
        try {
            j = pool.getPool();

            j.hset(String.valueOf(uuid), "exp", String.valueOf(exp));
        } finally {
            pool.getPool().close();
        }
    }

    public void updatePlayerBaoBonusy(final UUID uuid, final String baoBonusy) {
        Jedis j = null;
        try {
            j = pool.getPool();
            j.hset(String.valueOf(uuid), "BAO_BONUSY", baoBonusy);
        } finally {
            pool.getPool().close();
        }
    }

    public void updatePlayerBaoWartosci(final UUID uuid, final String baoWartosci) {
        Jedis j = null;
        try {
            j = pool.getPool();
            j.hset(String.valueOf(uuid), "BAO_WARTOSCI", baoWartosci);
        } finally {
            pool.getPool().close();
        }
    }

    public void updatePlayerOsAccept(final UUID uuid, final String osMobyAccept, final String osLudzieAccept, final String osSakwyAccept, final String osNiesyAccept, final String osRybakAccept, final String osDrwalAccept, final String osGornikAccept) {
        Jedis j = null;
        try {
            j = pool.getPool();
            j.hset(String.valueOf(uuid), "osMobyAccept", osMobyAccept);
            j.hset(String.valueOf(uuid), "osLudzieAccept", osLudzieAccept);
            j.hset(String.valueOf(uuid), "osSakwyAccept", osSakwyAccept);
            j.hset(String.valueOf(uuid), "osNiesyAccept", osNiesyAccept);
            j.hset(String.valueOf(uuid), "osRybakAccept", osRybakAccept);
            j.hset(String.valueOf(uuid), "osDrwalAccept", osDrwalAccept);
            j.hset(String.valueOf(uuid), "osGornikAccept", osGornikAccept);
        } finally {
            pool.getPool().close();
        }
    }

    public void updatePlayerOs(final UUID uuid, final Integer osMoby, final Integer osLudzie, final Integer osSakwy, final Integer osNiesy, final Integer osRybak, final Integer osDrwal, final Integer osGornik) {
        Jedis j = null;
        try {
            j = pool.getPool();
            j.hset(String.valueOf(uuid), "osMoby", String.valueOf(osMoby));
            j.hset(String.valueOf(uuid), "osLudzie", String.valueOf(osLudzie));
            j.hset(String.valueOf(uuid), "osSakwy", String.valueOf(osSakwy));
            j.hset(String.valueOf(uuid), "osNiesy", String.valueOf(osNiesy));
            j.hset(String.valueOf(uuid), "osRybak", String.valueOf(osRybak));
            j.hset(String.valueOf(uuid), "osDrwal", String.valueOf(osDrwal));
            j.hset(String.valueOf(uuid), "osGornik", String.valueOf(osGornik));
        } finally {
            pool.getPool().close();
        }
    }

    public void updatePlayerAkcesoria(final UUID uuid, final String akcesoriaInString) {
        Jedis j = null;
        try {
            j = pool.getPool();
            j.hset(String.valueOf(uuid), "Akcesoria", akcesoriaInString);
        } finally {
            pool.getPool().close();
        }
    }

    public void updatePlayerTarg(final UUID uuid, final String targiInString) {
        Jedis j = null;
        try {
            j = pool.getPool();
            j.hset(String.valueOf(uuid), "Targ", targiInString);
        } finally {
            pool.getPool().close();
        }
    }

    public void updatePlayerKasa(final UUID uuid, final double kasa) {
        Jedis j = null;
        try {
            j = pool.getPool();
            j.hset(String.valueOf(uuid), "kasa", Utils.kasaFormat.format(kasa));
            System.out.println(String.format("%.2f" + kasa));
        } finally {
            pool.getPool().close();
        }
    }
    final GenericObjectPoolConfig jedisPoolConfig = new GenericObjectPoolConfig();
    public void test() {
        jedisPoolConfig.setMaxTotal(100);
        jedisPoolConfig.setMaxIdle(100);
        jedisPoolConfig.setMaxWaitMillis(-1);
        jedisPoolConfig.setBlockWhenExhausted(false);
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, "130.61.33.79", 6379, -1, "");
//Run the following command:
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            //Specific commands
            jedis.set("test", "testConfigu");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            //In JedisPool mode, the Jedis resource is returned to the resource pool.
            if (jedis != null)
                jedis.close();
        }
    }

    public void updatePlayerRybakPostep(final UUID uuid, final int postep) {
        Jedis j = null;
        try {
            j = pool.getPool();
            j.hset(String.valueOf(uuid), "RYBAK_POSTEP", String.valueOf(postep));
        } finally {
            pool.getPool().close();
        }
    }

    public void updatePlayerRybakMisje(final UUID uuid, final String misje) {
        Jedis j = null;
        try {
            j = pool.getPool();
            j.hset(String.valueOf(uuid), "RYBAK_MISJE", misje);
        } finally {
            pool.getPool().close();
        }
    }

    public void updatePlayerRybakNagrody(final UUID uuid, final String srdmg, final String srdef, final String ddmg, final String blok) {
        Jedis j = null;
        try {
            j = pool.getPool();
            j.hset(String.valueOf(uuid), "RYBAK_SRDMG", srdmg);
            j.hset(String.valueOf(uuid), "RYBAK_SRDEF", srdef);
            j.hset(String.valueOf(uuid), "RYBAK_DDMG", ddmg);
            j.hset(String.valueOf(uuid), "RYBAK_BLOK", blok);
        } finally {
            pool.getPool().close();
        }
    }

    public void onDisable() {
        pool.getPool().close();
    }

}
