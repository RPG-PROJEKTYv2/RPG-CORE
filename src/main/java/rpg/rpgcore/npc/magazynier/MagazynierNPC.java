package rpg.rpgcore.npc.magazynier;

import com.google.common.collect.ImmutableSet;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.npc.magazynier.objects.MagazynierUser;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

public class MagazynierNPC {
    private final Map<UUID, MagazynierUser> userMap;

    public MagazynierNPC(final RPGCORE rpgcore) {
        this.userMap = rpgcore.getMongoManager().loadAllMagazynier();
    }

    public void openMagazynierMainGUI(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, InventoryType.HOPPER, Utils.format("&b&lMagazynier"));

        gui.setItem(0, new ItemBuilder(Material.CHEST, 1).setName(Utils.format("&a&lLista Magazynow")).setLore(Arrays.asList("&8&oKliknij&8, zeby otworzyc", "&8liste twoich magazynow")).toItemStack());
        gui.setItem(1, new ItemBuilder(Material.EMERALD).setName("&2&lSklep").setLore(Arrays.asList("&8Kupisz tu mozliwosc odblokowania", "&8nowych magazynow i wiele innych", "&8przydatnych rzeczy")).toItemStack());
        gui.setItem(2, new ItemBuilder(Material.BOOK_AND_QUILL).setName("&c&lMisje").toItemStack());
        gui.setItem(3, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setName(" ").toItemStack());
        gui.setItem(4, new ItemBuilder(Material.REDSTONE_TORCH_OFF).setName("&4&lInformacje").setLore(Arrays.asList("&7Wykonuj &cmisje &7i zdobywaj", "&bpunkty&7, a pozniej wymieniaj", "&7je w &2Sklepie &7na &dnagrody", "&7Ale pamietaj:", "&4&lMISJE RESETUJA SIE CO 24H!")).toItemStack());

        player.openInventory(gui);
    }

    public void openMagazynierMisjeGUI(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, InventoryType.HOPPER, Utils.format("&b&lMagazynier &8- &cMisje"));
        final MagazynierUser magazynierUser = this.find(player.getUniqueId());

        if (System.currentTimeMillis() >= magazynierUser.getResetTime()) {
            //this.generateNewMissionSet(magazynierUser);
        }
        //TODO: Misje
        player.openInventory(gui);
    }

    public void openMagazynierSklepGUI(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, InventoryType.HOPPER, Utils.format("&b&lMagazynier &8- &2Sklep"));
        final MagazynierUser magazynierUser = this.find(player.getUniqueId());

        //TODO: Sklep
        player.openInventory(gui);
    }

    public void openMagazynyList(final Player player) {
        final MagazynierUser user = this.find(player.getUniqueId());
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

    public MagazynierUser find(final UUID uuid) {
        return this.userMap.get(uuid);
    }

    public void add(final MagazynierUser user) {
        this.userMap.put(user.getUuid(), user);
    }

    public ImmutableSet<MagazynierUser> getUsers() {
        return ImmutableSet.copyOf(this.userMap.values());
    }
}
