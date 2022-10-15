package rpg.rpgcore.chat.events;

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
        final String title = Utils.removeColor(e.getInventory().getTitle());
        final int slot = e.getSlot();
        final ItemStack item = e.getCurrentItem();

        if (title.equals("Gui Interakcji z Chatem")) {
            e.setCancelled(true);
            if (item.getType() == Material.AIR) {
                return;
            }
            final String message = rpgcore.getChatManager().getMessageWithEQ(uuid);
            final String formatPrzed = rpgcore.getChatManager().formatujChat(player, Utils.CHAT_FORMAT, "");
            List<String> msg = new ArrayList<>(Arrays.asList(message.split("\\[eq]")));
            if (message.contains("[i]")) {
                msg = new ArrayList<>(Arrays.asList(message.split("\\[i]")));
            } else if (message.contains("[item]")) {
                msg = new ArrayList<>(Arrays.asList(message.split("\\[item]")));
            }

            StringBuilder finalMessage;
            String color = "&7";
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

                    final TextComponent before = new TextComponent(formatPrzed);
                    String itemName = player.getItemInHand().getType().name();

                    if (player.getItemInHand().getItemMeta().getDisplayName() != null) {
                        itemName = player.getItemInHand().getItemMeta().getDisplayName();
                    }

                    final TextComponent itemComponent = new TextComponent(Utils.format("&8[&6x" + player.getItemInHand().getAmount() + " " + itemName + "&8]"));
                    itemComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new ComponentBuilder(CraftItemStack.asNMSCopy(player.getItemInHand()).save(new NBTTagCompound()).toString()).create()));

                    if (msg.isEmpty()) {
                        before.addExtra(itemComponent);
                    } else {
                        if (!isHighStaff) {
                            before.addExtra(Utils.format(" &f" + Utils.removeColor(msg.get(0))));
                        } else {
                            before.addExtra(Utils.format(" " + color + msg.get(0)));
                        }
                        before.addExtra(itemComponent);
                        for (int i = 1; i < msg.size(); i++) {
                            if (!isHighStaff) {
                                before.addExtra(Utils.format(" &f" + Utils.removeColor(msg.get(i))));
                            } else {
                                before.addExtra(Utils.format(" " + color + msg.get(i)));
                            }
                        }
                    }


                    Bukkit.getServer().spigot().broadcast(before);
                    break;
                case 1:
                    // POKAZYWANIE SETA NA CHAT
                    if (player.getInventory().getHelmet() == null || player.getInventory().getChestplate() == null || player.getInventory().getLeggings() == null || player.getInventory().getBoots() == null) {
                        player.sendMessage(Utils.format(Utils.SERVERNAME + "&cMusisz miec zalozone wszystkie czesci zbroji zeby tego uzyc!"));
                        player.closeInventory();
                        return;
                    }
                    if (msg.isEmpty()) {
                        finalMessage = new StringBuilder(Utils.format(rpgcore.getChatManager().getEnchantemntLvlForEQ(player)));
                    } else {
                        if (!isHighStaff) {
                            finalMessage = new StringBuilder("&f" + Utils.removeColor(msg.get(0)) + Utils.format(rpgcore.getChatManager().getEnchantemntLvlForEQ(player)));
                            for (int i = 1; i < msg.size(); i++) {
                                finalMessage.append(" ").append(msg.get(i));
                            }
                        } else {
                            finalMessage = new StringBuilder(msg.get(0) + Utils.format(rpgcore.getChatManager().getEnchantemntLvlForEQ(player)));
                            for (int i = 1; i < msg.size(); i++) {
                                finalMessage = new StringBuilder(Utils.format(finalMessage + " " + color + msg.get(i)));
                            }
                        }
                    }
                    Bukkit.broadcastMessage(formatPrzed + finalMessage);
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
                    final TextComponent beforeMessageBao = new TextComponent(formatPrzed);
                    final TextComponent stolMagii = new TextComponent("§8[§bStol Magii§8]");
                    final TextComponent text = new TextComponent("§7Stol Magii gracza §c" + player.getName() + "§7:\n§6" + user.getBonus1() + ": §c" + user.getValue1() + "% §8\n§6" + user.getBonus2() + ": §c" + user.getValue2() + "% §8\n§6" + user.getBonus3() + ": §c" + user.getValue3() + "% §8\n");
                    TextComponent text2;
                    TextComponent text3;


                    if (user.getBonus4().equalsIgnoreCase("dodatkowe obrazenia")) {
                        text2 = new TextComponent("§6" + user.getBonus4() + ": §c" + user.getValue4() + " DMG §8\n");
                    } else {
                        text2 = new TextComponent("§6" + user.getBonus4() + ": §c" + user.getValue4() + "% §8\n");
                    }
                    if (user.getBonus5().equalsIgnoreCase("dodatkowe hp")) {
                        text3 = new TextComponent("§6" + user.getBonus5() + ": §c" + user.getValue5() + " HP");
                    } else {
                        text3 = new TextComponent("§6" + user.getBonus5() + ": §c" + user.getValue5() + "%");
                    }
                    text.addExtra(text2);
                    text.addExtra(text3);
                    stolMagii.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[]{text}));


                    if (msg.isEmpty()) {
                        beforeMessageBao.addExtra(stolMagii);
                    } else {
                        if (!isHighStaff) {
                            beforeMessageBao.addExtra(Utils.format(" &f" + Utils.removeColor(msg.get(0))));
                        } else {
                            beforeMessageBao.addExtra(Utils.format(" " + color + msg.get(0)));
                        }
                        beforeMessageBao.addExtra(stolMagii);
                        for (int i = 1; i < msg.size(); i++) {
                            if (!isHighStaff) {
                                beforeMessageBao.addExtra(Utils.format(" &f" + Utils.removeColor(msg.get(i))));
                            } else {
                                beforeMessageBao.addExtra(Utils.format(" " + color + msg.get(i)));
                            }
                        }
                    }


                    Bukkit.getServer().spigot().broadcast(beforeMessageBao);
                    break;
                case 3:
                    // POOKAZYWANIE DOSWIADCZENIA NA CHAT

                    final int lvlGracza = rpgcore.getUserManager().find(uuid).getLvl();
                    final double expGracza = rpgcore.getUserManager().find(uuid).getExp();
                    final double expNaNextLvlGracza = rpgcore.getLvlManager().getExpForLvl(lvlGracza + 1);

                    if (msg.isEmpty()) {
                        finalMessage = new StringBuilder(Utils.format(" &8[&f" + Utils.df.format(expGracza) + " &bexp &7/&f " + Utils.df.format(expNaNextLvlGracza) + " &bexp" + "&7(&b" + Utils.procentFormat.format((expGracza / expNaNextLvlGracza) * 100) + "%&7)&8]"));
                    } else {
                        if (!isHighStaff) {
                            finalMessage = new StringBuilder("&f" + Utils.removeColor(msg.get(0)) + Utils.format(" &8[&f" + Utils.df.format(expGracza) + " &bexp &7/&f " + Utils.df.format(expNaNextLvlGracza) + " &bexp" + "&7(&b" + Utils.procentFormat.format((expGracza / expNaNextLvlGracza) * 100) + "%&7)&8]"));
                            for (int i = 1; i < msg.size(); i++) {
                                finalMessage.append(" &f").append(msg.get(i));
                            }
                        } else {
                            finalMessage = new StringBuilder(msg.get(0) + Utils.format(" &8[&f " + Utils.df.format(expGracza) + " &bexp &7/&f " + Utils.df.format(expNaNextLvlGracza) + " &bexp" + "&7(&b" + Utils.procentFormat.format((expGracza / expNaNextLvlGracza) * 100) + "%&7)&8]"));
                            for (int i = 1; i < msg.size(); i++) {
                                finalMessage = new StringBuilder(Utils.format(finalMessage + " " + color + msg.get(i)));
                            }
                        }
                    }

                    Bukkit.broadcastMessage(formatPrzed + finalMessage);

                    break;
                case 4:
                    // POKAZYWANIE KASY NA CHAT
                    if (msg.isEmpty()) {
                        finalMessage = new StringBuilder(Utils.format(" &8[&2Kasa: &a" + Utils.df.format(rpgcore.getUserManager().find(uuid).getKasa()) + " &2$&8]"));
                    } else {
                        if (!isHighStaff) {
                            finalMessage = new StringBuilder("&f" + Utils.removeColor(msg.get(0)) + Utils.format(" &8[&2Kasa: &a" + Utils.df.format(rpgcore.getUserManager().find(uuid).getKasa()) + " &2$&8]"));
                            for (int i = 1; i < msg.size(); i++) {
                                finalMessage.append(" &f").append(msg.get(i));
                            }
                        } else {
                            finalMessage = new StringBuilder(msg.get(0) + Utils.format(" &8[&2Kasa: &a" + Utils.df.format(rpgcore.getUserManager().find(uuid).getKasa()) + " &2$&8]"));
                            for (int i = 1; i < msg.size(); i++) {
                                finalMessage = new StringBuilder(Utils.format(finalMessage + " " + color + msg.get(i)));
                            }
                        }
                    }

                    Bukkit.broadcastMessage(formatPrzed + finalMessage);
                    break;
                case 5:
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

            if (slot == 2) {
                if (user.isItemDropEnabled()) {
                    user.setItemDropEnabled(false);
                    rpgcore.getChatManager().openChatPanel(player);
                    return;
                }
                user.setItemDropEnabled(true);
                rpgcore.getChatManager().openChatPanel(player);
                return;
            }
            if (slot == 3) {
                if (user.isPingsEnabled()) {
                    user.setPingsEnabled(false);
                    rpgcore.getChatManager().openChatPanel(player);
                    return;
                }
                user.setPingsEnabled(true);
                rpgcore.getChatManager().openChatPanel(player);
                return;
            }
            if (slot == 4) {
                if (user.isNiesDropEnabled()) {
                    user.setNiesDropEnabled(false);
                    rpgcore.getChatManager().openChatPanel(player);
                    return;
                }
                user.setNiesDropEnabled(true);
                rpgcore.getChatManager().openChatPanel(player);
                return;
            }
            if (slot == 5) {
                if (user.isChestDropEnabled()) {
                    user.setChestDropEnabled(false);
                    rpgcore.getChatManager().openChatPanel(player);
                    return;
                }

                user.setChestDropEnabled(true);
                rpgcore.getChatManager().openChatPanel(player);
            }
        }
    }
}
