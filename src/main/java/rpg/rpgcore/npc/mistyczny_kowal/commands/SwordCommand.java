package rpg.rpgcore.npc.mistyczny_kowal.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.npc.mistyczny_kowal.enums.SwordType;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.GlobalItem;

public class SwordCommand extends CommandAPI {
    public SwordCommand() {
        super("sword");
        this.setRankLevel(RankType.ADMIN);
        this.setRestrictedForPlayer(true);
    }

    public void executeCommand(CommandSender sender, String[] args) {
        final Player player = (Player) sender;
        if (args.length < 2) {
            player.sendMessage(Utils.poprawneUzycie("sword <ks/tyra> <percent>"));
            return;
        }

        SwordType type;

        if (args[0].equalsIgnoreCase("ks")) type = SwordType.KS;
        else type = SwordType.TYRA;

        if (Integer.parseInt(args[1]) > 60) return;

        player.getInventory().addItem(GlobalItem.getPercentSword(type, Integer.parseInt(args[1])));
    }
}
