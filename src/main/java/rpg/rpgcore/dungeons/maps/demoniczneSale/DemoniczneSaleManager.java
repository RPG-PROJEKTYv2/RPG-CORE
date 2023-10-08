package rpg.rpgcore.dungeons.maps.demoniczneSale;

import io.lumine.xikage.mythicmobs.spawning.spawners.MythicSpawner;
import lombok.Getter;
import lombok.Setter;
import me.filoghost.holographicdisplays.api.hologram.Hologram;
import me.filoghost.holographicdisplays.api.hologram.line.TextHologramLine;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.dungeons.DungeonStatus;
import rpg.rpgcore.dungeons.maps.tajemniczePiaski.objects.RdzenPiaszczystychWydm;
import rpg.rpgcore.metiny.MetinyHelper;
import rpg.rpgcore.npc.magazynier.objects.MagazynierUser;
import rpg.rpgcore.npc.pustelnik.objects.PustelnikUser;
import rpg.rpgcore.utils.ChanceHelper;
import rpg.rpgcore.utils.Utils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


public class DemoniczneSaleManager {
    private final RPGCORE rpgcore;


    public DemoniczneSaleManager(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
        RPGCORE.getHolographicDisplaysAPI().getHolograms().forEach(hologram -> {
            if (hologram.getPosition().toLocation().equals(new Location(map, 284.5, 110, 106.5))) {
                this.hologram = hologram;
            }
        });
        this.resetDungeon();
    }

    @Getter
    private Hologram hologram;
    @Getter
    private final World map = Bukkit.getWorld("90-100map");

    @Getter
    private final World dungeon = Bukkit.getWorld("Dungeon90-100");

    @Getter
    @Setter
    private DungeonStatus dungeonStatus;
    @Getter
    @Setter
    private boolean occupied = false;
    @Getter
    @Setter
    private boolean canJoin = false;
    @Getter
    private final Location spawnLocation = new Location(dungeon, 0.5, 65, 110.5, 180, 0);


    public void resetDungeon() {
        this.resetHologram();
        this.closeMapGate();
        this.setOccupied(false);
        this.setCanJoin(false);
        this.resetCounter();
        this.resetTime();
        this.setDungeonStatus(DungeonStatus.ENDED);
        this.clearEntities(true);
        this.deactivateSpawners();
        this.getDungeon().getPlayers().forEach(p -> p.teleport(rpgcore.getSpawnManager().getSpawn()));
        //for (final int metinId : this.getMetinIds()) MetinyHelper.despawnMetin(metinId);
        Bukkit.getServer().broadcastMessage(Utils.format("&4&lDemoniczne Sale &8>> &cDungeon zostal zresetowany!"));
    }

    private final List<String> defaultHolo = Arrays.asList(
            "&4&lDemoniczne Sale",
            "&7Status: &aWolny",
            "&7Etap: &aWolny",
            "&7Przechodzi: &cNikt",
            "&7",
            "",
            "&8Kliknij &ckluczem &8w brame",
            "&8zeby rozpoczac"
    );

    public void resetHologram() {
        this.hologram.getLines().clear();
        for (String line : defaultHolo) {
            this.hologram.getLines().appendText(Utils.format(line));
        }
    }

    @Getter
    private final List<Location> mapGateLocations = Arrays.asList(
            new Location(map, 282, 107, 107),
            new Location(map, 282, 107, 106),

            new Location(map, 282, 108, 107),
            new Location(map, 282, 108, 106),
            new Location(map, 282, 108, 105),

            new Location(map, 282, 109, 106),
            new Location(map, 282, 109, 105),

            new Location(map, 282, 110, 107),
            new Location(map, 282, 110, 106),
            new Location(map, 282, 110, 105)
    );


