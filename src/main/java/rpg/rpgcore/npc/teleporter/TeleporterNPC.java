package rpg.rpgcore.npc.teleporter;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class TeleporterNPC {

    private final RPGCORE rpgcore;

    public TeleporterNPC(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    private final ItemBuilder fillInventory = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15);
    private final ArrayList<String> lore = new ArrayList<>();
    private final ItemBuilder expowisko1 = new ItemBuilder(Material.LEAVES);
    private final ItemBuilder expowisko2 = new ItemBuilder(Material.GRASS);
    private final ItemBuilder expowisko3 = new ItemBuilder(Material.VINE);
    private final ItemBuilder expowisko4 = new ItemBuilder(Material.WEB);
    private final ItemBuilder expowisko5 = new ItemBuilder(Material.PRISMARINE);
    private final ItemBuilder expowisko6 = new ItemBuilder(Material.ICE);
    private final ItemBuilder expowisko7 = new ItemBuilder(Material.BLAZE_POWDER);
    private final ItemBuilder expowisko8 = new ItemBuilder(Material.IRON_BARDING);
    private final ItemBuilder expowisko9 = new ItemBuilder(Material.SAND);
    private final ItemBuilder expowisko10 = new ItemBuilder(Material.SMOOTH_BRICK);
    private final ItemBuilder expowisko11 = new ItemBuilder(Material.PRISMARINE_SHARD);
    private final ItemBuilder expowisko12 = new ItemBuilder(Material.SNOW_BALL);
    private final ItemBuilder expowisko13 = new ItemBuilder(Material.HAY_BLOCK);


    private final ItemBuilder brakdostepu = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14);

    public void openTeleporterEXPOWISKA(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 54, Utils.format("&4&lTELEPORTER"));
        final User user = rpgcore.getUserManager().find(player.getUniqueId());
        fillInventory.setName(" ");
        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, fillInventory.toItemStack());
        }

        expowisko1.setName("&9&lPrzelecz Rozbojnikow");
        this.loreEXPOWISKA("&cOFF", "&f1");
        expowisko1.addGlowing();
        expowisko1.setLore(lore);
        gui.setItem(11, expowisko1.toItemStack());

        // expowisko 2
        if (user.getLvl() > 9) {
            expowisko2.setName("&2&lLas Goblinow");
            this.loreEXPOWISKA("&cOFF", "&f10");
            expowisko2.addGlowing();
            expowisko2.setLore(lore);
            gui.setItem(12, expowisko2.toItemStack());
        } else {
            brakdostepu.setName("&2Expowisko &8[ &a2. &8]");
            this.loreBRAKDOSTEPU("10");
            brakdostepu.addGlowing();
            brakdostepu.setLore(lore);
            gui.setItem(12, brakdostepu.toItemStack());
        }

        // expowisko 3
        if (user.getLvl() > 19) {
            expowisko3.setName("&a&lZapomniana Jungla");
            this.loreEXPOWISKA("&cOFF", "&f20");
            expowisko3.addGlowing();
            expowisko3.setLore(lore);
            gui.setItem(13, expowisko3.toItemStack());
        } else {
            brakdostepu.setName("&2Expowisko &8[ &a3. &8]");
            this.loreBRAKDOSTEPU("20");
            brakdostepu.addGlowing();
            brakdostepu.setLore(lore);
            gui.setItem(13, brakdostepu.toItemStack());
        }

        // expowisko 4
        if (user.getLvl() > 29) {
            expowisko4.setName("&7&lOpuszczony Port");
            this.loreEXPOWISKA("&cOFF", "&f30");
            expowisko4.addGlowing();
            expowisko4.setLore(lore);
            gui.setItem(14, expowisko4.toItemStack());
        } else {
            brakdostepu.setName("&2Expowisko &8[ &a4. &8]");
            this.loreBRAKDOSTEPU("30");
            brakdostepu.addGlowing();
            brakdostepu.setLore(lore);
            gui.setItem(14, brakdostepu.toItemStack());
        }

        // expowisko 5
        if (user.getLvl() > 39) {
            expowisko5.setName("&b&lPodwodna Krypta");
            this.loreEXPOWISKA("&cOFF", "&f40");
            expowisko5.addGlowing();
            expowisko5.setLore(lore);
            gui.setItem(15, expowisko5.toItemStack());
        } else {
            brakdostepu.setName("&2Expowisko &8[ &a5. &8]");
            this.loreBRAKDOSTEPU("40");
            brakdostepu.addGlowing();
            brakdostepu.setLore(lore);
            gui.setItem(15, brakdostepu.toItemStack());
        }

        // expowisko 6
        if (user.getLvl() > 49) {
            expowisko6.setName("&f&lMrozna Dolina");
            this.loreEXPOWISKA("&aON", "&f50");
            expowisko6.addGlowing();
            expowisko6.setLore(lore);
            gui.setItem(28, expowisko6.toItemStack());
        } else {
            brakdostepu.setName("&2Expowisko &8[ &a6. &8]");
            this.loreBRAKDOSTEPU("50");
            brakdostepu.addGlowing();
            brakdostepu.setLore(lore);
            gui.setItem(28, brakdostepu.toItemStack());
        }

        // expowisko 7
        if (user.getLvl() > 59) {
            expowisko7.setName("&4&lOgnista Kraina");
            this.loreEXPOWISKA("&aON", "&f60");
            expowisko7.addGlowing();
            expowisko7.setLore(lore);
            gui.setItem(29, expowisko7.toItemStack());
        } else {
            brakdostepu.setName("&2Expowisko &8[ &a7. &8]");
            this.loreBRAKDOSTEPU("60");
            brakdostepu.addGlowing();
            brakdostepu.setLore(lore);
            gui.setItem(29, brakdostepu.toItemStack());
        }

        // expowisko 8
        if (user.getLvl() > 69) {
            expowisko8.setName("&7&lPodziemne Katakumby");
            this.loreEXPOWISKA("&aON", "&f70");
            expowisko8.addGlowing();
            expowisko8.setLore(lore);
            gui.setItem(30, expowisko8.toItemStack());
        } else {
            brakdostepu.setName("&2Expowisko &8[ &a8. &8]");
            this.loreBRAKDOSTEPU("70");
            brakdostepu.addGlowing();
            brakdostepu.setLore(lore);
            gui.setItem(30, brakdostepu.toItemStack());
        }

        // expowisko 9
        if (user.getLvl() > 79) {
            expowisko9.setName("&e&lPustynia Ptasznikow");
            this.loreEXPOWISKA("&aON", "&f80");
            expowisko9.addGlowing();
            expowisko9.setLore(lore);
            gui.setItem(31, expowisko9.toItemStack());
        } else {
            brakdostepu.setName("&2Expowisko &8[ &a9. &8]");
            this.loreBRAKDOSTEPU("80");
            brakdostepu.addGlowing();
            brakdostepu.setLore(lore);
            gui.setItem(31, brakdostepu.toItemStack());
        }

        // expowisko 10
        if (user.getLvl() > 89) {
            expowisko10.setName("&5&lSekretne Korytarze");
            this.loreEXPOWISKA("&aON", "&f90");
            expowisko10.addGlowing();
            expowisko10.setLore(lore);
            gui.setItem(32, expowisko10.toItemStack());
        } else {
            brakdostepu.setName("&2Expowisko &8[ &a10. &8]");
            this.loreBRAKDOSTEPU("90");
            brakdostepu.addGlowing();
            brakdostepu.setLore(lore);
            gui.setItem(32, brakdostepu.toItemStack());
        }

        // expowisko 11
        if (user.getLvl() > 99) {
            expowisko11.setName("&b&lZatopiona Swiatynia");
            this.loreEXPOWISKA("&aON", "&f100");
            expowisko11.addGlowing();
            expowisko11.setLore(lore);
            gui.setItem(33, expowisko11.toItemStack());
        } else {
            brakdostepu.setName("&2Expowisko &8[ &a11. &8]");
            this.loreBRAKDOSTEPU("100");
            brakdostepu.addGlowing();
            brakdostepu.setLore(lore);
            gui.setItem(33, brakdostepu.toItemStack());
        }
        // expowisko 12
        if (user.getLvl() > 109) {
            expowisko12.setName("&f&lKrysztalowa Sala");
            this.loreEXPOWISKA("&aON", "&f110");
            expowisko12.addGlowing();
            expowisko12.setLore(lore);
            gui.setItem(34, expowisko12.toItemStack());
        } else {
            brakdostepu.setName("&2Expowisko &8[ &a12. &8]");
            this.loreBRAKDOSTEPU("110");
            brakdostepu.addGlowing();
            brakdostepu.setLore(lore);
            gui.setItem(34, brakdostepu.toItemStack());
        }
        // expowisko 13
        if (user.getLvl() > 119) {
            expowisko13.setName("&6&lTajemnicza Siedziba Kultu");
            this.loreEXPOWISKA("&aON", "&f120");
            expowisko13.addGlowing();
            expowisko13.setLore(lore);
            gui.setItem(40, expowisko13.toItemStack());
        } else {
            brakdostepu.setName("&2Expowisko &8[ &a13. &8]");
            this.loreBRAKDOSTEPU("120");
            brakdostepu.addGlowing();
            brakdostepu.setLore(lore);
            gui.setItem(40, brakdostepu.toItemStack());
        }
        /*if (rpgcore.getGuildManager().hasGuild(player.getUniqueId())) {
            gui.setItem(53, new ItemBuilder(Material.NETHER_BRICK).setName("&4&lKapliczka").setLore(Arrays.asList("Dodac ladowanie z obiektu, ile ma hp, jaka gildia aktualnie ja posiada, stan poszczegolnych wiez (fake graczy)")).addGlowing().toItemStack());
        }*/
        player.openInventory(gui);
    }

    private String loreEXPOWISKA(final String lorePVP, final String lorePOZIOM) {
        this.lore.clear();
        this.lore.add(" ");
        this.lore.add("&8* &fWymagany poziom: &6" + lorePOZIOM);
        this.lore.add("&8* &4PvP: " + lorePVP);
        this.lore.add(" ");
        this.lore.add("&8* &9Status: &a&lODBLOKOWANE");
        this.lore.add(" ");
        return lorePVP;
    }

    private void loreBRAKDOSTEPU(final String lorePOZIOM) {
        this.lore.clear();
        this.lore.add(" ");
        this.lore.add("&8* &9Status: &4&lZABLOKOWANE");
        this.lore.add("&8* &fWymagany poziom: &f" + lorePOZIOM);
        this.lore.add(" ");
    }


    public void teleportExp1(final Player player) {
        switch (new Random().nextInt(3)) {
            case 0:
                player.teleport(new Location(Bukkit.getWorld("1-10map"), -68.492, 72, -296.465, 125.7F, 0.1F));
                return;
            case 1:
                player.teleport(new Location(Bukkit.getWorld("1-10map"), 61.024, 72, -83.305, 59.3F, -1.4F));
                return;
            case 2:
                player.teleport(new Location(Bukkit.getWorld("1-10map"), -4.201, 72, -168.145, -149.7F, 0.5F));
        }
    }

    public void teleportExp2(final Player player) {
        switch (new Random().nextInt(3)) {
            case 0:
                player.teleport(new Location(Bukkit.getWorld("10-20map"), -25.678, 75, -226.866, -177.6F, 0.1F));
                return;
            case 1:
                player.teleport(new Location(Bukkit.getWorld("10-20map"), 93.504, 76, -263.091, 43.4F, -0.0F));
                return;
            case 2:
                player.teleport(new Location(Bukkit.getWorld("10-20map"), 12.739, 74, -276.367, -40.8F, -0.2F));
        }
    }

    public void teleportExp3(final Player player) {
        switch (new Random().nextInt(3)) {
            case 0:
                player.teleport(new Location(Bukkit.getWorld("20-30map"), 62.146, 75, 132.175, -169.5F, 0.1F));
                return;
            case 1:
                player.teleport(new Location(Bukkit.getWorld("20-30map"), 166.707, 75, 58.443, -24.1F, -0.6F));
                return;
            case 2:
                player.teleport(new Location(Bukkit.getWorld("20-30map"), 172.943, 75, 198.131, 100.8F, -0.9F));
        }
    }

    public void teleportExp4(final Player player) {
        player.teleport(new Location(Bukkit.getWorld("30-40map"), 27.5, 78, -121.5, -90F, -0F));
    }

    public void teleportExp5(final Player player) {
        player.teleport(new Location(Bukkit.getWorld("40-50map"), -89.5, 69, -24.5, -180F, -0F));
    }

    public void teleportExp6(final Player player) {
        switch (new Random().nextInt(3)) {
            case 0:
                player.teleport(new Location(Bukkit.getWorld("50-60map"), -204.700, 72, 213.632, -68.3F, 1.1F));
                return;
            case 1:
                player.teleport(new Location(Bukkit.getWorld("50-60map"), -247.274, 72, 94.258, -129.8F, 1.2F));
                return;
            case 2:
                player.teleport(new Location(Bukkit.getWorld("50-60map"), -80.465, 72, 174.225, -0.8F, 0.1F));
        }
    }

    public void teleportExp7(final Player player) {
        switch (new Random().nextInt(2)) {
            case 0:
                player.teleport(new Location(Bukkit.getWorld("60-70map"), 146, 76, 285, 96.7F, 1.8F));
                return;
            case 1:
                player.teleport(new Location(Bukkit.getWorld("60-70map"), 114.255, 75, 31.376, -36.9F, 2.2F));
        }
    }

    public void teleportExp8(final Player player) {
        switch (new Random().nextInt(2)) {
            case 0:
                player.teleport(new Location(Bukkit.getWorld("70-80map"), -93.572, 84, 248.489, -89.9F, 0.4F));
                return;
            case 1:
                player.teleport(new Location(Bukkit.getWorld("70-80map"), 92.550, 84, 248.421, 90.2F, 0.1F));
        }
    }

    public void teleportExp9(final Player player) {
        switch (new Random().nextInt(2)) {
            case 0:
                player.teleport(new Location(Bukkit.getWorld("80-90map"), -17, 67, 391, -165F, -0F));
                return;
            case 1:
                player.teleport(new Location(Bukkit.getWorld("80-90map"), 31, 67, 37, 23.4F, 0.9F));
        }
    }

    public void teleportExp10(final Player player) {
        switch (new Random().nextInt(3)) {
            case 0:
                player.teleport(new Location(Bukkit.getWorld("90-100map"), 379.765, 93, 136.684, 124.2F, 1.5F));
                return;
            case 1:
                player.teleport(new Location(Bukkit.getWorld("90-100map"), 365.186, 107, 79.967, -119.2F, 8.7F));
                return;
            case 2:
                player.teleport(new Location(Bukkit.getWorld("90-100map"), 345.545, 87, 62.483, -116.5F, 2.4F));
        }
    }

    public void teleportExp11(final Player player) {
        player.teleport(new Location(Bukkit.getWorld("100-110map"), 143, 83, 222, 90, 1));
    }

    public void teleportExp12(final Player player) {
        switch (new Random().nextInt(2)) {
            case 0:
                player.teleport(new Location(Bukkit.getWorld("110-120map"), -100, 66, -156, -90.1F, -0.6F));
                return;
            case 1:
                player.teleport(new Location(Bukkit.getWorld("110-120map"), 100, 66, -156, 90.1F, -0.6F));
        }
    }

    public void teleportExp13(final Player player) {
        switch (new Random().nextInt(2)) {
            case 0:
                player.teleport(new Location(Bukkit.getWorld("120-130map"), -13, 80, -94, 7.2F, 0.9F));
                return;
            case 1:
                player.teleport(new Location(Bukkit.getWorld("120-130map"), 1, 79, 79, 176.4F, 0.8F));
        }
    }
}
