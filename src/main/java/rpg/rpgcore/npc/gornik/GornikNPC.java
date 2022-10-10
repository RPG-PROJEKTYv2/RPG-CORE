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

        player.teleport(new Location(Bukkit.getWorld("Gornik"), -49.5, 9, 29.5, 180, 0));
        player.sendMessage(Utils.format("&6&lGornik &8&l>> &7Witaj w mojej kopalni. Mam nadzieje, ze zostaniesz tu na dluzej."));

        //TODO Ogarnac ta funkcje, bo moze duzo lagowac (Moze async???), ewentualnie po prostu zrobic hardcoded rudy i tyle
        //rollOres();
    }

    public void openGornikGUI(final Player player) {
        final UUID uuid = player.getUniqueId();
        final GornikUser user = this.find(uuid).getGornikUser();
        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&6&lGornik"));

        for (int i = 0; i < 27; i++) {
            gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName(" ").toItemStack());
        }

        gui.setItem(11, new ItemBuilder(Material.BOOK_AND_QUILL).setName("&4&lKampania Gornika").setLore(Arrays.asList("&f&lBonusy", "&7Srednia Odpornosc: &c" + user.getSredniaOdpornosc() + "%",
                "&7Przeszycie Bloku Ciosu: &c" + user.getPrzeszycieBloku() + "%", "&7Blok Ciosu: &c" + user.getBlokCiosu() + "%")).addGlowing().toItemStack().clone());
        gui.setItem(13, new ItemBuilder(Material.ANVIL).setName("&a&lUlepszanie").toItemStack().clone()); //TODO Dodac sprawdzanie dalszego dt do ulepszania gemow (nie wiem czy to od tego?)
        gui.setItem(15, new ItemBuilder(Material.WORKBENCH).setName("&6&lCraftingi").toItemStack());

        player.openInventory(gui);
    }

    public void openKampania(final Player player) {
        final UUID uuid = player.getUniqueId();
        final GornikUser user = this.find(uuid).getGornikUser();
        final Inventory gui = Bukkit.createInventory(null, 54, Utils.format("&6&lKampania Gornika"));

        for (int i = 0; i < 54; i++) {
            if (!((i > 9 && i < 17) || (i > 18 && i < 26) || (i > 27 && i < 35) || (i > 36 && i < 44))) {
                gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName(" ").toItemStack());
            }
        }


        for (int i = 1; i < 29; i++) {
            if (i < user.getMission()) {
                gui.setItem(gui.firstEmpty(), new ItemBuilder(Material.BOOK).setName("&c&lMisja " + i).setLore(Arrays.asList("&a&lUkonczone!")).addGlowing().toItemStack().clone());
            } else if (i == user.getMission()) {
                gui.setItem(gui.firstEmpty(), new ItemBuilder(Material.BOOK).setName("&c&lMisja " + i).setLore(GornikMissions.getMission(i).getLore()).toItemStack().clone());
            } else {
                gui.setItem(gui.firstEmpty(), new ItemBuilder(Material.BARRIER).setName("&c&lMisja " + i).setLore(Arrays.asList("&c&lZeby odblkowac, ukoncz poprzednie zadanie")).toItemStack().clone());
            }
        }


        player.openInventory(gui);
    }


    public void rollOres() {
        List<Chunk> chunkList = new ArrayList<>();
        chunkList.add(Bukkit.getWorld("Gornik").getChunkAt(5, -1));
        chunkList.add(Bukkit.getWorld("Gornik").getChunkAt(4, -1));
        chunkList.add(Bukkit.getWorld("Gornik").getChunkAt(3, -1));
        chunkList.add(Bukkit.getWorld("Gornik").getChunkAt(5, 0));
        chunkList.add(Bukkit.getWorld("Gornik").getChunkAt(4, 0));
        chunkList.add(Bukkit.getWorld("Gornik").getChunkAt(3, 0));
        List<Block> blockList = new ArrayList<>();

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

}
