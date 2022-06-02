package rpg.rpgcore.listeners;

import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.*;

public class PlayerInventoryClickListener implements Listener {

    private final RPGCORE rpgcore;

    public PlayerInventoryClickListener(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClick(final InventoryClickEvent e) {
        final Inventory clickedInventory = e.getClickedInventory();
        final Player player = (Player) e.getWhoClicked();
        final UUID playerUUID = player.getUniqueId();
        final HashMap<Integer, ItemStack> itemMapToRemove = new HashMap<>();

        if (e.getClickedInventory() == null) {
            return;
        }

        final String clickedInventoryTitle = clickedInventory.getTitle();
        final ItemStack clickedItem = e.getCurrentItem();
        final int clickedSlot = e.getSlot();

        //                      GUI OD BAO                  \\
        if (clickedInventoryTitle.equals(Utils.format("&6&lSTOL MAGII"))) {

            if (clickedSlot == 16) {
                if (!(player.getInventory().containsAtLeast(rpgcore.getBaoManager().getItemDoLosowania(), 1))) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie posiadasz &3&lKamien Bao&c!"));
                    player.closeInventory();
                    player.getInventory().addItem(rpgcore.getBaoManager().getItemDoLosowania());
                    player.getInventory().addItem(rpgcore.getBaoManager().getItemDoZmianki());
                    return;
                }
                rpgcore.getBaoManager().losujNoweBonusy(playerUUID);
                itemMapToRemove.put(0, rpgcore.getBaoManager().getItemDoLosowania());
                player.getInventory().removeItem(itemMapToRemove.get(0));
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie zmieniles swoje bonusy w &6Stole Magi"));
                player.closeInventory();


                e.setCancelled(true);
            }
            e.setCancelled(true);
            player.closeInventory();
        }

        if (clickedInventoryTitle.equalsIgnoreCase(rpgcore.getBaoManager().ksiegaMagiiGUI(playerUUID).getName())) {
            e.setCancelled(true);
            if (clickedSlot == 11) {
                rpgcore.getBaoManager().losujNowyBonus1(playerUUID);
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&6Twoj nowy bonus to: &c" + rpgcore.getBaoManager().getBaoBonusy(playerUUID).split(",")[clickedSlot - 11] + " " + rpgcore.getBaoManager().getBaoBonusyWartosci(playerUUID).split(",")[clickedSlot - 11]) + " %");
            }
            if (clickedSlot == 12) {
                rpgcore.getBaoManager().losujNowyBonus2(playerUUID);
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&6Twoj nowy bonus to: &c" + rpgcore.getBaoManager().getBaoBonusy(playerUUID).split(",")[clickedSlot - 11] + " " + rpgcore.getBaoManager().getBaoBonusyWartosci(playerUUID).split(",")[clickedSlot - 11]) + " %");
            }
            if (clickedSlot == 13) {
                rpgcore.getBaoManager().losujNowyBonus3(playerUUID);
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&6Twoj nowy bonus to: &c" + rpgcore.getBaoManager().getBaoBonusy(playerUUID).split(",")[clickedSlot - 11] + " " + rpgcore.getBaoManager().getBaoBonusyWartosci(playerUUID).split(",")[clickedSlot - 11]) + " %");
            }
            if (clickedSlot == 14) {
                rpgcore.getBaoManager().losujNowyBonus4(playerUUID);
                if (rpgcore.getBaoManager().getBaoBonusy(playerUUID).split(",")[3].equalsIgnoreCase("dodatkowe obrazenia")) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&6Twoj nowy bonus to: &c" + rpgcore.getBaoManager().getBaoBonusy(playerUUID).split(",")[clickedSlot - 11] + " " + rpgcore.getBaoManager().getBaoBonusyWartosci(playerUUID).split(",")[clickedSlot - 11]) + " DMG");
                } else {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&6Twoj nowy bonus to: &c" + rpgcore.getBaoManager().getBaoBonusy(playerUUID).split(",")[clickedSlot - 11] + " " + rpgcore.getBaoManager().getBaoBonusyWartosci(playerUUID).split(",")[clickedSlot - 11]) + " %");
                }
            }
            if (clickedSlot == 15) {
                rpgcore.getBaoManager().losujNowyBonus5(playerUUID);
                if (rpgcore.getBaoManager().getBaoBonusy(playerUUID).split(",")[4].equalsIgnoreCase("dodatkowe hp")) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&6Twoj nowy bonus to: &c" + rpgcore.getBaoManager().getBaoBonusy(playerUUID).split(",")[clickedSlot - 11] + " " + rpgcore.getBaoManager().getBaoBonusyWartosci(playerUUID).split(",")[clickedSlot - 11]) + " HP");
                } else {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&6Twoj nowy bonus to: &c" + rpgcore.getBaoManager().getBaoBonusy(playerUUID).split(",")[clickedSlot - 11] + " " + rpgcore.getBaoManager().getBaoBonusyWartosci(playerUUID).split(",")[clickedSlot - 11]) + " %");
                }
            }
            player.closeInventory();
            return;
        }

        if (clickedInventoryTitle.equals(rpgcore.getOsManager().osGuiMain().getName())) {
            e.setCancelled(true);
            if (clickedItem.getType() == Material.GOLD_SWORD){
                player.openInventory(rpgcore.getOsManager().osMobyGui(playerUUID));
                return;
            }
            if (clickedItem.getType() == Material.DIAMOND_SWORD) {
                player.openInventory(rpgcore.getOsManager().osGraczeGUI(playerUUID));
                return;
            }
            if (clickedItem.getType() == Material.EXP_BOTTLE) {
                player.openInventory(rpgcore.getOsManager().osSakwyGUI(playerUUID));
                return;
            }
            if (clickedItem.getType() == Material.DIAMOND_BLOCK) {
                player.openInventory(rpgcore.getOsManager().osNiesyGUI(playerUUID));
                return;
            }
            if (clickedItem.getType() == Material.FISHING_ROD) {
                player.openInventory(rpgcore.getOsManager().osRybakGUI(playerUUID));
                return;
            }
            if (clickedItem.getType() == Material.DIAMOND_AXE) {
                player.openInventory(rpgcore.getOsManager().osDrwalGUI(playerUUID));
                return;
            }
            if (clickedItem.getType() == Material.GOLD_PICKAXE) {
                player.openInventory(rpgcore.getOsManager().osGornikGUI(playerUUID));
                return;
            }
        }

        if (clickedInventoryTitle.equals(rpgcore.getOsManager().osMobyGui(playerUUID).getName())) {
            e.setCancelled(true);
            if (clickedItem.getType() == Material.AIR) {
                return;
            }

            String[] accepted = rpgcore.getPlayerManager().getOsMobyAccept(playerUUID).split(",");

            if (accepted[clickedSlot].equalsIgnoreCase("true")) {
                return;
            }

            if (clickedSlot != 0 && accepted[clickedSlot-1].equalsIgnoreCase("false")) {
                return;
            }

            final int killedMobs = rpgcore.getPlayerManager().getPlayerOsMoby(playerUUID);

            if (killedMobs < rpgcore.getOsManager().getRequiredForOsMoby().get(clickedSlot+1)) {
                return;
            }

            accepted[clickedSlot] = "true";
            StringBuilder noweOsMobyAccepted = new StringBuilder();
            for (int i = 0; i < accepted.length; i++) {
                noweOsMobyAccepted.append(accepted[i]);
                if (!(i+1 >= accepted.length)) {
                    noweOsMobyAccepted.append(",");
                }
            }
            player.sendMessage(String.valueOf(noweOsMobyAccepted));
            rpgcore.getPlayerManager().updatePlayerOsMobyAccepted(playerUUID, String.valueOf(noweOsMobyAccepted));

            player.closeInventory();
            Bukkit.getServer().broadcastMessage(Utils.format(Utils.SERVERNAME + "&3Gracz &f" + player.getName() + " &3odebral osiagniecie &f" + Utils.removeColor(clickedItem.getItemMeta().getDisplayName())));
            noweOsMobyAccepted.setLength(0);
            return;
        }

        if (clickedInventoryTitle.equals(rpgcore.getOsManager().osGraczeGUI(playerUUID).getName())) {

            e.setCancelled(true);
            if (clickedItem.getType() == Material.AIR) {
                return;
            }

            String[] accepted = rpgcore.getPlayerManager().getOsLudzieAccept(playerUUID).split(",");

            if (accepted[clickedSlot].equalsIgnoreCase("true")) {
                return;
            }

            if (clickedSlot != 0 && accepted[clickedSlot-1].equalsIgnoreCase("false")) {
                return;
            }

            final int killedPlayers = rpgcore.getPlayerManager().getPlayerOsLudzie(playerUUID);

            if (killedPlayers < rpgcore.getOsManager().getRequiredForOsLudzie().get(clickedSlot+1)) {
                return;
            }


            accepted[clickedSlot] = "true";
            StringBuilder noweOsLudzieAccepted = new StringBuilder();
            for (int i = 0; i < accepted.length; i++) {
                noweOsLudzieAccepted.append(accepted[i]);
                if (!(i+1 >= accepted.length)) {
                    noweOsLudzieAccepted.append(",");
                }
            }
            player.sendMessage(String.valueOf(noweOsLudzieAccepted));
            rpgcore.getPlayerManager().updatePlayerOsLudzieAccepted(playerUUID, String.valueOf(noweOsLudzieAccepted));

            player.closeInventory();
            Bukkit.getServer().broadcastMessage(Utils.format(Utils.SERVERNAME + "&3Gracz &f" + player.getName() + " &3odebral osiagniecie &f" + Utils.removeColor(clickedItem.getItemMeta().getDisplayName())));
            noweOsLudzieAccepted.setLength(0);
            return;
        }

        if (clickedInventoryTitle.equals(rpgcore.getOsManager().osSakwyGUI(playerUUID).getName())) {

            e.setCancelled(true);
            if (clickedItem.getType() == Material.AIR) {
                return;
            }

            String[] accepted = rpgcore.getPlayerManager().getOsSakwyAccept(playerUUID).split(",");

            if (accepted[clickedSlot].equalsIgnoreCase("true")) {
                return;
            }

            if (clickedSlot != 0 && accepted[clickedSlot-1].equalsIgnoreCase("false")) {
                return;
            }

            final int dropped = rpgcore.getPlayerManager().getPlayerOsSakwy(playerUUID);

            if (dropped < rpgcore.getOsManager().getRequiredForOsSakwy().get(clickedSlot+1)) {
                return;
            }

            accepted[clickedSlot] = "true";
            StringBuilder noweOsSakwyAccepted = new StringBuilder();
            for (int i = 0; i < accepted.length; i++) {
                noweOsSakwyAccepted.append(accepted[i]);
                if (!(i+1 >= accepted.length)) {
                    noweOsSakwyAccepted.append(",");
                }
            }
            player.sendMessage(String.valueOf(noweOsSakwyAccepted));
            rpgcore.getPlayerManager().updatePlayerOsSakwyAccepted(playerUUID, String.valueOf(noweOsSakwyAccepted));

            player.closeInventory();
            Bukkit.getServer().broadcastMessage(Utils.format(Utils.SERVERNAME + "&3Gracz &f" + player.getName() + " &3odebral osiagniecie &f" + Utils.removeColor(clickedItem.getItemMeta().getDisplayName())));
            noweOsSakwyAccepted.setLength(0);
            return;
        }

        if (clickedInventoryTitle.equals(rpgcore.getOsManager().osNiesyGUI(playerUUID).getName())) {

            e.setCancelled(true);
            if (clickedItem.getType() == Material.AIR) {
                return;
            }

            String[] accepted = rpgcore.getPlayerManager().getOsNiesyAccept(playerUUID).split(",");

            if (accepted[clickedSlot].equalsIgnoreCase("true")) {
                return;
            }

            if (clickedSlot != 0 && accepted[clickedSlot-1].equalsIgnoreCase("false")) {
                return;
            }

            final int dropped = rpgcore.getPlayerManager().getPlayerOsNiesy(playerUUID);

            if (dropped < rpgcore.getOsManager().getRequiredForOsNiesy().get(clickedSlot+1)) {
                return;
            }

            accepted[clickedSlot] = "true";
            StringBuilder noweOsNiesyAccepted = new StringBuilder();
            for (int i = 0; i < accepted.length; i++) {
                noweOsNiesyAccepted.append(accepted[i]);
                if (!(i+1 >= accepted.length)) {
                    noweOsNiesyAccepted.append(",");
                }
            }
            player.sendMessage(String.valueOf(noweOsNiesyAccepted));
            rpgcore.getPlayerManager().updatePlayerOsNiesyAccepted(playerUUID, String.valueOf(noweOsNiesyAccepted));

            player.closeInventory();
            Bukkit.getServer().broadcastMessage(Utils.format(Utils.SERVERNAME + "&3Gracz &f" + player.getName() + " &3odebral osiagniecie &f" + Utils.removeColor(clickedItem.getItemMeta().getDisplayName())));
            noweOsNiesyAccepted.setLength(0);
            return;
        }

        if (clickedInventoryTitle.equals(rpgcore.getOsManager().osRybakGUI(playerUUID).getName())) {

            e.setCancelled(true);
            if (clickedItem.getType() == Material.AIR) {
                return;
            }

            String[] accepted = rpgcore.getPlayerManager().getOsRybakAccept(playerUUID).split(",");

            if (accepted[clickedSlot].equalsIgnoreCase("true")) {
                return;
            }

            if (clickedSlot != 0 && accepted[clickedSlot-1].equalsIgnoreCase("false")) {
                return;
            }

            final int dropped = rpgcore.getPlayerManager().getPlayerOsRybak(playerUUID);

            if (dropped < rpgcore.getOsManager().getRequiredForOsRybak().get(clickedSlot+1)) {
                return;
            }

            accepted[clickedSlot] = "true";
            StringBuilder noweOsRybakAccepted = new StringBuilder();
            for (int i = 0; i < accepted.length; i++) {
                noweOsRybakAccepted.append(accepted[i]);
                if (!(i+1 >= accepted.length)) {
                    noweOsRybakAccepted.append(",");
                }
            }
            player.sendMessage(String.valueOf(noweOsRybakAccepted));
            rpgcore.getPlayerManager().updatePlayerOsRybakAccepted(playerUUID, String.valueOf(noweOsRybakAccepted));

            player.closeInventory();
            Bukkit.getServer().broadcastMessage(Utils.format(Utils.SERVERNAME + "&3Gracz &f" + player.getName() + " &3odebral osiagniecie &f" + Utils.removeColor(clickedItem.getItemMeta().getDisplayName())));
            noweOsRybakAccepted.setLength(0);
            return;
        }

        if (clickedInventoryTitle.equals(rpgcore.getOsManager().osDrwalGUI(playerUUID).getName())) {

            e.setCancelled(true);
            if (clickedItem.getType() == Material.AIR) {
                return;
            }

            String[] accepted = rpgcore.getPlayerManager().getOsDrwalAccept(playerUUID).split(",");

            if (accepted[clickedSlot].equalsIgnoreCase("true")) {
                return;
            }

            if (clickedSlot != 0 && accepted[clickedSlot-1].equalsIgnoreCase("false")) {
                return;
            }

            final int dropped = rpgcore.getPlayerManager().getPlayerOsDrwal(playerUUID);

            if (dropped < rpgcore.getOsManager().getRequiredForOsDrwal().get(clickedSlot+1)) {
                return;
            }

            accepted[clickedSlot] = "true";
            StringBuilder noweOsDrwalAccepted = new StringBuilder();
            for (int i = 0; i < accepted.length; i++) {
                noweOsDrwalAccepted.append(accepted[i]);
                if (!(i+1 >= accepted.length)) {
                    noweOsDrwalAccepted.append(",");
                }
            }
            player.sendMessage(String.valueOf(noweOsDrwalAccepted));
            rpgcore.getPlayerManager().updatePlayerOsDrwalAccepted(playerUUID, String.valueOf(noweOsDrwalAccepted));

            player.closeInventory();
            Bukkit.getServer().broadcastMessage(Utils.format(Utils.SERVERNAME + "&3Gracz &f" + player.getName() + " &3odebral osiagniecie &f" + Utils.removeColor(clickedItem.getItemMeta().getDisplayName())));
            noweOsDrwalAccepted.setLength(0);
            return;
        }

        if (clickedInventoryTitle.equals(rpgcore.getOsManager().osGornikGUI(playerUUID).getName())) {

            e.setCancelled(true);
            if (clickedItem.getType() == Material.AIR) {
                return;
            }

            String[] accepted = rpgcore.getPlayerManager().getOsGornikAccept(playerUUID).split(",");

            if (accepted[clickedSlot].equalsIgnoreCase("true")) {
                return;
            }

            if (clickedSlot != 0 && accepted[clickedSlot-1].equalsIgnoreCase("false")) {
                return;
            }

            final int dropped = rpgcore.getPlayerManager().getPlayerOsGornik(playerUUID);

            if (dropped < rpgcore.getOsManager().getRequiredForOsGornik().get(clickedSlot+1)) {
                return;
            }

            accepted[clickedSlot] = "true";
            StringBuilder noweOsGornikAccepted = new StringBuilder();
            for (int i = 0; i < accepted.length; i++) {
                noweOsGornikAccepted.append(accepted[i]);
                if (!(i+1 >= accepted.length)) {
                    noweOsGornikAccepted.append(",");
                }
            }
            player.sendMessage(String.valueOf(noweOsGornikAccepted));
            rpgcore.getPlayerManager().updatePlayerOsGornikAccepted(playerUUID, String.valueOf(noweOsGornikAccepted));

            player.closeInventory();
            Bukkit.getServer().broadcastMessage(Utils.format(Utils.SERVERNAME + "&3Gracz &f" + player.getName() + " &3odebral osiagniecie &f" + Utils.removeColor(clickedItem.getItemMeta().getDisplayName())));
            noweOsGornikAccepted.setLength(0);
        }

        if (clickedInventoryTitle.equals(Utils.format("&4&lEQ GUI"))) {
            e.setCancelled(true);
            if (clickedItem.getType() == Material.AIR) {
                e.getWhoClicked().closeInventory();
                return;
            }
            final String przedFormatem = Utils.format("&8[&bLvl. &f<player-lvl>&8]<player-group> &7<player-name>&7: <message>");
            final String message = rpgcore.getChatManager().getMessageWithEQ(e.getWhoClicked().getUniqueId());
            final ArrayList<String> msg = new ArrayList<>(Arrays.asList(message.split("\\[eq]")));
            final String formatPrzedWiadomoscia = rpgcore.getChatManager().formatujChat(player, przedFormatem, "");
            final String playerGroup = PlaceholderAPI.setPlaceholders(player, "%uperms_rank%");
            StringBuilder finalMessage;
            String color = "&7";
            if (message.contains("&")) {
                int znak = message.lastIndexOf("&");
                color = message.substring(znak, znak + 2);
            }
            final boolean playerGroupIsValid = playerGroup.equals("Gracz") || playerGroup.equals("Vip") || playerGroup.equals("Svip") || playerGroup.equals("ELITA") || playerGroup.equals("Budowniczy");
            switch (clickedSlot) {
                case 0:
                    if (e.getWhoClicked().getItemInHand().getType().equals(Material.AIR)) {
                        e.getWhoClicked().sendMessage(Utils.format(Utils.SERVERNAME + "&cMusisz miec cos w rece zeby tego uzyc"));
                        e.getWhoClicked().closeInventory();
                        return;
                    }
                    TextComponent beforeMessage = new TextComponent(formatPrzedWiadomoscia);

                    TextComponent item;
                    if (e.getWhoClicked().getItemInHand().getItemMeta().getDisplayName() != null) {
                        item = new TextComponent(" §8[§6x" + e.getWhoClicked().getItemInHand().getAmount() + " " + e.getWhoClicked().getItemInHand().getItemMeta().getDisplayName() + "§8]");
                    } else {
                        item = new TextComponent(" §8[§6x" + e.getWhoClicked().getItemInHand().getAmount() + " "  + e.getWhoClicked().getItemInHand().getType().toString() + "§8]");
                    }
                    item.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new ComponentBuilder(CraftItemStack.asNMSCopy(e.getWhoClicked().getItemInHand()).save(new NBTTagCompound()).toString()).create()));
                    if (!(msg.isEmpty())) {
                        TextComponent firstPartOfMessage;
                        if (playerGroupIsValid) {
                            firstPartOfMessage = new TextComponent(Utils.format("&7" + Utils.removeColor(msg.get(0))));
                        } else {
                            firstPartOfMessage = new TextComponent(Utils.format("&7" + msg.get(0)));
                        }
                        beforeMessage.addExtra(firstPartOfMessage);
                        beforeMessage.addExtra(item);
                        if (playerGroupIsValid) {
                            for (int i = 1; i < msg.size(); i++) {
                                beforeMessage.addExtra(Utils.format(" " + "&7" + msg.get(i)));
                            }
                        } else {
                            for (int i = 1; i < msg.size(); i++) {
                                beforeMessage.addExtra(Utils.format(" " + color + msg.get(i)));
                            }
                        }
                    } else {
                        beforeMessage.addExtra(item);
                    }


                    Bukkit.getServer().spigot().broadcast(beforeMessage);
                    break;
                case 1:
                    if (player.getInventory().getHelmet() == null || player.getInventory().getChestplate() == null || player.getInventory().getLeggings() == null || player.getInventory().getBoots() == null) {
                        player.sendMessage(Utils.format(Utils.SERVERNAME + "&cMusisz miec zalozone wszystkie czesci zbroji zeby tego uzyc!"));
                        player.closeInventory();
                        return;
                    }
                    if (!(msg.isEmpty())) {
                        if (playerGroupIsValid) {
                            finalMessage = new StringBuilder("&7" + Utils.removeColor(msg.get(0)) + Utils.format(rpgcore.getChatManager().getEnchantemntLvlForEQ(player)));
                            for (int i = 1; i < msg.size(); i++) {
                                finalMessage.append(" ").append(msg.get(i));
                            }
                        } else {
                            finalMessage = new StringBuilder(msg.get(0) + Utils.format(rpgcore.getChatManager().getEnchantemntLvlForEQ(player)));
                            for (int i = 1; i < msg.size(); i++) {
                                finalMessage = new StringBuilder(Utils.format(finalMessage + " " + color + msg.get(i)));
                            }
                        }
                    } else {
                        finalMessage = new StringBuilder(Utils.format(rpgcore.getChatManager().getEnchantemntLvlForEQ(player)));
                    }
                    Bukkit.broadcastMessage(formatPrzedWiadomoscia + finalMessage);
                    break;
                case 2:
                    if (rpgcore.getPlayerManager().getPlayerLvl(playerUUID) < 74) {
                        player.sendMessage(Utils.format(Utils.SERVERNAME + "&cMusisz posiadac minimum &c75 &7poziom, zeby pokazac bao na chacie"));
                        player.closeInventory();
                        break;
                    }
                    final String[] baoBonusy = rpgcore.getBaoManager().getBaoBonusy(playerUUID).split(",");
                    final String[] baoWartosci = rpgcore.getBaoManager().getBaoBonusyWartosci(playerUUID).split(",");
                    if (baoBonusy[0].equalsIgnoreCase("brak bonusu")) {
                        player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie masz jeszcze zrobionego bao!"));
                        player.closeInventory();
                        break;
                    }
                    TextComponent beforeMessageBao = new TextComponent(formatPrzedWiadomoscia);
                    TextComponent stolMagii = new TextComponent("§8[§bStol Magii§8]");
                    TextComponent text = new TextComponent("§7Stol Magii gracza §c" + player.getName() + "§7:\n§6" + baoBonusy[0] + ": §c" + baoWartosci[0] + "% §8\n§6" + baoBonusy[1] + ": §c" + baoWartosci[1] + "% §8\n§6" + baoBonusy[2] + ": §c" + baoWartosci[2] + "% §8\n");
                    TextComponent text2;
                    TextComponent text3;


                    if (baoBonusy[3].equalsIgnoreCase("dodatkowe obrazenia")) {
                        text2 = new TextComponent("§6" + baoBonusy[3] + ": §c" + baoWartosci[3] + " DMG §8\n");
                    } else {
                        text2 = new TextComponent("§6" + baoBonusy[3] + ": §c" + baoWartosci[3] + "% §8\n");
                    }
                    if (baoBonusy[4].equalsIgnoreCase("dodatkowe hp")) {
                        text3 = new TextComponent("§6" + baoBonusy[4] + ": §c" + baoWartosci[4] + " HP");
                    } else {
                        text3 = new TextComponent("§6" + baoBonusy[4] + ": §c" + baoWartosci[4] + "%");
                    }
                    text.addExtra(text2);
                    text.addExtra(text3);
                    stolMagii.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[]{text}));
                    beforeMessageBao.addExtra(stolMagii);
                    Bukkit.getServer().spigot().broadcast(beforeMessageBao);
                    break;
                case 3:
                    final int lvlGracza = rpgcore.getPlayerManager().getPlayerLvl(playerUUID);
                    final double expGracza = rpgcore.getPlayerManager().getPlayerExp(playerUUID);
                    final double expNaNextLvlGracza = rpgcore.getLvlManager().getExpForLvl(lvlGracza + 1);

                    if (!(msg.isEmpty())) {
                        if (playerGroupIsValid) {
                            finalMessage = new StringBuilder("&7" + Utils.removeColor(msg.get(0)) + Utils.format(" &8[&f" + Utils.df.format(expGracza) + " &bexp &7/&f " + Utils.df.format(expNaNextLvlGracza) + " &bexp" + "&7(&b" + Utils.procentFormat.format((expGracza / expNaNextLvlGracza) * 100) + "%&7)&8]"));
                            for (int i = 1; i < msg.size(); i++) {
                                finalMessage.append(" ").append(msg.get(i));
                            }
                        } else {
                            finalMessage = new StringBuilder(msg.get(0) + Utils.format(" &8[&f " + Utils.df.format(expGracza) + " &bexp &7/&f " + Utils.df.format(expNaNextLvlGracza) + " &bexp" + "&7(&b" + Utils.procentFormat.format((expGracza / expNaNextLvlGracza) * 100) + "%&7)&8]"));
                            for (int i = 1; i < msg.size(); i++) {
                                finalMessage = new StringBuilder(Utils.format(finalMessage + " " + color + msg.get(i)));
                            }
                        }
                    } else {
                        finalMessage = new StringBuilder(Utils.format(" &8[&f" + Utils.df.format(expGracza) + " &bexp &7/&f " + Utils.df.format(expNaNextLvlGracza) + " &bexp" + "&7(&b" + Utils.procentFormat.format((expGracza / expNaNextLvlGracza) * 100) + "%&7)&8]"));
                    }

                    Bukkit.broadcastMessage(formatPrzedWiadomoscia + finalMessage);

                    break;
                case 4:
                    player.chat("kasa");
                    break;
                case 5:
                    Bukkit.broadcastMessage("czas");
                    break;
                default:
                    e.setCancelled(true);
                    break;
            }
            rpgcore.getChatManager().updateMessageWithEQ(e.getWhoClicked().getUniqueId(), "");
            e.setCancelled(true);
            e.getWhoClicked().closeInventory();
        }

        if (clickedInventoryTitle.contains("Historia kar gracza ")) {
            e.setCancelled(true);
            return;
        }

        if (clickedInventoryTitle.contains("Akcesoria gracza ")) {
            ItemStack itemToGiveBack;

            e.setCancelled(true);

            if (clickedItem.getType() == Material.BARRIER) {
                return;
            }

            if (clickedSlot == 10) {
                itemToGiveBack = clickedItem;
                e.getWhoClicked().getInventory().addItem(itemToGiveBack);
                rpgcore.getPlayerManager().updatePlayerDef(playerUUID, rpgcore.getPlayerManager().getPlayerDef(playerUUID) - rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToGiveBack, "Obrona"));
                rpgcore.getPlayerManager().updatePlayerBlok(playerUUID, rpgcore.getPlayerManager().getPlayerBlok(playerUUID) - rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToGiveBack, "Blok Ciosu"));
                rpgcore.getPlayerManager().updatePlayerDamage(playerUUID, rpgcore.getPlayerManager().getPlayerDamage(playerUUID) - rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToGiveBack, "Obrazenia"));
                e.getInventory().setItem(clickedSlot, rpgcore.getAkcesoriaManager().noAkcesoriaItem("Tarczy"));
                return;
            }

            if (clickedSlot == 11) {
                itemToGiveBack = clickedItem;
                e.getWhoClicked().getInventory().addItem(itemToGiveBack);
                rpgcore.getPlayerManager().updatePlayerPrzeszywka(playerUUID, rpgcore.getPlayerManager().getPlayerPrzeszywka(playerUUID) - rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToGiveBack, "Przeszycie Bloku"));
                rpgcore.getPlayerManager().updatePlayerDamage(playerUUID, rpgcore.getPlayerManager().getPlayerDamage(playerUUID) - rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToGiveBack, "Obrazenia"));
                System.out.println("Naszyjnik dmg - " + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToGiveBack, "Obrazenia"));
                e.getInventory().setItem(clickedSlot, rpgcore.getAkcesoriaManager().noAkcesoriaItem("Naszyjnika"));
                return;
            }

            if (clickedSlot == 12) {
                itemToGiveBack = clickedItem;
                e.getWhoClicked().getInventory().addItem(itemToGiveBack);
                rpgcore.getPlayerManager().updatePlayerKryt(playerUUID, rpgcore.getPlayerManager().getPlayerKryt(playerUUID) - rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToGiveBack, "Cios Krytyczny"));
                rpgcore.getPlayerManager().updatePlayerSrednie(playerUUID, rpgcore.getPlayerManager().getPlayerSrednie(playerUUID) - rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToGiveBack, "Srednie Obrazenia"));
                e.getInventory().setItem(clickedSlot, rpgcore.getAkcesoriaManager().noAkcesoriaItem("Bransolety"));
                return;
            }

            if (clickedSlot == 13) {
                itemToGiveBack = clickedItem;
                e.getWhoClicked().getInventory().addItem(itemToGiveBack);
                rpgcore.getPlayerManager().updatePlayerHP(playerUUID, rpgcore.getPlayerManager().getPlayerHP(playerUUID) - rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToGiveBack, "Dodatkowe HP"));
                rpgcore.getPlayerManager().updatePlayerSilnyNaLudzi(playerUUID, rpgcore.getPlayerManager().getPlayerSilnyNaLudzi(playerUUID) - rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToGiveBack, "Silny przeciwko Ludziom"));
                player.setMaxHealth(player.getMaxHealth() - (double) rpgcore.getAkcesoriaManager().getAkcesoriaBonus(playerUUID, 13, "Dodatkowe HP") * 2);
                e.getInventory().setItem(clickedSlot, rpgcore.getAkcesoriaManager().noAkcesoriaItem("Kolczykow"));
                return;
            }

            if (clickedSlot == 14) {
                itemToGiveBack = clickedItem;
                e.getWhoClicked().getInventory().addItem(itemToGiveBack);
                rpgcore.getPlayerManager().updatePlayerBlok(playerUUID, rpgcore.getPlayerManager().getPlayerBlok(playerUUID) - rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToGiveBack, "Blok Ciosu"));
                rpgcore.getPlayerManager().updatePlayerHP(playerUUID, rpgcore.getPlayerManager().getPlayerHP(playerUUID) - rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToGiveBack, "Dodatkowe HP"));
                player.setMaxHealth(player.getMaxHealth() - (double) rpgcore.getAkcesoriaManager().getAkcesoriaBonus(playerUUID, 14, "Dodatkowe HP") * 2);
                System.out.println("Pierek blok -" + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToGiveBack, "Blok Ciosu"));
                e.getInventory().setItem(clickedSlot, rpgcore.getAkcesoriaManager().noAkcesoriaItem("Pierscienia"));
                return;
            }

            if (clickedSlot == 15) {
                itemToGiveBack = clickedItem;
                e.getWhoClicked().getInventory().addItem(itemToGiveBack);
                rpgcore.getPlayerManager().updatePlayerDef(playerUUID, rpgcore.getPlayerManager().getPlayerDef(playerUUID) - rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToGiveBack, "Obrona"));
                rpgcore.getPlayerManager().updatePlayerBlok(playerUUID, rpgcore.getPlayerManager().getPlayerBlok(playerUUID) - rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToGiveBack, "Blok Ciosu"));
                rpgcore.getPlayerManager().updatePlayerMinusSrednie(playerUUID, rpgcore.getPlayerManager().getPlayerMinusSrednie(playerUUID) - rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToGiveBack, "Srednie Obrazenia"));
                System.out.println("energia blok - " + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToGiveBack, "Blok Ciosu"));
                e.getInventory().setItem(clickedSlot, rpgcore.getAkcesoriaManager().noAkcesoriaItem("Energii"));
                return;
            }

            if (clickedSlot == 16) {
                itemToGiveBack = clickedItem;
                e.getWhoClicked().getInventory().addItem(itemToGiveBack);
                rpgcore.getPlayerManager().updatePlayerDamage(playerUUID, rpgcore.getPlayerManager().getPlayerDamage(playerUUID) - rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToGiveBack, "Obrazenia"));
                rpgcore.getPlayerManager().updatePlayerSilnyNaLudzi(playerUUID, rpgcore.getPlayerManager().getPlayerSilnyNaLudzi(playerUUID) - rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToGiveBack, "Silny przeciwko Ludziom"));
                rpgcore.getPlayerManager().updatePlayerMinusDef(playerUUID, rpgcore.getPlayerManager().getPlayerMinusDef(playerUUID) - rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToGiveBack, "Obrona"));
                System.out.println("Zegarek dmg - " + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToGiveBack, "Obrazenia"));
                e.getInventory().setItem(clickedSlot, rpgcore.getAkcesoriaManager().noAkcesoriaItem("Zegarka"));
                return;
            }
            return;
        }
        // POMOC 1
        if (clickedInventoryTitle.equals(rpgcore.getPomocManager().pomocGUIMAIN().getName())) {
            e.setCancelled(true);
            if (clickedItem.getType() == Material.BOOK) {
                player.openInventory(rpgcore.getPomocManager().pomocGUIREGULAMINTARYFIKATOR());
                return;
            }
            if (clickedSlot == 13) {
                player.openInventory(rpgcore.getPomocManager().pomocGUIPODSTAWOWEKOMENDY());
                return;
            }
            if (clickedItem.getType() == Material.FIREWORK_CHARGE) {
                player.closeInventory();
                player.sendMessage(Utils.SERVERNAME + Utils.format(" &6Poradnik:"));
                player.sendMessage("1. Nie bądź kurwą...");
                player.sendMessage("2. Odpierdalasz = wypierdalasz");
                return;
            }
            return;
        }
        if (clickedInventoryTitle.equals(rpgcore.getPomocManager().pomocGUIPODSTAWOWEKOMENDY().getName())) {
            e.setCancelled(true);
            return;
        }
        // POMOC 3
        if (clickedInventoryTitle.equals(rpgcore.getPomocManager().pomocGUIREGULAMINTARYFIKATOR().getName())) {
            e.setCancelled(true);
            if (clickedSlot == 2) {
                player.closeInventory();
                player.sendMessage(Utils.SERVERNAME + Utils.format(" &6Link do regulaminu:"));
                player.sendMessage(Utils.format("&ewww.twojstarywzoo.pl"));
            }
            if (clickedSlot == 6) {
                player.closeInventory();
                player.sendMessage(Utils.SERVERNAME + Utils.format(" &6Link do taryfikatora:"));
                player.sendMessage(Utils.format("&ewww.twojstarywzoo.pl"));
            }
            return;
        }

        // DUSZOLOG
        if (clickedInventoryTitle.equals(rpgcore.getDuszologNPC().duszologMAIN().getName())) {
            e.setCancelled(true);
            if (clickedSlot == 10) {
                rpgcore.getDuszologNPC().craftowanieDUSZ(player);
                player.closeInventory();
            }
            if (clickedSlot == 11) {
                player.openInventory(rpgcore.getDuszologNPC().dodawanieDUSZ());
            }
            if (clickedSlot == 13) {
                rpgcore.getDuszologNPC().craftowanieKAMIENUZBROJENIA(player);
                player.closeInventory();
            }
            return;
        }
        if (clickedInventoryTitle.equals(rpgcore.getDuszologNPC().dodawanieKAMIENIA().getName())) {
            e.setCancelled(true);
            if (clickedSlot == 2) {
                if (clickedItem.getType() == Material.DIAMOND_CHESTPLATE) {
                    player.sendMessage("ta juz tworzysz itd klata");
                    player.closeInventory();
                }
            }
        }
        if (e.getClickedInventory().getType() == InventoryType.PLAYER) {
            if (player.getOpenInventory().getTitle().contains(Utils.format("&eDodaj &3Kamien &bUzbrojenia"))) {
                e.setCancelled(true);
                player.sendMessage(clickedSlot + " - slot");
                if (!(player.getOpenInventory().getTopInventory().getItem(0).getType().equals(Material.STAINED_GLASS_PANE))) {
                    player.sendMessage("test");

                    ItemStack is = player.getOpenInventory().getTopInventory().getItem(0).clone();
                    ItemMeta meta = is.getItemMeta();

                    List<String> lore = meta.getLore();

                    int miejsce = 0;

                    if (!(lore.contains("Miejsce"))) {
                        for (int i = 0; i < lore.size(); i++) {
                            if (lore.get(i).contains("Thorns")) {
                                miejsce = i;
                                break;
                            }
                        }
                    } else {
                        for (int i = 0; i < lore.size(); i++) {
                            if (lore.get(i).contains("Miejsce")) {
                                miejsce = i;
                            }
                        }
                    }

                    //Obrona: 100
                    //Throns: 70

                    //Obrona: 100
                    //Throns: 70
                    //
                    //Miejsce: ...
                    //
                    //Dodakowa Odpornosc: ...
                    //Dodatkowy Exp: ...
                    //DOdatkowy dmg: ...



                    List<String> loreAfterMiejsce = new ArrayList<>();

                    for (int i = miejsce + 1; i < lore.size(); i++) {
                        loreAfterMiejsce.add(lore.get(miejsce));
                        lore.remove(miejsce);
                    }
                    if (!(lore.contains("Miejsce"))) {
                        lore.add(" ");
                        lore.add("Miejsce na cos tam:");
                    } else {
                        lore.add("Miejsce na cos tam:");
                    }

                    lore.addAll(loreAfterMiejsce);

                    meta.setLore(lore);
                    is.setItemMeta(meta);

                    player.getOpenInventory().getTopInventory().setItem(2, is);

                    return;
                }
                if (String.valueOf(clickedItem.getType()).contains("CHESTPLATE")) {
                    ItemStack klata = e.getCurrentItem();

                    player.getOpenInventory().getTopInventory().setItem(0, klata);
                    itemMapToRemove.put(1, klata);
                    player.getInventory().removeItem(itemMapToRemove.get(1));
                }
            }
        }

        // TELEPORTER
        if (clickedInventoryTitle.equals(rpgcore.getTeleporterNPC().teleporterMAIN(player).getName())) {
            if (clickedSlot == 10) {
                if (clickedItem.getType() == Material.GRASS) {
                    player.sendMessage("test teleportacja 1 expo");
                    player.closeInventory();
                }
            }
            if (clickedSlot == 11) {
                if (clickedItem.getType() == Material.STONE) {
                    player.sendMessage("test teleportacja 2 expo");
                    player.closeInventory();
                }
            }
            e.setCancelled(true);
        }
        //                      TRADE                   \\



        if (clickedInventoryTitle.contains("Wymiana ")) {
            final Player firstViewer = (Player) clickedInventory.getViewers().get(1);
            final Player secViewer = (Player) clickedInventory.getViewers().get(0);
            final UUID secViewerUUID = secViewer.getUniqueId();
            final UUID firstViewerUUID = firstViewer.getUniqueId();
            if (clickedInventoryTitle.equals(rpgcore.getTradeManager().createTradeGUI(firstViewerUUID, secViewerUUID).getName())) {
                final int i = clickedSlot;

                if (rpgcore.getTradeManager().isTradeAccepted(clickedInventory)) {
                    e.setCancelled(true);
                    return;
                }

                if (!((i > 9 && i < 13) || (i > 13 && i< 17) || (i > 18 && i < 22) || (i > 22 && i < 26) || (i > 27 && i < 31) || (i > 31 && i < 35) || (i > 36 && i < 40) ||
                        (i > 40 && i < 44))) {
                    e.setCancelled(true);
                    if (i == 48) {
                        if (e.getWhoClicked().equals(firstViewer)) {
                            if (clickedInventory.getItem(48).getItemMeta().getDisplayName().equals(rpgcore.getTradeManager().getNoAcceptItem(firstViewerUUID).getItemMeta().getDisplayName())) {
                                clickedInventory.setItem(48, rpgcore.getTradeManager().getAcceptItem(firstViewerUUID));
                                if (clickedInventory.getItem(48).getItemMeta().getDisplayName().equals(rpgcore.getTradeManager().getAcceptItem(firstViewerUUID).getItemMeta().getDisplayName())
                                        && clickedInventory.getItem(50).getItemMeta().getDisplayName().equals(rpgcore.getTradeManager().getAcceptItem(secViewerUUID).getItemMeta().getDisplayName())) {
                                    e.setCancelled(true);
                                    rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> rpgcore.getTradeManager().tradeAccepted(clickedInventory, firstViewer, secViewer), 1L);
                                    return;
                                }
                                return;
                            }
                            clickedInventory.setItem(48, rpgcore.getTradeManager().getNoAcceptItem(firstViewerUUID));
                            return;
                        }
                    }
                    if (i == 50) {
                        if (e.getWhoClicked().equals(secViewer)) {
                            if (clickedInventory.getItem(50).getItemMeta().getDisplayName().equals(rpgcore.getTradeManager().getNoAcceptItem(secViewerUUID).getItemMeta().getDisplayName())) {
                                clickedInventory.setItem(50, rpgcore.getTradeManager().getAcceptItem(secViewerUUID));
                                if (clickedInventory.getItem(48).getItemMeta().getDisplayName().equals(rpgcore.getTradeManager().getAcceptItem(firstViewerUUID).getItemMeta().getDisplayName())
                                        && clickedInventory.getItem(50).getItemMeta().getDisplayName().equals(rpgcore.getTradeManager().getAcceptItem(secViewerUUID).getItemMeta().getDisplayName())) {
                                    e.setCancelled(true);
                                    rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> rpgcore.getTradeManager().tradeAccepted(clickedInventory, firstViewer, secViewer), 1L);
                                    return;
                                }
                                return;
                            }
                            clickedInventory.setItem(50, rpgcore.getTradeManager().getNoAcceptItem(secViewerUUID));
                            return;
                        }
                    }
                    return;
                }

                if (e.getWhoClicked().equals(firstViewer)) {
                    if (! (i < 13 || i > 18 && i < 22 || i > 27 && i < 31 || i > 36 && i < 40)) {
                        e.setCancelled(true);
                        return;
                    }

                    if (clickedInventory.getItem(48).getItemMeta().getDisplayName().equals(rpgcore.getTradeManager().getAcceptItem(firstViewerUUID).getItemMeta().getDisplayName())) {
                        e.setCancelled(true);
                        return;
                    }
                    return;
                }

                if (e.getWhoClicked().equals(secViewer)) {
                    if (! (i > 13 && i < 17 || i > 22 && i < 26 || i > 31 && i < 35 || i > 40)) {
                        e.setCancelled(true);
                        return;
                    }
                    if (clickedInventory.getItem(50).getItemMeta().getDisplayName().equals(rpgcore.getTradeManager().getAcceptItem(secViewerUUID).getItemMeta().getDisplayName())) {
                        e.setCancelled(true);
                        return;
                    }
                    return;
                }
            }
            return;
        }


        //                      TARGI                   \\



        if (clickedInventoryTitle.contains("Lista Targow")) {
            e.setCancelled(true);

            if (clickedItem.getType() == Material.AIR) {
                return;
            }

            if (clickedItem.getType().equals(Material.BARRIER) || clickedItem.getType().equals(Material.STAINED_GLASS_PANE)) {
                return;
            }

            int task;

            if (rpgcore.getTargManager().isInTaskMap(playerUUID)) {
                rpgcore.getServer().getScheduler().cancelTask(rpgcore.getTargManager().getPlayerTaskId(playerUUID));
                rpgcore.getTargManager().removePlayerFromTaskMap(playerUUID);
            }

            int page = Integer.parseInt(Utils.removeColor(clickedInventoryTitle.replace("Lista Targow ", "").replace("    ", "").trim()));


            if (clickedItem.getItemMeta().getDisplayName().contains("nastepna")) {
                player.openInventory(rpgcore.getTargManager().openTargGUI(page + 1));
                return;
            }

            if (clickedItem.getItemMeta().getDisplayName().contains("poprzednia")) {
                player.openInventory(rpgcore.getTargManager().openTargGUI(page - 1));
                return;
            }

            final UUID playerTargUUID = rpgcore.getPlayerManager().getPlayerUUID(Utils.removeColor(clickedItem.getItemMeta().getDisplayName().trim()));
            if (rpgcore.getTargManager().getPlayerTarg(playerTargUUID).getViewers().size() != 0) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&cKtos aktualnie przeglada ten targ, sprobuj ponownie za chwile"));
                return;
            }
            player.openInventory(rpgcore.getTargManager().getPlayerTarg(playerTargUUID));
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie otworzyles targ gracza &6" + Utils.removeColor(clickedItem.getItemMeta().getDisplayName().trim())));
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&8&oOchrona AntyBlock, &8otwarty przez ciebie targ zostanie zamkniety po &c60 sekundach"));
            task = rpgcore.getServer().getScheduler().scheduleSyncDelayedTask(rpgcore, () -> {
                if (player.getOpenInventory().getTopInventory().getName().contains("Targ gracza") || player.getOpenInventory().getTopInventory().getName().contains("Kup przedmiot ")) {
                    player.closeInventory();
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&8&oOchrona AntyBlock, &8otwarty przez ciebie targ zostal zamkniety poniewaz byl otwarty dluzej niz &c60 sekund"));
                }
            }, 1200L);
            rpgcore.getTargManager().putPlayerTask(playerUUID, task);
            return;
        }

        if (clickedInventoryTitle.contains("Targ gracza")) {
            e.setCancelled(true);

            if (clickedItem == null) {
                return;
            }

            if (clickedItem.getType().equals(Material.ARROW) && Utils.removeColor(clickedItem.getItemMeta().getDisplayName()).equals("Powrot")) {
                player.openInventory(rpgcore.getTargManager().openTargGUI(1));
                return;
            }

            final String targetName = Utils.removeColor(clickedInventoryTitle).replace("Targ gracza ", "").trim();
            final UUID targetUUID = rpgcore.getPlayerManager().getPlayerUUID(targetName);

            if (player.getName().equals(targetName)) {
                rpgcore.getTargManager().updatePlayerTarg(player, clickedSlot);
                if (clickedItem.getItemMeta().getDisplayName() == null) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie zdjales ze swojego targu &6" + clickedItem.getAmount() + "x " + clickedItem.getType()));
                } else {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie zdjales ze swojego targu &6" + clickedItem.getAmount() + "x " + clickedItem.getItemMeta().getDisplayName()));
                }

                if (rpgcore.getTargManager().getPlayerTarg(playerUUID).getItem(0) != null) {
                    player.openInventory(rpgcore.getTargManager().getPlayerTarg(playerUUID));
                    return;
                }
                player.closeInventory();
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&cTen gracz nie ma wystawionych zadnych przedmiotow"));
                return;
            }



            final double kasaGracza = rpgcore.getPlayerManager().getPlayerKasa(playerUUID);
            final double itemCena = rpgcore.getTargManager().getItemCena(clickedItem);

            if (kasaGracza < itemCena) {

                player.closeInventory();
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie masz wystarczajacych srodkow, zeby kupic ten przedmiot."));
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&cBrakuje ci &6&o" + Utils.spaceNumber(Utils.kasaFormat.format(itemCena - kasaGracza)) + " &2$"));


                return;
            }

            rpgcore.getPlayerManager().updatePlayerKasa(playerUUID, kasaGracza - itemCena);
            rpgcore.getPlayerManager().updatePlayerKasa(targetUUID, rpgcore.getPlayerManager().getPlayerKasa(targetUUID) + itemCena);
            rpgcore.getTargManager().givePlayerBoughtItem(player, clickedItem);

            rpgcore.getTargManager().updatePlayerTarg(Bukkit.getPlayer(targetUUID), clickedSlot);
            player.closeInventory();
            for (int i = 0; i < clickedInventory.getViewers().size(); i++) {
                Player p = (Player) clickedInventory.getViewers().get(i);
                p.updateInventory();
            }
            if (clickedItem.getItemMeta().getDisplayName() == null) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie kupiles przedmiot &6" + clickedItem.getAmount() + "x " + clickedItem.getType() + " &aod gracza &6" + targetName + " &aza kwote &6&o" + Utils.spaceNumber(Utils.kasaFormat.format(itemCena)) + " &2$"));
                Bukkit.getPlayer(targetUUID).sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie sprzedales przedmiot &6" + clickedItem.getAmount() + "x " + clickedItem.getType() + " &adla gracza &6" + player.getName() + " &aza kwote &6&o" + Utils.spaceNumber(Utils.kasaFormat.format(itemCena)) + " &2$"));
                return;
            }
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie kupiles przedmiot &6" + clickedItem.getAmount() + "x " + clickedItem.getItemMeta().getDisplayName() + " &aod gracza &6" + targetName + " &aza kwote &6&o" + Utils.spaceNumber(Utils.kasaFormat.format(itemCena)) + " &2$"));
            Bukkit.getPlayer(targetUUID).sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie sprzedales przedmiot &6" + clickedItem.getAmount() + "x " + clickedItem.getItemMeta().getDisplayName() + " &adla gracza &6" + player.getName() + " &aza kwote &6&o" + Utils.spaceNumber(Utils.kasaFormat.format(itemCena)) + " &2$"));
            return;
        }


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
                    if (s.contains("5% ceny wystawienia")) {
                        podatek = Utils.removeColor(s).replace("W wysokosci 5% ceny wystawienia, czyli ", "").replace(" $", "");
                    }
                }

                System.out.println(cena);
                System.out.println(podatek);
                final double podatekInDouble = Double.parseDouble(podatek.replaceAll(" ", "").trim());
                final double kasa = rpgcore.getPlayerManager().getPlayerKasa(playerUUID);

                if (kasa < podatekInDouble) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie masz pieniedzy, zeby wystawic ten przedmiot"));

                    rpgcore.getTargManager().returnPlayerItem(player, item);
                    rpgcore.getTargManager().removeFromWystawia(playerUUID);
                    rpgcore.getServer().getScheduler().runTaskLater(rpgcore, player::closeInventory, 1L);
                    return;
                }
                rpgcore.getPlayerManager().updatePlayerKasa(playerUUID, kasa - podatekInDouble);

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

                rpgcore.getTargManager().getPlayerTarg(playerUUID).setItem(rpgcore.getTargManager().getPlayerTarg(playerUUID).firstEmpty(), item);
                for (int i = 0; i < rpgcore.getTargManager().getPlayerTarg(playerUUID).getViewers().size(); i++) {
                    Player p = (Player) rpgcore.getTargManager().getPlayerTarg(playerUUID).getViewers().get(i);
                    p.updateInventory();
                }
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie wystawiles przedmiot za &6&o" + cena + " &2$"));
                rpgcore.getTargManager().removeFromWystawia(playerUUID);
                player.closeInventory();
                return;
            }

            rpgcore.getTargManager().returnPlayerItem(player, clickedInventory.getItem(4));
            rpgcore.getTargManager().removeFromWystawia(playerUUID);
            rpgcore.getServer().getScheduler().runTaskLater(rpgcore, player::closeInventory, 1L);
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&cAnulowales wystawianie przedmiotu"));
            return;

        }

        if (clickedInventoryTitle.contains("Menu Rybaka")) {
            e.setCancelled(true);
            if (clickedSlot == 10) {
                rpgcore.getRybakNPC().openRybakKampania(player);
                return;
            }
            if (clickedSlot == 16) {
                rpgcore.getRybakNPC().openRybakSklep(player);
                return;
            }
        }

        if (clickedInventoryTitle.contains("Sklep Rybacki")) {
            e.setCancelled(true);
            //                  KUPOWANIE WEDKI             \\
            if (clickedSlot == 0) {
                final double cenaWedki = Double.parseDouble(Utils.removeColor(clickedItem.getItemMeta().getLore().get(clickedItem.getItemMeta().getLore().size() - 1)).replace("Cena:", "").replace(" ", "").replace("$", "").trim());
                if (rpgcore.getPlayerManager().getPlayerKasa(playerUUID) < cenaWedki) {
                    player.sendMessage(Utils.format(Utils.RYBAK + "&cCzy ty probujesz mnie oszukac? Nie stac cie na moja wedke"));
                    player.closeInventory();
                    return;
                }

                rpgcore.getPlayerManager().updatePlayerKasa(playerUUID, rpgcore.getPlayerManager().getPlayerKasa(playerUUID) - cenaWedki);

                player.getInventory().addItem(rpgcore.getRybakNPC().givePlayerRod(player));
                player.sendMessage(Utils.format(Utils.RYBAK + "&aPomyslnie zakupiles moja wedka. &6Udanych lowow!"));
                player.closeInventory();
                return;
            }


            //                  SPRZEDAWANIE RYBEK             \\

            ItemStack is = clickedItem.clone();
            ItemMeta im = is.getItemMeta();
            List<String> lore = new ArrayList<>();
            lore.add(Utils.format("&8&oChyba &8&n&orybak&r &8&otego potrzebuje"));
            im.setDisplayName(clickedItem.getItemMeta().getDisplayName());
            im.setLore(lore);
            im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            im.addItemFlags(ItemFlag.HIDE_DESTROYS);
            is.setItemMeta(im);

            int amount = 0;

            if (e.getClick().equals(ClickType.LEFT) || e.getClick().equals(ClickType.SHIFT_LEFT)) amount = 1;
            if (e.getClick().equals(ClickType.RIGHT) || e.getClick().equals(ClickType.SHIFT_RIGHT)) {
                for (ItemStack is2 : player.getInventory().getContents()) {
                    if (is2 != null && is2.getType() != null && is2.getItemMeta().getDisplayName() != null) {
                        if (is2.getItemMeta().getDisplayName().equals(is.getItemMeta().getDisplayName())) {
                            amount += is2.getAmount();
                        }
                    }
                }
            }

            is.setAmount(amount);
            lore.clear();


            if (player.getInventory().containsAtLeast(is, amount)) {
                player.getInventory().removeItem(is);

                lore = clickedItem.getItemMeta().getLore();
                double cena = 0.0;
                for (String s : lore) {
                    if (s.contains("Cena: ")) {
                        cena = Double.parseDouble(Utils.removeColor(s).replace("Cena: ", "").replace(" ", "").replace("$", "").trim());
                        break;
                    }
                }
                rpgcore.getPlayerManager().updatePlayerKasa(playerUUID, rpgcore.getPlayerManager().getPlayerKasa(playerUUID) + (cena * amount));
                player.sendMessage(Utils.format(Utils.RYBAK + "&aSprzedales &6&o" + amount + " &a&orybek za &6&o" + (cena * amount) + " &a$"));
                player.closeInventory();
                final int mission = rpgcore.getRybakNPC().getPlayerCurrentMission(playerUUID);
                if (mission == 41 || mission == 43) {
                    rpgcore.getRybakNPC().updatePlayerPostep(playerUUID, amount);
                }
                return;
            }
            player.sendMessage(Utils.format(Utils.RYBAK + "&cNie masz tego rodzaju rybek w swoim ekwipunku"));
            player.closeInventory();
            return;
        }

        if (clickedInventoryTitle.contains("Kampania Rybacka")) {
            e.setCancelled(true);

            if (!clickedItem.getType().equals(Material.BOOK_AND_QUILL)){
                return;
            }

            final String[] misjeGracza = rpgcore.getRybakNPC().getPlayerRybakMisje(playerUUID).split(",");

            if (misjeGracza[clickedSlot].equalsIgnoreCase("true")) {
                return;
            }

            final String[] misja = rpgcore.getRybakNPC().getMisja(clickedSlot).split(";");

            final int currentMission = rpgcore.getRybakNPC().getPlayerCurrentMission(playerUUID);

            if (currentMission == 4) {
                final ItemBuilder sledz = new ItemBuilder(Material.RAW_FISH, 256);
                final ItemBuilder dorsz = new ItemBuilder(Material.RAW_FISH, 256, (short) 1);
                final ItemBuilder losos = new ItemBuilder(Material.RAW_FISH, 256, (short) 1);

                final List<String> lore = new ArrayList<>();

                lore.add("&8&oChyba &8&n&orybak&r &8&otego potrzebuje");

                sledz.setName("&6Sledz").setLore(lore).hideFlag();
                dorsz.setName("&6Dorsz").setLore(lore).hideFlag();
                losos.setName("&6Losos").setLore(lore).hideFlag();


                if (player.getInventory().containsAtLeast(sledz.toItemStack(), 256) && player.getInventory().containsAtLeast(dorsz.toItemStack(), 256)
                        && player.getInventory().containsAtLeast(losos.toItemStack(), 256)) {
                    player.getInventory().removeItem(sledz.toItemStack());
                    player.getInventory().removeItem(dorsz.toItemStack());
                    player.getInventory().removeItem(losos.toItemStack());
                } else {
                    player.closeInventory();
                    player.sendMessage(Utils.format(Utils.RYBAK + "&7Nie probuj mnie tutaj oszukiwac. Musisz jeszcze &6" + misja[0].replace("Wylow", "Wylowic").replace("Zabij", "Zabic").replace("Oddaj", "Oddac") + " &c" + (Integer.parseInt(misja[1]) - rpgcore.getRybakNPC().getPlayerPostep(playerUUID)) + " " + misja[2]));
                    return;
                }
            } else if (currentMission == 8) {
                final ItemBuilder krasnopiorka = new ItemBuilder(Material.RAW_FISH, 256, (short) 2);
                final ItemBuilder dorszCzarny = new ItemBuilder(Material.COOKED_FISH, 256, (short) 1);
                final ItemBuilder dorada = new ItemBuilder(Material.RAW_FISH, 256);

                final List<String> lore = new ArrayList<>();

                lore.add("&8&oChyba &8&n&orybak&r &8&otego potrzebuje");

                krasnopiorka.setName("&6Krasnopiorka").setLore(lore);
                dorszCzarny.setName("&6Dorsz Czarny").setLore(lore);
                dorada.setName("&6Dorada").setLore(lore);


                if (player.getInventory().containsAtLeast(krasnopiorka.toItemStack(), 256) && player.getInventory().containsAtLeast(dorszCzarny.toItemStack(), 256)
                        && player.getInventory().containsAtLeast(dorada.toItemStack(), 256)) {
                    player.getInventory().removeItem(krasnopiorka.toItemStack());
                    player.getInventory().removeItem(dorszCzarny.toItemStack());
                    player.getInventory().removeItem(dorada.toItemStack());
                } else {
                    player.closeInventory();
                    player.sendMessage(Utils.format(Utils.RYBAK + "&7Nie probuj mnie tutaj oszukiwac. Musisz jeszcze &6" + misja[0].replace("Wylow", "Wylowic").replace("Zabij", "Zabic").replace("Oddaj", "Oddac") + " &c" + (Integer.parseInt(misja[1]) - rpgcore.getRybakNPC().getPlayerPostep(playerUUID)) + " " + misja[2]));
                    return;
                }
            } else if (currentMission == 13) {
                final ItemBuilder cierniczek = new ItemBuilder(Material.COOKED_FISH, 512);


                final List<String> lore = new ArrayList<>();

                lore.add("&8&oChyba &8&n&orybak&r &8&otego potrzebuje");

                cierniczek.setName("&6Cierniczek").setLore(lore);


                //player.getInventory().addItem(sledz.toItemStack(), dorsz.toItemStack(), losos.toItemStack());

                if (player.getInventory().containsAtLeast(cierniczek.toItemStack(), 512)) {
                    player.getInventory().removeItem(cierniczek.toItemStack());
                } else {
                    player.closeInventory();
                    player.sendMessage(Utils.format(Utils.RYBAK + "&7Nie probuj mnie tutaj oszukiwac. Musisz jeszcze &6" + misja[0].replace("Wylow", "Wylowic").replace("Zabij", "Zabic").replace("Oddaj", "Oddac") + " &c" + (Integer.parseInt(misja[1]) - rpgcore.getRybakNPC().getPlayerPostep(playerUUID)) + " " + misja[2]));
                    return;
                }
            } else if (currentMission == 14) {
                final ItemBuilder fladra = new ItemBuilder(Material.RAW_FISH, 512, (short) 3);


                final List<String> lore = new ArrayList<>();

                lore.add("&8&oChyba &8&n&orybak&r &8&otego potrzebuje");

                fladra.setName("&6Fladra").setLore(lore);

                if (player.getInventory().containsAtLeast(fladra.toItemStack(), 512)) {
                    player.getInventory().removeItem(fladra.toItemStack());
                } else {
                    player.closeInventory();
                    player.sendMessage(Utils.format(Utils.RYBAK + "&7Nie probuj mnie tutaj oszukiwac. Musisz jeszcze &6" + misja[0].replace("Wylow", "Wylowic").replace("Zabij", "Zabic").replace("Oddaj", "Oddac") + " &c" + (Integer.parseInt(misja[1]) - rpgcore.getRybakNPC().getPlayerPostep(playerUUID)) + " " + misja[2]));
                    return;
                }
            } else if (currentMission == 20) {
                final ItemBuilder chest = new ItemBuilder(Material.CHEST, 64);
                final List<String> lore = new ArrayList<>();

                lore.add("&8&n&oKliknij&r &8&ozeby otworzyc skrzynie i otrzymac przedmioty");

                chest.setName("&a&lSkrzynia Rybaka").setLore(lore);

                if (player.getInventory().containsAtLeast(chest.toItemStack(), 64)) {
                    player.getInventory().removeItem(chest.toItemStack());
                } else {
                    player.closeInventory();
                    player.sendMessage(Utils.format(Utils.RYBAK + "&7Nie probuj mnie tutaj oszukiwac. Musisz jeszcze &6" + misja[0].replace("Wylow", "Wylowic").replace("Zabij", "Zabic").replace("Oddaj", "Oddac") + " &c" + (Integer.parseInt(misja[1]) - rpgcore.getRybakNPC().getPlayerPostep(playerUUID)) + " " + misja[2]));
                    return;
                }
            } else if (currentMission == 22) {
                final ItemBuilder karas = new ItemBuilder(Material.RAW_FISH, 256, (short) 1);
                final ItemBuilder karp = new ItemBuilder(Material.COOKED_FISH, 256);
                final List<String> lore = new ArrayList<>();

                lore.add("&8&oChyba &8&n&orybak&r &8&otego potrzebuje");

                karas.setName("&6Karas").setLore(lore);
                karp.setName("&6Karp").setLore(lore);

                if (player.getInventory().containsAtLeast(karas.toItemStack(), 256) && player.getInventory().containsAtLeast(karp.toItemStack(), 256)) {
                    player.getInventory().removeItem(karas.toItemStack());
                    player.getInventory().removeItem(karp.toItemStack());
                } else {
                    player.closeInventory();
                    player.sendMessage(Utils.format(Utils.RYBAK + "&7Nie probuj mnie tutaj oszukiwac. Musisz jeszcze &6" + misja[0].replace("Wylow", "Wylowic").replace("Zabij", "Zabic").replace("Oddaj", "Oddac") + " &c" + (Integer.parseInt(misja[1]) - rpgcore.getRybakNPC().getPlayerPostep(playerUUID)) + " " + misja[2]));
                    return;
                }
            } else if (currentMission == 27) {
                final ItemBuilder leszcz = new ItemBuilder(Material.COOKED_FISH, 256, (short) 1);
                final ItemBuilder makrela = new ItemBuilder(Material.COOKED_FISH, 256);
                final ItemBuilder mintaj = new ItemBuilder(Material.COOKED_FISH, 256);
                final List<String> lore = new ArrayList<>();

                lore.add("&8&oChyba &8&n&orybak&r &8&otego potrzebuje");

                leszcz.setName("&6Leszcz").setLore(lore);
                makrela.setName("&6Makrela").setLore(lore);
                mintaj.setName("&6Mintaj").setLore(lore);

                if (player.getInventory().containsAtLeast(leszcz.toItemStack(), 256) && player.getInventory().containsAtLeast(makrela.toItemStack(), 256)
                && player.getInventory().containsAtLeast(mintaj.toItemStack(), 256)) {
                    player.getInventory().removeItem(leszcz.toItemStack());
                    player.getInventory().removeItem(makrela.toItemStack());
                    player.getInventory().removeItem(mintaj.toItemStack());
                } else {
                    player.closeInventory();
                    player.sendMessage(Utils.format(Utils.RYBAK + "&7Nie probuj mnie tutaj oszukiwac. Musisz jeszcze &6" + misja[0].replace("Wylow", "Wylowic").replace("Zabij", "Zabic").replace("Oddaj", "Oddac") + " &c" + (Integer.parseInt(misja[1]) - rpgcore.getRybakNPC().getPlayerPostep(playerUUID)) + " " + misja[2]));
                    return;
                }
            } else if (currentMission == 33) {
                final ItemBuilder okon = new ItemBuilder(Material.RAW_FISH, 256, (short) 3);
                final ItemBuilder plotka = new ItemBuilder(Material.RAW_FISH, 256, (short) 1);
                final List<String> lore = new ArrayList<>();

                lore.add("&8&oChyba &8&n&orybak&r &8&otego potrzebuje");

                okon.setName("&6Okon").setLore(lore);
                plotka.setName("&6Plotka").setLore(lore);

                if (player.getInventory().containsAtLeast(okon.toItemStack(), 256) && player.getInventory().containsAtLeast(plotka.toItemStack(), 256)) {
                    player.getInventory().removeItem(okon.toItemStack());
                    player.getInventory().removeItem(plotka.toItemStack());
                } else {
                    player.closeInventory();
                    player.sendMessage(Utils.format(Utils.RYBAK + "&7Nie probuj mnie tutaj oszukiwac. Musisz jeszcze &6" + misja[0].replace("Wylow", "Wylowic").replace("Zabij", "Zabic").replace("Oddaj", "Oddac") + " &c" + (Integer.parseInt(misja[1]) - rpgcore.getRybakNPC().getPlayerPostep(playerUUID)) + " " + misja[2]));
                    return;
                }
            } else









            if (rpgcore.getRybakNPC().getPlayerPostep(playerUUID) < Integer.parseInt(misja[1])) {
                player.closeInventory();
                player.sendMessage(Utils.format(Utils.RYBAK + "&7Nie probuj mnie tutaj oszukiwac. Musisz jeszcze &6" + misja[0].replace("Wylow", "Wylowic").replace("Zabij", "Zabic").replace("Oddaj", "Oddac") + " &c" + (Integer.parseInt(misja[1]) - rpgcore.getRybakNPC().getPlayerPostep(playerUUID)) + " " + misja[2]));
                return;
            }

            misjeGracza[clickedSlot] = "true";
            rpgcore.getRybakNPC().addReward(playerUUID, misja[3], misja[4]);
            rpgcore.getRybakNPC().addReward(playerUUID, misja[5], misja[6]);

            final StringBuilder builder = new StringBuilder();

            for (int i = 0; i < misjeGracza.length; i++) {
                builder.append(misjeGracza[i]);
                if (!(i+1 > misjeGracza.length)) {
                    builder.append(",");
                }
            }

            rpgcore.getRybakNPC().updatePlayerRybakMisje(playerUUID, String.valueOf(builder));
            rpgcore.getRybakNPC().setPlayerPostep(playerUUID, 0);

            player.closeInventory();
            if (clickedSlot == 44) {
                for (int i=0; i<20;i++) {
                    rpgcore.getServer().broadcastMessage(Utils.format(Utils.RYBAK + "&7Gracz &6" + player.getName() + " &7ukonczyl wlasnie moja &4&lCALA KAMPANIE.&6Gratulacje!"));
                }
                return;
            }
            rpgcore.getServer().broadcastMessage(Utils.format(Utils.RYBAK + "&7Gracz &6" + player.getName() + " &7ukonczyl wlasnie moja &6" + Utils.removeColor(clickedItem.getItemMeta().getDisplayName().replace("Misja #", "")) + " &7misje.&6Gratulacje!"));
            return;
        }

        
    }
}
