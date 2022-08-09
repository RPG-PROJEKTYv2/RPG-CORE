package rpg.rpgcore.commands.player;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.concurrent.TimeUnit;

public class HelpOP implements CommandExecutor {

    private final RPGCORE rpgcore;

    public HelpOP(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.NIEGRACZ);
            return false;
        }

        final Player player = (Player) sender;

        if (!player.hasPermission("rpg.helpop")) {
            player.sendMessage(Utils.permisje("rpg.helpop"));
            return false;
        }

        if (args.length == 0) {
            player.sendMessage(Utils.poprawneUzycie("helpop <wiadomosc>"));
            return false;
        }

        if (rpgcore.getCooldownManager().hasHelpopCooldown(player.getUniqueId())) {
            final long sekundy = rpgcore.getCooldownManager().getPlayerHelpopCooldown(player.getUniqueId()) - System.currentTimeMillis();
            final String mili = String.valueOf(TimeUnit.MILLISECONDS.toMillis(sekundy) - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(sekundy)));
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Musisz poczekac jeszcze &c" + TimeUnit.MILLISECONDS.toSeconds(sekundy) + "&7.&c" + mili + " sekund &7przed wyslaniem kolejnej wiadomosci do &cAdministracji!"));
            return false;
        }

        final StringBuilder sb = new StringBuilder();

        for (String s : args) {
            sb.append(Utils.removeColor(s)).append(" ");
        }

        for (Player staff : rpgcore.getServer().getOnlinePlayers()) {
            if (staff.hasPermission("admin.rpg.helpop")) {
                staff.sendMessage(Utils.format("&4&lHELP&8&lOP &7&l| &6&l" + player.getName() + "&7: " + sb));
            }
        }
        rpgcore.getCooldownManager().givePlayerHelpopCooldown(player.getUniqueId());

        return false;
    }

}
