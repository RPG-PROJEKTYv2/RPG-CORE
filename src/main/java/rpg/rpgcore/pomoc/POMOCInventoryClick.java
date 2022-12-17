package rpg.rpgcore.pomoc;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

public class POMOCInventoryClick implements Listener {

    private final RPGCORE rpgcore;

    public POMOCInventoryClick(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void pomocInventoryClick(final InventoryClickEvent e) {

        final Inventory clickedInventory = e.getClickedInventory();
        final Player player = (Player) e.getWhoClicked();

        if (e.getClickedInventory() == null || e.getInventory() == null) {
            return;
        }

        final String clickedInventoryTitle = clickedInventory.getTitle();
        final ItemStack clickedItem = e.getCurrentItem();
        final int clickedSlot = e.getSlot();

        if (Utils.removeColor(clickedInventoryTitle).equals("Pomoc")) {
            e.setCancelled(true);
            if (clickedSlot == 0) {
                player.closeInventory();
                player.sendMessage(Utils.format("&e&lPoradnik &f&l* &7Witaj &c" + player.getName() + " &7na samym poczatku powinienes przeczytac regulamin serwerowy ktory znajdziesz na naszym discordzie - &edc.hellrpg.pl&7!\n" +
                        "&7Znajdujesz sie na trybie &4&lMMO/RPG &7z poziomem &cmedium.\n" +
                        "&7Tryb polega na zwiekszanie poziomu postaci poprzez zabijanie potworow na poszczegolnych mapach, ulepszaniu swojego konta jak i ekwipunku oraz rywalizacji z innymi graczami!\n" +
                        "&7Aby teleportowac sie na pierwsza mape musisz udac sie do &9&lTeleportera&7.\n" +
                        "&7Wszystkie najwazniejsze npcty znajdziesz pod - &e/listanpc&7.\n" +
                        "\n " +
                        "&cZyczymy udanej rozgrywki! ~&4&lHELLRPG.PL"));
            }
            if (clickedSlot == 2) {
                player.closeInventory();
                player.sendMessage(" ");
                player.sendMessage(Utils.format("&7Kliknij:"));
                player.sendMessage(Utils.format("&a&lDiscord: &6&ldc.hellrpg.pl"));
                player.sendMessage(Utils.format("&a&lFacebook: &6&lfb.hellrpg.pl"));
                player.sendMessage(Utils.format("&a&lStrona &6&lwww.hellrpg.pl"));
                player.sendMessage(" ");
            }
            if (clickedSlot == 4) {
                player.closeInventory();
                rpgcore.getPomocManager().openAllKomendyInventory(player);
            }
        }
        if (Utils.removeColor(clickedInventoryTitle).equals("Spis komend")) {
            e.setCancelled(true);
        }
    }
}
