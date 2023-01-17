package rpg.rpgcore.npc.handlarz;

import com.google.common.collect.ImmutableSet;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.npc.handlarz.objects.HandlarzUser;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

public class HandlarzNPC {
    private final RPGCORE rpgcore;
    private final Map<UUID, HandlarzUser> userMap;

    public HandlarzNPC(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
        this.userMap = rpgcore.getMongoManager().loadAllHandlarz();
    }

    public void openHandlarzGUI(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&a&lHandlarz"));

        for (int i = 0; i < 27; i++) {
            if (i % 2 == 0) {
                gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 13).setName(" ").toItemStack());
            } else {
                gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 5).setName(" ").toItemStack());
            }
        }

        gui.setItem(10, new ItemBuilder(Material.DIAMOND).setName("&bWartosciowe przedmioty").toItemStack());
        gui.setItem(13, new ItemBuilder(Material.GOLD_INGOT).setName("&6Sprzedaz/Kupno").toItemStack());
        gui.setItem(16, new ItemBuilder(Material.EMERALD).setName("&6&lItem&2&lShop").toItemStack());

        player.openInventory(gui);
    }

    public void openHandlarzWartosciowe(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&a&lHandlarz &8>> &b&lWartosciowe przedmioty"));

        for (int i = 0; i < 27; i++) {
            if (i % 2 == 0) {
                gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 13).setName(" ").toItemStack());
            } else {
                gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 5).setName(" ").toItemStack());
            }
        }

        gui.setItem(11, new ItemBuilder(Material.SIGN).setName("&cTu przepustak 80-90").toItemStack());
        gui.setItem(12, new ItemBuilder(Material.SIGN).setName("&cTu przepustak 90-100").toItemStack());
        gui.setItem(13, new ItemBuilder(Material.SIGN).setName("&cTu przepustak 100-110").toItemStack());
        gui.setItem(14, new ItemBuilder(Material.SIGN).setName("&cTu przepustak 110-120").toItemStack());
        gui.setItem(15, new ItemBuilder(Material.SIGN).setName("&cTu przepustak 120-130").toItemStack());

        player.openInventory(gui);
    }

    public void openHandlarzKupnoSprzedaz(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&a&lHandlarz &8>> &6&lSprzedaz/Kupno"));

        for (int i = 0; i < 27; i++) {
            if (i % 2 == 0) {
                gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 13).setName(" ").toItemStack());
            } else {
                gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 5).setName(" ").toItemStack());
            }
        }

        gui.setItem(11, new ItemBuilder(Material.NETHER_STAR).setName("&a&lSklep Dzienny").setLore(Arrays.asList("", "&c&lDostepne od aktualizacji V0.2")).toItemStack());
        gui.setItem(15, new ItemBuilder(Material.GOLD_NUGGET).setName("&e&lSprzedaz").toItemStack());

        player.openInventory(gui);
    }

    public void openHandlarzItemShop(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 54, Utils.format("&a&lHandlarz &8>> &6&lItem&2&lShop"));

        for (int i = 0; i < 27; i++) {
            if (i % 2 == 0) {
                gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 13).setName(" ").toItemStack());
            } else {
                gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 5).setName(" ").toItemStack());
            }
        }
    }


    public HandlarzUser find(final UUID uuid) {
        return userMap.get(uuid);
    }

    public void add(final HandlarzUser user) {
        userMap.put(user.getUuid(), user);
    }

    public ImmutableSet<HandlarzUser> getHandlarzUsers() {
        return ImmutableSet.copyOf(userMap.values());
    }


}
