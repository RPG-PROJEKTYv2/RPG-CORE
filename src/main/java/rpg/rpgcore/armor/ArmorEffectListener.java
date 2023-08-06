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
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.ItemHelper;
import rpg.rpgcore.utils.Utils;


import static org.bukkit.event.EventPriority.HIGHEST;
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


    @EventHandler(priority = HIGHEST)
    public void onInventoryClick(final InventoryClickEvent e) {
        if (e.getClickedInventory() == null || e.getInventory() == null) return;

        if (e.getClickedInventory().getType() == InventoryType.PLAYER) {
            final Player player = (Player) e.getWhoClicked();
            if (!rpgcore.getUserManager().find(player.getUniqueId()).isHellCodeLogin()) {
                e.setCancelled(true);
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Przed zrobieniem tego zaloguj sie swoim HellCode. Uzyj: &c/hellcode <kod>"));
            }
        }
    }

    @EventHandler(priority = LOWEST)
    public void inventoryClickArmorContents(final InventoryClickEvent e) {
        if (e.getClickedInventory() == null || e.getInventory() == null) {
            return;
        }
        Player player = (Player) e.getWhoClicked();
        if (player.getGameMode().equals(GameMode.SURVIVAL)) {
            if (e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
                final String type = e.getCurrentItem().getType().toString();
                e.setCurrentItem(ItemHelper.checkEnchants(e.getCurrentItem(), player));
                if (type.contains("_HELMET")) {
                    if (player.getInventory().getHelmet() == null) {
                        if (player.getInventory().getItem(e.getSlot()) != null) {
                            final User user = this.rpgcore.getUserManager().find(player.getUniqueId());
                            int level = Utils.getTagInt(player.getInventory().getItem(e.getSlot()), "lvl");
                            if (user.getLvl() < level) {
                                e.setCancelled(true);
                                player.sendMessage(Utils.format("&8[&c✘&8] &cNie mozesz tego zalozyc, poniewaz nie posiadasz wymaganego poziomu."));
                                return;
                            }
                        }
                    }
                }
                if (type.contains("_CHESTPLATE")) {
                    if (player.getInventory().getChestplate() == null) {
                        if (player.getInventory().getItem(e.getSlot()) != null) {
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
                }
                if (type.contains("_LEGGINGS")) {
                    if (player.getInventory().getLeggings() == null) {
                        if (player.getInventory().getItem(e.getSlot()) != null) {
                            User user = this.rpgcore.getUserManager().find(player.getUniqueId());
                            int level = Utils.getTagInt(player.getInventory().getItem(e.getSlot()), "lvl");
                            if (user.getLvl() < level) {
                                e.setCancelled(true);
                                player.sendMessage(Utils.format("&8[&c✘&8] &cNie mozesz tego zalozyc, poniewaz nie posiadasz wymaganego poziomu."));
                                return;
                            }
                        }
                    }
                }
                if (type.contains("_BOOTS")) {
                    if (player.getInventory().getBoots() == null) {
                        if (player.getInventory().getItem(e.getSlot()) != null) {
                            User user = this.rpgcore.getUserManager().find(player.getUniqueId());
                            int level = Utils.getTagInt(player.getInventory().getItem(e.getSlot()), "lvl");
                            if (user.getLvl() < level) {
                                e.setCancelled(true);
                                player.sendMessage(Utils.format("&8[&c✘&8] &cNie mozesz tego zalozyc, poniewaz nie posiadasz wymaganego poziomu."));
                                return;
                            }
                        }
                    }
                }
            }
            if (e.getSlotType().equals(InventoryType.SlotType.ARMOR)) {
                if (e.getCurrentItem() == null || e.getCursor().getType() == Material.AIR) {
                    return;
                }
                final String type = e.getCursor().getType().toString();
                User user = this.rpgcore.getUserManager().find(player.getUniqueId());

                int level = Utils.getTagInt(e.getCursor(), "lvl");
                if (user.getLvl() < level) {
                    e.setCancelled(true);
                    player.sendMessage(Utils.format("&8[&c✘&8] &cNie mozesz tego zalozyc, poniewaz nie posiadasz wymaganego poziomu."));
                    return;
                }

                e.setCursor(ItemHelper.checkEnchants(e.getCursor(), player));

                if (type.contains("_CHESTPLATE")) {
                    if (e.getSlot() == 38 && player.getInventory().getItem(38) == null) {
                        rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> {
                            if (player.getInventory().getChestplate() == null) return;
                            ArmorEffectsHelper.addEffectChestPlate(player, Utils.getTagInt(player.getInventory().getChestplate(), "prot"));
                        }, 1L);
                    }
                }
                if (e.getSlot() == 38 && player.getInventory().getItem(38) != null) {
                    player.setMaxHealth(this.rpgcore.getBonusesManager().find(player.getUniqueId()).getBonusesUser().getDodatkowehp() * 2);
                }

            }
        }
    }

    @EventHandler(priority = LOWEST)
    public void interactArmorContents(final PlayerInteractEvent e) {
        final Player player = e.getPlayer();
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
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

            player.setItemInHand(ItemHelper.checkEnchants(player.getItemInHand(), player));

            if (type.contains("_CHESTPLATE")) {
                if (player.getInventory().getChestplate() == null) {
                    ArmorEffectsHelper.addEffectChestPlate(player, Utils.getTagInt(player.getItemInHand(), "prot"));
                }
            }
        }
    }

    @EventHandler
    public void onCraftInventory(final CraftItemEvent e) {
        e.setCancelled(true);
    }

}
