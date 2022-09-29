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

    public void openTrenerGUI(final Player player) {
        final UUID uuid = player.getUniqueId();
        final TrenerObject object = this.find(uuid);
        final TrenerUser user = object.getTrenerUser();
        final Inventory gui = Bukkit.createInventory(null, 9, Utils.format("&6Trener - Punkty: " + user.getPoints()));

        gui.setItem(0, this.getSredniDmgItem(object));
        gui.setItem(1, this.getSredniDefItem(object));
        gui.setItem(2, this.getHpItem(object));
        gui.setItem(3, this.getBlokItem(object));
        gui.setItem(4, this.getSzczescieItem(object));
        gui.setItem(5, this.getSilnyNaludzItem(object));
        gui.setItem(6, this.getDefNaLudziItem(object));
        gui.setItem(7, this.getKrytItem(object));
        if (user.getSredniDmg() == 20 && user.getSredniDef() == 20 && user.getDodatkoweHp() == 10 && user.getBlokCiosu() == 10 && user.getSzczescie() == 20
                && user.getSilnyNaLudzi() == 20 && user.getDefNaLudzi() == 20 && user.getKryt() == 10) {
            gui.setItem(8, new ItemBuilder(Material.BEACON).setName("&cD&6r&ez&ae&9w&dk&5o &cU&6m&ei&ae&9j&de&5t&cn&6o&es&ac&6i").toItemStack());
        } else {
            gui.setItem(8, new ItemBuilder(Material.WOOL, 1, (short) 14).setName("&cZresetuj Trenera").setLore(Arrays.asList("&7Koszt zresetowania statystyk: &65 000 000&2$")).toItemStack());
        }

        player.openInventory(gui);
    }


    public ItemStack getSredniDmgItem(final TrenerObject object) {
        if (rpgcore.getCooldownManager().hasTrenerCooldown(object.getId())) {
            return new ItemBuilder(Material.DIAMOND_SWORD).setName("&6Srednie Obrazenia").setLore(Arrays.asList("", "&7Postep ulepszenia: &c" + object.getTrenerUser().getSredniDmg() + "%&7/&c20%", "&cNastepny punkt mozesz dodac dopiero za:", "&f" + rpgcore.getCooldownManager().getPlayerTrenerCooldown(object.getId()))).hideFlag().toItemStack().clone();
        }
        return new ItemBuilder(Material.DIAMOND_SWORD).setName("&6Srednie Obrazenia").setLore(Arrays.asList("", "&7Postep ulepszenia: &c" + object.getTrenerUser().getSredniDmg() + "%&7/&c20%")).hideFlag().toItemStack().clone();
    }

    public ItemStack getSredniDefItem(final TrenerObject object) {
        if (rpgcore.getCooldownManager().hasTrenerCooldown(object.getId())) {
            return new ItemBuilder(Material.DIAMOND_CHESTPLATE).setName("&6Srednia Defensywa").setLore(Arrays.asList("", "&7Postep ulepszenia: &c" + object.getTrenerUser().getSredniDef() + "%&7/&c20%", "&cNastepny punkt mozesz dodac dopiero za:", "&f" + rpgcore.getCooldownManager().getPlayerTrenerCooldown(object.getId()))).hideFlag().toItemStack().clone();
        }
        return new ItemBuilder(Material.DIAMOND_CHESTPLATE).setName("&6Srednia Defensywa").setLore(Arrays.asList("", "&7Postep ulepszenia: &c" + object.getTrenerUser().getSredniDef() + "%&7/&c20%")).hideFlag().toItemStack().clone();
    }

    public ItemStack getHpItem(final TrenerObject object) {
        if (rpgcore.getCooldownManager().hasTrenerCooldown(object.getId())) {
            return new ItemBuilder(Material.GOLDEN_APPLE).setName("&6Dodatkowe HP").setLore(Arrays.asList("", "&7Postep ulepszenia: &c" + object.getTrenerUser().getDodatkoweHp() + "&7/&c10", "&cNastepny punkt mozesz dodac dopiero za:", "&f" + rpgcore.getCooldownManager().getPlayerTrenerCooldown(object.getId()))).hideFlag().toItemStack().clone();
        }
        return new ItemBuilder(Material.GOLDEN_APPLE).setName("&6Dodatkowe HP").setLore(Arrays.asList("", "&7Postep ulepszenia: &c" + object.getTrenerUser().getDodatkoweHp() + "&7/&c10")).hideFlag().toItemStack().clone();
    }

    public ItemStack getBlokItem(final TrenerObject object) {
        if (rpgcore.getCooldownManager().hasTrenerCooldown(object.getId())) {
            return new ItemBuilder(Material.CHAINMAIL_CHESTPLATE).setName("&6Blok Ciosu").setLore(Arrays.asList("", "&7Postep ulepszenia: &c" + object.getTrenerUser().getBlokCiosu() + "%&7/&c10%", "&cNastepny punkt mozesz dodac dopiero za:", "&f" + rpgcore.getCooldownManager().getPlayerTrenerCooldown(object.getId()))).hideFlag().toItemStack().clone();
        }
        return new ItemBuilder(Material.CHAINMAIL_CHESTPLATE).setName("&6Blok Ciosu").setLore(Arrays.asList("", "&7Postep ulepszenia: &c" + object.getTrenerUser().getBlokCiosu() + "%&7/&c10%")).hideFlag().toItemStack().clone();
    }

    public ItemStack getSzczescieItem(final TrenerObject object) {
        if (rpgcore.getCooldownManager().hasTrenerCooldown(object.getId())) {
            return new ItemBuilder(Material.GOLD_NUGGET).setName("&6Szczescie").setLore(Arrays.asList("", "&7Postep ulepszenia: &c" + object.getTrenerUser().getSzczescie() + "&7/&c20", "&cNastepny punkt mozesz dodac dopiero za:", "&f" + rpgcore.getCooldownManager().getPlayerTrenerCooldown(object.getId()))).hideFlag().toItemStack().clone();
        }
        return new ItemBuilder(Material.GOLD_NUGGET).setName("&6Szczescie").setLore(Arrays.asList("", "&7Postep ulepszenia: &c" + object.getTrenerUser().getSzczescie() + "&7/&c20")).hideFlag().toItemStack().clone();
    }

    public ItemStack getSilnyNaludzItem(final TrenerObject object) {
        if (rpgcore.getCooldownManager().hasTrenerCooldown(object.getId())) {
            return new ItemBuilder(Material.IRON_SWORD).setName("&6Silny Przeciwko Ludziom").setLore(Arrays.asList("", "&7Postep ulepszenia: &c" + object.getTrenerUser().getSilnyNaLudzi() + "%&7/&c20%", "&cNastepny punkt mozesz dodac dopiero za:", "&f" + rpgcore.getCooldownManager().getPlayerTrenerCooldown(object.getId()))).hideFlag().toItemStack().clone();
        }
        return new ItemBuilder(Material.IRON_SWORD).setName("&6Silny Przeciwko Ludziom").setLore(Arrays.asList("", "&7Postep ulepszenia: &c" + object.getTrenerUser().getSilnyNaLudzi() + "%&7/&c20%")).hideFlag().toItemStack().clone();
    }

    public ItemStack getDefNaLudziItem(final TrenerObject object) {
        if (rpgcore.getCooldownManager().hasTrenerCooldown(object.getId())) {
            return new ItemBuilder(Material.IRON_CHESTPLATE).setName("&6Odpornosc Przeciwko Ludziom").setLore(Arrays.asList("", "&7Postep ulepszenia: &c" + object.getTrenerUser().getDefNaLudzi() + "%&7/&c20%", "&cNastepny punkt mozesz dodac dopiero za:", "&f" + rpgcore.getCooldownManager().getPlayerTrenerCooldown(object.getId()))).hideFlag().toItemStack().clone();
        }
        return new ItemBuilder(Material.IRON_CHESTPLATE).setName("&6Odpornosc Przeciwko Ludziom").setLore(Arrays.asList("", "&7Postep ulepszenia: &c" + object.getTrenerUser().getDefNaLudzi() + "%&7/&c20%")).hideFlag().toItemStack().clone();
    }

    public ItemStack getKrytItem(final TrenerObject object) {
        if (rpgcore.getCooldownManager().hasTrenerCooldown(object.getId())) {
            return new ItemBuilder(Material.GOLD_SWORD).setName("&6Cios Krytyczny").setLore(Arrays.asList("", "&7Postep ulepszenia: &c" + object.getTrenerUser().getKryt() + "%&7/&c10%", "&cNastepny punkt mozesz dodac dopiero za:", "&f" + rpgcore.getCooldownManager().getPlayerTrenerCooldown(object.getId()))).hideFlag().toItemStack().clone();
        }
        return new ItemBuilder(Material.GOLD_SWORD).setName("&6Cios Krytyczny").setLore(Arrays.asList("", "&7Postep ulepszenia: &c" + object.getTrenerUser().getKryt() + "%&7/&c10%")).hideFlag().toItemStack().clone();
    }
    
    public TrenerObject find(final UUID uuid) {
        this.userMap.computeIfAbsent(uuid, k -> new TrenerObject(uuid));
        return this.userMap.get(uuid);
    }
    
    public void add(final TrenerObject trenerObject) {
        this.userMap.put(trenerObject.getId(), trenerObject);
    }

    public ImmutableSet<TrenerObject> getTrenerObjects() {
        return ImmutableSet.copyOf(userMap.values());
    }
}
