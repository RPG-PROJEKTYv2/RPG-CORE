package rpg.rpgcore.armor;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffectType;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.bonuses.Bonuses;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.Utils;

import static org.bukkit.event.EventPriority.LOWEST;

public class ArmorEffectListener implements Listener {

    private final RPGCORE rpgcore;

    public ArmorEffectListener(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void inventoryClose(final InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();
        ArmorEffectsHelper.addEffectsArmor(p);
    }

    @EventHandler(priority = LOWEST)
    public void inventoryClickArmorContents(final InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (player.getGameMode().equals(GameMode.SURVIVAL)) {
            if (e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
                final String type = e.getClickedInventory().getType().toString();
                if (type.contains("_HELMET")) {
                    if (player.getInventory().getHelmet() == null) {
                        User user = this.rpgcore.getUserManager().find(player.getUniqueId());
                        int level = Utils.getTagInt(player.getInventory().getItem(e.getSlot()), "lvl");
                        if (user.getLvl() < level) {
                            e.setCancelled(true);
                            player.sendMessage(Utils.format("&8[&c✘&8] &cNie mozesz tego zalozyc, poniewaz nie posiadasz wymaganego poziomu."));
                            return;
                        }
                        ArmorEffectsHelper.addEffectHelmet(player, Utils.getTagInt(player.getInventory().getItem(e.getSlot()), "prot"));
                    }
                }
                if (type.contains("_CHESTPLATE")) {
                    if (player.getInventory().getChestplate() == null) {
                        User user = this.rpgcore.getUserManager().find(player.getUniqueId());
                        int level = Utils.getTagInt(player.getInventory().getItem(e.getSlot()), "lvl");
                        if (user.getLvl() < level) {
                            e.setCancelled(true);
                            player.sendMessage(Utils.format("&8[&c✘&8] &cNie mozesz tego zalozyc, poniewaz nie posiadasz wymaganego poziomu."));
                            return;
                        }
                        ArmorEffectsHelper.addEffectChestPlate(player, Utils.getTagInt(player.getInventory().getItem(e.getSlot()), "prot"));
                    }
                }
                if (type.contains("_LEGGINGS")) {
                    if (player.getInventory().getLeggings() == null) {
                        User user = this.rpgcore.getUserManager().find(player.getUniqueId());
                        int level = Utils.getTagInt(player.getInventory().getItem(e.getSlot()), "lvl");
                        if (user.getLvl() < level) {
                            e.setCancelled(true);
                            player.sendMessage(Utils.format("&8[&c✘&8] &cNie mozesz tego zalozyc, poniewaz nie posiadasz wymaganego poziomu."));
                            return;
                        }
                        ArmorEffectsHelper.addEffectLeggings(player, Utils.getTagInt(player.getInventory().getItem(e.getSlot()), "prot"));
                    }
                }
                if (type.contains("_BOOTS")) {
                    if (player.getInventory().getBoots() == null) {
                        User user = this.rpgcore.getUserManager().find(player.getUniqueId());
                        int level = Utils.getTagInt(player.getInventory().getItem(e.getSlot()), "lvl");
                        if (user.getLvl() < level) {
                            e.setCancelled(true);
                            player.sendMessage(Utils.format("&8[&c✘&8] &cNie mozesz tego zalozyc, poniewaz nie posiadasz wymaganego poziomu."));
                            return;
                        }
                        ArmorEffectsHelper.addEffectBoots(player, Utils.getTagInt(player.getInventory().getItem(e.getSlot()), "prot"));
                    }
                }
            }
            if (e.getSlotType().equals(InventoryType.SlotType.ARMOR)) {
                if (e.getCurrentItem() == null || e.getCursor().getType() == Material.AIR) {
                    return;
                }
                final String type = e.getCursor().getType().toString();
                User user = this.rpgcore.getUserManager().find(player.getUniqueId());

                System.out.println(e.getCursor());
                System.out.println(Utils.getTagInt(e.getCursor(), "lvl"));


                int level = Utils.getTagInt(e.getCursor(), "lvl");
                if (user.getLvl() < level) {
                    e.setCancelled(true);
                    player.sendMessage(Utils.format("&8[&c✘&8] &cNie mozesz tego zalozyc, poniewaz nie posiadasz wymaganego poziomu."));
                    return;
                }
                if (type.contains("_HELMET")) {
                    if (e.getSlot() == 39 && player.getInventory().getItem(39) == null) {
                        ArmorEffectsHelper.addEffectHelmet(player, Utils.getTagInt(e.getCursor(), "prot"));
                    }
                }
                if (e.getSlot() == 39 && player.getInventory().getItem(39) != null) {
                    player.removePotionEffect(PotionEffectType.JUMP);
                }
                if (type.contains("_CHESTPLATE")) {
                    if (e.getSlot() == 38 && player.getInventory().getItem(38) == null) {
                        ArmorEffectsHelper.addEffectChestPlate(player, Utils.getTagInt(e.getCursor(), "prot"));
                    }
                }
                if (e.getSlot() == 38 && player.getInventory().getItem(38) != null) {
                    player.removePotionEffect(PotionEffectType.ABSORPTION);
                    player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
                    Bonuses bonuses = this.rpgcore.getBonusesManager().find(player.getUniqueId());
                    player.setMaxHealth(bonuses.getBonusesUser().getDodatkowehp() * 2);
                }

                if (type.contains("_LEGGINGS")) {
                    if (e.getSlot() == 37 && player.getInventory().getItem(37) == null) {
                        ArmorEffectsHelper.addEffectLeggings(player, Utils.getTagInt(player.getInventory().getItem(e.getSlot()), "prot"));
                    }
                }
                if (e.getSlot() == 37 && player.getInventory().getItem(37) != null) {
                    player.removePotionEffect(PotionEffectType.REGENERATION);
                }
                if (type.contains("_BOOTS")) {
                    if (e.getSlot() == 36 && player.getInventory().getItem(36) == null) {
                        ArmorEffectsHelper.addEffectBoots(player, Utils.getTagInt(player.getInventory().getItem(e.getSlot()), "prot"));
                    }
                }
                if (e.getSlot() == 36 && player.getInventory().getItem(36) != null) {
                    player.removePotionEffect(PotionEffectType.SPEED);
                }
            }
        }
    }

    @EventHandler(priority = LOWEST)
    public void interactArmorContents(final PlayerInteractEvent e) {
        final Player player = e.getPlayer();
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            if (player.getItemInHand() != null) {
                if (player.getItemInHand().getType().equals(Material.ENDER_PEARL) || player.getItemInHand().getType().equals(Material.EYE_OF_ENDER)) {
                    e.setCancelled(true);
                    return;
                }
            }
            if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if (e.getClickedBlock().getType().equals(Material.ANVIL) || e.getClickedBlock().getType().equals(Material.CHEST) || e.getClickedBlock().getType().equals(Material.ENDER_CHEST) || e.getClickedBlock().getType().equals(Material.TRAPPED_CHEST) || e.getClickedBlock().getType().equals(Material.HOPPER) || e.getClickedBlock().getType().equals(Material.HOPPER_MINECART) || e.getClickedBlock().getType().equals(Material.FURNACE) || e.getClickedBlock().getType().equals(Material.BURNING_FURNACE) || e.getClickedBlock().getType().equals(Material.STORAGE_MINECART) || e.getClickedBlock().getType().equals(Material.POWERED_MINECART) || e.getClickedBlock().getType().equals(Material.DISPENSER) || e.getClickedBlock().getType().equals(Material.DROPPER) || e.getClickedBlock().getType().equals(Material.WORKBENCH) || e.getClickedBlock().getType().equals(Material.JUKEBOX) || e.getClickedBlock().getType().equals(Material.ITEM_FRAME) || e.getClickedBlock().getType().equals(Material.BED) || e.getClickedBlock().getType().equals(Material.BREWING_STAND)) {
                    if (!player.isOp()) {
                        e.setCancelled(true);
                        return;
                    }
                }
            }
            if (player.getItemInHand() == null || player.getItemInHand().getType() == Material.AIR) {
                return;
            }


            final String type = player.getItemInHand().getType().toString();

            if (!type.contains("_HELMET") && !type.contains("_CHESTPLATE") && !type.contains("_LEGGINGS") && !type.contains("_BOOTS")) {
                return;
            }

            final User user = this.rpgcore.getUserManager().find(player.getUniqueId());
            final int level = Utils.getTagInt(player.getItemInHand(), "lvl");
            if (user.getLvl() < level) {
                e.setCancelled(true);
                player.updateInventory();
                player.sendMessage(Utils.format("&8[&c✘&8] &cNie mozesz tego zalozyc, poniewaz nie posiadasz wymaganego poziomu."));
                return;
            }

            player.sendMessage("prot: " + Utils.getTagInt(player.getItemInHand(), "prot"));
            player.sendMessage("thorns: " + Utils.getTagInt(player.getItemInHand(), "thorns"));

            if (type.contains("_HELMET")) {
                if (player.getInventory().getHelmet() == null) {
                    ArmorEffectsHelper.addEffectHelmet(player, Utils.getTagInt(player.getItemInHand(), "prot"));
                }
            }
            if (type.contains("_CHESTPLATE")) {
                if (player.getInventory().getChestplate() == null) {
                    ArmorEffectsHelper.addEffectChestPlate(player, Utils.getTagInt(player.getItemInHand(), "prot"));
                }
            }
            if (type.contains("_LEGGINGS")) {
                if (player.getInventory().getLeggings() == null) {
                    ArmorEffectsHelper.addEffectLeggings(player, Utils.getTagInt(player.getItemInHand(), "prot"));
                }
            }
            if (type.contains("_BOOTS")) {
                if (player.getInventory().getBoots() == null) {
                    ArmorEffectsHelper.addEffectBoots(player, Utils.getTagInt(player.getItemInHand(), "prot"));
                }
            }
        }
    }

    @EventHandler
    public void onCraftInventory(final CraftItemEvent e) {
        e.setCancelled(true);
    }

}
