package rpg.rpgcore.npc.trener;

import com.google.common.collect.ImmutableSet;
import jdk.nashorn.internal.ir.annotations.Immutable;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.lang.reflect.Array;
import java.util.*;

public class TrenerNPC {

    private final RPGCORE rpgcore;
    private final Map<UUID, TrenerObject> userMap;

    public TrenerNPC(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
        this.userMap = rpgcore.getMongoManager().loadAllTrener();
    }

    private final Map<UUID, Integer> points = new HashMap<>();
    private final Map<UUID, Integer> srednidmg = new HashMap<>();
    private final Map<UUID, Integer> srednidef = new HashMap<>();
    private final Map<UUID, Integer> hp = new HashMap<>();
    private final Map<UUID, Integer> blok = new HashMap<>();
    private final Map<UUID, Integer> przeszywka = new HashMap<>();
    private final Map<UUID, Integer> silnynaludzi = new HashMap<>();
    private final Map<UUID, Integer> defnaludzi = new HashMap<>();
    private final Map<UUID, Integer> kyt = new HashMap<>();

    public void openTrenerGUI(final Player player) {
        final UUID uuid = player.getUniqueId();
        final Inventory gui = Bukkit.createInventory(null, 9, Utils.format("&6Trener - Punkty: " + this.getPoints(uuid)));

        gui.setItem(0, this.getSredniDmgItem(uuid));
        gui.setItem(1, this.getSredniDefItem(uuid));
        gui.setItem(2, this.getHpItem(uuid));
        gui.setItem(3, this.getBlokItem(uuid));
        gui.setItem(4, this.getPrzeszywkaItem(uuid));
        gui.setItem(5, this.getSilnyNaludzItem(uuid));
        gui.setItem(6, this.getDefNaLudziItem(uuid));
        gui.setItem(7, this.getKrytItem(uuid));
        if (this.getSrednidmg(uuid) == 20 && this.getSrednidef(uuid) == 20 && this.getHP(uuid) == 10 && this.getBlok(uuid) == 10 && this.getPrzeszywka(uuid) == 20
                && this.getSilnynaludzi(uuid) == 20 && this.getDefnaludzi(uuid) == 20 && this.getKyt(uuid) == 10) {
            gui.setItem(8, new ItemBuilder(Material.BEACON).setName("&cD&6r&ez&ae&9w&dk&5o &cU&6m&ei&ae&9j&de&5t&cn&6o&es&ac&6i").toItemStack());
        } else {
            gui.setItem(8, new ItemBuilder(Material.WOOL, 1, (short) 14).setName("&cZresetuj Trenera").setLore(Arrays.asList("&7Koszt zresetowania statystyk: &65 000 000&2$")).toItemStack());
        }

        player.openInventory(gui);
    }


    public ItemStack getSredniDmgItem(final UUID uuid) {
        if (rpgcore.getCooldownManager().hasTrenerCooldown(uuid)) {
            return new ItemBuilder(Material.DIAMOND_SWORD).setName("&6Srednie Obrazenia").setLore(Arrays.asList("", "&7Postep ulepszenia: &c" + this.getSrednidmg(uuid) + "%&7/&c20%", "&cNastepny punkt mozesz dodac dopiero za:", "&f" + rpgcore.getCooldownManager().getPlayerTrenerCooldown(uuid))).hideFlag().toItemStack().clone();
        }
        return new ItemBuilder(Material.DIAMOND_SWORD).setName("&6Srednie Obrazenia").setLore(Arrays.asList("", "&7Postep ulepszenia: &c" + this.getSrednidmg(uuid) + "%&7/&c20%")).hideFlag().toItemStack().clone();
    }

