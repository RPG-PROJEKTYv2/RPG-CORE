package rpg.rpgcore;

import net.minecraft.server.v1_8_R3.Item;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.bao.*;
import rpg.rpgcore.bao.events.BAOEntityInteract;
import rpg.rpgcore.bao.events.BAOInventoryClick;
import rpg.rpgcore.bao.events.BAOPlayerInteract;
import rpg.rpgcore.bonuses.BonusesManager;
import rpg.rpgcore.chat.events.AsyncPlayerChatListener;
import rpg.rpgcore.chat.events.EQInventoryClose;
import rpg.rpgcore.chat.events.ChatInventoryClickListener;
import rpg.rpgcore.chat.mute.MuteCommand;
import rpg.rpgcore.chat.mute.MuteManager;
import rpg.rpgcore.chat.mute.TempMuteCommand;
import rpg.rpgcore.chat.mute.UnMuteCommand;
import rpg.rpgcore.chests.DropFromChestsListener;
import rpg.rpgcore.chests.Expowisko1.NajemnikManager;
import rpg.rpgcore.chests.Expowisko1.WygnaniecManager;
import rpg.rpgcore.chests.Inne.RoznosciManager;
import rpg.rpgcore.chests.Inne.TajemniczaManager;
import rpg.rpgcore.chests.Inne.ZwierzakiManager;
import rpg.rpgcore.commands.admin.EnchantCustomCommand;
import rpg.rpgcore.commands.admin.*;
import rpg.rpgcore.commands.admin.ban.UnBanCommand;
import rpg.rpgcore.commands.admin.dodatkowyexp.DodatkowyExpCommand;
import rpg.rpgcore.commands.player.*;
import rpg.rpgcore.commands.player.bossy.BossyCommand;
import rpg.rpgcore.commands.player.bossy.BossyInventoryClick;
import rpg.rpgcore.commands.player.kosz.KoszCommand;
import rpg.rpgcore.discord.DiscordBot;
import rpg.rpgcore.dodatki.DodatkiManager;
import rpg.rpgcore.dungeons.DungeonsInventoryClick;
import rpg.rpgcore.dungeons.DungeonsManager;
import rpg.rpgcore.dungeons.niebiosa.NiebiosaManager;
import rpg.rpgcore.dungeons.niebiosa.events.NiebiosaPlayerInteract;
import rpg.rpgcore.dungeons.niebiosa.events.NiebiosaPortalEntry;
import rpg.rpgcore.dungeons.zamekNieskonczonosci.ZamekNieskonczonosciManager;
import rpg.rpgcore.dungeons.zamekNieskonczonosci.events.ZamekNieskonczonosciEntityDamgeListener;
import rpg.rpgcore.dungeons.zamekNieskonczonosci.events.ZamekNieskonczonosciInventoryClick;
import rpg.rpgcore.dungeons.zamekNieskonczonosci.events.ZamekNieskonczonosciMoveListener;
import rpg.rpgcore.entities.EntityTypes;
import rpg.rpgcore.guilds.events.GuildEntityDeath;
import rpg.rpgcore.guilds.events.GuildsInventoryClick;
import rpg.rpgcore.guilds.events.GuildsPlayerDamage;
import rpg.rpgcore.inventory.InvseeInventoryCloseListener;
import rpg.rpgcore.inventory.InventoryCommand;
import rpg.rpgcore.klasy.choice.KlasaCommand;
import rpg.rpgcore.klasy.KlasyHelper;
import rpg.rpgcore.klasy.choice.KlasaPlayerMove;
import rpg.rpgcore.klasy.choice.KlasyInventoryClick;
import rpg.rpgcore.klasy.wojownik.KlasyNPC;
import rpg.rpgcore.listanpc.ListaNPCCommand;
import rpg.rpgcore.listanpc.ListaNPCInventoryClick;
import rpg.rpgcore.listanpc.ListaNPCManager;
import rpg.rpgcore.magazyn.MagazynManager;
import rpg.rpgcore.msg.IgnoreCommand;
import rpg.rpgcore.mythicstick.MythicStick;
import rpg.rpgcore.mythicstick.MythicstickPlayerInteract;
import rpg.rpgcore.npc.duszolog.events.DuszologDamageListener;
import rpg.rpgcore.npc.duszolog.events.DuszologInteractListener;
import rpg.rpgcore.npc.gornik.GornikNPC;
import rpg.rpgcore.npc.gornik.events.GornikBlockBreakListener;
import rpg.rpgcore.npc.gornik.events.GornikInventoryClick;
import rpg.rpgcore.npc.gornik.events.GornikInventoryCloseListener;
import rpg.rpgcore.npc.gornik.ore.OreCommand;
import rpg.rpgcore.npc.gornik.ore.OreManager;
import rpg.rpgcore.npc.lesnik.LesnikInventoryClick;
import rpg.rpgcore.npc.lesnik.LesnikInventoryClose;
import rpg.rpgcore.npc.lesnik.LesnikNPC;
import rpg.rpgcore.npc.lowca.LowcaInventoryClick;
import rpg.rpgcore.npc.lowca.LowcaNPC;
import rpg.rpgcore.npc.medyk.MedykInventoryClick;
import rpg.rpgcore.npc.medyk.MedykNPC;
import rpg.rpgcore.npc.metinolog.MetinologInventoryClick;
import rpg.rpgcore.npc.przyrodnik.PrzyrodnikInventoryClick;
import rpg.rpgcore.npc.przyrodnik.PrzyrodnikNPC;
import rpg.rpgcore.os.OsiagnieciaCommand;
import rpg.rpgcore.party.PartyManager;
import rpg.rpgcore.pets.PetCommand;
import rpg.rpgcore.pets.PetyManager;
import rpg.rpgcore.pets.listeners.PetInteractListener;
import rpg.rpgcore.pets.listeners.PetInventoryClickListener;
import rpg.rpgcore.pets.listeners.PetWorldChangeListener;
import rpg.rpgcore.server.ServerManager;
import rpg.rpgcore.commands.admin.teleport.TeleportCommand;
import rpg.rpgcore.commands.admin.teleport.TeleportHereCommand;
import rpg.rpgcore.commands.admin.teleport.TeleportManager;
import rpg.rpgcore.commands.admin.ban.BanCommand;
import rpg.rpgcore.commands.admin.ban.BanManager;
import rpg.rpgcore.commands.admin.ban.TempBanCommand;
import rpg.rpgcore.commands.admin.god.GodCommand;
import rpg.rpgcore.commands.admin.vanish.VanishCommand;
import rpg.rpgcore.chat.*;
import rpg.rpgcore.commands.player.kosz.KoszInventoryClick;
import rpg.rpgcore.commands.player.kosz.KoszInventoryClose;
import rpg.rpgcore.database.MongoManager;
import rpg.rpgcore.dmg.DamageManager;
import rpg.rpgcore.dmg.EntityDamageEntityListener;
import rpg.rpgcore.economy.EconomyPlayerInteract;
import rpg.rpgcore.economy.KasaCommand;
import rpg.rpgcore.economy.WyplacCommand;
import rpg.rpgcore.guilds.*;
import rpg.rpgcore.history.HISTORYInventoryClick;
import rpg.rpgcore.history.HistoryCommand;
import rpg.rpgcore.listeners.*;
import rpg.rpgcore.lvl.LvlCommand;
import rpg.rpgcore.lvl.LvlManager;
import rpg.rpgcore.managers.*;
import rpg.rpgcore.commands.admin.god.GodManager;
import rpg.rpgcore.metiny.MetinCommand;
import rpg.rpgcore.metiny.MetinyManager;
import rpg.rpgcore.msg.MSGManager;
import rpg.rpgcore.msg.MessageCommand;
import rpg.rpgcore.msg.ReplyCommand;
import rpg.rpgcore.newTarg.NewTargCommand;
import rpg.rpgcore.newTarg.NewTargInventoryClick;
import rpg.rpgcore.newTarg.NewTargManager;
import rpg.rpgcore.newTarg.NewTargWystawCommand;
import rpg.rpgcore.npc.kolekcjoner.KolekcjonerInventoryClick;
import rpg.rpgcore.npc.kolekcjoner.KolekcjonerNPC;
import rpg.rpgcore.npc.kowal.KowalInventoryClick;
import rpg.rpgcore.npc.kowal.KowalNPC;
import rpg.rpgcore.npc.kupiec.KupiecInventoryClick;
import rpg.rpgcore.npc.kupiec.KupiecInventoryClose;
import rpg.rpgcore.npc.kupiec.KupiecNPC;
import rpg.rpgcore.magazyn.MagazynCommand;
import rpg.rpgcore.magazyn.MagazynierInventoryClick;
import rpg.rpgcore.magazyn.MagazynierInventoryClose;
import rpg.rpgcore.npc.duszolog.DuszologNPC;
import rpg.rpgcore.npc.metinolog.MetinologNPC;
import rpg.rpgcore.npc.rybak.PlayerFishListener;
import rpg.rpgcore.npc.rybak.RybakInventoryClick;
import rpg.rpgcore.npc.rybak.RybakNPC;
import rpg.rpgcore.npc.teleporter.TeleporterInventoryClick;
import rpg.rpgcore.npc.teleporter.TeleporterNPC;
import rpg.rpgcore.commands.admin.vanish.VanishManager;
import rpg.rpgcore.npc.trener.TrenerInventoryClick;
import rpg.rpgcore.npc.trener.TrenerNPC;
import rpg.rpgcore.os.OSInventoryClick;
import rpg.rpgcore.os.OsManager;
import rpg.rpgcore.pomoc.POMOCInventoryClick;
import rpg.rpgcore.pomoc.PomocCommand;
import rpg.rpgcore.pomoc.PomocManager;
import rpg.rpgcore.spawn.SpawnCommand;
import rpg.rpgcore.spawn.SpawnManager;
import rpg.rpgcore.tab.TabManager;
import rpg.rpgcore.tab.UpdateTabTask;
import rpg.rpgcore.OLDtarg.*;
import rpg.rpgcore.tasks.ActionBarTask;
import rpg.rpgcore.tasks.KopalniaTask;
import rpg.rpgcore.tasks.MetinyTask;
import rpg.rpgcore.tasks.ReloadPetsTask;
import rpg.rpgcore.trade.TRADEInventoryClick;
import rpg.rpgcore.trade.TRADEInventoryClose;
import rpg.rpgcore.trade.TradeManager;
import rpg.rpgcore.user.UserManager;
import rpg.rpgcore.utils.Config;
import rpg.rpgcore.utils.Utils;

