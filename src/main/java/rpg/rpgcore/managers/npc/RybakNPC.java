package rpg.rpgcore.managers.npc;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.RandomItems;
import rpg.rpgcore.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class RybakNPC {

    private final RPGCORE rpgcore;

    public RybakNPC(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    private final HashMap<UUID, String> ryabkMisje = new HashMap<>();
    private final HashMap<UUID, Double> ryabkSrednieDMG = new HashMap<>();
    private final HashMap<UUID, Double> ryabkDodatkowyDMG = new HashMap<>();
    private final HashMap<UUID, Double> ryabkBlok = new HashMap<>();
    private final HashMap<UUID, Double> ryabkSredniDef = new HashMap<>();
    private final HashMap<UUID, Integer> ryabkPostep = new HashMap<>();
    private final HashMap<Integer, String> misjeRybackie = new HashMap<>(45);
    private final HashMap<Integer, Double> wymaganyExpWedki = new HashMap<>(50);

    private final ItemBuilder fill = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15);
    private final ItemBuilder sklep = new ItemBuilder(Material.DOUBLE_PLANT);
    private final ItemBuilder misje = new ItemBuilder(Material.BOOK);
    private final ItemBuilder stats = new ItemBuilder(Material.PAPER);
    private final ItemBuilder wedka = new ItemBuilder(Material.FISHING_ROD);

    private final RandomItems<ItemStack> rybakDrops = new RandomItems<>();

    private final List<String> lore = new ArrayList<>();


    public void openRybakGUI(final Player player) {
        final Inventory rybakGUI = Bukkit.createInventory(null, 27, Utils.format("&6&lMenu Rybaka"));

        fill.setName(" ").hideFlag();

        for (int i = 0; i < rybakGUI.getSize(); i++) {
            rybakGUI.setItem(i, fill.toItemStack());
        }

        lore.clear();

        lore.add(" ");
        lore.add("&8&o*Kliknij*&8 zeby zobaczyc jakie misje ma dla ciebie rybak");

        misje.setName("&4&lKampania Rybacka").addGlowing().setLore(lore);
        rybakGUI.setItem(10, misje.toItemStack());


        lore.clear();

        lore.add(" ");
        lore.add("&8&o*Kliknij*&8 zeby otworzyc sklep rybacki i poznac oferte rybaka");

        sklep.setName("&a&lSklep Rybacki").addGlowing().setLore(lore);
        rybakGUI.setItem(16, sklep.toItemStack());


        lore.clear();

        lore.add("&3Statystyki gracza: &f" + player.getName());
        lore.add("&8Ponizej znajduja sie twoje statystki z lowienia");
        lore.add("&f&lMISJE");
        lore.add("&8- &bSrednie Obrazenia: &f+ " + this.getPlayerRybakSredniDMG(player.getUniqueId()));
        lore.add("&8- &bSrednia Defensywa: &f+ " + this.getPlayerRybakSredniDef(player.getUniqueId()));
        lore.add("&8- &bDodatkowe Obrazenia: &f+ " + this.getPlayerRybakDodatkowyDMG(player.getUniqueId()));
        lore.add("&8- &bBlok Ciosu: &f+ " + this.getPlayerRybakBlok(player.getUniqueId()));
        lore.add("");
        lore.add("&f&lOSIAGNIECIA");
        lore.add("&8- &bWylowione Ryby: &f" + rpgcore.getPlayerManager().getPlayerOsRybak(player.getUniqueId()));


        stats.setName("&b&lTwoje Statystyki").addGlowing().setLore(lore);
        rybakGUI.setItem(13, stats.toItemStack());


        player.openInventory(rybakGUI);
    }

    public void openRybakSklep(final Player player) {
        final Inventory rybakGUI = Bukkit.createInventory(null, 9, Utils.format("&a&lSklep Rybacki"));

        fill.setName(" ").hideFlag();

        for (int i = 0; i < rybakGUI.getSize(); i++) {
            rybakGUI.setItem(i, fill.toItemStack());
        }

        lore.clear();

        lore.add("&bWlasciciel: &f" + player.getName());
        lore.add("&bPoziom: &f0");
        lore.add("&bExp: &f0&b/&f" + this.wymaganyExpWedki.get(1));
        lore.add("&bWylowione ryby: &f0");
        lore.add(" ");
        lore.add("&f&lBonusy");
        lore.add("&8- &bSzansa na podwojne wylowienie: &f0.05%");
        lore.add("&8- &bSzansa na skrzynie rybaka: &f0.005%");
        lore.add(" ");
        lore.add("&2Cena: &6100 000 000 &2$");

        wedka.setName("&6Wedka").setLore(lore);
        rybakGUI.setItem(4, wedka.toItemStack());

        player.openInventory(rybakGUI);
    }

    public void openRybakKampania(final Player player) {
        final Inventory kampaniaGui = Bukkit.createInventory(null, 45, Utils.format("&4&lKampania Rybacka"));

        final ItemBuilder misjeItem = new ItemBuilder(Material.BOOK_AND_QUILL);
        final ItemBuilder misjeDone = new ItemBuilder(Material.BOOK);
        final ItemBuilder previousNotDone = new ItemBuilder(Material.BARRIER);

        final String[] playerMisje = rpgcore.getRybakNPC().getPlayerRybakMisje(player.getUniqueId()).split(",");
        final List<String> lore = new ArrayList<>();

        fill.setName(" ").hideFlag();

        for (int i = 0; i < kampaniaGui.getSize(); i++) {
            if (playerMisje[i].equals("true")) {
                lore.clear();
                lore.add("&a&lUKONCZONE");
                kampaniaGui.setItem(i, misjeDone.setName("&c&lMisja #" + (i + 1)).setLore(lore).addGlowing().toItemStack().clone());
            } else {
                if (i != 0 && kampaniaGui.getItem(i - 1).getType().equals(Material.BOOK_AND_QUILL)) {
                    lore.clear();

                    lore.add(" ");
                    lore.add("&cNajpierw musisz wykonac poprzednia misje");
                    lore.add("&czeby otrzymac dostep do kolejnych");

                    for (int j = i; j < kampaniaGui.getSize(); j++) {
                        kampaniaGui.setItem(j, previousNotDone.setName("&c&lMisja #" + (j + 1)).setLore(lore).addGlowing().toItemStack().clone());
                    }
                    break;
                } else {
                    String[] misja = this.misjeRybackie.get(i).split(";");
                    lore.clear();
                    lore.add(" ");
                    lore.add("&f&lMisja:");
                    lore.add("&3" + misja[0] + " &f" + misja[1] + "x " + misja[2]);
                    lore.add(" ");
                    lore.add("&f&lPostep:");
                    lore.add("&b" + this.ryabkPostep.get(player.getUniqueId()) + " &f/&b " + misja[1] + " &8(&b" + Utils.procentFormat.format((long) (this.ryabkPostep.get(player.getUniqueId()) / Integer.parseInt(misja[1])) * 100) + "%&8)");

                    kampaniaGui.setItem(i, misjeItem.setName("&c&lMisja #" + (i + 1)).setLore(lore).toItemStack().clone());
                }
            }
        }


        player.openInventory(kampaniaGui);
    }

    public void loadRybakMisje() {
        for (int i = 0; i < 45; i++) {
            this.misjeRybackie.put(i, "Wylow;10;&6Karasi");
        }
    }

    public void loadExpWedka() {
        double wymaganyExp = 100.0;
        for (int i = 1; i <= 50; i++) {
            this.wymaganyExpWedki.put(i, wymaganyExp);
            wymaganyExp += 150;
        }
    }

    public void loadRybakDrops() {
        /*\
        Material.RAW_FISH, 1, (short) 1 - RAW SALMON
        Material.RAW_FISH, 1, (short) 2 - CLOWNFISH
        Material.RAW_FISH, 1, (short) 3 - PUFFERFISH
        Material.COOCKED_FISH, 1, (short) 1 - COOCKED SALMON
         */
        final ItemBuilder sledz = new ItemBuilder(Material.RAW_FISH);
        final ItemBuilder dorsz = new ItemBuilder(Material.RAW_FISH, 1, (short) 1);
        final ItemBuilder losos = new ItemBuilder(Material.RAW_FISH, 1, (short) 1);
        final ItemBuilder krasnopiorka = new ItemBuilder(Material.RAW_FISH, 1, (short) 2);
        final ItemBuilder dorszCzarny = new ItemBuilder(Material.COOKED_FISH, 1, (short) 1);
        final ItemBuilder dorada = new ItemBuilder(Material.RAW_FISH);
        final ItemBuilder cierniczek = new ItemBuilder(Material.COOKED_FISH);
        final ItemBuilder fladra = new ItemBuilder(Material.RAW_FISH, 1, (short) 3);
        final ItemBuilder karas = new ItemBuilder(Material.RAW_FISH, 1, (short) 1);
        final ItemBuilder karp = new ItemBuilder(Material.COOKED_FISH);
        final ItemBuilder leszcz = new ItemBuilder(Material.COOKED_FISH, 1, (short) 1);
        final ItemBuilder makrela = new ItemBuilder(Material.COOKED_FISH);
        final ItemBuilder mintaj = new ItemBuilder(Material.COOKED_FISH);
        final ItemBuilder okon = new ItemBuilder(Material.RAW_FISH, 1, (short) 3);
        final ItemBuilder plotka = new ItemBuilder(Material.RAW_FISH, 1, (short) 1);
        final ItemBuilder nies = new ItemBuilder(Material.DIAMOND_BLOCK);


        final List<String> lore = new ArrayList<>();

        lore.add("&8&oChyba &8&n&orybak&r &8&otego potrzebuje");

        rybakDrops.add(0.06666, sledz.setName("&6Sledz").setLore(lore).hideFlag().toItemStack());
        rybakDrops.add(0.06666, dorsz.setName("&6Dorsz").setLore(lore).hideFlag().toItemStack());
        rybakDrops.add(0.06666, losos.setName("&6Losos").setLore(lore).hideFlag().toItemStack());
        rybakDrops.add(0.06666, krasnopiorka.setName("&6Krasnopiorka").setLore(lore).hideFlag().toItemStack());
        rybakDrops.add(0.06666, dorszCzarny.setName("&6Dorsz Czarny").setLore(lore).hideFlag().toItemStack());
        rybakDrops.add(0.06666, dorada.setName("&6Dorada").setLore(lore).hideFlag().toItemStack());
        rybakDrops.add(0.06666, cierniczek.setName("&6Cierniczek").setLore(lore).hideFlag().toItemStack());
        rybakDrops.add(0.06666, fladra.setName("&6Fladra").setLore(lore).hideFlag().toItemStack());
        rybakDrops.add(0.06666, karas.setName("&6Karas").setLore(lore).hideFlag().toItemStack());
        rybakDrops.add(0.06666, karp.setName("&6Karp").setLore(lore).hideFlag().toItemStack());
        rybakDrops.add(0.06666, leszcz.setName("&6Leszcz").setLore(lore).hideFlag().toItemStack());
        rybakDrops.add(0.06666, makrela.setName("&6Makrela").setLore(lore).hideFlag().toItemStack());
        rybakDrops.add(0.06666, mintaj.setName("&6Mintaj").setLore(lore).hideFlag().toItemStack());
        rybakDrops.add(0.06666, okon.setName("&6Okon").setLore(lore).hideFlag().toItemStack());
        rybakDrops.add(0.06666, plotka.setName("&6Plotka").setLore(lore).hideFlag().toItemStack());
        rybakDrops.add(0.001, nies.setName("&b&lNiesamowity przedmiot rybacki").addGlowing().toItemStack());
    }

    public ItemStack getDrop() {
        return this.rybakDrops.next();
    }

    public ItemStack givePlayerRod(final Player player) {
        final ItemBuilder wedkaGracza = new ItemBuilder(Material.FISHING_ROD);

        lore.clear();

        lore.add("&bWlasciciel: &f" + player.getName());
        lore.add("&bPoziom: &f0");
        lore.add("&bExp: &f0&b/&f" + this.wymaganyExpWedki.get(1));
        lore.add("&bWylowione ryby: &f0");
        lore.add(" ");
        lore.add("&f&lBonusy");
        lore.add("&8- &bSzansa na podwojne wylowienie: &f0.05%");
        lore.add("&8- &bSzansa na skrzynie rybaka: &f0.005%");

        wedkaGracza.setName("&6Wedka").setLore(lore);

        return wedkaGracza.toItemStack();
    }

    public void addStatsToRod(final Player player, final double fishExp) {

        if (player.getItemInHand().getType() != Material.FISHING_ROD) {
            return;
        }

        if (player.getItemInHand().getItemMeta().getDisplayName() == null || player.getItemInHand().getItemMeta().getLore() == null) {
            return;
        }

        final ItemStack playerRod = player.getItemInHand();
        final ItemMeta im = playerRod.getItemMeta();
        final List<String> lore = im.getLore();

        int rodLvl = Integer.parseInt(Utils.removeColor(lore.get(1)).replace("Poziom: ", "").trim());
        double rodExp = Double.parseDouble(Utils.removeColor(lore.get(2).replace("Exp: ", "").substring(0, Utils.removeColor(lore.get(2)).indexOf('/') + 1)));
        int fished = rpgcore.getPlayerManager().getPlayerOsRybak(player.getUniqueId());
        if (rodLvl == 50) {
            lore.set(3, "&bWylowione ryby: &f" + (fished + 1));
            im.setLore(Utils.format(lore));
            playerRod.setItemMeta(im);
            rpgcore.getPlayerManager().updatePlayerOsRybak(player.getUniqueId(), fished + 1);
            return;
        }

        lore.set(2, "&bExp: &f" + (rodExp + fishExp) + "&b/&f" + this.wymaganyExpWedki.get(rodLvl + 1));
        lore.set(3, "&bWylowione ryby: &f" + (fished + 1));
        rodExp += fishExp;

        if (rodExp >= this.wymaganyExpWedki.get(rodLvl + 1)) {
            rodLvl += 1;
            rodExp = 0.0;
            if (rodLvl == 50) {
                lore.set(1, "&bPoziom: &4&lMAX");
                lore.set(2, "&bExp: &4&lMAX");
            } else {
                lore.set(1, "&bPoziom: &f" + rodLvl);
                lore.set(2, "&bExp: &f" + rodExp + "&b/&f" + this.wymaganyExpWedki.get(rodLvl + 1));
            }
            double doubleDrop = Double.parseDouble(Utils.removeColor(lore.get(6)).replace("-", "").replace("Szansa na podwojne wylowienie:", "").replace(" ", "").replace("%", "").trim());
            double caseDrop = Double.parseDouble(Utils.removeColor(lore.get(7)).replace("-", "").replace("Szansa na skrzynie rybaka:", "").replace(" ", "").replace("%", "").trim());

            doubleDrop += 0.75;
            caseDrop += 0.25;

            lore.set(6, "&8- &bSzansa na podwojne wylowienie: &f" + Utils.wedkaFormat.format(doubleDrop) + "%");
            lore.set(7, "&8- &bSzansa na skrzynie rybaka: &f" + Utils.wedkaFormat.format(caseDrop) + "%");

            if (im.getEnchantLevel(Enchantment.LURE) < 5) {
                if (rodLvl % 5 == 0) {
                    im.addEnchant(Enchantment.LURE, playerRod.getEnchantmentLevel(Enchantment.LURE) + 1, true);
                }
            }
            if (im.getEnchantLevel(Enchantment.LUCK) < 5) {
                if (rodLvl % 10 == 0) {
                    im.addEnchant(Enchantment.LUCK, playerRod.getEnchantmentLevel(Enchantment.LUCK) + 1, true);
                }
            }
            player.sendMessage(Utils.format(Utils.RYBAK + " &7Twoja wedka zyskala nowe moce"));
        }

        im.setLore(Utils.format(lore));
        playerRod.setItemMeta(im);
        rpgcore.getPlayerManager().updatePlayerOsRybak(player.getUniqueId(), fished + 1);
        return;
    }

    @Getter
    public String getPlayerRybakMisje(final UUID uuid) {
        return this.ryabkMisje.get(uuid);
    }

    @Setter
    public void setPlayerRybakMisje(final UUID uuid, final String noweMisje) {
        this.ryabkMisje.put(uuid, noweMisje);
    }

    @Setter
    public void updatePlayerRybakMisje(final UUID uuid, final String noweMisje) {
        this.ryabkMisje.replace(uuid, noweMisje);
    }

    public boolean isInRybakMisjeMap(final UUID uuid) {
        return this.ryabkMisje.containsKey(uuid);
    }


    @Getter
    public double getPlayerRybakSredniDMG(final UUID uuid) {
        return this.ryabkSrednieDMG.get(uuid);
    }

    @Setter
    public void setPlayerRybakSredniDMG(final UUID uuid, final double nowyDMG) {
        this.ryabkSrednieDMG.put(uuid, nowyDMG);
    }

    @Setter
    public void updatePlayerRybakSredniDMG(final UUID uuid, final double nowyDMG) {
        this.ryabkSrednieDMG.replace(uuid, nowyDMG);
    }


    @Getter
    public double getPlayerRybakSredniDef(final UUID uuid) {
        return this.ryabkSredniDef.get(uuid);
    }

    @Setter
    public void setPlayerRybakSredniDef(final UUID uuid, final double nowyDef) {
        this.ryabkSredniDef.put(uuid, nowyDef);
    }

    @Setter
    public void updatePlayerRybakSredniDef(final UUID uuid, final double nowyDef) {
        this.ryabkSredniDef.replace(uuid, nowyDef);
    }


    @Getter
    public double getPlayerRybakBlok(final UUID uuid) {
        return this.ryabkBlok.get(uuid);
    }

    @Setter
    public void setPlayerRybakBlok(final UUID uuid, final double nowyBlok) {
        this.ryabkBlok.put(uuid, nowyBlok);
    }

    @Setter
    public void updatePlayerRybakBlok(final UUID uuid, final double nowyBlok) {
        this.ryabkBlok.replace(uuid, nowyBlok);
    }


    @Getter
    public double getPlayerRybakDodatkowyDMG(final UUID uuid) {
        return this.ryabkDodatkowyDMG.get(uuid);
    }

    @Setter
    public void setPlayerRybakDodatkowyDMG(final UUID uuid, final double nowyDodatkowyDMG) {
        this.ryabkDodatkowyDMG.put(uuid, nowyDodatkowyDMG);
    }

    @Setter
    public void updatePlayerRybakDodatowyDMG(final UUID uuid, final double nowyDodatkowyDMG) {
        this.ryabkDodatkowyDMG.replace(uuid, nowyDodatkowyDMG);
    }

    @Getter
    public int getPlayerPostep(final UUID uuid) {
        return this.ryabkPostep.get(uuid);
    }

    @Setter
    public void setPlayerPostep(final UUID uuid, final int postep) {
        this.ryabkPostep.put(uuid, postep);
    }

    @Setter
    public void updtaePlayerPostep(final UUID uuid, final int nowyPostep) {
        this.ryabkPostep.replace(uuid, nowyPostep);
    }
}
