package rpg.rpgcore.utils.globalitems;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import rpg.rpgcore.npc.mistyczny_kowal.SwordType;
import rpg.rpgcore.utils.DoubleUtils;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.ItemHelper;
import rpg.rpgcore.utils.Utils;

import java.util.Arrays;
import java.util.Objects;

public enum GlobalItem {
    I_FRAGMENT_BONA("I_FRAGMENT_BONA", new ItemBuilder(Material.BOWL).setName("&3&lFragment Tajemniczego Bona").setLore(Arrays.asList("", "&7Wartosc nie do opisania...")).toItemStack().clone()),
    I_FRAGMENT_STALI("I_FRAGMENT_STALI", new ItemBuilder(Material.PRISMARINE_CRYSTALS).setName("&8&lFragment Stali Kowala").toItemStack().clone()),
    I_METAL("I_METAL", new ItemBuilder(Material.IRON_INGOT).setName("&7&lStal Kowalska").setLore(Arrays.asList("&dMagiczny &8przedmiot uzywany do wykucia", "&8najlepszych ostrzy, jakie widzial ten swiat.", "&8Nie tylko &achroni &8twoj przedmiot przed &cspaleniem", "&8ale daje ci szanse na &apewne", "&8ulepszenie twojego przedmiotu")).addGlowing().toItemStack().clone()),
    I_OCZYSZCZENIE("I_OCZYSZCZENIE", new ItemBuilder(Material.GHAST_TEAR).setName("&fLza Aniola").setLore(Arrays.asList("&8Lza uroniona przez &fAniola&8.", "&8Jest tak czysta ze uzycie", "&8jej w polaczeniu z twoim przedmiotem", "&8w &6Kowadle &8sprawi, ze bedziesz mogl", "&8jeszcze raz ulepszyc swoj przedmiot")).addGlowing().toItemStack().clone()),
    I10("I10", new ItemBuilder(Material.BOOK).setName("&a&lPodrecznik Kowala").setLore(Arrays.asList("&8Pradawny podrecznik wiedzy o kowalstwie.", "&8Uzycie go u &4Kowala &8sprawia, ze", "&achroni &8on twoj przedmiot przed spaleniem", "&8podczas ulepszania")).addGlowing().toItemStack().clone()),
    I_CZASTKA_MAGII("I_CZASTKA_MAGII", new ItemBuilder(Material.BLAZE_POWDER).setName("&d&lCzastka Magii").toItemStack().clone()),
    I_KAMIENBAO("I_KAMIENBAO", new ItemBuilder(Material.COAL, 1, (short) 1).setName("&3&lKamien Zaczarowania Stolu").setLore(Arrays.asList("&8Ten magiczny kamien pozwala Ci", "&8zmienic &cwszystkie &8bonusy w &6Stole Magii", "&8Pamietaj &c&lAdministracja &8nie odpowiada za zmieniane bonusy")).toItemStack().clone()),
    I_KSIEGAMAGII("I_KSIEGAMAGII", new ItemBuilder(Material.ENCHANTED_BOOK).setName("&5&lKsiega Magii").setLore(Arrays.asList("&8Ta magiczna ksiega pozwoli Ci", "&8zmienic &cjeden &8bonus w &6Stole Magii", "&8Pamietaj &c&lAdministracja &8nie odpowiada za zmieniane bonusy")).toItemStack().clone()),
    I11("I11", new ItemBuilder(Material.GOLD_NUGGET).setName("&4&lH&6&lS &4&lvalue").setLore(Arrays.asList("&8&oKliknij&8, zeby zasilic swoj balans")).addGlowing().toItemStack().clone()),
    // MATERIALY
    I12("I12", new ItemBuilder(Material.GOLD_INGOT).setName("&eZloto").setLore(Arrays.asList("&8&oMaterial")).hideFlag().toItemStack().clone()),
    I13("I13", new ItemBuilder(Material.DIAMOND).setName("&bBrylant").setLore(Arrays.asList("&8&oMaterial")).hideFlag().toItemStack().clone()),
    I14("I14", new ItemBuilder(Material.EMERALD).setName("&aSzmaragd").setLore(Arrays.asList("&8&oMaterial")).hideFlag().toItemStack().clone()),
    I15("I15", new ItemBuilder(Material.REDSTONE).setName("&cPyl").setLore(Arrays.asList("&8&oMaterial")).hideFlag().toItemStack().clone()),
    I16("I16", new ItemBuilder(Material.STONE).setName("&7Kamien").setLore(Arrays.asList("&8&oMaterial")).hideFlag().toItemStack().clone()),
    I17("I17", new ItemBuilder(Material.IRON_INGOT).setName("&8Stal").setLore(Arrays.asList("&8&oMaterial")).hideFlag().toItemStack().clone()),
    I18("I18", new ItemBuilder(Material.SULPHUR).setName("&7Proch").setLore(Arrays.asList("&8&oMaterial")).hideFlag().toItemStack().clone()),

