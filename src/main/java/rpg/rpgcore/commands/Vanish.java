package rpg.rpgcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

public class Vanish implements CommandExecutor {

    private final RPGCORE rpgcore;

    public Vanish(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.NIEGRACZ);
            return false;
        }
        final Player player = (Player) sender;

        if (!(player.hasPermission("rpg.vanish"))){
            player.sendMessage(Utils.permisje("rpg.vanish"));
            return false;
        }

        if (args.length == 0) {
            if (rpgcore.getVanishManager().containsPlayer(player.getUniqueId())) {
                rpgcore.getVanishManager().getVanishList().remove(player.getUniqueId());
                rpgcore.getVanishManager().revealPlayer(player);
                return false;
            }
            rpgcore.getVanishManager().getVanishList().add(player.getUniqueId());
            rpgcore.getVanishManager().hidePlayer(player);
            return false;
        }
        if (args.length == 1){

            Player target = Bukkit.getPlayer(args[0]);


            if (rpgcore.getVanishManager().containsPlayer(target.getUniqueId())){
                rpgcore.getVanishManager().getVanishList().remove(target.getUniqueId());
                rpgcore.getVanishManager().revealPlayer(target);
                return false;
            }
            rpgcore.getVanishManager().getVanishList().add(target.getUniqueId());
            rpgcore.getVanishManager().hidePlayer(target);
        }

        return false;
    }
}
