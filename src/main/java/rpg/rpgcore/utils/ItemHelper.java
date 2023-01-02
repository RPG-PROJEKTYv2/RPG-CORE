package rpg.rpgcore.utils;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.discord.EmbedUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ItemHelper {


    public static ItemStack createArmor(final String name, final Material itemType, final int prot, final int thorns) {
        final ItemBuilder set = new ItemBuilder(itemType);
        final List<String> lore = new ArrayList<>();

        set.setName(name);
        lore.add("&7Obrona: &f" + prot);
        lore.add("&7Ciernie: &f" + thorns);
        set.setLore(lore);
        if (thorns > 0 && itemType.toString().contains("_CHESTPLATE")) {
            set.addEnchant(Enchantment.THORNS, thorns);
        }

        set.hideFlag();
        set.addGlowing();
        set.addTagInt("prot", prot);
        set.addTagInt("thorns", thorns);
        set.addTagString("Wody", "false");
        set.addTagDouble("Wody-Bonus", 0);
        set.addTagString("Lodu", "false");
        set.addTagDouble("Lodu-Bonus", 0);
        if (String.valueOf(itemType).contains("_BOOTS")) {
            set.addTagString("Powietrza", "false");
            set.addTagDouble("Powietrza-Bonus", 0);
        } else {
            set.addTagString("Lasu", "false");
            set.addTagInt("Lasu-Bonus", 0);
        }

        return set.toItemStack();
    }

    public static ItemStack createSword(final String name, final Material itemType, final int dmg, final int moby, final boolean addGlowing) {
        final ItemBuilder sword = new ItemBuilder(itemType);
        final List<String> lore = new ArrayList<>();

        sword.setName(name);

        lore.add("&7Obrazenia: &c" + dmg);
        lore.add("&7Obrazenia na potwory: &c" + moby);
        sword.setLore(lore);

        sword.hideFlag();
        sword.addTagInt("dmg", dmg);
        sword.addTagInt("moby", moby);

        if (addGlowing) {
            sword.addGlowing();
        }
        sword.addTagString("Mroku", "false");
        sword.addTagDouble("Mroku-Bonus", 0);
        sword.addTagString("Blasku", "false");
        sword.addTagInt("Blasku-Bonus", 0);
        sword.addTagString("Ognia", "false");
        sword.addTagDouble("Ognia-Bonus", 0);


        return sword.toItemStack();
    }

    public static ItemStack setEnchants(final ItemStack is, final String type, final int v1, final int v2) {
        if (!is.getType().toString().contains("_CHESTPLATE")) {
            is.removeEnchantment(Enchantment.THORNS);
        }
        List<String> lore = new ArrayList<>();
        boolean addGlowing = false;
        final net.minecraft.server.v1_8_R3.ItemStack nmsStack = org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack.asNMSCopy(is);
        final net.minecraft.server.v1_8_R3.NBTTagCompound tag = (nmsStack.hasTag()) ? nmsStack.getTag() : new net.minecraft.server.v1_8_R3.NBTTagCompound();
        if (is.getItemMeta().hasLore()) {
            lore = is.getItemMeta().getLore();
            if (type.equalsIgnoreCase("zbroja")) {
                if (v1 > 0) {
                    lore.set(0, Utils.format("&7Obrona: &f" + v1));
                    tag.setInt("prot", v1);
                }
                if (v2 > 0) {
                    lore.set(1, Utils.format("&7Ciernie: &f" + v2));
                    tag.setInt("thorns", v2);
                    addGlowing = true;
                }
            } else if (type.equalsIgnoreCase("miecz")) {
                if (v1 > 0) {
                    lore.set(0, Utils.format("&7Obrazenia: &c" + v1));
                    tag.setInt("dmg", v1);
                }
                if (v2 > 0) {
                    lore.set(1, Utils.format("&7Obrazenia na potwory: &c" + v2));
                    tag.setInt("moby", v2);
                }
            }
        } else {
            if (type.equalsIgnoreCase("zbroja")) {
                if (v1 > 0) {
                    lore.add(Utils.format("&7Obrona: &f" + v1));
                    tag.setInt("prot", v1);
                }
                if (v2 > 0) {
                    lore.add(Utils.format("&7Ciernie: &f" + v2));
                    tag.setInt("thorns", v2);
                    addGlowing = true;
                }
            } else if (type.equalsIgnoreCase("miecz")) {
                if (v1 > 0) {
                    lore.add(Utils.format("&7Obrazenia: &c" + v1));
                    tag.setInt("dmg", v1);
                }
                if (v2 > 0) {
                    lore.add(Utils.format("&7Obrazenia na potwory: &c" + v2));
                    tag.setInt("moby", v2);
                }
            }
        }
        nmsStack.setTag(tag);
        final ItemStack toReturn = CraftItemStack.asBukkitCopy(nmsStack);
        if (type.equalsIgnoreCase("zbroja") && toReturn.getType().toString().contains("_CHESTPLATE")) {
            if (v2 > 0) {
                toReturn.addUnsafeEnchantment(Enchantment.THORNS, v2);
            }
        }
        final ItemMeta meta = toReturn.getItemMeta();
        if (addGlowing) {
            toReturn.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
        }
        meta.setLore(lore);
        toReturn.setItemMeta(meta);
        return toReturn;
    }

    public static void checkEnchants(final ItemStack itemStack, final Player player) {
        if (itemStack == null) {
            return;
        }
        if (!itemStack.getType().equals(Material.AIR)) {
            final String type = itemStack.getType().toString();
            if (type.contains("_HELMET") || type.contains("_CHESTPLATE") || type.contains("_LEGGINGS") || type.contains("_BOOTS")) {
                if (Utils.getTagInt(itemStack, "prot") > 250) {
                    RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getDiscordBot().sendChannelMessage("enchants-log", EmbedUtil.create(
                            "**Za Duża Obrona ! (" + Utils.getTagInt(itemStack, "prot") + ")**",
                            "Gracz: " + player.getName() + " (" + player.getUniqueId() + ")" +
                                    "Item: " + itemStack.getItemMeta(), Color.RED
                    )));
                    player.setItemInHand(setEnchants(itemStack, "zbroja", 250, 0));
                }
                if (Utils.getTagInt(itemStack, "thorns") > 50) {
                    RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getDiscordBot().sendChannelMessage("enchants-log", EmbedUtil.create(
                            "**Za Dużo THORNS ! (" + Utils.getTagInt(itemStack, "thorns") + ")**",
                            "Gracz: " + player.getName() + " (" + player.getUniqueId() + ")" +
                                    "Item: " + itemStack.getItemMeta(), Color.RED
                    )));
                    player.setItemInHand(setEnchants(itemStack, "zrboja", 0, 50));
                }
            }
            if (type.contains("_SWORD")) {
                if (Utils.getTagInt(itemStack, "dmg") > 2500) {
                    RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getDiscordBot().sendChannelMessage("enchants-log", EmbedUtil.create(
                            "**Za Duża DMG ! (" + Utils.getTagInt(itemStack, "dmg") + ")**",
                            "Gracz: " + player.getName() + " (" + player.getUniqueId() + ")" +
                                    "Item: " + itemStack.getItemMeta(), Color.RED
                    )));
                    player.setItemInHand(setEnchants(itemStack, "miecz", 2500, 0));
                }
                if (Utils.getTagInt(itemStack, "moby") > 2500) {
                    RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getDiscordBot().sendChannelMessage("enchants-log", EmbedUtil.create(
                            "**Za Dużo DMG MOBY ! (" + Utils.getTagInt(itemStack, "moby") + ")**",
                            "Gracz: " + player.getName() + " (" + player.getUniqueId() + ")" +
                                    "Item: " + itemStack.getItemMeta(), Color.RED
                    )));
                    player.setItemInHand(setEnchants(itemStack, "miecz", 0, 2500));
                }
            }
        }
    }
}
