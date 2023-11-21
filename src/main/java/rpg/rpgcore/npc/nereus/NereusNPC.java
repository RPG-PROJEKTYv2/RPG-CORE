package rpg.rpgcore.npc.nereus;

import com.google.common.collect.ImmutableSet;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.npc.nereus.objects.NereusUser;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.npc.NereusItems;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

public class NereusNPC {

    private final Map<UUID, NereusUser> userMap;

    public NereusNPC(final RPGCORE rpgcore) {
        this.userMap = rpgcore.getMongoManager().loadAllNereus();
    }


    public void openNereusGUI(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 45, Utils.format("&9Nereus"));
        final NereusUser user = this.find(player.getUniqueId());
        for (int i = 0; i < gui.getSize(); i++) {
            if (i % 2 == 0) gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 11).setName(" ").toItemStack());
            else gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 3).setName(" ").toItemStack());
        }

        gui.setItem(22, new ItemBuilder(Material.WORKBENCH).setName("&eWytworz Losowy Relikt").setLore(Arrays.asList(
                "&f&lWymagane Przedmioty",
                " &8- &bFragment Reliktu &7x12 &8(" + Utils.getPlayerInventoryItemCount(player, NereusItems.I1.getItemStack()) + "&8/12)",
                " &8- &620 000 000&2$",
                "",
                "&f&lMaksymalne Wartosci Reliktow",
                "&8 - &4&lRelikt Potegi &71-15%",
                "&8 - &2&lRelikt Wiecznosci &71-20HP",
                "&8 - &6&lRelikt Starozytnosci &71-25%",
                "&8 - &a&lRelikt Przodkow &71-15%"
        )).toItemStack().clone());
        gui.setItem(4, (user.getPotegi().getType().equals(Material.AIR) ? getMiejsceNa("Potęgi") : user.getPotegi().clone()));
        gui.setItem(19, (user.getStarozytnosci().getType().equals(Material.AIR) ? getMiejsceNa("Starozytnosci") : user.getStarozytnosci().clone()));
        gui.setItem(25, (user.getPrzodkow().getType().equals(Material.AIR) ? getMiejsceNa("Przodkow") : user.getPrzodkow().clone()));
        gui.setItem(40, (user.getWiecznosci().getType().equals(Material.AIR) ? getMiejsceNa("Wiecznosci") : user.getWiecznosci().clone()));

        gui.setItem(44, new ItemBuilder(Material.PAPER).setName("&c&lInformacje").setLore(Arrays.asList(
                "&bFragmenty Reliktu &7mozna zdobyc z",
                "&cKAZDEGO &7potwora na &cDOWOLNYM DUNGEONIE",
                "&8tj. Dung60-70, Dung70-80, Dung80-90, Dung90-100"
        )).toItemStack());

        player.openInventory(gui);
    }

    public ItemStack getMiejsceNa(final String name) {
        return new ItemBuilder(Material.IRON_FENCE).setName("&c&lMiejsce na Relikt " + name).toItemStack().clone();
    }


    public NereusUser find(final UUID uuid) {
        return this.userMap.get(uuid);
    }

    public void add(final NereusUser user) {
        this.userMap.put(user.getUuid(), user);
    }

    public void remove(final NereusUser user) {
        this.userMap.remove(user.getUuid());
    }

    public ImmutableSet<NereusUser> getNereusUsers() {
        return ImmutableSet.copyOf(this.userMap.values());
    }
}
