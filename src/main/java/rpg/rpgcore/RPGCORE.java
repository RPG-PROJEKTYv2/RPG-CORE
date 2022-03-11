package rpg.rpgcore;

import org.bukkit.plugin.java.JavaPlugin;
import rpg.rpgcore.commands.*;
import rpg.rpgcore.database.CreateTables;
import rpg.rpgcore.database.SQLManager;
import rpg.rpgcore.listeners.PlayerJoinListener;
import rpg.rpgcore.listeners.PlayerQuitListener;
import rpg.rpgcore.managers.*;
import rpg.rpgcore.utils.Config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public final class RPGCORE extends JavaPlugin {

    private final Config config = new Config(this);
    private SpawnManager spawn;
    private SQLManager sql;
    private CreateTables createTables;
    private TeleportManager teleportManager;
    private BanManager banManager;
    private VanishManager vanishManager;
    private NMSManager nmsManager;


    private final ArrayList<UUID> players = new ArrayList<>();
    private final HashMap<String, UUID> playerUUID = new HashMap<>();
    private final HashMap<UUID, String> playerName = new HashMap<>();
    private final HashMap<UUID, String> playerBanInfo = new HashMap<>();

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
        this.getCommand("vanish").setExecutor(new Vanish(this));

        this.getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        this.getServer().getPluginManager().registerEvents(new PlayerQuitListener(this), this);

    }

    public void onDisable() {
        this.sql.onDisable();
        this.spawn.setSpawn(null);
        this.removeAllPlayers();
    }

    private void initDatabase() {
        this.sql = new SQLManager(this);
        this.createTables = new CreateTables(this);
    }

    private void initManagers() {
        this.spawn = new SpawnManager();
        this.teleportManager = new TeleportManager();
        this.banManager = new BanManager(this);
        this.vanishManager = new VanishManager();
        this.nmsManager = new NMSManager();
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

    public BanManager getBanManager() {
        return this.banManager;
    }

    public VanishManager getVanishManager() {
        return vanishManager;
    }

    public NMSManager getNmsManager() {
        return nmsManager;
    }

    public UUID getPlayerUUID(final String playerName) {
        return this.playerUUID.get(playerName);
    }

    public String getPlayerName(final UUID uuid) {
        return this.playerName.get(uuid);
    }

    public ArrayList<UUID> getPlayers() {
        return this.players;
    }

    public void createPlayer(final String playerName, final UUID playerUUID, final String banInfo) {
        this.players.add(playerUUID);
        this.playerUUID.put(playerName, playerUUID);
        this.playerName.put(playerUUID, playerName);
        this.setPlayerBanInfo(playerUUID, banInfo);
    }

    public void removeAllPlayers() {
        this.playerName.clear();
        this.playerUUID.clear();
        this.players.clear();
        this.playerBanInfo.clear();
    }

    public void setPlayerBanInfo(final UUID uuid, final String banInfo) {
        this.playerBanInfo.put(uuid, banInfo);
    }

    public void updatePlayerBanInfo(final UUID uuid, final String banInfo) {
        this.playerBanInfo.replace(uuid, banInfo);
    }

    public String getPlayerBanInfo(final UUID uuid) {
        return this.playerBanInfo.get(uuid);
    }

    public boolean isBanned(final UUID uuid) {
        return !(getPlayerBanInfo(uuid).equalsIgnoreCase("false"));
    }
}
