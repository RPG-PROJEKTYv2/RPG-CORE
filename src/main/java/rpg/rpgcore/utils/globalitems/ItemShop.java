package rpg.rpgcore.utils.globalitems;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.Arrays;
import java.util.List;

public enum ItemShop {
    IS1("HS_5", Arrays.asList(GlobalItem.getHells(5).clone())),
    IS2("HS_10", Arrays.asList(GlobalItem.getHells(10).clone())),
    IS3("HS_15", Arrays.asList(GlobalItem.getHells(15).clone())),
    IS4("HS_30", Arrays.asList(GlobalItem.getHells(30).clone())),
    IS5("HS_55", Arrays.asList(GlobalItem.getHells(55).clone())),
    IS6("HS_130", Arrays.asList(GlobalItem.getHells(130).clone())),
    IS7("HS_280", Arrays.asList(GlobalItem.getHells(280).clone())),
    IS8("HS_585", Arrays.asList(GlobalItem.getHells(585).clone())),
    IS9("Vip_3d", Arrays.asList(new ItemBuilder(Material.BOOK).setName("&7&lVoucher na range &e&lVip").setLore(Arrays.asList(
            "&8Waznosc: &e3 dni",
            "",
            "&8Kliknij, aby aktywowac ten voucher i otrzymac jego zawartosc",
            "",
            "&6Rangi &8i &4&lHell&6&lS'y &8mozesz zakupic na naszej stronie",
            "&3www.hellrpg.pl &8lub na naszym discordzie &3dc.hellrpg.pl"
    )).addGlowing().addTagString("rank", "Vip").addTagString("time", "3d").toItemStack().clone())),
    IS10("Vip_7d", Arrays.asList(new ItemBuilder(Material.BOOK).setName("&7&lVoucher na range &e&lVip").setLore(Arrays.asList(
            "&8Waznosc: &e7 dni",
            "",
            "&8Kliknij, aby aktywowac ten voucher i otrzymac jego zawartosc",
            "",
            "&6Rangi &8i &4&lHell&6&lS'y &8mozesz zakupic na naszej stronie",
            "&3www.hellrpg.pl &8lub na naszym discordzie &3dc.hellrpg.pl"
    )).addGlowing().addTagString("rank", "Vip").addTagString("time", "7d").toItemStack().clone())),
    IS11("Vip_14d", Arrays.asList(new ItemBuilder(Material.BOOK).setName("&7&lVoucher na range &e&lVIP").setLore(Arrays.asList(
            "&8Waznosc: &e14 dni",
            "",
            "&8Kliknij, aby aktywowac ten voucher i otrzymac jego zawartosc",
            "",
            "&6Rangi &8i &4&lHell&6&lS'y &8mozesz zakupic na naszej stronie",
            "&3www.hellrpg.pl &8lub na naszym discordzie &3dc.hellrpg.pl"
    )).addGlowing().addTagString("rank", "Vip").addTagString("time", "14d").toItemStack().clone())),
    IS12("Svip_3d", Arrays.asList(new ItemBuilder(Material.BOOK).setName("&7&lVoucher na range &6&lS&e&lvip").setLore(Arrays.asList(
            "&8Waznosc: &e3 dni",
            "",
            "&8Kliknij, aby aktywowac ten voucher i otrzymac jego zawartosc",
            "",
            "&6Rangi &8i &4&lHell&6&lS'y &8mozesz zakupic na naszej stronie",
            "&3www.hellrpg.pl &8lub na naszym discordzie &3dc.hellrpg.pl"
    )).addGlowing().addTagString("rank", "Svip").addTagString("time", "3d").toItemStack().clone())),
    IS13("Svip_7d", Arrays.asList(new ItemBuilder(Material.BOOK).setName("&7&lVoucher na range &6&lS&e&lvip").setLore(Arrays.asList(
            "&8Waznosc: &e7 dni",
            "",
            "&8Kliknij, aby aktywowac ten voucher i otrzymac jego zawartosc",
            "",
            "&6Rangi &8i &4&lHell&6&lS'y &8mozesz zakupic na naszej stronie",
            "&3www.hellrpg.pl &8lub na naszym discordzie &3dc.hellrpg.pl"
    )).addGlowing().addTagString("rank", "Svip").addTagString("time", "7d").toItemStack().clone())),
    IS14("Svip_14d", Arrays.asList(new ItemBuilder(Material.BOOK).setName("&7&lVoucher na range &6&lS&e&lvip").setLore(Arrays.asList(
            "&8Waznosc: &e14 dni",
            "",
            "&8Kliknij, aby aktywowac ten voucher i otrzymac jego zawartosc",
            "",
            "&6Rangi &8i &4&lHell&6&lS'y &8mozesz zakupic na naszej stronie",
            "&3www.hellrpg.pl &8lub na naszym discordzie &3dc.hellrpg.pl"
    )).addGlowing().addTagString("rank", "Svip").addTagString("time", "14d").toItemStack().clone())),
    IS15("ELITA_3d", Arrays.asList(new ItemBuilder(Material.BOOK).setName("&7&lVoucher na range &5&lELITA").setLore(Arrays.asList(
            "&8Waznosc: &e3 dni",
            "",
            "&8Kliknij, aby aktywowac ten voucher i otrzymac jego zawartosc",
            "",
            "&6Rangi &8i &4&lHell&6&lS'y &8mozesz zakupic na naszej stronie",
            "&3www.hellrpg.pl &8lub na naszym discordzie &3dc.hellrpg.pl"
    )).addGlowing().addTagString("rank", "Elita").addTagString("time", "3d").toItemStack().clone())),
    IS16("ELITA_7d", Arrays.asList(new ItemBuilder(Material.BOOK).setName("&7&lVoucher na range &5&lELITA").setLore(Arrays.asList(
            "&8Waznosc: &e7 dni",
            "",
            "&8Kliknij, aby aktywowac ten voucher i otrzymac jego zawartosc",
            "",
            "&6Rangi &8i &4&lHell&6&lS'y &8mozesz zakupic na naszej stronie",
            "&3www.hellrpg.pl &8lub na naszym discordzie &3dc.hellrpg.pl"
    )).addGlowing().addTagString("rank", "Elita").addTagString("time", "7d").toItemStack().clone())),
    IS17("ELITA_14d", Arrays.asList(new ItemBuilder(Material.BOOK).setName("&7&lVoucher na range &5&lELITA").setLore(Arrays.asList(
            "&8Waznosc: &e14 dni",
            "",
            "&8Kliknij, aby aktywowac ten voucher i otrzymac jego zawartosc",
            "",
            "&6Rangi &8i &4&lHell&6&lS'y &8mozesz zakupic na naszej stronie",
            "&3www.hellrpg.pl &8lub na naszym discordzie &3dc.hellrpg.pl"
    )).addGlowing().addTagString("rank", "Elita").addTagString("time", "14d").toItemStack().clone())),
    IS18("ELITA_30d", Arrays.asList(new ItemBuilder(Material.BOOK).setName("&7&lVoucher na range &5&lELITA").setLore(Arrays.asList(
            "&8Waznosc: &e30 dni",
            "",
            "&8Kliknij, aby aktywowac ten voucher i otrzymac jego zawartosc",
            "",
            "&6Rangi &8i &4&lHell&6&lS'y &8mozesz zakupic na naszej stronie",
            "&3www.hellrpg.pl &8lub na naszym discordzie &3dc.hellrpg.pl"
    )).addGlowing().addTagString("rank", "Elita").addTagString("time", "30d").toItemStack().clone())),
    IS19("PELERYNKA", Arrays.asList(new ItemBuilder(Material.LEATHER).setName("&4&lPrzekleta &8&lSmocza Skora").setLore(Arrays.asList(
            "&7Zasieg: &610 kratek",
            "",
            "&8Tajemnicza skora, zdarta z pradawnego smoka",
            "&8uzycie jej spowoduje, ze wszystkie potwory w zasiegu",
            "&8jej dzialania zostana zwrocone przeciwko tobie"
    )).addTagInt("range", 10).addGlowing().toItemStack().clone())),
    IS20("PELERYNKA+", Arrays.asList(new ItemBuilder(Material.LEATHER).setName("&4&lPrzekleta &8&lSmocza Skora&b&l+").setLore(Arrays.asList(
            "&7Zasieg: &620 kratek",
            "",
            "&8Tajemnicza skora, zdarta z pradawnego smoka",
            "&8uzycie jej spowoduje, ze wszystkie potwory w zasiegu",
            "&8jej dzialania zostana zwrocone przeciwko tobie"
    )).addTagInt("range", 20).addGlowing().toItemStack().clone()));


    private final String name;
    private final List<ItemStack> items;

    ItemShop(String name, List<ItemStack> items) {
        this.name = name;
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public List<ItemStack> getItems() {
        return items;
    }

    public static ItemShop getByName(String name) {
        for (ItemShop itemShop : values()) {
            if (itemShop.getName().equalsIgnoreCase(name)) {
                return itemShop;
            }
        }
        return null;
    }

    public static void listAll(final CommandSender sender) {
        sender.sendMessage(Utils.format("&8&m------------{-&6&l ItemShop &8&m-}------------"));
        sender.sendMessage(Utils.format("&7Lista dostepnych pakietow:"));
        for (ItemShop itemShop : values()) {
            sender.sendMessage(Utils.format("&8- &6" + itemShop.getName()));
        }
        sender.sendMessage(Utils.format("&8&m------------{-&6&l ItemShop &8&m-}------------"));
    }
}
