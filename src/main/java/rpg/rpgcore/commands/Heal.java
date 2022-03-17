package rpg.rpgcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

public class Heal implements CommandExecutor {

    private final RPGCORE rpgcore;

    public Heal(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    public boolean onCommand (final CommandSender sender, final Command cmd, final String label, final String[] args){

        if (!(sender instanceof Player)){
            sender.sendMessage(Utils.NIEGRACZ);
            return false;
        }

        final Player player = (Player) sender;

        if (args.length == 0){
            if (player.getHealth() == player.getMaxHealth() && player.getFoodLevel() == 20){
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Jestes juz uleczony"));
                return false;
            }
            player.setHealth(player.getMaxHealth());
            player.setFoodLevel(20);
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie uleczono"));
            return false;
        }

        if (args.length == 1){

            final UUID uuidPlayerToHeal = rpgcore.getPlayerManager().getPlayerUUID(args[0]);


            if (uuidPlayerToHeal == null) {
                player.sendMessage(Utils.LVLPREFIX + Utils.NIEMATAKIEGOGRACZA);
                return false;
            }

            final Player target = Bukkit.getPlayer(uuidPlayerToHeal);

            if (!(target.isOnline())){
                player.sendMessage(Utils.offline(target.getName()));
                return false;
            }

            if (target.getHealth() == target.getMaxHealth() && target.getFoodLevel() == 20){
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&c" + target.getName() + " &7jest juz uleczony"));
                return false;
            }
            target.setHealth(target.getMaxHealth());
            target.setFoodLevel(20);
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie uleczono &6" + target.getName()));
            target.sendMessage(Utils.format(Utils.SERVERNAME + "&aZostales uleczony przez administratora &6" + player.getName()));
            return false;
        }

        player.sendMessage(Utils.poprawneUzycie("heal [gracz]"));
        return false;
    }
}
