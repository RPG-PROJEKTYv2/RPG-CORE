package rpg.rpgcore.commands.admin.drop;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;

public class DropCommand extends CommandAPI {


    public DropCommand() {
        super("drop");
        this.setRestrictedForPlayer(true);
        this.setRankLevel(RankType.ADMIN);
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;
        if (args.length != 0) {
            player.sendMessage(Utils.poprawneUzycie("drop"));
            return;
        }

        if (DropManager.isDropOn(player.getUniqueId())) {
            DropManager.removeDrop(player.getUniqueId());
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&cWylaczono &7mozliwosc wyrzucania przedmiotow na ziemie!"));
        } else {
            DropManager.addDrop(player.getUniqueId());
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&aWlaczono &7mozliwosc wyrzucania przedmiotow na ziemie!"));
        }
    }
}
