package rpg.rpgcore.npc.gornik.ore.objects;

import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import rpg.rpgcore.npc.gornik.ore.enums.Ores;
import rpg.rpgcore.utils.ChanceHelper;

@Getter
@Setter
public class Ore {
    private final int id;
    private final Location location;
    private Material type;
    private int currentHp, maxHp, exp;
    private long respawnTime;


    public Ore(final int id, final Location location) {
        this.id = id;
        this.location = location;
        final Ores info = Ores.getRandomOre();
        System.out.println(info.getMaterial().toString());
        this.type = info.getMaterial();
        this.maxHp = info.getHp();
        this.currentHp = info.getHp();
        this.exp = info.getExp();
        this.respawnTime = 0L;
    }

    public Ore(final Document document) {
        this.id = document.getInteger("_id");
        final Document locDoc = document.get("location", Document.class);
        this.location = new Location(Bukkit.getWorld(locDoc.getString("world")), locDoc.getDouble("x"), locDoc.getDouble("y"), locDoc.getDouble("z"));
        this.type = Material.valueOf(document.getString("type"));
        this.maxHp = document.getInteger("maxHp");
        this.currentHp = document.getInteger("currentHp");
        this.exp = document.getInteger("exp");
        try {
            this.respawnTime = document.getLong("respawnTime");
        } catch (final ClassCastException e) {
            this.respawnTime = (long) document.getInteger("respawnTime");
        }
    }

    public long getRandomRespawnTime() {
        return (long) ChanceHelper.getRandInt(2, 5) * 60 * 1000;
    }

    public void breakOre() {
        this.respawnTime = System.currentTimeMillis() + this.getRandomRespawnTime();
        this.currentHp = this.maxHp;
        location.getBlock().setType(Material.BEDROCK);
    }

    public Document toDocument() {
        return new Document("_id", this.id)
                .append("location", new Document("_id", "location")
                        .append("world", this.location.getWorld().getName())
                        .append("x", this.location.getX())
                        .append("y", this.location.getY())
                        .append("z", this.location.getZ()))
                .append("type", this.type.toString())
                .append("maxHp", this.maxHp)
                .append("currentHp", this.currentHp)
                .append("exp", this.exp)
                .append("respawnTime", this.respawnTime);
    }
}
