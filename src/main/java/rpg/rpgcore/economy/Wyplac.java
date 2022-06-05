package rpg.rpgcore.economy;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

public class Wyplac implements CommandExecutor {

    private final RPGCORE rpgcore;
    private final ItemBuilder czek = new ItemBuilder(Material.DOUBLE_PLANT);

    public Wyplac(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.NIEGRACZ);
            return false;
        }

        final Player player = (Player) sender;
        final UUID uuid = player.getUniqueId();

        if (!(player.hasPermission("rpg.wyplac"))) {
            player.sendMessage(Utils.permisje("rpg.wyplac"));
            return false;
        }

        if (args.length == 1) {
            final double playerKasa = rpgcore.getPlayerManager().getPlayerKasa(uuid);
            if (args[0].contains("k")) {
                int kIndex = args[0].indexOf('k');
                double beforeK = Double.parseDouble(args[0].substring(0, kIndex));
                double afterK = Double.parseDouble(args[0].substring(kIndex).replaceAll("k", "000").replaceFirst("0", "1") + "0");

                args[0] = String.valueOf(beforeK * afterK);
            }
            try {
                final double wyplac = Double.parseDouble(args[0]);

                if (wyplac < 1) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie mozesz wyplacic tej kwoty"));
                    return false;
                }

                if (playerKasa < wyplac) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie masz tyle pieniedzy"));
                    return false;
                }

                rpgcore.getPlayerManager().updatePlayerKasa(uuid, playerKasa - wyplac);
                czek.setName("&eCzek na &6&l" + Utils.spaceNumber(Utils.kasaFormat.format(wyplac)) + " &2$");
                czek.addGlowing();

                player.getInventory().addItem(czek.toItemStack());
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie wyplacono &6" + Utils.spaceNumber(Utils.kasaFormat.format(wyplac)) + " &2$"));
                return false;

            } catch (NumberFormatException e) {
                player.sendMessage(Utils.poprawneUzycie("wyplac <ilosc>"));
                return false;
            }


        }


        player.sendMessage(Utils.poprawneUzycie("wyplac <ilosc>"));
        return false;
    }
}
