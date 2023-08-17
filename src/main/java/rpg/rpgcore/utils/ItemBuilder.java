package rpg.rpgcore.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ItemBuilder {
    private final ItemStack is;

    public ItemBuilder(Material material) {
        this.is = new ItemStack(material);
    }

    public ItemBuilder(Material mat, int amount) {
        this.is = new ItemStack(mat, amount, (short) 0);
    }

    public ItemBuilder(Material material, int amount, short data) {
        this.is = new ItemStack(material, amount, data);
    }

    public ItemBuilder(ItemStack item) {
        this.is = item;
    }

    public ItemBuilder setDurability(short durability) {
        this.is.setDurability(durability);
        return this;
    }

    public ItemBuilder setType(final Material material) {
        this.is.setType(material);
        return this;
    }

    public ItemBuilder hideFlag() {
        ItemMeta im = this.is.getItemMeta();
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        im.addItemFlags(ItemFlag.HIDE_DESTROYS);
        this.is.setItemMeta(im);
        return this;
    }

    public ItemBuilder addFlag(ItemFlag flag) {
        ItemMeta im = this.is.getItemMeta();
        im.addItemFlags(flag);
        this.is.setItemMeta(im);
        return this;
    }

    public ItemBuilder addEnchant(Enchantment enchant, int level) {
        ItemMeta im = this.is.getItemMeta();
        im.addEnchant(enchant, level, true);
        this.is.setItemMeta(im);
        return this;
    }

    public ItemBuilder addGlowing() {
        is.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
        ItemMeta im = this.is.getItemMeta();
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        im.addItemFlags(ItemFlag.HIDE_DESTROYS);
        this.is.setItemMeta(im);
        return this;
    }

    public ItemBuilder removeGlowing() {
        is.removeEnchantment(Enchantment.DURABILITY);
        ItemMeta im = this.is.getItemMeta();
        im.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
        im.removeItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        im.removeItemFlags(ItemFlag.HIDE_DESTROYS);
        this.is.setItemMeta(im);
        return this;
    }

    public ItemBuilder setName(String name) {
        ItemMeta im = this.is.getItemMeta();
        im.setDisplayName(Utils.format(name));
        this.is.setItemMeta(im);
        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        ItemMeta im = this.is.getItemMeta();
        im.setLore(Utils.format(lore));
        this.is.setItemMeta(im);
        return this;
    }

    public ItemBuilder setLoreCrafting(List<String> lore, List<String> lore1) {
        ItemMeta im = this.is.getItemMeta();
        List<String> lore3 = new ArrayList<>(lore);
        lore3.addAll(lore1);
        im.setLore(Utils.format(lore3));
        this.is.setItemMeta(im);
        return this;
    }

    public ItemBuilder addLoreLine(final String... lines) {
        final ItemMeta meta = this.is.getItemMeta();
        List<String> lore = meta.getLore();
        if (lore == null) {
            lore = new ArrayList<>();
        }
        for (final String line : lines) {
            lore.add(Utils.format(line));
        }
        meta.setLore(lore);
        this.is.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setSkullOwner(String owner) {
        try {
            SkullMeta im = (SkullMeta) this.is.getItemMeta();
            im.setOwner(owner);
            this.is.setItemMeta(im);
            return this;
        } catch (ClassCastException im) {
            // empty catch block
        }
        return this;
    }

    public ItemBuilder setSkullOwnerByURL(final String uuid, final String nick, final String texture, final String signature) {
        SkullMeta skullMeta = (SkullMeta) this.is.getItemMeta();
        GameProfile profile = new GameProfile(UUID.fromString(uuid), nick);
        profile.getProperties().put("textures", new Property("textures", texture, signature));
        Field profileField = null;
        try {
            profileField = skullMeta.getClass().getDeclaredField("profile");
        } catch (NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }
        profileField.setAccessible(true);
        try {
            profileField.set(skullMeta, profile);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        this.is.setItemMeta(skullMeta);
        return this;
    }

    public ItemBuilder setLeatherArmorColor(Color color) {
        LeatherArmorMeta im = (LeatherArmorMeta) this.is.getItemMeta();
        im.setColor(color);
        this.is.setItemMeta(im);
        return this;
    }

    public ItemBuilder setLeatherArmorColorHEX(int red, int green, int blue) {
        LeatherArmorMeta im = (LeatherArmorMeta) this.is.getItemMeta();
        im.setColor(Color.fromRGB(red, green, blue));
        this.is.setItemMeta(im);
        return this;
    }

    public ItemBuilder setLeatherArmorColorHEX(int rgb) {
        LeatherArmorMeta im = (LeatherArmorMeta) this.is.getItemMeta();
        im.setColor(Color.fromRGB(rgb));
        this.is.setItemMeta(im);
        return this;
    }

    public ItemBuilder setAmount(int amount) {
        this.is.setAmount(amount);
        return this;
    }

    public ItemBuilder addTagInt(final String tag, final int val) {
        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(this.is);
        NBTTagCompound tagCompound = nmsStack.hasTag() ? nmsStack.getTag() : new NBTTagCompound();
        tagCompound.setInt(tag, val);
        nmsStack.setTag(tagCompound);
        this.is.setItemMeta(CraftItemStack.getItemMeta(nmsStack));
        return this;
    }

    public ItemBuilder addTagString(final String tag, final String val) {
        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(this.is);
        NBTTagCompound tagCompound = nmsStack.hasTag() ? nmsStack.getTag() : new NBTTagCompound();
        tagCompound.setString(tag, val);
        nmsStack.setTag(tagCompound);
        this.is.setItemMeta(CraftItemStack.getItemMeta(nmsStack));
        return this;
    }

    public ItemBuilder addTagBoolean(final String tag, final boolean val) {
        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(this.is);
        NBTTagCompound tagCompound = nmsStack.hasTag() ? nmsStack.getTag() : new NBTTagCompound();
        tagCompound.setBoolean(tag, val);
        nmsStack.setTag(tagCompound);
        this.is.setItemMeta(CraftItemStack.getItemMeta(nmsStack));
        return this;
    }

    public ItemBuilder addTagDouble(final String tag, final double val) {
        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(this.is);
        NBTTagCompound tagCompound = nmsStack.hasTag() ? nmsStack.getTag() : new NBTTagCompound();
        tagCompound.setDouble(tag, val);
        nmsStack.setTag(tagCompound);
        this.is.setItemMeta(CraftItemStack.getItemMeta(nmsStack));
        return this;
    }

    public ItemStack toItemStack() {
        return this.is;
    }
}
