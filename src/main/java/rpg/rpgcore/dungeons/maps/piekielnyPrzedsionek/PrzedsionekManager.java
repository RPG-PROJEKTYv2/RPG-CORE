package rpg.rpgcore.dungeons.maps.piekielnyPrzedsionek;

import lombok.Getter;
import lombok.Setter;
import me.filoghost.holographicdisplays.api.hologram.Hologram;
import me.filoghost.holographicdisplays.api.hologram.line.TextHologramLine;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.dungeons.DungeonStatus;
import rpg.rpgcore.metiny.MetinyHelper;
import rpg.rpgcore.npc.magazynier.objects.MagazynierUser;
import rpg.rpgcore.utils.ChanceHelper;
import rpg.rpgcore.utils.Utils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class PrzedsionekManager {

    private final RPGCORE rpgcore;

    public PrzedsionekManager(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
        RPGCORE.getHolographicDisplaysAPI().getHolograms().forEach(hologram -> {
            if (hologram.getPosition().toLocation().equals(new Location(gateWorld, 37.5, 77, 52))) {
                this.dungeonHologram = hologram;
            }
        });
        this.resetDungeon();
    }

    @Getter
    private final World gateWorld = Bukkit.getWorld("60-70map");
    @Getter
    private final World dungeonWorld = Bukkit.getWorld("Dungeon60-70");
    @Getter
    private final Location spawnLocation = new Location(dungeonWorld, -2.5, 66, 0.5, 90, 0);
    @Getter
    private final List<Location> gateLocations = Arrays.asList(
            new Location(gateWorld, 35, 74, 49),
            new Location(gateWorld, 35, 74, 50),
            new Location(gateWorld, 35, 74, 51),
            new Location(gateWorld, 35, 74, 52),
            new Location(gateWorld, 35, 74, 53),
            new Location(gateWorld, 35, 74, 54),

            new Location(gateWorld, 35, 75, 49),
            new Location(gateWorld, 35, 75, 50),
            new Location(gateWorld, 35, 75, 51),
            new Location(gateWorld, 35, 75, 52),
            new Location(gateWorld, 35, 75, 53),
            new Location(gateWorld, 35, 75, 54),

            new Location(gateWorld, 35, 76, 50),
            new Location(gateWorld, 35, 76, 51),
            new Location(gateWorld, 35, 76, 52),
            new Location(gateWorld, 35, 76, 53),

            new Location(gateWorld, 35, 77, 50),
            new Location(gateWorld, 35, 77, 51),
            new Location(gateWorld, 35, 77, 52),
            new Location(gateWorld, 35, 77, 53),

            new Location(gateWorld, 35, 78, 50),
            new Location(gateWorld, 35, 78, 51),
            new Location(gateWorld, 35, 78, 52),

            new Location(gateWorld, 35, 79, 51),
            new Location(gateWorld, 35, 79, 52)
    );

    final List<String> baseHoloLines = Arrays.asList(
            "&4&lPiekielny Przedsionek",
            "&7Status: &aWolny",
            "&7Etap: &aWolny",
            "&7Przechodzi: &cNikt",
            "&7",
            "&8Kliknij &ckluczem &8w brame",
            "&8zeby rozpoczac"
    );

    @Getter
    @Setter
    private DungeonStatus dungeonStatus;
    @Getter
    private Hologram dungeonHologram;
    @Getter
    @Setter
    private boolean occupied = false;
    @Getter
    @Setter
    private boolean canJoin = false;


    public void resetDungeon() {
        this.closeGate();
        this.setOccupied(false);
        this.setCanJoin(false);
        this.clearEntities();
        dungeonStatus = DungeonStatus.ENDED;
        this.resetHologram();
        this.resetCounter();
        this.getDungeonWorld().getPlayers().forEach(p -> p.teleport(rpgcore.getSpawnManager().getSpawn()));
        Bukkit.getServer().broadcastMessage(Utils.format("&4&lPiekielny Przedsionek &8>> &cDungeon zostal zresetowany!"));
    }


    public void resetHologram() {
        dungeonHologram.getLines().clear();
        for (String baseHoloLine : baseHoloLines) {
            dungeonHologram.getLines().appendText(Utils.format(baseHoloLine));
        }
    }

    public void startDungeon(final Player player) {
        dungeonStatus = DungeonStatus.OPENED_GATE;
        Bukkit.broadcastMessage(Utils.format("&4&lPiekielny Przedsionek &7>> &cGracz &4" + player.getName() + " &cotworzyl brame do &4Piekielnego Przedsionka&c!"));
        ((TextHologramLine) dungeonHologram.getLines().get(1)).setText(Utils.format("&7Status: &cZajety"));
        ((TextHologramLine) dungeonHologram.getLines().get(2)).setText(Utils.format("&7Etap: " + dungeonStatus.getName()));
        ((TextHologramLine) dungeonHologram.getLines().get(3)).setText(Utils.format("&7Przechodzi: &c" + player.getName()));
        dungeonHologram.getLines().insertText(4, Utils.format("&7Ilosc graczy: &c0"));
        this.openGate();
        this.setOccupied(true);
        this.setCanJoin(true);
        this.rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> {
            dungeonStatus = DungeonStatus.CLOSED_GATE;
            ((TextHologramLine) dungeonHologram.getLines().get(2)).setText(Utils.format("&7Etap: " + dungeonStatus.getName()));
            this.closeGate();
            this.setCanJoin(false);
            Bukkit.broadcastMessage(Utils.format("&4&lPiekielny Przedsionek &7>> &cBrama do &4Piekielnego Przedsionka &czostala zamknieta!"));
            if (dungeonWorld.getPlayers().isEmpty()) {
                this.resetDungeon();
                return;
            }
            this.startPhase1();
        }, 20 * 5);
    }

    public void openGate() {
        for (final Location loc : gateLocations) {
            loc.getBlock().setType(Material.AIR);
        }
    }

    public void closeGate() {
        for (final Location loc : gateLocations) {
            loc.getBlock().setType(Material.NETHER_FENCE);
        }
    }

    @Getter
    private List<Location> roomMidsLocations = Arrays.asList(
            new Location(dungeonWorld, -34.5, 66, 0.5),
            new Location(dungeonWorld, -58.5, 66, 0.5),
            new Location(dungeonWorld, -82.5, 66, 0.5),
            new Location(dungeonWorld, -106.5, 66, 0.5),

            new Location(dungeonWorld, -34.5, 66, 24.5),
            new Location(dungeonWorld, -58.5, 66, 24.5),
            new Location(dungeonWorld, -82.5, 66, 24.5),

            new Location(dungeonWorld, -58.5, 66, 48.5),

            new Location(dungeonWorld, -34.5, 66, -23.5),
            new Location(dungeonWorld, -58.5, 66, -23.5),
            new Location(dungeonWorld, -82.5, 66, -23.5),

            new Location(dungeonWorld, -58.5, 66, -47.5)
    );
    @Getter
    private List<Integer> metinsId = Arrays.asList(30_000, 30_001, 30_002, 30_003, 30_004, 30_005, 30_006, 30_007, 30_008, 30_009, 30_010, 30_011);
    @Getter
    private int counter = 0;
    public void incrementCounter() {
        counter++;
    }

    public void resetCounter() {
        counter = 0;
    }

    public void backPlayer() {
        for (Player player : dungeonWorld.getPlayers()) {
            player.teleport(this.getSpawnLocation());
        }
    }

    private void spawnMobs(final int amount) {
        for (final Location loc : roomMidsLocations) {
            for (int i = 0; i < amount; i++) {
                final double addedX = ChanceHelper.getRandDouble(-0.5, 0.5);
                final double addedZ = ChanceHelper.getRandDouble(-0.5, 0.5);
                RPGCORE.getMythicMobs().getMobManager().spawnMob("PIEKIELNY-PRZEDSIONEK-MOB", loc.clone().add(addedX, 0, addedZ));
            }
        }
    }

    private void clearEntities() {
        for (final Entity e : dungeonWorld.getEntities()) {
            if (e instanceof Player) continue;
            e.remove();
        }
    }

    private void updatePhase(final int phase) {
        this.resetCounter();
        this.backPlayer();
        this.clearEntities();
        String prefix = "&c";
        switch (phase) {
            case 1:
                dungeonStatus = DungeonStatus.ETAP_1;
                break;
            case 2:
                dungeonStatus = DungeonStatus.ETAP_2;
                break;
            case 3:
                dungeonStatus = DungeonStatus.ETAP_3;
                break;
            case 4:
                prefix = "&4&l";
                dungeonStatus = DungeonStatus.BOSS;
                break;
            default:
                break;
        }
        ((TextHologramLine) dungeonHologram.getLines().get(2)).setText(Utils.format("&7Etap: " + prefix + dungeonStatus.getName()));
    }

    private void sendMessage(final int phase) {
        String message = "";
        switch (phase) {
            case 1:
                message = "&c&lEtap 1 &8>> &7Zabij wszystkie moby!";
                break;
            case 2:
                message = "&c&lEtap 2 &8>> &7Zniszcz wszystkie kamienie metin!";
                break;
            case 3:
                message = "&c&lEtap 3 &8>> &7Zabij wszystkie moby!";
                break;
            case 4:
                message = "&c&lEtap 4 &8>> &7Zabij &8&l[&4&lBOSS&8&l] &c&lPiekielnego Wladce!";
                break;

        }
        for (Player player : dungeonWorld.getPlayers()) {
            player.sendMessage(Utils.format("&c----------{ &4&lPiekielny Przedsionek &c}----------"));
            player.sendMessage(Utils.format(message));
            player.sendMessage(Utils.format("&c----------{ &4&lPiekielny Przedsionek &c}----------"));
        }
    }

    public void startPhase1() {
        this.updatePhase(1);
        this.sendMessage(1);
        this.spawnMobs(8);
    }

    public void startPhase2() {
        this.updatePhase(2);
        this.sendMessage(2);
        for (Integer id : metinsId) {
            MetinyHelper.spawnMetinByPass(id);
        }
    }

    public void startPhase3() {
        this.updatePhase(3);
        this.sendMessage(3);
        this.spawnMobs(12);
    }

    @Getter
    private final Location bossSpawnLocation = new Location(dungeonWorld, -106.5, 66, 0.5, -90, 0);
    public void startPhase4() {
        this.updatePhase(4);
        this.sendMessage(4);
        RPGCORE.getMythicMobs().getMobManager().spawnMob("PIEKIELNY-PRZEDSIONEK-BOSS", this.getBossSpawnLocation());
    }

    public void endDungeon() {
        for (Player player : dungeonWorld.getPlayers()) {
            player.teleport(rpgcore.getSpawnManager().getSpawn());
            final MagazynierUser user = rpgcore.getMagazynierNPC().find(player.getUniqueId());
            if (user == null) continue;
            if (user.getMissions().getSelectedMission() == 4) {
                user.getMissions().setProgress(user.getMissions().getProgress() + 1);
                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataMagazynier(player.getUniqueId(), user));
            }
        }
        final String playerName = Utils.removeColor(Objects.requireNonNull(((TextHologramLine) rpgcore.getPrzedsionekManager().getDungeonHologram().getLines().get(3)).getText())).replace("Przechodzi: ", "");
        Bukkit.getServer().broadcastMessage(Utils.format("&4&lPiekielny Przedsionek &8>> &cGrupa gracza &4" + playerName + " &cukonczyla dungeon!"));
        this.dungeonStatus = DungeonStatus.RESETTING;
        this.resetDungeon();
    }
}
