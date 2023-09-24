package rpg.rpgcore.osiagniecia.events;

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
import rpg.rpgcore.osiagniecia.enums.*;
import rpg.rpgcore.osiagniecia.objects.OsUser;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

public class OsInventoryClickListener implements Listener {
    private final RPGCORE rpgcore;

    public OsInventoryClickListener(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

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

        if (title.equals("Osiagniecia")) {
            e.setCancelled(true);

            if (item == null || item.getType() == Material.STAINED_GLASS_PANE) {
                return;
            }

            switch (slot) {
                case 10:
                    rpgcore.getOsManager().openOsGracze(player);
                    return;
                case 11:
                    rpgcore.getOsManager().openOsMoby(player);
                    return;
                case 12:
                    rpgcore.getOsManager().openOsMetiny(player);
                    return;
                case 14:
                    //rpgcore.getOsManager().openOsSkrzynki(player);
                    return;
                case 15:
                    rpgcore.getOsManager().openOsNiesy(player);
                    return;
                case 16:
                    //rpgcore.getOsManager().openOsUlepszenia(player);
                    return;
                case 28:
                    rpgcore.getOsManager().openOsRybak(player);
                    return;
                case 29:
                    rpgcore.getOsManager().openOsGornik(player);
                    return;
            }
        }

        if (title.equals("Osiagniecia » Zabici Gracze")) {
            e.setCancelled(true);
            if (item == null || item.getType() == Material.STAINED_GLASS_PANE) {
                return;
            }
            if (item.getType() == Material.ARROW) {
                rpgcore.getOsManager().openOsGUI(player);
                return;
            }
            final OsUser osUser = rpgcore.getOsManager().find(uuid);
            final OsGracze osGracze = OsGracze.getByMission(Utils.getTagInt(item, "mission"));
            if (osGracze == null) {
                return;
            }
            if (osUser.getGraczeProgress() < osGracze.getReqProgress() || osUser.getGracze() < osGracze.getReqMission() || Utils.getTagBoolean(item, "done")) {
                return;
            }
            osUser.setGracze(osUser.getGracze() + 1);
            player.getInventory().addItem(osGracze.getReward());
            Bukkit.broadcastMessage(Utils.format("&6&lOsiagniecia &8>> &7Gracz &6" + player.getName() + " &7odblokowal osiagniecie &6" + item.getItemMeta().getDisplayName() + " &7!"));
            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataOs(uuid, osUser));
            rpgcore.getOsManager().openOsGracze(player);
        }

