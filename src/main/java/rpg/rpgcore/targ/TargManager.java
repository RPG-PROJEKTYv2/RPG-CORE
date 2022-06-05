package rpg.rpgcore.targ;

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

    //TODO zrobic zapis targow po edycji ich (kupienie, sprzedanie)


    private final RPGCORE rpgcore;

    private final ItemBuilder fill = new ItemBuilder(Material.STAINED_GLASS_PANE, 1 ,(short) 15);
    private final ItemBuilder goBack = new ItemBuilder(Material.ARROW);
    private final ItemBuilder targItem = new ItemBuilder(Material.CHEST);
    private final ItemBuilder goLeftAllow = new ItemBuilder(Material.ARROW);
    private final ItemBuilder goLeftDisallow = new ItemBuilder(Material.BARRIER);
    private final ItemBuilder goRightAllow = new ItemBuilder(Material.ARROW);
    private final ItemBuilder goRightDisallow = new ItemBuilder(Material.BARRIER);
    private final HashMap<UUID, Inventory> playerTargMap = new HashMap<>();
    private final HashMap<UUID, Integer> taskMap = new HashMap<>();
    private final List<String> lore = new ArrayList<>();
    private final List<UUID> wystawia = new ArrayList<>();
    private final List<ItemStack> allItems = new ArrayList<>();

    public TargManager(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }


    public Inventory openTargGUI(int page) {
        final Inventory targMain = Bukkit.createInventory(null, 54, Utils.format("      &f&lLista Targow &3" + page));
        allItems.clear();

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (rpgcore.getTargManager().getPlayerTarg(p.getUniqueId()).getItem(0) != null) {
                targItem.setName("&4&l" + p.getName());
                targItem.hideFlag();
                allItems.add(targItem.toItemStack().clone());
            }
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

    @Getter
    public int getPlayerTaskId(final UUID uuid) {
        return this.taskMap.get(uuid);
    }

    @Setter
    public void updatePlayerTask(final UUID uuid, final int task) {
        this.taskMap.replace(uuid, task);
    }

    @Setter
    public void putPlayerTask(final UUID uuid, final int task) {
        this.taskMap.put(uuid, task);
    }

    @Setter
    public void removePlayerFromTaskMap(final UUID uuid) {
        this.taskMap.remove(uuid);
    }

    public boolean isInTaskMap(final UUID uuid) {
        return this.taskMap.containsKey(uuid);
    }

    @Setter
    public void addToWystawia(final UUID uuid) {
        this.wystawia.add(uuid);
    }

    @Setter
    public void removeFromWystawia(final UUID uuid) {
        this.wystawia.remove(uuid);
    }

    public boolean isInWystawia(final UUID uuid) {
        return this.wystawia.contains(uuid);
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

    public void returnPlayerItem(final Player player, final ItemStack itemStack) {
        final ItemMeta meta = itemStack.getItemMeta();

        final List<String> itemLore = meta.getLore();

        final int i = itemLore.size();
        itemLore.remove(i-1);
        itemLore.remove(i-2);
        itemLore.remove(i-3);
        itemLore.remove(i-4);
        itemLore.remove(i-5);
        itemLore.remove(i-6);
        itemLore.remove(i-7);

        meta.setLore(Utils.format(itemLore));
        itemStack.setItemMeta(meta);

        player.getInventory().addItem(itemStack);
    }

    public double getItemCena(final ItemStack is) {
        for (String s : is.getItemMeta().getLore()) {
            if (s.contains("Cena: ")) {
                System.out.println(Utils.removeColor(s).replace("Cena: ", "").replace(" ", "").replace("$", "").trim());
                double cena = Double.parseDouble(Utils.removeColor(s).replace("Cena: ", "").replace(" ", "").replace("$", "").trim());
                System.out.println(cena);
               return cena;
            }
        }
        return 0.0;
    }

    public void givePlayerBoughtItem(final Player player, final ItemStack is) {
        final ItemMeta meta = is.getItemMeta();

        final List<String> lore = meta.getLore();

        final int loreSize = lore.size();

        lore.remove(loreSize - 1);
        lore.remove(loreSize - 2);

        meta.setLore(Utils.format(lore));

        is.setItemMeta(meta);

        player.getInventory().addItem(is);
    }

    public void updatePlayerTarg(final Player player, final int slot) {
        final Inventory targ = this.getPlayerTarg(player.getUniqueId());

        this.givePlayerBoughtItem(player, targ.getItem(slot));

        targ.setItem(slot, null);

        for (int i = 0; i < 45; i++) {
            targ.setItem(targ.firstEmpty(), targ.getItem(i));
            targ.setItem(i, null);
        }

        this.updatePlayerTarg(player.getUniqueId(), targ);

    }
}
