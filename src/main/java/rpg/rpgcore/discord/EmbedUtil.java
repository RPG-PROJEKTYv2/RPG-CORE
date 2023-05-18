package rpg.rpgcore.discord;

import com.google.common.collect.Multimap;
import net.dv8tion.jda.api.EmbedBuilder;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.trade.objects.Trade;
import rpg.rpgcore.utils.DoubleUtils;
import rpg.rpgcore.utils.Utils;

import java.util.List;
import java.awt.*;
import java.time.Instant;

public class EmbedUtil {
    public static EmbedBuilder create(final String title, final String desc, final Color color) {
        return new EmbedBuilder()
                .setColor(color)
                .setTitle(title, null)
                .setDescription(desc)
                .setTimestamp(Instant.now())
                .setFooter("© 2022 HELLRPG.PL");
    }

    public static EmbedBuilder createHandlarzcSellLog(final Player player, final Multimap<ItemStack, Double> itemMap, final double moneyBefore, final double moneyAfter) {
        double[] tps = MinecraftServer.getServer().recentTps;

        final StringBuilder sb = new StringBuilder();
        sb.append("\n**Lista przedmiotow:**\n");

        for (final ItemStack itemStack : itemMap.keySet()) {
            sb.append("**").append(Utils.removeColor(itemStack.getItemMeta().getDisplayName())).append("**\n");
            sb.append("Cena: ").append(itemMap.get(itemStack).stream().mapToDouble(Double::doubleValue).sum()).append("\n");
            if (itemStack.hasItemMeta() && itemStack.getItemMeta().hasLore()) {
                sb.append("Opis:\n");
                for (final String lore : itemStack.getItemMeta().getLore()) {
                    sb.append("- ").append(Utils.removeColor(lore)).append("\n");
                }
            }
            sb.append("\n");
        }

        sb.append("\n**Saldo przed sprzeaza: **").append(moneyBefore).append("\n");
        sb.append("**Saldo po sprzedazy: **").append(moneyAfter).append("\n");

        return new EmbedBuilder()
                .setColor(Color.decode("#3ff538"))
                .setTitle("**Gracz " + player.getName() + " sprzedal " + itemMap.entries().stream().mapToInt(entry -> entry.getKey().getAmount()).sum() + " przedmiotow**", null)
                .setDescription(
                        "**Łączna wartość sprzedanych przedmiotów: **" + itemMap.values().stream().mapToDouble(Double::doubleValue).sum() + "\n" +
                                "**Ping Gracza: **" + ((CraftPlayer) player).getHandle().ping + " ms\n" +
                                "**Ping Serwerowy: ** 1m - " + DoubleUtils.round(tps[0], 2) + "tps, 5m - " + DoubleUtils.round(tps[1], 2) +
                                "tps, 15m - " + DoubleUtils.round(tps[2], 2) + "tps\n" + sb)
                .setTimestamp(Instant.now())
                .setFooter("© 2022 HELLRPG.PL");
    }

    public static EmbedBuilder createHandlarzCancelLog(final Player player, final Multimap<ItemStack, Double> itemMap) {
        double[] tps = MinecraftServer.getServer().recentTps;

        final StringBuilder sb = new StringBuilder();
        sb.append("\n**Lista przedmiotow:**\n");

        for (final ItemStack itemStack : itemMap.keySet()) {
            sb.append("**").append(Utils.removeColor(itemStack.getItemMeta().getDisplayName())).append("**\n");
            sb.append("Cena: ").append(itemMap.get(itemStack).stream().mapToDouble(Double::doubleValue).sum()).append("\n");
            if (itemStack.hasItemMeta() && itemStack.getItemMeta().hasLore()) {
                sb.append("Opis:\n");
                for (final String lore : itemStack.getItemMeta().getLore()) {
                    sb.append("- ").append(Utils.removeColor(lore)).append("\n");
                }
            }
            sb.append("\n");
        }

        return new EmbedBuilder()
                .setColor(Color.decode("#b30202"))
                .setTitle("**Gracz " + player.getName() + " anulował sprzedaz " + itemMap.entries().stream().mapToInt(entry -> entry.getKey().getAmount()).sum() + " przedmiotow**", null)
                .setDescription("**Łączna wartość przedmiotów: **" + itemMap.values().stream().mapToDouble(Double::doubleValue).sum() + "\n" +
                                "**Ping Gracza: **" + ((CraftPlayer) player).getHandle().ping + " ms\n" +
                                "**Ping Serwerowy: ** 1m - " + DoubleUtils.round(tps[0], 2) + "tps, 5m - " + DoubleUtils.round(tps[1], 2) +
                                "tps, 15m - " + DoubleUtils.round(tps[2], 2) + "tps\n" + sb)
                .setTimestamp(Instant.now())
                .setFooter("© 2022 HELLRPG.PL");
    }

