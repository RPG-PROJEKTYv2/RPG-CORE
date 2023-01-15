package rpg.rpgcore.commands.admin;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.Arrays;

public class SetNameCommand extends CommandAPI {
    public SetNameCommand() {
        super("setname");
        this.setAliases(Arrays.asList("name"));
        this.setRankLevel(RankType.ADMIN);
        this.setRestrictedForPlayer(true);
    }

    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;
        if (args.length == 0) {
            player.sendMessage(Utils.poprawneUzycie("setname <nazwa>"));
            return;
        }

        if (player.getItemInHand() == null || player.getItemInHand().getType() == Material.AIR) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&cMusisz trzymac cos w lapce!"));
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (String s : args) {
            sb.append(s).append(" ");
        }

        player.setItemInHand(new ItemBuilder(player.getItemInHand().clone()).setName(Utils.format(sb.toString().trim())).toItemStack());
        player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie zmieniono nazwe przedmiotu!"));
    }
}
