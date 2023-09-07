package rpg.rpgcore.commands.player.rangi;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.ranks.types.RankTypePlayer;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.ChanceHelper;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.GlobalItem;
import rpg.rpgcore.utils.globalitems.expowiska.SkrzynkiOther;
import rpg.rpgcore.utils.globalitems.npc.LesnikItems;

import java.io.IOException;

public class ZestawRangiCommand extends CommandAPI {
    private final RPGCORE rpgcore;

    public ZestawRangiCommand(final RPGCORE rpgcore) {
        super("zestawrangi");
        this.setRankLevel(RankType.GRACZ);
        this.setRestrictedForPlayer(true);
        this.rpgcore = rpgcore;
    }

    public void executeCommand(final CommandSender sender, final String[] args) throws IOException {
        final Player player = (Player) sender;
        if (args.length > 0) {
            player.sendMessage(Utils.poprawneUzycie("zestawrangi"));
            return;
        }

        final User user = rpgcore.getUserManager().find(player.getUniqueId());

        if (user.getRankPlayerUser().getRankType().getPriority() < RankTypePlayer.VIP.getPriority()) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie posiadasz wymaganej rangi, zeby uzyc tej komendy!"));
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&cRange &e&lVIP&c, &5&lELITA &cmozesz zakupic na naszym dc: &edc.hellrpg.pl"));
            return;
        }

