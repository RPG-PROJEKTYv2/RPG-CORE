package rpg.rpgcore.commands.player.misje;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.npc.gornik.objects.GornikUser;
import rpg.rpgcore.npc.gornik.enums.GornikMissions;
import rpg.rpgcore.npc.kolekcjoner.objects.KolekcjonerUser;
import rpg.rpgcore.npc.kolekcjoner.enums.KolekcjonerMissions;
import rpg.rpgcore.npc.lowca.enums.LowcaMissions;
import rpg.rpgcore.npc.lowca.objects.LowcaUser;
import rpg.rpgcore.npc.magazynier.enums.MagazynierMissions;
import rpg.rpgcore.npc.magazynier.objects.MagazynierUser;
import rpg.rpgcore.npc.metinolog.objects.MetinologUser;
import rpg.rpgcore.npc.rybak.enums.RybakMissions;
import rpg.rpgcore.npc.rybak.objects.RybakUser;
import rpg.rpgcore.npc.wyslannik.enums.WyslannikMissionKillBoss;
import rpg.rpgcore.npc.wyslannik.enums.WyslannikMissionKillMob;
import rpg.rpgcore.npc.wyslannik.enums.WyslannikMissionOpen;
import rpg.rpgcore.npc.wyslannik.objects.WyslannikUser;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

public class MisjeCommand extends CommandAPI {
    private final RPGCORE rpgcore;

    public MisjeCommand(final RPGCORE rpgcore) {
        super("misje");
        this.setRankLevel(RankType.GRACZ);
        this.setRestrictedForPlayer(true);
        this.rpgcore = rpgcore;
    }

