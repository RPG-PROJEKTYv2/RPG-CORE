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
import rpg.rpgcore.utils.GlobalItems.npc.LesnikItems;
import rpg.rpgcore.utils.RandomItems;
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
        final ItemStack item = e.getCurrentItem();
        final int slot = e.getSlot();

        if (title.equalsIgnoreCase("Lesnik")) {
            e.setCancelled(true);

            if (e.getCursor().equals(LesnikItems.POTION.getItem()) && slot == 22) {
                e.getCursor().setType(Material.AIR);
                e.getClickedInventory().setItem(22, LesnikItems.POTION.getItem());
                return;
            }

            if (slot != 13 && slot != 22) {
                player.getInventory().addItem(LesnikItems.I1.getItem());
                player.getInventory().addItem(LesnikItems.POTION.getItem());
                return;
            }

            if (slot == 22) {
                if (item.getType().equals(Material.BARRIER)) {
                    return;
                }

                if (item.equals(LesnikItems.POTION.getItem())) {
                    e.getClickedInventory().setItem(22, new ItemStack(Material.AIR));
                    player.getInventory().addItem(LesnikItems.POTION.getItem());
                    return;
                }
            }

            final LesnikObject lesnikObject = this.rpgcore.getLesnikNPC().find(uuid);
            final LesnikMissions mission = LesnikMissions.getByNumber(lesnikObject.getUser().getMission());

            if (lesnikObject.getUser().hasCooldown()) {
                return;
            }

            if (lesnikObject.getUser().getProgress() >= mission.getReqAmount()) {
                final Bonuses bonuses = rpgcore.getBonusesManager().find(uuid);
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

            if (!player.getInventory().containsAtLeast(mission.getReqItem(), 1)) {
                player.getInventory().addItem(mission.getReqItem());
                return;
            }
            player.getInventory().removeItem(mission.getReqItem());

            final RandomItems<String> oddawanie = new RandomItems<>();
            if (e.getClickedInventory().getItem(22).equals(LesnikItems.POTION.getItem())) {
                oddawanie.add(1, "pozytywnie");
            } else {
                oddawanie.add(0.5, "pozytywnie");
                oddawanie.add(0.5, "negatywnie");
            }
            if (oddawanie.next().equals("pozytywnie")) {
                lesnikObject.getUser().setProgress(lesnikObject.getUser().getProgress() + 1);
                player.sendMessage(Utils.format("&2&lLesnik &8>> &aDziekuje ci za ten przedmiot"));
            } else {
                player.sendMessage(Utils.format("&2&lLesnik &8>> &cNiestety ten przedmiot okazal sie uszkodzony"));
            }
            lesnikObject.getUser().giveCooldown();
            e.getClickedInventory().setItem(22, new ItemStack(Material.AIR));
            rpgcore.getLesnikNPC().openLesnikGUI(player);

        }
    }
}
