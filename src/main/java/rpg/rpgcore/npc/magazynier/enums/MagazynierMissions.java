package rpg.rpgcore.npc.magazynier.enums;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.DoubleUtils;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.expowiska.SkrzynkiOther;
import rpg.rpgcore.utils.globalitems.npc.RybakItems;

import java.util.Arrays;

public enum MagazynierMissions {
    M1(1, new ItemBuilder(Material.BOOK).setName("&c&lMistrz Polowow").setLore(Arrays.asList(
            "&7Wykonaj &e200 &7udanych polowow!",
            "",
            "&f&lNAGRODA",
            "&8- &b30 punktow",
            "&8- &a&lKufer Rybacki")).addTagInt("mission", 1).toItemStack(), 200, 30, RybakItems.I5.getItemStack()),
    M2(2, new ItemBuilder(Material.BOOK).setName("&c&lKolekcjoner").setLore(Arrays.asList(
            "&7Otworz &e450 &7skrzyn",
            "",
            "&f&lNAGRODA",
            "&8- &b50 punktow",
            "&8- &6x2 &7&lCiezka Skrzynia Kowala")).addTagInt("mission", 2).toItemStack(), 450, 50, new ItemBuilder(SkrzynkiOther.I2.getItemStack().clone()).setAmount(2).toItemStack().clone()),
    M3(3, new ItemBuilder(Material.BOOK).setName("&c&lPogromca Bossow").setLore(Arrays.asList(
            "&7Zabij &e10 &8&l[&4&lBOSS&8&l] &7&lPrzekleta Dusza &8(Mapa 30-40)",
            "",
            "&f&lNAGRODA",
            "&8- &b75 punktow")).addTagInt("mission", 3).toItemStack(), 10, 75, null),
    M4(4, new ItemBuilder(Material.BOOK).setName("&c&lSpecjalista od Katakumb").setLore(Arrays.asList(
            "&7Ukoncz &e3 &7dungeony",
            "",
            "&f&lNAGRODA",
            "&8- &b150 punktow",
            "&8- &625 000 000 &2$"
    )).addTagInt("mission", 4).toItemStack(), 3, 150, new ItemBuilder(Material.DOUBLE_PLANT).setName("&eCzek na &6&l" + Utils.spaceNumber(Utils.kasaFormat.format(25000000)) + " &2$").addGlowing().toItemStack()),
    M5(5, new ItemBuilder(Material.BOOK).setName("&c&lKrysztalowy Niszczyciel").setLore(Arrays.asList(
            "&7Zniszcz &e100 &7krysztalow metin",
            "",
            "&f&lNAGRODA",
            "&8- &b50 punktow"
    )).addTagInt("mission", 5).toItemStack(), 100, 50, null),
    M6(6, new ItemBuilder(Material.BOOK).setName("&c&lHandlowiec").setLore(Arrays.asList(
            "&7Sprzedaj &e50 &7przedmiotow u &a&lHandlarza",
            "",
            "&f&lNAGRODA",
            "&8- &b80 punktow",
            "&8- &e&lPozlacany Skarb"
    )).addTagInt("mission", 6).toItemStack(), 50, 80, SkrzynkiOther.getItem("I1", 1)),
    M7(7, new ItemBuilder(Material.BOOK).setName("&c&lPierwszy Milion Trzeba Ukrasc").setLore(Arrays.asList(
            "&7Zarob &e1 000 000&2$ &8(moby, metiny, handlarz)",
            "",
            "&f&lNAGRODA",
            "&8- &b100 punktow"
    )).addTagInt("mission", 7).toItemStack(), 1000000, 100, null),
    M8(8, new ItemBuilder(Material.BOOK).setName("&c&lZawodowy Kopacz").setLore(Arrays.asList(
            "&7Wykop &e100 &7rud w &6Kopalni Gornika",
            "",
            "&f&lNAGRODA",
            "&8- &b75 punktow"
    )).addTagInt("mission", 8).toItemStack(), 100, 75, null),
    M9(9, new ItemBuilder(Material.BOOK).setName("&c&lBywalec").setLore(Arrays.asList(
            "&7Spedz &e3 godziny &7na serwerze",
            "",
            "&f&lNAGRODA",
            "&8- &b20 punktow"
    )).addTagInt("mission", 9).toItemStack(), 10_800_000, 20, null),
    M10(10, new ItemBuilder(Material.BOOK).setName("&c&lPrawdziwy Lodowy Sluga").setLore(Arrays.asList(
            "&7Ukoncz &e3 &7lodowe wieze",
            "",
            "&f&lNAGRODA",
            "&8- &b10 punktow",
            "&8- &7&lCiezka Skrzynia Kowala"
    )).addTagInt("mission", 10).toItemStack(), 3, 10, SkrzynkiOther.getItem("I2", 1)),
    M11(11, new ItemBuilder(Material.BOOK).setName("&c&lZabojca").setLore(Arrays.asList(
            "&7Zabij &e10 &7graczy",
            "",
            "&f&lNAGRODA",
            "&8- &b50 punktow"
    )).addTagInt("mission", 11).toItemStack(), 10, 50, null),
    M12(12, new ItemBuilder(Material.BOOK).setName("&c&lPogromca Stworow").setLore(Arrays.asList(
            "&7Zabij &e10 000 &7potworow",
            "",
            "&f&lNAGRODA",
            "&8- &b30 punktow",
            "&8- &e&lPozlacany Skarb"
    )).addTagInt("mission", 12).toItemStack(), 10_000, 30, SkrzynkiOther.getItem("I1", 1));
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
        return DoubleUtils.round(this.reqAmount, 2);
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

    public static int getMissionAmount() {
        return values().length;
    }
}
