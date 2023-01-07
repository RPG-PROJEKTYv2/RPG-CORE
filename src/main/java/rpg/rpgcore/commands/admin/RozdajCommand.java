package rpg.rpgcore.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class RozdajCommand extends CommandAPI {
    public RozdajCommand() {
        super("rozdaj");
        this.setAliases(Arrays.asList("losowanie", "losuj"));
        this.setRankLevel(RankType.ADMIN);
        this.setRestrictedForPlayer(true);
    }

    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;

        if (player.getItemInHand() == null || player.getItemInHand().getType() == Material.AIR || !player.getItemInHand().getItemMeta().hasDisplayName()) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&c&lMusisz trzymac prawidlowy przedmiot w rece!"));
            return;
        }

        if (args.length < 1) {
            player.sendMessage(Utils.poprawneUzycie("rozdaj <all/jeden>"));
            return;
        }

        if (args[0].equals("all")) {
            final ItemStack item = player.getItemInHand();
            RPGCORE.getInstance().getServer().broadcastMessage(Utils.format("&8[&6&lRozdanie&8] &7Administrator &6" + player.getName() + " &7rozdal wszystkim graczom &6x" + item.getAmount() + " " + item.getItemMeta().getDisplayName() + "&7!"));
            for (final Player p : Bukkit.getOnlinePlayers()) {
                p.getInventory().addItem(item);
            }
            return;
        }
        if (args[0].equals("jeden")) {
            final ItemStack item = player.getItemInHand();
            RPGCORE.getInstance().getServer().getScheduler().runTaskLater(RPGCORE.getInstance(), () -> {
                RPGCORE.getInstance().getServer().broadcastMessage(Utils.format("&8[&6&lRozdanie&8] &7Administrator &6" + player.getName() + " &7rozpoczal losowanie na &6x" + item.getAmount() + " " + item.getItemMeta().getDisplayName() + "&7!"));
                RPGCORE.getInstance().getServer().getScheduler().runTaskLater(RPGCORE.getInstance(), () -> {
                    RPGCORE.getInstance().getServer().broadcastMessage(Utils.format("&8[&6&lRozdanie&8] &f&lLosowanie Odbedzie sie za 3 sekundy!"));
                    RPGCORE.getInstance().getServer().getScheduler().runTaskLater(RPGCORE.getInstance(), () -> {
                        RPGCORE.getInstance().getServer().broadcastMessage(Utils.format("&8[&6&lRozdanie&8] &f&lLosowanie Odbedzie sie za 2 sekundy!"));
                        RPGCORE.getInstance().getServer().getScheduler().runTaskLater(RPGCORE.getInstance(), () -> {
                            RPGCORE.getInstance().getServer().broadcastMessage(Utils.format("&8[&6&lRozdanie&8] &f&lLosowanie Odbedzie sie za 1 sekunde!"));
                            RPGCORE.getInstance().getServer().getScheduler().runTaskLater(RPGCORE.getInstance(), () -> {
                                final Player winner = (Player) Bukkit.getOnlinePlayers().toArray()[new Random().nextInt(Bukkit.getOnlinePlayers().size())];
                                RPGCORE.getInstance().getServer().broadcastMessage(Utils.format("&8[&6&lRozdanie&8] &f&lWylosowany gracz to &b&l" + winner.getName()+ "&f&l!"));
                                winner.getInventory().addItem(item);
                            }, 20L);
                        }, 20L);
                    }, 20L);
                }, 20L);
            }, 20L);
        }
    }
}
