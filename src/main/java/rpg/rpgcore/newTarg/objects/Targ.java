package rpg.rpgcore.newTarg.objects;

import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
public class Targ implements Cloneable {
    private final UUID uuid;
    private final List<ItemStack> itemList;

    public Targ(final UUID uuid) {
        this.uuid = uuid;
        this.itemList = new ArrayList<>();
    }

    public Targ(final Document document) {
        this.uuid = UUID.fromString(document.getString("_id"));
        this.itemList = new ArrayList<>();
        for (final String item : document.getList("itemList", String.class)) {
            this.itemList.add(Utils.deserializeItem(item));
        }
    }

    public void addItem(final ItemStack itemStack) {
        this.itemList.add(itemStack);
    }

    public void removeItem(final ItemStack itemStack) {
        this.itemList.remove(itemStack);
    }

    public void save() {
        RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataTarg(this.uuid, this));
    }

    public Document toDocument() {
        return new Document("_id", this.uuid.toString())
                .append("itemList", this.itemList.stream().map(Utils::serializeItem).collect(Collectors.toList()));
    }

    @Override
    public Targ clone() {
        try {
            return (Targ) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
