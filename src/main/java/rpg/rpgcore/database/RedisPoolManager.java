package rpg.rpgcore.database;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;
import rpg.rpgcore.RPGCORE;



public class RedisPoolManager {

    private final RPGCORE rpgcore;
    private Jedis pool;

    public RedisPoolManager(final RPGCORE rpgcore) {
        pool = new Jedis("localhost", Integer.parseInt("6379"));
        this.rpgcore = rpgcore;
    }



    public Jedis getPool() throws JedisConnectionException {
        return pool;
    }

    public void onDisable() {
        pool.close();
    }

}
