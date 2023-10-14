package rpg.rpgcore.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.commands.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;

public class HealCommand extends CommandAPI {

    private final RPGCORE rpgcore;

    public HealCommand(final RPGCORE rpgcore) {
        super("heal");
        this.setRankLevel(RankType.ADMIN);
        this.setRestrictedForPlayer(true);
        this.rpgcore = rpgcore;
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player p = (Player) sender;
        if (args.length == 0) {
            p.setHealth(p.getMaxHealth());
            p.setFoodLevel(20);
            p.sendMessage(Utils.format("&aZostales uleczony!"));
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
            target.setHealth(target.getMaxHealth());
            target.setFoodLevel(20);
            target.sendMessage(Utils.format("&aZostales uleczony przez dobrego czleka!"));
            p.sendMessage(Utils.format("&aUleczono gracza " + target.getName()));
        }
    }
}
