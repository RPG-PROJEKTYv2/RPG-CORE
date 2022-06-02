package rpg.rpgcore.database;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;
import rpg.rpgcore.RPGCORE;



public class RedisPoolManager {

    private final RPGCORE rpgcore;


    /*private JedisPoolConfig buildPoolConfig() {
        final JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(128);
        poolConfig.setMaxIdle(128);
        poolConfig.setMinIdle(16);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        poolConfig.setTestWhileIdle(true);
        poolConfig.setMinEvictableIdleTimeMillis(Duration.ofSeconds(60).toMillis());
        poolConfig.setTimeBetweenEvictionRunsMillis(Duration.ofSeconds(30).toMillis());
        poolConfig.setNumTestsPerEvictionRun(3);
        poolConfig.setBlockWhenExhausted(true);
        return poolConfig;
    }

    final JedisPool jedisPool = new JedisPool(buildPoolConfig(), "127.0.0.1");*/
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