    public ItemStack getSredniDefItem(final UUID uuid) {
        if (rpgcore.getCooldownManager().hasTrenerCooldown(uuid)) {
            return new ItemBuilder(Material.DIAMOND_CHESTPLATE).setName("&6Srednia Defensywa").setLore(Arrays.asList("", "&7Postep ulepszenia: &c" + this.getSrednidef(uuid) + "%&7/&c20%", "&cNastepny punkt mozesz dodac dopiero za:", "&f" + rpgcore.getCooldownManager().getPlayerTrenerCooldown(uuid))).hideFlag().toItemStack().clone();
        }
        return new ItemBuilder(Material.DIAMOND_CHESTPLATE).setName("&6Srednia Defensywa").setLore(Arrays.asList("", "&7Postep ulepszenia: &c" + this.getSrednidef(uuid) + "%&7/&c20%")).hideFlag().toItemStack().clone();
    }

    public ItemStack getHpItem(final UUID uuid) {
        if (rpgcore.getCooldownManager().hasTrenerCooldown(uuid)) {
            return new ItemBuilder(Material.GOLDEN_APPLE).setName("&6Dodatkowe HP").setLore(Arrays.asList("", "&7Postep ulepszenia: &c" + this.getHP(uuid) + "&7/&c10", "&cNastepny punkt mozesz dodac dopiero za:", "&f" + rpgcore.getCooldownManager().getPlayerTrenerCooldown(uuid))).hideFlag().toItemStack().clone();
        }
        return new ItemBuilder(Material.GOLDEN_APPLE).setName("&6Dodatkowe HP").setLore(Arrays.asList("", "&7Postep ulepszenia: &c" + this.getHP(uuid) + "&7/&c10")).hideFlag().toItemStack().clone();
    }

    public ItemStack getBlokItem(final UUID uuid) {
        if (rpgcore.getCooldownManager().hasTrenerCooldown(uuid)) {
            return new ItemBuilder(Material.CHAINMAIL_CHESTPLATE).setName("&6Blok Ciosu").setLore(Arrays.asList("", "&7Postep ulepszenia: &c" + this.getBlok(uuid) + "%&7/&c10%", "&cNastepny punkt mozesz dodac dopiero za:", "&f" + rpgcore.getCooldownManager().getPlayerTrenerCooldown(uuid))).hideFlag().toItemStack().clone();
        }
        return new ItemBuilder(Material.CHAINMAIL_CHESTPLATE).setName("&6Blok Ciosu").setLore(Arrays.asList("", "&7Postep ulepszenia: &c" + this.getBlok(uuid) + "%&7/&c10%")).hideFlag().toItemStack().clone();
    }

    public ItemStack getPrzeszywkaItem(final UUID uuid) {
        if (rpgcore.getCooldownManager().hasTrenerCooldown(uuid)) {
            return new ItemBuilder(Material.ARROW).setName("&6Przeszycie Bloku Ciosu").setLore(Arrays.asList("", "&7Postep ulepszenia: &c" + this.getPrzeszywka(uuid) + "%&7/&c20%", "&cNastepny punkt mozesz dodac dopiero za:", "&f" + rpgcore.getCooldownManager().getPlayerTrenerCooldown(uuid))).hideFlag().toItemStack().clone();
        }
        return new ItemBuilder(Material.ARROW).setName("&6Przeszycie Bloku Ciosu").setLore(Arrays.asList("", "&7Postep ulepszenia: &c" + this.getPrzeszywka(uuid) + "%&7/&c20%")).hideFlag().toItemStack().clone();
    }

    public ItemStack getSilnyNaludzItem(final UUID uuid) {
        if (rpgcore.getCooldownManager().hasTrenerCooldown(uuid)) {
            return new ItemBuilder(Material.IRON_SWORD).setName("&6Silny Przeciwko Ludziom").setLore(Arrays.asList("", "&7Postep ulepszenia: &c" + this.getSilnynaludzi(uuid) + "%&7/&c20%", "&cNastepny punkt mozesz dodac dopiero za:", "&f" + rpgcore.getCooldownManager().getPlayerTrenerCooldown(uuid))).hideFlag().toItemStack().clone();
        }
        return new ItemBuilder(Material.IRON_SWORD).setName("&6Silny Przeciwko Ludziom").setLore(Arrays.asList("", "&7Postep ulepszenia: &c" + this.getSilnynaludzi(uuid) + "%&7/&c20%")).hideFlag().toItemStack().clone();
    }

