package rpg.rpgcore.npc.medrzec;

import com.google.common.collect.ImmutableSet;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.npc.medrzec.objects.MedrzecUser;
import rpg.rpgcore.utils.DoubleUtils;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

public class MedrzecNPC {
    private final Map<UUID, MedrzecUser> userMap;

    public MedrzecNPC(final RPGCORE rpgcore) {
        this.userMap = rpgcore.getMongoManager().loadAllMedrzec();
    }

    public void openMedrzecGUI(final Player player) {
        final MedrzecUser medrzecUser = this.find(player.getUniqueId());
        final Inventory gui = Bukkit.createInventory(null, InventoryType.HOPPER, Utils.format("&4&lMedrzec"));
        final ItemStack fill = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setName("").toItemStack();

        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, fill);
        }
        if (medrzecUser.getBonus() < 20) {
            gui.setItem(1, new ItemBuilder(Material.WORKBENCH).setName("&6Wytworz Rubinowe Serce").setLore(Arrays.asList(
                    "&7Koszt:",
                    "  &8- &e4x &cZniszczone Rubinowe Serce",
                    "  &8- &61 000 000&2$"
            )).toItemStack());
            gui.setItem(3, new ItemBuilder(Material.REDSTONE_TORCH_ON).setName("&3&lInformacje").setLore(Arrays.asList(
                    "&7Aktualny bonus: &e" + DoubleUtils.round(medrzecUser.getBonus() / 2.0, 2) + "❤",
                    "&7Szansa na znalezienie Serca: &e" + DoubleUtils.round(0.05 + ((0.05 * RPGCORE.getInstance().getBonusesManager().find(player.getUniqueId()).getBonusesUser().getSzczescie()) / 1000.0), 2) + "%"
            )).toItemStack().clone());
        } else {
            gui.setItem(1, new ItemBuilder(Material.WORKBENCH).setName("&6Wytworz Szafirowe Serce").setLore(Arrays.asList(
                    "&7Koszt:",
                    "  &8- &e4x &bZniszczone Szafirowe Serce",
                    "  &8- &62 500 000&2$"
            )).toItemStack());
            gui.setItem(3, new ItemBuilder(Material.REDSTONE_TORCH_ON).setName("&3&lInformacje").setLore(Arrays.asList(
                    "&7Aktualny bonus: &e" + DoubleUtils.round(medrzecUser.getBonus() / 2.0, 2) + "❤",
                    "&7Szansa na znalezienie Serca: &e" + DoubleUtils.round(0.015 + ((0.015 * RPGCORE.getInstance().getBonusesManager().find(player.getUniqueId()).getBonusesUser().getSzczescie()) / 1000.0), 2) + "%"
            )).toItemStack().clone());
        }

        player.openInventory(gui);
    }


    public MedrzecUser find(final UUID uuid) {
        return this.userMap.get(uuid);
    }

    public void add(final MedrzecUser medrzecUser) {
        this.userMap.put(medrzecUser.getUuid(), medrzecUser);
    }

    public ImmutableSet<MedrzecUser> getMedrzecUsers() {
        return ImmutableSet.copyOf(this.userMap.values());
    }
}
