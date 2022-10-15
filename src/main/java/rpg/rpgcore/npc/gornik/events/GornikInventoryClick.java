package rpg.rpgcore.npc.gornik.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.bonuses.Bonuses;
import rpg.rpgcore.npc.gornik.GornikObject;
import rpg.rpgcore.npc.gornik.GornikUser;
import rpg.rpgcore.npc.gornik.enums.GornikExchange;
import rpg.rpgcore.npc.gornik.enums.GornikMissions;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.ChanceHelper;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.npc.GornikItems;

import java.util.UUID;

public class GornikInventoryClick implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClick(final InventoryClickEvent e) {

        if (e.getInventory() == null || e.getClickedInventory() == null) {
            return;
        }

        final Inventory clickedInventory = e.getInventory();
        final Player player = (Player) e.getWhoClicked();
        final UUID uuid = player.getUniqueId();
        final String title = Utils.removeColor(clickedInventory.getTitle());
        final ItemStack item = e.getCurrentItem();
        final int slot = e.getSlot();
        final GornikObject object = RPGCORE.getInstance().getGornikNPC().find(uuid);
        final GornikUser gornikUser = object.getGornikUser();


        if (e.getClickedInventory().getType() == InventoryType.PLAYER) {
            if (e.getAction() == InventoryAction.SWAP_WITH_CURSOR) {
                //TODO Dodac wiaderka z woda jako paliwo do mycia rud
                if (String.valueOf(e.getCursor().getType()).contains("_SPADE") && e.getCursor().getItemMeta().hasDisplayName()
                        && e.getCursor().getItemMeta().getDisplayName().contains(" Dluto") && player.getInventory().containsAtLeast(GornikItems.getItem("WODA", 1), 1)) {
                    if (item != null && (item.getType() == Material.COAL_ORE || item.getType() == Material.IRON_ORE || item.getType() == Material.GOLD_ORE || item.getType() == Material.DIAMOND_ORE
                    || item.getType() == Material.EMERALD_ORE || item.getType() == Material.REDSTONE_ORE || item.getType() == Material.LAPIS_ORE) && item.getItemMeta().hasDisplayName()
                            && item.getItemMeta().getDisplayName().contains("Ruda")) {
                        e.setCancelled(true);
                        final ItemStack toRemove = new ItemBuilder(item.clone()).setAmount(1).toItemStack();
                        int woda = 0;
                        for (ItemStack item2 : player.getInventory().getContents()) {
                            if (item2 != null && item2.getType() != Material.AIR && item2.isSimilar(GornikItems.getItem("WODA", 1))) {
                                woda += item2.getAmount();
                            }
                        }
                        int done = 0;
                        for (int i = 0; i < woda + 1; i++) {
                            player.getInventory().removeItem(toRemove, GornikItems.getItem("WODA", 1));
                            if (ChanceHelper.getChance(GornikExchange.getExchange(item.getType()).getDropChance())) {
                                player.getInventory().addItem(GornikExchange.getExchange(item.getType()).getItem());
                                done++;
                            } else {
                                player.sendMessage(Utils.format("&cNiestety nie udalo sie wydobyc krysztalu z tej rudy."));
                            }
                        }
                        e.setCursor(RPGCORE.getInstance().getGornikNPC().updateDlutoExp(e.getCursor(), done));
                        if (gornikUser.getMission() == 4) {
                            gornikUser.setProgress(gornikUser.getProgress() + done);
                        }
                        switch (item.getType()) {
                            case COAL_ORE:
                                if (gornikUser.getMission() == 20) {
                                    gornikUser.setProgress(gornikUser.getProgress() + done);
                                }
                                break;
                            case IRON_ORE:
                                if (gornikUser.getMission() == 9) {
                                    gornikUser.setProgress(gornikUser.getProgress() + done);
                                }
                                break;
                            case GOLD_ORE:
                                if (gornikUser.getMission() == 12) {
                                    gornikUser.setProgress(gornikUser.getProgress() + done);
                                }
                                break;
                            case LAPIS_ORE:
                                if (gornikUser.getMission() == 16) {
                                    gornikUser.setProgress(gornikUser.getProgress() + done);
                                }
                                break;
                            case EMERALD_ORE:
                                if (gornikUser.getMission() == 14) {
                                    gornikUser.setProgress(gornikUser.getProgress() + done);
                                }
                                break;
                            case DIAMOND_ORE:
                                if (gornikUser.getMission() == 23) {
                                    gornikUser.setProgress(gornikUser.getProgress() + done);
                                }
                                break;
                            case REDSTONE_ORE:
                                if (gornikUser.getMission() == 26) {
                                    gornikUser.setProgress(gornikUser.getProgress() + done);
                                }
                                break;
                        }
                    }
                }
            }
        }


