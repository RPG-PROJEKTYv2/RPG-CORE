package rpg.rpgcore.dodatki.bony.events;

import net.minecraft.server.v1_8_R3.MinecraftServer;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
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
import java.util.UUID;

public class BonyInteractListener implements Listener {
    private final RPGCORE rpgcore;

    public BonyInteractListener(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onClikc(final PlayerInteractEvent e) {
        final ItemStack eventItem = e.getItem();
        final Player player = e.getPlayer();
        final UUID uuid = player.getUniqueId();

        if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
            return;
        }

        final DodatkiUser user = rpgcore.getDodatkiManager().find(uuid);
        final Bonuses bonuses = rpgcore.getBonusesManager().find(uuid);

        if (eventItem == null) {
            return;
        }

        if (eventItem.getType() != Material.SIGN) {
            return;
        }

        if (!eventItem.getItemMeta().hasDisplayName() || !eventItem.getItemMeta().hasLore()) {
            return;
        }

        if (!eventItem.getItemMeta().getDisplayName().contains("Bon")) return;

        if (eventItem.getAmount() > 1) {
            player.sendMessage(Utils.format("&8[&c✘&8] &cNie mozesz zalozyc wiecej niz 1 przedmiotu. Rozdziel je!"));
            return;
        }
        e.setCancelled(true);

        switch (Utils.removeColor(eventItem.getItemMeta().getDisplayName())) {
            case "Bon Srednich Obrazen 5%":
                if (!user.getBony().getDmg5().isEmpty()) {
                    player.sendMessage(Utils.format("&8[&c✘&8] &cMasz juz zalozony Bon Srednich Obrazen 5%!"));
                    return;
                }
                user.getBony().setDmg5(Utils.serializeItem(eventItem.clone()));
                bonuses.getBonusesUser().setSrednieobrazenia(bonuses.getBonusesUser().getSrednieobrazenia() + 5);
                player.sendMessage(Utils.format("&8[&a✔&8] &aZalozyles Bon Srednich Obrazen 5%!"));
                break;
            case "Bon Srednich Obrazen 10%":
                if (!user.getBony().getDmg10().isEmpty()) {
                    player.sendMessage(Utils.format("&8[&c✘&8] &cMasz juz zalozony Bon Srednich Obrazen 10%!"));
                    return;
                }
                user.getBony().setDmg10(Utils.serializeItem(eventItem.clone()));
                bonuses.getBonusesUser().setSrednieobrazenia(bonuses.getBonusesUser().getSrednieobrazenia() + 10);
                player.sendMessage(Utils.format("&8[&a✔&8] &aZalozyles Bon Srednich Obrazen 10%!"));
                break;
            case "Bon Srednich Obrazen 15%":
                if (!user.getBony().getDmg15().isEmpty()) {
                    player.sendMessage(Utils.format("&8[&c✘&8] &cMasz juz zalozony Bon Srednich Obrazen 15%!"));
                    return;
                }
                user.getBony().setDmg15(Utils.serializeItem(eventItem.clone()));
                bonuses.getBonusesUser().setSrednieobrazenia(bonuses.getBonusesUser().getSrednieobrazenia() + 15);
                player.sendMessage(Utils.format("&8[&a✔&8] &aZalozyles Bon Srednich Obrazen 15%!"));
                break;
            case "Bon Sredniej Defensywy 5%":
                if (!user.getBony().getDef5().isEmpty()) {
                    player.sendMessage(Utils.format("&8[&c✘&8] &cMasz juz zalozony Bon Sredniej Defensywy 5%!"));
                    return;
                }
                user.getBony().setDef5(Utils.serializeItem(eventItem.clone()));
                bonuses.getBonusesUser().setSredniadefensywa(bonuses.getBonusesUser().getSredniadefensywa() + 5);
                player.sendMessage(Utils.format("&8[&a✔&8] &aZalozyles Bon Sredniej Defensywy 5%!"));
                break;
            case "Bon Sredniej Defensywy 10%":
                if (!user.getBony().getDef10().isEmpty()) {
                    player.sendMessage(Utils.format("&8[&c✘&8] &cMasz juz zalozony Bon Sredniej Defensywy 10%!"));
                    return;
                }
                user.getBony().setDef10(Utils.serializeItem(eventItem.clone()));
                bonuses.getBonusesUser().setSredniadefensywa(bonuses.getBonusesUser().getSredniadefensywa() + 10);
                player.sendMessage(Utils.format("&8[&a✔&8] &aZalozyles Bon Sredniej Defensywy 10%!"));
                break;
            case "Bon Sredniej Defensywy 15%":
                if (!user.getBony().getDef15().isEmpty()) {
                    player.sendMessage(Utils.format("&8[&c✘&8] &cMasz juz zalozony Bon Sredniej Defensywy 15%!"));
                    return;
                }
                user.getBony().setDef15(Utils.serializeItem(eventItem.clone()));
                bonuses.getBonusesUser().setSredniadefensywa(bonuses.getBonusesUser().getSredniadefensywa() + 15);
                player.sendMessage(Utils.format("&8[&a✔&8] &aZalozyles Bon Sredniej Defensywy 15%!"));
                break;
            case "Bon Szansy Na Cios Krytyczny 5%":
                if (!user.getBony().getKryt5().isEmpty()) {
                    player.sendMessage(Utils.format("&8[&c✘&8] &cMasz juz zalozony Bon Szansy Na Cios Krytyczny 5%!"));
                    return;
                }
                user.getBony().setKryt5(Utils.serializeItem(eventItem.clone()));
                bonuses.getBonusesUser().setSzansanakryta(bonuses.getBonusesUser().getSzansanakryta() + 5);
                player.sendMessage(Utils.format("&8[&a✔&8] &aZalozyles Bon Szansy Na Cios Krytyczny 5%!"));
                break;
            case "Bon Szansy Na Cios Krytyczny 10%":
                if (!user.getBony().getKryt10().isEmpty()) {
                    player.sendMessage(Utils.format("&8[&c✘&8] &cMasz juz zalozony Bon Szansy Na Cios Krytyczny 10%!"));
                    return;
                }
                user.getBony().setKryt10(Utils.serializeItem(eventItem.clone()));
                bonuses.getBonusesUser().setSzansanakryta(bonuses.getBonusesUser().getSzansanakryta() + 10);
                player.sendMessage(Utils.format("&8[&a✔&8] &aZalozyles Bon Szansy Na Cios Krytyczny 10%!"));
                break;
            case "Bon Szansy Na Cios Krytyczny 15%":
                if (!user.getBony().getKryt15().isEmpty()) {
                    player.sendMessage(Utils.format("&8[&c✘&8] &cMasz juz zalozony Bon Szansy Na Cios Krytyczny 15%!"));
                    return;
                }
                user.getBony().setKryt15(Utils.serializeItem(eventItem.clone()));
                bonuses.getBonusesUser().setSzansanakryta(bonuses.getBonusesUser().getSzansanakryta() + 15);
                player.sendMessage(Utils.format("&8[&a✔&8] &aZalozyles Bon Szansy Na Cios Krytyczny 15%!"));
                break;
            case "Bon Szansy Na Wzmocnienie Ciosu Krytycznego 10%":
                if (!user.getBony().getWzmkryt10().isEmpty()) {
                    player.sendMessage(Utils.format("&8[&c✘&8] &cMasz juz zalozony Bon Szansy Na Wzmocnienie Ciosu Krytycznego 10%!"));
                    return;
                }
                user.getBony().setWzmkryt10(Utils.serializeItem(eventItem.clone()));
                bonuses.getBonusesUser().setSzansanawzmocnieniekryta(bonuses.getBonusesUser().getSzansanawzmocnieniekryta() + 10);
                player.sendMessage(Utils.format("&8[&a✔&8] &aZalozyles Bon Szansy Na Wzmocnienie Ciosu Krytycznego 10%!"));
                break;
            case "Bon Szansy Na Blok Ciosu 20%":
                if (!user.getBony().getBlok20().isEmpty()) {
                    player.sendMessage(Utils.format("&8[&c✘&8] &cMasz juz zalozony Bon Szansy Na Blok Ciosu 20%!"));
                    return;
                }
                user.getBony().setBlok20(Utils.serializeItem(eventItem.clone()));
                bonuses.getBonusesUser().setBlokciosu(bonuses.getBonusesUser().getBlokciosu() + 20);
                player.sendMessage(Utils.format("&8[&a✔&8] &aZalozyles Bon Szansy Na Blok Ciosu 20%!"));
                break;
            case "Bon Szansy Na Przeszycie Bloku Ciosu 20%":
                if (!user.getBony().getPrzeszywka20().isEmpty()) {
                    player.sendMessage(Utils.format("&8[&c✘&8] &cMasz juz zalozony Bon Szansy Na Przeszycie Bloku Ciosu 20%!"));
                    return;
                }
                user.getBony().setPrzeszywka20(Utils.serializeItem(eventItem.clone()));
                bonuses.getBonusesUser().setPrzeszyciebloku(bonuses.getBonusesUser().getPrzeszyciebloku() + 20);
                player.sendMessage(Utils.format("&8[&a✔&8] &aZalozyles Bon Szansy Na Przeszycie Bloku Ciosu 20%!"));
                break;
            case "Bon Dodatkowego Zdrowia +10":
                if (!user.getBony().getHp10().isEmpty()) {
                    player.sendMessage(Utils.format("&8[&c✘&8] &cMasz juz zalozony Bon Dodatkowego Zdrowia +10!"));
                    return;
                }
                user.getBony().setHp10(Utils.serializeItem(eventItem.clone()));
                bonuses.getBonusesUser().setDodatkowehp(bonuses.getBonusesUser().getDodatkowehp() + 10);
                player.sendMessage(Utils.format("&8[&a✔&8] &aZalozyles Bon Dodatkowego Zdrowia +10!"));
                player.setMaxHealth(player.getMaxHealth() + 10 * 2);
                break;
            case "Bon Dodatkowego Zdrowia +20":
                if (!user.getBony().getHp20().isEmpty()) {
                    player.sendMessage(Utils.format("&8[&c✘&8] &cMasz juz zalozony Bon Dodatkowego Zdrowia +20!"));
                    return;
                }
                user.getBony().setHp20(Utils.serializeItem(eventItem.clone()));
                bonuses.getBonusesUser().setDodatkowehp(bonuses.getBonusesUser().getDodatkowehp() + 20);
                player.sendMessage(Utils.format("&8[&a✔&8] &aZalozyles Bon Dodatkowego Zdrowia +20!"));
                player.setMaxHealth(player.getMaxHealth() + 20 * 2);
                break;
            case "Bon Dodatkowego Zdrowia +35":
                if (!user.getBony().getHp35().isEmpty()) {
                    player.sendMessage(Utils.format("&8[&c✘&8] &cMasz juz zalozony Bon Dodatkowego Zdrowia +35!"));
                    return;
                }
                user.getBony().setHp35(Utils.serializeItem(eventItem.clone()));
                bonuses.getBonusesUser().setDodatkowehp(bonuses.getBonusesUser().getDodatkowehp() + 35);
                player.sendMessage(Utils.format("&8[&a✔&8] &aZalozyles Bon Dodatkowego Zdrowia +35!"));
                player.setMaxHealth(player.getMaxHealth() + 35 * 2);
                break;
            case "Bon Zwiekszonej Predkosci Ruchu +25":
                if (!user.getBony().getPredkosc25().isEmpty()) {
                    player.sendMessage(Utils.format("&8[&c✘&8] &cMasz juz zalozony Bon Zwiekszonej Predkosci Ruchu +25!"));
                    return;
                }
                user.getBony().setPredkosc25(Utils.serializeItem(eventItem.clone()));
                bonuses.getBonusesUser().setSzybkosc(bonuses.getBonusesUser().getSzybkosc() + 25);
                player.sendMessage(Utils.format("&8[&a✔&8] &aZalozyles Bon Zwiekszonej Predkosci Ruchu +25!"));
                break;
            case "Bon Zwiekszonej Predkosci Ruchu +50":
                if (!user.getBony().getPredkosc50().isEmpty()) {
                    player.sendMessage(Utils.format("&8[&c✘&8] &cMasz juz zalozony Bon Zwiekszonej Predkosci Ruchu +50!"));
                    return;
                }
                user.getBony().setPredkosc50(Utils.serializeItem(eventItem.clone()));
                bonuses.getBonusesUser().setSzybkosc(bonuses.getBonusesUser().getSzybkosc() + 50);
                player.sendMessage(Utils.format("&8[&a✔&8] &aZalozyles Bon Zwiekszonej Predkosci Ruchu +50!"));
                break;
            case "Bon Zwiekszonych Obrazen W Kamienie Metin +2":
                if (!user.getBony().getDmgMetiny().isEmpty()) {
                    player.sendMessage(Utils.format("&8[&c✘&8] &cMasz juz zalozony Bon Zwiekszonych Obrazen W Kamienie Metin!"));
                    return;
                }
                user.getBony().setDmgMetiny(Utils.serializeItem(eventItem.clone()));
                bonuses.getBonusesUser().setDmgMetiny(2);
                player.sendMessage(Utils.format("&8[&a✔&8] &aZalozyles Bon Zwiekszonych Obrazen W Kamienie Metin +2!"));
                break;
            case "Bon Zwiekszonych Obrazen W Kamienie Metin +3":
                if (!user.getBony().getDmgMetiny().isEmpty()) {
                    player.sendMessage(Utils.format("&8[&c✘&8] &cMasz juz zalozony Bon Zwiekszonych Obrazen W Kamienie Metin!"));
                    return;
                }
                user.getBony().setDmgMetiny(Utils.serializeItem(eventItem.clone()));
                bonuses.getBonusesUser().setDmgMetiny(3);
                player.sendMessage(Utils.format("&8[&a✔&8] &aZalozyles Bon Zwiekszonych Obrazen W Kamienie Metin +3!"));
                break;
            case "Bon Zwiekszonych Obrazen W Kamienie Metin +5":
                if (!user.getBony().getDmgMetiny().isEmpty()) {
                    player.sendMessage(Utils.format("&8[&c✘&8] &cMasz juz zalozony Bon Zwiekszonych Obrazen W Kamienie Metin!"));
                    return;
                }
                user.getBony().setDmgMetiny(Utils.serializeItem(eventItem.clone()));
                bonuses.getBonusesUser().setDmgMetiny(5);
                player.sendMessage(Utils.format("&8[&a✔&8] &aZalozyles Bon Zwiekszonych Obrazen W Kamienie Metin +5!"));
                break;
            default:
                return;
        }
        player.getInventory().removeItem(new ItemBuilder(eventItem.clone()).setAmount(1).toItemStack());
        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
            rpgcore.getMongoManager().saveDataBonuses(uuid, bonuses);
            rpgcore.getMongoManager().saveDataDodatki(uuid, user);
            double[] tps = MinecraftServer.getServer().recentTps;
            RPGCORE.getDiscordBot().sendChannelMessage("player-bony-logs", EmbedUtil.create(
                    "**Gracz **`" + player.getName() + "`** zalozyl bona!**",
                    "**Ping Gracza: **" + ((CraftPlayer) player).getHandle().ping + " ms\n" +
                            "**Ping Serwerowy: ** 1m - " + tps[0] + "tps, 5m - " + tps[1] + "tps, 15m - " + tps[2] + "tps\n" +
                            "**Bon: **" + Utils.removeColor(eventItem.getItemMeta().getDisplayName()), Color.getHSBColor(114, 90, 47)));
        });
    }
}
