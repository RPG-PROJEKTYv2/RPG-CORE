package rpg.rpgcore.commands.player.rozpiska;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;

public class RozpiskaCommand extends CommandAPI {

    private RPGCORE rpgcore;

    public RozpiskaCommand(RPGCORE rpgcore) {
        super("rozpiska");
        this.setRankLevel(RankType.GRACZ);
        this.setRestrictedForPlayer(true);
        this.rpgcore = rpgcore;
    }
    @Override
    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;
        if (args.length > 0) {
            player.sendMessage(Utils.poprawneUzycie("rozpiska"));
            return;
        }
        rpgcore.getRozpiskaManager().openROZPISKAGUI(player);
        return;
    }
}
