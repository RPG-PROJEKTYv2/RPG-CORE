package rpg.rpgcore.commands.admin.ustawieniakonta;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.Utils;

import java.util.Arrays;

public class UstawieniaKontaCommand extends CommandAPI {
    private final RPGCORE rpgcore;

    public UstawieniaKontaCommand(final RPGCORE rpgcore) {
        super("ustawieniakonta");
        this.setRankLevel(RankType.DEV);
        this.setAliases(Arrays.asList("konto", "ustawienia"));
        this.setRestrictedForPlayer(true);
        this.rpgcore = rpgcore;
    }

    public void executeCommand(CommandSender sender, String[] args) {
        final Player player = (Player) sender;
        rpgcore.getUstawieniaKontaManager().log(player, args);
        if (args.length < 1) {
            player.sendMessage(Utils.poprawneUzycie("ustawieniakonta <gracz>"));
            return;
        }
        final User user = this.rpgcore.getUserManager().find(args[0]);
        if (user == null) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie znaleziono gracza o nicku &e" + args[0]));
            return;
        }
        rpgcore.getUstawieniaKontaManager().openUstawieniaKonta(player, user);
    }
}
