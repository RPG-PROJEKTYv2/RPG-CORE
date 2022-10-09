package rpg.rpgcore.npc.gornik;

import com.google.common.collect.ImmutableSet;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.*;

public class GornikNPC {
    private final RPGCORE rpgcore;
    private final Map<UUID, GornikObject> userMap;
    private final Map<Integer, String> missionMap = new HashMap<>(40);

    public GornikNPC(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
        this.userMap = rpgcore.getMongoManager().loadAllGornik();
        this.loadMissions();
    }

    public void loadMissions() {

    }

    public void onClick(final Player player) {
        final UUID uuid = player.getUniqueId();
        if (!this.userMap.containsKey(uuid)) {
            this.userMap.put(uuid, new GornikObject(uuid));
        }

        if (rpgcore.getUserManager().find(uuid).getLvl() < 75) {
            player.sendMessage(Utils.format("&6&lGornik &8&l>> &7Musisz zdobyc jescze troche doswiadczenia aby uzyskac dostep do mojej kopalni."));
            return;
        }

        player.teleport(new Location(Bukkit.getWorld("Gornik"), 0, 4, 0));
        player.sendMessage(Utils.format("&6&lGornik &8&l>> &7Witaj w mojej kopalni. Mam nadzieje, ze zostaniesz tu na dluzej."));



    }

    public void openGornikGUI(final Player player) {
        final UUID uuid = player.getUniqueId();
        final GornikUser user = this.find(uuid).getGornikUser();
        final Inventory gui = Bukkit.createInventory(null, 9, Utils.format("&6&lGornik"));

        gui.setItem(0, new ItemBuilder(Material.BOOK).setName("&cKampania Gornika").addGlowing().toItemStack().clone());

        player.openInventory(gui);
        //TODO Ogarnac ta funkcje, bo moze duzo lagowac (Moze async???), ewentualnie po prostu zrobic hardcoded rudy i tyle
        //rollOres();

    }


    public void rollOres() {
        List<Chunk> chunkList = new ArrayList<>();
        chunkList.add(Bukkit.getWorld("Gornik").getChunkAt(5, -1));
        chunkList.add(Bukkit.getWorld("Gornik").getChunkAt(4, -1));
        chunkList.add(Bukkit.getWorld("Gornik").getChunkAt(3, -1));
        chunkList.add(Bukkit.getWorld("Gornik").getChunkAt(5, 0));
        chunkList.add(Bukkit.getWorld("Gornik").getChunkAt(4, 0));
        chunkList.add(Bukkit.getWorld("Gornik").getChunkAt(3, 0));
        List <Block> blockList = new ArrayList<>();

        for (Chunk chunk : chunkList) {
            final int bx = chunk.getX() << 4;
            final int bz = chunk.getZ() << 4;
            for (int x = bx; x < bx + 16; x++) {
                for (int z = bz; z < bz + 16; z++) {
                    for (int y = 4; y < 13; y++) {
                        if (chunk.getBlock(x, y, z).getType() != Material.AIR && chunk.getBlock(x, y, z).getType() != Material.BEDROCK && chunk.getBlock(x, y, z).getType() != Material.GRASS
                                && chunk.getBlock(x, y, z).getType() != Material.DIRT) {
                            blockList.add(chunk.getBlock(x, y, z));
                        }
                    }
                }
            }
        }
        int changed = 0;
        final Random random = new Random();
        while (changed < 10) {
            final int index = random.nextInt(blockList.size());
            if (blockList.get(index).getRelative(BlockFace.DOWN).getType() == Material.AIR || blockList.get(index).getRelative(BlockFace.UP).getType() == Material.AIR ||
                    blockList.get(index).getRelative(BlockFace.NORTH).getType() == Material.AIR || blockList.get(index).getRelative(BlockFace.SOUTH).getType() == Material.AIR ||
                    blockList.get(index).getRelative(BlockFace.EAST).getType() == Material.AIR || blockList.get(index).getRelative(BlockFace.WEST).getType() == Material.AIR) {
                blockList.get(index).setType(Material.COAL_ORE);
                changed++;
            }
        }
    }

    public void add(GornikObject gornikObject) {
        this.userMap.put(gornikObject.getID(), gornikObject);
    }

    public GornikObject find(final UUID uuid) {
        this.userMap.computeIfAbsent(uuid, k -> new GornikObject(uuid));
        return this.userMap.get(uuid);
    }

    public ImmutableSet<GornikObject> getGornikObject() {
        return ImmutableSet.copyOf(this.userMap.values());
    }

    public boolean isGornikObject(final UUID string) {
        return this.userMap.containsKey(string);
    }
}
