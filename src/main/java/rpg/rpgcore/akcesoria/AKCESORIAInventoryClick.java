package rpg.rpgcore.akcesoria;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.bonuses.BonusesUser;
import rpg.rpgcore.discord.EmbedUtil;
import rpg.rpgcore.utils.Utils;

import java.awt.*;
import java.util.UUID;

public class AKCESORIAInventoryClick implements Listener {

    private final RPGCORE rpgcore;

    public AKCESORIAInventoryClick(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    public int getIntFromString(String string) {
        String string1 = string.replaceAll("%", "");
        String[] items = string1.split(" ");
        for (final String item : items) {
            try {
                return Integer.parseInt(Utils.removeColor(item));
            } catch (NumberFormatException e) {
                continue;
            }
        }
        return 0;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void akcesoriaInventoryClick(final InventoryClickEvent e) {

        final Inventory clickedInventory = e.getClickedInventory();
        final Player player = (Player) e.getWhoClicked();
        final UUID playerUUID = player.getUniqueId();

        if (e.getClickedInventory() == null) {
            return;
        }

        final String clickedInventoryTitle = clickedInventory.getTitle();
        final ItemStack clickedItem = e.getCurrentItem();
        final int clickedSlot = e.getSlot();



        if (clickedInventoryTitle.contains("AkcesoriaCommand gracza ")) {
            e.setCancelled(true);

            if (e.getClick() == ClickType.SHIFT_LEFT || e.getClick() == ClickType.SHIFT_RIGHT) {
                return;
            }

            if (clickedItem.getType() == Material.IRON_FENCE || clickedItem.getType() == Material.STAINED_GLASS_PANE) {
                return;
            }

            final AkcesoriaUser user = rpgcore.getAkcesoriaManager().find(playerUUID).getAkcesoriaUser();
            final BonusesUser bonusUser = rpgcore.getBonusesManager().find(playerUUID).getBonusesUser();

            if (clickedSlot == 1) {
                e.getWhoClicked().getInventory().addItem(clickedItem);
                bonusUser.setSrednieobrazenia(bonusUser.getSrednieobrazenia() - getIntFromString(clickedItem.getItemMeta().getLore().get(0)));
                bonusUser.setBlokciosu(bonusUser.getBlokciosu() - getIntFromString(clickedItem.getItemMeta().getLore().get(1)));
                bonusUser.setDodatkoweobrazenia(bonusUser.getDodatkoweobrazenia() - getIntFromString(clickedItem.getItemMeta().getLore().get(2)));
                user.setTarcza("");
                e.getInventory().setItem(clickedSlot, rpgcore.getAkcesoriaManager().noAkcesoriaItem("Tarczy"));
                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
                    rpgcore.getMongoManager().saveDataAkcesoria(playerUUID, rpgcore.getAkcesoriaManager().find(playerUUID));
                    rpgcore.getMongoManager().saveDataBonuses(playerUUID, rpgcore.getBonusesManager().find(playerUUID));
                });
                rpgcore.getAkcesoriaManager().openAkcesoriaGUI(player);
                RPGCORE.getDiscordBot().sendChannelMessage("player-akcesoria-logs", EmbedUtil.create(
                        "**Gracz **`" + player.getName() + "`** zdjal jedno ze swoich akcesoriow!**",
                          "**Typ: **`"  + clickedItem.getType() + "`\n"
                        + "**Akcesorium:** " + clickedItem.getItemMeta(), Color.getHSBColor(354, 74, 39)));
                return;
            }

            if (clickedSlot == 2) {
                e.getWhoClicked().getInventory().addItem(clickedItem);
                bonusUser.setPrzeszyciebloku(bonusUser.getPrzeszyciebloku() - getIntFromString(clickedItem.getItemMeta().getLore().get(0)));
                bonusUser.setDodatkoweobrazenia(bonusUser.getDodatkoweobrazenia() - getIntFromString(clickedItem.getItemMeta().getLore().get(1)));
                e.getInventory().setItem(clickedSlot, rpgcore.getAkcesoriaManager().noAkcesoriaItem("Medalionu"));
                user.setMedalion("");
                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
                    rpgcore.getMongoManager().saveDataAkcesoria(playerUUID, rpgcore.getAkcesoriaManager().find(playerUUID));
                    rpgcore.getMongoManager().saveDataBonuses(playerUUID, rpgcore.getBonusesManager().find(playerUUID));
                });
                rpgcore.getAkcesoriaManager().openAkcesoriaGUI(player);
                RPGCORE.getDiscordBot().sendChannelMessage("player-akcesoria-logs", EmbedUtil.create(
                        "**Gracz **`" + player.getName() + "`** zdjal jedno ze swoich akcesoriow!**",
                        "**Typ: **`"  + clickedItem.getType() + "`\n"
                                + "**Akcesorium:** " + clickedItem.getItemMeta(), Color.getHSBColor(354, 74, 39)));
                return;
            }

