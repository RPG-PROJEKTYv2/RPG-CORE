package rpg.rpgcore.old.gornik.ore;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
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
        /*final Player player = (Player) sender;
        if (args.length == 0) {
            player.sendMessage(Utils.poprawneUzycie("/ore <list/remove> [id]"));
            return;
        }

        if (args.length > 2) {
            player.sendMessage(Utils.poprawneUzycie("/ore <list/add/remove> [id]"));
            return;
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
                RPGCORE.getInstance().getMongoManager().removeDataOreLocation(RPGCORE.getInstance().getOreManager().findById(id));
                RPGCORE.getInstance().getOreManager().remove(RPGCORE.getInstance().getOreManager().findById(id));
            });
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie usunieto rude o id &6" + id));
        }*/
    }

}
