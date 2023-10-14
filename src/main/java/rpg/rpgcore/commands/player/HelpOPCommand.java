package rpg.rpgcore.commands.player;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.commands.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class HelpOPCommand extends CommandAPI {

    private final RPGCORE rpgcore;

    public HelpOPCommand(RPGCORE rpgcore) {
        super("helpop");
        this.setRankLevel(RankType.GRACZ);
        this.setRestrictedForPlayer(true);
        this.rpgcore = rpgcore;
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;
        if (args.length == 0) {
            player.sendMessage(Utils.poprawneUzycie("helpop <wiadomosc>"));
            return;
        }

        if (rpgcore.getCooldownManager().hasHelpopCooldown(player.getUniqueId())) {
            final long sekundy = rpgcore.getCooldownManager().getPlayerHelpopCooldown(player.getUniqueId()) - System.currentTimeMillis();
            final String mili = String.valueOf(TimeUnit.MILLISECONDS.toMillis(sekundy) - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(sekundy)));
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Musisz poczekac jeszcze &c" + TimeUnit.MILLISECONDS.toSeconds(sekundy) + "&7.&c" + mili + " sekund &7przed wyslaniem kolejnej wiadomosci do &cAdministracji!"));
            return;
        }

        final StringBuilder sb = new StringBuilder();
        for (String s : args) {
            sb.append(Utils.removeColor(s)).append(" ");
        }
        player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Twoja wiadomosc zostala wyslana do &cAdministracji&7!"));
        Utils.sendToStaff(Utils.format("&4&lHELP&8&lOP &7&l| &6&l" + player.getName() + "&7: " + sb));
        rpgcore.getCooldownManager().givePlayerHelpopCooldown(player.getUniqueId());
    }
}
