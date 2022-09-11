package rpg.rpgcore.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestAnimationCommand extends CommandAPI {


    public TestAnimationCommand() {
        super("testanimation");
        this.setRankLevel(RankType.DEV);
        this.setRestrictedForPlayer(true);
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;
        if (args.length == 0) {
            for (Entity e : Bukkit.getWorld("kopalnia").getEntities()) {
                if (e.getName().equals("TestCommand")) {
                    e.setVelocity(new Vector(0, 0, 1));
                    return;
                }
            }
        }
    }
}
