package rpg.rpgcore.pets;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

@Getter
@Setter
public class Pet {
    private ItemStack item;
    private String name;
    private int lvl;
    private double exp, reqExp, totalExp;
    private String rarity;
    private double value1, value2, value3, value4;
    private String ultimateAbility;

    public Pet(ItemStack item, String name, int lvl, double exp, double reqExp, double totalExp, String rarity, double value1, double value2, double value3, double value4, String ultimateAbility) {
        this.item = item;
        this.name = name;
        this.lvl = lvl;
        this.exp = exp;
        this.reqExp = reqExp;
        this.totalExp = totalExp;
        this.rarity = rarity;
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
        this.value4 = value4;
        this.ultimateAbility = ultimateAbility;
    }

    public void despawn(final ItemStack is, final UUID uuid) {
        this.item = null;
        this.name = "";
        this.lvl = 0;
        this.exp = 0.0;
        this.reqExp = 0.0;
        this.totalExp = 0.0;
        if (this.rarity.equals("Mityczny")) {
            Utils.removeBonuses(uuid, is.getItemMeta().getLore().get(1));
            Utils.removeBonuses(uuid, is.getItemMeta().getLore().get(2));
            Utils.removeBonuses(uuid, is.getItemMeta().getLore().get(3));
        } else {
            Utils.removeBonuses(uuid, is.getItemMeta().getLore().get(1));
            Utils.removeBonuses(uuid, is.getItemMeta().getLore().get(2));
        }
        this.rarity = "";
        this.ultimateAbility = "";
    }

    public void spawn(final ItemStack is, final UUID uuid) {
        this.item = is;
        System.out.println(is.getItemMeta().getDisplayName());
        this.name = Utils.removeColor(is.getItemMeta().getDisplayName());
        this.lvl = RPGCORE.getInstance().getPetyManager().getPetLvl(is);
        this.exp = RPGCORE.getInstance().getPetyManager().getPetExp(is);
        this.reqExp = RPGCORE.getInstance().getPetyManager().getReqPetExp(is);
        this.totalExp = RPGCORE.getInstance().getPetyManager().getPetTotalExp(is);
        this.rarity = RPGCORE.getInstance().getPetyManager().getPetRarity(is);
        if (this.rarity.equals("Mityczny")) {
            this.value1 = Utils.addBonuses(uuid, item.getItemMeta().getLore().get(1));
            this.value2 = Utils.addBonuses(uuid, item.getItemMeta().getLore().get(2));
            this.value3 = Utils.addBonuses(uuid, item.getItemMeta().getLore().get(3));
        } else {
            this.value1 = Utils.addBonuses(uuid, item.getItemMeta().getLore().get(1));
            this.value2 = Utils.addBonuses(uuid, item.getItemMeta().getLore().get(2));
        }
    }
}
