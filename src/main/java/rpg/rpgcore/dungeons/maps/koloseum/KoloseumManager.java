package rpg.rpgcore.dungeons.maps.koloseum;

import lombok.Getter;
import lombok.Setter;
import me.filoghost.holographicdisplays.api.hologram.Hologram;
import me.filoghost.holographicdisplays.api.hologram.line.TextHologramLine;
import net.minecraft.server.v1_8_R3.PathfinderGoalFloat;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftZombie;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.dungeons.DungeonStatus;
import rpg.rpgcore.metiny.MetinyHelper;
import rpg.rpgcore.utils.ChanceHelper;
import rpg.rpgcore.utils.Utils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/*

Wyznawca Aresa(Podpala)(Pomarańczowy)
Wyznawca Zeusa(Podpala)(Żółty)
Wyznawca Hery(Oślepia)(Fioletowy)
Wyznawca Hestii(Oślepia)(Jasno Niebieski/Zielony)
Wyznawca Herkulesa(Trujce)(Granatowy)
Wyznawca Ateny(Ogłusza(Stun)(Szary)
Wyznawca Posejdona(Truje)(Niebieski jasny)
Wyznawca Hadesa(Ogłusza(Stun)(Czarny/Czerwony)


Abilitki
Zatrucie-Spowolnienie i zmniejszone obrażenia gracza
Podpalanie-Zwiększone obrażenia w gracza
Oślepienie-Zmniejszona widocznośc oraz mdłości)
Ogłuszenie-Freez oraz brak możliwości skakania

 */


public class KoloseumManager {
    private final RPGCORE rpgcore;

    public KoloseumManager(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
        RPGCORE.getHolographicDisplaysAPI().getHolograms().forEach(hologram -> {
            if (hologram.getPosition().toLocation().equals(new Location(map, -0.5, 85, 366.5))) {
                this.hologram = hologram;
            }
        });
        this.resetDungeon();
    }

    @Getter
    private Hologram hologram;
    @Getter
    private final World map = Bukkit.getWorld("70-80map");

    @Getter
    private final World dungeon = Bukkit.getWorld("Dungeon70-80");

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
    private final Location spawnLocation = new Location(dungeon, 159.5, 69, 162.5);


    public void resetDungeon() {
        this.resetHologram();
        this.closeMapGate();
        this.setOccupied(false);
        this.setCanJoin(false);
        this.resetCounter();
        this.setDungeonStatus(DungeonStatus.ENDED);
        this.clearEntities(true);
        for (final int metinId : this.getMetinIds()) MetinyHelper.despawnMetin(metinId);
        for (final int taskId : this.getTaskIds()) {
            if (taskId == -1) continue;
            this.rpgcore.getServer().getScheduler().cancelTask(taskId);
        }
        if (this.getTargetStand() != null) this.getTargetStand().remove();
        Bukkit.getServer().broadcastMessage(Utils.format("&6&lKoloseum &8>> &7Dungeon zostal zresetowany!"));
    }

    private final List<String> defaultHolo = Arrays.asList(
            "&6&lKoloseum",
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
            new Location(map, 1, 82, 368),
            new Location(map, 0, 82, 368),
            new Location(map, -1, 82, 368),
            new Location(map, -2, 82, 368),
            new Location(map, -3, 82, 368),

            new Location(map, 1, 83, 368),
            new Location(map, 0, 83, 368),
            new Location(map, -1, 83, 368),
            new Location(map, -2, 83, 368),
            new Location(map, -3, 83, 368),

            new Location(map, 1, 84, 368),
            new Location(map, 0, 84, 368),
            new Location(map, -1, 84, 368),
            new Location(map, -2, 84, 368),
            new Location(map, -3, 84, 368),

            new Location(map, 1, 85, 368),
            new Location(map, 0, 85, 368),
            new Location(map, -1, 85, 368),
            new Location(map, -2, 85, 368),
            new Location(map, -3, 85, 368),

            new Location(map, 1, 86, 368),
            new Location(map, 0, 86, 368),
            new Location(map, -1, 86, 368),
            new Location(map, -2, 86, 368),
            new Location(map, -3, 86, 368)
    );


