package rpg.rpgcore.dodatki.akcesoriaD.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.bonuses.Bonuses;
import rpg.rpgcore.discord.EmbedUtil;
import rpg.rpgcore.dodatki.DodatkiUser;
import rpg.rpgcore.utils.Utils;

import java.awt.*;
import java.util.UUID;

public class AkcesoriaDodatInventoryClickListener implements Listener {
    private final RPGCORE rpgcore;

    public AkcesoriaDodatInventoryClickListener(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

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

        if (title.equals("Akcesorium Dodatkowe")) {
            e.setCancelled(true);
            if (item.getType() == Material.IRON_FENCE || item.getType() == Material.STAINED_GLASS_PANE) return;

            final DodatkiUser user = this.rpgcore.getDodatkiManager().find(uuid);
            final Bonuses bonuses = this.rpgcore.getBonusesManager().find(uuid);

            switch (slot) {
                case 1:
                    final double ludzie = Utils.getTagDouble(item, "ludzie");
                    final double moby = Utils.getTagDouble(item, "moby");

                    user.getAkcesoriaDodatkowe().setSzarfa("");

                    bonuses.getBonusesUser().setSilnynaludzi(bonuses.getBonusesUser().getSilnynaludzi() - ludzie);
                    bonuses.getBonusesUser().setSilnynapotwory(bonuses.getBonusesUser().getSilnynapotwory() - moby);

                    rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
                        rpgcore.getMongoManager().saveDataBonuses(uuid, bonuses);
                        rpgcore.getMongoManager().saveDataDodatki(uuid, user);
                        RPGCORE.getDiscordBot().sendChannelMessage("player-akcesoria-logs", EmbedUtil.create(
                                "**Gracz **`" + player.getName() + "`** zdjal swoje akcesorium dodatkowe!**",
                                "**Typ: **`" + item.getType() + "`\n"
                                        + "**Statystyki:** \n" +
                                        "- Silny Na Ludzi: " + ludzie + "\n" +
                                        "- Silny Na Potwory: " + moby + "\n" +
                                        "- Wymagazyny Poziom: " + Utils.getTagInt(item, "lvl"), Color.getHSBColor(114, 90, 47)));
                    });
                    break;
                case 3:
                    final double defLudzie = Utils.getTagDouble(item, "defLudzie");
                    final double defMoby = Utils.getTagDouble(item, "defMoby");

                    user.getAkcesoriaDodatkowe().setPas("");

                    bonuses.getBonusesUser().setDefnaludzi(bonuses.getBonusesUser().getDefnaludzi() - defLudzie);
                    bonuses.getBonusesUser().setDefnamoby(bonuses.getBonusesUser().getDefnamoby() - defMoby);

                    rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
                        rpgcore.getMongoManager().saveDataBonuses(uuid, bonuses);
                        rpgcore.getMongoManager().saveDataDodatki(uuid, user);
                        RPGCORE.getDiscordBot().sendChannelMessage("player-akcesoria-logs", EmbedUtil.create(
                                "**Gracz **`" + player.getName() + "`** zdjal swoje akcesorium dodatkowe!**",
                                "**Typ: **`" + item.getType() + "`\n"
                                        + "**Statystyki:** \n" +
                                        "- Odpornosc Na Ludzi: " + defLudzie + "\n" +
                                        "- Odpornosc Na Potwory: " + defMoby + "\n" +
                                        "- Wymagazyny Poziom: " + Utils.getTagInt(item, "lvl"), Color.getHSBColor(114, 90, 47)));
                    });
                    break;
                case 5:
                    final double srdmg = Utils.getTagDouble(item, "srdmg");
                    final int zloteSerca = Utils.getTagInt(item, "zloteSerca");

                    user.getAkcesoriaDodatkowe().setMedalion("");

                    bonuses.getBonusesUser().setSrednieobrazenia(bonuses.getBonusesUser().getSrednieobrazenia() - srdmg);
                    bonuses.getBonusesUser().setDodatkowezlotehp(bonuses.getBonusesUser().getDodatkowezlotehp() - zloteSerca);

                    rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
                        rpgcore.getMongoManager().saveDataBonuses(uuid, bonuses);
                        rpgcore.getMongoManager().saveDataDodatki(uuid, user);
                        RPGCORE.getDiscordBot().sendChannelMessage("player-akcesoria-logs", EmbedUtil.create(
                                "**Gracz **`" + player.getName() + "`** zdjal swoje akcesorium dodatkowe!**",
                                "**Typ: **`" + item.getType() + "`\n"
                                        + "**Statystyki:** \n" +
                                        "- Srednie Obrazenia: " + srdmg + "\n" +
                                        "- Zlote Serca: " + zloteSerca + "\n" +
                                        "- Wymagazyny Poziom: " + Utils.getTagInt(item, "lvl"), Color.getHSBColor(114, 90, 47)));
                    });
                    break;
                case 7:
                    final double mDmg = Utils.getTagDouble(item, "mDmg");
                    final double def = Utils.getTagDouble(item, "def");
                    final double blok = Utils.getTagDouble(item, "blok");
                    final double przebicie = Utils.getTagDouble(item, "przebicie");
                    final int mspeed = Utils.getTagInt(item, "mspeed");

                    user.getAkcesoriaDodatkowe().setEnergia("");

                    bonuses.getBonusesUser().setMinussrednieobrazenia(bonuses.getBonusesUser().getMinussrednieobrazenia() - mDmg);
                    bonuses.getBonusesUser().setSredniadefensywa(bonuses.getBonusesUser().getSredniadefensywa() - def);
                    bonuses.getBonusesUser().setBlokciosu(bonuses.getBonusesUser().getBlokciosu() - blok);
                    bonuses.getBonusesUser().setPrzebiciePancerza(bonuses.getBonusesUser().getPrzebiciePancerza() - przebicie);
                    bonuses.getBonusesUser().setSzybkosc(bonuses.getBonusesUser().getSzybkosc() + mspeed);

                    rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
                        rpgcore.getMongoManager().saveDataBonuses(uuid, bonuses);
                        rpgcore.getMongoManager().saveDataDodatki(uuid, user);
                        RPGCORE.getDiscordBot().sendChannelMessage("player-akcesoria-logs", EmbedUtil.create(
                                "**Gracz **`" + player.getName() + "`** zalozyl nowe akcesorium dodatkowe!**",
                                "**Typ: **`" + item.getType() + "`\n"
                                        + "**Statystyki:** \n" +
                                        "- Zmniejszone Obrazenia: " + mDmg + "\n" +
                                        "- Zwiekszona Defensywa: " + def + "\n" +
                                        "- Blok Ciosu: " + blok + "\n" +
                                        "- Przebicie Pancerza: " + przebicie + "\n" +
                                        "- Zmniejszona Predkosc Ruchu: " + mspeed + "\n" +
                                        "- Wymagazyny Poziom: " + Utils.getTagInt(item, "lvl"), Color.getHSBColor(114, 90, 47)));
                    });
                    break;
                case 8:
                    rpgcore.getDodatkiManager().openDodatkiGUI(player);
                    return;

            }
            player.getInventory().addItem(item);
            rpgcore.getDodatkiManager().openAkceDodaGUI(player, player);
            player.sendMessage(Utils.format("&8[&a✔&8] &aPomyslnie zdjales " + item.getItemMeta().getDisplayName() + "&a!"));
        }
    }
}
