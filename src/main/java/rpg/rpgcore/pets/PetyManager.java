package rpg.rpgcore.pets;

import com.google.common.collect.ImmutableSet;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.pets.enums.PetLevels;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.*;

public class PetyManager {
    private final Map<UUID, PetObject> activePet;
    private final Map<UUID, UserPets> allPets;
    public PetyManager(RPGCORE rpgcore) {
        this.activePet = rpgcore.getMongoManager().loadAllActivePets();
        this.allPets = rpgcore.getMongoManager().loadAllUserPets();
    }

    public void openPetyGUI(final @NotNull Player player) {
        final UUID uuid = player.getUniqueId();
        final UserPets userPets = this.findUserPets(uuid);
        final Pet pet = this.findActivePet(uuid).getPet();
        final Inventory gui = Bukkit.createInventory(player, 45, Utils.format("&c&lMenu Petow"));
        for (int i = 0; i < gui.getSize(); i++) {
            if (!((i > 9 && i < 17) || (i > 18 && i < 26) || (i > 27 && i < 35))) {
                gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setName(" ").toItemStack());
            }
        }
        for (ItemStack item : sortPety(userPets.getPety())) {
            gui.setItem(gui.firstEmpty(), item);
        }
        if (pet.getItem() != null) {
            System.out.println(pet.getItem().getItemMeta().getDisplayName());
        }

        if (pet.getItem() != null && gui.contains(pet.getItem())) {
            System.out.println("1");
            for (int i = 0; i < gui.getSize(); i++) {
                if (gui.getItem(i) == null || gui.getItem(i).getType().equals(Material.AIR) || gui.getItem(i).getType().equals(Material.STAINED_GLASS_PANE)) continue;
                if (gui.getItem(i).equals(pet.getItem())) {
                    System.out.println("2");
                    gui.setItem(i, new ItemBuilder(pet.getItem().clone()).setLoreCrafting(pet.getItem().getItemMeta().getLore(), Arrays.asList(" ", "&a&lPrzywolany")).toItemStack());
                    gui.setItem(44, addExpToLore(pet.getItem().clone()));
                    System.out.println(pet.getItem().getType().name());
                }
            }
        }

