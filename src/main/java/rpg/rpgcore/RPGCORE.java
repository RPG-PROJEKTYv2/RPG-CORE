package rpg.rpgcore;

import org.bukkit.plugin.java.JavaPlugin;
import rpg.rpgcore.commands.Spawn;
import rpg.rpgcore.database.CreateTables;
import rpg.rpgcore.database.SQLManager;
import rpg.rpgcore.managers.SpawnManager;
import rpg.rpgcore.utils.Alerts;
import rpg.rpgcore.utils.Colorize;
import rpg.rpgcore.utils.Config;

public final class RPGCORE extends JavaPlugin {

    private SpawnManager spawn;
    private final Config config = new Config(this);
    private SQLManager sql;
    private CreateTables createTables;
    private Colorize colorize;
    private Alerts alerts;

    public String nazwaserwera;

    public void onEnable() {
        this.config.createConfig();
        this.initDatabase();
        this.initManagers();

        this.createTables.createTables();
        this.sql.loadAll();
        nazwaserwera = this.getAlerts().serverName();
        this.getCommand("spawn").setExecutor(new Spawn(this));

    }

    public void onDisable() {
        this.sql.onDisable();
        this.spawn.setSpawn(null);
    }

    private void initDatabase() {
        this.sql = new SQLManager(this);
        this.createTables = new CreateTables(this);
    }

    private void initManagers(){
        this.colorize = new Colorize();
        this.alerts = new Alerts(this, this.colorize);
        this.spawn = new SpawnManager();
    }

    public SQLManager getSQLManager() {
        return sql;
    }

    public SpawnManager getSpawnManager(){
        return spawn;
    }

    public Colorize getColorize() {
        return this.colorize;
    }

    public Alerts getAlerts() {
        return this.alerts;
    }
}
