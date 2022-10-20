package rpg.rpgcore.commands.admin;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;

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
        //player.getInventory().addItem(GlobalItem.getItem("I16", 1));
        //EntityTypes.spawnEntity(new CustomZombie(player.getWorld()), player.getUniqueId(), player.getLocation(), "&c&lCustom Zombie");
        RPGCORE.getInstance().getZamekNieskonczonosciManager().startDungeon(RPGCORE.getInstance().getPartyManager().find(player.getUniqueId()));

        RPGCORE.getInstance().getServer().getScheduler().runTaskLater(RPGCORE.getInstance(), () -> {
            RPGCORE.getInstance().getZamekNieskonczonosciManager().endDungeon(RPGCORE.getInstance().getPartyManager().find(player.getUniqueId()));
        }, 300L);
        //RPGCORE.getInstance().getPetyManager().increasePetExp(player, RPGCORE.getInstance().getPetyManager().findActivePet(player.getUniqueId()).getPet().getItem(), 100);
    }
}
