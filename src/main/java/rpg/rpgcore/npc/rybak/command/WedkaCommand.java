package rpg.rpgcore.npc.rybak.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.commands.api.CommandAPI;
import rpg.rpgcore.npc.rybak.enums.WedkaExp;
import rpg.rpgcore.npc.rybak.helpers.RybakHelper;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.npc.RybakItems;

public class WedkaCommand extends CommandAPI {
    public WedkaCommand() {
        super("wedka");
        this.setRankLevel(RankType.ADMIN);
        this.setRestrictedForPlayer(true);
    }


    public void executeCommand(CommandSender sender, String[] args) {
        final Player player = (Player) sender;
        if (args.length < 5) {
            player.sendMessage(Utils.poprawneUzycie("wedka <stara/slaba> <lvl> <exp> <udane polowy> <wlasciciel>"));
            return;
        }
        if (args[0].equals("stara")) {
            final ItemStack wedka = RybakItems.getStaraWedka(args[4], Integer.parseInt(args[3]));
            for (int i = 0; i < Integer.parseInt(args[1]); i++) {
                RybakHelper.increaseLvl(wedka);
            }
            RybakHelper.increaseExp(wedka, Integer.parseInt(args[2]));
            player.getInventory().addItem(wedka);
        }
        if (args[0].equals("slaba")) {
            double doubleDrop = 0;
            for (int i =0; i < Integer.parseInt(args[1]); i++) doubleDrop += WedkaExp.getWedkaExp(i).getDropx2();


            final ItemStack wedka = RybakItems.getSlabaWedkaRybacka(args[4], Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]), doubleDrop);
            for (int i = 0; i < Integer.parseInt(args[1]); i++) {
                RybakHelper.increaseLvl(wedka);
            }
            RybakHelper.increaseExp(wedka, Integer.parseInt(args[2]));
            player.getInventory().addItem(wedka);
        }
    }
}
