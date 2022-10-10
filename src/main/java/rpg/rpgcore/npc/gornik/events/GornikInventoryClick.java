package rpg.rpgcore.npc.gornik.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.bonuses.Bonuses;
import rpg.rpgcore.npc.gornik.GornikObject;
import rpg.rpgcore.npc.gornik.GornikUser;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.npc.GornikItems;

import java.util.UUID;

public class GornikInventoryClick implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClick(final InventoryClickEvent e) {

        if (e.getClickedInventory() == null) {
            return;
        }

        final Inventory clickedInventory = e.getClickedInventory();
        final Player player = (Player) e.getWhoClicked();
        final UUID uuid = player.getUniqueId();
        final String title = Utils.removeColor(clickedInventory.getTitle());
        final ItemStack item = e.getCurrentItem();
        final int slot = e.getSlot();


        if (title.equals("Gornik")) {
            e.setCancelled(true);
            if (slot == 11) {
                RPGCORE.getInstance().getGornikNPC().openKampania(player);
                return;
            }
            if (slot == 22) {
                RPGCORE.getInstance().getGornikNPC().openDrzewko(player);
                return;
            }
        }

        if (title.equals("Drzewko Gornika")) {
            e.setCancelled(true);
            final GornikObject object = RPGCORE.getInstance().getGornikNPC().find(uuid);
            final GornikUser user = object.getGornikUser();
            final User playerUser = RPGCORE.getInstance().getUserManager().find(uuid);
            final Bonuses bonuses = RPGCORE.getInstance().getBonusesManager().find(uuid);

            if (item.getDurability() == 7 || item.getDurability() == 5) {
                player.getInventory().addItem(GornikItems.getItem("I1", 1));
                return;
            }
            if (playerUser.getKasa() < 50000000) {
                player.sendMessage(Utils.format("&6&lGornik &8>> &7Chyba czegos Ci brakuje moj przyjacielu &8(&2$&8)"));
                return;
            }
            if (!player.getInventory().containsAtLeast(GornikItems.getItem("I1", 1), 1)) {
                player.sendMessage(Utils.format("&6&lGornik &8>> &7Chyba czegos Ci brakuje moj przyjacielu &8(&6Krysztal&8)"));
                return;
            }
            switch (slot) {
                // SRODEK
                case 3:
                    if (!(user.isD1() && user.isD2() && user.isD3_3() && user.isD4_7() && user.isD5_5() && user.isD6_5())) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie odblokowales jeszcze pooprzednich atrybutow"));
                        return;
                    }
                    playerUser.setKasa(playerUser.getKasa() - 50000000);
                    player.getInventory().removeItem(GornikItems.getItem("I1", 1));
                    user.setD7_3(true);
                    bonuses.getBonusesUser().setSzczescie(bonuses.getBonusesUser().getSzczescie() + 15);
                    break;
                case 5:
                    if (!(user.isD1() && user.isD2() && user.isD3_3() && user.isD4_7() && user.isD5_5() && user.isD6_5())) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie odblokowales jeszcze pooprzednich atrybutow"));
                        return;
                    }
                    playerUser.setKasa(playerUser.getKasa() - 50000000);
                    player.getInventory().removeItem(GornikItems.getItem("I1", 1));
                    user.setD7_4(true);
                    bonuses.getBonusesUser().setPrzebiciePancerza(bonuses.getBonusesUser().getPrzebiciePancerza() + 2);
                    break;
                case 4:
                    if (!(user.isD1() && user.isD2() && user.isD3_3() && user.isD4_7() && user.isD5_5())) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie odblokowales jeszcze pooprzednich atrybutow"));
                        return;
                    }
                    if (playerUser.getKasa() < 150000000) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Chyba czegos Ci brakuje moj przyjacielu &8(&2$&8)"));
                        return;
                    }
                    if (!player.getInventory().containsAtLeast(GornikItems.getItem("I1", 1), 3)) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Chyba czegos Ci brakuje moj przyjacielu &8(&6Krysztal&8)"));
                        return;
                    }
                    playerUser.setKasa(playerUser.getKasa() - 150000000);
                    player.getInventory().removeItem(GornikItems.getItem("I1", 3));
                    user.setD6_5(true);
                    break;
                case 13:
                    if (!(user.isD1() && user.isD2() && user.isD3_3() && user.isD4_7())) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie odblokowales jeszcze pooprzednich atrybutow"));
                        return;
                    }
                    playerUser.setKasa(playerUser.getKasa() - 50000000);
                    player.getInventory().removeItem(GornikItems.getItem("I1", 1));
                    user.setD5_5(true);
                    bonuses.getBonusesUser().setSilnynapotwory(bonuses.getBonusesUser().getSilnynapotwory() + 5);
                    bonuses.getBonusesUser().setSzczescie(bonuses.getBonusesUser().getSzczescie() + 10);
                    break;
                case 22:
                    if (!(user.isD1() && user.isD2() && user.isD3_3())) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie odblokowales jeszcze pooprzednich atrybutow"));
                        return;
                    }
                    if (playerUser.getKasa() < 150000000) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Chyba czegos Ci brakuje moj przyjacielu &8(&2$&8)"));
                        return;
                    }
                    if (!player.getInventory().containsAtLeast(GornikItems.getItem("I1", 1), 3)) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Chyba czegos Ci brakuje moj przyjacielu &8(&6Krysztal&8)"));
                        return;
                    }
                    playerUser.setKasa(playerUser.getKasa() - 150000000);
                    player.getInventory().removeItem(GornikItems.getItem("I1", 3));
                    user.setD4_7(true);
                    bonuses.getBonusesUser().setSilnynapotwory(bonuses.getBonusesUser().getSilnynapotwory() + 5);
                    bonuses.getBonusesUser().setDefnamoby(bonuses.getBonusesUser().getDefnamoby() + 5);
                    break;
                case 31:
                    if (!(user.isD1() && user.isD2())) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie odblokowales jeszcze pooprzednich atrybutow"));
                        return;
                    }
                    playerUser.setKasa(playerUser.getKasa() - 50000000);
                    player.getInventory().removeItem(GornikItems.getItem("I1", 1));
                    user.setD3_3(true);
                    bonuses.getBonusesUser().setPrzebiciePancerza(bonuses.getBonusesUser().getPrzebiciePancerza() + 1);
                    break;
                case 40:
                    if (!(user.isD1())) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie odblokowales jeszcze pooprzednich atrybutow"));
                        return;
                    }
                    if (playerUser.getKasa() < 150000000) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Chyba czegos Ci brakuje moj przyjacielu &8(&2$&8)"));
                        return;
                    }
                    if (!player.getInventory().containsAtLeast(GornikItems.getItem("I1", 1), 3)) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Chyba czegos Ci brakuje moj przyjacielu &8(&6Krysztal&8)"));
                        return;
                    }
                    playerUser.setKasa(playerUser.getKasa() - 150000000);
                    player.getInventory().removeItem(GornikItems.getItem("I1", 3));
                    user.setD2(true);
                    break;
                case 49:
                    playerUser.setKasa(playerUser.getKasa() - 50000000);
                    player.getInventory().removeItem(GornikItems.getItem("I1", 1));
                    user.setD1(true);
                    bonuses.getBonusesUser().setSrednieobrazenia(bonuses.getBonusesUser().getSrednieobrazenia() + 1);
                    break;
                // PRAWA STRONA
                case 0:
                    if (!(user.isD1() && user.isD2() && user.isD3_1() && user.isD4_4() && user.isD4_6() && user.isD5_4() && user.isD6_4() && user.isD7_2() && user.isD8_2())) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie odblokowales jeszcze pooprzednich atrybutow"));
                        return;
                    }
                    if (playerUser.getKasa() < 150000000) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Chyba czegos Ci brakuje moj przyjacielu &8(&2$&8)"));
                        return;
                    }
                    if (!player.getInventory().containsAtLeast(GornikItems.getItem("I1", 1), 3)) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Chyba czegos Ci brakuje moj przyjacielu &8(&6Krysztal&8)"));
                        return;
                    }
                    playerUser.setKasa(playerUser.getKasa() - 150000000);
                    player.getInventory().removeItem(GornikItems.getItem("I1", 3));
                    user.setD9_2(true);
                    bonuses.getBonusesUser().setPrzeszyciebloku(bonuses.getBonusesUser().getPrzeszyciebloku() + 5);
                    break;
                case 9:
                    if (!(user.isD1() && user.isD2() && user.isD3_1() && user.isD4_4() && user.isD4_6() && user.isD5_4() && user.isD6_4() && user.isD7_2())) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie odblokowales jeszcze pooprzednich atrybutow"));
                        return;
                    }
                    playerUser.setKasa(playerUser.getKasa() - 50000000);
                    player.getInventory().removeItem(GornikItems.getItem("I1", 1));
                    user.setD8_2(true);
                    bonuses.getBonusesUser().setSilnynaludzi(bonuses.getBonusesUser().getSilnynaludzi() + 3);
                    break;
                case 10:
                    if (!(user.isD1() && user.isD2() && user.isD3_1() && user.isD4_4() && user.isD4_6() && user.isD5_4() && user.isD6_4())) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie odblokowales jeszcze pooprzednich atrybutow"));
                        return;
                    }
                    playerUser.setKasa(playerUser.getKasa() - 50000000);
                    player.getInventory().removeItem(GornikItems.getItem("I1", 1));
                    user.setD7_2(true);
                    bonuses.getBonusesUser().setDefnaludzi(bonuses.getBonusesUser().getDefnaludzi() + 2);
                    break;
                case 11:
                    if (!(user.isD1() && user.isD2() && user.isD3_1() && user.isD4_4() && user.isD4_6() && user.isD5_4())) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie odblokowales jeszcze pooprzednich atrybutow"));
                        return;
                    }
                    if (playerUser.getKasa() < 150000000) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Chyba czegos Ci brakuje moj przyjacielu &8(&2$&8)"));
                        return;
                    }
                    if (!player.getInventory().containsAtLeast(GornikItems.getItem("I1", 1), 3)) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Chyba czegos Ci brakuje moj przyjacielu &8(&6Krysztal&8)"));
                        return;
                    }
                    playerUser.setKasa(playerUser.getKasa() - 150000000);
                    player.getInventory().removeItem(GornikItems.getItem("I1", 3));
                    user.setD6_4(true);
                    break;
                case 20:
                    if (!(user.isD1() && user.isD2() && user.isD3_1() && user.isD4_4() && user.isD4_6())) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie odblokowales jeszcze pooprzednich atrybutow"));
                        return;
                    }
                    playerUser.setKasa(playerUser.getKasa() - 50000000);
                    player.getInventory().removeItem(GornikItems.getItem("I1", 1));
                    user.setD5_4(true);
                    bonuses.getBonusesUser().setDefnaludzi(bonuses.getBonusesUser().getDefnaludzi() + 2);
                    break;
                case 29:
                    if (!(user.isD1() && user.isD2() && user.isD3_1() && user.isD4_4())) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie odblokowales jeszcze pooprzednich atrybutow"));
                        return;
                    }
                    if (playerUser.getKasa() < 150000000) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Chyba czegos Ci brakuje moj przyjacielu &8(&2$&8)"));
                        return;
                    }
                    if (!player.getInventory().containsAtLeast(GornikItems.getItem("I1", 1), 3)) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Chyba czegos Ci brakuje moj przyjacielu &8(&6Krysztal&8)"));
                        return;
                    }
                    playerUser.setKasa(playerUser.getKasa() - 150000000);
                    player.getInventory().removeItem(GornikItems.getItem("I1", 3));
                    user.setD4_6(true);
                    bonuses.getBonusesUser().setDefnaludzi(bonuses.getBonusesUser().getDefnaludzi() + 5);
                    bonuses.getBonusesUser().setSilnynaludzi(bonuses.getBonusesUser().getSilnynaludzi() + 5);
                    break;
                case 28:
                    if (!(user.isD1() && user.isD2() && user.isD3_1() && user.isD4_4() && user.isD4_6())) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie odblokowales jeszcze pooprzednich atrybutow"));
                        return;
                    }
                    playerUser.setKasa(playerUser.getKasa() - 50000000);
                    player.getInventory().removeItem(GornikItems.getItem("I1", 1));
                    user.setD5_3(true);
                    bonuses.getBonusesUser().setSilnynaludzi(bonuses.getBonusesUser().getSilnynaludzi() + 2);
                    break;
                case 27:
                    if (!(user.isD1() && user.isD2() && user.isD3_1() && user.isD4_4() && user.isD4_6() && user.isD5_3())) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie odblokowales jeszcze pooprzednich atrybutow"));
                        return;
                    }
                    playerUser.setKasa(playerUser.getKasa() - 50000000);
                    player.getInventory().removeItem(GornikItems.getItem("I1", 1));
                    user.setD6_3(true);
                    bonuses.getBonusesUser().setDefnaludzi(bonuses.getBonusesUser().getDefnaludzi() + 1);
                    break;
                case 38:
                    if (!(user.isD1() && user.isD2() && user.isD3_1())) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie odblokowales jeszcze pooprzednich atrybutow"));
                        return;
                    }
                    playerUser.setKasa(playerUser.getKasa() - 50000000);
                    player.getInventory().removeItem(GornikItems.getItem("I1", 1));
                    user.setD4_4(true);
                    bonuses.getBonusesUser().setPrzeszyciebloku(bonuses.getBonusesUser().getPrzeszyciebloku() + 3);
                    break;
                case 47:
                    if (!(user.isD1() && user.isD2() && user.isD3_1() && user.isD4_4())) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie odblokowales jeszcze pooprzednich atrybutow"));
                        return;
                    }
                    playerUser.setKasa(playerUser.getKasa() - 50000000);
                    player.getInventory().removeItem(GornikItems.getItem("I1", 1));
                    user.setD4_5(true);
                    bonuses.getBonusesUser().setPrzeszyciebloku(bonuses.getBonusesUser().getPrzeszyciebloku() + 5);
                    break;
                case 39:
                    if (!(user.isD1() && user.isD2())) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie odblokowales jeszcze pooprzednich atrybutow"));
                        return;
                    }
                    playerUser.setKasa(playerUser.getKasa() - 50000000);
                    player.getInventory().removeItem(GornikItems.getItem("I1", 1));
                    user.setD3_1(true);
                    bonuses.getBonusesUser().setPrzeszyciebloku(bonuses.getBonusesUser().getPrzeszyciebloku() + 2);
                    break;

                // LEWA STRONA
                case 8:
                    if (!(user.isD1() && user.isD2() && user.isD3_2() && user.isD4_1() && user.isD4_3() && user.isD5_2() && user.isD6_2() && user.isD7_1() && user.isD8_1())) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie odblokowales jeszcze pooprzednich atrybutow"));
                        return;
                    }
                    if (playerUser.getKasa() < 150000000) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Chyba czegos Ci brakuje moj przyjacielu &8(&2$&8)"));
                        return;
                    }
                    if (!player.getInventory().containsAtLeast(GornikItems.getItem("I1", 1), 3)) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Chyba czegos Ci brakuje moj przyjacielu &8(&6Krysztal&8)"));
                        return;
                    }
                    playerUser.setKasa(playerUser.getKasa() - 150000000);
                    player.getInventory().removeItem(GornikItems.getItem("I1", 3));
                    user.setD9_1(true);
                    bonuses.getBonusesUser().setDodatkoweobrazenia(bonuses.getBonusesUser().getDodatkoweobrazenia() + 100);
                    break;
                case 17:
                    if (!(user.isD1() && user.isD2() && user.isD3_2() && user.isD4_1() && user.isD4_3() && user.isD5_2() && user.isD6_2() && user.isD7_1())) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie odblokowales jeszcze pooprzednich atrybutow"));
                        return;
                    }
                    playerUser.setKasa(playerUser.getKasa() - 50000000);
                    player.getInventory().removeItem(GornikItems.getItem("I1", 1));
                    user.setD8_1(true);
                    bonuses.getBonusesUser().setDodatkoweobrazenia(bonuses.getBonusesUser().getDodatkoweobrazenia() + 50);
                    break;
                case 16:
                    if (!(user.isD1() && user.isD2() && user.isD3_2() && user.isD4_1() && user.isD4_3() && user.isD5_2() && user.isD6_2())) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie odblokowales jeszcze pooprzednich atrybutow"));
                        return;
                    }
                    playerUser.setKasa(playerUser.getKasa() - 50000000);
                    player.getInventory().removeItem(GornikItems.getItem("I1", 1));
                    user.setD7_1(true);
                    bonuses.getBonusesUser().setDodatkoweobrazenia(bonuses.getBonusesUser().getDodatkoweobrazenia() + 50);
                    break;
                case 15:
                    if (!(user.isD1() && user.isD2() && user.isD3_2() && user.isD4_1() && user.isD4_3() && user.isD5_2())) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie odblokowales jeszcze pooprzednich atrybutow"));
                        return;
                    }
                    if (playerUser.getKasa() < 150000000) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Chyba czegos Ci brakuje moj przyjacielu &8(&2$&8)"));
                        return;
                    }
                    if (!player.getInventory().containsAtLeast(GornikItems.getItem("I1", 1), 3)) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Chyba czegos Ci brakuje moj przyjacielu &8(&6Krysztal&8)"));
                        return;
                    }
                    playerUser.setKasa(playerUser.getKasa() - 150000000);
                    player.getInventory().removeItem(GornikItems.getItem("I1", 3));
                    user.setD6_2(true);
                    break;
                case 24:
                    if (!(user.isD1() && user.isD2() && user.isD3_2() && user.isD4_1() && user.isD4_3())) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie odblokowales jeszcze pooprzednich atrybutow"));
                        return;
                    }
                    playerUser.setKasa(playerUser.getKasa() - 50000000);
                    player.getInventory().removeItem(GornikItems.getItem("I1", 1));
                    user.setD5_2(true);
                    bonuses.getBonusesUser().setDodatkoweobrazenia(bonuses.getBonusesUser().getDodatkoweobrazenia() + 50);
                    break;
                case 33:
                    if (!(user.isD1() && user.isD2() && user.isD3_2() && user.isD4_1())) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie odblokowales jeszcze pooprzednich atrybutow"));
                        return;
                    }
                    if (playerUser.getKasa() < 150000000) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Chyba czegos Ci brakuje moj przyjacielu &8(&2$&8)"));
                        return;
                    }
                    if (!player.getInventory().containsAtLeast(GornikItems.getItem("I1", 1), 3)) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Chyba czegos Ci brakuje moj przyjacielu &8(&6Krysztal&8)"));
                        return;
                    }
                    playerUser.setKasa(playerUser.getKasa() - 150000000);
                    player.getInventory().removeItem(GornikItems.getItem("I1", 3));
                    user.setD4_3(true);
                    bonuses.getBonusesUser().setDodatkoweobrazenia(bonuses.getBonusesUser().getDodatkoweobrazenia() + 150);
                    bonuses.getBonusesUser().setSrednieobrazenia(bonuses.getBonusesUser().getSrednieobrazenia() + 4);
                    break;
                case 34:
                    if (!(user.isD1() && user.isD2() && user.isD3_2() && user.isD4_1() && user.isD4_3())) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie odblokowales jeszcze pooprzednich atrybutow"));
                        return;
                    }
                    playerUser.setKasa(playerUser.getKasa() - 50000000);
                    player.getInventory().removeItem(GornikItems.getItem("I1", 1));
                    user.setD5_1(true);
                    bonuses.getBonusesUser().setSrednieobrazenia(bonuses.getBonusesUser().getSrednieobrazenia() + 2);
                    break;
                case 35:
                    if (!(user.isD1() && user.isD2() && user.isD3_2() && user.isD4_1() && user.isD4_3() && user.isD5_1())) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie odblokowales jeszcze pooprzednich atrybutow"));
                        return;
                    }
                    playerUser.setKasa(playerUser.getKasa() - 50000000);
                    player.getInventory().removeItem(GornikItems.getItem("I1", 1));
                    user.setD6_1(true);
                    bonuses.getBonusesUser().setSrednieobrazenia(bonuses.getBonusesUser().getSrednieobrazenia() + 3);
                    break;
                case 42:
                    if (!(user.isD1() && user.isD2() && user.isD3_2())) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie odblokowales jeszcze pooprzednich atrybutow"));
                        return;
                    }
                    playerUser.setKasa(playerUser.getKasa() - 50000000);
                    player.getInventory().removeItem(GornikItems.getItem("I1", 1));
                    user.setD4_1(true);
                    bonuses.getBonusesUser().setBlokciosu(bonuses.getBonusesUser().getBlokciosu() + 3);
                    break;
                case 51:
                    if (!(user.isD1() && user.isD2() && user.isD3_2() && user.isD4_1())) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie odblokowales jeszcze pooprzednich atrybutow"));
                        return;
                    }
                    playerUser.setKasa(playerUser.getKasa() - 50000000);
                    player.getInventory().removeItem(GornikItems.getItem("I1", 1));
                    user.setD4_2(true);
                    bonuses.getBonusesUser().setDodatkoweobrazenia(bonuses.getBonusesUser().getDodatkoweobrazenia() + 100);
                    break;
                case 41:
                    if (!(user.isD1() && user.isD2())) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie odblokowales jeszcze pooprzednich atrybutow"));
                        return;
                    }
                    playerUser.setKasa(playerUser.getKasa() - 50000000);
                    player.getInventory().removeItem(GornikItems.getItem("I1", 1));
                    user.setD3_2(true);
                    bonuses.getBonusesUser().setBlokciosu(bonuses.getBonusesUser().getBlokciosu() + 2);
                    break;
                default:
                    break;
            }
            RPGCORE.getInstance().getGornikNPC().openDrzewko(player);
            RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> {
                RPGCORE.getInstance().getMongoManager().saveDataGornik(uuid, object);
                RPGCORE.getInstance().getMongoManager().saveDataBonuses(uuid, bonuses);
                RPGCORE.getInstance().getMongoManager().saveDataUser(uuid, playerUser);
            });
        }
    }
}
