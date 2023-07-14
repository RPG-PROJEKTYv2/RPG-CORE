package rpg.rpgcore.npc.handlarz.events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.bonuses.Bonuses;
import rpg.rpgcore.klasy.enums.KlasyMain;
import rpg.rpgcore.klasy.enums.KlasySide;
import rpg.rpgcore.klasy.objects.Klasa;
import rpg.rpgcore.ranks.types.RankTypePlayer;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.DoubleUtils;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.Objects;
import java.util.UUID;

public class HandlarzInteractListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onInteract(final PlayerInteractEvent e) {

        final ItemStack eventItem = e.getItem();
        final Player player = e.getPlayer();
        final UUID uuid = player.getUniqueId();

        if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
            return;
        }

        if (eventItem == null) {
            return;
        }

        if (eventItem.getType() == Material.BOOK && eventItem.getItemMeta().hasDisplayName() && Utils.removeColor(eventItem.getItemMeta().getDisplayName()).contains("Voucher na range")) {
            final User user = RPGCORE.getInstance().getUserManager().find(uuid);

            if (user.getRankPlayerUser().getRankType().getPriority() > Objects.requireNonNull(RankTypePlayer.getByName(Utils.getTagString(eventItem, "rank"))).getPriority()) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&cUzyj tego voucher, kiedy czas twojej rangi dobiegnie konca."));
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&cCzas swojej rangi mozesz sprawdzic pod /ranktime."));
                return;
            }
            player.getInventory().removeItem(new ItemBuilder(eventItem.clone()).setAmount(1).toItemStack());
            final String rank = Utils.getTagString(eventItem, "rank");
            final String time = Utils.getTagString(eventItem, "time");
            RPGCORE.getInstance().getNmsManager().sendTitleAndSubTitle(player, RPGCORE.getInstance().getNmsManager().makeTitle("&8&l[&6&lItem&2&lShop&8&l]", 5, 20, 5),
                    RPGCORE.getInstance().getNmsManager().makeSubTitle("&aPomyslnie otrzymales range " + rank.replace("Vip", Utils.format("&e&lVip"))
                            .replace("Svip", Utils.format("&6&lS&e&lvip")).replace("Elita", Utils.format("&5&lELITA")) + " na okres &6" + time.replace("d", " dni"), 5, 20, 5));
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "setpremium " + player.getName() + " " + rank + " " + time + " false");
            return;
        }
        if (eventItem.getType() == Material.GHAST_TEAR && eventItem.getItemMeta().hasDisplayName() && Utils.removeColor(eventItem.getItemMeta().getDisplayName()).contains("Pierscien Doswiadczenia")) {
            final User user = RPGCORE.getInstance().getUserManager().find(uuid);

            if (user.getPierscienDoswiadczenia() != 0) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&cPosiadasz juz aktywowany pierscien doswiadczenia."));
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&cUzyj tego pierscienia, kiedy czas poprzedniego pierscienia dobiegnie konca."));
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&cCzas swojego pierscienia mozesz sprawdzic pod /pd."));
                return;
            }
            player.getInventory().removeItem(new ItemBuilder(eventItem.clone()).setAmount(1).toItemStack());
            final String time = Utils.getTagString(eventItem, "time");
            final int exp = Utils.getTagInt(eventItem, "exp");
            user.setPierscienDoswiadczenia(exp);
            user.setPierscienDoswiadczeniaTime(System.currentTimeMillis() + Utils.durationFromString(time, false));
            final Bonuses bonuses = RPGCORE.getInstance().getBonusesManager().find(uuid);
            bonuses.getBonusesUser().setDodatkowyExp(bonuses.getBonusesUser().getDodatkowyExp() + exp);
            RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> {
                RPGCORE.getInstance().getMongoManager().saveDataBonuses(uuid, bonuses);
                RPGCORE.getInstance().getMongoManager().saveDataUser(uuid, user);
            });
        }
        if (eventItem.getType() == Material.PAPER && eventItem.getItemMeta().hasDisplayName() && Utils.removeColor(eventItem.getItemMeta().getDisplayName()).contains("Reset Klasy")) {
            final Klasa klasa = RPGCORE.getInstance().getKlasyManager().find(uuid);
            final User user = RPGCORE.getInstance().getUserManager().find(uuid);

            if (klasa.getMainKlasa() == KlasyMain.NIE_WYBRANO) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie posiadasz wybranej klasy."));
                return;
            }

            if (user.getKasa() < 5_000_000) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie posiadasz wystarczajacej ilosci pieniedzy &8(&65 000 000&2$&8)."));
                return;
            }
            user.setKasa(DoubleUtils.round(user.getKasa() - 5_000_000, 2));
            player.getInventory().removeItem(new ItemBuilder(eventItem.clone()).setAmount(1).toItemStack());
            klasa.setMainKlasa(KlasyMain.NIE_WYBRANO);
            klasa.setPodKlasa(KlasySide.NIE_WYBRANO);
            klasa.setCdLMB(0L);
            klasa.setCdRMB(0L);
            klasa.setUpgrade(0);
            klasa.setBonus1(0);
            klasa.setBonus2(0);
            klasa.save();
            RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataUser(uuid, user));
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie zresetowales/-as swoja klase."));
        }
    }
}
