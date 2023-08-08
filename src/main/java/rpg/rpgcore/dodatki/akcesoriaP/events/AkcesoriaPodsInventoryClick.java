package rpg.rpgcore.dodatki.akcesoriaP.events;

import net.minecraft.server.v1_8_R3.MinecraftServer;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
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

public class AkcesoriaPodsInventoryClick implements Listener {
    private final RPGCORE rpgcore;

    public AkcesoriaPodsInventoryClick(final RPGCORE rpgcore) {
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


        if (title.equals("Akcesorium Podstawowe")) {
            e.setCancelled(true);
            if (item.getType() == Material.IRON_FENCE || item.getType() == Material.STAINED_GLASS_PANE) return;

            final DodatkiUser user = this.rpgcore.getDodatkiManager().find(uuid);
            final Bonuses bonuses = this.rpgcore.getBonusesManager().find(uuid);

            switch (slot) {
                case 2:
                    final double def = Utils.getTagDouble(item, "def");
                    final double blok = Utils.getTagDouble(item, "blok");
                    final int hp = Utils.getTagInt(item, "hp");

                    user.getAkcesoriaPodstawowe().setTarcza(new ItemStack(Material.AIR));
                    bonuses.getBonusesUser().setSredniadefensywa(bonuses.getBonusesUser().getSredniadefensywa() - def);
                    bonuses.getBonusesUser().setBlokciosu(bonuses.getBonusesUser().getBlokciosu() - blok);
                    bonuses.getBonusesUser().setDodatkowehp(bonuses.getBonusesUser().getDodatkowehp() - hp);
                    player.setMaxHealth(player.getMaxHealth() - hp * 2);
                    rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
                        rpgcore.getMongoManager().saveDataBonuses(uuid, bonuses);
                        rpgcore.getMongoManager().saveDataDodatki(uuid, user);
                        double[] tps = MinecraftServer.getServer().recentTps;
                        RPGCORE.getDiscordBot().sendChannelMessage("player-akcesoria-logs", EmbedUtil.create(
                                "**Gracz **`" + player.getName() + "`** zdjal swoje akcesorium!**",
                                "**Ping Gracza: **" + ((CraftPlayer) player).getHandle().ping + " ms\n" +
                                        "**Ping Serwerowy: ** 1m - " + tps[0] + "tps, 5m - " + tps[1] + "tps, 15m - " + tps[2] + "tps\n" +
                                        "**Typ: **`" + item.getType() + "`\n" +
                                        "**Statystyki:** \n" +
                                        "- Srednia Defensywa: " + def + "\n" +
                                        "- Blok Ciosu: " + blok + "\n" +
                                        "- Dodatkowe Hp: " + hp + "\n" +
                                        "- Wymagazyny Poziom: " + Utils.getTagInt(item, "lvl"), Color.getHSBColor(114, 90, 47)));
                    });
                    break;
                case 3:
                    final int ddmg = Utils.getTagInt(item, "ddmg");
                    final double kryt = Utils.getTagDouble(item, "kryt");
                    final double srdmg = Utils.getTagDouble(item, "srdmg");

                    user.getAkcesoriaPodstawowe().setNaszyjnik(new ItemStack(Material.AIR));
                    bonuses.getBonusesUser().setDodatkoweobrazenia(bonuses.getBonusesUser().getDodatkoweobrazenia() - ddmg);
                    bonuses.getBonusesUser().setSzansanakryta(bonuses.getBonusesUser().getSzansanakryta() - kryt);
                    bonuses.getBonusesUser().setSrednieobrazenia(bonuses.getBonusesUser().getSrednieobrazenia() - srdmg);

                    rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
                        rpgcore.getMongoManager().saveDataBonuses(uuid, bonuses);
                        rpgcore.getMongoManager().saveDataDodatki(uuid, user);
                        double[] tps = MinecraftServer.getServer().recentTps;
                        RPGCORE.getDiscordBot().sendChannelMessage("player-akcesoria-logs", EmbedUtil.create(
                                "**Gracz **`" + player.getName() + "`** zdjal swoje akcesorium!**",
                                "**Ping Gracza: **" + ((CraftPlayer) player).getHandle().ping + " ms\n" +
                                        "**Ping Serwerowy: ** 1m - " + tps[0] + "tps, 5m - " + tps[1] + "tps, 15m - " + tps[2] + "tps\n" +
                                        "**Typ: **`" + item.getType() + "`\n" +
                                        "**Statystyki:** \n" +
                                        "- Dodatkowe Obrazenia: " + ddmg + "\n" +
                                        "- Szansa Na Cios Krytyczny: " + kryt + "\n" +
                                        "- Srednie Obrazenia: " + srdmg + "\n" +
                                        "- Wymagazyny Poziom: " + Utils.getTagInt(item, "lvl"), Color.getHSBColor(114, 90, 47)));
                    });
                    break;
                case 4:
                    final double ludzie = Utils.getTagDouble(item, "ludzie");
                    final double odpo = Utils.getTagDouble(item, "odpo");
                    final int mspeed = Utils.getTagInt(item, "mspeed");

                    user.getAkcesoriaPodstawowe().setKolczyki(new ItemStack(Material.AIR));
                    bonuses.getBonusesUser().setSilnynaludzi(bonuses.getBonusesUser().getSilnynaludzi() - ludzie);
                    bonuses.getBonusesUser().setDefnaludzi(bonuses.getBonusesUser().getDefnaludzi() - odpo);
                    bonuses.getBonusesUser().setSzybkosc(bonuses.getBonusesUser().getSzybkosc() - mspeed);

                    rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
                        rpgcore.getMongoManager().saveDataBonuses(uuid, bonuses);
                        rpgcore.getMongoManager().saveDataDodatki(uuid, user);
                        double[] tps = MinecraftServer.getServer().recentTps;
                        RPGCORE.getDiscordBot().sendChannelMessage("player-akcesoria-logs", EmbedUtil.create(
                                "**Gracz **`" + player.getName() + "`** zdjal swoje akcesorium!**",
                                "**Ping Gracza: **" + ((CraftPlayer) player).getHandle().ping + " ms\n" +
                                        "**Ping Serwerowy: ** 1m - " + tps[0] + "tps, 5m - " + tps[1] + "tps, 15m - " + tps[2] + "tps\n" +
                                        "**Typ: **`" + item.getType() + "`\n" +
                                        "**Statystyki:** \n" +
                                        "- Silny Na Ludzi: " + ludzie + "\n" +
                                        "- Def Na Ludzi: " + odpo + "\n" +
                                        "- Zmniejszona Szybkosc: " + mspeed + "\n" +
                                        "- Wymagazyny Poziom: " + Utils.getTagInt(item, "lvl"), Color.getHSBColor(114, 90, 47)));
                    });
                    break;
                case 5:
                    final double przeszycie = Utils.getTagDouble(item, "przeszycie");
                    final double wkryt = Utils.getTagDouble(item, "wkryt");
                    final int speed = Utils.getTagInt(item, "speed");

                    user.getAkcesoriaPodstawowe().setPierscien(new ItemStack(Material.AIR));
                    bonuses.getBonusesUser().setPrzeszyciebloku(bonuses.getBonusesUser().getPrzeszyciebloku() - przeszycie);
                    bonuses.getBonusesUser().setWzmocnienieKryta(bonuses.getBonusesUser().getWzmocnienieKryta() - wkryt);
                    bonuses.getBonusesUser().setSzybkosc(bonuses.getBonusesUser().getSzybkosc() - speed);

                    rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
                        rpgcore.getMongoManager().saveDataBonuses(uuid, bonuses);
                        rpgcore.getMongoManager().saveDataDodatki(uuid, user);
                        double[] tps = MinecraftServer.getServer().recentTps;
                        RPGCORE.getDiscordBot().sendChannelMessage("player-akcesoria-logs", EmbedUtil.create(
                                "**Gracz **`" + player.getName() + "`** zdjal swoje akcesorium!**",
                                "**Ping Gracza: **" + ((CraftPlayer) player).getHandle().ping + " ms\n" +
                                        "**Ping Serwerowy: ** 1m - " + tps[0] + "tps, 5m - " + tps[1] + "tps, 15m - " + tps[2] + "tps\n" +
                                        "**Typ: **`" + item.getType() + "`\n" +
                                        "**Statystyki:** \n" +
                                        "- Przeszycie Bloku: " + przeszycie + "\n" +
                                        "- Wzm Kryta: " + wkryt + "\n" +
                                        "- Zwiekszona Szybkosc: " + speed + "\n" +
                                        "- Wymagazyny Poziom: " + Utils.getTagInt(item, "lvl"), Color.getHSBColor(114, 90, 47)));
                    });
                    break;
                case 6:
                    final double srdmg2 = Utils.getTagDouble(item, "srdmg");
                    final double potwory = Utils.getTagDouble(item, "potwory");
                    final double exp = Utils.getTagInt(item, "exp");

                    user.getAkcesoriaPodstawowe().setDiadem(new ItemStack(Material.AIR));
                    bonuses.getBonusesUser().setSrednieobrazenia(bonuses.getBonusesUser().getSrednieobrazenia() - srdmg2);
                    bonuses.getBonusesUser().setSilnynapotwory(bonuses.getBonusesUser().getSilnynapotwory() - potwory);
                    bonuses.getBonusesUser().setDodatkowyExp(bonuses.getBonusesUser().getDodatkowyExp() - exp);

                    rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
                        rpgcore.getMongoManager().saveDataBonuses(uuid, bonuses);
                        rpgcore.getMongoManager().saveDataDodatki(uuid, user);
                        double[] tps = MinecraftServer.getServer().recentTps;
                        RPGCORE.getDiscordBot().sendChannelMessage("player-akcesoria-logs", EmbedUtil.create(
                                "**Gracz **`" + player.getName() + "`** zdjal swoje akcesorium!**",
                                "**Ping Gracza: **" + ((CraftPlayer) player).getHandle().ping + " ms\n" +
                                        "**Ping Serwerowy: ** 1m - " + tps[0] + "tps, 5m - " + tps[1] + "tps, 15m - " + tps[2] + "tps\n" +
                                        "**Typ: **`" + item.getType() + "`\n" +
                                        "**Statystyki:** \n" +
                                        "- Srednie Obrazenia: " + srdmg2 + "\n" +
                                        "- Silny na potwory: " + potwory + "\n" +
                                        "- Dodatkowy exp: " + exp + "\n" +
                                        "- Wymagazyny Poziom: " + Utils.getTagInt(item, "lvl"), Color.getHSBColor(114, 90, 47)));
                    });
                    break;
                case 8:
                    rpgcore.getDodatkiManager().openDodatkiGUI(player);
                    return;
            }
            player.getInventory().addItem(item);
            player.sendMessage(Utils.format("&8[&a✔&8] &aPomyslnie zdjales " + item.getItemMeta().getDisplayName() + "&a!"));
            rpgcore.getDodatkiManager().openAkcePodsGUI(player, player.getUniqueId());
        }
    }
}