    // Zmianki
    I50("I50", new ItemBuilder(Material.INK_SACK, 1, (short) 13).setName("&9&lMagiczne Zaczarowanie").setLore(Arrays.asList("&7Pozwala nadac bonusy twojego miecza i ekwipunku...")).toItemStack().clone()),

    // PIERSCIENIE DOSWIADCZENIA
    I52("I52", new ItemBuilder(Material.GHAST_TEAR).setName("&e&lPierscien Doswiadczenia &6&l25%").setLore(Arrays.asList(
            "&7Czas trwania: &e30min",
            "&8Uzycie go powoduje zwiekszenie",
            "&8twojego bonusu doswiadczenia o &625%"
    )).addTagDouble("exp", 25).addTagString("time", "30m").addGlowing().toItemStack().clone()),
    I53("I53", new ItemBuilder(Material.GHAST_TEAR).setName("&e&lPierscien Doswiadczenia &6&l25%").setLore(Arrays.asList(
            "&7Czas trwania: &e1h",
            "&8Uzycie go powoduje zwiekszenie",
            "&8twojego bonusu doswiadczenia o &625%"
    )).addTagDouble("exp", 25).addTagString("time", "1h").addGlowing().toItemStack().clone()),
    I54("I54", new ItemBuilder(Material.GHAST_TEAR).setName("&e&lPierscien Doswiadczenia &6&l50%").setLore(Arrays.asList(
            "&7Czas trwania: &e30min",
            "&8Uzycie go powoduje zwiekszenie",
            "&8twojego bonusu doswiadczenia o &650%"
    )).addTagDouble("exp", 50).addTagString("time", "30m").addGlowing().toItemStack().clone()),
    I55("I55", new ItemBuilder(Material.GHAST_TEAR).setName("&e&lPierscien Doswiadczenia &6&l50%").setLore(Arrays.asList(
            "&7Czas trwania: &e1h",
            "&8Uzycie go powoduje zwiekszenie",
            "&8twojego bonusu doswiadczenia o &650%"
    )).addTagDouble("exp", 50).addTagString("time", "1h").addGlowing().toItemStack().clone()),

    // ZNISZCZONE RUBINOWE SERCE (MEDRZEC ITEM)
    ZNISZCZONE_RUBINOWE_SERCE("ZNISZCZONE_RUBINOWE_SERCE", new ItemBuilder(Material.REDSTONE).setName("&cZniszczone Rubinowe Serce").setLore(Arrays.asList("&8&oChyba Medrzec tego potrzebuje")).toItemStack().clone()),
    ZNISZCZONE_SZAFIROWE_SERCE("ZNISZCZONE_SZAFIROWE_SERCE", new ItemBuilder(Material.INK_SACK, 1, (short) 4).setName("&bZniszczone Szafirowe Serce").setLore(Arrays.asList("&8&oChyba Medrzec tego potrzebuje")).toItemStack().clone()),
    RUBINOWE_SERCE("RUBINOWE_SERCE", new ItemBuilder(Material.INK_SACK, 1, (short) 1).setName("&c&lRubinowe Serce").setLore(Arrays.asList(
            "&8Wykute przez Medrca wedlug pradawnej receptury",
            "&8Po uzyciu spowoduje, ze staniesz sie silniejszy",
            "&8i otrzymasz &c0.5❤"
    )).addGlowing().toItemStack().clone()),
    SZAFIROWE_SERCE("SZAFIROWE_SERCE", new ItemBuilder(Material.INK_SACK, 1, (short) 6).setName("&b&lSzafirowe Serce").setLore(Arrays.asList(
            "&8Wykute przez Medrca wedlug pradawnej receptury",
            "&8Po uzyciu spowoduje, ze staniesz sie silniejszy",
            "&8i otrzymasz &c0.5❤.",
            "",
            "&8Mozna uzyc dopiero po uzyskaniu &c10❤ &8od Medrca"
    )).addGlowing().toItemStack().clone()),
    // SAKWA?
    SAKWA("SAKWA", new ItemBuilder(Material.FLOWER_POT_ITEM).setName("&8✦&eSakiewka&8✦").toItemStack().clone()),
    RUDA_MITHRYLU("RUDA_MITHRYLU", new ItemBuilder(Material.PRISMARINE).setName("&2Ruda Mithrylu").setLore(Arrays.asList(
            "&8Bardzo rzadka ruda...",
            "&8Ciekawe skad wziela sie u potworow",
            "",
            "&8&oKomus na pewno sie to przyda..."
    )).toItemStack().clone()),
    // KONIEC MOZLIWYCH MISJI U NPC
    I_ERROR("error", new ItemBuilder(Material.BARRIER).setName("&aUkonczono!").setLore(Arrays.asList("&7Ukonczyles/as juz wszystkie dostepne", "&7Misje u tego npc!", "", "&8Mozliwe ze w przyszloscie", "&8pojawi sie ich wiecej")).toItemStack().clone());


