package rpg.rpgcore.npc.metinolog;

import com.google.common.collect.ImmutableSet;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MetinologNPC {

    private final Map<UUID, MetinologObject> userMap;
    private final Map<Integer, String> killMissions = new HashMap<>();
    private final Map<Integer, String> giveMissions = new HashMap<>();

    public MetinologNPC(final RPGCORE rpgcore) {
        this.userMap = rpgcore.getMongoManager().loadAllMetinolog();
    }

    public void loadMissions() {
        killMissions.put(1, "1-10;10;1.5;0.5;0;0");
        killMissions.put(2, "10-20;20;1.5;0.5;0;0");
        killMissions.put(3, "20-30;35;1.5;0.5;0;0");
        killMissions.put(4, "30-40;50;1.5;0.5;0;0");
        killMissions.put(5, "40-50;65;1.5;0.5;0;0");
        killMissions.put(6, "50-60;80;1.5;0.5;0;0");
        killMissions.put(7, "60-70;95;1.5;0.5;0;0");
        killMissions.put(8, "70-80;110;1.5;0.5;0;0");
        killMissions.put(9, "80-90;130;1.5;0.5;0;0");
        killMissions.put(10, "90-100;150;1.5;0.5;0;0");
        killMissions.put(11, "100-110;170;1.5;0.5;0;0");
        killMissions.put(12, "110-120;190;1.5;0.5;0;0");
        killMissions.put(13, "120-130;210;2;1.5;0;0");

        giveMissions.put(1, "1-10;10;0;0;25;1.5");
        giveMissions.put(2, "10-20;20;0;0;25;1.5");
        giveMissions.put(3, "20-30;30;0;0;25;1.5");
        giveMissions.put(4, "30-40;40;0;0;25;1.5");
        giveMissions.put(5, "40-50;50;0;0;25;1.5");
        giveMissions.put(6, "50-60;60;0;0;25;1.5");
        giveMissions.put(7, "60-70;70;0;0;25;1.5");
        giveMissions.put(8, "70-80;80;0;0;25;1.5");
        giveMissions.put(9, "80-90;90;0;0;25;1.5");
        giveMissions.put(10, "90-100;100;0;0;25;1.5");
        giveMissions.put(11, "100-110;110;0;0;25;1.5");
        giveMissions.put(12, "110-120;120;0;0;25;1.5");
        giveMissions.put(13, "120-130;130;0;0;50;2");
    }

    public void openMetinologGUI(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 9, Utils.format("&6&lMetinolog"));
        final MetinologUser ms = this.find(player.getUniqueId()).getMetinologUser();
        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setName(" ").toItemStack());
        }

        gui.setItem(1, this.getKillMissionItem(ms));
        gui.setItem(4, this.getStatystyki(ms));
        gui.setItem(7, this.getGiveMissionItem(ms));

        player.openInventory(gui);
    }

    public ItemStack getKillMissionItem(final MetinologUser ms) {
        try {
            final String[] mission = killMissions.get(ms.getPostepKill() + 1).split(";");
            return new ItemBuilder(Material.DIAMOND_SWORD).setName("&6Misja #" + (ms.getPostepKill() + 1)).setLore(
                    Arrays.asList("&7Zniszcz &c" + mission[1] + " &7Kamieni Metin na mapie &c" + mission[0],
                            " ",
                            "&b&lNagroda",
                            "&7Srednia Odpornosc: &c" + mission[2],
                            "&7Przeszycie Bloku Ciosu: &c" + mission[3],
                            " ",
                            "&7Postep: &6" + ms.getPostepMisjiKill() + "&7/&6" + mission[1])).hideFlag().toItemStack().clone();
        } catch (final IndexOutOfBoundsException e) {
            return new ItemBuilder(Material.BARRIER).setName("&a&lUkonczono").setLore(Arrays.asList("&7Ukonczyles/as juz wszystkie dostepne",
                    "&7misje dla tego NPC!", " ", "&8Wiecej misji bedzie dostepnych wkrotce!")).toItemStack().clone();
        }
    }

    public ItemStack getGiveMissionItem(final MetinologUser ms) {
        try {
            final String[] mission = giveMissions.get(ms.getPostepGive() + 1).split(";");
            return new ItemBuilder(Material.PRISMARINE_SHARD).setName("&6Misja #" + (ms.getPostepGive() + 1)).setLore(
                    Arrays.asList("&7Przynies &c" + mission[1] + " &4Odlamkow Kamienia Metin " + mission[0],
                            " ",
                            "&b&lNagroda",
                            "&7Dodatkowe Obrazenia: &c" + mission[4],
                            "&7Odpornosc Przeciwko Ludziom: &c" + mission[5],
                            " ",
                            "&7Postep: &6" + ms.getPostepMisjiGive() + "&7/&6" + mission[1])).hideFlag().toItemStack().clone();
        } catch (final IndexOutOfBoundsException e) {
            return new ItemBuilder(Material.BARRIER).setName("&a&lUkonczono").setLore(Arrays.asList("&7Ukonczyles/as juz wszystkie dostepne",
                    "&7misje dla tego NPC!", " ", "&8Wiecej misji bedzie dostepnych wkrotce!")).toItemStack().clone();
        }
    }

    public ItemStack getStatystyki(final MetinologUser ms) {
        return new ItemBuilder(Material.PAPER).setName("&6&lStatystyki").setLore(Arrays.asList(
                "&7Dodatkowe Obrazenia: &c" + ms.getValue1(),
                "&7Srednia Odpornosc: &c" + ms.getValue2(),
                " ",
                "&7Przeszycie Bloku Ciosu: &c" + ms.getValue3(),
                "&7Odpornosc Przeciwko Ludziom: &c" + ms.getValue4())).hideFlag().toItemStack().clone();
    }

    public void add(MetinologObject metinologObject) {
        this.userMap.put(metinologObject.getID(), metinologObject);
    }

    public MetinologObject find(final UUID uuid) {
        this.userMap.computeIfAbsent(uuid, k -> new MetinologObject(uuid));
        return this.userMap.get(uuid);
    }

    public ImmutableSet<MetinologObject> getMetinologObject() {
        return ImmutableSet.copyOf(this.userMap.values());
    }

    public Map<Integer, String> getKillMissions() {
        return killMissions;
    }

    public Map<Integer, String> getGiveMissions() {
        return giveMissions;
    }

}
