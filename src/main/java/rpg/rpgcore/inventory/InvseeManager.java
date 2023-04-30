package rpg.rpgcore.inventory;

import net.minecraft.server.v1_8_R3.MinecraftServer;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.discord.EmbedUtil;
import rpg.rpgcore.utils.Utils;

import java.awt.*;
import java.util.*;
import java.util.List;

public class InvseeManager {
    private final Map<UUID, ItemStack[]> inventories = new HashMap<>();
    private final Map<UUID, ItemStack[]> armors = new HashMap<>();

    public void addInventory(UUID uuid, ItemStack[] inventory) {
        inventories.put(uuid, inventory);
    }

    public ItemStack[] getInventory(UUID uuid) {
        return inventories.get(uuid);
    }

    public void removeInventory(UUID uuid) {
        inventories.remove(uuid);
    }

    public void addArmor(UUID uuid, ItemStack[] armor) {
        armors.put(uuid, armor);
    }

    public ItemStack[] getArmor(UUID uuid) {
        return armors.get(uuid);
    }

    public void removeArmor(UUID uuid) {
        armors.remove(uuid);
    }

    public void log(final Player admin, final String modifiedName, final UUID uuid, final ItemStack[] currentItems, final ItemStack[] currentArmor) {
        final List<ItemStack> contentsAfter = new java.util.ArrayList<>(java.util.Arrays.asList(currentItems));
        final List<ItemStack> contentsBefore = new java.util.ArrayList<>(java.util.Arrays.asList(getInventory(uuid)));

        final List<ItemStack> armorAfter = new java.util.ArrayList<>(java.util.Arrays.asList(currentArmor));
        final List<ItemStack> armorBefore = new java.util.ArrayList<>(java.util.Arrays.asList(getArmor(uuid)));

        removeArmor(uuid);
        removeInventory(uuid);

        final List<ItemStack> differenceAdded = new ArrayList<>(contentsAfter);
        differenceAdded.removeAll(contentsBefore);

        final List<ItemStack> differenceRemoved = new ArrayList<>(contentsBefore);
        differenceRemoved.removeAll(contentsAfter);

        final List<ItemStack> differenceAddedArmor = new ArrayList<>(armorAfter);
        differenceAddedArmor.removeAll(armorBefore);

        final List<ItemStack> differenceRemovedArmor = new ArrayList<>(armorBefore);
        differenceRemovedArmor.removeAll(armorAfter);

        StringBuilder sb = new StringBuilder();
        for (ItemStack item : differenceRemoved) {
            sb.append("- ").append("x ").append(item.getAmount()).append(" ").append("Type: `").append(item.getType().name()).append("`\nName: ").append(Utils.removeColor(item.getItemMeta().hasDisplayName() ? item.getItemMeta().getDisplayName() : item.getType().name())).append("\nLore: ").append(item.getItemMeta().hasLore() ? RPGCORE.getDiscordBot().buildStringFromLore(item.getItemMeta().getLore()) : "Brak Lore\n").append("\n");
        }

        StringBuilder sb2 = new StringBuilder();
        for (ItemStack item : differenceAdded) {
            sb2.append("+ ").append("x ").append(item.getAmount()).append(" ").append("Type: `").append(item.getType().name()).append("`\nName: ").append(Utils.removeColor(item.getItemMeta().hasDisplayName() ? item.getItemMeta().getDisplayName() : item.getType().name())).append("\nLore: ").append(item.getItemMeta().hasLore() ? RPGCORE.getDiscordBot().buildStringFromLore(item.getItemMeta().getLore()) : "Brak Lore\n").append("\n");
        }

        StringBuilder sb3 = new StringBuilder();
        for (ItemStack item : differenceRemovedArmor) {
            sb3.append("- ").append("x ").append(item.getAmount()).append(" ").append("Type: `").append(item.getType().name()).append("`\nName: ").append(Utils.removeColor(item.getItemMeta().hasDisplayName() ? item.getItemMeta().getDisplayName() : item.getType().name())).append("\nLore: ").append(item.getItemMeta().hasLore() ? RPGCORE.getDiscordBot().buildStringFromLore(item.getItemMeta().getLore()) : "Brak Lore\n").append("\n");
        }

        StringBuilder sb4 = new StringBuilder();
        for (ItemStack item : differenceAddedArmor) {
            sb4.append("+ ").append("x ").append(item.getAmount()).append(" ").append("Type: `").append(item.getType().name()).append("`\nName: ").append(Utils.removeColor(item.getItemMeta().hasDisplayName() ? item.getItemMeta().getDisplayName() : item.getType().name())).append("\nLore: ").append(item.getItemMeta().hasLore() ? RPGCORE.getDiscordBot().buildStringFromLore(item.getItemMeta().getLore()) : "Brak Lore\n").append("\n");
        }

        if (differenceAdded.isEmpty() && differenceRemoved.isEmpty() && differenceAddedArmor.isEmpty() && differenceRemovedArmor.isEmpty()) return;
        final double[] tps = MinecraftServer.getServer().recentTps;
        RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () ->
        RPGCORE.getDiscordBot().sendChannelMessage("invsee-logs", EmbedUtil.create("**Modyfikowano Ekwipunek**",
                "**Gracz:** `" + admin.getName() + "` **modyfikowal ekwipunek innego gracza!**\n" +
                        "**Ping Gracza: **" + ((CraftPlayer) admin).getHandle().ping + " ms\n" +
                        "**Ping Serwerowy: ** 1m - " + tps[0] + "tps, 5m - " + tps[1] + "tps, 15m - " + tps[2] + "tps\n" +
                        "**Modyfikowany Gracz: **`" + modifiedName + "`\n" +
                        "**Dodane przedmioty:**\n" + (sb2.toString().isEmpty() ? "+ Brak" : sb2.toString()) + "\n\n" +
                        "**Wyjete przedmioty:**\n" + (sb.toString().isEmpty() ? "- Brak" : sb.toString()) + "\n\n" +
                        "**Dodana Zbroja:**\n" + (sb4.toString().isEmpty() ? "- Brak" : sb.toString()) + "\n\n" +
                        "**Wyjeta Zbroja:**\n" + (sb3.toString().isEmpty() ? "- Brak" : sb.toString())
                , Color.RED)));
    }
}