    public void startDungeon(final Player player) {
        dungeonStatus = DungeonStatus.OPENED_GATE;
        Bukkit.broadcastMessage(Utils.format("&6&lKoloseum &7>> &7Gracz &f" + player.getName() + " &7otworzyl brame do &6&lKoloseum&7!"));
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
            Bukkit.broadcastMessage(Utils.format("&6&lKoloseum &7>> &7Brama do &6&lKoloseum &7zostala zamknieta!"));
            if (dungeon.getPlayers().isEmpty()) {
                this.resetDungeon();
                return;
            }
            this.startPhase1();
        }, 20 * 5);
    }

    public void openMapGate() {
        this.mapGateLocations.forEach(location -> location.getBlock().setType(Material.AIR));
    }

    public void closeMapGate() {
        this.mapGateLocations.forEach(location -> location.getBlock().setType(Material.IRON_FENCE));
    }




    @Getter
    private int counter = 0;
    public void incrementCounter() {
        counter++;
    }

    public void resetCounter() {
        counter = 0;
    }


    private void clearEntities(final boolean bypass) {
        if (bypass) {
            for (final Entity e : dungeon.getEntities()) {
                if (e instanceof Player) continue;
                e.remove();
            }
            return;
        }
        for (final Entity e : dungeon.getEntities()) {
            if (e instanceof Player) continue;
            if (e.getName().contains("Wyznawca") || e.getName().contains("Czempion")) continue;
            e.remove();
        }
    }

    @Getter
    private final List<Location> mobsSpawnLocations = Arrays.asList(
            new Location(dungeon, 159.5, 68, 175.5),
            new Location(dungeon, 145.5, 68, 162.5),
            new Location(dungeon, 159.5, 68, 148.5),
            new Location(dungeon, 173.5, 68, 162.5)
    );

    private void spawnMobs(final int mobType) {
        for (final Location loc : mobsSpawnLocations) {
            for (int i = 0; i < 60; i++) {
                final double addedX = ChanceHelper.getRandDouble(-0.5, 0.5);
                final double addedZ = ChanceHelper.getRandDouble(-0.5, 0.5);
                RPGCORE.getMythicMobs().getMobManager().spawnMob("KOLOSEUM-MOB" + mobType, loc.clone().add(addedX, 0, addedZ));
            }
        }
    }

    private void backPlayers() {
        for (final Player player : dungeon.getPlayers()) player.teleport(this.getSpawnLocation());
    }

    private void updatePhase(final int phase) {
        this.resetCounter();
        this.backPlayers();
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
                dungeonStatus = DungeonStatus.ETAP_3;
                break;
            case 4:
                dungeonStatus = DungeonStatus.ETAP_4;
                break;
            case 5:
                dungeonStatus = DungeonStatus.ETAP_5;
                break;
            case 6:
                dungeonStatus = DungeonStatus.ETAP_6;
                break;
            case 7:
                dungeonStatus = DungeonStatus.ETAP_7;
                break;
            case 8:
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
                message = "&6&lEtap 1 &8>> &7Pokonaj &f240 &eZapomnianych Wojownikow &fLvl. 75&7!";
                break;
            case 2:
                message = "&6&lEtap 2 &8>> &7Pokonaj &8&l[&7&lMini&4&lBOSS&8&l] &c&lWyznawce &4&lAteny&7!";
                break;
            case 3:
                message = "&6&lEtap 3 &8>> &7Zniszcz wszystkie &e&lMetiny Areny&7!";
                break;
            case 4:
                message = "&6&lEtap 4 &8>> &7Pokonaj &8&l[&7&lMini&4&lBOSS&8&l] &b&lWyznawce Posejdona&7!";
                break;
            case 5:
                message = "&6&lEtap 5 &8>> &7Pokonaj &f240 &eZapomnianych Wojownikow &fLvl. 79&7!";
                break;
            case 6:
                message = "&6&lEtap 6 &8>> &7Pokonaj &8&l[&7&lMini&4&lBOSS&8&l] &3&lWyznawce Zeusa &7i &8&l[&7&lMini&4&lBOSS&8&l] &4&lWyznawce Hadesa&7!";
                break;
            case 7:
                message = "&6&lEtap 7 &8>> &7Zniszcz wszystkie &e&lMetiny Areny&7!";
                break;
            case 8:
                message = "&6&lEtap 8 &8>> &7Pokonaj &8&l[&4&lBOSS&8&l] &6&lCzempiona Areny&7!";
                break;

        }
        for (Player player : dungeon.getPlayers()) {
            player.sendMessage(Utils.format("&7----------{ &6&lKoloseum &7}----------"));
            player.sendMessage(Utils.format(message));
            player.sendMessage(Utils.format("&7----------{ &6&lKoloseum &7}----------"));
        }
    }

    @Getter
    private final int[] taskIds = new int[5];
    @Getter
    private final Entity[] entities = new Entity[5];
    @Getter
    private final int[] metinIds = new int[]{40_000, 40_001, 40_002, 40_003, 40_004, 40_005};
    @Getter
    private ArmorStand targetStand;

    private void spawnEntities() {
        targetStand = (ArmorStand) dungeon.spawnEntity(new Location(dungeon, 159.5, 73.5, 162.5), EntityType.ARMOR_STAND);
        targetStand.setGravity(false);
        targetStand.setMarker(true);
        targetStand.setVisible(false);
        targetStand.setArms(false);
        targetStand.setBasePlate(false);
        targetStand.setSmall(true);
        entities[0] = RPGCORE.getMythicMobs().getMobManager().spawnMob("KOLOSEUM-MINIBOSS-1", new Location(dungeon, 140.5, 78.5, 162.5)).getEntity().getBukkitEntity();
        entities[1] = RPGCORE.getMythicMobs().getMobManager().spawnMob("KOLOSEUM-MINIBOSS-2", new Location(dungeon, 147.5, 78.5, 150.5)).getEntity().getBukkitEntity();
        entities[2] = RPGCORE.getMythicMobs().getMobManager().spawnMob("KOLOSEUM-MINIBOSS-3", new Location(dungeon, 171.5, 78.5, 150.5)).getEntity().getBukkitEntity();
        entities[3] = RPGCORE.getMythicMobs().getMobManager().spawnMob("KOLOSEUM-MINIBOSS-4", new Location(dungeon, 178.5, 78.5, 162.5)).getEntity().getBukkitEntity();
        entities[4] = RPGCORE.getMythicMobs().getMobManager().spawnMob("KOLOSEUM-BOSS", new Location(dungeon, 159.5, 78.5, 180.5)).getEntity().getBukkitEntity();


        // TODO Sprawdzic task id czy dobrze zwraca
        // TODO Sprawdzic o chuj chdodzi z tym lataniem bo nie dziala
        // TODO Sprawdzic czemu nie anuluje taska

        for (final Entity entity : entities) {
            ((CraftZombie) entity).getHandle().goalSelector.a(0, new PathfinderGoalFloat(((CraftZombie) entity).getHandle()));
            taskIds[0] = Utils.flyAndTarget(entity, targetStand);
        }
    }

    private void spawnMetins(int amount) {
        for (int i = 0; i < amount; i++) MetinyHelper.spawnMetinByPass(metinIds[i]);
    }

    private void getBossDown(final int bossId, final String bossName) {
        Entity boss = entities[bossId];
        this.rpgcore.getServer().getScheduler().cancelTask(taskIds[bossId]);
        taskIds[bossId] = -1;

        boss.remove();
        RPGCORE.getMythicMobs().getMobManager().spawnMob(bossName, new Location(dungeon, 159.5, 68, 205.5, 180F, 0));
    }


    public void startPhase1() {
        this.spawnEntities();
        this.updatePhase(1);
        this.sendMessage(1);
        this.spawnMobs(1);
    }
    public void startPhase2() {
        this.updatePhase(2);
        this.sendMessage(2);
        this.getBossDown(0, "KOLOSEUM-MINIBOSS-1");
    }
    public void startPhase3() {
        this.updatePhase(3);
        this.sendMessage(3);
        this.spawnMetins(4);
    }
    public void startPhase4() {
        this.updatePhase(4);
        this.sendMessage(4);
        this.getBossDown(1, "KOLOSEUM-MINIBOSS-2");
    }
    public void startPhase5() {
        this.updatePhase(5);
        this.sendMessage(5);
        this.spawnMobs(2);
    }
    public void startPhase6() {
        this.updatePhase(6);
        this.sendMessage(6);
        this.getBossDown(2, "KOLOSEUM-MINIBOSS-3");
        this.getBossDown(3, "KOLOSEUM-MINIBOSS-4");
    }
    public void startPhase7() {
        this.updatePhase(7);
        this.sendMessage(7);
        this.spawnMetins(6);
    }
    public void startPhase8() {
        this.updatePhase(8);
        this.sendMessage(8);
        this.getBossDown(4, "KOLOSEUM-BOSS");
    }

    public void endDungeon() {
        for (Player player : dungeon.getPlayers()) {
            player.teleport(rpgcore.getSpawnManager().getSpawn());
        }
        final String playerName = Utils.removeColor(Objects.requireNonNull(((TextHologramLine) hologram.getLines().get(3)).getText())).replace("Przechodzi: ", "");
        Bukkit.getServer().broadcastMessage(Utils.format("&6&lKoloseum &8>> &7Grupa gracza &f" + playerName + " &7ukonczyla dungeon!"));
        this.dungeonStatus = DungeonStatus.RESETTING;
        this.resetDungeon();
    }



}
