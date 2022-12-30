package rpg.rpgcore.npc.itemshop.events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.bonuses.Bonuses;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

public class ItemShopInteractListener implements Listener {
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

            if (user.getRankPlayerUser().getRankType().getPriority() != 0) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&cAktualnie posiadasz aktywowana range."));
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
        if (eventItem.getType() == Material.LEATHER && eventItem.getItemMeta().hasDisplayName() && Utils.removeColor(eventItem.getItemMeta().getDisplayName()).contains("Przeklety Smoczy Zwoj")) {
            final int range = Utils.getTagInt(eventItem, "range");

            for (Entity entity : player.getNearbyEntities(range/2.0, range/2.0, range/2.0)) {
                if (entity instanceof Creature) {
                    final Creature creature = (Creature) entity;
                    creature.setTarget(player);
                }
            }
        }
    }
}
