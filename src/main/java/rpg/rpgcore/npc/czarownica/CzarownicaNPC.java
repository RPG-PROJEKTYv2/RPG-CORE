package rpg.rpgcore.npc.czarownica;

import com.google.common.collect.ImmutableSet;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.npc.czarownica.enums.CzarownicaMissions;
import rpg.rpgcore.npc.czarownica.objects.CzarownicaUser;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class CzarownicaNPC {
    private final Map<UUID, CzarownicaUser> userMap;

    public CzarownicaNPC(final RPGCORE rpgcore) {
        this.userMap = rpgcore.getMongoManager().loadAllCzarownica();
    }


    public void click(final Player player) {
        CzarownicaUser czarownicaUser = this.userMap.get(player.getUniqueId());
        if (czarownicaUser == null) {
            czarownicaUser = new CzarownicaUser(player.getUniqueId());
            this.userMap.put(player.getUniqueId(), czarownicaUser);
        }
        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&5&lCzarownica"));
        for (int i = 0; i < gui.getSize(); i++) {
            if (i % 2 == 0) {
                gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName(" ").toItemStack());
            } else {
                gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 10).setName(" ").toItemStack());
            }
        }
        gui.setItem(12, new ItemBuilder(Material.BOOK).setName("&5&kaa&e Kampania &5&kaa").toItemStack());
        if (czarownicaUser.isUnlocked()) {
            gui.setItem(14, new ItemBuilder(Material.WORKBENCH).setName("&5&kaa&e Crafting &5&kaa").toItemStack());
        } else {
            gui.setItem(14, new ItemBuilder(Material.WORKBENCH).setName("&5&kaa&e Crafting &5&kaa").setLore(Arrays.asList(" ", "&4&lZABLOKOWANE")).toItemStack());
        }

        player.openInventory(gui);
    }

    public void openCraftingi(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 9, Utils.format("&5&lCzarownica &8- &eCrafting"));
        for (int i = 0; i < gui.getSize(); i++) {
            if (i % 2 == 0) {
                gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName(" ").toItemStack());
            } else {
                gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 10).setName(" ").toItemStack());
            }
        }
        gui.setItem(4, new ItemBuilder(Material.BOOK_AND_QUILL).setName("&5&kaa &fWytworz &5Magiczna Ksiege &5&kaa").setLore(Arrays.asList(
                "&fOrzeł dodaj lore",
                "&fi ogarnijcie co do craftingu z chytrym"
        )).toItemStack());

        player.openInventory(gui);
    }

    public void openKampania(final Player player) {
        final CzarownicaUser user = this.find(player.getUniqueId());
        final Inventory gui = Bukkit.createInventory(null, 18, Utils.format("&5&lCzarownica &8- &eKampania"));
        for (int i = 9; i < gui.getSize(); i++) {
            if (i % 2 != 0) {
                gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName(" ").toItemStack());
            } else {
                gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 10).setName(" ").toItemStack());
            }
        }
        for (int i = 1; i <= 9; i++) {
            if (i == user.getMission()) {
                gui.setItem(i- 1, Objects.requireNonNull(CzarownicaMissions.getMissionById(i)).getMissionItem(user));
                continue;
            }
            if (i > user.getMission()) {
                gui.setItem(i -1, new ItemBuilder(Material.BOOK).setName("&5Misja " + i).setLore(Arrays.asList(" ", "&7Ukoncz poprzednia misje, zeby odblokowac")).toItemStack().clone());
                continue;
            }
            gui.setItem(i - 1, new ItemBuilder(Material.BOOK).setName("&5Misja " + i).setLore(Arrays.asList(" ", "&a&lUKONCZONE")).toItemStack().clone());
        }

        player.openInventory(gui);
    }


    public CzarownicaUser find(final UUID uuid) {
        return this.userMap.get(uuid);
    }

    public void add(final CzarownicaUser czarownicaUser) {
        this.userMap.put(czarownicaUser.getUuid(), czarownicaUser);
    }

    public void set(final UUID uuid, final CzarownicaUser czarownicaUser) {
        this.userMap.replace(uuid, czarownicaUser);
    }

    public ImmutableSet<CzarownicaUser> getCzarownicaUsers() {
        return ImmutableSet.copyOf(this.userMap.values());
    }
}
