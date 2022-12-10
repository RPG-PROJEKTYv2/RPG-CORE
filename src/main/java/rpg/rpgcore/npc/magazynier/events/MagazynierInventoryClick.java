package rpg.rpgcore.npc.magazynier.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.npc.magazynier.enums.MagazynierMissions;
import rpg.rpgcore.npc.magazynier.objects.MagazynierUser;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

public class MagazynierInventoryClick implements Listener {

    private final RPGCORE rpgcore;

    public MagazynierInventoryClick(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void magazynierInventoryClick(final InventoryClickEvent e) {

        if (e.getClickedInventory() == null) {
            return;
        }

        final Player player = (Player) e.getWhoClicked();
        final UUID uuid = player.getUniqueId();
        final String title = Utils.removeColor(e.getClickedInventory().getTitle());
        final ItemStack item = e.getCurrentItem();
        final int slot = e.getSlot();


        if (title.equals("Magazynier")) {
            e.setCancelled(true);
            if (slot == 0) {
                rpgcore.getMagazynierNPC().openMagazynyList(player);
                return;
            }
            if (slot == 1) {
                rpgcore.getMagazynierNPC().openMagazynierSklepGUI(player);
                return;
            }
            if (slot == 2) {
                rpgcore.getMagazynierNPC().openMagazynierMisjeGUI(player);
                return;
            }
            return;
        }

        if (title.equals("Magazynier - Misje")) {
            e.setCancelled(true);
            final MagazynierUser user = rpgcore.getMagazynierNPC().find(uuid);

            if (user.getMissions().getSelectedMission() == 0) {
                user.getMissions().setSelectedMission(Utils.getTagInt(item, "mission"));
                user.getMissions().setProgress(0);
                rpgcore.getMagazynierNPC().openMagazynierMisjeGUI(player);
                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataMagazynier(user.getUuid(), user));
                return;
            }
            if (user.getMissions().getSelectedMission() == Utils.getTagInt(item, "mission")) {
                final MagazynierMissions mission = MagazynierMissions.getMissionById(user.getMissions().getSelectedMission());
                assert mission != null;
                if (user.getMissions().getProgress() >= mission.getReqAmount()) {
                    if (mission.getItemReward() != null) {
                        player.getInventory().addItem(mission.getItemReward());
                    }
                    user.setPoints(user.getPoints() + mission.getPoints());
                    user.getMissions().setSelectedMission(0);
                    user.getMissions().setProgress(0);
                    rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataMagazynier(user.getUuid(), user));
                    rpgcore.getMagazynierNPC().openMagazynierMisjeGUI(player);
                    return;
                }
                return;
            }

            user.getMissions().setSelectedMission(Utils.getTagInt(item, "mission"));
            user.getMissions().setProgress(0);
            rpgcore.getMagazynierNPC().openMagazynierMisjeGUI(player);
            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataMagazynier(user.getUuid(), user));
        }


        if (title.equals("Magazynier - Sklep")) {
            e.setCancelled(true);
            final MagazynierUser user = rpgcore.getMagazynierNPC().find(uuid);
            final int price = Utils.getTagInt(item, "price");
            final int amount = Utils.getTagInt(item, "amount");
            if (user.getPoints() >= price) {
                user.setPoints(user.getPoints() - price);
                player.getInventory().addItem(item);
                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataMagazynier(user.getUuid(), user));
                rpgcore.getMagazynierNPC().openMagazynierSklepGUI(player);
            }
        }







        if (title.equals("Lista Magazynow")) {
            e.setCancelled(true);
            final MagazynierUser user = rpgcore.getMagazynierNPC().find(uuid);
            switch (slot) {
                case 0:
                    if (user.isUnlocked1()) {
                       rpgcore.getMagazynierNPC().openMagazyn(player, user.getMagazyn1());
                       return;
                    }
                    break;
                case 1:
                    if (user.isUnlocked2()) {
                        rpgcore.getMagazynierNPC().openMagazyn(player, user.getMagazyn2());
                        return;
                    }
                    break;
                case 2:
                    if (user.isUnlocked3()) {
                        rpgcore.getMagazynierNPC().openMagazyn(player, user.getMagazyn3());
                        return;
                    }
                    break;
                case 3:
                    if (user.isUnlocked4()) {
                        rpgcore.getMagazynierNPC().openMagazyn(player, user.getMagazyn4());
                        return;
                    }
                    break;
                case 4:
                    if (user.isUnlocked5()) {
                        rpgcore.getMagazynierNPC().openMagazyn(player, user.getMagazyn5());
                        return;
                    }
                    break;
            }
        }
    }

}
