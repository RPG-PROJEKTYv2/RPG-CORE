package rpg.rpgcore.commands.player.topki;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.Arrays;

public class TopkiCommand extends CommandAPI {
    private final RPGCORE rpgcore;
    public TopkiCommand(RPGCORE rpgcore) {
        super("topki");
        this.setAliases(Arrays.asList("top"));
        this.setRestrictedForPlayer(true);
        this.rpgcore = rpgcore;
    }

    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;
        if (args.length != 0) {
            player.sendMessage(Utils.poprawneUzycie("topki"));
            return;
        }
        this.rpgcore.getTopkiManager().openTopkaGui(player);
    }

}
