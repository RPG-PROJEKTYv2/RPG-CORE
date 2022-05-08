package rpg.rpgcore.managers.npc;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.ItemBuilder;
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

    private final ItemBuilder fill = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15);
    private final ItemBuilder sklep = new ItemBuilder(Material.DOUBLE_PLANT);
    private final ItemBuilder misje = new ItemBuilder(Material.BOOK);
    private final ItemBuilder stats = new ItemBuilder(Material.PAPER);
    private final ItemBuilder misjeItem = new ItemBuilder(Material.BOOK_AND_QUILL);
    private final ItemBuilder misjeItemEnd = new ItemBuilder(Material.BOOK);
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
}
