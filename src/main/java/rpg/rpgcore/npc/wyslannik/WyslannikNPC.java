package rpg.rpgcore.npc.wyslannik;

import com.google.common.collect.ImmutableSet;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.npc.wyslannik.enums.WyslannikMissionKillBoss;
import rpg.rpgcore.npc.wyslannik.enums.WyslannikMissionKillMob;
import rpg.rpgcore.npc.wyslannik.objects.WyslannikUser;
import rpg.rpgcore.utils.DoubleUtils;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

public class WyslannikNPC {
    private final Map<UUID, WyslannikUser> userMap;

    public WyslannikNPC(final RPGCORE rpgcore) {
        this.userMap = rpgcore.getMongoManager().loadAllWyslannik();
    }

    public void openGUI(final Player player) {
        final WyslannikUser user = this.find(player.getUniqueId());
        final Inventory gui = Bukkit.createInventory(null, InventoryType.HOPPER, Utils.format("&c&lWyslannik"));
        final WyslannikMissionKillMob missionKillMob = WyslannikMissionKillMob.getByMission(user.getKillMobsMission());
        final WyslannikMissionKillBoss missionKillBoss = WyslannikMissionKillBoss.getByMission(user.getKillBossMission());
        assert missionKillMob != null;
        assert missionKillBoss != null;
        gui.setItem(0, getMobKillsGUI(user, missionKillMob));
        gui.setItem(2, getStatusBonuses(user));
        gui.setItem(4, getBossKillsGUI(user, missionKillBoss));

        player.openInventory(gui);
    }
    public ItemStack getMobKillsGUI(final WyslannikUser user, final WyslannikMissionKillMob wyslannikMissionKillMob) {
        if (user.getKillMobsMission() == 10) {
            return new ItemBuilder(Material.BARRIER).setName("&a&lWykonane!").setLore(Arrays.asList(
                    "&7Ukonczyles/-as juz wszystkie mozliwe",
                    "&7misje w tej kategorii."
            )).toItemStack();
        } else {
            return new ItemBuilder(Material.IRON_SWORD).setName("&c&lMisja " + user.getKillMobsMission()).setLore(Arrays.asList(
                    "&7Zabij &6" + wyslannikMissionKillMob.getMobsAmount(),
                    "&8- " + wyslannikMissionKillMob.getMobName(),
                    "&f&lNAGRODA",
                    "&8- &7Srednie Obrazenia: &c" + wyslannikMissionKillMob.getSredniDMG(),
                    "",
                    "&7Postep misji: &6" + user.getKillMobsMissionProgress() + "&7/&6" + wyslannikMissionKillMob.getMobsAmount() + " &7(&6" + (DoubleUtils.round((double) user.getKillMobsMissionProgress() / (double) wyslannikMissionKillMob.getMobsAmount() * 100.0, 2) + "%&7)")
            )).hideFlag().toItemStack().clone();
        }
    }
    public ItemStack getBossKillsGUI(final WyslannikUser user, final WyslannikMissionKillBoss wyslannikMissionKillBoss) {
        if (user.getKillBossMission() == 10) {
            return new ItemBuilder(Material.BARRIER).setName("&a&lWykonane!").setLore(Arrays.asList(
                    "&7Ukonczyles/-as juz wszystkie mozliwe",
                    "&7misje w tej kategorii."
            )).toItemStack();
        } else {
            return new ItemBuilder(Material.DIAMOND_SWORD).setName("&c&lMisja " + user.getKillBossMission()).setLore(Arrays.asList(
                    "&7Zabij &6" + wyslannikMissionKillBoss.getBossAmount(),
                    "&8- " + wyslannikMissionKillBoss.getBossName(),
                    "&f&lNAGRODA",
                    "&8- &7Srednia Defensywa: &c" + wyslannikMissionKillBoss.getSredniDEF(),
                    "",
                    "&7Postep misji: &6" + user.getKillBossMissionProgress() + "&7/&6" + wyslannikMissionKillBoss.getBossAmount() + " &7(&6" + (DoubleUtils.round((double) user.getKillBossMissionProgress() / (double) wyslannikMissionKillBoss.getBossAmount() * 100.0, 2) + "%&7)")
            )).hideFlag().toItemStack().clone();
        }
    }
    public ItemStack getStatusBonuses(final WyslannikUser user) {
        return new ItemBuilder(Material.PAPER).setName("&c&lStatystyki").setLore(Arrays.asList(
                "",
                "&7Srednie Obrazenia: &c" + user.getSredniDMG() + "%",
                "&7Srednia Defensywa: &c" + user.getSredniDEF() + "%"
                        )).toItemStack().clone();
    }



    public WyslannikUser find(final UUID uuid) {
        this.userMap.computeIfAbsent(uuid, k -> new WyslannikUser(uuid));
        return this.userMap.get(uuid);
    }

    public void set(final UUID uuid, final WyslannikUser wyslannikUser) {
        this.userMap.replace(uuid, wyslannikUser);
    }

    public void add(final WyslannikUser wyslannikObject) {
        this.userMap.put(wyslannikObject.getUuid(), wyslannikObject);
    }
    public ImmutableSet<WyslannikUser> getWyslannikUser() {
        return ImmutableSet.copyOf(userMap.values());
    }
}
