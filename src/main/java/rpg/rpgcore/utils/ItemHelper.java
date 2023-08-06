package rpg.rpgcore.utils;

import net.minecraft.server.v1_8_R3.MinecraftServer;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
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

        set.addGlowing();
        set.addTagInt("prot", prot);
        set.addTagInt("thorns", thorns);

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


        return sword.toItemStack();
    }

    public static ItemStack setEnchants(final ItemStack is, final String type, final int v1, final int v2) {
        if (!is.getType().toString().contains("_CHESTPLATE")) {
            is.removeEnchantment(Enchantment.THORNS);
        }
        List<String> lore = new ArrayList<>();
        final net.minecraft.server.v1_8_R3.ItemStack nmsStack = org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack.asNMSCopy(is);
        final net.minecraft.server.v1_8_R3.NBTTagCompound tag = nmsStack.hasTag() ? nmsStack.getTag() : new net.minecraft.server.v1_8_R3.NBTTagCompound();
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
        meta.setLore(lore);
        toReturn.setItemMeta(meta);
        return toReturn;
    }

    public static ItemStack checkEnchants(final ItemStack itemStack, final Player player) {
        if (itemStack == null) {
            return null;
        }
        if (!itemStack.getType().equals(Material.AIR)) {
            final String type = itemStack.getType().toString();
            if (type.contains("_HELMET") || type.contains("_CHESTPLATE") || type.contains("_LEGGINGS") || type.contains("_BOOTS")) {
                int prot = Utils.getTagInt(itemStack, "prot");
                int thorns = Utils.getTagInt(itemStack, "thorns");
                double[] tps = MinecraftServer.getServer().recentTps;
                if (prot > 250) {
                    RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getDiscordBot().sendChannelMessage("enchants-log", EmbedUtil.create(
                            "**Za Duża Obrona ! (" + Utils.getTagInt(itemStack, "prot") + ")**",
                            "**Ping Gracza: **" + ((CraftPlayer) player).getHandle().ping + " ms\n" +
                                    "**Ping Serwerowy: ** 1m - " + tps[0] + "tps, 5m - " + tps[1] + "tps, 15m - " + tps[2] + "tps\n" +
                                    "**Gracz: **" + player.getName() + " (" + player.getUniqueId() + ")" + "\n" +
                                    "**Item: **" + itemStack.getItemMeta(), Color.RED
                    )));
                    prot = 250;
                }
                if (thorns > 50) {
                    RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getDiscordBot().sendChannelMessage("enchants-log", EmbedUtil.create(
                            "**Za Dużo THORNS ! (" + Utils.getTagInt(itemStack, "thorns") + ")**",
                            "**Ping Gracza: **" + ((CraftPlayer) player).getHandle().ping + " ms\n" +
                                    "**Ping Serwerowy: ** 1m - " + tps[0] + "tps, 5m - " + tps[1] + "tps, 15m - " + tps[2] + "tps\n" +
                                    "**Gracz: **" + player.getName() + " (" + player.getUniqueId() + ")" + "\n" +
                                    "**Item: **" + itemStack.getItemMeta(), Color.RED
                    )));
                    thorns = 50;
                }
                return setEnchants(itemStack, "zbroja", prot, thorns);
            }
            if (type.contains("_SWORD")) {
                int dmg = Utils.getTagInt(itemStack, "dmg");
                int moby = Utils.getTagInt(itemStack, "moby");
                double[] tps = MinecraftServer.getServer().recentTps;
                if (dmg > 2500) {
                    RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getDiscordBot().sendChannelMessage("enchants-log", EmbedUtil.create(
                            "**Za Duża DMG ! (" + Utils.getTagInt(itemStack, "dmg") + ")**",
                            "**Ping Gracza: **" + ((CraftPlayer) player).getHandle().ping + " ms\n" +
                                    "**Ping Serwerowy: ** 1m - " + tps[0] + "tps, 5m - " + tps[1] + "tps, 15m - " + tps[2] + "tps\n" +
                                    "**Gracz: **" + player.getName() + " (" + player.getUniqueId() + ")" + "\n" +
                                    "**Item: **" + itemStack.getItemMeta(), Color.RED
                    )));
                    dmg = 2500;
                }
                if (moby > 2500) {
                    RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getDiscordBot().sendChannelMessage("enchants-log", EmbedUtil.create(
                            "**Za Dużo DMG MOBY ! (" + Utils.getTagInt(itemStack, "moby") + ")**",
                            "**Ping Gracza: **" + ((CraftPlayer) player).getHandle().ping + " ms\n" +
                                    "**Ping Serwerowy: ** 1m - " + tps[0] + "tps, 5m - " + tps[1] + "tps, 15m - " + tps[2] + "tps\n" +
                                    "**Gracz: **" + player.getName() + " (" + player.getUniqueId() + ")" + "\n" +
                                    "**Item: **" + itemStack.getItemMeta(), Color.RED
                    )));
                    moby = 2500;
                }
                return setEnchants(itemStack, "miecz", dmg, moby);
            }
        }
        return itemStack;
    }
}
