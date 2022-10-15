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
import rpg.rpgcore.entities.EntityTypes;
import rpg.rpgcore.pets.enums.PetLevels;
import rpg.rpgcore.pets.enums.PetList;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.PageUtils;
import rpg.rpgcore.utils.Utils;

import java.util.*;

public class PetyManager {
    private final Map<UUID, PetObject> activePet;
    private final Map<UUID, UserPets> allPets;

    public PetyManager(RPGCORE rpgcore) {
        this.activePet = rpgcore.getMongoManager().loadAllActivePets();
        this.allPets = rpgcore.getMongoManager().loadAllUserPets();
    }

    public void openPetyGUI(final @NotNull Player player, final int page) {
        final UUID uuid = player.getUniqueId();
        final UserPets userPets = this.findUserPets(uuid);
        final Pet pet = this.findActivePet(uuid).getPet();
        final Inventory gui = Bukkit.createInventory(player, 54, Utils.format("&c&lMenu Petow - " + page));
        for (int i = 0; i < gui.getSize(); i++) {
            if (!((i > 9 && i < 17) || (i > 18 && i < 26) || (i > 27 && i < 35) || (i > 36 && i < 44))) {
                gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setName(" ").toItemStack());
            }
        }

        final List<ItemStack> finalAllItems = sortPety(userPets.getPety());

        if (PageUtils.isPageValid(finalAllItems, page - 1, 28)) {
            gui.setItem(47, new ItemBuilder(Material.ARROW).setName("&aPoprzednia").toItemStack());
        } else {
            gui.setItem(47, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setName(" ").toItemStack());
        }


        if (PageUtils.isPageValid(finalAllItems, page + 1, 28)) {
            gui.setItem(53, new ItemBuilder(Material.ARROW).setName("&aNastepna").toItemStack());
        } else {
            gui.setItem(53, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setName(" ").toItemStack());
        }
        for (ItemStack is : PageUtils.getPageItems(finalAllItems, page, 28)) {
            gui.setItem(gui.firstEmpty(), is);
        }


        if (pet.getItem() != null) {
            for (int i = 0; i < gui.getSize(); i++) {
                if (gui.getItem(i) == null || gui.getItem(i).getType().equals(Material.AIR) || gui.getItem(i).getType().equals(Material.STAINED_GLASS_PANE))
                    continue;
                if (gui.getItem(i).equals(pet.getItem())) {
                    gui.setItem(i, new ItemBuilder(pet.getItem().clone()).setLoreCrafting(pet.getItem().getItemMeta().getLore(), Arrays.asList(" ", "&a&lPrzywolany")).toItemStack());
                    break;
                }
            }
            gui.setItem(50, addExpToLore(pet.getItem().clone()));
        }

        gui.setItem(49, new ItemBuilder(Material.ARMOR_STAND).setName("&cZamien Na Item").toItemStack());
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

