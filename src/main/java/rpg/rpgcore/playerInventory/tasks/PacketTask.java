package rpg.rpgcore.playerInventory.tasks;

import net.minecraft.server.v1_8_R3.PacketPlayOutSetSlot;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.ItemBuilder;

import java.util.Arrays;

public class PacketTask implements Runnable {

    public PacketTask(final RPGCORE rpgcore) {
        rpgcore.getServer().getScheduler().scheduleSyncRepeatingTask(rpgcore, this, 0L, 10L);
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getGameMode() != GameMode.SURVIVAL) continue;
            if (player.getOpenInventory().getTopInventory().getType() == InventoryType.CRAFTING) {
                if (player.getOpenInventory().getTopInventory().contains(Material.WORKBENCH)) {
                    continue;
                }
            }
            final PacketPlayOutSetSlot[] packets = {new PacketPlayOutSetSlot(0, 1, CraftItemStack.asNMSCopy(new ItemBuilder(Material.CHEST).setName("&6Magazyny").setLore(Arrays.asList("&8Kliknij, aby otworzyc liste magazynow")).toItemStack())),
            new PacketPlayOutSetSlot(0, 2, CraftItemStack.asNMSCopy(new ItemBuilder(Material.ITEM_FRAME).setName("&6Akcesoria Podstawowe").setLore(Arrays.asList("&8Kliknij, aby otworzyc menu podstawowego akcesorium")).toItemStack())),
            new PacketPlayOutSetSlot(0, 3, CraftItemStack.asNMSCopy(new ItemBuilder(Material.FLOWER_POT_ITEM).setName("&cKosz").setLore(Arrays.asList("&8Kliknij, aby otworzyc kosz")).toItemStack())),
            new PacketPlayOutSetSlot(0, 4, CraftItemStack.asNMSCopy(new ItemBuilder(Material.WORKBENCH).setName("&6Craftingi").setLore(Arrays.asList("&8Kliknij, aby otworzyc menu craftingow")).toItemStack())),
            new PacketPlayOutSetSlot(0, 0, CraftItemStack.asNMSCopy(new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setSkullOwner(player.getName()).setName("&6Profil").setLore(Arrays.asList("&8Kliknij, aby otworzyc swoj profil")).toItemStack().clone())),
            };
            final PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
            for (final PacketPlayOutSetSlot packet : packets) {
                connection.sendPacket(packet);
            }
        }
    }
}
