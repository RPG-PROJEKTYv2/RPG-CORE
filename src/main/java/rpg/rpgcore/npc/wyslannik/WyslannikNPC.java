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
import rpg.rpgcore.npc.wyslannik.enums.WyslannikMissionOpen;
import rpg.rpgcore.npc.wyslannik.objects.WyslannikObject;
import rpg.rpgcore.npc.wyslannik.objects.WyslannikUser;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

public class WyslannikNPC {
    private final Map<UUID, WyslannikObject> userMap;

    public WyslannikNPC(final RPGCORE rpgcore) {
        this.userMap = rpgcore.getMongoManager().loadAllWyslannik();
    }


    public void openGUI(final Player player) {
        final WyslannikUser user = this.find(player.getUniqueId()).getWyslannikUser();
        final Inventory gui = Bukkit.createInventory(null, InventoryType.HOPPER, Utils.format("&c&lWyslannik"));
        final WyslannikMissionKillMob missionKillMob = WyslannikMissionKillMob.getByMission(user.getKillMobsMission());
        final WyslannikMissionKillBoss missionKillBoss = WyslannikMissionKillBoss.getByMission(user.getKillBossMission());
        final WyslannikMissionOpen missionOpen = WyslannikMissionOpen.getByMission(user.getOpenChestMission());
        assert missionKillMob != null;
        assert missionKillBoss != null;
        assert missionOpen != null;
        gui.setItem(0, this.getMobKillsItem(user, missionKillMob));
        gui.setItem(2, this.getBossKillsItem(user, missionKillBoss));
        gui.setItem(4, this.getChestOpenItem(user, missionOpen));




        gui.setItem(1, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (byte) 15).setName(" ").toItemStack());
        gui.setItem(3, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (byte) 15).setName(" ").toItemStack());

        player.openInventory(gui);
    }

    public ItemStack getMobKillsItem(final WyslannikUser user, final WyslannikMissionKillMob mission) {
        if (user.getKillMobsMission() == 9) {
            return new ItemBuilder(Material.BARRIER).setName("&a&lWykonane!").setLore(Arrays.asList(
                    "&7Ukonczyles/-as juz wszystkie mozliwe",
                    "&7misje w tej kategorii."
            )).toItemStack();
        } else {
            return new ItemBuilder(Material.IRON_SWORD).setName("&c&lMisja " + user.getKillMobsMission()).setLore(Arrays.asList(
                    "&7Zabij &6" + (Math.max(mission.getReqAmount() - user.getKillMobsMissionProgress(), 0)),
                    "&8- " + mission.getMobName(),
                    "&f&lNAGRODA",
                    "&8- " + mission.getReward().getItemMeta().getDisplayName()
            )).hideFlag().toItemStack().clone();
        }
    }

    public ItemStack getBossKillsItem(final WyslannikUser user, final WyslannikMissionKillBoss mission) {
        if (user.getKillBossMission() == 9) {
            return new ItemBuilder(Material.BARRIER).setName("&a&lWykonane!").setLore(Arrays.asList(
                    "&7Ukonczyles/-as juz wszystkie mozliwe",
                    "&7misje w tej kategorii."
            )).toItemStack();
        } else {
            return new ItemBuilder(Material.DIAMOND_SWORD).setName("&c&lMisja " + user.getKillBossMission()).setLore(Arrays.asList(
                    "&7Zabij &6" + (Math.max(mission.getReqAmount() - user.getKillBossMissionProgress(), 0)),
                    "&8- " + mission.getMobName(),
                    "&f&lNAGRODA",
                    "&8- " + mission.getReward().getItemMeta().getDisplayName()
            )).hideFlag().toItemStack().clone();
        }
    }

    public ItemStack getChestOpenItem(final WyslannikUser user, final WyslannikMissionOpen mission) {
        if (user.getOpenChestMission() == 9) {
            return new ItemBuilder(Material.BARRIER).setName("&a&lWykonane!").setLore(Arrays.asList(
                    "&7Ukonczyles/-as juz wszystkie mozliwe",
                    "&7misje w tej kategorii."
            )).toItemStack();
        } else {
            return new ItemBuilder(Material.CHEST).setName("&c&lMisja " + user.getOpenChestMission()).setLore(Arrays.asList(
                    "&7Otworz &6" + (Math.max(mission.getReqAmount() - user.getOpenChestMissionProgress(), 0)),
                    "&7- " + mission.getChestName(),
                    "&f&lNAGRODA",
                    "&8- " + mission.getReward().getItemMeta().getDisplayName()
            )).hideFlag().toItemStack().clone();
        }
    }

    public WyslannikObject find(final UUID uuid) {
        this.userMap.computeIfAbsent(uuid, k -> new WyslannikObject(uuid));
        return this.userMap.get(uuid);
    }

    public void add(final WyslannikObject wyslannikObject) {
        this.userMap.put(wyslannikObject.getUuid(), wyslannikObject);
    }

    public ImmutableSet<WyslannikObject> getWyslannikObjects() {
        return ImmutableSet.copyOf(userMap.values());
    }
}