        if (title.equals("Gornik")) {
            e.setCancelled(true);
            if (slot == 4) {
                RPGCORE.getInstance().getGornikNPC().openSklep(player);
                return;
            }
            if (slot == 11) {
                RPGCORE.getInstance().getGornikNPC().openKampania(player);
                return;
            }
            if (slot == 15) {
                RPGCORE.getInstance().getGornikNPC().openCrafting(player);
                return;
            }
            if (slot == 22) {
                RPGCORE.getInstance().getGornikNPC().openDrzewko(player);
                return;
            }
        }

        if (title.equals("Sklep Gorniczy")) {
            if (item == null || item.getType() == Material.AIR) {
                return;
            }
            final User user = RPGCORE.getInstance().getUserManager().find(uuid);
            if (slot == 0) {
                if (user.getKasa() < 100000000) {
                    player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie masz wystarczajaco pieniedzy zeby kupic moj kilof!"));
                    return;
                }
                user.setKasa(user.getKasa() - 100000000);
                player.getInventory().addItem(GornikItems.getKilof());
                player.sendMessage(Utils.format("&6&lGornik &8>> &7Trzymaj... Mam nadzieje, ze bedzie ci dobrze sluzyl."));
                player.sendMessage(Utils.format("&8Pssst... Kilof ten posiada niesamowite wlasciowsci przemiany."));
                player.closeInventory();
            }
        }

