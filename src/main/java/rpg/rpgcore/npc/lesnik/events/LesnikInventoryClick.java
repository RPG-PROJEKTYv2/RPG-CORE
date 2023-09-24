package rpg.rpgcore.npc.lesnik.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.bonuses.Bonuses;
import rpg.rpgcore.npc.lesnik.enums.LesnikMissions;
import rpg.rpgcore.npc.lesnik.objects.LesnikObject;
import rpg.rpgcore.utils.ChanceHelper;
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


        if (title.equals("Lesnik")) {
            e.setCancelled(true);

            if (slot != 13) {
                return;
            }

            final LesnikObject lesnikObject = this.rpgcore.getLesnikNPC().find(uuid);
            final LesnikMissions mission = LesnikMissions.getByNumber(lesnikObject.getUser().getMission());

            if (lesnikObject.getUser().hasCooldown()) {
                player.closeInventory();
                player.sendMessage(Utils.format("&2&lLesnik &8>> &cMusisz odczekac jeszcze &6"+ Utils.durationToString(lesnikObject.getUser().getCooldown(), false)));
                return;
            }

            if (!player.getInventory().containsAtLeast(mission.getReqItem(), 1)) {
                return;
            }
            player.getInventory().removeItem(mission.getReqItem());

            final double chance = mission.getChance();

            if (ChanceHelper.getChance(chance)) {
                lesnikObject.getUser().setProgress(lesnikObject.getUser().getProgress() + 1);
                player.sendMessage(Utils.format("&2&lLesnik &8>> &aDziekuje ci za ten przedmiot"));
                if (lesnikObject.getUser().getProgress() >= mission.getReqAmount()) {
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
                    rpgcore.getLesnikNPC().openLesnikGUI(player);

                    return;
                }
            } else {
                player.sendMessage(Utils.format("&2&lLesnik &8>> &cNiestety ten przedmiot okazal sie uszkodzony"));
            }
            lesnikObject.getUser().giveCooldown();
            rpgcore.getLesnikNPC().openLesnikGUI(player);

        }

    }
}
