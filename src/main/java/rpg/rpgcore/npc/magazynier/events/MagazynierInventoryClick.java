package rpg.rpgcore.npc.magazynier.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
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

            if (e.getCurrentItem().getItemMeta().getLore().stream().anyMatch(s -> s.contains("Wykonano!"))) return;

            if (user.getMissions().getSelectedMission() == Utils.getTagInt(item, "mission")) {
                switch (slot) {
                    case 0:
                        if (user.getMissions().isMission1done()) return;
                        break;
                    case 1:
                        if (user.getMissions().isMission2done()) return;
                        break;
                    case 2:
                        if (user.getMissions().isMission3done()) return;
                        break;
                    case 3:
                        if (user.getMissions().isMission4done()) return;
                        break;
                    case 4:
                        if (user.getMissions().isMission5done()) return;
                        break;
                }
                final MagazynierMissions mission = MagazynierMissions.getMissionById(user.getMissions().getSelectedMission());
                assert mission != null;
                if (user.getMissions().getProgress() >= mission.getReqAmount()) {
                    if (mission.getItemReward() != null) {
                        player.getInventory().addItem(mission.getItemReward());
                    }
                    user.setPoints(user.getPoints() + mission.getPoints());
                    switch (slot) {
                        case 0:
                            user.getMissions().setMission1done(true);
                            break;
                        case 1:
                            user.getMissions().setMission2done(true);
                            break;
                        case 2:
                            user.getMissions().setMission3done(true);
                            break;
                        case 3:
                            user.getMissions().setMission4done(true);
                            break;
                        case 4:
                            user.getMissions().setMission5done(true);
                            break;
                    }
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
            e.setResult(Event.Result.DENY);

            if (item == null || item.getType().equals(Material.AIR)) return;

            final MagazynierUser user = rpgcore.getMagazynierNPC().find(uuid);
            final int price = Utils.getTagInt(item, "price");
            if (user.getPoints() < price) {
                player.sendMessage(Utils.format("&b&lMagazynier &8» &7Nie posiadasz wystarczajacej ilości punktow!"));
                player.closeInventory();
                return;
            }
            switch (slot) {
                case 0:
                    if (user.isUnlocked1() && user.isUnlocked2() && user.isUnlocked3() && user.isUnlocked4() && user.isUnlocked5()) {
                        player.sendMessage(Utils.format("&b&lMagazynier &8» &7Posiadasz juz wszystkie magazyny!"));
                        player.closeInventory();
                        return;
                    }
                    if (!user.isUnlocked1()) {
                        user.setUnlocked1(true);
                    } else if (!user.isUnlocked2()) {
                        user.setUnlocked2(true);
                    } else if (!user.isUnlocked3()) {
                        user.setUnlocked3(true);
                    } else if (!user.isUnlocked4()) {
                        user.setUnlocked4(true);
                    } else {
                        user.setUnlocked5(true);
                    }
                    user.setPoints(user.getPoints() - price);
                    player.sendMessage(Utils.format("&b&lMagazynier &8» &aPomyslnie Oblokowales/-as swoj magazyn!"));
                    break;
                case 1:
                    if (user.isRemoteCommand()) {
                        player.sendMessage(Utils.format("&b&lMagazynier &8» &7Odblokowales/-as juz ta komende!"));
                        player.closeInventory();
                        return;
                    }
                    user.setRemoteCommand(true);
                    user.setPoints(user.getPoints() - price);
                    player.sendMessage(Utils.format("&b&lMagazynier &8» &aPomyslnie Odblokowales/-as komende /magazyny!"));
                    break;
            }
            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataMagazynier(user.getUuid(), user));
            rpgcore.getMagazynierNPC().openMagazynierSklepGUI(player);
        }


        if (title.equals("Lista Magazynow")) {
            e.setCancelled(true);
            if (rpgcore.getCooldownManager().hasMagazynyCooldown(uuid)) {
                player.sendMessage(Utils.format("&b&lMagazynier &8>> &cOdczekaj jeszcze chwile przed ponownym otwarciem magazynu!"));
                player.closeInventory();
                return;
            }
            final MagazynierUser user = rpgcore.getMagazynierNPC().find(uuid);
            switch (slot) {
                case 0:
                    if (user.isUnlocked1()) {
                        rpgcore.getMagazynierNPC().openMagazyn(player, user.getMagazyn1(), 1);
                        return;
                    }
                    break;
                case 1:
                    if (user.isUnlocked2()) {
                        rpgcore.getMagazynierNPC().openMagazyn(player, user.getMagazyn2(), 2);
                        return;
                    }
                    break;
                case 2:
                    if (user.isUnlocked3()) {
                        rpgcore.getMagazynierNPC().openMagazyn(player, user.getMagazyn3(), 3);
                        return;
                    }
                    break;
                case 3:
                    if (user.isUnlocked4()) {
                        rpgcore.getMagazynierNPC().openMagazyn(player, user.getMagazyn4(), 4);
                        return;
                    }
                    break;
                case 4:
                    if (user.isUnlocked5()) {
                        rpgcore.getMagazynierNPC().openMagazyn(player, user.getMagazyn5(), 5);
                        return;
                    }
                    break;
            }
        }


        if (title.contains("Lista Magazynow - ")) {
            e.setCancelled(true);
            final UUID target = UUID.fromString(title.replaceAll("Lista Magazynow - ", "").trim());
            if (rpgcore.getCooldownManager().hasMagazynyCooldown(uuid)) {
                player.sendMessage(Utils.format("&b&lMagazynier &8>> &cOdczekaj jeszcze chwile przed ponownym otwarciem magazynu!"));
                player.closeInventory();
                return;
            }
            final MagazynierUser user = rpgcore.getMagazynierNPC().find(target);
            switch (slot) {
                case 0:
                    if (user.isUnlocked1()) {
                        rpgcore.getMagazynierNPC().openMagazyn(player, target, user.getMagazyn1(), 1);
                        return;
                    }
                    break;
                case 1:
                    if (user.isUnlocked2()) {
                        rpgcore.getMagazynierNPC().openMagazyn(player, target, user.getMagazyn2(), 2);
                        return;
                    }
                    break;
                case 2:
                    if (user.isUnlocked3()) {
                        rpgcore.getMagazynierNPC().openMagazyn(player, target, user.getMagazyn3(), 3);
                        return;
                    }
                    break;
                case 3:
                    if (user.isUnlocked4()) {
                        rpgcore.getMagazynierNPC().openMagazyn(player, target, user.getMagazyn4(), 4);
                        return;
                    }
                    break;
                case 4:
                    if (user.isUnlocked5()) {
                        rpgcore.getMagazynierNPC().openMagazyn(player, target, user.getMagazyn5(), 5);
                        return;
                    }
                    break;
            }
        }

    }

}
