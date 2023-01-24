package rpg.rpgcore.npc.rybak.events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.bonuses.Bonuses;
import rpg.rpgcore.npc.rybak.enums.RybakMissions;
import rpg.rpgcore.npc.rybak.objects.RybakObject;
import rpg.rpgcore.npc.rybak.objects.RybakUser;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.npc.RybakItems;

import java.util.UUID;

public class RybakInventoryClick implements Listener {

    private final RPGCORE rpgcore;

    public RybakInventoryClick(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void rybakInventoryClick(final InventoryClickEvent e) {

        final Inventory clickedInventory = e.getClickedInventory();
        final Player player = (Player) e.getWhoClicked();
        final UUID uuid = player.getUniqueId();

        if (e.getClickedInventory() == null || e.getInventory() == null) {
            return;
        }

        final String title = Utils.removeColor(clickedInventory.getTitle());
        final ItemStack item = e.getCurrentItem();
        final int slot = e.getSlot();

        if (title.equals("Rybak")) {
            e.setCancelled(true);
            if (item.getType() == Material.STAINED_GLASS_PANE || item.getType() == Material.PAPER) {
                return;
            }

            if (slot == 11) {
                rpgcore.getRybakNPC().openKampaniaGUI(player);
                return;
            }
            if (slot == 15) {
                rpgcore.getRybakNPC().openSklepGUI(player);
            }
        }

        if (title.equals("Kampania Rybacka")) {
            e.setCancelled(true);
            if (item.getType() == Material.BOOK || item.getType() == Material.BOOK_AND_QUILL) return;

            final RybakObject rybakObject = rpgcore.getRybakNPC().find(uuid);
            final RybakUser user = rybakObject.getRybakUser();

            final RybakMissions mission = RybakMissions.getMission(user.getMission());
            assert mission != null;

            if (user.getProgress() >= mission.getReqAmount()) {
                final Bonuses bonuses = rpgcore.getBonusesManager().find(uuid);

                user.setProgress(0);
                user.setMission(user.getMission() + 1);
                user.setKryt(user.getKryt() + mission.getKryt());
                user.setBlok(user.getBlok() + mission.getBlok());
                user.setSrDef(user.getSrDef() + mission.getSrDef());
                user.setMorskieSzczescie(user.getMorskieSzczescie() + mission.getMorskieSzczescie());

                bonuses.getBonusesUser().setSredniadefensywa(bonuses.getBonusesUser().getSredniadefensywa() + mission.getSrDef());
                bonuses.getBonusesUser().setSzansanakryta(bonuses.getBonusesUser().getSzansanakryta() + mission.getKryt());
                bonuses.getBonusesUser().setBlokciosu(bonuses.getBonusesUser().getBlokciosu() + mission.getBlok());

                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
                    rpgcore.getMongoManager().saveDataRybak(uuid, rybakObject);
                    rpgcore.getMongoManager().saveDataBonuses(uuid, bonuses);
                });
                if (user.getMission() == 27) {
                        RPGCORE.getInstance().getServer().broadcastMessage(" ");
                        RPGCORE.getInstance().getServer().broadcastMessage(Utils.format("&6&lRybak &8>> &7Gracz &c" + player.getName() + " &7ukonczyl moja &4KAMPANIE&7. &6&lGratulacje!"));
                        if (RPGCORE.getInstance().getArtefaktyZaLvlManager().getArtefaktyZaLvl().getRybak().getNadanych() < 4) {
                            if (!RPGCORE.getInstance().getArtefaktyZaLvlManager().getArtefaktyZaLvl().getRybak().getGracze().contains(player.getName())) {
                                RPGCORE.getInstance().getArtefaktyZaLvlManager().getArtefaktyZaLvl().getRybak().setNadanych(RPGCORE.getInstance().getArtefaktyZaLvlManager().getArtefaktyZaLvl().getRybak().getNadanych() + 1);
                                RPGCORE.getInstance().getArtefaktyZaLvlManager().getArtefaktyZaLvl().getRybak().getGracze().add(player.getName());
                                RPGCORE.getInstance().getArtefaktyZaLvlManager().save();
                                RPGCORE.getInstance().getServer().broadcastMessage(Utils.format("&6&lRybak &8>> &7Doodatkowo gracz ten ukonczyl ja jako &c" + RPGCORE.getInstance().getArtefaktyZaLvlManager().getArtefaktyZaLvl().getRybak().getNadanych() + " &7osoba na serwerze!"));
                                RPGCORE.getInstance().getServer().broadcastMessage(Utils.format("&8Po odbior artefaktu prosimy zglosic sie"));
                                RPGCORE.getInstance().getServer().broadcastMessage(Utils.format("&8do &4Wyzszej Administracji&8."));
                            }
                        }
                        RPGCORE.getInstance().getServer().broadcastMessage(" ");
                } else {
                    rpgcore.getServer().broadcastMessage(Utils.format("&6&lRybak &8>> &7Gracz &6" + player.getName() + " &7ukonczyl &6" + mission.getMission() + " &7misje!"));
                }
                return;
            }

            if (mission == RybakMissions.M7) {
                if (player.getItemInHand() == null || player.getItemInHand().getType() != Material.FISHING_ROD) {
                    player.sendMessage(Utils.format("&6&lRybak &8>> &7Musisz trzymac wedke w rece, zeby wykonac te misje!"));
                    return;
                }

                final int lvl = Utils.getTagInt(player.getItemInHand(), "lvl");
                if (lvl >= 5) {
                    user.setProgress(1);
                }
            } else if (mission == RybakMissions.M21) {
                if (player.getItemInHand() == null || player.getItemInHand().getType() != Material.FISHING_ROD) {
                    player.sendMessage(Utils.format("&6&lRybak &8>> &7Musisz trzymac wedke w rece, zeby wykonac te misje!"));
                    return;
                }

                final int lvl = Utils.getTagInt(player.getItemInHand(), "lvl");
                if (lvl >= 15) {
                    user.setProgress(1);
                }
            }

            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataRybak(uuid, rybakObject));

        }

        if (title.equals("Sklep Rybacki")) {
            e.setCancelled(true);
            if (e.getClick() == ClickType.SHIFT_LEFT || e.getClick() == ClickType.SHIFT_RIGHT) return;
            if (item == null || item.getType() == Material.AIR) return;

            final RybakObject rybakObject = rpgcore.getRybakNPC().find(uuid);
            final User user = rpgcore.getUserManager().find(uuid);
            int amount = 0;
            ItemStack toRemove;
            switch (slot) {
                case 0:
                    if (user.getKasa() < 25000000) {
                        player.sendMessage(Utils.format("&6&lRybak &8>> &cCzy ty probujesz mnie oszukac?!"));
                        return;
                    }
                    user.setKasa(user.getKasa() - 25000000);
                    player.getInventory().addItem(RybakItems.getWedka(player.getName(), 1));
                    player.sendMessage(Utils.format("&6&lRybak &8>> &aPomyslnie zakupiles moja wedke. Niech Ci dobrze sluzy..."));
                    rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataUser(uuid, user));
                    return;
                case 1:
                    if (!player.getInventory().containsAtLeast(RybakItems.I1.getItemStack(), 1)) {
                        return;
                    }
                    if (e.getClick() == ClickType.RIGHT) {
                        for (ItemStack is : player.getInventory().getContents()) {
                            if (is != null && is.getType() != Material.AIR && is == RybakItems.I1.getItemStack()) {
                                amount += is.getAmount();
                            }
                        }
                    } else {
                        amount = 1;
                    }
                    toRemove = new ItemBuilder(RybakItems.I1.getItemStack().clone()).setAmount(amount).toItemStack();
                    user.setKasa(user.getKasa() + (amount * 1000));
                    player.getInventory().removeItem(toRemove);
                    player.sendMessage(Utils.format("&6&lRybak &8>> &aPomyslnie sprzedales &6" + amount + " &a&l" + RybakItems.I1.getItemStack().getItemMeta().getDisplayName() + " &aza &6" + (amount * 1000) + " &amonet!"));
                    if (rybakObject.getRybakUser().getMission() == 13 || rybakObject.getRybakUser().getMission() == 14) {
                        rybakObject.getRybakUser().setProgress(rybakObject.getRybakUser().getProgress() + amount);
                    }
                    break;
                case 2:
                    if (!player.getInventory().containsAtLeast(RybakItems.I2.getItemStack(), 1)) {
                        return;
                    }
                    if (e.getClick() == ClickType.RIGHT) {
                        for (ItemStack is : player.getInventory().getContents()) {
                            if (is != null && is.getType() != Material.AIR && is == RybakItems.I2.getItemStack()) {
                                amount += is.getAmount();
                            }
                        }
                    } else {
                        amount = 1;
                    }
                    toRemove = new ItemBuilder(RybakItems.I2.getItemStack().clone()).setAmount(amount).toItemStack();
                    user.setKasa(user.getKasa() + (amount * 1500));
                    if (rybakObject.getRybakUser().getMission() == 13 || rybakObject.getRybakUser().getMission() == 15) {
                        rybakObject.getRybakUser().setProgress(rybakObject.getRybakUser().getProgress() + amount);
                    }
                    break;
                case 3:
                    if (!player.getInventory().containsAtLeast(RybakItems.I3.getItemStack(), 1)) {
                        return;
                    }
                    if (e.getClick() == ClickType.RIGHT) {
                        for (ItemStack is : player.getInventory().getContents()) {
                            if (is != null && is.getType() != Material.AIR && is == RybakItems.I3.getItemStack()) {
                                amount += is.getAmount();
                            }
                        }
                    } else {
                        amount = 1;
                    }
                    toRemove = new ItemBuilder(RybakItems.I3.getItemStack().clone()).setAmount(amount).toItemStack();
                    user.setKasa(user.getKasa() + (amount * 1500));
                    if (rybakObject.getRybakUser().getMission() == 13 || rybakObject.getRybakUser().getMission() == 16) {
                        rybakObject.getRybakUser().setProgress(rybakObject.getRybakUser().getProgress() + amount);
                    }
                    break;
                case 4:
                    if (!player.getInventory().containsAtLeast(RybakItems.I4.getItemStack(), 1)) {
                        return;
                    }
                    if (e.getClick() == ClickType.RIGHT) {
                        for (ItemStack is : player.getInventory().getContents()) {
                            if (is != null && is.getType() != Material.AIR && is == RybakItems.I4.getItemStack()) {
                                amount += is.getAmount();
                            }
                        }
                    } else {
                        amount = 1;
                    }
                    toRemove = new ItemBuilder(RybakItems.I4.getItemStack().clone()).setAmount(amount).toItemStack();
                    user.setKasa(user.getKasa() + (amount * 2000));
                    if (rybakObject.getRybakUser().getMission() == 13 || rybakObject.getRybakUser().getMission() == 17) {
                        rybakObject.getRybakUser().setProgress(rybakObject.getRybakUser().getProgress() + amount);
                    }
                    break;
                case 5:
                    if (!player.getInventory().containsAtLeast(RybakItems.I5.getItemStack(), 1)) {
                        return;
                    }
                    if (e.getClick() == ClickType.RIGHT) {
                        for (ItemStack is : player.getInventory().getContents()) {
                            if (is != null && is.getType() != Material.AIR && is == RybakItems.I5.getItemStack()) {
                                amount += is.getAmount();
                            }
                        }
                    } else {
                        amount = 1;
                    }
                    toRemove = new ItemBuilder(RybakItems.I5.getItemStack().clone()).setAmount(amount).toItemStack();
                    user.setKasa(user.getKasa() + (amount * 2000));
                    if (rybakObject.getRybakUser().getMission() == 13 || rybakObject.getRybakUser().getMission() == 18) {
                        rybakObject.getRybakUser().setProgress(rybakObject.getRybakUser().getProgress() + amount);
                    }
                    break;
                case 6:
                    if (!player.getInventory().containsAtLeast(RybakItems.I6.getItemStack(), 1)) {
                        return;
                    }
                    if (e.getClick() == ClickType.RIGHT) {
                        for (ItemStack is : player.getInventory().getContents()) {
                            if (is != null && is.getType() != Material.AIR && is == RybakItems.I6.getItemStack()) {
                                amount += is.getAmount();
                            }
                        }
                    } else {
                        amount = 1;
                    }
                    toRemove = new ItemBuilder(RybakItems.I6.getItemStack().clone()).setAmount(amount).toItemStack();
                    user.setKasa(user.getKasa() + (amount * 2000));
                    if (rybakObject.getRybakUser().getMission() == 13 || rybakObject.getRybakUser().getMission() == 19) {
                        rybakObject.getRybakUser().setProgress(rybakObject.getRybakUser().getProgress() + amount);
                    }
                    break;
                case 7:
                    if (!player.getInventory().containsAtLeast(RybakItems.I7.getItemStack(), 1)) {
                        return;
                    }
                    if (e.getClick() == ClickType.RIGHT) {
                        for (ItemStack is : player.getInventory().getContents()) {
                            if (is != null && is.getType() != Material.AIR && is == RybakItems.I7.getItemStack()) {
                                amount += is.getAmount();
                            }
                        }
                    } else {
                        amount = 1;
                    }
                    toRemove = new ItemBuilder(RybakItems.I7.getItemStack().clone()).setAmount(amount).toItemStack();
                    user.setKasa(user.getKasa() + (amount * 5000));
                    break;
                case 8:
                    if (!player.getInventory().containsAtLeast(RybakItems.I8.getItemStack(), 1)) {
                        return;
                    }
                    if (e.getClick() == ClickType.RIGHT) {
                        for (ItemStack is : player.getInventory().getContents()) {
                            if (is != null && is.getType() != Material.AIR && is == RybakItems.I8.getItemStack()) {
                                amount += is.getAmount();
                            }
                        }
                    } else {
                        amount = 1;
                    }
                    toRemove = new ItemBuilder(RybakItems.I8.getItemStack().clone()).setAmount(amount).toItemStack();
                    user.setKasa(user.getKasa() + (amount * 5000));
                    break;
                case 9:
                    if (!player.getInventory().containsAtLeast(RybakItems.I9.getItemStack(), 1)) {
                        return;
                    }
                    if (e.getClick() == ClickType.RIGHT) {
                        for (ItemStack is : player.getInventory().getContents()) {
                            if (is != null && is.getType() != Material.AIR && is == RybakItems.I9.getItemStack()) {
                                amount += is.getAmount();
                            }
                        }
                    } else {
                        amount = 1;
                    }
                    toRemove = new ItemBuilder(RybakItems.I9.getItemStack().clone()).setAmount(amount).toItemStack();
                    user.setKasa(user.getKasa() + (amount * 12500));
                    if (rybakObject.getRybakUser().getMission() == 13 || rybakObject.getRybakUser().getMission() == 22) {
                        rybakObject.getRybakUser().setProgress(rybakObject.getRybakUser().getProgress() + amount);
                    }
                    break;
                case 10:
                    if (!player.getInventory().containsAtLeast(RybakItems.I10.getItemStack(), 1)) {
                        return;
                    }
                    if (e.getClick() == ClickType.RIGHT) {
                        for (ItemStack is : player.getInventory().getContents()) {
                            if (is != null && is.getType() != Material.AIR && is == RybakItems.I10.getItemStack()) {
                                amount += is.getAmount();
                            }
                        }
                    } else {
                        amount = 1;
                    }
                    toRemove = new ItemBuilder(RybakItems.I10.getItemStack().clone()).setAmount(amount).toItemStack();
                    user.setKasa(user.getKasa() + (amount * 12500));
                    if (rybakObject.getRybakUser().getMission() == 13 || rybakObject.getRybakUser().getMission() == 23) {
                        rybakObject.getRybakUser().setProgress(rybakObject.getRybakUser().getProgress() + amount);
                    }
                    break;
                case 11:
                    player.getInventory().addItem(RybakItems.getWedka(player.getName(), 20));
                default:
                    return;
            }
            player.getInventory().removeItem(toRemove);
            player.sendMessage(Utils.format("&6&lRybak &8>> &aPomyslnie sprzedales &6" + amount + " &a&l" + toRemove.getItemMeta().getDisplayName() + " &aza &6" + (amount * 1000) + " &amonet!"));

            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
                rpgcore.getMongoManager().saveDataUser(uuid, user);
                rpgcore.getMongoManager().saveDataRybak(uuid, rybakObject);
            });

        }

        if (title.equals("Rybak » Anty-AFK")) {
            e.setCancelled(true);
            if (item != null && item.getItemMeta().hasItemFlag(ItemFlag.HIDE_ENCHANTS)) {
                clickedInventory.setItem(slot, new ItemStack(Material.AIR));
                rpgcore.getRybakNPC().resetFishingCount(uuid);
                rpgcore.getRybakNPC().resetFailedAttempt(uuid);
                player.closeInventory();
                player.sendMessage(Utils.format("&6&lRybak &8>> &aPomyslnie przeszedles/-as weryfikacje &cAnty-AFK&a!"));
                return;
            }
            player.closeInventory();
            rpgcore.getRybakNPC().addFailedAttempt(uuid);
            player.sendMessage(Utils.format("&6&lRybak &8>> &cNie udalo sie przeslac weryfikacji Anty-AFK &4(" + rpgcore.getRybakNPC().getFailedAttempts(uuid) + "/3)"));
            if (rpgcore.getRybakNPC().getFailedAttempts(uuid) >= 3) {
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "tempban " + player.getName() + "6 h Afk na lowienie (skrypt?)");
            }
        }


    }

}
