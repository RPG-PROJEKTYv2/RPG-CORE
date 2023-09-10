package rpg.rpgcore.dungeons.maps.icetower;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.Getter;
import lombok.Setter;
import me.filoghost.holographicdisplays.api.hologram.Hologram;
import me.filoghost.holographicdisplays.api.hologram.line.TextHologramLine;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.dungeons.DungeonStatus;
import rpg.rpgcore.metiny.MetinyHelper;
import rpg.rpgcore.npc.magazynier.objects.MagazynierUser;
import rpg.rpgcore.npc.mrozny_stroz.objects.MroznyStrozUser;
import rpg.rpgcore.utils.ChanceHelper;
import rpg.rpgcore.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Getter
@Setter
public class IceTowerManager {
    private final RPGCORE rpgcore;
    @Getter
    private final Cache<String, Long> damaged = CacheBuilder.newBuilder().expireAfterWrite(5, TimeUnit.SECONDS).build();
    @Getter
    private final List<Integer> taskIds = new ArrayList<>();

    public IceTowerManager(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
        RPGCORE.getHolographicDisplaysAPI().getHolograms().forEach(hologram -> {
            if (hologram.getPosition().toLocation().equals(new Location(map, -131.5, 98, 213.5))) {
                this.holo1 = hologram;
            } else if (hologram.getPosition().toLocation().equals(new Location(map, -123.5, 98, 205.5))) {
                this.holo2 = hologram;
            } else if (hologram.getPosition().toLocation().equals(new Location(map, -115.5, 98, 213.5))) {
                this.holo3 = hologram;
            } else if (hologram.getPosition().toLocation().equals(new Location(map, -123.5, 98, 221.5))) {
                this.holo4 = hologram;
            }
        });
        this.resetDungeon();
    }

    private Hologram holo1;
    private Hologram holo2;
    private Hologram holo3;
    private Hologram holo4;

    @Getter
    private final World map = Bukkit.getWorld("50-60map");
    @Getter
    private final World dungeonWorld = Bukkit.getWorld("DemonTower");
    @Getter
    @Setter
    private DungeonStatus status = DungeonStatus.ENDED;
    private final List<String> baseHoloDisplay = Arrays.asList(
            "&b&lIce Tower",
            "&7Status: &aWolne",
            "&7Przechodzi: &cNikt",
            "&7Etap: &aWolne",
            "&7HP: &a200&7/&a200"
    );


    @Getter
    private final List<Location> kamienLocations = Arrays.asList(
            new Location(Bukkit.getWorld("50-60map"), -124, 97, 213),

            new Location(Bukkit.getWorld("50-60map"), -124, 98, 214),
            new Location(Bukkit.getWorld("50-60map"), -125, 98, 213),
            new Location(Bukkit.getWorld("50-60map"), -124, 98, 213),
            new Location(Bukkit.getWorld("50-60map"), -123, 98, 213),
            new Location(Bukkit.getWorld("50-60map"), -124, 98, 212),

            new Location(Bukkit.getWorld("50-60map"), -124, 99, 214),
            new Location(Bukkit.getWorld("50-60map"), -125, 99, 213),
            new Location(Bukkit.getWorld("50-60map"), -124, 99, 213),
            new Location(Bukkit.getWorld("50-60map"), -123, 99, 213),
            new Location(Bukkit.getWorld("50-60map"), -124, 99, 212),

            new Location(Bukkit.getWorld("50-60map"), -124, 100, 214),
            new Location(Bukkit.getWorld("50-60map"), -125, 100, 213),
            new Location(Bukkit.getWorld("50-60map"), -124, 100, 213),
            new Location(Bukkit.getWorld("50-60map"), -123, 100, 213),
            new Location(Bukkit.getWorld("50-60map"), -124, 100, 212),

            new Location(Bukkit.getWorld("50-60map"), -124, 101, 213)
    );

    private int hp = 200;
    private int latestHp = 200;
    private int count = 0;
    private int time = 0;
    private int antyAfkTime = 0;
    private int antyAfkMaxTime = 0;


