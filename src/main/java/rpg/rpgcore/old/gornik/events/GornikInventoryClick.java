package rpg.rpgcore.old.gornik.events;

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
import org.bukkit.inventory.meta.ItemMeta;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.bonuses.Bonuses;
import rpg.rpgcore.old.gornik.objects.GornikObject;
import rpg.rpgcore.old.gornik.objects.GornikUser;
import rpg.rpgcore.old.gornik.enums.GornikExchange;
import rpg.rpgcore.old.gornik.enums.GornikMissions;
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

        final Inventory clickedInventory = e.getClickedInventory();
        final Player player = (Player) e.getWhoClicked();
        final UUID uuid = player.getUniqueId();
        final String title = Utils.removeColor(clickedInventory.getTitle());
        final ItemStack item = e.getCurrentItem();
        final int slot = e.getSlot();
        final GornikObject object = RPGCORE.getInstance().getGornikNPC().find(uuid);
        final GornikUser gornikUser = object.getGornikUser();


        if (e.getClickedInventory().getType() == InventoryType.PLAYER) {
            if (e.getAction() == InventoryAction.SWAP_WITH_CURSOR) {
                if (String.valueOf(e.getCursor().getType()).contains("_SPADE") && e.getCursor().getItemMeta().hasDisplayName()
                        && e.getCursor().getItemMeta().getDisplayName().contains(" Dluto") && player.getInventory().containsAtLeast(GornikItems.getItem("WODA", 1), 1)) {
                    if (!Utils.getTagString(e.getCursor(), "owner").equals(player.getName())) {
                        e.setCancelled(true);
                        player.sendMessage(Utils.format("&6&lGornik &8» &cNie mozesz uzywac nie swojego sprzetu!"));
                        return;
                    }
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
            if (slot == 13) {
                RPGCORE.getInstance().getGornikNPC().openOsadzanieKrysztalow(player, null);
                return;
            }
            if (slot == 15) {
                RPGCORE.getInstance().getGornikNPC().openCrafting(player);
                return;
            }
            if (slot == 22) {
                if (RPGCORE.getInstance().getGornikNPC().find(uuid).getGornikUser().getMission() <= 28) return;
                RPGCORE.getInstance().getGornikNPC().openDrzewko(player);
                return;
            }
        }

        if (title.equals("Sklep Gorniczy")) {
            e.setCancelled(true);
            if (item == null || item.getType() == Material.AIR) {
                return;
            }
            final User user = RPGCORE.getInstance().getUserManager().find(uuid);
            if (slot == 0) {
                if (user.getKasa() < 65_000_000) {
                    player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie masz wystarczajaco pieniedzy zeby kupic moj kilof!"));
                    player.closeInventory();
                    return;
                }
                user.setKasa(user.getKasa() - 65_000_000);
                RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataUser(user.getId(), user));
                player.getInventory().addItem(GornikItems.getKilof(player.getName()));
                player.sendMessage(Utils.format("&6&lGornik &8>> &7Trzymaj... Mam nadzieje, ze bedzie ci dobrze sluzyl."));
                player.sendMessage(Utils.format("&8Pssst... Kilof ten posiada niesamowite wlasciowsci przemiany."));
                player.closeInventory();
                return;
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

            if (gornikUser.getMission() == 28) {
                RPGCORE.getInstance().getServer().broadcastMessage(" ");
                RPGCORE.getInstance().getServer().broadcastMessage(Utils.format("&6&lGornik &8>> &7Gracz &c" + player.getName() + " &7ukonczyl moja &4KAMPANIE&7. &6&lGratulacje!"));
                if (RPGCORE.getInstance().getArtefaktyZaLvlManager().getArtefaktyZaLvl().getGornik().getNadanych() < 4) {
                    if (!RPGCORE.getInstance().getArtefaktyZaLvlManager().getArtefaktyZaLvl().getGornik().getGracze().contains(player.getName())) {
                        RPGCORE.getInstance().getArtefaktyZaLvlManager().getArtefaktyZaLvl().getGornik().setNadanych(RPGCORE.getInstance().getArtefaktyZaLvlManager().getArtefaktyZaLvl().getGornik().getNadanych() + 1);
                        RPGCORE.getInstance().getArtefaktyZaLvlManager().getArtefaktyZaLvl().getGornik().getGracze().add(player.getName());
                        RPGCORE.getInstance().getArtefaktyZaLvlManager().save();
                        RPGCORE.getInstance().getServer().broadcastMessage(Utils.format("&6&lGornik &8>> &7Doodatkowo gracz ten ukonczyl ja jako &c" + RPGCORE.getInstance().getArtefaktyZaLvlManager().getArtefaktyZaLvl().getGornik().getNadanych() + " &7osoba na serwerze!"));
                        RPGCORE.getInstance().getServer().broadcastMessage(Utils.format("&8Po odbior artefaktu prosimy zglosic sie"));
                        RPGCORE.getInstance().getServer().broadcastMessage(Utils.format("&8do &4Wyzszej Administracji&8."));
                    }
                }
                RPGCORE.getInstance().getServer().broadcastMessage(" ");
            }

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
                    if (!player.getInventory().containsAtLeast(GornikItems.getItem("R1", 8), 1) || !player.getInventory().containsAtLeast(GornikItems.getItem("R2", 8), 1) ||
                            !player.getInventory().containsAtLeast(GornikItems.getItem("R4", 8), 1) || user.getKasa() < 2500000) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie posadasz wszystkich potrzebnych skladnikow"));
                        return;
                    }
                    player.getInventory().removeItem(GornikItems.getItem("R1", 8), GornikItems.getItem("R2", 8), GornikItems.getItem("R4", 8));
                    user.setKasa(user.getKasa() - 2500000);
                    player.getInventory().addItem(GornikItems.getItem("WODA", 1));
                    player.sendMessage(Utils.format("&6&lGornik &8>> &aPomyslnie wytworzyles &1Wiadro Wody&a!"));
                    break;
                case 1:
                    if (!player.getInventory().containsAtLeast(GornikItems.getItem("R1", 128), 1) || !player.getInventory().containsAtLeast(GornikItems.getItem("R2", 128), 1) ||
                            !player.getInventory().containsAtLeast(GornikItems.getItem("R3", 128), 1) || !player.getInventory().containsAtLeast(GornikItems.getItem("R4", 128), 1) ||
                            !player.getInventory().containsAtLeast(GornikItems.getItem("R5", 128), 1) || !player.getInventory().containsAtLeast(GornikItems.getItem("R6", 128), 1) ||
                            !player.getInventory().containsAtLeast(GornikItems.getItem("R7", 128), 1) || user.getKasa() < 100000000) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie posadasz wszystkich potrzebnych skladnikow"));
                        return;
                    }
                    player.getInventory().removeItem(GornikItems.getItem("R1", 128), GornikItems.getItem("R2", 128), GornikItems.getItem("R3", 128), GornikItems.getItem("R4", 128),
                            GornikItems.getItem("R5", 128), GornikItems.getItem("R6", 128), GornikItems.getItem("R7", 128));
                    user.setKasa(user.getKasa() - 100000000);
                    player.getInventory().addItem(GornikItems.getZmiotka("T0", 1, player.getName()));
                    player.sendMessage(Utils.format("&6&lGornik &8>> &aPomyslnie wytworzyles &8Drewniane Dluto&a!"));
                    break;
                case 2:
                    if (!player.getInventory().containsAtLeast(GornikItems.getCompressed("G1", 1), 8) ||
                            !player.getInventory().containsAtLeast(GornikItems.getCompressed("G2", 1), 8) ||
                            !player.getInventory().containsAtLeast(GornikItems.getCompressed("G3", 1), 8) ||
                            !player.getInventory().containsAtLeast(GornikItems.getCompressed("G4", 1), 8) ||
                            !player.getInventory().containsAtLeast(GornikItems.getCompressed("G5", 1), 8) ||
                            !player.getInventory().containsAtLeast(GornikItems.getCompressed("G6", 1), 8) ||
                            !player.getInventory().containsAtLeast(GornikItems.getCompressed("G7", 1), 8) ||
                            !player.getInventory().contains(GornikItems.getZmiotka("T0", 50, player.getName())) || user.getKasa() < 200000000) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie posadasz wszystkich potrzebnych skladnikow"));
                        return;
                    }
                    player.getInventory().removeItem(GornikItems.getZmiotka("T0", 50, player.getName()), GornikItems.getCompressed("G1", 8), GornikItems.getCompressed("G2", 8),
                            GornikItems.getCompressed("G3", 8), GornikItems.getCompressed("G4", 8), GornikItems.getCompressed("G5", 8),
                            GornikItems.getCompressed("G6", 8), GornikItems.getCompressed("G7", 8));
                    user.setKasa(user.getKasa() - 200000000);
                    player.getInventory().addItem(GornikItems.getZmiotka("T1", 1, player.getName()));
                    player.sendMessage(Utils.format("&6&lGornik &8>> &aPomyslnie wytworzyles &8Kamienne Dluto&a!"));
                    break;
                case 3:
                    if (!player.getInventory().containsAtLeast(GornikItems.getCompressed("G1", 1), 16) ||
                            !player.getInventory().containsAtLeast(GornikItems.getCompressed("G2", 1), 16) ||
                            !player.getInventory().containsAtLeast(GornikItems.getCompressed("G3", 1), 16) ||
                            !player.getInventory().containsAtLeast(GornikItems.getCompressed("G4", 1), 16) ||
                            !player.getInventory().containsAtLeast(GornikItems.getCompressed("G5", 1), 16) ||
                            !player.getInventory().containsAtLeast(GornikItems.getCompressed("G6", 1), 16) ||
                            !player.getInventory().containsAtLeast(GornikItems.getCompressed("G7", 1), 16) ||
                            !player.getInventory().contains(GornikItems.getZmiotka("T1", 50, player.getName())) || user.getKasa() < 350000000) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie posadasz wszystkich potrzebnych skladnikow"));
                        return;
                    }
                    player.getInventory().removeItem(GornikItems.getZmiotka("T1", 50, player.getName()), GornikItems.getCompressed("G1", 16), GornikItems.getCompressed("G2", 16),
                            GornikItems.getCompressed("G3", 16), GornikItems.getCompressed("G4", 16), GornikItems.getCompressed("G5", 16),
                            GornikItems.getCompressed("G6", 16), GornikItems.getCompressed("G7", 16));
                    user.setKasa(user.getKasa() - 350000000);
                    player.getInventory().addItem(GornikItems.getZmiotka("T2", 1, player.getName()));
                    player.sendMessage(Utils.format("&6&lGornik &8>> &aPomyslnie wytworzyles &8Metalowe Dluto&a!"));
                    break;
                case 4:
                    if (!player.getInventory().containsAtLeast(GornikItems.getCompressed("G1", 1), 24) ||
                            !player.getInventory().containsAtLeast(GornikItems.getCompressed("G2", 1), 24) ||
                            !player.getInventory().containsAtLeast(GornikItems.getCompressed("G3", 1), 24) ||
                            !player.getInventory().containsAtLeast(GornikItems.getCompressed("G4", 1), 24) ||
                            !player.getInventory().containsAtLeast(GornikItems.getCompressed("G5", 1), 24) ||
                            !player.getInventory().containsAtLeast(GornikItems.getCompressed("G6", 1), 24) ||
                            !player.getInventory().containsAtLeast(GornikItems.getCompressed("G7", 1), 24) ||
                            !player.getInventory().contains(GornikItems.getZmiotka("T2", 50, player.getName())) || user.getKasa() < 500000000) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie posadasz wszystkich potrzebnych skladnikow"));
                        return;
                    }
                    player.getInventory().removeItem(GornikItems.getZmiotka("T2", 50, player.getName()), GornikItems.getCompressed("G1", 24), GornikItems.getCompressed("G2", 24),
                            GornikItems.getCompressed("G3", 24), GornikItems.getCompressed("G4", 24), GornikItems.getCompressed("G5", 24),
                            GornikItems.getCompressed("G6", 24), GornikItems.getCompressed("G7", 24));
                    user.setKasa(user.getKasa() - 500000000);
                    player.getInventory().addItem(GornikItems.getZmiotka("T3", 1, player.getName()));
                    player.sendMessage(Utils.format("&6&lGornik &8>> &aPomyslnie wytworzyles &8Zlote Dluto&a!"));
                    break;
                case 5:
                    if (!player.getInventory().containsAtLeast(GornikItems.getCompressed("G1", 1), 32) ||
                            !player.getInventory().containsAtLeast(GornikItems.getCompressed("G2", 1), 32) ||
                            !player.getInventory().containsAtLeast(GornikItems.getCompressed("G3", 1), 32) ||
                            !player.getInventory().containsAtLeast(GornikItems.getCompressed("G4", 1), 32) ||
                            !player.getInventory().containsAtLeast(GornikItems.getCompressed("G5", 1), 32) ||
                            !player.getInventory().containsAtLeast(GornikItems.getCompressed("G6", 1), 32) ||
                            !player.getInventory().containsAtLeast(GornikItems.getCompressed("G7", 1), 32) ||
                            !player.getInventory().contains(GornikItems.getZmiotka("T3", 50, player.getName())) || user.getKasa() < 750000000) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie posadasz wszystkich potrzebnych skladnikow"));
                        return;
                    }
                    player.getInventory().removeItem(GornikItems.getZmiotka("T3", 50, player.getName()), GornikItems.getCompressed("G1", 32), GornikItems.getCompressed("G2", 32),
                            GornikItems.getCompressed("G3", 32), GornikItems.getCompressed("G4", 32), GornikItems.getCompressed("G5", 32),
                            GornikItems.getCompressed("G6", 32), GornikItems.getCompressed("G7", 32));
                    user.setKasa(user.getKasa() - 750000000);
                    player.getInventory().addItem(GornikItems.getZmiotka("T4", 1, player.getName()));
                    player.sendMessage(Utils.format("&6&lGornik &8>> &aPomyslnie wytworzyles &8Diamentowe Dluto&a!"));
                    break;
                case 6:
                    if (!player.getInventory().containsAtLeast(GornikItems.getCompressed("C1", 1), 5) ||
                            !player.getInventory().containsAtLeast(GornikItems.getCompressed("C2", 1), 5) ||
                            !player.getInventory().containsAtLeast(GornikItems.getCompressed("C3", 1), 5) ||
                            !player.getInventory().containsAtLeast(GornikItems.getCompressed("C4", 1), 5) ||
                            !player.getInventory().containsAtLeast(GornikItems.getCompressed("C5", 1), 5) ||
                            !player.getInventory().containsAtLeast(GornikItems.getCompressed("C6", 1), 5) ||
                            !player.getInventory().containsAtLeast(GornikItems.getCompressed("C7", 1), 5)) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie posadasz wszystkich potrzebnych skladnikow"));
                        return;
                    }
                    player.getInventory().removeItem(GornikItems.getCompressed("C1", 5), GornikItems.getCompressed("C2", 5),
                            GornikItems.getCompressed("C3", 5), GornikItems.getCompressed("C4", 5), GornikItems.getCompressed("C5", 5),
                            GornikItems.getCompressed("C6", 5), GornikItems.getCompressed("C7", 5));
                    user.setKasa(user.getKasa() - 100_000_000);
                    player.getInventory().addItem(GornikItems.getItem("I1", 1));
                    player.sendMessage(Utils.format("&6&lGornik &8>> &aPomyslnie wytworzyles &6&lLegendarny Krysztal Wzmocnienia&a!"));
                    break;
                case 8:
                    if (RPGCORE.getInstance().getGornikNPC().find(uuid).getGornikUser().getMission() > 28) {
                        if (RPGCORE.getInstance().getUserManager().find(uuid).getLvl() >= 110) {
                            RPGCORE.getInstance().getGornikNPC().openCraftingiKrysztalow(player);
                        }
                    }
                    return;
                case 10:


                default:
                    return;
            }

            RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataUser(uuid, user));
        }


        if (title.equals("Wytwarzanie Krysztalow")) {
            e.setCancelled(true);
            if (item.getType() == Material.STAINED_GLASS_PANE) {
                return;
            }
            final User user = RPGCORE.getInstance().getUserManager().find(uuid);
            ItemStack itemToAdd = null;
            switch (slot) {
                // MROKU
                case 10:
                    if (!player.getInventory().containsAtLeast(GornikItems.getItem("G1", 1), 8) || user.getKasa() < 1000000) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie posadasz wszystkich potrzebnych skladnikow"));
                        return;
                    }
                    player.getInventory().removeItem(GornikItems.getItem("G1", 8));
                    user.setKasa(user.getKasa() - 1000000);
                    itemToAdd = GornikItems.getItem("P1", 1);
                    break;
                case 19:
                    if (!player.getInventory().containsAtLeast(GornikItems.getItem("P1", 1), 8) || user.getKasa() < 2500000) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie posadasz wszystkich potrzebnych skladnikow"));
                        return;
                    }
                    player.getInventory().removeItem(GornikItems.getItem("P1", 8));
                    user.setKasa(user.getKasa() - 2500000);
                    itemToAdd = GornikItems.getItem("S1", 1);
                    break;
                case 28:
                    if (!player.getInventory().containsAtLeast(GornikItems.getItem("S1", 1), 8) || user.getKasa() < 7500000) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie posadasz wszystkich potrzebnych skladnikow"));
                        return;
                    }
                    player.getInventory().removeItem(GornikItems.getItem("S1", 8));
                    user.setKasa(user.getKasa() - 7500000);
                    itemToAdd = GornikItems.getItem("C1", 1);
                    break;
                case 37:
                    if (!player.getInventory().containsAtLeast(GornikItems.getItem("C1", 1), 8) || user.getKasa() < 12000000) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie posadasz wszystkich potrzebnych skladnikow"));
                        return;
                    }
                    player.getInventory().removeItem(GornikItems.getItem("C1", 8));
                    user.setKasa(user.getKasa() - 12000000);
                    itemToAdd = GornikItems.getItem("W1", 1);
                    break;
                // POWIETRZA
                case 11:
                    if (!player.getInventory().containsAtLeast(GornikItems.getItem("G2", 1), 8) || user.getKasa() < 1000000) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie posadasz wszystkich potrzebnych skladnikow"));
                        return;
                    }
                    player.getInventory().removeItem(GornikItems.getItem("G2", 8));
                    user.setKasa(user.getKasa() - 1000000);
                    itemToAdd = GornikItems.getItem("P2", 1);
                    break;
                case 20:
                    if (!player.getInventory().containsAtLeast(GornikItems.getItem("P2", 1), 8) || user.getKasa() < 2500000) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie posadasz wszystkich potrzebnych skladnikow"));
                        return;
                    }
                    player.getInventory().removeItem(GornikItems.getItem("P2", 8));
                    user.setKasa(user.getKasa() - 2500000);
                    itemToAdd = GornikItems.getItem("S2", 1);
                    break;
                case 29:
                    if (!player.getInventory().containsAtLeast(GornikItems.getItem("S2", 1), 8) || user.getKasa() < 7500000) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie posadasz wszystkich potrzebnych skladnikow"));
                        return;
                    }
                    player.getInventory().removeItem(GornikItems.getItem("S2", 8));
                    user.setKasa(user.getKasa() - 7500000);
                    itemToAdd = GornikItems.getItem("C2", 1);
                    break;
                case 38:
                    if (!player.getInventory().containsAtLeast(GornikItems.getItem("C2", 1), 8) || user.getKasa() < 12000000) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie posadasz wszystkich potrzebnych skladnikow"));
                        return;
                    }
                    player.getInventory().removeItem(GornikItems.getItem("C2", 8));
                    user.setKasa(user.getKasa() - 12000000);
                    itemToAdd = GornikItems.getItem("W2", 1);
                    break;
                // BLASKU
                case 12:
                    if (!player.getInventory().containsAtLeast(GornikItems.getItem("G3", 1), 8) || user.getKasa() < 1000000) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie posadasz wszystkich potrzebnych skladnikow"));
                        return;
                    }
                    player.getInventory().removeItem(GornikItems.getItem("G3", 8));
                    user.setKasa(user.getKasa() - 1000000);
                    itemToAdd = GornikItems.getItem("P3", 1);
                    break;
                case 21:
                    if (!player.getInventory().containsAtLeast(GornikItems.getItem("P3", 1), 8) || user.getKasa() < 2500000) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie posadasz wszystkich potrzebnych skladnikow"));
                        return;
                    }
                    player.getInventory().removeItem(GornikItems.getItem("P3", 8));
                    user.setKasa(user.getKasa() - 2500000);
                    itemToAdd = GornikItems.getItem("S3", 1);
                    break;
                case 30:
                    if (!player.getInventory().containsAtLeast(GornikItems.getItem("S3", 1), 8) || user.getKasa() < 7500000) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie posadasz wszystkich potrzebnych skladnikow"));
                        return;
                    }
                    player.getInventory().removeItem(GornikItems.getItem("S3", 8));
                    user.setKasa(user.getKasa() - 7500000);
                    itemToAdd = GornikItems.getItem("C3", 1);
                    break;
                case 39:
                    if (!player.getInventory().containsAtLeast(GornikItems.getItem("C3", 1), 8) || user.getKasa() < 12000000) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie posadasz wszystkich potrzebnych skladnikow"));
                        return;
                    }
                    player.getInventory().removeItem(GornikItems.getItem("C3", 8));
                    user.setKasa(user.getKasa() - 12000000);
                    itemToAdd = GornikItems.getItem("W3", 1);
                    break;
                // WODY
                case 13:
                    if (!player.getInventory().containsAtLeast(GornikItems.getItem("G4", 1), 8) || user.getKasa() < 1000000) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie posadasz wszystkich potrzebnych skladnikow"));
                        return;
                    }
                    player.getInventory().removeItem(GornikItems.getItem("G4", 8));
                    user.setKasa(user.getKasa() - 1000000);
                    itemToAdd = GornikItems.getItem("P4", 1);
                    break;
                case 22:
                    if (!player.getInventory().containsAtLeast(GornikItems.getItem("P4", 1), 8) || user.getKasa() < 2500000) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie posadasz wszystkich potrzebnych skladnikow"));
                        return;
                    }
                    player.getInventory().removeItem(GornikItems.getItem("P4", 8));
                    user.setKasa(user.getKasa() - 2500000);
                    itemToAdd = GornikItems.getItem("S4", 1);
                    break;
                case 31:
                    if (!player.getInventory().containsAtLeast(GornikItems.getItem("S4", 1), 8) || user.getKasa() < 7500000) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie posadasz wszystkich potrzebnych skladnikow"));
                        return;
                    }
                    player.getInventory().removeItem(GornikItems.getItem("S4", 8));
                    user.setKasa(user.getKasa() - 7500000);
                    itemToAdd = GornikItems.getItem("C4", 1);
                    break;
                case 40:
                    if (!player.getInventory().containsAtLeast(GornikItems.getItem("C4", 1), 8) || user.getKasa() < 12000000) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie posadasz wszystkich potrzebnych skladnikow"));
                        return;
                    }
                    player.getInventory().removeItem(GornikItems.getItem("C4", 8));
                    user.setKasa(user.getKasa() - 12000000);
                    itemToAdd = GornikItems.getItem("W4", 1);
                    break;
                // LASU
                case 14:
                    if (!player.getInventory().containsAtLeast(GornikItems.getItem("G5", 1), 8) || user.getKasa() < 1000000) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie posadasz wszystkich potrzebnych skladnikow"));
                        return;
                    }
                    player.getInventory().removeItem(GornikItems.getItem("G5", 8));
                    user.setKasa(user.getKasa() - 1000000);
                    itemToAdd = GornikItems.getItem("P5", 1);
                    break;
                case 23:
                    if (!player.getInventory().containsAtLeast(GornikItems.getItem("P5", 1), 8) || user.getKasa() < 2500000) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie posadasz wszystkich potrzebnych skladnikow"));
                        return;
                    }
                    player.getInventory().removeItem(GornikItems.getItem("P5", 8));
                    user.setKasa(user.getKasa() - 2500000);
                    itemToAdd = GornikItems.getItem("S5", 1);
                    break;
                case 32:
                    if (!player.getInventory().containsAtLeast(GornikItems.getItem("S5", 1), 8) || user.getKasa() < 7500000) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie posadasz wszystkich potrzebnych skladnikow"));
                        return;
                    }
                    player.getInventory().removeItem(GornikItems.getItem("S5", 8));
                    user.setKasa(user.getKasa() - 7500000);
                    itemToAdd = GornikItems.getItem("C5", 1);
                    break;
                case 41:
                    if (!player.getInventory().containsAtLeast(GornikItems.getItem("C5", 1), 8) || user.getKasa() < 12000000) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie posadasz wszystkich potrzebnych skladnikow"));
                        return;
                    }
                    player.getInventory().removeItem(GornikItems.getItem("C5", 8));
                    user.setKasa(user.getKasa() - 12000000);
                    itemToAdd = GornikItems.getItem("W5", 1);
                    break;
                // LODU
                case 15:
                    if (!player.getInventory().containsAtLeast(GornikItems.getItem("G6", 1), 8) || user.getKasa() < 1000000) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie posadasz wszystkich potrzebnych skladnikow"));
                        return;
                    }
                    player.getInventory().removeItem(GornikItems.getItem("G6", 8));
                    user.setKasa(user.getKasa() - 1000000);
                    itemToAdd = GornikItems.getItem("P6", 1);
                    break;
                case 24:
                    if (!player.getInventory().containsAtLeast(GornikItems.getItem("P6", 1), 8) || user.getKasa() < 2500000) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie posadasz wszystkich potrzebnych skladnikow"));
                        return;
                    }
                    player.getInventory().removeItem(GornikItems.getItem("P6", 8));
                    user.setKasa(user.getKasa() - 2500000);
                    itemToAdd = GornikItems.getItem("S6", 1);
                    break;
                case 33:
                    if (!player.getInventory().containsAtLeast(GornikItems.getItem("S6", 1), 8) || user.getKasa() < 7500000) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie posadasz wszystkich potrzebnych skladnikow"));
                        return;
                    }
                    player.getInventory().removeItem(GornikItems.getItem("S6", 8));
                    user.setKasa(user.getKasa() - 7500000);
                    itemToAdd = GornikItems.getItem("C6", 1);
                    break;
                case 42:
                    if (!player.getInventory().containsAtLeast(GornikItems.getItem("C6", 1), 8) || user.getKasa() < 12000000) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie posadasz wszystkich potrzebnych skladnikow"));
                        return;
                    }
                    player.getInventory().removeItem(GornikItems.getItem("C6", 8));
                    user.setKasa(user.getKasa() - 12000000);
                    itemToAdd = GornikItems.getItem("W6", 1);
                    break;
                // OGNIA
                case 16:
                    if (!player.getInventory().containsAtLeast(GornikItems.getItem("G7", 1), 8) || user.getKasa() < 1000000) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie posadasz wszystkich potrzebnych skladnikow"));
                        return;
                    }
                    player.getInventory().removeItem(GornikItems.getItem("G7", 8));
                    user.setKasa(user.getKasa() - 1000000);
                    itemToAdd = GornikItems.getItem("P7", 1);
                    break;
                case 25:
                    if (!player.getInventory().containsAtLeast(GornikItems.getItem("P7", 1), 8) || user.getKasa() < 2500000) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie posadasz wszystkich potrzebnych skladnikow"));
                        return;
                    }
                    player.getInventory().removeItem(GornikItems.getItem("P7", 8));
                    user.setKasa(user.getKasa() - 2500000);
                    itemToAdd = GornikItems.getItem("S7", 1);
                    break;
                case 34:
                    if (!player.getInventory().containsAtLeast(GornikItems.getItem("S7", 1), 8) || user.getKasa() < 7500000) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie posadasz wszystkich potrzebnych skladnikow"));
                        return;
                    }
                    player.getInventory().removeItem(GornikItems.getItem("S7", 8));
                    user.setKasa(user.getKasa() - 7500000);
                    itemToAdd = GornikItems.getItem("C7", 1);
                    break;
                case 43:
                    if (!player.getInventory().containsAtLeast(GornikItems.getItem("C7", 1), 8) || user.getKasa() < 12000000) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie posadasz wszystkich potrzebnych skladnikow"));
                        return;
                    }
                    player.getInventory().removeItem(GornikItems.getItem("C7", 8));
                    user.setKasa(user.getKasa() - 12000000);
                    itemToAdd = GornikItems.getItem("W7", 1);
                    break;
            }

            player.getInventory().addItem(itemToAdd);
            player.sendMessage(Utils.format("&6&lGornik &8>> &aPomyslnie wytworzyles " + itemToAdd.getItemMeta().getDisplayName() + "&a!"));
            RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataUser(uuid, user));
        }

        if (title.equals("Osadzanie Krysztalow")) {
            e.setCancelled(true);
            if (clickedInventory.getType() == InventoryType.DISPENSER){
                if (slot != 4) return;
                if (e.getCursor() == null || e.getCursor().getType() == Material.AIR) return;
                if (!String.valueOf(e.getCursor().getType()).contains("_SWORD") && !String.valueOf(e.getCursor().getType()).contains("_HELMET")
                        && !String.valueOf(e.getCursor().getType()).contains("_CHESTPLATE")
                        && !String.valueOf(e.getCursor().getType()).contains("_LEGGINGS") && !String.valueOf(e.getCursor().getType()).contains("_BOOTS")) return;
                ItemStack is = e.getCursor().clone();
                e.setCursor(null);
                RPGCORE.getInstance().getGornikNPC().openOsadzanieKrysztalow(player, is);
                return;
            }
            if (clickedInventory.getSize() != 54) return;
            if (item != null && item.getType() == Material.STAINED_GLASS_PANE && item.getDurability() == 0) return;
            if (slot == 13) {
                if (e.getCursor() != null && e.getCursor().getType() != Material.AIR) {
                    player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie mozesz tego aktualnie osadzic!"));
                    player.sendMessage(Utils.format("&8Zdejmij przedmiot z kursora!"));
                    return;
                }
                player.getInventory().addItem(e.getCurrentItem());
                e.setCurrentItem(null);
                RPGCORE.getInstance().getGornikNPC().openOsadzanieKrysztalow(player, null);
                return;
            }
            
            final ItemStack playerItem = clickedInventory.getItem(13);
            final User user = RPGCORE.getInstance().getUserManager().find(uuid);
            int slotKrysztalu = 0;
            String prefix = "";
            if (String.valueOf(playerItem.getType()).contains("_SWORD")) {
                switch (slot) {
                    case 38:
                        slotKrysztalu = 0;
                        final String mroku = Utils.getTagString(playerItem, "Mroku");
                        if (mroku.equals("false")) {
                            if (e.getCursor() != null && e.getCursor().getType() != Material.AIR) {
                                player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie mozesz tego aktualnie osadzic!"));
                                player.sendMessage(Utils.format("&8Zdejmij przedmiot z kursora!"));
                                return;
                            }
                            if (!player.getInventory().containsAtLeast(GornikItems.getItem("S1", 1), 5) || user.getKasa() < 25000000) {
                                player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie posadasz wszystkich potrzebnych skladnikow"));
                                return;
                            }
                            player.getInventory().removeItem(GornikItems.getItem("S1", 5));
                            user.setKasa(user.getKasa() - 25000000);
                            Utils.setTagString(playerItem, "Mroku", "true");
                            prefix = "&7";
                            break;
                        }
                        if (mroku.equals("true")) {
                            if (e.getCursor() == null || e.getCursor().getType() == Material.AIR || e.getCursor().getAmount() != 1) return;
                            if (e.getCursor().equals(GornikItems.getItem("C1", 1))) {
                                e.setCursor(null);
                                Utils.setTagString(playerItem, "Mroku", "Czysty");
                                Utils.setTagDouble(playerItem, "Mroku-Bonus", 3);
                                prefix = "&0";
                                break;
                            }
                            if (e.getCursor().equals(GornikItems.getItem("W1", 1))) {
                                e.setCursor(null);
                                Utils.setTagString(playerItem, "Mroku", "Wypolerowany");
                                Utils.setTagDouble(playerItem, "Mroku-Bonus", 8);
                                prefix = "&0&l";
                                break;
                            }
                            player.sendMessage(Utils.format("&6&lGornik &8>> &7To miejsce jest przeznaczone na inny &5Krysztal&7!"));
                            return;
                        }

                        if (mroku.equals("Czysty")) {
                            if (e.getCursor() != null && e.getCursor().getType() != Material.AIR) {
                                player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie mozesz tego aktualnie osadzic!"));
                                player.sendMessage(Utils.format("&8Zdejmij przedmiot z kursora!"));
                                return;
                            }
                            if (!ChanceHelper.getChance(50)) {
                                player.sendMessage(Utils.format("&6&lGornik &8>> &7Niestety twoj &5Krysztal &7doznal powaznych uszkodzen pod czas wyjmowania i zostal zniszczony"));
                            } else {
                                player.sendMessage(Utils.format("&6&lGornik &8>> &7Pomyslnie wyjmowalem twoj &5Krysztal"));
                                player.getInventory().addItem(GornikItems.getItem("C1", 1));
                            }
                        }
                        if (mroku.equals("Wypolerowany")) {
                            if (e.getCursor() != null && e.getCursor().getType() != Material.AIR) {
                                player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie mozesz tego aktualnie osadzic!"));
                                player.sendMessage(Utils.format("&8Zdejmij przedmiot z kursora!"));
                                return;
                            }
                            if (!ChanceHelper.getChance(60)) {
                                player.sendMessage(Utils.format("&6&lGornik &8>> &7Niestety twoj &5Krysztal &7doznal powaznych uszkodzen pod czas wyjmowania i zostal zniszczony"));
                            } else {
                                player.sendMessage(Utils.format("&6&lGornik &8>> &7Pomyslnie wyjmowalem twoj &5Krysztal"));
                                player.getInventory().addItem(GornikItems.getItem("W1", 1));
                            }
                        }
                        Utils.setTagString(playerItem, "Mroku", "true");
                        Utils.setTagDouble(playerItem, "Mroku-Bonus", 0);
                        prefix = "&7";
                        break;
                    case 40:
                        slotKrysztalu = 1;
                        final String blasku = Utils.getTagString(playerItem, "Blasku");
                        if (blasku.equals("false")) {
                            if (e.getCursor() != null && e.getCursor().getType() != Material.AIR) {
                                player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie mozesz tego aktualnie osadzic!"));
                                player.sendMessage(Utils.format("&8Zdejmij przedmiot z kursora!"));
                                return;
                            }
                            if (!player.getInventory().containsAtLeast(GornikItems.getItem("S3", 1), 5) || user.getKasa() < 25000000) {
                                player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie posadasz wszystkich potrzebnych skladnikow"));
                                return;
                            }
                            player.getInventory().removeItem(GornikItems.getItem("S3", 5));
                            user.setKasa(user.getKasa() - 25000000);
                            Utils.setTagString(playerItem, "Blasku", "true");
                            prefix = "&7";
                            break;
                        }
                        if (blasku.equals("true")) {
                            if (e.getCursor() == null || e.getCursor().getType() == Material.AIR || e.getCursor().getAmount() != 1) return;
                            if (e.getCursor().equals(GornikItems.getItem("C3", 1))) {
                                e.setCursor(null);
                                Utils.setTagString(playerItem, "Blasku", "Czysty");
                                Utils.setTagInt(playerItem, "Blasku-Bonus", 75);
                                prefix = "&e";
                                break;
                            } 
                            if (e.getCursor().equals(GornikItems.getItem("W3", 1))) {
                                e.setCursor(null);
                                Utils.setTagString(playerItem, "Blasku", "Wypolerowany");
                                Utils.setTagInt(playerItem, "Blasku-Bonus", 300);
                                prefix = "&e&l";
                                break;
                            }
                            player.sendMessage(Utils.format("&6&lGornik &8>> &7To miejsce jest przeznaczone na inny &5Krysztal&7!"));
                            return;
                        }
                        if (blasku.equals("Czysty")) {
                            if (e.getCursor() != null && e.getCursor().getType() != Material.AIR) {
                                player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie mozesz tego aktualnie osadzic!"));
                                player.sendMessage(Utils.format("&8Zdejmij przedmiot z kursora!"));
                                return;
                            }
                            if (!ChanceHelper.getChance(50)) {
                                player.sendMessage(Utils.format("&6&lGornik &8>> &7Niestety twoj &5Krysztal &7doznal powaznych uszkodzen pod czas wyjmowania i zostal zniszczony"));
                            } else {
                                player.sendMessage(Utils.format("&6&lGornik &8>> &7Pomyslnie wyjmowalem twoj &5Krysztal"));
                                player.getInventory().addItem(GornikItems.getItem("C3", 1));
                            }
                        }
                        if (blasku.equals("Wypolerowany")) {
                            if (e.getCursor() != null && e.getCursor().getType() != Material.AIR) {
                                player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie mozesz tego aktualnie osadzic!"));
                                player.sendMessage(Utils.format("&8Zdejmij przedmiot z kursora!"));
                                return;
                            }
                            if (!ChanceHelper.getChance(60)) {
                                player.sendMessage(Utils.format("&6&lGornik &8>> &7Niestety twoj &5Krysztal &7doznal powaznych uszkodzen pod czas wyjmowania i zostal zniszczony"));
                            } else {
                                player.sendMessage(Utils.format("&6&lGornik &8>> &7Pomyslnie wyjmowalem twoj &5Krysztal"));
                                player.getInventory().addItem(GornikItems.getItem("W3", 1));
                            }
                        }
                        Utils.setTagString(playerItem, "Blasku", "true");
                        Utils.setTagInt(playerItem, "Blasku-Bonus", 0);
                        prefix = "&7";
                        break;
                    case 42:
                        slotKrysztalu = 2;
                        final String ognia = Utils.getTagString(playerItem, "Ognia");
                        if (ognia.equals("false")) {
                            if (e.getCursor() != null && e.getCursor().getType() != Material.AIR) {
                                player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie mozesz tego aktualnie osadzic!"));
                                player.sendMessage(Utils.format("&8Zdejmij przedmiot z kursora!"));
                                return;
                            }
                            if (!player.getInventory().containsAtLeast(GornikItems.getItem("S7", 1), 5) || user.getKasa() < 25000000) {
                                player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie posadasz wszystkich potrzebnych skladnikow"));
                                return;
                            }
                            player.getInventory().removeItem(GornikItems.getItem("S7", 5));
                            user.setKasa(user.getKasa() - 25000000);
                            Utils.setTagString(playerItem, "Ognia", "true");
                            prefix = "&7";
                            break;
                        }
                        if (ognia.equals("true")) {
                            if (e.getCursor() == null || e.getCursor().getType() == Material.AIR || e.getCursor().getAmount() != 1) return;
                            if (e.getCursor().equals(GornikItems.getItem("C7", 1))) {
                                e.setCursor(null);
                                Utils.setTagString(playerItem, "Ognia", "Czysty");
                                Utils.setTagDouble(playerItem, "Ognia-Bonus", 2.5);
                                prefix = "&c";
                                break;
                            }  
                            if (e.getCursor().equals(GornikItems.getItem("W7", 1))) {
                                e.setCursor(null);
                                Utils.setTagString(playerItem, "Ognia", "Wypolerowany");
                                Utils.setTagDouble(playerItem, "Ognia-Bonus", 7.5);
                                prefix = "&c&l";
                                break;
                            }
                            player.sendMessage(Utils.format("&6&lGornik &8>> &7To miejsce jest przeznaczone na inny &5Krysztal&7!"));
                            return;
                        }
                        if (ognia.equals("Czysty")) {
                            if (e.getCursor() != null && e.getCursor().getType() != Material.AIR) {
                                player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie mozesz tego aktualnie osadzic!"));
                                player.sendMessage(Utils.format("&8Zdejmij przedmiot z kursora!"));
                                return;
                            }
                            if (!ChanceHelper.getChance(50)) {
                                player.sendMessage(Utils.format("&6&lGornik &8>> &7Niestety twoj &5Krysztal &7doznal powaznych uszkodzen pod czas wyjmowania i zostal zniszczony"));
                            } else {
                                player.sendMessage(Utils.format("&6&lGornik &8>> &7Pomyslnie wyjmowalem twoj &5Krysztal"));
                                player.getInventory().addItem(GornikItems.getItem("C7", 1));
                            }
                        }
                        if (ognia.equals("Wypolerowany")) {
                            if (e.getCursor() != null && e.getCursor().getType() != Material.AIR) {
                                player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie mozesz tego aktualnie osadzic!"));
                                player.sendMessage(Utils.format("&8Zdejmij przedmiot z kursora!"));
                                return;
                            }
                            if (!ChanceHelper.getChance(60)) {
                                player.sendMessage(Utils.format("&6&lGornik &8>> &7Niestety twoj &5Krysztal &7doznal powaznych uszkodzen pod czas wyjmowania i zostal zniszczony"));
                            } else {
                                player.sendMessage(Utils.format("&6&lGornik &8>> &7Pomyslnie wyjmowalem twoj &5Krysztal"));
                                player.getInventory().addItem(GornikItems.getItem("W7", 1));
                            }
                        }
                        Utils.setTagString(playerItem, "Ognia", "true");
                        Utils.setTagDouble(playerItem, "Ognia-Bonus", 0);
                        prefix = "&7";
                        break;
                    default:
                        return;
                }
            } else {
                switch (slot) {
                    case 38:
                        slotKrysztalu = 0;
                        final String wody = Utils.getTagString(playerItem, "Wody");
                        if (wody.equals("false")) {
                            if (e.getCursor() != null && e.getCursor().getType() != Material.AIR) {
                                player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie mozesz tego aktualnie osadzic!"));
                                player.sendMessage(Utils.format("&8Zdejmij przedmiot z kursora!"));
                                return;
                            }
                            if (!player.getInventory().containsAtLeast(GornikItems.getItem("S4", 1), 5) || user.getKasa() < 25000000) {
                                player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie posadasz wszystkich potrzebnych skladnikow"));
                                return;
                            }
                            player.getInventory().removeItem(GornikItems.getItem("S4", 5));
                            user.setKasa(user.getKasa() - 25000000);
                            Utils.setTagString(playerItem, "Wody", "true");
                            prefix = "&7";
                            break;
                        }
                        if (wody.equals("true")) {
                            if (e.getCursor() == null || e.getCursor().getType() == Material.AIR || e.getCursor().getAmount() != 1) return;
                            if (e.getCursor().equals(GornikItems.getItem("C4", 1))) {
                                e.setCursor(null);
                                Utils.setTagString(playerItem, "Wody", "Czysty");
                                Utils.setTagDouble(playerItem, "Wody-Bonus", 1.5);
                                prefix = "&1";
                                break;
                            }
                            if (e.getCursor().equals(GornikItems.getItem("W4", 1))) {
                                e.setCursor(null);
                                Utils.setTagString(playerItem, "Wody", "Wypolerowany");
                                Utils.setTagDouble(playerItem, "Wody-Bonus", 4);
                                prefix = "&1&l";
                                break;
                            }
                            player.sendMessage(Utils.format("&6&lGornik &8>> &7To miejsce jest przeznaczone na inny &5Krysztal&7!"));
                            return;
                        }
                        if (wody.equals("Czysty")) {
                            if (e.getCursor() != null && e.getCursor().getType() != Material.AIR) {
                                player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie mozesz tego aktualnie osadzic!"));
                                player.sendMessage(Utils.format("&8Zdejmij przedmiot z kursora!"));
                                return;
                            }
                            if (!ChanceHelper.getChance(50)) {
                                player.sendMessage(Utils.format("&6&lGornik &8>> &7Niestety twoj &5Krysztal &7doznal powaznych uszkodzen pod czas wyjmowania i zostal zniszczony"));
                            } else {
                                player.sendMessage(Utils.format("&6&lGornik &8>> &7Pomyslnie wyjmowalem twoj &5Krysztal"));
                                player.getInventory().addItem(GornikItems.getItem("C4", 1));
                            }
                        }
                        if (wody.equals("Wypolerowany")) {
                            if (e.getCursor() != null && e.getCursor().getType() != Material.AIR) {
                                player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie mozesz tego aktualnie osadzic!"));
                                player.sendMessage(Utils.format("&8Zdejmij przedmiot z kursora!"));
                                return;
                            }
                            if (!ChanceHelper.getChance(60)) {
                                player.sendMessage(Utils.format("&6&lGornik &8>> &7Niestety twoj &5Krysztal &7doznal powaznych uszkodzen pod czas wyjmowania i zostal zniszczony"));
                            } else {
                                player.sendMessage(Utils.format("&6&lGornik &8>> &7Pomyslnie wyjmowalem twoj &5Krysztal"));
                                player.getInventory().addItem(GornikItems.getItem("W4", 1));
                            }
                        }
                        Utils.setTagString(playerItem, "Wody", "true");
                        Utils.setTagDouble(playerItem, "Wody-Bonus", 0);
                        prefix = "&7";
                        break;
                    case 40:
                        slotKrysztalu = 1;
                        final String lodu = Utils.getTagString(playerItem, "Lodu");
                        if (lodu.equals("false")) {
                            if (e.getCursor() != null && e.getCursor().getType() != Material.AIR) {
                                player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie mozesz tego aktualnie osadzic!"));
                                player.sendMessage(Utils.format("&8Zdejmij przedmiot z kursora!"));
                                return;
                            }
                            if (!player.getInventory().containsAtLeast(GornikItems.getItem("S6", 1), 5) || user.getKasa() < 25000000) {
                                player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie posadasz wszystkich potrzebnych skladnikow"));
                                return;
                            }
                            player.getInventory().removeItem(GornikItems.getItem("S6", 5));
                            user.setKasa(user.getKasa() - 25000000);
                            Utils.setTagString(playerItem, "Lodu", "true");
                            prefix = "&7";
                            break;
                        }
                        if (lodu.equals("true")) {
                            if (e.getCursor() == null || e.getCursor().getType() == Material.AIR || e.getCursor().getAmount() != 1) return;
                            if (e.getCursor().equals(GornikItems.getItem("C6", 1))) {
                                e.setCursor(null);
                                Utils.setTagString(playerItem, "Lodu", "Czysty");
                                Utils.setTagDouble(playerItem, "Lodu-Bonus", 0.5);
                                prefix = "&b";
                                break;
                            }
                            if (e.getCursor().equals(GornikItems.getItem("W6", 1))) {
                                e.setCursor(null);
                                Utils.setTagString(playerItem, "Lodu", "Wypolerowany");
                                Utils.setTagDouble(playerItem, "Lodu-Bonus", 1.5);
                                prefix = "&b&l";
                                break;
                            }
                            player.sendMessage(Utils.format("&6&lGornik &8>> &7To miejsce jest przeznaczone na inny &5Krysztal&7!"));
                            return;
                        }
                        if (lodu.equals("Czysty")) {
                            if (e.getCursor() != null && e.getCursor().getType() != Material.AIR) {
                                player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie mozesz tego aktualnie osadzic!"));
                                player.sendMessage(Utils.format("&8Zdejmij przedmiot z kursora!"));
                                return;
                            }
                            if (!ChanceHelper.getChance(50)) {
                                player.sendMessage(Utils.format("&6&lGornik &8>> &7Niestety twoj &5Krysztal &7doznal powaznych uszkodzen pod czas wyjmowania i zostal zniszczony"));
                            } else {
                                player.sendMessage(Utils.format("&6&lGornik &8>> &7Pomyslnie wyjmowalem twoj &5Krysztal"));
                                player.getInventory().addItem(GornikItems.getItem("C6", 1));
                            }
                        }
                        if (lodu.equals("Wypolerowany")) {
                            if (e.getCursor() != null && e.getCursor().getType() != Material.AIR) {
                                player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie mozesz tego aktualnie osadzic!"));
                                player.sendMessage(Utils.format("&8Zdejmij przedmiot z kursora!"));
                                return;
                            }
                            if (!ChanceHelper.getChance(60)) {
                                player.sendMessage(Utils.format("&6&lGornik &8>> &7Niestety twoj &5Krysztal &7doznal powaznych uszkodzen pod czas wyjmowania i zostal zniszczony"));
                            } else {
                                player.sendMessage(Utils.format("&6&lGornik &8>> &7Pomyslnie wyjmowalem twoj &5Krysztal"));
                                player.getInventory().addItem(GornikItems.getItem("W6", 1));
                            }
                        }
                        Utils.setTagString(playerItem, "Lodu", "true");
                        Utils.setTagDouble(playerItem, "Lodu-Bonus", 0);
                        prefix = "&7";
                        break;
                    case 42:
                        slotKrysztalu = 2;
                        if (String.valueOf(playerItem.getType()).contains("_BOOTS")) {
                            final String powietrza = Utils.getTagString(playerItem, "Powietrza");
                            if (powietrza.equals("false")) {
                                if (e.getCursor() != null && e.getCursor().getType() != Material.AIR) {
                                    player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie mozesz tego aktualnie osadzic!"));
                                    player.sendMessage(Utils.format("&8Zdejmij przedmiot z kursora!"));
                                    return;
                                }
                                if (!player.getInventory().containsAtLeast(GornikItems.getItem("S2", 1), 5) || user.getKasa() < 25000000) {
                                    player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie posadasz wszystkich potrzebnych skladnikow"));
                                    return;
                                }
                                player.getInventory().removeItem(GornikItems.getItem("S2", 5));
                                user.setKasa(user.getKasa() - 25000000);
                                Utils.setTagString(playerItem, "Powietrza", "true");
                                prefix = "&7";
                                break;
                            }
                            if (powietrza.equals("true")) {
                                if (e.getCursor() == null || e.getCursor().getType() == Material.AIR || e.getCursor().getAmount() != 1) return;
                                if (e.getCursor().equals(GornikItems.getItem("C2", 1))) {
                                    e.setCursor(null);
                                    Utils.setTagString(playerItem, "Powietrza", "Czysty");
                                    Utils.setTagInt(playerItem, "Powietrza-Bonus", 5);
                                    prefix = "&f";
                                    break;
                                }
                                if (e.getCursor().equals(GornikItems.getItem("W2", 1))) {
                                    e.setCursor(null);
                                    Utils.setTagString(playerItem, "Powietrza", "Wypolerowany");
                                    Utils.setTagInt(playerItem, "Powietrza-Bonus", 25);
                                    prefix = "&f&l";
                                    break;
                                }
                                player.sendMessage(Utils.format("&6&lGornik &8>> &7To miejsce jest przeznaczone na inny &5Krysztal&7!"));
                                return;
                            }
                            if (powietrza.equals("Czysty")) {
                                if (e.getCursor() != null && e.getCursor().getType() != Material.AIR) {
                                    player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie mozesz tego aktualnie osadzic!"));
                                    player.sendMessage(Utils.format("&8Zdejmij przedmiot z kursora!"));
                                    return;
                                }
                                if (!ChanceHelper.getChance(50)) {
                                    player.sendMessage(Utils.format("&6&lGornik &8>> &7Niestety twoj &5Krysztal &7doznal powaznych uszkodzen pod czas wyjmowania i zostal zniszczony"));
                                } else {
                                    player.sendMessage(Utils.format("&6&lGornik &8>> &7Pomyslnie wyjmowalem twoj &5Krysztal"));
                                    player.getInventory().addItem(GornikItems.getItem("C2", 1));
                                }
                            }
                            if (powietrza.equals("Wypolerowany")) {
                                if (e.getCursor() != null && e.getCursor().getType() != Material.AIR) {
                                    player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie mozesz tego aktualnie osadzic!"));
                                    player.sendMessage(Utils.format("&8Zdejmij przedmiot z kursora!"));
                                    return;
                                }
                                if (!ChanceHelper.getChance(60)) {
                                    player.sendMessage(Utils.format("&6&lGornik &8>> &7Niestety twoj &5Krysztal &7doznal powaznych uszkodzen pod czas wyjmowania i zostal zniszczony"));
                                } else {
                                    player.sendMessage(Utils.format("&6&lGornik &8>> &7Pomyslnie wyjmowalem twoj &5Krysztal"));
                                    player.getInventory().addItem(GornikItems.getItem("W2", 1));
                                }
                            }
                            Utils.setTagString(playerItem, "Powietrza", "true");
                            Utils.setTagInt(playerItem, "Powietrza-Bonus", 0);
                            prefix = "&7";
                            break;
                        }
                        final String lasu = Utils.getTagString(playerItem, "Lasu");
                        if (lasu.equals("false")) {
                            if (e.getCursor() != null && e.getCursor().getType() != Material.AIR) {
                                player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie mozesz tego aktualnie osadzic!"));
                                player.sendMessage(Utils.format("&8Zdejmij przedmiot z kursora!"));
                                return;
                            }
                            if (!player.getInventory().containsAtLeast(GornikItems.getItem("S5", 1), 5) || user.getKasa() < 25000000) {
                                player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie posadasz wszystkich potrzebnych skladnikow"));
                                return;
                            }
                            player.getInventory().removeItem(GornikItems.getItem("S5", 5));
                            user.setKasa(user.getKasa() - 25000000);
                            Utils.setTagString(playerItem, "Lasu", "true");
                            prefix = "&7";
                            break;
                        }
                        if (lasu.equals("true")) {
                            if (e.getCursor() == null || e.getCursor().getType() == Material.AIR || e.getCursor().getAmount() != 1) return;
                            if (e.getCursor().equals(GornikItems.getItem("C5", 1))) {
                                e.setCursor(null);
                                Utils.setTagString(playerItem, "Lasu", "Czysty");
                                Utils.setTagDouble(playerItem, "Lasu-Bonus", 2);
                                prefix = "&2";
                                break;
                            }
                            if (e.getCursor().equals(GornikItems.getItem("W5", 1))) {
                                e.setCursor(null);
                                Utils.setTagString(playerItem, "Lasu", "Wypolerowany");
                                Utils.setTagDouble(playerItem, "Lasu-Bonus", 5);
                                prefix = "&2&l";
                                break;
                            }
                            player.sendMessage(Utils.format("&6&lGornik &8>> &7To miejsce jest przeznaczone na inny &5Krysztal&7!"));
                            return;
                        }
                        if (lasu.equals("Czysty")) {
                            if (e.getCursor() != null && e.getCursor().getType() != Material.AIR) {
                                player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie mozesz tego aktualnie osadzic!"));
                                player.sendMessage(Utils.format("&8Zdejmij przedmiot z kursora!"));
                                return;
                            }
                            if (!ChanceHelper.getChance(50)) {
                                player.sendMessage(Utils.format("&6&lGornik &8>> &7Niestety twoj &5Krysztal &7doznal powaznych uszkodzen pod czas wyjmowania i zostal zniszczony"));
                            } else {
                                player.sendMessage(Utils.format("&6&lGornik &8>> &7Pomyslnie wyjmowalem twoj &5Krysztal"));
                                player.getInventory().addItem(GornikItems.getItem("C5", 1));
                            }
                        }
                        if (lasu.equals("Wypolerowany")) {
                            if (e.getCursor() != null && e.getCursor().getType() != Material.AIR) {
                                player.sendMessage(Utils.format("&6&lGornik &8>> &7Nie mozesz tego aktualnie osadzic!"));
                                player.sendMessage(Utils.format("&8Zdejmij przedmiot z kursora!"));
                                return;
                            }
                            if (!ChanceHelper.getChance(60)) {
                                player.sendMessage(Utils.format("&6&lGornik &8>> &7Niestety twoj &5Krysztal &7doznal powaznych uszkodzen pod czas wyjmowania i zostal zniszczony"));
                            } else {
                                player.sendMessage(Utils.format("&6&lGornik &8>> &7Pomyslnie wyjmowalem twoj &5Krysztal"));
                                player.getInventory().addItem(GornikItems.getItem("W5", 1));
                            }
                        }
                        Utils.setTagString(playerItem, "Lasu", "true");
                        Utils.setTagDouble(playerItem, "Lasu-Bonus", 0);
                        prefix = "&7";
                        break;
                    default:
                        return;
                }
            }
            this.updateLore(playerItem, slotKrysztalu, prefix);
            RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataUser(uuid, user));
            clickedInventory.setItem(13, null);
            RPGCORE.getInstance().getGornikNPC().openOsadzanieKrysztalow(player, playerItem);
            return;
        }

        if (title.equals("Pomocnik Gornika")) {
            e.setCancelled(true);
        }
    }

    private void updateLore(final ItemStack is, final int slot, final String prefix) {
        final ItemMeta im = is.getItemMeta();
        if (!Utils.removeColor(im.getDisplayName()).contains("♦")) {
            System.out.println("Dodano!");
            im.setDisplayName(im.getDisplayName() + Utils.format(" &8♦&8♦&8♦"));
        }
        String displayName = im.getDisplayName().substring(0, im.getDisplayName().lastIndexOf(" "));
        String[] prefixes = im.getDisplayName().substring(im.getDisplayName().lastIndexOf(" ") + 1).split("♦");
        prefixes[slot] = prefix;
        im.setDisplayName(Utils.format(displayName + " " + prefixes[0] + "♦" + prefixes[1] + "♦" + prefixes[2] + "♦"));
        is.setItemMeta(im);
    }
}
