package rpg.rpgcore;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import io.lumine.xikage.mythicmobs.MythicMobs;
import me.filoghost.holographicdisplays.api.HolographicDisplaysAPI;
import net.minecraft.server.v1_8_R3.Item;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;
import rpg.rpgcore.BACKUP.BackupManager;
import rpg.rpgcore.BACKUP.commands.BackupCommand;
import rpg.rpgcore.BACKUP.database.BackupMongoManager;
import rpg.rpgcore.BACKUP.events.BackupInventoryClickListener;
import rpg.rpgcore.BACKUP.tasks.BackupTask;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.armor.ArmorEffectListener;
import rpg.rpgcore.armor.ArmorEffectTask;
import rpg.rpgcore.artefakty.ArtefaktyCommand;
import rpg.rpgcore.artefakty.events.ArtefaktyInteractListener;
import rpg.rpgcore.artefakty.events.ArtefaktyInventoryClickListener;
import rpg.rpgcore.bao.BaoManager;
import rpg.rpgcore.bao.events.BAOEntityInteract;
import rpg.rpgcore.bao.events.BAOInventoryClick;
import rpg.rpgcore.bao.events.BAOPlayerInteract;
import rpg.rpgcore.bonuses.BonusesManager;
import rpg.rpgcore.bossy.BossyManager;
import rpg.rpgcore.bossy.BossyTargetChangeListener;
import rpg.rpgcore.bossy.effects.PrzekletyCzarnoksieznik.PrzekletyCzarnoksieznikBossManager;
import rpg.rpgcore.bossy.effects.PrzekletyCzarnoksieznik.PrzekletyOdlamekInteractListener;
import rpg.rpgcore.bossy.effects.PrzekletyCzarnoksieznik.PrzekletyOdlamekInventoryClick;
import rpg.rpgcore.bossy.events.*;
import rpg.rpgcore.chat.commands.ChatCommand;
import rpg.rpgcore.chat.ChatManager;
import rpg.rpgcore.chat.events.AsyncPlayerChatListener;
import rpg.rpgcore.chat.events.ChatInventoryClickListener;
import rpg.rpgcore.chat.events.EQInventoryClose;
import rpg.rpgcore.chat.mute.MuteCommand;
import rpg.rpgcore.chat.mute.MuteManager;
import rpg.rpgcore.chat.mute.TempMuteCommand;
import rpg.rpgcore.chat.mute.UnMuteCommand;
import rpg.rpgcore.chests.DropFromChestsListener;
import rpg.rpgcore.chests.Dungeony.Dungeon60z70.PiekielnyWladcaManager;
import rpg.rpgcore.chests.Dungeony.IceTower.LodowySlugaManager;
import rpg.rpgcore.chests.Dungeony.IceTower.KrolLoduManager;
import rpg.rpgcore.chests.Expowisko1.DowodcaRozbojnikow;
import rpg.rpgcore.chests.Expowisko1.RozbojnikManager;
import rpg.rpgcore.chests.Expowisko10.PodziemnyRozpruwaczManager;
import rpg.rpgcore.chests.Expowisko11.MitycznyKrakenManager;
import rpg.rpgcore.chests.Expowisko12.KrysztalowyWladcaManager;
import rpg.rpgcore.chests.Expowisko13.StarozytnySmoczyCesarzManager;
import rpg.rpgcore.chests.Expowisko2.GoblinManager;
import rpg.rpgcore.chests.Expowisko2.WodzGoblinowManager;
import rpg.rpgcore.chests.Expowisko3.GorylManager;
import rpg.rpgcore.chests.Expowisko3.KrolGoryliManager;
import rpg.rpgcore.chests.Expowisko4.PrzekletaDuszaManager;
import rpg.rpgcore.chests.Expowisko4.ZjawaManager;
import rpg.rpgcore.chests.Expowisko5.StraznikSwiatyniManager;
import rpg.rpgcore.chests.Expowisko5.TrytonManager;
import rpg.rpgcore.chests.Expowisko6.MroznyWilkManager;
import rpg.rpgcore.chests.Expowisko7.PiekielnyRycerzManager;
import rpg.rpgcore.chests.Expowisko7.ZywiolakOgniaManager;
import rpg.rpgcore.chests.Expowisko8.MrocznaDuszaManager;
import rpg.rpgcore.chests.Expowisko8.PrzekletyCzarnoksieznikManager;
import rpg.rpgcore.chests.Expowisko9.MitycznyPajakManager;
import rpg.rpgcore.chests.Inne.*;
import rpg.rpgcore.chests.Npc.GornikChestManager;
import rpg.rpgcore.commands.admin.*;
import rpg.rpgcore.commands.admin.adminpanel.AdminPanelCommand;
import rpg.rpgcore.commands.admin.adminpanel.AdminPanelInventoryClick;
import rpg.rpgcore.commands.admin.adminpanel.AdminPanelManager;
import rpg.rpgcore.commands.admin.ban.BanCommand;
import rpg.rpgcore.commands.admin.ban.BanManager;
import rpg.rpgcore.commands.admin.ban.TempBanCommand;
import rpg.rpgcore.commands.admin.ban.UnBanCommand;
import rpg.rpgcore.commands.admin.dodatkowyexp.DodatkowyExpCommand;
import rpg.rpgcore.commands.admin.drop.DropCommand;
import rpg.rpgcore.commands.admin.god.GodCommand;
import rpg.rpgcore.commands.admin.god.GodManager;
import rpg.rpgcore.commands.admin.kolorki.KolorkiCommand;
import rpg.rpgcore.commands.admin.serverwhitelist.SerwerWhiteListCommand;
import rpg.rpgcore.commands.admin.serverwhitelist.SerwerWhiteListManager;
import rpg.rpgcore.commands.admin.teleport.TeleportCommand;
import rpg.rpgcore.commands.admin.teleport.TeleportHereCommand;
import rpg.rpgcore.commands.admin.teleport.TeleportManager;
import rpg.rpgcore.commands.admin.ustawieniakonta.UstawieniaKontaCommand;
import rpg.rpgcore.commands.admin.ustawieniakonta.UstawieniaKontaManager;
import rpg.rpgcore.commands.admin.ustawieniakonta.events.UstawieniaKontaInventoryClickListener;
import rpg.rpgcore.commands.admin.vanish.VanishCommand;
import rpg.rpgcore.commands.admin.vanish.VanishListener;
import rpg.rpgcore.commands.admin.vanish.VanishManager;
import rpg.rpgcore.commands.player.*;
import rpg.rpgcore.commands.player.administracja.AdministracjaCommand;
import rpg.rpgcore.commands.player.administracja.AdministracjaInventoryClick;
import rpg.rpgcore.commands.player.bossy.BossyCommand;
import rpg.rpgcore.commands.player.bossy.BossyInventoryClick;
import rpg.rpgcore.commands.player.craftingi.CraftingiCommand;
import rpg.rpgcore.commands.player.craftingi.CraftingiInventoryClickListener;
import rpg.rpgcore.commands.player.craftingi.CraftingiManager;
import rpg.rpgcore.commands.player.enderchest.EnderChestCommand;
import rpg.rpgcore.commands.player.enderchest.EnderChestInventoryCloseListener;
import rpg.rpgcore.commands.player.kosz.KoszCommand;
import rpg.rpgcore.commands.player.kosz.KoszInventoryClick;
import rpg.rpgcore.commands.player.kosz.KoszInventoryClose;
import rpg.rpgcore.commands.player.misje.MisjeCommand;
import rpg.rpgcore.commands.player.misje.MisjeInventoryClickListener;
import rpg.rpgcore.commands.player.profile.ProfileCommand;
import rpg.rpgcore.commands.player.profile.ProfileInventoryClickListener;
import rpg.rpgcore.commands.player.rangi.RangiCommand;
import rpg.rpgcore.commands.player.rangi.RangiInventoryClick;
import rpg.rpgcore.commands.player.rangi.ZestawRangiCommand;
import rpg.rpgcore.commands.player.rozpiska.RozpiskaCommand;
import rpg.rpgcore.commands.player.rozpiska.RozpiskaInventoryClick;
import rpg.rpgcore.commands.player.rozpiska.RozpiskaManager;
import rpg.rpgcore.commands.player.showcase.ShowcaseItemCommand;
import rpg.rpgcore.commands.player.showcase.ShowcaseItemInventoryClickListener;
import rpg.rpgcore.commands.player.showcase.ShowcaseItemManager;
import rpg.rpgcore.commands.player.topki.TopkiCommand;
import rpg.rpgcore.commands.player.topki.TopkiInventoryClickListener;
import rpg.rpgcore.commands.player.topki.TopkiManager;
import rpg.rpgcore.database.MongoManager;
import rpg.rpgcore.discord.DiscordBot;
import rpg.rpgcore.dmg.*;
import rpg.rpgcore.dodatki.DodatkiManager;
import rpg.rpgcore.dodatki.akcesoriaD.events.AkcesoriaDodatInteractListener;
import rpg.rpgcore.dodatki.akcesoriaD.events.AkcesoriaDodatInventoryClickListener;
import rpg.rpgcore.dodatki.akcesoriaP.events.AkcesoriaPodsInteractListener;
import rpg.rpgcore.dodatki.akcesoriaP.events.AkcesoriaPodsInventoryClick;
import rpg.rpgcore.dodatki.bony.events.BonyInteractListener;
import rpg.rpgcore.dodatki.bony.events.BonyInventoryClickListener;
import rpg.rpgcore.dodatki.events.DodatkiInventoryClick;
import rpg.rpgcore.dungeons.custom.DungeonsInventoryClick;
import rpg.rpgcore.dungeons.custom.DungeonsManager;
import rpg.rpgcore.dungeons.custom.zamekNieskonczonosci.ZamekNieskonczonosciManager;
import rpg.rpgcore.dungeons.custom.zamekNieskonczonosci.events.ZamekNieskonczonosciEntityDamgeListener;
import rpg.rpgcore.dungeons.custom.zamekNieskonczonosci.events.ZamekNieskonczonosciInventoryClick;
import rpg.rpgcore.dungeons.custom.zamekNieskonczonosci.events.ZamekNieskonczonosciMoveListener;
import rpg.rpgcore.dungeons.maps.icetower.IceTowerManager;
import rpg.rpgcore.dungeons.maps.icetower.events.IceTowerListener;
import rpg.rpgcore.dungeons.maps.icetower.tasks.IceTowerTask;
import rpg.rpgcore.dungeons.maps.piekielnyPrzedsionek.PrzedsionekManager;
import rpg.rpgcore.dungeons.maps.piekielnyPrzedsionek.events.PiekielnyPrzedsionekInteractListener;
import rpg.rpgcore.dungeons.maps.piekielnyPrzedsionek.events.PiekielnyPrzedsionekPortalEntryListener;
import rpg.rpgcore.dungeons.maps.piekielnyPrzedsionek.tasks.PiekielnyPrzedsionekTask;
import rpg.rpgcore.economy.EconomyPlayerInteract;
import rpg.rpgcore.economy.HsCommand;
import rpg.rpgcore.economy.KasaCommand;
import rpg.rpgcore.economy.WyplacCommand;
import rpg.rpgcore.entities.EntityTypes;
import rpg.rpgcore.guilds.GuildCommand;
import rpg.rpgcore.guilds.GuildManager;
import rpg.rpgcore.guilds.events.GuildEntityDeath;
import rpg.rpgcore.guilds.events.GuildsInventoryClick;
import rpg.rpgcore.guilds.events.GuildsPlayerDamage;
import rpg.rpgcore.history.HISTORYInventoryClick;
import rpg.rpgcore.history.HistoryCommand;
import rpg.rpgcore.inventory.InventoryCommand;
import rpg.rpgcore.inventory.InvseeInventoryClickListener;
import rpg.rpgcore.inventory.InvseeInventoryCloseListener;
import rpg.rpgcore.inventory.InvseeManager;
import rpg.rpgcore.klasy.KlasyManager;
import rpg.rpgcore.klasy.events.KlasyInteractListener;
import rpg.rpgcore.klasy.events.KlasyInventoryClickListener;
import rpg.rpgcore.klasy.tasks.KlasyTask;
import rpg.rpgcore.kociolki.KociolkiManager;
import rpg.rpgcore.listanpc.ListaNPCCommand;
import rpg.rpgcore.listanpc.ListaNPCInventoryClick;
import rpg.rpgcore.listanpc.ListaNPCManager;
import rpg.rpgcore.listeners.*;
import rpg.rpgcore.listeners.block.*;
import rpg.rpgcore.listeners.custom.AkcesoriumPickUpListener;
import rpg.rpgcore.listeners.custom.ItemSpawnListener;
import rpg.rpgcore.listeners.custom.NiesyPickUpListener;
import rpg.rpgcore.listeners.player.*;
import rpg.rpgcore.lvl.LvlCommand;
import rpg.rpgcore.lvl.LvlManager;
import rpg.rpgcore.lvl.artefaktyZaLvL.ArtefaktyZaLvlManager;
import rpg.rpgcore.managers.UserSaveManager;
import rpg.rpgcore.managers.CooldownManager;
import rpg.rpgcore.managers.NMSManager;
import rpg.rpgcore.managers.disabled.DisabledManager;
import rpg.rpgcore.metiny.MetinCommand;
import rpg.rpgcore.metiny.MetinyManager;
import rpg.rpgcore.msg.commands.IgnoreCommand;
import rpg.rpgcore.msg.MSGManager;
import rpg.rpgcore.msg.commands.MessageCommand;
import rpg.rpgcore.msg.commands.ReplyCommand;
import rpg.rpgcore.mythicstick.MythicStick;
import rpg.rpgcore.mythicstick.MythicstickPlayerInteract;
import rpg.rpgcore.newTarg.*;
import rpg.rpgcore.newTarg.commands.NewTargCommand;
import rpg.rpgcore.newTarg.commands.NewTargSprawdzCommand;
import rpg.rpgcore.newTarg.commands.NewTargWystawCommand;
import rpg.rpgcore.newTarg.events.NewTargInventoryClick;
import rpg.rpgcore.newTarg.events.NewTargInventoryClose;
import rpg.rpgcore.npc.czarownica.CzarownicaNPC;
import rpg.rpgcore.npc.czarownica.events.CzarownicaInventoryClickListener;
import rpg.rpgcore.npc.duszolog.DuszologNPC;
import rpg.rpgcore.npc.duszolog.events.DuszologDamageListener;
import rpg.rpgcore.npc.duszolog.events.DuszologInteractListener;
import rpg.rpgcore.npc.gornik.GornikNPC;
import rpg.rpgcore.npc.gornik.events.GornikBlockBreakListener;
import rpg.rpgcore.npc.gornik.events.GornikInventoryClickListener;
import rpg.rpgcore.npc.gornik.events.GornikItemInteractListener;
import rpg.rpgcore.npc.gornik.ore.OreManager;
import rpg.rpgcore.npc.handlarz.HandlarzNPC;
import rpg.rpgcore.npc.handlarz.events.HandlarzInteractListener;
import rpg.rpgcore.npc.handlarz.events.HandlarzInventoryClickListener;
import rpg.rpgcore.npc.handlarz.events.HandlarzInventoryCloseListener;
import rpg.rpgcore.npc.handlarz.events.PelerynkiInteractListener;
import rpg.rpgcore.npc.kolekcjoner.events.KolekcjonerInventoryClick;
import rpg.rpgcore.npc.kolekcjoner.KolekcjonerNPC;
import rpg.rpgcore.npc.kowal.events.KowalInventoryClickListener;
import rpg.rpgcore.npc.kowal.events.KowalInventoryCloseListener;
import rpg.rpgcore.npc.kowal.KowalNPC;
import rpg.rpgcore.npc.lesnik.events.LesnikInventoryClick;
import rpg.rpgcore.npc.lesnik.LesnikNPC;
import rpg.rpgcore.npc.lesnik.events.LesnikItemInteractListener;
import rpg.rpgcore.npc.lowca.events.LowcaInventoryClick;
import rpg.rpgcore.npc.lowca.LowcaNPC;
import rpg.rpgcore.npc.magazynier.MagazynierNPC;
import rpg.rpgcore.npc.magazynier.commands.MagazynyCommand;
import rpg.rpgcore.npc.magazynier.events.MagazynierInventoryClick;
import rpg.rpgcore.npc.magazynier.events.MagazynierInventoryClose;
import rpg.rpgcore.npc.medrzec.MedrzecNPC;
import rpg.rpgcore.npc.medrzec.events.MedrzecInteractListener;
import rpg.rpgcore.npc.medrzec.events.MedrzecInventoryClickListener;
import rpg.rpgcore.npc.metinolog.events.MetinologInventoryClick;
import rpg.rpgcore.npc.metinolog.MetinologNPC;
import rpg.rpgcore.npc.mistrz_yang.MistrzYangNPC;
import rpg.rpgcore.npc.mistrz_yang.events.MistrzYangInventoryClickListener;
import rpg.rpgcore.npc.mistyczny_kowal.MistycznyKowalManager;
import rpg.rpgcore.npc.mistyczny_kowal.events.MistycznyKowalInventoryClickListener;
import rpg.rpgcore.npc.przyrodnik.events.PrzyrodnikInventoryClick;
import rpg.rpgcore.npc.przyrodnik.PrzyrodnikNPC;
import rpg.rpgcore.npc.pustelnik.PustelnikNPC;
import rpg.rpgcore.npc.pustelnik.events.PustelnikInventoryClickListener;
import rpg.rpgcore.npc.rybak.RybakNPC;
import rpg.rpgcore.npc.rybak.events.*;
import rpg.rpgcore.npc.rzemieslnik.RzemieslnikManager;
import rpg.rpgcore.npc.rzemieslnik.events.RzemieslnikInventoryClickListener;
import rpg.rpgcore.npc.teleporter.TeleporterInventoryClick;
import rpg.rpgcore.npc.teleporter.TeleporterNPC;
import rpg.rpgcore.npc.wyslannik.WyslannikNPC;
import rpg.rpgcore.npc.wyslannik.events.WyslannikInventoryClickListener;
import rpg.rpgcore.osiagniecia.OsManager;
import rpg.rpgcore.osiagniecia.OsiagnieciaCommand;
import rpg.rpgcore.osiagniecia.events.OsInventoryClickListener;
import rpg.rpgcore.party.PartyManager;
import rpg.rpgcore.pets.commands.PetCommand;
import rpg.rpgcore.pets.PetyManager;
import rpg.rpgcore.pets.events.PetInteractListener;
import rpg.rpgcore.pets.events.PetInventoryClickListener;
import rpg.rpgcore.playerInventory.PlayerOpenInventoryListener;
import rpg.rpgcore.playerInventory.tasks.PlayerInventoryTask;
import rpg.rpgcore.pomoc.POMOCInventoryClick;
import rpg.rpgcore.pomoc.PomocCommand;
import rpg.rpgcore.pomoc.PomocManager;
import rpg.rpgcore.server.ServerManager;
import rpg.rpgcore.spawn.SpawnCommand;
import rpg.rpgcore.spawn.SpawnManager;
import rpg.rpgcore.tab.TabManager;
import rpg.rpgcore.tab.UpdateTabTask;
import rpg.rpgcore.tasks.*;
import rpg.rpgcore.trade.TradeManager;
import rpg.rpgcore.trade.events.TradeInventoryClickListener;
import rpg.rpgcore.trade.events.TradeInventoryCloseListener;
import rpg.rpgcore.user.UserManager;
import rpg.rpgcore.utils.Config;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.wyszkolenie.WyszkolenieManager;
import rpg.rpgcore.wyszkolenie.events.WyszkolenieInventoryClickListener;
import rpg.rpgcore.zmianki.ZmiankiManager;
import rpg.rpgcore.zmianki.events.ZmiankiInventoryClickListener;
import rpg.rpgcore.zmianki.events.ZmiankiInventoryCloseListener;
import rpg.rpgcore.zmianki.events.ZmiankiItemInteractListener;