        if (title.equals("Osiagniecia » Zabite Potwory")) {
            e.setCancelled(true);
            if (item == null || item.getType() == Material.STAINED_GLASS_PANE) {
                return;
            }
            if (item.getType() == Material.ARROW) {
                rpgcore.getOsManager().openOsGUI(player);
                return;
            }
            final OsUser osUser = rpgcore.getOsManager().find(uuid);
            final OsMoby osMoby = OsMoby.getByMission(Utils.getTagInt(item, "mission"));
            if (osMoby == null) {
                return;
            }
            if (osUser.getMobyProgress() < osMoby.getReqProgress() || osUser.getMoby() < osMoby.getReqMission() || Utils.getTagBoolean(item, "done")) {
                return;
            }
            osUser.setMoby(osUser.getMoby() + 1);
            player.getInventory().addItem(osMoby.getReward());
            Bukkit.broadcastMessage(Utils.format("&6&lOsiagniecia &8>> &7Gracz &6" + player.getName() + " &7odblokowal osiagniecie &6" + item.getItemMeta().getDisplayName() + " &7!"));
            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataOs(uuid, osUser));
            rpgcore.getOsManager().openOsMoby(player);
        }

        if (title.equals("Osiagniecia » Zniszczone Metiny")) {
            e.setCancelled(true);
            if (item == null || item.getType() == Material.STAINED_GLASS_PANE) {
                return;
            }
            if (item.getType() == Material.ARROW) {
                rpgcore.getOsManager().openOsGUI(player);
                return;
            }
            final OsUser osUser = rpgcore.getOsManager().find(uuid);
            final OsMetiny osMetiny = OsMetiny.getByMission(Utils.getTagInt(item, "mission"));
            if (osMetiny == null) {
                return;
            }
            if (osUser.getMetinyProgress() < osMetiny.getReqProgress() || osUser.getMetiny() < osMetiny.getReqMission() || Utils.getTagBoolean(item, "done")) {
                return;
            }
            osUser.setMetiny(osUser.getMetiny() + 1);
            player.getInventory().addItem(osMetiny.getReward());
            Bukkit.broadcastMessage(Utils.format("&6&lOsiagniecia &8>> &7Gracz &6" + player.getName() + " &7odblokowal osiagniecie &6" + item.getItemMeta().getDisplayName() + " &7!"));
            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataOs(uuid, osUser));
            rpgcore.getOsManager().openOsMetiny(player);
        }

        if (title.equals("Osiagniecia » Otworzone Skrzynie")) {
            e.setCancelled(true);
            if (item == null || item.getType() == Material.STAINED_GLASS_PANE) {
                return;
            }
            if (item.getType() == Material.ARROW) {
                rpgcore.getOsManager().openOsGUI(player);
                return;
            }
            final OsUser osUser = rpgcore.getOsManager().find(uuid);
            final OsSkrzynki osSkrzynki = OsSkrzynki.getByMission(Utils.getTagInt(item, "mission"));
            if (osSkrzynki == null) {
                return;
            }
            if (osUser.getSkrzynkiProgress() < osSkrzynki.getReqProgress() || osUser.getSkrzynki() < osSkrzynki.getReqMission() || Utils.getTagBoolean(item, "done")) {
                return;
            }
            osUser.setSkrzynki(osUser.getSkrzynki() + 1);
            player.getInventory().addItem(osSkrzynki.getReward());
            Bukkit.broadcastMessage(Utils.format("&6&lOsiagniecia &8>> &7Gracz &6" + player.getName() + " &7odblokowal osiagniecie &6" + item.getItemMeta().getDisplayName() + " &7!"));
            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataOs(uuid, osUser));
            rpgcore.getOsManager().openOsSkrzynki(player);
        }

        if (title.equals("Osiagniecia » Znalezione Niesy")) {
            e.setCancelled(true);
            if (item == null || item.getType() == Material.STAINED_GLASS_PANE) {
                return;
            }
            if (item.getType() == Material.ARROW) {
                rpgcore.getOsManager().openOsGUI(player);
                return;
            }
            final OsUser osUser = rpgcore.getOsManager().find(uuid);
            final OsNiesy osNiesy = OsNiesy.getByMission(Utils.getTagInt(item, "mission"));
            if (osNiesy == null) {
                return;
            }
            if (osUser.getNiesyProgress() < osNiesy.getReqProgress() || osUser.getNiesy() < osNiesy.getReqMission() || Utils.getTagBoolean(item, "done")) {
                return;
            }
            osUser.setNiesy(osUser.getNiesy() + 1);
            player.getInventory().addItem(osNiesy.getReward());
            Bukkit.broadcastMessage(Utils.format("&6&lOsiagniecia &8>> &7Gracz &6" + player.getName() + " &7odblokowal osiagniecie &6" + item.getItemMeta().getDisplayName() + " &7!"));
            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataOs(uuid, osUser));
            rpgcore.getOsManager().openOsNiesy(player);
        }

        if (title.equals("Osiagniecia » Udane Ulepszenia")) {
            e.setCancelled(true);
            if (item == null || item.getType() == Material.STAINED_GLASS_PANE) {
                return;
            }
            if (item.getType() == Material.ARROW) {
                rpgcore.getOsManager().openOsGUI(player);
                return;
            }
            final OsUser osUser = rpgcore.getOsManager().find(uuid);
            final OsUlepszenia osUlepszenia = OsUlepszenia.getByMission(Utils.getTagInt(item, "mission"));
            if (osUlepszenia == null) {
                return;
            }
            if (osUser.getUlepszeniaProgress() < osUlepszenia.getReqProgress() || osUser.getUlepszenia() < osUlepszenia.getReqMission() || Utils.getTagBoolean(item, "done")) {
                return;
            }
            osUser.setUlepszenia(osUser.getUlepszenia() + 1);
            player.getInventory().addItem(osUlepszenia.getReward());
            Bukkit.broadcastMessage(Utils.format("&6&lOsiagniecia &8>> &7Gracz &6" + player.getName() + " &7odblokowal osiagniecie &6" + item.getItemMeta().getDisplayName() + " &7!"));
            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataOs(uuid, osUser));
            rpgcore.getOsManager().openOsUlepszenia(player);
        }

        if (title.equals("Osiagniecia » Udane Polowy")) {
            e.setCancelled(true);
            if (item == null || item.getType() == Material.STAINED_GLASS_PANE) {
                return;
            }
            if (item.getType() == Material.ARROW) {
                rpgcore.getOsManager().openOsGUI(player);
                return;
            }
            final OsUser osUser = rpgcore.getOsManager().find(uuid);
            final OsRybak osRybak = OsRybak.getByMission(Utils.getTagInt(item, "mission"));
            if (osRybak == null) {
                return;
            }
            if (osUser.getRybakProgress() < osRybak.getReqProgress() || osUser.getRybak() < osRybak.getReqMission() || Utils.getTagBoolean(item, "done")) {
                return;
            }
            osUser.setRybak(osUser.getRybak() + 1);
            player.getInventory().addItem(osRybak.getReward());
            Bukkit.broadcastMessage(Utils.format("&6&lOsiagniecia &8>> &7Gracz &6" + player.getName() + " &7odblokowal osiagniecie &6" + item.getItemMeta().getDisplayName() + " &7!"));
            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataOs(uuid, osUser));
            rpgcore.getOsManager().openOsRybak(player);
        }

        if (title.equals("Osiagniecia » Wydobyte Rudy")) {
            e.setCancelled(true);
            if (item == null || item.getType() == Material.STAINED_GLASS_PANE) {
                return;
            }
            if (item.getType() == Material.ARROW) {
                rpgcore.getOsManager().openOsGUI(player);
                return;
            }
            final OsUser osUser = rpgcore.getOsManager().find(uuid);
            final OsGornik osGornik = OsGornik.getByMission(Utils.getTagInt(item, "mission"));
            if (osGornik == null) {
                return;
            }
            if (osUser.getGornikProgress() < osGornik.getReqProgress() || osUser.getGornik() < osGornik.getReqMission() || Utils.getTagBoolean(item, "done")) {
                return;
            }
            osUser.setGornik(osUser.getGornik() + 1);
            player.getInventory().addItem(osGornik.getReward());
            Bukkit.broadcastMessage(Utils.format("&6&lOsiagniecia &8>> &7Gracz &6" + player.getName() + " &7odblokowal osiagniecie &6" + item.getItemMeta().getDisplayName() + " &7!"));
            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataOs(uuid, osUser));
            rpgcore.getOsManager().openOsGornik(player);
        }
    }
}
