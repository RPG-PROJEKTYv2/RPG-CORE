package rpg.rpgcore.newTarg;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.discord.EmbedUtil;
import rpg.rpgcore.utils.Utils;

import java.awt.*;
import java.util.List;
import java.util.UUID;

public class NewTargInventoryClick implements Listener {

    private final RPGCORE rpgcore;

    public NewTargInventoryClick(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void newTargInventoryClick(final InventoryClickEvent e) {
        final Inventory clickedInventory = e.getClickedInventory();
        final Player player = (Player) e.getWhoClicked();
        final UUID playerUUID = player.getUniqueId();

        if (e.getClickedInventory() == null || e.getInventory() == null) {
            return;
        }

        final String clickedInventoryTitle = clickedInventory.getTitle();
        final ItemStack clickedItem = e.getCurrentItem();
        final int clickedSlot = e.getSlot();

        if (Utils.removeColor(clickedInventoryTitle).contains("Targi #")) {
            e.setCancelled(true);

            if (clickedSlot == 50) {
                player.closeInventory();
                return;
            }

            // DEKLAROWANIE POTRZEBNYCH ZMIENNYCH

            int page = Integer.parseInt(Utils.removeColor(clickedInventoryTitle).replace("Targi #", ""));
            int category = this.getCategory(clickedInventory);
            int sort = this.getSortIndex(clickedInventory.getItem(49));

            // ZMIANA SORTOWANIA

            if (clickedSlot == 49) {
                sort += 1;
                if (sort == 5) {
                    sort = 1;
                }
                clickedInventory.setItem(49, rpgcore.getNewTargManager().getSortItem(sort));
                rpgcore.getNewTargManager().openNewTargInventory(player, category, sort, page);
                return;
            }


            // ZMIANA POMIEDZY KATEGORIAMI
            if (clickedSlot == 0) {
                rpgcore.getNewTargManager().openNewTargInventory(player, 1, sort,1);
                return;
            }
            if (clickedSlot == 9) {
                rpgcore.getNewTargManager().openNewTargInventory(player, 2, sort,1);
                return;
            }
            if (clickedSlot == 18) {
                rpgcore.getNewTargManager().openNewTargInventory(player, 3, sort,1);
                return;
            }
            if (clickedSlot == 27) {
                rpgcore.getNewTargManager().openNewTargInventory(player, 4, sort,1);
                return;
            }
            if (clickedSlot == 36) {
                rpgcore.getNewTargManager().openNewTargInventory(player, 5, sort,1);
                return;
            }
            if (clickedSlot == 45) {
                rpgcore.getNewTargManager().openNewTargInventory(player, 6, sort,1);
                return;
            }

            if (clickedItem.getType().equals(Material.ARROW) && clickedItem.getItemMeta().getDisplayName().contains("§a")) {
                if (clickedSlot == 47) {
                    rpgcore.getNewTargManager().openNewTargInventory(player, category, sort, page - 1);
                    return;
                }
                if (clickedSlot == 53) {
                    rpgcore.getNewTargManager().openNewTargInventory(player, category, sort, page + 1);
                    return;
                }
                return;
            }

            // KATEGORIA 1
            if (category == 1) {
                if (clickedItem.getType().equals(Material.CHEST)) {
                    rpgcore.getNewTargManager().openPlayerTarg(Utils.removeColor(clickedItem.getItemMeta().getDisplayName()), player);
                    return;
                }
            }

            // RESZTA KATEGORI
            if (clickedItem.getType().equals(Material.AIR) || clickedItem.getType().equals(Material.ARROW) || clickedItem.getType().equals(Material.STAINED_GLASS_PANE)) {
                return;
            }

            final String itemOwnwer = this.getItemOwner(clickedItem);
            final UUID itemOwnerUUID = rpgcore.getUserManager().find(itemOwnwer).getId();
            final double itemPrice = this.getItemPrice(clickedItem);
            final double playerMoney = rpgcore.getUserManager().find(playerUUID).getKasa();

            if (player.getName().equals(itemOwnwer)) {
                rpgcore.getNewTargManager().givePlayerBoughtItem(player, clickedItem.clone());
                rpgcore.getNewTargManager().removePlayerTargItem(itemOwnerUUID, clickedItem.clone());
                if (clickedItem.getItemMeta().getDisplayName() == null) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie zdjales ze swojego targu &6" + clickedItem.getAmount() + "x " + clickedItem.getType()));
                } else {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie zdjales ze swojego targu &6" + clickedItem.getAmount() + "x " + clickedItem.getItemMeta().getDisplayName()));
                }

                player.closeInventory();
                for (int i = 0; i < clickedInventory.getViewers().size(); i++) {
                    Player p = (Player) clickedInventory.getViewers().get(i);
                    sort = this.getSortIndex(p.getOpenInventory().getTopInventory().getItem(49));
                    rpgcore.getNewTargManager().openNewTargInventory(p, category, sort, 1);
                }
                return;
            }

            if (playerMoney < itemPrice) {
                player.closeInventory();
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&cBrakuje ci &6&o" + Utils.spaceNumber(Utils.kasaFormat.format(itemPrice - playerMoney)) + " &2$"));
                return;
            }
            this.finalizeTradeCategory(player, itemOwnerUUID, itemOwnwer, playerMoney, itemPrice, clickedItem);
            return;
        }


        // WYSTAWIANIE
        if (clickedInventoryTitle.equals(Utils.format("&7&lWystaw przedmiot"))) {
            e.setCancelled(true);
            if (clickedSlot == 4) {
                return;
            }

            if (clickedSlot < 4) {
                String cena = "";
                String podatek = "";
                final ItemStack item = clickedInventory.getItem(4);


                for (String s : item.getItemMeta().getLore()) {
                    if (s.contains("Cena: ")) {
                        cena = Utils.removeColor(s).replace("Cena: ", "").replace(" $", "").trim();
                    }
                    if (s.contains("1% ceny wystawienia")) {
                        podatek = Utils.removeColor(s).replace("W wysokosci 1% ceny wystawienia, czyli ", "").replace(" $", "");
                    }
                }

                final double podatekInDouble = Double.parseDouble(podatek.replaceAll(" ", "").trim());
                final double kasa = rpgcore.getUserManager().find(playerUUID).getKasa();

                if (kasa < podatekInDouble) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie masz pieniedzy, zeby wystawic ten przedmiot"));

                    rpgcore.getNewTargManager().returnPlayerItem(player, item);
                    rpgcore.getNewTargManager().removeFromWystawia(playerUUID);
                    clickedInventory.setItem(4, new ItemStack(Material.AIR));
                    rpgcore.getServer().getScheduler().runTaskLater(rpgcore, player::closeInventory, 1L);
                    return;
                }
                rpgcore.getUserManager().find(playerUUID).setKasa(kasa - podatekInDouble);
                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataUser(playerUUID, rpgcore.getUserManager().find(playerUUID)));

                final ItemMeta meta = item.getItemMeta();
                final List<String> lore = meta.getLore();
                final int loreSize = lore.size() -1;

                lore.remove(loreSize);
                lore.remove(loreSize-1);
                lore.remove(loreSize-2);
                lore.remove(loreSize-3);
                lore.remove(loreSize-4);
                meta.setLore(Utils.format(lore));
                item.setItemMeta(meta);

                rpgcore.getNewTargManager().addPlayerTargItems(playerUUID, item);
                for (int i = 0; i < rpgcore.getNewTargManager().getPlayerTarg(player.getName(), playerUUID).getViewers().size(); i++) {
                    Player p = (Player) rpgcore.getNewTargManager().getPlayerTarg(player.getName(), playerUUID).getViewers().get(i);
                    p.updateInventory();
                }
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie wystawiles przedmiot za &6&o" + cena + " &2$"));
                RPGCORE.getDiscordBot().sendChannelMessage("targ-log", EmbedUtil.create("**Targ - Wystaw**",
                        "**Gracz: **`" + player.getName() + "` **pomyslnie wystawil przedmiot na swoj targ.**\n" +
                                "**Nazwa Przedmiotu: **" + Utils.removeColor(item.getItemMeta().getDisplayName()) + "\n" +
                                "**Ilosc Przedmiotu: **" + item.getAmount() + "\n" +
                                "**Cena Przedmiotu: **" + cena + "\n" +
                                "**Zapłacony Podatek: **" + podatek + "$\n" +
                                "**Typ Przedmiotu: **`" + item.getType().toString() + "`\n" +
                                "**Lore Przedmiotu: **\n" + RPGCORE.getDiscordBot().buildStringFromLore(item.getItemMeta().getLore()) + "\n", Color.GREEN));
                rpgcore.getNewTargManager().removeFromWystawia(playerUUID);
                clickedInventory.setItem(4, new ItemStack(Material.AIR));
                player.closeInventory();
                return;
            }

