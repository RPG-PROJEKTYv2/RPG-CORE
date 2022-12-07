package rpg.rpgcore.npc.magazynier.enums;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.GlobalItem;
import rpg.rpgcore.utils.globalitems.npc.RybakItems;

import java.util.Arrays;

public enum MagazynierMissions {
    M1(1, new ItemBuilder(Material.BOOK).setName("&c&lMistrz Polowow").setLore(Arrays.asList(
            "&7Wykonaj &e200 &7udanych polowow!",
            "",
            "&f&lNAGRODA",
            "&8- &b30 punktow",
            "&8- &a&lKufer Rybacki")).toItemStack(), 200, 30, RybakItems.getByName("I11").getItemStack()),
    M2(2, new ItemBuilder(Material.BOOK).setName("&c&lKolekcjoner").setLore(Arrays.asList(
            "&7Oddaj &e450 &7skrzyn &8(ogolnie)",
            "",
            "&f&lNAGRODA",
            "&8- &b50 punktow",
            "&8- &6x2 &f&lSkrzynia Kowala")).toItemStack(), 450, 50, GlobalItem.getItem("I2", 2)),
    M3(3, new ItemBuilder(Material.BOOK).setName("&c&lPogromca Bossow").setLore(Arrays.asList(
            "&7Zabij &e10 &fNAZWA BOSSA 3-4 &8(Mapa 30-40)",
            "",
            "&f&lNAGRODA",
            "&8- &b75 punktow")).toItemStack(), 10, 75, null),
    M4(4, new ItemBuilder(Material.BOOK).setName("&c&lSpecjalista od Katakumb").setLore(Arrays.asList(
            "&7Ukoncz &e5 &7dungeonow&7! &8(ogolnie)",
            "",
            "&f&lNAGRODA",
            "&8- &b150 punktow",
            "&8- &625 000 000 &2$"
    )).toItemStack(), 5, 150, new ItemBuilder(Material.DOUBLE_PLANT).setName("&eCzek na &6&l" + Utils.spaceNumber(Utils.kasaFormat.format(25000000)) + " &2$").addGlowing().toItemStack()),
    M5(5, new ItemBuilder(Material.BOOK).setName("&c&lKrysztalowy Niszczyciel").setLore(Arrays.asList(
            "&7Zniszcz &e100 &7krysztalow metin",
            "",
            "&f&lNAGRODA",
            "&8- &b50 punktow"
    )).toItemStack(), 100, 50, null);
    private final int id;
    private final ItemStack missionItem;
    private final double reqAmount;
    private final int points;
    private final ItemStack itemReward;

    MagazynierMissions(final int id, final ItemStack missionItem, final double reqAmount, final int points, final ItemStack itemReward) {
        this.id = id;
        this.missionItem = missionItem;
        this.reqAmount = reqAmount;
        this.points = points;
        this.itemReward = itemReward;
    }

    public int getId() {
        return this.id;
    }

    public ItemStack getMissionItem() {
        return this.missionItem;
    }

    public double getReqAmount() {
        return this.reqAmount;
    }

    public int getPoints() {
        return this.points;
    }

    public ItemStack getItemReward() {
        return this.itemReward;
    }

    public static MagazynierMissions getMissionById(final int id) {
        for (final MagazynierMissions mission : values()) {
            if (mission.getId() == id) {
                return mission;
            }
        }
        return null;
    }
}
