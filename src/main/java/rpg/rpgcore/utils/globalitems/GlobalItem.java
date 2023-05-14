package rpg.rpgcore.utils.globalitems;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import rpg.rpgcore.managers.miecze.SwordType;
import rpg.rpgcore.utils.DoubleUtils;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.ItemHelper;
import rpg.rpgcore.utils.Utils;

import java.util.Arrays;
import java.util.Objects;

public enum GlobalItem {
    // SKRZYNKI //
    // RARE
    I1("I1", new ItemBuilder(Material.ENDER_CHEST).setName("&6&lWartosciowy Kufer").setLore(Arrays.asList("&8&oOtworz i zobacz co skrywa...")).hideFlag().toItemStack().clone()),
    I2("I2", new ItemBuilder(Material.CHEST).setName("&f&lSkrzynia Kowala").setLore(Arrays.asList("&8&oTa skrzynia zawiera asortyment kowalski...")).hideFlag().toItemStack().clone()),
    I3("I3", new ItemBuilder(Material.CHEST).setName("&e&lSkrzynia Ze Zwierzakami").setLore(Arrays.asList("&8&oOtworz i zobacz co skrywa...")).addTagString("Type", "Normal").hideFlag().toItemStack().clone()),
    // INNE
    I4("I4", new ItemBuilder(Material.CHEST).setName("&3Tajemnicza Skrzynia").setLore(Arrays.asList("&8&oSkrzynia ta zawiera cenne przedmioty...")).hideFlag().toItemStack().clone()),
    I5("I5", new ItemBuilder(Material.CHEST).setName("&2Skrzynia Z Surowcami").setLore(Arrays.asList("&8&oSkrzynia ta zawiera rozne materialy...")).hideFlag().toItemStack().clone()),
    I6("I6", new ItemBuilder(Material.CHEST).setName("&4&lHELLCASE").setLore(Arrays.asList("&8&oSerwerowa skrzynia skrywa cenne przedmioty...")).hideFlag().toItemStack().clone()),
    I_FRAGMENT_STALI("I_FRAGMENT_STALI", new ItemBuilder(Material.PRISMARINE_CRYSTALS).setName("&8&lFragment Magicznej Stali").toItemStack().clone()),
    I_METAL("I_METAL", new ItemBuilder(Material.IRON_INGOT).setName("&7&lStal Kowalska").setLore(Arrays.asList("&dMagiczny &8przedmiot uzywany do wykucia", "&8najlepszych ostrzy, jakie widzial ten swiat.", "&8Nie tylko &achroni &8twoj przedmiot przed &cspaleniem", "&8ale daje ci szanse na &apewne", "&8ulepszenie twojego przedmiotu")).addGlowing().toItemStack().clone()),
    I_OCZYSZCZENIE("I_OCZYSZCZENIE", new ItemBuilder(Material.GHAST_TEAR, 1).setName("&fLza Aniola").setLore(Arrays.asList("&8Lza uroniona przez &fAniola&8.", "&8Jest tak czysta ze uzycie", "&8jej w polaczeniu z twoim przedmiotem", "&8w &6Kowadle &8sprawi, ze bedziesz mogl", "&8jeszcze raz ulepszyc swoj przedmiot")).addGlowing().toItemStack().clone()),
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

    I21("I21", new ItemBuilder(Material.CHEST).setName("&8&lSkrzynia Gornika").setLore(Arrays.asList("&8&oMoze zawierac wartosciowe przedmioty...")).hideFlag().toItemStack().clone()),
    I22("I22", new ItemBuilder(Material.CHEST).setName("&e&lSkrzynia ze Zwierzakami").setLore(Arrays.asList("&8&oOtworz i zobacz co skrywa...")).addTagString("Type", "ItemShop").hideFlag().toItemStack().clone()),

    // Zmianki
    I50("I50", new ItemBuilder(Material.INK_SACK, 1, (short) 1).setName("&6&lKamien Zaczarowania Miecza").setLore(Arrays.asList("&8Pozwala zmienic bonusy na twoim mieczu")).toItemStack().clone()),
    I51("I51", new ItemBuilder(Material.INK_SACK, 1, (short) 14).setName("&6&lKamien Zaczarowania Zbroi").setLore(Arrays.asList("&8Pozwala zmienic bonusy na twojej zbroi")).toItemStack().clone()),

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

    // MIKSTURA MEDYKA
    I56("I56", new ItemBuilder(Material.POTION).setName("&5Metna Mikstura Medyka").setLore(Arrays.asList("&8Mikstura ta jest niezdatna do picia.")).addFlag(ItemFlag.HIDE_POTION_EFFECTS).toItemStack().clone()),
    I57("I57", new ItemBuilder(Material.INK_SACK, 1, (short) 1).setName("&4Fragment Serca").setLore(Arrays.asList("&8Chyba Medyk tego potrzebuje...")).toItemStack().clone()),
    // ZNISZCZONE RUBINOWE SERCE (MEDRZEC ITEM)
    ZNISZCZONE_RUBINOWE_SERCE("ZNISZCZONE_RUBINOWE_SERCE", new ItemBuilder(Material.REDSTONE).setName("&cZniszczone Rubinowe Serce").setLore(Arrays.asList("&8&oChyba Medrzec tego potrzebuje")).toItemStack().clone()),
    RUBINOWE_SERCE("RUBINOWE_SERCE", new ItemBuilder(Material.INK_SACK, 1, (short) 1).setName("&c&lRubinowe Serce").setLore(Arrays.asList(
            "&8Wykute przez Medrca wedlug pradawnej receptury",
            "&8Po uzyciu spowoduje, ze staniesz sie silniejszy",
            "i otrzymasz &c0.5❤"
    )).addGlowing().toItemStack().clone()),
    // SAKWA?
    SAKWA("SAKWA", new ItemBuilder(Material.FLOWER_POT_ITEM).setName("&8✦&eSakiewka&8✦").toItemStack().clone()),
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
        return new ItemBuilder(itemStack.clone()).addTagInt("value", value).toItemStack();
    }

    public static ItemStack getPercentSword(final SwordType swordType, final int percent) {
        ItemBuilder is;
        if (swordType == SwordType.KS) {
            is = new ItemBuilder(ItemHelper.createSword("&cKS", Material.IRON_SWORD, 35, 15, false));
            is.setLoreCrafting(is.toItemStack().clone().getItemMeta().getLore(), Arrays.asList(
                    " ",
                    "&9Silny Na Ludzi: &e+" + percent + "%",
                    "&9Silny Na Potwory: &e-" + (percent * 2) + "%",
                    "&cWymagany Poziom: &665"
            )).addTagInt("ludzie", percent).addTagString("type", "ks").addTagInt("lvl", 65);
            return is.toItemStack().clone();
        } else {
            is = new ItemBuilder(ItemHelper.createSword("&cTYRA", Material.IRON_SWORD, 35, 15, false));
            is.setLoreCrafting(is.toItemStack().clone().getItemMeta().getLore(), Arrays.asList(
                    " ",
                    "&9Silny Na Potwory: &e+" + percent + "%",
                    "&9Silny Na Ludzi: &e-" + (percent * 2) + "%",
                    "&cWymagany Poziom: &665"
            )).addTagInt("moby", percent).addTagString("type", "tyra").addTagInt("lvl", 65);
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
        return itemStack;
    }
}
