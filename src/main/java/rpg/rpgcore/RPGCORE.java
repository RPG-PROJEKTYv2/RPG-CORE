package rpg.rpgcore;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import rpg.rpgcore.commands.*;
import rpg.rpgcore.database.CreateTables;
import rpg.rpgcore.database.SQLManager;
import rpg.rpgcore.listeners.*;
import rpg.rpgcore.managers.*;
import rpg.rpgcore.managers.god.GodManager;
import rpg.rpgcore.managers.npc.DuszologNPC;
import rpg.rpgcore.managers.npc.TeleporterNPC;
import rpg.rpgcore.managers.vanish.VanishManager;
import rpg.rpgcore.utils.Config;
import rpg.rpgcore.utils.Utils;

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
    private DamageManager damageManager;
    private ChatManager chatManager;
    private BAOManager baoManager;
    private OsManager osManager;
    private AkcesoriaManager akcesoriaManager;
    private PomocManager pomocManager;
    private MuteManager muteManager;
    private MSGManager msgManager;
    private TradeManager tradeManager;
    private DuszologNPC duszologNPC;
    private TargManager targManager;
    private TeleporterNPC teleporterNPC;

    private int i = 1;

    public void onEnable() {
        this.config.createConfig();
        this.initDatabase();
        this.initManagers();

        this.createTables.createTables();
        this.sql.loadAll();

        this.getLvlManager().loadAllReqExp();
        this.getLvlManager().loadExpForAllMobs();
        this.getOsManager().loadAllRequiredOs();
        this.autoMessage();


        this.getCommand("teleport").setExecutor(new Teleport(this));
        this.getCommand("teleportcoords").setExecutor(new TeleportCoords(this));
        this.getCommand("spawn").setExecutor(new Spawn(this));
        this.getCommand("ban").setExecutor(new Ban(this));
        this.getCommand("unban").setExecutor(new UnBan(this));
        this.getCommand("kick").setExecutor(new Kick(this));
        this.getCommand("vanish").setExecutor(new Vanish(this));
        this.getCommand("god").setExecutor(new God(this));
        this.getCommand("speed").setExecutor(new Speed());
        this.getCommand("fly").setExecutor(new Fly(this));
        this.getCommand("history").setExecutor(new History(this));
        this.getCommand("back").setExecutor(new Back(this));
        this.getCommand("lvl").setExecutor(new Lvl(this));
        this.getCommand("gamemode").setExecutor(new Gm());
        this.getCommand("heal").setExecutor(new Heal(this));
        this.getCommand("tempban").setExecutor(new TempBan(this));
        this.getCommand("osiagniecia").setExecutor(new Osiagniecia(this));
        this.getCommand("setdmg").setExecutor(new SetDmg(this));
        this.getCommand("akcesoria").setExecutor(new Akcesoria(this));
        this.getCommand("pomoc").setExecutor(new Pomoc(this));
        this.getCommand("sprawdzmojebonusy").setExecutor(new SprawdzMojeBonusy(this));
        this.getCommand("mute").setExecutor(new Mute(this));
        this.getCommand("unmute").setExecutor(new UnMute(this));
        this.getCommand("tempmute").setExecutor(new TempMute(this));
        this.getCommand("message").setExecutor(new Message(this));
        this.getCommand("reply").setExecutor(new Reply(this));
        this.getCommand("targ").setExecutor(new Targ(this));
        this.getCommand("kasa").setExecutor(new Kasa(this));
        this.getCommand("wyplac").setExecutor(new Wyplac(this));
        this.getCommand("wystaw").setExecutor(new Wystaw());
//        this.getCommand("testanimation").setExecutor(new TestAnimation(this));

        this.getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        this.getServer().getPluginManager().registerEvents(new PlayerQuitListener(this), this);
        this.getServer().getPluginManager().registerEvents(new EntityDeathListener(this), this);
        this.getServer().getPluginManager().registerEvents(new EntityDamageEntityListener(this), this);
        this.getServer().getPluginManager().registerEvents(new PlayerChatListener(this), this);
        this.getServer().getPluginManager().registerEvents(chatManager, this);
        this.getServer().getPluginManager().registerEvents(new EntityDamageListener(this), this);
        this.getServer().getPluginManager().registerEvents(new PlayerInteractListener(this), this);
        this.getServer().getPluginManager().registerEvents(new PlayerInteractEntityListener(this), this);
        this.getServer().getPluginManager().registerEvents(new PlayerInventoryClickListener(this), this);
        this.getServer().getPluginManager().registerEvents(new PlayerInventoryCloseListener(this), this);
        this.getServer().getPluginManager().registerEvents(new InventoryDragListener(this), this);
        this.getServer().getPluginManager().registerEvents(new BlockBreakListener(this), this);
        this.getServer().getPluginManager().registerEvents(new BlockPlaceListener(this), this);

        this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new BackupRunnable(this), 6000L, 6000L);

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
        this.teleportManager = new TeleportManager(this);
        this.banManager = new BanManager(this);
        this.vanishManager = new VanishManager(this);
        this.nmsManager = new NMSManager();
        this.godManager = new GodManager(this);
        this.playerManager = new PlayerManager();
        this.lvlManager = new LvlManager(this);
        this.damageManager = new DamageManager(this);
        this.chatManager = new ChatManager(this);
        this.baoManager = new BAOManager();
        this.osManager = new OsManager(this);
        this.akcesoriaManager = new AkcesoriaManager(this);
        this.pomocManager = new PomocManager();
        this.muteManager = new MuteManager(this);
        this.msgManager = new MSGManager();
        this.tradeManager = new TradeManager(this);
        this.duszologNPC = new DuszologNPC();
        this.targManager = new TargManager(this);
        this.teleporterNPC = new TeleporterNPC();
    }

    private void autoMessage() {

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

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public LvlManager getLvlManager() {
        return lvlManager;
    }

    public DamageManager getDamageManager() {
        return damageManager;
    }

    public ChatManager getChatManager() {
        return chatManager;
    }

    public BAOManager getBaoManager() {
        return baoManager;
    }

    public OsManager getOsManager() {
        return osManager;
    }

    public AkcesoriaManager getAkcesoriaManager() {
        return akcesoriaManager;
    }

    public PomocManager getPomocManager() {
        return pomocManager;
    }

    public MuteManager getMuteManager() {
        return muteManager;
    }

    public MSGManager getMsgManager() {
        return msgManager;
    }

    public TradeManager getTradeManager() {
        return tradeManager;
    }

    public DuszologNPC getDuszologNPC() {
        return duszologNPC;
    }

    public TargManager getTargManager() {
        return targManager;
    }

    public TeleporterNPC getTeleporterNPC() {
        return teleporterNPC;
    }
}
