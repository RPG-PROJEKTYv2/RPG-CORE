package rpg.rpgcore.npc.kowal;


import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.GlobalItems;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.*;

public class KowalNPC {

    private final RPGCORE rpgcore;

    public KowalNPC(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    // WAZNE MAPY I LISTY
    private final Map<UUID, List<ItemStack>> ostatniUlepszonyItem = new HashMap<>();

    // ITEMY DO GUI
    private final ItemBuilder anvil = new ItemBuilder(Material.ANVIL);
    private final ItemBuilder ulepszItem = new ItemBuilder(Material.ANVIL);
    private final ItemBuilder placeForItem = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 5);
    private final ItemBuilder placeForZwoj = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 5);
    private final ItemBuilder dalsze = new ItemBuilder(Material.REDSTONE_TORCH_ON);
    private final ItemBuilder fill = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14);

    public void openKowalMainGui(final Player player) {

        player.getInventory().addItem(GlobalItems.getZwojBlogoslawienstwa(1));

        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&4&lKowal"));
        final List<String> lore = new ArrayList<>();

        fill.setName(" ");

        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, fill.toItemStack());
        }

        lore.add(" ");
        lore.add("&8Pamietaj, ze &4Administracja&8 nie ponosi");
        lore.add("&8odpowiedzialnosci za itemy stracone");
        lore.add("&8przez blad wlasny gracza");

        gui.setItem(12, anvil.setName("&aUlepsz swoj przedmiot").setLore(lore).toItemStack());

        lore.clear();
        lore.add(" ");
        lore.add("&7Wymagania: ");
        lore.add("&8>> &61 000 000&2$");
        if (rpgcore.getPlayerManager().getPlayerLvl(player.getUniqueId()) < 75) {
            lore.add("&8>> &c75 &7Lvl");
        } else {
            lore.add("&8>> &a75 &7Lvl");
        }
        gui.setItem(14, dalsze.setName("&cDalsze Etapy &4DT").setLore(lore).toItemStack());

        player.openInventory(gui);
    }

    public void openKowalUlepszanieGui(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 36, Utils.format("&4&lKowal - &6&lUlepszanie"));
        final List<String> lore = new ArrayList<>();

        fill.setName(" ");

        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, fill.toItemStack());
        }

        gui.setItem(12, this.getPlaceForItem());
        gui.setItem(14, this.getPlaceForZwoj());

        lore.add("&7Szansa na ulepszenie przedmiotu: &c50%");
        lore.add(" ");
        lore.add("&8Pamietaj, ze &cAdministracja &8nie ponosi");
        lore.add("&8odpowiedzialnosci za spalone itemy");
        lore.add("&8jezeli bylo to spowodowane bledem serwera");
        lore.add("&cmusisz posiadac nagranie &8potwierdzajace blad");

        gui.setItem(22, ulepszItem.setName("&cUlepsz swoj przedmiot").setLore(lore).toItemStack());


        player.openInventory(gui);
    }

    public ItemStack getPlaceForItem() {
        return placeForItem.setName("&aMiejsce na przedmiot").toItemStack();
    }

    public ItemStack getPlaceForZwoj() {
        return placeForZwoj.setName("&aMiejsce na zwoj").toItemStack();
    }

    public List<ItemStack> getOstatniUlepszonyItem(final UUID uuid) {
        return ostatniUlepszonyItem.get(uuid);
    }

    public void updateOstatniUlepszonyItem(final UUID uuid, final List<ItemStack> item) {
        ostatniUlepszonyItem.remove(uuid);
        ostatniUlepszonyItem.put(uuid, item);
    }

    public void addOstatniUlepszonyItem(final UUID uuid, final ItemStack is) {
        ostatniUlepszonyItem.computeIfAbsent(uuid, k -> new ArrayList<>());
        ostatniUlepszonyItem.get(uuid).add(is);
    }

    public void removeOstatniUlepszonyItem(final UUID uuid, final ItemStack is) {
        ostatniUlepszonyItem.get(uuid).remove(is);
    }

}
