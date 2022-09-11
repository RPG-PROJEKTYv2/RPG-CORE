package rpg.rpgcore.spawn;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;

public class SpawnCommand extends CommandAPI {

    private final RPGCORE rpgcore;

    public SpawnCommand(RPGCORE rpgcore) {
        super("spawn");
        this.setRankLevel(RankType.GRACZ);
        this.setRestrictedForPlayer(true);
        this.rpgcore = rpgcore;
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;
        if (args.length == 0) {
            player.teleport(rpgcore.getSpawnManager().getSpawn());
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPrzeteleportowano na spawna!"));
            return;
        }
        if (rpgcore.getUserManager().find(player.getUniqueId()).getRankUser().getRankType() == RankType.ADMIN || rpgcore.getUserManager().find(player.getUniqueId()).getRankUser().getRankType() == RankType.DEV) {
            if (args[0] == null) {
                return;
            }

            if (args[0].equalsIgnoreCase("set")) {
                final double x = player.getLocation().getX();
                final double y = player.getLocation().getY();
                final double z = player.getLocation().getZ();
                final World w = player.getLocation().getWorld();

                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().setSpawn(player.getLocation()));
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie ustawiono nowego spawna! Na kordach: " + " &7x: " + Utils.df.format(x) + " &7y: " + Utils.df.format(y) + " &7z: " + Utils.df.format(z) + " &7w swiecie " + w.getName()));
                return;

            }

            final Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie ma takiego gracza!"));
                return;
            }
            this.rpgcore.getTeleportManager().setBeforeTeleportLocation(target.getUniqueId(), target.getLocation());
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPrzeteleportowano gracza &6" + target.getName() + " &ana spawna!"));
            target.sendMessage(Utils.format(Utils.SERVERNAME + "&aZostales przeteleportowany na spawna!"));
            target.teleport(rpgcore.getSpawnManager().getSpawn());
        }

    }
}
