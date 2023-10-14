package rpg.rpgcore.economy;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.commands.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.DoubleUtils;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.GlobalItem;

import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

public class WyplacCommand extends CommandAPI {

    private final RPGCORE rpgcore;

    public WyplacCommand(RPGCORE rpgcore) {
        super("wyplac");
        this.setAliases(Arrays.asList("withdraw"));
        this.setRankLevel(RankType.GRACZ);
        this.setRestrictedForPlayer(true);
        this.rpgcore = rpgcore;
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;
        final UUID uuid = player.getUniqueId();
        if (args.length == 2 ) {
            if (args[0].equals("hs")) {
                int amount;
                try {
                    amount = Integer.parseInt(args[1]);
                } catch (NumberFormatException e) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Niepoprawna liczba!"));
                    return;
                }
                if (amount < 1) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Niepoprawna liczba!"));
                    return;
                }

                User user = rpgcore.getUserManager().find(player.getUniqueId());

                if (user.getHellcoins() < amount) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Nie posiadasz tylu &4&lH&6&lS&7!"));
                    return;
                }

                user.setHellcoins(user.getHellcoins() - amount);
                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataUser(player.getUniqueId(), user));
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie wyplacono &6" + Utils.spaceNumber(String.valueOf(amount)) + " &4&lH&6&lS&a!"));
                player.getInventory().addItem(GlobalItem.getHells(amount).clone());
                return;
            }

            if (args[0].equals("kasa")) {
                final User user = rpgcore.getUserManager().find(uuid);
                final double playerKasa = user.getKasa();
                if (args[1].contains("k")) {
                    int kIndex = args[1].indexOf('k');
                    try {
                        double beforeK = Double.parseDouble(args[1].substring(0, kIndex));
                        double afterK = Double.parseDouble(args[1].substring(kIndex).replaceAll("k", "000").replaceFirst("0", "1") + "0");

                        args[1] = String.valueOf(beforeK * afterK);
                    } catch (NumberFormatException e) {
                        player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Niepoprawna liczba!"));
                        return;
                    }
                }
                try {
                    final double wyplac = Double.parseDouble(args[1]);

                    if (wyplac < 1) {
                        player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie mozesz wyplacic tej kwoty"));
                        return;
                    }

                    if (playerKasa < wyplac) {
                        player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie masz tyle pieniedzy"));
                        return;
                    }

                    user.setKasa(playerKasa - wyplac);
                    rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataUser(uuid, user));

                    player.getInventory().addItem(new ItemBuilder(Material.DOUBLE_PLANT).setName("&eCzek na &6&l" + Utils.spaceNumber(DoubleUtils.round(wyplac, 2)) + " &2$").addGlowing().toItemStack().clone());
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie wyplacono &6" + Utils.spaceNumber(Utils.kasaFormat.format(wyplac)) + " &2$"));
                } catch (NumberFormatException e) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Niepoprawna liczba!"));
                }
            }
        } else {
            player.sendMessage(Utils.poprawneUzycie("wyplac hs/kasa <ilosc>"));
        }
    }
}
