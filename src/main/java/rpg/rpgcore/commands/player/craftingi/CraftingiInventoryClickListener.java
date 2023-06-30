package rpg.rpgcore.commands.player.craftingi;

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
import rpg.rpgcore.dodatki.akcesoriaP.helpers.AkcesoriaPodsHelper;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.ChanceHelper;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.ItemHelper;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.GlobalItem;

import java.util.UUID;

public class CraftingiInventoryClickListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onClick(final InventoryClickEvent e) {
        if (e.getInventory() == null || e.getClickedInventory() == null) {
            return;
        }

        final Inventory clickedInventory = e.getClickedInventory();
        final Player player = (Player) e.getWhoClicked();
        final UUID uuid = player.getUniqueId();
        final String title = Utils.removeColor(clickedInventory.getTitle());
        final ItemStack item = e.getCurrentItem();
        final int slot = e.getSlot();

        if (title.equals("Craftingi")) {
            e.setCancelled(true);
            if (item == null || item.getType() == Material.STAINED_GLASS_PANE) return;
            if (slot == 10) {
                RPGCORE.getInstance().getCraftingiManager().openCraftingAnielskie(player);
                return;
            }
            if (slot == 12) {
                RPGCORE.getInstance().getCraftingiManager().openCraftingDiabelskie(player);
                return;
            }
            if (slot == 14) {
                RPGCORE.getInstance().getCraftingiManager().openCratingSwietlnaZbroja(player);
                return;
            }
            if (slot == 16) {
                RPGCORE.getInstance().getCraftingiManager().openCraftingiInne(player);
                return;
            }
        }

