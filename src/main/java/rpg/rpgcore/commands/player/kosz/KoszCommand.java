package rpg.rpgcore.commands.player.kosz;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.commands.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.Arrays;

public class KoszCommand extends CommandAPI {

    public KoszCommand() {
        super("kosz");
        this.setAliases(Arrays.asList("k"));
        this.setRankLevel(RankType.GRACZ);
        this.setRestrictedForPlayer(true);
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;

        if (args.length > 0) {
            player.sendMessage(Utils.poprawneUzycie("kosz"));
            return;
        }
        this.openKosz(player);
    }

    private void openKosz(final Player player) {
        final Inventory kosz = Bukkit.createInventory(null, 54, Utils.format("&4&lKosz"));
        player.openInventory(kosz);
    }
}
