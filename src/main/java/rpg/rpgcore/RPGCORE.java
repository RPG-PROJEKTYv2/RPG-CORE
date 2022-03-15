package rpg.rpgcore;

import io.lumine.xikage.mythicmobs.io.MythicConfig;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import rpg.rpgcore.commands.*;
import rpg.rpgcore.database.CreateTables;
import rpg.rpgcore.database.SQLManager;
import rpg.rpgcore.listeners.EntityDeathListener;
import rpg.rpgcore.listeners.PlayerJoinListener;
import rpg.rpgcore.listeners.PlayerQuitListener;
import rpg.rpgcore.managers.*;
import rpg.rpgcore.utils.Config;
import rpg.rpgcore.utils.Utils;

import java.util.Map;
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
    private GodManager godManager;
    private PlayerManager playerManager;
    private LvlManager lvlManager;


    private int i=1;

    public void onEnable() {
        this.config.createConfig();
        this.initDatabase();
        this.initManagers();

        this.createTables.createTables();
        this.sql.loadAll();

        this.getLvlManager().loadAllReqExp();
        this.getLvlManager().loadExpForAllMobs();
        this.autoMessage();
        this.sendActionBar();


        this.getCommand("teleport").setExecutor(new Teleport(this));
        this.getCommand("teleportcoords").setExecutor(new TeleportCoords(this));
        this.getCommand("spawn").setExecutor(new Spawn(this));
        this.getCommand("ban").setExecutor(new Ban(this));
        this.getCommand("unban").setExecutor(new UnBan(this));
        this.getCommand("kick").setExecutor(new Kick(this));
        this.getCommand("vanish").setExecutor(new Vanish(this));
        this.getCommand("god").setExecutor(new God(this));
        this.getCommand("speed").setExecutor(new Speed());
        this.getCommand("fly").setExecutor(new Fly());
        this.getCommand("history").setExecutor(new History(this));
        this.getCommand("back").setExecutor(new Back(this));

        this.getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        this.getServer().getPluginManager().registerEvents(new PlayerQuitListener(this), this);
        this.getServer().getPluginManager().registerEvents(new EntityDeathListener(this), this);

        this.updateAllPlayerInfo();
    }

    public void onDisable() {
        this.sql.onDisable();
        this.spawn.setSpawn(null);
        this.playerManager.removeAllPlayers();
        this.getLvlManager().unLoadAll();
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
        this.godManager = new GodManager();
        this.playerManager = new PlayerManager(this);
        this.lvlManager = new LvlManager(this);
    }

    private void updateAllPlayerInfo(){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            Bukkit.broadcastMessage(Utils.format(Utils.SERVERNAME + "&6Rozpoczeto update do bazy daynch..."));
            for (Map.Entry<UUID, Integer> entry : this.getPlayerManager().getPlayerLvl().entrySet()) {
                Bukkit.getScheduler().runTaskAsynchronously(this, () -> this.getSQLManager().updatePlayerLvl(entry.getKey(), entry.getValue()));
            }
            for (Map.Entry<UUID, Double> entry : this.getPlayerManager().getPlayerExp().entrySet()) {
                Bukkit.getScheduler().runTaskAsynchronously(this, () -> this.getSQLManager().updatePlayerExp(entry.getKey(), entry.getValue()));
            }
            System.out.println("[rpg.core] Pomyslnie zapisano dane wszytskich graczy!!!");
            Bukkit.broadcastMessage(Utils.format(Utils.SERVERNAME + "&aUpdate zakonczony pomyslnie!"));
        }, 6000L, 200L);
    }

    private void sendActionBar(){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            for (Player target : Bukkit.getOnlinePlayers()){
                if (getGodManager().containsPlayer(target.getUniqueId()) && this.vanishManager.containsPlayer(target.getUniqueId())) {
                    getNmsManager().sendActionBar(target, getNmsManager().makeActionBar("&3&lVanish &8| &5&lGOD"));
                } else if (getGodManager().containsPlayer(target.getUniqueId())) {
                    getNmsManager().sendActionBar(target, getNmsManager().makeActionBar("&5&lGOD"));
                } else if (getVanishManager().containsPlayer(target.getUniqueId())) {
                    getNmsManager().sendActionBar(target, getNmsManager().makeActionBar("&3&lVanish"));
                }
            }
        }, 150L, 50L);
    }

    private void autoMessage(){

        if (getConfig().getBoolean("auto_message")) {
            int sciezki = getConfig().getConfigurationSection("auto_messages").getKeys(false).size();
            int time = getConfig().getInt("auto_message_time");
            this.getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
                if (i > sciezki) {
                    i = 1;
                }
                Bukkit.broadcastMessage(Utils.SERVERNAME + Utils.format(getConfig().getConfigurationSection("auto_messages").getString("auto_message_" + i)));
                i++;
            }, 1L, time);
        } else {
            System.out.println("[rpg.core] Automessage jest aktualnie wylaczany. Zmien opcje auto_message na true w pliku config.yml i przeladuj serwer zeby ja wlaczyc!");
        }
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

    public GodManager getGodManager() {
        return godManager;
    }

    public PlayerManager getPlayerManager() {return playerManager;}

    public LvlManager getLvlManager() {
        return lvlManager;
    }
}
