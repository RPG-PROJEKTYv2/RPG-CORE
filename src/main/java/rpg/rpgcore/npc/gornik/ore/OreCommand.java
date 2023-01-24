package rpg.rpgcore.npc.gornik.ore;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.npc.gornik.enums.GornikOres;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

public class OreCommand extends CommandAPI {
    public OreCommand() {
        super("ore");
        this.setRankLevel(RankType.HA);
        this.setRestrictedForPlayer(true);
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;
        if (args.length == 0) {
            player.sendMessage(Utils.poprawneUzycie("/ore <list/remove> [id]"));
            return;
        }

        if (args.length > 2) {
            player.sendMessage(Utils.poprawneUzycie("/ore <list/add/remove> [id]"));
            return;
        }

        if (args[0].equalsIgnoreCase("add")) {
            final Location from = new Location(player.getWorld(), player.getLocation().getX() + 30, player.getLocation().getY() + 10, player.getLocation().getZ() + 30);
            int id = RPGCORE.getInstance().getOreManager().getHighestId() + 1;
            for (final Block block : this.getRegionBlocks(player.getWorld(), player.getLocation(), from)) {
                if (GornikOres.getOre(block.getType()) == null) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie ma takiej rudy!"));
                    return;
                }
                final int hp = Objects.requireNonNull(GornikOres.getOre(block.getType())).getMaxHp();
                if (RPGCORE.getInstance().getOreManager().isOre(block.getLocation())) {
                    player.sendMessage(Utils.poprawneUzycie("ore <list/add/remove> [id]"));
                    return;
                }
                final Ore ore = new Ore(id, block.getType(), block.getLocation(), hp, hp);
                RPGCORE.getInstance().getOreManager().add(ore.getLocation(), ore);
                RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().addDataOreLocation(ore));
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie dodano rude o id &6" + id + " &amateriale &6" + block.getType().name() + "&a i lokalizacji x:&6" + block.getLocation().getBlockX() + " &ay:&6" + block.getLocation().getBlockY() + " &az:&6" + block.getLocation().getBlockZ()));
                id ++;
            }
        }
        if (args[0].equalsIgnoreCase("list")) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&aLista rud:"));
            final Map<Integer, Ore> oreMap = new HashMap<>();
            for (Ore ore : RPGCORE.getInstance().getOreManager().getOreLocations()) {
                oreMap.put(ore.getId(), ore);
            }

            Stream<Map.Entry<Integer, Ore>> sortedDesc = oreMap.entrySet().stream().sorted(Map.Entry.comparingByKey());
            sortedDesc.forEach(e -> player.sendMessage(Utils.format("&8ID: &6" + e.getKey() + " &8Material: &6" + e.getValue().getOreMaterial().name() + " &8X:&6" + e.getValue().getLocation().getBlockX() + " &8Y:&6" + e.getValue().getLocation().getBlockY() + " &8Z:&6" + e.getValue().getLocation().getBlockZ())));

            return;
        }
        if (args[0].equalsIgnoreCase("remove")) {
            if (args[1] == null) {
                player.sendMessage(Utils.poprawneUzycie("ore remove <id>"));
                return;
            }
            int id;
            try {
                id = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&cMusisz podac liczbe jako id!"));
                return;
            }
            if (RPGCORE.getInstance().getOreManager().findById(id) == null) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie ma takiej rudy!"));
                return;
            }
            RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> {
                RPGCORE.getInstance().getOreManager().remove(RPGCORE.getInstance().getOreManager().findById(id));
                RPGCORE.getInstance().getMongoManager().removeDataOreLocation(RPGCORE.getInstance().getOreManager().findById(id));
            });
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie usunieto rude o id &6" + id));
        }
    }

    private List<Block> getRegionBlocks(World world, Location loc1, Location loc2) {
        List<Block> blocks = new ArrayList<Block>();
        for(double x = loc1.getX(); x <= loc2.getX(); x++) {
            for(double y = loc1.getY(); y <= loc2.getY(); y++) {
                for(double z = loc1.getZ(); z <= loc2.getZ(); z++) {
                    Location loc = new Location(world, x, y, z);
                    if (!loc.getBlock().getType().toString().contains("_ORE")) continue;
                    blocks.add(loc.getBlock());
                }
            }
        }
        return blocks;
    }

}
