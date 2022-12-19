package rpg.rpgcore.dodatki.akcesoriaD.events;

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
import rpg.rpgcore.discord.EmbedUtil;
import rpg.rpgcore.dodatki.DodatkiUser;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class AkcesoriaDodatInteractListener implements Listener {
    private final RPGCORE rpgcore;
    private final List<Material> akcesoriaList = Arrays.asList(Material.LADDER, Material.LEASH, Material.FIREBALL, Material.MINECART);

    public AkcesoriaDodatInteractListener(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onClick(final PlayerInteractEvent e) {
        final ItemStack eventItem = e.getItem();
        final Player player = e.getPlayer();
        final UUID uuid = player.getUniqueId();

        if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
            return;
        }


        final int lvl = rpgcore.getUserManager().find(uuid).getLvl();
        final DodatkiUser user = rpgcore.getDodatkiManager().find(uuid);
        final Bonuses bonuses = rpgcore.getBonusesManager().find(uuid);

        if (eventItem == null) {
            return;
        }

        if (!akcesoriaList.contains(eventItem.getType())) {
            return;
        }

        if (!eventItem.getItemMeta().hasDisplayName() || !eventItem.getItemMeta().hasLore()) {
            return;
        }

        if (!eventItem.getItemMeta().getDisplayName().contains("Szarfa") && !eventItem.getItemMeta().getDisplayName().contains("Pas") &&
                !eventItem.getItemMeta().getDisplayName().contains("Medalion")  && !eventItem.getItemMeta().getDisplayName().contains("Energia")) return;

        if (eventItem.getAmount() > 1) {
            player.sendMessage(Utils.format("&8[&c✘&8] &cNie mozesz zalozyc wiecej niz 1 przedmiotu. Rozdziel je!"));
            return;
        }

        if (Utils.getTagInt(eventItem, "lvl") > lvl) {
            player.sendMessage(Utils.format("&8[&c✘&8] &cNie posiadasz wymaganego poziomu, zeby zalozyc ten przedmiot!"));
            return;
        }

        if (eventItem.getType() == Material.LADDER) {
            e.setCancelled(true);

            if (!user.getAkcesoriaDodatkowe().getSzarfa().isEmpty()) {
                player.sendMessage(Utils.format("&8[&c✘&8] &cMasz juz zalozona szarfe!"));
                return;
            }

            final double ludzie = Utils.getTagDouble(eventItem, "ludzie");
            final double moby = Utils.getTagDouble(eventItem, "moby");

            user.getAkcesoriaDodatkowe().setSzarfa(Utils.serializeItem(eventItem));

            player.getInventory().removeItem(new ItemBuilder(eventItem.clone()).setAmount(1).toItemStack());
            player.sendMessage(Utils.format("&8[&a✔&8] &aPomyslnie zalozyles " + eventItem.getItemMeta().getDisplayName() + "&a!"));

            bonuses.getBonusesUser().setSilnynaludzi(bonuses.getBonusesUser().getSilnynaludzi() + ludzie);
            bonuses.getBonusesUser().setSilnynapotwory(bonuses.getBonusesUser().getSilnynapotwory() + moby);

            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
                rpgcore.getMongoManager().saveDataBonuses(uuid, bonuses);
                rpgcore.getMongoManager().saveDataDodatki(uuid, user);
                RPGCORE.getDiscordBot().sendChannelMessage("player-akcesoria-logs", EmbedUtil.create(
                        "**Gracz **`" + player.getName() + "`** zalozyl nowe akcesorium dodatkowe!**",
                        "**Typ: **`" + eventItem.getType() + "`\n"
                                + "**Statystyki:** \n" +
                                "- Silny Na Ludzi: " + ludzie + "\n" +
                                "- Silny Na Potwory: " + moby + "\n" +
                                "- Wymagazyny Poziom: " + Utils.getTagInt(eventItem, "lvl"), Color.getHSBColor(114, 90, 47)));
            });
        }

        if (eventItem.getType() == Material.LEASH) {
            e.setCancelled(true);

            if (!user.getAkcesoriaDodatkowe().getPas().isEmpty()) {
                player.sendMessage(Utils.format("&8[&c✘&8] &cMasz juz zalozony pas!"));
                return;
            }

            final double defLudzie = Utils.getTagDouble(eventItem, "defLudzie");
            final double defMoby = Utils.getTagDouble(eventItem, "defMoby");

            user.getAkcesoriaDodatkowe().setPas(Utils.serializeItem(eventItem));

            player.getInventory().removeItem(new ItemBuilder(eventItem.clone()).setAmount(1).toItemStack());
            player.sendMessage(Utils.format("&8[&a✔&8] &aPomyslnie zalozyles " + eventItem.getItemMeta().getDisplayName() + "&a!"));

            bonuses.getBonusesUser().setDefnaludzi(bonuses.getBonusesUser().getDefnaludzi() + defLudzie);
            bonuses.getBonusesUser().setDefnamoby(bonuses.getBonusesUser().getDefnamoby() + defMoby);

            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
                rpgcore.getMongoManager().saveDataBonuses(uuid, bonuses);
                rpgcore.getMongoManager().saveDataDodatki(uuid, user);
                RPGCORE.getDiscordBot().sendChannelMessage("player-akcesoria-logs", EmbedUtil.create(
                        "**Gracz **`" + player.getName() + "`** zalozyl nowe akcesorium dodatkowe!**",
                        "**Typ: **`" + eventItem.getType() + "`\n"
                                + "**Statystyki:** \n" +
                                "- Odpornosc Na Ludzi: " + defLudzie + "\n" +
                                "- Odpornosc Na Potwory: " + defMoby + "\n" +
                                "- Wymagazyny Poziom: " + Utils.getTagInt(eventItem, "lvl"), Color.getHSBColor(114, 90, 47)));
            });
        }

        if (eventItem.getType() == Material.FIREBALL) {
            e.setCancelled(true);

            if (!user.getAkcesoriaDodatkowe().getMedalion().isEmpty()) {
                player.sendMessage(Utils.format("&8[&c✘&8] &cMasz juz zalozony medalion!"));
                return;
            }

            final double srdmg = Utils.getTagDouble(eventItem, "srdmg");
            final int zloteSerca = Utils.getTagInt(eventItem, "zloteSerca");

            user.getAkcesoriaDodatkowe().setMedalion(Utils.serializeItem(eventItem));

            player.getInventory().removeItem(new ItemBuilder(eventItem.clone()).setAmount(1).toItemStack());
            player.sendMessage(Utils.format("&8[&a✔&8] &aPomyslnie zalozyles " + eventItem.getItemMeta().getDisplayName() + "&a!"));

            bonuses.getBonusesUser().setSrednieobrazenia(bonuses.getBonusesUser().getSrednieobrazenia() + srdmg);
            bonuses.getBonusesUser().setDodatkowezlotehp(bonuses.getBonusesUser().getDodatkowezlotehp() + zloteSerca);

            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
                rpgcore.getMongoManager().saveDataBonuses(uuid, bonuses);
                rpgcore.getMongoManager().saveDataDodatki(uuid, user);
                RPGCORE.getDiscordBot().sendChannelMessage("player-akcesoria-logs", EmbedUtil.create(
                        "**Gracz **`" + player.getName() + "`** zalozyl nowe akcesorium dodatkowe!**",
                        "**Typ: **`" + eventItem.getType() + "`\n"
                                + "**Statystyki:** \n" +
                                "- Srednie Obrazenia: " + srdmg + "\n" +
                                "- Zlote Serca: " + zloteSerca + "\n" +
                                "- Wymagazyny Poziom: " + Utils.getTagInt(eventItem, "lvl"), Color.getHSBColor(114, 90, 47)));
            });
        }

        if (eventItem.getType() == Material.MINECART) {
            e.setCancelled(true);

            if (!user.getAkcesoriaDodatkowe().getEnergia().isEmpty()) {
                player.sendMessage(Utils.format("&8[&c✘&8] &cMasz juz zalozona energie!"));
                return;
            }

            final double mDmg = Utils.getTagDouble(eventItem, "mDmg");
            final double def = Utils.getTagDouble(eventItem, "def");
            final double blok = Utils.getTagDouble(eventItem, "blok");
            final double przebicie = Utils.getTagDouble(eventItem, "przebicie");
            final int mspeed = Utils.getTagInt(eventItem, "mspeed");

            user.getAkcesoriaDodatkowe().setEnergia(Utils.serializeItem(eventItem));

            player.getInventory().removeItem(new ItemBuilder(eventItem.clone()).setAmount(1).toItemStack());
            player.sendMessage(Utils.format("&8[&a✔&8] &aPomyslnie zalozyles " + eventItem.getItemMeta().getDisplayName() + "&a!"));

            bonuses.getBonusesUser().setMinussrednieobrazenia(bonuses.getBonusesUser().getMinussrednieobrazenia() + mDmg);
            bonuses.getBonusesUser().setSredniadefensywa(bonuses.getBonusesUser().getSredniadefensywa() + def);
            bonuses.getBonusesUser().setBlokciosu(bonuses.getBonusesUser().getBlokciosu() + blok);
            bonuses.getBonusesUser().setPrzebiciePancerza(bonuses.getBonusesUser().getPrzebiciePancerza() + przebicie);
            bonuses.getBonusesUser().setSzybkosc(bonuses.getBonusesUser().getSzybkosc() - mspeed);

            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
                rpgcore.getMongoManager().saveDataBonuses(uuid, bonuses);
                rpgcore.getMongoManager().saveDataDodatki(uuid, user);
                RPGCORE.getDiscordBot().sendChannelMessage("player-akcesoria-logs", EmbedUtil.create(
                        "**Gracz **`" + player.getName() + "`** zalozyl nowe akcesorium dodatkowe!**",
                        "**Typ: **`" + eventItem.getType() + "`\n"
                                + "**Statystyki:** \n" +
                                "- Zmniejszone Obrazenia: " + mDmg + "\n" +
                                "- Zwiekszona Defensywa: " + def + "\n" +
                                "- Blok Ciosu: " + blok + "\n" +
                                "- Przebicie Pancerza: " + przebicie + "\n" +
                                "- Zmniejszona Predkosc Ruchu: " + mspeed + "\n" +
                                "- Wymagazyny Poziom: " + Utils.getTagInt(eventItem, "lvl"), Color.getHSBColor(114, 90, 47)));
            });
        }
    }
}
