package rpg.rpgcore;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import rpg.rpgcore.admin.teleport.Teleport;
import rpg.rpgcore.admin.teleport.TeleportCoords;
import rpg.rpgcore.admin.teleport.TeleportManager;
import rpg.rpgcore.admin.ban.Ban;
import rpg.rpgcore.admin.ban.BanManager;
import rpg.rpgcore.admin.ban.TempBan;
import rpg.rpgcore.admin.ban.UnBan;
import rpg.rpgcore.admin.commands.*;
import rpg.rpgcore.admin.god.God;
import rpg.rpgcore.admin.vanish.Vanish;
import rpg.rpgcore.akcesoria.AKCESORIAInventoryClick;
import rpg.rpgcore.akcesoria.AKCESORIAPlayerInteract;
import rpg.rpgcore.akcesoria.Akcesoria;
import rpg.rpgcore.akcesoria.AkcesoriaManager;
import rpg.rpgcore.bao.BAOEntityInteract;
import rpg.rpgcore.bao.BAOInventoryClick;
import rpg.rpgcore.bao.BAOManager;
import rpg.rpgcore.bao.BAOPlayerInteract;
import rpg.rpgcore.chat.*;
import rpg.rpgcore.commands.*;
import rpg.rpgcore.commands.kosz.Kosz;
import rpg.rpgcore.commands.kosz.KoszInventoryClick;
import rpg.rpgcore.commands.kosz.KoszInventoryClose;
import rpg.rpgcore.database.MongoManager;
import rpg.rpgcore.economy.EconomyPlayerInteract;
import rpg.rpgcore.economy.Kasa;
import rpg.rpgcore.economy.Wyplac;
import rpg.rpgcore.guilds.Guild;
import rpg.rpgcore.guilds.GuildManager;
import rpg.rpgcore.guilds.GuildsInventoryClick;
import rpg.rpgcore.guilds.GuildsPlayerDamage;
import rpg.rpgcore.history.HISTORYInventoryClick;
import rpg.rpgcore.history.History;
import rpg.rpgcore.listeners.*;
import rpg.rpgcore.lvl.Lvl;
import rpg.rpgcore.lvl.LvlManager;
import rpg.rpgcore.managers.*;
import rpg.rpgcore.admin.god.GodManager;
import rpg.rpgcore.msg.MSGManager;
import rpg.rpgcore.msg.Message;
import rpg.rpgcore.msg.Reply;
import rpg.rpgcore.newTarg.NewTarg;
import rpg.rpgcore.newTarg.NewTargInventoryClick;
import rpg.rpgcore.newTarg.NewTargManager;
import rpg.rpgcore.newTarg.NewTargWystaw;
import rpg.rpgcore.npc.kowal.KowalInventoryClick;
import rpg.rpgcore.npc.kowal.KowalNPC;
import rpg.rpgcore.npc.kupiec.KupiecInventoryClick;
import rpg.rpgcore.npc.kupiec.KupiecInventoryClose;
import rpg.rpgcore.npc.kupiec.KupiecNPC;
import rpg.rpgcore.npc.magazynier.Magazyn;
import rpg.rpgcore.npc.magazynier.MagazynierInventoryClick;
import rpg.rpgcore.npc.magazynier.MagazynierInventoryClose;
import rpg.rpgcore.npc.magazynier.MagazynierNPC;
import rpg.rpgcore.npc.duszolog.DuszologInventoryClick;
import rpg.rpgcore.npc.duszolog.DuszologNPC;
import rpg.rpgcore.npc.rybak.PlayerFishListener;
import rpg.rpgcore.npc.rybak.RybakInventoryClick;
import rpg.rpgcore.npc.rybak.RybakNPC;
import rpg.rpgcore.npc.teleporter.TeleporterInventoryClick;
import rpg.rpgcore.npc.teleporter.TeleporterNPC;
import rpg.rpgcore.admin.vanish.VanishManager;
import rpg.rpgcore.npc.duszolog.DuszologPlayerInteract;
import rpg.rpgcore.os.OSInventoryClick;
import rpg.rpgcore.os.OsManager;
import rpg.rpgcore.os.Osiagniecia;
import rpg.rpgcore.pomoc.POMOCInventoryClick;
import rpg.rpgcore.pomoc.Pomoc;
import rpg.rpgcore.pomoc.PomocManager;
import rpg.rpgcore.spawn.Spawn;
import rpg.rpgcore.spawn.SpawnManager;
import rpg.rpgcore.tab.TabManager;
import rpg.rpgcore.tab.UpdateTabTask;
import rpg.rpgcore.OLDtarg.*;
import rpg.rpgcore.trade.TRADEInventoryClick;
import rpg.rpgcore.trade.TRADEInventoryClose;
import rpg.rpgcore.trade.TradeManager;
import rpg.rpgcore.utils.Config;
import rpg.rpgcore.utils.Utils;

