package rpg.rpgcore.npc.gornik.events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.artefakty.Artefakty;
import rpg.rpgcore.bonuses.Bonuses;
import rpg.rpgcore.npc.gornik.enums.GornikMissions;
import rpg.rpgcore.npc.gornik.objects.GornikUser;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.DoubleUtils;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.GlobalItem;
import rpg.rpgcore.utils.globalitems.npc.GornikItems;

import java.util.UUID;

public class GornikInventoryClickListener implements Listener {
    private final RPGCORE rpgcore;

    public GornikInventoryClickListener(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }


    @EventHandler(priority = EventPriority.LOWEST)
    public void onClick(final InventoryClickEvent e) {
        if (e.getClickedInventory() == null) {
            return;
        }

        final Player player = (Player) e.getWhoClicked();
        final UUID uuid = player.getUniqueId();
        final String title = Utils.removeColor(e.getClickedInventory().getTitle());
        final ItemStack item = e.getCurrentItem();
        final int slot = e.getSlot();


        if (title.equals("Gornik")) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
            switch (slot) {
                case 10:
                    rpgcore.getGornikNPC().openKampania(player);
                    return;
                case 13:
                    rpgcore.getGornikNPC().openSklep(player);
                    return;
                case 16:
                    rpgcore.getGornikNPC().openCraftingi(player);
                    return;
            }
        }

