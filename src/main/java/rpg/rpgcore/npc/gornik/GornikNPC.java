package rpg.rpgcore.npc.gornik;

import com.google.common.collect.ImmutableSet;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.npc.gornik.enums.GornikLevels;
import rpg.rpgcore.npc.gornik.enums.GornikMissions;
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
        final GornikUser user = find(uuid).getGornikUser();

        if (rpgcore.getUserManager().find(uuid).getLvl() < 75) {
            player.sendMessage(Utils.format("&6&lGornik &8&l>> &7Musisz zdobyc jescze troche doswiadczenia aby uzyskac dostep do mojej kopalni."));
            return;
        }

        player.teleport(new Location(Bukkit.getWorld("Gornik"), -49.5, 9, 29.5, 180, 0));
        player.sendMessage(Utils.format("&6&lGornik &8&l>> &7Witaj w mojej kopalni. Mam nadzieje, ze zostaniesz tu na dluzej."));
        int slowness = 3;
        int miningFatique = 3;
        int haste = 0;
        if (user.isD2()) {
            slowness -= 1;
        }
        if (user.isD6_2()) {
            miningFatique -= 1;
        }
        if (user.isD6_5()) {
            slowness -= 2;
        }
        if (user.isD6_4()) {
            haste += 1;
        }
        if (user.isD9_2()) {
            haste += 1;
        }

        if (slowness != 0) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, slowness));
        }
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, Integer.MAX_VALUE, miningFatique));
        if (haste != 0) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, haste));
        }



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
        gui.setItem(13, new ItemBuilder(Material.ANVIL).setName("&a&lOsadzanie Krysztalow").toItemStack().clone()); //TODO Dodac sprawdzanie dalszego dt do ulepszania gemow (nie wiem czy to od tego?)
        gui.setItem(15, new ItemBuilder(Material.WORKBENCH).setName("&6&lCraftingi").toItemStack());
        if (user.getMission() < 14) {
            gui.setItem(22, new ItemBuilder(Material.BEACON).setName("&6&lDrzewko Umiejetnosci").setLore(Arrays.asList("&8W tym miejscu rozwiniesz swoje", "&8prawdziwe umiejetnosci gornicze oraz", "&8uzyskasz kilka ciekawych bonusow", "", "&c&lZablokowano! &8(Ukoncz 14 misje w kampanii, zeby odblokowac)")).addGlowing().toItemStack().clone());
        } else  {
            gui.setItem(22, new ItemBuilder(Material.BEACON).setName("&6&lDrzewko Umiejetnosci").setLore(Arrays.asList("&8W tym miejscu rozwiniesz swoje", "&8prawdziwe umiejetnosci gornicze oraz", "&8uzyskasz kilka ciekawych bonusow")).addGlowing().toItemStack().clone());

        }
        player.openInventory(gui);
    }

    public void openSklep(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 9, Utils.format("&6&lSklep Gorniczy"));
        gui.setItem(0, new ItemBuilder(Material.STONE_PICKAXE).setName("&6Kilof Gornika").setLore(Arrays.asList("&7Poziom: &61", "&7Exp: &60&7/&6100", "", "&7Koszt: &6100,000,000&2$")).toItemStack());

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

    public ItemStack updateKilofExp(final ItemStack itemStack, final int exp) {
        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(itemStack);
        NBTTagCompound tag = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
        tag.setInt("Exp", tag.getInt("Exp") + exp);

        final ItemStack item = CraftItemStack.asBukkitCopy(nmsStack);
        final ItemMeta meta = item.getItemMeta();
        final List<String> lore = meta.getLore();
        lore.set(1, Utils.format("&7Exp: &6" + tag.getInt("Exp") + "&7/&6100"));
        meta.setLore(lore);
        item.setItemMeta(meta);
        if (tag.getInt("Exp") >= tag.getInt("ReqExp")) {
            return this.updateLevel(item);
        }
        return item;
    }

    public ItemStack updateLevel(final ItemStack itemStack) {
        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(itemStack);
        NBTTagCompound tag = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
        tag.setInt("Lvl", tag.getInt("Lvl") + 1);
        tag.setInt("Exp", 0);
        tag.setInt("ReqExp", tag.getInt("ReqExp") + GornikLevels.getByLvl(tag.getInt("Lvl")).getExp());
        final ItemStack item = CraftItemStack.asBukkitCopy(nmsStack);
        final ItemMeta meta = item.getItemMeta();
        final List<String> lore = meta.getLore();
        lore.set(0, Utils.format("&7Poziom: &6" + tag.getInt("Lvl")));
        lore.set(1, Utils.format("&7Exp: &60&7/&6" + tag.getInt("ReqExp")));
        meta.setLore(lore);
        item.setItemMeta(meta);
        if (tag.getInt("Lvl") == 10 || tag.getInt("Lvl") == 20 || tag.getInt("Lvl") == 30) {
            switch (itemStack.getType()) {
                case STONE_PICKAXE:
                    item.setType(Material.GOLD_PICKAXE);
                    break;
                case GOLD_PICKAXE:
                    item.setType(Material.IRON_PICKAXE);
                    break;
                case IRON_PICKAXE:
                    item.setType(Material.DIAMOND_PICKAXE);
                    break;
            }
        }
        if (tag.getInt("Lvl") == 40 || tag.getInt("Lvl") == 50) {
            item.addEnchantment(Enchantment.DIG_SPEED, 1);
        }
        return item;
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
