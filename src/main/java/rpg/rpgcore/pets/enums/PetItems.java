package rpg.rpgcore.pets.enums;

import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagDouble;
import net.minecraft.server.v1_8_R3.NBTTagInt;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.globalitems.GlobalItem;

import java.util.Arrays;

public enum PetItems {
    D1("Duszek-Zwykly", addLevelsAndExpToPet(new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setSkullOwnerByURL("d077de35-2535-4223-9e27-630c44f77a4e", "skin465e53eb",
            "eyJ0aW1lc3RhbXAiOjE1MDgwMDUxODcxNjAsInByb2ZpbGVJZCI6IjNkOWNmOTZiN2MyNzRiZWVhZDFiOWQ0NTM3NTRjYjc2IiwicHJvZmlsZU5hbWUiOiJOaWtha2EiLCJzaWduYXR1cmVSZXF1" +
                    "aXJlZCI6dHJ1ZSwidGV4dHVyZXMiOnsiU0tJTiI6eyJ1cmwiOiJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzc5Yjc2ODUwZDczZjE4YmVmMjY2ZTE2YTFiNjQ5ODcyNzYzZj" +
                    "JlYzlkMWY4Zjg4ZTZhN2U5NmIzNDMyYmFiZiJ9fX0=", "pU1Pe0fw2/eW2ovUxNa0o5H2zwCzupv2N6RwrMvwYOzXMbH5cHoZgtokHiNPGa2G54izIQc5p/MzKijlzpPYbJilSnM8RuyNju959xhTQQmDFatdVuk+c4N4VBPul/XPdA9hAHuYBhpCAtSDKza" +
                    "oNnt/eACjQm0EnO2XSPjlhJmG+JZJV9/8rEFX/gSGhpEeelLpEY2TUk6ss3T/wJ7zbStUEpEV7lEZOEOruOCfgn/2zBxvjCnEpXepNCgB4CUMY5Z6f9NUfJGEezWeoeO4RyaS1xms6eHzT/abBHdCj" +
                    "ZFuUiUokSiD1Csl906s6h8i7AA768P+hG/dG8stk7Y98a4bW/faD9wSC+pAlKZ5wJtAnE77Q1sWpDFw9tv7E0RUJi5N8GfOozWDAjWDRoFcgavAf4TlAvg4FfR5Zr0yV4jUy7DrxKlseNFWJor8XIN0" +
                    "JwysCPWyurekORAjmPrs+DhSuak3grp69II8Rhnbovz+sQKbvyfM9qVZObsz/qUOG8yq9jPHKFpomwJvdfKlefjy58ivIgMR3vnogIdp4QpBT06pJVnyejtAYVTbiVRrf5eLhKQ5u7eR/VnB9dxv6qz1" +
                    "4D0E8Q1rrIoTZPmEDD9H/EXV7kJ9n7QPdC01rlvBi69Rfp7GkjIAZfiBFkj2jPm7wXNxpRcsk4HWEcRSSVo=").setName("&aDuszek &8[&7Lvl. &61&8]").setLore(Arrays.asList(
            "&8Typ: Walczacy",
            "&7Szansa Na Spowolnienie: &c0%",
            "&7Zmniejszone Obrazenia: &c0%",
            "",
            "&7&lZwykly Zwierzak",
            "&8Exp: &60&8/&6" + PetLevels.getExp(2, "Zwykly")
    )).toItemStack(), "Zwykly")),
    D2("Duszek-Rzadki", addLevelsAndExpToPet(new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setSkullOwnerByURL("d077de35-2535-4223-9e27-630c44f77a4e", "skin465e53eb",
            "eyJ0aW1lc3RhbXAiOjE1MDgwMDUxODcxNjAsInByb2ZpbGVJZCI6IjNkOWNmOTZiN2MyNzRiZWVhZDFiOWQ0NTM3NTRjYjc2IiwicHJvZmlsZU5hbWUiOiJOaWtha2EiLCJzaWduYXR1cmVSZXF1" +
                    "aXJlZCI6dHJ1ZSwidGV4dHVyZXMiOnsiU0tJTiI6eyJ1cmwiOiJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzc5Yjc2ODUwZDczZjE4YmVmMjY2ZTE2YTFiNjQ5ODcyNzYzZj" +
                    "JlYzlkMWY4Zjg4ZTZhN2U5NmIzNDMyYmFiZiJ9fX0=", "pU1Pe0fw2/eW2ovUxNa0o5H2zwCzupv2N6RwrMvwYOzXMbH5cHoZgtokHiNPGa2G54izIQc5p/MzKijlzpPYbJilSnM8RuyNju959xhTQQmDFatdVuk+c4N4VBPul/XPdA9hAHuYBhpCAtSDKza" +
                    "oNnt/eACjQm0EnO2XSPjlhJmG+JZJV9/8rEFX/gSGhpEeelLpEY2TUk6ss3T/wJ7zbStUEpEV7lEZOEOruOCfgn/2zBxvjCnEpXepNCgB4CUMY5Z6f9NUfJGEezWeoeO4RyaS1xms6eHzT/abBHdCj" +
                    "ZFuUiUokSiD1Csl906s6h8i7AA768P+hG/dG8stk7Y98a4bW/faD9wSC+pAlKZ5wJtAnE77Q1sWpDFw9tv7E0RUJi5N8GfOozWDAjWDRoFcgavAf4TlAvg4FfR5Zr0yV4jUy7DrxKlseNFWJor8XIN0" +
                    "JwysCPWyurekORAjmPrs+DhSuak3grp69II8Rhnbovz+sQKbvyfM9qVZObsz/qUOG8yq9jPHKFpomwJvdfKlefjy58ivIgMR3vnogIdp4QpBT06pJVnyejtAYVTbiVRrf5eLhKQ5u7eR/VnB9dxv6qz1" +
                    "4D0E8Q1rrIoTZPmEDD9H/EXV7kJ9n7QPdC01rlvBi69Rfp7GkjIAZfiBFkj2jPm7wXNxpRcsk4HWEcRSSVo=").setName("&1Duszek &8[&7Lvl. &61&8]").setLore(Arrays.asList(
            "&8Typ: Walczacy",
            "&7Szansa Na Spowolnienie: &c0%",
            "&7Zmniejszone Obrazenia: &c0%",
            "",
            "&1&lRzadki Zwierzak",
            "&8Exp: &60&8/&6" + PetLevels.getExp(2, "Rzadki")
    )).addGlowing().toItemStack(), "Rzadki")),
    D3("Duszek-Epicki", addLevelsAndExpToPet(new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setSkullOwnerByURL("d077de35-2535-4223-9e27-630c44f77a4e", "skin465e53eb",
            "eyJ0aW1lc3RhbXAiOjE1MDgwMDUxODcxNjAsInByb2ZpbGVJZCI6IjNkOWNmOTZiN2MyNzRiZWVhZDFiOWQ0NTM3NTRjYjc2IiwicHJvZmlsZU5hbWUiOiJOaWtha2EiLCJzaWduYXR1cmVSZXF1" +
                    "aXJlZCI6dHJ1ZSwidGV4dHVyZXMiOnsiU0tJTiI6eyJ1cmwiOiJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzc5Yjc2ODUwZDczZjE4YmVmMjY2ZTE2YTFiNjQ5ODcyNzYzZj" +
                    "JlYzlkMWY4Zjg4ZTZhN2U5NmIzNDMyYmFiZiJ9fX0=", "pU1Pe0fw2/eW2ovUxNa0o5H2zwCzupv2N6RwrMvwYOzXMbH5cHoZgtokHiNPGa2G54izIQc5p/MzKijlzpPYbJilSnM8RuyNju959xhTQQmDFatdVuk+c4N4VBPul/XPdA9hAHuYBhpCAtSDKza" +
                    "oNnt/eACjQm0EnO2XSPjlhJmG+JZJV9/8rEFX/gSGhpEeelLpEY2TUk6ss3T/wJ7zbStUEpEV7lEZOEOruOCfgn/2zBxvjCnEpXepNCgB4CUMY5Z6f9NUfJGEezWeoeO4RyaS1xms6eHzT/abBHdCj" +
                    "ZFuUiUokSiD1Csl906s6h8i7AA768P+hG/dG8stk7Y98a4bW/faD9wSC+pAlKZ5wJtAnE77Q1sWpDFw9tv7E0RUJi5N8GfOozWDAjWDRoFcgavAf4TlAvg4FfR5Zr0yV4jUy7DrxKlseNFWJor8XIN0" +
                    "JwysCPWyurekORAjmPrs+DhSuak3grp69II8Rhnbovz+sQKbvyfM9qVZObsz/qUOG8yq9jPHKFpomwJvdfKlefjy58ivIgMR3vnogIdp4QpBT06pJVnyejtAYVTbiVRrf5eLhKQ5u7eR/VnB9dxv6qz1" +
                    "4D0E8Q1rrIoTZPmEDD9H/EXV7kJ9n7QPdC01rlvBi69Rfp7GkjIAZfiBFkj2jPm7wXNxpRcsk4HWEcRSSVo=").setName("&5Duszek &8[&7Lvl. &61&8]").setLore(Arrays.asList(
            "&8Typ: Walczacy",
            "&7Szansa Na Spowolnienie: &c0%",
            "&7Zmniejszone Obrazenia: &c0%",
            "",
            "&5&lEpicki Zwierzak",
            "&8Exp: &60&8/&6" + PetLevels.getExp(2, "Epicki")
    )).toItemStack(), "Epicki")),
    D4("Duszek-Legendarny", addLevelsAndExpToPet(new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setSkullOwnerByURL("d077de35-2535-4223-9e27-630c44f77a4e", "skin465e53eb",
            "eyJ0aW1lc3RhbXAiOjE1MDgwMDUxODcxNjAsInByb2ZpbGVJZCI6IjNkOWNmOTZiN2MyNzRiZWVhZDFiOWQ0NTM3NTRjYjc2IiwicHJvZmlsZU5hbWUiOiJOaWtha2EiLCJzaWduYXR1cmVSZXF1" +
                    "aXJlZCI6dHJ1ZSwidGV4dHVyZXMiOnsiU0tJTiI6eyJ1cmwiOiJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzc5Yjc2ODUwZDczZjE4YmVmMjY2ZTE2YTFiNjQ5ODcyNzYzZj" +
                    "JlYzlkMWY4Zjg4ZTZhN2U5NmIzNDMyYmFiZiJ9fX0=", "pU1Pe0fw2/eW2ovUxNa0o5H2zwCzupv2N6RwrMvwYOzXMbH5cHoZgtokHiNPGa2G54izIQc5p/MzKijlzpPYbJilSnM8RuyNju959xhTQQmDFatdVuk+c4N4VBPul/XPdA9hAHuYBhpCAtSDKza" +
                    "oNnt/eACjQm0EnO2XSPjlhJmG+JZJV9/8rEFX/gSGhpEeelLpEY2TUk6ss3T/wJ7zbStUEpEV7lEZOEOruOCfgn/2zBxvjCnEpXepNCgB4CUMY5Z6f9NUfJGEezWeoeO4RyaS1xms6eHzT/abBHdCj" +
                    "ZFuUiUokSiD1Csl906s6h8i7AA768P+hG/dG8stk7Y98a4bW/faD9wSC+pAlKZ5wJtAnE77Q1sWpDFw9tv7E0RUJi5N8GfOozWDAjWDRoFcgavAf4TlAvg4FfR5Zr0yV4jUy7DrxKlseNFWJor8XIN0" +
                    "JwysCPWyurekORAjmPrs+DhSuak3grp69II8Rhnbovz+sQKbvyfM9qVZObsz/qUOG8yq9jPHKFpomwJvdfKlefjy58ivIgMR3vnogIdp4QpBT06pJVnyejtAYVTbiVRrf5eLhKQ5u7eR/VnB9dxv6qz1" +
                    "4D0E8Q1rrIoTZPmEDD9H/EXV7kJ9n7QPdC01rlvBi69Rfp7GkjIAZfiBFkj2jPm7wXNxpRcsk4HWEcRSSVo=").setName("&6Duszek &8[&7Lvl. &61&8]").setLore(Arrays.asList(
            "&8Typ: Walczacy",
            "&7Szansa Na Spowolnienie: &c0%",
            "&7Zmniejszone Obrazenia: &c0%",
            "",
            "&6&lLegendarny Zwierzak",
            "&8Exp: &60&8/&6" + PetLevels.getExp(2, "Legendarny")
    )).toItemStack(), "Legendarny"));

    private final String name;
    private final ItemStack item;

    PetItems(String name, ItemStack item) {
        this.name = name;
        this.item = item;
    }

    public String getName() {
        return name;
    }

    public ItemStack getItemStack() {
        return item;
    }

    public static PetItems getByName(String name) {
        for (PetItems petItem : PetItems.values()) {
            if (petItem.getName().equalsIgnoreCase(name)) {
                return petItem;
            }
        }
        return null;
    }

    public static ItemStack getItem(String string, int amount) {
        ItemStack itemStack = GlobalItem.getByName(string).getItemStack();
        itemStack.setAmount(amount);
        return itemStack;
    }

    private static ItemStack addLevelsAndExpToPet(final ItemStack is, final String rarity) {
        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(is);
        NBTTagCompound tag = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
        tag.set("PetLevel", new NBTTagInt(1));
        tag.set("PetExp", new NBTTagDouble(0.0));
        tag.set("ReqPetExp", new NBTTagDouble(PetLevels.getExp(2, rarity)));
        tag.set("TotalPetExp", new NBTTagDouble(0.0));

        nmsStack.setTag(tag);

        return CraftItemStack.asBukkitCopy(nmsStack);
    }
}
