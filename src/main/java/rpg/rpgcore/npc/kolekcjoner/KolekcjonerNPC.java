package rpg.rpgcore.npc.kolekcjoner;

import com.google.common.collect.ImmutableSet;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.GlobalItem;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.*;

public class KolekcjonerNPC {

    public KolekcjonerNPC(RPGCORE rpgcore) {
        this.userMap = rpgcore.getMongoManager().loadAllKolekcjoner();
    }

    // WAZNE MAPY
    private final Map<UUID, KolekcjonerObject> userMap;
    private final Map<Integer, String> missions = new HashMap<>(12);

    // ITEMY DO GUI
    private final ItemBuilder fill = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15);
    private final ItemBuilder statystyki = new ItemBuilder(Material.PAPER);
    private final ItemBuilder oddajITEMY = new ItemBuilder(Material.DISPENSER);

    public void loadMissions() {
        missions.put(0, "10;&7&lTestowy Patyk;2.5;2.5;1.5");
        missions.put(1, "20;&8Item10-20;3.5;2.5;1.5");
        missions.put(2, "30;Item20-30;4.5;2.5;1.5");
        missions.put(3, "40;Item30-40;1.5;2.5;1.5");
        missions.put(4, "50;Item40-50;3.5;2.5;1.5");
        missions.put(5, "60;Item50-60;4.5;2.5;1.5");
        missions.put(6, "70;Item60-80;2.5;2.5;1.5");
        missions.put(7, "80;Item80-90;3.5;2.5;1.5");
        missions.put(8, "90;Item90-100;5.5;2.5;1.5");
        missions.put(9, "100;Item100-110;2.5;2.5;1.5");
        missions.put(10, "110;Item110-120;1.5;2.5;1.5");
        missions.put(11, "120;Item120-130;3.5;2.5;1.5");
    }

    public final void openKolekcjonerGUI(Player player) {
        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&6&lKolekcjoner"));

        fill.setName(" ");
        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, fill.toItemStack());
        }

        gui.setItem(10, this.getPlayerStatystykiItem(player.getUniqueId()));
        gui.setItem(13, this.getOddajItemyItem(player.getUniqueId()));
        gui.setItem(16, this.getCurrentItemToDrop(player.getUniqueId()));

        player.openInventory(gui);
    }

    private ItemStack getPlayerStatystykiItem(final UUID uuid) {
        final List<String> lore = new ArrayList<>();
        final KolekcjonerUser user = this.find(uuid).getKolekcjonerUser();

        if (String.valueOf(user.getValue1()).contains(".0")) {
            lore.add("&8- &fSrednie Obrazenia&7: &b" + String.format("%.0f", user.getValue1()) + " %");
        } else  {
            lore.add("&8- &fSrednie Obrazenia&7: &b" + user.getValue1()+ " %");
        }
        if (String.valueOf(user.getValue2()).contains(".0")) {
            lore.add("&8- &fSrednie Obrona&7: &b" + String.format("%.0f", user.getValue2()) + " %");
        } else  {
            lore.add("&8- &fSrednie Obrona&7: &b" + user.getValue2() + " %");
        }
        if (String.valueOf(user.getValue3()).contains(".0")) {
            lore.add("&8- &fSzansa na Cios Krytyczny&7: &b" + String.format("%.0f", user.getValue3()) + " %");
        } else  {
            lore.add("&8- &fSzansa na Cios Krytyczny&7: &b" + user.getValue3() + " %");
        }

        return statystyki.setName("&b&lTwoje statystyki").setLore(lore).addGlowing().toItemStack().clone();
    }

    private ItemStack getOddajItemyItem(final UUID uuid) {
        final List<String> lore = new ArrayList<>();
        final KolekcjonerUser user = this.find(uuid).getKolekcjonerUser();
        final int postep = user.getMission();

        if (postep == 11) {
            return new ItemBuilder(Material.BARRIER).setName("&aUkonczono!").setLore(Arrays.asList("&7Ukonczyles/as juz wszystkie mozliwe misje", "&7u tego NPC!", " ", "&8Wiecej misji pojawi sie w krotce!")).addGlowing().toItemStack().clone();
        }

        final String[] misjaInfo = this.missions.get(postep).split(";");

        lore.add("&7Oddaj &cx" + misjaInfo[0] + " " + misjaInfo[1]);
        lore.add(" ");
        lore.add("&f&lNAGRODA");
        lore.add("&8- &fSrednie Obrazenia&7: &f" + misjaInfo[2]);
        lore.add("&8- &fSrednia Odpornosc&7: &f" + misjaInfo[3]);
        lore.add("&8- &fSzansa na cios Krytyczny&7: &f" + misjaInfo[4]);
        lore.add(" ");
        lore.add("&7Postep: &c" + user.getMissionProgress() + "&7/ &c" + misjaInfo[0] + " &8(&c" + String.format("%.2f", Utils.convertDoublesToPercentage(user.getMissionProgress(), Double.parseDouble(misjaInfo[0]))) + " &c%&8)");

        return oddajITEMY.setName("&c&lAktualna Misja #" + postep).setLore(lore).addGlowing().setAmount(postep).toItemStack().clone();
    }

    private ItemStack getCurrentItemToDrop(final UUID uuid) {
        final KolekcjonerUser user = this.find(uuid).getKolekcjonerUser();
        final int postep = user.getMission();

        ItemStack currentItem;

        switch (postep) {
            case 1:
                currentItem = GlobalItem.getItem("I21", 1).clone();
                final ItemMeta im = currentItem.getItemMeta();
                final List<String> lore = im.getLore();

                lore.add(" ");
                lore.add(Utils.format("&4&lMOZNA ZDOBYC Z MAPY 1-10!"));
                im.setLore(lore);
                currentItem.setItemMeta(im);
                return currentItem;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                break;
            case 9:
                break;
            case 10:
                break;
            case 11:
                break;
            case 12:
                break;
        }

        return null;
    }

    public ItemStack getRequiredItem(final int currentMission) {
        switch (currentMission) {
            case 1:
                return GlobalItem.getItem("I21", 1);
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                break;
            case 9:
                break;
            case 10:
                break;
            case 11:
                break;
            case 12:
                break;
        }

        return null;
    }



    public void updatePostepMisji(final UUID uuid, final int postep) {
        final KolekcjonerUser user = this.find(uuid).getKolekcjonerUser();
        user.setMissionProgress(user.getMissionProgress() + postep);
        final int currentMission = user.getMission();
        final String[] missionInfo = this.missions.get(currentMission).split(";");
        if (user.getMissionProgress() >= Integer.parseInt(missionInfo[0])) {
            user.setMission(user.getMission() + 1);
            user.setMissionProgress(0);
            user.setValue1(user.getValue1() + Double.parseDouble(missionInfo[2]));
            user.setValue2(user.getValue2() + Double.parseDouble(missionInfo[3]));
            user.setValue3(user.getValue3() + Double.parseDouble(missionInfo[4]));
        }
    }

    public void add(KolekcjonerObject kolekcjonerObject) {
        this.userMap.put(kolekcjonerObject.getID(), kolekcjonerObject);
    }

    public KolekcjonerObject find(final UUID uuid) {
        this.userMap.computeIfAbsent(uuid, k -> new KolekcjonerObject(uuid));
        return this.userMap.get(uuid);
    }

    public ImmutableSet<KolekcjonerObject> getKolekcjonerObject() {
        return ImmutableSet.copyOf(this.userMap.values());
    }

    public boolean isKolekcjonerObject(final UUID string) {
        return this.userMap.containsKey(string);
    }
}
