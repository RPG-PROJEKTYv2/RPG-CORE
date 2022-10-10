package rpg.rpgcore.npc.gornik;

import com.google.common.collect.ImmutableSet;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.*;

public class GornikNPC {
    private final RPGCORE rpgcore;
    private final Map<UUID, GornikObject> userMap;

    public GornikNPC(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
        this.userMap = rpgcore.getMongoManager().loadAllGornik();
        this.loadMissions();
    }

    public void loadMissions() {

    }

    public void onClick(final Player player) {
        final UUID uuid = player.getUniqueId();
        if (!this.userMap.containsKey(uuid)) {
            this.userMap.put(uuid, new GornikObject(uuid));
        }

        if (rpgcore.getUserManager().find(uuid).getLvl() < 75) {
            player.sendMessage(Utils.format("&6&lGornik &8&l>> &7Musisz zdobyc jescze troche doswiadczenia aby uzyskac dostep do mojej kopalni."));
            return;
        }

        player.teleport(new Location(Bukkit.getWorld("Gornik"), -49.5, 9, 29.5, 180, 0));
        player.sendMessage(Utils.format("&6&lGornik &8&l>> &7Witaj w mojej kopalni. Mam nadzieje, ze zostaniesz tu na dluzej."));

        //TODO Ogarnac ta funkcje, bo moze duzo lagowac (Moze async???), ewentualnie po prostu zrobic hardcoded rudy i tyle
        //rollOres();
    }

    public void openGornikGUI(final Player player) {
        final UUID uuid = player.getUniqueId();
        final GornikUser user = this.find(uuid).getGornikUser();
        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&6&lGornik"));

        for (int i = 0; i < 27; i++) {
            gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName(" ").toItemStack());
        }

