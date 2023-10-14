package rpg.rpgcore.commands.player.hellcode.panel.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.chat.objects.ChatUser;
import rpg.rpgcore.commands.player.hellcode.panel.objects.HellcodeUser;
import rpg.rpgcore.utils.Utils;

public class HellCodePanelInventoryClickListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onClick(final InventoryClickEvent e) {
        if (e.getClickedInventory() == null || e.getInventory() == null) {
            return;
        }

        final Player player = (Player) e.getWhoClicked();
        final String title = Utils.removeColor(e.getClickedInventory().getTitle());
        final int slot = e.getSlot();

        if (title.equals("HellCode Panel")) {
            e.setCancelled(true);
            final ChatUser chatUser = RPGCORE.getInstance().getChatManager().find(player.getUniqueId());
            final HellcodeUser user = chatUser.getHellcodeUser();

            switch (slot) {
                case 1:
                    user.changeChatEnabled();
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Zmieniono status pisania na chacie na " + (user.isChatEnabled() ? "&aWlaczony" : "&cWylaczony")));
                    break;
                case 2:
                    user.changeMsgAndReply();
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Zmieniono status pisania i odpisywania na wiadomosci prywatne na " + (user.isMsgAndReply() ? "&aWlaczony" : "&cWylaczony")));
                    break;
                case 3:
                    user.changeEnderchest();
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Zmieniono status otwierania enderchest'a na " + (user.isEnderchest() ? "&aWlaczony" : "&cWylaczony")));
                    break;
                case 5:
                    user.changeMagazyn();
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Zmieniono status otwierania magazynow na " + (user.isMagazyn() ? "&aWlaczony" : "&cWylaczony")));
                    break;
                case 6:
                    user.changeInventoryInteract();
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Zmieniono status interakcji z ekwipunkiem na " + (user.isInventoryInteract() ? "&aWlaczony" : "&cWylaczony")));
                    break;
                case 7:
                    user.changeEntityInteract();
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Zmieniono status interakcji z NPC na " + (user.isEntityInteract() ? "&aWlaczony" : "&cWylaczony")));
                    break;
                default:
                    return;
            }
            RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataChatUsers(player.getUniqueId(), chatUser));

        }
    }
}
