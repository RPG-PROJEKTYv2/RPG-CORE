package rpg.rpgcore.dodatki.akcesoriaP.events;

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

public class AkcesoriaPodsInteractListener implements Listener {
    private final RPGCORE rpgcore;
    private final List<Material> akcesoriaList = Arrays.asList(Material.ITEM_FRAME, Material.STORAGE_MINECART, Material.HOPPER_MINECART, Material.WATCH, Material.EXPLOSIVE_MINECART);

    public AkcesoriaPodsInteractListener(final RPGCORE rpgcore) {
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

        if (!eventItem.getItemMeta().getDisplayName().contains("Tarcza") && !eventItem.getItemMeta().getDisplayName().contains("Kolczyki") &&
                !eventItem.getItemMeta().getDisplayName().contains("Naszyjnik") && !eventItem.getItemMeta().getDisplayName().contains("Pierscien") &&
                !eventItem.getItemMeta().getDisplayName().contains("Diadem")) return;

        if (eventItem.getAmount() > 1) {
            player.sendMessage(Utils.format("&8[&c✘&8] &cNie mozesz zalozyc wiecej niz 1 przedmiotu. Rozdziel je!"));
            return;
        }

        if (Utils.getTagInt(eventItem, "lvl") > lvl) {
            player.sendMessage(Utils.format("&8[&c✘&8] &cNie posiadasz wymaganego poziomu, zeby zalozyc ten przedmiot!"));
            return;
        }

        if (eventItem.getType() == Material.ITEM_FRAME) {
            e.setCancelled(true);

            if (!user.getAkcesoriaPodstawowe().getTarcza().isEmpty()) {
                player.sendMessage(Utils.format("&8[&c✘&8] &cMasz juz zalozona tarcze!"));
                return;
            }

            final double def = Utils.getTagDouble(eventItem, "def");
            final double blok = Utils.getTagDouble(eventItem, "blok");
            final int hp = Utils.getTagInt(eventItem, "hp");

            user.getAkcesoriaPodstawowe().setTarcza(Utils.serializeItem(eventItem));
            player.getInventory().removeItem(new ItemBuilder(eventItem.clone()).setAmount(1).toItemStack());
            player.sendMessage(Utils.format("&8[&a✔&8] &aPomyslnie zalozyles " + eventItem.getItemMeta().getDisplayName() + "&a!"));
            bonuses.getBonusesUser().setSredniadefensywa(bonuses.getBonusesUser().getSredniadefensywa() + def);
            bonuses.getBonusesUser().setBlokciosu(bonuses.getBonusesUser().getBlokciosu() + blok);
            bonuses.getBonusesUser().setDodatkowehp(bonuses.getBonusesUser().getDodatkowehp() + hp);
            player.setMaxHealth(player.getMaxHealth() + hp*2);

            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
                rpgcore.getMongoManager().saveDataBonuses(uuid, bonuses);
                rpgcore.getMongoManager().saveDataDodatki(uuid, user);
                RPGCORE.getDiscordBot().sendChannelMessage("player-akcesoria-logs", EmbedUtil.create(
                        "**Gracz **`" + player.getName() + "`** zalozyl nowe akcesorium!**",
                        "**Typ: **`" + eventItem.getType() + "`\n"
                                + "**Statystyki:** \n" +
                                "- Srednia Defensywa: " + def + "\n" +
                                "- Blok Ciosu: " + blok + "\n" +
                                "- Dodatkowe Hp: " + hp + "\n" +
                                "- Wymagazyny Poziom: " + Utils.getTagInt(eventItem, "lvl"), Color.getHSBColor(114, 90, 47)));
            });
        }

        if (eventItem.getType() == Material.STORAGE_MINECART) {
            e.setCancelled(true);

            if (!user.getAkcesoriaPodstawowe().getNaszyjnik().isEmpty()) {
                player.sendMessage(Utils.format("&8[&c✘&8] &cMasz juz zalozony naszyjnik!"));
                return;
            }

            final int ddmg = Utils.getTagInt(eventItem, "ddmg");
            final double kryt = Utils.getTagDouble(eventItem, "kryt");
            final double srdmg = Utils.getTagDouble(eventItem, "srdmg");

            user.getAkcesoriaPodstawowe().setNaszyjnik(Utils.serializeItem(eventItem));
            player.getInventory().removeItem(new ItemBuilder(eventItem.clone()).setAmount(1).toItemStack());
            player.sendMessage(Utils.format("&8[&a✔&8] &aPomyslnie zalozyles " + eventItem.getItemMeta().getDisplayName() + "&a!"));
            bonuses.getBonusesUser().setDodatkoweobrazenia(bonuses.getBonusesUser().getDodatkoweobrazenia() + ddmg);
            bonuses.getBonusesUser().setSzansanakryta(bonuses.getBonusesUser().getSzansanakryta() + kryt);
            bonuses.getBonusesUser().setSrednieobrazenia(bonuses.getBonusesUser().getSrednieobrazenia() + srdmg);

            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
                rpgcore.getMongoManager().saveDataBonuses(uuid, bonuses);
                rpgcore.getMongoManager().saveDataDodatki(uuid, user);
                RPGCORE.getDiscordBot().sendChannelMessage("player-akcesoria-logs", EmbedUtil.create(
                        "**Gracz **`" + player.getName() + "`** zalozyl nowe akcesorium!**",
                        "**Typ: **`" + eventItem.getType() + "`\n"
                                + "**Statystyki:** \n" +
                                "- Dodatkowe Obrazenia: " + ddmg + "\n" +
                                "- Szansa Na Cios Krytyczny: " + kryt + "\n" +
                                "- Srednie Obrazenia: " + srdmg + "\n" +
                                "- Wymagazyny Poziom: " + Utils.getTagInt(eventItem, "lvl"), Color.getHSBColor(114, 90, 47)));
            });
        }

        if (eventItem.getType() == Material.HOPPER_MINECART) {
            e.setCancelled(true);

            if (!user.getAkcesoriaPodstawowe().getKolczyki().isEmpty()) {
                player.sendMessage(Utils.format("&8[&c✘&8] &cMasz juz zalozone kolczyki!"));
                return;
            }

            final double ludzie = Utils.getTagDouble(eventItem, "ludzie");
            final double odpo = Utils.getTagDouble(eventItem, "odpo");
            final int mspeed = Utils.getTagInt(eventItem, "mspeed");

            user.getAkcesoriaPodstawowe().setKolczyki(Utils.serializeItem(eventItem));
            player.getInventory().removeItem(new ItemBuilder(eventItem.clone()).setAmount(1).toItemStack());
            player.sendMessage(Utils.format("&8[&a✔&8] &aPomyslnie zalozyles " + eventItem.getItemMeta().getDisplayName() + "&a!"));
            bonuses.getBonusesUser().setSilnynaludzi(bonuses.getBonusesUser().getSilnynaludzi() + ludzie);
            bonuses.getBonusesUser().setDefnaludzi(bonuses.getBonusesUser().getDefnaludzi() + odpo);
            bonuses.getBonusesUser().setSzybkosc(bonuses.getBonusesUser().getSzybkosc() - mspeed);

            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
                rpgcore.getMongoManager().saveDataBonuses(uuid, bonuses);
                rpgcore.getMongoManager().saveDataDodatki(uuid, user);
                RPGCORE.getDiscordBot().sendChannelMessage("player-akcesoria-logs", EmbedUtil.create(
                        "**Gracz **`" + player.getName() + "`** zalozyl nowe akcesorium!**",
                        "**Typ: **`" + eventItem.getType() + "`\n"
                                + "**Statystyki:** \n" +
                                "- Silny Na Ludzi: " + ludzie + "\n" +
                                "- Def Na Ludzi: " + odpo + "\n" +
                                "- Zmniejszona Szybkosc: " + mspeed + "\n" +
                                "- Wymagazyny Poziom: " + Utils.getTagInt(eventItem, "lvl"), Color.getHSBColor(114, 90, 47)));
            });
        }

        if (eventItem.getType() == Material.EXPLOSIVE_MINECART) {
            e.setCancelled(true);

            if (!user.getAkcesoriaPodstawowe().getPierscien().isEmpty()) {
                player.sendMessage(Utils.format("&8[&c✘&8] &cMasz juz zalozony pierscien!"));
                return;
            }

            final double przeszycie = Utils.getTagDouble(eventItem, "przeszycie");
            final double wkryt = Utils.getTagDouble(eventItem, "wkryt");
            final int speed = Utils.getTagInt(eventItem, "speed");

            user.getAkcesoriaPodstawowe().setPierscien(Utils.serializeItem(eventItem));
            player.getInventory().removeItem(new ItemBuilder(eventItem.clone()).setAmount(1).toItemStack());
            player.sendMessage(Utils.format("&8[&a✔&8] &aPomyslnie zalozyles " + eventItem.getItemMeta().getDisplayName() + "&a!"));
            bonuses.getBonusesUser().setPrzeszyciebloku(bonuses.getBonusesUser().getPrzeszyciebloku() + przeszycie);
            bonuses.getBonusesUser().setSzansanawzmocnieniekryta(bonuses.getBonusesUser().getSzansanawzmocnieniekryta() + wkryt);
            bonuses.getBonusesUser().setSzybkosc(bonuses.getBonusesUser().getSzybkosc() + speed);

            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
                rpgcore.getMongoManager().saveDataBonuses(uuid, bonuses);
                rpgcore.getMongoManager().saveDataDodatki(uuid, user);
                RPGCORE.getDiscordBot().sendChannelMessage("player-akcesoria-logs", EmbedUtil.create(
                        "**Gracz **`" + player.getName() + "`** zalozyl nowe akcesorium!**",
                        "**Typ: **`" + eventItem.getType() + "`\n"
                                + "**Statystyki:** \n" +
                                "- Przeszycie Bloku: " + przeszycie + "\n" +
                                "- Wzm Szansa na Kryta: " + wkryt + "\n" +
                                "- Zwiekszona Szybkosc: " + speed + "\n" +
                                "- Wymagazyny Poziom: " + Utils.getTagInt(eventItem, "lvl"), Color.getHSBColor(114, 90, 47)));
            });
        }

        if (eventItem.getType() == Material.WATCH) {
            e.setCancelled(true);

            if (!user.getAkcesoriaPodstawowe().getDiadem().isEmpty()) {
                player.sendMessage(Utils.format("&8[&c✘&8] &cMasz juz zalozony diadem!"));
                return;
            }

            final double srdmg = Utils.getTagDouble(eventItem, "srdmg");
            final double potwory = Utils.getTagDouble(eventItem, "potwory");
            final double exp = Utils.getTagInt(eventItem, "exp");

            user.getAkcesoriaPodstawowe().setDiadem(Utils.serializeItem(eventItem));
            player.getInventory().removeItem(new ItemBuilder(eventItem.clone()).setAmount(1).toItemStack());
            player.sendMessage(Utils.format("&8[&a✔&8] &aPomyslnie zalozyles " + eventItem.getItemMeta().getDisplayName() + "&a!"));
            bonuses.getBonusesUser().setSrednieobrazenia(bonuses.getBonusesUser().getSrednieobrazenia() + srdmg);
            bonuses.getBonusesUser().setSilnynapotwory(bonuses.getBonusesUser().getSilnynapotwory() + potwory);
            bonuses.getBonusesUser().setDodatkowyExp(bonuses.getBonusesUser().getDodatkowyExp() + exp);

            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
                rpgcore.getMongoManager().saveDataBonuses(uuid, bonuses);
                rpgcore.getMongoManager().saveDataDodatki(uuid, user);
                RPGCORE.getDiscordBot().sendChannelMessage("player-akcesoria-logs", EmbedUtil.create(
                        "**Gracz **`" + player.getName() + "`** zalozyl nowe akcesorium!**",
                        "**Typ: **`" + eventItem.getType() + "`\n"
                                + "**Statystyki:** \n" +
                                "- Srednie Obrazenia: " + srdmg + "\n" +
                                "- Silny na potwory: " + potwory + "\n" +
                                "- Dodatkowy exp: " + exp + "\n" +
                                "- Wymagazyny Poziom: " + Utils.getTagInt(eventItem, "lvl"), Color.getHSBColor(114, 90, 47)));
            });
        }
    }

    /*
Akcesoria
KOLCZYKI
-Silny przeciwko ludzią %
-Odpornośc na Ludzi %
-Zmniejszona Szybkość Ruchu %
TARCZA
-Zwiększona Defensywa %
-Szansa Na Blok %
-Dodatkowe Serca xxx
NASZYJNIK
-Dodatkowe DMG xxx
-Szansa na Cios Krytyczny %
-Zwiekszone Obrażenia %
PIERSCIEŃ
-Szansa na przebicie xxx
-Wzmocnienie Ciostu Krytycznego %
-Zwiększona Szybkość Ruchu %
DIADEM
-Średnie Obrażenia %
-Silny na potwory %
-Dodatkowy EXP %
 */
}
