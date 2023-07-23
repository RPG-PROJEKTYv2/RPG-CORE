package rpg.rpgcore.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.dungeons.DungeonStatus;
import rpg.rpgcore.user.User;
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
        final User user = rpgcore.getUserManager().find(player.getUniqueId());


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

        // NPCTY ===========================================================================//
        // TELEPORTER
        if (e.getRightClicked().getType().equals(EntityType.MAGMA_CUBE)) {
            if (Utils.removeColor(e.getRightClicked().getName()).equals("TELEPORTER")) {
                e.setCancelled(true);
                rpgcore.getTeleporterNPC().openTeleporterEXPOWISKA(player);
                return;
            }
        }
        // HANDLARZ
        if (e.getRightClicked().getType().equals(EntityType.VILLAGER)) {
            if (Utils.removeColor(e.getRightClicked().getName()).equals("Handlarz")) {
                rpgcore.getHandlarzNPC().openHandlarzGUI(player);
                return;
            }
        }
        // MAGAZYNIER
        if (e.getRightClicked().getType().equals(EntityType.IRON_GOLEM)) {
            if (Utils.removeColor(e.getRightClicked().getName()).equals("Magazynier")) {
                e.setCancelled(true);
                rpgcore.getMagazynierNPC().openMagazynierMainGUI(player);
                return;
            }
        }
        // PRZYRODNIK
        if (e.getRightClicked().getType().equals(EntityType.CREEPER)) {
            if (Utils.removeColor(e.getRightClicked().getName()).equals("Przyrodnik")) {
                e.setCancelled(true);
                rpgcore.getPrzyrodnikNPC().openMainGUI(player);
                return;
            }
        }



        if (e.getRightClicked().getType().equals(EntityType.ENDERMAN)) {
            e.setCancelled(true);
            final String entityName = Utils.removeColor(e.getRightClicked().getName());
            // METINOLOG
            if (entityName.equalsIgnoreCase("Metinolog")) {
                rpgcore.getMetinologNPC().openMetinologGUI(player);
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
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&cTen NPC jest aktualnie niedostepny dla graczy, bedzie on dodany w update!"));
                    return;
                }
                rpgcore.getDungeonsManager().openDungeonMenu(player);
                return;
            }
        }
        if (e.getRightClicked().getType().equals(EntityType.WITCH)) {
            e.setCancelled(true);
            final String entityName = Utils.removeColor(e.getRightClicked().getName());
            if (entityName.equals("Czarownica")) {
                rpgcore.getCzarownicaNPC().click(player);
                return;
            }
        }
        if (e.getRightClicked().getType().equals(EntityType.IRON_GOLEM)) {
            e.setCancelled(true);
            final String entityName = Utils.removeColor(e.getRightClicked().getName());
            if (e.getRightClicked().getLocation().getWorld().getName().equals("60-70map")) {
                if (entityName.equalsIgnoreCase("Mistyczny Kowal")) {
                    if (user.getLvl() < 65) {
                        player.sendMessage(Utils.format("&7&l&kMistyczny Kowal&8 >> &c..."));
                        return;
                    }
                    rpgcore.getMistycznyKowalManager().openGUI(player);
                    return;
                }
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
            // RYBAK
            if (entityName.equalsIgnoreCase("Wloczykij")) {
                if (rpgcore.getUserManager().find(player.getUniqueId()).getLvl() < 30) {
                    player.sendMessage(Utils.format("&3&lWloczykij &8>> &7Osiagnij przynajmniej &630 &7poziom!"));
                    return;
                }
                rpgcore.getRybakNPC().clickWloczykij(player);
                return;
            }
            // KOWAL
            if (entityName.equalsIgnoreCase("Kowal")) {
                rpgcore.getKowalNPC().openKowalMainGui(player);
                return;
            }
            // KLASY
            if (entityName.equalsIgnoreCase("Dowodca Strazy")) {
                rpgcore.getKlasyManager().openGUI(player);
                return;
            }

            // GORNIK
            if (entityName.equalsIgnoreCase("Gornik")) {
                if (playerRightClicked.getLocation().getWorld().getName().equals("world")) {
                    rpgcore.getGornikNPC().onClick(player);
                    return;
                }
                if (playerRightClicked.getLocation().getWorld().getName().equals("Kopalnia")) {
                    rpgcore.getGornikNPC().openGornik(player);
                    return;
                }
            }



            // LESNIK
            if (entityName.equalsIgnoreCase("Lesnik")) {
                if (user.getLvl() > 29) {
                    rpgcore.getLesnikNPC().openLesnikGUI(player);
                    return;
                } else {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&cWroc jak osiagniesz 30 poziom!"));
                }
                return;
            }

            // WYSLANNIK
            if (entityName.equalsIgnoreCase("Wyslannik")) {
                rpgcore.getWyslannikNPC().openGUI(player);
                return;
            }
            // MEDRZEC
            if (entityName.equalsIgnoreCase("Medrzec")) {
                rpgcore.getMedrzecNPC().openMedrzecGUI(player);
                return;
            }
            // PUSTELNIK
            if (entityName.equalsIgnoreCase("Pustelnik")) {
                rpgcore.getPustelnikNPC().click(player);
                return;
            }
            // MISTRZ YANG
            if (entityName.equalsIgnoreCase("Mistrz Yang")) {
                rpgcore.getMistrzYangNPC().click(player);
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
                if (entityName.equalsIgnoreCase("Magazynier") || entityName.equalsIgnoreCase("Handlarz") || entityName.equalsIgnoreCase("Metinolog") || entityName.equalsIgnoreCase("Przyrodnik") ||
                        entityName.equalsIgnoreCase("Kolekcjoner") || entityName.equalsIgnoreCase("Lowca") || entityName.equalsIgnoreCase("Dungeony") || entityName.equalsIgnoreCase("ItemShop") ||
                        entityName.equalsIgnoreCase("Pomocnik Gornika") || entityName.equalsIgnoreCase("Duszolog") || entityName.equalsIgnoreCase("TELEPORTER") || entityName.equalsIgnoreCase("Rybak") ||
                        entityName.equalsIgnoreCase("Kowal") || entityName.equalsIgnoreCase("Trener") || entityName.equalsIgnoreCase("Medrzec") || entityName.equalsIgnoreCase("Gornik") ||
                        entityName.equalsIgnoreCase("Lesnik") || entityName.equalsIgnoreCase("Zmianki") || entityName.equalsIgnoreCase("Wyslannik") || entityName.equalsIgnoreCase("Zaginiony Wladca") ||
                        entityName.equalsIgnoreCase("czarownica") || entityName.equalsIgnoreCase("Bremu")) {
                    e.setCancelled(true);
                    return;
                }
                final UUID entityUUID = playerRightClicked.getUniqueId();

                if (rpgcore.getTradeManager().isInTradeRequestMap(uuid)) {
                    // create trade gui
                    rpgcore.getTradeManager().removeTradeRequest(entityUUID, uuid);
                    rpgcore.getTradeManager().createTrade(entityUUID, uuid, playerRightClicked, player);
                    return;
                }

                if (!rpgcore.getTradeManager().getTradeRequestMap().containsKey(uuid)) {
                    rpgcore.getTradeManager().sendTradeRequest(uuid, entityUUID);
                    rpgcore.getServer().getScheduler().scheduleSyncDelayedTask(rpgcore, () -> {
                        if (rpgcore.getTradeManager().isInTradeRequestMap(uuid) || rpgcore.getTradeManager().isInTradeRequestMap(entityUUID)) {
                            rpgcore.getTradeManager().removeTradeRequest(uuid, entityUUID);
                            if (playerRightClicked != null && playerRightClicked.isOnline() && player != null && player.isOnline()) {
                                playerRightClicked.sendMessage(Utils.format(Utils.TRADEPREFIX + "&cProsba o wymiane od gracza &6" + player.getName() + " &cwygasla!"));
                                player.sendMessage(Utils.format(Utils.TRADEPREFIX + "&cProsba o wymiane do gracza &6" + playerRightClicked.getName() + " &cwygasla!"));
                            }
                        }
                    }, 200L);
                    player.sendMessage(Utils.format(Utils.TRADEPREFIX + "&7Wyslano prosbe o wymiane do &6" + entityName));
                    playerRightClicked.sendMessage(Utils.format(Utils.TRADEPREFIX + "&7Otrzymales prosbe o wymiane od gracza &6" + player.getName()));
                }

            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInteract(final PlayerInteractEntityEvent e) {
        if (e.getRightClicked() instanceof ItemFrame && !rpgcore.getUserManager().find(e.getPlayer().getUniqueId()).getRankUser().isHighStaff()) {
            e.setCancelled(true);
        }
    }
}
