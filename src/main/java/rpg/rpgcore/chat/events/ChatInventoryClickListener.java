package rpg.rpgcore.chat.events;

import net.md_5.bungee.api.chat.ClickEvent;
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
import rpg.rpgcore.bao.BaoUser;
import rpg.rpgcore.chat.ChatUser;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.DoubleUtils;
import rpg.rpgcore.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ChatInventoryClickListener implements Listener {
    private final RPGCORE rpgcore;

    public ChatInventoryClickListener(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClick(final InventoryClickEvent e) {
        if (e.getClickedInventory() == null || e.getInventory() == null) {
            return;
        }

        final Player player = (Player) e.getWhoClicked();
        final UUID uuid = player.getUniqueId();
        final String title = Utils.removeColor(e.getClickedInventory().getTitle());
        final int slot = e.getSlot();
        final ItemStack item = e.getCurrentItem();

        if (title.equals("Gui Interakcji z Chatem")) {
            e.setCancelled(true);
            if (item.getType() == Material.AIR) {
                return;
            }
            final String message = rpgcore.getChatManager().getMessageWithEQ(uuid);
            final TextComponent formatPrzed = rpgcore.getChatManager().formmatPrzed(player);
            List<String> msg = new ArrayList<>(Arrays.asList(message.split("\\[eq]")));
            if (message.contains("[i]")) {
                msg = new ArrayList<>(Arrays.asList(message.split("\\[i]")));
            } else if (message.contains("[item]")) {
                msg = new ArrayList<>(Arrays.asList(message.split("\\[item]")));
            }

            StringBuilder finalMessage;
            String color = "&f";
            if (message.contains("&")) {
                color = message.substring(message.lastIndexOf("&"), message.lastIndexOf("&") + 2);
            }
            final boolean isHighStaff = rpgcore.getUserManager().find(uuid).getRankUser().isHighStaff();
            switch (slot) {
                case 0:
                    // POKAZYWANIE ITEMU NA CHAT
                    if (player.getItemInHand() == null) {
                        player.sendMessage(Utils.format(Utils.SERVERNAME + "&cMusisz miec cos w rece zeby tego uzyc"));
                        player.closeInventory();
                        return;
                    }

                    String itemName = player.getItemInHand().getType().name();

                    if (player.getItemInHand().getItemMeta().hasDisplayName()) {
                        itemName = player.getItemInHand().getItemMeta().getDisplayName();
                    }

                    final TextComponent itemComponent = new TextComponent(Utils.format("&8[&6x" + player.getItemInHand().getAmount() + " " + itemName + "&8]"));
                    itemComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new ComponentBuilder(CraftItemStack.asNMSCopy(player.getItemInHand()).save(new NBTTagCompound()).toString()).create()));
                    rpgcore.getShowcaseItemManager().addShowcaseItem(player.getName(), player.getItemInHand().clone());
                    if (item.getItemMeta().hasDisplayName()) {
                        itemComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/showcaseitem " + player.getName() + " " + Utils.removeColor(player.getItemInHand().getItemMeta().getDisplayName().replace(" ", "_"))));
                    } else {
                        itemComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/showcaseitem " + player.getName() + " " + Utils.removeColor(player.getItemInHand().getType().name().replace(" ", "_"))));
                    }

                    if (msg.isEmpty()) {
                        formatPrzed.addExtra(itemComponent);
                    } else {
                        if (!isHighStaff) {
                            formatPrzed.addExtra(Utils.format(" &f" + Utils.removeColor(msg.get(0))));
                        } else {
                            formatPrzed.addExtra(Utils.format(" " + color + msg.get(0)));
                        }
                        formatPrzed.addExtra(itemComponent);
                        for (int i = 1; i < msg.size(); i++) {
                            if (!isHighStaff) {
                                formatPrzed.addExtra(Utils.format(" &f" + Utils.removeColor(msg.get(i))));
                            } else {
                                formatPrzed.addExtra(Utils.format(" " + color + msg.get(i)));
                            }
                        }
                    }


                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.spigot().sendMessage(formatPrzed);
                    }
                    break;
                case 1:
                    // POKAZYWANIE SETA NA CHAT
                    if (player.getInventory().getHelmet() == null || player.getInventory().getChestplate() == null || player.getInventory().getLeggings() == null || player.getInventory().getBoots() == null) {
                        player.sendMessage(Utils.format(Utils.SERVERNAME + "&cMusisz miec zalozone wszystkie czesci zbroji zeby tego uzyc!"));
                        player.closeInventory();
                        return;
                    }

                    final TextComponent zbroja = rpgcore.getChatManager().getPlayerArmor(player);
                    finalMessage = new StringBuilder();
                    if (msg.isEmpty()) {
                        formatPrzed.addExtra(zbroja);
                    } else {
                        if (!isHighStaff) {
                            formatPrzed.addExtra(Utils.format("&f" + Utils.removeColor(msg.get(0))));
                            formatPrzed.addExtra(zbroja);
                            for (int i = 1; i < msg.size(); i++) {
                                finalMessage.append(" ").append(msg.get(i));
                            }
                        } else {
                            formatPrzed.addExtra(msg.get(0));
                            for (int i = 1; i < msg.size(); i++) {
                                finalMessage.append(" ").append(color).append(msg.get(i));
                            }
                        }
                    }
                    formatPrzed.addExtra(Utils.format(finalMessage.toString()));
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.spigot().sendMessage(formatPrzed);
                    }
                    break;
                case 2:
                    // POKAZYWANIE BAO NA CHAT

                    if (rpgcore.getUserManager().find(uuid).getLvl() < 74) {
                        player.sendMessage(Utils.format(Utils.SERVERNAME + "&cMusisz posiadac minimum &c75 &7poziom, zeby pokazac bao na chacie"));
                        player.closeInventory();
                        break;
                    }

                    if (rpgcore.getBaoManager().isNotRolled(uuid)) {
                        player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie masz jeszcze zrobionego bao!"));
                        player.closeInventory();
                        break;
                    }
                    final BaoUser user = rpgcore.getBaoManager().find(uuid).getBaoUser();
                    final TextComponent stolMagii = new TextComponent("§8[§bStol Magii§8]");
                    final TextComponent text = new TextComponent("§7Stol Magii gracza §c" + player.getName() + "§7:\n§6" + user.getBonus1() + ": §c" + user.getValue1() + "% §8\n§6" + user.getBonus2() + ": §c" + user.getValue2() + "% §8\n");
                    TextComponent text2;
                    TextComponent text3;
                    TextComponent text4;


                    if (user.getBonus3().equalsIgnoreCase("dodatkowe obrazenia")) {
                        text2 = new TextComponent("§6" + user.getBonus3() + ": §c" + user.getValue3() + " DMG §8\n");
                    } else {
                        text2 = new TextComponent("§6" + user.getBonus3() + ": §c" + user.getValue3() + "% §8\n");
                    }
                    if (user.getBonus4().equalsIgnoreCase("predkosc ruchu") || user.getBonus4().equalsIgnoreCase("szczescie")) {
                        text3 = new TextComponent("§6" + user.getBonus4() + ": §c" + user.getValue4() + " §8\n");
                    } else {
                        text3 = new TextComponent("§6" + user.getBonus4() + ": §c" + user.getValue4() + "% §8\n");
                    }
                    if (user.getBonus5().equalsIgnoreCase("dodatkowe hp")) {
                        text4 = new TextComponent("§6" + user.getBonus5() + ": §c" + user.getValue5() + " HP");
                    } else {
                        text4 = new TextComponent("§6" + user.getBonus5() + ": §c" + user.getValue5() + "%");
                    }
                    text.addExtra(text2);
                    text.addExtra(text3);
                    text.addExtra(text4);
                    stolMagii.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[]{text}));


                    if (msg.isEmpty()) {
                        formatPrzed.addExtra(stolMagii);
                    } else {
                        if (!isHighStaff) {
                            formatPrzed.addExtra(Utils.format(" &f" + Utils.removeColor(msg.get(0))));
                        } else {
                            formatPrzed.addExtra(Utils.format(" " + color + msg.get(0)));
                        }
                        formatPrzed.addExtra(stolMagii);
                        for (int i = 1; i < msg.size(); i++) {
                            if (!isHighStaff) {
                                formatPrzed.addExtra(Utils.format(" &f" + Utils.removeColor(msg.get(i))));
                            } else {
                                formatPrzed.addExtra(Utils.format(" " + color + msg.get(i)));
                            }
                        }
                    }


                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.spigot().sendMessage(formatPrzed);
                    }
                    break;
                case 3:
                    // POOKAZYWANIE DOSWIADCZENIA NA CHAT

                    final int lvlGracza = rpgcore.getUserManager().find(uuid).getLvl();
                    final double expGracza = rpgcore.getUserManager().find(uuid).getExp();
                    final double expNaNextLvlGracza = rpgcore.getLvlManager().getExpForLvl(lvlGracza + 1);

                    if (msg.isEmpty()) {
                        finalMessage = new StringBuilder(Utils.format(" &8[&f" + Utils.spaceNumber(String.format("%.2f", expGracza)) + " &bexp &7/&f " + Utils.spaceNumber(String.format("%.2f", expNaNextLvlGracza)) + " &bexp" + " &7(&b" + ((expGracza / expNaNextLvlGracza) * 100 == 0 ? Utils.procentFormat.format((expGracza / expNaNextLvlGracza) * 100) : "0.0") + "%&7)&8]"));
                    } else {
                        if (!isHighStaff) {
                            finalMessage = new StringBuilder("&f" + Utils.removeColor(msg.get(0)) + Utils.format(" &8[&f" + Utils.spaceNumber(String.format("%.2f", expGracza)) + " &bexp &7/&f " + Utils.spaceNumber(String.format("%.2f", expNaNextLvlGracza)) + " &bexp" + " &7(&b" + ((expGracza / expNaNextLvlGracza) * 100 == 0 ? Utils.procentFormat.format((expGracza / expNaNextLvlGracza) * 100) : "0.0") + "%&7)&8]"));
                            for (int i = 1; i < msg.size(); i++) {
                                finalMessage.append(" &f").append(msg.get(i));
                            }
                        } else {
                            finalMessage = new StringBuilder(msg.get(0) + Utils.format(" &8[&f " + Utils.spaceNumber(String.format("%.2f", expGracza)) + " &bexp &7/&f " + Utils.spaceNumber(String.format("%.2f", expNaNextLvlGracza)) + " &bexp" + " &7(&b" + ((expGracza / expNaNextLvlGracza) * 100 == 0 ? Utils.procentFormat.format((expGracza / expNaNextLvlGracza) * 100) : "0.0") + "%&7)&8]"));
                            for (int i = 1; i < msg.size(); i++) {
                                finalMessage.append(color).append(msg.get(i));
                            }
                        }
                    }
                    formatPrzed.addExtra(finalMessage.toString());
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.spigot().sendMessage(formatPrzed);
                    }

                    break;
                case 4:
                    // POKAZYWANIE KASY NA CHAT
                    if (msg.isEmpty()) {
                        finalMessage = new StringBuilder(Utils.format(" &8[&2Kasa: &a" + Utils.spaceNumber(String.format("%.2f", rpgcore.getUserManager().find(uuid).getKasa()) + " &2$&8]")));
                    } else {
                        if (!isHighStaff) {
                            finalMessage = new StringBuilder("&f" + Utils.removeColor(msg.get(0)) + Utils.format(" &8[&2Kasa: &a" + Utils.spaceNumber(String.format("%.2f", rpgcore.getUserManager().find(uuid).getKasa()) + " &2$&8]")));
                            for (int i = 1; i < msg.size(); i++) {
                                finalMessage.append(" &f").append(msg.get(i));
                            }
                        } else {
                            finalMessage = new StringBuilder(msg.get(0) + Utils.format(" &8[&2Kasa: &a" + Utils.spaceNumber(String.format("%.2f", rpgcore.getUserManager().find(uuid).getKasa()) + " &2$&8]")));
                            for (int i = 1; i < msg.size(); i++) {
                                finalMessage.append(color).append(msg.get(i));
                            }
                        }
                    }

                    formatPrzed.addExtra(finalMessage.toString());
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.spigot().sendMessage(formatPrzed);
                    }
                    break;
                case 5:
                    // POKAZYWANIE CZASU NA CHAT
                    if (msg.isEmpty()) {
                        finalMessage = new StringBuilder(Utils.format(" &8[&6Spedzony Czas: &7" + Utils.durationToString(rpgcore.getOsManager().find(uuid).getCzasProgress(), true) + "&8]"));
                    } else {
                        if (!isHighStaff) {
                            finalMessage = new StringBuilder("&f" + Utils.removeColor(msg.get(0)) + Utils.format(" &8[&6Spedzony Czas: &7" + Utils.durationToString(rpgcore.getOsManager().find(uuid).getCzasProgress(), true) + "&8]"));
                            for (int i = 1; i < msg.size(); i++) {
                                finalMessage.append("&f").append(msg.get(i));
                            }
                        } else {
                            finalMessage = new StringBuilder(msg.get(0) + Utils.format(" &8[&6Spedzony Czas: &7" + Utils.durationToString(rpgcore.getOsManager().find(uuid).getCzasProgress(), true) + "&8]"));
                            for (int i = 1; i < msg.size(); i++) {
                                finalMessage.append(color).append(msg.get(i));
                            }
                        }
                    }

                    formatPrzed.addExtra(finalMessage.toString());
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.spigot().sendMessage(formatPrzed);
                    }
                    break;
                case 6:
                    final User mainUser = rpgcore.getUserManager().find(uuid);
                    finalMessage = new StringBuilder();
                    if (msg.isEmpty()) finalMessage.append(Utils.format(" &8[&4Krytyk: &c" + DoubleUtils.round(mainUser.getKrytyk(), 2) + " dmg&8]"));
                    else {
                        if (!isHighStaff) {
                            finalMessage = new StringBuilder("&f" + Utils.removeColor(msg.get(0)) + Utils.format(" &8[&4Krytyk: &c" + DoubleUtils.round(mainUser.getKrytyk(), 2) + " dmg&8]"));
                            for (int i = 1; i < msg.size(); i++) {
                                finalMessage.append("&f").append(msg.get(i));
                            }
                        } else {
                            finalMessage = new StringBuilder(msg.get(0) + Utils.format(" &8[&4Krytyk: &c" + DoubleUtils.round(mainUser.getKrytyk(), 2) + " dmg&8]"));
                            for (int i = 1; i < msg.size(); i++) {
                                finalMessage.append(color).append(msg.get(i));
                            }
                        }
                    }
                    formatPrzed.addExtra(finalMessage.toString());
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.spigot().sendMessage(formatPrzed);
                    }
                    break;
            }
            if (message.contains("@")) {
                rpgcore.getChatManager().pingPlayer(player, message);
            }
            rpgcore.getChatManager().updateMessageWithEQ(uuid, "");
            player.closeInventory();
        }

        if (title.equals("Chat Panel")) {
            e.setCancelled(true);
            final ChatUser user = rpgcore.getChatManager().find(uuid);
            switch (slot) {
                case 10:
                    if (user.isItemDropEnabled()) {
                        user.setItemDropEnabled(false);
                        rpgcore.getChatManager().openChatPanel(player);
                        return;
                    }
                    user.setItemDropEnabled(true);
                    rpgcore.getChatManager().openChatPanel(player);
                    return;
                case 13:
                    if (user.isPingsEnabled()) {
                        user.setPingsEnabled(false);
                        rpgcore.getChatManager().openChatPanel(player);
                        return;
                    }
                    user.setPingsEnabled(true);
                    rpgcore.getChatManager().openChatPanel(player);
                    return;
                case 12:
                    if (user.isNiesDropEnabled()) {
                        user.setNiesDropEnabled(false);
                        rpgcore.getChatManager().openChatPanel(player);
                        return;
                    }
                    user.setNiesDropEnabled(true);
                    rpgcore.getChatManager().openChatPanel(player);
                    return;
                case 11:
                    if (user.isChestDropEnabled()) {
                        user.setChestDropEnabled(false);
                        rpgcore.getChatManager().openChatPanel(player);
                        return;
                    }

                    user.setChestDropEnabled(true);
                    rpgcore.getChatManager().openChatPanel(player);
                    return;
                case 14:
                    if (user.isMsgEnabled()) {
                        user.setMsgEnabled(false);
                        rpgcore.getChatManager().openChatPanel(player);
                        return;
                    }

                    user.setMsgEnabled(true);
                    rpgcore.getChatManager().openChatPanel(player);
                    return;
                case 15:
                    if (user.isJoinMessageEnabled()) {
                        user.setJoinMessageEnabled(false);
                        rpgcore.getChatManager().openChatPanel(player);
                        return;
                    }
                    user.setJoinMessageEnabled(true);
                    rpgcore.getChatManager().openChatPanel(player);
                    return;
                case 16:
                    if (user.isQuitMessageEnabled()) {
                        user.setQuitMessageEnabled(false);
                        rpgcore.getChatManager().openChatPanel(player);
                        return;
                    }

                    user.setQuitMessageEnabled(true);
                    rpgcore.getChatManager().openChatPanel(player);
                    return;
                case 22:
                    if (user.isDmgHologramsVisable()) {
                        user.setDmgHologramsVisable(false);
                        rpgcore.getChatManager().openChatPanel(player);
                        return;
                    }

                    user.setDmgHologramsVisable(true);
                    rpgcore.getChatManager().openChatPanel(player);
                    return;
            }
        }
    }
}
