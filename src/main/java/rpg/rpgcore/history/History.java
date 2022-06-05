package rpg.rpgcore.history;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class History implements CommandExecutor {

    private final RPGCORE rpgcore;
    private final ItemBuilder itemBuilder = new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3);
    private final List<String> lore = new ArrayList<>();

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

            player.openInventory(punishmentHistoryGui(targetUUID));
            return false;
        }

        if (args.length == 2) {
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

            player.openInventory(punishmentHistoryGui(targetUUID, args[1]));
            return false;
        }

        player.sendMessage(Utils.poprawneUzycie("history <gracz> [un-/mute/tempmute/ban/tempban/]"));
        return false;
    }



    private Inventory punishmentHistoryGui(final UUID uuid) {
        final Inventory punishmentHistory = Bukkit.createInventory(null, 54, Utils.format("&4&lHistoria kar gracza " + rpgcore.getPlayerManager().getPlayerName(uuid)));

        final String[] fullPunishmentHistory = String.valueOf(rpgcore.getPlayerManager().getPlayerPunishmentHistory(uuid)).split(",");

            int j = 0;
            for (final String onePunishment : fullPunishmentHistory) {

                if (!(onePunishment.equalsIgnoreCase( ""))){

                    final String[] splitPunishment = onePunishment.split(";");


                    itemBuilder.setSkullOwner(splitPunishment[1]);

                    itemBuilder.setName("&4&l" + splitPunishment[0].toUpperCase(Locale.ROOT));

                    if (splitPunishment.length == 3) {
                        lore.add("&cNadany przez:&7 " + splitPunishment[1]);
                        lore.add("&cNadany dnia:&7 " + splitPunishment[2]);
                    } else if (splitPunishment.length == 4) {
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

    private Inventory punishmentHistoryGui(final UUID uuid, final String regex) {
        final Inventory punishmentHistory = Bukkit.createInventory(null, 54, Utils.format("&4&lHistoria kar gracza " + rpgcore.getPlayerManager().getPlayerName(uuid)));

        final String[] fullPunishmentHistory = String.valueOf(rpgcore.getPlayerManager().getPlayerPunishmentHistory(uuid)).split(",");

        for (final String onePunishment : fullPunishmentHistory) {

            if (!(onePunishment.equalsIgnoreCase( ""))){

                final String[] splitPunishment = onePunishment.split(";");

                if (splitPunishment[0].equalsIgnoreCase(regex)) {
                    itemBuilder.setSkullOwner(splitPunishment[1]);

                    itemBuilder.setName("&4&l" + splitPunishment[0].toUpperCase(Locale.ROOT));

                    if (splitPunishment.length == 3) {
                        lore.add("&cNadany przez:&7 " + splitPunishment[1]);
                        lore.add("&cNadany dnia:&7 " + splitPunishment[2]);
                    } else if (splitPunishment.length == 4) {
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
                    punishmentHistory.setItem(punishmentHistory.firstEmpty(), itemBuilder.toItemStack());
                }
            }
        }
        return punishmentHistory;
    }
}
