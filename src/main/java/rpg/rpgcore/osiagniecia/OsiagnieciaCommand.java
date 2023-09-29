package rpg.rpgcore.osiagniecia;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.Arrays;

public class OsiagnieciaCommand extends CommandAPI {
    public OsiagnieciaCommand() {
        super("osiagniecia");
        this.setAliases(Arrays.asList("os"));
        this.setRestrictedForPlayer(true);
    }

    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;
        if (args.length != 0) {
            player.sendMessage(Utils.poprawneUzycie("osiagniecia"));
            return;
        }
        if (RPGCORE.getInstance().getUserManager().find(player.getUniqueId()).getLvl() < 55) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Osiagniecia sa dostepne od &655 &7poziomu!"));
            return;
        }
        RPGCORE.getInstance().getOsManager().openOsGUI(player);
    }
}
