package rpg.rpgcore;

import org.bukkit.plugin.java.JavaPlugin;
import rpg.rpgcore.commands.Spawn;
import rpg.rpgcore.commands.Teleport;
import rpg.rpgcore.commands.TeleportOnCoords;
import rpg.rpgcore.database.CreateTables;
import rpg.rpgcore.database.SQLManager;
import rpg.rpgcore.managers.SpawnManager;
import rpg.rpgcore.managers.TeleportManager;
import rpg.rpgcore.utils.Alerts;
import rpg.rpgcore.utils.Colorize;
import rpg.rpgcore.utils.Config;

public final class RPGCORE extends JavaPlugin {

    private SpawnManager spawnManager;
    private final Config config = new Config(this);
    private SQLManager sql;
    private CreateTables createTables;
    private Colorize colorize;
    private Alerts alerts;
    private TeleportManager teleportManager;

    public void onEnable() {
        this.config.createConfig();
        this.initDatabase();
        this.initManagers();

        final Spawn spawn = new Spawn(this);
        final Teleport teleport = new Teleport(this);
        final TeleportOnCoords teleportOnCoords = new TeleportOnCoords(this);

        this.createTables.createTables();
        this.sql.loadAll();
        this.getCommand("spawn").setExecutor(spawn);
        this.getCommand("teleport").setExecutor(teleport);
        this.getCommand("teleportOnoCoords").setExecutor(teleportOnCoords);

    }

    public void onDisable() {
        this.sql.onDisable();
        this.spawnManager.setSpawn(null);
    }

    private void initDatabase() {
        this.sql = new SQLManager(this);
        this.createTables = new CreateTables(this);
    }

    private void initManagers(){
        this.colorize = new Colorize();
        this.alerts = new Alerts(this.colorize);
        this.spawnManager = new SpawnManager();
        this.teleportManager = new TeleportManager(this);

    }

    public SQLManager getSQLManager() {
        return sql;
    }

    public SpawnManager getSpawnManager(){
        return spawnManager;
    }

    public Colorize getColorize() {
        return this.colorize;
    }

    public Alerts getAlerts() {
        return this.alerts;
    }

    public TeleportManager getTeleportManager(){
        return teleportManager;
    }
}