        if (user.hasKitCooldown()) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&cMusisz odczekac jeszcze &e" + user.getKitCooldown() + " &c przed odebraniem zestawu!"));
            return;
        }

        switch (user.getRankPlayerUser().getRankType()) {
            case VIP:
                if (ChanceHelper.getChance(10)) {
                    player.getInventory().addItem(GlobalItem.I52.getItemStack());
                    player.sendMessage(Utils.format("&9&lTWORCA &8&l>> &fOtrzymales x1 &e&lPierscien Doswiadczenia &6&l25% &6&l30 MINUT"));
                    break;
                }
                if (ChanceHelper.getChance(12)) {
                    final int amountPOTION = ChanceHelper.getRandInt(1,2);
                    player.getInventory().addItem(LesnikItems.getByItem("Potion", amountPOTION));
                    player.sendMessage(Utils.format("&9&lTWORCA &8&l>> &fOtrzymales x" + amountPOTION + " &2Wywar z Kory"));
                    break;

                }
                if (ChanceHelper.getChance(45)) {
                    final int amoutZMIANKA = ChanceHelper.getRandInt(1,3);
                    player.getInventory().addItem(GlobalItem.getItem("I50", amoutZMIANKA));
                    player.sendMessage(Utils.format("&9&lTWORCA &8&l>> &fOtrzymales x" + amoutZMIANKA + " &9&lMagiczne Zaczarowanie"));
                    break;

                }
                if (ChanceHelper.getChance(55)) {
                    final int amountTAJEMNICZA = ChanceHelper.getRandInt(1,5);
                    player.getInventory().addItem(SkrzynkiOther.getItem("I4", amountTAJEMNICZA));
                    player.sendMessage(Utils.format("&9&lTWORCA &8&l>> &fOtrzymales x" + amountTAJEMNICZA + " &3Tajemnicza Skrzynia"));
                    break;

                }
                if (ChanceHelper.getChance(100)) {
                    final int amountPOZLACANY = ChanceHelper.getRandInt(1,3);
                    player.getInventory().addItem(SkrzynkiOther.getItem("I1", amountPOZLACANY));
                    player.sendMessage(Utils.format("&9&lTWORCA &8&l>> &fOtrzymales x" + amountPOZLACANY + " &e&lPozlacany Skarb"));
                    break;
                }
            case TWORCA:
                if (ChanceHelper.getChance(10)) {
                    player.getInventory().addItem(GlobalItem.I54.getItemStack());
                    player.sendMessage(Utils.format("&9&lTWORCA &8&l>> &fOtrzymales x1 &e&lPierscien Doswiadczenia &6&l50%% &6&l30 MINUT"));
                    break;
                }
                if (ChanceHelper.getChance(12)) {
                    final int amountPOTION = ChanceHelper.getRandInt(1,3);
                    player.getInventory().addItem(LesnikItems.getByItem("Potion", amountPOTION));
                    player.sendMessage(Utils.format("&9&lTWORCA &8&l>> &fOtrzymales x" + amountPOTION + " &2Wywar z Kory"));
                    break;
                }
                if (ChanceHelper.getChance(45)) {
                    final int amoutZMIANKA = ChanceHelper.getRandInt(1,4);
                    player.getInventory().addItem(GlobalItem.getItem("I50", amoutZMIANKA));
                    player.sendMessage(Utils.format("&9&lTWORCA &8&l>> &fOtrzymales x" + amoutZMIANKA + " &9&lMagiczne Zaczarowanie"));
                    break;
                }
                if (ChanceHelper.getChance(55)) {
                    final int amountTAJEMNICZA = ChanceHelper.getRandInt(1,8);
                    player.getInventory().addItem(SkrzynkiOther.getItem("I4", amountTAJEMNICZA));
                    player.sendMessage(Utils.format("&9&lTWORCA &8&l>> &fOtrzymales x" + amountTAJEMNICZA + " &3Tajemnicza Skrzynia"));
                    break;
                }
                if (ChanceHelper.getChance(100)) {
                    final int amountPOZLACANY = ChanceHelper.getRandInt(1,4);
                    player.getInventory().addItem(SkrzynkiOther.getItem("I1", amountPOZLACANY));
                    player.sendMessage(Utils.format("&9&lTWORCA &8&l>> &fOtrzymales x" + amountPOZLACANY + " &e&lPozlacany Skarb"));
                    break;
                }
                break;
            case ELITA:
                if (ChanceHelper.getChance(10)) {
                    player.getInventory().addItem(GlobalItem.I55.getItemStack());
                    player.sendMessage(Utils.format("&9&lTWORCA &8&l>> &fOtrzymales x1 &e&lPierscien Doswiadczenia &6&l50% &6&l60 MINUT"));
                    break;
                }
                if (ChanceHelper.getChance(12)) {
                    final int amountPOTION = ChanceHelper.getRandInt(1,3);
                    player.getInventory().addItem(LesnikItems.getByItem("Potion", amountPOTION));
                    player.sendMessage(Utils.format("&9&lTWORCA &8&l>> &fOtrzymales x" + amountPOTION + " &2Wywar z Kory"));
                    break;
                }
                if (ChanceHelper.getChance(45)) {
                    final int amoutZMIANKA = ChanceHelper.getRandInt(1,5);
                    player.getInventory().addItem(GlobalItem.getItem("I50", amoutZMIANKA));
                    player.sendMessage(Utils.format("&9&lTWORCA &8&l>> &fOtrzymales x" + amoutZMIANKA + " &9&lMagiczne Zaczarowanie"));
                    break;
                }
                if (ChanceHelper.getChance(55)) {
                    final int amountTAJEMNICZA = ChanceHelper.getRandInt(1,12);
                    player.getInventory().addItem(SkrzynkiOther.getItem("I4", amountTAJEMNICZA));
                    player.sendMessage(Utils.format("&9&lTWORCA &8&l>> &fOtrzymales x" + amountTAJEMNICZA + " &3Tajemnicza Skrzynia"));
                    break;
                }
                if (ChanceHelper.getChance(100)) {
                    final int amountPOZLACANY = ChanceHelper.getRandInt(1,5);
                    player.getInventory().addItem(SkrzynkiOther.getItem("I1", amountPOZLACANY));
                    player.sendMessage(Utils.format("&9&lTWORCA &8&l>> &fOtrzymales x" + amountPOZLACANY + " &e&lPozlacany Skarb"));
                    break;
                }
                break;
            default:
                return;
        }
        user.giveKitCooldown();
        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataUser(user.getId(), user));
    }

}
