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
    public void openGUI(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&6&lZmiana Bonusow"));


        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 8).setName(" ").toItemStack());
        }
        gui.setItem(13, new ItemBuilder(Material.IRON_FENCE).setName("&cMiejsce na Miecz/Zbroje").toItemStack());
        gui.setItem(14, new ItemBuilder(Material.ANVIL).setName("&6&lZmien Bonusy").toItemStack());

        player.openInventory(gui);
    }

    public ItemStack ItemToBlock() {
        return new ItemBuilder(Material.IRON_FENCE).setName("&cMiejsce na Miecz/Zbroje").toItemStack();
    }

    public ItemStack rollNewBonuses(final ItemStack is, final Player player) {
        final ItemMeta meta = is.getItemMeta();
        if (meta.getLore().contains(Utils.format("&8------------{&9&lMagiczne Zaczarowanie&8}------------"))) {
            final List<String> lore = meta.getLore();
            final int index = lore.indexOf(Utils.format("&8------------{&9&lMagiczne Zaczarowanie&8}------------"));
            lore.remove(index - 1);
            lore.remove(Utils.format("&8------------{&9&lMagiczne Zaczarowanie&8}------------"));
            if (is.getType().toString().contains("_SWORD")) {
                lore.remove(Utils.format("&eDodatkowe Obrazenia: &f+" + Utils.getTagInt(is, "dodatkowe")));
                lore.remove(Utils.format("&eSilny Na " + Utils.getTagString(is, "silny-na-mob") + "&7: &f+" + Utils.getTagDouble(is, "silny-na-val") + "%"));
            } else {
                lore.remove(Utils.format("&eOdpornosc Na Potwory: &f+" + Utils.getTagDouble(is, "defMoby") + "%"));
                lore.remove(Utils.format("&eSzansa Na Wzmocnienie Ciosu Krytycznego: &f+" + Utils.getTagDouble(is, "szansaWzmKryt") + "%"));
                lore.remove(Utils.format("&e7Szczescie: &f+" + Utils.getTagInt(is, "szczescie")));
            }
            lore.remove(Utils.format("&cWymagany Poziom: &6" + Utils.getTagInt(is, "lvl")));
            lore.remove(Utils.format("&8------------{&9&lMagiczne Zaczarowanie&8}------------"));
            meta.setLore(Utils.format(lore));

            is.setItemMeta(meta);
        }
        ItemStack toReturn;
        final int lvl = RPGCORE.getInstance().getUserManager().find(player.getUniqueId()).getLvl();
        int loreLvl;
        if (is.getType().toString().contains("_SWORD")) {
            if (lvl < 80) {
                toReturn = rollFirstBonus(is, 1, 50);
                toReturn = rollSecondBonus(toReturn.clone(), 0.01, 10);
//                toReturn = rollThirdBonus(toReturn.clone(), 0.01, 5);
//                toReturn = rollFourthBonus(toReturn.clone(), 0.01, 5);
//                toReturn = rollFifthBonus(toReturn.clone(), 1, 10);
                loreLvl = 50;
            } else {
                toReturn = rollFirstBonus(is, 1, 200);
                toReturn = rollSecondBonus(toReturn.clone(), 0.01, 20);
//                toReturn = rollThirdBonus(toReturn.clone(), 0.01, 10);
//                toReturn = rollFourthBonus(toReturn.clone(), 0.01, 10);
//                toReturn = rollFifthBonus(toReturn.clone(), 1, 20);
                loreLvl = 80;
            }

            toReturn = new ItemBuilder(toReturn.clone()).setLoreCrafting(toReturn.clone().getItemMeta().getLore(), Arrays.asList(
                    " ",
                    "&8------------{&9&lMagiczne Zaczarowanie&8}------------",
                    "&eDodatkowe Obrazenia: &f+" + Utils.getTagInt(toReturn, "dodatkowe"),
                    "&eSilny Na " + Utils.getTagString(toReturn, "silny-na-mob") + "&7: &f+" + Utils.getTagDouble(toReturn, "silny-na-val") + "%",
                    "&cWymagany Poziom: &6" + loreLvl,
                    "&8------------{&9&lMagiczne Zaczarowanie&8}------------"
            )).addTagInt("lvl", loreLvl).toItemStack().clone();
        } else {
            net.minecraft.server.v1_8_R3.ItemStack nmsStack = org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack.asNMSCopy(is);
            NBTTagCompound tag = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
            tag.setDouble("defMoby", ChanceHelper.getRandDouble(1, 10));
            tag.setDouble("szansaWzmKryt", ChanceHelper.getRandDouble(0.1, 2.5));
            tag.setInt("szczescie", ChanceHelper.getRandInt(1, 5));
            nmsStack.setTag(tag);
            toReturn = CraftItemStack.asBukkitCopy(nmsStack);

            toReturn = new ItemBuilder(toReturn.clone()).setLoreCrafting(toReturn.clone().getItemMeta().getLore(), Arrays.asList(
                    " ",
                    "&8------------{&9&lMagiczne Zaczarowanie&8}------------",
                    "&7Odpornosc Na Potwory: &f+" + Utils.getTagDouble(toReturn, "defMoby") + "%",
                    "&7Szansa Na Wzmocnienie Ciosu Krytycznego: &f+" + Utils.getTagDouble(toReturn, "szansaWzmKryt") + "%",
                    "&7Szczescie: &f+" + Utils.getTagInt(toReturn, "szczescie"),
                    "&cWymagany Poziom: &645",
                    "&8------------{&9&lMagiczne Zaczarowanie&8}------------"
            )).addTagInt("lvl", 45).toItemStack().clone();
        }


        return toReturn.clone();
    }

    public final ItemStack rollFirstBonus(final ItemStack is, final int min, final int max) {
        net.minecraft.server.v1_8_R3.ItemStack nmsStack = org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack.asNMSCopy(is);
        NBTTagCompound tag = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
        tag.setInt("dodatkowe", ChanceHelper.getRandInt(min, max));

        final ItemStack toReturn = CraftItemStack.asBukkitCopy(nmsStack);
        return toReturn;
    }

    public final ItemStack rollSecondBonus(final ItemStack is, final double min, final double max) {
        net.minecraft.server.v1_8_R3.ItemStack nmsStack = org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack.asNMSCopy(is);
        NBTTagCompound tag = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
        tag.setString("silny-na-mob", getRandomMob());
        tag.setDouble("silny-na-val", ChanceHelper.getRandDouble(min, max));

        final ItemStack toReturn = CraftItemStack.asBukkitCopy(nmsStack);
        return toReturn;
    }

    private String getRandomMob() {
        final List<String> mobs = Arrays.asList("&8Najemnik", "&2Goblin", "&7Goryl", "&8Zjawa", "&3Straznik Swiatyni", "&bMrozny Wilk", "&6Zywiolak Ognia", "&fMroczna Dusza");
        return mobs.get(ChanceHelper.getRandInt(0, mobs.size() - 1));
    }

    /*public final ItemStack rollThirdBonus(final ItemStack is, final double min, final double max) {
        net.minecraft.server.v1_8_R3.ItemStack nmsStack = org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack.asNMSCopy(is);
        NBTTagCompound tag = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
        tag.setDouble("krytyk", ChanceHelper.getRandDouble(min, max));

        final ItemStack toReturn = CraftItemStack.asBukkitCopy(nmsStack);
        return toReturn;
    }

    public final ItemStack rollFourthBonus(final ItemStack is, final double min, final double max) {
        net.minecraft.server.v1_8_R3.ItemStack nmsStack = org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack.asNMSCopy(is);
        NBTTagCompound tag = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
        tag.setString("silny-lvl", getRandomLvl());
        tag.setDouble("silny-lvl-val", ChanceHelper.getRandDouble(min, max));

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
        tag.setDouble("przeszywka", ChanceHelper.getRandDouble(min, max));

        final ItemStack toReturn = CraftItemStack.asBukkitCopy(nmsStack);
        return toReturn;
    }*/
}
