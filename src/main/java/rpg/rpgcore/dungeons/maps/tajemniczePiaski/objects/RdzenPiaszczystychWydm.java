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
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.dungeons.DungeonStatus;
import rpg.rpgcore.utils.ChanceHelper;
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
    private final List<String> entityId = new ArrayList<>();

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
        this.entityId.clear();
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
        if (RPGCORE.getInstance().getTajemniczePiaskiManager().getDungeonStatus() != DungeonStatus.ETAP_2) return;
        if (!this.entityId.isEmpty()) {
            player.sendMessage(Utils.format("&6Rdzen Piastycznych Wydm &8>> &cNie zabiles jeszcze wszystkich moich slugow!"));
            return;
        }
        if (RPGCORE.getInstance().getCooldownManager().hasPlayerTajemniczePiaskiCooldown(player.getUniqueId())) return;
        RPGCORE.getInstance().getCooldownManager().givePlayerTajemniczePiaskiRdzenCooldown(player.getUniqueId());
        this.hp--;
        ((TextHologramLine) this.hologram.getLines().get(1)).setText(Utils.format("&7Zdrowie: " + this.getPrefix() + this.hp + "&7/&a" + this.maxHp));
        if (ChanceHelper.getChance(15)) {
            for (int i = 0; i < 7; i++) {
                final double x = ChanceHelper.getRandDouble(-0.3, 0.3);
                final double z = ChanceHelper.getRandDouble(-0.3, 0.3);
                final Entity e = RPGCORE.getMythicMobs().getMobManager().spawnMob("TAJEMNICZE-PIASKI-MOB2", this.hologram.getPosition().toLocation().clone().add(0, 0, -1.5).add(x, 0, z)).getEntity().getBukkitEntity();
                this.entityId.add("" + e.getEntityId());
            }
            player.sendMessage(Utils.format("&6Rdzen Piastycznych Wydm &8>> &cBierzcie Go!"));
        }
        if (ChanceHelper.getChance(10)) {
            double pushX = player.getLocation().getDirection().normalize().getX() * -3.5;
            double pushY = player.getLocation().getDirection().normalize().getY() * -6;
            double pushZ = player.getLocation().getDirection().normalize().getZ() * -3.5;
            player.setVelocity(new Vector(pushX, pushY, pushZ));
            player.sendMessage(Utils.format("&6Rdzen Piastycznych Wydm &8>> &7Odejdz!"));
        }
        if (ChanceHelper.getChance(10)) {
            player.setHealth(player.getHealth() - (player.getHealth() / 15));
            player.sendMessage(Utils.format("&6Rdzen Piastycznych Wydm &8>> &cGin!"));
        }
        if (this.hp <= 0) {
            RPGCORE.getInstance().getTajemniczePiaskiManager().incrementCounter();
            this.destroyed = true;
            this.hologram.getVisibilitySettings().setIndividualVisibility(player, VisibilitySettings.Visibility.HIDDEN);
            this.locations.forEach(location -> location.getBlock().setType(Material.AIR));
            player.getWorld().playEffect(player.getLocation(), Effect.EXPLOSION_HUGE, 5);
            player.getWorld().playSound(player.getLocation(), Sound.EXPLODE, 3, 3);
        }
    }

    public void respawn() {
        this.hp = this.maxHp;
        this.destroyed = false;
        this.entityId.clear();
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
