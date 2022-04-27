package rpg.rpgcore.managers;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.TargUtils;
import rpg.rpgcore.utils.Utils;

import java.util.*;

public class TargManager {

    private final RPGCORE rpgcore;

    private final ItemBuilder fill = new ItemBuilder(Material.STAINED_GLASS_PANE, 1 ,(short) 15);
    private final ItemBuilder goBack = new ItemBuilder(Material.ARROW);
    private final ItemBuilder targItem = new ItemBuilder(Material.CHEST);
    private final ItemBuilder goLeftAllow = new ItemBuilder(Material.ARROW);
    private final ItemBuilder goLeftDisallow = new ItemBuilder(Material.BARRIER);
    private final ItemBuilder goRightAllow = new ItemBuilder(Material.ARROW);
    private final ItemBuilder goRightDisallow = new ItemBuilder(Material.BARRIER);
    private final HashMap<UUID, Inventory> playerTargMap = new HashMap<>();
    private final List<String> lore = new ArrayList<>();
    private final List<ItemStack> allItems = new ArrayList<>();

    public TargManager(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }


    public Inventory openTargGUI(int page) {
        final Inventory targMain = Bukkit.createInventory(null, 54, Utils.format("      &f&lLista Targow &3" + page));
        allItems.clear();

        for (Player p : Bukkit.getOnlinePlayers()) {
            targItem.setName("&4&l" + p.getName());
            targItem.hideFlag();
            allItems.add(targItem.toItemStack().clone());
        }

        fill.setName(" ");
        fill.hideFlag();

        for (int i = 46; i < 54; i++) {
            targMain.setItem(i, fill.toItemStack());
        }

        if (TargUtils.isPageValid(allItems, page - 1, 45)) {
            goLeftAllow.setName("&aPrzejdz na poprzednia strone");
            goLeftAllow.hideFlag();
            targMain.setItem(45, goLeftAllow.toItemStack());
        } else {
            goLeftDisallow.setName("&cPrzejdz na poprzednia strone");
            goLeftDisallow.hideFlag();
            targMain.setItem(45, goLeftDisallow.toItemStack());
        }

        if (TargUtils.isPageValid(allItems, page + 1, 45)) {
            goRightAllow.setName("&aPrzejdz na nastepna strone");
            goRightAllow.hideFlag();
            targMain.setItem(53, goRightAllow.toItemStack());
        } else {
            goRightDisallow.setName("&cPrzejdz na nastepna strone");
            goRightDisallow.hideFlag();
            targMain.setItem(53, goRightDisallow.toItemStack());
        }


        for (ItemStack is : TargUtils.getPageItems(allItems, page, 45)) {
            targMain.setItem(targMain.firstEmpty(), is);
        }

        return targMain;
    }


    @Getter
    public Inventory getPlayerTarg(final UUID uuid) {
        return this.playerTargMap.get(uuid);
    }

    @Setter
    public void putPlayerInTargMap(final UUID uuid, final Inventory targ) {
        this.playerTargMap.put(uuid, targ);
    }

    @Setter
    public void updatePlayerTarg(final UUID uuid, final Inventory targ) {
        this.playerTargMap.replace(uuid, targ);
    }

    public boolean isInPlayerTargMap(final UUID uuid) {
        return this.playerTargMap.containsKey(uuid);
    }


    public void createPlayerTargGUI(final UUID uuid) {
        final Inventory targ = Bukkit.createInventory(null, 54, Utils.format("&f&lTarg gracza &3") + rpgcore.getPlayerManager().getPlayerName(uuid));

        fill.setName(" ");
        fill.addGlowing();

        for (int i = 45; i <  54; i++) {
            targ.setItem(i, fill.toItemStack());
        }

        goBack.setName("&cPowrot");

        lore.add(" ");
        lore.add("&8&o'Kliknij' &8, zeby wrocic do wyboru targow");

        goBack.setLore(lore);
        lore.clear();
        targ.setItem(49, goBack.toItemStack());

        this.putPlayerInTargMap(uuid, targ);
    }
}
