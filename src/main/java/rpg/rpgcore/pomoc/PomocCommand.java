package rpg.rpgcore.pomoc;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.expowiska.Map1;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.GlobalItems.GlobalItem;
import rpg.rpgcore.utils.GlobalItems.expowiska.Map1Items;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;


public class PomocCommand extends CommandAPI {

    private final RPGCORE rpgcore;

    public PomocCommand(RPGCORE rpgcore) {
        super("pomoc");
        this.setRankLevel(RankType.GRACZ);
        this.setRestrictedForPlayer(true);
        this.rpgcore = rpgcore;
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;
        if (args.length == 0) {
            player.openInventory(rpgcore.getPomocManager().pomocGUIMAIN());
            player.getInventory().addItem(GlobalItem.getItem("I1", 1));
            player.getInventory().addItem(GlobalItem.getItem("I2", 1));
            player.getInventory().addItem(GlobalItem.getItem("I3", 1));
            player.getInventory().addItem(GlobalItem.getItem("I4", 1));
            player.getInventory().addItem(GlobalItem.getItem("I5", 1));

            player.getInventory().addItem(Map1Items.getItem("I1", 1));
            player.getInventory().addItem(Map1Items.getItem("I2", 1));

            return;
        }
        player.sendMessage(Utils.poprawneUzycie("pomoc"));
    }
}