    public ItemStack getDefNaLudziItem(final UUID uuid) {
        if (rpgcore.getCooldownManager().hasTrenerCooldown(uuid)) {
            return new ItemBuilder(Material.IRON_CHESTPLATE).setName("&6Odpornosc Przeciwko Ludziom").setLore(Arrays.asList("", "&7Postep ulepszenia: &c" + this.getDefnaludzi(uuid) + "%&7/&c20%", "&cNastepny punkt mozesz dodac dopiero za:", "&f" + rpgcore.getCooldownManager().getPlayerTrenerCooldown(uuid))).hideFlag().toItemStack().clone();
        }
        return new ItemBuilder(Material.IRON_CHESTPLATE).setName("&6Odpornosc Przeciwko Ludziom").setLore(Arrays.asList("", "&7Postep ulepszenia: &c" + this.getDefnaludzi(uuid) + "%&7/&c20%")).hideFlag().toItemStack().clone();
    }

    public ItemStack getKrytItem(final UUID uuid) {
        if (rpgcore.getCooldownManager().hasTrenerCooldown(uuid)) {
            return new ItemBuilder(Material.GOLD_SWORD).setName("&6Cios Krytyczny").setLore(Arrays.asList("", "&7Postep ulepszenia: &c" + this.getKyt(uuid) + "%&7/&c10%", "&cNastepny punkt mozesz dodac dopiero za:", "&f" + rpgcore.getCooldownManager().getPlayerTrenerCooldown(uuid))).hideFlag().toItemStack().clone();
        }
        return new ItemBuilder(Material.GOLD_SWORD).setName("&6Cios Krytyczny").setLore(Arrays.asList("", "&7Postep ulepszenia: &c" + this.getKyt(uuid) + "%&7/&c10%")).hideFlag().toItemStack().clone();
    }

    public int getPoints(final UUID uuid) {
        points.computeIfAbsent(uuid, k -> 0);
        return points.get(uuid);
    }

    public int getSrednidmg(final UUID uuid) {
        srednidmg.computeIfAbsent(uuid, k -> 0);
        return srednidmg.get(uuid);
    }

    public int getSrednidef(final UUID uuid) {
        srednidef.computeIfAbsent(uuid, k -> 0);
        return srednidef.get(uuid);
    }

    public int getHP(final UUID uuid) {
        hp.computeIfAbsent(uuid, k -> 0);
        return hp.get(uuid);
    }

    public int getBlok(final UUID uuid) {
        blok.computeIfAbsent(uuid, k -> 0);
        return blok.get(uuid);
    }

    public int getPrzeszywka(final UUID uuid) {
        przeszywka.computeIfAbsent(uuid, k -> 0);
        return przeszywka.get(uuid);
    }

    public int getSilnynaludzi(final UUID uuid) {
        silnynaludzi.computeIfAbsent(uuid, k -> 0);
        return silnynaludzi.get(uuid);
    }

    public int getDefnaludzi(final UUID uuid) {
        defnaludzi.computeIfAbsent(uuid, k -> 0);
        return defnaludzi.get(uuid);
    }

    public int getKyt(final UUID uuid) {
        kyt.computeIfAbsent(uuid, k -> 0);
        return kyt.get(uuid);
    }

    public void setPoints(final UUID uuid, final int dmg) {
        points.put(uuid, dmg);
    }

    public void setSrednidmg(final UUID uuid, final int dmg) {
        srednidmg.put(uuid, dmg);
    }

    public void setSrednidef(final UUID uuid, final int def) {
        srednidef.put(uuid, def);
    }

    public void setHP(final UUID uuid, final int hp) {
        this.hp.put(uuid, hp);
    }

    public void setBlok(final UUID uuid, final int blok) {
        this.blok.put(uuid, blok);
    }

    public void setPrzeszywka(final UUID uuid, final int przeszywka) {
        this.przeszywka.put(uuid, przeszywka);
    }

    public void setSilnynaludzi(final UUID uuid, final int silnynaludzi) {
        this.silnynaludzi.put(uuid, silnynaludzi);
    }

    public void setDefnaludzi(final UUID uuid, final int defnaludzi) {
        this.defnaludzi.put(uuid, defnaludzi);
    }

