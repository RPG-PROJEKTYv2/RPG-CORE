package rpg.rpgcore.listeners;

import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

public class PlayerInventoryClickListener implements Listener {

    private final RPGCORE rpgcore;

    public PlayerInventoryClickListener(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClick(final InventoryClickEvent e) {
        final Player player = (Player) e.getWhoClicked();
        final UUID playerUUID = player.getUniqueId();
        final HashMap<Integer, ItemStack> itemMapToRemove = new HashMap<>();

        if (e.getClickedInventory() == null) {
            return;
        }

        //                      GUI OD BAO                  \\
        if (e.getClickedInventory().getName().equals(Utils.format("&6&lSTOL MAGII"))) {

            if (e.getSlot() == 16) {
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

        if (e.getClickedInventory().getName().equalsIgnoreCase(rpgcore.getBaoManager().ksiegaMagiiGUI(playerUUID).getName())) {
            e.setCancelled(true);
            if (e.getSlot() == 11) {
                rpgcore.getBaoManager().losujNowyBonus1(playerUUID);
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&6Twoj nowy bonus to: &c" + rpgcore.getBaoManager().getBaoBonusy(playerUUID).split(",")[e.getSlot() - 11] + " " + rpgcore.getBaoManager().getBaoBonusyWartosci(playerUUID).split(",")[e.getSlot() - 11]) + " %");
            }
            if (e.getSlot() == 12) {
                rpgcore.getBaoManager().losujNowyBonus2(playerUUID);
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&6Twoj nowy bonus to: &c" + rpgcore.getBaoManager().getBaoBonusy(playerUUID).split(",")[e.getSlot() - 11] + " " + rpgcore.getBaoManager().getBaoBonusyWartosci(playerUUID).split(",")[e.getSlot() - 11]) + " %");
            }
            if (e.getSlot() == 13) {
                rpgcore.getBaoManager().losujNowyBonus3(playerUUID);
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&6Twoj nowy bonus to: &c" + rpgcore.getBaoManager().getBaoBonusy(playerUUID).split(",")[e.getSlot() - 11] + " " + rpgcore.getBaoManager().getBaoBonusyWartosci(playerUUID).split(",")[e.getSlot() - 11]) + " %");
            }
            if (e.getSlot() == 14) {
                rpgcore.getBaoManager().losujNowyBonus4(playerUUID);
                if (rpgcore.getBaoManager().getBaoBonusy(playerUUID).split(",")[3].equalsIgnoreCase("dodatkowe obrazenia")) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&6Twoj nowy bonus to: &c" + rpgcore.getBaoManager().getBaoBonusy(playerUUID).split(",")[e.getSlot() - 11] + " " + rpgcore.getBaoManager().getBaoBonusyWartosci(playerUUID).split(",")[e.getSlot() - 11]) + " DMG");
                } else {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&6Twoj nowy bonus to: &c" + rpgcore.getBaoManager().getBaoBonusy(playerUUID).split(",")[e.getSlot() - 11] + " " + rpgcore.getBaoManager().getBaoBonusyWartosci(playerUUID).split(",")[e.getSlot() - 11]) + " %");
                }
            }
            if (e.getSlot() == 15) {
                rpgcore.getBaoManager().losujNowyBonus5(playerUUID);
                if (rpgcore.getBaoManager().getBaoBonusy(playerUUID).split(",")[4].equalsIgnoreCase("dodatkowe hp")) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&6Twoj nowy bonus to: &c" + rpgcore.getBaoManager().getBaoBonusy(playerUUID).split(",")[e.getSlot() - 11] + " " + rpgcore.getBaoManager().getBaoBonusyWartosci(playerUUID).split(",")[e.getSlot() - 11]) + " HP");
                } else {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&6Twoj nowy bonus to: &c" + rpgcore.getBaoManager().getBaoBonusy(playerUUID).split(",")[e.getSlot() - 11] + " " + rpgcore.getBaoManager().getBaoBonusyWartosci(playerUUID).split(",")[e.getSlot() - 11]) + " %");
                }
            }
            player.closeInventory();
            return;
        }

        if (e.getClickedInventory().getName().equals(rpgcore.getOsManager().osGuiMain().getName())) {
            e.setCancelled(true);
            if (e.getCurrentItem().getType() == Material.GOLD_SWORD){
                player.openInventory(rpgcore.getOsManager().osMobyGui(playerUUID));
                return;
            }
            if (e.getCurrentItem().getType() == Material.DIAMOND_SWORD) {
                player.openInventory(rpgcore.getOsManager().osGraczeGUI(playerUUID));
                return;
            }
            if (e.getCurrentItem().getType() == Material.EXP_BOTTLE) {
                player.openInventory(rpgcore.getOsManager().osSakwyGUI(playerUUID));
                return;
            }
            if (e.getCurrentItem().getType() == Material.DIAMOND_BLOCK) {
                player.openInventory(rpgcore.getOsManager().osNiesyGUI(playerUUID));
                return;
            }
            if (e.getCurrentItem().getType() == Material.FISHING_ROD) {
                player.openInventory(rpgcore.getOsManager().osRybakGUI(playerUUID));
                return;
            }
            if (e.getCurrentItem().getType() == Material.DIAMOND_AXE) {
                player.openInventory(rpgcore.getOsManager().osDrwalGUI(playerUUID));
                return;
            }
            if (e.getCurrentItem().getType() == Material.GOLD_PICKAXE) {
                player.openInventory(rpgcore.getOsManager().osGornikGUI(playerUUID));
                return;
            }
        }

        if (e.getClickedInventory().getName().equals(rpgcore.getOsManager().osMobyGui(playerUUID).getName())) {
            final int slot = e.getSlot();
            e.setCancelled(true);
            if (e.getCurrentItem().getType() == Material.AIR) {
                return;
            }

            String[] accepted = rpgcore.getPlayerManager().getOsMobyAccept(playerUUID).split(",");

            if (accepted[slot].equalsIgnoreCase("true")) {
                return;
            }

            if (slot != 0 && accepted[slot-1].equalsIgnoreCase("false")) {
                return;
            }

            final int killedMobs = rpgcore.getPlayerManager().getPlayerOsMoby(playerUUID);

            if (killedMobs < rpgcore.getOsManager().getRequiredForOsMoby().get(slot+1)) {
                return;
            }

            accepted[slot] = "true";
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
            Bukkit.getServer().broadcastMessage(Utils.format(Utils.SERVERNAME + "&3Gracz &f" + player.getName() + " &3odebral osiagniecie &f" + Utils.removeColor(e.getCurrentItem().getItemMeta().getDisplayName())));
            noweOsMobyAccepted.setLength(0);
            return;
        }

        if (e.getClickedInventory().getName().equals(rpgcore.getOsManager().osGraczeGUI(playerUUID).getName())) {
            final int slot = e.getSlot();
            e.setCancelled(true);
            if (e.getCurrentItem().getType() == Material.AIR) {
                return;
            }

            String[] accepted = rpgcore.getPlayerManager().getOsLudzieAccept(playerUUID).split(",");

            if (accepted[slot].equalsIgnoreCase("true")) {
                return;
            }

            if (slot != 0 && accepted[slot-1].equalsIgnoreCase("false")) {
                return;
            }

            final int killedPlayers = rpgcore.getPlayerManager().getPlayerOsLudzie(playerUUID);

            if (killedPlayers < rpgcore.getOsManager().getRequiredForOsLudzie().get(slot+1)) {
                return;
            }


            accepted[slot] = "true";
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
            Bukkit.getServer().broadcastMessage(Utils.format(Utils.SERVERNAME + "&3Gracz &f" + player.getName() + " &3odebral osiagniecie &f" + Utils.removeColor(e.getCurrentItem().getItemMeta().getDisplayName())));
            noweOsLudzieAccepted.setLength(0);
            return;
        }

        if (e.getClickedInventory().getName().equals(rpgcore.getOsManager().osSakwyGUI(playerUUID).getName())) {
            final int slot = e.getSlot();
            e.setCancelled(true);
            if (e.getCurrentItem().getType() == Material.AIR) {
                return;
            }

            String[] accepted = rpgcore.getPlayerManager().getOsSakwyAccept(playerUUID).split(",");

            if (accepted[slot].equalsIgnoreCase("true")) {
                return;
            }

            if (slot != 0 && accepted[slot-1].equalsIgnoreCase("false")) {
                return;
            }

            final int dropped = rpgcore.getPlayerManager().getPlayerOsSakwy(playerUUID);

            if (dropped < rpgcore.getOsManager().getRequiredForOsSakwy().get(slot+1)) {
                return;
            }

            accepted[slot] = "true";
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
            Bukkit.getServer().broadcastMessage(Utils.format(Utils.SERVERNAME + "&3Gracz &f" + player.getName() + " &3odebral osiagniecie &f" + Utils.removeColor(e.getCurrentItem().getItemMeta().getDisplayName())));
            noweOsSakwyAccepted.setLength(0);
            return;
        }

        if (e.getClickedInventory().getName().equals(rpgcore.getOsManager().osNiesyGUI(playerUUID).getName())) {
            final int slot = e.getSlot();
            e.setCancelled(true);
            if (e.getCurrentItem().getType() == Material.AIR) {
                return;
            }

            String[] accepted = rpgcore.getPlayerManager().getOsNiesyAccept(playerUUID).split(",");

            if (accepted[slot].equalsIgnoreCase("true")) {
                return;
            }

            if (slot != 0 && accepted[slot-1].equalsIgnoreCase("false")) {
                return;
            }

            final int dropped = rpgcore.getPlayerManager().getPlayerOsNiesy(playerUUID);

            if (dropped < rpgcore.getOsManager().getRequiredForOsNiesy().get(slot+1)) {
                return;
            }

            accepted[slot] = "true";
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
            Bukkit.getServer().broadcastMessage(Utils.format(Utils.SERVERNAME + "&3Gracz &f" + player.getName() + " &3odebral osiagniecie &f" + Utils.removeColor(e.getCurrentItem().getItemMeta().getDisplayName())));
            noweOsNiesyAccepted.setLength(0);
            return;
        }

        if (e.getClickedInventory().getName().equals(rpgcore.getOsManager().osRybakGUI(playerUUID).getName())) {
            final int slot = e.getSlot();
            e.setCancelled(true);
            if (e.getCurrentItem().getType() == Material.AIR) {
                return;
            }

            String[] accepted = rpgcore.getPlayerManager().getOsRybakAccept(playerUUID).split(",");

            if (accepted[slot].equalsIgnoreCase("true")) {
                return;
            }

            if (slot != 0 && accepted[slot-1].equalsIgnoreCase("false")) {
                return;
            }

            final int dropped = rpgcore.getPlayerManager().getPlayerOsRybak(playerUUID);

            if (dropped < rpgcore.getOsManager().getRequiredForOsRybak().get(slot+1)) {
                return;
            }

            accepted[slot] = "true";
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
            Bukkit.getServer().broadcastMessage(Utils.format(Utils.SERVERNAME + "&3Gracz &f" + player.getName() + " &3odebral osiagniecie &f" + Utils.removeColor(e.getCurrentItem().getItemMeta().getDisplayName())));
            noweOsRybakAccepted.setLength(0);
            return;
        }

        if (e.getClickedInventory().getName().equals(rpgcore.getOsManager().osDrwalGUI(playerUUID).getName())) {
            final int slot = e.getSlot();
            e.setCancelled(true);
            if (e.getCurrentItem().getType() == Material.AIR) {
                return;
            }

            String[] accepted = rpgcore.getPlayerManager().getOsDrwalAccept(playerUUID).split(",");

            if (accepted[slot].equalsIgnoreCase("true")) {
                return;
            }

            if (slot != 0 && accepted[slot-1].equalsIgnoreCase("false")) {
                return;
            }

            final int dropped = rpgcore.getPlayerManager().getPlayerOsDrwal(playerUUID);

            if (dropped < rpgcore.getOsManager().getRequiredForOsDrwal().get(slot+1)) {
                return;
            }

            accepted[slot] = "true";
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
            Bukkit.getServer().broadcastMessage(Utils.format(Utils.SERVERNAME + "&3Gracz &f" + player.getName() + " &3odebral osiagniecie &f" + Utils.removeColor(e.getCurrentItem().getItemMeta().getDisplayName())));
            noweOsDrwalAccepted.setLength(0);
            return;
        }

        if (e.getClickedInventory().getName().equals(rpgcore.getOsManager().osGornikGUI(playerUUID).getName())) {
            final int slot = e.getSlot();
            e.setCancelled(true);
            if (e.getCurrentItem().getType() == Material.AIR) {
                return;
            }

            String[] accepted = rpgcore.getPlayerManager().getOsGornikAccept(playerUUID).split(",");

            if (accepted[slot].equalsIgnoreCase("true")) {
                return;
            }

            if (slot != 0 && accepted[slot-1].equalsIgnoreCase("false")) {
                return;
            }

            final int dropped = rpgcore.getPlayerManager().getPlayerOsGornik(playerUUID);

            if (dropped < rpgcore.getOsManager().getRequiredForOsGornik().get(slot+1)) {
                return;
            }

            accepted[slot] = "true";
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
            Bukkit.getServer().broadcastMessage(Utils.format(Utils.SERVERNAME + "&3Gracz &f" + player.getName() + " &3odebral osiagniecie &f" + Utils.removeColor(e.getCurrentItem().getItemMeta().getDisplayName())));
            noweOsGornikAccepted.setLength(0);
        }

        if (e.getClickedInventory().getName().equals(Utils.format("&4&lEQ GUI"))) {
            e.setCancelled(true);
            if (e.getCurrentItem().getType() == Material.AIR) {
                e.getWhoClicked().closeInventory();
                return;
            }
            final int clickedSlot = e.getSlot();
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
                        if (playerGroup.equals("Gracz") || playerGroup.equals("Vip") || playerGroup.equals("Svip") || playerGroup.equals("ELITA") || playerGroup.equals("Budowniczy")) {
                            firstPartOfMessage = new TextComponent(Utils.format("&7" + Utils.removeColor(msg.get(0))));
                        } else {
                            firstPartOfMessage = new TextComponent(Utils.format("&7" + msg.get(0)));
                        }
                        beforeMessage.addExtra(firstPartOfMessage);
                        beforeMessage.addExtra(item);
                        if (playerGroup.equals("Gracz") || playerGroup.equals("Vip") || playerGroup.equals("Svip") || playerGroup.equals("ELITA") || playerGroup.equals("Budowniczy")) {
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
                        if (playerGroup.equals("Gracz") || playerGroup.equals("Vip") || playerGroup.equals("Svip") || playerGroup.equals("ELITA") || playerGroup.equals("Budowniczy")) {
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
                        if (playerGroup.equals("Gracz") || playerGroup.equals("Vip") || playerGroup.equals("Svip") || playerGroup.equals("ELITA") || playerGroup.equals("Budowniczy")) {
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

        if (e.getClickedInventory().getName().contains("Historia kar gracza ")) {
            e.setCancelled(true);
            return;
        }

        if (e.getClickedInventory().getName().contains("Akcesoria gracza ")) {
            ItemStack itemToGiveBack;

            e.setCancelled(true);

            if (e.getCurrentItem().getType() == Material.BARRIER) {
                return;
            }

            if (e.getSlot() == 10) {
                itemToGiveBack = e.getCurrentItem();
                e.getWhoClicked().getInventory().addItem(itemToGiveBack);
                rpgcore.getPlayerManager().updatePlayerDef(playerUUID, rpgcore.getPlayerManager().getPlayerDef(playerUUID) - rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToGiveBack, "Obrona"));
                rpgcore.getPlayerManager().updatePlayerBlok(playerUUID, rpgcore.getPlayerManager().getPlayerBlok(playerUUID) - rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToGiveBack, "Blok Ciosu"));
                rpgcore.getPlayerManager().updatePlayerDamage(playerUUID, rpgcore.getPlayerManager().getPlayerDamage(playerUUID) - rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToGiveBack, "Obrazenia"));
                e.getInventory().setItem(e.getSlot(), rpgcore.getAkcesoriaManager().noAkcesoriaItem("Tarczy"));
                return;
            }

            if (e.getSlot() == 11) {
                itemToGiveBack = e.getCurrentItem();
                e.getWhoClicked().getInventory().addItem(itemToGiveBack);
                rpgcore.getPlayerManager().updatePlayerPrzeszywka(playerUUID, rpgcore.getPlayerManager().getPlayerPrzeszywka(playerUUID) - rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToGiveBack, "Przeszycie Bloku"));
                rpgcore.getPlayerManager().updatePlayerDamage(playerUUID, rpgcore.getPlayerManager().getPlayerDamage(playerUUID) - rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToGiveBack, "Obrazenia"));
                System.out.println("Naszyjnik dmg - " + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToGiveBack, "Obrazenia"));
                e.getInventory().setItem(e.getSlot(), rpgcore.getAkcesoriaManager().noAkcesoriaItem("Naszyjnika"));
                return;
            }

            if (e.getSlot() == 12) {
                itemToGiveBack = e.getCurrentItem();
                e.getWhoClicked().getInventory().addItem(itemToGiveBack);
                rpgcore.getPlayerManager().updatePlayerKryt(playerUUID, rpgcore.getPlayerManager().getPlayerKryt(playerUUID) - rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToGiveBack, "Cios Krytyczny"));
                rpgcore.getPlayerManager().updatePlayerSrednie(playerUUID, rpgcore.getPlayerManager().getPlayerSrednie(playerUUID) - rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToGiveBack, "Srednie Obrazenia"));
                e.getInventory().setItem(e.getSlot(), rpgcore.getAkcesoriaManager().noAkcesoriaItem("Bransolety"));
                return;
            }

            if (e.getSlot() == 13) {
                itemToGiveBack = e.getCurrentItem();
                e.getWhoClicked().getInventory().addItem(itemToGiveBack);
                rpgcore.getPlayerManager().updatePlayerHP(playerUUID, rpgcore.getPlayerManager().getPlayerHP(playerUUID) - rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToGiveBack, "Dodatkowe HP"));
                rpgcore.getPlayerManager().updatePlayerSilnyNaLudzi(playerUUID, rpgcore.getPlayerManager().getPlayerSilnyNaLudzi(playerUUID) - rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToGiveBack, "Silny przeciwko Ludziom"));
                player.setMaxHealth(player.getMaxHealth() - (double) rpgcore.getAkcesoriaManager().getAkcesoriaBonus(playerUUID, 13, "Dodatkowe HP") * 2);
                e.getInventory().setItem(e.getSlot(), rpgcore.getAkcesoriaManager().noAkcesoriaItem("Kolczykow"));
                return;
            }

            if (e.getSlot() == 14) {
                itemToGiveBack = e.getCurrentItem();
                e.getWhoClicked().getInventory().addItem(itemToGiveBack);
                rpgcore.getPlayerManager().updatePlayerBlok(playerUUID, rpgcore.getPlayerManager().getPlayerBlok(playerUUID) - rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToGiveBack, "Blok Ciosu"));
                rpgcore.getPlayerManager().updatePlayerHP(playerUUID, rpgcore.getPlayerManager().getPlayerHP(playerUUID) - rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToGiveBack, "Dodatkowe HP"));
                player.setMaxHealth(player.getMaxHealth() - (double) rpgcore.getAkcesoriaManager().getAkcesoriaBonus(playerUUID, 14, "Dodatkowe HP") * 2);
                System.out.println("Pierek blok -" + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToGiveBack, "Blok Ciosu"));
                e.getInventory().setItem(e.getSlot(), rpgcore.getAkcesoriaManager().noAkcesoriaItem("Pierscienia"));
                return;
            }

            if (e.getSlot() == 15) {
                itemToGiveBack = e.getCurrentItem();
                e.getWhoClicked().getInventory().addItem(itemToGiveBack);
                rpgcore.getPlayerManager().updatePlayerDef(playerUUID, rpgcore.getPlayerManager().getPlayerDef(playerUUID) - rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToGiveBack, "Obrona"));
                rpgcore.getPlayerManager().updatePlayerBlok(playerUUID, rpgcore.getPlayerManager().getPlayerBlok(playerUUID) - rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToGiveBack, "Blok Ciosu"));
                rpgcore.getPlayerManager().updatePlayerMinusSrednie(playerUUID, rpgcore.getPlayerManager().getPlayerMinusSrednie(playerUUID) - rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToGiveBack, "Srednie Obrazenia"));
                System.out.println("energia blok - " + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToGiveBack, "Blok Ciosu"));
                e.getInventory().setItem(e.getSlot(), rpgcore.getAkcesoriaManager().noAkcesoriaItem("Energii"));
                return;
            }

            if (e.getSlot() == 16) {
                itemToGiveBack = e.getCurrentItem();
                e.getWhoClicked().getInventory().addItem(itemToGiveBack);
                rpgcore.getPlayerManager().updatePlayerDamage(playerUUID, rpgcore.getPlayerManager().getPlayerDamage(playerUUID) - rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToGiveBack, "Obrazenia"));
                rpgcore.getPlayerManager().updatePlayerSilnyNaLudzi(playerUUID, rpgcore.getPlayerManager().getPlayerSilnyNaLudzi(playerUUID) - rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToGiveBack, "Silny przeciwko Ludziom"));
                rpgcore.getPlayerManager().updatePlayerMinusDef(playerUUID, rpgcore.getPlayerManager().getPlayerMinusDef(playerUUID) - rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToGiveBack, "Obrona"));
                System.out.println("Zegarek dmg - " + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToGiveBack, "Obrazenia"));
                e.getInventory().setItem(e.getSlot(), rpgcore.getAkcesoriaManager().noAkcesoriaItem("Zegarka"));
                return;
            }
            return;
        }
        // POMOC 1
        if (e.getClickedInventory().getName().equals(rpgcore.getPomocManager().pomocGUIMAIN().getName())) {
            e.setCancelled(true);
            if (e.getCurrentItem().getType() == Material.BOOK) {
                player.openInventory(rpgcore.getPomocManager().pomocGUIREGULAMINTARYFIKATOR());
                return;
            }
            if (e.getSlot() == 13) {
                player.openInventory(rpgcore.getPomocManager().pomocGUIPODSTAWOWEKOMENDY());
                return;
            }
            if (e.getCurrentItem().getType() == Material.FIREWORK_CHARGE) {
                player.closeInventory();
                player.sendMessage(Utils.SERVERNAME + Utils.format(" &6Poradnik:"));
                player.sendMessage("1. Nie bądź kurwą...");
                player.sendMessage("2. Odpierdalasz = wypierdalasz");
                return;
            }
            return;
        }
        if (e.getClickedInventory().getName().equals(rpgcore.getPomocManager().pomocGUIPODSTAWOWEKOMENDY().getName())) {
            e.setCancelled(true);
            return;
        }
        // POMOC 3
        if (e.getClickedInventory().getName().equals(rpgcore.getPomocManager().pomocGUIREGULAMINTARYFIKATOR().getName())) {
            if (e.getSlot() == 2) {
                player.closeInventory();
                player.sendMessage(Utils.SERVERNAME + Utils.format(" &6Link do regulaminu:"));
                player.sendMessage(Utils.format("&ewww.twojstarywzoo.pl"));
            }
            if (e.getSlot() == 6) {
                player.closeInventory();
                player.sendMessage(Utils.SERVERNAME + Utils.format(" &6Link do taryfikatora:"));
                player.sendMessage(Utils.format("&ewww.twojstarywzoo.pl"));
            }
            e.setCancelled(true);
            return;
        }

        // DUSZOLOG
        if (e.getClickedInventory().getName().equals(rpgcore.getDuszologNPC().duszologMAIN().getName())) {
            if (e.getSlot() == 10) {
                rpgcore.getDuszologNPC().craftowanieDUSZ(player);
                player.closeInventory();
            }
            e.setCancelled(true);
            return;
        }
        //                      TRADE                   \\



        if (e.getClickedInventory().getName().contains("Wymiana ")) {
            final Player firstViewer = (Player) e.getClickedInventory().getViewers().get(1);
            final Player secViewer = (Player) e.getClickedInventory().getViewers().get(0);
            final UUID secViewerUUID = secViewer.getUniqueId();
            final UUID firstViewerUUID = firstViewer.getUniqueId();
            if (e.getClickedInventory().getName().equals(rpgcore.getTradeManager().createTradeGUI(firstViewerUUID, secViewerUUID).getName())) {
                final int i = e.getSlot();

                if (rpgcore.getTradeManager().isTradeAccepted(e.getClickedInventory())) {
                    e.setCancelled(true);
                    return;
                }

                if (!((i > 9 && i < 13) || (i > 13 && i< 17) || (i > 18 && i < 22) || (i > 22 && i < 26) || (i > 27 && i < 31) || (i > 31 && i < 35) || (i > 36 && i < 40) ||
                        (i > 40 && i < 44))) {
                    e.setCancelled(true);
                    if (i == 48) {
                        if (e.getWhoClicked().equals(firstViewer)) {
                            if (e.getClickedInventory().getItem(48).getItemMeta().getDisplayName().equals(rpgcore.getTradeManager().getNoAcceptItem(firstViewerUUID).getItemMeta().getDisplayName())) {
                                e.getClickedInventory().setItem(48, rpgcore.getTradeManager().getAcceptItem(firstViewerUUID));
                                if (e.getClickedInventory().getItem(48).getItemMeta().getDisplayName().equals(rpgcore.getTradeManager().getAcceptItem(firstViewerUUID).getItemMeta().getDisplayName())
                                        && e.getClickedInventory().getItem(50).getItemMeta().getDisplayName().equals(rpgcore.getTradeManager().getAcceptItem(secViewerUUID).getItemMeta().getDisplayName())) {
                                    e.setCancelled(true);
                                    rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> rpgcore.getTradeManager().tradeAccepted(e.getClickedInventory(), firstViewer, secViewer), 1L);
                                    return;
                                }
                                return;
                            }
                            e.getClickedInventory().setItem(48, rpgcore.getTradeManager().getNoAcceptItem(firstViewerUUID));
                            return;
                        }
                    }
                    if (i == 50) {
                        if (e.getWhoClicked().equals(secViewer)) {
                            if (e.getClickedInventory().getItem(50).getItemMeta().getDisplayName().equals(rpgcore.getTradeManager().getNoAcceptItem(secViewerUUID).getItemMeta().getDisplayName())) {
                                e.getClickedInventory().setItem(50, rpgcore.getTradeManager().getAcceptItem(secViewerUUID));
                                if (e.getClickedInventory().getItem(48).getItemMeta().getDisplayName().equals(rpgcore.getTradeManager().getAcceptItem(firstViewerUUID).getItemMeta().getDisplayName())
                                        && e.getClickedInventory().getItem(50).getItemMeta().getDisplayName().equals(rpgcore.getTradeManager().getAcceptItem(secViewerUUID).getItemMeta().getDisplayName())) {
                                    e.setCancelled(true);
                                    rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> rpgcore.getTradeManager().tradeAccepted(e.getClickedInventory(), firstViewer, secViewer), 1L);
                                    return;
                                }
                                return;
                            }
                            e.getClickedInventory().setItem(50, rpgcore.getTradeManager().getNoAcceptItem(secViewerUUID));
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

                    if (e.getClickedInventory().getItem(48).getItemMeta().getDisplayName().equals(rpgcore.getTradeManager().getAcceptItem(firstViewerUUID).getItemMeta().getDisplayName())) {
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
                    if (e.getClickedInventory().getItem(50).getItemMeta().getDisplayName().equals(rpgcore.getTradeManager().getAcceptItem(secViewerUUID).getItemMeta().getDisplayName())) {
                        e.setCancelled(true);
                        return;
                    }
                    return;
                }
            }
            return;
        }

    }
}