    @Deprecated
    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;
        if (rpgcore.getUserManager().find(player.getUniqueId()).getRankUser().getRankType().getPriority() >= RankType.HA.getPriority()) {
            if (args.length == 1) {
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null) {
                    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);
                    this.openMissionGUI(player, offlinePlayer.getName(), offlinePlayer.getUniqueId());
                    return;
                }
                this.openMissionGUI(player, target.getName(), target.getUniqueId());
                return;
            }
        }
        if (args.length > 0) {
            player.sendMessage(Utils.poprawneUzycie("misje"));
            return;
        }
        this.openMissionGUI(player, player.getName(), player.getUniqueId());
    }


    public void openMissionGUI(Player player, final String targetName, final UUID targetUUID) {
        final Inventory gui = Bukkit.createInventory(null, 54, Utils.format("&8Misje gracza &e" + targetName));
        final User user = rpgcore.getUserManager().find(targetUUID);
        if (user == null) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&cCos poszlo nie tak i nie udalo sie stworzyc GUI aktualnych misji gracza &6" + targetName + "&c!"));
            return;
        }
        // PIERWSZY RZAD
        gui.setItem(0, new ItemBuilder(Material.HOPPER).setName("&c&lWyslannik").toItemStack());

        final WyslannikUser wyslannikUser = rpgcore.getWyslannikNPC().find(targetUUID).getWyslannikUser();
        gui.setItem(9, rpgcore.getWyslannikNPC().getMobKillsItem(wyslannikUser, WyslannikMissionKillMob.getByMission(wyslannikUser.getKillMobsMission())));
        gui.setItem(18, rpgcore.getWyslannikNPC().getBossKillsItem(wyslannikUser, WyslannikMissionKillBoss.getByMission(wyslannikUser.getKillBossMission())));
        gui.setItem(27, rpgcore.getWyslannikNPC().getChestOpenItem(wyslannikUser, WyslannikMissionOpen.getByMission(wyslannikUser.getOpenChestMission())));

        gui.setItem(36, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName("").toItemStack());
        gui.setItem(45, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 10).setName("").toItemStack());

        // DRUGI RZAD
        gui.setItem(1, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName("").toItemStack());
        gui.setItem(10, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 10).setName("").toItemStack());

        gui.setItem(19, new ItemBuilder(Material.HOPPER).setName("&c&lLowca").toItemStack());
        final LowcaUser lowcaUser = rpgcore.getLowcaNPC().find(targetUUID).getLowcaUser();
        gui.setItem(28, rpgcore.getLowcaNPC().getMissionItem(LowcaMissions.getMission(lowcaUser.getMission()), lowcaUser));

        gui.setItem(37, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 10).setName("").toItemStack());
        gui.setItem(46, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName("").toItemStack());

        // TRZECI RZAD
        gui.setItem(2, new ItemBuilder(Material.HOPPER).setName("&c&lPrzyrodnik").toItemStack());
        gui.setItem(11, rpgcore.getPrzyrodnikNPC().getOddajItemyItem(rpgcore.getPrzyrodnikNPC().find(targetUUID).getUser()));

        gui.setItem(20, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 10).setName("").toItemStack());
        gui.setItem(29, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName("").toItemStack());

        gui.setItem(38, new ItemBuilder(Material.HOPPER).setName("&c&lKolekcjoner").toItemStack());
        gui.setItem(39, new ItemBuilder(Material.HOPPER).setName("&c&lKolekcjoner").toItemStack());
        gui.setItem(40, new ItemBuilder(Material.HOPPER).setName("&c&lKolekcjoner").toItemStack());
        gui.setItem(41, new ItemBuilder(Material.HOPPER).setName("&c&lKolekcjoner").toItemStack());
        gui.setItem(42, new ItemBuilder(Material.HOPPER).setName("&c&lKolekcjoner").toItemStack());

        final KolekcjonerUser kolekcjonerUser = rpgcore.getKolekcjonerNPC().find(targetUUID).getKolekcjonerUser();
        final KolekcjonerMissions mission = KolekcjonerMissions.getByNumber(kolekcjonerUser.getMission());

        gui.setItem(47, rpgcore.getKolekcjonerNPC().getIfItemIsGiven(mission, 0, kolekcjonerUser.getMissionProgress().get(0)));
        gui.setItem(48, rpgcore.getKolekcjonerNPC().getIfItemIsGiven(mission, 1, kolekcjonerUser.getMissionProgress().get(1)));
        gui.setItem(49, rpgcore.getKolekcjonerNPC().getIfItemIsGiven(mission, 2, kolekcjonerUser.getMissionProgress().get(2)));
        gui.setItem(50, rpgcore.getKolekcjonerNPC().getIfItemIsGiven(mission, 3, kolekcjonerUser.getMissionProgress().get(3)));
        gui.setItem(51, rpgcore.getKolekcjonerNPC().getIfItemIsGiven(mission, 4, kolekcjonerUser.getMissionProgress().get(4)));

        // CZWARTY RZAD
        gui.setItem(3, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 10).setName("").toItemStack());
        gui.setItem(12, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName("").toItemStack());

        gui.setItem(21, new ItemBuilder(Material.HOPPER).setName("&c&lLesnik").toItemStack());
        if (user.getLvl() >= 30) {
            gui.setItem(30, rpgcore.getLesnikNPC().getCurrentMissionItem(rpgcore.getLesnikNPC().find(targetUUID).getUser()));
        } else {
            gui.setItem(30, new ItemBuilder(Material.BARRIER).setName("&6&lOsiagnij 30 poziom, zeby odblokowac").toItemStack());
        }

        // PIATY RZAD
        gui.setItem(4, new ItemBuilder(Material.HOPPER).setName("&c&lDuszolog").toItemStack());
        gui.setItem(13, new ItemBuilder(Material.BOOK).setName("&4&lDostepne od aktualizacji 0.1").toItemStack());

        gui.setItem(22, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName("").toItemStack());
        gui.setItem(31, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 10).setName("").toItemStack());

        // SZOSTY RZAD
        gui.setItem(5, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName("").toItemStack());
        gui.setItem(14, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 10).setName("").toItemStack());

        gui.setItem(23, new ItemBuilder(Material.HOPPER).setName("&c&lGornik").toItemStack());
        if (user.getLvl() < 60) {
            gui.setItem(32, new ItemBuilder(Material.BARRIER).setName("&6&lOsiagnij 60 poziom, zeby odblokowac").toItemStack());
        } else {
            final GornikUser gornikUser = rpgcore.getGornikNPC().find(targetUUID).getGornikUser();
            gui.setItem(32, new ItemBuilder(Material.BOOK).setName("&c&lMisja " + gornikUser.getMission()).setLoreCrafting(GornikMissions.getMission(gornikUser.getMission()).getLore(), Arrays.asList(" ", "&7Postep: &6" + gornikUser.getProgress() + "&7/&6" + GornikMissions.getMission(gornikUser.getMission()).getReqAmount())).toItemStack().clone());
        }

        // SIODY RZAD
        gui.setItem(6, new ItemBuilder(Material.HOPPER).setName("&c&lMetinolog").toItemStack());
        final MetinologUser metinologUser = rpgcore.getMetinologNPC().find(targetUUID).getMetinologUser();
        gui.setItem(15, rpgcore.getMetinologNPC().getGiveMissionItem(metinologUser));
        gui.setItem(24, rpgcore.getMetinologNPC().getKillMissionItem(metinologUser));

        gui.setItem(33, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName("").toItemStack());

        // OSMY RZAD
        gui.setItem(7, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 10).setName("").toItemStack());
        gui.setItem(16, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName("").toItemStack());

        gui.setItem(25, new ItemBuilder(Material.HOPPER).setName("&c&lRybak").toItemStack());
        if (user.getLvl() < 30) {
            gui.setItem(34, new ItemBuilder(Material.BARRIER).setName("&6&lOsiagnij 30 poziom, zeby odblokowac").toItemStack());
        } else {
            final RybakUser rybakUser = rpgcore.getRybakNPC().find(targetUUID).getRybakUser();
            final RybakMissions rybakMissions = RybakMissions.getMission(rybakUser.getMission());
            assert rybakMissions != null;
            gui.setItem(34, new ItemBuilder(rybakMissions.getMissionItem().clone()).setName("&c&lMisja #" + rybakUser.getMission()).setLoreCrafting(rybakMissions.getMissionItem().getItemMeta().getLore(),
                    Arrays.asList(" ", "&7Postep: &6" + rybakUser.getProgress() + "&7/&6" + rybakMissions.getReqAmount())).toItemStack().clone());
        }

        gui.setItem(43, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 10).setName("").toItemStack());
        gui.setItem(52, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName("").toItemStack());

        // DZIEWIETY RZAD
        gui.setItem(8, new ItemBuilder(Material.HOPPER).setName("&c&lMagazynier").toItemStack());
        final MagazynierUser magazynierUser = rpgcore.getMagazynierNPC().find(targetUUID);
        if (!magazynierUser.getMissions().isGenerated()) {
            rpgcore.getMagazynierNPC().generateNewMissionSet(magazynierUser);
        }
        if (System.currentTimeMillis() >= magazynierUser.getResetTime()) {
            rpgcore.getMagazynierNPC().generateNewMissionSet(magazynierUser);
        }
        final MagazynierMissions m1 = MagazynierMissions.getMissionById(magazynierUser.getMissions().getMission1());
        final MagazynierMissions m2 = MagazynierMissions.getMissionById(magazynierUser.getMissions().getMission2());
        final MagazynierMissions m3 = MagazynierMissions.getMissionById(magazynierUser.getMissions().getMission3());
        final MagazynierMissions m4 = MagazynierMissions.getMissionById(magazynierUser.getMissions().getMission4());
        final MagazynierMissions m5 = MagazynierMissions.getMissionById(magazynierUser.getMissions().getMission5());

        gui.setItem(17, rpgcore.getMagazynierNPC().isMissionDone(magazynierUser, 1, m1));
        gui.setItem(26, rpgcore.getMagazynierNPC().isMissionDone(magazynierUser, 2, m2));
        gui.setItem(35, rpgcore.getMagazynierNPC().isMissionDone(magazynierUser, 3, m3));
        gui.setItem(44, rpgcore.getMagazynierNPC().isMissionDone(magazynierUser, 4, m4));
        gui.setItem(53, rpgcore.getMagazynierNPC().isMissionDone(magazynierUser, 5, m5));
        player.openInventory(gui);
    }
}
