package rpg.rpgcore.commands;

import net.citizensnpcs.Citizens;
import net.citizensnpcs.api.CitizensAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import rpg.rpgcore.RPGCORE;

import java.io.File;
import java.util.Arrays;
import java.util.Set;

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