        gui.setItem(4, new ItemBuilder(Material.EMERALD).setName("&2&lSklep").addGlowing().toItemStack());
        gui.setItem(11, new ItemBuilder(Material.BOOK_AND_QUILL).setName("&4&lKampania Gornika").setLore(Arrays.asList("&f&lBonusy", "&7Srednia Odpornosc: &c" + user.getSredniaOdpornosc() + "%",
                "&7Przeszycie Bloku Ciosu: &c" + user.getPrzeszycieBloku() + "%", "&7Blok Ciosu: &c" + user.getBlokCiosu() + "%")).addGlowing().toItemStack().clone());
        gui.setItem(13, new ItemBuilder(Material.ANVIL).setName("&a&lUlepszanie").toItemStack().clone()); //TODO Dodac sprawdzanie dalszego dt do ulepszania gemow (nie wiem czy to od tego?)
        gui.setItem(15, new ItemBuilder(Material.WORKBENCH).setName("&6&lCraftingi").toItemStack());
        if (user.getMission() < 14) {
            gui.setItem(22, new ItemBuilder(Material.BEACON).setName("&6&lDrzewko Umiejetnosci").setLore(Arrays.asList("&8W tym miejscu rozwiniesz swoje", "&8prawdziwe umiejetnosci gornicze oraz", "&8uzyskasz kilka ciekawych bonusow", "", "&c&lZablokowano! &8(Ukoncz 14 misje w kampanii, zeby odblokowac)")).addGlowing().toItemStack().clone());
        } else  {
            gui.setItem(22, new ItemBuilder(Material.BEACON).setName("&6&lDrzewko Umiejetnosci").setLore(Arrays.asList("&8W tym miejscu rozwiniesz swoje", "&8prawdziwe umiejetnosci gornicze oraz", "&8uzyskasz kilka ciekawych bonusow")).addGlowing().toItemStack().clone());

        }
        player.openInventory(gui);
    }

    public void openKampania(final Player player) {
        final UUID uuid = player.getUniqueId();
        final GornikUser user = this.find(uuid).getGornikUser();
        final Inventory gui = Bukkit.createInventory(null, 54, Utils.format("&6&lKampania Gornika"));

        for (int i = 0; i < 54; i++) {
            if (!((i > 9 && i < 17) || (i > 18 && i < 26) || (i > 27 && i < 35) || (i > 36 && i < 44))) {
                gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName(" ").toItemStack());
            }
        }


        for (int i = 1; i < 29; i++) {
            if (i < user.getMission()) {
                gui.setItem(gui.firstEmpty(), new ItemBuilder(Material.BOOK).setName("&c&lMisja " + i).setLore(Arrays.asList("&a&lUkonczone!")).addGlowing().toItemStack().clone());
            } else if (i == user.getMission()) {
                gui.setItem(gui.firstEmpty(), new ItemBuilder(Material.BOOK).setName("&c&lMisja " + i).setLore(GornikMissions.getMission(i).getLore()).toItemStack().clone());
            } else {
                gui.setItem(gui.firstEmpty(), new ItemBuilder(Material.BARRIER).setName("&c&lMisja " + i).setLore(Arrays.asList("&c&lZeby odblkowac, ukoncz poprzednie zadanie")).toItemStack().clone());
            }
        }


        player.openInventory(gui);
    }

    public void openDrzewko(final Player player) {
        final UUID uuid = player.getUniqueId();
        final GornikUser user = this.find(uuid).getGornikUser();
        final Inventory gui = Bukkit.createInventory(null, 54, Utils.format("&c&lDrzewko Gornika"));

        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName(" ").toItemStack());
        }

        if (user.isD1()) {
            gui.setItem(49, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 5).setName("&6Berserk I").setLore(Arrays.asList("&6Srednie Obrazenia: &c1%", "", "&a&lOdblokowano!")).addGlowing().toItemStack());
        } else {
            gui.setItem(49, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName("&6Berserk I").setLore(Arrays.asList("&6Srednie Obrazenia: &c1%", "&8Koszt:", "&8- &61x Legendarny Krysztal Wzmocnienia", "&8- &650,000,000&2$", "", "&4&lZablokowano!")).toItemStack());
        }
        if (user.isD2()) {
            gui.setItem(40, new ItemBuilder(Material.STAINED_CLAY, 1, (short) 5).setName("&6Szybki Kopacz I").setLore(Arrays.asList("&6Zmniejszenie Spowolnienia o &c1 &6poziom", "&7Pozwala zmniejszyc poziom efektu &5Spowolnienia", "&7nalozonego pod czas przebywania w Kopalni", "", "&a&lOdblokowano!")).addGlowing().toItemStack());
        } else {
            gui.setItem(40, new ItemBuilder(Material.STAINED_CLAY, 1, (short) 14).setName("&6Szybki Kopacz I").setLore(Arrays.asList("&6Zmniejszenie Spowolnienia o &c1 &6poziom", "&7Pozwala zmniejszyc poziom efektu &5Spowolnienia", "&7nalozonego pod czas przebywania w Kopalni", "", "&8Koszt:", "&8- &63x Legendarny Krysztal Wzmocnienia", "&8- &6150,000,000&2$", "", "&4&lZablokowano!")).toItemStack());
        }


        // GORA
        if (user.isD3_3()) {
            gui.setItem(31, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 5).setName("&6Mistrz Destrukcji I").setLore(Arrays.asList("&6Przebicie Pancerza: &c1%", "&7Zwieksza szanse na &6100% &7przebicie", "&7pancerza ofiary", "", "&a&lOdblokowano!")).addGlowing().toItemStack());
        } else {
            gui.setItem(31, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName("&6Mistrz Destrukcji I").setLore(Arrays.asList("&6Przebicie Pancerza: &c1%", "&7Zwieksza szanse na &6100% &7przebicie", "&7pancerza ofiary", "&8Koszt:", "&8- &61x Legendarny Krysztal Wzmocnienia", "&8- &650,000,000&2$", "", "&4&lZablokowano!")).toItemStack());
        }
        if (user.isD4_7()) {
            gui.setItem(22, new ItemBuilder(Material.STAINED_CLAY, 1, (short) 5).setName("&6Pogromca Potworow").setLore(Arrays.asList("&6Odpornosc Na Potwory: &c5%", "&6Silny Na Potwory: &c5%", "", "&a&lOdblokowano!")).addGlowing().toItemStack());
        } else {
            gui.setItem(22, new ItemBuilder(Material.STAINED_CLAY, 1, (short) 14).setName("&6Pogromca Potworow").setLore(Arrays.asList("&6Odpornosc Na Potwory: &c5%", "&6Silny Na Potwory: &c5%", "&8Koszt:", "&8- &63x Legendarny Krysztal Wzmocnienia", "&8- &6150,000,000&2$", "", "&4&lZablokowano!")).toItemStack());
        }
        if (user.isD5_5()) {
            gui.setItem(13, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 5).setName("&6Szczesliwy Lowca").setLore(Arrays.asList("&6Silny Na Potwory: &c5%", "&6Szczescie &c10", "", "&a&lOdblokowano!")).addGlowing().toItemStack());
        } else {
            gui.setItem(13, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName("&6Szczesliwy Lowca").setLore(Arrays.asList("&6Silny Na Potwory: &c5%", "&6Szczescie &c10", "&8Koszt:", "&8- &61x Legendarny Krysztal Wzmocnienia", "&8- &650,000,000&2$", "", "&4&lZablokowano!")).toItemStack());
        }
        if (user.isD6_5()) {
            gui.setItem(4, new ItemBuilder(Material.STAINED_CLAY, 1, (short) 5).setName("&6Szybki Kopacz II").setLore(Arrays.asList("&6Zmniejszenie Spowolnienia o &c2 &6poziomy", "&7Pozwala zmniejszyc poziom efektu &5Spowolnienia", "&7nalozonego pod czas przebywania w Kopalni", "", "&a&lOdblokowano!")).addGlowing().toItemStack());
        } else {
            gui.setItem(4, new ItemBuilder(Material.STAINED_CLAY, 1, (short) 14).setName("&6Szybki Kopacz II").setLore(Arrays.asList("&6Zmniejszenie Spowolnienia o &c2 &6poziomy", "&7Pozwala zmniejszyc poziom efektu &5Spowolnienia", "&7nalozonego pod czas przebywania w Kopalni", "&8Koszt:", "&8- &63x Legendarny Krysztal Wzmocnienia", "&8- &6150,000,000&2$", "", "&4&lZablokowano!")).toItemStack());
        }
        if (user.isD7_3()) {
            gui.setItem(3, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 5).setName("&6Szczesciarz").setLore(Arrays.asList("&6Szczescie &c15", "", "&a&lOdblokowano!")).addGlowing().toItemStack());
        } else {
            gui.setItem(3, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName("&6Szczesciarz").setLore(Arrays.asList("&6Szczescie &c15", "&8Koszt:", "&8- &61x Legendarny Krysztal Wzmocnienia", "&8- &650,000,000&2$", "", "&4&lZablokowano!")).toItemStack());
        }
        if (user.isD7_4()) {
            gui.setItem(5, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 5).setName("&6Mistrz Destrukcji II").setLore(Arrays.asList("&6Przebicie Pancerza: &c2%", "&7Zwieksza szanse na &6100% &7przebicie", "&7pancerza ofiary", "", "&a&lOdblokowano!")).addGlowing().toItemStack());
        } else {
            gui.setItem(5, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName("&6Mistrz Destrukcji II").setLore(Arrays.asList("&6Przebicie Pancerza: &c2%", "&7Zwieksza szanse na &6100% &7przebicie", "&7pancerza ofiary", "&8Koszt:", "&8- &61x Legendarny Krysztal Wzmocnienia", "&8- &650,000,000&2$", "", "&4&lZablokowano!")).toItemStack());
        }


        // PRAWO
        if (user.isD3_2()) {
            gui.setItem(41, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 5).setName("&6Tarczownik I").setLore(Arrays.asList("&6Bloku Ciosu: &c2%", "", "&a&lOdblokowano!")).addGlowing().toItemStack());
        } else {
            gui.setItem(41, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName("&6Tarczownik I").setLore(Arrays.asList("&6Bloku Ciosu: &c2%", "&8Koszt:", "&8- &61x Legendarny Krysztal Wzmocnienia", "&8- &650,000,000&2$", "", "&4&lZablokowano!")).toItemStack());
        }
        if (user.isD4_1()) {
            gui.setItem(42, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 5).setName("&6Tarczownik II").setLore(Arrays.asList("&6Bloku Ciosu: &c3%", "", "&a&lOdblokowano!")).addGlowing().toItemStack());
        } else {
            gui.setItem(42, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName("&6Tarczownik II").setLore(Arrays.asList("&6Bloku Ciosu: &c3%", "&8Koszt:", "&8- &61x Legendarny Krysztal Wzmocnienia", "&8- &650,000,000&2$", "", "&4&lZablokowano!")).toItemStack());
        }
        if (user.isD4_2()) {
            gui.setItem(51, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 5).setName("&6Silacz I").setLore(Arrays.asList("&6Dodatkowe Obrazenia: &c100", "", "&a&lOdblokowano!")).addGlowing().toItemStack());
        } else {
            gui.setItem(51, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName("&6Silacz I").setLore(Arrays.asList("&6Dodatkowe Obrazenia: &c100", "&8Koszt:", "&8- &61x Legendarny Krysztal Wzmocnienia", "&8- &650,000,000&2$", "", "&4&lZablokowano!")).toItemStack());
        }
        if (user.isD4_3()) {
            gui.setItem(33, new ItemBuilder(Material.STAINED_CLAY, 1, (short) 5).setName("&6Pasjonat").setLore(Arrays.asList("&6Srednie Obrazenia: &c4%", "&6Dodatkowe Obrazenia: &c150", "", "&a&lOdblokowano!")).addGlowing().toItemStack());
        } else {
            gui.setItem(33, new ItemBuilder(Material.STAINED_CLAY, 1, (short) 14).setName("&6Pasjonat").setLore(Arrays.asList("&6Srednie Obrazenia: &c4%", "&6Dodatkowe Obrazenia: &c150", "&8Koszt:", "&8- &63x Legendarny Krysztal Wzmocnienia", "&8- &6150,000,000&2$", "", "&4&lZablokowano!")).toItemStack());
        }
        if (user.isD5_1()) {
            gui.setItem(34, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 5).setName("&6Berserk II").setLore(Arrays.asList("&6Srednie Obrazenia: &c2%", "", "&a&lOdblokowano!")).addGlowing().toItemStack());
        } else {
            gui.setItem(34, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName("&6Berserk II").setLore(Arrays.asList("&6Srednie Obrazenia: &c2%", "&8Koszt:", "&8- &63x Legendarny Krysztal Wzmocnienia", "&8- &6150,000,000&2$", "", "&4&lZablokowano!")).toItemStack());
        }
        if (user.isD6_1()) {
            gui.setItem(35, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 5).setName("&6Berserk III").setLore(Arrays.asList("&6Srednie Obrazenia: &c3%", "", "&a&lOdblokowano!")).addGlowing().toItemStack());
        } else {
            gui.setItem(35, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName("&6Berserk III").setLore(Arrays.asList("&6Srednie Obrazenia: &c3%", "&8Koszt:", "&8- &63x Legendarny Krysztal Wzmocnienia", "&8- &6150,000,000&2$", "", "&4&lZablokowano!")).toItemStack());
        }
        if (user.isD5_2()) {
            gui.setItem(24, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 5).setName("&6Silacz II").setLore(Arrays.asList("&6Dodatkowe Obrazenia: &c50", "", "&a&lOdblokowano!")).addGlowing().toItemStack());
        } else {
            gui.setItem(24, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName("&6Silacz II").setLore(Arrays.asList("&6Dodatkowe Obrazenia: &c50", "&8Koszt:", "&8- &63x Legendarny Krysztal Wzmocnienia", "&8- &6150,000,000&2$", "", "&4&lZablokowano!")).toItemStack());
        }
        if (user.isD6_2()) {
            gui.setItem(15, new ItemBuilder(Material.STAINED_CLAY, 1, (short) 5).setName("&6Oczyszczenie I").setLore(Arrays.asList("&6Zmniejszenie Wyczerpania o &c1 &6poziom", "&7Zmniejsza wartosc efektu &8Wyczerpanie", "&7nakladanego pod czas pobytu w kopalni", "", "&a&lOdblokowano!")).addGlowing().toItemStack());
        } else {
            gui.setItem(15, new ItemBuilder(Material.STAINED_CLAY, 1, (short) 14).setName("&6Oczyszczenie I").setLore(Arrays.asList("&6Zmniejszenie Wyczerpania o &c1 &6poziom", "&7Zmniejsza wartosc efektu &8Wyczerpanie", "&7nakladanego pod czas pobytu w kopalni", "&8Koszt:", "&8- &63x Legendarny Krysztal Wzmocnienia", "&8- &6150,000,000&2$", "", "&4&lZablokowano!")).toItemStack());
        }
        if (user.isD7_1()) {
            gui.setItem(16, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 5).setName("&6Silacz III").setLore(Arrays.asList("&6Dodatkowe Obrazenia: &c50", "", "&a&lOdblokowano!")).addGlowing().toItemStack());
        } else {
            gui.setItem(16, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName("&6Silacz III").setLore(Arrays.asList("&6Dodatkowe Obrazenia: &c50", "&8Koszt:", "&8- &63x Legendarny Krysztal Wzmocnienia", "&8- &6150,000,000&2$", "", "&4&lZablokowano!")).toItemStack());
        }
        if (user.isD8_1()) {
            gui.setItem(17, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 5).setName("&6Silacz IV").setLore(Arrays.asList("&6Dodatkowe Obrazenia: &c50", "", "&a&lOdblokowano!")).addGlowing().toItemStack());
        } else {
            gui.setItem(17, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName("&6Silacz IV").setLore(Arrays.asList("&6Dodatkowe Obrazenia: &c50", "&8Koszt:", "&8- &63x Legendarny Krysztal Wzmocnienia", "&8- &6150,000,000&2$", "", "&4&lZablokowano!")).toItemStack());
        }
        if (user.isD9_1()) {
            gui.setItem(8, new ItemBuilder(Material.STAINED_CLAY, 1, (short) 5).setName("&6Oczyszczenie II").setLore(Arrays.asList("&6Dodatkowe Obrazenia: &c100", "&6Zmniejszenie Wyczerpania o &c1 &6poziom", "&7Zmniejsza wartosc efektu &8Wyczerpanie", "&7nakladanego pod czas pobytu w kopalni", "", "&a&lOdblokowano!")).addGlowing().toItemStack());
        } else {
            gui.setItem(8, new ItemBuilder(Material.STAINED_CLAY, 1, (short) 14).setName("&6Oczyszczenie II").setLore(Arrays.asList("&6Dodatkowe Obrazenia: &c100", "&6Zmniejszenie Wyczerpania o &c1 &6poziom", "&7Zmniejsza wartosc efektu &8Wyczerpanie", "&7nakladanego pod czas pobytu w kopalni", "&8Koszt:", "&8- &63x Legendarny Krysztal Wzmocnienia", "&8- &6150,000,000&2$", "", "&4&lZablokowano!")).toItemStack());
        }



        // LEWO
        if (user.isD3_1()) {
            gui.setItem(39, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 5).setName("&6Skrytobojca I").setLore(Arrays.asList("&6Przeszycie Bloku Ciosu: &c2%", "", "&a&lOdblokowano!")).addGlowing().toItemStack());
        } else {
            gui.setItem(39, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName("&6Skrytobojca I").setLore(Arrays.asList("&6Przeszycie Bloku Ciosu: &c2%", "&8Koszt:", "&8- &61x Legendarny Krysztal Wzmocnienia", "&8- &650,000,000&2$", "", "&4&lZablokowano!")).toItemStack());
        }
        if (user.isD4_4()) {
            gui.setItem(38, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 5).setName("&6Skrytobojca II").setLore(Arrays.asList("&6Przeszycie Bloku Ciosu: &c3%", "", "&a&lOdblokowano!")).addGlowing().toItemStack());
        } else {
            gui.setItem(38, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName("&6Skrytobojca II").setLore(Arrays.asList("&6Przeszycie Bloku Ciosu: &c3%", "&8Koszt:", "&8- &61x Legendarny Krysztal Wzmocnienia", "&8- &650,000,000&2$", "", "&4&lZablokowano!")).toItemStack());
        }
        if (user.isD4_5()) {
            gui.setItem(47, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 5).setName("&6Skrytobojca III").setLore(Arrays.asList("&6Przeszycie Bloku Ciosu: &c5%", "", "&a&lOdblokowano!")).addGlowing().toItemStack());
        } else {
            gui.setItem(47, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName("&6Skrytobojca III").setLore(Arrays.asList("&6Przeszycie Bloku Ciosu: &c5%", "&8Koszt:", "&8- &61x Legendarny Krysztal Wzmocnienia", "&8- &650,000,000&2$", "", "&4&lZablokowano!")).toItemStack());
        }
        if (user.isD4_6()) {
            gui.setItem(29, new ItemBuilder(Material.STAINED_CLAY, 1, (short) 5).setName("&6Pogromca Pokolen I").setLore(Arrays.asList("&6Odpornosc Na Ludzi: &c5%", "&6Silny Na Ludzi: &c5%", "", "&a&lOdblokowano!")).addGlowing().toItemStack());
        } else {
            gui.setItem(29, new ItemBuilder(Material.STAINED_CLAY, 1, (short) 14).setName("&6Pogromca Pokolen I").setLore(Arrays.asList("&6Odpornosc Na Ludzi: &c5%", "&6Silny Na Ludzi: &c5%", "&8Koszt:", "&8- &61x Legendarny Krysztal Wzmocnienia", "&8- &650,000,000&2$", "", "&4&lZablokowano!")).toItemStack());
        }
        if (user.isD5_3()) {
            gui.setItem(28, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 5).setName("&6Pogromca Pokolen II").setLore(Arrays.asList("&6Silny Na Ludzi: &c2%", "", "&a&lOdblokowano!")).addGlowing().toItemStack());
        } else {
            gui.setItem(28, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName("&6Pogromca Pokolen II").setLore(Arrays.asList("&6Silny Na Ludzi: &c2%", "&8Koszt:", "&8- &61x Legendarny Krysztal Wzmocnienia", "&8- &650,000,000&2$", "", "&4&lZablokowano!")).toItemStack());
        }
        if (user.isD6_3()) {
            gui.setItem(27, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 5).setName("&6Pogromca Pokolen III").setLore(Arrays.asList("&6Odpornosc Na Ludzi: &c1%", "", "&a&lOdblokowano!")).addGlowing().toItemStack());
        } else {
            gui.setItem(27, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName("&6Pogromca Pokolen III").setLore(Arrays.asList("&6Odpornosc Na Ludzi: &c1%", "&8Koszt:", "&8- &61x Legendarny Krysztal Wzmocnienia", "&8- &650,000,000&2$", "", "&4&lZablokowano!")).toItemStack());
        }
        if (user.isD5_4()) {
            gui.setItem(20, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 5).setName("&6Pogromca Pokolen IV").setLore(Arrays.asList("&6Odpornosc Na Ludzi: &c2%", "", "&a&lOdblokowano!")).addGlowing().toItemStack());
        } else {
            gui.setItem(20, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName("&6Pogromca Pokolen IV").setLore(Arrays.asList("&6Odpornosc Na Ludzi: &c2%", "&8Koszt:", "&8- &61x Legendarny Krysztal Wzmocnienia", "&8- &650,000,000&2$", "", "&4&lZablokowano!")).toItemStack());
        }
        if (user.isD6_4()) {
            gui.setItem(11, new ItemBuilder(Material.STAINED_CLAY, 1, (short) 5).setName("&6Przyspieszenie I").setLore(Arrays.asList("&6Zwieksza poziom Przyspieszenia o &c1 &6poziom", "&7Zwieksza poziom efetku &ePrzyspieszenie &7nakladanego", "&7pod czas pobytu w Kopalni", "", "&a&lOdblokowano!")).addGlowing().toItemStack());
        } else {
            gui.setItem(11, new ItemBuilder(Material.STAINED_CLAY, 1, (short) 14).setName("&6Przyspieszenie I").setLore(Arrays.asList("&6Zwieksza poziom Przyspieszenia o &c1 &6poziom", "&7Zwieksza poziom efetku &ePrzyspieszenie &7nakladanego", "&7pod czas pobytu w Kopalni", "&8Koszt:", "&8- &61x Legendarny Krysztal Wzmocnienia", "&8- &650,000,000&2$", "", "&4&lZablokowano!")).toItemStack());
        }
        if (user.isD7_2()) {
            gui.setItem(10, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 5).setName("&6Pogromca Pokolen V").setLore(Arrays.asList("&6Odpornosc Na Ludzi: &c2%", "", "&a&lOdblokowano!")).addGlowing().toItemStack());
        } else {
            gui.setItem(10, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName("&6Pogromca Pokolen V").setLore(Arrays.asList("&6Odpornosc Na Ludzi: &c2%", "&8Koszt:", "&8- &61x Legendarny Krysztal Wzmocnienia", "&8- &650,000,000&2$", "", "&4&lZablokowano!")).toItemStack());
        }
        if (user.isD8_2()) {
            gui.setItem(9, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 5).setName("&6Pogromca Pokolen VI").setLore(Arrays.asList("&6Silny Na Ludzi: &c3%", "", "&a&lOdblokowano!")).addGlowing().toItemStack());
        } else {
            gui.setItem(9, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName("&6Pogromca Pokolen VI").setLore(Arrays.asList("&6Silny Na Ludzi: &c3%", "&8Koszt:", "&8- &61x Legendarny Krysztal Wzmocnienia", "&8- &650,000,000&2$", "", "&4&lZablokowano!")).toItemStack());
        }
        if (user.isD9_2()) {
            gui.setItem(0, new ItemBuilder(Material.STAINED_CLAY, 1, (short) 5).setName("&6Przyspieszenie II").setLore(Arrays.asList("&6Przeszycie Bloku Ciosu: &c5%", "&6Zwieksza poziom Przyspieszenia o &c1 &6poziom", "&7Zwieksza poziom efetku &ePrzyspieszenie &7nakladanego", "&7pod czas pobytu w Kopalni", "", "&a&lOdblokowano!")).addGlowing().toItemStack());
        } else {
            gui.setItem(0, new ItemBuilder(Material.STAINED_CLAY, 1, (short) 14).setName("&6Przyspieszenie II").setLore(Arrays.asList("&6Przeszycie Bloku Ciosu: &c5%", "&6Zwieksza poziom Przyspieszenia o &c1 &6poziom", "&7Zwieksza poziom efetku &ePrzyspieszenie &7nakladanego", "&7pod czas pobytu w Kopalni", "&8Koszt:", "&8- &61x Legendarny Krysztal Wzmocnienia", "&8- &650,000,000&2$", "", "&4&lZablokowano!")).toItemStack());
        }

        player.openInventory(gui);
    }


    public void rollOres() {
        List<Chunk> chunkList = new ArrayList<>();
        chunkList.add(Bukkit.getWorld("Gornik").getChunkAt(5, -1));
        chunkList.add(Bukkit.getWorld("Gornik").getChunkAt(4, -1));
        chunkList.add(Bukkit.getWorld("Gornik").getChunkAt(3, -1));
        chunkList.add(Bukkit.getWorld("Gornik").getChunkAt(5, 0));
        chunkList.add(Bukkit.getWorld("Gornik").getChunkAt(4, 0));
        chunkList.add(Bukkit.getWorld("Gornik").getChunkAt(3, 0));
        List<Block> blockList = new ArrayList<>();

        for (Chunk chunk : chunkList) {
            final int bx = chunk.getX() << 4;
            final int bz = chunk.getZ() << 4;
            for (int x = bx; x < bx + 16; x++) {
                for (int z = bz; z < bz + 16; z++) {
                    for (int y = 4; y < 13; y++) {
                        if (chunk.getBlock(x, y, z).getType() != Material.AIR && chunk.getBlock(x, y, z).getType() != Material.BEDROCK && chunk.getBlock(x, y, z).getType() != Material.GRASS
                                && chunk.getBlock(x, y, z).getType() != Material.DIRT) {
                            blockList.add(chunk.getBlock(x, y, z));
                        }
                    }
                }
            }
        }
        int changed = 0;
        final Random random = new Random();
        while (changed < 10) {
            final int index = random.nextInt(blockList.size());
            if (blockList.get(index).getRelative(BlockFace.DOWN).getType() == Material.AIR || blockList.get(index).getRelative(BlockFace.UP).getType() == Material.AIR ||
                    blockList.get(index).getRelative(BlockFace.NORTH).getType() == Material.AIR || blockList.get(index).getRelative(BlockFace.SOUTH).getType() == Material.AIR ||
                    blockList.get(index).getRelative(BlockFace.EAST).getType() == Material.AIR || blockList.get(index).getRelative(BlockFace.WEST).getType() == Material.AIR) {
                blockList.get(index).setType(Material.COAL_ORE);
                changed++;
            }
        }
    }

    public void add(GornikObject gornikObject) {
        this.userMap.put(gornikObject.getID(), gornikObject);
    }

    public GornikObject find(final UUID uuid) {
        this.userMap.computeIfAbsent(uuid, k -> new GornikObject(uuid));
        return this.userMap.get(uuid);
    }

    public ImmutableSet<GornikObject> getGornikObject() {
        return ImmutableSet.copyOf(this.userMap.values());
    }

}
