package rpg.rpgcore.commands.player;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.GlobalItems.GlobalItem;

public class Test implements CommandExecutor {

    private final RPGCORE rpgcore;
    public Test(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        final Player player = (Player) commandSender;

        player.getInventory().addItem(GlobalItem.getItem("I16", 1));

        return false;
    }
}