    private final ItemStack itemStack;
    private final String name;

    GlobalItem(String name, ItemStack itemStack) {
        this.itemStack = itemStack;
        this.name = name;
    }

    public static void getList(Player player) {
        int i = 1;
        for (GlobalItem rank : values()) {
            ItemStack itemStack = rank.getItemStack();
            player.sendMessage(Utils.format(i + ". " + itemStack.getItemMeta().getDisplayName()));
            i = i + 1;
        }
    }

    public static GlobalItem getByName(String name) {
        for (GlobalItem rank : values()) {
            if (rank.getName().equalsIgnoreCase(name)) {
                return rank;
            }
        }
        return null;
    }

    public static ItemStack getItem(String string, int amount) {
        ItemStack itemStack = GlobalItem.getByName(string).getItemStack();
        itemStack.setAmount(amount);
        return itemStack;
    }

    public static ItemStack getArtefakt(final String itemName, final String playerName) {
        ItemStack itemStack = GlobalItem.getByName(itemName).getItemStack();
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(itemMeta.getDisplayName().replace("playerName", playerName));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack getHells(final int value) {
        ItemStack itemStack = Objects.requireNonNull(GlobalItem.getByName("I11")).getItemStack().clone();
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(itemMeta.getDisplayName().replace("value", String.valueOf(value)));
        itemStack.setItemMeta(itemMeta);
        return new ItemBuilder(itemStack.clone()).addTagInt("value", value).toItemStack().clone();
    }

    public static ItemStack getPercentSword(final SwordType swordType, final int percent) {
        ItemBuilder is;
        if (swordType == SwordType.KS) {
            is = new ItemBuilder(ItemHelper.createSword("&9&lMithrylowy Sztylet", Material.DIAMOND_SWORD, 35, 15, true));
            is.setLoreCrafting(is.toItemStack().clone().getItemMeta().getLore(), Arrays.asList(
                    " ",
                    "&3Silny Na Potwory: &f+" + percent + "%",
                    "&3Silny Na Ludzi: &f-" + (percent * 2) + "%",
                    "",
                    "&cWymagany Poziom: &665"
            )).addTagInt("moby", percent).addTagString("type", "ks").addTagInt("lvl", 65);
            return is.toItemStack().clone();
        } else {
            is = new ItemBuilder(ItemHelper.createSword("&9&lMithrylowe Ostrze", Material.DIAMOND_SWORD, 35, 15, true));
            is.setLoreCrafting(is.toItemStack().clone().getItemMeta().getLore(), Arrays.asList(
                    " ",
                    "&3Silny Na Ludzi: &f+" + percent + "%",
                    "&3Silny Na Potwory: &f-" + (percent * 2) + "%",
                    "",
                    "&cWymagany Poziom: &665"
            )).addTagInt("ludzie", percent).addTagString("type", "tyra").addTagInt("lvl", 65);
            return is.toItemStack().clone();
        }
    }

    public static ItemStack getCheck(final double money) {
        return new ItemBuilder(Material.DOUBLE_PLANT).setName("&eCzek na &6&l" + Utils.spaceNumber(DoubleUtils.round(money, 2)) + " &2$").addGlowing().toItemStack().clone();
    }

    public String getName() {
        return name;
    }

    public ItemStack getItemStack() {
        return itemStack.clone();
    }
}
