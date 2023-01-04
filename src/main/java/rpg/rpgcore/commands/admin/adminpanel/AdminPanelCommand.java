package rpg.rpgcore.commands.admin.adminpanel;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.Arrays;

public class AdminPanelCommand extends CommandAPI {

    private final RPGCORE rpgcore;

    public AdminPanelCommand(RPGCORE rpgcore) {
        super("adminpanel");
        this.setAliases(Arrays.asList("paneladmin"));
        this.setRankLevel(RankType.ADMIN);
        this.setRestrictedForPlayer(true);
        this.rpgcore = rpgcore;
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;

        if (args.length == 0) {
            rpgcore.getAdminPanelManager().openAdminPanelGUI(player);
            return;
        }
        player.sendMessage(Utils.poprawneUzycie("adminpanel"));
    }
}
