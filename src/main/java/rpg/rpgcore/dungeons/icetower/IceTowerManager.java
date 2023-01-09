package rpg.rpgcore.dungeons.icetower;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.npc.magazynier.objects.MagazynierUser;
import rpg.rpgcore.utils.BossBarUtil;
import rpg.rpgcore.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


@Getter
@Setter
public class IceTowerManager {
    private static final RPGCORE rpgcore = RPGCORE.getInstance();

    private static final List<Location> kamienLocations = Arrays.asList(
            new Location(Bukkit.getWorld("50-60map"), -39, 71, 52),

            new Location(Bukkit.getWorld("50-60map"), -40, 72, 52),
            new Location(Bukkit.getWorld("50-60map"), -39, 72, 52),
            new Location(Bukkit.getWorld("50-60map"), -38, 72, 52),
            new Location(Bukkit.getWorld("50-60map"), -39, 72, 53),
            new Location(Bukkit.getWorld("50-60map"), -39, 72, 51),

            new Location(Bukkit.getWorld("50-60map"), -41, 73, 52),
            new Location(Bukkit.getWorld("50-60map"), -40, 73, 52),
            new Location(Bukkit.getWorld("50-60map"), -39, 73, 52),
            new Location(Bukkit.getWorld("50-60map"), -38, 73, 52),
            new Location(Bukkit.getWorld("50-60map"), -37, 73, 52),
            new Location(Bukkit.getWorld("50-60map"), -38, 73, 51),
            new Location(Bukkit.getWorld("50-60map"), -38, 73, 53),
            new Location(Bukkit.getWorld("50-60map"), -39, 73, 50),
            new Location(Bukkit.getWorld("50-60map"), -39, 73, 51),
            new Location(Bukkit.getWorld("50-60map"), -39, 73, 53),
            new Location(Bukkit.getWorld("50-60map"), -39, 73, 54),
            new Location(Bukkit.getWorld("50-60map"), -40, 73, 51),
            new Location(Bukkit.getWorld("50-60map"), -40, 73, 53),

            new Location(Bukkit.getWorld("50-60map"), -41, 74, 52),
            new Location(Bukkit.getWorld("50-60map"), -40, 74, 52),
            new Location(Bukkit.getWorld("50-60map"), -39, 74, 52),
            new Location(Bukkit.getWorld("50-60map"), -38, 74, 52),
            new Location(Bukkit.getWorld("50-60map"), -37, 74, 52),
            new Location(Bukkit.getWorld("50-60map"), -38, 74, 51),
            new Location(Bukkit.getWorld("50-60map"), -38, 74, 53),
            new Location(Bukkit.getWorld("50-60map"), -39, 74, 50),
            new Location(Bukkit.getWorld("50-60map"), -39, 74, 51),
            new Location(Bukkit.getWorld("50-60map"), -39, 74, 53),
            new Location(Bukkit.getWorld("50-60map"), -39, 74, 54),
            new Location(Bukkit.getWorld("50-60map"), -40, 74, 51),
            new Location(Bukkit.getWorld("50-60map"), -40, 74, 53),

            new Location(Bukkit.getWorld("50-60map"), -41, 75, 52),
            new Location(Bukkit.getWorld("50-60map"), -40, 75, 52),
            new Location(Bukkit.getWorld("50-60map"), -39, 75, 52),
            new Location(Bukkit.getWorld("50-60map"), -38, 75, 52),
            new Location(Bukkit.getWorld("50-60map"), -37, 75, 52),
            new Location(Bukkit.getWorld("50-60map"), -38, 75, 51),
            new Location(Bukkit.getWorld("50-60map"), -38, 75, 53),
            new Location(Bukkit.getWorld("50-60map"), -39, 75, 50),
            new Location(Bukkit.getWorld("50-60map"), -39, 75, 51),
            new Location(Bukkit.getWorld("50-60map"), -39, 75, 53),
            new Location(Bukkit.getWorld("50-60map"), -39, 75, 54),
            new Location(Bukkit.getWorld("50-60map"), -40, 75, 51),
            new Location(Bukkit.getWorld("50-60map"), -40, 75, 53),

            new Location(Bukkit.getWorld("50-60map"), -39, 77, 52),

            new Location(Bukkit.getWorld("50-60map"), -40, 76, 52),
            new Location(Bukkit.getWorld("50-60map"), -39, 76, 52),
            new Location(Bukkit.getWorld("50-60map"), -38, 76, 52),
            new Location(Bukkit.getWorld("50-60map"), -39, 76, 53),
            new Location(Bukkit.getWorld("50-60map"), -39, 76, 51)
    );

