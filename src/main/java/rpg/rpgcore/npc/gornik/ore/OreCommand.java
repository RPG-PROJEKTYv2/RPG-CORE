package rpg.rpgcore.npc.gornik.ore;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class OreCommand extends CommandAPI {
    public OreCommand() {
        super("ore");
        this.setRankLevel(RankType.DEV);
        this.setRestrictedForPlayer(true);
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;
        final Set<Material> materials = new HashSet<>();
        materials.add(Material.COAL_ORE);
        materials.add(Material.IRON_ORE);
        materials.add(Material.GOLD_ORE);
        materials.add(Material.DIAMOND_ORE);
        materials.add(Material.EMERALD_ORE);
        materials.add(Material.REDSTONE_ORE);
        materials.add(Material.LAPIS_ORE);
        if (args.length == 0) {
            player.sendMessage(Utils.poprawneUzycie("/ore <list/add/remove> [id]"));
            return;
        }

        if (args.length > 2) {
            player.sendMessage(Utils.poprawneUzycie("/ore <list/add/remove> [id]"));
            return;
        }

        if (args[0].equalsIgnoreCase("add")) {
            if (args[1] != null) {
                if (player.getTargetBlock(materials, 10) == null) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&cMusisz patrzec na rude!"));
                    return;
                }
                final int id = Integer.parseInt(args[1]);
                final Block block = player.getTargetBlock(materials, 10);
                final int hp = this.getOreHp(block.getType());
                if (RPGCORE.getInstance().getOreManager().isOre(id)) {
                    player.sendMessage(Utils.poprawneUzycie("/ore <list/add/remove> [id]"));
                    return;
                }
                final Ore ore = new Ore(id, block.getType(), block.getLocation(), hp, hp);
                RPGCORE.getInstance().getOreManager().add(ore);
                RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().addDataOreLocation(ore));
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie dodano rude o id &6" + id + " &amateriale &6" + block.getType().name() + "&a i lokalizacji x:&6" + block.getLocation().getBlockX() + " &ay:&6" + block.getLocation().getBlockY() + " &az:&6" + block.getLocation().getBlockZ()));
                return;
            }
        }
    }

    private int getOreHp(final Material material) {
        switch (material) {
            case COAL_ORE:
                return 5;
            case IRON_ORE:
                return 10;
            case GOLD_ORE:
                return 10;
            case DIAMOND_ORE:
                return 15;
            case EMERALD_ORE:
                return 15;
            case REDSTONE_ORE:
                return 20;
            case LAPIS_ORE:
                return 30;
            default:
                return 0;
        }
    }
}
