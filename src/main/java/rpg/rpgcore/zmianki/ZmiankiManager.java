package rpg.rpgcore.zmianki;

import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.ChanceHelper;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.Arrays;
import java.util.List;

public class ZmiankiManager {
    public void openGUI(final Player player, final ItemStack is) {
        final Inventory gui = Bukkit.createInventory(null, 54, Utils.format("&6&lZmiana Bonusow"));


        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 8).setName(" ").toItemStack());
        }
        if (is == null) {
            gui.setItem(13, new ItemBuilder(Material.AIR).toItemStack());
        } else {
            gui.setItem(13, is.clone());
            gui.setItem(22, new ItemBuilder(Material.REDSTONE_TORCH_ON).setName("&4&lInformacje").setLore(Arrays.asList(
                    "&8Dostepne sa 2 rodzaje bonusow: na 50lvl i na 80lvl",
                    "",
                    "&8Kliknij &6&l1 Zwojem Zaczarowania&8, zeby",
                    "&8wylosowac nowe bonusy!",
                    "",
                    "&8Pamietaj! Nie mozesz klikac wiecej, niz &c1 &8przedmiotem",
                    "",
                    "&c&lPrzedmiot dostaje nowy wymagany poziom rowny 50!",
                    "&8(jesli byl wiekszy, to zostaje wiekszy)")).toItemStack());
            if (is.getType().toString().contains("_SWORD")) {
                if (Utils.getTagInt(is, "dodatkowe") == 0) {
                    gui.setItem(38, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName("&c&lWylosuj Bonus!").toItemStack());
                } else {
                    gui.setItem(38, new ItemBuilder(Material.BOOK_AND_QUILL).setName("&7Dodatkowe Obrazenia: &f+" + Utils.getTagInt(is, "dodatkowe")).toItemStack().clone());
                }
                if (Utils.getTagString(is, "silny-na-mob").equals("") || Utils.getTagDouble(is, "silny-na-val") == 0) {
                    gui.setItem(39, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName("&c&lWylosuj Bonus!").toItemStack());
                } else {
                    gui.setItem(39, new ItemBuilder(Material.BOOK_AND_QUILL).setName("&7Silny Na " + Utils.getTagString(is, "silny-na-mob") + ": &f+" + Utils.getTagDouble(is, "silny-na-val") + "%").toItemStack().clone());
                }
                if (Utils.getTagDouble(is, "krytyk") == 0) {
                    gui.setItem(40, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName("&c&lWylosuj Bonus!").toItemStack());
                } else {
                    gui.setItem(40, new ItemBuilder(Material.BOOK_AND_QUILL).setName("&7Szansa Na Cios Krytyczny: &f+" + Utils.getTagDouble(is, "krytyk") + "%").toItemStack().clone());
                }
                if (Utils.getTagString(is, "silny-lvl").equals("") || Utils.getTagDouble(is, "silny-lvl-val") == 0) {
                    gui.setItem(41, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName("&c&lWylosuj Bonus!").toItemStack());
                } else {
                    gui.setItem(41, new ItemBuilder(Material.BOOK_AND_QUILL).setName("&7Silny Na " + Utils.getTagString(is, "silny-lvl") + " poziomy: &f+" + Utils.getTagDouble(is, "silny-lvl-val") + "%").toItemStack().clone());
                }
                if (Utils.getTagDouble(is, "przebicie-pancerza") == 0) {
                    gui.setItem(42, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName("&c&lWylosuj Bonus!").toItemStack());
                } else {
                    gui.setItem(42, new ItemBuilder(Material.BOOK_AND_QUILL).setName("&7Przebicie Pancerza: &f+" + Utils.getTagDouble(is, "przebicie-pancerza") + "%").toItemStack().clone());
                }
            }

        }

        player.openInventory(gui);
    }


    public ItemStack rollNewBonuses(final ItemStack is, final Player player) {
        final ItemMeta meta = is.getItemMeta();
        if (meta.getLore().contains(Utils.format("&8------------{&dMagiczne Zaczarowanie&8}------------"))) {
            final List<String> lore = meta.getLore();
            final int index = lore.indexOf(Utils.format("&8------------{&dMagiczne Zaczarowanie&8}------------"));
            lore.remove(index - 1);
            lore.remove(Utils.format("&8------------{&dMagiczne Zaczarowanie&8}------------"));
            if (is.getType().toString().contains("_SWORD")) {
                lore.remove(Utils.format("&7Dodatkowe Obrazenia: &f+" + Utils.getTagInt(is, "dodatkowe")));
                lore.remove(Utils.format("&7Silny Na " + Utils.getTagString(is, "silny-na-mob") + "&7: &f+" + Utils.getTagDouble(is, "silny-na-val") + "%"));
                lore.remove(Utils.format("&7Szansa Na Cios Krytyczny: &f+" + Utils.getTagDouble(is, "krytyk") + "%"));
                lore.remove(Utils.format("&7Silny Na " + Utils.getTagString(is, "silny-lvl") + " &7poziomy: &f+" + Utils.getTagDouble(is, "silny-lvl-val") + "%"));
                lore.remove(Utils.format("&7Przeszycie Bloku Ciosu: &f+" + Utils.getTagDouble(is, "przeszywka") + "%"));
            } else {
                // ZBROJA
            }
            lore.remove(Utils.format("&cWymagany Poziom: &6" + Utils.getTagInt(is, "lvl")));
            lore.remove(Utils.format("&8------------{&dMagiczne Zaczarowanie&8}------------"));
            meta.setLore(Utils.format(lore));

            is.setItemMeta(meta);
        }
        ItemStack toReturn = null;
        final int lvl = RPGCORE.getInstance().getUserManager().find(player.getUniqueId()).getLvl();
        int loreLvl = 0;
        if (lvl < 80) {
            toReturn = rollFirstBonus(is, 1, 50);
            toReturn = rollSecondBonus(toReturn.clone(), 0.01, 10);
            toReturn = rollThirdBonus(toReturn.clone(), 0.01, 5);
            toReturn = rollFourthBonus(toReturn.clone(), 0.01, 5);
            toReturn = rollFifthBonus(toReturn.clone(), 1, 10);
            loreLvl = 50;
        } else {
            toReturn = rollFirstBonus(is, 1, 200);
            toReturn = rollSecondBonus(toReturn.clone(), 0.01, 20);
            toReturn = rollThirdBonus(toReturn.clone(), 0.01, 10);
            toReturn = rollFourthBonus(toReturn.clone(), 0.01, 10);
            toReturn = rollFifthBonus(toReturn.clone(), 1, 20);
            loreLvl = 80;
        }
            toReturn = new ItemBuilder(toReturn.clone()).setLoreCrafting(toReturn.clone().getItemMeta().getLore(), Arrays.asList(
                    " ",
                    "&8------------{&dMagiczne Zaczarowanie&8}------------",
                    "&7Dodatkowe Obrazenia: &f+" + Utils.getTagInt(toReturn, "dodatkowe"),
                    "&7Silny Na " + Utils.getTagString(toReturn, "silny-na-mob") + "&7: &f+" + Utils.getTagDouble(toReturn, "silny-na-val") + "%",
                    "&7Szansa Na Cios Krytyczny: &f+" + Utils.getTagDouble(toReturn, "krytyk") + "%",
                    "&7Silny Na " + Utils.getTagString(toReturn, "silny-lvl") + " &7poziomy: &f+" + Utils.getTagDouble(toReturn, "silny-lvl-val") + "%",
                    "&7Przeszycie Bloku Ciosu: &f+" + Utils.getTagDouble(toReturn, "przeszywka") + "%",
                    "&cWymagany Poziom: &6" + loreLvl,
                    "&8------------{&dMagiczne Zaczarowanie&8}------------"
            )).addTagInt("lvl", loreLvl).toItemStack().clone();

        return toReturn.clone();
    }

    public final ItemStack rollFirstBonus(final ItemStack is, final int min, final int max) {
        net.minecraft.server.v1_8_R3.ItemStack nmsStack = org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack.asNMSCopy(is);
        NBTTagCompound tag = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
        if (is.getType().toString().contains("_SWORD")) {
            tag.setInt("dodatkowe", ChanceHelper.getRandInt(min, max));
        } else {
            //ZBROJA
        }
        final ItemStack toReturn = CraftItemStack.asBukkitCopy(nmsStack);
        return toReturn;
    }

    public final ItemStack rollSecondBonus(final ItemStack is, final double min, final double max) {
        net.minecraft.server.v1_8_R3.ItemStack nmsStack = org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack.asNMSCopy(is);
        NBTTagCompound tag = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
        if (is.getType().toString().contains("_SWORD")) {
            tag.setString("silny-na-mob", getRandomMob());
            tag.setDouble("silny-na-val", ChanceHelper.getRandDouble(min, max));
        } else {
            //ZBROJA
        }
        final ItemStack toReturn = CraftItemStack.asBukkitCopy(nmsStack);
        return toReturn;
    }

    private String getRandomMob() {
        final List<String> mobs = Arrays.asList("&8Najemnik", "&2Goblin", "&7Goryl", "&8Zjawa", "&3Straznik Swiatyni", "&bMrozny Wilk", "&6Zywiolak Ognia", "&fMroczna Dusza");
        return mobs.get(ChanceHelper.getRandInt(0, mobs.size() - 1));
    }

    public final ItemStack rollThirdBonus(final ItemStack is, final double min, final double max) {
        net.minecraft.server.v1_8_R3.ItemStack nmsStack = org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack.asNMSCopy(is);
        NBTTagCompound tag = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
        if (is.getType().toString().contains("_SWORD")) {
            tag.setDouble("krytyk", ChanceHelper.getRandDouble(min, max));
        } else {
            //ZBROJA
        }
        final ItemStack toReturn = CraftItemStack.asBukkitCopy(nmsStack);
        return toReturn;
    }

    public final ItemStack rollFourthBonus(final ItemStack is, final double min, final double max) {
        net.minecraft.server.v1_8_R3.ItemStack nmsStack = org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack.asNMSCopy(is);
        NBTTagCompound tag = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
        if (is.getType().toString().contains("_SWORD")) {
            tag.setString("silny-lvl", getRandomLvl());
            tag.setDouble("silny-lvl-val", ChanceHelper.getRandDouble(min, max));
        } else {
            //ZBROJA
        }
        final ItemStack toReturn = CraftItemStack.asBukkitCopy(nmsStack);
        return toReturn;
    }

    private String getRandomLvl() {
        final List<String> lvls = Arrays.asList("mniejsze", "rowne", "wyzsze");
        return lvls.get(ChanceHelper.getRandInt(0, lvls.size() - 1));
    }


    public final ItemStack rollFifthBonus(final ItemStack is, final double min, final double max) {
        net.minecraft.server.v1_8_R3.ItemStack nmsStack = org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack.asNMSCopy(is);
        NBTTagCompound tag = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
        if (is.getType().toString().contains("_SWORD")) {
            tag.setDouble("przeszywka", ChanceHelper.getRandDouble(min, max));
        } else {
            //ZBROJA
        }
        final ItemStack toReturn = CraftItemStack.asBukkitCopy(nmsStack);
        return toReturn;
    }
}
