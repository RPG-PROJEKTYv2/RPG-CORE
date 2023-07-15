package rpg.rpgcore.npc.gornik.ore;

import com.google.common.collect.ImmutableSet;
import lombok.Getter;
import org.bukkit.*;
import org.bukkit.block.Block;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.npc.gornik.ore.enums.Ores;
import rpg.rpgcore.npc.gornik.ore.objects.Ore;

import java.util.*;

public class OreManager {
    @Getter
    private final Map<Location, Ore> oreMap;

    public OreManager(final RPGCORE rpgcore) {
        this.oreMap = rpgcore.getMongoManager().loadAllOre();
        this.resetOres();
        //rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, this::lagServer);
    }

    public void resetOres() {
        for (final Ore ore : this.oreMap.values()) {
            this.setOre(ore, Ores.getRandomOre());
        }
        this.fixOres();
    }

    public void setOre(final Ore ore, final Ores info) {
        ore.setType(info.getMaterial());
        ore.setExp(info.getExp());
        ore.setMaxHp(info.getHp());
        ore.setCurrentHp(info.getHp());
        ore.setRespawnTime(-1L);
        ore.getLocation().getBlock().setType(info.getMaterial());
    }

    public void fixOres() {
        final List<Ore> oreList = new ArrayList<>(this.oreMap.values());
        Collections.shuffle(oreList);
        for (final Ore ore : oreList) {
            Ores ores = Ores.getRandomOre();
            if (this.oreMap.values().stream().filter(ore1 -> ore1.getType().equals(Material.COAL_ORE)).count() < Math.ceil(this.oreMap.values().size() * 0.225))  ores = Ores.O1;
            if (this.oreMap.values().stream().filter(ore1 -> ore1.getType().equals(Material.IRON_ORE)).count() < Math.ceil(this.oreMap.values().size() * 0.205))  ores = Ores.O2;
            if (this.oreMap.values().stream().filter(ore1 -> ore1.getType().equals(Material.GOLD_ORE)).count() < Math.ceil(this.oreMap.values().size() * 0.165))  ores = Ores.O3;
            if (this.oreMap.values().stream().filter(ore1 -> ore1.getType().equals(Material.LAPIS_ORE)).count() < Math.ceil(this.oreMap.values().size() * 0.135))  ores = Ores.O4;
            if (this.oreMap.values().stream().filter(ore1 -> ore1.getType().equals(Material.EMERALD_ORE)).count() < Math.ceil(this.oreMap.values().size() * 0.105))  ores = Ores.O5;
            if (this.oreMap.values().stream().filter(ore1 -> ore1.getType().equals(Material.DIAMOND_ORE)).count() < Math.ceil(this.oreMap.values().size() * 0.08))  ores = Ores.O6;
            if (this.oreMap.values().stream().filter(ore1 -> ore1.getType().equals(Material.REDSTONE_ORE) || ore1.getType().equals(Material.GLOWING_REDSTONE_ORE)).count() < Math.ceil(this.oreMap.values().size() * 0.05))  ores = Ores.O7;
            this.setOre(ore, ores);
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