    public double getReqPetExp(final ItemStack is) {
        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(is);
        NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
        return compound.getDouble("ReqPetExp");
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


    public ItemStack increaseAbility(final UUID uuid, final ItemStack is, final double toAdd, final int slot) {
        ItemMeta im = is.getItemMeta();
        List<String> lore = im.getLore();
        if (this.getPetRarity(is).equals("Mityczny")) {
            Utils.removeBonuses(uuid, is.getItemMeta().getLore().get(1));
            Utils.removeBonuses(uuid, is.getItemMeta().getLore().get(2));
            Utils.removeBonuses(uuid, is.getItemMeta().getLore().get(3));
        } else {
            Utils.removeBonuses(uuid, is.getItemMeta().getLore().get(1));
            Utils.removeBonuses(uuid, is.getItemMeta().getLore().get(2));
        }

        final String ability = Utils.getLoreLineColored(is, slot);
        String newAbility = ability.substring(0, ability.lastIndexOf(" "));
        double abilityBonuse = Double.parseDouble(Utils.removeColor(ability.substring(ability.lastIndexOf(" "))).replace("%", "").trim());

        if (ability.contains("%")) {
            lore.set(slot, Utils.format(newAbility + " &c" + String.format("%.3f", (abilityBonuse + toAdd)) + "%"));
        } else {
            lore.set(slot, Utils.format(newAbility + " &c" + String.format("%.3f", (abilityBonuse + toAdd))));
        }

        im.setLore(lore);
        is.setItemMeta(im);
        return is;
    }

    public void increasePetLvl(final Player player, final ItemStack is) {
        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(is);
        NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
        compound.setInt("PetLevel", compound.getInt("PetLevel") + 1);
        compound.setDouble("ReqPetExp", PetLevels.getExp(compound.getInt("PetLevel") + 1, getPetRarity(is)));
        compound.setDouble("PetExp", 0);
        nmsStack.setTag(compound);

        final String petNamePlusRarity = Utils.removeColor(nmsStack.getName().substring(0, nmsStack.getName().indexOf("[") - 1) + " - " + getPetRarity(CraftItemStack.asBukkitCopy(nmsStack)));
        System.out.println(petNamePlusRarity);
        final double toAdd = Double.parseDouble(String.format("%.3f", PetList.getAbilityIncrease(petNamePlusRarity, 1, compound.getInt("PetLevel"))));
        final double toAdd2 = Double.parseDouble(String.format("%.3f", PetList.getAbilityIncrease(petNamePlusRarity, 2, compound.getInt("PetLevel"))));
        //TODO PRZETESTOWAC CZY NIE DODAJE W PIZDU 0 NA KONCU!!!!!!!!!!!!!!!!!!!
        ItemStack newItem = increaseAbility(player.getUniqueId(), CraftItemStack.asBukkitCopy(nmsStack), toAdd, 1);
        newItem = increaseAbility(player.getUniqueId(), newItem, toAdd2, 2);
        final String name = is.getItemMeta().getDisplayName();
        String newName = name.substring(0, name.lastIndexOf(" "));
        final ItemMeta im = newItem.getItemMeta();
        im.setDisplayName(Utils.format(newName + " &6" + compound.getInt("PetLevel") + "&8]"));
        newItem.setItemMeta(im);
        updatePet(player, CraftItemStack.asNMSCopy(newItem));
        player.sendMessage(Utils.format("&aTwoj " + nmsStack.getName() + " &aosiagnal &6" + compound.getInt("PetLevel") + " &apoziom!"));
        EntityTypes.updateName(player.getUniqueId(), newName + " &6" + compound.getInt("PetLevel") + "&8]");
        final PetObject po = this.findActivePet(player.getUniqueId());
        if (this.getPetRarity(newItem).equals("Mityczny")) {
            po.getPet().setValue1(Utils.addBonuses(player.getUniqueId(), newItem.getItemMeta().getLore().get(1)));
            po.getPet().setValue1(Utils.addBonuses(player.getUniqueId(), newItem.getItemMeta().getLore().get(2)));
            po.getPet().setValue1(Utils.addBonuses(player.getUniqueId(), newItem.getItemMeta().getLore().get(3)));
        } else {
            po.getPet().setValue1(Utils.addBonuses(player.getUniqueId(), newItem.getItemMeta().getLore().get(1)));
            po.getPet().setValue1(Utils.addBonuses(player.getUniqueId(), newItem.getItemMeta().getLore().get(2)));
        }
        RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> {
            RPGCORE.getInstance().getMongoManager().saveDataUserPets(player.getUniqueId(), this.findUserPets(player.getUniqueId()));
            RPGCORE.getInstance().getMongoManager().saveDataActivePets(player.getUniqueId(), po);
        });
        //EntityTypes.updatePet(player, newItem);
    }

    public void increasePetExp(final Player player, final ItemStack is, final double expToAdd) {
        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(is);
        NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
        compound.setDouble("PetExp", compound.getDouble("PetExp") + expToAdd);
        compound.setDouble("PetTotalExp", compound.getDouble("PetTotalExp") + expToAdd);
        nmsStack.setTag(compound);
        updatePet(player, nmsStack);
        if (compound.getDouble("PetExp") >= compound.getDouble("ReqPetExp")) {
            increasePetLvl(player, is);
        }
    }

    public void updatePet(final Player player, final net.minecraft.server.v1_8_R3.ItemStack is) {
        this.findUserPets(player.getUniqueId()).getPety().set(getPetIndexFromList(player.getUniqueId()), CraftItemStack.asBukkitCopy(is));
        final Pet pet = this.findActivePet(player.getUniqueId()).getPet();
        pet.setItem(CraftItemStack.asBukkitCopy(is));
        NBTTagCompound compound = (is.hasTag()) ? is.getTag() : new NBTTagCompound();
        pet.setLvl(compound.getInt("PetLevel"));
        pet.setExp(compound.getDouble("PetExp"));
        pet.setName(Utils.removeColor(is.getName()));
        pet.setReqExp(compound.getDouble("ReqPetExp"));
        pet.setTotalExp(compound.getDouble("PetTotalExp"));
    }

    public int getPetIndexFromList(final UUID uuid) {
        final Pet pet = this.findActivePet(uuid).getPet();
        final UserPets userPets = this.findUserPets(uuid);

        for (int i = 0; i < userPets.getPety().size(); i++) {
            if (userPets.getPety().get(i).equals(pet.getItem())) {
                return i;
            }
        }
        return -1;
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
