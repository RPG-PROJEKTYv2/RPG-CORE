package rpg.rpgcore.npc.rybak.events;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.npc.rybak.objects.RybakUser;
import rpg.rpgcore.npc.rybak.objects.StaruszekUser;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.npc.RybakItems;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class RybakInteractListener implements Listener {

    private final RPGCORE rpgcore;

    public RybakInteractListener(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void rybakPlayerInteract(final PlayerInteractAtEntityEvent e) {

        final Player player = e.getPlayer();
        final UUID uuid = player.getUniqueId();


        if (e.getRightClicked().getType() != EntityType.ARMOR_STAND) {
            return;
        }


        if (!rpgcore.getRybakNPC().checkIfMapContainsArmorStand(e.getRightClicked().getLocation())) return;

        e.setCancelled(true);
        if (rpgcore.getUserManager().find(uuid).getLvl() < 30) {
            player.sendMessage(Utils.format("&3&lWloczykij &8>> &7Osiagnij &c30 &7poziom, zeby to zebrac!"));
            return;
        }
        if (rpgcore.getDisabledManager().getDisabled().isDisabledNpc("Wloczykij")) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&cTen npc zostal tymczasowo wylaczony przez administratora!"));
            return;
        }
        final ArmorStand as = (ArmorStand) e.getRightClicked();

        final RybakUser user = rpgcore.getRybakNPC().find(uuid);
        final int key = rpgcore.getRybakNPC().getKeyByValue(as);
        if (key == -1) {
            player.sendMessage(Utils.format("&3&lWloczykij &8>> &cCos poszlo nie tak!"));
            return;
        }
        if (user.getClickedArmorStands().contains(key)) {
            player.sendMessage(Utils.format("&3&lWloczykij &8>> &7Juz to zebrales!"));
            return;
        }

        final Material type = (as.getItemInHand() == null || as.getItemInHand().getType().equals(Material.AIR) ? as.getHelmet().getType() : as.getItemInHand().getType());

        switch (type) {
            case WOOD:
                player.getInventory().addItem(RybakItems.I1.getItemStack().clone());
                break;
            case STICK:
                player.getInventory().addItem(RybakItems.I2.getItemStack().clone());
                break;
            case WEB:
                player.getInventory().addItem(RybakItems.I3.getItemStack().clone());
                break;
        }
        player.sendMessage(Utils.format("&3&lWloczykij &8>> &7Zebrales jeden z moich przedmiotow&7!"));
        user.getClickedArmorStands().add(key);
        user.save();
    }

    private final List<Chunk> blindWaterChunks = Arrays.asList(
            Bukkit.getWorld("world").getChunkAt(-6, -10),
            Bukkit.getWorld("world").getChunkAt(-7, -10),
            Bukkit.getWorld("world").getChunkAt(-6, -11),
            Bukkit.getWorld("world").getChunkAt(-7, -11)
    );

    @EventHandler(priority = EventPriority.LOWEST)
    public void onWalkInWater(final PlayerMoveEvent e) {
        if (!e.getPlayer().getWorld().getName().equals("world")) return;
        if (!blindWaterChunks.contains(e.getPlayer().getLocation().getChunk())) return;
        if (e.getPlayer().getLocation().getY() >= 95) {
            e.getPlayer().removePotionEffect(PotionEffectType.BLINDNESS);
            return;
        }
        e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 1));
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBoatPlace(final PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (e.getPlayer().getItemInHand().getType() == Material.BOAT) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onWyspa1Interact(final PlayerInteractEvent e) {
        if (!e.getPlayer().getWorld().getName().equals("Rybak")) return;
        if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR)
            return;
        if (e.getClickedBlock() == null || e.getClickedBlock().getType() == Material.AIR) return;
        if (!e.getClickedBlock().getType().equals(Material.FURNACE)) return;
        if (!e.getClickedBlock().getLocation().getChunk().equals(Bukkit.getWorld("Rybak").getChunkAt(2, -10))) return;

        e.setCancelled(true);
        e.setUseInteractedBlock(Event.Result.DENY);
        e.setUseItemInHand(Event.Result.DENY);

        final Player player = e.getPlayer();
        final StaruszekUser user = rpgcore.getRybakNPC().find(player.getUniqueId()).getStaruszekUser();

        if (e.getItem() == null || e.getItem().getType() == Material.AIR) {
            player.sendMessage(Utils.format("&6&lStaruszek &8>> &7W piecu jest jeszcze &e" + user.getPaliwo() + " &7paliwa!"));
            return;
        }


        if (!(user.getMission() == 6 || user.getMission() == 7 || user.getMission() == 8 || user.getMission() == 9 || user.getMission() == 10)) {
            player.sendMessage(Utils.format("&6&lStaruszek &8>> &cTo nie ma teraz znaczenia! Masz inne zadanie do wykonania..."));
            return;
        }

        if (!e.getItem().hasItemMeta() || !e.getItem().getItemMeta().hasDisplayName()) return;


        switch (e.getItem().getType()) {
            case RAW_FISH:

                switch (e.getItem().getDurability()) {
                    case 1:
                        if (user.getMission() != 6) {
                            player.sendMessage(Utils.format("&6&lStaruszek &8>> &cNie tego teraz potrzebuje!!!"));
                            return;
                        }

                        if (!player.getInventory().containsAtLeast(RybakItems.I6.getItemStack().clone(), 1)) {
                            player.sendMessage(Utils.format("&6&lStaruszek &8>> &cNie posiadasz wymaganego przedmiotu!"));
                            return;
                        }

                        if (user.getPaliwo() < 1) {
                            player.sendMessage(Utils.format("&6&lStaruszek &8>> &7To chyba nie wystarczy... &cDorzuc no troche &8Wegla&c!"));
                            return;
                        }

                        player.getInventory().removeItem(new ItemBuilder(RybakItems.I6.getItemStack().clone()).setAmount(1).toItemStack());
                        user.setPaliwo(user.getPaliwo() - 1);
                        user.setProgress(user.getProgress() + 1);
                        break;
                    case 0:
                        if (user.getMission() != 8) {
                            player.sendMessage(Utils.format("&6&lStaruszek &8>> &cNie tego teraz potrzebuje!!!"));
                            return;
                        }

                        if (!player.getInventory().containsAtLeast(RybakItems.I8.getItemStack().clone(), 1)) {
                            player.sendMessage(Utils.format("&6&lStaruszek &8>> &cNie posiadasz wymaganego przedmiotu!"));
                            return;
                        }

                        if (user.getPaliwo() < 3) {
                            player.sendMessage(Utils.format("&6&lStaruszek &8>> &7To chyba nie wystarczy... &cDorzuc no troche &8Wegla&c!"));
                            return;
                        }

                        player.getInventory().removeItem(new ItemBuilder(RybakItems.I8.getItemStack().clone()).setAmount(1).toItemStack());
                        user.setPaliwo(user.getPaliwo() - 3);
                        user.setProgress(user.getProgress() + 1);
                        break;
                    case 3:
                        if (user.getMission() != 10) {
                            player.sendMessage(Utils.format("&6&lStaruszek &8>> &cNie tego teraz potrzebuje!!!"));
                            return;
                        }

                        if (!player.getInventory().containsAtLeast(RybakItems.I10.getItemStack().clone(), 1)) {
                            player.sendMessage(Utils.format("&6&lStaruszek &8>> &cNie posiadasz wymaganego przedmiotu!"));
                            return;
                        }

                        if (user.getPaliwo() < 5) {
                            player.sendMessage(Utils.format("&6&lStaruszek &8>> &7To chyba nie wystarczy... &cDorzuc no troche &8Wegla&c!"));
                            return;
                        }

                        player.getInventory().removeItem(new ItemBuilder(RybakItems.I10.getItemStack().clone()).setAmount(1).toItemStack());
                        user.setPaliwo(user.getPaliwo() - 5);
                        user.setProgress(user.getProgress() + 1);
                        break;
                }
                break;
            case COOKED_FISH:

                switch (e.getItem().getDurability()) {
                    case 1:
                        if (user.getMission() != 7) {
                            player.sendMessage(Utils.format("&6&lStaruszek &8>> &cNie tego teraz potrzebuje!!!"));
                            return;
                        }

                        if (!player.getInventory().containsAtLeast(RybakItems.I7.getItemStack().clone(), 1)) {
                            player.sendMessage(Utils.format("&6&lStaruszek &8>> &cNie posiadasz wymaganego przedmiotu!"));
                            return;
                        }

                        if (user.getPaliwo() < 2) {
                            player.sendMessage(Utils.format("&6&lStaruszek &8>> &7To chyba nie wystarczy... &cDorzuc no troche &8Wegla&c!"));
                            return;
                        }

                        player.getInventory().removeItem(new ItemBuilder(RybakItems.I7.getItemStack().clone()).setAmount(1).toItemStack());
                        user.setPaliwo(user.getPaliwo() - 2);
                        user.setProgress(user.getProgress() + 1);
                        break;
                    case 0:
                        if (user.getMission() != 9) {
                            player.sendMessage(Utils.format("&6&lStaruszek &8>> &cNie tego teraz potrzebuje!!!"));
                            return;
                        }

                        if (!player.getInventory().containsAtLeast(RybakItems.I9.getItemStack().clone(), 1)) {
                            player.sendMessage(Utils.format("&6&lStaruszek &8>> &cNie posiadasz wymaganego przedmiotu!"));
                            return;
                        }

                        if (user.getPaliwo() < 4) {
                            player.sendMessage(Utils.format("&6&lStaruszek &8>> &7To chyba nie wystarczy... &cDorzuc no troche &8Wegla&c!"));
                            return;
                        }

                        player.getInventory().removeItem(new ItemBuilder(RybakItems.I9.getItemStack().clone()).setAmount(1).toItemStack());
                        user.setPaliwo(user.getPaliwo() - 4);
                        user.setProgress(user.getProgress() + 1);
                        break;
                }
                break;
            case COAL:
                if (!e.getItem().isSimilar(RybakItems.I14.getItemStack())) return;

                player.getInventory().removeItem(new ItemBuilder(RybakItems.I14.getItemStack().clone()).setAmount(1).toItemStack());
                user.setPaliwo(user.getPaliwo() + 5);
                break;
            default:
                return;
        }
        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataRybak(player.getUniqueId(), rpgcore.getRybakNPC().find(player.getUniqueId())));
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onRybakStolInteract(final PlayerInteractEvent e) {
        if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) return;
        if (!e.getPlayer().getWorld().getName().equals("Rybak")) return;
        if (e.getClickedBlock() == null || e.getClickedBlock().getType() == Material.AIR) return;
        if (!e.getClickedBlock().getType().equals(Material.ENCHANTMENT_TABLE)) return;
        if (!e.getClickedBlock().getLocation().equals(new Location(Bukkit.getWorld("Rybak"), 16, 164, -293))) return;
        e.setCancelled(true);
        e.setUseInteractedBlock(Event.Result.DENY);
        e.setUseItemInHand(Event.Result.DENY);

        final Player player = e.getPlayer();
        if (e.getItem() == null || !e.getItem().getType().equals(Material.FISHING_ROD)) {
            player.sendMessage(Utils.format("&b&lStol Rybacki &8>> &cKliknij na mnie &6Slaba Wedka Rybacka &ci zobacz co sie stanie!"));
            return;
        }
        rpgcore.getRybakNPC().openRybackiStolWyspa2(player);
    }
}
