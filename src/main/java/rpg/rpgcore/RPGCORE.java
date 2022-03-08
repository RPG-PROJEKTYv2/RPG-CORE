package rpg.rpgcore;

import org.bukkit.plugin.java.JavaPlugin;
import rpg.rpgcore.commands.*;
import rpg.rpgcore.database.CreateTables;
import rpg.rpgcore.database.SQLManager;
import rpg.rpgcore.listeners.PlayerJoinListener;
import rpg.rpgcore.listeners.PlayerQuitListener;
import rpg.rpgcore.managers.BanManager;
import rpg.rpgcore.managers.PlayerManager;
import rpg.rpgcore.managers.SpawnManager;
import rpg.rpgcore.managers.TeleportManager;
import rpg.rpgcore.utils.Config;

public final class RPGCORE extends JavaPlugin {

    private final Config config = new Config(this);
    private SpawnManager spawn;
    private SQLManager sql;
    private CreateTables createTables;
    private TeleportManager teleportManager;
    private PlayerManager playerManager;
    private BanManager banManager;

    public void onEnable() {
        this.config.createConfig();
        this.initDatabase();
        this.initManagers();

        this.createTables.createTables();
        this.sql.loadAll();

        this.getCommand("teleport").setExecutor(new Teleport(this));
        this.getCommand("teleportcoords").setExecutor(new TeleportCoords(this));
        this.getCommand("spawn").setExecutor(new Spawn(this));
        this.getCommand("ban").setExecutor(new Ban(this));
        this.getCommand("unban").setExecutor(new UnBan(this));
        this.getCommand("kick").setExecutor(new Kick(this));

        this.getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        this.getServer().getPluginManager().registerEvents(new PlayerQuitListener(), this);

    }

    public void onDisable() {
        this.sql.onDisable();
        this.spawn.setSpawn(null);
        this.playerManager.removeAllPlayers();
    }

    private void initDatabase() {
        this.sql = new SQLManager(this);
        this.createTables = new CreateTables(this);
    }

    private void initManagers() {
        this.spawn = new SpawnManager();
        this.teleportManager = new TeleportManager();
        this.playerManager = new PlayerManager();
        this.banManager = new BanManager();
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

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public BanManager getBanManager() {
        return this.banManager;
    }
}
