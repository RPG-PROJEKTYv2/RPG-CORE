package rpg.rpgcore.inventory;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.Utils;

public class InvseeInventoryCloseListener implements Listener {
    private final RPGCORE rpgcore;

    public InvseeInventoryCloseListener(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onInventoryClose(final InventoryCloseEvent e) {
        if (!Utils.removeColor(e.getInventory().getName()).contains("Ekwipunek gracza")) return;
        final String playerName = Utils.removeColor(e.getInventory().getName()).replace("Ekwipunek gracza ", "").trim();

        final ItemStack[] contents = new ItemStack[36];
        for (int i = 0; i < contents.length; i++) {
            contents[i] = e.getInventory().getItem(i);
        }
        final ItemStack[] armor = new ItemStack[4];
        armor[0] = e.getInventory().getItem(36);
        armor[1] = e.getInventory().getItem(37);
        armor[2] = e.getInventory().getItem(38);
        armor[3] = e.getInventory().getItem(39);

        final User user = this.rpgcore.getUserManager().find(playerName);


        user.getInventoriesUser().setInventory(Utils.itemStackArrayToBase64(contents));
        user.getInventoriesUser().setArmor(Utils.itemStackArrayToBase64(armor));

        if (Bukkit.getPlayer(user.getId()) != null) {
            final Player p = Bukkit.getPlayer(user.getId());
            p.getInventory().setContents(contents);
            p.getInventory().setArmorContents(armor);
        }

        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataUser(user.getId(), user));
        e.getPlayer().sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie zapisano ekwipunek gracza &6" + playerName));
        rpgcore.getInvseeManager().log((Player) e.getPlayer(), playerName, user.getId(), contents, armor);
    }
}
