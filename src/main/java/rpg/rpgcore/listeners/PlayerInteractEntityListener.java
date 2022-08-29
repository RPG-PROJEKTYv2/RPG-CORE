package rpg.rpgcore.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.klasy.objects.KlasaUser;
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

        if (e.getRightClicked().getType().equals(EntityType.IRON_GOLEM)) {
            final String entityName = Utils.removeColor(e.getRightClicked().getName());
            // MAGAZYNIER
            if (entityName.equalsIgnoreCase("Magazynier")) {
                rpgcore.getMagazynierNPC().openMagazynierMain(player);
                return;
            }
        }

        if (e.getRightClicked().getType().equals(EntityType.VILLAGER)) {
            final String entityName = Utils.removeColor(e.getRightClicked().getName());
            // KUPIEC
            if (entityName.equalsIgnoreCase("Kupiec")) {
                rpgcore.getKupiecNPC().openKupiecInventory(player);
                return;
            }
        }

        if (e.getRightClicked().getType().equals(EntityType.WITCH)) {
            final String entityName = Utils.removeColor(e.getRightClicked().getName());
            if (rpgcore.getklasyHelper().find(uuid).getKlasaUser() != null) {
                KlasaUser user = rpgcore.getklasyHelper().find(uuid).getKlasaUser();
                if (entityName.equalsIgnoreCase(".")) {
                    if (!user.getName().equals("Mag")) {
                        player.sendMessage(Utils.format("&5&lMag &8>> &dMagia &7to nie zabawa dla tak niedojrzalych &6chlopcow &7jak ty. &7Wroc do mnie, kiedy wybierzesz wlasciwa sciezka &5mocy&7."));
                        return;
                    }
                    //rpgcore.getWojownikNPC().openWojownikMainGUI(player);
                    return;
                }
            }
        }

        // NPCTY
        if (e.getRightClicked().getType() == EntityType.PLAYER) {

            final Player playerRightClicked = (Player) e.getRightClicked();
            final String entityName = Utils.removeColor(playerRightClicked.getName());

            // DUSZOLOG
            if (entityName.equalsIgnoreCase("Duszolog")) {
                player.openInventory(rpgcore.getDuszologNPC().duszologMAIN());
                return;
            }
            // TELEPORTER
            if (entityName.equalsIgnoreCase("TELEPORTER")) {
                rpgcore.getTeleporterNPC().openTeleporterMAIN(player);
                return;
            }
            // RYBAK
            if (entityName.equalsIgnoreCase("Rybak")) {
                rpgcore.getRybakNPC().openRybakGUI(player);
                return;
            }
            // KOWAL
            if (entityName.equalsIgnoreCase("Kowal")) {
                rpgcore.getKowalNPC().openKowalMainGui(player);
                return;
            }
            // KOLEKCJONER
            if (entityName.equalsIgnoreCase("Kolekcjoner")) {
                rpgcore.getKolekcjonerNPC().openKolekcjonerGUI(player);
                return;
            }
            // TRENER
            if (entityName.equalsIgnoreCase("Trener")) {
                rpgcore.getTrenerNPC().openTrenerGUI(player);
                return;
            }
            // Metinolog
            if (entityName.equalsIgnoreCase("Metinolog")) {
                rpgcore.getMetinologNPC().openMetinologGUI(player);
                return;
            }
            // KLASY
            if (rpgcore.getklasyHelper().find(uuid).getKlasaUser() != null) {
                KlasaUser user = rpgcore.getklasyHelper().find(uuid).getKlasaUser();
                if (entityName.equalsIgnoreCase("Wojownik")) {
                    if (!user.getName().equals("Wojownik")) {
                        player.sendMessage(Utils.format("&c&lWojownik &8>> &7Niestety nie mam nic do powiedzenia &cslabeuszom. &7Wroc do mnie jak zmezniejesz i wezmiesz &costrze &7do reki."));
                        return;
                    }
                    rpgcore.getKlasyNPC().openMainGUI(player);
                    return;
                }

                if (entityName.equalsIgnoreCase("Obronca")) {
                    if (!user.getName().equals("Obronca")) {
                        player.sendMessage(Utils.format("&a&lObronca &8>> &7Niestety tylko prawdziwi rycerze moga sie ode mnie uczyc. &7Wroc do mnie jak odziejesz zbroje i zlozysz &aprzysiege rycerska."));
                        return;
                    }
                    //rpgcore.getWojownikNPC().openWojownikMainGUI(player);
                    return;
                }
            }
            // MEDYK
            if (entityName.equalsIgnoreCase("Medyk")) {
                rpgcore.getMedykNPC().openMedykGUI(player);
                return;
            }

            // GORNIK
            if (entityName.equalsIgnoreCase("Gornik")) {
                if (playerRightClicked.getLocation().getWorld().getName().equals("world")) {
                    rpgcore.getGornikNPC().onClick(player);
                    return;
                }
                if (playerRightClicked.getLocation().getWorld().getName().equals("kopalnia")) {
                    rpgcore.getGornikNPC().openGornikGUI(player);
                    return;
                }
            }

            // TRADE
            if (player.isSneaking()) {
                if (entityName.equalsIgnoreCase("trener") || entityName.equalsIgnoreCase("kolekcjoner") ||
                        entityName.equalsIgnoreCase("magazynier") || entityName.equalsIgnoreCase("medyk") ||
                        entityName.equalsIgnoreCase("czarodziej") || entityName.equalsIgnoreCase("itemshop") ||
                        entityName.equalsIgnoreCase("lowca") || entityName.equalsIgnoreCase("lesnik") ||
                        entityName.equalsIgnoreCase("straznik") || entityName.equalsIgnoreCase("alchemik") ||
                        entityName.equalsIgnoreCase("rybak") || entityName.equalsIgnoreCase("dungeony") ||
                        entityName.equalsIgnoreCase("kupiec") || entityName.equalsIgnoreCase("duszolog")) {
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
