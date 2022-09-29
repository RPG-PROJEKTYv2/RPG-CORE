package rpg.rpgcore.pets;

import com.google.common.collect.ImmutableSet;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.*;

public class PetyManager {
    private final RPGCORE rpgcore;
    private final Map<UUID, PetObject> activePet;
    private final Map<UUID, UserPets> allPets;

    public PetyManager(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
        this.activePet = rpgcore.getMongoManager().loadAllActivePets();
        this.allPets = rpgcore.getMongoManager().loadAllUserPets();
    }

    public void changeActivePet(final ItemStack newPet, final @NotNull Player player) {
        final UUID uuid = player.getUniqueId();
        final PetObject petObject = this.findActivePet(uuid);
        final UserPets userPets = this.findUserPets(uuid);

        final ItemStack activePet = petObject.getPet().getItem();

        if (activePet != null && activePet == newPet) {
            petObject.getPet().despawn();
            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataActivePets(petObject.getId(), petObject));
            //TODO zroobic usuwanie statystyk z peta po desawnwowaniu
            player.sendMessage(Utils.format("&cPomyslnie zdespawnowano   " + activePet.getItemMeta().getDisplayName()));
            return;
        }

        //TODO zrobic dodawanie statystyk po respie peta i dokonczyc jego ustawianie


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

        if (pet != null && gui.contains(pet.getItem())) {
            System.out.println("1");
            for (int i = 0; i < gui.getSize(); i++) {
                if (gui.getItem(i) == null || gui.getItem(i).getType().equals(Material.AIR) || gui.getItem(i).getType().equals(Material.STAINED_GLASS_PANE)) continue;
                if (gui.getItem(i).equals(pet.getItem())) {
                    System.out.println("2");
                    gui.setItem(i, new ItemBuilder(pet.getItem()).setLoreCrafting(pet.getItem().getItemMeta().getLore(), Arrays.asList(" ", "&a&lPrzywolany")).toItemStack().clone());
                }
            }
        }

        gui.setItem(40, new ItemBuilder(Material.ARMOR_STAND).setName("&cZamien Na Item").toItemStack());
        player.openInventory(gui);
    }

    public List<ItemStack> sortPety(final ItemStack[] pety) {
        if (pety == null || pety.length == 0) {
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
