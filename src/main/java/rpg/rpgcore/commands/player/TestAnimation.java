package rpg.rpgcore.commands.player;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import rpg.rpgcore.RPGCORE;

import java.util.ArrayList;
import java.util.List;

public class TestAnimation implements CommandExecutor {

    private final RPGCORE rpgcore;
    private final List<ItemStack> listaItemow = new ArrayList<>();
    int task = 0;

    public TestAnimation(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return false;
        }

        final Player player = (Player) sender;


        if (args.length == 0) {
            for (Entity e : Bukkit.getWorld("kopalnia").getEntities()) {
                if (e.getName().equals("Test")) {
                    e.setVelocity(new Vector(0, 0, 1));
                    return true;
                }
            }
        }

        return false;
    }
}