import javax.security.auth.login.LoginException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public final class RPGCORE extends JavaPlugin {
    private static RPGCORE instance;
    private static DiscordBot discordBot;
    private static HolographicDisplaysAPI holographicDisplaysAPI;
    private static MythicMobs mythicMobs;
    private static WorldGuardPlugin worldGuard;
    private final Config config = new Config(this);
    private SpawnManager spawn;
    private MongoManager mongo;
    private BackupMongoManager backupMongo;
    private TeleportManager teleportManager;
    private BanManager banManager;
    private VanishManager vanishManager;
    private NMSManager nmsManager;
    private GodManager godManager;
    private AdminPanelManager adminPanelManager;
    private LvlManager lvlManager;
    private DamageManager damageManager;
    private ChatManager chatManager;
    private BaoManager baoManager;
    private OsManager osManager;
    private DodatkiManager dodatkiManager;
    private PomocManager pomocManager;
    private RozpiskaManager rozpiskaManager;
    private MuteManager muteManager;
    private MSGManager msgManager;
    private DuszologNPC duszologNPC;
    private TeleporterNPC teleporterNPC;
    private CooldownManager cooldownManager;
    private MagazynierNPC magazynierNPC;
    private RybakNPC rybakNPC;
    private GuildManager guildManager;
    private UserSaveManager userSaveManager;
    private HandlarzNPC handlarzNPC;
    private KowalNPC kowalNPC;
    private NewTargManager newTargManager;
    private KolekcjonerNPC kolekcjonerNPC;
    private PrzekletyCzarnoksieznikBossManager przekletyCzarnoksieznikBossManager;
    private MetinyManager metinyManager;
    private MetinologNPC metinologNPC;
    private ServerManager serverManager;
    private PrzyrodnikNPC przyrodnikNPC;
    private ListaNPCManager listaNPCManager;
    private UserManager userManager;
    private PartyManager partyManager;
    private LowcaNPC lowcaNPC;
    private LesnikNPC lesnikNPC;
    private PetyManager petyManager;
    private ZamekNieskonczonosciManager zamekNieskonczonosciManager;
    private BonusesManager bonusesManager;
    // ================================ SKRZYNKI INNE ================================
    private PozlacanySkarbManager pozlacanySkarbManager;
    private CiezkaSkrzyniaKowalaManager ciezkaSkrzyniaKowalaManager;
    private SurowceManager surowceManager;
    private TajemniczaManager tajemniczaManager;
    private ZwierzakiManager zwierzakiManager;
    // ================================ SKRZYNKI EXPOWISKO ================================
    // EXPOWISKO 1
    private RozbojnikManager najemnikManager;
    private DowodcaRozbojnikow wygnaniecManager;
    // EXPOWISKO 2
    private GoblinManager goblinManager;
    private WodzGoblinowManager wodzGoblinowManager;
    // EXPOWISKO 3
    private GorylManager gorylManager;
    private KrolGoryliManager krolGoryliManager;
    // EXPOWISKO 4
    private ZjawaManager zjawaManager;
    private PrzekletaDuszaManager przekletaDuszaManager;
    // EXPOWISKO 5
    private StraznikSwiatyniManager straznikSwiatyniManager;
    private TrytonManager trytonManager;
    // EXPOWISKO 6
    private MroznyWilkManager mroznyWilkManager;
    // EXPOWISKO 7
    private PiekielnyRycerzManager piekielnyRycerzManager;
    private ZywiolakOgniaManager zywiolakOgniaManager;
    // EXPOWISKO 8
    private MrocznaDuszaManager mrocznaDuszaManager;
    private PrzekletyCzarnoksieznikManager przekletyCzarnoksieznikManager;
    // EXPOWISKO 9
    private MitycznyPajakManager mitycznyPajakManager;
    // EXPOWISK0 10
    private PodziemnyRozpruwaczManager podziemnyRozpruwaczManager;
    // EXPOWISKO 11
    private MitycznyKrakenManager mitycznyKrakenManager;
    // Expowisko 12
    private KrysztalowyWladcaManager krysztalowyWladcaManager;
    // Expowisko 13
    private StarozytnySmoczyCesarzManager starozytnySmoczyCesarzManager;
    // ================================ SKRZYNKI DUNGEONY & MAPY SPECJALNE ================================
    // ice tower
    private KrolLoduManager krolLoduManager;
    private LodowySlugaManager lodowySlugaManager;
    // dung 60-70
    private PiekielnyWladcaManager piekielnyWladcaManager;
    // ================================ SKRZYNKI NPCTY & INNE ================================
    private GornikChestManager gornikChestManager;


    // cos innego...
    private DungeonsManager dungeonsManager;
    private ZmiankiManager zmiankiManager;
    private WyslannikNPC wyslannikNPC;
    private IceTowerManager iceTowerManager;
    // private TestNPC testNPC; // TU JEST TEST NPC
    private KociolkiManager kociolkiManager;
    private TopkiManager topkiManager;
    private CraftingiManager craftingiManager;
    private SerwerWhiteListManager serwerWhiteListManager;
    private ArtefaktyZaLvlManager artefaktyZaLvlManager;
    private ShowcaseItemManager showcaseItemManager;
    private DisabledManager disabledManager;
    private TradeManager tradeManager;
    private WyszkolenieManager wyszkolenieManager;
    private MedrzecNPC medrzecNPC;
    private InvseeManager invseeManager;
    private BossyManager bossyManager;
    private PustelnikNPC pustelnikNPC;
    private MistrzYangNPC mistrzYangNPC;
    private CzarownicaNPC czarownicaNPC;
    private PrzedsionekManager przedsionekManager;
    private UstawieniaKontaManager ustawieniaKontaManager;
    private MistycznyKowalManager mistycznyKowalManager;
    private RzemieslnikManager rzemieslnikManager;
    private GornikNPC gornikNPC;
    private OreManager oreManager;
    private KlasyManager klasyManager;
    private BackupManager backupManager;


    private int i = 1;

    public static RPGCORE getInstance() {
        return instance;
    }

    public static DiscordBot getDiscordBot() {
        return discordBot;
    }

    public static HolographicDisplaysAPI getHolographicDisplaysAPI() {
        return holographicDisplaysAPI;
    }

    public static MythicMobs getMythicMobs() {
        return mythicMobs;
    }
    public static WorldGuardPlugin getWorldGuard() {
        return worldGuard;
    }

    public void onEnable() {
        if (!Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays")) {
            getLogger().severe("*** HolographicDisplays is not installed or not enabled. ***");
            getLogger().severe("*** This plugin will be disabled. ***");
            this.setEnabled(false);
            return;
        }

        instance = this;
        holographicDisplaysAPI = HolographicDisplaysAPI.get(Bukkit.getServer().getPluginManager().getPlugin("HolographicDisplays"));
        mythicMobs = MythicMobs.inst();
        worldGuard = WorldGuardPlugin.inst();
        new PluginMessageReceiveListener(this);
        this.config.createConfig();
        this.initDatabase();
        this.initManagers();
        this.initNPCS();

        this.mongo.loadAll();


        this.getGuildManager().loadGuildLvlReq();
        this.autoMessage();

        this.initGlobalCommands();
        this.initEvents();
        this.initOwnDungeons();
        this.initCustomDungeons();
        this.initBosses();
        this.initTasks();
        //this.initPacketListeners();
        // BACKUP
        //this.mongo.tempUpdate();

        // TASKS


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
                    if (e.getName().contains("Duszek") || e.getName().contains("Zlota Rybka") || e.getName().contains("Pancernik") || e.getName().contains("Foka") ||
                            e.getName().contains("Nietoperz") || e.getName().contains("Bobr") || e.getName().contains("Ognisty Smok") || e.getName().contains("Demon") ||
                            e.getName().contains("Wampir")) {
                        e.remove();
                    }
                }
                if (e.getType().equals(EntityType.ENDER_CRYSTAL)) {
                    e.remove();
                }
            }
        }
        Bukkit.clearRecipes();
    }

    public void onDisable() {
        this.mongo.saveAllUsers();
        this.mongo.saveAllKlasy();
        this.mongo.saveAllMetins();
        this.mongo.saveAllBao();
        this.mongo.saveAllBaoArmorStands();
        this.mongo.saveAllDuszolog();
        this.mongo.saveAllGuilds();
        this.mongo.saveAllGornik();
        this.mongo.saveAllOre();
        this.mongo.saveAllTarg();
        this.mongo.saveAllMedrzec();
        this.mongo.saveAllKolekcjoner();
        this.mongo.saveAllPrzekletyCzarnoksieznikEffect();
        this.mongo.saveAllMetinolog();
        this.mongo.saveAllOs();
        this.mongo.saveAllPrzyrodnik();
        this.mongo.saveAllActivePets();
        this.mongo.saveAllDodatki();
        this.mongo.saveAllBonuses();
        this.mongo.saveAllWyslannik();
        this.mongo.saveAllUserPets();
        this.mongo.saveAllChatUsers();
        this.mongo.saveAllRybak();
        this.mongo.saveAllMagazyny();
        this.mongo.saveAllLowca();
        this.mongo.saveAllLesnik();
        this.mongo.saveAllKociolki();
        this.mongo.saveAllHandlarz();
        this.mongo.saveSerwerWhiteList(this.serwerWhiteListManager.getWhitelist());
        this.mongo.saveArtefaktyZaLvl(this.artefaktyZaLvlManager.getArtefaktyZaLvl());
        this.mongo.saveAllDisabled();
        this.mongo.saveAllWyszkolenie();
        this.mongo.saveAllPustelnik();
        this.mongo.saveDataBossy();
        this.mongo.saveAllMistrzYang();
        this.mongo.saveAllCzarownica();

        //this.mongo.clearDatabase();

        this.mongo.onDisable();
        this.backupMongo.onDisable();

        this.spawn.setSpawn(null);
        EntityTypes.despawnAllEntities();
        this.iceTowerManager.resetDungeon();
        this.bossyManager.reset70_80();
        this.bossyManager.despawnKlejnot(null, 1);
        this.bossyManager.despawnKlejnot(null, 2);
        this.getServer().getMessenger().unregisterIncomingPluginChannel(this, "rpgproxy:main:log");
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
        CommandAPI.getCommand().register("HellRPGCore", new ClearCommand());
        CommandAPI.getCommand().register("HellRPGCore", new KolorkiCommand());
        CommandAPI.getCommand().register("HellRPGCore", new LvlCommand(this));
        CommandAPI.getCommand().register("HellRPGCore", new GmCommand());
        CommandAPI.getCommand().register("HellRPGCore", new HealCommand(this));
        CommandAPI.getCommand().register("HellRPGCore", new TempBanCommand(this));
        CommandAPI.getCommand().register("HellRPGCore", new OsiagnieciaCommand());
        CommandAPI.getCommand().register("HellRPGCore", new PomocCommand(this));
        CommandAPI.getCommand().register("HellRPGCore", new MuteCommand(this));
        CommandAPI.getCommand().register("HellRPGCore", new UnMuteCommand(this));
        CommandAPI.getCommand().register("HellRPGCore", new TempMuteCommand(this));
        CommandAPI.getCommand().register("HellRPGCore", new MessageCommand(this));
        CommandAPI.getCommand().register("HellRPGCore", new ReplyCommand(this));
        CommandAPI.getCommand().register("HellRPGCore", new NewTargCommand(this));
        CommandAPI.getCommand().register("HellRPGCore", new KasaCommand(this));
        CommandAPI.getCommand().register("HellRPGCore", new WyplacCommand(this));
        CommandAPI.getCommand().register("HellRPGCore", new NewTargWystawCommand(this));
        CommandAPI.getCommand().register("HellRPGCore", new NewTargSprawdzCommand(this));
        CommandAPI.getCommand().register("HellRPGCore", new HelpOPCommand(this));
        CommandAPI.getCommand().register("HellRPGCore", new MetinCommand(this));
        CommandAPI.getCommand().register("HellRPGCore", new DodatkowyExpCommand());
        CommandAPI.getCommand().register("HellRPGCore", new ChatCommand(this));
        CommandAPI.getCommand().register("HellRPGCore", new BossyCommand());
        CommandAPI.getCommand().register("HellRPGCore", new RozpiskaCommand(this));
        CommandAPI.getCommand().register("HellRPGCore", new OnlineCommand());
        CommandAPI.getCommand().register("HellRPGCore", new PingCommand());
        CommandAPI.getCommand().register("HellRPGCore", new KontaktCommand());
        CommandAPI.getCommand().register("HellRPGCore", new RangiCommand());
        CommandAPI.getCommand().register("HellRPGCore", new ZestawRangiCommand(this));
        CommandAPI.getCommand().register("HellRPGCore", new ListaNPCCommand());
        CommandAPI.getCommand().register("HellRPGCore", new GuildCommand(this));
        CommandAPI.getCommand().register("HellRPGCore", new KoszCommand());
        CommandAPI.getCommand().register("HellRPGCore", new MythicStick());
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
        CommandAPI.getCommand().register("HellRPGCore", new EnchantCustomCommand());
        CommandAPI.getCommand().register("HellRPGCore", new EnchantCommand());
        CommandAPI.getCommand().register("HellRPGCore", new MemoryCommand());
        CommandAPI.getCommand().register("HellRPGCore", new IgnoreCommand());
        CommandAPI.getCommand().register("HellRPGCore", new AdminChatCommand());
        CommandAPI.getCommand().register("HellRPGCore", new DodatkiCommand());
        CommandAPI.getCommand().register("HellRPGCore", new GiveAkcesoriaCommand());
        CommandAPI.getCommand().register("HellRPGCore", new ProfileCommand());
        CommandAPI.getCommand().register("HellRPGCore", new MagazynyCommand());
        CommandAPI.getCommand().register("HellRPGCore", new SaveStopCommand());
        CommandAPI.getCommand().register("HellRPGCore", new RozdajCommand());
        CommandAPI.getCommand().register("HellRPGCore", new AdministracjaCommand());
        CommandAPI.getCommand().register("HellRPGCore", new BroadcastCommand());
        CommandAPI.getCommand().register("HellRPGCore", new AdminPanelCommand(this));
        CommandAPI.getCommand().register("HellRPGCore", new TowerCommand());
        CommandAPI.getCommand().register("HellRPGCore", new MisjeCommand(this));
        CommandAPI.getCommand().register("HellRPGCore", new ItemShopCommand());
        CommandAPI.getCommand().register("HellRPGCore", new PdCommand());
        CommandAPI.getCommand().register("HellRPGCore", new ArtefaktyCommand());
        CommandAPI.getCommand().register("HellRPGCore", new EnderChestCommand());
        CommandAPI.getCommand().register("HellRPGCore", new TopkiCommand(this));
        CommandAPI.getCommand().register("HellRPGCore", new CraftingiCommand());
        CommandAPI.getCommand().register("HellRPGCore", new SerwerWhiteListCommand());
        CommandAPI.getCommand().register("HellRPGCore", new ShowcaseItemCommand());
        CommandAPI.getCommand().register("HellRPGCore", new SetNameCommand());
        CommandAPI.getCommand().register("HellRPGCore", new HsCommand(this));
        CommandAPI.getCommand().register("HellRPGCore", new DisableCommand(this));
        CommandAPI.getCommand().register("HellRPGCore", new ChangeMobsCommand());
        CommandAPI.getCommand().register("HellRPGCore", new ResetDungeonCommand(this));
        CommandAPI.getCommand().register("HellRPGCore", new UstawieniaKontaCommand(this));
        CommandAPI.getCommand().register("HellRPGCore", new LiveCommand(this));
        CommandAPI.getCommand().register("HellRPGCore", new GammaCommand());
        CommandAPI.getCommand().register("HellRPGCore", new DropCommand());
        CommandAPI.getCommand().register("HellRPGCore", new GornikZaplacCommand(this));
        CommandAPI.getCommand().register("HellRPGCore", new BackupCommand(this));
    }

    private void initEvents() {
        this.getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        this.getServer().getPluginManager().registerEvents(new PlayerQuitListener(this), this);
        this.getServer().getPluginManager().registerEvents(new EntityDeathListener(this), this);
        this.getServer().getPluginManager().registerEvents(new EntityDamageEntityListener(this), this);
        this.getServer().getPluginManager().registerEvents(new AsyncPlayerChatListener(this), this);
        this.getServer().getPluginManager().registerEvents(new EntityDamageListener(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerInteractEntityListener(this), this);
        this.getServer().getPluginManager().registerEvents(new BlockBreakListener(this), this);
        this.getServer().getPluginManager().registerEvents(new BlockPlaceListener(this), this);
        this.getServer().getPluginManager().registerEvents(new ItemSpawnListener(), this);
        this.getServer().getPluginManager().registerEvents(new BlockInteractListener(this), this);
        this.getServer().getPluginManager().registerEvents(new PlayerItemDamageListener(), this);
        this.getServer().getPluginManager().registerEvents(new FoodLevelChangeListener(), this);
        this.getServer().getPluginManager().registerEvents(new InventoryItemDragListener(), this);
        this.getServer().getPluginManager().registerEvents(new NiesyPickUpListener(this), this);
        this.getServer().getPluginManager().registerEvents(new PrzekletyOdlamekInventoryClick(), this);
        this.getServer().getPluginManager().registerEvents(new PrzekletyOdlamekInteractListener(), this);
        this.getServer().getPluginManager().registerEvents(new AkcesoriumPickUpListener(), this);
        this.getServer().getPluginManager().registerEvents(new EntityCombustListener(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerTeleportListener(this), this);
        this.getServer().getPluginManager().registerEvents(new WeatherChangeListener(), this);
        // BAO
        this.getServer().getPluginManager().registerEvents(new BAOInventoryClick(this), this);
        this.getServer().getPluginManager().registerEvents(new BAOEntityInteract(this), this);
        this.getServer().getPluginManager().registerEvents(new BAOPlayerInteract(this), this);

        // OS
        this.getServer().getPluginManager().registerEvents(new OsInventoryClickListener(this), this);

        // TRADE
        this.getServer().getPluginManager().registerEvents(new TradeInventoryClickListener(this), this);
        this.getServer().getPluginManager().registerEvents(new TradeInventoryCloseListener(this), this);

        // TARG
        this.getServer().getPluginManager().registerEvents(new NewTargInventoryClick(this), this);
        this.getServer().getPluginManager().registerEvents(new NewTargInventoryClose(), this);

        // POMOC
        this.getServer().getPluginManager().registerEvents(new POMOCInventoryClick(this), this);

        // ADMIN PANEL
        this.getServer().getPluginManager().registerEvents(new AdminPanelInventoryClick(this), this);
        // EQ
        this.getServer().getPluginManager().registerEvents(new EQInventoryClose(this), this);
        this.getServer().getPluginManager().registerEvents(new ChatInventoryClickListener(this), this);

        // AKCESORIA
        this.getServer().getPluginManager().registerEvents(new DodatkiInventoryClick(), this);
        // ... PODSTAWOWE
        this.getServer().getPluginManager().registerEvents(new AkcesoriaPodsInteractListener(this), this);
        this.getServer().getPluginManager().registerEvents(new AkcesoriaPodsInventoryClick(this), this);
        // ... DODATKOWE
        this.getServer().getPluginManager().registerEvents(new AkcesoriaDodatInteractListener(this), this);
        this.getServer().getPluginManager().registerEvents(new AkcesoriaDodatInventoryClickListener(this), this);
        // ... BONY
        this.getServer().getPluginManager().registerEvents(new BonyInteractListener(this), this);
        this.getServer().getPluginManager().registerEvents(new BonyInventoryClickListener(this), this);

        // HISTORY
        this.getServer().getPluginManager().registerEvents(new HISTORYInventoryClick(), this);

        // ECONOMY
        this.getServer().getPluginManager().registerEvents(new EconomyPlayerInteract(this), this);

        // GUILDS
        this.getServer().getPluginManager().registerEvents(new GuildsInventoryClick(this), this);
        this.getServer().getPluginManager().registerEvents(new GuildsPlayerDamage(this), this);
        this.getServer().getPluginManager().registerEvents(new GuildEntityDeath(this), this);


        // Rangi command
        this.getServer().getPluginManager().registerEvents(new RangiInventoryClick(), this);

        // Administracja command
        this.getServer().getPluginManager().registerEvents(new AdministracjaInventoryClick(), this);

        // KOSZ
        this.getServer().getPluginManager().registerEvents(new KoszInventoryClick(), this);
        this.getServer().getPluginManager().registerEvents(new KoszInventoryClose(), this);

        // PROFILE
        this.getServer().getPluginManager().registerEvents(new ProfileInventoryClickListener(), this);

        // MISJE
        this.getServer().getPluginManager().registerEvents(new MisjeInventoryClickListener(), this);

        // TOPKI
        this.getServer().getPluginManager().registerEvents(new TopkiInventoryClickListener(), this);

        // CRAFTINGI
        this.getServer().getPluginManager().registerEvents(new CraftingiInventoryClickListener(), this);

        // KLASY

        // WYSZKOLENIE
        this.getServer().getPluginManager().registerEvents(new WyszkolenieInventoryClickListener(this), this);

        // BOSSY
        this.getServer().getPluginManager().registerEvents(new BossyInventoryClick(), this);

        // ROZPISKA
        this.getServer().getPluginManager().registerEvents(new RozpiskaInventoryClick(this), this);

        // LISTANPC
        this.getServer().getPluginManager().registerEvents(new ListaNPCInventoryClick(), this);

        // INNE

        // MythicSTICK
        this.getServer().getPluginManager().registerEvents(new MythicstickPlayerInteract(), this);

        // NPC

        // ...DUSZOLOG
        this.getServer().getPluginManager().registerEvents(new DuszologDamageListener(), this);
        this.getServer().getPluginManager().registerEvents(new DuszologInteractListener(), this);

        // ...WYSLANNIK
        this.getServer().getPluginManager().registerEvents(new WyslannikInventoryClickListener(), this);


        // ...RYBAK
        this.getServer().getPluginManager().registerEvents(new PlayerFishListener(this), this);
        this.getServer().getPluginManager().registerEvents(new RybakInventoryClick(this), this);
        this.getServer().getPluginManager().registerEvents(new RybakInventoryCloseListener(), this);
        this.getServer().getPluginManager().registerEvents(new RybakInteractListener(this), this);

        this.getServer().getPluginManager().registerEvents(new RybakRegionEnterListener(this), this);

        // ...MAGAZYNIER
        this.getServer().getPluginManager().registerEvents(new MagazynierInventoryClick(this), this);
        this.getServer().getPluginManager().registerEvents(new MagazynierInventoryClose(this), this);

        // ...TELEPORTER
        this.getServer().getPluginManager().registerEvents(new TeleporterInventoryClick(this), this);

        // ...HANDLARZ
        this.getServer().getPluginManager().registerEvents(new HandlarzInventoryClickListener(this), this);
        this.getServer().getPluginManager().registerEvents(new HandlarzInventoryCloseListener(this), this);
        this.getServer().getPluginManager().registerEvents(new HandlarzInteractListener(), this);
        this.getServer().getPluginManager().registerEvents(new PelerynkiInteractListener(), this);

        // ...KOWAL
        this.getServer().getPluginManager().registerEvents(new KowalInventoryClickListener(this), this);
        this.getServer().getPluginManager().registerEvents(new KowalInventoryCloseListener(), this);

        // ...KOLEKCJONER
        this.getServer().getPluginManager().registerEvents(new KolekcjonerInventoryClick(this), this);

        // ...METINOLOG
        this.getServer().getPluginManager().registerEvents(new MetinologInventoryClick(), this);

        // ...GORNIK
        this.getServer().getPluginManager().registerEvents(new GornikInventoryClickListener(this), this);
        this.getServer().getPluginManager().registerEvents(new GornikBlockBreakListener(this), this);
        this.getServer().getPluginManager().registerEvents(new GornikItemInteractListener(), this);

        // ...PRZYRODNIK
        this.getServer().getPluginManager().registerEvents(new PrzyrodnikInventoryClick(), this);

        // ...LOWCA
        this.getServer().getPluginManager().registerEvents(new LowcaInventoryClick(this), this);

        // ...LESNIK
        this.getServer().getPluginManager().registerEvents(new LesnikInventoryClick(this), this);
        this.getServer().getPluginManager().registerEvents(new LesnikItemInteractListener(), this);

        // ...MEDRZEC
        this.getServer().getPluginManager().registerEvents(new MedrzecInventoryClickListener(this), this);
        this.getServer().getPluginManager().registerEvents(new MedrzecInteractListener(this), this);

        // ...PUSTELNIK
        this.getServer().getPluginManager().registerEvents(new PustelnikInventoryClickListener(this), this);

        // ...MISTRZ YANG
        this.getServer().getPluginManager().registerEvents(new MistrzYangInventoryClickListener(), this);

        // ...CZAROWNICA
        this.getServer().getPluginManager().registerEvents(new CzarownicaInventoryClickListener(this), this);

        // ...MISTYCZNY KOWAL
        this.getServer().getPluginManager().registerEvents(new MistycznyKowalInventoryClickListener(), this);

        // ...RZEMIESLNIK
        this.getServer().getPluginManager().registerEvents(new RzemieslnikInventoryClickListener(), this);

        // DUNGEONS

        // ...ICE TOWER
        this.getServer().getPluginManager().registerEvents(new IceTowerListener(), this);


        // INVSEE
        this.getServer().getPluginManager().registerEvents(new InvseeInventoryCloseListener(this), this);
        this.getServer().getPluginManager().registerEvents(new InvseeInventoryClickListener(), this);
        //this.getServer().getPluginManager().registerEvents(new KeyClickListener(), this);

        // PETY
        this.getServer().getPluginManager().registerEvents(new PetInventoryClickListener(this), this);
        this.getServer().getPluginManager().registerEvents(new PetInteractListener(this), this);

        // ZMIANKI
        this.getServer().getPluginManager().registerEvents(new ZmiankiInventoryClickListener(), this);
        this.getServer().getPluginManager().registerEvents(new ZmiankiInventoryCloseListener(), this);
        this.getServer().getPluginManager().registerEvents(new ZmiankiItemInteractListener(), this);

        // EFFEKTY ARMOR
        this.getServer().getPluginManager().registerEvents(new ArmorEffectListener(this), this);

        // ARTEFAKTY
        this.getServer().getPluginManager().registerEvents(new ArtefaktyInventoryClickListener(), this);
        this.getServer().getPluginManager().registerEvents(new ArtefaktyInteractListener(this), this);

        // PLAYER INVENTORY
        this.getServer().getPluginManager().registerEvents(new PlayerOpenInventoryListener(), this);

        // SHOWCASE ITEM
        this.getServer().getPluginManager().registerEvents(new ShowcaseItemInventoryClickListener(), this);

        // ENDERCHEST
        this.getServer().getPluginManager().registerEvents(new EnderChestInventoryCloseListener(), this);

        // BOSSY
        this.getServer().getPluginManager().registerEvents(new BossyTargetChangeListener(), this);

        // USTAWIENIA KONTA
        this.getServer().getPluginManager().registerEvents(new UstawieniaKontaInventoryClickListener(this), this);

        // KLASY
        this.getServer().getPluginManager().registerEvents(new KlasyInventoryClickListener(this), this);
        this.getServer().getPluginManager().registerEvents(new KlasyInteractListener(this), this);

        //VANISH
        this.getServer().getPluginManager().registerEvents(new VanishListener(this), this);

        // BACKUP
        this.getServer().getPluginManager().registerEvents(new BackupInventoryClickListener(this), this);

    }

    private void initDatabase() {
        this.mongo = new MongoManager(this);
        this.backupMongo = new BackupMongoManager(this);
    }

    private void initManagers() {
        this.userManager = new UserManager(this);
        this.spawn = new SpawnManager();
        this.teleportManager = new TeleportManager(this);
        this.banManager = new BanManager(this);
        this.vanishManager = new VanishManager(this);
        this.nmsManager = new NMSManager();
        this.godManager = new GodManager(this);
        this.adminPanelManager = new AdminPanelManager();
        this.lvlManager = new LvlManager(this);
        this.damageManager = new DamageManager(this);
        this.chatManager = new ChatManager(this);
        this.osManager = new OsManager(this);
        this.dodatkiManager = new DodatkiManager(this);
        this.pomocManager = new PomocManager();
        this.muteManager = new MuteManager(this);
        this.msgManager = new MSGManager();
        this.tradeManager = new TradeManager();
        this.przekletyCzarnoksieznikBossManager = new PrzekletyCzarnoksieznikBossManager(this);
        this.cooldownManager = new CooldownManager();
        this.guildManager = new GuildManager(this);
        new TabManager(this);
        this.newTargManager = new NewTargManager(this);
        this.userSaveManager = new UserSaveManager(this);
        this.metinyManager = new MetinyManager(this);
        this.serverManager = new ServerManager(this);
        this.listaNPCManager = new ListaNPCManager(this);
        this.baoManager = new BaoManager(this);
        this.bonusesManager = new BonusesManager(this);
        this.magazynierNPC = new MagazynierNPC(this);
        this.partyManager = new PartyManager();
        this.petyManager = new PetyManager(this);
        this.rozpiskaManager = new RozpiskaManager(this);
        this.zmiankiManager = new ZmiankiManager();
        this.kociolkiManager = new KociolkiManager(this);
        this.topkiManager = new TopkiManager(this);
        this.craftingiManager = new CraftingiManager();
        this.serwerWhiteListManager = new SerwerWhiteListManager(this);
        this.artefaktyZaLvlManager = new ArtefaktyZaLvlManager(this);
        this.showcaseItemManager = new ShowcaseItemManager();
        this.disabledManager = new DisabledManager(this);
        this.wyszkolenieManager = new WyszkolenieManager(this);
        this.invseeManager = new InvseeManager();
        this.ustawieniaKontaManager = new UstawieniaKontaManager(this);
        this.oreManager = new OreManager(this);
        this.klasyManager = new KlasyManager(this);
        this.backupManager = new BackupManager(this);
    }

    private void initNPCS() {
        this.duszologNPC = new DuszologNPC(this);
        this.teleporterNPC = new TeleporterNPC(this);
        this.rybakNPC = new RybakNPC(this);
        this.wyslannikNPC = new WyslannikNPC(this);
        this.kolekcjonerNPC = new KolekcjonerNPC(this);
        this.handlarzNPC = new HandlarzNPC(this);
        this.kowalNPC = new KowalNPC(this);
        this.metinologNPC = new MetinologNPC(this);
        this.przyrodnikNPC = new PrzyrodnikNPC(this);
        this.lowcaNPC = new LowcaNPC(this);
        this.lesnikNPC = new LesnikNPC(this);
        this.medrzecNPC = new MedrzecNPC(this);
        //this.testNPC = new TestNPC(this); // TU INICJALIZUJESZ NPC
        this.pustelnikNPC = new PustelnikNPC(this);
        this.mistrzYangNPC = new MistrzYangNPC(this);
        this.czarownicaNPC = new CzarownicaNPC(this);
        this.mistycznyKowalManager = new MistycznyKowalManager();
        this.gornikNPC = new GornikNPC(this);
        this.rzemieslnikManager = new RzemieslnikManager();
    }

    private void initChests() {
        this.getServer().getPluginManager().registerEvents(new DropFromChestsListener(this), this);
        // ================================ SKRZYNKI INNE ================================
        this.pozlacanySkarbManager = new PozlacanySkarbManager();
        this.ciezkaSkrzyniaKowalaManager = new CiezkaSkrzyniaKowalaManager();
        this.surowceManager = new SurowceManager();
        this.tajemniczaManager = new TajemniczaManager();
        this.zwierzakiManager = new ZwierzakiManager();
        // ================================ SKRZYNKI EXPOWISKO ================================
        // EXPOWISKO 1
        this.najemnikManager = new RozbojnikManager();
        this.wygnaniecManager = new DowodcaRozbojnikow();
        // EXPOWISKO 2
        this.wodzGoblinowManager = new WodzGoblinowManager();
        this.goblinManager = new GoblinManager();
        // EXPOWISKO 3
        this.gorylManager = new GorylManager();
        this.krolGoryliManager = new KrolGoryliManager();
        // EXPOWISKO 4
        this.zjawaManager = new ZjawaManager();
        this.przekletaDuszaManager = new PrzekletaDuszaManager();
        // EXPOWISKO 5
        this.straznikSwiatyniManager = new StraznikSwiatyniManager();
        this.trytonManager = new TrytonManager();
        // EXPOWISKO 6
        this.mroznyWilkManager = new MroznyWilkManager();
        // EXPOWISKO 7
        this.piekielnyRycerzManager = new PiekielnyRycerzManager();
        this.zywiolakOgniaManager = new ZywiolakOgniaManager();
        // EXPOWISKO 8
        this.mrocznaDuszaManager = new MrocznaDuszaManager();
        this.przekletyCzarnoksieznikManager = new PrzekletyCzarnoksieznikManager();
        // EXPOWISKO 9
        this.mitycznyPajakManager = new MitycznyPajakManager();
        // EXPOWISKO 10
        this.podziemnyRozpruwaczManager = new PodziemnyRozpruwaczManager();
        // EXPOWISKO 11
        this.mitycznyKrakenManager = new MitycznyKrakenManager();
        // EXPOWISKO 12
        this.krysztalowyWladcaManager = new KrysztalowyWladcaManager();
        // EXPOWISKO 13
        this.starozytnySmoczyCesarzManager = new StarozytnySmoczyCesarzManager();
        // ================================ SKRZYNKI DUNGEONY & MAPY SPECJALNE ================================
        // ice tower
        this.krolLoduManager = new KrolLoduManager();
        this.lodowySlugaManager = new LodowySlugaManager();
        // dungeon 60-70
        this.piekielnyWladcaManager = new PiekielnyWladcaManager();
        // ================================ SKRZYNKI NPCTY & INNE ================================
        this.gornikChestManager = new GornikChestManager();
    }

    public void initOwnDungeons() {
        // DEMON TOWER
        this.iceTowerManager = new IceTowerManager(this);

        // PIEKIELNY PRZEDSIONEK
        this.przedsionekManager = new PrzedsionekManager(this);
        this.getServer().getPluginManager().registerEvents(new PiekielnyPrzedsionekInteractListener(this), this);
        this.getServer().getPluginManager().registerEvents(new PiekielnyPrzedsionekPortalEntryListener(this), this);
    }

    private void initCustomDungeons() {
        // ZAMEK NIESKONCZONOSCI
        this.dungeonsManager = new DungeonsManager();
        this.zamekNieskonczonosciManager = new ZamekNieskonczonosciManager(this);
        this.getServer().getPluginManager().registerEvents(new DungeonsInventoryClick(this), this);
        this.getServer().getPluginManager().registerEvents(new ZamekNieskonczonosciMoveListener(this), this);
        this.getServer().getPluginManager().registerEvents(new ZamekNieskonczonosciEntityDamgeListener(), this);
        this.getServer().getPluginManager().registerEvents(new ZamekNieskonczonosciInventoryClick(), this);
    }

    private void initBosses() {
        this.bossyManager = new BossyManager();
        this.getServer().getPluginManager().registerEvents(new DowodcaRozbojnikowListener1_10(), this);
        this.getServer().getPluginManager().registerEvents(new WodzGoblinowListener10_20(), this);
        this.getServer().getPluginManager().registerEvents(new KrolGoryliListener20_30(), this);
        this.getServer().getPluginManager().registerEvents(new PrzekletaDuszaListener30_40(), this);
        this.getServer().getPluginManager().registerEvents(new TrytonListener40_50(), this);
        this.getServer().getPluginManager().registerEvents(new PiekielnyRycerzListener60_70(), this);
        this.getServer().getPluginManager().registerEvents(new PrzekletyCzarnoksieznikListener(), this);
        this.getServer().getPluginManager().registerEvents(new MitycznyPajakListener80_90(), this);
        this.getServer().getPluginManager().registerEvents(new PodwodnyStraznikEntityInteractListener(), this);
        this.getServer().getPluginManager().registerEvents(new KrysztalowyWladca110_120Listener(this), this);
        this.getServer().getPluginManager().registerEvents(new SmoczyCesarzListener(), this);
    }

    private void initTasks() {
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
        // ...BOSS BAR
        new BossBarTask(this);
        // ... KOCIOLKI I ARTEFAKTY
        new KociolkiTask(this);
        // ... TOPKI
        new TopkiTask(this);
        // ... PLAYER CRAFTIN ITEMS
        new PlayerInventoryTask(this);
        // ... PIEKIELNY PRZEDSIONEK
        new PiekielnyPrzedsionekTask(this);
        // ... ICE TOWER
        new IceTowerTask(this);

        // KLASY
        new KlasyTask(this);

        new ArmorEffectTask(this);
        new GodVanishTask(this);
        new BackupTask(this);
    }

    private void fixBuckets() {
        try {
            final Field f = Item.class.getDeclaredField("maxStackSize");
            f.setAccessible(true);
            f.setInt(Item.REGISTRY.a(326), 16);
            f.setInt(Item.REGISTRY.a(327), 16);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void autoMessage() {
        if (getConfig().getBoolean("auto_message")) {
            final List<String> messages = new ArrayList<>();
            final int sciezki = getConfig().getConfigurationSection("auto_messages").getKeys(false).size();
            final ConfigurationSection sec = getConfig().getConfigurationSection("auto_messages");
            for (int i = 1; i <= sciezki; i++) {
                messages.add(sec.getString("auto_message_" + i));
            }
            final int time = getConfig().getInt("auto_message_time");
            this.getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
                if (i > sciezki - 1) {
                    i = 0;
                }
                Bukkit.broadcastMessage(Utils.SERVERNAME + Utils.format(messages.get(i)));
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

    public BackupMongoManager getBackupMongoManager() {
        return backupMongo;
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

    public AdminPanelManager getAdminPanelManager() {
        return adminPanelManager;
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

    public KolekcjonerNPC getKolekcjonerNPC() {
        return kolekcjonerNPC;
    }

    public GuildManager getGuildManager() {
        return guildManager;
    }

    public NewTargManager getNewTargManager() {
        return newTargManager;
    }

    public UserSaveManager getUserSaveManager() {
        return userSaveManager;
    }

    public HandlarzNPC getHandlarzNPC() {
        return handlarzNPC;
    }

    public KowalNPC getKowalNPC() {
        return kowalNPC;
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

    // ================================ SKRZYNKI INNE ================================
    public PozlacanySkarbManager getPozlacanySkarbManager() {
        return pozlacanySkarbManager;
    }

    public CiezkaSkrzyniaKowalaManager getCiezkaSkrzyniaKowalaManager() {
        return ciezkaSkrzyniaKowalaManager;
    }

    public SurowceManager getSurowceManager() {
        return surowceManager;
    }

    public TajemniczaManager getTajemniczaManager() {
        return tajemniczaManager;
    }

    public ZwierzakiManager getZwierzakiManager() {
        return zwierzakiManager;
    }

    // ================================ SKRZYNKI EXPOWISKA ===============================
    // exp1
    public RozbojnikManager getNajemnikManager() {
        return najemnikManager;
    }

    public DowodcaRozbojnikow getDowodcaRozbojnikow() {
        return wygnaniecManager;
    }

    // exp2
    public GoblinManager getGoblinManager() {
        return goblinManager;
    }

    public WodzGoblinowManager getWodzGoblinowManager() {
        return wodzGoblinowManager;
    }

    // exp3
    public GorylManager getGorylManager() {
        return gorylManager;
    }

    public KrolGoryliManager getKrolGoryliManager() {
        return krolGoryliManager;
    }

    // exp4
    public ZjawaManager getZjawaManager() {
        return zjawaManager;
    }

    public PrzekletaDuszaManager getPrzekletaDuszaManager() {
        return przekletaDuszaManager;
    }

    // exp5
    public StraznikSwiatyniManager getStraznikSwiatyniManager() {
        return straznikSwiatyniManager;
    }

    public TrytonManager getTrytonManager() {
        return trytonManager;
    }

    // exp6
    public MroznyWilkManager getMroznyWilkManager() {
        return mroznyWilkManager;
    }

    // exp7
    public PiekielnyRycerzManager getPrzekletyRycerzManager() {
        return piekielnyRycerzManager;
    }

    public ZywiolakOgniaManager getZywiolakOgniaManager() {
        return zywiolakOgniaManager;
    }

    // exp8
    public MrocznaDuszaManager getMrocznaDuszaManager() {
        return mrocznaDuszaManager;
    }

    public PrzekletyCzarnoksieznikManager getPrzekletyCzarnoksieznikManager() {
        return przekletyCzarnoksieznikManager;
    }

    // exp9
    public MitycznyPajakManager getMitycznyPajakManager() {
        return mitycznyPajakManager;
    }

    // exp10
    public PodziemnyRozpruwaczManager getPodziemnyRozpruwaczManager() {
        return podziemnyRozpruwaczManager;
    }

    // exp11
    public MitycznyKrakenManager getMitycznyKrakenManager() {
        return mitycznyKrakenManager;
    }

    // exp12
    public KrysztalowyWladcaManager getKrysztalowyWladcaManager() {
        return krysztalowyWladcaManager;
    }

    // exp13
    public StarozytnySmoczyCesarzManager getStarozytnySmoczyCesarzManager() {
        return starozytnySmoczyCesarzManager;
    }

    // ================================ SKRZYNKI DUNGEONY & MAPY SPECJALNE ===============================
    // ice tower
    public KrolLoduManager getKrolLoduManager() {
        return krolLoduManager;
    }
    public LodowySlugaManager getLodowySlugaManager() {
        return lodowySlugaManager;
    }
    // dungeon 60-70
    public PiekielnyWladcaManager getPiekielnyWladcaManager() { return piekielnyWladcaManager;}
    // ================================ SKRZYNKI NPCTY & INNE ================================
    public GornikChestManager getGornikChestManager() {
        return gornikChestManager;
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

    public RozpiskaManager getRozpiskaManager() {
        return rozpiskaManager;
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

    public WyslannikNPC getWyslannikNPC() { return wyslannikNPC; }

    public PrzekletyCzarnoksieznikBossManager getPrzekletyCzarnoksieznikBossManager() {
        return przekletyCzarnoksieznikBossManager;
    }

    public PetyManager getPetyManager() {
        return petyManager;
    }

    public ZamekNieskonczonosciManager getZamekNieskonczonosciManager() {
        return zamekNieskonczonosciManager;
    }

    public DungeonsManager getDungeonsManager() {
        return dungeonsManager;
    }

    public ZmiankiManager getZmiankiManager() {
        return zmiankiManager;
    }

    public IceTowerManager getIceTowerManager() {
        return iceTowerManager;
    }

    /*public TestNPC getTestNPC() {
        return testNPC;
    }*/ // TO TWORZYSZ ZAWSZE BO INACEJ NIE ODWOLASZ SIE W BAZIE DANYCH DO TEGO
    public KociolkiManager getKociolkiManager() {
        return kociolkiManager;
    }

    public TopkiManager getTopkiManager() {
        return topkiManager;
    }

    public CraftingiManager getCraftingiManager() {
        return craftingiManager;
    }

    public SerwerWhiteListManager getSerwerWhiteListManager() {
        return serwerWhiteListManager;
    }

    public ArtefaktyZaLvlManager getArtefaktyZaLvlManager() {
        return artefaktyZaLvlManager;
    }

    public ShowcaseItemManager getShowcaseItemManager() {
        return showcaseItemManager;
    }

    public DisabledManager getDisabledManager() {
        return disabledManager;
    }

    public WyszkolenieManager getWyszkolenieManager() {
        return wyszkolenieManager;
    }

    public MedrzecNPC getMedrzecNPC() {
        return medrzecNPC;
    }

    public InvseeManager getInvseeManager() {
        return invseeManager;
    }

    public BossyManager getBossyManager() {
        return bossyManager;
    }

    public PustelnikNPC getPustelnikNPC() {
        return pustelnikNPC;
    }

    public MistrzYangNPC getMistrzYangNPC() {
        return mistrzYangNPC;
    }

    public CzarownicaNPC getCzarownicaNPC() {
        return czarownicaNPC;
    }

    public PrzedsionekManager getPrzedsionekManager() {
        return przedsionekManager;
    }

    public UstawieniaKontaManager getUstawieniaKontaManager() {
        return ustawieniaKontaManager;
    }

    public MistycznyKowalManager getMistycznyKowalManager() {
        return mistycznyKowalManager;
    }

    public RzemieslnikManager getRzemieslnikManager() { return rzemieslnikManager; }
    public GornikNPC getGornikNPC() {
        return gornikNPC;
    }

    public OreManager getOreManager() {
        return oreManager;
    }

    public KlasyManager getKlasyManager() {
        return klasyManager;
    }
    public BackupManager getBackupManager() {
        return backupManager;
    }
}