    public void startDungeon(final Player player) {
        dungeonStatus = DungeonStatus.OPENED_GATE;
        Bukkit.broadcastMessage(Utils.format("&4&lDemoniczne Sale &8>> &cGracz &4" + player.getName() + " &cotworzyl brame do &4&lDemonicznych Sal&c!"));
        ((TextHologramLine) hologram.getLines().get(1)).setText(Utils.format("&7Status: &cZajety"));
        ((TextHologramLine) hologram.getLines().get(2)).setText(Utils.format("&7Etap: " + dungeonStatus.getName()));
        ((TextHologramLine) hologram.getLines().get(3)).setText(Utils.format("&7Przechodzi: &c" + player.getName()));
        hologram.getLines().insertText(4, Utils.format("&7Ilosc graczy: &c0"));
        this.openMapGate();
        this.setOccupied(true);
        this.setCanJoin(true);
        this.rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> {
            dungeonStatus = DungeonStatus.CLOSED_GATE;
            ((TextHologramLine) hologram.getLines().get(2)).setText(Utils.format("&7Etap: " + dungeonStatus.getName()));
            this.closeMapGate();
            this.setCanJoin(false);
            Bukkit.broadcastMessage(Utils.format("&4&lDemoniczne Sale &8>> &cBrama do &4&lDemonicznych Sal &czostala zamknieta!"));
            if (dungeon.getPlayers().isEmpty()) {
                this.resetDungeon();
                return;
            }
            this.startPhase1();
        }, 20 * 20);
    }

    public void openMapGate() {
        this.mapGateLocations.forEach(location -> location.getBlock().setType(Material.AIR));
    }

    public void closeMapGate() {
        this.mapGateLocations.forEach(location -> location.getBlock().setType(Material.IRON_FENCE));
    }


    @Getter
    private int counter = 0;

    @Getter
    private int time = 0;

    public void incrementCounter() {
        counter++;
    }

    public void resetCounter() {
        counter = 0;
    }

    public void incrementTime() {
        time++;
    }

    public void resetTime() {
        time = 0;
    }


    private void clearEntities(final boolean bypass) {
        if (bypass) {
            for (final Entity e : dungeon.getEntities()) {
                if (e instanceof Player || e instanceof ArmorStand) continue;
                e.remove();
            }
            return;
        }
        for (final Entity e : dungeon.getEntities()) {
            if (e instanceof Player || e instanceof ArmorStand || e instanceof Item) continue;
            e.remove();
        }
    }



    public void activateSpawners() {
        for (final MythicSpawner spawner : RPGCORE.getMythicMobs().getSpawnerManager().getSpawners().stream().filter(s -> s.getName().contains("DEMONICZNE-SALE-MOB1-RESP-")).collect(Collectors.toList())) {
            spawner.Enable();
            spawner.ActivateSpawner();
        }
    }

    public void deactivateSpawners() {
        for (final MythicSpawner spawner : RPGCORE.getMythicMobs().getSpawnerManager().getSpawners().stream().filter(s -> s.getName().contains("DEMONICZNE-SALE-MOB1-RESP-")).collect(Collectors.toList())) {
            spawner.Disable();
        }
        this.clearEntities(false);
    }

    private void updatePhase(final int phase) {
        this.resetCounter();
        this.clearEntities(false);
        String prefix = "&c";
        switch (phase) {
            case 1:
                dungeonStatus = DungeonStatus.ETAP_1;
                break;
            case 2:
                dungeonStatus = DungeonStatus.ETAP_2;
                break;
            case 3:
                prefix = "&4&l";
                dungeonStatus = DungeonStatus.BOSS;
                break;
            default:
                break;
        }
        ((TextHologramLine) hologram.getLines().get(2)).setText(Utils.format("&7Etap: " + prefix + dungeonStatus.getName()));
    }

    private void sendMessage(final int phase) {
        String message = "";
        switch (phase) {
            case 1:
                message = "&4&lEtap 1 &8>> &cUkoncz &4&lDemoniczny Sarkofag&c!";
                break;
            case 2:
                message = "&4&lEtap 2 &8>> &cPokonaj &8&l[&7&lMini&4&lBOSS&8&l] &c&lElitarnego Sluge&c!";
                break;
            case 3:
                message = "&4&lEtap 3 &8>> &cPokonaj &8&l[&4&lBOSS&8&l] &4&lDemona Ciemnosci&c!";
                break;
        }
        for (Player player : dungeon.getPlayers()) {
            player.sendMessage(Utils.format("&c----------{ &4&lDemoniczne Sale &c}----------"));
            player.sendMessage(Utils.format(message));
            player.sendMessage(Utils.format("&c----------{ &4&lDemoniczne Sale &c}----------"));
        }
    }

    private void backPlayer() {
        for (Player player : dungeon.getPlayers()) {
            player.teleport(this.spawnLocation);
        }
    }



    public void startPhase1() {
        this.backPlayer();
        this.updatePhase(1);
        this.sendMessage(1);
        this.activateSpawners();
    }

    public void startPhase2() {
        this.backPlayer();
        this.updatePhase(2);
        this.sendMessage(2);
        RPGCORE.getMythicMobs().getMobManager().spawnMob("DEMONICZNE-SALE-MINIBOSS1", new Location(dungeon, 0.5, 65, 64.5));
    }

    public void startPhase3() {
        this.backPlayer();
        this.updatePhase(3);
        this.sendMessage(3);
        RPGCORE.getMythicMobs().getMobManager().spawnMob("DEMONICZNE-SALE-BOSS", new Location(dungeon, 0.5, 65, 64.5));

    }


    public void endDungeon() {
        for (Player player : dungeon.getPlayers()) {
            player.teleport(rpgcore.getSpawnManager().getSpawn());
            final MagazynierUser user = rpgcore.getMagazynierNPC().find(player.getUniqueId());
            if (user == null) continue;
            if (user.getMissions().getSelectedMission() == 4) {
                user.getMissions().setProgress(user.getMissions().getProgress() + 1);
                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataMagazynier(player.getUniqueId(), user));
            }
        }
        final String playerName = Utils.removeColor(Objects.requireNonNull(((TextHologramLine) hologram.getLines().get(3)).getText())).replace("Przechodzi: ", "");
        Bukkit.getServer().broadcastMessage(Utils.format("&4&lDemoniczne Sale &8>> &cGrupa gracza &4" + playerName + " &cukonczyla dungeon!"));
        this.dungeonStatus = DungeonStatus.RESETTING;
        this.resetDungeon();
    }

}
