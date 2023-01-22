package rpg.rpgcore.discord;

import com.google.common.collect.Multimap;
import net.dv8tion.jda.api.EmbedBuilder;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.Utils;

import java.awt.*;
import java.time.Instant;

public class EmbedUtil {
    public static EmbedBuilder create(final String title, final String desc, final Color color) {
        return (new EmbedBuilder())
                .setColor(color)
                .setTitle(title, null)
                .setDescription(desc)
                .setTimestamp(Instant.now())
                .setFooter("© 2022 HELLRPG.PL");
    }

    public static EmbedBuilder createHandlarzcSellLog(final Player player, final Multimap<ItemStack, Double> itemMap, final double moneyBefore, final double moneyAfter) {

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

        return (new EmbedBuilder())
                .setColor(Color.decode("#3ff538"))
                .setTitle("**Gracz " + player.getName() + " sprzedal " + itemMap.entries().stream().mapToInt(entry -> entry.getKey().getAmount()).sum() + " przedmiotow**", null)
                .setDescription("Łączna wartość sprzedanych przedmiotów: " + itemMap.values().stream().mapToDouble(Double::doubleValue).sum() + "\n" + sb)
                .setTimestamp(Instant.now())
                .setFooter("© 2022 HELLRPG.PL");
    }

    public static EmbedBuilder createHandlarzCancelLog(final Player player, final Multimap<ItemStack, Double> itemMap) {

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

        return (new EmbedBuilder())
                .setColor(Color.decode("#b30202"))
                .setTitle("**Gracz " + player.getName() + " anulował sprzedaz " + itemMap.entries().stream().mapToInt(entry -> entry.getKey().getAmount()).sum() + " przedmiotow**", null)
                .setDescription("Łączna wartość przedmiotów: " + itemMap.values().stream().mapToDouble(Double::doubleValue).sum() + "\n" + sb)
                .setTimestamp(Instant.now())
                .setFooter("© 2022 HELLRPG.PL");
    }
}
