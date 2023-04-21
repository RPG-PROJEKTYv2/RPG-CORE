package rpg.rpgcore.managers.miecze;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.ChanceHelper;
import rpg.rpgcore.utils.DoubleUtils;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.GlobalItem;

public class MieczeManager {
    public static void getRandomMiecz(final Player player, final int szczescie) {
        final double chance = getPercentWithSzczescie(40, szczescie);
        if (!ChanceHelper.getChance(chance)) {
            player.sendMessage(Utils.format("&cNiestety &esakiewka &cokazala sie byc pusta"));
            return;
        }
        SwordType swordType;
        if (ChanceHelper.getChance(50)) {
            swordType = SwordType.TYRA;
        } else {
            swordType = SwordType.KS;
        }

        /*
            60-55% - 5%
            54-45% -  25%
            44-25% - 30%
            24-1% - 50%
         */

        int randomPercent = 1;

        if (ChanceHelper.getChance(getPercentWithSzczescie(5, szczescie))) {
            randomPercent = ChanceHelper.getRandInt(55, 60);
        } else if (ChanceHelper.getChance(getPercentWithSzczescie(25, szczescie))) {
            randomPercent = ChanceHelper.getRandInt(45, 54);
        } else if (ChanceHelper.getChance(getPercentWithSzczescie(30, szczescie))) {
            randomPercent = ChanceHelper.getRandInt(25, 44);
        } else if (ChanceHelper.getChance(getPercentWithSzczescie(50, szczescie))) {
            randomPercent = ChanceHelper.getRandInt(1, 24);
        }


        final ItemStack item = GlobalItem.getPercentSword(swordType, randomPercent);
        player.getInventory().addItem(item);
        player.sendMessage(Utils.format("&eSakiewka skrywala w sobie skarb... Szybko sprawdz co to bylo!"));
    }


    private static double getPercentWithSzczescie(final double chance, final int szczescie) {
        return DoubleUtils.round(chance + (chance * (szczescie / 1000.0)), 2);
    }
}