        if (title.equals("Gornik » Kampania")) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);

            if (item == null || item.getType() == Material.AIR || item.getType() == Material.STAINED_GLASS_PANE) return;
            if (!item.hasItemMeta()) return;
            if (item.getItemMeta().hasItemFlag(ItemFlag.HIDE_ATTRIBUTES)) return;

            final GornikUser user = rpgcore.getGornikNPC().find(uuid);
            final GornikMissions mission = GornikMissions.getById(user.getMission());

            if (user.getProgress() >= mission.getReqAmount()) {
                final Bonuses bonuses = rpgcore.getBonusesManager().find(uuid);
                user.setProgress(0);
                user.setMission(user.getMission() + 1);
                user.setDefNaMoby(user.getDefNaMoby() + mission.getDefNaMoby());
                user.setSilnyNaMoby(user.getSilnyNaMoby() + mission.getSilnyNaLudzi());
                user.setMaxTimeLeft(user.getMaxTimeLeft() + mission.getBonusTime());
                bonuses.getBonusesUser().setSilnynapotwory(bonuses.getBonusesUser().getSilnynapotwory() + mission.getSilnyNaLudzi());
                bonuses.getBonusesUser().setDefnamoby(bonuses.getBonusesUser().getDefnamoby() + mission.getDefNaMoby());
                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
                    rpgcore.getMongoManager().saveDataGornik(uuid, user);
                    rpgcore.getMongoManager().saveDataBonuses(uuid, bonuses);
                });
                player.closeInventory();
                if (mission.getId() == 28) {
                    Bukkit.getServer().broadcastMessage(Utils.format(""));
                    Bukkit.getServer().broadcastMessage(Utils.format("&6&lGornik &8>> &7Gracz &e" + player.getName() + " &7ukonczyl moja &4&lKAMPANIE"));
                    if (rpgcore.getArtefaktyZaLvlManager().getArtefaktyZaLvl().getGornik().getNadanych() < 4 && !rpgcore.getArtefaktyZaLvlManager().getArtefaktyZaLvl().getGornik().getGracze().contains(player.getName())) {
                        rpgcore.getArtefaktyZaLvlManager().getArtefaktyZaLvl().getGornik().getGracze().add(player.getName());
                        rpgcore.getArtefaktyZaLvlManager().getArtefaktyZaLvl().getGornik().setNadanych(rpgcore.getArtefaktyZaLvlManager().getArtefaktyZaLvl().getGornik().getNadanych() + 1);
                        rpgcore.getArtefaktyZaLvlManager().save();
                        player.getInventory().addItem(Artefakty.getArtefakt("Eliksir-Obroncy", player));
                        Bukkit.getServer().broadcastMessage(Utils.format("&6&lGornik &8>> &7Poniewaz ukonczyl ja, jako &c" + rpgcore.getArtefaktyZaLvlManager().getArtefaktyZaLvl().getGornik().getNadanych() + " &7na serwerze, otrzymal &6&lEliksir Obroncy&7!"));
                    }
                    Bukkit.getServer().broadcastMessage(Utils.format(""));
                    return;
                }
                Bukkit.getServer().broadcastMessage(Utils.format("&6&lGornik &8>> &7Gracz &e" + player.getName() + " &7ukonczyl moja &e" + mission.getId() + " &7misje!"));
                return;
            }

            if (user.getMission() == 5 || user.getMission() == 15 || user.getMission() == 18 || user.getMission() == 24) {
                if (player.getItemInHand() == null) {
                    player.sendMessage(Utils.format("&6&lGornika &8>> &cMusisz trzymac w rece &6Kilof Gornika&c!"));
                    return;
                }
                if (!player.getItemInHand().getItemMeta().getDisplayName().contains("Kilof Gornika")) {
                    player.sendMessage(Utils.format("&6&lGornika &8>> &cMusisz trzymac w rece &6Kilof Gornika&c!"));
                    return;
                }
                if (!Utils.getTagString(player.getItemInHand(), "owner").equals(player.getName())) {
                    player.sendMessage(Utils.format("&6&lGornika &8>> &cTo nie jest twoj kilof&c!"));
                    return;
                }
                if (!Utils.getTagString(player.getItemInHand(), "owner-uuid").equals(player.getUniqueId().toString())) {
                    player.sendMessage(Utils.format("&6&lGornika &8>> &cTo nie jest twoj kilof&c!"));
                    return;
                }
                final int reqLvl = Utils.getTagInt(item, "reqPickaxeLvl");

                if (reqLvl == 0) {
                    player.sendMessage(Utils.format("&6&lGornik &8>> &cCos poszlo nie tak! Zglos sie do Administracji z tym bledem!"));
                    return;
                }

                if (Utils.getTagInt(player.getItemInHand(), "lvl") < reqLvl) return;
                user.setProgress(1);
                return;
            }

            if (slot == 49) {
                rpgcore.getGornikNPC().openGornik(player);
                return;
            }
            return;
        }

        if (title.equals("Gornik » Sklep Gorniczy")) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);

            final User user = rpgcore.getUserManager().find(uuid);

            final int price = Utils.getTagInt(item, "price");
            switch (slot) {
                case 0:
                    if (user.getKasa() < price) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Czy ty chcesz mnie oszukac?"));
                        player.sendMessage(Utils.format("&6&lGornik &8>> &cBrakuje Ci jeszcze &e" + (price - user.getKasa()) + "&2$ &7aby kupic ten przedmiot!"));
                        player.closeInventory();
                        return;
                    }

                    user.setKasa(user.getKasa() - price);
                    player.sendMessage(Utils.format("&6&lGornik &8>> &7Trzymaj, oby Ci dobrze sluzyl!"));
                    player.getInventory().addItem(GornikItems.getKilof(player).clone());
                    player.closeInventory();
                    rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataUser(user.getId(), user));
                    break;
                case 1:
                    if (!player.getInventory().containsAtLeast(GornikItems.I1.getItemStack(), 1)) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &cNie posiadasz tego przedmiotu!"));
                        return;
                    }
                    player.getInventory().removeItem(new ItemBuilder(GornikItems.I1.getItemStack().clone()).setAmount(1).toItemStack());
                    user.setKasa(DoubleUtils.round(user.getKasa() + price, 2));
                    player.sendMessage(Utils.format("&6&lGornik &8>> &7Sprzedales przedmiot za &e" + price + "&2$&7!"));
                    rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataUser(user.getId(), user));
                    break;
                case 2:
                    if (!player.getInventory().containsAtLeast(GornikItems.I2.getItemStack(), 1)) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &cNie posiadasz tego przedmiotu!"));
                        return;
                    }
                    player.getInventory().removeItem(new ItemBuilder(GornikItems.I2.getItemStack().clone()).setAmount(1).toItemStack());
                    user.setKasa(DoubleUtils.round(user.getKasa() + price, 2));
                    player.sendMessage(Utils.format("&6&lGornik &8>> &7Sprzedales przedmiot za &e" + price + "&2$&7!"));
                    rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataUser(user.getId(), user));
                    break;
                case 3:
                    if (!player.getInventory().containsAtLeast(GornikItems.I3.getItemStack(), 1)) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &cNie posiadasz tego przedmiotu!"));
                        return;
                    }
                    player.getInventory().removeItem(new ItemBuilder(GornikItems.I3.getItemStack().clone()).setAmount(1).toItemStack());
                    user.setKasa(DoubleUtils.round(user.getKasa() + price, 2));
                    player.sendMessage(Utils.format("&6&lGornik &8>> &7Sprzedales przedmiot za &e" + price + "&2$&7!"));
                    rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataUser(user.getId(), user));
                    break;
                case 4:
                    if (!player.getInventory().containsAtLeast(GornikItems.I4.getItemStack(), 1)) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &cNie posiadasz tego przedmiotu!"));
                        return;
                    }
                    player.getInventory().removeItem(new ItemBuilder(GornikItems.I4.getItemStack().clone()).setAmount(1).toItemStack());
                    user.setKasa(DoubleUtils.round(user.getKasa() + price, 2));
                    player.sendMessage(Utils.format("&6&lGornik &8>> &7Sprzedales przedmiot za &e" + price + "&2$&7!"));
                    rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataUser(user.getId(), user));
                    break;
                case 5:
                    if (!player.getInventory().containsAtLeast(GornikItems.I5.getItemStack(), 1)) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &cNie posiadasz tego przedmiotu!"));
                        return;
                    }
                    player.getInventory().removeItem(new ItemBuilder(GornikItems.I5.getItemStack().clone()).setAmount(1).toItemStack());
                    user.setKasa(DoubleUtils.round(user.getKasa() + price, 2));
                    player.sendMessage(Utils.format("&6&lGornik &8>> &7Sprzedales przedmiot za &e" + price + "&2$&7!"));
                    rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataUser(user.getId(), user));
                    break;
                case 6:
                    if (!player.getInventory().containsAtLeast(GornikItems.I6.getItemStack(), 1)) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &cNie posiadasz tego przedmiotu!"));
                        return;
                    }
                    player.getInventory().removeItem(new ItemBuilder(GornikItems.I6.getItemStack().clone()).setAmount(1).toItemStack());
                    user.setKasa(DoubleUtils.round(user.getKasa() + price, 2));
                    player.sendMessage(Utils.format("&6&lGornik &8>> &7Sprzedales przedmiot za &e" + price + "&2$&7!"));
                    rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataUser(user.getId(), user));
                    break;
                case 7:
                    if (!player.getInventory().containsAtLeast(GornikItems.I7.getItemStack(), 1)) {
                        player.sendMessage(Utils.format("&6&lGornik &8>> &cNie posiadasz tego przedmiotu!"));
                        return;
                    }
                    player.getInventory().removeItem(new ItemBuilder(GornikItems.I7.getItemStack().clone()).setAmount(1).toItemStack());
                    user.setKasa(DoubleUtils.round(user.getKasa() + price, 2));
                    player.sendMessage(Utils.format("&6&lGornik &8>> &7Sprzedales przedmiot za &e" + price + "&2$&7!"));
                    rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataUser(user.getId(), user));
                    break;
                case 8:
                    rpgcore.getGornikNPC().openGornik(player);
                    break;
                default:
                    return;
            }
            return;
        }

        if (title.equals("Gornik » Craftingi Gornicze")) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
            if (item == null || item.getType() == Material.STAINED_GLASS_PANE) return;
            if (slot == 26) {
                rpgcore.getGornikNPC().openGornik(player);
                return;
            }
            final User user = rpgcore.getUserManager().find(uuid);
            switch (slot) {
                case 1:
                    if (!player.getInventory().containsAtLeast(GornikItems.I1.getItemStack(), 64)) return;
                    if (user.getKasa() < 50_000) return;
                    player.getInventory().removeItem(new ItemBuilder(GornikItems.I1.getItemStack().clone()).setAmount(64).toItemStack());
                    user.setKasa(user.getKasa() - 50_000);
                    player.getInventory().addItem(GornikItems.I1_1.getItemStack().clone());
                    break;
                case 2:
                    if (!player.getInventory().containsAtLeast(GornikItems.I2.getItemStack(), 64)) return;
                    if (user.getKasa() < 55_000) return;
                    player.getInventory().removeItem(new ItemBuilder(GornikItems.I2.getItemStack().clone()).setAmount(64).toItemStack());
                    user.setKasa(user.getKasa() - 55_000);
                    player.getInventory().addItem(GornikItems.I2_1.getItemStack().clone());
                    break;
                case 3:
                    if (!player.getInventory().containsAtLeast(GornikItems.I3.getItemStack(), 64)) return;
                    if (user.getKasa() < 60_000) return;
                    player.getInventory().removeItem(new ItemBuilder(GornikItems.I3.getItemStack().clone()).setAmount(64).toItemStack());
                    user.setKasa(user.getKasa() - 60_000);
                    player.getInventory().addItem(GornikItems.I3_1.getItemStack().clone());
                    break;
                case 4:
                    if (!player.getInventory().containsAtLeast(GornikItems.I4.getItemStack(), 64)) return;
                    if (user.getKasa() < 65_000) return;
                    player.getInventory().removeItem(new ItemBuilder(GornikItems.I4.getItemStack().clone()).setAmount(64).toItemStack());
                    user.setKasa(user.getKasa() - 65_000);
                    player.getInventory().addItem(GornikItems.I4_1.getItemStack().clone());
                    break;
                case 5:
                    if (!player.getInventory().containsAtLeast(GornikItems.I5.getItemStack(), 64)) return;
                    if (user.getKasa() < 70_000) return;
                    player.getInventory().removeItem(new ItemBuilder(GornikItems.I5.getItemStack().clone()).setAmount(64).toItemStack());
                    user.setKasa(user.getKasa() - 70_000);
                    player.getInventory().addItem(GornikItems.I5_1.getItemStack().clone());
                    break;
                case 6:
                    if (!player.getInventory().containsAtLeast(GornikItems.I6.getItemStack(), 64)) return;
                    if (user.getKasa() < 75_000) return;
                    player.getInventory().removeItem(new ItemBuilder(GornikItems.I6.getItemStack().clone()).setAmount(64).toItemStack());
                    user.setKasa(user.getKasa() - 75_000);
                    player.getInventory().addItem(GornikItems.I6_1.getItemStack().clone());
                    break;
                case 7:
                    if (!player.getInventory().containsAtLeast(GornikItems.I7.getItemStack(), 64)) return;
                    if (user.getKasa() < 80_000) return;
                    player.getInventory().removeItem(new ItemBuilder(GornikItems.I7.getItemStack().clone()).setAmount(64).toItemStack());
                    user.setKasa(user.getKasa() - 80_000);
                    player.getInventory().addItem(GornikItems.I7_1.getItemStack().clone());
                    break;
                case 10:
                    if (!player.getInventory().containsAtLeast(GornikItems.I1_1.getItemStack(), 10) ||
                            !player.getInventory().containsAtLeast(GornikItems.I2_1.getItemStack(), 10) ||
                            !player.getInventory().containsAtLeast(GornikItems.I3_1.getItemStack(), 10) ||
                            !player.getInventory().containsAtLeast(GornikItems.I4_1.getItemStack(), 10) ||
                            !player.getInventory().containsAtLeast(GornikItems.I5_1.getItemStack(), 10) ||
                            !player.getInventory().containsAtLeast(GornikItems.I6_1.getItemStack(), 10) ||
                            !player.getInventory().containsAtLeast(GornikItems.I7_1.getItemStack(), 10)) return;
                    if (user.getKasa() < 35_000_000) return;
                    player.getInventory().removeItem(new ItemBuilder(GornikItems.I1_1.getItemStack().clone()).setAmount(10).toItemStack(),
                            new ItemBuilder(GornikItems.I2_1.getItemStack().clone()).setAmount(10).toItemStack(),
                            new ItemBuilder(GornikItems.I3_1.getItemStack().clone()).setAmount(10).toItemStack(),
                            new ItemBuilder(GornikItems.I4_1.getItemStack().clone()).setAmount(10).toItemStack(),
                            new ItemBuilder(GornikItems.I5_1.getItemStack().clone()).setAmount(10).toItemStack(),
                            new ItemBuilder(GornikItems.I6_1.getItemStack().clone()).setAmount(10).toItemStack(),
                            new ItemBuilder(GornikItems.I7_1.getItemStack().clone()).setAmount(10).toItemStack());
                    user.setKasa(user.getKasa() - 35_000_000);
                    player.getInventory().addItem(GornikItems.I9.getItemStack().clone());
                    break;
                case 19:
                    if (!player.getInventory().containsAtLeast(GornikItems.I1_1.getItemStack(), 24) ||
                            !player.getInventory().containsAtLeast(GornikItems.I2_1.getItemStack(), 24) ||
                            !player.getInventory().containsAtLeast(GornikItems.I3_1.getItemStack(), 24) ||
                            !player.getInventory().containsAtLeast(GornikItems.I4_1.getItemStack(), 24) ||
                            !player.getInventory().containsAtLeast(GornikItems.I5_1.getItemStack(), 24) ||
                            !player.getInventory().containsAtLeast(GornikItems.I6_1.getItemStack(), 24) ||
                            !player.getInventory().containsAtLeast(GornikItems.I7_1.getItemStack(), 24)) return;
                    if (user.getKasa() < 75_000_000) return;
                    player.getInventory().removeItem(new ItemBuilder(GornikItems.I1_1.getItemStack().clone()).setAmount(24).toItemStack(),
                            new ItemBuilder(GornikItems.I2_1.getItemStack().clone()).setAmount(24).toItemStack(),
                            new ItemBuilder(GornikItems.I3_1.getItemStack().clone()).setAmount(24).toItemStack(),
                            new ItemBuilder(GornikItems.I4_1.getItemStack().clone()).setAmount(24).toItemStack(),
                            new ItemBuilder(GornikItems.I5_1.getItemStack().clone()).setAmount(24).toItemStack(),
                            new ItemBuilder(GornikItems.I6_1.getItemStack().clone()).setAmount(24).toItemStack(),
                            new ItemBuilder(GornikItems.I7_1.getItemStack().clone()).setAmount(24).toItemStack());
                    user.setKasa(user.getKasa() - 75_000_000);
                    player.getInventory().addItem(GornikItems.I10.getItemStack().clone());
                    break;
                case 11:
                    if (!player.getInventory().containsAtLeast(GornikItems.I1_1.getItemStack(), 16) ||
                            !player.getInventory().containsAtLeast(GornikItems.I2_1.getItemStack(), 16) ||
                            !player.getInventory().containsAtLeast(GornikItems.I3_1.getItemStack(), 16) ||
                            !player.getInventory().containsAtLeast(GornikItems.I4_1.getItemStack(), 16) ||
                            !player.getInventory().containsAtLeast(GornikItems.I5_1.getItemStack(), 16) ||
                            !player.getInventory().containsAtLeast(GornikItems.I6_1.getItemStack(), 16) ||
                            !player.getInventory().containsAtLeast(GornikItems.I7_1.getItemStack(), 16)) return;
                    if (user.getKasa() < 50_000_000) return;
                    player.getInventory().removeItem(new ItemBuilder(GornikItems.I1_1.getItemStack().clone()).setAmount(16).toItemStack(),
                            new ItemBuilder(GornikItems.I2_1.getItemStack().clone()).setAmount(16).toItemStack(),
                            new ItemBuilder(GornikItems.I3_1.getItemStack().clone()).setAmount(16).toItemStack(),
                            new ItemBuilder(GornikItems.I4_1.getItemStack().clone()).setAmount(16).toItemStack(),
                            new ItemBuilder(GornikItems.I5_1.getItemStack().clone()).setAmount(16).toItemStack(),
                            new ItemBuilder(GornikItems.I6_1.getItemStack().clone()).setAmount(16).toItemStack(),
                            new ItemBuilder(GornikItems.I7_1.getItemStack().clone()).setAmount(16).toItemStack());
                    user.setKasa(user.getKasa() - 50_000_000);
                    player.getInventory().addItem(GornikItems.I11.getItemStack().clone());
                    break;
                case 20:
                    if (!player.getInventory().containsAtLeast(GornikItems.I1_1.getItemStack(), 8) ||
                            !player.getInventory().containsAtLeast(GornikItems.I2_1.getItemStack(), 8) ||
                            !player.getInventory().containsAtLeast(GornikItems.I3_1.getItemStack(), 8) ||
                            !player.getInventory().containsAtLeast(GornikItems.I4_1.getItemStack(), 8) ||
                            !player.getInventory().containsAtLeast(GornikItems.I5_1.getItemStack(), 8) ||
                            !player.getInventory().containsAtLeast(GornikItems.I6_1.getItemStack(), 8) ||
                            !player.getInventory().containsAtLeast(GornikItems.I7_1.getItemStack(), 8)) return;
                    if (user.getKasa() < 25_000_000) return;
                    player.getInventory().removeItem(new ItemBuilder(GornikItems.I1_1.getItemStack().clone()).setAmount(8).toItemStack(),
                            new ItemBuilder(GornikItems.I2_1.getItemStack().clone()).setAmount(8).toItemStack(),
                            new ItemBuilder(GornikItems.I3_1.getItemStack().clone()).setAmount(8).toItemStack(),
                            new ItemBuilder(GornikItems.I4_1.getItemStack().clone()).setAmount(8).toItemStack(),
                            new ItemBuilder(GornikItems.I5_1.getItemStack().clone()).setAmount(8).toItemStack(),
                            new ItemBuilder(GornikItems.I6_1.getItemStack().clone()).setAmount(8).toItemStack(),
                            new ItemBuilder(GornikItems.I7_1.getItemStack().clone()).setAmount(8).toItemStack());
                    user.setKasa(user.getKasa() - 25_000_000);
                    player.getInventory().addItem(GornikItems.I12.getItemStack().clone());
                    break;
                case 13:
                    if (!player.getInventory().containsAtLeast(GornikItems.I3_1.getItemStack(), 4) ||
                            !player.getInventory().containsAtLeast(GornikItems.I4_1.getItemStack(), 3) ||
                            !player.getInventory().containsAtLeast(GornikItems.I6_1.getItemStack(), 2) ||
                            !player.getInventory().containsAtLeast(GornikItems.I7_1.getItemStack(), 2)) return;
                    if (user.getKasa() < 4_000_000) return;
                    player.getInventory().removeItem(new ItemBuilder(GornikItems.I3_1.getItemStack().clone()).setAmount(4).toItemStack(),
                            new ItemBuilder(GornikItems.I4_1.getItemStack().clone()).setAmount(3).toItemStack(),
                            new ItemBuilder(GornikItems.I6_1.getItemStack().clone()).setAmount(2).toItemStack(),
                            new ItemBuilder(GornikItems.I7_1.getItemStack().clone()).setAmount(2).toItemStack());
                    user.setKasa(user.getKasa() - 4_000_000);
                    player.getInventory().addItem(GlobalItem.I_KAMIENBAO.getItemStack().clone());
                    break;
                case 22:
                    if (!player.getInventory().containsAtLeast(GornikItems.I1_1.getItemStack(), 3) ||
                            !player.getInventory().containsAtLeast(GornikItems.I2_1.getItemStack(), 2) ||
                            !player.getInventory().containsAtLeast(GornikItems.I5_1.getItemStack(), 1)) return;
                    if (user.getKasa() < 1_000_000) return;
                    player.getInventory().removeItem(new ItemBuilder(GornikItems.I1_1.getItemStack().clone()).setAmount(3).toItemStack(),
                            new ItemBuilder(GornikItems.I2_1.getItemStack().clone()).setAmount(2).toItemStack(),
                            new ItemBuilder(GornikItems.I5_1.getItemStack().clone()).setAmount(1).toItemStack());
                    user.setKasa(user.getKasa() - 1_000_000);
                    player.getInventory().addItem(GlobalItem.I_METAL.getItemStack().clone());
                    break;
                case 15:
                    if (player.getItemInHand() == null || !player.getItemInHand().getType().toString().contains("_PICKAXE") ||
                            !player.getItemInHand().hasItemMeta() || !player.getItemInHand().getItemMeta().getDisplayName().contains("Kilof Gornika"))
                        return;
                    if (!player.getItemInHand().getType().equals(Material.STONE_PICKAXE)) return;
                    if (Utils.getTagInt(player.getItemInHand(), "lvl") < 10) return;
                    if (!player.getInventory().containsAtLeast(GornikItems.I1_1.getItemStack(), 5) ||
                            !player.getInventory().containsAtLeast(GornikItems.I2_1.getItemStack(), 5)) return;
                    if (user.getKasa() < 10_000_000) return;
                    player.getInventory().removeItem(new ItemBuilder(GornikItems.I1_1.getItemStack().clone()).setAmount(5).toItemStack(),
                            new ItemBuilder(GornikItems.I2_1.getItemStack().clone()).setAmount(5).toItemStack());
                    user.setKasa(user.getKasa() - 10_000_000);
                    player.getItemInHand().setType(Material.IRON_PICKAXE);
                    break;
                case 24:
                    if (player.getItemInHand() == null || !player.getItemInHand().getType().toString().contains("_PICKAXE") ||
                            !player.getItemInHand().hasItemMeta() || !player.getItemInHand().getItemMeta().getDisplayName().contains("Kilof Gornika"))
                        return;
                    if (!player.getItemInHand().getType().equals(Material.IRON_PICKAXE)) return;
                    if (Utils.getTagInt(player.getItemInHand(), "lvl") < 17) return;
                    if (!player.getInventory().containsAtLeast(GornikItems.I1_1.getItemStack(), 10) ||
                            !player.getInventory().containsAtLeast(GornikItems.I2_1.getItemStack(), 10) ||
                            !player.getInventory().containsAtLeast(GornikItems.I3_1.getItemStack(), 5) ||
                            !player.getInventory().containsAtLeast(GornikItems.I4_1.getItemStack(), 5)) return;
                    if (user.getKasa() < 25_000_000) return;
                    player.getInventory().removeItem(new ItemBuilder(GornikItems.I1_1.getItemStack().clone()).setAmount(10).toItemStack(),
                            new ItemBuilder(GornikItems.I2_1.getItemStack().clone()).setAmount(10).toItemStack(),
                            new ItemBuilder(GornikItems.I3_1.getItemStack().clone()).setAmount(5).toItemStack(),
                            new ItemBuilder(GornikItems.I4_1.getItemStack().clone()).setAmount(5).toItemStack());
                    user.setKasa(user.getKasa() - 25_000_000);
                    player.getItemInHand().setType(Material.GOLD_PICKAXE);
                    break;
                case 16:
                    if (player.getItemInHand() == null || !player.getItemInHand().getType().toString().contains("_PICKAXE") ||
                            !player.getItemInHand().hasItemMeta() || !player.getItemInHand().getItemMeta().getDisplayName().contains("Kilof Gornika"))
                        return;
                    if (!player.getItemInHand().getType().equals(Material.GOLD_PICKAXE)) return;
                    if (Utils.getTagInt(player.getItemInHand(), "lvl") < 25) return;
                    if (!player.getInventory().containsAtLeast(GornikItems.I1_1.getItemStack(), 20) ||
                            !player.getInventory().containsAtLeast(GornikItems.I2_1.getItemStack(), 20) ||
                            !player.getInventory().containsAtLeast(GornikItems.I3_1.getItemStack(), 10) ||
                            !player.getInventory().containsAtLeast(GornikItems.I4_1.getItemStack(), 10) ||
                            !player.getInventory().containsAtLeast(GornikItems.I5_1.getItemStack(), 5) ||
                            !player.getInventory().containsAtLeast(GornikItems.I6_1.getItemStack(), 5)) return;
                    if (user.getKasa() < 50_000_000) return;
                    player.getInventory().removeItem(new ItemBuilder(GornikItems.I1_1.getItemStack().clone()).setAmount(20).toItemStack(),
                            new ItemBuilder(GornikItems.I2_1.getItemStack().clone()).setAmount(20).toItemStack(),
                            new ItemBuilder(GornikItems.I3_1.getItemStack().clone()).setAmount(10).toItemStack(),
                            new ItemBuilder(GornikItems.I4_1.getItemStack().clone()).setAmount(10).toItemStack(),
                            new ItemBuilder(GornikItems.I5_1.getItemStack().clone()).setAmount(5).toItemStack(),
                            new ItemBuilder(GornikItems.I6_1.getItemStack().clone()).setAmount(5).toItemStack());
                    user.setKasa(user.getKasa() - 50_000_000);
                    player.getItemInHand().setType(Material.DIAMOND_PICKAXE);
                    break;
            }
            player.sendMessage(Utils.format("&6&lGornik &8>> &aPomyslnie stworzyles " + item.getItemMeta().getDisplayName() + "&a!"));
            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataUser(user.getId(), user));
        }
    }
}
