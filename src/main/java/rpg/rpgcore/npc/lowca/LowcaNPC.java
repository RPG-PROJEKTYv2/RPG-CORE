package rpg.rpgcore.npc.lowca;

import com.google.common.collect.ImmutableSet;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.*;

public class LowcaNPC {
    private final Map<UUID, LowcaObject> userMap;

    public LowcaNPC(final RPGCORE rpgcore) {
        this.userMap = rpgcore.getMongoManager().loadAllLowca();
    }


    public void openLowcaGUI(final Player player) {
        final UUID uuid = player.getUniqueId();
        final LowcaUser lowcaUser = this.userMap.get(uuid).getLowcaUser();
        final LowcaMissions lowcaMissions = LowcaMissions.getMission(lowcaUser.getMission());
        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&4&lLowca"));

        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setName(" ").toItemStack());
        }


        for (int i = 0; i < LowcaMissions.getSize(); i++) {
            if (lowcaUser.getMission() > i + 1) {
                gui.setItem(i, new ItemBuilder(Material.BOOK).setName("&4&lMisja " + (i + 1)).setLore(Arrays.asList(" ", "&a&lWykonane!")).addGlowing().toItemStack().clone());
            } else if (lowcaUser.getMission() == i + 1) {
                if (lowcaMissions == LowcaMissions.M99) {
                    gui.setItem(i, lowcaMissions.getReqItem().clone());
                } else {
                    gui.setItem(i, this.getMissionItem(lowcaMissions, lowcaUser));
                }
            } else {
                gui.setItem(i, new ItemBuilder(Material.BOOK_AND_QUILL).setName("&4&lMisja " + (i + 1)).setLore(Arrays.asList("&c&lWykonaj najpierw poprzednia misje!")).toItemStack().clone());
            }
        }

        gui.setItem(26, new ItemBuilder(Material.PAPER).setName("&c&lStatystyki").setLore(Arrays.asList(
                "&7Szczescie: &c" + lowcaUser.getSzczescie() + " &7pkt",
                "&7Szybkosc: &c" + lowcaUser.getSzybkosc() + " &7pkt",
                "&7Dodatkowe Obrazenia: &c" + lowcaUser.getDodatkoweDmg() + " &7dmg"
        )).toItemStack().clone());

        player.openInventory(gui);
    }

    public ItemStack getMissionItem(final LowcaMissions lowcaMissions, final LowcaUser lowcaUser) {
        return new ItemBuilder(Material.BOOK).setName("&4&lMisja " + lowcaUser.getMission()).setLore(getlore(lowcaMissions.getLore(), lowcaUser, lowcaMissions)).toItemStack().clone();
    }

    private List<String> getlore(final List<String> lore, final LowcaUser lowcaUser, final LowcaMissions lowcaMissions) {
        final List<String> newLore = new ArrayList<>();
        for (String s : lore) {
            newLore.add(s.replace("%leftItem%", (lowcaMissions.getReqAmount() - lowcaUser.getProgress()) + "")
                    .replace("%itemName%", lowcaMissions.getReqItem().getItemMeta().getDisplayName())
                    .replace("%szczescie%", lowcaMissions.getSzczescie() + "")
                    .replace("%szybkosc%", lowcaMissions.getSzybkosc() + "")
                    .replace("%dodatkoweDmg%", lowcaMissions.getDodatkoweDmg() + ""));
        }
        return newLore;
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
