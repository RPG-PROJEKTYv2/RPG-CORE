package rpg.rpgcore.dodatki.bony.events;

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

public class BonyInventoryClickListener implements Listener {
    private final RPGCORE rpgcore;

    public BonyInventoryClickListener(final RPGCORE rpgcore) {
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

        if (!title.equals("Bony")) return;
        e.setCancelled(true);
        if (item.getType() == Material.IRON_FENCE || item.getType() == Material.STAINED_GLASS_PANE) return;

        if (e.getSlot() == 17) {
            rpgcore.getDodatkiManager().openDodatkiGUI(player);
            return;
        }

        final DodatkiUser user = rpgcore.getDodatkiManager().find(uuid);
        final Bonuses bonuses = rpgcore.getBonusesManager().find(uuid);

        switch (Utils.removeColor(item.getItemMeta().getDisplayName())) {
            case "Bon Srednich Obrazen 5%":
                user.getBony().setDmg5("");
                bonuses.getBonusesUser().setSrednieobrazenia(bonuses.getBonusesUser().getSrednieobrazenia() - 5);
                player.sendMessage(Utils.format("&8[&a✔&8] &aZdjales Bon Srednich Obrazen 5%!"));
                break;
            case "Bon Srednich Obrazen 10%":
                user.getBony().setDmg10("");
                bonuses.getBonusesUser().setSrednieobrazenia(bonuses.getBonusesUser().getSrednieobrazenia() - 10);
                player.sendMessage(Utils.format("&8[&a✔&8] &aZdjales Bon Srednich Obrazen 10%!"));
                break;
            case "Bon Srednich Obrazen 15%":
                user.getBony().setDmg15("");
                bonuses.getBonusesUser().setSrednieobrazenia(bonuses.getBonusesUser().getSrednieobrazenia() - 15);
                player.sendMessage(Utils.format("&8[&a✔&8] &aZdjales Bon Srednich Obrazen 15%!"));
                break;
            case "Bon Sredniej Defensywy 5%":
                user.getBony().setDef5("");
                bonuses.getBonusesUser().setSredniadefensywa(bonuses.getBonusesUser().getSredniadefensywa() - 5);
                player.sendMessage(Utils.format("&8[&a✔&8] &aZdjales Bon Sredniej Defensywy 5%!"));
                break;
            case "Bon Sredniej Defensywy 10%":
                user.getBony().setDef10("");
                bonuses.getBonusesUser().setSredniadefensywa(bonuses.getBonusesUser().getSredniadefensywa() - 10);
                player.sendMessage(Utils.format("&8[&a✔&8] &aZdjales Bon Sredniej Defensywy 10%!"));
                break;
            case "Bon Sredniej Defensywy 15%":
                user.getBony().setDef15("");
                bonuses.getBonusesUser().setSredniadefensywa(bonuses.getBonusesUser().getSredniadefensywa() - 15);
                player.sendMessage(Utils.format("&8[&a✔&8] &aZdjales Bon Sredniej Defensywy 15%!"));
                break;
            case "Bon Szansy Na Cios Krytyczny 5%":
                user.getBony().setKryt5("");
                bonuses.getBonusesUser().setSzansanakryta(bonuses.getBonusesUser().getSzansanakryta() - 5);
                player.sendMessage(Utils.format("&8[&a✔&8] &aZdjales Bon Szansy Na Cios Krytyczny 5%!"));
                break;
            case "Bon Szansy Na Cios Krytyczny 10%":
                user.getBony().setKryt10("");
                bonuses.getBonusesUser().setSzansanakryta(bonuses.getBonusesUser().getSzansanakryta() - 10);
                player.sendMessage(Utils.format("&8[&a✔&8] &aZdjales Bon Szansy Na Cios Krytyczny 10%!"));
                break;
            case "Bon Szansy Na Cios Krytyczny 15%":
                user.getBony().setKryt15("");
                bonuses.getBonusesUser().setSzansanakryta(bonuses.getBonusesUser().getSzansanakryta() - 15);
                player.sendMessage(Utils.format("&8[&a✔&8] &aZdjales Bon Szansy Na Cios Krytyczny 15%!"));
                break;
            case "Bon Szansy Na Wzmocnienie Ciosu Krytycznego 10%":
                user.getBony().setWzmkryt10("");
                bonuses.getBonusesUser().setSzansanawzmocnieniekryta(bonuses.getBonusesUser().getSzansanawzmocnieniekryta() - 10);
                player.sendMessage(Utils.format("&8[&a✔&8] &aZdjales Bon Szansy Na Wzmocnienie Ciosu Krytycznego 10%!"));
                break;
            case "Bon Szansy Na Blok Ciosu 20%":
                user.getBony().setBlok20("");
                bonuses.getBonusesUser().setBlokciosu(bonuses.getBonusesUser().getBlokciosu() - 20);
                player.sendMessage(Utils.format("&8[&a✔&8] &aZdjales Bon Szansy Na Blok Ciosu 20%!"));
                break;
            case "Bon Szansy Na Przeszycie Bloku Ciosu 20%":
                user.getBony().setPrzeszywka20("");
                bonuses.getBonusesUser().setPrzeszyciebloku(bonuses.getBonusesUser().getPrzeszyciebloku() - 20);
                player.sendMessage(Utils.format("&8[&a✔&8] &aZdjales Bon Szansy Na Przeszycie Bloku Ciosu 20%!"));
                break;
            case "Bon Dodatkowego Zdrowia +10":
                user.getBony().setHp10("");
                bonuses.getBonusesUser().setDodatkowehp(bonuses.getBonusesUser().getDodatkowehp() - 10);
                player.sendMessage(Utils.format("&8[&a✔&8] &aZdjales Bon Dodatkowego Zdrowia +10!"));
                player.setMaxHealth(player.getMaxHealth() - 10 * 2);
                break;
            case "Bon Dodatkowego Zdrowia +20":
                user.getBony().setHp20("");
                bonuses.getBonusesUser().setDodatkowehp(bonuses.getBonusesUser().getDodatkowehp() - 20);
                player.sendMessage(Utils.format("&8[&a✔&8] &aZdjales Bon Dodatkowego Zdrowia +20!"));
                player.setMaxHealth(player.getMaxHealth() - 20 * 2);
                break;
            case "Bon Dodatkowego Zdrowia +35":
                user.getBony().setHp35("");
                bonuses.getBonusesUser().setDodatkowehp(bonuses.getBonusesUser().getDodatkowehp() - 35);
                player.sendMessage(Utils.format("&8[&a✔&8] &aZdjales Bon Dodatkowego Zdrowia +35!"));
                player.setMaxHealth(player.getMaxHealth() - 35 * 2);
                break;
            case "Bon Zwiekszonej Predkosci Ruchu +25":
                user.getBony().setPredkosc25("");
                bonuses.getBonusesUser().setSzybkosc(bonuses.getBonusesUser().getSzybkosc() - 25);
                player.sendMessage(Utils.format("&8[&a✔&8] &aZdjales Bon Zwiekszonej Predkosci Ruchu +25!"));
                break;
            case "Bon Zwiekszonej Predkosci Ruchu +50":
                user.getBony().setPredkosc50("");
                bonuses.getBonusesUser().setSzybkosc(bonuses.getBonusesUser().getSzybkosc() - 50);
                player.sendMessage(Utils.format("&8[&a✔&8] &aZdjales Bon Zwiekszonej Predkosci Ruchu +50!"));
                break;
            case "Bon Zwiekszonych Obrazen W Kamienie Metin +2":
                user.getBony().setDmgMetiny("");
                bonuses.getBonusesUser().setDmgMetiny(0);
                player.sendMessage(Utils.format("&8[&a✔&8] &aZdjales Bon Zwiekszonych Obrazen W Kamienie Metin +2!"));
                break;
            case "Bon Zwiekszonych Obrazen W Kamienie Metin +3":
                user.getBony().setDmgMetiny("");
                bonuses.getBonusesUser().setDmgMetiny(0);
                player.sendMessage(Utils.format("&8[&a✔&8] &aZdjales Bon Zwiekszonych Obrazen W Kamienie Metin +3!"));
                break;
            case "Bon Zwiekszonych Obrazen W Kamienie Metin +5":
                user.getBony().setDmgMetiny("");
                bonuses.getBonusesUser().setDmgMetiny(0);
                player.sendMessage(Utils.format("&8[&a✔&8] &aZdjales Bon Zwiekszonych Obrazen W Kamienie Metin +5!"));
                break;
            default:
                return;
        }
        player.getInventory().addItem(item);
        rpgcore.getDodatkiManager().openBonyGUI(player, player.getUniqueId());
        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
            rpgcore.getMongoManager().saveDataBonuses(uuid, bonuses);
            rpgcore.getMongoManager().saveDataDodatki(uuid, user);
            double[] tps = MinecraftServer.getServer().recentTps;
            RPGCORE.getDiscordBot().sendChannelMessage("player-bony-logs", EmbedUtil.create(
                    "**Gracz **`" + player.getName() + "`** zalozyl bona!**",
                    "**Ping Gracza: **" + ((CraftPlayer) player).getHandle().ping + " ms\n" +
                            "**Ping Serwerowy: ** 1m - " + tps[0] + "tps, 5m - " + tps[1] + "tps, 15m - " + tps[2] + "tps\n" +
                            "**Bon: **" + Utils.removeColor(item.getItemMeta().getDisplayName()), Color.getHSBColor(114, 90, 47)));
        });
    }
}
