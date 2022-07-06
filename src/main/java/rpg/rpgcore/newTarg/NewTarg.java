package rpg.rpgcore.newTarg;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;

public class NewTarg implements CommandExecutor {

    private final RPGCORE rpgcore;

    public NewTarg(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        Player player = (Player) commandSender;

        rpgcore.getNewTargManager().openNewTargInventory(player, 1, 1,1);

        return false;
    }
}
