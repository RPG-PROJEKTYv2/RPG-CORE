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
import rpg.rpgcore.bao.objects.BaoUser;
import rpg.rpgcore.bossy.effects.PrzekletyCzarnoksieznik.PrzekletyCzarnoksieznikUser;
import rpg.rpgcore.chat.objects.ChatUser;
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
                    if (item.hasItemMeta()) {
                        if (item.getItemMeta().hasDisplayName()) {
                            itemComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/showcaseitem " + player.getName() + " " + Utils.removeColor(player.getItemInHand().getItemMeta().getDisplayName().replace(" ", "_"))));
                        } else {
                            itemComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/showcaseitem " + player.getName() + " " + Utils.removeColor(player.getItemInHand().getType().name().replace(" ", "_"))));
                        }
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

                    if (rpgcore.getUserManager().find(uuid).getLvl() < 70) {
                        player.sendMessage(Utils.format(Utils.SERVERNAME + "&cMusisz posiadac minimum &c70 &7poziom, zeby pokazac bao na chacie"));
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
                        finalMessage = new StringBuilder(Utils.format(" &8[&f" + Utils.spaceNumber(Utils.shorterNumber(expGracza)) + " &bexp &7/&f " + Utils.spaceNumber(Utils.shorterNumber(expNaNextLvlGracza)) + " &bexp" + " &7(&b" + DoubleUtils.round((expGracza / expNaNextLvlGracza) * 100, 2) + "%&7)&8]"));
                    } else {
                        if (!isHighStaff) {
                            finalMessage = new StringBuilder("&f" + Utils.removeColor(msg.get(0)) + Utils.format(" &8[&f" + Utils.spaceNumber(Utils.shorterNumber(expGracza)) + " &bexp &7/&f " + Utils.spaceNumber(Utils.shorterNumber(expNaNextLvlGracza)) + " &bexp" + " &7(&b" + DoubleUtils.round((expGracza / expNaNextLvlGracza) * 100, 2) + "%&7)&8]"));
                            for (int i = 1; i < msg.size(); i++) {
                                finalMessage.append(" &f").append(msg.get(i));
                            }
                        } else {
                            finalMessage = new StringBuilder(msg.get(0) + Utils.format(" &8[&f " + Utils.spaceNumber(Utils.shorterNumber(expGracza)) + " &bexp &7/&f " + Utils.spaceNumber(Utils.shorterNumber(expNaNextLvlGracza)) + " &bexp" + " &7(&b" + DoubleUtils.round((expGracza / expNaNextLvlGracza) * 100, 2) + "%&7)&8]"));
                            for (int i = 1; i < msg.size(); i++) {
                                finalMessage.append(color).append(msg.get(i));
                            }
                        }
                    }
                    formatPrzed.addExtra(Utils.format(finalMessage.toString()));
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.spigot().sendMessage(formatPrzed);
                    }

                    break;
                case 4:
                    // POKAZYWANIE KASY NA CHAT
                    if (msg.isEmpty()) {
                        finalMessage = new StringBuilder(Utils.format(" &8[&2Kasa: &a" + Utils.spaceNumber(String.format("%.2f", rpgcore.getUserManager().find(uuid).getKasa()))  + " &2$&8]"));
                    } else {
                        if (!isHighStaff) {
                            finalMessage = new StringBuilder("&f" + Utils.removeColor(msg.get(0)) + Utils.format(" &8[&2Kasa: &a" + Utils.spaceNumber(String.format("%.2f", rpgcore.getUserManager().find(uuid).getKasa())) + " &2$&8]"));
                            for (int i = 1; i < msg.size(); i++) {
                                finalMessage.append(" &f").append(msg.get(i));
                            }
                        } else {
                            finalMessage = new StringBuilder(msg.get(0) + Utils.format(" &8[&2Kasa: &a" + Utils.spaceNumber(String.format("%.2f", rpgcore.getUserManager().find(uuid).getKasa()))  + " &2$&8]"));
                            for (int i = 1; i < msg.size(); i++) {
                                finalMessage.append(color).append(msg.get(i));
                            }
                        }
                    }

                    formatPrzed.addExtra(Utils.format(finalMessage.toString()));
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

                    formatPrzed.addExtra(Utils.format(finalMessage.toString()));
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
                    formatPrzed.addExtra(Utils.format(finalMessage.toString()));
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.spigot().sendMessage(formatPrzed);
                    }
                    break;
                case 7:
                    final PrzekletyCzarnoksieznikUser przekletyCzarnoksieznikUser = rpgcore.getPrzekletyCzarnoksieznikBossManager().find(uuid);

                    if (rpgcore.getUserManager().find(uuid).getLvl() < 70) {
                        player.sendMessage(Utils.format(Utils.SERVERNAME + "&cMusisz posiadac minimum &c70 &7poziom, zeby pokazac Przekleta Moc na chacie"));
                        player.closeInventory();
                        break;
                    }

                    if (przekletyCzarnoksieznikUser.getDefMOB() == 0 || przekletyCzarnoksieznikUser.getDmgMOB() == 0) {
                        player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie masz wylosowanych bonusow!"));
                        player.closeInventory();
                        break;
                    }

                    final TextComponent przekletaMoc = new TextComponent("§8[§5Przekleta Moc Czarnoksieznika§8]");
                    final TextComponent MOCtext = new TextComponent(
                            "§5Przekleta Moc gracza: §c" + player.getName() +
                                    "§7:\n§cSilny Na Potwory: §e+" +  przekletyCzarnoksieznikUser.getDmgMOB() + "%§7/§625%" +
                                    "\n§cDefensywa Przeciwko Potworom: §e+" + przekletyCzarnoksieznikUser.getDefMOB() + "%§7/§625%" +
                                    "\n");
                    przekletaMoc.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[]{MOCtext}));


                    if (msg.isEmpty()) {
                        formatPrzed.addExtra(przekletaMoc);
                    } else {
                        if (!isHighStaff) {
                            formatPrzed.addExtra(Utils.format(" &f" + Utils.removeColor(msg.get(0))));
                        } else {
                            formatPrzed.addExtra(Utils.format(" " + color + msg.get(0)));
                        }
                        formatPrzed.addExtra(przekletaMoc);
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
                    user.setItemDropEnabled(!user.isItemDropEnabled());
                    break;
                case 13:
                    user.setPingsEnabled(!user.isPingsEnabled());
                    break;
                case 12:
                    user.setNiesDropEnabled(!user.isNiesDropEnabled());
                    break;
                case 11:
                    user.setChestDropEnabled(!user.isChestDropEnabled());
                    break;
                case 14:
                    user.setMsgEnabled(!user.isMsgEnabled());
                    break;
                case 15:
                    user.setJoinMessageEnabled(!user.isJoinMessageEnabled());
                    break;
                case 16:
                    user.setQuitMessageEnabled(!user.isQuitMessageEnabled());
                    break;
                case 19:
                    user.setDmgHologramsVisable(!user.isDmgHologramsVisable());
                    break;
                case 20:
                    user.setBoss1_10(!user.isBoss1_10());
                    break;
                case 21:
                    user.setBoss10_20(!user.isBoss10_20());
                    break;
                case 22:
                    user.setBoss20_30(!user.isBoss20_30());
                    break;
                case 23:
                    user.setBoss30_40(!user.isBoss30_40());
                    break;
                case 24:
                    user.setBoss40_50(!user.isBoss40_50());
                    break;
                case 25:
                    user.setBao(!user.isBao());
                    break;
                case 35:
                    final User mainUser = rpgcore.getUserManager().find(uuid);
                    if (mainUser.getRankUser().isHighStaff() && mainUser.isAdminCodeLogin()) {
                        user.setDatabaseMessageEnabled(!user.isDatabaseMessageEnabled());
                    }
                    break;
                default:
                    return;
            }
            rpgcore.getChatManager().openChatPanel(player);
        }
    }
}
