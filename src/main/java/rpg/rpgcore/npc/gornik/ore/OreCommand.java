package rpg.rpgcore.npc.gornik.ore;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.npc.gornik.enums.GornikOres;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
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
                Block block = player.getTargetBlock((Set<Material>) null, 100);
                if (block == null || block.getType() == Material.AIR) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&cMusisz patrzec na rude!"));
                    return;
                }
                if (GornikOres.getOre(block.getType()) == null) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie ma takiej rudy!"));
                    return;
                }
                final int id = Integer.parseInt(args[1]);
                final int hp = GornikOres.getOre(block.getType()).getMaxHp();
                if (RPGCORE.getInstance().getOreManager().isOre(block.getLocation())) {
                    player.sendMessage(Utils.poprawneUzycie("/ore <list/add/remove> [id]"));
                    return;
                }
                final Ore ore = new Ore(id, block.getType(), block.getLocation(), hp, hp);
                RPGCORE.getInstance().getOreManager().add(ore.getLocation(), ore);
                RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().addDataOreLocation(ore));
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie dodano rude o id &6" + id + " &amateriale &6" + block.getType().name() + "&a i lokalizacji x:&6" + block.getLocation().getBlockX() + " &ay:&6" + block.getLocation().getBlockY() + " &az:&6" + block.getLocation().getBlockZ()));
            }
        }
    }

}