        gui.setItem(40, new ItemBuilder(Material.ARMOR_STAND).setName("&cZamien Na Item").toItemStack());
        player.openInventory(gui);
    }

    public List<ItemStack> sortPety(final List<ItemStack> pety) {
        if (pety == null || pety.isEmpty()) {
            return new ArrayList<>();
        }
        final List<ItemStack> sorted = new ArrayList<>();

        for (ItemStack item : pety) {
            if (item == null || item.getType().equals(Material.AIR) || !item.hasItemMeta() || !item.getItemMeta().hasLore()) {
                continue;
            }
            for (String s : item.getItemMeta().getLore()) {
                if (s.contains("Mityczny")) {
                    sorted.add(item);
                }
            }
        }

        for (ItemStack item : pety) {
            if (item == null || item.getType().equals(Material.AIR) || !item.hasItemMeta() || !item.getItemMeta().hasLore()) {
                continue;
            }
            for (String s : item.getItemMeta().getLore()) {
                if (s.contains("Legendarny")) {
                    sorted.add(item);
                }
            }
        }

        for (ItemStack item : pety) {
            if (item == null || item.getType().equals(Material.AIR) || !item.hasItemMeta() || !item.getItemMeta().hasLore()) {
                continue;
            }
            for (String s : item.getItemMeta().getLore()) {
                if (s.contains("Epicki")) {
                    sorted.add(item);
                }
            }
        }

        for (ItemStack item : pety) {
            if (item == null || item.getType().equals(Material.AIR) || !item.hasItemMeta() || !item.getItemMeta().hasLore()) {
                continue;
            }
            for (String s : item.getItemMeta().getLore()) {
                if (s.contains("Rzadki")) {
                    sorted.add(item);
                }
            }
        }

        for (ItemStack item : pety) {
            if (item == null || item.getType().equals(Material.AIR) || !item.hasItemMeta() || !item.getItemMeta().hasLore()) {
                continue;
            }
            for (String s : item.getItemMeta().getLore()) {
                if (s.contains("Zwykly")) {
                    sorted.add(item);
                }
            }
        }


        return sorted;
    }

    public int getPetLvl(final ItemStack is) {
        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(is);
        NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
        return compound.getInt("PetLevel");
    }

    public double getPetExp(final ItemStack is) {
        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(is);
        NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
        return compound.getDouble("PetExp");
    }

    public double getPetReqExp(final ItemStack is) {
        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(is);
        NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
        return compound.getDouble("PetReqExp");
    }

    public double getPetTotalExp(final ItemStack is) {
        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(is);
        NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
        return compound.getDouble("PetTotalExp");
    }

    public String getPetRarity(final ItemStack is) {
        for (String s : is.getItemMeta().getLore()) {
            if (s.contains(" Zwierzak")) {
                return Utils.removeColor(s).replace("Zwierzak", "").replaceAll(" ", "");
            }
        }
        return "";
    }


    public ItemStack addExpToLore(final ItemStack is) {
        final ItemMeta im = is.getItemMeta();
        final List<String> lore = im.getLore();
        net.minecraft.server.v1_8_R3.ItemStack nmsStack = org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack.asNMSCopy(is);
        lore.add(Utils.format("&8Exp: &6" + nmsStack.getTag().getDouble("PetExp") + "&8/&6" + nmsStack.getTag().getDouble("ReqPetExp")));
        im.setLore(lore);
        is.setItemMeta(im);
        return is;
    }


    public ItemStack increaseAbility(final ItemStack is, final double toAdd) {
        final String ability = Utils.getLoreLineColored(is, 1);
        String newAbility = ability.substring(0, ability.lastIndexOf(" ") -1);
        double abilityBonuse = Double.parseDouble(Utils.removeColor(ability.substring(ability.lastIndexOf(" "))).replace("%", "").trim());
        ItemMeta im = is.getItemMeta();
        List<String> lore = im.getLore();
        lore.set(1, Utils.format(newAbility + " &c" + (abilityBonuse + toAdd) + "%"));
        im.setLore(lore);
        is.setItemMeta(im);
        return is;
    }

    public ItemStack increasePetLvl(final ItemStack is) {
        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(is);
        NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
        compound.setInt("PetLevel", compound.getInt("PetLevel") + 1);
        compound.setDouble("PetReqExp", PetLevels.getExp(compound.getInt("PetLevel") + 1, getPetRarity(is)));
        compound.setDouble("PetExp", 0);
        nmsStack.setTag(compound);
        final ItemStack newItem = CraftItemStack.asBukkitCopy(nmsStack);
        final String name = is.getItemMeta().getDisplayName();
        String newName = name.substring(0, name.lastIndexOf(" ") -1);
        final ItemMeta im = newItem.getItemMeta();
        im.setDisplayName(Utils.format(newName + " " + compound.getInt("PetLevel") + "&8]"));
        newItem.setItemMeta(im);
        return newItem;
    }

    public ItemStack increasePetExp(final ItemStack is, final double expToAdd) {
        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(is);
        NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
        compound.setDouble("PetExp", compound.getDouble("PetExp") + expToAdd);
        compound.setDouble("PetTotalExp", compound.getDouble("PetTotalExp") + expToAdd);
        nmsStack.setTag(compound);
        return CraftItemStack.asBukkitCopy(nmsStack);
    }




    public PetObject findActivePet(final UUID uuid) {
        this.activePet.computeIfAbsent(uuid, k -> new PetObject(uuid));
        return this.activePet.get(uuid);
    }

    public UserPets findUserPets(final UUID uuid) {
        this.allPets.computeIfAbsent(uuid, k -> new UserPets(uuid));
        return this.allPets.get(uuid);
    }

    public void addToActivePet(final PetObject petObject) {
        activePet.put(petObject.getId(), petObject);
    }

    public void addToUserPets(final UserPets userPets) {
        allPets.put(userPets.getUuid(), userPets);
    }

    public ImmutableSet<PetObject> getAllActivePets() {
        return ImmutableSet.copyOf(activePet.values());
    }

    public ImmutableSet<UserPets> getAllUserPets() {
        return ImmutableSet.copyOf(allPets.values());
    }


}
