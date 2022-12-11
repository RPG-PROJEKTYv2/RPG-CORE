package rpg.rpgcore.npc.teleporter;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.ArrayList;

import static org.bukkit.Bukkit.getPlayer;

public class TeleporterNPC {

    private final RPGCORE rpgcore;

    public TeleporterNPC(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    private final ItemBuilder fillInventory = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15);
    private final ArrayList<String> lore = new ArrayList<>();
    //private final ItemBuilder wybor1 = new ItemBuilder(Material.WATER_BUCKET);
    //private final ItemBuilder wybor2 = new ItemBuilder(Material.LAVA_BUCKET);
    private final ItemBuilder expowisko1 = new ItemBuilder(Material.LEAVES);
    private final ItemBuilder expowisko2 = new ItemBuilder(Material.GRASS);
    private final ItemBuilder expowisko3 = new ItemBuilder(Material.VINE);
    private final ItemBuilder expowisko4 = new ItemBuilder(Material.WEB);
    private final ItemBuilder expowisko5 = new ItemBuilder(Material.PRISMARINE);
    private final ItemBuilder expowisko6 = new ItemBuilder(Material.ICE);
    private final ItemBuilder expowisko7 = new ItemBuilder(Material.BLAZE_POWDER);
    private final ItemBuilder expowisko8 = new ItemBuilder(Material.IRON_BARDING);
    //(Material.SMOOTH_BRICK, 1, (short)2);
    //SKULL_ITEM, 1, (short) SkullType.SKELETON.ordinal()
    private final ItemBuilder expowisko9 = new ItemBuilder(Material.SAND);
    private final ItemBuilder expowisko10 = new ItemBuilder(Material.STONE, (short)98);
    private final ItemBuilder expowisko11 = new ItemBuilder(Material.PRISMARINE_SHARD);


    //private final ItemBuilder miasto1 = new ItemBuilder(Material.PUMPKIN);
    //private final ItemBuilder demontower = new ItemBuilder(Material.NETHERRACK);

    private final ItemBuilder brakdostepu = new ItemBuilder(Material.STAINED_GLASS_PANE, 1 , (short)14);

