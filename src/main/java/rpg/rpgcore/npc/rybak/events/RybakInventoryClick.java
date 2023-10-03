package rpg.rpgcore.npc.rybak.events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.artefakty.Artefakty;
import rpg.rpgcore.bonuses.Bonuses;
import rpg.rpgcore.npc.rybak.enums.MlodszyRybakMissions;
import rpg.rpgcore.npc.rybak.enums.StaruszekMissions;
import rpg.rpgcore.npc.rybak.helpers.RybakHelper;
import rpg.rpgcore.npc.rybak.objects.MlodszyRybakUser;
import rpg.rpgcore.npc.rybak.objects.StaruszekUser;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.npc.RybakItems;

import java.util.Objects;
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

        //                                          STARUSZEK (WYSPA 1)                            //

        if (title.equals("Staruszek")) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);

            if (item == null || item.getType() == Material.BARRIER || item.getType() == Material.STAINED_GLASS_PANE) return;

            final StaruszekUser user = rpgcore.getRybakNPC().find(player.getUniqueId()).getStaruszekUser();

            if (e.getSlot() == 26) {
                rpgcore.getRybakNPC().openStaruszekShop(player);
                return;
            }

            if (user.getMission() == 27) return;

            final StaruszekMissions mission = StaruszekMissions.getMissionById(user.getMission());

            assert mission != null;

            if (user.getProgress() >= mission.getReqAmount()) {
                user.setMission(user.getMission() + 1);
                user.setProgress(0);

                user.setSrDef(user.getSrDef() + mission.getSrDef());
                final Bonuses bonuses = rpgcore.getBonusesManager().find(player.getUniqueId());
                bonuses.getBonusesUser().setSredniadefensywa(bonuses.getBonusesUser().getSredniadefensywa() + mission.getSrDef());

                if (user.getMission() == 27) {
                    user.setDone(true);
                    rpgcore.getServer().broadcastMessage(Utils.format("&6&lStaruszek &8>> &7Gracz &e" + player.getName() + " &7wykonal moje &4&lWSZYSTKIE &7misje!"));
                    if (rpgcore.getArtefaktyZaLvlManager().getArtefaktyZaLvl().getRybak().getNadanych() < 4 && !rpgcore.getArtefaktyZaLvlManager().getArtefaktyZaLvl().getRybak().getGracze().contains(player.getName())) {
                        rpgcore.getArtefaktyZaLvlManager().getArtefaktyZaLvl().getRybak().getGracze().add(player.getName());
                        rpgcore.getArtefaktyZaLvlManager().getArtefaktyZaLvl().getRybak().setNadanych(rpgcore.getArtefaktyZaLvlManager().getArtefaktyZaLvl().getRybak().getNadanych() + 1);
                        rpgcore.getArtefaktyZaLvlManager().save();
                        player.getInventory().addItem(Artefakty.getArtefakt("Eliksir-Potegi", player));
                        Bukkit.getServer().broadcastMessage(Utils.format("&6&lStaruszek &8>> &7Poniewaz ukonczyl je, jako &c" + rpgcore.getArtefaktyZaLvlManager().getArtefaktyZaLvl().getRybak().getNadanych() + " &7na serwerze, otrzymal &6&lEliksir Potegi&7!"));
                    }
                } else {
                    rpgcore.getServer().broadcastMessage(Utils.format("&6&lStaruszek &8>> &7Gracz &e" + player.getName() + " &7wykonal moja &c" + (user.getMission() - 1) + " &7misje!"));
                }
                player.sendMessage(Utils.format("&6&lStaruszek &8>> &aTwoja nagroda to:"));
                player.sendMessage(Utils.format("&b+" + mission.getSrDef() + "% sredniej defensywy"));
                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
                    rpgcore.getMongoManager().saveDataRybak(user.getUuid(), rpgcore.getRybakNPC().find(user.getUuid()));
                    rpgcore.getMongoManager().saveDataBonuses(bonuses.getId(), bonuses);
                });
                rpgcore.getRybakNPC().onClickStaruszek(player);
                return;
            }

            if (item.getType().equals(Material.BOOK) && !item.getItemMeta().hasItemFlag(ItemFlag.HIDE_ENCHANTS)) {
                if (!(user.getMission() == 20 || user.getMission() == 21 || user.getMission() == 22 || user.getMission() == 23 || user.getMission() == 24)) return;

                final String itemName = Utils.getTagString(item, "itemName");
                if (itemName.isEmpty()) return;

                final ItemStack reqItem = Objects.requireNonNull(RybakItems.getByName(itemName)).getItemStack();

                if (!player.getInventory().containsAtLeast(reqItem, 1)) {
                    player.sendMessage(Utils.format("&6&lStaruszek &8>> &cNie posiadasz tego przedmiotu!"));
                    player.closeInventory();
                    return;
                }

                int amount = 0;
                for (ItemStack items : player.getInventory().getContents()) {
                    if (items == null || items.getType() == Material.AIR || !items.hasItemMeta() || !items.getItemMeta().hasDisplayName()) continue;
                    if (items.isSimilar(reqItem)) amount += items.getAmount();
                }

                if (user.getProgress() + amount > Objects.requireNonNull(StaruszekMissions.getMissionById(user.getMission())).getReqAmount()) amount -= user.getProgress() + amount - Objects.requireNonNull(StaruszekMissions.getMissionById(user.getMission())).getReqAmount();

                player.getInventory().removeItem(new ItemBuilder(reqItem.clone()).setAmount(amount).toItemStack());
                user.setProgress(user.getProgress() + amount);

                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataRybak(user.getUuid(), rpgcore.getRybakNPC().find(user.getUuid())));
                player.sendMessage(Utils.format("&6&lStaruszek &8>> &aPomyslnie oddales &6" + amount + " &aprzedmiotow do misji!"));
                rpgcore.getRybakNPC().onClickStaruszek(player);
                return;
            }

            return;
        }


        if (title.equals("Staruszek » Sklep")) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);

            if (item == null || item.getType() == Material.STAINED_GLASS_PANE) return;


            final ItemStack reqItem = Objects.requireNonNull(RybakItems.getByName(Utils.getTagString(e.getCurrentItem(), "itemName"))).getItemStack();

            if (!player.getInventory().containsAtLeast(reqItem, 1)) {
                player.sendMessage(Utils.format("&6&lStaruszek &8>> &cNie posiadasz tego przedmiotu!"));
                player.closeInventory();
                return;
            }

            final int price = Utils.getTagInt(e.getCurrentItem(), "price");

            player.getInventory().removeItem(new ItemBuilder(reqItem.clone()).setAmount(1).toItemStack());
            final User user = rpgcore.getUserManager().find(player.getUniqueId());
            user.setKasa(user.getKasa() + price);
            player.sendMessage(Utils.format("&6&lStaruszek &8>> &aPomyslnie sprzedales " + item.getItemMeta().getDisplayName() + " &aza &6" + Utils.spaceNumber(price) + "&2$&a!"));
            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataUser(user.getId(), user));
            return;
        }

        if (title.equals("Przyjaciel")) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);

            if (item == null || item.getType() == Material.STAINED_GLASS_PANE) return;

            switch (e.getSlot()) {
                case 1:
                    if (!player.getInventory().containsAtLeast(RybakItems.I6.getItemStack(), 64)) return;
                    player.getInventory().removeItem(new ItemBuilder(RybakItems.I6.getItemStack().clone()).setAmount(64).toItemStack());
                    player.getInventory().addItem(RybakItems.I6_1.getItemStack().clone());
                    return;
                case 10:
                    if (!player.getInventory().containsAtLeast(RybakItems.I6_1.getItemStack(), 1)) return;
                    player.getInventory().removeItem(new ItemBuilder(RybakItems.I6_1.getItemStack().clone()).setAmount(1).toItemStack());
                    player.getInventory().addItem(new ItemBuilder(RybakItems.I6.getItemStack().clone()).setAmount(64).toItemStack());
                    return;
                case 2:
                    if (!player.getInventory().containsAtLeast(RybakItems.I7.getItemStack(), 64)) return;
                    player.getInventory().removeItem(new ItemBuilder(RybakItems.I7.getItemStack().clone()).setAmount(64).toItemStack());
                    player.getInventory().addItem(RybakItems.I7_1.getItemStack().clone());
                    return;
                case 11:
                    if (!player.getInventory().containsAtLeast(RybakItems.I7_1.getItemStack(), 1)) return;
                    player.getInventory().removeItem(new ItemBuilder(RybakItems.I7_1.getItemStack().clone()).setAmount(1).toItemStack());
                    player.getInventory().addItem(new ItemBuilder(RybakItems.I7.getItemStack().clone()).setAmount(64).toItemStack());
                    return;
                case 3:
                    if (!player.getInventory().containsAtLeast(RybakItems.I8.getItemStack(), 64)) return;
                    player.getInventory().removeItem(new ItemBuilder(RybakItems.I8.getItemStack().clone()).setAmount(64).toItemStack());
                    player.getInventory().addItem(RybakItems.I8_1.getItemStack().clone());
                    return;
                case 12:
                    if (!player.getInventory().containsAtLeast(RybakItems.I8_1.getItemStack(), 1)) return;
                    player.getInventory().removeItem(new ItemBuilder(RybakItems.I8_1.getItemStack().clone()).setAmount(1).toItemStack());
                    player.getInventory().addItem(new ItemBuilder(RybakItems.I8.getItemStack().clone()).setAmount(64).toItemStack());
                    return;
                case 5:
                    if (!player.getInventory().containsAtLeast(RybakItems.I9.getItemStack(), 64)) return;
                    player.getInventory().removeItem(new ItemBuilder(RybakItems.I9.getItemStack().clone()).setAmount(64).toItemStack());
                    player.getInventory().addItem(RybakItems.I9_1.getItemStack().clone());
                    return;
                case 14:
                    if (!player.getInventory().containsAtLeast(RybakItems.I9_1.getItemStack(), 1)) return;
                    player.getInventory().removeItem(new ItemBuilder(RybakItems.I9_1.getItemStack().clone()).setAmount(1).toItemStack());
                    player.getInventory().addItem(new ItemBuilder(RybakItems.I9.getItemStack().clone()).setAmount(64).toItemStack());
                    return;
                case 6:
                    if (!player.getInventory().containsAtLeast(RybakItems.I10.getItemStack(), 64)) return;
                    player.getInventory().removeItem(new ItemBuilder(RybakItems.I10.getItemStack().clone()).setAmount(64).toItemStack());
                    player.getInventory().addItem(RybakItems.I10_1.getItemStack().clone());
                    return;
                case 15:
                    if (!player.getInventory().containsAtLeast(RybakItems.I10_1.getItemStack(), 1)) return;
                    player.getInventory().removeItem(new ItemBuilder(RybakItems.I10_1.getItemStack().clone()).setAmount(1).toItemStack());
                    player.getInventory().addItem(new ItemBuilder(RybakItems.I10.getItemStack().clone()).setAmount(64).toItemStack());
                    return;
                case 7:
                    if (!player.getInventory().containsAtLeast(RybakItems.I14.getItemStack(), 64)) return;
                    player.getInventory().removeItem(new ItemBuilder(RybakItems.I14.getItemStack().clone()).setAmount(64).toItemStack());
                    player.getInventory().addItem(RybakItems.I14_1.getItemStack().clone());
                    return;
                case 16:
                    if (!player.getInventory().containsAtLeast(RybakItems.I14_1.getItemStack(), 1)) return;
                    player.getInventory().removeItem(new ItemBuilder(RybakItems.I14_1.getItemStack().clone()).setAmount(1).toItemStack());
                    player.getInventory().addItem(new ItemBuilder(RybakItems.I14.getItemStack().clone()).setAmount(64).toItemStack());
                    return;
            }
            return;
        }



        //                                          MLODSZY RYBAK (WYSPA 2)                            //

        if (title.equals("Mlodszy Rybak")) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);

            if (item == null || item.getType() == Material.BARRIER || item.getType() == Material.STAINED_GLASS_PANE) return;

            final MlodszyRybakUser user = rpgcore.getRybakNPC().find(player.getUniqueId()).getMlodszyRybakUser();

            if (e.getSlot() == 26) {
                rpgcore.getRybakNPC().openMlodszyRybakShop(player);
                return;
            }

            if (user.getMission() == 27) return;

            final MlodszyRybakMissions mission = MlodszyRybakMissions.getMissionById(user.getMission());

            assert mission != null;

            if (user.getProgress() >= mission.getReqAmount()) {
                user.setMission(user.getMission() + 1);
                user.setProgress(0);

                user.setSrDef(user.getSrDef() + mission.getSrDef());
                user.setDodatkowyDmg(user.getDodatkowyDmg() + mission.getDodatkoweDmg());
                user.setPrzeszywka(user.getPrzeszywka() + mission.getPrzeszywka());
                final Bonuses bonuses = rpgcore.getBonusesManager().find(player.getUniqueId());
                bonuses.getBonusesUser().setSredniadefensywa(bonuses.getBonusesUser().getSredniadefensywa() + mission.getSrDef());
                bonuses.getBonusesUser().setDodatkoweobrazenia(bonuses.getBonusesUser().getDodatkoweobrazenia() + mission.getDodatkoweDmg());
                bonuses.getBonusesUser().setPrzeszyciebloku(bonuses.getBonusesUser().getPrzeszyciebloku() + mission.getPrzeszywka());

                if (user.getMission() == 27) {
                    user.setDone(true);
                    rpgcore.getServer().broadcastMessage(Utils.format("&3&lMlodszy Rybak &8>> &bGracz &3" + player.getName() + " &bwykonal moje &4&lWSZYSTKIE &bmisje!"));
                } else {
                    rpgcore.getServer().broadcastMessage(Utils.format("&3&lMlodszy Rybak &8>> &bGracz &3" + player.getName() + " &bwykonal moja &c" + (user.getMission() - 1) + " &bmisje!"));
                }
                player.sendMessage(Utils.format("&3&lMlodszy Rybak &8>> &bTwoja nagroda to:"));
                player.sendMessage(Utils.format("&a+" + mission.getSrDef() + "% sredniej defensywy"));
                player.sendMessage(Utils.format("&c+" + mission.getDodatkoweDmg() + " dodatkowych obrazen"));
                player.sendMessage(Utils.format("&6+" + mission.getPrzeszywka() + "% przeszycia bloku ciosu"));
                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
                    rpgcore.getMongoManager().saveDataRybak(user.getUuid(), rpgcore.getRybakNPC().find(user.getUuid()));
                    rpgcore.getMongoManager().saveDataBonuses(bonuses.getId(), bonuses);
                });
                rpgcore.getRybakNPC().onClickMlodszyRybak(player);
                return;
            }

            if (item.getType().equals(Material.BOOK) && !item.getItemMeta().hasItemFlag(ItemFlag.HIDE_ENCHANTS)) {
                if (!(user.getMission() == 8 || user.getMission() == 9 || user.getMission() == 10 || user.getMission() == 11 || user.getMission() == 12
                        || user.getMission() == 13 || user.getMission() == 14)) return;

                final String itemName = Utils.getTagString(item, "itemName");
                if (itemName.isEmpty()) return;

                final ItemStack reqItem = Objects.requireNonNull(RybakItems.getByName(itemName)).getItemStack();

                if (!player.getInventory().containsAtLeast(reqItem, 1)) {
                    player.sendMessage(Utils.format("&3&lMlodszy Rybak &8>> &cNie posiadasz tego przedmiotu!"));
                    player.closeInventory();
                    return;
                }

                int amount = 0;
                for (ItemStack items : player.getInventory().getContents()) {
                    if (items == null || items.getType() == Material.AIR || !items.hasItemMeta() || !items.getItemMeta().hasDisplayName()) continue;
                    if (items.isSimilar(reqItem)) amount += items.getAmount();
                }

                if (user.getProgress() + amount > Objects.requireNonNull(MlodszyRybakMissions.getMissionById(user.getMission())).getReqAmount())
                    amount -= user.getProgress() + amount - Objects.requireNonNull(MlodszyRybakMissions.getMissionById(user.getMission())).getReqAmount();

                player.getInventory().removeItem(new ItemBuilder(reqItem.clone()).setAmount(amount).toItemStack());
                user.setProgress(user.getProgress() + amount);

                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataRybak(user.getUuid(), rpgcore.getRybakNPC().find(user.getUuid())));
                player.sendMessage(Utils.format("&3&lMlodszy Rybak &8>> &aPomyslnie oddales &6" + amount + " &aprzedmiotow do misji!"));
                rpgcore.getRybakNPC().onClickMlodszyRybak(player);
                return;
            }

            return;
        }


        if (title.equals("Mlodszy Rybak » Sklep")) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);

            if (item == null || item.getType() == Material.STAINED_GLASS_PANE) return;


            final ItemStack reqItem = Objects.requireNonNull(RybakItems.getByName(Utils.getTagString(e.getCurrentItem(), "itemName"))).getItemStack();

            if (!player.getInventory().containsAtLeast(reqItem, 1)) {
                player.sendMessage(Utils.format("&3&lMlodszy Rybak &8>> &cNie posiadasz tego przedmiotu!"));
                player.closeInventory();
                return;
            }

            final int price = Utils.getTagInt(e.getCurrentItem(), "price");

            player.getInventory().removeItem(new ItemBuilder(reqItem.clone()).setAmount(1).toItemStack());
            final User user = rpgcore.getUserManager().find(player.getUniqueId());
            user.setKasa(user.getKasa() + price);
            player.sendMessage(Utils.format("&3&lMlodszy Rybak &8>> &aPomyslnie sprzedales " + item.getItemMeta().getDisplayName() + " &aza &6" + Utils.spaceNumber(price) + "&2$&a!"));
            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataUser(user.getId(), user));
            return;
        }

        if (title.equals("Stol Rybacki")) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);

            if (item == null || item.getType() == Material.STAINED_GLASS_PANE || item.getType() == Material.IRON_FENCE) return;

            if (e.getSlot() != 2) return;

            final ItemStack wedka = player.getItemInHand();

            final String krysztal = Utils.getTagString(wedka, "krysztal");

            if (krysztal.equals("BRAK")) return;

            RybakHelper.removeKrysztal(wedka, player);
            player.closeInventory();
            return;
        }

        if (Utils.removeColor(player.getOpenInventory().getTopInventory().getTitle()).equals("Stol Rybacki") && e.getClickedInventory().getType() == InventoryType.PLAYER) {
            if (item == null || item.getType() != Material.INK_SACK || Utils.getTagDouble(item, "krysztalValue") == 0) return;
            final ItemStack wedka = player.getItemInHand();

            final String krysztal = Utils.getTagString(wedka, "krysztal");

            if (!krysztal.equals("BRAK")) return;
            e.setResult(Event.Result.DENY);
            e.setCancelled(true);

            RybakHelper.addKrysztal(wedka, item);
            player.getInventory().setItem(e.getSlot(), null);
            player.closeInventory();
            return;
        }


        //                                          ANTY AFK                            //

        if (title.equals("Rybak » Anty-AFK")) {
            e.setCancelled(true);
            if (item != null && item.getItemMeta().hasItemFlag(ItemFlag.HIDE_ENCHANTS) && item.getItemMeta().getDisplayName().equals(Utils.format("&a&lAnty-AFK"))) {
                rpgcore.getRybakNPC().getPassed().add(uuid);
                rpgcore.getRybakNPC().resetFishingCount(uuid);
                rpgcore.getRybakNPC().resetFailedAttempt(uuid);
                Bukkit.getScheduler().cancelTask(rpgcore.getRybakNPC().getTaskMap().get(uuid));
                player.closeInventory();
                player.sendMessage(Utils.format("&7Ochrona &cAnty-AFK"));
                player.sendMessage(Utils.format("&aPomyslnie przeszedles/-as weryfikacje &cAnty-AFK&a!"));
                player.sendMessage(Utils.format("&7Ochrona &cAnty-AFK"));
                return;
            }
            Bukkit.getScheduler().cancelTask(rpgcore.getRybakNPC().getTaskMap().get(uuid));
            player.closeInventory();
        }


    }

}