        if (title.equals("Craftingi » Anielskie Akce")) {
            e.setCancelled(true);
            if (item == null || item.getType() == Material.STAINED_GLASS_PANE) return;
            if (slot == 22) {
                RPGCORE.getInstance().getCraftingiManager().openCraftingiGUI(player);
                return;
            }
            final User user = RPGCORE.getInstance().getUserManager().find(uuid);
            if (slot == 10) {
                if (player.getInventory().containsAtLeast(GlobalItem.getItem("I12", 1), 10) && player.getInventory().containsAtLeast(GlobalItem.getItem("I13", 1), 10)
                        && player.getInventory().containsAtLeast(GlobalItem.getItem("I14", 1), 10) && player.getInventory().containsAtLeast(GlobalItem.getItem("I15", 1), 10)
                        && player.getInventory().containsAtLeast(GlobalItem.getItem("I16", 1), 10) && player.getInventory().containsAtLeast(GlobalItem.getItem("I17", 1), 10)
                        && player.getInventory().containsAtLeast(GlobalItem.getItem("I18", 1), 10) && user.getKasa() >= 50_000) {
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I12", 1).clone()).setAmount(10).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I13", 1).clone()).setAmount(10).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I14", 1).clone()).setAmount(10).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I15", 1).clone()).setAmount(10).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I16", 1).clone()).setAmount(10).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I17", 1).clone()).setAmount(10).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I18", 1).clone()).setAmount(10).toItemStack());
                    user.setKasa(user.getKasa() - 50_000);
                    final int def = ChanceHelper.getRandInt(1, 16);
                    final int blok = ChanceHelper.getRandInt(1, 15);
                    final int hp = ChanceHelper.getRandInt(1, 5);
                    final int lvl = ChanceHelper.getRandInt(1, 30);
                    player.getInventory().addItem(AkcesoriaPodsHelper.createTarcza(def, blok, hp, lvl, "&f&lAnielska Tarcza"));
                    Bukkit.broadcastMessage(Utils.format("&e&lCraftingi &8>> &7Gracz &6" + player.getName() + " &7wytworzyl &f&lAnielska Tarcze &7(&f" + def + "&7/&f" + blok + "&7/&f" + hp + "&7/&f" + lvl + "&7)"));
                    RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataUser(uuid, user));
                    return;
                } else {
                    player.sendMessage(Utils.format("&e&lCraftingi &8>> &7Nie posiadasz wszystkich potrzebnych przedmiotow!"));
                    return;
                }
            }
            if (slot == 12) {
                if (player.getInventory().containsAtLeast(GlobalItem.getItem("I12", 1), 16) && player.getInventory().containsAtLeast(GlobalItem.getItem("I13", 1), 16)
                        && player.getInventory().containsAtLeast(GlobalItem.getItem("I14", 1), 16) && player.getInventory().containsAtLeast(GlobalItem.getItem("I15", 1), 16)
                        && player.getInventory().containsAtLeast(GlobalItem.getItem("I16", 1), 16) && player.getInventory().containsAtLeast(GlobalItem.getItem("I17", 1), 16)
                        && player.getInventory().containsAtLeast(GlobalItem.getItem("I18", 1), 16) && user.getKasa() >= 150_000) {
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I12", 1).clone()).setAmount(16).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I13", 1).clone()).setAmount(16).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I14", 1).clone()).setAmount(16).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I15", 1).clone()).setAmount(16).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I16", 1).clone()).setAmount(16).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I17", 1).clone()).setAmount(16).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I18", 1).clone()).setAmount(16).toItemStack());
                    user.setKasa(user.getKasa() - 150_000);
                    final int srdmg = ChanceHelper.getRandInt(1, 8);
                    final int potwory = ChanceHelper.getRandInt(1, 10);
                    final double exp = ChanceHelper.getRandInt(1, 2);
                    final int lvl = ChanceHelper.getRandInt(1, 30);
                    player.getInventory().addItem(AkcesoriaPodsHelper.createDiadem(srdmg, potwory, exp, lvl, "&f&lAnielski Diadem"));
                    Bukkit.broadcastMessage(Utils.format("&e&lCraftingi &8>> &7Gracz &6" + player.getName() + " &7wytworzyl &f&lAnielski Diadem &7(&f" + srdmg + "&7/&f" + potwory + "&7/&f" + exp + "&7/&f" + lvl + "&7)"));
                    RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataUser(uuid, user));
                    return;
                } else {
                    player.sendMessage(Utils.format("&e&lCraftingi &8>> &7Nie posiadasz wszystkich potrzebnych przedmiotow!"));
                    return;
                }
            }
            if (slot == 14) {
                if (player.getInventory().containsAtLeast(GlobalItem.getItem("I12", 1), 24) && player.getInventory().containsAtLeast(GlobalItem.getItem("I13", 1), 24)
                        && player.getInventory().containsAtLeast(GlobalItem.getItem("I14", 1), 24) && player.getInventory().containsAtLeast(GlobalItem.getItem("I15", 1), 24)
                        && player.getInventory().containsAtLeast(GlobalItem.getItem("I16", 1), 24) && player.getInventory().containsAtLeast(GlobalItem.getItem("I17", 1), 24)
                        && player.getInventory().containsAtLeast(GlobalItem.getItem("I18", 1), 24) && user.getKasa() >= 200_000) {
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I12", 1).clone()).setAmount(24).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I13", 1).clone()).setAmount(24).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I14", 1).clone()).setAmount(24).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I15", 1).clone()).setAmount(24).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I16", 1).clone()).setAmount(24).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I17", 1).clone()).setAmount(24).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I18", 1).clone()).setAmount(24).toItemStack());
                    user.setKasa(user.getKasa() - 200_000);
                    final int ddmg = ChanceHelper.getRandInt(1, 30);
                    final int kryt = ChanceHelper.getRandInt(1, 10);
                    final int srdmg = ChanceHelper.getRandInt(1, 10);
                    final int lvl = ChanceHelper.getRandInt(1, 30);
                    player.getInventory().addItem(AkcesoriaPodsHelper.createNaszyjnik(ddmg, kryt, srdmg, lvl, "&f&lAnielski Naszyjnik"));
                    Bukkit.broadcastMessage(Utils.format("&e&lCraftingi &8>> &7Gracz &6" + player.getName() + " &7wytworzyl &f&lAnielski Naszyjnik &7(&f" + ddmg + "&7/&f" + kryt + "&7/&f" + srdmg + "&7/&f" + lvl + "&7)"));
                    RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataUser(uuid, user));
                    return;
                } else {
                    player.sendMessage(Utils.format("&e&lCraftingi &8>> &7Nie posiadasz wszystkich potrzebnych przedmiotow!"));
                    return;
                }
            }
            if (slot == 16) {
                if (player.getInventory().containsAtLeast(GlobalItem.getItem("I12", 1), 16) && player.getInventory().containsAtLeast(GlobalItem.getItem("I13", 1), 16)
                        && player.getInventory().containsAtLeast(GlobalItem.getItem("I14", 1), 16) && player.getInventory().containsAtLeast(GlobalItem.getItem("I15", 1), 16)
                        && player.getInventory().containsAtLeast(GlobalItem.getItem("I16", 1), 16) && player.getInventory().containsAtLeast(GlobalItem.getItem("I17", 1), 16)
                        && player.getInventory().containsAtLeast(GlobalItem.getItem("I18", 1), 16) && user.getKasa() >= 100_000) {
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I12", 1).clone()).setAmount(16).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I13", 1).clone()).setAmount(16).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I14", 1).clone()).setAmount(16).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I15", 1).clone()).setAmount(16).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I16", 1).clone()).setAmount(16).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I17", 1).clone()).setAmount(16).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I18", 1).clone()).setAmount(16).toItemStack());
                    user.setKasa(user.getKasa() - 100_000);
                    final int przeszycie = ChanceHelper.getRandInt(1, 5);
                    final int kryt = ChanceHelper.getRandInt(1, 10);
                    final int speed = ChanceHelper.getRandInt(1, 30);
                    final int lvl = ChanceHelper.getRandInt(1, 30);
                    player.getInventory().addItem(AkcesoriaPodsHelper.createPierscien(przeszycie, kryt, speed, lvl, "&f&lAnielski Pierscien"));
                    Bukkit.broadcastMessage(Utils.format("&e&lCraftingi &8>> &7Gracz &6" + player.getName() + " &7wytworzyl &f&lAnielski Pierscien &7(&f" + przeszycie + "&7/&f" + kryt + "&7/&f" + speed + "&7/&f" + lvl + "&7)"));
                    RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataUser(uuid, user));
                    return;
                } else {
                    player.sendMessage(Utils.format("&e&lCraftingi &8>> &7Nie posiadasz wszystkich potrzebnych przedmiotow!"));
                    return;
                }
            }
        }

        if (title.equals("Craftingi » Diabelskie Akce")) {
            e.setCancelled(true);
            if (item == null || item.getType() == Material.STAINED_GLASS_PANE) return;
            if (slot == 22) {
                RPGCORE.getInstance().getCraftingiManager().openCraftingiGUI(player);
                return;
            }
            final User user = RPGCORE.getInstance().getUserManager().find(uuid);
            if (slot == 10) {
                if (player.getInventory().containsAtLeast(GlobalItem.getItem("I12", 1), 24) && player.getInventory().containsAtLeast(GlobalItem.getItem("I13", 1), 24)
                        && player.getInventory().containsAtLeast(GlobalItem.getItem("I14", 1), 24) && player.getInventory().containsAtLeast(GlobalItem.getItem("I15", 1), 24)
                        && player.getInventory().containsAtLeast(GlobalItem.getItem("I16", 1), 24) && player.getInventory().containsAtLeast(GlobalItem.getItem("I17", 1), 24)
                        && player.getInventory().containsAtLeast(GlobalItem.getItem("I18", 1), 24) && user.getKasa() >= 200_000) {
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I12", 1).clone()).setAmount(24).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I13", 1).clone()).setAmount(24).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I14", 1).clone()).setAmount(24).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I15", 1).clone()).setAmount(24).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I16", 1).clone()).setAmount(24).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I17", 1).clone()).setAmount(24).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I18", 1).clone()).setAmount(24).toItemStack());
                    user.setKasa(user.getKasa() - 200_000);
                    final int def = ChanceHelper.getRandInt(5, 28);
                    final int blok = ChanceHelper.getRandInt(3, 26);
                    final int hp = ChanceHelper.getRandInt(1, 14);
                    final int lvl = ChanceHelper.getRandInt(30, 60);
                    player.getInventory().addItem(AkcesoriaPodsHelper.createTarcza(def, blok, hp, lvl, "&4&lDiabelska Tarcza"));
                    Bukkit.broadcastMessage(Utils.format("&e&lCraftingi &8>> &7Gracz &6" + player.getName() + " &7wytworzyl &4&lDiabelska Tarcze &7(&f" + def + "&7/&f" + blok + "&7/&f" + hp + "&7/&f" + lvl + "&7)"));
                    RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataUser(uuid, user));
                    return;
                } else {
                    player.sendMessage(Utils.format("&e&lCraftingi &8>> &7Nie posiadasz wszystkich potrzebnych przedmiotow!"));
                    return;
                }
            }
            if (slot == 12) {
                if (player.getInventory().containsAtLeast(GlobalItem.getItem("I12", 1), 36) && player.getInventory().containsAtLeast(GlobalItem.getItem("I13", 1), 36)
                        && player.getInventory().containsAtLeast(GlobalItem.getItem("I14", 1), 36) && player.getInventory().containsAtLeast(GlobalItem.getItem("I15", 1), 36)
                        && player.getInventory().containsAtLeast(GlobalItem.getItem("I16", 1), 36) && player.getInventory().containsAtLeast(GlobalItem.getItem("I17", 1), 36)
                        && player.getInventory().containsAtLeast(GlobalItem.getItem("I18", 1), 36) && user.getKasa() >= 400_000) {
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I12", 1).clone()).setAmount(36).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I13", 1).clone()).setAmount(36).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I14", 1).clone()).setAmount(36).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I15", 1).clone()).setAmount(36).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I16", 1).clone()).setAmount(36).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I17", 1).clone()).setAmount(36).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I18", 1).clone()).setAmount(36).toItemStack());
                    user.setKasa(user.getKasa() - 400_000);
                    final int srdmg = ChanceHelper.getRandInt(3, 15);
                    final int potwory = ChanceHelper.getRandInt(3, 15);
                    final double exp = ChanceHelper.getRandInt(1, 3);
                    final int lvl = ChanceHelper.getRandInt(30, 60);
                    player.getInventory().addItem(AkcesoriaPodsHelper.createDiadem(srdmg, potwory, exp, lvl, "&4&lDiabelski Diadem"));
                    Bukkit.broadcastMessage(Utils.format("&e&lCraftingi &8>> &7Gracz &6" + player.getName() + " &7wytworzyl &4&lDiabelski Diadem &7(&f" + srdmg + "&7/&f" + potwory + "&7/&f" + exp + "&7/&f" + lvl + "&7)"));
                    RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataUser(uuid, user));
                    return;
                } else {
                    player.sendMessage(Utils.format("&e&lCraftingi &8>> &7Nie posiadasz wszystkich potrzebnych przedmiotow!"));
                    return;
                }
            }
            if (slot == 13) {
                if (player.getInventory().containsAtLeast(GlobalItem.getItem("I12", 1), 42) && player.getInventory().containsAtLeast(GlobalItem.getItem("I13", 1), 42)
                        && player.getInventory().containsAtLeast(GlobalItem.getItem("I14", 1), 42) && player.getInventory().containsAtLeast(GlobalItem.getItem("I15", 1), 42)
                        && player.getInventory().containsAtLeast(GlobalItem.getItem("I16", 1), 42) && player.getInventory().containsAtLeast(GlobalItem.getItem("I17", 1), 42)
                        && player.getInventory().containsAtLeast(GlobalItem.getItem("I18", 1), 42) && user.getKasa() >= 400_000) {
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I12", 1).clone()).setAmount(42).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I13", 1).clone()).setAmount(42).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I14", 1).clone()).setAmount(42).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I15", 1).clone()).setAmount(42).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I16", 1).clone()).setAmount(42).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I17", 1).clone()).setAmount(42).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I18", 1).clone()).setAmount(42).toItemStack());
                    user.setKasa(user.getKasa() - 400_000);
                    final int ludzie = ChanceHelper.getRandInt(3, 18);
                    final int def = ChanceHelper.getRandInt(2, 15);
                    final int mspeed = ChanceHelper.getRandInt(10, 30);
                    final int lvl = ChanceHelper.getRandInt(30, 60);
                    player.getInventory().addItem(AkcesoriaPodsHelper.createKolczyki(ludzie, def, mspeed, lvl, "&4&lDiabelskie Kolczyki"));
                    Bukkit.broadcastMessage(Utils.format("&e&lCraftingi &8>> &7Gracz &6" + player.getName() + " &7wytworzyl &4&lDiabelskie Kolczyki &7(&f" + ludzie + "&7/&f" + def + "&7/&f" + mspeed + "&7/&f" + lvl + "&7)"));
                    RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataUser(uuid, user));
                    return;
                } else {
                    player.sendMessage(Utils.format("&e&lCraftingi &8>> &7Nie posiadasz wszystkich potrzebnych przedmiotow!"));
                    return;
                }
            }
            if (slot == 14) {
                if (player.getInventory().containsAtLeast(GlobalItem.getItem("I12", 1), 48) && player.getInventory().containsAtLeast(GlobalItem.getItem("I13", 1), 48)
                        && player.getInventory().containsAtLeast(GlobalItem.getItem("I14", 1), 48) && player.getInventory().containsAtLeast(GlobalItem.getItem("I15", 1), 48)
                        && player.getInventory().containsAtLeast(GlobalItem.getItem("I16", 1), 48) && player.getInventory().containsAtLeast(GlobalItem.getItem("I17", 1), 48)
                        && player.getInventory().containsAtLeast(GlobalItem.getItem("I18", 1), 48) && user.getKasa() >= 500_000) {
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I12", 1).clone()).setAmount(48).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I13", 1).clone()).setAmount(48).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I14", 1).clone()).setAmount(48).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I15", 1).clone()).setAmount(48).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I16", 1).clone()).setAmount(48).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I17", 1).clone()).setAmount(48).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I18", 1).clone()).setAmount(48).toItemStack());
                    user.setKasa(user.getKasa() - 500_000);
                    final int ddmg = ChanceHelper.getRandInt(12, 90);
                    final int kryt = ChanceHelper.getRandInt(5, 17);
                    final int srdmg = ChanceHelper.getRandInt(5, 17);
                    final int lvl = ChanceHelper.getRandInt(30, 60);
                    player.getInventory().addItem(AkcesoriaPodsHelper.createNaszyjnik(ddmg, kryt, srdmg, lvl, "&4&lDiabelski Naszyjnik"));
                    Bukkit.broadcastMessage(Utils.format("&e&lCraftingi &8>> &7Gracz &6" + player.getName() + " &7wytworzyl &4&lDiabelski Naszyjnik &7(&f" + ddmg + "&7/&f" + kryt + "&7/&f" + srdmg + "&7/&f" + lvl + "&7)"));
                    RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataUser(uuid, user));
                    return;
                } else {
                    player.sendMessage(Utils.format("&e&lCraftingi &8>> &7Nie posiadasz wszystkich potrzebnych przedmiotow!"));
                    return;
                }
            }
            if (slot == 16) {
                if (player.getInventory().containsAtLeast(GlobalItem.getItem("I12", 1), 36) && player.getInventory().containsAtLeast(GlobalItem.getItem("I13", 1), 36)
                        && player.getInventory().containsAtLeast(GlobalItem.getItem("I14", 1), 36) && player.getInventory().containsAtLeast(GlobalItem.getItem("I15", 1), 36)
                        && player.getInventory().containsAtLeast(GlobalItem.getItem("I16", 1), 36) && player.getInventory().containsAtLeast(GlobalItem.getItem("I17", 1), 36)
                        && player.getInventory().containsAtLeast(GlobalItem.getItem("I18", 1), 36) && user.getKasa() >= 300_000) {
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I12", 1).clone()).setAmount(36).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I13", 1).clone()).setAmount(36).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I14", 1).clone()).setAmount(36).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I15", 1).clone()).setAmount(36).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I16", 1).clone()).setAmount(36).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I17", 1).clone()).setAmount(36).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I18", 1).clone()).setAmount(36).toItemStack());
                    user.setKasa(user.getKasa() - 300_000);
                    final int przeszycie = ChanceHelper.getRandInt(4, 15);
                    final int kryt = ChanceHelper.getRandInt(3, 16);
                    final int speed = ChanceHelper.getRandInt(15, 50);
                    final int lvl = ChanceHelper.getRandInt(30, 60);
                    player.getInventory().addItem(AkcesoriaPodsHelper.createPierscien(przeszycie, kryt, speed, lvl, "&4&lDiabelski Pierscien"));
                    Bukkit.broadcastMessage(Utils.format("&e&lCraftingi &8>> &7Gracz &6" + player.getName() + " &7wytworzyl &4&lDiabelski Pierscien &7(&f" + przeszycie + "&7/&f" + kryt + "&7/&f" + speed + "&7/&f" + lvl + "&7)"));
                    RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataUser(uuid, user));
                    return;
                } else {
                    player.sendMessage(Utils.format("&e&lCraftingi &8>> &7Nie posiadasz wszystkich potrzebnych przedmiotow!"));
                    return;
                }
            }
        }

        if (title.equals("Craftingi » Swietlne EQ")) {
            e.setCancelled(true);
            if (item == null || item.getType() == Material.STAINED_GLASS_PANE) return;
            if (slot == 22) {
                RPGCORE.getInstance().getCraftingiManager().openCraftingiGUI(player);
                return;
            }
            final User user = RPGCORE.getInstance().getUserManager().find(uuid);
            if (slot == 10) {
                if (player.getInventory().containsAtLeast(GlobalItem.getItem("I12", 1), 40) && player.getInventory().containsAtLeast(GlobalItem.getItem("I13", 1), 40)
                        && player.getInventory().containsAtLeast(GlobalItem.getItem("I14", 1), 40) && player.getInventory().containsAtLeast(GlobalItem.getItem("I15", 1), 40)
                        && player.getInventory().containsAtLeast(GlobalItem.getItem("I16", 1), 40) && player.getInventory().containsAtLeast(GlobalItem.getItem("I17", 1), 40)
                        && player.getInventory().containsAtLeast(GlobalItem.getItem("I18", 1), 40) && user.getKasa() >= 1_000_000) {
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I12", 1).clone()).setAmount(40).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I13", 1).clone()).setAmount(40).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I14", 1).clone()).setAmount(40).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I15", 1).clone()).setAmount(40).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I16", 1).clone()).setAmount(40).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I17", 1).clone()).setAmount(40).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I18", 1).clone()).setAmount(40).toItemStack());
                    user.setKasa(user.getKasa() - 1_000_000);
                    final int prot = ChanceHelper.getRandInt(1, 75);
                    final int thorns = ChanceHelper.getRandInt(1, 20);
                    player.getInventory().addItem(ItemHelper.createArmor("&e&lSwietlny Helm", Material.DIAMOND_HELMET, prot, thorns));
                    Bukkit.broadcastMessage(Utils.format("&e&lCraftingi &8>> &7Gracz &6" + player.getName() + " &7wytworzyl &e&lSwietlny Helm &7(&f" + prot + "&7/&f" + thorns + "&7)"));
                    RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataUser(uuid, user));
                    return;
                } else {
                    player.sendMessage(Utils.format("&e&lCraftingi &8>> &7Nie posiadasz wszystkich potrzebnych przedmiotow!"));
                    return;
                }
            }
            if (slot == 12) {
                if (player.getInventory().containsAtLeast(GlobalItem.getItem("I12", 1), 64) && player.getInventory().containsAtLeast(GlobalItem.getItem("I13", 1), 64)
                        && player.getInventory().containsAtLeast(GlobalItem.getItem("I14", 1), 64) && player.getInventory().containsAtLeast(GlobalItem.getItem("I15", 1), 64)
                        && player.getInventory().containsAtLeast(GlobalItem.getItem("I16", 1), 64) && player.getInventory().containsAtLeast(GlobalItem.getItem("I17", 1), 64)
                        && player.getInventory().containsAtLeast(GlobalItem.getItem("I18", 1), 64) && user.getKasa() >= 1_250_000) {
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I12", 1).clone()).setAmount(64).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I13", 1).clone()).setAmount(64).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I14", 1).clone()).setAmount(64).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I15", 1).clone()).setAmount(64).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I16", 1).clone()).setAmount(64).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I17", 1).clone()).setAmount(64).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I18", 1).clone()).setAmount(64).toItemStack());
                    user.setKasa(user.getKasa() - 1_250_000);
                    final int prot = ChanceHelper.getRandInt(1, 75);
                    final int thorns = ChanceHelper.getRandInt(1, 20);
                    player.getInventory().addItem(ItemHelper.createArmor("&e&lSwietlny Napiersnik", Material.DIAMOND_CHESTPLATE, prot, thorns));
                    Bukkit.broadcastMessage(Utils.format("&e&lCraftingi &8>> &7Gracz &6" + player.getName() + " &7wytworzyl &e&lSwietlny Napiersnik &7(&f" + prot + "&7/&f" + thorns + "&7)"));
                    RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataUser(uuid, user));
                    return;
                } else {
                    player.sendMessage(Utils.format("&e&lCraftingi &8>> &7Nie posiadasz wszystkich potrzebnych przedmiotow!"));
                    return;
                }
            }
            if (slot == 14) {
                if (player.getInventory().containsAtLeast(GlobalItem.getItem("I12", 1), 56) && player.getInventory().containsAtLeast(GlobalItem.getItem("I13", 1), 56)
                        && player.getInventory().containsAtLeast(GlobalItem.getItem("I14", 1), 56) && player.getInventory().containsAtLeast(GlobalItem.getItem("I15", 1), 56)
                        && player.getInventory().containsAtLeast(GlobalItem.getItem("I16", 1), 56) && player.getInventory().containsAtLeast(GlobalItem.getItem("I17", 1), 56)
                        && player.getInventory().containsAtLeast(GlobalItem.getItem("I18", 1), 56) && user.getKasa() >= 1_150_000) {
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I12", 1).clone()).setAmount(56).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I13", 1).clone()).setAmount(56).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I14", 1).clone()).setAmount(56).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I15", 1).clone()).setAmount(56).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I16", 1).clone()).setAmount(56).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I17", 1).clone()).setAmount(56).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I18", 1).clone()).setAmount(56).toItemStack());
                    user.setKasa(user.getKasa() - 1_150_000);
                    final int prot = ChanceHelper.getRandInt(1, 75);
                    final int thorns = ChanceHelper.getRandInt(1, 20);
                    player.getInventory().addItem(ItemHelper.createArmor("&e&lSwietlne Nogawice", Material.DIAMOND_LEGGINGS, prot, thorns));
                    Bukkit.broadcastMessage(Utils.format("&e&lCraftingi &8>> &7Gracz &6" + player.getName() + " &7wytworzyl &e&lSwietlne Nogawice &7(&f" + prot + "&7/&f" + thorns + "&7)"));
                    RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataUser(uuid, user));
                    return;
                } else {
                    player.sendMessage(Utils.format("&e&lCraftingi &8>> &7Nie posiadasz wszystkich potrzebnych przedmiotow!"));
                    return;
                }
            }
            if (slot == 16) {
                if (player.getInventory().containsAtLeast(GlobalItem.getItem("I12", 1), 32) && player.getInventory().containsAtLeast(GlobalItem.getItem("I13", 1), 32)
                        && player.getInventory().containsAtLeast(GlobalItem.getItem("I14", 1), 32) && player.getInventory().containsAtLeast(GlobalItem.getItem("I15", 1), 32)
                        && player.getInventory().containsAtLeast(GlobalItem.getItem("I16", 1), 32) && player.getInventory().containsAtLeast(GlobalItem.getItem("I17", 1), 32)
                        && player.getInventory().containsAtLeast(GlobalItem.getItem("I18", 1), 32) && user.getKasa() >= 1_000_000) {
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I12", 1).clone()).setAmount(32).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I13", 1).clone()).setAmount(32).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I14", 1).clone()).setAmount(32).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I15", 1).clone()).setAmount(32).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I16", 1).clone()).setAmount(32).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I17", 1).clone()).setAmount(32).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I18", 1).clone()).setAmount(32).toItemStack());
                    user.setKasa(user.getKasa() - 1_000_000);
                    final int prot = ChanceHelper.getRandInt(1, 75);
                    final int thorns = ChanceHelper.getRandInt(1, 20);
                    player.getInventory().addItem(ItemHelper.createArmor("&e&lSwietlne Buty", Material.DIAMOND_BOOTS, prot, thorns));
                    Bukkit.broadcastMessage(Utils.format("&e&lCraftingi &8>> &7Gracz &6" + player.getName() + " &7wytworzyl &e&lSwietlne Buty &7(&f" + prot + "&7/&f" + thorns + "&7)"));
                    RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataUser(uuid, user));
                    return;
                } else {
                    player.sendMessage(Utils.format("&e&lCraftingi &8>> &7Nie posiadasz wszystkich potrzebnych przedmiotow!"));
                    return;
                }
            }
        }

        if (title.equals("Craftingi » Inne")) {
            e.setCancelled(true);
            if (item == null || item.getType() == Material.STAINED_GLASS_PANE) return;
            if (slot == 22) {
                RPGCORE.getInstance().getCraftingiManager().openCraftingiGUI(player);
                return;
            }
            final User user = RPGCORE.getInstance().getUserManager().find(uuid);
            if (slot == 10) {
                if (player.getInventory().containsAtLeast(GlobalItem.getItem("I12", 1), 14) && player.getInventory().containsAtLeast(GlobalItem.getItem("I13", 1), 14)
                        && player.getInventory().containsAtLeast(GlobalItem.getItem("I14", 1), 14) && player.getInventory().containsAtLeast(GlobalItem.getItem("I15", 1), 14)
                        && player.getInventory().containsAtLeast(GlobalItem.getItem("I16", 1), 14) && player.getInventory().containsAtLeast(GlobalItem.getItem("I17", 1), 14)
                        && player.getInventory().containsAtLeast(GlobalItem.getItem("I18", 1), 14) && user.getKasa() >= 300_000) {
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I12", 1).clone()).setAmount(14).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I13", 1).clone()).setAmount(14).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I14", 1).clone()).setAmount(14).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I15", 1).clone()).setAmount(14).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I16", 1).clone()).setAmount(14).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I17", 1).clone()).setAmount(14).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I18", 1).clone()).setAmount(14).toItemStack());
                    user.setKasa(user.getKasa() - 300_000);
                    player.getInventory().addItem(GlobalItem.getItem("I50", 1).clone());
                    Bukkit.broadcastMessage(Utils.format("&e&lCraftingi &8>> &7Gracz &6" + player.getName() + " &7wytworzyl &6&lKamien Zaczarowania Miecza"));
                    RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataUser(uuid, user));
                    return;
                } else {
                    player.sendMessage(Utils.format("&e&lCraftingi &8>> &7Nie posiadasz wszystkich potrzebnych przedmiotow!"));
                    return;
                }
            }
            if (slot == 12) {
                if (player.getInventory().containsAtLeast(GlobalItem.getItem("I_FRAGMENT_STALI", 1), 3) && player.getInventory().containsAtLeast(GlobalItem.getItem("I12", 1), 16) && player.getInventory().containsAtLeast(GlobalItem.getItem("I15", 1), 16)
                        && player.getInventory().containsAtLeast(GlobalItem.getItem("I16", 1), 16) && player.getInventory().containsAtLeast(GlobalItem.getItem("I17", 1), 16)
                        && user.getKasa() >= 1_000_000) {
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I_FRAGMENT_STALI", 1).clone()).setAmount(3).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I12", 1).clone()).setAmount(16).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I15", 1).clone()).setAmount(16).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I16", 1).clone()).setAmount(16).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I17", 1).clone()).setAmount(16).toItemStack());
                    user.setKasa(user.getKasa() - 1_000_000);
                    player.getInventory().addItem(GlobalItem.getItem("I_METAL", 1).clone());
                    Bukkit.broadcastMessage(Utils.format("&e&lCraftingi &8>> &7Gracz &6" + player.getName() + " &7wytworzyl &7&lMagiczna Stal"));
                    RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataUser(uuid, user));
                    return;
                }
            }
            if (slot == 13) {
                if (player.getInventory().containsAtLeast(GlobalItem.getItem("I_FRAGMENT_STALI", 1), 1) && player.getInventory().containsAtLeast(GlobalItem.getItem("I13", 1), 16) && player.getInventory().containsAtLeast(GlobalItem.getItem("I14", 1), 16)
                        && player.getInventory().containsAtLeast(GlobalItem.getItem("I18", 1), 16) && user.getKasa() >= 700_000) {
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I_FRAGMENT_STALI", 1).clone()).setAmount(1).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I13", 1).clone()).setAmount(16).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I14", 1).clone()).setAmount(16).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I18", 1).clone()).setAmount(16).toItemStack());
                    user.setKasa(user.getKasa() - 700_000);
                    player.getInventory().addItem(GlobalItem.getItem("I_OCZYSZCZENIE", 1).clone());
                    Bukkit.broadcastMessage(Utils.format("&e&lCraftingi &8>> &7Gracz &6" + player.getName() + " &7wytworzyl &fLze Aniola"));
                    RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataUser(uuid, user));
                    return;
                } else {
                    player.sendMessage(Utils.format("&e&lCraftingi &8>> &7Nie posiadasz wszystkich potrzebnych przedmiotow!"));
                    return;
                }
            }
            if (slot == 15) {
                if (player.getInventory().containsAtLeast(GlobalItem.getItem("I_CZASTkA_MAGII", 1), 1) && player.getInventory().containsAtLeast(GlobalItem.getItem("I12", 1), 32)
                        && player.getInventory().containsAtLeast(GlobalItem.getItem("I13", 1), 32) && player.getInventory().containsAtLeast(GlobalItem.getItem("I14", 1), 32)
                        && player.getInventory().containsAtLeast(GlobalItem.getItem("I15", 1), 32) && player.getInventory().containsAtLeast(GlobalItem.getItem("I18", 1), 32)
                        && user.getKasa() >= 3_000_000) {
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I_CZASTkA_MAGII", 1).clone()).setAmount(1).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I12", 1).clone()).setAmount(32).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I13", 1).clone()).setAmount(32).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I14", 1).clone()).setAmount(32).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I15", 1).clone()).setAmount(32).toItemStack());
                    player.getInventory().removeItem(new ItemBuilder(GlobalItem.getItem("I18", 1).clone()).setAmount(32).toItemStack());
                    user.setKasa(user.getKasa() - 3_000_000);
                    player.getInventory().addItem(GlobalItem.getItem("I_KAMIENBAO", 1).clone());
                    Bukkit.broadcastMessage(Utils.format("&e&lCraftingi &8>> &7Gracz &6" + player.getName() + " &7wytworzyl &3&lKamien Zaczarowania Stolu"));
                    RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataUser(uuid, user));
                } else {
                    player.sendMessage(Utils.format("&e&lCraftingi &8>> &7Nie posiadasz wszystkich potrzebnych przedmiotow!"));
                }
            }
        }
    }
}
