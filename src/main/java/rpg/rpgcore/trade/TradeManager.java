package rpg.rpgcore.trade;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.*;

@Getter
@Setter
public class TradeManager {

    private final RPGCORE rpgcore;
    private final ItemBuilder fill = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15);
    private final ItemBuilder finalTrade1 = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 0);
    private final ItemBuilder finalTrade2 = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 5);
    private final ItemBuilder noAccept = new ItemBuilder(Material.STAINED_CLAY, 1, (short) 14);
    private final ItemBuilder accept = new ItemBuilder(Material.STAINED_CLAY, 1, (short) 5);
    private final HashMap<UUID, UUID> tradeMap = new HashMap<>();
    private final List<UUID> acceptList = new ArrayList<>();
    private final List<Inventory> tradeAccepted = new ArrayList<>();

    public TradeManager(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    public Inventory createTradeGUI(final UUID sender, final UUID target) {
        final Inventory tradeGUI = Bukkit.createInventory(null, 54, Utils.format("&4&lWymiana &6&l" + rpgcore.getUserManager().find(sender).getName() + " &4&l- &6&l" + rpgcore.getUserManager().find(target).getName()));

        fill.setName(" ");
        fill.hideFlag();

        for (int i = 0; i < tradeGUI.getSize(); i++) {
            if (! ( (i > 9 && i < 13) || (i > 13 && i< 17) || (i > 18 && i < 22) || (i > 22 && i < 26) || (i > 27 && i < 31) || (i > 31 && i < 35) || (i > 36 && i < 40) ||
                    (i > 40 && i < 44)) ) {
                tradeGUI.setItem(i, fill.toItemStack());
            }
        }


        tradeGUI.setItem(48, getNoAcceptItem(sender));

        tradeGUI.setItem(50, getNoAcceptItem(target));
        return tradeGUI;
    }


    public boolean isInAcceptList(final UUID target) {
        return this.acceptList.contains(target);
    }

    public boolean isInTradeMapAsKey(final UUID uuid) {
        return this.tradeMap.containsKey(uuid);
    }

    public boolean isInTradeMapAsValue(final UUID uuid) {
        return this.tradeMap.containsValue(uuid);
    }

    public boolean isTradeAccepted(final Inventory inventory) {
        return this.tradeAccepted.contains(inventory);
    }

    public HashMap<UUID, UUID> getTradeMap() {
        return tradeMap;
    }

    public void putInTradeMap(final UUID sender, final UUID target) {
        this.tradeMap.put(sender, target);
    }

    public void addToAcceptList(final UUID target) {
        this.acceptList.add(target);
    }

    public void removeFromAcceptList(final UUID target) {
        this.acceptList.remove(target);
    }

    public void canceltrade(final UUID sender, final UUID target) {
        if (!this.isInAcceptList(target)) {
            return;
        }
        this.acceptList.remove(target);
        this.tradeMap.remove(sender, target);
        if (Bukkit.getPlayer(sender) != null) {
            Bukkit.getPlayer(sender).sendMessage(Utils.format(Utils.TRADEPREFIX + "&7Gracz &6" + rpgcore.getUserManager().find(target).getName() + " &7nie zaakceptowal wymiany na czas!"));
        }
        if (Bukkit.getPlayer(target) != null) {
            Bukkit.getPlayer(target).sendMessage(Utils.format(Utils.TRADEPREFIX + "&7Czas na zaakceptowanie wymiany od gracza &6" + rpgcore.getUserManager().find(sender).getName() + " &7wylasnie minal!"));
        }
    }


    public ItemStack getNoAcceptItem(final UUID target) {
        noAccept.setName("&6" + rpgcore.getUserManager().find(target).getName() + " &cjeszcze nie zaakceptowal wymiany");
        noAccept.hideFlag();

        return this.noAccept.toItemStack();
    }

    public ItemStack getAcceptItem(final UUID target) {
        accept.setName("&6" + rpgcore.getUserManager().find(target).getName() + " &azaakceptowal wymiane");
        accept.hideFlag();
        return this.accept.toItemStack();
    }

    public void tradeAccepted(final Inventory inventory, final Player firstViewer, final Player secViewer) {
        this.tradeAccepted.add(inventory);
        final List<ItemStack> firstViewerItems = new ArrayList<>();
        final List<ItemStack> secViewerItems = new ArrayList<>();

        for (int i = 0; i < inventory.getSize(); i++) {

            if ((i > 9 && i < 13) || (i > 18 && i < 22) || (i > 27 && i < 31) || (i > 36 && i < 40)) {
                if (inventory.getItem(i) != null) {
                    firstViewerItems.add(inventory.getItem(i));
                }
            }

            if ((i > 13 && i < 17) || (i > 22 && i < 26) || (i > 31 && i < 35) || (i > 40 && i < 44)) {
                if (inventory.getItem(i) != null) {
                    secViewerItems.add(inventory.getItem(i));
                }
            }

        }

        secViewer.sendMessage(Utils.format(Utils.TRADEPREFIX + "&7Rezultat wymiany z graczem &6" + firstViewer.getName()));
        for (ItemStack item : firstViewerItems) {
            secViewer.getInventory().addItem(item);
            if (item.getItemMeta().getDisplayName() == null) {
                secViewer.sendMessage(Utils.format("&7- &6" + item.getAmount() + "x " + item.getType()));
            } else {
                secViewer.sendMessage(Utils.format("&7- &6" + item.getAmount() + "x &r" + item.getItemMeta().getDisplayName()));
            }
        }


        firstViewer.sendMessage(Utils.format(Utils.TRADEPREFIX + "&7Rezultat wymiany z graczem &6" + secViewer.getName()));
        for (ItemStack item : secViewerItems) {
            firstViewer.getInventory().addItem(item);
            if (item.getItemMeta().getDisplayName() == null) {
                firstViewer.sendMessage(Utils.format("&7- &6" + item.getAmount() + "x " + item.getType()));
            } else {
                firstViewer.sendMessage(Utils.format("&7- &6" + item.getAmount() + "x &r" + item.getItemMeta().getDisplayName()));
            }
        }

        rpgcore.getTradeManager().getTradeMap().remove(firstViewer.getUniqueId(), secViewer.getUniqueId());
        tradeAccepted.remove(inventory);
        firstViewer.closeInventory();
        secViewer.closeInventory();

    }
}
