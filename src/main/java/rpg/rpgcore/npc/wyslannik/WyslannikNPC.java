package rpg.rpgcore.npc.wyslannik;

import com.google.common.collect.ImmutableSet;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
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
        if (user.getKillMobsMission() == 9) {
            gui.setItem(0, new ItemBuilder(Material.BARRIER).setName("&a&lWykonane!").setLore(Arrays.asList(
                    "&7Ukonczyles/-as juz wszystkie mozliwe",
                    "&7misje w tej kategorii."
            )).toItemStack());
        } else {
            gui.setItem(0, new ItemBuilder(Material.IRON_SWORD).setName("&c&lMisja " + user.getKillMobsMission()).setLore(Arrays.asList(
                    "&7Zabij &6" + (Math.max(missionKillMob.getReqAmount() - user.getKillMobsMissionProgress(), 0)),
                    "&8- " + missionKillMob.getMobName(),
                    "&f&lNAGRODA",
                    "&8- " + missionKillMob.getReward().getItemMeta().getDisplayName()
            )).hideFlag().toItemStack().clone());
        }
        if (user.getKillBossMission() == 9) {
            gui.setItem(2, new ItemBuilder(Material.BARRIER).setName("&a&lWykonane!").setLore(Arrays.asList(
                    "&7Ukonczyles/-as juz wszystkie mozliwe",
                    "&7misje w tej kategorii."
            )).toItemStack());
        } else {
            gui.setItem(2, new ItemBuilder(Material.DIAMOND_SWORD).setName("&c&lMisja " + user.getKillBossMission()).setLore(Arrays.asList(
                    "&7Zabij &6" + (Math.max(missionKillBoss.getReqAmount() - user.getKillBossMissionProgress(), 0)),
                    "&8- " + missionKillBoss.getMobName(),
                    "&f&lNAGRODA",
                    "&8- " + missionKillBoss.getReward().getItemMeta().getDisplayName()
            )).hideFlag().toItemStack().clone());
        }

        if (user.getOpenChestMission() == 9) {
            gui.setItem(4, new ItemBuilder(Material.BARRIER).setName("&a&lWykonane!").setLore(Arrays.asList(
                    "&7Ukonczyles/-as juz wszystkie mozliwe",
                    "&7misje w tej kategorii."
            )).toItemStack());
        } else {
            gui.setItem(4, new ItemBuilder(Material.CHEST).setName("&c&lMisja " + user.getOpenChestMission()).setLore(Arrays.asList(
                    "&7Otworz &6" + (Math.max(missionOpen.getReqAmount() - user.getOpenChestMissionProgress(), 0)),
                    "&7- " + missionOpen.getChestName(),
                    "&f&lNAGRODA",
                    "&8- " + missionOpen.getReward().getItemMeta().getDisplayName()
            )).hideFlag().toItemStack().clone());
        }

        gui.setItem(1, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (byte) 15).setName(" ").toItemStack());
        gui.setItem(3, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (byte) 15).setName(" ").toItemStack());

        player.openInventory(gui);
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
