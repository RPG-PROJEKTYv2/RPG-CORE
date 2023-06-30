package rpg.rpgcore.commands.player.rozpiska;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.npc.przyrodnik.enums.PrzyrodnikMissions;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.MobDropHelper;
import rpg.rpgcore.utils.Utils;

import java.util.Arrays;
import java.util.UUID;

public class RozpiskaManager {
    private final RPGCORE rpgcore;
    public RozpiskaManager(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }
    public void openROZPISKAGUI(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 54, Utils.format("&7Rozpiska - menu"));
        ItemStack itemGUI1 = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short)15).setName(" ").toItemStack().clone();
        ItemStack itemGUI2 = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short)7).setName(" ").toItemStack().clone();
        ItemBuilder itemGUI3 = new ItemBuilder(Material.STAINED_CLAY, 1, (short)5);
        ItemBuilder itemGUI4 = new ItemBuilder(Material.STAINED_CLAY, 1, (short)14);
        gui.setItem(0, itemGUI1);
        gui.setItem(1, itemGUI1);
        gui.setItem(2, itemGUI2);
        gui.setItem(3, itemGUI2);
        gui.setItem(4, itemGUI2);
        gui.setItem(5, itemGUI2);
        gui.setItem(6, itemGUI2);
        gui.setItem(7, itemGUI1);
        gui.setItem(8, itemGUI1);
        gui.setItem(9, itemGUI1);
        gui.setItem(10, itemGUI2);
        gui.setItem(11, itemGUI3.setName("&cExpowisko &8* &f(&a1-10&f)").addGlowing().toItemStack().clone());
        gui.setItem(12, itemGUI3.setName("&cExpowisko &8* &f(&a10-20&f)").addGlowing().toItemStack().clone());
        gui.setItem(13, itemGUI3.setName("&cExpowisko &8* &f(&a20-30&f)").addGlowing().toItemStack().clone());
        gui.setItem(14, itemGUI3.setName("&cExpowisko &8* &f(&a30-40&f)").addGlowing().toItemStack().clone());
        gui.setItem(15, itemGUI3.setName("&cExpowisko &8* &f(&a40-50&f)").addGlowing().toItemStack().clone());
        gui.setItem(16, itemGUI2);
        gui.setItem(17, itemGUI1);
        gui.setItem(18, itemGUI1);
        gui.setItem(19, itemGUI2);
        gui.setItem(20, itemGUI2);
        gui.setItem(21, itemGUI2);
        gui.setItem(22, itemGUI2);
        gui.setItem(23, itemGUI2);
        gui.setItem(24, itemGUI2);
        gui.setItem(25, itemGUI2);
        gui.setItem(26, itemGUI1);
        gui.setItem(27, itemGUI2);
        gui.setItem(28, itemGUI4.setName("&cExpowisko &8* &f(&a50-60&f)").addGlowing().toItemStack().clone());
        gui.setItem(29, itemGUI4.setName("&cExpowisko &8* &f(&a60-70&f)").addGlowing().toItemStack().clone());
        gui.setItem(30, itemGUI4.setName("&cExpowisko &8* &f(&a70-80&f)").addGlowing().toItemStack().clone());
        gui.setItem(31, itemGUI4.setName("&cExpowisko &8* &f(&a80-90&f)").addGlowing().toItemStack().clone());
        gui.setItem(32, itemGUI4.setName("&cExpowisko &8* &f(&a90-100&f)").addGlowing().toItemStack().clone());
        gui.setItem(33, itemGUI4.setName("&cExpowisko &8* &f(&a100-110&f)").addGlowing().toItemStack().clone());
        gui.setItem(34, itemGUI4.setName("&cExpowisko &8* &f(&a110-120&f)").addGlowing().toItemStack().clone());
        gui.setItem(35, itemGUI2);
        gui.setItem(36, itemGUI1);
        gui.setItem(37, itemGUI2);
        gui.setItem(38, itemGUI2);
        gui.setItem(39, itemGUI2);
        gui.setItem(40, itemGUI4.setName("&cExpowisko &8* &f(&a120-130&f)").addGlowing().toItemStack().clone());
        gui.setItem(41, itemGUI2);
        gui.setItem(42, itemGUI2);
        gui.setItem(43, itemGUI2);
        gui.setItem(44, itemGUI1);
        gui.setItem(45, itemGUI1);
        gui.setItem(46, itemGUI1);
        gui.setItem(47, itemGUI1);
        gui.setItem(48, itemGUI1);
        gui.setItem(49, itemGUI2);
        gui.setItem(50, itemGUI1);
        gui.setItem(51, itemGUI1);
        gui.setItem(52, itemGUI1);
        gui.setItem(53, itemGUI1);
        player.openInventory(gui);
    }
    private Inventory ramkaGUI(final String nazwagui) {
        final Inventory ramkaGUI = Bukkit.createInventory(null, 45, Utils.format(nazwagui));
        ramkaGUI.setItem(0, new ItemBuilder(Material.STAINED_GLASS_PANE,1, (short)3).setName(" ").toItemStack().clone());
        ramkaGUI.setItem(1, new ItemBuilder(Material.STAINED_GLASS_PANE,1, (short)3).setName(" ").toItemStack().clone());
        ramkaGUI.setItem(2, new ItemBuilder(Material.STAINED_GLASS_PANE,1, (short)3).setName(" ").toItemStack().clone());
        ramkaGUI.setItem(3, new ItemBuilder(Material.STAINED_GLASS_PANE,1, (short)5).setName(" ").toItemStack().clone());
        ramkaGUI.setItem(4, new ItemBuilder(Material.STAINED_GLASS_PANE,1, (short)5).setName(" ").toItemStack().clone());
        ramkaGUI.setItem(5, new ItemBuilder(Material.STAINED_GLASS_PANE,1, (short)5).setName(" ").toItemStack().clone());
        ramkaGUI.setItem(6, new ItemBuilder(Material.STAINED_GLASS_PANE,1, (short)3).setName(" ").toItemStack().clone());
        ramkaGUI.setItem(7, new ItemBuilder(Material.STAINED_GLASS_PANE,1, (short)3).setName(" ").toItemStack().clone());
        ramkaGUI.setItem(8, new ItemBuilder(Material.STAINED_GLASS_PANE,1, (short)3).setName(" ").toItemStack().clone());
        ramkaGUI.setItem(9, new ItemBuilder(Material.STAINED_GLASS_PANE,1, (short)3).setName(" ").toItemStack().clone());
        ramkaGUI.setItem(10, new ItemBuilder(Material.STAINED_GLASS_PANE,1, (short)5).setName(" ").toItemStack().clone());
        ramkaGUI.setItem(11, new ItemBuilder(Material.STAINED_GLASS_PANE,1, (short)3).setName(" ").toItemStack().clone());
        ramkaGUI.setItem(12, new ItemBuilder(Material.STAINED_GLASS_PANE,1, (short)13).setName(" ").toItemStack().clone());
        ramkaGUI.setItem(13, new ItemBuilder(Material.STAINED_GLASS_PANE,1, (short)13).setName(" ").toItemStack().clone());
        ramkaGUI.setItem(14, new ItemBuilder(Material.STAINED_GLASS_PANE,1, (short)13).setName(" ").toItemStack().clone());
        ramkaGUI.setItem(15, new ItemBuilder(Material.STAINED_GLASS_PANE,1, (short)3).setName(" ").toItemStack().clone());
        ramkaGUI.setItem(16, new ItemBuilder(Material.STAINED_GLASS_PANE,1, (short)5).setName(" ").toItemStack().clone());
        ramkaGUI.setItem(17, new ItemBuilder(Material.STAINED_GLASS_PANE,1, (short)3).setName(" ").toItemStack().clone());
        ramkaGUI.setItem(18, new ItemBuilder(Material.STAINED_GLASS_PANE,1, (short)3).setName(" ").toItemStack().clone());
        ramkaGUI.setItem(19, new ItemBuilder(Material.STAINED_GLASS_PANE,1, (short)5).setName(" ").toItemStack().clone());
        ramkaGUI.setItem(20, new ItemBuilder(Material.STAINED_GLASS_PANE,1, (short)13).setName(" ").toItemStack().clone());
        ramkaGUI.setItem(24, new ItemBuilder(Material.STAINED_GLASS_PANE,1, (short)13).setName(" ").toItemStack().clone());
        ramkaGUI.setItem(25, new ItemBuilder(Material.STAINED_GLASS_PANE,1, (short)5).setName(" ").toItemStack().clone());
        ramkaGUI.setItem(26, new ItemBuilder(Material.STAINED_GLASS_PANE, 1,(short)3).setName(" ").toItemStack().clone());
        ramkaGUI.setItem(27, new ItemBuilder(Material.STAINED_GLASS_PANE,1, (short)3).setName(" ").toItemStack().clone());
        ramkaGUI.setItem(28, new ItemBuilder(Material.STAINED_GLASS_PANE,1, (short)5).setName(" ").toItemStack().clone());
        ramkaGUI.setItem(29, new ItemBuilder(Material.STAINED_GLASS_PANE,1, (short)3).setName(" ").toItemStack().clone());
        ramkaGUI.setItem(30, new ItemBuilder(Material.STAINED_GLASS_PANE,1, (short)13).setName(" ").toItemStack().clone());
        ramkaGUI.setItem(31, new ItemBuilder(Material.STAINED_GLASS_PANE,1, (short)13).setName(" ").toItemStack().clone());
        ramkaGUI.setItem(32, new ItemBuilder(Material.STAINED_GLASS_PANE,1, (short)13).setName(" ").toItemStack().clone());
        ramkaGUI.setItem(33, new ItemBuilder(Material.STAINED_GLASS_PANE,1, (short)3).setName(" ").toItemStack().clone());
        ramkaGUI.setItem(34, new ItemBuilder(Material.STAINED_GLASS_PANE,1, (short)5).setName(" ").toItemStack().clone());
        ramkaGUI.setItem(35, new ItemBuilder(Material.STAINED_GLASS_PANE,1, (short)3).setName(" ").toItemStack().clone());
        ramkaGUI.setItem(36, new ItemBuilder(Material.STAINED_GLASS_PANE,1, (short)3).setName(" ").toItemStack().clone());
        ramkaGUI.setItem(37, new ItemBuilder(Material.STAINED_GLASS_PANE,1, (short)3).setName(" ").toItemStack().clone());
        ramkaGUI.setItem(38, new ItemBuilder(Material.STAINED_GLASS_PANE,1, (short)3).setName(" ").toItemStack().clone());
        ramkaGUI.setItem(39, new ItemBuilder(Material.STAINED_GLASS_PANE,1, (short)5).setName(" ").toItemStack().clone());
        ramkaGUI.setItem(40, new ItemBuilder(Material.STAINED_GLASS_PANE,1, (short)5).setName(" ").toItemStack().clone());
        ramkaGUI.setItem(41, new ItemBuilder(Material.STAINED_GLASS_PANE,1, (short)5).setName(" ").toItemStack().clone());
        ramkaGUI.setItem(42, new ItemBuilder(Material.STAINED_GLASS_PANE,1, (short)3).setName(" ").toItemStack().clone());
        ramkaGUI.setItem(43, new ItemBuilder(Material.STAINED_GLASS_PANE,1, (short)3).setName(" ").toItemStack().clone());
        ramkaGUI.setItem(44, Utils.powrot());
        return ramkaGUI;
    }
    // TODO MOBKA
    // skrzynka mob
    // skrzynka surowce
    // skrzynka tajemnicza
    // wartosciowy kufer
    // skrzynia kowala

    // nies

    // ulepszacz
    // przyrodnik
    // zywica
    // fragment stali
    // rubinowe serce

    // sakwa JAKO TA CZASTKA DO CRAFTOWANIA MIECZA
    // czastka magii
    // przepustka

    // przywolanie bossa

    // TODO BOSSA
    // skrzynka boss
    // lowca glowka
    //

    // TODO METINA
    // odlamek metina w #metinolog % jest
    // bony metinow jako RARE &kSSSS item... w #metin helper % jest
    public void openFIRSTexp(final Player player) {
        final Inventory openFIRSTgui = this.ramkaGUI("&cRozpiska &8* &f(&a1-10&f)");
        final UUID uuid = player.getUniqueId();
        final int szczescie = RPGCORE.getInstance().getBonusesManager().find(player.getUniqueId()).getBonusesUser().getSzczescie();
        final PrzyrodnikMissions przyrodnikMission = PrzyrodnikMissions.getByNumber(rpgcore.getPrzyrodnikNPC().find(uuid).getUser().getMission());
        final int medrzecBonus = rpgcore.getMedrzecNPC().find(uuid).getBonus();

        openFIRSTgui.setItem(21, new ItemBuilder(Material.SKULL_ITEM, 1, (short) SkullType.SKELETON.ordinal()).setName("&6Rozbojnik").setLore(Arrays.asList("",
                "&6Skrzynia Rozbojnika: &f" + MobDropHelper.getDropChance(szczescie,2.7) + "%",
                "&2Skrzynia Z Surowcami: &f" + MobDropHelper.getDropChance(szczescie,2) + "%",
                "&3Tajemnicza Skrzynia: &f" + MobDropHelper.getDropChance(szczescie,0.06) + "%",
                "&f&lSkrzynia Kowala: &f" + MobDropHelper.getDropChance(szczescie,0.05) + "%",
                "&6&lWartosciowy Kufer: &f" + MobDropHelper.getDropChance(szczescie,0.01) + "%",
                "",
                "&b&lNIESAMOWITY PRZEDMIOT: &f" + MobDropHelper.getDropChance(szczescie,0.05) + "%",
                "",
                "&8Szata Rozbojnika: &f" + MobDropHelper.getDropChance(szczescie,2.5) + "%",
                "&6Zardzewialy Pierscien: &f" + MobDropHelper.getDropChance(szczescie, przyrodnikMission.getDropChance()) + "%",
                "&6Zywica: &f" + MobDropHelper.getDropChance(szczescie,1.0) + "%",
                "&8&lFragment Magicznej Stali: &f" + MobDropHelper.getDropChance(szczescie,0.025) + "%",
                "" + (medrzecBonus < 20 ? "&cZniszczone Rubinowe Serce: &f" + MobDropHelper.getDropChance(szczescie, 0.05) + "%" : "&bZniszczone Szafirowe Serce: &f"  + MobDropHelper.getDropChance(szczescie, 0.015) + "%"),
                ""
        )).addGlowing().toItemStack().clone());
        openFIRSTgui.setItem(22, new ItemBuilder(Material.SKULL_ITEM, 1, (short) SkullType.WITHER.ordinal()).setName("&c&lDowodca Rozbojnikow").setLore(Arrays.asList("",
                "&3&lSkrzynia &c&lDowodcy Rozbojnikow: &f100%",
                "&4Trofeum Bossa 1-10: &f" + MobDropHelper.getDropChance(szczescie,15) + "%",
                ""
        )).addGlowing().toItemStack().clone());
        openFIRSTgui.setItem(23, new ItemBuilder(Material.NETHER_STAR).setName("&9&lMetin Rozbojnikow").setLore(Arrays.asList("",
                "&4Odlamek Kamienia Metin 1-10: &f25%",
                "",
                "&aUkryty Przedmiot: &f0.01%",
                "&6Ukryty Przedmiot: &f0.005%",
                "&cUkryty Przedmiot: &f0.002%",
                ""
        )).addGlowing().toItemStack().clone());
        player.openInventory(openFIRSTgui);
    }
    public void openSECONDexp(final Player player) {
        final Inventory openSECONDgui = this.ramkaGUI("&cExpowisko &8* &f(&a10-20&f)");
        openSECONDgui.setItem(21, new ItemBuilder(Material.MONSTER_EGG).setName("&6Rozbojnik").setLore(Arrays.asList("", "&7Skrzynka: &6X%")).addGlowing().toItemStack().clone());
        openSECONDgui.setItem(22, new ItemBuilder(Material.FIREWORK_CHARGE).setName("&c&lDowodca Rozbojnikow").setLore(Arrays.asList("", "&eSzansa: &aX")).addGlowing().toItemStack().clone());
        openSECONDgui.setItem(23, new ItemBuilder(Material.RABBIT_HIDE).setName("&9&lMetin Rozbojnikow").setLore(Arrays.asList("", "&eSzansa: &aX")).addGlowing().toItemStack().clone());
        player.openInventory(openSECONDgui);
    }
}