    /*public void openTeleporterMAIN(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&9&lTELEPORTER &7- WYBOR"));

        fillInventory.setName(" ");
        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, fillInventory.toItemStack());
        }
        lore.clear();
        lore.add(" ");
        lore.add("&7Kliknij aby otworzyc wybor expowisk.");
        lore.add(" ");
        wybor1.setName(Utils.format("&8* &f&lExpowiska &8*")).setLore(lore);
        gui.setItem(11, wybor1.toItemStack());

        lore.clear();
        lore.add(" ");
        lore.add("&7Kliknij aby otworzyc wybor dodatkowych miejsc.");
        lore.add(" ");
        wybor2.setName(Utils.format("&8* &f&lMiejsca Dodatkowe &8*")).setLore(lore);
        gui.setItem(15, wybor2.toItemStack());

        player.openInventory(gui);
    }*/
    public void openTeleporterEXPOWISKA(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 18, Utils.format("&9&lTELEPORTER &7- EXPOWISKA"));
        final User user = rpgcore.getUserManager().find(player.getUniqueId());
        fillInventory.setName(" ");
        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, fillInventory.toItemStack());
        }

        // expowisko 1
        expowisko1.setName("&9&lPrzelecz Najemnikow");
        this.loreEXPOWISKA("&cOFF", "&f1");
        expowisko1.addGlowing();
        expowisko1.setLore(lore);
        gui.setItem(0, expowisko1.toItemStack());

        // expowisko 2
        if (user.getLvl() > 9) {
            expowisko2.setName("&2&lLas Goblinow");
            this.loreEXPOWISKA("&cOFF", "&f10");
            expowisko2.addGlowing();
            expowisko2.setLore(lore);
            gui.setItem(1, expowisko2.toItemStack());
        } else {
            brakdostepu.setName("&2Expowisko &8[ &a2. &8]");
            this.loreBRAKDOSTEPU("10");
            brakdostepu.addGlowing();
            brakdostepu.setLore(lore);
            gui.setItem(1, brakdostepu.toItemStack());
        }

        // expowisko 3
        if (user.getLvl() > 19) {
            expowisko3.setName("&a&lZapomniana Jungla");
            this.loreEXPOWISKA("&cOFF", "&f20");
            expowisko3.addGlowing();
            expowisko3.setLore(lore);
            gui.setItem(2, expowisko3.toItemStack());
        }  else {
            brakdostepu.setName("&2Expowisko &8[ &a3. &8]");
            this.loreBRAKDOSTEPU("20");
            brakdostepu.addGlowing();
            brakdostepu.setLore(lore);
            gui.setItem(2, brakdostepu.toItemStack());
        }

        // expowisko 4
        if (user.getLvl() > 29) {
            expowisko4.setName("&7&lOpuszczony Port");
            this.loreEXPOWISKA("&cOFF", "&f30");
            expowisko4.addGlowing();
            expowisko4.setLore(lore);
            gui.setItem(3, expowisko4.toItemStack());
        }   else {
            brakdostepu.setName("&2Expowisko &8[ &a4. &8]");
            this.loreBRAKDOSTEPU("30");
            brakdostepu.addGlowing();
            brakdostepu.setLore(lore);
            gui.setItem(3, brakdostepu.toItemStack());
        }

        // expowisko 5
        if (user.getLvl() > 39) {
            expowisko5.setName("&b&lPodwodna Swiatynia");
            this.loreEXPOWISKA("&cOFF", "&f40");
            expowisko5.addGlowing();
            expowisko5.setLore(lore);
            gui.setItem(4, expowisko5.toItemStack());
        }  else {
            brakdostepu.setName("&2Expowisko &8[ &a5. &8]");
            this.loreBRAKDOSTEPU("40");
            brakdostepu.addGlowing();
            brakdostepu.setLore(lore);
            gui.setItem(4, brakdostepu.toItemStack());
        }

        // expowisko 6
        if (user.getLvl() > 49) {
            expowisko6.setName("&f&lMrozna Dolina");
            this.loreEXPOWISKA("&aON", "&f50");
            expowisko6.addGlowing();
            expowisko6.setLore(lore);
            gui.setItem(5, expowisko6.toItemStack());
        } else {
            brakdostepu.setName("&2Expowisko &8[ &a6. &8]");
            this.loreBRAKDOSTEPU("50");
            brakdostepu.addGlowing();
            brakdostepu.setLore(lore);
            gui.setItem(5, brakdostepu.toItemStack());
        }

        // expowisko 7
        if (user.getLvl() > 59) {
            expowisko7.setName("&4&lOgnista Kraina");
            this.loreEXPOWISKA("&aON", "&f60");
            expowisko7.addGlowing();
            expowisko7.setLore(lore);
            gui.setItem(6, expowisko7.toItemStack());
        } else {
            brakdostepu.setName("&2Expowisko &8[ &a7. &8]");
            this.loreBRAKDOSTEPU("60");
            brakdostepu.addGlowing();
            brakdostepu.setLore(lore);
            gui.setItem(6, brakdostepu.toItemStack());
        }

        // expowisko 8
        if (user.getLvl() > 69) {
            expowisko8.setName("&7&lPodziemne Katakumby");
            this.loreEXPOWISKA("&aON", "&f70");
            expowisko8.addGlowing();
            expowisko8.setLore(lore);
            gui.setItem(7, expowisko8.toItemStack());
        } else {
            brakdostepu.setName("&2Expowisko &8[ &a8. &8]");
            this.loreBRAKDOSTEPU("70");
            brakdostepu.addGlowing();
            brakdostepu.setLore(lore);
            gui.setItem(7, brakdostepu.toItemStack());
        }

        // expowisko 9
        if (user.getLvl() > 79) {
            expowisko9.setName("&e&lPustynia Ptasznikow");
            this.loreEXPOWISKA("&aON", "&f80");
            expowisko9.addGlowing();
            expowisko9.setLore(lore);
            gui.setItem(8, expowisko9.toItemStack());
        } else {
            brakdostepu.setName("&2Expowisko &8[ &a9. &8]");
            this.loreBRAKDOSTEPU("80");
            brakdostepu.addGlowing();
            brakdostepu.setLore(lore);
            gui.setItem(8, brakdostepu.toItemStack());
        }

        // expowisko 10
        if (user.getLvl() > 89) {
            expowisko10.setName("&9&lGrota Wygnancow");
            this.loreEXPOWISKA("&aON", "&f90");
            expowisko10.addGlowing();
            expowisko10.setLore(lore);
            gui.setItem(9, expowisko10.toItemStack());
        } else {
            brakdostepu.setName("&2Expowisko &8[ &a10. &8]");
            this.loreBRAKDOSTEPU("90");
            brakdostepu.addGlowing();
            brakdostepu.setLore(lore);
            gui.setItem(9, brakdostepu.toItemStack());
        }

        // expowisko 11
        if (user.getLvl() > 99) {
            expowisko11.setName("&b&lZatopiona Swiatynia");
            this.loreEXPOWISKA("&aON", "&f100");
            expowisko11.addGlowing();
            expowisko11.setLore(lore);
            gui.setItem(10, expowisko11.toItemStack());
        } else {
            brakdostepu.setName("&2Expowisko &8[ &a11. &8]");
            this.loreBRAKDOSTEPU("100");
            brakdostepu.addGlowing();
            brakdostepu.setLore(lore);
            gui.setItem(10, brakdostepu.toItemStack());
        }
        /*
        // expowisko 12
        if (user.getLvl() > 109) {
            expowisko12.setName("&6&lBRAK NAZWY");
            this.loreEXPOWISKA("&aON", "&f110");
            expowisko12.addGlowing();
            expowisko12.setLore(lore);
            gui.setItem(11, expowisko12.toItemStack());
        } else {
            brakdostepu.setName("&2Expowisko &8[ &a12. &8]");
            this.loreBRAKDOSTEPU("110");
            brakdostepu.addGlowing();
            brakdostepu.setLore(lore);
            gui.setItem(11, brakdostepu.toItemStack());
        }
        // expowisko 13
        if (user.getLvl() > 119) {
            expowisko13.setName("&6&lBRAK NAZWY");
            this.loreEXPOWISKA("&aON", "&f120");
            expowisko13.addGlowing();
            expowisko13.setLore(lore);
            gui.setItem(12, expowisko13.toItemStack());
        } else {
            brakdostepu.setName("&2Expowisko &8[ &a13. &8]");
            this.loreBRAKDOSTEPU("120");
            brakdostepu.addGlowing();
            brakdostepu.setLore(lore);
            gui.setItem(12, brakdostepu.toItemStack());
        }
        // expowisko 14
        if (user.getLvl() > 124) {
            expowisko14.setName("&6&lBRAK NAZWY");
            this.loreEXPOWISKA("&aON", "&f125");
            expowisko14.addGlowing();
            expowisko14.setLore(lore);
            gui.setItem(13, expowisko14.toItemStack());
        } else {
            brakdostepu.setName("&2Expowisko &8[ &a14. &8]");
            this.loreBRAKDOSTEPU("125");
            brakdostepu.addGlowing();
            brakdostepu.setLore(lore);
            gui.setItem(13, brakdostepu.toItemStack());
        }
        // expowisko 15
        if (user.getLvl() > 129) {
            expowisko15.setName("&6&lBRAK NAZWY");
            this.loreEXPOWISKA("&aON", "&f130");
            expowisko15.addGlowing();
            expowisko15.setLore(lore);
            gui.setItem(14, expowisko15.toItemStack());
        } else {
            brakdostepu.setName("&2Expowisko &8[ &a15. &8]");
            this.loreBRAKDOSTEPU("130");
            brakdostepu.addGlowing();
            brakdostepu.setLore(lore);
            gui.setItem(14, brakdostepu.toItemStack());
        }*/
        player.openInventory(gui);
    }
    /*public void openTeleporterDODATKOWEMAPY(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&9&lTELEPORTER &7- INNE"));
        final User user = rpgcore.getUserManager().find(player.getUniqueId());

        fillInventory.setName(" ");
        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, fillInventory.toItemStack());
        }

        if (user.getLvl() > 59) {
            miasto1.setName("&6Miasto pierwsze");
            this.loreEXPOWISKA("&cOFF", "&f60");
            miasto1.addGlowing();
            miasto1.setLore(lore);
            gui.setItem(11, miasto1.toItemStack());
        } else {
            brakdostepu.setName("&6Miasto pierwsze");
            this.loreBRAKDOSTEPU("60");
            brakdostepu.addGlowing();
            brakdostepu.setLore(lore);
            gui.setItem(11, brakdostepu.toItemStack());
        }

        if (user.getLvl() > 49) {
            demontower.setName("&cDemonTower");
            this.loreEXPOWISKA("&cOFF", "&f50");
            demontower.addGlowing();
            demontower.setLore(lore);
            gui.setItem(15, demontower.toItemStack());
        } else {
            brakdostepu.setName("&cDemonTower");
            this.loreBRAKDOSTEPU("50");
            brakdostepu.addGlowing();
            brakdostepu.setLore(lore);
            gui.setItem(15, brakdostepu.toItemStack());
        }
        player.openInventory(gui);
    }*/
    private void loreEXPOWISKA(final String lorePVP, final String lorePOZIOM) {
        this.lore.clear();
        this.lore.add(" ");
        this.lore.add("&8* &fWymagany poziom: &6" + lorePOZIOM);
        this.lore.add("&8* &4PvP: " + lorePVP );
        this.lore.add(" ");
        this.lore.add("&8* &9Status: &a&lODBLOKOWANE");
        this.lore.add(" ");
    }
    private void loreBRAKDOSTEPU(final String lorePOZIOM) {
        this.lore.clear();
        this.lore.add(" ");
        this.lore.add("&8* &9Status: &4&lZABLOKOWANE");
        this.lore.add("&8* &bWymagany poziom: &f" + lorePOZIOM);
        this.lore.add(" ");
    }
}
