package rpg.rpgcore.magazyn;

import com.google.common.collect.ImmutableSet;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

public class MagazynManager {

    private final Map<UUID, MagazynObject> userMap;

    public MagazynManager(final RPGCORE rpgcore) {
        this.userMap = rpgcore.getMongoManager().loadAllMagazyny();
    }

    public void openMagazynyList(final Player player) {
        final MagazynUser user = this.find(player.getUniqueId()).getMagazynUser();
        final Inventory gui = Bukkit.createInventory(null, InventoryType.HOPPER, Utils.format("&c&lLista Magazynow"));
        if (user.isUnlocked1()) {
            gui.setItem(0, new ItemBuilder(Material.CHEST).setName("&aMagazyn #1").setLore(Arrays.asList("&a&lOdblokowany!", "&8&oKliknij&8, aby otworzyc")).hideFlag().toItemStack().clone());
        } else {
            gui.setItem(0, new ItemBuilder(Material.CHEST).setName("&cMagazyn #1").setLore(Arrays.asList("&c&lZablokowany!")).hideFlag().toItemStack().clone());
        }
        if (user.isUnlocked2()) {
            gui.setItem(1, new ItemBuilder(Material.CHEST).setName("&aMagazyn #2").setLore(Arrays.asList("&a&lOdblokowany!", "&8&oKliknij&8, aby otworzyc")).hideFlag().toItemStack().clone());
        } else {
            gui.setItem(1, new ItemBuilder(Material.CHEST).setName("&cMagazyn #2").setLore(Arrays.asList("&c&lZablokowany!")).hideFlag().toItemStack().clone());
        }
        if (user.isUnlocked3()) {
            gui.setItem(2, new ItemBuilder(Material.CHEST).setName("&aMagazyn #3").setLore(Arrays.asList("&a&lOdblokowany!", "&8&oKliknij&8, aby otworzyc")).hideFlag().toItemStack().clone());
        } else {
            gui.setItem(2, new ItemBuilder(Material.CHEST).setName("&cMagazyn #3").setLore(Arrays.asList("&c&lZablokowany!")).hideFlag().toItemStack().clone());
        }
        if (user.isUnlocked4()) {
            gui.setItem(3, new ItemBuilder(Material.CHEST).setName("&aMagazyn #4").setLore(Arrays.asList("&a&lOdblokowany!", "&8&oKliknij&8, aby otworzyc")).hideFlag().toItemStack().clone());
        } else {
            gui.setItem(3, new ItemBuilder(Material.CHEST).setName("&cMagazyn #4").setLore(Arrays.asList("&c&lZablokowany!")).hideFlag().toItemStack().clone());
        }
        if (user.isUnlocked5()) {
            gui.setItem(4, new ItemBuilder(Material.CHEST).setName("&aMagazyn #5").setLore(Arrays.asList("&a&lOdblokowany!", "&8&oKliknij&8, aby otworzyc")).hideFlag().toItemStack().clone());
        } else {
            gui.setItem(4, new ItemBuilder(Material.CHEST).setName("&cMagazyn #5").setLore(Arrays.asList("&c&lZablokowany!")).hideFlag().toItemStack().clone());
        }

        player.openInventory(gui);
    }

    public void openPlayerMagazyn(final Player player, final int nr) {
        final MagazynUser user = this.find(player.getUniqueId()).getMagazynUser();
        final Inventory gui = Bukkit.createInventory(null, 54, Utils.format("&c&lMagazyn #" + nr));
        if (!user.isEmpty(nr)) {
            try {
                gui.setContents(Utils.itemStackArrayFromBase64(user.getMagazyn(nr)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        for (int i = 45; i < gui.getSize(); i++) {
            gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setName(" ").toItemStack());
        }
        gui.setItem(49, new ItemBuilder(Material.ARROW).setName("&c&lPowrot").hideFlag().toItemStack().clone());

        player.openInventory(gui);
    }


    public MagazynObject find(final UUID uuid) {
        this.userMap.computeIfAbsent(uuid, k -> new MagazynObject(uuid));
        return userMap.get(uuid);
    }

    public void add(final MagazynObject magazynObject) {
        this.userMap.put(magazynObject.getId(), magazynObject);
    }

    public ImmutableSet<MagazynObject> getMagazynObjects() {
        return ImmutableSet.copyOf(this.userMap.values());
    }
}
