package rpg.rpgcore.npc.magazynier;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.UUID;

public class MagazynCommand extends CommandAPI {

    private final RPGCORE rpgcore;

    public MagazynCommand(RPGCORE rpgcore) {
        super("magazyn");
        this.setRankLevel(RankType.GRACZ);
        this.setRestrictedForPlayer(true);
        this.rpgcore = rpgcore;
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;
        if (args.length != 1) {
            player.sendMessage(Utils.poprawneUzycie("magazyn <nr magazynu>"));
            return;
        }
        try {
            final int nrMagazynu = Integer.parseInt(args[0]);

            if (nrMagazynu > 5) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&cMusisz podac numer od 1 do 5"));
                return;
            }
            rpgcore.getMagazynierNPC().openMagazynierMagazyny(player);
        } catch (final NumberFormatException e) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&cMusisz podac numer od 1 do 5"));
        }
    }
}