import javax.security.auth.login.LoginException;
import java.lang.reflect.Field;

public final class RPGCORE extends JavaPlugin {

    private static RPGCORE instance;
    private static DiscordBot discordBot;
    private final Config config = new Config(this);
    private SpawnManager spawn;
    private MongoManager mongo;
    private TeleportManager teleportManager;
    private BanManager banManager;
    private VanishManager vanishManager;
    private NMSManager nmsManager;
    private GodManager godManager;
    private LvlManager lvlManager;
    private DamageManager damageManager;
    private ChatManager chatManager;
    private BaoManager baoManager;
    private OsManager osManager;
    private DodatkiManager dodatkiManager;
    private PomocManager pomocManager;
    private MuteManager muteManager;
    private MSGManager msgManager;
    private TradeManager tradeManager;
    private DuszologNPC duszologNPC;
    private TargManager targManager;
    private TeleporterNPC teleporterNPC;
    private CooldownManager cooldownManager;
    private MagazynManager magazynManager;
    private RybakNPC rybakNPC;
    private GuildManager guildManager;
    private BackupManager backup;
    private KupiecNPC kupiecNPC;
    private KowalNPC kowalNPC;
    private NewTargManager newTargManager;
    private KolekcjonerNPC kolekcjonerNPC;
    private TrenerNPC trenerNPC;
    private MetinyManager metinyManager;
    private MetinologNPC metinologNPC;
    private ServerManager serverManager;
    private KlasyHelper klasyHelper;
    private KlasyNPC klasyNPC;
    private MedykNPC medykNPC;
    private GornikNPC gornikNPC;
    private PrzyrodnikNPC przyrodnikNPC;
    private ListaNPCManager listaNPCManager;
    private UserManager userManager;
    private PartyManager partyManager;
    private LowcaNPC lowcaNPC;
    private LesnikNPC lesnikNPC;
    private PetyManager petyManager;
    private ZamekNieskonczonosciManager zamekNieskonczonosciManager;



