package rpg.rpgcore.npc.metinolog;

import com.google.common.collect.ImmutableSet;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.npc.metinolog.enums.MetinologMissionGive;
import rpg.rpgcore.npc.metinolog.enums.MetinologMissionKill;
import rpg.rpgcore.npc.metinolog.objects.MetinologObject;
import rpg.rpgcore.npc.metinolog.objects.MetinologUser;
import rpg.rpgcore.utils.DoubleUtils;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

public class MetinologNPC {

    private final Map<UUID, MetinologObject> userMap;
    public MetinologNPC(final RPGCORE rpgcore) {
        this.userMap = rpgcore.getMongoManager().loadAllMetinolog();
    }

    public void openMetinologGUI(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 9, Utils.format("&6&lMetinolog"));
        final MetinologUser ms = this.find(player.getUniqueId()).getMetinologUser();
        int dmgMetiny = 0;
        for (int i = 1; i < 13; i++) {
            if (ms.getPostepKill() >= i && ms.getPostepGive() >= i) {
                dmgMetiny++;
            } else {
                break;
            }
        }

        if (ms.getDmgMetiny() != dmgMetiny) ms.setDmgMetiny(dmgMetiny);

        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setName(" ").toItemStack());
        }

        gui.setItem(1, this.getKillMissionItem(ms));
        gui.setItem(4, this.getStatystyki(ms));
        gui.setItem(7, this.getGiveMissionItem(ms));

        player.openInventory(gui);
    }

    public ItemStack getKillMissionItem(final MetinologUser ms) {
        final MetinologMissionKill killMission = MetinologMissionKill.getMission(ms.getPostepKill());
        if (killMission != null) {
            return new ItemBuilder(Material.DIAMOND_SWORD).setName("&6Misja #" + (ms.getPostepKill() + 1)).setLore(
                    Arrays.asList("&7Zniszcz &c" + killMission.getReqAmount() + " &7Kamieni Metin na mapie &c" + killMission.getMapa(),
                            " ",
                            "&b&lNagroda",
                            "&7Srednia Odpornosc: &c" + killMission.getSrOdpo(),
                            "&7Przeszycie Bloku Ciosu: &c" + killMission.getPrzeszycie(),
                            " ",
                            "&7Postep: &6" + ms.getPostepMisjiKill() + "&7/&6" + killMission.getReqAmount() + " &7(&6" + DoubleUtils.round(((double) ms.getPostepMisjiKill() / killMission.getReqAmount()) * 100, 2) + "%&7)"
                    )).hideFlag().toItemStack().clone();
        } else {
            return new ItemBuilder(Material.BARRIER).setName("&a&lUkonczono").setLore(Arrays.asList("&7Ukonczyles/as juz wszystkie dostepne",
                    "&7misje dla tego NPC!", " ", "&8Wiecej misji bedzie dostepnych wkrotce!")).toItemStack().clone();
        }
    }

    public ItemStack getGiveMissionItem(final MetinologUser ms) {
        final MetinologMissionGive giveMission = MetinologMissionGive.getMission(ms.getPostepGive());
        if (giveMission != null) {
            return new ItemBuilder(Material.PRISMARINE_SHARD).setName("&6Misja #" + (ms.getPostepGive() + 1)).setLore(
                    Arrays.asList("&7Przynies &c" + giveMission.getReqAmount() + " &4Odlamkow Kamienia Metin " + giveMission.getMapa(),
                            " ",
                            "&b&lNagroda",
                            "&7Dodatkowe Obrazenia: &c" + giveMission.getDodatkoweDmg(),
                            "&7Srednia Odpornosc: &c" + giveMission.getSrOdpo(),
                            " ",
                            "&7Postep: &6" + ms.getPostepMisjiGive() + "&7/&6" + giveMission.getReqAmount() + " &7(&6" + DoubleUtils.round(((double) ms.getPostepMisjiGive() / giveMission.getReqAmount()) * 100, 2) + "%&7)"
                    )).hideFlag().toItemStack().clone();
        } else {
            return new ItemBuilder(Material.BARRIER).setName("&a&lUkonczono").setLore(Arrays.asList("&7Ukonczyles/as juz wszystkie dostepne",
                    "&7misje dla tego NPC!", " ", "&8Wiecej misji bedzie dostepnych wkrotce!")).toItemStack().clone();
        }
    }

    public ItemStack getStatystyki(final MetinologUser ms) {
        return new ItemBuilder(Material.PAPER).setName("&6&lStatystyki").setLore(Arrays.asList(
                "&7Dodatkowe Obrazenia: &c" + ms.getDodatkowedmg(),
                "&7Srednia Odpornosc: &c" + ms.getSrOdpo(),
                "&7Przeszycie Bloku Ciosu: &c" + ms.getPrzeszycie(),
                "&7Obrazenia w Kamienie Metin: &c" + ms.getDmgMetiny()
                )).hideFlag().toItemStack().clone();
    }

    public void add(MetinologObject metinologObject) {
        this.userMap.put(metinologObject.getID(), metinologObject);
    }

    public void set(final UUID uuid, final MetinologObject metinologObject) {
        this.userMap.replace(uuid, metinologObject);
    }

    public MetinologObject find(final UUID uuid) {
        this.userMap.computeIfAbsent(uuid, k -> new MetinologObject(uuid));
        return this.userMap.get(uuid);
    }

    public ImmutableSet<MetinologObject> getMetinologObject() {
        return ImmutableSet.copyOf(this.userMap.values());
    }
}
