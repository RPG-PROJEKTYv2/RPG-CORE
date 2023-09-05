package rpg.rpgcore.dungeons.maps.tajemniczePiaski.objects;

import lombok.Getter;
import lombok.Setter;
import me.filoghost.holographicdisplays.api.hologram.Hologram;
import me.filoghost.holographicdisplays.api.hologram.VisibilitySettings;
import me.filoghost.holographicdisplays.api.hologram.line.TextHologramLine;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.BlockStepAbstract;
import net.minecraft.server.v1_8_R3.IBlockData;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
public class RdzenPiaszczystychWydm {
    private Hologram hologram;
    private List<Location> locations;
    private Map<Location, rpg.rpgcore.dungeons.maps.tajemniczePiaski.objects.Block> blocks;
    private int hp, maxHp;
    private boolean destroyed;

    private final List<String> defaultRdzenHolo = Arrays.asList(
            "&6Rdzen Piastycznych Wydm",
            "&7Zdrowie: &a30&7/&a30"
    );

    public RdzenPiaszczystychWydm(final Location hologramLocation, int x, int y, int z, int x1, int y1, int z1) {
        RPGCORE.getHolographicDisplaysAPI().getHolograms().forEach(hologram -> {
            if (hologram.getPosition().toLocation().equals(hologramLocation)) {
                this.hologram = hologram;
            }
        });
        this.hp = 30;
        this.maxHp = 30;
        this.destroyed = false;
        this.locations = new ArrayList<>();
        this.blocks = new HashMap<>();

        if (x > x1) {
            final int temp = x;
            x = x1;
            x1 = temp;
        }
        if (y > y1) {
            final int temp = y;
            y = y1;
            y1 = temp;
        }
        if (z > z1) {
            final int temp = z;
            z = z1;
            z1 = temp;
        }

        for (int i = y; i < y1; i++) {
            for (int j = x; j < x1; j++)
                for (int k = z; k < z1; k++) {
                    final Location loc = new Location(hologramLocation.getWorld(), j, i, k);
                    if (loc.getBlock() == null || loc.getBlock().getType().equals(Material.AIR) || loc.getBlock().getType().equals(Material.SAND))
                        continue;
                    if (loc.getBlock().getType().equals(Material.STEP)) {
                        IBlockData blockData = ((CraftWorld) hologramLocation.getWorld()).getHandle().getType(new BlockPosition(j, i, k));
                    }
                    this.locations.add(loc);
                    this.blocks.put(loc, new rpg.rpgcore.dungeons.maps.tajemniczePiaski.objects.Block(loc.getBlock()));
                }
        }
    }

    public RdzenPiaszczystychWydm(final Document document) {
        RPGCORE.getHolographicDisplaysAPI().getHolograms().forEach(hologram -> {
            if (hologram.getPosition().toLocation().equals(Utils.locationFromString(document.getString("hologramLocation")))) {
                this.hologram = hologram;
            }
        });
        this.maxHp = document.getInteger("maxHp");
        this.hp = this.maxHp;
        this.destroyed = false;
        this.locations = document.getList("locations", String.class).stream().map(Utils::locationFromString).collect(Collectors.toList());
        this.blocks = new HashMap<>();
        final Document blocks = document.get("blocks", Document.class);

        for (final String str : blocks.keySet()) {
            if (str.equals("_id")) continue;
            final Document doc = blocks.get(str, Document.class);
            final Location loc = new Location(Bukkit.getWorld(doc.getString("world")), doc.getInteger("x"), doc.getInteger("y"), doc.getInteger("z"));
            final Block block = loc.getBlock();
            block.setTypeIdAndData(Material.valueOf(doc.getString("material")).getId(), Byte.parseByte(doc.getInteger("data").toString()), false);
            if (block.getType() == Material.STEP) {
                final IBlockData blockData = ((CraftWorld) block.getWorld()).getHandle().getType(new BlockPosition(block.getX(), block.getY(), block.getZ()));
                blockData.set(BlockStepAbstract.HALF, BlockStepAbstract.EnumSlabHalf.valueOf(doc.getString("half").toUpperCase()));
            }
            this.blocks.put(loc, new rpg.rpgcore.dungeons.maps.tajemniczePiaski.objects.Block(block));
        }
    }

    public void damage(final Player player) {
        this.hp--;
        ((TextHologramLine) this.hologram.getLines().get(1)).setText(Utils.format("&7Zdrowie: " + this.getPrefix() + this.hp + "&7/&a" + this.maxHp));
        if (this.hp <= 0) {
            this.destroyed = true;
            this.hologram.getVisibilitySettings().setIndividualVisibility(player, VisibilitySettings.Visibility.HIDDEN);
            this.locations.forEach(location -> location.getBlock().setType(Material.AIR));
        }
    }

    public void respawn() {
        this.hp = this.maxHp;
        this.destroyed = false;
        this.hologram.getVisibilitySettings().clearIndividualVisibilities();
        this.hologram.getLines().clear();
        this.defaultRdzenHolo.forEach(line -> this.hologram.getLines().appendText(Utils.format(line)));
        this.blocks.forEach((location, block) -> {
            location.getBlock().setTypeIdAndData(block.getMaterial().getId(), block.getData(), false);
            if (block.getMaterial() == Material.STEP) {
                final IBlockData blockData = ((CraftWorld) location.getWorld()).getHandle().getType(new BlockPosition(location.getX(), location.getY(), location.getZ()));
                blockData.set(BlockStepAbstract.HALF, block.getHalf());
            }
        });
    }

    private String getPrefix() {
        if (this.hp <= 10) return "&c";
        if (this.hp <= 20) return "&e";
        return "&a";
    }

    public Document toDocument(final int id) {
        final Document toReturn = new Document("_id", id);
        toReturn.append("hologramLocation", Utils.locatinoToString(this.hologram.getPosition().toLocation()));
        toReturn.append("maxHp", this.maxHp);
        toReturn.append("locations", this.locations.stream().map(Utils::locatinoToString).collect(Collectors.toList()));
        final Document blocks = new Document("_id", "blocks");
        int i = 0;
        for (final rpg.rpgcore.dungeons.maps.tajemniczePiaski.objects.Block block : this.blocks.values()) {
            blocks.append("block-" + i, block.toDocument(i));
            i++;
        }
        toReturn.append("blocks", blocks);

        return toReturn;
    }
}
