package rpg.rpgcore.inventories;

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
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
                List<ItemStack> difference = new ArrayList<>();

                for (ItemStack item : contentsBefore) {
                    if (item != null) {
                        if (!Arrays.asList(contents).contains(item)) {
                            difference.add(item);
                        }
                    }
                }

                StringBuilder sb = new StringBuilder();
                for (ItemStack item : difference) {
                    sb.append("- ").append("x " + item.getAmount()).append(" ").append(item.getItemMeta()).append("\n");
                }

                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataUser(user.getId(), user));
                e.getPlayer().sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie zapisano ekwipunek gracza &6" + playerName));
                RPGCORE.getDiscordBot().sendChannelMessage("invsee-logs", EmbedUtil.create("**Modyfikowano Ekwipunek**",
                        "**Gracz:** `" + e.getPlayer().getName() + "` **modyfikowal ekwipunek innego gracza!**\n" +
                                "**Modyfikowany Gracz: **`" + playerName + "`\n" +
                                "**Zmodyfikowane przedmioty:**\n" + sb, Color.RED));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
