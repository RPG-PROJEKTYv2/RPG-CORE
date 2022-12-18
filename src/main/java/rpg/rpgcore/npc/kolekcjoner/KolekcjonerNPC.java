package rpg.rpgcore.npc.kolekcjoner;

import com.google.common.collect.ImmutableSet;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.npc.kolekcjoner.enums.KolekcjonerMissions;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.*;

public class KolekcjonerNPC {

    public KolekcjonerNPC(RPGCORE rpgcore) {
        this.userMap = rpgcore.getMongoManager().loadAllKolekcjoner();
    }

    // WAZNE MAPY
    private final Map<UUID, KolekcjonerObject> userMap;

    public final void openKolekcjonerGUI(Player player) {
        final UUID uuid = player.getUniqueId();
        final KolekcjonerUser user = this.find(uuid).getKolekcjonerUser();
        final KolekcjonerMissions mission = KolekcjonerMissions.getByNumber(user.getMission());
        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&6&lKolekcjoner"));

        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setName(" ").toItemStack().clone());
        }

        gui.setItem(4, this.getStatystykiItem(user));
        gui.setItem(11, this.getIfItemIsGiven(mission, 0, user.getMissionProgress().get(0)));
        gui.setItem(12, this.getIfItemIsGiven(mission, 1, user.getMissionProgress().get(1)));
        gui.setItem(13, this.getIfItemIsGiven(mission, 2, user.getMissionProgress().get(2)));
        gui.setItem(14, this.getIfItemIsGiven(mission, 3, user.getMissionProgress().get(3)));
        gui.setItem(15, this.getIfItemIsGiven(mission, 4, user.getMissionProgress().get(4)));

        player.openInventory(gui);
    }

    private ItemStack getIfItemIsGiven(final KolekcjonerMissions missions, final int i, final boolean isGiven) {
        final ItemStack item = missions.getReqItems()[i].clone();
        final ItemMeta meta = item.getItemMeta();
        final List<String> lore = meta.getLore();
        if (isGiven) {
            meta.setDisplayName(meta.getDisplayName() + Utils.format(" &a✓"));
        } else {
            meta.setDisplayName(meta.getDisplayName() + Utils.format(" &c✘"));
        }
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack getStatystykiItem(final KolekcjonerUser user) {
        return new ItemBuilder(Material.PAPER).setName("&c&lStatystyki").setLore(Arrays.asList(
                "&7Misja: &c" + user.getMission(),
                "&7Szczescie: &c" + user.getSzczescie(),
                "&7Silny przeciwko Ludziom: &c" + user.getSilnyNaLudzi(),
                "&7Dodatkowe Obrazenia: &c" + user.getDodatkowe())).toItemStack().clone();
    }


    public boolean hasGivenBackAll(final KolekcjonerUser user) {
        return !user.getMissionProgress().contains(false);
    }


    public void add(KolekcjonerObject kolekcjonerObject) {
        this.userMap.put(kolekcjonerObject.getID(), kolekcjonerObject);
    }

    public KolekcjonerObject find(final UUID uuid) {
        this.userMap.computeIfAbsent(uuid, k -> new KolekcjonerObject(uuid));
        return this.userMap.get(uuid);
    }

    public ImmutableSet<KolekcjonerObject> getKolekcjonerObject() {
        return ImmutableSet.copyOf(this.userMap.values());
    }

}
