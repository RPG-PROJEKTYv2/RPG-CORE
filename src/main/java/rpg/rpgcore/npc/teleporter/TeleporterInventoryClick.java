package rpg.rpgcore.npc.teleporter;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.expowiska.Przepustki;

import java.util.UUID;

public class TeleporterInventoryClick implements Listener {

    private final RPGCORE rpgcore;

    public TeleporterInventoryClick(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void teleporterInventoryClick(final InventoryClickEvent e) {

        if (e.getClickedInventory() == null) {
            return;
        }

        final Inventory clickedInventory = e.getClickedInventory();
        final Player player = (Player) e.getWhoClicked();
        final UUID uuid = player.getUniqueId();
        final String title = clickedInventory.getTitle();
        final int slot = e.getSlot();


        if (Utils.removeColor(title).equals("TELEPORTER")) {
            e.setCancelled(true);
            if (slot == 11) {
                player.closeInventory();
                player.sendMessage(Utils.SERVERNAME + Utils.format("&aPrzeteleportowales sie na mape!"));
                rpgcore.getTeleporterNPC().teleportExp1(player);
            }
            if (rpgcore.getUserManager().find(uuid).getLvl() > 9) {
                if (slot == 12) {
                    player.closeInventory();
                    player.sendMessage(Utils.SERVERNAME + Utils.format("&aPrzeteleportowales sie na mape!"));
                    rpgcore.getTeleporterNPC().teleportExp2(player);
                }
            }
            if (rpgcore.getUserManager().find(uuid).getLvl() > 19) {
                if (slot == 13) {
                    player.closeInventory();
                    player.sendMessage(Utils.SERVERNAME + Utils.format("&aPrzeteleportowales sie na mape!"));
                    rpgcore.getTeleporterNPC().teleportExp3(player);
                }
            }
            if (rpgcore.getUserManager().find(uuid).getLvl() > 29) {
                if (slot == 14) {
                    player.closeInventory();
                    player.sendMessage(Utils.SERVERNAME + Utils.format("&aPrzeteleportowales sie na mape!"));
                    rpgcore.getTeleporterNPC().teleportExp4(player);
                }
            }
            if (rpgcore.getUserManager().find(uuid).getLvl() > 39) {
                if (slot == 15) {
                    player.closeInventory();
                    player.sendMessage(Utils.SERVERNAME + Utils.format("&aPrzeteleportowales sie na mape!"));
                    rpgcore.getTeleporterNPC().teleportExp5(player);
                }
            }
            if (rpgcore.getUserManager().find(uuid).getLvl() > 49) {
                if (slot == 28) {
                    player.closeInventory();
                    player.sendMessage(Utils.SERVERNAME + Utils.format("&aPrzeteleportowales sie na mape!"));
                    rpgcore.getTeleporterNPC().teleportExp6(player);
                }
            }
            if (rpgcore.getUserManager().find(uuid).getLvl() > 59) {
                if (slot == 29) {
                    player.closeInventory();
                    player.sendMessage(Utils.SERVERNAME + Utils.format("&aPrzeteleportowales sie na mape!"));
                    rpgcore.getTeleporterNPC().teleportExp7(player);
                }
            }
            if (rpgcore.getUserManager().find(uuid).getLvl() > 69) {
                if (slot == 30) {
                    player.closeInventory();
                    player.sendMessage(Utils.SERVERNAME + Utils.format("&aPrzeteleportowales sie na mape!"));
                    rpgcore.getTeleporterNPC().teleportExp8(player);
                }
            }
            if (rpgcore.getUserManager().find(uuid).getLvl() > 79) {
                if (slot == 31) {
                    player.closeInventory();
                    player.sendMessage(Utils.SERVERNAME + Utils.format("&aPrzeteleportowales sie na mape!"));
                    rpgcore.getTeleporterNPC().teleportExp9(player);
                }
            }
            if (rpgcore.getUserManager().find(uuid).getLvl() > 89) {
                if (slot == 32) {
                    player.closeInventory();
                    player.sendMessage(Utils.SERVERNAME + Utils.format("&aPrzeteleportowales sie na mape!"));
                    rpgcore.getTeleporterNPC().teleportExp10(player);
                }
            }
            if (rpgcore.getUserManager().find(uuid).getLvl() > 99) {
                if (slot == 33) {
                    if (player.getInventory().containsAtLeast(Przepustki.getItem("I4", 1), 1)) {
                        if (rpgcore.getUserManager().find(uuid).getLvl() >= 120) {
                            player.sendMessage(Utils.SERVERNAME + Utils.format("&aPrzeteleportowales sie na mape!"));
                            player.closeInventory();
                            rpgcore.getTeleporterNPC().teleportExp11(player);
                        } else {
                            player.sendMessage(Utils.SERVERNAME + Utils.format("&7Twoj poziom jest zbyt niski aby uzywac talizmanu!"));
                        }
                    } else if (player.getInventory().containsAtLeast(Przepustki.getItem("I1", 1),1)) {
                        player.sendMessage(Utils.SERVERNAME + Utils.format("&aPrzeteleportowales sie na mape!"));
                        player.closeInventory();
                        rpgcore.getTeleporterNPC().teleportExp11(player);
                        player.getInventory().removeItem(Przepustki.getItem("I1", 1));
                    } else {
                        player.sendMessage(Utils.SERVERNAME + Utils.format("&7Nie posiadasz wejsciowki!"));
                    }
                }
            }
            if (rpgcore.getUserManager().find(uuid).getLvl() > 109) {
                if (slot == 34) {
                    player.closeInventory();
                    player.sendMessage(Utils.SERVERNAME + Utils.format("&4&lTo expowisko zostanie wlaczone kiedy 4 osoby osiagna wymagany na nie poziom!"));
                    return;
                   /* if (player.getInventory().containsAtLeast(Przepustki.getItem("I4", 1), 1)) {
                        if (rpgcore.getUserManager().find(uuid).getLvl() >= 120) {
                            player.sendMessage(Utils.SERVERNAME + Utils.format("&aPrzeteleportowales sie na mape!"));
                            player.closeInventory();
                            rpgcore.getTeleporterNPC().teleportExp12(player);
                        } else {
                            player.sendMessage(Utils.SERVERNAME + Utils.format("&7Twoj poziom jest zbyt niski aby uzywac talizmanu!"));
                        }
                    } else if (player.getInventory().containsAtLeast(Przepustki.getItem("I2", 1),1)) {
                        player.sendMessage(Utils.SERVERNAME + Utils.format("&aPrzeteleportowales sie na mape!"));
                        player.closeInventory();
                        rpgcore.getTeleporterNPC().teleportExp12(player);
                        player.getInventory().removeItem(Przepustki.getItem("I2", 1));
                    } else {
                        player.sendMessage(Utils.SERVERNAME + Utils.format("&7Nie posiadasz wejsciowki!"));
                    }*/
                }
            }
            if (rpgcore.getUserManager().find(uuid).getLvl() > 119) {
                if (slot == 40) {
                    if (player.getInventory().containsAtLeast(Przepustki.getItem("I4", 1), 1)) {
                        if (rpgcore.getUserManager().find(uuid).getLvl() >= 120) {
                            player.sendMessage(Utils.SERVERNAME + Utils.format("&aPrzeteleportowales sie na mape!"));
                            player.closeInventory();
                            rpgcore.getTeleporterNPC().teleportExp13(player);
                        } else {
                            player.sendMessage(Utils.SERVERNAME + Utils.format("&7Twoj poziom jest zbyt niski aby uzywac talizmanu!"));
                        }
                    } else if (player.getInventory().containsAtLeast(Przepustki.getItem("I3", 1),1)) {
                        player.sendMessage(Utils.SERVERNAME + Utils.format("&aPrzeteleportowales sie na mape!"));
                        player.closeInventory();
                        rpgcore.getTeleporterNPC().teleportExp13(player);
                        player.getInventory().removeItem(Przepustki.getItem("I3", 1));
                    } else {
                        player.sendMessage(Utils.SERVERNAME + Utils.format("&7Nie posiadasz wejsciowki!"));
                    }
                }
            }
        }
    }
}