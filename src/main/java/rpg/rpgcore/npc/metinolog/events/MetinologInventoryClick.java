package rpg.rpgcore.npc.metinolog.events;

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
import rpg.rpgcore.npc.metinolog.enums.MetinologMissionGive;
import rpg.rpgcore.npc.metinolog.enums.MetinologMissionKill;
import rpg.rpgcore.npc.metinolog.objects.MetinologUser;
import rpg.rpgcore.utils.DoubleUtils;
import rpg.rpgcore.utils.globalitems.npc.MetinologItems;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

public class MetinologInventoryClick implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void metinologInventoryClick(final InventoryClickEvent e) {

        final Inventory clickedInventory = e.getClickedInventory();
        final Player player = (Player) e.getWhoClicked();
        final UUID uuid = player.getUniqueId();

        if (e.getClickedInventory() == null || e.getInventory() == null) {
            return;
        }

        final String title = clickedInventory.getTitle();
        final ItemStack item = e.getCurrentItem();

        if (Utils.removeColor(title).equals("Metinolog")) {
            e.setCancelled(true);

            if (item == null) {
                return;
            }

            final MetinologUser ms = RPGCORE.getInstance().getMetinologNPC().find(player.getUniqueId()).getMetinologUser();

            if (item.getType().equals(Material.PRISMARINE_SHARD)) {
                final MetinologMissionGive mission = MetinologMissionGive.getMission(ms.getPostepGive());
                assert mission != null;
                if (ms.getPostepMisjiGive() >= mission.getReqAmount()) {
                    ms.setPostepMisjiGive(0);
                    ms.setDodatkowedmg(ms.getDodatkowedmg() + mission.getDodatkoweDmg());
                    ms.setSrOdpo(ms.getSrOdpo() + mission.getSrOdpo());
                    ms.setPostepGive(ms.getPostepGive() + 1);

                    final Bonuses bonuses = RPGCORE.getInstance().getBonusesManager().find(uuid);

                    if (ms.getPostepGive() == ms.getPostepKill()) {
                        ms.setDmgMetiny(ms.getDmgMetiny() + 1);
                        bonuses.getBonusesUser().setDmgMetiny(bonuses.getBonusesUser().getDmgMetiny() + 1);
                        player.sendMessage(Utils.format("&b&lMetinolog &8>> &aDodatkowo za ukonczenie mapy otrzymales/-as:"));
                        player.sendMessage(Utils.format("&b&lMetinolog &8>> &c+1 obrazen w kamienie metin!"));
                    }
                    bonuses.getBonusesUser().setDodatkoweobrazenia(bonuses.getBonusesUser().getDodatkoweobrazenia() + mission.getDodatkoweDmg());
                    bonuses.getBonusesUser().setSredniadefensywa(bonuses.getBonusesUser().getSredniadefensywa() + DoubleUtils.round(mission.getSrOdpo(), 2));

                    RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> {
                        RPGCORE.getInstance().getMongoManager().saveDataMetinolog(uuid, RPGCORE.getInstance().getMetinologNPC().find(uuid));
                        RPGCORE.getInstance().getMongoManager().saveDataBonuses(uuid, bonuses);
                    });

                    RPGCORE.getInstance().getServer().broadcastMessage(Utils.format("&b&lMetinolog &8>> &f" + player.getName() + " &bukonczyl moja &f" + ms.getPostepGive() + " &bmisje (&fODDAJ&b)"));
                    player.closeInventory();
                    return;
                }

                if (!player.getInventory().containsAtLeast(MetinologItems.getItem("I" + mission.getMapa().replace(" ", "-"), 1), 1)) {
                    return;
                }

                player.getInventory().removeItem(MetinologItems.getItem("I" + mission.getMapa(), 1));
                ms.setPostepMisjiGive(ms.getPostepMisjiGive() + 1);
                RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataMetinolog(uuid, RPGCORE.getInstance().getMetinologNPC().find(uuid)));
                RPGCORE.getInstance().getMetinologNPC().openMetinologGUI(player);
                return;
            }

            if (item.getType().equals(Material.DIAMOND_SWORD)) {
                final MetinologMissionKill mission = MetinologMissionKill.getMission(ms.getPostepKill());
                assert mission != null;
                if (ms.getPostepMisjiKill() >= mission.getReqAmount()) {
                    ms.setPostepMisjiKill(0);
                    ms.setSrOdpo(ms.getSrOdpo() + mission.getSrOdpo());
                    ms.setPrzeszycie(ms.getPrzeszycie() + mission.getPrzeszycie());
                    ms.setPostepKill(ms.getPostepKill() + 1);

                    final Bonuses bonuses = RPGCORE.getInstance().getBonusesManager().find(uuid);

                    if (ms.getPostepGive() == ms.getPostepKill()) {
                        ms.setDmgMetiny(ms.getDmgMetiny() + 1);
                        bonuses.getBonusesUser().setDmgMetiny(bonuses.getBonusesUser().getDmgMetiny() + 1);
                        player.sendMessage(Utils.format("&b&lMetinolog &8>> &aDodatkowo za ukonczenie mapy otrzymales/-as:"));
                        player.sendMessage(Utils.format("&b&lMetinolog &8>> &c+1 obrazen w kamienie metin!"));
                    }

                    bonuses.getBonusesUser().setSredniadefensywa(bonuses.getBonusesUser().getSredniadefensywa() + DoubleUtils.round(mission.getSrOdpo(), 2));
                    bonuses.getBonusesUser().setPrzeszyciebloku(bonuses.getBonusesUser().getPrzeszyciebloku() + DoubleUtils.round(mission.getPrzeszycie(), 2));

                    RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> {
                        RPGCORE.getInstance().getMongoManager().saveDataMetinolog(uuid, RPGCORE.getInstance().getMetinologNPC().find(uuid));
                        RPGCORE.getInstance().getMongoManager().saveDataBonuses(uuid, bonuses);
                    });

                    RPGCORE.getInstance().getServer().broadcastMessage(Utils.format("&b&lMetinolog &8>> &f" + player.getName() + " &bukonczyl moja &f" + ms.getPostepKill() + " &bmisje (&fZNISZCZ&b)"));
                    player.closeInventory();
                }
            }
        }
    }
}