            rpgcore.getNewTargManager().returnPlayerItem(player, clickedInventory.getItem(4));
            rpgcore.getNewTargManager().removeFromWystawia(playerUUID);
            clickedInventory.setItem(4, new ItemStack(Material.AIR));
            rpgcore.getServer().getScheduler().runTaskLater(rpgcore, player::closeInventory, 1L);
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&cAnulowales wystawianie przedmiotu"));
            RPGCORE.getDiscordBot().sendChannelMessage("targ-log", EmbedUtil.create("**Targ - Wystaw**",
                    "**Gracz: **`" + player.getName() + "` **anulowal wystawianie przedmiotu.**\n" +
                            "**Nazwa Przedmiotu: **" + Utils.removeColor(clickedInventory.getItem(4).getItemMeta().getDisplayName()) + "\n" +
                            "**Ilosc Przedmiotu: **" + clickedInventory.getItem(4).getAmount() + "\n" +
                            "**Typ Przedmiotu: **`" + clickedInventory.getItem(4).getType().toString() + "`\n" +
                            "**Lore Przedmiotu: **\n" + RPGCORE.getDiscordBot().buildStringFromLore(clickedInventory.getItem(4).getItemMeta().getLore()) + "\n", Color.RED));

        }


        // TARG GRACZA
        if (clickedInventoryTitle.contains("Targ gracza")) {
            e.setCancelled(true);

            if (clickedItem == null) {
                return;
            }

            if (clickedItem.getType().equals(Material.ARROW) && Utils.removeColor(clickedItem.getItemMeta().getDisplayName()).equals("Powrot")) {
                rpgcore.getNewTargManager().openNewTargInventory(player, 1, 1, 1);
                return;
            }

            if (clickedItem.getType().equals(Material.STAINED_GLASS_PANE)) {
                return;
            }

            final String targetName = Utils.removeColor(clickedInventoryTitle).replace("Targ gracza ", "").trim();
            final UUID targetUUID = rpgcore.getUserManager().find(targetName).getId();

            if (player.getName().equals(targetName)) {
                rpgcore.getNewTargManager().givePlayerBoughtItem(player, clickedItem.clone());
                rpgcore.getNewTargManager().removePlayerTargItem(targetUUID, clickedItem.clone());
                if (clickedItem.getItemMeta().getDisplayName() == null) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie zdjales ze swojego targu &6" + clickedItem.getAmount() + "x " + clickedItem.getType()));
                } else {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie zdjales ze swojego targu &6" + clickedItem.getAmount() + "x " + clickedItem.getItemMeta().getDisplayName()));
                }

                final int itemCategory = this.getItemCategory(clickedItem);

                this.updateInventory(itemCategory);

                player.closeInventory();
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&cTen gracz nie ma wystawionych zadnych przedmiotow"));
                return;
            }



            final double kasaGracza = rpgcore.getUserManager().find(playerUUID).getKasa();
            final double itemCena = this.getItemPrice(clickedItem);

            if (kasaGracza < itemCena) {
                player.closeInventory();

                player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie masz wystarczajacych srodkow, zeby kupic ten przedmiot."));
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&cBrakuje ci &6&o" + Utils.spaceNumber(Utils.kasaFormat.format(itemCena - kasaGracza)) + " &2$"));

                return;
            }

            this.finalizeTradeTarg(player, targetUUID, targetName, kasaGracza, itemCena, clickedItem);
        }

    }


    private int getSortIndex(final ItemStack is) {
        for (String s : is.getItemMeta().getLore()) {
            if (s.contains("§3")) {
                return is.getItemMeta().getLore().indexOf(s) + 1;
            }
        }
        return 0;
    }

    private int getCategory(final Inventory inv) {
        int category = 0;
        if (inv.getItem(0).getItemMeta().getItemFlags().contains(ItemFlag.HIDE_ATTRIBUTES)) {
            category = 1;
        } else if (inv.getItem(9).getItemMeta().getItemFlags().contains(ItemFlag.HIDE_ATTRIBUTES)) {
            category = 2;
        } else if (inv.getItem(18).getItemMeta().getItemFlags().contains(ItemFlag.HIDE_ATTRIBUTES)) {
            category = 3;
        } else if (inv.getItem(27).getItemMeta().getItemFlags().contains(ItemFlag.HIDE_ATTRIBUTES)) {
            category = 4;
        } else if (inv.getItem(36).getItemMeta().getItemFlags().contains(ItemFlag.HIDE_ATTRIBUTES)) {
            category = 5;
        } else if (inv.getItem(45).getItemMeta().getItemFlags().contains(ItemFlag.HIDE_ATTRIBUTES)) {
            category = 6;
        }
        return category;
    }

    private String getItemOwner(final ItemStack is) {
        for (String s : is.getItemMeta().getLore()) {
            if (s.contains("Wlasciciel: ")) {
                return Utils.removeColor(s).replace("Wlasciciel:", "").replaceAll(" ", "").trim();
            }
        }
        return "null";
    }

    private double getItemPrice(final ItemStack is) {
        for (String s : is.getItemMeta().getLore()) {
            if (s.contains("Cena: ")) {
                return Double.parseDouble(Utils.removeColor(s).replace("Cena: ", "").replace(" ", "").replace("$", "").trim());
            }
        }
        return 0.0;
    }

    private void finalizeTradeTarg(final Player player, final UUID targetUUID, final String targetName, final double kasaGracza, final double itemCena, final ItemStack clickedItem) {
        final UUID playerUUID = player.getUniqueId();
        rpgcore.getUserManager().find(playerUUID).setKasa(kasaGracza - itemCena);
        rpgcore.getUserManager().find(targetUUID).setKasa(rpgcore.getUserManager().find(targetUUID).getKasa() + itemCena);
        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
           rpgcore.getMongoManager().saveDataUser(playerUUID, rpgcore.getUserManager().find(playerUUID));
           rpgcore.getMongoManager().saveDataUser(targetUUID, rpgcore.getUserManager().find(targetUUID));
        });
        rpgcore.getNewTargManager().givePlayerBoughtItem(player, clickedItem.clone());

        rpgcore.getNewTargManager().removePlayerTargItem(targetUUID, clickedItem.clone());

        player.closeInventory();

        final int itemCategory = this.getItemCategory(clickedItem);

        this.updateInventory(itemCategory);

        if (clickedItem.getItemMeta().getDisplayName() == null) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie kupiles przedmiot &6" + clickedItem.getAmount() + "x " + clickedItem.getType() + " &aod gracza &6" + targetName + " &aza kwote &6&o" + Utils.spaceNumber(Utils.kasaFormat.format(itemCena)) + " &2$"));
            Bukkit.getPlayer(targetUUID).sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie sprzedales przedmiot &6" + clickedItem.getAmount() + "x " + clickedItem.getType() + " &adla gracza &6" + player.getName() + " &aza kwote &6&o" + Utils.spaceNumber(Utils.kasaFormat.format(itemCena)) + " &2$"));
            return;
        }

        player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie kupiles przedmiot &6" + clickedItem.getAmount() + "x " + clickedItem.getItemMeta().getDisplayName() + " &aod gracza &6" + targetName + " &aza kwote &6&o" + Utils.spaceNumber(Utils.kasaFormat.format(itemCena)) + " &2$"));
        Bukkit.getPlayer(targetUUID).sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie sprzedales przedmiot &6" + clickedItem.getAmount() + "x " + clickedItem.getItemMeta().getDisplayName() + " &adla gracza &6" + player.getName() + " &aza kwote &6&o" + Utils.spaceNumber(Utils.kasaFormat.format(itemCena)) + " &2$"));
    }

    private void finalizeTradeCategory(final Player player, final UUID targetUUID, final String targetName, final double kasaGracza, final double itemCena, final ItemStack clickedItem) {
        final UUID playerUUID = player.getUniqueId();
        rpgcore.getUserManager().find(playerUUID).setKasa(kasaGracza - itemCena);
        rpgcore.getUserManager().find(targetUUID).setKasa(rpgcore.getUserManager().find(targetUUID).getKasa() + itemCena);
        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
            rpgcore.getMongoManager().saveDataUser(playerUUID, rpgcore.getUserManager().find(playerUUID));
            rpgcore.getMongoManager().saveDataUser(targetUUID, rpgcore.getUserManager().find(targetUUID));
        });
        rpgcore.getNewTargManager().givePlayerBoughtItem(player, clickedItem.clone());

        rpgcore.getNewTargManager().removePlayerTargItem(targetUUID, clickedItem.clone());

        player.closeInventory();
        final int itemCategory = this.getItemCategory(clickedItem);

        this.updateInventory(itemCategory);

        if (clickedItem.getItemMeta().getDisplayName() == null) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie kupiles przedmiot &6" + clickedItem.getAmount() + "x " + clickedItem.getType() + " &aod gracza &6" + targetName + " &aza kwote &6&o" + Utils.spaceNumber(Utils.kasaFormat.format(itemCena)) + " &2$"));
            Bukkit.getPlayer(targetUUID).sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie sprzedales przedmiot &6" + clickedItem.getAmount() + "x " + clickedItem.getType() + " &adla gracza &6" + player.getName() + " &aza kwote &6&o" + Utils.spaceNumber(Utils.kasaFormat.format(itemCena)) + " &2$"));
            return;
        }

        player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie kupiles przedmiot &6" + clickedItem.getAmount() + "x " + clickedItem.getItemMeta().getDisplayName() + " &aod gracza &6" + targetName + " &aza kwote &6&o" + Utils.spaceNumber(Utils.kasaFormat.format(itemCena)) + " &2$"));
        Bukkit.getPlayer(targetUUID).sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie sprzedales przedmiot &6" + clickedItem.getAmount() + "x " + clickedItem.getItemMeta().getDisplayName() + " &adla gracza &6" + player.getName() + " &aza kwote &6&o" + Utils.spaceNumber(Utils.kasaFormat.format(itemCena)) + " &2$"));
    }
    
    private int getItemCategory(final ItemStack is) {
        
        if (String.valueOf(is.getType()).contains("HELMET") || String.valueOf(is.getType()).contains("CHESTPLATE")
                || String.valueOf(is.getType()).contains("LEGGINGS") || String.valueOf(is.getType()).contains("BOOTS")) {
            return 2;
        } else if (String.valueOf(is.getType()).contains("SWORD") || String.valueOf(is.getType()).contains("AXE")
                || String.valueOf(is.getType()).contains("HOE") || String.valueOf(is.getType()).contains("PICKAXE")) {
            return 3;
        } else if (String.valueOf(is.getType()).contains("MINECART") || is.getType().equals(Material.WATCH) || is.getType().equals(Material.ITEM_FRAME)) {
            return 4;
        } else if (is.getItemMeta().getLore().contains("&8&oMaterial")) {
            return 5;
        } else if (!(String.valueOf(is.getType()).contains("HELMET") || String.valueOf(is.getType()).contains("CHESTPLATE")
                || String.valueOf(is.getType()).contains("LEGGINGS") || String.valueOf(is.getType()).contains("BOOTS")
                || String.valueOf(is.getType()).contains("SWORD") || String.valueOf(is.getType()).contains("AXE")
                || String.valueOf(is.getType()).contains("HOE") || String.valueOf(is.getType()).contains("PICKAXE")
                || String.valueOf(is.getType()).contains("MINECART") || is.getType().equals(Material.WATCH)
                || is.getType().equals(Material.ITEM_FRAME) || is.getItemMeta().getLore().contains("&8&oMaterial"))) {
            return 6;
        }
        return 1;
    }

    private void updateInventory(final int itemCategory) {
        for (final Player p : Bukkit.getOnlinePlayers()) {
            if (p.getOpenInventory() != null && p.getOpenInventory().getTopInventory() != null && Utils.removeColor(p.getOpenInventory().getTopInventory().getTitle()).contains("Targi #")
                    || Utils.removeColor(p.getOpenInventory().getTopInventory().getTitle()).contains("Targ gracza")) {
                if (Utils.removeColor(p.getOpenInventory().getTopInventory().getTitle()).contains("Targi #")) {
                    int playerCategory = this.getCategory(p.getOpenInventory().getTopInventory());
                    if (playerCategory == itemCategory || playerCategory == 1) {
                        int playerSort = this.getSortIndex(p.getOpenInventory().getTopInventory().getItem(49));
                        rpgcore.getNewTargManager().openNewTargInventory(p, playerCategory, playerSort, 1);
                        return;
                    }
                    return;
                }
                if (Utils.removeColor(p.getOpenInventory().getTopInventory().getTitle()).contains("Targ gracza")) {
                    final String targetName = Utils.removeColor(p.getOpenInventory().getTopInventory().getTitle()).replace("Targ gracza", "").replaceAll(" ", "").trim();
                    if (rpgcore.getNewTargManager().getPlayerTargItems(rpgcore.getUserManager().find(targetName).getId()).isEmpty()) {
                        p.closeInventory();
                        p.sendMessage(Utils.format(Utils.SERVERNAME + "&cTen gracz nie ma wystawionych zadnych przedmiotow"));
                        return;
                    }
                    rpgcore.getNewTargManager().openPlayerTarg(targetName, p);
                    return;
                }
            }
        }
    }

}
