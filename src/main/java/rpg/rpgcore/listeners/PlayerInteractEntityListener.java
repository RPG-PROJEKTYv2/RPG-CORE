package rpg.rpgcore.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.dungeons.DungeonStatus;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

public class PlayerInteractEntityListener implements Listener {

    private final RPGCORE rpgcore;

    public PlayerInteractEntityListener(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerInteractEntity(PlayerInteractAtEntityEvent e) {

        final Player player = e.getPlayer();
        final UUID uuid = player.getUniqueId();

        if (rpgcore.getDisabledManager().getDisabled().isDisabledNpc(Utils.removeColor(e.getRightClicked().getName())) && !(rpgcore.getUserManager().find(uuid).getRankUser().isHighStaff() && rpgcore.getUserManager().find(uuid).isAdminCodeLogin())) {
            e.setCancelled(true);
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&cTen npc zostal tymczasowo wylaczony przez administratora!"));
            return;
        }

        if (!rpgcore.getUserManager().find(player.getUniqueId()).isHellCodeLogin() && !Utils.removeColor(e.getRightClicked().getName()).equals("TELEPORTER")) {
            e.setCancelled(true);
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Zaloguj sie przy uzyciu swojego &chellcodu &7zeby wykonac te czynnosc!"));
            return;
        }

        if (e.getRightClicked().getCustomName() == null && !(e.getRightClicked() instanceof Player)) {
            e.setCancelled(true);
            return;
        }


        if (e.getRightClicked().getType().equals(EntityType.ARMOR_STAND)) {
            e.setCancelled(true);
            /*if (e.getRightClicked().getLocation().getWorld().getName().equals("demontower")) {
                player.sendMessage("x: " + e.getRightClicked().getLocation().getX());
                player.sendMessage("y: " + e.getRightClicked().getLocation().getY());
                player.sendMessage("z: " + e.getRightClicked().getLocation().getZ());
                player.sendMessage("yaw: " + e.getRightClicked().getLocation().getYaw());
                player.sendMessage("pitch: " + e.getRightClicked().getLocation().getPitch());
                player.sendMessage("rightArm X: " + ((ArmorStand) e.getRightClicked()).getRightArmPose().getX());
                player.sendMessage("rightArm Y: " + ((ArmorStand) e.getRightClicked()).getRightArmPose().getY());
                player.sendMessage("rightArm Z: " + ((ArmorStand) e.getRightClicked()).getRightArmPose().getZ());
            }*/
            return;
        }

        // MAGAZYNIER
        if (e.getRightClicked().getType().equals(EntityType.IRON_GOLEM)) {
            if (Utils.removeColor(e.getRightClicked().getName()).equals("Magazynier")) {
                e.setCancelled(true);
                rpgcore.getMagazynierNPC().openMagazynierMainGUI(player);
                return;
            }
        }

        if (e.getRightClicked().getType().equals(EntityType.VILLAGER)) {
            e.setCancelled(true);
            final String entityName = Utils.removeColor(e.getRightClicked().getName());
            // KUPIEC
            if (entityName.equalsIgnoreCase("Kupiec")) {
                rpgcore.getHandlarzNPC().openHandlarzGUI(player);
                return;
            }
        }
        if (e.getRightClicked().getType().equals(EntityType.ENDERMAN)) {
            e.setCancelled(true);
            final String entityName = Utils.removeColor(e.getRightClicked().getName());
            // KUPIEC
            if (entityName.equalsIgnoreCase("Metinolog")) {
                rpgcore.getMetinologNPC().openMetinologGUI(player);
                return;
            }
        }
        if (e.getRightClicked().getType().equals(EntityType.CREEPER)) {
            e.setCancelled(true);
            final String entityName = Utils.removeColor(e.getRightClicked().getName());
            // PRZYRODNIK
            if (entityName.equalsIgnoreCase("przyrodnik")) {
                rpgcore.getPrzyrodnikNPC().openMainGUI(player);
                return;
            }
        }
        if (e.getRightClicked().getType().equals(EntityType.PIG_ZOMBIE)) {
            e.setCancelled(true);
            final String entityName = Utils.removeColor(e.getRightClicked().getName());
            // KOLEKCJONER
            if (entityName.equalsIgnoreCase("Kolekcjoner")) {
                rpgcore.getKolekcjonerNPC().openKolekcjonerGUI(player);
                return;
            }
        }
        if (e.getRightClicked().getType().equals(EntityType.BLAZE)) {
            e.setCancelled(true);
            final String entityName = Utils.removeColor(e.getRightClicked().getName());
            // LOWCA
            if (entityName.equals("Lowca")) {
                rpgcore.getLowcaNPC().openLowcaGUI(player);
                return;
            }
        }
        if (e.getRightClicked().getType().equals(EntityType.SLIME)) {
            e.setCancelled(true);
            final String entityName = Utils.removeColor(e.getRightClicked().getName());
            // DUNGEONS
            if (entityName.equals("Dungeony")) {
                if (!rpgcore.getUserManager().find(uuid).getRankUser().isHighStaff()) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&cTen NPC jest aktualnie niedostepny dla graczy, pozniewaz trwaja nad nim prace!"));
                    return;
                }
                rpgcore.getDungeonsManager().openDungeonMenu(player);
                return;
            }
        }
        if (e.getRightClicked().getType().equals(EntityType.SHEEP)) {
            e.setCancelled(true);
            final String entityName = Utils.removeColor(e.getRightClicked().getName());
            // ITEMSHOP
            if (entityName.equals("ItemShop")) {
                rpgcore.getItemShopNPC().openItemShop(player);
                return;
            }
        }
        if (e.getRightClicked().getType().equals(EntityType.ENDERMITE)) {
            e.setCancelled(true);
            final String entityName = Utils.removeColor(e.getRightClicked().getName());
            // ITEMSHOP
            if (entityName.equals("Pomocnik Gornika")) {
                rpgcore.getGornikNPC().openPomocnikGUI(player);
                return;
            }
        }


        // NPCTY
        if (e.getRightClicked().getType() == EntityType.PLAYER) {
            e.setCancelled(true);
            final Player playerRightClicked = (Player) e.getRightClicked();
            final String entityName = Utils.removeColor(playerRightClicked.getName());

            // DUSZOLOG
            if (entityName.equalsIgnoreCase("Duszolog")) {
                rpgcore.getDuszologNPC().openMainGUI(player);
                return;
            }
            // TELEPORTER
            if (entityName.equalsIgnoreCase("TELEPORTER")) {
                rpgcore.getTeleporterNPC().openTeleporterEXPOWISKA(player);
                return;
            }
            // RYBAK
            if (entityName.equalsIgnoreCase("Rybak")) {
                if (rpgcore.getUserManager().find(player.getUniqueId()).getLvl() < 30) {
                    player.sendMessage(Utils.format(Utils.RYBAK + "&7Osiagnij przynajmniej &630 &7poziom, aby odblokowac kampanie rybaka!"));
                    return;
                }
                rpgcore.getRybakNPC().openRybakGUI(player);
                return;
            }
            // KOWAL
            if (entityName.equalsIgnoreCase("Kowal")) {
                rpgcore.getKowalNPC().openKowalMainGui(player);
                return;
            }
            // TRENER
            if (entityName.equalsIgnoreCase("Trener")) {
                rpgcore.getTrenerNPC().openTrenerGUI(player);
                return;
            }
            // KLASY

            // MEDYK
            if (entityName.equalsIgnoreCase("Medyk")) {
                if (rpgcore.getMedykNPC().find(uuid).getMedykUser().getBonus() == 50) {
                    player.sendMessage(Utils.format("&c&lMedyk &8>> &7Dalem Ci juz wszystko co moglem. Czego wiecej odemnie chcesz?"));
                    return;
                }
                rpgcore.getMedykNPC().openMedykGUI(player);
                return;
            }

            // GORNIK
            if (entityName.equalsIgnoreCase("Gornik")) {
                if (playerRightClicked.getLocation().getWorld().getName().equals("world")) {
                    rpgcore.getGornikNPC().onClick(player);
                    return;
                }
                if (playerRightClicked.getLocation().getWorld().getName().equals("Gornik")) {
                    rpgcore.getGornikNPC().openGornikGUI(player);
                    return;
                }
            }



            // LESNIK
            if (entityName.equalsIgnoreCase("Lesnik")) {
                rpgcore.getLesnikNPC().openLesnikGUI(player);
                return;
            }

            // ZMIANKI //TODO Przerobic na stol itd.
            if (entityName.equalsIgnoreCase("Zmianki")) {
                rpgcore.getZmiankiManager().openGUI(player, null);
                return;
            }

            // WYSLANNIK
            if (entityName.equalsIgnoreCase("Wyslannik")) {
                rpgcore.getWyslannikNPC().openGUI(player);
                return;
            }
            //  ...ZAGINIONY WLADCA
            if (entityName.equalsIgnoreCase("Zaginiony Wladca")) {
                if (rpgcore.getZamekNieskonczonosciManager().status == DungeonStatus.STARTED) {
                    rpgcore.getZamekNieskonczonosciManager().openWladcaGUI(player);
                    return;
                }
                return;
            }

            // TRADE
            if (player.isSneaking()) {
                if (entityName.equalsIgnoreCase("Magazynier") || entityName.equalsIgnoreCase("Kupiec") || entityName.equalsIgnoreCase("Metinolog") || entityName.equalsIgnoreCase("Przyrodnik") ||
                        entityName.equalsIgnoreCase("Kolekcjoner") || entityName.equalsIgnoreCase("Lowca") || entityName.equalsIgnoreCase("Dungeony") || entityName.equalsIgnoreCase("ItemShop") ||
                        entityName.equalsIgnoreCase("Pomocnik Gornika") || entityName.equalsIgnoreCase("Duszolog") || entityName.equalsIgnoreCase("TELEPORTER") || entityName.equalsIgnoreCase("Rybak") ||
                        entityName.equalsIgnoreCase("Kowal") || entityName.equalsIgnoreCase("Trener") || entityName.equalsIgnoreCase("Medyk") || entityName.equalsIgnoreCase("Gornik") ||
                        entityName.equalsIgnoreCase("Lesnik") || entityName.equalsIgnoreCase("Zmianki") || entityName.equalsIgnoreCase("Wyslannik") || entityName.equalsIgnoreCase("Zaginiony Wladca") ||
                        entityName.equalsIgnoreCase("czarownica") || entityName.equalsIgnoreCase("Bremu")) {
                    e.setCancelled(true);
                    return;
                }
                final UUID entityUUID = playerRightClicked.getUniqueId();

                if (rpgcore.getTradeManager().isInAcceptList(uuid)) {
                    final Inventory tradeGUi = rpgcore.getTradeManager().createTradeGUI(entityUUID, uuid);
                    player.openInventory(tradeGUi);
                    playerRightClicked.openInventory(tradeGUi);
                    rpgcore.getTradeManager().removeFromAcceptList(uuid);
                    return;
                }

                if (!(rpgcore.getTradeManager().isInTradeMapAsKey(uuid) && rpgcore.getTradeManager().isInTradeMapAsValue(entityUUID))) {
                    rpgcore.getTradeManager().putInTradeMap(uuid, entityUUID);
                    rpgcore.getTradeManager().addToAcceptList(entityUUID);
                    rpgcore.getServer().getScheduler().scheduleSyncDelayedTask(rpgcore, () -> rpgcore.getTradeManager().canceltrade(uuid, entityUUID), 600L);
                    player.sendMessage(Utils.format(Utils.TRADEPREFIX + "&7Wyslano prosbe o wymiane do &6" + entityName));
                    playerRightClicked.sendMessage(Utils.format(Utils.TRADEPREFIX + "&7Otrzymales prosbe o wymiane od gracza &6" + player.getName()));
                }

            }
        }

    }
}
