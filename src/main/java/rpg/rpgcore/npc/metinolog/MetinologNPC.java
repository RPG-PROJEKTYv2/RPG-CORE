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
    private final RPGCORE rpgcore;

    public MetinologNPC(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
        this.userMap = rpgcore.getMongoManager().loadAllMetinolog();
    }

    public void loadMissions() {
        killMissions.put(1, "1-10;10;0.5;0.5;0;0");
        killMissions.put(2, "10-20;20;1;1;0;0");
        killMissions.put(3, "20-30;35;1;1;0;0");
        killMissions.put(4, "30-40;50;1;1;0;0");
        killMissions.put(5, "40-50;65;1.5;1.5;0;0");
        killMissions.put(6, "50-60;80;1.5;1.5;0;0");
        killMissions.put(7, "60-70;95;1.5;1.5;0;0");
        killMissions.put(8, "70-80;110;2;2;0;0");
        killMissions.put(9, "80-90;130;3;3;0;0");
        killMissions.put(10, "90-100;150;3.5;3.5;0;0");
        killMissions.put(11, "100-110;170;3.5;3.5;0;0");
        killMissions.put(12, "110-120;190;4;4;0;0");
        killMissions.put(13, "120-130;210;4.5;4.5;0;0");

        giveMissions.put(1, "1-10;20;0;0;15;0.5");
        giveMissions.put(2, "10-20;40;0;0;25;1");
        giveMissions.put(3, "20-30;60;0;0;25;1");
        giveMissions.put(4, "30-40;80;0;0;25;1");
        giveMissions.put(5, "40-50;100;0;0;50;1.5");
        giveMissions.put(6, "50-60;120;0;0;50;1.5");
        giveMissions.put(7, "60-70;140;0;0;75;1.5");
        giveMissions.put(8, "70-80;160;0;0;100;2");
        giveMissions.put(9, "80-90;180;0;0;150;3");
        giveMissions.put(10, "90-100;200;0;0;255;3.5");
        giveMissions.put(11, "100-110;220;0;0;330;3.5");
        giveMissions.put(12, "110-120;240;0;0;370;4");
        giveMissions.put(13, "120-130;260;0;0;430;4.5");
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

    public boolean isMetinologObject(final UUID string) {
        return this.userMap.containsKey(string);
    }

    public Map<Integer, String> getKillMissions() {
        return killMissions;
    }

    public Map<Integer, String> getGiveMissions() {
        return giveMissions;
    }

}
