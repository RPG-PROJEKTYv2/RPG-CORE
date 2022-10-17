package rpg.rpgcore.guilds.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.globalitems.GlobalItem;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

public class GuildsInventoryClick implements Listener {

    private final RPGCORE rpgcore;

    public GuildsInventoryClick(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void guildInventoryClick(final InventoryClickEvent e) {

        final Inventory clickedInventory = e.getClickedInventory();
        final Player player = (Player) e.getWhoClicked();
        final UUID playerUUID = player.getUniqueId();

        if (e.getClickedInventory() == null || e.getInventory() == null) {
            return;
        }

        final String clickedInventoryTitle = clickedInventory.getTitle();
        final ItemStack clickedItem = e.getCurrentItem();
        final int clickedSlot = e.getSlot();

        // MAIN GUI
        if (clickedInventoryTitle.contains("Panel Klanu")) {
            e.setCancelled(true);

            final String playerGuild = rpgcore.getGuildManager().getGuildTag(playerUUID);

            if (clickedSlot == 10) {
                rpgcore.getGuildManager().showMembers(playerGuild, player);
                return;
            }

            if (clickedSlot == 16) {
                if (rpgcore.getGuildManager().getGuildOwner(playerGuild).equals(playerUUID) ||
                        (!rpgcore.getGuildManager().getGuildCoOwner(playerGuild).isEmpty() && rpgcore.getGuildManager().getGuildCoOwner(playerGuild).equals(playerUUID.toString()))) {
                    rpgcore.getGuildManager().showUpgrades(playerGuild, player);
                    return;
                }
                player.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&cNie jestes zalozycielem/zastepca klanu!"));
                player.closeInventory();
                return;
            }
            return;
        }

        // MEMBERS
        if (clickedInventoryTitle.contains("Czlonkowie Klanu")) {
            e.setCancelled(true);
            final String tag = rpgcore.getGuildManager().getGuildTag(playerUUID);

            if (!(rpgcore.getGuildManager().getGuildOwner(tag).equals(playerUUID) || rpgcore.getGuildManager().getGuildCoOwner(tag).equals(playerUUID.toString()))) {
                return;
            }

            if (clickedSlot == 0) {
                return;
            }


            if (clickedItem.getType().equals(Material.AIR)) {
                return;
            }

            final UUID targetUUID = rpgcore.getUserManager().find(Utils.removeColor(clickedItem.getItemMeta().getDisplayName())).getId();

            if (targetUUID.equals(playerUUID)) {
                return;
            }

            if (clickedSlot == 1) {
                if (!rpgcore.getGuildManager().getGuildCoOwner(tag).isEmpty()) {
                    if (rpgcore.getGuildManager().getGuildOwner(tag).equals(playerUUID)) {
                        rpgcore.getGuildManager().removePlayerFromGuild(tag, targetUUID);
                        player.closeInventory();
                        return;
                    }
                }
            }

            rpgcore.getGuildManager().removePlayerFromGuild(tag, targetUUID);
            player.closeInventory();
            return;
        }

        // UPGRADES
        if (clickedInventoryTitle.contains("Ulepszenia Klanu ")) {
            e.setCancelled(true);
            final String tag = rpgcore.getGuildManager().getGuildTag(playerUUID);
            if (!(rpgcore.getGuildManager().getGuildOwner(tag).equals(playerUUID) ||
                    (!rpgcore.getGuildManager().getGuildCoOwner(tag).isEmpty() && rpgcore.getGuildManager().getGuildCoOwner(tag).equals(playerUUID.toString())))) {
                player.closeInventory();
                return;
            }
            int guildPoints = rpgcore.getGuildManager().getGuildBalance(tag);

            if (guildPoints < 1) {
                player.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&cTwoj Klan nie posiada dostepnych kredytow do rozdania. Mozesz je zdobyc zwiekszajac poziom swojej gildi"));
                rpgcore.getGuildManager().setGuildBalance(tag, 1);
                player.closeInventory();
                return;
            }
            switch (clickedSlot) {
                case 0:
                    if (rpgcore.getGuildManager().getGuildSredniDmg(tag) >= 50) {
                        player.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&7Twoja gildia posiada juz maksymalny poziom ulepszenia w tym drzewku"));
                        player.closeInventory();
                        return;
                    }
                    rpgcore.getGuildManager().updateGuildSredniDmg(tag, 2.5);
                    break;
                case 1:
                    return;
                case 2:
                    if (rpgcore.getGuildManager().getGuildSredniDef(tag) >= 50) {
                        player.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&7Twoja gildia posiada juz maksymalny poziom ulepszenia w tym drzewku"));
                        player.closeInventory();
                        return;
                    }
                    rpgcore.getGuildManager().updateGuildSredniDef(tag, 2.5);
                    break;
                case 3:
                    return;
                case 4:
                    if (rpgcore.getGuildManager().getGuildDodatkowyExp(tag) >= 25) {
                        player.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&7Twoja gildia posiada juz maksymalny poziom ulepszenia w tym drzewku"));
                        player.closeInventory();
                        return;
                    }
                    if (!player.getInventory().containsAtLeast(GlobalItem.getHellCoin(100), 1)) {
                        player.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&cNie posiadasz odpowiednich przedmiotow do ulepszenia tego drzewka!"));
                        player.closeInventory();
                        return;
                    }
                    rpgcore.getGuildManager().updateGuildDodatkowyExp(tag, 2.5);
                    rpgcore.getGuildManager().updateGuildBalance(tag, -1);
                    player.getInventory().removeItem(GlobalItem.getHellCoin(100));
                    break;
                case 5:
                    return;
                case 6:
                    if (rpgcore.getGuildManager().getGuildDefNaLudzi(tag) >= 50) {
                        player.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&7Twoja gildia posiada juz maksymalny poziom ulepszenia w tym drzewku"));
                        player.closeInventory();
                        return;
                    }
                    rpgcore.getGuildManager().updateGuildDefNaLudzi(tag, 2.5);
                    break;
                case 7:
                    return;
                case 8:
                    if (rpgcore.getGuildManager().getGuildSilnyNaLudzi(tag) >= 50) {
                        player.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&7Twoja gildia posiada juz maksymalny poziom ulepszenia w tym drzewku"));
                        player.closeInventory();
                        return;
                    }
                    rpgcore.getGuildManager().updateGuildSilnyNaLudzi(tag, 2.5);
                    break;
            }
            rpgcore.getGuildManager().updateGuildBalance(tag, -1);
            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataGuild(tag, rpgcore.getGuildManager().find(tag)));
            player.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&aPomyslnie ulepszono drzewko " + clickedItem.getItemMeta().getDisplayName()));
            rpgcore.getGuildManager().showUpgrades(tag, player);
        }
    }
}