    private NiebiosaManager niebiosaManager;
    private BonusesManager bonusesManager;
    // SKRZYNKI
    // inne
    private RoznosciManager roznosciManager;
    private TajemniczaManager tajemniczaManager;
    // exp1
    private NajemnikManager najemnikManager;
    private WygnaniecManager wygnaniecManager;
    private ZwierzakiManager zwierzakiManager;
    private OreManager oreManager;
    private DungeonsManager dungeonsManager;

    private int i = 1;

    public static RPGCORE getInstance() {
        return instance;
    }

    public static DiscordBot getDiscordBot() {
        return discordBot;
    }

    public void onEnable() {
        instance = this;
        this.config.createConfig();
        this.initDatabase();
        this.initManagers();
        this.initNPCS();

        this.mongo.loadAll();

        this.getLvlManager().loadAllReqExp();
        this.getLvlManager().loadExpForAllMobs();
        this.getKupiecNPC().loadAll();
        this.getGuildManager().loadGuildLvlReq();
        this.autoMessage();

        this.initGlobalCommands();
        this.initEvents();
        this.initDungeons();
        //this.initPacketListeners();



        // BACKUP
        //this.mongo.tempUpdate();

        // TASKS

        // ...TAB
        new UpdateTabTask(this);

        // ...SAVE GUILDS
        this.getServer().getScheduler().scheduleSyncRepeatingTask(this, this::saveGuilds, 100L, 12000L);
        // ...METINY
        new MetinyTask(this);
        // ...ACTIONBAR
        new ActionBarTask(this);
        // ...PETY
        new ReloadPetsTask(this);
        // ...KOPALNIA
        new KopalniaTask(this);

        // SKRZYNIE
        this.initChests();

        this.fixBuckets();

        try {
            discordBot = new DiscordBot("MTAxNTczNDAzOTU3MjkxNDIzOA.G4WBAu.JNyI0YhZtn9f0C4NAgjoTOuw6_Cua8iBvpXEpY");
        } catch (LoginException e) {
            throw new RuntimeException(e);
        }

        for (final World w : Bukkit.getWorlds()) {
            for (final Entity e : w.getEntities()) {
                if (e instanceof ArmorStand) {
                    if (e.getName().contains("Duszek") || e.getName().contains("Zlota Rybka") ||e.getName().contains("Pancernik") ||e.getName().contains("Foka") ||
                            e.getName().contains("Nietoperz") ||e.getName().contains("Bobr") ||e.getName().contains("Ognisty Smok") ||e.getName().contains("Demon") ||
                            e.getName().contains("Wampir")) {
                        e.remove();
                    }
                }
                if (e instanceof EnderCrystal) {
                    e.remove();
                }
            }
        }
        Bukkit.clearRecipes();
    }
    public void onDisable() {
        this.mongo.saveAllUsers();
        this.mongo.saveAllMetins();
        this.mongo.saveAllBao();
        this.mongo.saveAllDuszolog();
        this.mongo.saveAllGuilds();
        this.mongo.saveAllGornik();
        this.mongo.saveAllMedyk();
        this.mongo.saveAllKolekcjoner();
        this.mongo.saveAllMetinolog();
        this.mongo.saveAllOs();
        this.mongo.saveAllPrzyrodnik();
        this.mongo.saveAllActivePets();
        this.mongo.saveAllDodatki();
        this.mongo.saveAllBonuses();
        this.mongo.saveAllUserPets();
        this.mongo.saveAllChatUsers();
        this.mongo.saveAllRybak();
        this.mongo.saveAllMagazyny();
        this.mongo.saveAllLowca();
        this.mongo.saveAllLesnik();
        this.mongo.saveAllTrener();
        this.mongo.saveAllOreLocations();
        this.mongo.onDisable();
        this.spawn.setSpawn(null);
        this.getLvlManager().unLoadAll();
        EntityTypes.despawnAllEntities();
    }

