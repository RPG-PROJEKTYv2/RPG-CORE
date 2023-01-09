package rpg.rpgcore.commands.admin;

import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.Arrays;

public class BroadcastCommand extends CommandAPI {
    public BroadcastCommand() {
        super("broadcast");
        this.setAliases(Arrays.asList("bc", "alert", "ogloszenie", "title"));
        this.setRankLevel(RankType.GM);
        this.setRestrictedForPlayer(true);
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;

        if (args.length < 2) {
            player.sendMessage(Utils.poprawneUzycie("broadcast <title/bar> <wiadomosc>"));
            return;
        }

        if (args[0].equals("title")) {
            StringBuilder message = new StringBuilder();
            for (int i = 1; i < args.length; i++) {
                message.append(args[i]).append(" ");
            }
            final PacketPlayOutTitle title = RPGCORE.getInstance().getNmsManager().makeTitle("&8&l[&4&lHELLRPG&8&l]", 10, 25, 10);
            final PacketPlayOutTitle subtitle = RPGCORE.getInstance().getNmsManager().makeSubTitle(message.toString(), 10, 25, 10);

            for (Player server : Bukkit.getOnlinePlayers()) {
                RPGCORE.getInstance().getNmsManager().sendTitleAndSubTitle(server, title, subtitle);
            }
            return;
        }
        if (args[0].equals("bar")) {
            StringBuilder message = new StringBuilder();
            for (int i = 1; i < args.length; i++) {
                message.append(args[i]).append(" ");
            }
            for (Player server : Bukkit.getOnlinePlayers()) {
                RPGCORE.getInstance().getNmsManager().sendActionBar(server, "&8&l[&4&lHELLRPG&8&l] &f&l" + message + "&8&l[&4&lHELLRPG&8&l]");
            }
        }
    }
}
