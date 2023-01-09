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
import rpg.rpgcore.npc.medyk.objects.MedykObject;
import rpg.rpgcore.npc.medyk.objects.MedykUser;
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
        final int bonus = medykObject.getMedykUser().getBonus();
        gui.setItem(0, new ItemBuilder(Material.IRON_SWORD).setName("&c" + medykObject.getMedykUser().getProgress() + "&7/&c2,500").addTagInt("req", 2_500).hideFlag().toItemStack().clone());
        if (bonus >= 10) {
            gui.setItem(0, new ItemBuilder(Material.IRON_SWORD).setName("&c" + medykObject.getMedykUser().getProgress() + "&7/&c4,000").addTagInt("req", 4_000).hideFlag().toItemStack().clone());
        }
        if (bonus >= 20) {
            gui.setItem(0, new ItemBuilder(Material.IRON_SWORD).setName("&c" + medykObject.getMedykUser().getProgress() + "&7/&c7,000").addTagInt("req", 7_000).hideFlag().toItemStack().clone());
        }
        if (bonus >= 30) {
            gui.setItem(0, new ItemBuilder(Material.IRON_SWORD).setName("&c" + medykObject.getMedykUser().getProgress() + "&7/&c12,000").addTagInt("req", 12_000).hideFlag().toItemStack().clone());
        }
        if (bonus >= 40) {
            gui.setItem(0, new ItemBuilder(Material.IRON_SWORD).setName("&c" + medykObject.getMedykUser().getProgress() + "&7/&c20,000").addTagInt("req", 20_000).hideFlag().toItemStack().clone());
        }
        if (medykObject.getMedykUser().isDone()) {
            gui.setItem(1, this.getMedykPotionDone(medykObject.getMedykUser()));
        }
        gui.setItem(2, new ItemBuilder(Material.REDSTONE_TORCH_ON).setName("&c&lInformacje").setLore(Arrays.asList(
                "&7Maksymalny bonus: &c50❤",
                "&7Potrzebne Przedmioty:",
                "  &8- &5Metna Mikstura Medyka",
                "  &8- &4Fragment Serca")).toItemStack().clone());


        player.openInventory(gui);
    }

    public void runAnimation(final Player player, final int reqAmount) {
        player.closeInventory();
        final MedykUser medykUser = this.find(player.getUniqueId()).getMedykUser();
        medykUser.setDone(true);
        medykUser.setProgress(medykUser.getProgress() - reqAmount);
        PacketPlayOutWorldParticles particle = new PacketPlayOutWorldParticles(EnumParticle.VILLAGER_ANGRY, true, (float) -89.642, (float) 18, (float) -136.251, 0, 0, 0, 0, 0, 0);
        PacketPlayOutWorldParticles particle2 = new PacketPlayOutWorldParticles(EnumParticle.LAVA, true, (float) -89.642, (float) 18, (float) -136.251, 0, 0, 0, 0, 0, 0);
        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> ((CraftPlayer) player).getHandle().playerConnection.sendPacket(particle));
        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> ((CraftPlayer) player).getHandle().playerConnection.sendPacket(particle));
        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> ((CraftPlayer) player).getHandle().playerConnection.sendPacket(particle2));
        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> ((CraftPlayer) player).getHandle().playerConnection.sendPacket(particle2));
        rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> this.openMedykGUI(player), 20L);
    }

    public ItemStack getMedykPotionDone(final MedykUser user) {
        ItemStack item = new ItemBuilder(Material.POTION, 1, (short) 8197).setName("&dMikstura Medyka").setLore(Arrays.asList("&7Przedzial: &c0-9❤", "", "&8Mikstura ta posiada niesamowite wlasciwosci medyczne", "&8i pozwala podniesc poziom", "&8swojego maksymalnego zdrowia o 1&c❤")).addFlag(ItemFlag.HIDE_POTION_EFFECTS).addTagInt("minBonus", 0).addTagInt("maxBonus", 9).toItemStack().clone();
        if (user.getBonus() >= 10) {
            item = new ItemBuilder(Material.POTION, 1, (short) 8197).setName("&dMikstura Medyka").setLore(Arrays.asList("&7Przedzial: &c10-19❤", "", "&8Mikstura ta posiada niesamowite wlasciwosci medyczne", "&8i pozwala podniesc poziom", "&8swojego maksymalnego zdrowia o 1&c❤")).addFlag(ItemFlag.HIDE_POTION_EFFECTS).addTagInt("minBonus", 10).addTagInt("maxBonus", 19).toItemStack().clone();
        }
        if (user.getBonus() >= 20) {
            item = new ItemBuilder(Material.POTION, 1, (short) 8197).setName("&dMikstura Medyka").setLore(Arrays.asList("&7Przedzial: &c20-29❤", "", "&8Mikstura ta posiada niesamowite wlasciwosci medyczne", "&8i pozwala podniesc poziom", "&8swojego maksymalnego zdrowia o 1&c❤")).addFlag(ItemFlag.HIDE_POTION_EFFECTS).addTagInt("minBonus", 20).addTagInt("maxBonus", 29).toItemStack().clone();
        }
        if (user.getBonus() >= 30) {
            item = new ItemBuilder(Material.POTION, 1, (short) 8197).setName("&dMikstura Medyka").setLore(Arrays.asList("&7Przedzial: &c30-39❤", "", "&8Mikstura ta posiada niesamowite wlasciwosci medyczne", "&8i pozwala podniesc poziom", "&8swojego maksymalnego zdrowia o 1&c❤")).addFlag(ItemFlag.HIDE_POTION_EFFECTS).addTagInt("minBonus", 30).addTagInt("maxBonus", 39).toItemStack().clone();
        }
        if (user.getBonus() >= 40) {
            item = new ItemBuilder(Material.POTION, 1, (short) 8197).setName("&dMikstura Medyka").setLore(Arrays.asList("&7Przedzial: &c40-49❤", "", "&8Mikstura ta posiada niesamowite wlasciwosci medyczne", "&8i pozwala podniesc poziom", "&8swojego maksymalnego zdrowia o 1&c❤")).addFlag(ItemFlag.HIDE_POTION_EFFECTS).addTagInt("minBonus", 40).addTagInt("maxBonus", 49).toItemStack().clone();
        }
        return item;
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