            if (clickedSlot == 3) {
                e.getWhoClicked().getInventory().addItem(clickedItem);
                bonusUser.setSzansanakryta(bonusUser.getSzansanakryta() - getIntFromString(clickedItem.getItemMeta().getLore().get(0)));
                bonusUser.setSrednieobrazenia(bonusUser.getSrednieobrazenia() - getIntFromString(clickedItem.getItemMeta().getLore().get(1)));
                e.getInventory().setItem(clickedSlot, rpgcore.getAkcesoriaManager().noAkcesoriaItem("Pasa"));
                user.setPas("");
                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
                    rpgcore.getMongoManager().saveDataAkcesoria(playerUUID, rpgcore.getAkcesoriaManager().find(playerUUID));
                    rpgcore.getMongoManager().saveDataBonuses(playerUUID, rpgcore.getBonusesManager().find(playerUUID));
                });
                rpgcore.getAkcesoriaManager().openAkcesoriaGUI(player);
                RPGCORE.getDiscordBot().sendChannelMessage("player-akcesoria-logs", EmbedUtil.create(
                        "**Gracz **`" + player.getName() + "`** zdjal jedno ze swoich akcesoriow!**",
                        "**Typ: **`"  + clickedItem.getType() + "`\n"
                                + "**Akcesorium:** " + clickedItem.getItemMeta(), Color.getHSBColor(354, 74, 39)));
                return;
            }

            if (clickedSlot == 4) {
                e.getWhoClicked().getInventory().addItem(clickedItem);
                bonusUser.setDodatkowehp(bonusUser.getDodatkowehp() - getIntFromString(clickedItem.getItemMeta().getLore().get(0)));
                bonusUser.setSilnynaludzi(bonusUser.getSilnynaludzi() - getIntFromString(clickedItem.getItemMeta().getLore().get(1)));
                player.setMaxHealth((double) bonusUser.getDodatkowehp() * 2);
                player.setHealth(player.getMaxHealth());
                e.getInventory().setItem(clickedSlot, rpgcore.getAkcesoriaManager().noAkcesoriaItem("Kolczykow"));
                user.setKolczyki("");
                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
                    rpgcore.getMongoManager().saveDataAkcesoria(playerUUID, rpgcore.getAkcesoriaManager().find(playerUUID));
                    rpgcore.getMongoManager().saveDataBonuses(playerUUID, rpgcore.getBonusesManager().find(playerUUID));
                });
                rpgcore.getAkcesoriaManager().openAkcesoriaGUI(player);
                RPGCORE.getDiscordBot().sendChannelMessage("player-akcesoria-logs", EmbedUtil.create(
                        "**Gracz **`" + player.getName() + "`** zdjal jedno ze swoich akcesoriow!**",
                        "**Typ: **`"  + clickedItem.getType() + "`\n"
                                + "**Akcesorium:** " + clickedItem.getItemMeta(), Color.getHSBColor(354, 74, 39)));
                return;
            }

            if (clickedSlot == 5) {
                e.getWhoClicked().getInventory().addItem(clickedItem);
                bonusUser.setDodatkowehp(bonusUser.getDodatkowehp() - getIntFromString(clickedItem.getItemMeta().getLore().get(0)));
                bonusUser.setSilnynapotwory(bonusUser.getSilnynapotwory() - getIntFromString(clickedItem.getItemMeta().getLore().get(1)));
                player.setMaxHealth((double) bonusUser.getDodatkowehp() * 2);
                player.setHealth(player.getMaxHealth());
                e.getInventory().setItem(clickedSlot, rpgcore.getAkcesoriaManager().noAkcesoriaItem("Sygnetu"));
                user.setSygnet("");
                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
                    rpgcore.getMongoManager().saveDataAkcesoria(playerUUID, rpgcore.getAkcesoriaManager().find(playerUUID));
                    rpgcore.getMongoManager().saveDataBonuses(playerUUID, rpgcore.getBonusesManager().find(playerUUID));
                });
                rpgcore.getAkcesoriaManager().openAkcesoriaGUI(player);
                RPGCORE.getDiscordBot().sendChannelMessage("player-akcesoria-logs", EmbedUtil.create(
                        "**Gracz **`" + player.getName() + "`** zdjal jedno ze swoich akcesoriow!**",
                        "**Typ: **`"  + clickedItem.getType() + "`\n"
                                + "**Akcesorium:** " + clickedItem.getItemMeta(), Color.getHSBColor(354, 74, 39)));
                return;
            }

            if (clickedSlot == 6) {
                e.getWhoClicked().getInventory().addItem(clickedItem);
                bonusUser.setSredniadefensywa(bonusUser.getSredniadefensywa() - getIntFromString(clickedItem.getItemMeta().getLore().get(0)));
                bonusUser.setBlokciosu(bonusUser.getBlokciosu() - getIntFromString(clickedItem.getItemMeta().getLore().get(1)));
                bonusUser.setMinussrednieobrazenia(bonusUser.getMinussrednieobrazenia() - getIntFromString(clickedItem.getItemMeta().getLore().get(2)));
                e.getInventory().setItem(clickedSlot, rpgcore.getAkcesoriaManager().noAkcesoriaItem("Energii"));
                user.setEnergia("");
                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
                    rpgcore.getMongoManager().saveDataAkcesoria(playerUUID, rpgcore.getAkcesoriaManager().find(playerUUID));
                    rpgcore.getMongoManager().saveDataBonuses(playerUUID, rpgcore.getBonusesManager().find(playerUUID));
                });
                rpgcore.getAkcesoriaManager().openAkcesoriaGUI(player);
                RPGCORE.getDiscordBot().sendChannelMessage("player-akcesoria-logs", EmbedUtil.create(
                        "**Gracz **`" + player.getName() + "`** zdjal jedno ze swoich akcesoriow!**",
                        "**Typ: **`"  + clickedItem.getType() + "`\n"
                                + "**Akcesorium:** " + clickedItem.getItemMeta(), Color.getHSBColor(354, 74, 39)));
                return;
            }

            if (clickedSlot == 7) {
                e.getWhoClicked().getInventory().addItem(clickedItem);
                bonusUser.setDefnamoby(bonusUser.getDefnamoby() + getIntFromString(clickedItem.getItemMeta().getLore().get(0)));
                bonusUser.setSilnynapotwory(bonusUser.getSilnynapotwory() + getIntFromString(clickedItem.getItemMeta().getLore().get(1)));
                e.getInventory().setItem(clickedSlot, rpgcore.getAkcesoriaManager().noAkcesoriaItem("Diademu"));
                user.setDiadem("");
                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
                    rpgcore.getMongoManager().saveDataAkcesoria(playerUUID, rpgcore.getAkcesoriaManager().find(playerUUID));
                    rpgcore.getMongoManager().saveDataBonuses(playerUUID, rpgcore.getBonusesManager().find(playerUUID));
                });
                rpgcore.getAkcesoriaManager().openAkcesoriaGUI(player);
                RPGCORE.getDiscordBot().sendChannelMessage("player-akcesoria-logs", EmbedUtil.create(
                        "**Gracz **`" + player.getName() + "`** zdjal jedno ze swoich akcesoriow!**",
                        "**Typ: **`"  + clickedItem.getType() + "`\n"
                                + "**Akcesorium:** " + clickedItem.getItemMeta(), Color.getHSBColor(354, 74, 39)));
            }
        }

    }

}
