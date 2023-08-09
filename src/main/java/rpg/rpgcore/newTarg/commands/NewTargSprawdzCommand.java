package rpg.rpgcore.newTarg.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;

public class NewTargSprawdzCommand extends CommandAPI {
    private final RPGCORE rpgcore;

    public NewTargSprawdzCommand(RPGCORE rpgcore) {
        super("sprawdz");
        this.setRankLevel(RankType.GRACZ);
        this.setRestrictedForPlayer(true);
        this.rpgcore = rpgcore;
    }

    @Override
    public void executeCommand(final CommandSender sender, final String[] args) throws IOException {
        final Player player = (Player) sender;
        if (args.length < 1) {
            player.sendMessage(Utils.poprawneUzycie("sprawdz <nick>"));
            return;
        }

        final Player target = Bukkit.getPlayer(args[0]);

        if (target == null || !target.isOnline()) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie znaleziono gracza o nicku &e" + args[0]));
            return;
        }

        if (rpgcore.getNewTargManager().find(rpgcore.getUserManager().find(target.getUniqueId()).getId()).getItemList().isEmpty()) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&cTen gracz nie ma wystawionych zadnych przedmiotow"));
            return;
        }

        rpgcore.getNewTargManager().openPlayerTarg(target.getName(), player);
        player.sendMessage(Utils.format(Utils.SERVERNAME + "&aOtworzono targ gracza &e" + target.getName()));
    }
}
