package rpg.rpgcore.trade;

import lombok.Getter;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.trade.objects.Trade;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class TradeManager {

    private final Map<String, Trade> tradeMap = new HashMap<>();
    @Getter
    private final Map<UUID, UUID> tradeRequestMap = new HashMap<>();


    public boolean isInTradeRequestMap(final UUID uuid) {
        return tradeRequestMap.containsValue(uuid);
    }

    public void sendTradeRequest(final UUID uuid1, final UUID uuid2) {
        tradeRequestMap.put(uuid1, uuid2);
    }

    public void removeTradeRequest(final UUID uuid1, final UUID uuid2) {
        tradeRequestMap.remove(uuid1, uuid2);
    }


    public void createTrade(final UUID uuid1, final UUID uuid2, final Player player1, final Player player2) {
        final Trade trade = new Trade(uuid1, uuid2, player1, player2);
        tradeMap.put(player1.getName() + " - " + player2.getName(), trade);

        openGUI(trade);
    }

    public void openGUI(final Trade trade) {
        final Inventory guiPlayer1 = Bukkit.createInventory(trade.getPlayer1(), 54, Utils.format("&6&l" + trade.getPlayer1().getName() + " &8&l- &6&l" + trade.getPlayer2().getName()));
        final Inventory guiPlayer2 = Bukkit.createInventory(trade.getPlayer2(), 54, Utils.format("&6&l" + trade.getPlayer2().getName() + " &8&l- &6&l" + trade.getPlayer1().getName()));

        int j1 = 0;
        int j2 = 0;
        int j3 = 0;
        int j4 = 0;

        for (int i = 0; i < guiPlayer1.getSize(); i++) {
            if ((i > 13 && i < 17) || (i > 22 && i < 26) || (i > 31 && i < 35) || (i > 40 && i < 44)) {

                if (!trade.getItems(trade.getPlayer1().getUniqueId()).isEmpty()) {
                    if (trade.getItems(trade.getPlayer1().getUniqueId()).size() > j1) {
                        guiPlayer2.setItem(i, trade.getItems(trade.getPlayer1().getUniqueId()).get(j1));
                        j1++;
                    } else {
                        guiPlayer2.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName(" ").toItemStack());
                    }
                } else {
                    guiPlayer2.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName(" ").toItemStack());
                }

                if (!trade.getItems(trade.getPlayer2().getUniqueId()).isEmpty()) {
                    if (trade.getItems(trade.getPlayer2().getUniqueId()).size() > j2) {
                        guiPlayer1.setItem(i, trade.getItems(trade.getPlayer2().getUniqueId()).get(j2));
                        j2++;
                    } else {
                        guiPlayer1.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName(" ").toItemStack());
                    }
                } else {
                    guiPlayer1.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName(" ").toItemStack());
                }
            }

            if (!((i > 9 && i < 13) || (i > 13 && i < 17) || (i > 18 && i < 22) || (i > 22 && i < 26) || (i > 27 && i < 31) || (i > 31 && i < 35) || (i > 36 && i < 40) || (i > 40 && i < 44))) {
                if (i % 2 == 0) {
                    guiPlayer1.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 8).setName(" ").toItemStack());
                    guiPlayer2.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 8).setName(" ").toItemStack());
                } else {
                    guiPlayer1.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName(" ").toItemStack());
                    guiPlayer2.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName(" ").toItemStack());
                }
            } else if ((i > 9 && i < 13) || (i > 18 && i < 22) || (i > 27 && i < 31) || (i > 36 && i < 40)) {
                if (!trade.getItems(trade.getPlayer1().getUniqueId()).isEmpty()) {
                    if (trade.getItems(trade.getPlayer1().getUniqueId()).size() > j3) {
                        guiPlayer1.setItem(i, trade.getItems(trade.getPlayer1().getUniqueId()).get(j3));
                        j3++;
                    }
                }

                if (!trade.getItems(trade.getPlayer2().getUniqueId()).isEmpty()) {
                    if (trade.getItems(trade.getPlayer2().getUniqueId()).size() > j4) {
                        guiPlayer2.setItem(i, trade.getItems(trade.getPlayer2().getUniqueId()).get(j4));
                        j4++;
                    }
                }
            }
        }


        guiPlayer1.setItem(48, getAcceptItem(trade, trade.getPlayer1()));
        guiPlayer1.setItem(50, getAcceptItem(trade, trade.getPlayer2()));

        guiPlayer2.setItem(48, getAcceptItem(trade, trade.getPlayer2()));
        guiPlayer2.setItem(50, getAcceptItem(trade, trade.getPlayer1()));

        trade.setGuiPlayer1(guiPlayer1);
        trade.setGuiPlayer2(guiPlayer2);

        trade.getPlayer1().openInventory(guiPlayer1);
        trade.getPlayer2().openInventory(guiPlayer2);

        trade.setItemAdded(false);
    }

    public ItemStack getAcceptItem(final Trade trade, final Player player) {
        if (trade == null) {
            return null;
        }

        if (trade.getAcceptMap().get(player.getUniqueId()))
            return new ItemBuilder(Material.STAINED_CLAY, 1, (short) 5).setName("&6" + player.getName() + " &azaakceptowal wymiane!").addGlowing().toItemStack().clone();
        else
            return new ItemBuilder(Material.STAINED_CLAY, 1, (short) 14).setName("&6" + player.getName() + " &cnie zaakceptowal wymiany!").addGlowing().toItemStack().clone();
    }

    public void finishTrade(final Trade trade) {
        trade.getPlayer1().sendMessage(Utils.format(Utils.TRADEPREFIX + "&aWymiana zakonczona pomyslnie!"));
        trade.getPlayer2().sendMessage(Utils.format(Utils.TRADEPREFIX + "&aWymiana zakonczona pomyslnie!"));
        givePlayerItems(trade.getPlayer1(), trade.getItems(trade.getPlayer2().getUniqueId()));
        givePlayerItems(trade.getPlayer2(), trade.getItems(trade.getPlayer1().getUniqueId()));
        trade.getPlayer1().closeInventory();
        trade.getPlayer2().closeInventory();
        trade.log();
        tradeMap.remove(trade.getName());
    }


    public void givePlayerItems(final Player player, final CopyOnWriteArrayList<ItemStack> items) {
        player.sendMessage(Utils.format(Utils.TRADEPREFIX + "&7Otrzymane przedmioty: "));
        if (items.isEmpty()) {
            player.sendMessage(Utils.format("&8- &cBrak"));
            return;
        }
        for (final ItemStack item : items) {
            if (item != null) {
                player.getInventory().addItem(item);
                final TextComponent itemComponent = new TextComponent(Utils.format("&8- &6" + item.getAmount() + "x &7" + item.getItemMeta().getDisplayName()));
                itemComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new ComponentBuilder(CraftItemStack.asNMSCopy(item).save(new NBTTagCompound()).toString()).create()));
                player.spigot().sendMessage(itemComponent);
            }
        }
    }


    public Trade find(final String playerName) {
        for (final Map.Entry<String, Trade> entry : tradeMap.entrySet()) {
            if (entry.getKey().contains(playerName)) {
                return entry.getValue();
            }
        }
        return null;
    }

    public void remove(final Trade trade) {
        for (final Map.Entry<String, Trade> entry : tradeMap.entrySet()) {
            if (entry.getValue().getName().equals(trade.getName())) {
                tradeMap.remove(entry.getKey());
                return;
            }
        }
    }

    public void remove(final String name) {
        tradeMap.remove(name);
    }
}