    public static EmbedBuilder createTradeLogs(final Trade trade) {
        double[] tps = MinecraftServer.getServer().recentTps;
        final Color color = trade.isCanceled() ? Color.decode("#fc2c28") : Color.decode("#8efa41");
        final StringBuilder sb = new StringBuilder();
        sb.append("**Wymiana pomiedzy:** `").append(trade.getPlayer1().getName()).append(" i ").append(trade.getPlayer2().getName()).append("`\n");
        sb.append("**Ping Gracza** `").append(trade.getPlayer1().getName()).append("`**:**").append(((CraftPlayer) trade.getPlayer1()).getHandle().ping).append(" ms\n");
        sb.append("**Ping Gracza** `").append(trade.getPlayer2().getName()).append("`**:**").append(((CraftPlayer) trade.getPlayer2()).getHandle().ping).append(" ms\n");
        sb.append("**Ping Serwerowy: ** 1m - ").append(DoubleUtils.round(tps[0], 2)).append("tps, 5m - ").append(DoubleUtils.round(tps[1], 2))
                .append("tps, 15m - ").append(DoubleUtils.round(tps[2], 2)).append("tps\n");
        sb.append("**Status: **").append(trade.isCanceled() ? "Anulowana" : "Zakonczona").append("\n");
        sb.append("**Przedmioty gracza:** `").append(trade.getPlayer1().getName()).append("`\n");
        for (final ItemStack is : trade.getItems(trade.getUuid1())) {
            sb.append("- ").append(Utils.removeColor(is.getItemMeta().getDisplayName())).append("\n");
            if (is.getItemMeta().hasLore()) {
                sb.append("\t*Lore:*\n");
                for (final String lore : is.getItemMeta().getLore()) {
                    sb.append("\t- ").append(Utils.removeColor(lore)).append("\n");
                }
            }
            sb.append("\n");
        }
        sb.append("**Przedmioty gracza:** `").append(trade.getPlayer2().getName()).append("`\n");
        for (final ItemStack is : trade.getItems(trade.getUuid2())) {
            sb.append("- ").append(Utils.removeColor(is.getItemMeta().getDisplayName())).append("\n");
            if (is.getItemMeta().hasLore()) {
                sb.append("\t*Lore:*\n");
                for (final String lore : is.getItemMeta().getLore()) {
                    sb.append("\t- ").append(Utils.removeColor(lore)).append("\n");
                }
            }
            sb.append("\n");
        }

        return new EmbedBuilder()
                .setColor(color)
                .setTitle("**Wymiana**", null)
                .setDescription(sb)
                .setTimestamp(Instant.now())
                .setFooter("© 2022 HELLRPG.PL");
    }

    public static EmbedBuilder createKoszLogs(final Player player, final List<ItemStack> itemList) {
        double[] tps = MinecraftServer.getServer().recentTps;

        final StringBuilder sb = new StringBuilder();
        sb.append("\n**Lista przedmiotow:**\n");

        for (final ItemStack item : itemList) {
            sb.append("**").append(Utils.removeColor(item.getItemMeta().getDisplayName())).append("**\n");
            if (item.hasItemMeta() && item.getItemMeta().hasLore()) {
                sb.append("Opis:\n");
                for (final String lore : item.getItemMeta().getLore()) {
                    sb.append("- ").append(Utils.removeColor(lore)).append("\n");
                }
            }
            sb.append("\n");
        }

        return new EmbedBuilder()
                .setColor(Color.decode("#b30202"))
                .setTitle("**Gracz " + player.getName() + " wyrzucił do kosza " + itemList.size() + " przedmiotow**", null)
                .setDescription("**Ping Gracza: **" + ((CraftPlayer) player).getHandle().ping + " ms\n" +
                        "**Ping Serwerowy: ** 1m - " + DoubleUtils.round(tps[0], 2) + "tps, 5m - " + DoubleUtils.round(tps[1], 2) + "tps, 15m - " + DoubleUtils.round(tps[2], 2) + "tps\n" +
                        sb)
                .setTimestamp(Instant.now())
                .setFooter("© 2022 HELLRPG.PL");
    }
}
