package rpg.rpgcore.pets.objects;

import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class UserPets {
    private final UUID uuid;
    private List<ItemStack> pety;
    private String inventoryName;

    public UserPets(UUID uuid) {
        this.uuid = uuid;
        this.pety = new ArrayList<>();
    }

    public UserPets(Document document) {
        this.uuid = UUID.fromString(document.getString("_id"));
        try {
            this.pety = Arrays.asList(Utils.itemStackArrayFromBase64(document.getString("pety")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void removePet(final ItemStack pet) {
        if (this.pety.isEmpty()) return;
        if (this.pety.size() -1 == 0) {
            this.pety = new ArrayList<>();
            return;
        }
        final List<ItemStack> newPety = new ArrayList<>(this.pety);
        newPety.remove(pet);
        this.pety = newPety;
    }

    public void addPet(final ItemStack pet) {
        final List<ItemStack> newPets = new ArrayList<>(this.pety);
        newPets.add(pet);
        this.pety = newPets;
    }

    public Document toDocument() {
        return new Document("_id", uuid.toString())
                .append("pety", Utils.itemStackArrayToBase64(pety.toArray(new ItemStack[0])));
    }
}
