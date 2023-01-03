package rpg.rpgcore.commands.player.craftingi;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.Arrays;

public class CraftingiCommand extends CommandAPI {
    public CraftingiCommand() {
        super("craftingi");
        this.setAliases(Arrays.asList("craft", "crafting"));
        this.setRestrictedForPlayer(true);
    }

    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;
        if (args.length != 0) {
            player.sendMessage(Utils.poprawneUzycie("craftingi"));
            return;
        }
        RPGCORE.getInstance().getCraftingiManager().openCraftingiGUI(player);
        return;
    }
}
