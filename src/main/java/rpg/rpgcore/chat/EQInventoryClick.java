package rpg.rpgcore.chat;

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
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.bao.BaoUser;
import rpg.rpgcore.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

public class EQInventoryClick implements Listener {

    private final RPGCORE rpgcore;

    public EQInventoryClick(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void eqInventoryClicK(final InventoryClickEvent e) {

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

        if (clickedInventoryTitle.equals(Utils.format("&4&lEQ GUI"))) {
            e.setCancelled(true);
            if (clickedItem.getType() == Material.AIR) {
                e.getWhoClicked().closeInventory();
                return;
            }
            final String przedFormatem = Utils.format("<player-klan>&8[&bLvl. &f<player-lvl>&8]<player-group> &7<player-name>:&f <message>");
            final String message = rpgcore.getChatManager().getMessageWithEQ(e.getWhoClicked().getUniqueId());
            ArrayList<String> msg = new ArrayList<>(Arrays.asList(message.split("\\[eq]")));
            if (message.contains("[i]")) {
                msg = new ArrayList<>(Arrays.asList(message.split("\\[i]")));
            } else if (message.contains("[item]")){
                msg = new ArrayList<>(Arrays.asList(message.split("\\[item]")));
            }
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
                    if (rpgcore.getUserManager().find(playerUUID).getLvl() < 74) {
                        player.sendMessage(Utils.format(Utils.SERVERNAME + "&cMusisz posiadac minimum &c75 &7poziom, zeby pokazac bao na chacie"));
                        player.closeInventory();
                        break;
                    }

                    if (rpgcore.getBaoManager().isNotRolled(playerUUID)) {
                        player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie masz jeszcze zrobionego bao!"));
                        player.closeInventory();
                        break;
                    }
                    final BaoUser user = rpgcore.getBaoManager().find(playerUUID).getBaoUser();
                    TextComponent beforeMessageBao = new TextComponent(formatPrzedWiadomoscia);
                    TextComponent stolMagii = new TextComponent("§8[§bStol Magii§8]");
                    TextComponent text = new TextComponent("§7Stol Magii gracza §c" + player.getName() + "§7:\n§6" + user.getBonus1() + ": §c" + user.getValue1() + "% §8\n§6" + user.getBonus2() + ": §c" + user.getValue2() + "% §8\n§6" + user.getBonus3() + ": §c" + user.getValue3() + "% §8\n");
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
                    beforeMessageBao.addExtra(stolMagii);
                    Bukkit.getServer().spigot().broadcast(beforeMessageBao);
                    break;
                case 3:
                    final int lvlGracza = rpgcore.getUserManager().find(playerUUID).getLvl();
                    final double expGracza = rpgcore.getUserManager().find(playerUUID).getExp();
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
    }

}
