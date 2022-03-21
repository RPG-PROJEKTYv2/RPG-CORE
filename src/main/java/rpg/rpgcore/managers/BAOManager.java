package rpg.rpgcore.managers;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.*;

public class BAOManager {

    private final RPGCORE rpgcore;

    public BAOManager(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    private HashMap<UUID, String> baoBonusy = new HashMap<>();
    private HashMap<UUID, String> baoBonusyWartosci = new HashMap<>();

    public HashMap<UUID, String> getBaoBonusyMap() {
        return this.baoBonusy;
    }

    public HashMap<UUID, String> getBaoBonusyWartosciMap() {
        return this.baoBonusyWartosci;
    }

    public String getBaoBonusy(final UUID uuid) {
        return this.baoBonusy.get(uuid);
    }

    public String getBaoBonusyWartosci(final UUID uuid) {
        return this.baoBonusyWartosci.get(uuid);
    }

    public void updateBaoBonusy(final UUID uuid, final String nowyBonus) {
        if (!(this.baoBonusy.containsKey(uuid))) {
            this.baoBonusy.put(uuid, nowyBonus);
            return;
        }
        this.baoBonusy.replace(uuid, nowyBonus);
    }

    public void updateBaoBonusyWartosci(final UUID uuid, final String noweWartosci) {
        if (!(this.baoBonusyWartosci.containsKey(uuid))) {
            this.baoBonusyWartosci.put(uuid, noweWartosci);
            return;
        }
        this.baoBonusyWartosci.replace(uuid, noweWartosci);
    }

    public ItemStack getItemDoLosowania() {
        final ItemStack itemDoLosowania = new ItemStack(Material.COAL, 1, (short) 1);
        final ItemMeta itemDoLosowaniaMeta = itemDoLosowania.getItemMeta();
        final ArrayList<String> itemDoLosowaniaLore = new ArrayList<>();

        itemDoLosowaniaLore.add(Utils.format("&8Ten magiczny kamien pozwala Ci"));
        itemDoLosowaniaLore.add(Utils.format("&8zmienic swoje bonusy w &6Stole Magi"));
        itemDoLosowaniaLore.add(Utils.format("&8Pamietaj &c&lAdministracja &8nie odpowiada za zmieniane bonusy"));

        itemDoLosowaniaMeta.setDisplayName(Utils.format("&3&lKamien Bao"));
        itemDoLosowaniaMeta.setLore(itemDoLosowaniaLore);

        itemDoLosowania.setItemMeta(itemDoLosowaniaMeta);


        return itemDoLosowania;
    }

    public Inventory baoGUI(final UUID uuid) {
        Inventory baoGUI = Bukkit.createInventory(null, 3*9, Utils.format("&6&lSTOL MAGII"));

        final ItemStack item = new ItemStack(Material.BOOK_AND_QUILL);
        final ItemStack background = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
        final ItemStack rollItem = new ItemStack(Material.NETHER_STAR);

        final ItemMeta itemMeta = item.getItemMeta();
        final ItemMeta backgroudItemMeta = background.getItemMeta();
        final ItemMeta rollItemMeta = rollItem.getItemMeta();

        final ArrayList<String> rollItemLore = new ArrayList<>();

        backgroudItemMeta.setDisplayName("");
        background.setItemMeta(backgroudItemMeta);


        rollItemMeta.setDisplayName(Utils.format("&4&lZmien bonusy Stolu Magi"));

        rollItemLore.add(Utils.format("&8Kliknij na ten item, zeby zmienic"));
        rollItemLore.add(Utils.format("&8swoje bonusy w &6stole magi"));
        rollItemLore.add(Utils.format("&8Pamietaj, ze &c&lAdministracja &8nie odpowiada za zmienione bonusy"));

        rollItemMeta.setLore(rollItemLore);
        rollItem.setItemMeta(rollItemMeta);

        String[] baoBonusy = this.getBaoBonusy(uuid).split(",");
        String[] baoBonusyWartosci = this.getBaoBonusyWartosci(uuid).split(",");


        for (int i = 0; i < baoGUI.getSize(); i++) {
            baoGUI.setItem(i ,background);
        }
        for (int i = 0; i < baoBonusy.length; i++){
            if (baoBonusy[i].equalsIgnoreCase("brak bonusu")){
                itemMeta.setDisplayName(Utils.format("&6&l" + baoBonusy[i]));
            } else {
                if (i == 3){
                    if (baoBonusy[3].equalsIgnoreCase("dodatkowe hp")){
                        itemMeta.setDisplayName(Utils.format("&6&l" + baoBonusy[3] + ":&c&l " + baoBonusyWartosci[3] + " HP"));
                    } else if (baoBonusy[3].equalsIgnoreCase("dodatkowe obrazenia")){
                        itemMeta.setDisplayName(Utils.format("&6&l" + baoBonusy[3] + ":&c&l " + baoBonusyWartosci[3] + " DMG"));
                    } else {
                        itemMeta.setDisplayName(Utils.format("&6&l" + baoBonusy[3] + ":&c&l " + baoBonusyWartosci[3] + "%"));
                    }
                } else {
                    itemMeta.setDisplayName(Utils.format("&6&l" + baoBonusy[i] + ":&c&l " + baoBonusyWartosci[i] + "%"));
                }
            }

            item.setItemMeta(itemMeta);

            baoGUI.setItem(10 + i, item);
        }

        //                  ROLL ITEM                 \\
        baoGUI.setItem(16, rollItem);
        return baoGUI;
    }

    public void losujNoweBonusy(final UUID uuid) {
        final Random random = new Random();
        String[] baoBonusy = this.getBaoBonusy(uuid).split(",");
        String[] baoBonusyWartosci = this.getBaoBonusyWartosci(uuid).split(",");

        //                  LOSOWANIE 1 BONUSU BAO                  \\
        final int nowyBaoBonus1 = random.nextInt(3) + 1;
        int nowaWartoscBonusu;
        switch (nowyBaoBonus1) {
            case 1:
                baoBonusy[0] = "Srednie obrazenia";
                nowaWartoscBonusu = random.nextInt(50) +1;
                baoBonusyWartosci[0] = String.valueOf(nowaWartoscBonusu);
                break;
            case 2:
                baoBonusy[0] = "Srednie obrazenie przeciwko ludziom";
                nowaWartoscBonusu = random.nextInt(75) +1;
                baoBonusyWartosci[0] = String.valueOf(nowaWartoscBonusu);
                break;
            case 3:
                baoBonusy[0] = "Srednie obrazenie przeciwko potworom";
                nowaWartoscBonusu = random.nextInt(75) +1;
                baoBonusyWartosci[0] = String.valueOf(nowaWartoscBonusu);
                break;
        }

        //                  LOSOWANIE 2 BONUSU BAO                  \\

        final int nowyBaoBonus2 = random.nextInt(3) + 1;
        switch (nowyBaoBonus2) {
            case 1:
                baoBonusy[1] = "Srednia defensywa";
                nowaWartoscBonusu = random.nextInt(50) +1;
                baoBonusyWartosci[1] = String.valueOf(nowaWartoscBonusu);
                break;
            case 2:
                baoBonusy[1] = "Srednia defensywa przeciwko ludziom";
                nowaWartoscBonusu = random.nextInt(75) +1;
                baoBonusyWartosci[1] = String.valueOf(nowaWartoscBonusu);
                break;
            case 3:
                baoBonusy[1] = "Srednia defensywa przeciwko potworom";
                nowaWartoscBonusu = random.nextInt(75) +1;
                baoBonusyWartosci[1] = String.valueOf(nowaWartoscBonusu);
                break;
        }

        //                  LOSOWANIE 3 BONUSU BAO                  \\

        final int nowyBaoBonus3 = random.nextInt(3) + 1;
        switch (nowyBaoBonus3) {
            case 1:
                baoBonusy[2] = "Dodatkowy EXP";
                nowaWartoscBonusu = random.nextInt(15) +1;
                baoBonusyWartosci[2] = String.valueOf(nowaWartoscBonusu);
                break;
            case 2:
                baoBonusy[2] = "Przeszycie bloku ciosu";
                nowaWartoscBonusu = random.nextInt(20) +1;
                baoBonusyWartosci[2] = String.valueOf(nowaWartoscBonusu);
                break;
            case 3:
                baoBonusy[2] = "Szansa na cios krytyczny";
                nowaWartoscBonusu = random.nextInt(25) +1;
                baoBonusyWartosci[2] = String.valueOf(nowaWartoscBonusu);
                break;
        }

        //                  LOSOWANIE 4 BONUSU BAO                  \\

        final int nowyBaoBonus4 = random.nextInt(3) + 1;
        switch (nowyBaoBonus4) {
            case 1:
                baoBonusy[3] = "Dodatkowe HP";
                nowaWartoscBonusu = random.nextInt(10) +1;
                baoBonusyWartosci[3] = String.valueOf(nowaWartoscBonusu);
                break;
            case 2:
                baoBonusy[3] = "Blok ciosu";
                nowaWartoscBonusu = random.nextInt(15) +1;
                baoBonusyWartosci[3] = String.valueOf(nowaWartoscBonusu);
                break;
            case 3:
                baoBonusy[3] = "Dodatkowe obrazenia";
                nowaWartoscBonusu = random.nextInt(5000) +1;
                baoBonusyWartosci[3] = String.valueOf(nowaWartoscBonusu);
                break;
        }
        final String bonusyPoLosowaniu = baoBonusy[0] + "," + baoBonusy[1] + "," + baoBonusy[2] + "," + baoBonusy[3];
        final String wartosciBonusowPoLosowaniu = baoBonusyWartosci[0] + "," + baoBonusyWartosci[1] + "," + baoBonusyWartosci[2] + "," + baoBonusyWartosci[3];

        this.updateBaoBonusy(uuid, bonusyPoLosowaniu);
        this.updateBaoBonusyWartosci(uuid, wartosciBonusowPoLosowaniu);
    }

}
