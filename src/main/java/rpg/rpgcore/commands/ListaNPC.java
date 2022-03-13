package rpg.rpgcore.commands;


import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import rpg.rpgcore.RPGCORE;



public class ListaNPC implements CommandExecutor {

    private final RPGCORE rpgcore;

    public ListaNPC(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {




        return false;
    }
}
