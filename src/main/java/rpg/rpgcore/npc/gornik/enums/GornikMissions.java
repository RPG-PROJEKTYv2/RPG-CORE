package rpg.rpgcore.npc.gornik.enums;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.DoubleUtils;
import rpg.rpgcore.utils.ItemBuilder;

import java.util.Arrays;

public enum GornikMissions {

    M1(1, new ItemBuilder(Material.BOOK).setName("&7Misja &c1").setLore(Arrays.asList(
            "&7Wykop &c64 &8Rudy Wegla",
            "&f&lNagroda:",
            " &a+5m &7czasu kopalni"
    )).toItemStack().clone(), 64, 0,0,300_000),
    M2(2, new ItemBuilder(Material.BOOK).setName("&7Misja &c2").setLore(Arrays.asList(
            "&7Wykop &c64 &7Rudy Zelaza",
            "&f&lNagroda:",
            " &a+5m &7czasu kopalni"
    )).toItemStack().clone(), 64, 0,0,300_000),
    M3(3, new ItemBuilder(Material.BOOK).setName("&7Misja &c3").setLore(Arrays.asList(
            "&7Wykop pomyslnie &c32 &8Rudy Wegla",
            "&f&lNagroda:",
            " &a+4m 10s &7czasu kopalni"
    )).toItemStack().clone(), 32, 0,0,250_000),
    M4(4, new ItemBuilder(Material.BOOK).setName("&7Misja &c4").setLore(Arrays.asList(
            "&7Wykop pomyslnie &c32 &7Rudy Zelaza",
            "&f&lNagroda:",
            " &a+4m 10s &7czasu kopalni"
    )).toItemStack().clone(), 32, 0,0,250_000),
    M5(5, new ItemBuilder(Material.BOOK).setName("&7Misja &c5").setLore(Arrays.asList(
            "&7Osiagnij &c5 &7poziom &6Kilofu Gornika",
            "&f&lNagroda:",
            " &a+2m 30s &7czasu kopalni",
            " &c+5% silny na ludzi"
    )).addTagInt("reqPickaxeLvl", 5).toItemStack().clone(), 1, 5,0,150_000),
    M6(6, new ItemBuilder(Material.BOOK).setName("&7Misja &c6").setLore(Arrays.asList(
            "&7Znajdz &c1 &aZakopana Skrzynie",
            "&f&lNagroda:",
            " &a+4m 10s &7czasu kopalni",
            " &2+5% defensywa na potwory"
    )).toItemStack().clone(), 1, 0,5,250_000),
    M7(7, new ItemBuilder(Material.BOOK).setName("&7Misja &c7").setLore(Arrays.asList(
            "&7Wykop &c128 &eRudy Topazu",
            "&f&lNagroda:",
            " &a+4m 10s &7czasu kopalni"
    )).toItemStack().clone(), 128, 0,0,250_000),
    M8(8, new ItemBuilder(Material.BOOK).setName("&7Misja &c8").setLore(Arrays.asList(
            "&7Wykop &c128 &1Rudy Tanzanitu",
            "&f&lNagroda:",
            " &a+4m 10s &7czasu kopalni"
    )).toItemStack().clone(), 128, 0,0,250_000),
    M9(9, new ItemBuilder(Material.BOOK).setName("&7Misja &c9").setLore(Arrays.asList(
            "&7Znajdz &c16 &7Skrzyn Gornika",
            "&f&lNagroda:",
            " &a+4m 10s &7czasu kopalni"
    )).toItemStack().clone(), 16, 0,0,250_000),
    M10(10, new ItemBuilder(Material.BOOK).setName("&7Misja &c10").setLore(Arrays.asList(
            "&7Otworz &c32 &7Skrzynie Gornika",
            "&f&lNagroda:",
            " &c+5% silny na ludzi"
    )).toItemStack().clone(), 32, 5,0,0),
    M11(11, new ItemBuilder(Material.BOOK).setName("&7Misja &c11").setLore(Arrays.asList(
            "&7Otrzymaj &c5 &7razy efekt &eHaste I &7po wydobyciu rudy",
            "&f&lNagroda:",
            " &a+55s &7czasu kopalni",
            " &c+8% silny na ludzi"
    )).toItemStack().clone(), 5, 8,0,55_000),
    M12(12, new ItemBuilder(Material.BOOK).setName("&7Misja &c12").setLore(Arrays.asList(
            "&7Znajdz &c5 &aZakopanych Skrzyn",
            "&f&lNagroda:",
            " &a+4m 5s &7czasu kopalni",
            " &2+8% defensywa na potwory"
    )).toItemStack().clone(), 5, 0,8,245_000),
    M13(13, new ItemBuilder(Material.BOOK).setName("&7Misja &c13").setLore(Arrays.asList(
            "&7Wykop pomyslnie &c256 &8Rudy Wegla",
            "&f&lNagroda:",
            " &a+1m 40s &7czasu kopalni"
    )).toItemStack().clone(), 256, 0,0,100_000),
    M14(14, new ItemBuilder(Material.BOOK).setName("&7Misja &c14").setLore(Arrays.asList(
            "&7Wykop pomyslnie &c256 &7Rudy Zelaza",
            "&f&lNagroda:",
            " &a+1m 40s &7czasu kopalni"
    )).toItemStack().clone(), 256, 0,0,100_000),
    M15(15, new ItemBuilder(Material.BOOK).setName("&7Misja &c15").setLore(Arrays.asList(
            "&7Osiagnij &c15 &7poziom &6Kilofu Gornika",
            "&f&lNagroda:",
            " &a+4m 10s &7czasu kopalni",
            " &c+8% silny na ludzi"
    )).addTagInt("reqPickaxeLvl", 15).toItemStack().clone(), 1, 8,0,250_000),
    M16(16, new ItemBuilder(Material.BOOK).setName("&7Misja &c16").setLore(Arrays.asList(
            "&7Wykop &c256 &aRuda Jadeitu",
            "&f&lNagroda:",
            " &a+2m 30s &7czasu kopalni"
    )).toItemStack().clone(), 256, 0,0,150_000),
    M17(17, new ItemBuilder(Material.BOOK).setName("&7Misja &c17").setLore(Arrays.asList(
            "&7Wykop &c256 &bRuda Diamentu",
            "&f&lNagroda:",
            " &a+2m 30s &7czasu kopalni"
    )).toItemStack().clone(), 256, 0,0,150_000),
    M18(18, new ItemBuilder(Material.BOOK).setName("&7Misja &c18").setLore(Arrays.asList(
            "&7Osiagnij &c20 &7poziom &6Kilofu Gornika",
            "&f&lNagroda:",
            " &2+8% defensywa na potwory"
    )).addTagInt("reqPickaxeLvl", 20).toItemStack().clone(), 1, 0,8,0),
    M19(19, new ItemBuilder(Material.BOOK).setName("&7Misja &c19").setLore(Arrays.asList(
            "&7Wykop pomyslnie &c512 &eRudy Topazu",
            "&f&lNagroda:",
            " &a+2m 30s &7czasu kopalni"
    )).toItemStack().clone(), 512, 0,0,150_000),
    M20(20, new ItemBuilder(Material.BOOK).setName("&7Misja &c20").setLore(Arrays.asList(
            "&7Wykop pomyslnie &c512 &1Ruda Tanzanitu",
            "&f&lNagroda:",
            " &a+2m 30s &7czasu kopalni"
    )).toItemStack().clone(), 512, 0,0,150_000),
    M21(21, new ItemBuilder(Material.BOOK).setName("&7Misja &c21").setLore(Arrays.asList(
            "&7Otrzymaj &c20 &7razy efekt &eHaste I &7po wydobyciu rudy",
            "&f&lNagroda:",
            " &a+1m 40s &7czasu kopalni",
            " &2+12% defensywa na potwory"
    )).toItemStack().clone(), 20, 0,12,100_000),
    M22(22, new ItemBuilder(Material.BOOK).setName("&7Misja &c22").setLore(Arrays.asList(
            "&7Znajdz &c25 &aZakopanych Skrzyn",
            "&f&lNagroda:",
            " &a+1m 40s &7czasu kopalni",
            " &c+8% silny na ludzi"
    )).toItemStack().clone(), 25, 8,0,100_000),
    M23(23, new ItemBuilder(Material.BOOK).setName("&7Misja &c23").setLore(Arrays.asList(
            "&7Znajdz &c48 &7Skrzyn Gornika",
            "&f&lNagroda:",
            " &a+1m 40s &7czasu kopalni"
    )).toItemStack().clone(), 48, 0,0,100_000),
    M24(24, new ItemBuilder(Material.BOOK).setName("&7Misja &c24").setLore(Arrays.asList(
            "&7Otworz &c96 &7Skrzyn Gornika",
            "&f&lNagroda:",
            " &2+5% defensywa na potwory"
    )).toItemStack().clone(), 96, 0,5,0),
    M25(25, new ItemBuilder(Material.BOOK).setName("&7Misja &c25").setLore(Arrays.asList(
            "&7Wykop &c1024 &cRudy Rubinu",
            "&f&lNagroda:",
            " &a+2m &7czasu kopalni"
    )).toItemStack().clone(), 1_024, 0,0,120_000),
    M26(26, new ItemBuilder(Material.BOOK).setName("&7Misja &c26").setLore(Arrays.asList(
            "&7Znajdz &c32 &aZakopane Skrzynie",
            "&f&lNagroda:",
            " &a+2m &7czasu kopalni",
            " &c+4% silny na ludzi"
    )).toItemStack().clone(), 32, 4,0,120_000),
    M27(27, new ItemBuilder(Material.BOOK).setName("&7Misja &c27").setLore(Arrays.asList(
            "&7Otrzymaj &c25 &7razy efekt &eHaste I &7po wydobyciu rudy",
            "&f&lNagroda:",
            " &c+2% silny na ludzi",
            " &2+2% defensywa na potwory"
    )).toItemStack().clone(), 25, 2,2,0),
    M28(28, new ItemBuilder(Material.BOOK).setName("&7Misja &c28").setLore(Arrays.asList(
            "&7Osiagnij &c30 &7poziom &6Kilofu Gornika",
            "&f&lNagroda:",
            " &a+6m &7czasu kopalni"
    )).addTagInt("reqPickaxeLvl", 30).toItemStack().clone(), 1, 0,0,360_000),
    M99(99, null, 0,0,0,0);


    private final int id;
    private final ItemStack item;
    private final int reqAmount;
    private final double silnyNaLudzi, defNaMoby;
    private final long bonusTime;

    GornikMissions(final int id, final ItemStack item, final int reqAmount, final double silnyNaLudzi, final double defNaMoby, final long bonusTime) {
        this.id = id;
        this.item = item;
        this.reqAmount = reqAmount;
        this.silnyNaLudzi = silnyNaLudzi;
        this.defNaMoby = defNaMoby;
        this.bonusTime = bonusTime;
    }

    public int getId() {
        return id;
    }

    public ItemStack getItem() {
        return item;
    }

    public int getReqAmount() {
        return reqAmount;
    }

    public double getSilnyNaLudzi() {
        return DoubleUtils.round(silnyNaLudzi, 2);
    }

    public double getDefNaMoby() {
        return DoubleUtils.round(defNaMoby, 2);
    }

    public long getBonusTime() {
        return bonusTime;
    }

    public static GornikMissions getById(final int id) {
        for (final GornikMissions gornikMissions : values()) {
            if (gornikMissions.getId() == id) {
                return gornikMissions;
            }
        }
        return M99;
    }
}
