package rpg.rpgcore;

import org.bukkit.plugin.java.JavaPlugin;
import rpg.rpgcore.commands.BanCommand;
import rpg.rpgcore.commands.Spawn;
import rpg.rpgcore.commands.Teleport;
import rpg.rpgcore.commands.TeleportCoords;
import rpg.rpgcore.database.CreateTables;
import rpg.rpgcore.database.SQLManager;
import rpg.rpgcore.managers.SpawnManager;
import rpg.rpgcore.managers.TeleportManager;
import rpg.rpgcore.utils.Config;

public final class RPGCORE extends JavaPlugin {

    private final Config config = new Config(this);
    private SpawnManager spawn;
    private SQLManager sql;
    private CreateTables createTables;
    private TeleportManager teleportManager;

    public void onEnable() {
        this.config.createConfig();
        this.initDatabase();
        this.initManagers();

        this.createTables.createTables();
        this.sql.loadAll();

        this.getCommand("teleport").setExecutor(new Teleport(this));
        this.getCommand("teleportcoords").setExecutor(new TeleportCoords(this));
        this.getCommand("spawn").setExecutor(new Spawn(this));
        this.getCommand("ban").setExecutor(new BanCommand());

    }

    public void onDisable() {
        this.sql.onDisable();
        this.spawn.setSpawn(null);
    }

    private void initDatabase() {
        this.sql = new SQLManager(this);
        this.createTables = new CreateTables(this);
    }

    private void initManagers() {
        this.spawn = new SpawnManager();
        this.teleportManager = new TeleportManager();
    }

    public SQLManager getSQLManager() {
        return sql;
    }

    public SpawnManager getSpawnManager() {
        return spawn;
    }

    public TeleportManager getTeleportManager() {
        return teleportManager;
    }
}
