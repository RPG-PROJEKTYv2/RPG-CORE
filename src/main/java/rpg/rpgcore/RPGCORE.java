package rpg.rpgcore;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import rpg.rpgcore.database.CreateTables;
import rpg.rpgcore.database.SQLManager;
import rpg.rpgcore.utils.Config;

public final class RPGCORE extends JavaPlugin {

    private Location spawn;
    private final Config config = new Config(this);
    private SQLManager sql;
    private CreateTables createTables;

    public void onEnable() {
        config.createConfig();

        initDatabase();
        createTables.createTables();
        sql.loadAll();

        if (spawn == null) {
            final Location locSpawn = Bukkit.getWorld("world").getSpawnLocation();
            Bukkit.getScheduler().runTaskAsynchronously(this, () -> this.getSQLManager().firstLocationSpawn(locSpawn));
        }

    }

    public void onDisable() {
        sql.onDisable();
    }

    private void initDatabase() {
        sql = new SQLManager(this);
        createTables = new CreateTables(this);
    }

    public SQLManager getSQLManager() {
        return sql;
    }

    public Location getSpawn() {
        return spawn;
    }

    public void setSpawn(Location spawn) {
        this.spawn = spawn;
    }

}
