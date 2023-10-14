package rpg.rpgcore.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.commands.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;

public class FlyCommand extends CommandAPI {

    private final RPGCORE rpgcore;

    public FlyCommand(final RPGCORE rpgcore) {
        super("fly");
        this.setRankLevel(RankType.MOD);
        this.setRestrictedForPlayer(true);
        this.rpgcore = rpgcore;
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player p = (Player) sender;
        if (args.length == 0) {
            this.fly(p);
            return;
        }

        if (args.length == 1) {
            if (!rpgcore.getUserManager().isUserName(args[0])) {
                sender.sendMessage(Utils.NIEMATAKIEGOGRACZA);
                return;
            }
            final Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(Utils.offline(args[0]));
                return;
            }
            this.fly(target);
            p.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie zmieniles tryb latania dla gracza &6" + target.getName()));
        }
    }

    private void fly(Player p) {
        if (!(p.isFlying())) {
            p.setAllowFlight(true);
            p.setFlying(true);
            p.sendMessage(Utils.format(Utils.SERVERNAME + "&aWlaczono &7fly"));
        } else {
            p.setAllowFlight(false);
            p.setFlying(false);
            p.sendMessage(Utils.format(Utils.SERVERNAME + "&cWylaczono &7fly"));
        }
    }
}