    public void setKyt(final UUID uuid, final int kyt) {
        this.kyt.put(uuid, kyt);
    }

    public void updatePoints(final UUID uuid, final int point) {
        points.replace(uuid, this.getPoints(uuid) + point);
    }

    public void updateSredniDmg(final UUID uuid, final int dmg) {
        srednidmg.replace(uuid, this.getSrednidmg(uuid) + dmg);
    }

    public void updateSredniDef(final UUID uuid, final int def) {
        srednidef.replace(uuid, this.getSrednidef(uuid) + def);
    }

    public void updateHP(final UUID uuid, final int hp) {
        this.hp.replace(uuid, this.getHP(uuid) + hp);
    }

    public void updateBlok(final UUID uuid, final int blok) {
        this.blok.replace(uuid, this.getBlok(uuid) + blok);
    }

    public void updatePrzeszywka(final UUID uuid, final int przeszywka) {
        this.przeszywka.replace(uuid, this.getPrzeszywka(uuid) + przeszywka);
    }

    public void updateSilnynaludzi(final UUID uuid, final int silnynaludzi) {
        this.silnynaludzi.replace(uuid, this.getSilnynaludzi(uuid) + silnynaludzi);
    }

    public void updateDefnaludzi(final UUID uuid, final int defnaludzi) {
        this.defnaludzi.replace(uuid, this.getDefnaludzi(uuid) + defnaludzi);
    }

    public void updateKyt(final UUID uuid, final int kyt) {
        this.kyt.replace(uuid, this.getKyt(uuid) + kyt);
    }

    public void reset(final UUID uuid) {
        int pointsToAdd = 0;
        pointsToAdd += this.getPoints(uuid);
        pointsToAdd += this.getSrednidmg(uuid);
        pointsToAdd += this.getSrednidef(uuid);
        pointsToAdd += this.getHP(uuid);
        pointsToAdd += this.getBlok(uuid);
        pointsToAdd += this.getPrzeszywka(uuid);
        pointsToAdd += this.getSilnynaludzi(uuid);
        pointsToAdd += this.getDefnaludzi(uuid);
        pointsToAdd += this.getKyt(uuid);
        this.setPoints(uuid, pointsToAdd);
        this.setSrednidmg(uuid, 0);
        this.setSrednidef(uuid, 0);
        this.setHP(uuid, 0);
        this.setBlok(uuid, 0);
        this.setPrzeszywka(uuid, 0);
        this.setSilnynaludzi(uuid, 0);
        this.setDefnaludzi(uuid, 0);
        this.setKyt(uuid, 0);
    }

    public void fromDocument(final Document document) {
        final UUID uuid = UUID.fromString(document.getString("_id"));
        this.setPoints(uuid, document.getInteger("points"));
        this.setSrednidmg(uuid, document.getInteger("srednidmg"));
        this.setSrednidef(uuid, document.getInteger("srednidef"));
        this.setHP(uuid, document.getInteger("hp"));
        this.setBlok(uuid, document.getInteger("blok"));
        this.setPrzeszywka(uuid, document.getInteger("przeszywka"));
        this.setSilnynaludzi(uuid, document.getInteger("silnynaludzi"));
        this.setDefnaludzi(uuid, document.getInteger("defnaludzi"));
        this.setKyt(uuid, document.getInteger("kyt"));
    }

    public Document toDocument(final UUID uuid) {
        return new Document("_id", uuid.toString())
                .append("points", this.getPoints(uuid))
                .append("srednidmg", this.getSrednidmg(uuid))
                .append("srednidef", this.getSrednidef(uuid))
                .append("hp", this.getHP(uuid))
                .append("blok", this.getBlok(uuid))
                .append("przeszywka", this.getPrzeszywka(uuid))
                .append("silnynaludzi", this.getSilnynaludzi(uuid))
                .append("defnaludzi", this.getDefnaludzi(uuid))
                .append("kyt", this.getKyt(uuid));
    }

    public ImmutableSet<TrenerObject> getTrenerObjects() {
        return ImmutableSet.copyOf(userMap.values());
    }
}
