package rpg.rpgcore.dungeons.maps.tajemniczePiaski.objects;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.BlockStepAbstract;
import org.bson.Document;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;

@Getter
@Setter
public class Block {
    private final Material material;
    private final byte data;
    private BlockStepAbstract.EnumSlabHalf half;
    private final World world;
    private final int x,y,z;

    public Block(final org.bukkit.block.Block block) {
        this.material = block.getType();
        this.world = block.getWorld();
        this.x = block.getX();
        this.y = block.getY();
        this.z = block.getZ();
        this.data = block.getData();
        if (material == Material.STEP) {
            this.half = ((CraftWorld) block.getWorld()).getHandle().getType(new BlockPosition(block.getX(), block.getY(), block.getZ())).get(BlockStepAbstract.HALF);
        } else this.half = null;
    }

    public Document toDocument(int id) {
        return new Document("id", id)
                .append("material", material.toString())
                .append("data", data)
                .append("half", half == null ? "brak" : half.toString())
                .append("world", world.getName())
                .append("x", x)
                .append("y", y)
                .append("z", z);
    }
}
