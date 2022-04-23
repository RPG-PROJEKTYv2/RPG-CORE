package rpg.rpgcore.managers;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.*;

public class TradeManager {

    private final RPGCORE rpgcore;
    private final ItemBuilder fill = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15);
    private final ItemBuilder noAccept = new ItemBuilder(Material.STAINED_CLAY, 1, (short) 14);
    private final ItemBuilder accept = new ItemBuilder(Material.STAINED_CLAY, 1, (short) 5);
    private final HashMap<UUID, UUID> tradeMap = new HashMap<>();
    private final List<UUID> acceptList = new ArrayList<>();

    public TradeManager(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    public Inventory createTradeGUI(final UUID sender, final UUID target) {
        final Inventory tradeGUI = Bukkit.createInventory(null, 54, Utils.format("&4&lWymiana &6&l" + rpgcore.getPlayerManager().getPlayerName(sender) + " &4&l- &6&l" + rpgcore.getPlayerManager().getPlayerName(target)));

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

    @Getter
    public UUID getTradeTarget(final UUID sender) {
        return this.tradeMap.get(sender);
    }

    @Getter
    public UUID getTradeSender(final UUID target) {
        for (Map.Entry<UUID, UUID> entry : this.tradeMap.entrySet()) {
            if (entry.getValue() == target) {
                return entry.getKey();
            }
        }
        return null;
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

    @Getter
    public HashMap<UUID, UUID> getTradeMap() {
        return tradeMap;
    }

    @Setter
    public void putInTradeMap(final UUID sender, final UUID target) {
        this.tradeMap.put(sender, target);
    }

    @Setter
    public void addToAcceptList(final UUID target) {
        this.acceptList.add(target);
    }

    @Setter
    public void removeFromAcceptList(final UUID target) {
        this.acceptList.remove(target);
    }

    @Setter
    public void canceltrade(final UUID sender, final UUID target) {
        if (!this.isInAcceptList(target)) {
            return;
        }
        this.acceptList.remove(target);
        this.tradeMap.remove(sender, target);
        if (Bukkit.getPlayer(sender) != null) {
            Bukkit.getPlayer(sender).sendMessage(Utils.format(Utils.TRADEPREFIX + "&7Gracz &6" + rpgcore.getPlayerManager().getPlayerName(target) + " &7nie zaakceptowal wymiany na czas!"));
        }
        if (Bukkit.getPlayer(target) != null) {
            Bukkit.getPlayer(target).sendMessage(Utils.format(Utils.TRADEPREFIX + "&7Czas na zaakceptowanie wymiany od gracza &6" + rpgcore.getPlayerManager().getPlayerName(sender) + " &7wylasnie minal!"));
        }
    }

    @Getter
    public ItemStack getNoAcceptItem(final UUID target) {
        noAccept.setName("&6" + rpgcore.getPlayerManager().getPlayerName(target) + " &cjeszcze nie zaakceptowal wymiany");
        noAccept.hideFlag();

        return this.noAccept.toItemStack();
    }

    @Getter
    public ItemStack getAcceptItem(final UUID target) {
        accept.setName("&6" + rpgcore.getPlayerManager().getPlayerName(target) + " &azaakceptowal wymiane");
        accept.hideFlag();
        return this.accept.toItemStack();
    }

}
