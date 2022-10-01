package rpg.rpgcore.commands.admin;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.entities.CustomZombie.CustomZombie;
import rpg.rpgcore.entities.EntityTypes;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.globalitems.GlobalItem;

import java.io.IOException;

public class TestCommand extends CommandAPI {

    public TestCommand() {
        super("test");
        this.setRankLevel(RankType.DEV);
        this.setRestrictedForPlayer(true);
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;
        player.getInventory().addItem(GlobalItem.getItem("I16", 1));
        EntityTypes.spawnEntity(new CustomZombie(player.getWorld()), player.getUniqueId(), player.getLocation(), "&c&lCustom Zombie");
    }
}
