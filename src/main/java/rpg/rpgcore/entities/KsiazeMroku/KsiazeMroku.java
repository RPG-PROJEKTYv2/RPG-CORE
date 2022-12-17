package rpg.rpgcore.entities.KsiazeMroku;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.List;

public class KsiazeMroku extends EntityPigZombie {

    public KsiazeMroku(World world) {
        super(world);
        List goalB = (List) Utils.getPrivateField("b", PathfinderGoalSelector.class, goalSelector); goalB.clear();
        List goalC = (List) Utils.getPrivateField("c", PathfinderGoalSelector.class, goalSelector); goalC.clear();
        List targetB = (List) Utils.getPrivateField("b", PathfinderGoalSelector.class, targetSelector); targetB.clear();
        List targetC = (List) Utils.getPrivateField("c", PathfinderGoalSelector.class, targetSelector); targetC.clear();
        this.setEquipment(0, CraftItemStack.asNMSCopy(new ItemBuilder(org.bukkit.Material.DIAMOND_SWORD).addGlowing().toItemStack()));
        this.setEquipment(1, CraftItemStack.asNMSCopy(new ItemBuilder(org.bukkit.Material.IRON_HELMET).addGlowing().toItemStack()));
        this.setEquipment(2, CraftItemStack.asNMSCopy(new ItemBuilder(org.bukkit.Material.IRON_CHESTPLATE).addGlowing().toItemStack()));
        this.setEquipment(3, CraftItemStack.asNMSCopy(new ItemBuilder(org.bukkit.Material.IRON_LEGGINGS).addGlowing().toItemStack()));
        this.setEquipment(4, CraftItemStack.asNMSCopy(new ItemBuilder(org.bukkit.Material.IRON_BOOTS).addGlowing().toItemStack()));
        this.getAttributeInstance(a).setValue(0.0);
        this.getAttributeInstance(GenericAttributes.maxHealth).setValue(2500000);
        this.setHealth(2500000);
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.35);
        this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(1);
    }

}
