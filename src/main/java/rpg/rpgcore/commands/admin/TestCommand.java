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
        RPGCORE.getInstance().getZamekNieskonczonosciManager().endDungeon(RPGCORE.getInstance().getPartyManager().find(player.getUniqueId()));
    }
}
