package rpg.rpgcore.akcesoria;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.Arrays;

public class AkcesoriaCommand extends CommandAPI {

    private final RPGCORE rpgcore;

    public AkcesoriaCommand(RPGCORE rpgcore) {
        super("akcesoria");
        this.setAliases(Arrays.asList("akce", "akc"));
        this.setRankLevel(RankType.GRACZ);
        this.setRestrictedForPlayer(true);
        this.rpgcore = rpgcore;
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;
        if (args.length == 0) {
            rpgcore.getAkcesoriaManager().openAkcesoriaGUI(player);
            //player.getInventory().addItem(ItemHelper.createArmor("&8Testowa Zbroja", Material.DIAMOND_CHESTPLATE, 100, 20, false, true), ItemHelper.createSword("&7&lTestowy Miecz", Material.STONE_SWORD, 250, 100, false, true));
        } else {
            player.sendMessage(Utils.poprawneUzycie("akcesoria"));
        }
    }
}
