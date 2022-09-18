package rpg.rpgcore.npc.lowca;

import com.google.common.collect.ImmutableSet;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.Map;
import java.util.UUID;

public class LowcaNPC {
    private final RPGCORE rpgcore;
    private final Map<UUID, LowcaObject> userMap;

    public LowcaNPC(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
        this.userMap = rpgcore.getMongoManager().loadAllLowca();
    }


    public void openLowcaGUI(final Player player) {
        final UUID uuid = player.getUniqueId();
        final LowcaUser lowcaUser = this.userMap.get(uuid).getLowcaUser();
        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&4&lLowca"));

        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setName(" ").toItemStack());
        }


    }



    public LowcaObject find(final UUID uuid) {
        userMap.computeIfAbsent(uuid, k -> new LowcaObject(uuid));
        return this.userMap.get(uuid);
    }

    public void add(final LowcaObject lowcaObject) {
        this.userMap.put(lowcaObject.getId(), lowcaObject);
    }

    public ImmutableSet<LowcaObject> getLowcaObjects() {
        return ImmutableSet.copyOf(this.userMap.values());
    }
}