public final class RPGCORE extends JavaPlugin {

    private final Config config = new Config(this);
    private SpawnManager spawn;
    private MongoManager mongo;
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
    private CooldownManager cooldownManager;
    private RybakNPC rybakNPC;
    private MagazynierNPC magazynierNPC;
    private GuildManager guildManager;
    private TabManager tabManager;
    private KupiecNPC kupiecNPC;
    private KowalNPC kowalNPC;
    private NewTargManager newTargManager;

    private int i = 1;

    public void onEnable() {
        this.config.createConfig();
        this.initDatabase();
        this.initManagers();
        this.initNPCS();

        this.mongo.loadAll();

        this.getLvlManager().loadAllReqExp();
        this.getLvlManager().loadExpForAllMobs();
        this.getOsManager().loadAllRequiredOs();
        this.getKupiecNPC().loadAll();
        this.getGuildManager().loadGuildLvlReq();
        this.autoMessage();
        this.getBaoManager().getAllEntities();
        this.getMagazynierNPC().loadMagazynierMissions();



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
        this.getCommand("setdmg").setExecutor(new SetDmg());
        this.getCommand("akcesoria").setExecutor(new Akcesoria(this));
        this.getCommand("pomoc").setExecutor(new Pomoc(this));
        this.getCommand("sprawdzmojebonusy").setExecutor(new SprawdzMojeBonusy(this));
        this.getCommand("mute").setExecutor(new Mute(this));
        this.getCommand("unmute").setExecutor(new UnMute(this));
        this.getCommand("tempmute").setExecutor(new TempMute(this));
        this.getCommand("message").setExecutor(new Message(this));
        this.getCommand("reply").setExecutor(new Reply(this));
        this.getCommand("targ").setExecutor(new NewTarg(this));
        this.getCommand("kasa").setExecutor(new Kasa(this));
        this.getCommand("wyplac").setExecutor(new Wyplac(this));
        this.getCommand("wystaw").setExecutor(new NewTargWystaw(this));
        this.getCommand("sprawdz").setExecutor(new Sprawdz(this));
        this.getCommand("testanimation").setExecutor(new TestAnimation(this));
        this.getCommand("test").setExecutor(new Test(this));
        this.getCommand("helpop").setExecutor(new HelpOP(this));
        this.getCommand("removenearbyentities").setExecutor(new RemoveNearbyEntities());

        this.getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        this.getServer().getPluginManager().registerEvents(new PlayerQuitListener(this), this);
        this.getServer().getPluginManager().registerEvents(new EntityDeathListener(this), this);
        this.getServer().getPluginManager().registerEvents(new EntityDamageEntityListener(this), this);
        this.getServer().getPluginManager().registerEvents(new PlayerChatListener(this), this);
        this.getServer().getPluginManager().registerEvents(new EntityDamageListener(this), this);
        this.getServer().getPluginManager().registerEvents(new PlayerInteractEntityListener(this), this);
        this.getServer().getPluginManager().registerEvents(new BlockBreakListener(this), this);
        this.getServer().getPluginManager().registerEvents(new BlockPlaceListener(this), this);
        this.getServer().getPluginManager().registerEvents(new ItemSpawnListener(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerCommandPreprocessListener(this), this);
        this.getServer().getPluginManager().registerEvents(new PlayerItemDamageListener(), this);
        this.getServer().getPluginManager().registerEvents(new FoodLevelChangeListener(), this);
        this.getServer().getPluginManager().registerEvents(new InventoryItemDragListener(), this);

        // BAO
        this.getServer().getPluginManager().registerEvents(new BAOInventoryClick(this), this);
        this.getServer().getPluginManager().registerEvents(new BAOEntityInteract(this), this);
        this.getServer().getPluginManager().registerEvents(new BAOPlayerInteract(this), this);

        // OS
        this.getServer().getPluginManager().registerEvents(new OSInventoryClick(this), this);

        // TRADE
        this.getServer().getPluginManager().registerEvents(new TRADEInventoryClick(this), this);
        this.getServer().getPluginManager().registerEvents(new TRADEInventoryClose(this), this);

        // TARG
        this.getServer().getPluginManager().registerEvents(new NewTargInventoryClick(this), this);
        this.getServer().getPluginManager().registerEvents(new TARGInventoryClose(this), this);

        // POMOC
        this.getServer().getPluginManager().registerEvents(new POMOCInventoryClick(this), this);

        // EQ
        this.getServer().getPluginManager().registerEvents(new EQInventoryClick(this), this);
        this.getServer().getPluginManager().registerEvents(new EQInventoryClose(this), this);

        // AKCESORIA
        this.getServer().getPluginManager().registerEvents(new AKCESORIAInventoryClick(this), this);
        this.getServer().getPluginManager().registerEvents(new AKCESORIAPlayerInteract(this), this);

        // HISTORY
        this.getServer().getPluginManager().registerEvents(new HISTORYInventoryClick(), this);

        // POMOC
        this.getServer().getPluginManager().registerEvents(new POMOCInventoryClick(this), this);

        // ECONOMY
        this.getServer().getPluginManager().registerEvents(new EconomyPlayerInteract(this), this);

        // GUILDS
        this.getCommand("klan").setExecutor(new Guild(this));
        this.getServer().getPluginManager().registerEvents(new GuildsInventoryClick(this), this);
        this.getServer().getPluginManager().registerEvents(new GuildsPlayerDamage(this), this);


        // KOSZ
        this.getServer().getPluginManager().registerEvents(new KoszInventoryClick(), this);
        this.getServer().getPluginManager().registerEvents(new KoszInventoryClose(), this);
        this.getCommand("kosz").setExecutor(new Kosz());


        // NPC

        // ...DUSZOLOG
        this.getServer().getPluginManager().registerEvents(new DuszologInventoryClick(this), this);
        this.getServer().getPluginManager().registerEvents(new DuszologPlayerInteract(this), this);

        // ...RYBAK
        this.getServer().getPluginManager().registerEvents(new PlayerFishListener(this), this);
        this.getServer().getPluginManager().registerEvents(new RybakInventoryClick(this), this);

        // ...MAGAZYNIER
        this.getServer().getPluginManager().registerEvents(new MagazynierInventoryClick(this), this);
        this.getServer().getPluginManager().registerEvents(new MagazynierInventoryClose(this), this);
        this.getCommand("magazyn").setExecutor(new Magazyn(this));

        // ...TELEPORTER
        this.getServer().getPluginManager().registerEvents(new TeleporterInventoryClick(this), this);

        // ...KUPIEC
        this.getServer().getPluginManager().registerEvents(new KupiecInventoryClick(this), this);
        this.getServer().getPluginManager().registerEvents(new KupiecInventoryClose(this), this);

        // ...KOWAL
        this.getServer().getPluginManager().registerEvents(new KowalInventoryClick(this), this);

        // BACKUP
        this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new BackupRunnable(this), 6000L, 6000L);

        // TAB
        new UpdateTabTask(this);
    }

