package rpg.rpgcore.npc.rybak.helpers;

import com.google.common.collect.Sets;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.chests.Items;
import rpg.rpgcore.dodatki.bony.enums.BonType;
import rpg.rpgcore.npc.rybak.enums.LureBonuses;
import rpg.rpgcore.npc.rybak.enums.WedkaExp;
import rpg.rpgcore.npc.rybak.objects.MlodszyRybakUser;
import rpg.rpgcore.npc.rybak.objects.RybakUser;
import rpg.rpgcore.npc.rybak.objects.StaruszekUser;
import rpg.rpgcore.osiagniecia.objects.OsUser;
import rpg.rpgcore.utils.ChanceHelper;
import rpg.rpgcore.utils.DoubleUtils;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.npc.RybakItems;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RybakHelper {

    public static void getDrop(final Player player, final Location playerLocation, final Location hookLocation) {
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

        int amount = 1;
        if (ChanceHelper.getChance(Utils.getTagDouble(player.getItemInHand(), "doubleDrop"))) amount = 2;
        if (Utils.getTagString(player.getItemInHand(), "krysztal").equals("Rzecznego Krola")) {
            if (ChanceHelper.getChance(Utils.getTagDouble(player.getItemInHand(), "krysztalValue"))) amount = 3;
        }
        int exp = 0;

        switch (region) {
            case "rybak-wyspa1":
                exp = addRewardWyspa1(player, amount);
                break;
            case "rybak-wyspa2":
                exp = addRewardWyspa2(player, amount, playerLocation, hookLocation);
                break;
        }

        if (exp != 0) increaseExp(player.getItemInHand(), exp);

        RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataOs(player.getUniqueId(), osUser));


    }


    private static int addRewardWyspa1(final Player player, final int dropAmount) {
        final ItemStack reward = RPGCORE.getInstance().getRybakNPC().getWyspa1Drop(player);
        if (reward == null) {
            player.sendMessage(Utils.format("&8[&c✘&8] &cNiestety ryba zerwala sie z linki..."));
            return 0;
        }
        final StaruszekUser user = RPGCORE.getInstance().getRybakNPC().find(player.getUniqueId()).getStaruszekUser();

        final int mission = user.getMission();

        if (dropAmount == 2) {
            if (mission == 25) user.setProgress(user.getProgress() + 1);
            reward.setAmount(reward.getAmount() * 2);
        }

        int exp = 0;

        switch (Utils.removeColor(reward.getItemMeta().getDisplayName())) {
            case "Karp":
                exp = reward.getAmount();
                if (mission == 1 || mission == 15 || mission == 26)
                    user.setProgress(user.getProgress() + 1);
                break;
            case "Leszcz":
                exp = 2 * reward.getAmount();
                if (mission == 2 || mission == 16 || mission == 26)
                    user.setProgress(user.getProgress() + 1);
                break;
            case "Karas":
                exp = 2 * reward.getAmount();
                if (mission == 3 || mission == 17 || mission == 26)
                    user.setProgress(user.getProgress() + 1);
                break;
            case "Szczupak":
                exp = 3 * reward.getAmount();
                if (mission == 4 || mission == 18 || mission == 26)
                    user.setProgress(user.getProgress() + 1);
                break;
            case "Wegorz":
                exp = 3 * reward.getAmount();
                if (mission == 5 || mission == 19 || mission == 26)
                    user.setProgress(user.getProgress() + 1);
                break;
            case "Zniszczony But":
                reward.setAmount(1);
                exp = 3 * reward.getAmount();
                if (mission == 11) user.setProgress(user.getProgress() + 1);
                break;
            case "Glina":
                reward.setAmount(1);
                exp = 3 * reward.getAmount();
                if (mission == 12) user.setProgress(user.getProgress() + 1);
                break;
            case "Zylka":
                reward.setAmount(1);
                exp = 3 * reward.getAmount();
                if (mission == 13) user.setProgress(user.getProgress() + 1);
                break;
            case "Wilgotny Wegiel":
                exp = 2 * reward.getAmount();
                if (mission == 14) user.setProgress(user.getProgress() + 1);
                break;
            case "Kufer Rybacki":
                reward.setAmount(1);
                exp = 25 * reward.getAmount();
                break;

        }
        player.getInventory().addItem(reward.clone());
        RPGCORE.getInstance().getNmsManager().sendActionBar(player, "&3Pomyslnie wylowiono: " + reward.getItemMeta().getDisplayName() + "&3! &a+" + exp + " EXP" + (reward.getAmount() == 2 ? " &6&l(x2)" : ""));
        return exp;
    }

    private static int addRewardWyspa2(final Player player, final int dropAmount, final Location playerLocation, final Location hookLocation) {
        ItemStack reward = RPGCORE.getInstance().getRybakNPC().getWyspa2Drop(player).clone();
        if (reward == null) {
            player.sendMessage(Utils.format("&8[&c✘&8] &cNiestety ryba zerwala sie z linki..."));
            return 0;
        }
        final MlodszyRybakUser user = RPGCORE.getInstance().getRybakNPC().find(player.getUniqueId()).getMlodszyRybakUser();

        final int mission = user.getMission();


        reward.setAmount(dropAmount);

        final String krysztal = Utils.getTagString(player.getItemInHand(), "krysztal");

        if (ChanceHelper.getChance(0.125 + Utils.getTagDouble(player.getItemInHand(), "zwyklyMob"))) {
            if (ChanceHelper.getChance(0.5 + (krysztal.equals("Wladcy Oceanow") ? Utils.getTagDouble(player.getItemInHand(), "krysztalValue") : 0))) {
                if (user.getMission() == 18) user.setProgress(user.getProgress() + 1);
                RPGCORE.getInstance().getRybakNPC().spawnPosejdon(playerLocation, hookLocation);

                Bukkit.getServer().broadcastMessage(" ");
                Bukkit.getServer().broadcastMessage(Utils.format("&7&lStara Fabryka &8>> &7Gracz &e" + player.getName() + " &7wylowil &3&lPosejdona&7!"));
                Bukkit.getServer().broadcastMessage(" ");

                RPGCORE.getInstance().getNmsManager().sendActionBar(player, "&3Pomyslnie wylowiono: &3&lPosejdona&3! &a+1 000 EXP");
                return 1000;
            }

            if (ChanceHelper.getChance(5 + (krysztal.equals("Zlotej Rybki") ? Utils.getTagDouble(player.getItemInHand(), "krysztalValue") : 0))) {
                if (user.getMission() == 17) user.setProgress(user.getProgress() + 1);

                Bukkit.getServer().broadcastMessage(" ");
                Bukkit.getServer().broadcastMessage(Utils.format("&7&lStara Fabryka &8>> &7Gracz &e" + player.getName() + " &7wylowil &b&lMorskiego Ksiecia&7!"));
                Bukkit.getServer().broadcastMessage(" ");

                RPGCORE.getInstance().getRybakNPC().spawnMorskiKsiaze(playerLocation, hookLocation);
                RPGCORE.getInstance().getNmsManager().sendActionBar(player, "&3Pomyslnie wylowiono: &b&lMorskiego Ksiecia&3! &a+625 EXP");
                return 625;
            }

            if (ChanceHelper.getChance(15 + (krysztal.equals("Oceanicznej Grozy") ? Utils.getTagDouble(player.getItemInHand(), "krysztalValue") : 0))) {
                if (user.getMission() == 16) user.setProgress(user.getProgress() + 1);

                RPGCORE.getInstance().getRybakNPC().spawnNurekGlebinowy(playerLocation, hookLocation);
                RPGCORE.getInstance().getNmsManager().sendActionBar(player, "&3Pomyslnie wylowiono: &9Nurka Glebinowego&3! &a+450 EXP");
                return 450;
            }

            if (ChanceHelper.getChance(37.5)) {
                if (user.getMission() == 15) user.setProgress(user.getProgress() + 1);

                RPGCORE.getInstance().getRybakNPC().spawnWodnyStwor(playerLocation, hookLocation);
                RPGCORE.getInstance().getNmsManager().sendActionBar(player, "&3Pomyslnie wylowiono: &bWodnego Stwora&3! &a+250 EXP");
                return 250;
            }

            player.sendMessage(Utils.format("&8[&c✘&8] &cNiestety stwor wody sploszyl sie i uciekl..."));
            return 0;
        }


        int exp = 0;


        switch (Utils.removeColor(reward.getItemMeta().getDisplayName())) {
            case "Dorsz":
                exp = reward.getAmount() + 2;
                if (mission == 1) user.setProgress(user.getProgress() + 1);
                break;
            case "Losos":
                exp = 2 * reward.getAmount() + 2;
                if (mission == 2) user.setProgress(user.getProgress() + 1);
                break;
            case "Plotka":
                exp = 2 * reward.getAmount() + 2;
                if (mission == 3) user.setProgress(user.getProgress() + 1);
                break;
            case "Sandacz":
                exp = 3 * reward.getAmount() + 2;
                if (mission == 4) user.setProgress(user.getProgress() + 1);
                break;
            case "Pstrag":
                exp = 3 * reward.getAmount() + 2;
                if (mission == 5) user.setProgress(user.getProgress() + 1);
                break;
            case "Okon":
                exp = 5 * reward.getAmount() + 2;
                if (mission == 6) user.setProgress(user.getProgress() + 1);
                break;
            case "Amur":
                exp = 5 * reward.getAmount() + 2;
                if (mission == 7) user.setProgress(user.getProgress() + 1);
                break;
            case "Podwodny Skarb":
                reward.setAmount(1);
                exp = 35;
                if (mission == 21) user.setProgress(user.getProgress() + 1);
                break;
            case "Niesamowity Przedmioty (Stara Fabryka)":
                reward.setAmount(1);
                exp = 125;
                if (mission == 20) user.setProgress(user.getProgress() + 1);
                break;
            case "Krysztal Czarnoksieznika":
                reward = RybakItems.getRandomKrysztal();

                Bukkit.getServer().broadcastMessage(" ");
                Bukkit.getServer().broadcastMessage(Utils.format("&7&lStara Fabryka &8>> &7Gracz &e" + player.getName() + " &7znalazl " + reward.getItemMeta().getDisplayName() + "&7!"));
                Bukkit.getServer().broadcastMessage(" ");

                exp = 75;
                if (mission == 19) user.setProgress(user.getProgress() + 1);
                break;

        }
        player.getInventory().addItem(reward.clone());
        RPGCORE.getInstance().getNmsManager().sendActionBar(player, "&3Pomyslnie wylowiono: " + reward.getItemMeta().getDisplayName() + "&3! &a+" + exp + " EXP" + (reward.getAmount() == 2 ? " &6&l(x2)" : ""));
        return exp;
    }


    public static void increaseExp(final ItemStack wedka, final int exp) {
        if (wedka == null || !wedka.getType().equals(Material.FISHING_ROD)) {
            return;
        }

        if (wedka.getItemMeta().hasEnchant(Enchantment.LUCK)) wedka.removeEnchantment(Enchantment.LUCK);


        final int lvl = Utils.getTagInt(wedka, "lvl");

        int udanePolowy = Utils.getTagInt(wedka, "udanePolowy");
        udanePolowy++;
        Utils.setTagInt(wedka, "udanePolowy", udanePolowy, true);
        if (lvl / 10 != 0) {
            Utils.setTagInt(wedka, "lure-lvl", lvl / 10, true);
            Utils.setTagInt(wedka, "lure-speed", LureBonuses.getLureFishingSpeed(lvl / 10), true);
        }
        ItemMeta meta = wedka.getItemMeta();
        final List<String> lore = meta.getLore();

        lore.set(2, "&7Udane Polowy: &f" + udanePolowy);

        int currentExp = Utils.getTagInt(wedka, "exp");
        final int reqExp = Utils.getTagInt(wedka, "reqExp");

        if ((wedka.getItemMeta().getDisplayName().contains("Stara Wedka") && lvl != 30) || ((wedka.getItemMeta().getDisplayName().contains("Slaba Wedka Rybacka") && lvl != 55))) {
            currentExp += exp;
            Utils.setTagInt(wedka, "exp", currentExp, true);
            meta = wedka.getItemMeta();
            final RybakUser user = RPGCORE.getInstance().getRybakNPC().find(RPGCORE.getInstance().getUserManager().find(Utils.getTagString(wedka, "owner")).getId());
            user.setWylowioneRyby(udanePolowy);
            user.setExpWedki(currentExp);

            if (lvl == 75) {
                lore.set(1, "&7Exp: &f" + currentExp + "&7/&4&lMAX");
            } else {
                lore.set(1, "&7Exp: &f" + currentExp + "&7/&f" + reqExp);
            }
        }

        if (wedka.getItemMeta().hasEnchant(Enchantment.LURE)) {
            wedka.removeEnchantment(Enchantment.LURE);
            if (lore.stream().noneMatch(s -> Utils.removeColor(s).contains("Lure"))) {
                final int lureLvl = Utils.getTagInt(wedka, "lure-lvl");
                final int fishingSpeed = LureBonuses.getLureFishingSpeed(lureLvl);
                lore.add(3, "");
                if (lureLvl == 7) {
                    lore.add(4, "&6Lure VII");
                } else {
                    lore.add(4, "&bLure " + Utils.intToRoman(lureLvl));
                }
                lore.add(5, "&7Daje Ci &3+" + fishingSpeed + " szybkosci lowienia");
            }
            if (!meta.hasItemFlag(ItemFlag.HIDE_ENCHANTS)) {
                meta.addEnchant(Enchantment.DURABILITY, 1, true);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            }
        }

        meta.setLore(Utils.format(lore));

        wedka.setItemMeta(meta);

        if (lvl == 75) return;

        if (currentExp >= reqExp) {
            switch (Utils.removeColor(wedka.getItemMeta().getDisplayName())) {
                case "Stara Wedka":
                    if (lvl == 30) return;
                    break;
                case "Slaba Wedka Rybacka":
                    if (lvl == 55) return;
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
        final double zwyklyMob = Utils.getTagDouble(wedka, "zwyklyMob");
        final double krysztalDrop = Utils.getTagDouble(wedka, "krysztalDrop");

        final RybakUser user = RPGCORE.getInstance().getRybakNPC().find(RPGCORE.getInstance().getUserManager().find(Utils.getTagString(wedka, "owner")).getId());
        user.setLvlWedki(lvl);
        user.setExpWedki(0);
        user.setPodwojnyDrop(doubleDrop + stats.getDropx2());

        Utils.setTagInt(wedka, "lvl", lvl, true);
        Utils.setTagDouble(wedka, "exp", 0, true);
        Utils.setTagDouble(wedka, "reqExp", stats.getExp(), true);
        Utils.setTagDouble(wedka, "doubleDrop", doubleDrop + stats.getDropx2(), true);
        Utils.setTagDouble(wedka, "zwyklyMob", zwyklyMob + stats.getMobDrop(), true);
        Utils.setTagDouble(wedka, "krysztalDrop", krysztalDrop + stats.getKrysztalDrop(), true);
        if (lvl % 10 == 0) {
            Utils.setTagInt(wedka, "lure-lvl", lvl / 10, true);
            Utils.setTagInt(wedka, "lure-speed", LureBonuses.getLureFishingSpeed(lvl / 10), true);
        }


        final ItemMeta meta = wedka.getItemMeta();
        final List<String> lore = meta.getLore();
        if (lvl == 75) {
            lore.set(0, "&7Poziom: &4&lMAX");
            lore.set(1, "&7Exp: &f0&7/&4&lMAX");
        } else {
            lore.set(0, "&7Poziom: &f" + lvl);
            lore.set(1, "&7Exp: &f0&7/&f" + stats.getExp());
        }


        switch (Utils.removeColor(meta.getDisplayName())) {
            case "Stara Wedka":
                if (lore.stream().noneMatch(s -> Utils.removeColor(s).contains("Lure"))) {
                    lore.set(5, "&7Szansa Na Podwojenie Polowu: &f" + (DoubleUtils.round(doubleDrop + stats.getDropx2(), 1)) + "&f%");
                } else {
                    lore.set(8, "&7Szansa Na Podwojenie Polowu: &f" + (DoubleUtils.round(doubleDrop + stats.getDropx2(), 1)) + "&f%");
                }
                break;
            case "Slaba Wedka Rybacka":
                lore.set(8, "&7Szansa Na Podwojenie Polowu: &f" + (DoubleUtils.round(doubleDrop + stats.getDropx2(), 1)) + "&f%");
                lore.set(9, "&7Szansa Na Zwyklego Potwora: &f" + (DoubleUtils.round(Utils.getTagDouble(wedka, "zwyklyMob"), 2)) + "&f%");
                lore.set(10, "&7Szansa Na Krysztal: &f" + (DoubleUtils.round(Utils.getTagDouble(wedka, "krysztalDrop"), 3)) + "&f%");
                break;
        }
        if (lvl % 10 == 0) {
            final int lureLvl = Utils.getTagInt(wedka, "lure-lvl");
            final int fishingSpeed = LureBonuses.getLureFishingSpeed(lureLvl);
            if (lore.stream().noneMatch(s -> Utils.removeColor(s).contains("Lure"))) {
                lore.add(3, "");
                if (lureLvl == 7) {
                    lore.add(4, "&6Lure VII");
                } else {
                    lore.add(4, "&bLure " + Utils.intToRoman(lureLvl));
                }
                lore.add(5, "&7Daje Ci &3+" + fishingSpeed + " szybkosci lowienia");
            } else {
                if (lureLvl == 7) {
                    lore.set(4, "&6Lure VII");
                } else {
                    lore.set(4, "&bLure " + Utils.intToRoman(lureLvl));
                }
                lore.set(5, "&7Daje Ci &3+" + fishingSpeed + " szybkosci lowienia");
            }
            if (!meta.hasItemFlag(ItemFlag.HIDE_ENCHANTS)) {
                meta.addEnchant(Enchantment.DURABILITY, 1, true);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            }
        }

        meta.setLore(Utils.format(lore));
        wedka.setItemMeta(meta);
    }

    public static void removeKrysztal(final ItemStack wedka, final Player player) {
        final String krysztal = Utils.getTagString(wedka, "krysztal");
        final double krysztalValue = Utils.getTagDouble(wedka, "krysztalValue");

        player.getInventory().addItem(RybakItems.getKrysztal(krysztal, krysztalValue).clone());

        Utils.setTagString(wedka, "krysztal", "BRAK");
        Utils.setTagDouble(wedka, "krysztalValue", 0, true);

        final ItemMeta meta = wedka.getItemMeta();
        final List<String> lore = meta.getLore();
        final int index = lore.indexOf(lore.stream().filter(s -> s.contains("Aktywny Krysztal")).collect(Collectors.toList()).get(0));

        lore.set(index, "&7Aktywny Krysztal: &cBRAK");

        meta.setLore(Utils.format(lore));
        wedka.setItemMeta(meta);
    }

    public static void addKrysztal(final ItemStack wedka, final ItemStack krysztal) {
        final double krysztalValue = Utils.getTagDouble(krysztal, "krysztalValue");

        Utils.setTagString(wedka, "krysztal", Utils.removeColor(krysztal.getItemMeta().getDisplayName()).replace("Krysztal ", "").trim());
        Utils.setTagDouble(wedka, "krysztalValue", krysztalValue, true);

        final ItemMeta meta = wedka.getItemMeta();
        final List<String> lore = meta.getLore();

        final int index = lore.indexOf(lore.stream().filter(s -> s.contains("Aktywny Krysztal")).collect(Collectors.toList()).get(0));

        lore.set(index, "&7Aktywny Krysztal: " + krysztal.getItemMeta().getDisplayName().replace(Utils.format("&5&lKrysztal "), "").trim() + " &8(&f" + krysztalValue + "%&8)");

        meta.setLore(Utils.format(lore));
        wedka.setItemMeta(meta);
    }


    private final static Set<Items> niesy = Sets.newHashSet(
            new Items("1", 0.01, new ItemBuilder(Material.ENCHANTED_BOOK).setName("&3&lMorskie Szczescie I").setLore(Arrays.asList(
                    "&7Gwarantuje &b+1.5% &7szansy na wylowienie",
                    "&2potwora&7, &dkrysztalu &7lub &3Niesamowitego Przedmiotu"
            )).toItemStack().clone(), 1),
            new Items("2", 1, new ItemBuilder(Material.ENCHANTED_BOOK).setName("&3&lMorskie Szczescie I").setLore(Arrays.asList(
                    "&7Gwarantuje &b+0.5% &7szansy na wylowienie",
                    "&2potwora&7, &dkrysztalu &7lub &3Niesamowitego Przedmiotu"
            )).toItemStack().clone(), 1)
    );


    public static ItemStack getRandomNies() {
        for (final Items item : niesy) {
            if (ChanceHelper.getChance(item.getChance())) {
                return item.getRewardItem().clone();
            }
        }
        return getRandomNies();
    }
}
