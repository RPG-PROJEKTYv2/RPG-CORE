package rpg.rpgcore.metiny;

import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class Metiny {
    private final int id;
    private final MetinsObject metins;

    public Metiny(int id) {
        this.id = id;
        this.metins = new MetinsObject("", "", 0, 0, 0, 0, 0, 0, 0);
    }

    public Metiny(Document document) {
        this.id = document.getInteger("_id");
        this.metins = new MetinsObject(
                document.getString("name"),
                document.getString("world"),
                document.getDouble("x"),
                document.getDouble("y"),
                document.getDouble("z"),
                document.getInteger("maxhealth"),
                document.getInteger("health"),
                document.getInteger("resp"),
                document.getInteger("moby")
        );
    }

    public int getId() {
        return id;
    }

    public MetinsObject getMetins() {
        return this.metins;
    }

    public Location getLocation() {
        return new Location(Bukkit.getWorld(this.getMetins().getWorld()), this.getMetins().getX(), this.getMetins().getY(), this.getMetins().getZ());
    }

    public Document toDocument() {
        return new Document("_id", this.id)
                .append("name", this.getMetins().getName())
                .append("world", this.getMetins().getWorld())
                .append("x", this.getMetins().getX())
                .append("y", this.getMetins().getY())
                .append("z", this.getMetins().getZ())
                .append("maxhealth", this.getMetins().getMaxhealth())
                .append("health", this.getMetins().getHealth())
                .append("resp", this.getMetins().getResp())
                .append("moby", this.getMetins().getMoby());
    }
}
