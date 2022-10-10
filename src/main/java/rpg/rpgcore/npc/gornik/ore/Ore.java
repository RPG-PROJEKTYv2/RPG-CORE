package rpg.rpgcore.npc.gornik.ore;

import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import org.bukkit.Location;
import org.bukkit.Material;

@Getter
@Setter
public class Ore {
    private int id;
    private Material oreMaterial;
    private String world;
    private double x,y,z;
    private int hp, maxHp;

    public Ore(int id, Material oreMaterial, Location location, int hp, int maxHp) {
        this.id = id;
        this.oreMaterial = oreMaterial;
        this.world = location.getWorld().getName();
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
        this.hp = hp;
        this.maxHp = maxHp;
    }

    public Ore(final Document document) {
        this.id = document.getInteger("_id");
        this.oreMaterial = Material.valueOf(document.getString("oreMaterial"));
        this.world = document.getString("world");
        this.x = document.getDouble("x");
        this.y = document.getDouble("y");
        this.z = document.getDouble("z");
        this.hp = document.getInteger("hp");
        this.maxHp = document.getInteger("maxHp");
    }

    public Location getLocation() {
        return new Location(org.bukkit.Bukkit.getWorld(world), x, y, z);
    }


    public Document toDocument() {
        return new Document("_id", id)
                .append("oreMaterial", oreMaterial.name())
                .append("world", world)
                .append("x", x)
                .append("y", y)
                .append("z", z)
                .append("hp", hp)
                .append("maxHp", maxHp);
    }
}