        if (title.equals("Kampania Gornika")) {
            e.setCancelled(true);
            if (item.getType() == Material.BARRIER || item.getType() == Material.STAINED_GLASS_PANE && Utils.checkIfLoreContainsString(item.getItemMeta().getLore(), "Ukonczone!")) {
                return;
            }

            final GornikMissions mission = GornikMissions.getMission(gornikUser.getMission());

            if (gornikUser.getProgress() < mission.getReqAmount()) {
                player.sendMessage(Utils.format("&6&lGornik &8>> &7Czy ty wlasnie probujesz mnie oszukac? &cNie wykonales jeszcze swojego zadania!"));
                player.closeInventory();
                return;
            }
            if (mission.getReward() != null) {
                player.getInventory().addItem(mission.getReward());
            }
            gornikUser.setProgress(0);
            final Bonuses bonuses = RPGCORE.getInstance().getBonusesManager().find(uuid);
            bonuses.getBonusesUser().setSredniadefensywa(bonuses.getBonusesUser().getSredniadefensywa() + mission.getDef());
            if (mission.getBlok() != 0 && mission.getPrzeszycie() != 0) {
                bonuses.getBonusesUser().setPrzeszyciebloku(bonuses.getBonusesUser().getPrzeszyciebloku() + mission.getPrzeszycie());
                bonuses.getBonusesUser().setBlokciosu(bonuses.getBonusesUser().getBlokciosu() + mission.getBlok());
            }
            gornikUser.setMission(gornikUser.getMission() + 1);
            RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> {
                RPGCORE.getInstance().getMongoManager().saveDataGornik(uuid, object);
                RPGCORE.getInstance().getMongoManager().saveDataBonuses(uuid, bonuses);
            });
            RPGCORE.getInstance().getGornikNPC().openKampania(player);
        }

        if (title.equals("Drzewko Gornika")) {
            e.setCancelled(true);
            final User playerUser = RPGCORE.getInstance().getUserManager().find(uuid);
            final Bonuses bonuses = RPGCORE.getInstance().getBonusesManager().find(uuid);

            if (item.getDurability() == 7 || item.getDurability() == 5) {
                player.getInventory().addItem(GornikItems.getItem("I1", 1));
                return;
            }
            if (playerUser.getKasa() < 50000000) {
                player.sendMessage(Utils.format("&6&lGornik &8>> &7Chyba czegos Ci brakuje moj przyjacielu &8(&2$&8)"));
                return;
            }
            if (!player.getInventory().containsAtLeast(GornikItems.getItem("I1", 1), 1)) {
                player.sendMessage(Utils.format("&6&lGornik &8>> &7Chyba czegos Ci brakuje moj przyjacielu &8(&6Krysztal&8)"));
                return;
            }
            switch (slot) {
                // SRODEK
                case 3:
                    if (!(gornikUser.isD1() && gornikUser.isD2() && gornikUser.isD3_3() && gornikUser.isD4_7() && gornikUser.isD5_5() && gornikUser.isD6_5())) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie odblokowales jeszcze pooprzednich atrybutow"));
                        return;
                    }
                    playerUser.setKasa(playerUser.getKasa() - 50000000);
                    player.getInventory().removeItem(GornikItems.getItem("I1", 1));
                    gornikUser.setD7_3(true);
                    bonuses.getBonusesUser().setSzczescie(bonuses.getBonusesUser().getSzczescie() + 15);
                    break;
                case 5:
                    if (!(gornikUser.isD1() && gornikUser.isD2() && gornikUser.isD3_3() && gornikUser.isD4_7() && gornikUser.isD5_5() && gornikUser.isD6_5())) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie odblokowales jeszcze pooprzednich atrybutow"));
                        return;
                    }
                    playerUser.setKasa(playerUser.getKasa() - 50000000);
                    player.getInventory().removeItem(GornikItems.getItem("I1", 1));
                    gornikUser.setD7_4(true);
                    bonuses.getBonusesUser().setPrzebiciePancerza(bonuses.getBonusesUser().getPrzebiciePancerza() + 2);
                    break;
                case 4:
                    if (!(gornikUser.isD1() && gornikUser.isD2() && gornikUser.isD3_3() && gornikUser.isD4_7() && gornikUser.isD5_5())) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie odblokowales jeszcze pooprzednich atrybutow"));
                        return;
                    }
                    if (playerUser.getKasa() < 150000000) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Chyba czegos Ci brakuje moj przyjacielu &8(&2$&8)"));
                        return;
                    }
                    if (!player.getInventory().containsAtLeast(GornikItems.getItem("I1", 1), 3)) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Chyba czegos Ci brakuje moj przyjacielu &8(&6Krysztal&8)"));
                        return;
                    }
                    playerUser.setKasa(playerUser.getKasa() - 150000000);
                    player.getInventory().removeItem(GornikItems.getItem("I1", 3));
                    gornikUser.setD6_5(true);
                    break;
                case 13:
                    if (!(gornikUser.isD1() && gornikUser.isD2() && gornikUser.isD3_3() && gornikUser.isD4_7())) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie odblokowales jeszcze pooprzednich atrybutow"));
                        return;
                    }
                    playerUser.setKasa(playerUser.getKasa() - 50000000);
                    player.getInventory().removeItem(GornikItems.getItem("I1", 1));
                    gornikUser.setD5_5(true);
                    bonuses.getBonusesUser().setSilnynapotwory(bonuses.getBonusesUser().getSilnynapotwory() + 5);
                    bonuses.getBonusesUser().setSzczescie(bonuses.getBonusesUser().getSzczescie() + 10);
                    break;
                case 22:
                    if (!(gornikUser.isD1() && gornikUser.isD2() && gornikUser.isD3_3())) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie odblokowales jeszcze pooprzednich atrybutow"));
                        return;
                    }
                    if (playerUser.getKasa() < 150000000) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Chyba czegos Ci brakuje moj przyjacielu &8(&2$&8)"));
                        return;
                    }
                    if (!player.getInventory().containsAtLeast(GornikItems.getItem("I1", 1), 3)) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Chyba czegos Ci brakuje moj przyjacielu &8(&6Krysztal&8)"));
                        return;
                    }
                    playerUser.setKasa(playerUser.getKasa() - 150000000);
                    player.getInventory().removeItem(GornikItems.getItem("I1", 3));
                    gornikUser.setD4_7(true);
                    bonuses.getBonusesUser().setSilnynapotwory(bonuses.getBonusesUser().getSilnynapotwory() + 5);
                    bonuses.getBonusesUser().setDefnamoby(bonuses.getBonusesUser().getDefnamoby() + 5);
                    break;
                case 31:
                    if (!(gornikUser.isD1() && gornikUser.isD2())) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie odblokowales jeszcze pooprzednich atrybutow"));
                        return;
                    }
                    playerUser.setKasa(playerUser.getKasa() - 50000000);
                    player.getInventory().removeItem(GornikItems.getItem("I1", 1));
                    gornikUser.setD3_3(true);
                    bonuses.getBonusesUser().setPrzebiciePancerza(bonuses.getBonusesUser().getPrzebiciePancerza() + 1);
                    break;
                case 40:
                    if (!(gornikUser.isD1())) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie odblokowales jeszcze pooprzednich atrybutow"));
                        return;
                    }
                    if (playerUser.getKasa() < 150000000) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Chyba czegos Ci brakuje moj przyjacielu &8(&2$&8)"));
                        return;
                    }
                    if (!player.getInventory().containsAtLeast(GornikItems.getItem("I1", 1), 3)) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Chyba czegos Ci brakuje moj przyjacielu &8(&6Krysztal&8)"));
                        return;
                    }
                    playerUser.setKasa(playerUser.getKasa() - 150000000);
                    player.getInventory().removeItem(GornikItems.getItem("I1", 3));
                    gornikUser.setD2(true);
                    break;
                case 49:
                    playerUser.setKasa(playerUser.getKasa() - 50000000);
                    player.getInventory().removeItem(GornikItems.getItem("I1", 1));
                    gornikUser.setD1(true);
                    bonuses.getBonusesUser().setSrednieobrazenia(bonuses.getBonusesUser().getSrednieobrazenia() + 1);
                    break;
                // PRAWA STRONA
                case 0:
                    if (!(gornikUser.isD1() && gornikUser.isD2() && gornikUser.isD3_1() && gornikUser.isD4_4() && gornikUser.isD4_6() && gornikUser.isD5_4() && gornikUser.isD6_4() && gornikUser.isD7_2() && gornikUser.isD8_2())) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie odblokowales jeszcze pooprzednich atrybutow"));
                        return;
                    }
                    if (playerUser.getKasa() < 150000000) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Chyba czegos Ci brakuje moj przyjacielu &8(&2$&8)"));
                        return;
                    }
                    if (!player.getInventory().containsAtLeast(GornikItems.getItem("I1", 1), 3)) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Chyba czegos Ci brakuje moj przyjacielu &8(&6Krysztal&8)"));
                        return;
                    }
                    playerUser.setKasa(playerUser.getKasa() - 150000000);
                    player.getInventory().removeItem(GornikItems.getItem("I1", 3));
                    gornikUser.setD9_2(true);
                    bonuses.getBonusesUser().setPrzeszyciebloku(bonuses.getBonusesUser().getPrzeszyciebloku() + 5);
                    break;
                case 9:
                    if (!(gornikUser.isD1() && gornikUser.isD2() && gornikUser.isD3_1() && gornikUser.isD4_4() && gornikUser.isD4_6() && gornikUser.isD5_4() && gornikUser.isD6_4() && gornikUser.isD7_2())) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie odblokowales jeszcze pooprzednich atrybutow"));
                        return;
                    }
                    playerUser.setKasa(playerUser.getKasa() - 50000000);
                    player.getInventory().removeItem(GornikItems.getItem("I1", 1));
                    gornikUser.setD8_2(true);
                    bonuses.getBonusesUser().setSilnynaludzi(bonuses.getBonusesUser().getSilnynaludzi() + 3);
                    break;
                case 10:
                    if (!(gornikUser.isD1() && gornikUser.isD2() && gornikUser.isD3_1() && gornikUser.isD4_4() && gornikUser.isD4_6() && gornikUser.isD5_4() && gornikUser.isD6_4())) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie odblokowales jeszcze pooprzednich atrybutow"));
                        return;
                    }
                    playerUser.setKasa(playerUser.getKasa() - 50000000);
                    player.getInventory().removeItem(GornikItems.getItem("I1", 1));
                    gornikUser.setD7_2(true);
                    bonuses.getBonusesUser().setDefnaludzi(bonuses.getBonusesUser().getDefnaludzi() + 2);
                    break;
                case 11:
                    if (!(gornikUser.isD1() && gornikUser.isD2() && gornikUser.isD3_1() && gornikUser.isD4_4() && gornikUser.isD4_6() && gornikUser.isD5_4())) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie odblokowales jeszcze pooprzednich atrybutow"));
                        return;
                    }
                    if (playerUser.getKasa() < 150000000) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Chyba czegos Ci brakuje moj przyjacielu &8(&2$&8)"));
                        return;
                    }
                    if (!player.getInventory().containsAtLeast(GornikItems.getItem("I1", 1), 3)) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Chyba czegos Ci brakuje moj przyjacielu &8(&6Krysztal&8)"));
                        return;
                    }
                    playerUser.setKasa(playerUser.getKasa() - 150000000);
                    player.getInventory().removeItem(GornikItems.getItem("I1", 3));
                    gornikUser.setD6_4(true);
                    break;
                case 20:
                    if (!(gornikUser.isD1() && gornikUser.isD2() && gornikUser.isD3_1() && gornikUser.isD4_4() && gornikUser.isD4_6())) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie odblokowales jeszcze pooprzednich atrybutow"));
                        return;
                    }
                    playerUser.setKasa(playerUser.getKasa() - 50000000);
                    player.getInventory().removeItem(GornikItems.getItem("I1", 1));
                    gornikUser.setD5_4(true);
                    bonuses.getBonusesUser().setDefnaludzi(bonuses.getBonusesUser().getDefnaludzi() + 2);
                    break;
                case 29:
                    if (!(gornikUser.isD1() && gornikUser.isD2() && gornikUser.isD3_1() && gornikUser.isD4_4())) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie odblokowales jeszcze pooprzednich atrybutow"));
                        return;
                    }
                    if (playerUser.getKasa() < 150000000) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Chyba czegos Ci brakuje moj przyjacielu &8(&2$&8)"));
                        return;
                    }
                    if (!player.getInventory().containsAtLeast(GornikItems.getItem("I1", 1), 3)) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Chyba czegos Ci brakuje moj przyjacielu &8(&6Krysztal&8)"));
                        return;
                    }
                    playerUser.setKasa(playerUser.getKasa() - 150000000);
                    player.getInventory().removeItem(GornikItems.getItem("I1", 3));
                    gornikUser.setD4_6(true);
                    bonuses.getBonusesUser().setDefnaludzi(bonuses.getBonusesUser().getDefnaludzi() + 5);
                    bonuses.getBonusesUser().setSilnynaludzi(bonuses.getBonusesUser().getSilnynaludzi() + 5);
                    break;
                case 28:
                    if (!(gornikUser.isD1() && gornikUser.isD2() && gornikUser.isD3_1() && gornikUser.isD4_4() && gornikUser.isD4_6())) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie odblokowales jeszcze pooprzednich atrybutow"));
                        return;
                    }
                    playerUser.setKasa(playerUser.getKasa() - 50000000);
                    player.getInventory().removeItem(GornikItems.getItem("I1", 1));
                    gornikUser.setD5_3(true);
                    bonuses.getBonusesUser().setSilnynaludzi(bonuses.getBonusesUser().getSilnynaludzi() + 2);
                    break;
                case 27:
                    if (!(gornikUser.isD1() && gornikUser.isD2() && gornikUser.isD3_1() && gornikUser.isD4_4() && gornikUser.isD4_6() && gornikUser.isD5_3())) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie odblokowales jeszcze pooprzednich atrybutow"));
                        return;
                    }
                    playerUser.setKasa(playerUser.getKasa() - 50000000);
                    player.getInventory().removeItem(GornikItems.getItem("I1", 1));
                    gornikUser.setD6_3(true);
                    bonuses.getBonusesUser().setDefnaludzi(bonuses.getBonusesUser().getDefnaludzi() + 1);
                    break;
                case 38:
                    if (!(gornikUser.isD1() && gornikUser.isD2() && gornikUser.isD3_1())) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie odblokowales jeszcze pooprzednich atrybutow"));
                        return;
                    }
                    playerUser.setKasa(playerUser.getKasa() - 50000000);
                    player.getInventory().removeItem(GornikItems.getItem("I1", 1));
                    gornikUser.setD4_4(true);
                    bonuses.getBonusesUser().setPrzeszyciebloku(bonuses.getBonusesUser().getPrzeszyciebloku() + 3);
                    break;
                case 47:
                    if (!(gornikUser.isD1() && gornikUser.isD2() && gornikUser.isD3_1() && gornikUser.isD4_4())) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie odblokowales jeszcze pooprzednich atrybutow"));
                        return;
                    }
                    playerUser.setKasa(playerUser.getKasa() - 50000000);
                    player.getInventory().removeItem(GornikItems.getItem("I1", 1));
                    gornikUser.setD4_5(true);
                    bonuses.getBonusesUser().setPrzeszyciebloku(bonuses.getBonusesUser().getPrzeszyciebloku() + 5);
                    break;
                case 39:
                    if (!(gornikUser.isD1() && gornikUser.isD2())) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie odblokowales jeszcze pooprzednich atrybutow"));
                        return;
                    }
                    playerUser.setKasa(playerUser.getKasa() - 50000000);
                    player.getInventory().removeItem(GornikItems.getItem("I1", 1));
                    gornikUser.setD3_1(true);
                    bonuses.getBonusesUser().setPrzeszyciebloku(bonuses.getBonusesUser().getPrzeszyciebloku() + 2);
                    break;

                // LEWA STRONA
                case 8:
                    if (!(gornikUser.isD1() && gornikUser.isD2() && gornikUser.isD3_2() && gornikUser.isD4_1() && gornikUser.isD4_3() && gornikUser.isD5_2() && gornikUser.isD6_2() && gornikUser.isD7_1() && gornikUser.isD8_1())) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie odblokowales jeszcze pooprzednich atrybutow"));
                        return;
                    }
                    if (playerUser.getKasa() < 150000000) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Chyba czegos Ci brakuje moj przyjacielu &8(&2$&8)"));
                        return;
                    }
                    if (!player.getInventory().containsAtLeast(GornikItems.getItem("I1", 1), 3)) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Chyba czegos Ci brakuje moj przyjacielu &8(&6Krysztal&8)"));
                        return;
                    }
                    playerUser.setKasa(playerUser.getKasa() - 150000000);
                    player.getInventory().removeItem(GornikItems.getItem("I1", 3));
                    gornikUser.setD9_1(true);
                    bonuses.getBonusesUser().setDodatkoweobrazenia(bonuses.getBonusesUser().getDodatkoweobrazenia() + 100);
                    break;
                case 17:
                    if (!(gornikUser.isD1() && gornikUser.isD2() && gornikUser.isD3_2() && gornikUser.isD4_1() && gornikUser.isD4_3() && gornikUser.isD5_2() && gornikUser.isD6_2() && gornikUser.isD7_1())) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie odblokowales jeszcze pooprzednich atrybutow"));
                        return;
                    }
                    playerUser.setKasa(playerUser.getKasa() - 50000000);
                    player.getInventory().removeItem(GornikItems.getItem("I1", 1));
                    gornikUser.setD8_1(true);
                    bonuses.getBonusesUser().setDodatkoweobrazenia(bonuses.getBonusesUser().getDodatkoweobrazenia() + 50);
                    break;
                case 16:
                    if (!(gornikUser.isD1() && gornikUser.isD2() && gornikUser.isD3_2() && gornikUser.isD4_1() && gornikUser.isD4_3() && gornikUser.isD5_2() && gornikUser.isD6_2())) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie odblokowales jeszcze pooprzednich atrybutow"));
                        return;
                    }
                    playerUser.setKasa(playerUser.getKasa() - 50000000);
                    player.getInventory().removeItem(GornikItems.getItem("I1", 1));
                    gornikUser.setD7_1(true);
                    bonuses.getBonusesUser().setDodatkoweobrazenia(bonuses.getBonusesUser().getDodatkoweobrazenia() + 50);
                    break;
                case 15:
                    if (!(gornikUser.isD1() && gornikUser.isD2() && gornikUser.isD3_2() && gornikUser.isD4_1() && gornikUser.isD4_3() && gornikUser.isD5_2())) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie odblokowales jeszcze pooprzednich atrybutow"));
                        return;
                    }
                    if (playerUser.getKasa() < 150000000) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Chyba czegos Ci brakuje moj przyjacielu &8(&2$&8)"));
                        return;
                    }
                    if (!player.getInventory().containsAtLeast(GornikItems.getItem("I1", 1), 3)) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Chyba czegos Ci brakuje moj przyjacielu &8(&6Krysztal&8)"));
                        return;
                    }
                    playerUser.setKasa(playerUser.getKasa() - 150000000);
                    player.getInventory().removeItem(GornikItems.getItem("I1", 3));
                    gornikUser.setD6_2(true);
                    break;
                case 24:
                    if (!(gornikUser.isD1() && gornikUser.isD2() && gornikUser.isD3_2() && gornikUser.isD4_1() && gornikUser.isD4_3())) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie odblokowales jeszcze pooprzednich atrybutow"));
                        return;
                    }
                    playerUser.setKasa(playerUser.getKasa() - 50000000);
                    player.getInventory().removeItem(GornikItems.getItem("I1", 1));
                    gornikUser.setD5_2(true);
                    bonuses.getBonusesUser().setDodatkoweobrazenia(bonuses.getBonusesUser().getDodatkoweobrazenia() + 50);
                    break;
                case 33:
                    if (!(gornikUser.isD1() && gornikUser.isD2() && gornikUser.isD3_2() && gornikUser.isD4_1())) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie odblokowales jeszcze pooprzednich atrybutow"));
                        return;
                    }
                    if (playerUser.getKasa() < 150000000) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Chyba czegos Ci brakuje moj przyjacielu &8(&2$&8)"));
                        return;
                    }
                    if (!player.getInventory().containsAtLeast(GornikItems.getItem("I1", 1), 3)) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Chyba czegos Ci brakuje moj przyjacielu &8(&6Krysztal&8)"));
                        return;
                    }
                    playerUser.setKasa(playerUser.getKasa() - 150000000);
                    player.getInventory().removeItem(GornikItems.getItem("I1", 3));
                    gornikUser.setD4_3(true);
                    bonuses.getBonusesUser().setDodatkoweobrazenia(bonuses.getBonusesUser().getDodatkoweobrazenia() + 150);
                    bonuses.getBonusesUser().setSrednieobrazenia(bonuses.getBonusesUser().getSrednieobrazenia() + 4);
                    break;
                case 34:
                    if (!(gornikUser.isD1() && gornikUser.isD2() && gornikUser.isD3_2() && gornikUser.isD4_1() && gornikUser.isD4_3())) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie odblokowales jeszcze pooprzednich atrybutow"));
                        return;
                    }
                    playerUser.setKasa(playerUser.getKasa() - 50000000);
                    player.getInventory().removeItem(GornikItems.getItem("I1", 1));
                    gornikUser.setD5_1(true);
                    bonuses.getBonusesUser().setSrednieobrazenia(bonuses.getBonusesUser().getSrednieobrazenia() + 2);
                    break;
                case 35:
                    if (!(gornikUser.isD1() && gornikUser.isD2() && gornikUser.isD3_2() && gornikUser.isD4_1() && gornikUser.isD4_3() && gornikUser.isD5_1())) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie odblokowales jeszcze pooprzednich atrybutow"));
                        return;
                    }
                    playerUser.setKasa(playerUser.getKasa() - 50000000);
                    player.getInventory().removeItem(GornikItems.getItem("I1", 1));
                    gornikUser.setD6_1(true);
                    bonuses.getBonusesUser().setSrednieobrazenia(bonuses.getBonusesUser().getSrednieobrazenia() + 3);
                    break;
                case 42:
                    if (!(gornikUser.isD1() && gornikUser.isD2() && gornikUser.isD3_2())) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie odblokowales jeszcze pooprzednich atrybutow"));
                        return;
                    }
                    playerUser.setKasa(playerUser.getKasa() - 50000000);
                    player.getInventory().removeItem(GornikItems.getItem("I1", 1));
                    gornikUser.setD4_1(true);
                    bonuses.getBonusesUser().setBlokciosu(bonuses.getBonusesUser().getBlokciosu() + 3);
                    break;
                case 51:
                    if (!(gornikUser.isD1() && gornikUser.isD2() && gornikUser.isD3_2() && gornikUser.isD4_1())) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie odblokowales jeszcze pooprzednich atrybutow"));
                        return;
                    }
                    playerUser.setKasa(playerUser.getKasa() - 50000000);
                    player.getInventory().removeItem(GornikItems.getItem("I1", 1));
                    gornikUser.setD4_2(true);
                    bonuses.getBonusesUser().setDodatkoweobrazenia(bonuses.getBonusesUser().getDodatkoweobrazenia() + 100);
                    break;
                case 41:
                    if (!(gornikUser.isD1() && gornikUser.isD2())) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie odblokowales jeszcze pooprzednich atrybutow"));
                        return;
                    }
                    playerUser.setKasa(playerUser.getKasa() - 50000000);
                    player.getInventory().removeItem(GornikItems.getItem("I1", 1));
                    gornikUser.setD3_2(true);
                    bonuses.getBonusesUser().setBlokciosu(bonuses.getBonusesUser().getBlokciosu() + 2);
                    break;
                default:
                    break;
            }
            RPGCORE.getInstance().getGornikNPC().openDrzewko(player);
            RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> {
                RPGCORE.getInstance().getMongoManager().saveDataGornik(uuid, object);
                RPGCORE.getInstance().getMongoManager().saveDataBonuses(uuid, bonuses);
                RPGCORE.getInstance().getMongoManager().saveDataUser(uuid, playerUser);
            });
        }
        
        if (title.equals("Receptury Gornika")) {
            e.setCancelled(true);
            final User user = RPGCORE.getInstance().getUserManager().find(uuid);
            switch (slot) {
                case 0:
                    if (!(player.getInventory().containsAtLeast(GornikItems.getItem("R1", 128), 1) || player.getInventory().containsAtLeast(GornikItems.getItem("R2", 128), 1) ||
                            player.getInventory().containsAtLeast(GornikItems.getItem("R3", 128), 1) || player.getInventory().containsAtLeast(GornikItems.getItem("R4", 128), 1) ||
                            player.getInventory().containsAtLeast(GornikItems.getItem("R5", 128), 1) || player.getInventory().containsAtLeast(GornikItems.getItem("R6", 128), 1) ||
                            player.getInventory().containsAtLeast(GornikItems.getItem("R7", 128), 1) || user.getKasa() < 100000000)) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie posadasz wszystkich potrzebnych skladnikow"));
                        return;
                    }
                    player.getInventory().removeItem(GornikItems.getItem("R1", 128), GornikItems.getItem("R2", 128), GornikItems.getItem("R3", 128), GornikItems.getItem("R4", 128),
                            GornikItems.getItem("R5", 128), GornikItems.getItem("R6", 128), GornikItems.getItem("R7", 128));
                    user.setKasa(user.getKasa() - 100000000);
                    player.getInventory().addItem(GornikItems.getZmiotka("T0"));
                    player.sendMessage(Utils.format("&6&lGornik &8>> &aPomyslnie wytworzyles &8Drewniane Dluto&a!"));
                    break;
                case 1:
                    if (!(player.getInventory().containsAtLeast(GornikItems.getItem("R1", 8), 1) || player.getInventory().containsAtLeast(GornikItems.getItem("R2", 8), 1) ||
                            player.getInventory().containsAtLeast(GornikItems.getItem("R4", 8), 1) || user.getKasa() < 2500000)) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie posadasz wszystkich potrzebnych skladnikow"));
                        return;
                    }
                    player.getInventory().removeItem(GornikItems.getItem("R1", 8), GornikItems.getItem("R2", 8), GornikItems.getItem("R4", 8));
                    user.setKasa(user.getKasa() - 2500000);
                    player.getInventory().addItem(GornikItems.getItem("WODA", 1));
                    player.sendMessage(Utils.format("&6&lGornik &8>> &aPomyslnie wytworzyles &1Wiadro Wody&a!"));
                    break;
            }

            RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataUser(uuid, user));
        }
    }
}