    public void resetDungeon() {
        this.status = DungeonStatus.RESETTING;
        for (Integer i : this.taskIds) Bukkit.getScheduler().cancelTask(i);
        this.taskIds.clear();
        this.resetHolograms();
        this.lowerGate();
        this.spawnKamien();
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc sel 77");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc despawn");
        for (Entity e : this.dungeonWorld.getEntities()) {
            if (e instanceof Player) continue;
            e.remove();
        }
        this.dungeonWorld.getEntities().clear();
        this.hp = 200;
        this.latestHp = 200;
        this.count = 0;
        this.time = 0;
        this.antyAfkTime = 0;
        this.antyAfkMaxTime = 0;
        Bukkit.getServer().broadcastMessage(Utils.format("&b&oKamien Lodowej Wiezy zostal zresetowany!"));
        this.status = DungeonStatus.ENDED;
        rpgcore.getKowalNPC().resetUpgradeList();
    }

    private void spawnPlayers() {
        for (final Player player : this.getDungeonWorld().getPlayers()) {
            player.teleport(rpgcore.getSpawnManager().getSpawn());
        }
    }

    public void resetHolograms() {
        this.holo1.getLines().clear();
        this.holo2.getLines().clear();
        this.holo3.getLines().clear();
        this.holo4.getLines().clear();
        for (String s : baseHoloDisplay) {
            this.holo1.getLines().appendText(Utils.format(s));
            this.holo2.getLines().appendText(Utils.format(s));
            this.holo3.getLines().appendText(Utils.format(s));
            this.holo4.getLines().appendText(Utils.format(s));
        }
    }

    public void incrementCount() {
        this.count++;
    }

    public void spawnKamien() {
        for (Location location : kamienLocations) {
            location.getBlock().setType(Material.LAPIS_BLOCK);
        }
    }

    public void despawnKamien() {
        for (Location location : kamienLocations) {
            location.getBlock().setType(Material.AIR);
        }
    }

    public void updatePlayerCount() {
        final int playerCount = this.dungeonWorld.getPlayers().size();
        String sufix = " graczy";
        if (playerCount == 1) sufix = " gracz";
        ((TextHologramLine) this.holo1.getLines().get(2)).setText(Utils.format("&7Przechodzi: &c" + playerCount + sufix));
        ((TextHologramLine) this.holo2.getLines().get(2)).setText(Utils.format("&7Przechodzi: &c" + playerCount + sufix));
        ((TextHologramLine) this.holo3.getLines().get(2)).setText(Utils.format("&7Przechodzi: &c" + playerCount + sufix));
        ((TextHologramLine) this.holo4.getLines().get(2)).setText(Utils.format("&7Przechodzi: &c" + playerCount + sufix));
    }

    private void updateStatus() {
        String status = "&aWolne";
        if (this.status != DungeonStatus.ENDED) status = "&cZajete";
        ((TextHologramLine) this.holo1.getLines().get(1)).setText(Utils.format("&7Status: " + status));
        ((TextHologramLine) this.holo2.getLines().get(1)).setText(Utils.format("&7Status: " + status));
        ((TextHologramLine) this.holo3.getLines().get(1)).setText(Utils.format("&7Status: " + status));
        ((TextHologramLine) this.holo4.getLines().get(1)).setText(Utils.format("&7Status: " + status));
    }

    private void updateEtap() {
        if (this.status == DungeonStatus.ENDED) return;
        ((TextHologramLine) this.holo1.getLines().get(3)).setText(Utils.format("&7Etap: &e" + status.getName()));
        ((TextHologramLine) this.holo2.getLines().get(3)).setText(Utils.format("&7Etap: &e" + status.getName()));
        ((TextHologramLine) this.holo3.getLines().get(3)).setText(Utils.format("&7Etap: &e" + status.getName()));
        ((TextHologramLine) this.holo4.getLines().get(3)).setText(Utils.format("&7Etap: &e" + status.getName()));
    }

    private void updateHealth() {
        final String prefix = this.getHealthPrefix();
        ((TextHologramLine) this.holo1.getLines().get(4)).setText(Utils.format("&7HP: " + prefix + this.hp + "&7/&a200"));
        ((TextHologramLine) this.holo2.getLines().get(4)).setText(Utils.format("&7HP: " + prefix + this.hp + "&7/&a200"));
        ((TextHologramLine) this.holo3.getLines().get(4)).setText(Utils.format("&7HP: " + prefix + this.hp + "&7/&a200"));
        ((TextHologramLine) this.holo4.getLines().get(4)).setText(Utils.format("&7HP: " + prefix + this.hp + "&7/&a200"));
    }

    public void updateTime() {
        if (!(this.status == DungeonStatus.ETAP_1 || this.status == DungeonStatus.WAITING || this.status == DungeonStatus.ETAP_2 ||
                this.status == DungeonStatus.ETAP_3 || this.status == DungeonStatus.ETAP_4 || this.status == DungeonStatus.BOSS)) return;
        this.time++;
        this.antyAfkTime++;
        if (this.antyAfkTime >= this.antyAfkMaxTime) {
            for (Player player : this.dungeonWorld.getPlayers()) {
                player.sendMessage(Utils.format("&3&m================&b&l Ice Tower &3&m================"));
                player.sendMessage(Utils.format("&cPrzewidziany czas na te Etap minal!"));
                player.sendMessage(Utils.format("&cDungeon zostal zresetowany!"));
                player.sendMessage(Utils.format("&3&m================&b&l Ice Tower &3&m================"));
            }
            this.spawnPlayers();
            this.resetDungeon();
            return;
        }
        ((TextHologramLine) this.holo1.getLines().get(4)).setText(Utils.format("&7Czas Trwania: &c" + Utils.durationToString(this.time * 1000L, true)));
        ((TextHologramLine) this.holo2.getLines().get(4)).setText(Utils.format("&7Czas Trwania: &c" + Utils.durationToString(this.time * 1000L, true)));
        ((TextHologramLine) this.holo3.getLines().get(4)).setText(Utils.format("&7Czas Trwania: &c" + Utils.durationToString(this.time * 1000L, true)));
        ((TextHologramLine) this.holo4.getLines().get(4)).setText(Utils.format("&7Czas Trwania: &c" + Utils.durationToString(this.time * 1000L, true)));
    }

    private String getHealthPrefix() {
        if (this.hp >= 150) {
            return "&a";
        } else if (this.hp >= 100) {
            return "&e";
        } else if (this.hp >= 50) {
            return "&c";
        } else {
            return "&4";
        }
    }

    public void damageKamien(final Player player) {
        if (this.status != DungeonStatus.STARTED) this.status = DungeonStatus.STARTED;
        this.damaged.put("kamien", System.currentTimeMillis() + 5_000L);
        this.hp--;
        this.updateHealth();
        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getNmsManager().sendActionBar(player, "&bKrysztal Lodowej Wiezy &c( " + this.hp + "HP &c)"));
        if (this.hp == 0) this.startDungeon(player.getName());
    }

    public void startDungeon(final String playerName) {
        this.despawnKamien();
        this.updateStatus();
        final ArmorStand stand = (ArmorStand) this.map.spawnEntity(new Location(map, -124.5, 98, 213.5), EntityType.ARMOR_STAND);
        stand.setGravity(false);
        stand.setVisible(false);
        stand.setCustomNameVisible(false);
        Bukkit.getServer().broadcastMessage(Utils.format("&b&lIce Tower &3>> &fKamien zostal zniszczony przez &b" + playerName + "&f!"));
        rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> {
            stand.getNearbyEntities(7.5, 10, 7.5).forEach(entity -> {
                if (entity instanceof Player) {
                    entity.teleport(this.spawnLoc);
                }
            });
            stand.remove();
            if (dungeonWorld.getPlayers().isEmpty()) {
                this.resetDungeon();
                return;
            }
            this.startPhase1();
        }, 80L);
    }

    private final Location spawnLoc = new Location(dungeonWorld, -4.5, 66, 29.5);
    private final Location bossSpawnLoc = new Location(dungeonWorld, -4.5, 67, 122.5);
    private final List<Location> mobSpawnLocations = Arrays.asList(
            new Location(dungeonWorld, -14.5, 67, 39.5),
            new Location(dungeonWorld, 32.5, 67, 80.5),
            new Location(dungeonWorld, -29.5, 67, 97.5)
    );
    public void startPhase1() {
        this.status = DungeonStatus.ETAP_1;
        this.antyAfkTime = 0;
        this.antyAfkMaxTime = 240;
        this.updateEtap();
        this.spawnMobs(20, IceTowerMobType.M1);
        this.sendMessage(1);
    }

    public void startPrePhase() {
        this.setAntyAfkTime(0);
        this.count = 0;
        this.status = DungeonStatus.WAITING;
        this.updateEtap();
        for (Player player : dungeonWorld.getPlayers()) {
            player.sendMessage(Utils.format("&3&m================&b&l Ice Tower &3&m================"));
            player.sendMessage(Utils.format("&fUkonczono aktualny etap!"));
            player.sendMessage(Utils.format("&fNastepny rozpocznie sie za &c15 &fsekund!"));
            player.sendMessage(Utils.format("&3&m================&b&l Ice Tower &3&m================"));
        }
    }

    public void startPhase2() {
        this.status = DungeonStatus.ETAP_2;
        this.antyAfkTime = 0;
        this.antyAfkMaxTime = 240;
        this.backPlayers();
        this.updateEtap();
        this.spawnMobs(20, IceTowerMobType.M2);
        this.sendMessage(2);
    }

    private final List<Integer> metinIds = Arrays.asList(20_000, 20_001, 20_002, 20_003, 20_004, 20_005, 20_006, 20_007);

    public void startPhase3() {
        this.status = DungeonStatus.ETAP_3;
        this.antyAfkTime = 0;
        if (this.dungeonWorld.getPlayers().size() == 1) this.antyAfkMaxTime = 480;
        else this.antyAfkMaxTime = 240;
        this.backPlayers();
        this.updateEtap();
        this.spawnMetins();
        this.sendMessage(3);
    }

    private void spawnMetins() {
        for (Integer i : this.metinIds) {
            MetinyHelper.spawnMetinByPass(i);
        }
    }

    public void startPhase4() {
        this.status = DungeonStatus.ETAP_4;
        this.backPlayers();
        this.updateEtap();
        this.antyAfkTime = 0;
        this.antyAfkMaxTime = 240;
        this.spawnMobs(25, IceTowerMobType.M3);
        this.sendMessage(4);
    }

    public void startPhaseBOSS() {
        this.status = DungeonStatus.BOSS;
        this.antyAfkTime = 0;
        this.antyAfkMaxTime = 120;
        this.backPlayers();
        this.updateEtap();
        this.spawnMobs(1, IceTowerMobType.BOSS);
        this.sendMessage(5);
    }

    private void spawnMobs(final int amount, final IceTowerMobType mobType) {
        if (mobType == IceTowerMobType.BOSS) {
            RPGCORE.getMythicMobs().getMobManager().spawnMob(mobType.getMobName(), this.bossSpawnLoc);
            return;
        }
        for (int i = 0; i < amount; i++) {
            final double x = ChanceHelper.getRandDouble(-0.2, 0.2);
            final double z = ChanceHelper.getRandDouble(-0.2, 0.2);
            for (final Location loc : this.mobSpawnLocations) {
                RPGCORE.getMythicMobs().getMobManager().spawnMob(mobType.getMobName(), loc.clone().add(x, 0, z));
            }
        }
    }

    private void backPlayers() {
        for (Player p : dungeonWorld.getPlayers()) {
            p.teleport(this.spawnLoc);
        }
    }

    private void sendMessage(final int phase) {
        String message = "";
        switch (phase) {
            case 1:
                message = "&b&lEtap 1 &3>> &fPokonaj wszystkie potwory!";
                break;
            case 2:
                message = "&b&lEtap 2 &3>> &fPokonaj wszystkie potwory!";
                break;
            case 3:
                message = "&b&lEtap 3 &3>> &fZniszcz Wszystkie Kamienie Metin!";
                break;
            case 4:
                message = "&b&lEtap 4 &3>> &fPokonaj wszystkie potwory!";
                break;
            case 5:
                message = "&b&lEtap 5 &3>> &fPokonaj &8&l[&4&lBOSS&8&l] &bKrola Lodu!";
                break;
        }
        for (Player player : dungeonWorld.getPlayers()) {
            player.sendMessage(Utils.format("&3&m================&b&l Ice Tower &3&m================"));
            player.sendMessage(Utils.format(message));
            player.sendMessage(Utils.format("&3&m================&b&l Ice Tower &3&m================"));
        }
    }

    private final List<Location> gateLocations = Arrays.asList(
            // BRAMA 1
            new Location(dungeonWorld, -4, 65, 76),
            new Location(dungeonWorld, -5, 65, 76),
            new Location(dungeonWorld, -6, 65, 76),

            new Location(dungeonWorld, -4, 66, 76),
            new Location(dungeonWorld, -5, 66, 76),
            new Location(dungeonWorld, -6, 66, 76),

            new Location(dungeonWorld, -4, 67, 76),
            new Location(dungeonWorld, -5, 67, 76),
            new Location(dungeonWorld, -6, 67, 76),

            new Location(dungeonWorld, -5, 68, 76),

            // BRAMA 2
            new Location(dungeonWorld, -4, 65, 86),
            new Location(dungeonWorld, -5, 65, 86),
            new Location(dungeonWorld, -6, 65, 86),

            new Location(dungeonWorld, -4, 66, 86),
            new Location(dungeonWorld, -5, 66, 86),
            new Location(dungeonWorld, -6, 66, 86),

            new Location(dungeonWorld, -4, 67, 86),
            new Location(dungeonWorld, -5, 67, 86),
            new Location(dungeonWorld, -6, 67, 86),

            new Location(dungeonWorld, -5, 68, 86),

            // BRAMA 3
            new Location(dungeonWorld, 0, 65, 82),
            new Location(dungeonWorld, 0, 65, 81),
            new Location(dungeonWorld, 0, 65, 80),

            new Location(dungeonWorld, 0, 66, 82),
            new Location(dungeonWorld, 0, 66, 81),
            new Location(dungeonWorld, 0, 66, 80),

            new Location(dungeonWorld, 0, 67, 82),
            new Location(dungeonWorld, 0, 67, 81),
            new Location(dungeonWorld, 0, 67, 80),

            new Location(dungeonWorld, 0, 68, 81),

            // BRAMA 4
            new Location(dungeonWorld, -10, 65, 82),
            new Location(dungeonWorld, -10, 65, 81),
            new Location(dungeonWorld, -10, 65, 80),

            new Location(dungeonWorld, -10, 66, 82),
            new Location(dungeonWorld, -10, 66, 81),
            new Location(dungeonWorld, -10, 66, 80),

            new Location(dungeonWorld, -10, 67, 82),
            new Location(dungeonWorld, -10, 67, 81),
            new Location(dungeonWorld, -10, 67, 80),

            new Location(dungeonWorld, -10, 68, 81)
    );

    public void endIceTower() {
        this.antyAfkTime = 0;
        this.antyAfkMaxTime = 360;
        this.status = DungeonStatus.UPGRADING;
        this.backPlayers();
        this.liftGate();
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc sel 77");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc spawn");

        for (Player player : this.dungeonWorld.getPlayers()) {
            if (Utils.removeColor(player.getName()).equals("Kowal")) continue;
            final MagazynierUser magazynier = rpgcore.getMagazynierNPC().find(player.getUniqueId());
            if (magazynier == null) {
                Utils.sendToHighStaff("&8===========================");
                Utils.sendToHighStaff("   &6Gracz: &c" + player.getName());
                Utils.sendToHighStaff("   &6UUID: &c" + player.getUniqueId().toString());
                Utils.sendToHighStaff("   &6Błąd: &cBrak Magazyniera (DT)");
                Utils.sendToHighStaff("&8===========================");
                player.kickPlayer(Utils.format(Utils.CLEANSERVERNAME + "\n&cCos poszlo nie tak!\n&c&lJak najszybciej zglos sie do administracji!!!\n&8Blad: &cBrak Magazyniera!\n&8UUID: " + player.getUniqueId().toString()));
             continue;
            }
            if (magazynier.getMissions().getSelectedMission() == 10) {
                magazynier.getMissions().setProgress(magazynier.getMissions().getProgress() + 1);
                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataMagazynier(magazynier.getUuid(), magazynier));
            }
            final MroznyStrozUser mroznyStrozUser = rpgcore.getMroznyStrozNPC().find(player.getUniqueId());
            if (mroznyStrozUser == null) {
                Utils.sendToHighStaff("&8===========================");
                Utils.sendToHighStaff("   &6Gracz: &c" + player.getName());
                Utils.sendToHighStaff("   &6UUID: &c" + player.getUniqueId().toString());
                Utils.sendToHighStaff("   &6Błąd: &cBrak Mroznego Stroza (DT)");
                Utils.sendToHighStaff("&8===========================");
                player.kickPlayer(Utils.format(Utils.CLEANSERVERNAME + "\n&cCos poszlo nie tak!\n&c&lJak najszybciej zglos sie do administracji!!!\n&8Blad: &cBrak Mroznego Stroza!\n&8UUID: " + player.getUniqueId().toString()));
                continue;
            }
            if (mroznyStrozUser.getMission() == 1 || mroznyStrozUser.getMission() == 3 || mroznyStrozUser.getMission() ==8) {
                mroznyStrozUser.setProgress(mroznyStrozUser.getProgress() + 1);
                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataMroznyStroz(mroznyStrozUser.getUuid(), mroznyStrozUser));
            }
        }

        int taskId = rpgcore.getServer().getScheduler().runTaskLater(rpgcore,() -> {
            this.spawnPlayers();
            this.resetDungeon();
        }, 7_200L).getTaskId();
        this.taskIds.add(taskId);
    }

    private void liftGate() {
        for (final Location loc : this.gateLocations) {
            loc.getBlock().setType(Material.AIR);
        }
    }

    private void lowerGate() {
        for (final Location loc : this.gateLocations) {
            loc.getBlock().setType(Material.IRON_FENCE);
        }
    }
}
