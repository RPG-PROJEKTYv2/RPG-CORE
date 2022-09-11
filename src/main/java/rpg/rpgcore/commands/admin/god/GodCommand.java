package rpg.rpgcore.commands.admin.god;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.Arrays;

public class GodCommand extends CommandAPI {

    private final RPGCORE rpgcore;

    public GodCommand(RPGCORE rpgcore) {
        super("god");
        this.setAliases(Arrays.asList("g"));
        this.setRankLevel(RankType.ADMIN);
        this.setRestrictedForPlayer(true);
        this.rpgcore = rpgcore;
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player p = (Player) sender;
        if (args.length == 0) {
            rpgcore.getGodManager().changeStatusGod(p);
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
            rpgcore.getGodManager().changeStatusGod(target);
        }
    }
}
