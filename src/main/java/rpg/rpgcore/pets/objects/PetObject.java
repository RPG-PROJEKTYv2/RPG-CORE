package rpg.rpgcore.pets.objects;

import org.bson.Document;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

public class PetObject {
    private final UUID uuid;
    private final Pet pet;

    public PetObject(final UUID uuid) {
        this.uuid = uuid;
        this.pet = new Pet(null, "", 0, 0.0, 0.0, 0.0, "", 0.0, 0.0, 0.0, 0.0, "");
    }


    public PetObject(final Document document) {
        this.uuid = UUID.fromString(document.getString("_id"));
        this.pet = new Pet(Utils.deserializeItem(document.getString("item")), document.getString("name"),
                document.getInteger("lvl"), document.getDouble("exp"), document.getDouble("reqExp"), document.getDouble("totalExp"),
                document.getString("rarity"), document.getDouble("value1"), document.getDouble("value2"), document.getDouble("value3"),
                document.getDouble("value4"), document.getString("ultimateAbility"));
    }

    public UUID getId() {
        return uuid;
    }

    public Pet getPet() {
        return pet;
    }

    public Document toDocument() {
        return new Document("_id", uuid.toString())
                .append("item", Utils.serializeItem(pet.getItem()))
                .append("name", pet.getName())
                .append("lvl", pet.getLvl())
                .append("exp", pet.getExp())
                .append("reqExp", pet.getReqExp())
                .append("totalExp", pet.getTotalExp())
                .append("rarity", pet.getRarity())
                .append("value1", pet.getValue1())
                .append("value2", pet.getValue2())
                .append("value3", pet.getValue3())
                .append("value4", pet.getValue4())
                .append("ultimateAbility", pet.getUltimateAbility());
    }
}
