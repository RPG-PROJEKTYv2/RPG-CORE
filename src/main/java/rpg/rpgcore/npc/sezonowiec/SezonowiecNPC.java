package rpg.rpgcore.npc.sezonowiec;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.dodatki.akcesoriaD.helpers.AkcesoriaDodatHelper;
import rpg.rpgcore.npc.sezonowiec.enums.SezonowiecRewards;
import rpg.rpgcore.utils.ChanceHelper;
import rpg.rpgcore.utils.DoubleUtils;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.GlobalItem;
import rpg.rpgcore.utils.globalitems.akcesoriumOLD.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class SezonowiecNPC {
    private static final RPGCORE rpgcore = RPGCORE.getInstance();

    public static void openSeznonowiecGUI(final Player player) {
        final int points = rpgcore.getUserManager().find(player.getUniqueId()).getSezonowiecPoints();
        final Inventory gui = Bukkit.createInventory(null, InventoryType.HOPPER, Utils.format("&e&lSezonowiec"));
        gui.setItem(0, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 4).setName(" ").toItemStack());
        gui.setItem(1, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 1).setName(" ").toItemStack());
        gui.setItem(2, getMainItem(points));
        gui.setItem(3, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 1).setName(" ").toItemStack());
        gui.setItem(4, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 4).setName(" ").toItemStack());

        player.openInventory(gui);
    }

    private static ItemStack getMainItem(final int points) {
        final List<String> lore = new ArrayList<>();

        Arrays.stream(SezonowiecRewards.values()).sorted(Comparator.comparingDouble(SezonowiecRewards::getChance)).forEach(reward -> {
           lore.add("&8- " + reward.getReward().getItemMeta().getDisplayName() + " &7x" + reward.getMinAmount() + "-" + reward.getMaxAmount() +
                   " &8(" + DoubleUtils.round(reward.getChance(), 2) + "%)");
        });

        return new ItemBuilder(Material.HOPPER).setName("&e&lWymien 1 000 Lisci na nagrody!").setLoreCrafting(Arrays.asList(
                "&7Po uzbieraniu &e1 000 &7lisci kliknij",
                "&7zeby otrzymac niesamowite nagrody!",
                "&7Twoje punkty: &e" + points,
                "",
                "&f&lDostepne Nagrody"
        ), lore).addGlowing().toItemStack().clone();
    }

    public static void getRewards(final Player player) {
        player.sendMessage(Utils.format("&e&lSezonowiec &8>> &aOtrzymane nagrody to:"));
        Arrays.stream(SezonowiecRewards.values()).sorted(Comparator.comparingDouble(SezonowiecRewards::getChance)).forEach(reward -> {
            if (ChanceHelper.getChance(reward.getChance())) {
                ItemStack item = reward.getReward().clone();

                if (reward.getReward().getType().equals(Material.GOLD_NUGGET)) {
                    item = GlobalItem.getHells(ChanceHelper.getRandInt(reward.getMinAmount(), reward.getMaxAmount()));
                } else if (reward.getReward().getType().equals(Material.LEASH)) {
                    item = AkcesoriaDodatHelper.createPas(ChanceHelper.getRandInt(1, 30),
                            ChanceHelper.getRandInt(1, 30),
                            ChanceHelper.getRandInt(50, 120), "&6&lJesienny Pas").clone();
                } else if (reward.getReward().getType().equals(Material.LADDER)) {
                    item = AkcesoriaDodatHelper.createSzarfa(ChanceHelper.getRandInt(1, 30),
                            ChanceHelper.getRandInt(1, 30),
                            ChanceHelper.getRandInt(50, 120), "&6&lJesienna Szarfa").clone();
                } else if (reward.getReward().getType().equals(Material.IRON_BLOCK)) {
                    player.sendMessage(Utils.format("&8- &6&lLosowe Akcesorium &7x1"));
                    getRandomAkce(player);
                    return;
                } else item.setAmount(ChanceHelper.getRandInt(reward.getMinAmount(), reward.getMaxAmount()));
                player.getInventory().addItem(item);
                player.sendMessage(Utils.format("&8- " + item.getItemMeta().getDisplayName() + " &7x" + item.getAmount()));
            }
        });
    }

    private static void getRandomAkce(final Player player) {
        final int lvl = rpgcore.getUserManager().find(player.getUniqueId()).getLvl();
        final int szczescie = rpgcore.getBonusesManager().find(player.getUniqueId()).getBonusesUser().getSzczescie();
        if (lvl < 10) {
            Akce1_10.getDrop(player, szczescie);
        } else if (lvl < 20) {
            Akce10_20.getDrop(player, szczescie);
        } else if (lvl < 30) {
            Akce20_30.getDrop(player, szczescie);
        } else if (lvl < 40) {
            Akce30_40.getDrop(player, szczescie);
        } else if (lvl < 50) {
            Akce40_50.getDrop(player, szczescie);
        } else if (lvl < 60) {
            Akce50_60.getDrop(player, szczescie);
        } else if (lvl < 70) {
            Akce60_70.getDrop(player, szczescie);
        } else if (lvl < 80) {
            Akce70_80.getDrop(player, szczescie);
        } else if (lvl < 90) {
            Akce80_90.getDrop(player, szczescie);
        } else if (lvl < 100) {
            Akce90_100.getDrop(player, szczescie);
        } else if (lvl < 110) {
            Akce100_110.getDrop(player, szczescie);
        } else if (lvl < 120) {
            Akce110_120.getDrop(player, szczescie);
        } else if (lvl <= 130) {
            Akce120_130.getDrop(player, szczescie);
        }
    }

}
