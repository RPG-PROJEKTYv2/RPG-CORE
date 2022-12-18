package rpg.rpgcore.npc.lesnik;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.bonuses.Bonuses;
import rpg.rpgcore.utils.ChanceHelper;
import rpg.rpgcore.utils.globalitems.npc.LesnikItems;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

public class LesnikInventoryClick implements Listener {
    private final RPGCORE rpgcore;

    public LesnikInventoryClick(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClick(final InventoryClickEvent e) {
        if (e.getClickedInventory() == null) {
            return;
        }

        final Player player = (Player) e.getWhoClicked();
        final UUID uuid = player.getUniqueId();
        final String title = Utils.removeColor(e.getClickedInventory().getTitle());
        final int slot = e.getSlot();

       /* if (e.getClickedInventory().getType().equals(InventoryType.PLAYER) && Utils.removeColor(player.getOpenInventory().getTopInventory().getTitle()).equals("Lesnik")
                && item != null && item.equals(LesnikItems.POTION.getItem())) {
            player.getInventory().removeItem(item);
            player.getOpenInventory().getTopInventory().setItem(22, LesnikItems.POTION.getItem());
            return;
        }*/

        if (title.equalsIgnoreCase("Lesnik")) {
            e.setCancelled(true);

            if (slot != 13 && slot != 22) {
                player.getInventory().addItem(LesnikItems.I1.getItem());
                player.getInventory().addItem(LesnikItems.POTION.getItem());
                rpgcore.getLesnikNPC().find(uuid).getUser().setCooldown(0);
                return;
            }

            if (slot == 22) {
                if (e.getCursor().equals(LesnikItems.POTION.getItem()) && e.getClickedInventory().getItem(22) != null && e.getClickedInventory().getItem(22).getType().equals(Material.BARRIER)) {
                    player.getOpenInventory().setCursor(null);
                    e.getClickedInventory().setItem(22, LesnikItems.POTION.getItem());
                    return;
                } else {
                    player.getInventory().addItem(LesnikItems.POTION.getItem());
                    e.getClickedInventory().setItem(22, new ItemBuilder(Material.BARRIER).setName("&cBrak Mikstury Lesnika").toItemStack().clone());
                    return;
                }
            }

            final LesnikObject lesnikObject = this.rpgcore.getLesnikNPC().find(uuid);
            final LesnikMissions mission = LesnikMissions.getByNumber(lesnikObject.getUser().getMission());

            if (lesnikObject.getUser().hasCooldown()) {
                return;
            }

            if (!player.getInventory().containsAtLeast(mission.getReqItem(), 1)) {
                return;
            }
            player.getInventory().removeItem(mission.getReqItem());

           final double chance = mission.getChance() + (e.getClickedInventory().getItem(22) != null && e.getClickedInventory().getItem(22).equals(LesnikItems.POTION.getItem()) ? 50 : 0);

            if (ChanceHelper.getChance(chance)) {
                lesnikObject.getUser().setProgress(lesnikObject.getUser().getProgress() + 1);
                player.sendMessage(Utils.format("&2&lLesnik &8>> &aDziekuje ci za ten przedmiot"));
                if (lesnikObject.getUser().getProgress() == mission.getReqAmount()) {
                    final Bonuses bonuses = rpgcore.getBonusesManager().find(uuid);
                    lesnikObject.getUser().giveCooldown();
                    lesnikObject.getUser().setProgress(0);
                    lesnikObject.getUser().setMission(lesnikObject.getUser().getMission() + 1);
                    lesnikObject.getUser().setPrzeszycie(lesnikObject.getUser().getPrzeszycie() + mission.getPrzeszywka());
                    lesnikObject.getUser().setWzmKryta(lesnikObject.getUser().getWzmKryta() + mission.getWzmKryta());
                    lesnikObject.getUser().setDefNaLudzi(lesnikObject.getUser().getDefNaLudzi() + mission.getDefNaLudzi());

                    bonuses.getBonusesUser().setPrzeszyciebloku(bonuses.getBonusesUser().getPrzeszyciebloku() + mission.getPrzeszywka());
                    bonuses.getBonusesUser().setSzansanawzmocnieniekryta(bonuses.getBonusesUser().getSzansanawzmocnieniekryta() + mission.getWzmKryta());
                    bonuses.getBonusesUser().setDefnaludzi(bonuses.getBonusesUser().getDefnaludzi() + mission.getDefNaLudzi());

                    rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
                        rpgcore.getMongoManager().saveDataLesnik(lesnikObject.getId(), lesnikObject);
                        rpgcore.getMongoManager().saveDataBonuses(bonuses.getId(), bonuses);
                    });

                    rpgcore.getServer().broadcastMessage(Utils.format("&2&lLesnik &8>> &aGracz &2" + player.getName() + " &aukonczyl moja &2" + (lesnikObject.getUser().getMission() - 1) + " &amisje!"));
                    e.getClickedInventory().setItem(22, new ItemStack(Material.AIR));
                    rpgcore.getLesnikNPC().openLesnikGUI(player);

                    return;
                }
            } else {
                player.sendMessage(Utils.format("&2&lLesnik &8>> &cNiestety ten przedmiot okazal sie uszkodzony"));
            }
            lesnikObject.getUser().giveCooldown();
            e.getClickedInventory().setItem(22, new ItemStack(Material.AIR));
            rpgcore.getLesnikNPC().openLesnikGUI(player);

        }

    }
}
