package rpg.rpgcore.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.dodatki.bony.enums.BonType;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.Arrays;

public class GetBonyCommand extends CommandAPI {
    public GetBonyCommand() {
        super("getbony");
        this.setAliases(Arrays.asList("bonydaj", "dajbony"));
        this.setRankLevel(RankType.HA);
        this.setRestrictedForPlayer(true);
    }

    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;

        if  (args.length != 0) {
            player.sendMessage(Utils.poprawneUzycie("getbony"));
            return;
        }
        final Inventory gui = Bukkit.createInventory(null, 36, Utils.format("&c&lAdministracyjne Bony"));
        for (final BonType bonType : BonType.values()) {
            gui.setItem(gui.firstEmpty(), bonType.getBon());
        }

        player.openInventory(gui);
    }
}
