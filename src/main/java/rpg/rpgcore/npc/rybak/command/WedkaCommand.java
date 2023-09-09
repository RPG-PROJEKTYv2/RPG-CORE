package rpg.rpgcore.npc.rybak.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.npc.rybak.helpers.RybakHelper;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.npc.RybakItems;

public class WedkaCommand extends CommandAPI {
    public WedkaCommand() {
        super("wedka");
        this.setRankLevel(RankType.HA);
        this.setRestrictedForPlayer(true);
    }


    public void executeCommand(CommandSender sender, String[] args) {
        final Player player = (Player) sender;
        if (args.length < 4) {
            player.sendMessage(Utils.poprawneUzycie("wedka <stara/?> <lvl> <exp> <wlasciciel>"));
            return;
        }

        final ItemStack wedka = RybakItems.getStaraWedka(args[3]);
        for (int i = 0; i < Integer.parseInt(args[1]); i++) {
            RybakHelper.increaseLvl(wedka);
        }
        RybakHelper.increaseExp(wedka, Integer.parseInt(args[2]));
        player.getInventory().addItem(wedka);
    }
}
