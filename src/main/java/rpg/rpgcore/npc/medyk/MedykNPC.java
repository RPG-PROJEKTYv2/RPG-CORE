package rpg.rpgcore.npc.medyk;

import com.google.common.collect.ImmutableSet;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

public class MedykNPC {
    private final Map<UUID, MedykObject> userMap;
    private final RPGCORE rpgcore;

    public MedykNPC(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
        this.userMap = rpgcore.getMongoManager().loadAllMedyk();
    }


    public void openMedykGUI(final Player player) {
        final UUID uuid = player.getUniqueId();
        final MedykObject medykObject = this.find(uuid);
        final Inventory gui = Bukkit.createInventory(null, InventoryType.BREWING, Utils.format("&c&lMedyk - Bonus: " + medykObject.getMedykUser().getBonus()));
        player.getInventory().addItem(new ItemBuilder(Material.REDSTONE).setName("&8Test").toItemStack());
        gui.setItem(0, new ItemBuilder(Material.IRON_SWORD).setName("&c" + medykObject.getMedykUser().getProgress() + "&7/&c7,000").hideFlag().toItemStack().clone());
        if (medykObject.getMedykUser().isDone()) {
            gui.setItem(1, this.getMedykPotionDone());

        } else {
            gui.setItem(1, this.getMedykPotionNotDone());
        }
        gui.setItem(2, new ItemBuilder(Material.REDSTONE_TORCH_ON).setName("&c&lInformacje").setLore(Arrays.asList("&9&lOrzel zrobi lore ob mi sie nie chce")).toItemStack().clone());


        player.openInventory(gui);
    }

    public void runAnimation(final Player player) {
        player.closeInventory();
        final MedykUser medykUser = this.find(player.getUniqueId()).getMedykUser();
        medykUser.setDone(true);
        medykUser.setProgress(medykUser.getProgress() - 7000);
        PacketPlayOutWorldParticles particle = new PacketPlayOutWorldParticles(EnumParticle.VILLAGER_ANGRY, true, (float) -89.642, (float) 18, (float) -136.251, 0, 0, 0, 0, 0, 0);
        PacketPlayOutWorldParticles particle2 = new PacketPlayOutWorldParticles(EnumParticle.LAVA, true, (float) -89.642, (float) 18, (float) -136.251, 0, 0, 0, 0, 0, 0);
        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> ((CraftPlayer) player).getHandle().playerConnection.sendPacket(particle));
        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> ((CraftPlayer) player).getHandle().playerConnection.sendPacket(particle));
        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> ((CraftPlayer) player).getHandle().playerConnection.sendPacket(particle2));
        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> ((CraftPlayer) player).getHandle().playerConnection.sendPacket(particle2));
        rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> this.openMedykGUI(player), 20L);
    }

    public ItemStack getMedykPotionDone() {
        return new ItemBuilder(Material.POTION, 1, (short) 8197).setName("&dMikstura Medyka").setLore(Arrays.asList("&8Mikstura ta posiada niesamowite wlasciwosci medyczne", "&8i pozwala podniesc poziom", "&8swojego maksymalnego zdrowia o 1&c❤")).addFlag(ItemFlag.HIDE_POTION_EFFECTS).toItemStack().clone();
    }

    public ItemStack getMedykPotionNotDone() {
        return new ItemBuilder(Material.POTION).setName("&5Metna Mikstura Medyka").setLore(Arrays.asList("&8Mikstura ta jest niezdatna do picia.")).addFlag(ItemFlag.HIDE_POTION_EFFECTS).toItemStack().clone();
    }




    public void add(MedykObject medykObject) {
        this.userMap.put(medykObject.getID(), medykObject);
    }

    public MedykObject find(final UUID uuid) {
        this.userMap.computeIfAbsent(uuid, k -> new MedykObject(uuid));
        return this.userMap.get(uuid);
    }

    public ImmutableSet<MedykObject> getMedykObject() {
        return ImmutableSet.copyOf(this.userMap.values());
    }

    public boolean isMetinologObject(final UUID string) {
        return this.userMap.containsKey(string);
    }
}
