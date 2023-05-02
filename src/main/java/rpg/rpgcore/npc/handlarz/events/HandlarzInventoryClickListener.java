package rpg.rpgcore.npc.handlarz.events;

import com.google.common.collect.Multimap;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.discord.EmbedUtil;
import rpg.rpgcore.npc.handlarz.enums.HandlarzSellItems;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.GlobalItem;
import rpg.rpgcore.utils.globalitems.ItemShop;
import rpg.rpgcore.utils.globalitems.expowiska.Przepustki;

import java.util.UUID;

public class HandlarzInventoryClickListener implements Listener {
    private final RPGCORE rpgcore;

    public HandlarzInventoryClickListener(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClick(final InventoryClickEvent e) {
        if (e.getInventory() == null || e.getClickedInventory() == null) return;

        final Inventory gui = e.getClickedInventory();
        final String title = Utils.removeColor(gui.getTitle());
        final Player player = (Player) e.getWhoClicked();
        final UUID uuid = player.getUniqueId();
        final int slot = e.getSlot();
        final ItemStack item = e.getCurrentItem();

        if (title.equals("Handlarz")) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);

            if (slot == 10) {
                rpgcore.getHandlarzNPC().openHandlarzWartosciowe(player);
                return;
            }
            if (slot == 13) {
                rpgcore.getHandlarzNPC().openHandlarzKupnoSprzedaz(player);
                return;
            }
            if (slot == 16) {
                rpgcore.getHandlarzNPC().openHandlarzItemShop(player);
                return;
            }
            return;
        }

        if (title.equals("Handlarz » Wartosciowe przedmioty")) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
            e.setCancelled(true);
            final User user = RPGCORE.getInstance().getUserManager().find(uuid);
            if (slot == 12) {
                if (user.getKasa() >= Przepustki.I1.getCena()) {
                    player.sendMessage(Utils.SERVERNAME + Utils.format("&7Gratulacje zakupiles " + e.getCurrentItem().getItemMeta().getDisplayName() + " &7x1."));
                    user.setKasa(user.getKasa() - Przepustki.I1.getCena());
                    player.getInventory().addItem(Przepustki.getItem("I1", 1));
                } else {
                    player.closeInventory();
                    player.sendMessage(Utils.SERVERNAME + Utils.format("&7Nie masz wystarczajacej kwoty na zakup tego przedmiotu."));
                }
                return;
            }
            if (slot == 13) {
                if (user.getKasa() >= Przepustki.I2.getCena()) {
                    player.sendMessage(Utils.SERVERNAME + Utils.format("&7Gratulacje zakupiles " + e.getCurrentItem().getItemMeta().getDisplayName() + " &7x1."));
                    user.setKasa(user.getKasa() - Przepustki.I2.getCena());
                    player.getInventory().addItem(Przepustki.getItem("I2", 1));
                } else {
                    player.closeInventory();
                    player.sendMessage(Utils.SERVERNAME + Utils.format("&7Nie masz wystarczajacej kwoty na zakup tego przedmiotu."));
                }
                return;
            }
            if (slot == 14) {
                if (user.getKasa() >= Przepustki.I3.getCena()) {
                    player.sendMessage(Utils.SERVERNAME + Utils.format("&7Gratulacje zakupiles " + e.getCurrentItem().getItemMeta().getDisplayName() + " &7x1."));
                    user.setKasa(user.getKasa() - Przepustki.I3.getCena());
                    player.getInventory().addItem(Przepustki.getItem("I3", 1));
                } else {
                    player.closeInventory();
                    player.sendMessage(Utils.SERVERNAME + Utils.format("&7Nie masz wystarczajacej kwoty na zakup tego przedmiotu."));
                }
                return;
            }

