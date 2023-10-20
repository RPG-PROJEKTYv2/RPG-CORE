package rpg.rpgcore.npc.alchemik.objects;

import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

@Getter
@Setter
public class AlchemikUser implements Cloneable {
    private final UUID uuid;
    private int lvl;
    private int progress;
    private ItemStack potegi, obrony, potworow, ludzi;

    public AlchemikUser(final UUID uuid) {
        this.uuid = uuid;
        this.lvl = 0;
        this.progress = 0;
        this.potegi = new ItemStack(Material.AIR);
        this.obrony = new ItemStack(Material.AIR);
        this.potworow = new ItemStack(Material.AIR);
        this.ludzi = new ItemStack(Material.AIR);
    }

    public AlchemikUser(final Document document) {
        this.uuid = UUID.fromString(document.getString("_id"));
        this.lvl = document.getInteger("lvl");
        this.progress = document.getInteger("progress");
        this.potegi = Utils.deserializeItem(document.getString("potegi"));
        this.obrony = Utils.deserializeItem(document.getString("obrony"));
        this.potworow = Utils.deserializeItem(document.getString("potworow"));
        this.ludzi = Utils.deserializeItem(document.getString("ludzi"));
    }

    public void incrementProgress(final Player player) {
        this.progress++;
        player.sendMessage(Utils.format("&5&lAlchemik &8>> &dPomyslnie dodano &51 &dpunkt doswiadczenia!"));
        if (this.progress == 10) {
            this.progress = 0;
            this.lvl++;
            RPGCORE.getInstance().getNmsManager().sendTitleAndSubTitle(player, "&5&lLVL UP!", "&dAwansowales na &5" + this.lvl + " &dalchemicki poziom!", 20, 40);
        }
    }

    public Document toDocument() {
        return new Document("_id", this.uuid.toString())
                .append("lvl", this.lvl)
                .append("progress", this.progress)
                .append("potegi", Utils.serializeItem(this.potegi))
                .append("obrony", Utils.serializeItem(this.obrony))
                .append("potworow", Utils.serializeItem(this.potworow))
                .append("ludzi", Utils.serializeItem(this.ludzi));
    }

    @Override
    public AlchemikUser clone() {
        try {
            return (AlchemikUser) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}

