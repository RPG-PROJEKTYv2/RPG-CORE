package rpg.rpgcore.commands.admin.vanish;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

public class VanishCommand extends CommandAPI {

    private final RPGCORE rpgcore;

    public VanishCommand(RPGCORE rpgcore) {
        super("vanish");
        this.setAliases(Arrays.asList("v", "van", "vs"));
        this.setRankLevel(RankType.GM);
        this.setRestrictedForPlayer(true);
        this.rpgcore = rpgcore;
    }


    @Override
    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player p = (Player) sender;
        final UUID uuid = p.getUniqueId();

        if (args.length == 0) {

            if (!(rpgcore.getVanishManager().isVisible(uuid))) {
                rpgcore.getVanishManager().hidePlayer(p);
                return;
            }
            rpgcore.getVanishManager().showPlayer(p);
            return;
        }
        if (args.length == 1) {

            if (!rpgcore.getUserManager().isUserName(args[0])) {
                sender.sendMessage(Utils.NIEMATAKIEGOGRACZA);
                return;
            }

            final Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                p.sendMessage(Utils.offline(args[0]));
                return;
            }

            final UUID targetUUID = target.getUniqueId();

            if (!(rpgcore.getVanishManager().isVisible(targetUUID))) {
                rpgcore.getVanishManager().hidePlayer(p, target);
                return;
            }
            rpgcore.getVanishManager().showPlayer(p, target);
        }
    }
}
