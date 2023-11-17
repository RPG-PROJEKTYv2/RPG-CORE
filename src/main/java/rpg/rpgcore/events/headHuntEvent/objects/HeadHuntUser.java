package rpg.rpgcore.events.headHuntEvent.objects;

import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import org.bukkit.Location;
import rpg.rpgcore.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class HeadHuntUser {
    private final UUID uuid;
    private Location glowka1;
    private List<Location> glowka1PrevLocations;
    private boolean glowka1Found;
    //...

    public HeadHuntUser(final UUID uuid) {
        this.uuid = uuid;
        this.glowka1 = null;
        this.glowka1PrevLocations = new ArrayList<>();
        this.glowka1Found = false;
        //...
    }

    public HeadHuntUser(final Document document) {
        this.uuid = UUID.fromString(document.getString("_id"));
        this.glowka1 = (document.containsKey("glowka1") ? Utils.locationFromString(document.getString("glowka1")) : null);
        final List<String> glowka1PrevLocationsString = (document.containsKey("glowka1PrevLocations") ? document.getList("glowka1PrevLocations", String.class) : new ArrayList<>());
        final List<Location> glowka1PrevLocations = new ArrayList<>();

        glowka1PrevLocationsString.forEach(key -> glowka1PrevLocations.add(Utils.locationFromString(key)));

        this.glowka1PrevLocations = glowka1PrevLocations;
        this.glowka1Found = (document.containsKey("glowka1Found") ? document.getBoolean("glowka1Found") : false);
        //...
    }


    public Document toDocument() {
        final List<String> glowka1PrevLocationsString = new ArrayList<>();
        this.glowka1PrevLocations.forEach(location -> glowka1PrevLocationsString.add(Utils.locatinoToString(location)));

        //...

        return new Document("_id", this.uuid.toString())
                .append("glowka1", Utils.locatinoToString(this.glowka1))
                .append("glowka1PrevLocations", glowka1PrevLocationsString)
                .append("glowka1Found", this.glowka1Found)
                //...
                ;
    }

}
