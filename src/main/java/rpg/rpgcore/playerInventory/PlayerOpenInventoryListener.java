package rpg.rpgcore.playerInventory;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;


public class PlayerOpenInventoryListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClick(final InventoryClickEvent e) {
        if (e.getClickedInventory() == null || e.getInventory() == null) return;

        final Player player = (Player) e.getWhoClicked();

        if (player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR) return;

        if (e.getClickedInventory().getType() != InventoryType.CRAFTING && e.getSlotType() != InventoryType.SlotType.CRAFTING) return;
        player.sendMessage("1");

        if (e.getClickedInventory().getSize() > 5) return;
        player.sendMessage("2");

        if (!RPGCORE.getInstance().getUserManager().find(player.getUniqueId()).isHellCodeLogin()) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Przed uzyciem tej komendy zaloguj sie swoim HellCode. Uzyj: &c/hellcode <kod>"));
            return;
        }

        final int slot = e.getSlot();
        e.setCancelled(true);
        player.sendMessage("3");
        if (slot == 0) {
            player.performCommand("profile");
            return;
        }
        if (slot == 1) {
            player.performCommand("magazyny");
            return;
        }
        if (slot == 2) {
            RPGCORE.getInstance().getDodatkiManager().openAkcePodsGUI(player, player);
            return;
        }
        if (slot == 3) {
            player.performCommand("kosz");
            return;
        }
        if (slot == 4) {
            RPGCORE.getInstance().getCraftingiManager().openCraftingiGUI(player);
        }
    }
}
