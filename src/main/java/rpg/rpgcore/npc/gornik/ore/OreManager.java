package rpg.rpgcore.npc.gornik.ore;

import com.google.common.collect.ImmutableSet;
import org.bukkit.*;
import org.bukkit.block.Block;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.npc.gornik.ore.enums.Ores;
import rpg.rpgcore.npc.gornik.ore.objects.Ore;

import java.util.Map;

public class OreManager {
    private final Map<Location, Ore> oreMap;

    public OreManager(final RPGCORE rpgcore) {
        this.oreMap = rpgcore.getMongoManager().loadAllOre();
        //this.fixOres();
        //rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, this::lagServer);
    }

    public void fixOres() {
        for (final Ore ore : this.oreMap.values()) {
            final Ores ores = Ores.getRandomOre();
            ore.setType(ores.getMaterial());
            ore.setExp(ores.getExp());
            ore.setMaxHp(ores.getHp());
            ore.setCurrentHp(ores.getHp());
            ore.getLocation().getBlock().setType(ores.getMaterial());
        }
    }

    public void lagServer() {
        final World kopalnia = Bukkit.getWorld("Kopalnia");
        int id = 1;
        for (int y = 66; y <= 83; y++) {
            for (int x = -256; x <= 0; x++) {
                for (int z = -94; z <= 80; z++) {
                    final Block block = kopalnia.getBlockAt(x, y, z);
                    if (!block.getType().equals(Material.EMERALD_BLOCK)) continue;
                    if (this.oreMap.containsKey(block.getLocation())) continue;
                    System.out.println("Znaleziono Blok: " + id + " na pozycji: " + x + " " + y + " " + z);
                    final Ore ore = new Ore(id, block.getLocation());
                    this.add(ore);
                    id++;
                }
            }
        }
    }

    public Ore find(final Location location) {
        return this.oreMap.get(location);
    }

    public void add(final Ore ore) {
        this.oreMap.put(ore.getLocation(), ore);
    }

    public void remove(final Ore ore) {
        this.oreMap.remove(ore.getLocation());
    }

    public ImmutableSet<Ore> getOres() {
        return ImmutableSet.copyOf(this.oreMap.values());
    }



}
