package rpg.rpgcore.pomoc;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.globalitems.GlobalItem;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.expowiska.Skrzynki;

import java.io.IOException;


public class PomocCommand extends CommandAPI {

    private final RPGCORE rpgcore;

    public PomocCommand(RPGCORE rpgcore) {
        super("pomoc");
        this.setRankLevel(RankType.GRACZ);
        this.setRestrictedForPlayer(true);
        this.rpgcore = rpgcore;
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;
        if (args.length == 0) {
            player.getInventory().addItem(GlobalItem.getItem("I_SZATANAJEMNIKA", 1));
            player.getInventory().addItem(GlobalItem.getItem("I_UCHOGOBLINA", 1));
            player.getInventory().addItem(GlobalItem.getItem("I_SKORAGORYLA", 1));
            player.getInventory().addItem(GlobalItem.getItem("I_PROCHYZJAWY", 1));
            player.getInventory().addItem(GlobalItem.getItem("I_LZAOCEANU", 1));
            player.getInventory().addItem(GlobalItem.getItem("I_MROZNYPAZUR", 1));
            player.getInventory().addItem(GlobalItem.getItem("I_OGNISTYPYL", 1));
            player.getInventory().addItem(GlobalItem.getItem("I_TRUJACAROSLINA", 1));
            player.getInventory().addItem(GlobalItem.getItem("I_JADPTASZNIKA", 1));
            player.getInventory().addItem(GlobalItem.getItem("I_MROCZNYMATERIAL", 1));
            player.getInventory().addItem(GlobalItem.getItem("I_SZAFIROWESERCE", 1));
            player.getInventory().addItem(GlobalItem.getItem("I_SERCEDEMONA", 1));
            player.getInventory().addItem(GlobalItem.getItem("I_NIEBIANSKIMATERIAL", 1));


            rpgcore.getPomocManager().openPomocInventory(player);
            return;
        }
        player.sendMessage(Utils.poprawneUzycie("pomoc"));
    }
}
