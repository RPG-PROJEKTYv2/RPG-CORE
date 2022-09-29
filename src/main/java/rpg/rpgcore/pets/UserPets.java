package rpg.rpgcore.pets;

import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.UUID;

@Getter
@Setter
public class UserPets {
    private final UUID uuid;
    private ItemStack[] pety;
    private String inventoryName;

    public UserPets(UUID uuid) {
        this.uuid = uuid;
        this.pety = new ItemStack[]{};
    }

    public UserPets(Document document) {
        this.uuid = UUID.fromString(document.getString("_id"));
        try {
            this.pety = Utils.itemStackArrayFromBase64(document.getString("pety"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void removePet(final ItemStack pet) {
        if (this.pety.length == 0) return;
        if (this.pety.length -1 == 0) {
            this.pety = new ItemStack[]{};
            return;
        }
        final ItemStack[] newPety = new ItemStack[this.pety.length - 1];
        int i = 0;
        for (final ItemStack itemStack : this.pety) {
            if (itemStack == null || itemStack.getType().equals(Material.AIR) || !itemStack.hasItemMeta() || !itemStack.getItemMeta().hasLore()) {
                continue;
            }
            if (itemStack.isSimilar(pet)) {
                continue;
            }
            newPety[i] = itemStack;
            ++i;
        }
        this.pety = newPety;
    }

    public void addPet(final ItemStack pet) {
        final ItemStack[] newPety = new ItemStack[this.pety.length + 1];
        int i = 0;
        for (final ItemStack itemStack : this.pety) {
            newPety[i] = itemStack;
            ++i;
        }
        newPety[i] = pet;
        this.pety = newPety;
    }

    public Document toDocument() {
        return new Document("_id", uuid.toString())
                .append("pety", Utils.itemStackArrayToBase64(pety));
    }
}
