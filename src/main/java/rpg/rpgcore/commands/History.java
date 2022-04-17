package rpg.rpgcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class History implements CommandExecutor {

    private final RPGCORE rpgcore;
    private final ItemBuilder itemBuilder = new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3);
    private List<String> lore = new ArrayList<>();

    public History(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.NIEGRACZ);
            return false;
        }

        final Player player = (Player) sender;

        if (!(player.hasPermission("rpg.history"))) {
            player.sendMessage(Utils.permisje("rpg.history"));
            return false;
        }


        if (args.length == 1) {
            final UUID targetUUID = rpgcore.getPlayerManager().getPlayerUUID(args[0]);

            if (!(rpgcore.getPlayerManager().getPlayers().contains(targetUUID))) {
                player.sendMessage(Utils.NIEMATAKIEGOGRACZA);
                return false;
            }

            final String punishmentsHistory = String.valueOf(rpgcore.getPlayerManager().getPlayerPunishmentHistory(targetUUID));

            if (punishmentsHistory.equalsIgnoreCase("")) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&cTen gracz nie ma zadnych kar!"));
                return false;
            }



            //  OTWIERANIE GUI

            /*player.sendMessage(Utils.format("\n&cHistoria kar gracza:&7 " + args[0]));

            for (final String onePunishments : fullPunishmentHistory) {

                if (!(onePunishments.equalsIgnoreCase(""))) {

                    final String[] punishment = onePunishments.split(";");

                    String punishmentToSend;

                    if (punishment.length == 4) {
                        punishmentToSend = Utils.format(
                                "\n&cTyp:&7 " + punishment[0] + "\n" +
                                        "\n&cNadany przez:&7 " + punishment[1] + "" +
                                        "\n&cPowod:&7" + punishment[2] +
                                        "\n&cNadany dnia:&7 " + punishment[3]);
                    } else {
                        punishmentToSend = Utils.format(
                                "\n&cTyp:&7 " + punishment[0] + "\n" +
                                        "\n&cNadany przez:&7 " + punishment[1] + "" +
                                        "\n&cPowod:&7" + punishment[2] +
                                        "\n&cWygasa:&7 " + punishment[3] +
                                        "\n&cNadany dnia:&7 " + punishment[4]);
                    }
                    player.sendMessage(punishmentToSend);
                    player.sendMessage(Utils.format("\n-------------------------------------\n"));
                }
            }*/
            player.openInventory(punishmentHistoryGui(targetUUID));
            return false;
        }
        player.sendMessage(Utils.poprawneUzycie("history [gracz]"));
        return false;
    }



    private final Inventory punishmentHistoryGui(final UUID uuid) {
        final Inventory punishmentHistory = Bukkit.createInventory(null, 27, Utils.format("&4&lHistoria kar gracza " + rpgcore.getPlayerManager().getPlayerName(uuid)));

        final String[] fullPunishmentHistory = String.valueOf(rpgcore.getPlayerManager().getPlayerPunishmentHistory(uuid)).split(",");

            int j = 0;
            for (final String onePunishment : fullPunishmentHistory) {

                if (!(onePunishment.equalsIgnoreCase( ""))){

                    final String[] splitPunishment = onePunishment.split(";");


                    itemBuilder.setSkullOwner(splitPunishment[1]);

                    itemBuilder.setName("&4&l" + splitPunishment[0]);

                    if (splitPunishment.length == 4) {
                        lore.add("&cNadany przez:&7 " + splitPunishment[1]);
                        lore.add("&cPowod:&7 " + splitPunishment[2]);
                        lore.add("&cNadany dnia:&7 " + splitPunishment[3]);
                    } else {
                        lore.add("&cNadany przez:&7 " + splitPunishment[1]);
                        lore.add("&cPowod:&7 " + splitPunishment[2]);
                        lore.add("&cWygasa:&7 " + splitPunishment[3]);
                        lore.add("&cNadany dnia:&7 " + splitPunishment[4]);
                    }
                    itemBuilder.setLore(lore);
                    lore.clear();
                    punishmentHistory.setItem(j, itemBuilder.toItemStack());
                    j++;
                }
            }
        return punishmentHistory;
    }
}
