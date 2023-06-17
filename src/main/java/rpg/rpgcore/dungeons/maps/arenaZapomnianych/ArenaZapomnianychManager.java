package rpg.rpgcore.dungeons.maps.arenaZapomnianych;

import lombok.Getter;
import lombok.Setter;
import me.filoghost.holographicdisplays.api.hologram.Hologram;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.dungeons.DungeonStatus;
import rpg.rpgcore.utils.Utils;

import java.util.Arrays;
import java.util.List;

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


public class ArenaZapomnianychManager {
    public ArenaZapomnianychManager() {
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


    public void resetDungeon() {
        this.resetHologram();
        this.closeMapGate();
        this.setOccupied(false);
        this.setCanJoin(false);
        this.setDungeonStatus(DungeonStatus.ENDED);
        Bukkit.getServer().broadcastMessage(Utils.format("&7&lArena Zapomnianych &8>> &fDungeon zostal zresetowany!"));
    }

    private final List<String> defaultHolo = Arrays.asList(
            "&7&lArena Zapomnianych",
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

    public void openMapGate() {
        this.mapGateLocations.forEach(location -> location.getBlock().setType(Material.AIR));
    }

    public void closeMapGate() {
        this.mapGateLocations.forEach(location -> location.getBlock().setType(Material.IRON_FENCE));
    }


}
