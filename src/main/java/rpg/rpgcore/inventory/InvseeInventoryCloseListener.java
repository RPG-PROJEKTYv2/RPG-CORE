package rpg.rpgcore.inventory;

import net.minecraft.server.v1_8_R3.MinecraftServer;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.discord.EmbedUtil;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.Utils;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InvseeInventoryCloseListener implements Listener {
    private final RPGCORE rpgcore;

    public InvseeInventoryCloseListener(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onInventoryClose(final InventoryCloseEvent e) {
        if (Utils.removeColor(e.getInventory().getName()).contains("Ekwipunek gracza")) {
            final String playerName = Utils.removeColor(e.getInventory().getName()).replace("Ekwipunek gracza ", "").trim();
            final User user = this.rpgcore.getUserManager().find(playerName);
            try {
                final ItemStack[] contentsBefore = Utils.itemStackArrayFromBase64(user.getInventoriesUser().getInventory());
                final ItemStack[] armorBefore = Utils.itemStackArrayFromBase64(user.getInventoriesUser().getArmor());
                final ItemStack[] contents = new ItemStack[36];
                for (int i = 0; i < contents.length; i++) {
                    contents[i] = e.getInventory().getItem(i);
                }
                final ItemStack[] armor = new ItemStack[4];
                armor[0] = e.getInventory().getItem(36);
                armor[1] = e.getInventory().getItem(37);
                armor[2] = e.getInventory().getItem(38);
                armor[3] = e.getInventory().getItem(39);

                user.getInventoriesUser().setInventory(Utils.itemStackArrayToBase64(contents));
                user.getInventoriesUser().setArmor(Utils.itemStackArrayToBase64(armor));
                List<ItemStack> differenceAdded = new ArrayList<>(Arrays.asList(contents));
                differenceAdded.removeAll(Arrays.asList(contentsBefore));


                List<ItemStack> differenceRemoved = new ArrayList<>(Arrays.asList(contentsBefore));
                differenceRemoved.removeAll(Arrays.asList(contents));

                e.getPlayer().sendMessage("Contents before: " + Arrays.asList(contentsBefore));
                e.getPlayer().sendMessage("Contents after: " + Arrays.asList(contents));
                e.getPlayer().sendMessage("Difference: " + differenceRemoved);

                StringBuilder sb = new StringBuilder();
                for (ItemStack item : differenceRemoved) {
                    sb.append("- ").append("x " + item.getAmount()).append(" ").append("Type: `" + item.getType().name() + "`\nName: " + Utils.removeColor(item.getItemMeta().hasDisplayName() ? item.getItemMeta().getDisplayName() : item.getType().name()) + "\nLore: " + (item.getItemMeta().hasLore() ? RPGCORE.getDiscordBot().buildStringFromLore(item.getItemMeta().getLore()) : "Brak Lore\n")).append("\n");
                }

                StringBuilder sb2 = new StringBuilder();
                for (ItemStack item : differenceAdded) {
                    sb2.append("+ ").append("x " + item.getAmount()).append(" ").append("Type: `" + item.getType().name() + "`\nName: " + Utils.removeColor(item.getItemMeta().hasDisplayName() ? item.getItemMeta().getDisplayName() : item.getType().name()) + "\nLore: " + (item.getItemMeta().hasLore() ? RPGCORE.getDiscordBot().buildStringFromLore(item.getItemMeta().getLore()) : "Brak Lore\n")).append("\n");
                }

                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataUser(user.getId(), user));
                e.getPlayer().sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie zapisano ekwipunek gracza &6" + playerName));
                if (differenceRemoved.isEmpty() && differenceRemoved.isEmpty()) {
                    return;
                }
                double[] tps = MinecraftServer.getServer().recentTps;
                RPGCORE.getDiscordBot().sendChannelMessage("invsee-logs", EmbedUtil.create("**Modyfikowano Ekwipunek**",
                        "**Gracz:** `" + e.getPlayer().getName() + "` **modyfikowal ekwipunek innego gracza!**\n" +
                                "**Ping Gracza: **" + ((CraftPlayer) e.getPlayer()).getHandle().ping + " ms\n" +
                                "**Ping Serwerowy: ** 1m - " + tps[0] + "tps, 5m - " + tps[1] + "tps, 15m - " + tps[2] + "tps\n" +
                                "**Modyfikowany Gracz: **`" + playerName + "`\n" +
                                "**Dodane przedmioty:**\n" + (sb2.toString().isEmpty() ? "+ Brak" : sb2.toString()) + "\n\n" +
                                "**Wyjete przedmioty:**\n" + (sb.toString().isEmpty() ? "- Brak" : sb.toString())
                        , Color.RED));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
