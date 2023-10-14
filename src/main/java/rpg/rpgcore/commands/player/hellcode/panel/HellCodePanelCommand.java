package rpg.rpgcore.commands.player.hellcode.panel;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.commands.api.CommandAPI;
import rpg.rpgcore.utils.Utils;

public class HellCodePanelCommand extends CommandAPI {

    private final RPGCORE rpgcore;

    public HellCodePanelCommand(final RPGCORE rpgcore) {
        super("hcpanel");
        this.rpgcore = rpgcore;
        this.setRestrictedForPlayer(true);
    }

    @Override
    public void executeCommand(final CommandSender sender, final String[] args) {
        final Player player = (Player) sender;
        if (args.length != 0) {
            player.sendMessage(Utils.poprawneUzycie("hcpanel"));
            return;
        }

        rpgcore.getChatManager().openHellcodePanel(player);
        player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie otworzono panel HellCode'u!"));
    }
}
