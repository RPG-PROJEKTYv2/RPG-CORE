package rpg.rpgcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.utils.GlobalItems;

public class Test implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        final Player player = (Player) commandSender;

        player.getInventory().addItem(GlobalItems.SAKWA());

        return false;
    }
}
