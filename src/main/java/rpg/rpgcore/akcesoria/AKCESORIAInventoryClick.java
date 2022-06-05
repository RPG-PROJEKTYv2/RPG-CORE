package rpg.rpgcore.akcesoria;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;

import java.util.HashMap;
import java.util.UUID;

public class AKCESORIAInventoryClick implements Listener {

    private final RPGCORE rpgcore;

    public AKCESORIAInventoryClick(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void akcesoriaInventoryClick(final InventoryClickEvent e) {

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



        if (clickedInventoryTitle.contains("Akcesoria gracza ")) {
            ItemStack itemToGiveBack;

            e.setCancelled(true);

            if (clickedItem.getType() == Material.BARRIER) {
                return;
            }

            if (clickedSlot == 10) {
                itemToGiveBack = clickedItem;
                e.getWhoClicked().getInventory().addItem(itemToGiveBack);
                rpgcore.getPlayerManager().updatePlayerDef(playerUUID, rpgcore.getPlayerManager().getPlayerDef(playerUUID) - rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToGiveBack, "Obrona"));
                rpgcore.getPlayerManager().updatePlayerBlok(playerUUID, rpgcore.getPlayerManager().getPlayerBlok(playerUUID) - rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToGiveBack, "Blok Ciosu"));
                rpgcore.getPlayerManager().updatePlayerDamage(playerUUID, rpgcore.getPlayerManager().getPlayerDamage(playerUUID) - rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToGiveBack, "Obrazenia"));
                e.getInventory().setItem(clickedSlot, rpgcore.getAkcesoriaManager().noAkcesoriaItem("Tarczy"));
                return;
            }

            if (clickedSlot == 11) {
                itemToGiveBack = clickedItem;
                e.getWhoClicked().getInventory().addItem(itemToGiveBack);
                rpgcore.getPlayerManager().updatePlayerPrzeszywka(playerUUID, rpgcore.getPlayerManager().getPlayerPrzeszywka(playerUUID) - rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToGiveBack, "Przeszycie Bloku"));
                rpgcore.getPlayerManager().updatePlayerDamage(playerUUID, rpgcore.getPlayerManager().getPlayerDamage(playerUUID) - rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToGiveBack, "Obrazenia"));
                System.out.println("Naszyjnik dmg - " + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToGiveBack, "Obrazenia"));
                e.getInventory().setItem(clickedSlot, rpgcore.getAkcesoriaManager().noAkcesoriaItem("Naszyjnika"));
                return;
            }

            if (clickedSlot == 12) {
                itemToGiveBack = clickedItem;
                e.getWhoClicked().getInventory().addItem(itemToGiveBack);
                rpgcore.getPlayerManager().updatePlayerKryt(playerUUID, rpgcore.getPlayerManager().getPlayerKryt(playerUUID) - rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToGiveBack, "Cios Krytyczny"));
                rpgcore.getPlayerManager().updatePlayerSrednie(playerUUID, rpgcore.getPlayerManager().getPlayerSrednie(playerUUID) - rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToGiveBack, "Srednie Obrazenia"));
                e.getInventory().setItem(clickedSlot, rpgcore.getAkcesoriaManager().noAkcesoriaItem("Bransolety"));
                return;
            }

            if (clickedSlot == 13) {
                itemToGiveBack = clickedItem;
                e.getWhoClicked().getInventory().addItem(itemToGiveBack);
                rpgcore.getPlayerManager().updatePlayerHP(playerUUID, rpgcore.getPlayerManager().getPlayerHP(playerUUID) - rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToGiveBack, "Dodatkowe HP"));
                rpgcore.getPlayerManager().updatePlayerSilnyNaLudzi(playerUUID, rpgcore.getPlayerManager().getPlayerSilnyNaLudzi(playerUUID) - rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToGiveBack, "Silny przeciwko Ludziom"));
                player.setMaxHealth(player.getMaxHealth() - (double) rpgcore.getAkcesoriaManager().getAkcesoriaBonus(playerUUID, 13, "Dodatkowe HP") * 2);
                e.getInventory().setItem(clickedSlot, rpgcore.getAkcesoriaManager().noAkcesoriaItem("Kolczykow"));
                return;
            }

            if (clickedSlot == 14) {
                itemToGiveBack = clickedItem;
                e.getWhoClicked().getInventory().addItem(itemToGiveBack);
                rpgcore.getPlayerManager().updatePlayerBlok(playerUUID, rpgcore.getPlayerManager().getPlayerBlok(playerUUID) - rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToGiveBack, "Blok Ciosu"));
                rpgcore.getPlayerManager().updatePlayerHP(playerUUID, rpgcore.getPlayerManager().getPlayerHP(playerUUID) - rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToGiveBack, "Dodatkowe HP"));
                player.setMaxHealth(player.getMaxHealth() - (double) rpgcore.getAkcesoriaManager().getAkcesoriaBonus(playerUUID, 14, "Dodatkowe HP") * 2);
                System.out.println("Pierek blok -" + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToGiveBack, "Blok Ciosu"));
                e.getInventory().setItem(clickedSlot, rpgcore.getAkcesoriaManager().noAkcesoriaItem("Pierscienia"));
                return;
            }

            if (clickedSlot == 15) {
                itemToGiveBack = clickedItem;
                e.getWhoClicked().getInventory().addItem(itemToGiveBack);
                rpgcore.getPlayerManager().updatePlayerDef(playerUUID, rpgcore.getPlayerManager().getPlayerDef(playerUUID) - rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToGiveBack, "Obrona"));
                rpgcore.getPlayerManager().updatePlayerBlok(playerUUID, rpgcore.getPlayerManager().getPlayerBlok(playerUUID) - rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToGiveBack, "Blok Ciosu"));
                rpgcore.getPlayerManager().updatePlayerMinusSrednie(playerUUID, rpgcore.getPlayerManager().getPlayerMinusSrednie(playerUUID) - rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToGiveBack, "Srednie Obrazenia"));
                System.out.println("energia blok - " + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToGiveBack, "Blok Ciosu"));
                e.getInventory().setItem(clickedSlot, rpgcore.getAkcesoriaManager().noAkcesoriaItem("Energii"));
                return;
            }

            if (clickedSlot == 16) {
                itemToGiveBack = clickedItem;
                e.getWhoClicked().getInventory().addItem(itemToGiveBack);
                rpgcore.getPlayerManager().updatePlayerDamage(playerUUID, rpgcore.getPlayerManager().getPlayerDamage(playerUUID) - rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToGiveBack, "Obrazenia"));
                rpgcore.getPlayerManager().updatePlayerSilnyNaLudzi(playerUUID, rpgcore.getPlayerManager().getPlayerSilnyNaLudzi(playerUUID) - rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToGiveBack, "Silny przeciwko Ludziom"));
                rpgcore.getPlayerManager().updatePlayerMinusDef(playerUUID, rpgcore.getPlayerManager().getPlayerMinusDef(playerUUID) - rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToGiveBack, "Obrona"));
                System.out.println("Zegarek dmg - " + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(itemToGiveBack, "Obrazenia"));
                e.getInventory().setItem(clickedSlot, rpgcore.getAkcesoriaManager().noAkcesoriaItem("Zegarka"));
            }
        }

    }

}