            if (slot == 22) rpgcore.getHandlarzNPC().openHandlarzGUI(player);
            return;
        }


        if (title.equals("Handlarz » Sprzedaz/Kupno")) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
            e.setCancelled(true);

            if (slot == 15) {
                rpgcore.getHandlarzNPC().openHandlarzSprzedaz(player);
                return;
            }

            if (slot == 22) {
                rpgcore.getHandlarzNPC().openHandlarzGUI(player);
                return;
            }

            return;
        }

        if (title.equals("Handlarz » ItemShop")) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
            e.setCancelled(true);

            if (item == null || item.getType() == Material.STAINED_GLASS_PANE) {
                return;
            }

            if (slot == 49) {
                rpgcore.getHandlarzNPC().openHandlarzGUI(player);
                return;
            }

            final User user = rpgcore.getUserManager().find(uuid);

            final int price = Utils.getTagInt(item, "price");
            if (user.getHellcoins() < price) {
                player.sendMessage(Utils.format("&6&lItem&2&lShop &8>> &7Nie posiadasz wystarczajaco &4&lH&6&lS'ow&7!"));
                return;
            }

            user.setHellcoins(user.getHellcoins() - price);
            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataUser(uuid, user));
            player.sendMessage(Utils.format("&6&lItem&2&lShop &8>> &aPomyslnie zakupiles/-as " + item.getItemMeta().getDisplayName()));
            switch (slot) {
                case 10:
                    player.getInventory().addItem(ItemShop.IS9.getItems().get(0));
                    return;
                case 11:
                    player.getInventory().addItem(ItemShop.IS10.getItems().get(0));
                    return;
                case 12:
                    player.getInventory().addItem(ItemShop.IS11.getItems().get(0));
                    return;
                case 15:
                    player.getInventory().addItem(ItemShop.IS19.getItems().get(0));
                    return;
                case 16:
                    player.getInventory().addItem(ItemShop.IS20.getItems().get(0));
                    return;
                case 19:
                    player.getInventory().addItem(ItemShop.IS12.getItems().get(0));
                    return;
                case 20:
                    player.getInventory().addItem(ItemShop.IS13.getItems().get(0));
                    return;
                case 21:
                    player.getInventory().addItem(ItemShop.IS14.getItems().get(0));
                    return;
                case 22:
                    player.getInventory().addItem(ItemShop.IS15.getItems().get(0));
                    return;
                case 37:
                    player.getInventory().addItem(GlobalItem.getItem("I52", 1));
                    return;
                case 38:
                    player.getInventory().addItem(GlobalItem.getItem("I53", 1));
                    return;
                case 39:
                    player.getInventory().addItem(GlobalItem.getItem("I54", 1));
                    return;
                case 40:
                    player.getInventory().addItem(GlobalItem.getItem("I55", 1));
                case 33:
                    player.getInventory().addItem(ItemShop.IS21.getItems().get(0));
                    return;
            }
            return;
        }

        if (Utils.removeColor(player.getOpenInventory().getTopInventory().getTitle()).equals("Handlarz » Sprzedaz")) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
            e.setCancelled(true);

            if (item == null || item.getType() == Material.AIR) return;

            if (e.getClickedInventory() == player.getOpenInventory().getBottomInventory()) {

                final HandlarzSellItems sellItem = HandlarzSellItems.checkIfSellItem(item.clone());

                if (sellItem == null) return;

                if (rpgcore.getHandlarzNPC().getUserItemMap(uuid).size() == 45) {
                    player.sendMessage(Utils.format("&6&lHandlarz &8>> &7Nie mozesz sprzedac wiecej przedmiotow!"));
                    return;
                }

                rpgcore.getHandlarzNPC().addItem(player.getUniqueId(), item.clone(), sellItem.getPrice(item.clone()));
                player.getInventory().removeItem(item.clone());
                player.getOpenInventory().getTopInventory().setItem(player.getOpenInventory().getTopInventory().firstEmpty(), item.clone());
                player.getOpenInventory().getTopInventory().setItem(49, rpgcore.getHandlarzNPC().getSprzedajItem(uuid));
                return;
            }

            if (slot == 49) {

                if (rpgcore.getHandlarzNPC().getUserItemMap(uuid).size() == 0) {
                    player.closeInventory();
                    player.sendMessage(Utils.format("&6&lHandlarz &8>> &7Nie posiadasz zadnych przedmiotow do sprzedazy!"));
                    return;
                }

                final double price = Utils.getTagDouble(item, "sellValue");

                player.sendMessage(Utils.format("&6&lHandlarz &8>> &aPomyslnie sprzedales/-as &6" + rpgcore.getHandlarzNPC().getUserItemMap(uuid).entries().stream().mapToInt(stream -> stream.getKey().getAmount()).sum() + " &aprzedmiotow za &6" + Utils.spaceNumber(price) + "&2$&a!"));

                final User user = rpgcore.getUserManager().find(uuid);

                final double kasa = user.getKasa();

                user.setKasa(user.getKasa() + price);
                final Multimap<ItemStack, Double> itemMap = rpgcore.getHandlarzNPC().getUserItemMap(uuid);
                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> RPGCORE.getDiscordBot().sendChannelMessage("handlarz-logs", EmbedUtil.createHandlarzcSellLog(player, itemMap, kasa, user.getKasa())));

                rpgcore.getHandlarzNPC().removeUserItemMap(uuid);
                player.closeInventory();



                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataUser(uuid, user));
                return;
            }

            final HandlarzSellItems sellItem = HandlarzSellItems.checkIfSellItem(item.clone());

            if (sellItem == null) return;

            rpgcore.getHandlarzNPC().removeItem(uuid, item.clone(), sellItem.getPrice(item.clone()));
            player.getInventory().addItem(item.clone());
            player.getOpenInventory().getTopInventory().removeItem(item.clone());
            player.getOpenInventory().getTopInventory().setItem(49, rpgcore.getHandlarzNPC().getSprzedajItem(uuid));
        }
    }
}