    private void initGlobalCommands() {
        CommandAPI.getCommand().register("HellRPGCore", new TeleportCommand(this));
        CommandAPI.getCommand().register("HellRPGCore", new TeleportHereCommand(this));
        CommandAPI.getCommand().register("HellRPGCore", new SpawnCommand(this));
        CommandAPI.getCommand().register("HellRPGCore", new BanCommand(this));
        CommandAPI.getCommand().register("HellRPGCore", new UnBanCommand(this));
        CommandAPI.getCommand().register("HellRPGCore", new KickCommand(this));
        CommandAPI.getCommand().register("HellRPGCore", new VanishCommand(this));
        CommandAPI.getCommand().register("HellRPGCore", new GodCommand(this));
        CommandAPI.getCommand().register("HellRPGCore", new SpeedCommand());
        CommandAPI.getCommand().register("HellRPGCore", new FlyCommand(this));
        CommandAPI.getCommand().register("HellRPGCore", new HistoryCommand(this));
        CommandAPI.getCommand().register("HellRPGCore", new BackCommand(this));
        CommandAPI.getCommand().register("HellRPGCore", new LvlCommand(this));
        CommandAPI.getCommand().register("HellRPGCore", new GmCommand());
        CommandAPI.getCommand().register("HellRPGCore", new HealCommand(this));
        CommandAPI.getCommand().register("HellRPGCore", new TempBanCommand(this));
        CommandAPI.getCommand().register("HellRPGCore", new OsiagnieciaCommand(this));
        CommandAPI.getCommand().register("HellRPGCore", new PomocCommand(this));
        CommandAPI.getCommand().register("HellRPGCore", new SprawdzMojeBonusyCommand(this));
        CommandAPI.getCommand().register("HellRPGCore", new MuteCommand(this));
        CommandAPI.getCommand().register("HellRPGCore", new UnMuteCommand(this));
        CommandAPI.getCommand().register("HellRPGCore", new TempMuteCommand(this));
        CommandAPI.getCommand().register("HellRPGCore", new MessageCommand(this));
        CommandAPI.getCommand().register("HellRPGCore", new ReplyCommand(this));
        CommandAPI.getCommand().register("HellRPGCore", new NewTargCommand(this));
        CommandAPI.getCommand().register("HellRPGCore", new KasaCommand(this));
        CommandAPI.getCommand().register("HellRPGCore", new WyplacCommand(this));
        CommandAPI.getCommand().register("HellRPGCore", new NewTargWystawCommand(this));
        CommandAPI.getCommand().register("HellRPGCore", new SprawdzCommand(this));
        CommandAPI.getCommand().register("HellRPGCore", new HelpOPCommand(this));
        CommandAPI.getCommand().register("HellRPGCore", new MetinCommand(this));
        CommandAPI.getCommand().register("HellRPGCore", new DodatkowyExpCommand());
        CommandAPI.getCommand().register("HellRPGCore", new ChatCommand(this));
        CommandAPI.getCommand().register("HellRPGCore", new KlasaCommand(this));
        CommandAPI.getCommand().register("HellRPGCore", new BossyCommand());
        CommandAPI.getCommand().register("HellRPGCore", new ListaNPCCommand());
        CommandAPI.getCommand().register("HellRPGCore", new GuildCommand(this));
        CommandAPI.getCommand().register("HellRPGCore", new KoszCommand());
        CommandAPI.getCommand().register("HellRPGCore", new MythicStick());
        CommandAPI.getCommand().register("HellRPGCore", new MagazynCommand(this));
        CommandAPI.getCommand().register("HellRPGCore", new SetDmgCommand());
        CommandAPI.getCommand().register("HellRPGCore", new TestAnimationCommand());
        CommandAPI.getCommand().register("HellRPGCore", new TestCommand());
        CommandAPI.getCommand().register("HellRPGCore", new RemoveNearbyEntitiesCommand());
        CommandAPI.getCommand().register("HellRPGCore", new SetPremiumCommand(this));
        CommandAPI.getCommand().register("HellRPGCore", new RankTimeCommand());
        CommandAPI.getCommand().register("HellRPGCore", new SetAdminRankCommand(this));
        CommandAPI.getCommand().register("HellRPGCore", new HellCodeCommand(this));
        CommandAPI.getCommand().register("HellRPGCore", new AdminCodeCommand(this));
        CommandAPI.getCommand().register("HellRPGCore", new ChatPanelCommand());
        CommandAPI.getCommand().register("HellRPGCore", new PartyCommand(this));
        CommandAPI.getCommand().register("HellRPGCore", new InventoryCommand());
        CommandAPI.getCommand().register("HellRPGCore", new PetCommand(this));
        CommandAPI.getCommand().register("HellRPGCore", new OreCommand());
        CommandAPI.getCommand().register("HellRPGCore", new EnchantCustomCommand());
        CommandAPI.getCommand().register("HellRPGCore", new EnchantCommand());
        CommandAPI.getCommand().register("HellRPGCore", new MemoryCommand());
        CommandAPI.getCommand().register("HellRPGCore", new IgnoreCommand());
        CommandAPI.getCommand().register("HellRPGCore", new AdminChatCommand());
        CommandAPI.getCommand().register("HellRPGCore", new DodatkiCommand());
        CommandAPI.getCommand().register("HellRPGCore", new GiveAkcesoriaCommand());
    }

