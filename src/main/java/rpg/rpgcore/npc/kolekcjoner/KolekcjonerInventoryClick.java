package rpg.rpgcore.npc.kolekcjoner;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.bonuses.BonusesUser;
import rpg.rpgcore.npc.kolekcjoner.enums.KolekcjonerMissions;
import rpg.rpgcore.utils.Utils;

import java.util.List;
import java.util.UUID;

public class KolekcjonerInventoryClick implements Listener {

    private final RPGCORE rpgcore;

    public KolekcjonerInventoryClick(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void kolekcjonerInventoryClick(final InventoryClickEvent e) {

        if (e.getClickedInventory() == null) {
            return;
        }

        final Inventory clickedInventory = e.getClickedInventory();
        final Player player = (Player) e.getWhoClicked();
        final UUID uuid = player.getUniqueId();
        final String clickedInventoryTitle = clickedInventory.getTitle();
        final ItemStack item = e.getCurrentItem();
        final int slot = e.getSlot();

        if (Utils.removeColor(clickedInventoryTitle).equals("Kolekcjoner")) {
            e.setCancelled(true);

            if (item.getType().equals(Material.STAINED_GLASS_PANE) || item.getType().equals(Material.PAPER)) {
                return;
            }
            final KolekcjonerUser user = rpgcore.getKolekcjonerNPC().find(uuid).getKolekcjonerUser();
            final List<Boolean> given = user.getMissionProgress();
            final KolekcjonerMissions mission = KolekcjonerMissions.getByNumber(user.getMission());
            switch (slot) {
                case 11:
                    if (given.get(0)) {
                        return;
                    }
                    if (!player.getInventory().containsAtLeast(mission.getReqItems()[0], 1)) {
                        player.getInventory().addItem(mission.getReqItems()[0]);
                        return;
                    }

                    player.getInventory().removeItem(mission.getReqItems()[0]);
                    given.set(0, true);
                    break;
                case 12:
                    if (given.get(1)) {
                        return;
                    }
                    if (!player.getInventory().containsAtLeast(mission.getReqItems()[1], 1)) {
                        player.getInventory().addItem(mission.getReqItems()[1]);
                        return;
                    }

                    player.getInventory().removeItem(mission.getReqItems()[1]);
                    given.set(1, true);
                    break;
                case 14:
                    if (given.get(2)) {
                        return;
                    }
                    if (!player.getInventory().containsAtLeast(mission.getReqItems()[2], 1)) {
                        player.getInventory().addItem(mission.getReqItems()[2]);
                        return;
                    }

                    player.getInventory().removeItem(mission.getReqItems()[2]);
                    given.set(2, true);
                    break;
                case 15:
                    if (given.get(3)) {
                        return;
                    }
                    if (!player.getInventory().containsAtLeast(mission.getReqItems()[3], 1)) {
                        player.getInventory().addItem(mission.getReqItems()[3]);
                        return;
                    }

                    player.getInventory().removeItem(mission.getReqItems()[3]);
                    given.set(3, true);
                    break;
            }
            player.sendMessage(Utils.format("&6&lKolekcjoner &8>> &7Dziekuje Ci za ten przedmiot. Wlasnie tego potrzebowalem!"));
            user.setMissionProgress(given);
            rpgcore.getKolekcjonerNPC().openKolekcjonerGUI(player);
            if (rpgcore.getKolekcjonerNPC().hasGivenBackAll(user)) {
                user.setMission(user.getMission() + 1);
                user.resetMissionProgress();
                user.setSzczescie(user.getSzczescie() + mission.getSzczescie());
                user.setSilnyNaLudzi(user.getSilnyNaLudzi() + mission.getSilnyNaLudzi());
                user.setDodatkowe(user.getDodatkowe() + mission.getDodatkowe());
                final BonusesUser bonusesUser = rpgcore.getBonusesManager().find(uuid).getBonusesUser();
                bonusesUser.setDodatkoweobrazenia(bonusesUser.getDodatkoweobrazenia() + mission.getDodatkowe());
                bonusesUser.setSilnynaludzi(bonusesUser.getSilnynaludzi() + mission.getSilnyNaLudzi());
                bonusesUser.setSzczescie(bonusesUser.getSzczescie() + mission.getSzczescie());
                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
                    rpgcore.getMongoManager().saveDataKolekcjoner(uuid, rpgcore.getKolekcjonerNPC().find(uuid));
                    rpgcore.getMongoManager().saveDataBonuses(uuid, rpgcore.getBonusesManager().find(uuid));
                });
                if (user.getMission() == 10) {
                    Bukkit.broadcastMessage(" ");
                    Bukkit.broadcastMessage(Utils.format("&6&lKolekcjoner &8>> &7Gracz &6" + player.getName() + " &7ukonczyl moja &c&lKampanie &7!"));
                    Bukkit.broadcastMessage(" ");
                } else {
                    Bukkit.broadcastMessage(Utils.format("&6&lKolekcjoner &8>> &7Gracz &6" + player.getName() + " &7ukonczyl moja &6" + (user.getMission() - 1) + " &7misje!"));
                }
                rpgcore.getKolekcjonerNPC().openKolekcjonerGUI(player);
            }
        }
    }
}