    private int hp = 200;
    private int mobsAmount = 0;
    private int time = -1;
    private int maxTime = -1;
    private int antyAfk = -1;

    public static final List<UUID> damagers = new ArrayList<>();

    public static void resetIceTower(final ResetType resetType) {
        if (resetType == ResetType.NORMAL) {
            if (Bukkit.getWorld("demontower").getPlayers().size() == 0) {
                damagers.clear();
                if (rpgcore.getIceTowerManager().getHp() < 205) return;
                if (rpgcore.getIceTowerManager().getMaxTime() == 99999) return;
                rpgcore.getIceTowerManager().setHp(200);
                rpgcore.getIceTowerManager().setMobsAmount(0);
                rpgcore.getIceTowerManager().setTime(-1);
                rpgcore.getIceTowerManager().setMaxTime(-1);
                rpgcore.getIceTowerManager().setAntyAfk(-1);
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc sel 77");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc despawn");
                Bukkit.getWorld("demontower").getBlockAt(16, 9, 97).setType(Material.AIR);
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mm m kill DT-MOB1");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mm m kill DT-MOB2");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mm m kill DT-MOB3");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mm m kill DT-BOSS");
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "metin despawn 20000");
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "metin despawn 20001");
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "metin despawn 20002");
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "metin despawn 20003");
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "metin despawn 20004");
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "metin despawn 20005");
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "metin despawn 20006");
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "metin despawn 20007");
                despawnKamien();
                spawnKamien();
                rpgcore.getServer().broadcastMessage(Utils.format("&b&lKamien Lodowej Wiezy zostal zresetowany!"));
                return;
            }
        }
        if (resetType == ResetType.BYPASS) {
            damagers.clear();
            rpgcore.getIceTowerManager().setHp(200);
            rpgcore.getIceTowerManager().setMobsAmount(0);
            rpgcore.getIceTowerManager().setTime(-1);
            rpgcore.getIceTowerManager().setMaxTime(-1);
            rpgcore.getIceTowerManager().setAntyAfk(-1);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc sel 77");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc despawn");
            Bukkit.getWorld("demontower").getBlockAt(16, 9, 97).setType(Material.AIR);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mm m kill DT-MOB1");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mm m kill DT-MOB2");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mm m kill DT-MOB3");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mm m kill DT-BOSS");
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "metin despawn 20000");
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "metin despawn 20001");
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "metin despawn 20002");
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "metin despawn 20003");
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "metin despawn 20004");
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "metin despawn 20005");
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "metin despawn 20006");
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "metin despawn 20007");
            despawnKamien();
            spawnKamien();
            if (Bukkit.getWorld("demontower").getPlayers() != null) {
                for (Player player : Bukkit.getWorld("demontower").getPlayers()) {
                    BossBarUtil.removeBar(player);
                    player.closeInventory();
                    player.teleport(rpgcore.getSpawnManager().getSpawn());
                }
            }
            rpgcore.getServer().broadcastMessage(Utils.format("&b&lKamien Lodowej Wiezy zostal zresetowany!"));
        }
    }

    public static List<Location> getKamienLocations() {
        return kamienLocations;
    }

    public static void despawnKamien() {
        for (Location loc : kamienLocations) {
            loc.getBlock().setType(Material.AIR);
        }
    }


    public static void spawnKamien() {
        for (Location loc : kamienLocations) {
            loc.getBlock().setType(Material.LAPIS_BLOCK);
        }
    }


    public static void startIceTower() {
        rpgcore.getIceTowerManager().setMobsAmount(0);
        rpgcore.getIceTowerManager().setHp(1000);
        despawnKamien();
        rpgcore.getIceTowerManager().setMaxTime(99999);
        Bukkit.getServer().getScheduler().runTaskLater(rpgcore, () -> {
            if (Bukkit.getWorld("50-60map").getPlayers() != null) {
                for (Player player : Bukkit.getWorld("50-60map").getPlayers()) {
                    if (!damagers.contains(player.getUniqueId())) continue;
                    BossBarUtil.removeBar(player);
                    player.teleport(new Location(Bukkit.getWorld("demontower"), 16.5, 10, 42.5, 0, 0));
                    player.sendMessage(Utils.format("&1>>-------------&bIce Tower&1-------------<<" + "\n"
                            + "&fZnadujesz sie na &b1 &fetapie lodowej wiezy..." + "\n"
                            + "&fNa tym etapie musisz zabic &b1 &ffale &bNazwa Moba!" + "\n"
                            + "&1>>-------------&bIce Tower&1-------------<<"));
                }
            }
            rpgcore.getIceTowerManager().setTime(5);
            rpgcore.getIceTowerManager().setMaxTime(5);
            Bukkit.getServer().getScheduler().runTaskLater(rpgcore, () -> {
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mm mobs spawn DT-MOB1 15 demontower,-0.5,11,56.5");
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mm mobs spawn DT-MOB1 15 demontower,49.5,11,97.5");
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mm mobs spawn DT-MOB1 15 demontower,16.5,9,126.5");
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mm mobs spawn DT-MOB1 15 demontower,-12.5,11,111.5");
            }, 100L);
        }, 200L);
    }


    public static void startIceTowerEtap2() {
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mm m kill DT-MOB1");
        if (Bukkit.getWorld("demontower").getPlayers() != null) {
            for (Player player : Bukkit.getWorld("demontower").getPlayers()) {
                BossBarUtil.removeBar(player);
                player.sendMessage(Utils.format("&1>>-------------&bIce Tower&1-------------<<" + "\n"
                        + "&bFala mobow zostala pomyslnie pokonana!" + "\n"
                        + "&1>>-------------&bIce Tower&1-------------<<"));
            }
        }
        rpgcore.getIceTowerManager().setTime(125);
        rpgcore.getIceTowerManager().setMaxTime(125);
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mm m kill DT-MOB1");
        Bukkit.getServer().getScheduler().runTaskLater(rpgcore, () -> {
            rpgcore.getIceTowerManager().setMobsAmount(0);
            if (Bukkit.getWorld("demontower").getPlayers() != null) {
                for (Player player : Bukkit.getWorld("demontower").getPlayers()) {
                    player.teleport(new Location(Bukkit.getWorld("demontower"), 16.5, 10, 42.5, 0, 0));
                    player.sendMessage(Utils.format("&1>>-------------&bIce Tower&1-------------<<" + "\n"
                            + "&fZnadujesz sie na &b2 &fetapie lodowej wiezy..." + "\n"
                            + "&fNa tym etapie musisz zabic &b8 &fkamieni metin!" + "\n"
                            + "&1>>-------------&bIce Tower&1-------------<<"));
                }
            }
            Bukkit.getServer().getScheduler().runTaskLater(rpgcore, () -> {
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "metin spawn 20000");
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "metin spawn 20001");
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "metin spawn 20002");
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "metin spawn 20003");
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "metin spawn 20004");
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "metin spawn 20005");
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "metin spawn 20006");
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "metin spawn 20007");
            }, 100L);
        }, 1400L);
    }

    public static void startIceTowerEtap3() {
        if (Bukkit.getWorld("demontower").getPlayers() != null) {
            for (Player player : Bukkit.getWorld("demontower").getPlayers()) {
                BossBarUtil.removeBar(player);
                player.sendMessage(Utils.format("&1>>-------------&bIce Tower&1-------------<<" + "\n"
                        + "&bFala mobow zostala pomyslnie pokonana!" + "\n"
                        + "&1>>-------------&bIce Tower&1-------------<<"));
            }
        }
        rpgcore.getIceTowerManager().setTime(125);
        rpgcore.getIceTowerManager().setMaxTime(125);
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mm m kill DT-MOB3");
        Bukkit.getServer().getScheduler().runTaskLater(rpgcore, () -> {
            rpgcore.getIceTowerManager().setMobsAmount(0);
            if (Bukkit.getWorld("demontower").getPlayers() != null) {
                for (Player player : Bukkit.getWorld("demontower").getPlayers()) {
                    player.teleport(new Location(Bukkit.getWorld("demontower"), 16.5, 10, 42.5, 0, 0));
                    player.sendMessage(Utils.format("&1>>-------------&bIce Tower&1-------------<<" + "\n"
                            + "&fZnadujesz sie na &b3 &fetapie lodowej wiezy..." + "\n"
                            + "&fNa tym etapie musisz zabic &b1 &ffale...!" + "\n"
                            + "&1>>-------------&bIce Tower&1-------------<<"));
                }
            }
            Bukkit.getServer().getScheduler().runTaskLater(rpgcore, () -> {
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mm mobs spawn DT-MOB2 20 demontower,-0.5,11,56.5");
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mm mobs spawn DT-MOB2 20 demontower,49.5,11,97.5");
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mm mobs spawn DT-MOB2 20 demontower,16.5,9,126.5");
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mm mobs spawn DT-MOB2 20 demontower,-12.5,11,111.5");
            }, 100L);
        }, 1400L);
    }

    public static void startIceTowerEtapBOSS() {
        if (Bukkit.getWorld("demontower").getPlayers() != null) {
            for (Player player : Bukkit.getWorld("demontower").getPlayers()) {
                BossBarUtil.removeBar(player);
                player.sendMessage(Utils.format("&1>>-------------&bIce Tower&1-------------<<" + "\n"
                        + "&bFala mobow zostala pomyslnie pokonana!" + "\n"
                        + "&1>>-------------&bIce Tower&1-------------<<"));
            }
        }
        rpgcore.getIceTowerManager().setTime(125);
        rpgcore.getIceTowerManager().setMaxTime(125);
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mm m kill DT-MOB2");
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mm m kill DT-MOB3");
        Bukkit.getServer().getScheduler().runTaskLater(rpgcore, () -> {
            rpgcore.getIceTowerManager().setMobsAmount(0);
            if (Bukkit.getWorld("demontower").getPlayers() != null) {
                for (Player player : Bukkit.getWorld("demontower").getPlayers()) {
                    player.teleport(new Location(Bukkit.getWorld("demontower"), 16.5, 10, 42.5, 0, 0));
                    player.sendMessage(Utils.format("&1>>-------------&bIce Tower&1-------------<<" + "\n"
                            + "&fZnadujesz sie na &b3 &fetapie lodowej wiezy..." + "\n"
                            + "&fNa tym etapie musisz zabic &8&l[&4&lBOSS&8&l] &b&lMrozny Wladca!" + "\n"
                            + "&1>>-------------&bIce Tower&1-------------<<"));
                }
            }
            Bukkit.getServer().getScheduler().runTaskLater(rpgcore, () -> Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mm mobs spawn DT-BOSS 1 demontower,16.5,10,141.5"), 100L);
        }, 1400L);
    }

    public static void actionBar(final int reqMobAmount, final String barText) {
        for (Player player : Bukkit.getWorld("demontower").getPlayers()) {
            /*if (!BossBarUtil.getPlayers().contains(player.getName())) {
                BossBarUtil.setBar(player.getPlayer(), Utils.format(barText), reqMobAmount - rpgcore.getIceTowerManager().getMobsAmount());
            }
            BossBarUtil.updateBar(player.getPlayer(), Utils.format(barText), reqMobAmount - rpgcore.getIceTowerManager().getMobsAmount());*/
            rpgcore.getNmsManager().sendActionBar(player, "&b&lPostep Lodowej Wiezy: &f" + rpgcore.getIceTowerManager().getMobsAmount());
        }
    }

    public static void teleportKowal() {
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mm m kill DT-MOB1");
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mm m kill DT-MOB2");
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mm m kill DT-MOB3");
        rpgcore.getIceTowerManager().setTime(-1);
        rpgcore.getIceTowerManager().setMaxTime(-1);
        if (Bukkit.getWorld("demontower").getPlayers() != null) {
            rpgcore.getIceTowerManager().setAntyAfk(120);
            for (Player player : Bukkit.getWorld("demontower").getPlayers()) {
                BossBarUtil.removeBar(player);
                player.teleport(new Location(Bukkit.getWorld("demontower"), 16.5, 10, 42.5, 0, 0));
                player.sendMessage(Utils.format("&1>>-------------&bIce Tower&1-------------<<" + "\n"
                        + "&fPoomyslnie Ukonczyliscie Lodowa Wieze. Gratulacje" + "\n"
                        + "&fTeraz mozecie ulepszyc swoje przedmioty" + "\n"
                        + "&1>>-------------&bIce Tower&1-------------<<"));
                rpgcore.getKowalNPC().resetUpgradeList();
                final MagazynierUser user = rpgcore.getMagazynierNPC().find(player.getUniqueId());
                if (user.getMissions().getSelectedMission() == 10) {
                    user.getMissions().setProgress(user.getMissions().getProgress() + 1);
                    rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataMagazynier(user.getUuid(), user));
                }
            }
            Block block = Bukkit.getWorld("demontower").getBlockAt(16, 9, 97);
            block.setType(Material.ANVIL);
            block.setData((byte) 1);

            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc sel 77");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc spawn");
        }
    }

}