    private void initEvents() {
        this.getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        this.getServer().getPluginManager().registerEvents(new PlayerQuitListener(this), this);
        this.getServer().getPluginManager().registerEvents(new EntityDeathListener(this), this);
        this.getServer().getPluginManager().registerEvents(new EntityDamageEntityListener(this), this);
        this.getServer().getPluginManager().registerEvents(new AsyncPlayerChatListener(this), this);
        this.getServer().getPluginManager().registerEvents(new EntityDamageListener(this), this);
        this.getServer().getPluginManager().registerEvents(new PlayerInteractEntityListener(this), this);
        this.getServer().getPluginManager().registerEvents(new BlockBreakListener(this), this);
        this.getServer().getPluginManager().registerEvents(new BlockPlaceListener(this), this);
        this.getServer().getPluginManager().registerEvents(new ItemSpawnListener(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerItemDamageListener(), this);
        this.getServer().getPluginManager().registerEvents(new FoodLevelChangeListener(), this);
        this.getServer().getPluginManager().registerEvents(new InventoryItemDragListener(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerItemPickUpListener(), this);
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
        this.getServer().getPluginManager().registerEvents(new EQInventoryClose(this), this);
        this.getServer().getPluginManager().registerEvents(new ChatInventoryClickListener(this), this);

        // AKCESORIA


        // HISTORY
        this.getServer().getPluginManager().registerEvents(new HISTORYInventoryClick(), this);

        // ECONOMY
        this.getServer().getPluginManager().registerEvents(new EconomyPlayerInteract(this), this);

        // GUILDS
        this.getServer().getPluginManager().registerEvents(new GuildsInventoryClick(this), this);
        this.getServer().getPluginManager().registerEvents(new GuildsPlayerDamage(this), this);
        this.getServer().getPluginManager().registerEvents(new GuildEntityDeath(this), this);


        // KOSZ
        this.getServer().getPluginManager().registerEvents(new KoszInventoryClick(), this);
        this.getServer().getPluginManager().registerEvents(new KoszInventoryClose(), this);

        // KLASY
        this.getServer().getPluginManager().registerEvents(new KlasaPlayerMove(this), this);
        this.getServer().getPluginManager().registerEvents(new KlasyInventoryClick(this), this);

        // BOSSY
        this.getServer().getPluginManager().registerEvents(new BossyInventoryClick(), this);

        // LISTANPC
        this.getServer().getPluginManager().registerEvents(new ListaNPCInventoryClick(), this);

        // INNE

        // MythicSTICK
        this.getServer().getPluginManager().registerEvents(new MythicstickPlayerInteract(), this);

        // NPC

        // ...DUSZOLOG
        this.getServer().getPluginManager().registerEvents(new DuszologDamageListener(), this);
        this.getServer().getPluginManager().registerEvents(new DuszologInteractListener(), this);


        // ...RYBAK
        this.getServer().getPluginManager().registerEvents(new PlayerFishListener(this), this);
        this.getServer().getPluginManager().registerEvents(new RybakInventoryClick(this), this);

        // ...MAGAZYNIER
        this.getServer().getPluginManager().registerEvents(new MagazynierInventoryClick(this), this);
        this.getServer().getPluginManager().registerEvents(new MagazynierInventoryClose(this), this);

        // ...TELEPORTER
        this.getServer().getPluginManager().registerEvents(new TeleporterInventoryClick(this), this);

        // ...KUPIEC
        this.getServer().getPluginManager().registerEvents(new KupiecInventoryClick(this), this);
        this.getServer().getPluginManager().registerEvents(new KupiecInventoryClose(this), this);

        // ...KOWAL
        this.getServer().getPluginManager().registerEvents(new KowalInventoryClick(this), this);

        // ...KOLEKCJONER
        this.getServer().getPluginManager().registerEvents(new KolekcjonerInventoryClick(this), this);

        // ...TRENER
        this.getServer().getPluginManager().registerEvents(new TrenerInventoryClick(this), this);

        // ...METINOLOG
        this.getServer().getPluginManager().registerEvents(new MetinologInventoryClick(), this);

        // ...MEDYK
        this.getServer().getPluginManager().registerEvents(new MedykInventoryClick(this), this);

        // ...GORNIK
        this.getServer().getPluginManager().registerEvents(new GornikInventoryClick(), this);
        this.getServer().getPluginManager().registerEvents(new GornikBlockBreakListener(this), this);
        this.getServer().getPluginManager().registerEvents(new GornikInventoryCloseListener(), this);

        // ...PRZYRODNIK
        this.getServer().getPluginManager().registerEvents(new PrzyrodnikInventoryClick(), this);

        // ...LOWCA
        this.getServer().getPluginManager().registerEvents(new LowcaInventoryClick(this), this);

        // ...LESNIK
        this.getServer().getPluginManager().registerEvents(new LesnikInventoryClick(this), this);
        this.getServer().getPluginManager().registerEvents(new LesnikInventoryClose(), this);


        // DUNGEONS

        // ...NIEBIOSA
        this.getServer().getPluginManager().registerEvents(new NiebiosaPlayerInteract(this), this);
        this.getServer().getPluginManager().registerEvents(new NiebiosaPortalEntry(), this);

        // INVSEE
        this.getServer().getPluginManager().registerEvents(new InvseeInventoryCloseListener(this), this);
        //this.getServer().getPluginManager().registerEvents(new KeyClickListener(), this);

        // PETY
        this.getServer().getPluginManager().registerEvents(new PetInventoryClickListener(this), this);
        this.getServer().getPluginManager().registerEvents(new PetInteractListener(this), this);
        this.getServer().getPluginManager().registerEvents(new PetWorldChangeListener(), this);
    }

    private void initDatabase() {
        this.mongo = new MongoManager(this);
    }

    private void initManagers() {
        this.userManager = new UserManager(this);
        this.spawn = new SpawnManager();
        this.teleportManager = new TeleportManager(this);
        this.banManager = new BanManager(this);
        this.vanishManager = new VanishManager(this);
        this.nmsManager = new NMSManager();
        this.godManager = new GodManager(this);
        this.lvlManager = new LvlManager(this);
        this.damageManager = new DamageManager(this);
        this.chatManager = new ChatManager(this);
        this.osManager = new OsManager(this);
        this.dodatkiManager = new DodatkiManager(this);
        this.pomocManager = new PomocManager();
        this.muteManager = new MuteManager(this);
        this.msgManager = new MSGManager();
        this.tradeManager = new TradeManager(this);
        this.targManager = new TargManager(this);
        this.cooldownManager = new CooldownManager();
        this.guildManager = new GuildManager(this);
        new TabManager(this);
        this.newTargManager = new NewTargManager(this);
        this.backup = new BackupManager(this);
        this.metinyManager = new MetinyManager(this);
        this.serverManager = new ServerManager(this);
        this.niebiosaManager = new NiebiosaManager(this);
        this.listaNPCManager = new ListaNPCManager(this);
        this.baoManager = new BaoManager(this);
        this.bonusesManager = new BonusesManager(this);
        this.magazynManager = new MagazynManager(this);
        this.partyManager = new PartyManager();
        this.petyManager = new PetyManager(this);
        this.oreManager = new OreManager(this);
    }

    private void initNPCS() {
        this.duszologNPC = new DuszologNPC(this);
        this.teleporterNPC = new TeleporterNPC(this);
        this.rybakNPC = new RybakNPC(this);
        this.kolekcjonerNPC = new KolekcjonerNPC(this);
        this.kupiecNPC = new KupiecNPC(this);
        this.kowalNPC = new KowalNPC(this);
        this.trenerNPC = new TrenerNPC(this);
        this.metinologNPC = new MetinologNPC(this);
        this.klasyNPC = new KlasyNPC(this);
        this.klasyHelper = new KlasyHelper(this);
        this.medykNPC = new MedykNPC(this);
        this.gornikNPC = new GornikNPC(this);
        this.przyrodnikNPC = new PrzyrodnikNPC(this);
        this.lowcaNPC = new LowcaNPC(this);
        this.lesnikNPC = new LesnikNPC(this);


        this.getMetinologNPC().loadMissions();
    }

    private void initChests() {
        this.getServer().getPluginManager().registerEvents(new DropFromChestsListener(this), this);
        // SKRZYNKI
        // inne
        this.roznosciManager = new RoznosciManager();
        this.tajemniczaManager = new TajemniczaManager();
        // exp1
        this.najemnikManager = new NajemnikManager();
        this.wygnaniecManager = new WygnaniecManager();
        this.zwierzakiManager = new ZwierzakiManager();
    }

    private void initDungeons() {
        // ZAMEK NIESKONCZONOSCI
        this.dungeonsManager = new DungeonsManager();
        this.zamekNieskonczonosciManager = new ZamekNieskonczonosciManager(this);
        this.getServer().getPluginManager().registerEvents(new DungeonsInventoryClick(this), this);
        this.getServer().getPluginManager().registerEvents(new ZamekNieskonczonosciMoveListener(this), this);
        this.getServer().getPluginManager().registerEvents(new ZamekNieskonczonosciEntityDamgeListener(), this);
        this.getServer().getPluginManager().registerEvents(new ZamekNieskonczonosciInventoryClick(), this);
    }

    private void fixBuckets() {
        try {
            Field f = Item.class.getDeclaredField("maxStackSize");
            f.setAccessible(true);
            f.setInt(Item.REGISTRY.a(326), 16);
            f.setInt(Item.REGISTRY.a(327), 16);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    private void saveGuilds() {
        this.getServer().getScheduler().runTaskAsynchronously(this, () -> this.getMongoManager().saveAllGuilds());
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


    public LvlManager getLvlManager() {
        return lvlManager;
    }

    public DamageManager getDamageManager() {
        return damageManager;
    }

    public ChatManager getChatManager() {
        return chatManager;
    }


    public OsManager getOsManager() {
        return osManager;
    }
    public DodatkiManager getDodatkiManager() {
        return dodatkiManager;
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

    public MagazynManager getMagazynManager() {
        return magazynManager;
    }
    public KolekcjonerNPC getKolekcjonerNPC() {
        return kolekcjonerNPC;
    }

    public GuildManager getGuildManager() {
        return guildManager;
    }

    public NewTargManager getNewTargManager() {
        return newTargManager;
    }

    public BackupManager getBackupManager() {
        return backup;
    }

    public KupiecNPC getKupiecNPC() {
        return kupiecNPC;
    }

    public KowalNPC getKowalNPC() {
        return kowalNPC;
    }

    public TrenerNPC getTrenerNPC() {
        return trenerNPC;
    }

    public MetinyManager getMetinyManager() {
        return metinyManager;
    }

    public MetinologNPC getMetinologNPC() {
        return metinologNPC;
    }

    public ServerManager getServerManager() {
        return serverManager;
    }

    public KlasyHelper getklasyHelper() {
        return klasyHelper;
    }

    public KlasyNPC getKlasyNPC() {
        return klasyNPC;
    }

    public MedykNPC getMedykNPC() {
        return medykNPC;
    }

    public NiebiosaManager getNiebiosaManager() {
        return niebiosaManager;
    }

    // SKRZYNKI ALL
    // inne
    public RoznosciManager getRoznosciManager() {
        return roznosciManager;
    }
    public TajemniczaManager getTajemniczaManager() {
        return tajemniczaManager;
    }
    // exp1
    public NajemnikManager getNajemnikManager() {
        return najemnikManager;
    }
    public WygnaniecManager getWygnaniecManager() {
        return wygnaniecManager;
    }

    public GornikNPC getGornikNPC() {
        return gornikNPC;
    }

    public PrzyrodnikNPC getPrzyrodnikNPC() {
        return przyrodnikNPC;
    }

    public ListaNPCManager getListaNPCManager() {
        return listaNPCManager;
    }

    public BaoManager getBaoManager() {
        return baoManager;
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public BonusesManager getBonusesManager() {
        return bonusesManager;
    }
    public PartyManager getPartyManager() {
        return partyManager;
    }

    public LowcaNPC getLowcaNPC() {
        return lowcaNPC;
    }

    public LesnikNPC getLesnikNPC() {
        return lesnikNPC;
    }

    public PetyManager getPetyManager() {
        return petyManager;
    }

    public ZwierzakiManager getZwierzakiManager() {
        return zwierzakiManager;
    }

    public OreManager getOreManager() {
        return oreManager;
    }

    public ZamekNieskonczonosciManager getZamekNieskonczonosciManager() {
        return zamekNieskonczonosciManager;
    }

    public DungeonsManager getDungeonsManager() {
        return dungeonsManager;
    }
}