    public void onDisable() {
        this.mongo.saveAllPlayers();
        this.mongo.onDisable();
        this.spawn.setSpawn(null);
        this.playerManager.removeAllPlayers();
        this.getLvlManager().unLoadAll();
    }

    private void initDatabase() {
        this.mongo = new MongoManager(this);
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
        this.targManager = new TargManager(this);
        this.cooldownManager = new CooldownManager();
        this.guildManager = new GuildManager(this);
        this.tabManager = new TabManager(this);
        this.newTargManager = new NewTargManager(this);
    }

    private void initNPCS() {
        this.duszologNPC = new DuszologNPC();
        this.teleporterNPC = new TeleporterNPC(this);
        this.rybakNPC = new RybakNPC(this);
        this.magazynierNPC = new MagazynierNPC(this);
        this.kupiecNPC = new KupiecNPC(this);
        this.kowalNPC = new KowalNPC(this);


        this.getRybakNPC().loadExpWedka();
        this.getRybakNPC().loadRybakDrops();
        this.getRybakNPC().loadRybakMobs();
        this.getRybakNPC().loadRybakMisje();
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
    

    public MongoManager getMongoManager() {
        return mongo;
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

    public CooldownManager getCooldownManager() {
        return cooldownManager;
    }

    public RybakNPC getRybakNPC() {
        return rybakNPC;
    }

    public MagazynierNPC getMagazynierNPC() {
        return magazynierNPC;
    }

    public GuildManager getGuildManager() {
        return guildManager;
    }

    public TabManager getTabManager() {
        return tabManager;
    }

    public KupiecNPC getKupiecNPC() {
        return kupiecNPC;
    }

    public KowalNPC getKowalNPC() {
        return kowalNPC;
    }

    public NewTargManager getNewTargManager() {
        return newTargManager;
    }

}
