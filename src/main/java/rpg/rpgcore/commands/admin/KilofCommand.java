package rpg.rpgcore.commands.admin;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.npc.GornikItems;

public class KilofCommand extends CommandAPI {
    public KilofCommand() {
        super("kilof");
        this.setRankLevel(RankType.ADMIN);
        this.setRestrictedForPlayer(true);
    }

    public void executeCommand(CommandSender sender, String[] args) {
        final Player player = (Player) sender;
        if (args.length < 4) {
            player.sendMessage("§cPoprawne uzycie: /kilof <gracz> <stone/iron/gold/diamond> <poziom> <exp>");
            return;
        }

        final User user = RPGCORE.getInstance().getUserManager().find(args[0]);

        if (user == null) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie znaleziono podanego gracza!"));
            return;
        }

        if (Integer.parseInt(args[2]) > 30) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&cMaksymalny poziom to 30!"));
            return;
        }

        final String type = args[1];
        final ItemStack kilof = GornikItems.getKilof(user.getId()).clone();

        for (int i = 0; i < Integer.parseInt(args[2]); i++) RPGCORE.getInstance().getGornikNPC().updateLvl(kilof);
        RPGCORE.getInstance().getGornikNPC().updateExp(kilof, Integer.parseInt(args[3]));
        switch (type) {
            case "stone":
                kilof.setType(Material.STONE_PICKAXE);
                break;
            case "iron":
                kilof.setType(Material.IRON_PICKAXE);
                break;
            case "gold":
                kilof.setType(Material.GOLD_PICKAXE);
                break;
            case "diamond":
                kilof.setType(Material.DIAMOND_PICKAXE);
                break;
        }
        player.getInventory().addItem(kilof);
        player.sendMessage(Utils.format(Utils.SERVERNAME + "&aDodano kilof!"));
    }
}
