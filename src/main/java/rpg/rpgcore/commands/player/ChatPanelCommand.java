package rpg.rpgcore.commands.player;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.chat.ChatUser;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.Arrays;

public class ChatPanelCommand extends CommandAPI {
    public ChatPanelCommand() {
        super("chatpanel");
        this.setAliases(Arrays.asList("panel", "chatp"));
        this.setRankLevel(RankType.GRACZ);
        this.setRestrictedForPlayer(true);
    }


    @Override
    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;
        if (args.length > 0) {
            player.sendMessage(Utils.poprawneUzycie("/chatpanel"));
        }
        this.openChatPanel(player);
        player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie otwarto panel powiadomien chatu!"));
    }


    private void openChatPanel(Player player) {
        final ChatUser user = RPGCORE.getInstance().getChatManager().find(player.getUniqueId());
        final Inventory gui = Bukkit.createInventory(null, 9, Utils.format("&6&lChat Panel"));

        if (user.isChestDropEnabled()) {
            gui.setItem(2, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 5).setName("&a&lWiadomosci o dropie").setLore(Arrays.asList("&a&lWlaczono!")).addGlowing().toItemStack().clone());
        } else {
            gui.setItem(2, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName("&a&lWiadomosci o dropie").setLore(Arrays.asList("&c&lWylaczono!")).addGlowing().toItemStack().clone());
        }
        if (user.isPingsEnabled()) {
            gui.setItem(3, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 5).setName("&a&lWiadomosci o zaczepkach").setLore(Arrays.asList("&a&lWlaczono!")).addGlowing().toItemStack().clone());
        } else {
            gui.setItem(3, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName("&a&lWiadomosci o zaczepkach").setLore(Arrays.asList("&c&lWylaczono!")).addGlowing().toItemStack().clone());
        }
        if (user.isNiesDropEnabled()) {
            gui.setItem(4, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 5).setName("&a&lWiadomosci o Niesamowitych Przedmiotach").setLore(Arrays.asList("&a&lWlaczono!")).addGlowing().toItemStack().clone());
        } else {
            gui.setItem(4, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName("&a&lWiadomosci o Niesamowitych Przedmiotach").setLore(Arrays.asList("&c&lWylaczono!")).addGlowing().toItemStack().clone());
        }
        if (user.isChestDropEnabled()) {
            gui.setItem(5, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 5).setName("&a&lWiadomosci o dropie ze skrzynek").setLore(Arrays.asList("&a&lWlaczono!")).addGlowing().toItemStack().clone());
        } else {
            gui.setItem(5, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName("&a&lWiadomosci o dropie ze skrzynek").setLore(Arrays.asList("&c&lWylaczono!")).addGlowing().toItemStack().clone());
        }
        gui.setItem(6, new ItemBuilder(Material.BARRIER).setName("&a&lCos tu kiedys jeszcze bedzie").addGlowing().toItemStack().clone());


        player.openInventory(gui);
    }
}
