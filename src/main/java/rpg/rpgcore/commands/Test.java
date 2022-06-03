package rpg.rpgcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.GlobalItems;

public class Test implements CommandExecutor {

    private final RPGCORE rpgcore;
    public Test(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        final Player player = (Player) commandSender;

        player.getInventory().addItem(GlobalItems.getSakwa(1));

        if (strings.length == 1) {
            try {
                int misja = Integer.parseInt(strings[0]);

                String[] misje = rpgcore.getRybakNPC().getPlayerRybakMisje(player.getUniqueId()).split(",");

                for (int i = 0; i < misje.length; i++) {
                    misje[i] = "false";
                }

                for (int i=0; i < misja; i++) {
                    misje[i] = "true";
                }
                StringBuilder builder = new StringBuilder();

                for (int i = 0; i < misje.length; i++) {
                    builder.append(misje[i]);
                    if (!(i+1 > misje.length)) {
                        builder.append(",");
                    }
                }

                rpgcore.getRybakNPC().setPlayerRybakMisje(player.getUniqueId(), String.valueOf(builder));
                return false;


            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        return false;
    }
}
