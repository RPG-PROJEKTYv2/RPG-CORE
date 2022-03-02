package rpg.rpgcore;

import org.bukkit.plugin.java.JavaPlugin;
import rpg.rpgcore.database.SQLManager;
import rpg.rpgcore.utils.Config;

public final class RPGCORE extends JavaPlugin {

    private final Config config = new Config(this);
    private SQLManager sql;

    public void onEnable() {
        config.createConfig();

        initDatabase();
        sql.loadAll();

    }

    public void onDisable() {
        sql.onDisable();
    }

    private void initDatabase() {
        sql = new SQLManager(this);
    }

    public SQLManager getSQLManager() {
        return sql;
    }
}
