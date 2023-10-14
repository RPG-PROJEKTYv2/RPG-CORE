package rpg.rpgcore.commands.player.kod_tworcy;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.commands.api.CommandAPI;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;

public class KodCommand extends CommandAPI {
    private final RPGCORE rpgcore;

    public KodCommand(final RPGCORE rpgcore) {
        super("kod");
        this.setAliases("kodtworcy");
        this.setRestrictedForPlayer(true);
        this.rpgcore = rpgcore;
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;
        if (args.length < 1) {
            player.sendMessage(Utils.poprawneUzycie("kod <nick_tworcy/list>"));
            return;
        }

        if (args[0].equals("list")) {
            player.sendMessage(Utils.format("&6Lista twórców: &c" + rpgcore.getKodTworcyManager().getTworcy()));
            return;
        }
        final User tworca = rpgcore.getUserManager().find(args[0]);

        if (tworca == null) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie znaleziono gracza o nicku &e" + args[0] + "&c!"));
            return;
        }
        if (!rpgcore.getKodTworcyManager().isTworca(tworca.getId())) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&cTen gracz nie jest (juz) tworca!"));
            return;
        }
        if (rpgcore.getKodTworcyManager().isUser(player.getUniqueId())) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&cUzyles juz kodu tworcy!"));
            return;
        }
        rpgcore.getKodTworcyManager().addUser(tworca.getId(), player.getUniqueId());
        player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie uzyto kodu tworcy gracza &e" + tworca.getName() + "&a!"));
        if (Bukkit.getPlayer(tworca.getId()) != null) {
            Bukkit.getPlayer(tworca.getId()).sendMessage(Utils.format(Utils.SERVERNAME + "&aGracz &e" + player.getName() + "&a uzyl twojego kodu tworcy!"));
        }
    }
}
