package rpg.rpgcore.npc.trener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.bonuses.Bonuses;
import rpg.rpgcore.utils.Utils;

import java.util.HashMap;
import java.util.UUID;

public class TrenerInventoryClick implements Listener {

    private final RPGCORE rpgcore;

    public TrenerInventoryClick(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void trenerInventoryClick(final InventoryClickEvent e) {

        final Inventory clickedInventory = e.getClickedInventory();
        final Player player = (Player) e.getWhoClicked();
        final UUID uuid = player.getUniqueId();
        final HashMap<Integer, ItemStack> itemMapToRemove = new HashMap<>();

        if (e.getClickedInventory() == null) {
            return;
        }

        final String clickedInventoryTitle = clickedInventory.getTitle();
        final ItemStack clickedItem = e.getCurrentItem();
        final int clickedSlot = e.getSlot();

        if (clickedInventoryTitle.contains("Trener - Punkty: ")) {
            e.setCancelled(true);
            if (clickedSlot != 8 && rpgcore.getTrenerNPC().getPoints(uuid) <= 0) {
                player.closeInventory();
                player.sendMessage(Utils.format(Utils.TRENER + "&cNie posiadasz zadnych punktow do rozdania"));
                return;
            }

            if (clickedSlot != 8 && rpgcore.getCooldownManager().hasTrenerCooldown(uuid)) {
                return;
            }

            if (clickedSlot == 8 && clickedItem.getType().equals(Material.BEACON) && (rpgcore.getTrenerNPC().getSrednidmg(uuid) == 20 && rpgcore.getTrenerNPC().getSrednidef(uuid) == 20 && rpgcore.getTrenerNPC().getHP(uuid) == 10 && rpgcore.getTrenerNPC().getBlok(uuid) == 10 && rpgcore.getTrenerNPC().getPrzeszywka(uuid) == 20
                    && rpgcore.getTrenerNPC().getSilnynaludzi(uuid) == 20 && rpgcore.getTrenerNPC().getDefnaludzi(uuid) == 20 && rpgcore.getTrenerNPC().getKyt(uuid) == 10)) {
                player.sendMessage(Utils.format("&cTa opcja jest na razie nie dostępna!"));
                player.closeInventory();
                return;
            }
            final Bonuses bonuses = RPGCORE.getInstance().getBonusesManager().find(uuid);
            switch (clickedSlot) {
                case 0:
                    if (rpgcore.getTrenerNPC().getSrednidmg(uuid) == 20) {
                        player.sendMessage(Utils.format(Utils.TRENER + "&cTa umiejetnosc jest juz rozwinieta na maksymalny poziom"));
                        player.closeInventory();
                        return;
                    }
                    rpgcore.getTrenerNPC().updateSredniDmg(uuid, 1);
                    break;
                case 1:
                    if (rpgcore.getTrenerNPC().getSrednidef(uuid) == 20) {
                        player.sendMessage(Utils.format(Utils.TRENER + "&cTa umiejetnosc jest juz rozwinieta na maksymalny poziom"));
                        player.closeInventory();
                        return;
                    }
                    rpgcore.getTrenerNPC().updateSredniDef(uuid, 1);
                    break;
                case 2:
                    if (rpgcore.getTrenerNPC().getHP(uuid) == 10) {
                        player.sendMessage(Utils.format(Utils.TRENER + "&cTa umiejetnosc jest juz rozwinieta na maksymalny poziom"));
                        player.closeInventory();
                        return;
                    }
                    rpgcore.getTrenerNPC().updateHP(uuid, 1);
                    break;
                case 3:
                    if (rpgcore.getTrenerNPC().getBlok(uuid) == 10) {
                        player.sendMessage(Utils.format(Utils.TRENER + "&cTa umiejetnosc jest juz rozwinieta na maksymalny poziom"));
                        player.closeInventory();
                        return;
                    }
                    rpgcore.getTrenerNPC().updateBlok(uuid, 1);
                    break;
                case 4:
                    if (rpgcore.getTrenerNPC().getPrzeszywka(uuid) == 20) {
                        player.sendMessage(Utils.format(Utils.TRENER + "&cTa umiejetnosc jest juz rozwinieta na maksymalny poziom"));
                        player.closeInventory();
                        return;
                    }
                    rpgcore.getTrenerNPC().updatePrzeszywka(uuid, 1);
                    break;
                case 5:
                    if (rpgcore.getTrenerNPC().getSilnynaludzi(uuid) == 20) {
                        player.sendMessage(Utils.format(Utils.TRENER + "&cTa umiejetnosc jest juz rozwinieta na maksymalny poziom"));
                        player.closeInventory();
                        return;
                    }
                    rpgcore.getTrenerNPC().updateSilnynaludzi(uuid, 1);
                    break;
                case 6:
                    if (rpgcore.getTrenerNPC().getDefnaludzi(uuid) == 20) {
                        player.sendMessage(Utils.format(Utils.TRENER + "&cTa umiejetnosc jest juz rozwinieta na maksymalny poziom"));
                        player.closeInventory();
                        return;
                    }
                    rpgcore.getTrenerNPC().updateDefnaludzi(uuid, 1);
                    break;
                case 7:
                    if (rpgcore.getTrenerNPC().getKyt(uuid) == 10) {
                        player.sendMessage(Utils.format(Utils.TRENER + "&cTa umiejetnosc jest juz rozwinieta na maksymalny poziom"));
                        player.closeInventory();
                        return;
                    }
                    rpgcore.getTrenerNPC().updateKyt(uuid, 1);
                    break;
                case 8:
                    if (rpgcore.getUserManager().find(uuid).getKasa() < 5000000) {
                        player.sendMessage(Utils.format(Utils.TRENER + "&cNie posiadasz tylu pieniedzy, zeby zresetowac swoje statystyki"));
                        player.closeInventory();
                        return;
                    }
                    rpgcore.getTrenerNPC().reset(uuid);
                    rpgcore.getUserManager().find(uuid).setKasa(rpgcore.getUserManager().find(uuid).getKasa() - 5000000);
                    rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataUser(uuid, rpgcore.getUserManager().find(uuid)));
                    player.closeInventory();
                    player.sendMessage(Utils.format(Utils.TRENER + "&aPomyslnie zresetowałes/as swoj postep"));
                    return;
            }
            rpgcore.getTrenerNPC().updatePoints(uuid, -1);
            rpgcore.getCooldownManager().givePlayerTrenerCooldown(uuid);
            player.closeInventory();
            player.sendMessage(Utils.format(Utils.TRENER + "&aPomyslnie zwiekszyles/as " + clickedItem.getItemMeta().getDisplayName() + " &ao 1 %"));
            rpgcore.getTrenerNPC().openTrenerGUI(player);

        }
    }
}
