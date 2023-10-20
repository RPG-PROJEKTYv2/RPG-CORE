package rpg.rpgcore.npc.straganiarz.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.GlobalItem;
import rpg.rpgcore.utils.globalitems.expowiska.Ulepszacze;

public class StraganiarzInventoryClickListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onClick(final InventoryClickEvent e) {
        final Inventory gui = e.getClickedInventory();
        final Player player = (Player) e.getWhoClicked();

        if (e.getClickedInventory() == null || e.getInventory() == null) {
            return;
        }

        final String title = Utils.removeColor(gui.getTitle());
        final int slot = e.getSlot();
        final ItemStack item = e.getCurrentItem();
        final User user = RPGCORE.getInstance().getUserManager().find(player.getUniqueId());

        if (title.equals("Straganiarz - MENU")) {
            e.setResult(Event.Result.DENY);
            e.setCancelled(true);
            if (item == null || item.getType() == Material.STAINED_GLASS_PANE) return;
            if (slot == 12) {
                RPGCORE.getInstance().getStraganiarzManager().openGUI2(player);
                return;
            }
            if (slot == 14) {
                RPGCORE.getInstance().getStraganiarzManager().openGUI3(player);
                return;
            }
        }

        if (title.equals("Straganiarz - ULEPSZACZE")) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
            if (item == null || item.getType() == Material.STAINED_GLASS_PANE) return;
            switch (slot) {
                case 10:
                if (e.getClick().isLeftClick()) {
                    if (!player.getInventory().containsAtLeast(Ulepszacze.I_SZATAROZBOJNIKA.getItem(), 64)) return;
                    player.getInventory().removeItem(new ItemBuilder(Ulepszacze.I_SZATAROZBOJNIKA.getItem().clone()).setAmount(64).toItemStack());
                    player.getInventory().addItem(new ItemBuilder(Ulepszacze.I_SZATAROZBOJNIKA64.getItem().clone()).setAmount(1).toItemStack());
                } else if (e.isRightClick()) {
                    if (!player.getInventory().containsAtLeast(Ulepszacze.I_SZATAROZBOJNIKA64.getItem(), 1)) return;
                    player.getInventory().removeItem(new ItemBuilder(Ulepszacze.I_SZATAROZBOJNIKA64.getItem().clone()).setAmount(1).toItemStack());
                    player.getInventory().addItem(new ItemBuilder(Ulepszacze.I_SZATAROZBOJNIKA.getItem().clone()).setAmount(64).toItemStack());
                }
                return;
                case 11:
                    if (e.getClick().isLeftClick()) {
                        if (!player.getInventory().containsAtLeast(Ulepszacze.I_OKOGOBLINA.getItem(), 64)) return;
                        player.getInventory().removeItem(new ItemBuilder(Ulepszacze.I_OKOGOBLINA.getItem().clone()).setAmount(64).toItemStack());
                        player.getInventory().addItem(new ItemBuilder(Ulepszacze.I_OKOGOBLINA64.getItem().clone()).setAmount(1).toItemStack());
                    } else if (e.isRightClick()) {
                        if (!player.getInventory().containsAtLeast(Ulepszacze.I_OKOGOBLINA64.getItem(), 1)) return;
                        player.getInventory().removeItem(new ItemBuilder(Ulepszacze.I_OKOGOBLINA64.getItem().clone()).setAmount(1).toItemStack());
                        player.getInventory().addItem(new ItemBuilder(Ulepszacze.I_OKOGOBLINA.getItem().clone()).setAmount(64).toItemStack());
                    }
                    return;
                case 12:
                    if (e.getClick().isLeftClick()) {
                        if (!player.getInventory().containsAtLeast(Ulepszacze.I_SKORAGORYLA.getItem(), 64)) return;
                        player.getInventory().removeItem(new ItemBuilder(Ulepszacze.I_SKORAGORYLA.getItem().clone()).setAmount(64).toItemStack());
                        player.getInventory().addItem(new ItemBuilder(Ulepszacze.I_SKORAGORYLA64.getItem().clone()).setAmount(1).toItemStack());
                    } else if (e.isRightClick()) {
                        if (!player.getInventory().containsAtLeast(Ulepszacze.I_SKORAGORYLA64.getItem(), 1)) return;
                        player.getInventory().removeItem(new ItemBuilder(Ulepszacze.I_SKORAGORYLA64.getItem().clone()).setAmount(1).toItemStack());
                        player.getInventory().addItem(new ItemBuilder(Ulepszacze.I_SKORAGORYLA.getItem().clone()).setAmount(64).toItemStack());
                    }
                    return;
                case 13:
                    if (e.getClick().isLeftClick()) {
                        if (!player.getInventory().containsAtLeast(Ulepszacze.I_ZLAMANAKOSC.getItem(), 64)) return;
                        player.getInventory().removeItem(new ItemBuilder(Ulepszacze.I_ZLAMANAKOSC.getItem().clone()).setAmount(64).toItemStack());
                        player.getInventory().addItem(new ItemBuilder(Ulepszacze.I_ZLAMANAKOSC64.getItem().clone()).setAmount(1).toItemStack());
                    } else if (e.isRightClick()) {
                        if (!player.getInventory().containsAtLeast(Ulepszacze.I_ZLAMANAKOSC64.getItem(), 1)) return;
                        player.getInventory().removeItem(new ItemBuilder(Ulepszacze.I_ZLAMANAKOSC64.getItem().clone()).setAmount(1).toItemStack());
                        player.getInventory().addItem(new ItemBuilder(Ulepszacze.I_ZLAMANAKOSC.getItem().clone()).setAmount(64).toItemStack());
                    }
                    return;

                case 14:
                    if (e.getClick().isLeftClick()) {
                        if (!player.getInventory().containsAtLeast(Ulepszacze.I_LZAOCEANU.getItem(), 64)) return;
                        player.getInventory().removeItem(new ItemBuilder(Ulepszacze.I_LZAOCEANU.getItem().clone()).setAmount(64).toItemStack());
                        player.getInventory().addItem(new ItemBuilder(Ulepszacze.I_LZAOCEANU64.getItem().clone()).setAmount(1).toItemStack());
                    } else if (e.isRightClick()) {
                        if (!player.getInventory().containsAtLeast(Ulepszacze.I_LZAOCEANU64.getItem(), 1)) return;
                        player.getInventory().removeItem(new ItemBuilder(Ulepszacze.I_LZAOCEANU64.getItem().clone()).setAmount(1).toItemStack());
                        player.getInventory().addItem(new ItemBuilder(Ulepszacze.I_LZAOCEANU.getItem().clone()).setAmount(64).toItemStack());
                    }
                    return;
                case 15:
                    if (e.getClick().isLeftClick()) {
                        if (!player.getInventory().containsAtLeast(Ulepszacze.I_WILCZEFUTRO.getItem(), 64)) return;
                        player.getInventory().removeItem(new ItemBuilder(Ulepszacze.I_WILCZEFUTRO.getItem().clone()).setAmount(64).toItemStack());
                        player.getInventory().addItem(new ItemBuilder(Ulepszacze.I_WILCZEFUTRO64.getItem().clone()).setAmount(1).toItemStack());
                    } else if (e.isRightClick()) {
                        if (!player.getInventory().containsAtLeast(Ulepszacze.I_WILCZEFUTRO64.getItem(), 1)) return;
                        player.getInventory().removeItem(new ItemBuilder(Ulepszacze.I_WILCZEFUTRO64.getItem().clone()).setAmount(1).toItemStack());
                        player.getInventory().addItem(new ItemBuilder(Ulepszacze.I_WILCZEFUTRO.getItem().clone()).setAmount(64).toItemStack());
                    }
                    return;
                case 16:
                    if (e.getClick().isLeftClick()) {
                        if (!player.getInventory().containsAtLeast(Ulepszacze.I_OGNISTYPYL.getItem(), 64)) return;
                        player.getInventory().removeItem(new ItemBuilder(Ulepszacze.I_OGNISTYPYL.getItem().clone()).setAmount(64).toItemStack());
                        player.getInventory().addItem(new ItemBuilder(Ulepszacze.I_OGNISTYPYL64.getItem().clone()).setAmount(1).toItemStack());
                    } else if (e.isRightClick()) {
                        if (!player.getInventory().containsAtLeast(Ulepszacze.I_OGNISTYPYL64.getItem(), 1)) return;
                        player.getInventory().removeItem(new ItemBuilder(Ulepszacze.I_OGNISTYPYL64.getItem().clone()).setAmount(1).toItemStack());
                        player.getInventory().addItem(new ItemBuilder(Ulepszacze.I_OGNISTYPYL.getItem().clone()).setAmount(64).toItemStack());
                    }
                    return;


                case 20:
                    if (e.getClick().isLeftClick()) {
                        if (!player.getInventory().containsAtLeast(Ulepszacze.I_TRUJACAROSLINA.getItem(), 64)) return;
                        player.getInventory().removeItem(new ItemBuilder(Ulepszacze.I_TRUJACAROSLINA.getItem().clone()).setAmount(64).toItemStack());
                        player.getInventory().addItem(new ItemBuilder(Ulepszacze.I_TRUJACAROSLINA64.getItem().clone()).setAmount(1).toItemStack());
                    } else if (e.isRightClick()) {
                        if (!player.getInventory().containsAtLeast(Ulepszacze.I_TRUJACAROSLINA64.getItem(), 1)) return;
                        player.getInventory().removeItem(new ItemBuilder(Ulepszacze.I_TRUJACAROSLINA64.getItem().clone()).setAmount(1).toItemStack());
                        player.getInventory().addItem(new ItemBuilder(Ulepszacze.I_TRUJACAROSLINA.getItem().clone()).setAmount(64).toItemStack());
                    }
                    return;
                case 21:
                    if (e.getClick().isLeftClick()) {
                        if (!player.getInventory().containsAtLeast(Ulepszacze.I_JADPTASZNIKA.getItem(), 64)) return;
                        player.getInventory().removeItem(new ItemBuilder(Ulepszacze.I_JADPTASZNIKA.getItem().clone()).setAmount(64).toItemStack());
                        player.getInventory().addItem(new ItemBuilder(Ulepszacze.I_JADPTASZNIKA64.getItem().clone()).setAmount(1).toItemStack());
                    } else if (e.isRightClick()) {
                        if (!player.getInventory().containsAtLeast(Ulepszacze.I_JADPTASZNIKA64.getItem(), 1)) return;
                        player.getInventory().removeItem(new ItemBuilder(Ulepszacze.I_JADPTASZNIKA64.getItem().clone()).setAmount(1).toItemStack());
                        player.getInventory().addItem(new ItemBuilder(Ulepszacze.I_JADPTASZNIKA.getItem().clone()).setAmount(64).toItemStack());
                    }
                    return;
                case 22:
                    if (e.getClick().isLeftClick()) {
                        if (!player.getInventory().containsAtLeast(Ulepszacze.I_MROCZNYMATERIAL.getItem(), 64)) return;
                        player.getInventory().removeItem(new ItemBuilder(Ulepszacze.I_MROCZNYMATERIAL.getItem().clone()).setAmount(64).toItemStack());
                        player.getInventory().addItem(new ItemBuilder(Ulepszacze.I_MROCZNYMATERIAL64.getItem().clone()).setAmount(1).toItemStack());
                    } else if (e.isRightClick()) {
                        if (!player.getInventory().containsAtLeast(Ulepszacze.I_MROCZNYMATERIAL64.getItem(), 1)) return;
                        player.getInventory().removeItem(new ItemBuilder(Ulepszacze.I_MROCZNYMATERIAL64.getItem().clone()).setAmount(1).toItemStack());
                        player.getInventory().addItem(new ItemBuilder(Ulepszacze.I_MROCZNYMATERIAL.getItem().clone()).setAmount(64).toItemStack());
                    }
                    return;
                case 23:
                    if (e.getClick().isLeftClick()) {
                        if (!player.getInventory().containsAtLeast(Ulepszacze.I_SZAFIROWYSKRAWEK.getItem(), 64)) return;
                        player.getInventory().removeItem(new ItemBuilder(Ulepszacze.I_SZAFIROWYSKRAWEK.getItem().clone()).setAmount(64).toItemStack());
                        player.getInventory().addItem(new ItemBuilder(Ulepszacze.I_SZAFIROWESERCE64.getItem().clone()).setAmount(1).toItemStack());
                    } else if (e.isRightClick()) {
                        if (!player.getInventory().containsAtLeast(Ulepszacze.I_SZAFIROWESERCE64.getItem(), 1)) return;
                        player.getInventory().removeItem(new ItemBuilder(Ulepszacze.I_SZAFIROWESERCE64.getItem().clone()).setAmount(1).toItemStack());
                        player.getInventory().addItem(new ItemBuilder(Ulepszacze.I_SZAFIROWYSKRAWEK.getItem().clone()).setAmount(64).toItemStack());
                    }
                    return;
                case 24:
                    if (e.getClick().isLeftClick()) {
                        if (!player.getInventory().containsAtLeast(Ulepszacze.I_ZAKLETYLOD.getItem(), 64)) return;
                        player.getInventory().removeItem(new ItemBuilder(Ulepszacze.I_ZAKLETYLOD.getItem().clone()).setAmount(64).toItemStack());
                        player.getInventory().addItem(new ItemBuilder(Ulepszacze.I_ZAKLETYLOD64.getItem().clone()).setAmount(1).toItemStack());
                    } else if (e.isRightClick()) {
                        if (!player.getInventory().containsAtLeast(Ulepszacze.I_ZAKLETYLOD64.getItem(), 1)) return;
                        player.getInventory().removeItem(new ItemBuilder(Ulepszacze.I_ZAKLETYLOD64.getItem().clone()).setAmount(1).toItemStack());
                        player.getInventory().addItem(new ItemBuilder(Ulepszacze.I_ZAKLETYLOD.getItem().clone()).setAmount(64).toItemStack());
                    }
                    return;

                case 31:
                    if (e.getClick().isLeftClick()) {
                        if (!player.getInventory().containsAtLeast(Ulepszacze.I_NIEBIANSKIMATERIAL.getItem(), 64)) return;
                        player.getInventory().removeItem(new ItemBuilder(Ulepszacze.I_NIEBIANSKIMATERIAL.getItem().clone()).setAmount(64).toItemStack());
                        player.getInventory().addItem(new ItemBuilder(Ulepszacze.I_NIEBIANSKIMATERIAL64.getItem().clone()).setAmount(1).toItemStack());
                    } else if (e.isRightClick()) {
                        if (!player.getInventory().containsAtLeast(Ulepszacze.I_NIEBIANSKIMATERIAL64.getItem(), 1)) return;
                        player.getInventory().removeItem(new ItemBuilder(Ulepszacze.I_NIEBIANSKIMATERIAL64.getItem().clone()).setAmount(1).toItemStack());
                        player.getInventory().addItem(new ItemBuilder(Ulepszacze.I_NIEBIANSKIMATERIAL.getItem().clone()).setAmount(64).toItemStack());
                    }
                    return;
                case 35:
                    RPGCORE.getInstance().getStraganiarzManager().openGUI1(player);
                    return;
                default:
                    break;
            }
        }

        if (title.equals("Straganiarz - MATERIALY")) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
            if (item == null || item.getType() == Material.STAINED_GLASS_PANE) return;
            switch (slot) {
                case 10:
                    if (e.getClick().isLeftClick()) {
                        if (!player.getInventory().containsAtLeast(GlobalItem.I12.getItemStack(), 64)) return;
                        player.getInventory().removeItem(new ItemBuilder(GlobalItem.I12.getItemStack().clone()).setAmount(64).toItemStack());
                        player.getInventory().addItem(new ItemBuilder(GlobalItem.I12_64.getItemStack().clone()).setAmount(1).toItemStack());
                    } else if (e.isRightClick()) {
                        if (!player.getInventory().containsAtLeast(GlobalItem.I12_64.getItemStack(), 1)) return;
                        player.getInventory().removeItem(new ItemBuilder(GlobalItem.I12_64.getItemStack().clone()).setAmount(1).toItemStack());
                        player.getInventory().addItem(new ItemBuilder(GlobalItem.I12.getItemStack().clone()).setAmount(64).toItemStack());
                    }
                    return;
                case 11:
                    if (e.getClick().isLeftClick()) {
                        if (!player.getInventory().containsAtLeast(GlobalItem.I13.getItemStack(), 64)) return;
                        player.getInventory().removeItem(new ItemBuilder(GlobalItem.I13.getItemStack().clone()).setAmount(64).toItemStack());
                        player.getInventory().addItem(new ItemBuilder(GlobalItem.I13_64.getItemStack().clone()).setAmount(1).toItemStack());
                    } else if (e.isRightClick()) {
                        if (!player.getInventory().containsAtLeast(GlobalItem.I13_64.getItemStack(), 1)) return;
                        player.getInventory().removeItem(new ItemBuilder(GlobalItem.I13_64.getItemStack().clone()).setAmount(1).toItemStack());
                        player.getInventory().addItem(new ItemBuilder(GlobalItem.I13.getItemStack().clone()).setAmount(64).toItemStack());
                    }
                    return;
                case 12:
                    if (e.getClick().isLeftClick()) {
                        if (!player.getInventory().containsAtLeast(GlobalItem.I14.getItemStack(), 64)) return;
                        player.getInventory().removeItem(new ItemBuilder(GlobalItem.I14.getItemStack().clone()).setAmount(64).toItemStack());
                        player.getInventory().addItem(new ItemBuilder(GlobalItem.I14_64.getItemStack().clone()).setAmount(1).toItemStack());
                    } else if (e.isRightClick()) {
                        if (!player.getInventory().containsAtLeast(GlobalItem.I14_64.getItemStack(), 1)) return;
                        player.getInventory().removeItem(new ItemBuilder(GlobalItem.I14_64.getItemStack().clone()).setAmount(1).toItemStack());
                        player.getInventory().addItem(new ItemBuilder(GlobalItem.I14.getItemStack().clone()).setAmount(64).toItemStack());
                    }
                    return;
                case 13:
                    if (e.getClick().isLeftClick()) {
                        if (!player.getInventory().containsAtLeast(GlobalItem.I15.getItemStack(), 64)) return;
                        player.getInventory().removeItem(new ItemBuilder(GlobalItem.I15.getItemStack().clone()).setAmount(64).toItemStack());
                        player.getInventory().addItem(new ItemBuilder(GlobalItem.I15_64.getItemStack().clone()).setAmount(1).toItemStack());
                    } else if (e.isRightClick()) {
                        if (!player.getInventory().containsAtLeast(GlobalItem.I15_64.getItemStack(), 1)) return;
                        player.getInventory().removeItem(new ItemBuilder(GlobalItem.I15_64.getItemStack().clone()).setAmount(1).toItemStack());
                        player.getInventory().addItem(new ItemBuilder(GlobalItem.I15.getItemStack().clone()).setAmount(64).toItemStack());
                    }
                    return;
                case 14:
                    if (e.getClick().isLeftClick()) {
                        if (!player.getInventory().containsAtLeast(GlobalItem.I16.getItemStack(), 64)) return;
                        player.getInventory().removeItem(new ItemBuilder(GlobalItem.I16.getItemStack().clone()).setAmount(64).toItemStack());
                        player.getInventory().addItem(new ItemBuilder(GlobalItem.I16_64.getItemStack().clone()).setAmount(1).toItemStack());
                    } else if (e.isRightClick()) {
                        if (!player.getInventory().containsAtLeast(GlobalItem.I16_64.getItemStack(), 1)) return;
                        player.getInventory().removeItem(new ItemBuilder(GlobalItem.I16_64.getItemStack().clone()).setAmount(1).toItemStack());
                        player.getInventory().addItem(new ItemBuilder(GlobalItem.I16.getItemStack().clone()).setAmount(64).toItemStack());
                    }
                    return;
                case 15:
                    if (e.getClick().isLeftClick()) {
                        if (!player.getInventory().containsAtLeast(GlobalItem.I17.getItemStack(), 64)) return;
                        player.getInventory().removeItem(new ItemBuilder(GlobalItem.I17.getItemStack().clone()).setAmount(64).toItemStack());
                        player.getInventory().addItem(new ItemBuilder(GlobalItem.I17_64.getItemStack().clone()).setAmount(1).toItemStack());
                    } else if (e.isRightClick()) {
                        if (!player.getInventory().containsAtLeast(GlobalItem.I17_64.getItemStack(), 1)) return;
                        player.getInventory().removeItem(new ItemBuilder(GlobalItem.I17_64.getItemStack().clone()).setAmount(1).toItemStack());
                        player.getInventory().addItem(new ItemBuilder(GlobalItem.I17.getItemStack().clone()).setAmount(64).toItemStack());
                    }
                    return;
                case 16:
                    if (e.getClick().isLeftClick()) {
                        if (!player.getInventory().containsAtLeast(GlobalItem.I18.getItemStack(), 64)) return;
                        player.getInventory().removeItem(new ItemBuilder(GlobalItem.I18.getItemStack().clone()).setAmount(64).toItemStack());
                        player.getInventory().addItem(new ItemBuilder(GlobalItem.I18_64.getItemStack().clone()).setAmount(1).toItemStack());
                    } else if (e.isRightClick()) {
                        if (!player.getInventory().containsAtLeast(GlobalItem.I18_64.getItemStack(), 1)) return;
                        player.getInventory().removeItem(new ItemBuilder(GlobalItem.I18_64.getItemStack().clone()).setAmount(1).toItemStack());
                        player.getInventory().addItem(new ItemBuilder(GlobalItem.I18.getItemStack().clone()).setAmount(64).toItemStack());
                    }
                    return;
                case 26:
                    RPGCORE.getInstance().getStraganiarzManager().openGUI1(player);
                    return;
                default:
                    break;
            }
        }
    }
}
