package rpg.rpgcore.managers;

import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.*;

public class ChatManager implements Listener {

    private final RPGCORE rpgcore;

    public ChatManager(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    private final HashMap<UUID, String> messageWithEQ = new HashMap<>();

    public String getMessageWithEQ(final UUID uuid) {
        return this.messageWithEQ.get(uuid);
    }

    public void updateMessageWithEQ(final UUID uuid, final String message) {
        if (this.messageWithEQ.containsKey(uuid)) {
            this.messageWithEQ.replace(uuid, message);
            return;
        }
        this.messageWithEQ.put(uuid, message);
    }

    public String formatujChat(final Player player, final String format, String message) {
        final int playerLVL = rpgcore.getPlayerManager().getPlayerLvl(player.getUniqueId());
        String playerName = player.getName();
        String playerRank = PlaceholderAPI.setPlaceholders(player, "%uperms_rank%");

        switch (playerRank) {
            case "H@":
                playerName = Utils.format("&c" + playerName);
                break;
            case "Admin":
                playerName = Utils.format("&c" + playerName);
                break;
            case "GM":
                playerName = Utils.format("&a" + playerName);
                break;
            case "Mod":
                message = Utils.removeColor(message);
                playerName = Utils.format("&a" + playerName);
                break;
            case "KidMod":
                message = Utils.removeColor(message);
                playerName = Utils.format("&a" + playerName);
                break;
            case "Helper":
                message = Utils.removeColor(message);
                playerName = Utils.format("&b" + playerName);
                break;
            case "JuniorHelper":
                message = Utils.removeColor(message);
                playerName = Utils.format("&b" + playerName);
                break;
            default:
                message = Utils.removeColor(message);
                playerName = Utils.format("&7" + playerName);
                break;
        }
        if (playerRank.equals("Gracz")) {
            return PlaceholderAPI.setPlaceholders(player, format.replace("<player-lvl>", String.valueOf(playerLVL)).replace("<player-group>", "").replace("<player-name>", playerName).replace("<message>", message));
        }
        return PlaceholderAPI.setPlaceholders(player, format.replace("<player-lvl>", String.valueOf(playerLVL)).replace("<player-group>", " %uperms_prefixes%").replace("<player-name>", playerName).replace("<message>", message));
    }


    public void formatujChatEQ(final Player player) {
        final Inventory eqQUI = this.createEQGUI();
        player.openInventory(eqQUI);
    }

    public Inventory createEQGUI() {
        final Inventory eqGUI = Bukkit.createInventory(null, 1 * 9, Utils.format("&4&lEQ GUI"));

        ItemStack item;
        ItemMeta itemMeta;
        List<String> itemLore = new ArrayList<>();

        //          POKAZ ITEM          \\
        item = new ItemStack(Material.DIAMOND_SWORD);
        itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(Utils.format("&6&lPokaz Item"));
        itemLore.add(Utils.format("&7Pokaz swoj item na chacie"));
        itemMeta.setLore(itemLore);
        itemLore.clear();
        item.setItemMeta(itemMeta);
        eqGUI.addItem(item);

        //          POKAZ SET          \\
        item = new ItemStack(Material.DIAMOND_CHESTPLATE);
        itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(Utils.format("&6&lPokaz Seta"));
        itemLore.add(Utils.format("&7Pokaz seta na chacie"));
        itemMeta.setLore(itemLore);
        itemLore.clear();
        item.setItemMeta(itemMeta);
        eqGUI.addItem(item);

        //          POKAZ BAO          \\
        item = new ItemStack(Material.ENCHANTMENT_TABLE);
        itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(Utils.format("&6&lPokaz BAO"));
        itemLore.add(Utils.format("&7Pokaz na chacie swoje bonusy stolu magii"));
        itemMeta.setLore(itemLore);
        itemLore.clear();
        item.setItemMeta(itemMeta);
        eqGUI.addItem(item);

        //          POKAZ DOSWIADCZENIE          \\
        item = new ItemStack(Material.MONSTER_EGG);
        itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(Utils.format("&6&lPokaz EXP"));
        itemLore.add(Utils.format("&7Pokaz swoj postep do nastepnego poziomu na chacie"));
        itemMeta.setLore(itemLore);
        itemLore.clear();
        item.setItemMeta(itemMeta);
        eqGUI.addItem(item);

        //          POKAZ KASE          \\
        item = new ItemStack(Material.BOOK);
        itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(Utils.format("&6&lPokaz KASE"));
        itemLore.add(Utils.format("&7Pokaz swoj stan konta na chacie"));
        itemMeta.setLore(itemLore);
        itemLore.clear();
        item.setItemMeta(itemMeta);
        eqGUI.addItem(item);

        //          POKAZ SPEDZONY CZAS          \\
        item = new ItemStack(Material.WATCH);
        itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(Utils.format("&6&lPokaz SPEDZONY CZAS"));
        itemLore.add(Utils.format("&7Pokaz swoj czas spedzony online na serwerze"));
        itemMeta.setLore(itemLore);
        itemLore.clear();
        item.setItemMeta(itemMeta);
        eqGUI.addItem(item);

        return eqGUI;
    }

    public String getEnchantemntLvlForEQ(final Player player) {
        return " &8[&3&lP: &b" + player.getInventory().getHelmet().getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) +
                "&b/" + player.getInventory().getChestplate().getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) +
                "&b/" + player.getInventory().getLeggings().getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) +
                "&b/" + player.getInventory().getBoots().getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) +
                " &3&lT: &b " + player.getInventory().getHelmet().getEnchantmentLevel(Enchantment.THORNS) +
                "&b/" + player.getInventory().getChestplate().getEnchantmentLevel(Enchantment.THORNS) +
                "&b/" + player.getInventory().getLeggings().getEnchantmentLevel(Enchantment.THORNS) +
                "&b/" + player.getInventory().getBoots().getEnchantmentLevel(Enchantment.THORNS) +
                "&8]&7";
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClick(final InventoryClickEvent e) {

        //TODO poprawic z Cauflandem
        if (e.getInventory().getName() == null) {
            e.getWhoClicked().closeInventory();
            return;
        }
        if (e.getClickedInventory().getName().equals(Utils.format("&4&lEQ GUI"))) {
            if (e.getCurrentItem().getType() == Material.AIR) {
                e.setCancelled(true);
                e.getWhoClicked().closeInventory();
                return;
            }
            final int clickedSlot = e.getSlot();
            final String przedFormatem = Utils.format("&8[&bLvl. &f<player-lvl>&8] <player-group> &7<player-name>&7: <message>");
            final Player player = (Player) e.getWhoClicked();
            final UUID playerUUID = player.getUniqueId();
            final String message = this.getMessageWithEQ(e.getWhoClicked().getUniqueId());
            final ArrayList<String> msg = new ArrayList<>(Arrays.asList(message.split("\\[eq\\]")));
            final String formatPrzedWiadomoscia = this.formatujChat(player, przedFormatem, "");
            final String playerGroup = PlaceholderAPI.setPlaceholders(player, "%uperms_rank%");
            String finalMessage;
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
                    TextComponent befoerMessage = new TextComponent(formatPrzedWiadomoscia);

                    TextComponent item;
                    if (e.getWhoClicked().getItemInHand().getItemMeta().getDisplayName() != null) {
                        item = new TextComponent(" §8[" + e.getWhoClicked().getItemInHand().getItemMeta().getDisplayName() + "§8]");
                    } else {
                        item = new TextComponent(" §8[" + e.getWhoClicked().getItemInHand().getType().toString() + "§8]");
                    }
                    item.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new ComponentBuilder(CraftItemStack.asNMSCopy(e.getWhoClicked().getItemInHand()).save(new NBTTagCompound()).toString()).create()));
                    if (!(msg.isEmpty())) {
                        TextComponent firstPartOfMessage;
                        if (playerGroup.equals("Gracz") || playerGroup.equals("Vip") || playerGroup.equals("Svip") || playerGroup.equals("ELITA") || playerGroup.equals("Budowniczy")) {
                            firstPartOfMessage = new TextComponent(Utils.format("&7" + Utils.removeColor(msg.get(0))));
                        } else {
                            firstPartOfMessage = new TextComponent(Utils.format("&7" + msg.get(0)));
                        }
                        befoerMessage.addExtra(firstPartOfMessage);
                        befoerMessage.addExtra(item);
                        if (playerGroup.equals("Gracz") || playerGroup.equals("Vip") || playerGroup.equals("Svip") || playerGroup.equals("ELITA") || playerGroup.equals("Budowniczy")) {
                            for (int i = 1; i < msg.size(); i++) {
                                befoerMessage.addExtra(Utils.format(" " + "&7" + msg.get(i)));
                            }
                        } else {
                            for (int i = 1; i < msg.size(); i++) {
                                befoerMessage.addExtra(Utils.format(" " + color + msg.get(i)));
                            }
                        }
                    } else {
                        befoerMessage.addExtra(item);
                    }


                    Bukkit.spigot().broadcast(befoerMessage);
                    break;
                case 1:
                    if (player.getInventory().getHelmet() == null || player.getInventory().getChestplate() == null || player.getInventory().getLeggings() == null || player.getInventory().getBoots() == null) {
                        player.sendMessage(Utils.format(Utils.SERVERNAME + "&cMusisz miec zalozone wszystkie czesci zbroji zeby tego uzyc!"));
                        player.closeInventory();
                        return;
                    }
                    if (!(msg.isEmpty())) {
                        if (playerGroup.equals("Gracz") || playerGroup.equals("Vip") || playerGroup.equals("Svip") || playerGroup.equals("ELITA") || playerGroup.equals("Budowniczy")) {
                            finalMessage = "&7" + Utils.removeColor(msg.get(0)) + Utils.format(this.getEnchantemntLvlForEQ(player));
                            for (int i = 1; i < msg.size(); i++) {
                                finalMessage = finalMessage + " " + msg.get(i);
                            }
                        } else {
                            finalMessage = msg.get(0) + Utils.format(this.getEnchantemntLvlForEQ(player));
                            for (int i = 1; i < msg.size(); i++) {
                                finalMessage = Utils.format(finalMessage + " " + color + msg.get(i));
                            }
                        }
                    } else {
                        finalMessage = Utils.format(this.getEnchantemntLvlForEQ(player));
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
                    finalMessage = Utils.format("&8[&6" + baoBonusy[0] + ": &c" + baoWartosci[0] + " % &8| &6" + baoBonusy[1] + ": &c" + baoWartosci[1] + " % &8| &6" + baoBonusy[2] + ": &c" + baoWartosci[2] + " % &8| &6");
                    if (baoBonusy[3].equalsIgnoreCase("dodatkowe obrazenia")) {
                        finalMessage = finalMessage + Utils.format(baoBonusy[3] + ": &c" + baoWartosci[3] + " DMG &8| &6");
                    } else {
                        finalMessage = finalMessage + Utils.format(baoBonusy[3] + ": &c" + baoWartosci[3] + " % &8| &6");
                    }
                    if (baoBonusy[4].equalsIgnoreCase("dodatkowe hp")) {
                        finalMessage = finalMessage + Utils.format(baoBonusy[4] + ": &c" + baoWartosci[4] + " HP&8]");
                    } else {
                        finalMessage = finalMessage + Utils.format(baoBonusy[4] + ": &c" + baoWartosci[4] + " %&8]");
                    }
                    Bukkit.broadcastMessage(formatPrzedWiadomoscia + finalMessage);
                    break;
                case 3:
                    final int lvlGracza = rpgcore.getPlayerManager().getPlayerLvl(playerUUID);
                    final double expGracza = rpgcore.getPlayerManager().getPlayerExp(playerUUID);
                    final double expNaNextLvlGracza = rpgcore.getLvlManager().getExpForLvl(lvlGracza + 1);

                    if (!(msg.isEmpty())) {
                        if (playerGroup.equals("Gracz") || playerGroup.equals("Vip") || playerGroup.equals("Svip") || playerGroup.equals("ELITA") || playerGroup.equals("Budowniczy")) {
                            finalMessage = "&7" + Utils.removeColor(msg.get(0)) + Utils.format(" &8[&f" + Utils.df.format(expGracza) + " &bexp &7/&f " + Utils.df.format(expNaNextLvlGracza) + " &bexp" + "&7(&b" + Utils.procentFormat.format((expGracza / expNaNextLvlGracza) * 100) + "%&7)&8]");
                            for (int i = 1; i < msg.size(); i++) {
                                finalMessage = finalMessage + " " + msg.get(i);
                            }
                        } else {
                            finalMessage = msg.get(0) + Utils.format(" &8[&f " + Utils.df.format(expGracza) + " &bexp &7/&f " + Utils.df.format(expNaNextLvlGracza) + " &bexp" + "&7(&b" + Utils.procentFormat.format((expGracza / expNaNextLvlGracza) * 100) + "%&7)&8]");
                            for (int i = 1; i < msg.size(); i++) {
                                finalMessage = Utils.format(finalMessage + " " + color + msg.get(i));
                            }
                        }
                    } else {
                        finalMessage = Utils.format(" &8[&f" + Utils.df.format(expGracza) + " &bexp &7/&f " + Utils.df.format(expNaNextLvlGracza) + " &bexp" + "&7(&b" + Utils.procentFormat.format((expGracza / expNaNextLvlGracza) * 100) + "%&7)&8]");
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
            this.updateMessageWithEQ(e.getWhoClicked().getUniqueId(), "");
            e.setCancelled(true);
            e.getWhoClicked().closeInventory();
        }
    }

    @EventHandler
    public void onInventoryClose(final InventoryCloseEvent e) {
        if (e.getInventory().getName().equals(Utils.format("&4&lEQ GUI"))) {
            this.updateMessageWithEQ(e.getPlayer().getUniqueId(), "");
        }
    }
}
