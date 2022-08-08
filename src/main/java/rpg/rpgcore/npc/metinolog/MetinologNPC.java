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
        killMissions.put(1, "1-10;10;1;1;0;0");
        giveMissions.put(1, "1-10;10;0;0;0.5;0.5");
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
                            "&7Silny przeciwko Potworom: &c" + mission[2],
                            "&7Odpornosc na Potwory: &c" + mission[3],
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
            return new ItemBuilder(Material.NETHER_STAR).setName("&6Misja #" + (ms.getPostepGive() + 1)).setLore(
                    Arrays.asList("&7Przynies &c" + mission[1] + " &4Odlamkow Kamienia Metin " + mission[0],
                            " ",
                            "&b&lNagroda",
                            "&7Silny przeciwko Ludziom: &c" + mission[4],
                            "&7Odpornosc na Ludzi: &c" + mission[5],
                            " ",
                            "&7Postep: &6" + ms.getPostepMisjiGive() + "&7/&6" + mission[1])).hideFlag().toItemStack().clone();
        } catch (final IndexOutOfBoundsException e) {
            return new ItemBuilder(Material.BARRIER).setName("&a&lUkonczono").setLore(Arrays.asList("&7Ukonczyles/as juz wszystkie dostepne",
                    "&7misje dla tego NPC!", " ", "&8Wiecej misji bedzie dostepnych wkrotce!")).toItemStack().clone();
        }
    }

    public ItemStack getStatystyki(final MetinologUser ms) {
        return new ItemBuilder(Material.PAPER).setName("&6&lStatystyki").setLore(Arrays.asList("&7Silny przeciwko Potworom: &c" + ms.getValue1(),
                "&7Odpornosc na Potwory: &c" + ms.getValue2(), " ", "&7Silny przeciwko Ludziom: &c" + ms.getValue3(),
                "&7Odpornosc na Ludzi: &c" + ms.getValue4())).hideFlag().toItemStack().clone();
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
