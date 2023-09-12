package rpg.rpgcore.npc.rybak.helpers;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.npc.rybak.enums.WedkaExp;
import rpg.rpgcore.npc.rybak.objects.RybakUser;
import rpg.rpgcore.npc.rybak.objects.StaruszekUser;
import rpg.rpgcore.osiagniecia.objects.OsUser;
import rpg.rpgcore.utils.ChanceHelper;
import rpg.rpgcore.utils.DoubleUtils;
import rpg.rpgcore.utils.Utils;

import java.util.List;
import java.util.Set;

public class RybakHelper {

    public static void getDrop(final Player player) {
        final OsUser osUser = RPGCORE.getInstance().getOsManager().find(player.getUniqueId());
        osUser.setRybakProgress(osUser.getRybakProgress() + 1);
        if (RPGCORE.getInstance().getMagazynierNPC().find(player.getUniqueId()).getMissions().getSelectedMission() == 1) {
            RPGCORE.getInstance().getMagazynierNPC().find(player.getUniqueId()).getMissions().setProgress(RPGCORE.getInstance().getMagazynierNPC().find(player.getUniqueId()).getMissions().getProgress() + 1);
            RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataMagazynier(player.getUniqueId(), RPGCORE.getInstance().getMagazynierNPC().find(player.getUniqueId())));
        }
        if (player.getItemInHand() == null || !player.getItemInHand().getType().equals(Material.FISHING_ROD)) {
            player.sendMessage(Utils.format("&8[&c✘&8] &cCos poszlo nie tak lub nie masz wedki w rece!"));
            return;
        }

        final Set<ProtectedRegion> playerRegion = RPGCORE.getWorldGuard().getRegionManager(player.getWorld()).getApplicableRegions(player.getLocation()).getRegions();

        if (playerRegion == null) {
            player.sendMessage(Utils.format("&8[&c✘&8] &cCos poszlo nie tak!"));
            return;
        }

        if (playerRegion.isEmpty()) {
            player.sendMessage(Utils.format("&8[&c✘&8] &cCos poszlo nie tak!"));
            return;
        }


        final String region = playerRegion.stream().findFirst().get().getId();

        final double doubleDrop = Utils.getTagDouble(player.getItemInHand(), "doubleDrop");
        int exp = 0;

        switch (region) {
            case "rybak-wyspa1":
                exp = addRewardWyspa1(player, ChanceHelper.getChance(doubleDrop));
                break;
        }


        increaseExp(player.getItemInHand(), exp);

       RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataOs(player.getUniqueId(), osUser));


    }


    private static int addRewardWyspa1(final Player player, final boolean doubleDrop) {
        final ItemStack reward = RPGCORE.getInstance().getRybakNPC().getWyspa1Drop(player).clone();
        final StaruszekUser user = RPGCORE.getInstance().getRybakNPC().find(player.getUniqueId()).getStaruszekUser();

        final int mission = user.getMission();

        if (doubleDrop) {
            if (mission == 25) user.setProgress(user.getProgress() + 1);
            reward.setAmount(reward.getAmount() * 2);
        }

        int exp = 0;

        switch (Utils.removeColor(reward.getItemMeta().getDisplayName())) {
            case "Karp":
                exp = reward.getAmount();
                if (mission == 1 || mission == 15 || mission == 26)
                    user.setProgress(user.getProgress() + reward.getAmount());
                break;
            case "Leszcz":
                exp = 2 * reward.getAmount();
                if (mission == 2 || mission == 16 || mission == 26)
                    user.setProgress(user.getProgress() + reward.getAmount());
                break;
            case "Karas":
                exp = 2 * reward.getAmount();
                if (mission == 3 || mission == 17 || mission == 26)
                    user.setProgress(user.getProgress() + reward.getAmount());
                break;
            case "Szczupak":
                exp = 3 * reward.getAmount();
                if (mission == 4 || mission == 18 || mission == 26)
                    user.setProgress(user.getProgress() + reward.getAmount());
                break;
            case "Wegorz":
                exp = 3 * reward.getAmount();
                if (mission == 5 || mission == 19 || mission == 26)
                    user.setProgress(user.getProgress() + reward.getAmount());
                break;
            case "Zniszczony But":
                exp = 3 * reward.getAmount();
                reward.setAmount(1);
                if (mission == 11) user.setProgress(user.getProgress() + 1);
                break;
            case "Glina":
                exp = 3 * reward.getAmount();
                reward.setAmount(1);
                if (mission == 12) user.setProgress(user.getProgress() + 1);
                break;
            case "Zylka":
                exp = 3 * reward.getAmount();
                reward.setAmount(1);
                if (mission == 13) user.setProgress(user.getProgress() + 1);
                break;
            case "Wilgotny Wegiel":
                exp = 2 * reward.getAmount();
                if (mission == 14) user.setProgress(user.getProgress() + reward.getAmount());
                break;

        }
        player.getInventory().addItem(reward.clone());
        RPGCORE.getInstance().getNmsManager().sendActionBar(player, "&3Pomyslnie wylowiono: " + reward.getItemMeta().getDisplayName() + "&3! &a+" + exp + " EXP" + (doubleDrop ? " &6&l(x2)" : ""));
        return exp;
    }


    public static void increaseExp(final ItemStack wedka, final int exp) {
        if (wedka == null || !wedka.getType().equals(Material.FISHING_ROD)) {
            return;
        }

        final int lvl = Utils.getTagInt(wedka, "lvl");

        int udanePolowy = Utils.getTagInt(wedka, "udanePolowy");
        udanePolowy++;
        Utils.setTagInt(wedka, "udanePolowy", udanePolowy, true);
        final ItemMeta meta = wedka.getItemMeta();
        final List<String> lore = meta.getLore();
        lore.set(2, "&7Udane Polowy: &f" + udanePolowy);
        int currentExp = Utils.getTagInt(wedka, "exp");
        final int reqExp = Utils.getTagInt(wedka, "reqExp");
        if (!(wedka.getItemMeta().getDisplayName().contains("Stara Wedka") && lvl == 25)) {
            currentExp += exp;
            Utils.setTagInt(wedka, "exp", currentExp, true);

            final RybakUser user = RPGCORE.getInstance().getRybakNPC().find(RPGCORE.getInstance().getUserManager().find(Utils.getTagString(wedka, "owner")).getId());
            user.setWylowioneRyby(udanePolowy);
            user.setExpWedki(currentExp);

            if (lvl == 75) {
                lore.set(1, "&7Exp: &f" + currentExp + "&7/&4&lMAX");
            } else {
                lore.set(1, "&7Exp: &f" + currentExp + "&7/&f" + reqExp);
            }
        }

        meta.setLore(Utils.format(lore));

        wedka.setItemMeta(meta);

        if (lvl == 75) return;

        if (currentExp >= reqExp) {
            switch (Utils.removeColor(wedka.getItemMeta().getDisplayName())) {
                case "Stara Wedka":
                    if (lvl == 25) return;
                    break;
            }
            increaseLvl(wedka);
        }
    }

    public static void increaseLvl(final ItemStack wedka) {
        if (wedka == null || !wedka.getType().equals(Material.FISHING_ROD)) {
            return;
        }
        final int lvl = Utils.getTagInt(wedka, "lvl") + 1;

        final WedkaExp stats = WedkaExp.getWedkaExp(lvl + 1);
        final double doubleDrop = Utils.getTagDouble(wedka, "doubleDrop");

        final RybakUser user = RPGCORE.getInstance().getRybakNPC().find(RPGCORE.getInstance().getUserManager().find(Utils.getTagString(wedka, "owner")).getId());
        user.setLvlWedki(lvl);
        user.setExpWedki(0);
        user.setPodwojnyDrop(doubleDrop + stats.getDropx2());

        Utils.setTagInt(wedka, "lvl", lvl, true);
        Utils.setTagDouble(wedka, "exp", 0, true);
        Utils.setTagDouble(wedka, "reqExp", stats.getExp(), true);
        Utils.setTagDouble(wedka, "doubleDrop", doubleDrop + stats.getDropx2(), true);


        final ItemMeta meta = wedka.getItemMeta();
        final List<String> lore = meta.getLore();
        if (lvl == 75) {
            lore.set(0, "&7Poziom: &4&lMAX");
            lore.set(1, "&7Exp: &f0&7/&4&lMAX");
        } else {
            lore.set(0, "&7Poziom: &f" + lvl);
            lore.set(1, "&7Exp: &f0&7/&f" + stats.getExp());
        }
        lore.set(5, "&7Szansa Na Podwojenie Polowu: &f" + (DoubleUtils.round(doubleDrop + stats.getDropx2(), 1)) + "&f%");

//        if (meta.getDisplayName().contains("dalsza wedka")) {
//            lore.set(x, "kolejny bonus");
//        }

        meta.setLore(Utils.format(lore));

        wedka.setItemMeta(meta);
        if (lvl <= 50 && lvl % 10 == 0) {
            wedka.addEnchantment(Enchantment.LURE, lvl / 10);
        }
        if (lvl % 25 == 0) {
            wedka.addEnchantment(Enchantment.LUCK, lvl / 25);
        }
    }
}
