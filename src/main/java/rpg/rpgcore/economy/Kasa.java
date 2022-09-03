package rpg.rpgcore.economy;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

public class Kasa implements CommandExecutor {

    private final RPGCORE rpgcore;

    public Kasa(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.NIEGRACZ);
            return false;
        }

        final Player player = (Player) sender;

        if (!(player.hasPermission("rpg.kasa"))) {
            player.sendMessage(Utils.permisje("rpg.kasa"));
            return false;
        }

        if (args.length == 0) {
            player.sendMessage(Utils.format("&2Twoj aktualny stan konta wynosi: &6&o" + Utils.spaceNumber(String.valueOf(Utils.kasaFormat.format(rpgcore.getUserManager().find(player.getUniqueId()).getKasa()))) + "&2$"));
            return false;
        }

        if (args.length == 1) {
            if (args[0].equals("daj")) {
                if (!player.hasPermission("admin.rpg.dajkase")) {
                    player.sendMessage(Utils.permisje("admin.rpg.dajkase"));
                    return false;
                }
                rpgcore.getUserManager().find(player.getUniqueId()).setKasa(100000000000.0);
                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataUser(player.getUniqueId(), rpgcore.getUserManager().find(player.getUniqueId())));
                return false;
            }
        }

        player.sendMessage(Utils.poprawneUzycie("kasa"));
    return false;
    }
}
