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
                    "4D0E8Q1rrIoTZPmEDD9H/EXV7kJ9n7QPdC01rlvBi69Rfp7GkjIAZfiBFkj2jPm7wXNxpRcsk4HWEcRSSVo=").setName("&fDuszek &8[&7Lvl. &61&8]").setLore(Arrays.asList(
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
    )).toItemStack(), "Rzadki")),
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
    )).toItemStack(), "Legendarny")),
    ZR1("Zlota-Rybka-Zwykly", addLevelsAndExpToPet(new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setSkullOwnerByURL("1e16214c-ad0e-451c-ac7a-6446333309bf", "skin863ae842",
            "ewogICJ0aW1lc3RhbXAiIDogMTY2NDcwNzA2ODg4MiwKICAicHJvZmlsZUlkIiA6ICJmMTkyZGU3MDUzMTQ0ODcxOTAwMjQ1MmIzZWE3MzA3NCIsCiAgInByb2ZpbGVOYW1lIiA6ICJOZVhvU2V0IiwK" +
                    "ICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL" +
                    "2VhM2Y3ZTNmYTVjNTVlM2FiMjk5MGE3NTEwNjk1OGI2N2QzYzRkYWMzYzhhNjU3MDI1NDM3YmI5NmNlMGYxOSIKICAgIH0KICB9Cn0=", "JXMN7r+QUPCRpnw9W9feuCHB3lSw6wcM/lkhlR2" +
                    "IEHZ9S9B21oN0uj2aAHCCLzgAR8AOGByOO8qsG3Ihstfnz2dBbDSVrd68H3P1iPHVv5FX6e1xbDEDl95DskLpeEo0U0WqMiebAD9sFxZ9s7ZQXhUVGVvllar96Tk+4bw//483ty12x67qlZZWYTY1lxWXwZYuE" +
                    "vfyPYD2lfiYrCezX2bKlHG34qPdZ9G55OQaItGWI9CUyiVin2i9UN3ohR95XEFDfnsxR1//tIZH+13N82MXQwn3qc614mRz0Ina5m+5zXS8/Kmnp6+IDdJION8ChFRjC5gW+2yt2+Ss3dcAYo/ICVzGnGIIzcS" +
                    "hrhwrkGdWYDOwzbj4GjkIdK+FGjj2AzpzCNei5FciuQh6FGx8JnnjZY749Bql2+AFtpxIAAfrvIMP2Fx2t3Uy7rFFJyP7BNochMgrDDoE1RkMsrnmF9iQD4/sejxt+0OUVTRMoepw4F4mSvdleehCvkqg0GleM" +
                    "WnKb2tmzwIda0STV6ENiJdiaiN4oTWx3ujDurWjojOyJH1MLiDny4aj9iYf/b4PCovz/MdQGk48jsgitlpuCAG0omW8jceVUwpeo5lLWtijEn6uioa0TLbLcg5fIvtzyE2aLMOK1QD2a4Yo90o55i6OtW41JBz" +
                    "28lvwXRon6fQ=").setName("&fZlota Rybka &8[&7Lvl. &61&8]").setLore(Arrays.asList(
            "&8Typ: Magiczny",
            "&7Szczescie: &c0%",
            "&7Dodatkowy EXP: &c0%",
            "",
            "&7&lZwykly Zwierzak",
            "&8Exp: &60&8/&6" + PetLevels.getExp(2, "Zwykly")
    )).toItemStack(), "Zwykly")),
    ZR2("Zlota-Rybka-Rzadki", addLevelsAndExpToPet(new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setSkullOwnerByURL("1e16214c-ad0e-451c-ac7a-6446333309bf", "skin863ae842",
            "ewogICJ0aW1lc3RhbXAiIDogMTY2NDcwNzA2ODg4MiwKICAicHJvZmlsZUlkIiA6ICJmMTkyZGU3MDUzMTQ0ODcxOTAwMjQ1MmIzZWE3MzA3NCIsCiAgInByb2ZpbGVOYW1lIiA6ICJOZVhvU2V0IiwK" +
                    "ICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL" +
                    "2VhM2Y3ZTNmYTVjNTVlM2FiMjk5MGE3NTEwNjk1OGI2N2QzYzRkYWMzYzhhNjU3MDI1NDM3YmI5NmNlMGYxOSIKICAgIH0KICB9Cn0=", "JXMN7r+QUPCRpnw9W9feuCHB3lSw6wcM/lkhlR2" +
                    "IEHZ9S9B21oN0uj2aAHCCLzgAR8AOGByOO8qsG3Ihstfnz2dBbDSVrd68H3P1iPHVv5FX6e1xbDEDl95DskLpeEo0U0WqMiebAD9sFxZ9s7ZQXhUVGVvllar96Tk+4bw//483ty12x67qlZZWYTY1lxWXwZYuE" +
                    "vfyPYD2lfiYrCezX2bKlHG34qPdZ9G55OQaItGWI9CUyiVin2i9UN3ohR95XEFDfnsxR1//tIZH+13N82MXQwn3qc614mRz0Ina5m+5zXS8/Kmnp6+IDdJION8ChFRjC5gW+2yt2+Ss3dcAYo/ICVzGnGIIzcS" +
                    "hrhwrkGdWYDOwzbj4GjkIdK+FGjj2AzpzCNei5FciuQh6FGx8JnnjZY749Bql2+AFtpxIAAfrvIMP2Fx2t3Uy7rFFJyP7BNochMgrDDoE1RkMsrnmF9iQD4/sejxt+0OUVTRMoepw4F4mSvdleehCvkqg0GleM" +
                    "WnKb2tmzwIda0STV6ENiJdiaiN4oTWx3ujDurWjojOyJH1MLiDny4aj9iYf/b4PCovz/MdQGk48jsgitlpuCAG0omW8jceVUwpeo5lLWtijEn6uioa0TLbLcg5fIvtzyE2aLMOK1QD2a4Yo90o55i6OtW41JBz" +
                    "28lvwXRon6fQ=").setName("&1Zlota Rybka &8[&7Lvl. &61&8]").setLore(Arrays.asList(
            "&8Typ: Magiczny",
            "&7Szczescie: &c0%",
            "&7Dodatkowy EXP: &c0%",
            "",
            "&1&lRzadki Zwierzak",
            "&8Exp: &60&8/&6" + PetLevels.getExp(2, "Rzadki")
    )).toItemStack(), "Rzadki")),
    ZR3("Zlota-Rybka-Epicki", addLevelsAndExpToPet(new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setSkullOwnerByURL("1e16214c-ad0e-451c-ac7a-6446333309bf", "skin863ae842",
            "ewogICJ0aW1lc3RhbXAiIDogMTY2NDcwNzA2ODg4MiwKICAicHJvZmlsZUlkIiA6ICJmMTkyZGU3MDUzMTQ0ODcxOTAwMjQ1MmIzZWE3MzA3NCIsCiAgInByb2ZpbGVOYW1lIiA6ICJOZVhvU2V0IiwK" +
                    "ICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL" +
                    "2VhM2Y3ZTNmYTVjNTVlM2FiMjk5MGE3NTEwNjk1OGI2N2QzYzRkYWMzYzhhNjU3MDI1NDM3YmI5NmNlMGYxOSIKICAgIH0KICB9Cn0=", "JXMN7r+QUPCRpnw9W9feuCHB3lSw6wcM/lkhlR2" +
                    "IEHZ9S9B21oN0uj2aAHCCLzgAR8AOGByOO8qsG3Ihstfnz2dBbDSVrd68H3P1iPHVv5FX6e1xbDEDl95DskLpeEo0U0WqMiebAD9sFxZ9s7ZQXhUVGVvllar96Tk+4bw//483ty12x67qlZZWYTY1lxWXwZYuE" +
                    "vfyPYD2lfiYrCezX2bKlHG34qPdZ9G55OQaItGWI9CUyiVin2i9UN3ohR95XEFDfnsxR1//tIZH+13N82MXQwn3qc614mRz0Ina5m+5zXS8/Kmnp6+IDdJION8ChFRjC5gW+2yt2+Ss3dcAYo/ICVzGnGIIzcS" +
                    "hrhwrkGdWYDOwzbj4GjkIdK+FGjj2AzpzCNei5FciuQh6FGx8JnnjZY749Bql2+AFtpxIAAfrvIMP2Fx2t3Uy7rFFJyP7BNochMgrDDoE1RkMsrnmF9iQD4/sejxt+0OUVTRMoepw4F4mSvdleehCvkqg0GleM" +
                    "WnKb2tmzwIda0STV6ENiJdiaiN4oTWx3ujDurWjojOyJH1MLiDny4aj9iYf/b4PCovz/MdQGk48jsgitlpuCAG0omW8jceVUwpeo5lLWtijEn6uioa0TLbLcg5fIvtzyE2aLMOK1QD2a4Yo90o55i6OtW41JBz" +
                    "28lvwXRon6fQ=").setName("&5Zlota Rybka &8[&7Lvl. &61&8]").setLore(Arrays.asList(
            "&8Typ: Magiczny",
            "&7Szczescie: &c0%",
            "&7Dodatkowy EXP: &c0%",
            "",
            "&5&lEpicki Zwierzak",
            "&8Exp: &60&8/&6" + PetLevels.getExp(2, "Epicki")
    )).toItemStack(), "Epicki")),
    ZR4("Zlota-Rybka-Legendarny", addLevelsAndExpToPet(new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setSkullOwnerByURL("1e16214c-ad0e-451c-ac7a-6446333309bf", "skin863ae842",
            "ewogICJ0aW1lc3RhbXAiIDogMTY2NDcwNzA2ODg4MiwKICAicHJvZmlsZUlkIiA6ICJmMTkyZGU3MDUzMTQ0ODcxOTAwMjQ1MmIzZWE3MzA3NCIsCiAgInByb2ZpbGVOYW1lIiA6ICJOZVhvU2V0IiwK" +
                    "ICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL" +
                    "2VhM2Y3ZTNmYTVjNTVlM2FiMjk5MGE3NTEwNjk1OGI2N2QzYzRkYWMzYzhhNjU3MDI1NDM3YmI5NmNlMGYxOSIKICAgIH0KICB9Cn0=", "JXMN7r+QUPCRpnw9W9feuCHB3lSw6wcM/lkhlR2" +
                    "IEHZ9S9B21oN0uj2aAHCCLzgAR8AOGByOO8qsG3Ihstfnz2dBbDSVrd68H3P1iPHVv5FX6e1xbDEDl95DskLpeEo0U0WqMiebAD9sFxZ9s7ZQXhUVGVvllar96Tk+4bw//483ty12x67qlZZWYTY1lxWXwZYuE" +
                    "vfyPYD2lfiYrCezX2bKlHG34qPdZ9G55OQaItGWI9CUyiVin2i9UN3ohR95XEFDfnsxR1//tIZH+13N82MXQwn3qc614mRz0Ina5m+5zXS8/Kmnp6+IDdJION8ChFRjC5gW+2yt2+Ss3dcAYo/ICVzGnGIIzcS" +
                    "hrhwrkGdWYDOwzbj4GjkIdK+FGjj2AzpzCNei5FciuQh6FGx8JnnjZY749Bql2+AFtpxIAAfrvIMP2Fx2t3Uy7rFFJyP7BNochMgrDDoE1RkMsrnmF9iQD4/sejxt+0OUVTRMoepw4F4mSvdleehCvkqg0GleM" +
                    "WnKb2tmzwIda0STV6ENiJdiaiN4oTWx3ujDurWjojOyJH1MLiDny4aj9iYf/b4PCovz/MdQGk48jsgitlpuCAG0omW8jceVUwpeo5lLWtijEn6uioa0TLbLcg5fIvtzyE2aLMOK1QD2a4Yo90o55i6OtW41JBz" +
                    "28lvwXRon6fQ=").setName("&6Zlota Rybka &8[&7Lvl. &61&8]").setLore(Arrays.asList(
            "&8Typ: Magiczny",
            "&7Szczescie: &c0%",
            "&7Dodatkowy EXP: &c0%",
            "",
            "&6&lLegendarny Zwierzak",
            "&8Exp: &60&8/&6" + PetLevels.getExp(2, "Legendarny")
    )).toItemStack(), "Legendarny")),
    PA1("Pancernik-Zwykly", addLevelsAndExpToPet(new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setSkullOwnerByURL("761c2bf2-74fc-4b06-af38-acafb59a9645", "skin550e24df",
                    "ewogICJ0aW1lc3RhbXAiIDogMTY2NDcwOTQ4MzA0MSwKICAicHJvZmlsZUlkIiA6ICIzOTdlMmY5OTAyNmI0NjI1OTcyNTM1OTNjODgyZjRmMyIsCiAgInByb2ZpbGVOYW1lIiA6ICJ4WnlkdWVMeCIsCiAgInNpZ" +
                            "25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS8xMDAwM2ZjOWI0Y2" +
                            "NjMjMyMWVhN2M2ZmVjNjRjYjE5MTQ5ODc1ZDZiYWY2OTJkODgyOGMwNGNmNDU0NjE1YTdjIgogICAgfQogIH0KfQ==", "JkMuprorRPasHaK1J2Q8ePQdMibQyp/HmNtz2pYGMS/h56Y2R4Hl1ySWa5c" +
                            "tSFmtiHw+CvlUiTyGowSEIPnuE4qhHB0xqMrBZ5w0ZHNn3DlM0R+26UyDN5lApaTdSmcmmb7Dy1JdMtxLnwl5fVAKN1Y09gV8FJRguCjhCYqXjdmz800Eo5mrx4DHGQS31pj39AyoTlWQMqr4Y+goPbeysYqIWsOWrv" +
                            "DmEXD4ZEJhW9zcogVPabtKZqFhp9llK52FdNbo2jxk5N8nPMJp+7Eohlb2q2UTraIuo65kaU0mw77s8j9Qa61eQXsJ2DmIVJHlup1JewHuFWFwdUMPL6+BGRrDS2oXbYdUqXr+cRDLxIZP5eS2W3xgT0YhubeUYHQMf" +
                            "FkYOqb1hzZDl+Ijb6T6vaEq5mhxuRIbQHJvYs2LBvZ5Xhy6TQRuoGLkr3Wu4rzbnOosc4QrjQAKoB4j0QQW7jO2BBWY4WCvReIb3dNS5AOKkK31w+FxlUK/vBgjqdGUCwEBj6ujAY1s8bfC2fFGwzhaAyxGUZFGcxWA" +
                            "5vlwwG7TZ65/vyiV33jdaxeZ6lB3ZN6lDXEDWtjCrXmbBlHYnkd3dY/y3JNQOmjFLBmS0uAfCGVWVfWw1bbDIA4F8WoDcxmjdSpTcQX8Ql1854IGhVmnQ1+uY24Cs7bl+NhEQP0=")
            .setName("&fPancernik &8[&7Lvl. &61&8]").setLore(Arrays.asList(
                    "&8Typ: Obronca",
                    "&7Odpornosc Na Ludzi: &c0%",
                    "&7Odpornosc Na Potwory: &c0%",
                    "",
                    "&7&lZwykly Zwierzak",
                    "&8Exp: &60&8/&6" + PetLevels.getExp(2, "Zwykly")
            )).toItemStack(), "Zwykly")),
    PA2("Pancernik-Rzadki", addLevelsAndExpToPet(new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setSkullOwnerByURL("761c2bf2-74fc-4b06-af38-acafb59a9645", "skin550e24df",
                    "ewogICJ0aW1lc3RhbXAiIDogMTY2NDcwOTQ4MzA0MSwKICAicHJvZmlsZUlkIiA6ICIzOTdlMmY5OTAyNmI0NjI1OTcyNTM1OTNjODgyZjRmMyIsCiAgInByb2ZpbGVOYW1lIiA6ICJ4WnlkdWVMeCIsCiAgInNpZ" +
                            "25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS8xMDAwM2ZjOWI0Y2" +
                            "NjMjMyMWVhN2M2ZmVjNjRjYjE5MTQ5ODc1ZDZiYWY2OTJkODgyOGMwNGNmNDU0NjE1YTdjIgogICAgfQogIH0KfQ==", "JkMuprorRPasHaK1J2Q8ePQdMibQyp/HmNtz2pYGMS/h56Y2R4Hl1ySWa5c" +
                            "tSFmtiHw+CvlUiTyGowSEIPnuE4qhHB0xqMrBZ5w0ZHNn3DlM0R+26UyDN5lApaTdSmcmmb7Dy1JdMtxLnwl5fVAKN1Y09gV8FJRguCjhCYqXjdmz800Eo5mrx4DHGQS31pj39AyoTlWQMqr4Y+goPbeysYqIWsOWrv" +
                            "DmEXD4ZEJhW9zcogVPabtKZqFhp9llK52FdNbo2jxk5N8nPMJp+7Eohlb2q2UTraIuo65kaU0mw77s8j9Qa61eQXsJ2DmIVJHlup1JewHuFWFwdUMPL6+BGRrDS2oXbYdUqXr+cRDLxIZP5eS2W3xgT0YhubeUYHQMf" +
                            "FkYOqb1hzZDl+Ijb6T6vaEq5mhxuRIbQHJvYs2LBvZ5Xhy6TQRuoGLkr3Wu4rzbnOosc4QrjQAKoB4j0QQW7jO2BBWY4WCvReIb3dNS5AOKkK31w+FxlUK/vBgjqdGUCwEBj6ujAY1s8bfC2fFGwzhaAyxGUZFGcxWA" +
                            "5vlwwG7TZ65/vyiV33jdaxeZ6lB3ZN6lDXEDWtjCrXmbBlHYnkd3dY/y3JNQOmjFLBmS0uAfCGVWVfWw1bbDIA4F8WoDcxmjdSpTcQX8Ql1854IGhVmnQ1+uY24Cs7bl+NhEQP0=")
            .setName("&1Pancernik &8[&7Lvl. &61&8]").setLore(Arrays.asList(
                    "&8Typ: Obronca",
                    "&7Odpornosc Na Ludzi: &c0%",
                    "&7Odpornosc Na Potwory: &c0%",
                    "",
                    "&1&lRzadki Zwierzak",
                    "&8Exp: &60&8/&6" + PetLevels.getExp(2, "Rzadki")
            )).toItemStack(), "Rzadki")),
    PA3("Pancernik-Epicki", addLevelsAndExpToPet(new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setSkullOwnerByURL("761c2bf2-74fc-4b06-af38-acafb59a9645", "skin550e24df",
                    "ewogICJ0aW1lc3RhbXAiIDogMTY2NDcwOTQ4MzA0MSwKICAicHJvZmlsZUlkIiA6ICIzOTdlMmY5OTAyNmI0NjI1OTcyNTM1OTNjODgyZjRmMyIsCiAgInByb2ZpbGVOYW1lIiA6ICJ4WnlkdWVMeCIsCiAgInNpZ" +
                            "25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS8xMDAwM2ZjOWI0Y2" +
                            "NjMjMyMWVhN2M2ZmVjNjRjYjE5MTQ5ODc1ZDZiYWY2OTJkODgyOGMwNGNmNDU0NjE1YTdjIgogICAgfQogIH0KfQ==", "JkMuprorRPasHaK1J2Q8ePQdMibQyp/HmNtz2pYGMS/h56Y2R4Hl1ySWa5c" +
                            "tSFmtiHw+CvlUiTyGowSEIPnuE4qhHB0xqMrBZ5w0ZHNn3DlM0R+26UyDN5lApaTdSmcmmb7Dy1JdMtxLnwl5fVAKN1Y09gV8FJRguCjhCYqXjdmz800Eo5mrx4DHGQS31pj39AyoTlWQMqr4Y+goPbeysYqIWsOWrv" +
                            "DmEXD4ZEJhW9zcogVPabtKZqFhp9llK52FdNbo2jxk5N8nPMJp+7Eohlb2q2UTraIuo65kaU0mw77s8j9Qa61eQXsJ2DmIVJHlup1JewHuFWFwdUMPL6+BGRrDS2oXbYdUqXr+cRDLxIZP5eS2W3xgT0YhubeUYHQMf" +
                            "FkYOqb1hzZDl+Ijb6T6vaEq5mhxuRIbQHJvYs2LBvZ5Xhy6TQRuoGLkr3Wu4rzbnOosc4QrjQAKoB4j0QQW7jO2BBWY4WCvReIb3dNS5AOKkK31w+FxlUK/vBgjqdGUCwEBj6ujAY1s8bfC2fFGwzhaAyxGUZFGcxWA" +
                            "5vlwwG7TZ65/vyiV33jdaxeZ6lB3ZN6lDXEDWtjCrXmbBlHYnkd3dY/y3JNQOmjFLBmS0uAfCGVWVfWw1bbDIA4F8WoDcxmjdSpTcQX8Ql1854IGhVmnQ1+uY24Cs7bl+NhEQP0=")
            .setName("&5Pancernik &8[&7Lvl. &61&8]").setLore(Arrays.asList(
                    "&8Typ: Obronca",
                    "&7Odpornosc Na Ludzi: &c0%",
                    "&7Odpornosc Na Potwory: &c0%",
                    "",
                    "&5&lEpicki Zwierzak",
                    "&8Exp: &60&8/&6" + PetLevels.getExp(2, "Epicki")
            )).toItemStack(), "Epicki")),
    PA4("Pancernik-Legendarny", addLevelsAndExpToPet(new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setSkullOwnerByURL("761c2bf2-74fc-4b06-af38-acafb59a9645", "skin550e24df",
                    "ewogICJ0aW1lc3RhbXAiIDogMTY2NDcwOTQ4MzA0MSwKICAicHJvZmlsZUlkIiA6ICIzOTdlMmY5OTAyNmI0NjI1OTcyNTM1OTNjODgyZjRmMyIsCiAgInByb2ZpbGVOYW1lIiA6ICJ4WnlkdWVMeCIsCiAgInNpZ" +
                            "25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS8xMDAwM2ZjOWI0Y2" +
                            "NjMjMyMWVhN2M2ZmVjNjRjYjE5MTQ5ODc1ZDZiYWY2OTJkODgyOGMwNGNmNDU0NjE1YTdjIgogICAgfQogIH0KfQ==", "JkMuprorRPasHaK1J2Q8ePQdMibQyp/HmNtz2pYGMS/h56Y2R4Hl1ySWa5c" +
                            "tSFmtiHw+CvlUiTyGowSEIPnuE4qhHB0xqMrBZ5w0ZHNn3DlM0R+26UyDN5lApaTdSmcmmb7Dy1JdMtxLnwl5fVAKN1Y09gV8FJRguCjhCYqXjdmz800Eo5mrx4DHGQS31pj39AyoTlWQMqr4Y+goPbeysYqIWsOWrv" +
                            "DmEXD4ZEJhW9zcogVPabtKZqFhp9llK52FdNbo2jxk5N8nPMJp+7Eohlb2q2UTraIuo65kaU0mw77s8j9Qa61eQXsJ2DmIVJHlup1JewHuFWFwdUMPL6+BGRrDS2oXbYdUqXr+cRDLxIZP5eS2W3xgT0YhubeUYHQMf" +
                            "FkYOqb1hzZDl+Ijb6T6vaEq5mhxuRIbQHJvYs2LBvZ5Xhy6TQRuoGLkr3Wu4rzbnOosc4QrjQAKoB4j0QQW7jO2BBWY4WCvReIb3dNS5AOKkK31w+FxlUK/vBgjqdGUCwEBj6ujAY1s8bfC2fFGwzhaAyxGUZFGcxWA" +
                            "5vlwwG7TZ65/vyiV33jdaxeZ6lB3ZN6lDXEDWtjCrXmbBlHYnkd3dY/y3JNQOmjFLBmS0uAfCGVWVfWw1bbDIA4F8WoDcxmjdSpTcQX8Ql1854IGhVmnQ1+uY24Cs7bl+NhEQP0=")
            .setName("&6Pancernik &8[&7Lvl. &61&8]").setLore(Arrays.asList(
                    "&8Typ: Obronca",
                    "&7Odpornosc Na Ludzi: &c0%",
                    "&7Odpornosc Na Potwory: &c0%",
                    "",
                    "&6&lLegendarny Zwierzak",
                    "&8Exp: &60&8/&6" + PetLevels.getExp(2, "Legendarny")
            )).toItemStack(), "Legendarny")),
    FO1("Foka-Zwykly", addLevelsAndExpToPet(new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setSkullOwnerByURL("b2560a25-b44e-4ea0-acb0-d02b4385fa28", "skin29126b21",
                    "ewogICJ0aW1lc3RhbXAiIDogMTY2NDUwMzcwMDA1NCwKICAicHJvZmlsZUlkIiA6ICIwZDZjODU0ODA3ZGQ0NWZkYmMxZDEyMzY2OGY1ZWQwZiIsCiAgInByb2ZpbGVOYW1lIiA6ICJXcWxmZnhJcmt0IiwKICAic2lnb" +
                            "mF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzRkZDJmMTVkNzg2M2MwN2" +
                            "FiODJiODYxMjY0MmExMGIxMmFjNTc2NjQ4MzcwYjdhYjQ2YjA0MmIzM2MxYzcxYzMiCiAgICB9CiAgfQp9", "GitJ5O1SnagsyXOOjpNwmVuDxUAXAA9HpAwV6HDVtVVDEGv+S5eESbxHcIp52p9Z/DpeGyY" +
                            "mIGI8ZYIr0oOPEUrmXlPtUR5e+lJBLEJp5icE7I6lRiFMgdd6e9VDnRwtNrItFPReUMK3jIdYYKSVxmSgZ0frGuzzC1DjJBrrw3JgfxfeOt8vtn1qEJ6Z5m6Pwxn5yjWJG5UO8M+Dt7cAgY5AvZcz7zi+2k5yH" +
                            "zlHUcrrmxF/ldETNHeVZSafbsi05XuQp9ldZhwkGpvsCIE41BCGZ/pIeTr0W5ohPHfDMMRmTMiHL3k7vTjMA+HSbMij+3lQ6eMnVqNEnrO2FVnUEVw8Vfb+iMViseEFX8PD1Ksz7joBgtzqA1yK1LnY5iICyY9" +
                            "SZSdBssU5MI++cE4MRPMJOOjZmCBneelOOBsZKLS5XSlaA8NZB53tNFtQu/Ca4SqWJqKA1iBm0mf/FuXaMzlYa0Fx0LmZ/Hj44kOh40BJXzXgF3tEO3XmTA4dYE29RCgvG7bHzMOa3HIX+zJju+ZsccCvIoifh" +
                            "w2WJ5K05/oeenGbbFWJBbM6io0XBF0tyLiZRYU1nG8mo8x59r7nTUWKpOsTxYE7bowdC2fO7VUVa2o4/ZTyPEMEQxngg4fxj3DUBmbljd+XL3spe9djLSSK7tk1O61u3lhhRXJbPoQ=")
            .setName("&fFoka &8[&7Lvl. &61&8]").setLore(Arrays.asList(
                    "&8Typ: Wodny",
                    "&7Szansa na Podwojenie Polowu: &c0%",
                    "&7Morskie Szczescie: &c0%",
                    "",
                    "&7&lZwykly Zwierzak",
                    "&8Exp: &60&8/&6" + PetLevels.getExp(2, "Zwykly")
            )).toItemStack(), "Zwykly")),
    FO2("Foka-Rzadki", addLevelsAndExpToPet(new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setSkullOwnerByURL("b2560a25-b44e-4ea0-acb0-d02b4385fa28", "skin29126b21",
                    "ewogICJ0aW1lc3RhbXAiIDogMTY2NDUwMzcwMDA1NCwKICAicHJvZmlsZUlkIiA6ICIwZDZjODU0ODA3ZGQ0NWZkYmMxZDEyMzY2OGY1ZWQwZiIsCiAgInByb2ZpbGVOYW1lIiA6ICJXcWxmZnhJcmt0IiwKICAic2lnb" +
                            "mF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzRkZDJmMTVkNzg2M2MwN2" +
                            "FiODJiODYxMjY0MmExMGIxMmFjNTc2NjQ4MzcwYjdhYjQ2YjA0MmIzM2MxYzcxYzMiCiAgICB9CiAgfQp9", "GitJ5O1SnagsyXOOjpNwmVuDxUAXAA9HpAwV6HDVtVVDEGv+S5eESbxHcIp52p9Z/DpeGyY" +
                            "mIGI8ZYIr0oOPEUrmXlPtUR5e+lJBLEJp5icE7I6lRiFMgdd6e9VDnRwtNrItFPReUMK3jIdYYKSVxmSgZ0frGuzzC1DjJBrrw3JgfxfeOt8vtn1qEJ6Z5m6Pwxn5yjWJG5UO8M+Dt7cAgY5AvZcz7zi+2k5yH" +
                            "zlHUcrrmxF/ldETNHeVZSafbsi05XuQp9ldZhwkGpvsCIE41BCGZ/pIeTr0W5ohPHfDMMRmTMiHL3k7vTjMA+HSbMij+3lQ6eMnVqNEnrO2FVnUEVw8Vfb+iMViseEFX8PD1Ksz7joBgtzqA1yK1LnY5iICyY9" +
                            "SZSdBssU5MI++cE4MRPMJOOjZmCBneelOOBsZKLS5XSlaA8NZB53tNFtQu/Ca4SqWJqKA1iBm0mf/FuXaMzlYa0Fx0LmZ/Hj44kOh40BJXzXgF3tEO3XmTA4dYE29RCgvG7bHzMOa3HIX+zJju+ZsccCvIoifh" +
                            "w2WJ5K05/oeenGbbFWJBbM6io0XBF0tyLiZRYU1nG8mo8x59r7nTUWKpOsTxYE7bowdC2fO7VUVa2o4/ZTyPEMEQxngg4fxj3DUBmbljd+XL3spe9djLSSK7tk1O61u3lhhRXJbPoQ=")
            .setName("&1Foka &8[&7Lvl. &61&8]").setLore(Arrays.asList(
                    "&8Typ: Wodny",
                    "&7Szansa na Podwojenie Polowu: &c0%",
                    "&7Morskie Szczescie: &c0%",
                    "",
                    "&1&lRzadki Zwierzak",
                    "&8Exp: &60&8/&6" + PetLevels.getExp(2, "Rzadki")
            )).toItemStack(), "Rzadki")),
    FO3("Foka-Epicki", addLevelsAndExpToPet(new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setSkullOwnerByURL("b2560a25-b44e-4ea0-acb0-d02b4385fa28", "skin29126b21",
                    "ewogICJ0aW1lc3RhbXAiIDogMTY2NDUwMzcwMDA1NCwKICAicHJvZmlsZUlkIiA6ICIwZDZjODU0ODA3ZGQ0NWZkYmMxZDEyMzY2OGY1ZWQwZiIsCiAgInByb2ZpbGVOYW1lIiA6ICJXcWxmZnhJcmt0IiwKICAic2lnb" +
                            "mF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzRkZDJmMTVkNzg2M2MwN2" +
                            "FiODJiODYxMjY0MmExMGIxMmFjNTc2NjQ4MzcwYjdhYjQ2YjA0MmIzM2MxYzcxYzMiCiAgICB9CiAgfQp9", "GitJ5O1SnagsyXOOjpNwmVuDxUAXAA9HpAwV6HDVtVVDEGv+S5eESbxHcIp52p9Z/DpeGyY" +
                            "mIGI8ZYIr0oOPEUrmXlPtUR5e+lJBLEJp5icE7I6lRiFMgdd6e9VDnRwtNrItFPReUMK3jIdYYKSVxmSgZ0frGuzzC1DjJBrrw3JgfxfeOt8vtn1qEJ6Z5m6Pwxn5yjWJG5UO8M+Dt7cAgY5AvZcz7zi+2k5yH" +
                            "zlHUcrrmxF/ldETNHeVZSafbsi05XuQp9ldZhwkGpvsCIE41BCGZ/pIeTr0W5ohPHfDMMRmTMiHL3k7vTjMA+HSbMij+3lQ6eMnVqNEnrO2FVnUEVw8Vfb+iMViseEFX8PD1Ksz7joBgtzqA1yK1LnY5iICyY9" +
                            "SZSdBssU5MI++cE4MRPMJOOjZmCBneelOOBsZKLS5XSlaA8NZB53tNFtQu/Ca4SqWJqKA1iBm0mf/FuXaMzlYa0Fx0LmZ/Hj44kOh40BJXzXgF3tEO3XmTA4dYE29RCgvG7bHzMOa3HIX+zJju+ZsccCvIoifh" +
                            "w2WJ5K05/oeenGbbFWJBbM6io0XBF0tyLiZRYU1nG8mo8x59r7nTUWKpOsTxYE7bowdC2fO7VUVa2o4/ZTyPEMEQxngg4fxj3DUBmbljd+XL3spe9djLSSK7tk1O61u3lhhRXJbPoQ=")
            .setName("&5Foka &8[&7Lvl. &61&8]").setLore(Arrays.asList(
                    "&8Typ: Wodny",
                    "&7Szansa na Podwojenie Polowu: &c0%",
                    "&7Morskie Szczescie: &c0%",
                    "",
                    "&5&lEpicki Zwierzak",
                    "&8Exp: &60&8/&6" + PetLevels.getExp(2, "Epicki")
            )).toItemStack(), "Epicki")),
    FO4("Foka-Legendarny", addLevelsAndExpToPet(new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setSkullOwnerByURL("b2560a25-b44e-4ea0-acb0-d02b4385fa28", "skin29126b21",
                    "ewogICJ0aW1lc3RhbXAiIDogMTY2NDUwMzcwMDA1NCwKICAicHJvZmlsZUlkIiA6ICIwZDZjODU0ODA3ZGQ0NWZkYmMxZDEyMzY2OGY1ZWQwZiIsCiAgInByb2ZpbGVOYW1lIiA6ICJXcWxmZnhJcmt0IiwKICAic2lnb" +
                            "mF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzRkZDJmMTVkNzg2M2MwN2" +
                            "FiODJiODYxMjY0MmExMGIxMmFjNTc2NjQ4MzcwYjdhYjQ2YjA0MmIzM2MxYzcxYzMiCiAgICB9CiAgfQp9", "GitJ5O1SnagsyXOOjpNwmVuDxUAXAA9HpAwV6HDVtVVDEGv+S5eESbxHcIp52p9Z/DpeGyY" +
                            "mIGI8ZYIr0oOPEUrmXlPtUR5e+lJBLEJp5icE7I6lRiFMgdd6e9VDnRwtNrItFPReUMK3jIdYYKSVxmSgZ0frGuzzC1DjJBrrw3JgfxfeOt8vtn1qEJ6Z5m6Pwxn5yjWJG5UO8M+Dt7cAgY5AvZcz7zi+2k5yH" +
                            "zlHUcrrmxF/ldETNHeVZSafbsi05XuQp9ldZhwkGpvsCIE41BCGZ/pIeTr0W5ohPHfDMMRmTMiHL3k7vTjMA+HSbMij+3lQ6eMnVqNEnrO2FVnUEVw8Vfb+iMViseEFX8PD1Ksz7joBgtzqA1yK1LnY5iICyY9" +
                            "SZSdBssU5MI++cE4MRPMJOOjZmCBneelOOBsZKLS5XSlaA8NZB53tNFtQu/Ca4SqWJqKA1iBm0mf/FuXaMzlYa0Fx0LmZ/Hj44kOh40BJXzXgF3tEO3XmTA4dYE29RCgvG7bHzMOa3HIX+zJju+ZsccCvIoifh" +
                            "w2WJ5K05/oeenGbbFWJBbM6io0XBF0tyLiZRYU1nG8mo8x59r7nTUWKpOsTxYE7bowdC2fO7VUVa2o4/ZTyPEMEQxngg4fxj3DUBmbljd+XL3spe9djLSSK7tk1O61u3lhhRXJbPoQ=")
            .setName("&6Foka &8[&7Lvl. &61&8]").setLore(Arrays.asList(
                    "&8Typ: Wodny",
                    "&7Szansa na Podwojenie Polowu: &c0%",
                    "&7Morskie Szczescie: &c0%",
                    "",
                    "&6&lLegendarny Zwierzak",
                    "&8Exp: &60&8/&6" + PetLevels.getExp(2, "Legendarny")
            )).toItemStack(), "Legendarny")),
    NI1("Nietoperz-Zwykly", addLevelsAndExpToPet(new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setSkullOwnerByURL("7e28eed1-b93b-4851-873a-f45109d53b34", "skine79681e2",
                    "ewogICJ0aW1lc3RhbXAiIDogMTY1OTU5MTEyOTY1NCwKICAicHJvZmlsZUlkIiA6ICIxOTQzY2VmYzM4NWM0YTJjYWJiZGViODBjZTIwM2RjZSIsCiAgInByb2ZpbGVOYW1lIiA6ICJzb29vb29vb29vb29vb29wIiwKICAi" +
                            "c2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2Y4YzMyYjg4NDQzYTI5" +
                            "YjU1ZjE2N2ZmNTRhZjQyNWMxNDdhZmE1ZmViZjYzMGYxYTVkNDA4OTA4ZDFkZjViN2YiCiAgICB9CiAgfQp9", "T2rbkD3wTeQc6RIinTewKcD87PtxLDzb8hLaeyQU42ayDkB4LDhhCy8SvkDD4SYuZt6chq2" +
                            "HkRh4xaV13dxT+S4sS4NgUa0zXqNCXGl+rrxeoIS4xXIuow8Q52Ghf9BzhDAILAHZnvKS2dMxdrY87XsJ4Ww08Ynj21BIp7oqGMtNUKn6icCDOwO6K6T5DEmyiwclJwqZS71hNwdUHKchlat7MHmi/0K2Mm0aGM7" +
                            "Mpjml1BNl8RZd8K+ONMn+SrfiCI7TDsq8BpVZLCMjVkkFhlmV/SnYAxoWBGzxpYZ3fAJvyM2ZlVe9PZV+Xgy1mmOsdEvEP1+JUawl1QBTPOH7wCjFrqS91xYxLCF1sUaX0f+Q9y6/k3BdPB9q7OhGhIbtGjWVwR/" +
                            "L+grhgoPCsn5KL87phjum1203g8yWURqdneLOh2J9j8vlAsHeA1VBJ4k5a5uCy/Tlt0CheKMXfTBV0ZQtpHnmWFGogfrKgMNUQDNeL5J+rihlWUBYfCNA+7hWmkLXyr1eAZ/fPEaWs+Rt4CelSZLNh/GpnJuptvu" +
                            "nwfE9n3EqcdSNeVGMEukP8xLQvqF5T9/ReOrKyjXif2u/XWvrfh6HkJUotBJeeY70zvulHPsTlt80bwDrLOJEaRdrTr75pu6m2YI9TqDSNT//SaiII/TGij86A1X3MSsnweg=")
            .setName("&fNietoperz &8[&7Lvl. &61&8]").setLore(Arrays.asList(
                    "&8Typ: Magiczny",
                    "&7Zwiekszenie sznasy na Ulepszenie przedmiotu: &c0%",
                    "&7Zmniejszenie Cen Craftingu przedmiotów u Kowala: &c0%",
                    "",
                    "&7&lZwykly Zwierzak",
                    "&8Exp: &60&8/&6" + PetLevels.getExp(2, "Zwykly")
            )).toItemStack(), "Zwykly")),
    NI2("Nietoperz-Rzadki", addLevelsAndExpToPet(new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setSkullOwnerByURL("7e28eed1-b93b-4851-873a-f45109d53b34", "skine79681e2",
                    "ewogICJ0aW1lc3RhbXAiIDogMTY1OTU5MTEyOTY1NCwKICAicHJvZmlsZUlkIiA6ICIxOTQzY2VmYzM4NWM0YTJjYWJiZGViODBjZTIwM2RjZSIsCiAgInByb2ZpbGVOYW1lIiA6ICJzb29vb29vb29vb29vb29wIiwKICAi" +
                            "c2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2Y4YzMyYjg4NDQzYTI5" +
                            "YjU1ZjE2N2ZmNTRhZjQyNWMxNDdhZmE1ZmViZjYzMGYxYTVkNDA4OTA4ZDFkZjViN2YiCiAgICB9CiAgfQp9", "T2rbkD3wTeQc6RIinTewKcD87PtxLDzb8hLaeyQU42ayDkB4LDhhCy8SvkDD4SYuZt6chq2" +
                            "HkRh4xaV13dxT+S4sS4NgUa0zXqNCXGl+rrxeoIS4xXIuow8Q52Ghf9BzhDAILAHZnvKS2dMxdrY87XsJ4Ww08Ynj21BIp7oqGMtNUKn6icCDOwO6K6T5DEmyiwclJwqZS71hNwdUHKchlat7MHmi/0K2Mm0aGM7" +
                            "Mpjml1BNl8RZd8K+ONMn+SrfiCI7TDsq8BpVZLCMjVkkFhlmV/SnYAxoWBGzxpYZ3fAJvyM2ZlVe9PZV+Xgy1mmOsdEvEP1+JUawl1QBTPOH7wCjFrqS91xYxLCF1sUaX0f+Q9y6/k3BdPB9q7OhGhIbtGjWVwR/" +
                            "L+grhgoPCsn5KL87phjum1203g8yWURqdneLOh2J9j8vlAsHeA1VBJ4k5a5uCy/Tlt0CheKMXfTBV0ZQtpHnmWFGogfrKgMNUQDNeL5J+rihlWUBYfCNA+7hWmkLXyr1eAZ/fPEaWs+Rt4CelSZLNh/GpnJuptvu" +
                            "nwfE9n3EqcdSNeVGMEukP8xLQvqF5T9/ReOrKyjXif2u/XWvrfh6HkJUotBJeeY70zvulHPsTlt80bwDrLOJEaRdrTr75pu6m2YI9TqDSNT//SaiII/TGij86A1X3MSsnweg=")
            .setName("&1Nietoperz &8[&7Lvl. &61&8]").setLore(Arrays.asList(
                    "&8Typ: Magiczny",
                    "&7Zwiekszenie sznasy na Ulepszenie przedmiotu: &c0%",
                    "&7Zmniejszenie Cen Craftingu przedmiotów u Kowala: &c0%",
                    "",
                    "&1&lRzadki Zwierzak",
                    "&8Exp: &60&8/&6" + PetLevels.getExp(2, "Rzadki")
            )).toItemStack(), "Rzadki")),
    NI3("Nietoperz-Epicki", addLevelsAndExpToPet(new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setSkullOwnerByURL("7e28eed1-b93b-4851-873a-f45109d53b34", "skine79681e2",
                    "ewogICJ0aW1lc3RhbXAiIDogMTY1OTU5MTEyOTY1NCwKICAicHJvZmlsZUlkIiA6ICIxOTQzY2VmYzM4NWM0YTJjYWJiZGViODBjZTIwM2RjZSIsCiAgInByb2ZpbGVOYW1lIiA6ICJzb29vb29vb29vb29vb29wIiwKICAi" +
                            "c2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2Y4YzMyYjg4NDQzYTI5" +
                            "YjU1ZjE2N2ZmNTRhZjQyNWMxNDdhZmE1ZmViZjYzMGYxYTVkNDA4OTA4ZDFkZjViN2YiCiAgICB9CiAgfQp9", "T2rbkD3wTeQc6RIinTewKcD87PtxLDzb8hLaeyQU42ayDkB4LDhhCy8SvkDD4SYuZt6chq2" +
                            "HkRh4xaV13dxT+S4sS4NgUa0zXqNCXGl+rrxeoIS4xXIuow8Q52Ghf9BzhDAILAHZnvKS2dMxdrY87XsJ4Ww08Ynj21BIp7oqGMtNUKn6icCDOwO6K6T5DEmyiwclJwqZS71hNwdUHKchlat7MHmi/0K2Mm0aGM7" +
                            "Mpjml1BNl8RZd8K+ONMn+SrfiCI7TDsq8BpVZLCMjVkkFhlmV/SnYAxoWBGzxpYZ3fAJvyM2ZlVe9PZV+Xgy1mmOsdEvEP1+JUawl1QBTPOH7wCjFrqS91xYxLCF1sUaX0f+Q9y6/k3BdPB9q7OhGhIbtGjWVwR/" +
                            "L+grhgoPCsn5KL87phjum1203g8yWURqdneLOh2J9j8vlAsHeA1VBJ4k5a5uCy/Tlt0CheKMXfTBV0ZQtpHnmWFGogfrKgMNUQDNeL5J+rihlWUBYfCNA+7hWmkLXyr1eAZ/fPEaWs+Rt4CelSZLNh/GpnJuptvu" +
                            "nwfE9n3EqcdSNeVGMEukP8xLQvqF5T9/ReOrKyjXif2u/XWvrfh6HkJUotBJeeY70zvulHPsTlt80bwDrLOJEaRdrTr75pu6m2YI9TqDSNT//SaiII/TGij86A1X3MSsnweg=")
            .setName("&5Nietoperz &8[&7Lvl. &61&8]").setLore(Arrays.asList(
                    "&8Typ: Magiczny",
                    "&7Zwiekszenie sznasy na Ulepszenie przedmiotu: &c0%",
                    "&7Zmniejszenie Cen Craftingu przedmiotów u Kowala: &c0%",
                    "",
                    "&5&lEpicki Zwierzak",
                    "&8Exp: &60&8/&6" + PetLevels.getExp(2, "Epicki")
            )).toItemStack(), "Epicki")),
    NI4("Nietoperz-Legendarny", addLevelsAndExpToPet(new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setSkullOwnerByURL("7e28eed1-b93b-4851-873a-f45109d53b34", "skine79681e2",
                    "ewogICJ0aW1lc3RhbXAiIDogMTY1OTU5MTEyOTY1NCwKICAicHJvZmlsZUlkIiA6ICIxOTQzY2VmYzM4NWM0YTJjYWJiZGViODBjZTIwM2RjZSIsCiAgInByb2ZpbGVOYW1lIiA6ICJzb29vb29vb29vb29vb29wIiwKICAi" +
                            "c2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2Y4YzMyYjg4NDQzYTI5" +
                            "YjU1ZjE2N2ZmNTRhZjQyNWMxNDdhZmE1ZmViZjYzMGYxYTVkNDA4OTA4ZDFkZjViN2YiCiAgICB9CiAgfQp9", "T2rbkD3wTeQc6RIinTewKcD87PtxLDzb8hLaeyQU42ayDkB4LDhhCy8SvkDD4SYuZt6chq2" +
                            "HkRh4xaV13dxT+S4sS4NgUa0zXqNCXGl+rrxeoIS4xXIuow8Q52Ghf9BzhDAILAHZnvKS2dMxdrY87XsJ4Ww08Ynj21BIp7oqGMtNUKn6icCDOwO6K6T5DEmyiwclJwqZS71hNwdUHKchlat7MHmi/0K2Mm0aGM7" +
                            "Mpjml1BNl8RZd8K+ONMn+SrfiCI7TDsq8BpVZLCMjVkkFhlmV/SnYAxoWBGzxpYZ3fAJvyM2ZlVe9PZV+Xgy1mmOsdEvEP1+JUawl1QBTPOH7wCjFrqS91xYxLCF1sUaX0f+Q9y6/k3BdPB9q7OhGhIbtGjWVwR/" +
                            "L+grhgoPCsn5KL87phjum1203g8yWURqdneLOh2J9j8vlAsHeA1VBJ4k5a5uCy/Tlt0CheKMXfTBV0ZQtpHnmWFGogfrKgMNUQDNeL5J+rihlWUBYfCNA+7hWmkLXyr1eAZ/fPEaWs+Rt4CelSZLNh/GpnJuptvu" +
                            "nwfE9n3EqcdSNeVGMEukP8xLQvqF5T9/ReOrKyjXif2u/XWvrfh6HkJUotBJeeY70zvulHPsTlt80bwDrLOJEaRdrTr75pu6m2YI9TqDSNT//SaiII/TGij86A1X3MSsnweg=")
            .setName("&6Nietoperz &8[&7Lvl. &61&8]").setLore(Arrays.asList(
                    "&8Typ: Magiczny",
                    "&7Zwiekszenie sznasy na Ulepszenie przedmiotu: &c0%",
                    "&7Zmniejszenie Cen Craftingu przedmiotów u Kowala: &c0%",
                    "",
                    "&6&lLegendarny Zwierzak",
                    "&8Exp: &60&8/&6" + PetLevels.getExp(2, "Legendarny")
            )).toItemStack(), "Legendarny")),
    BO1("Bobr-Zwykly", addLevelsAndExpToPet(new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setSkullOwnerByURL("5c598930-0b14-37e7-826a-c96241402abf", "skinffe1ee76",
                    "eyJ0aW1lc3RhbXAiOjE0OTE5MzE5MDMxMTEsInByb2ZpbGVJZCI6IjdkYTJhYjNhOTNjYTQ4ZWU4MzA0OGFmYzNiODBlNjhlIiwicHJvZmlsZU5hbWUiOiJHb2xkYXBmZWwiLCJzaWduYXR1cmVSZXF1aXJlZCI6dHJ1ZSwi" +
                            "dGV4dHVyZXMiOnsiU0tJTiI6eyJ1cmwiOiJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2IwNjAxNGI1M2NmYTkyMzQ0NDY3ODE4MTcyYTg1NTE1NzllZjcyNWZmNGYwYjhkYWZiNzY4MWFkNWI3MTU" +
                            "3In19fQ==", "hRXD4o1Q8RNfy/t/74GkVh+MiDraoyiz1IH2B3C7oHS7e8di8hlgNbExxkLiN5zR3q/36QIylVWqn9qDxq18NaaFvd+cr12i968+OziyKr409wwchGqrNRiH/Mx9BcWF0Urt44/cE8q2qctE" +
                            "4xfHIpIUNWNNrzWwhQCot0R2ppEext1m3ccMNPrrCXX7dIZybzD+9VItlGVj6aKzAT0WqMyaHABerRH8Z+swcar2UNxVPcy0BPo0VUEyNjzae5ZpcIGp0i8gL1e0q5MqXgd/yDV2+7Fnsvc5Dj7WzyEZTo2woS" +
                            "Vq9bqH58b2dr9QHFh2f/R7Hftx3By++7Az8iCcR74g5Nz+gGZaLzr+ez3Wm9Knd2sDT6qhT2J5/+r/0gDGdQLL5hZLGHe3daPk6j99g4u/MFXEmgvUXdNYjwdA44eL1Iqwf7iyyRZ/44iDsQ3sQe0a8ZNPC/js" +
                            "YUYlcTr/QEZ2qvk1JeV+YQlP3AnE1azML03OFhRI7cM+E48rRzBo98r2/uW1dUUBzUCAnww2aIqQqK8Nkllm2I3gd1IPFCls8FpSID3hedkt/AqYDzL59ZZ8TCWw6EqIKx5WrYz97iSgW15vIHVNMBw3P6Es0T" +
                            "LwM008TlYxVjoY/kMyfes2nrGXkjOKbE+pfjvXFZFumNZwWq7AelZv/FLbu/6eVkU=")
            .setName("&fBobr &8[&7Lvl. &61&8]").setLore(Arrays.asList(
                    "&8Typ: Magiczny",
                    "&7Zwiekszenie sznasy na przyjecie u Lesnika: &c0%",
                    "&7Zwiekszenie szansy na znalezienie przedmiotu do Lesnika: &c0%",
                    "",
                    "&7&lZwykly Zwierzak",
                    "&8Exp: &60&8/&6" + PetLevels.getExp(2, "Zwykly")
            )).toItemStack(), "Zwykly")),
    BO2("Bobr-Rzadki", addLevelsAndExpToPet(new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setSkullOwnerByURL("5c598930-0b14-37e7-826a-c96241402abf", "skinffe1ee76",
                    "eyJ0aW1lc3RhbXAiOjE0OTE5MzE5MDMxMTEsInByb2ZpbGVJZCI6IjdkYTJhYjNhOTNjYTQ4ZWU4MzA0OGFmYzNiODBlNjhlIiwicHJvZmlsZU5hbWUiOiJHb2xkYXBmZWwiLCJzaWduYXR1cmVSZXF1aXJlZCI6dHJ1ZSwi" +
                            "dGV4dHVyZXMiOnsiU0tJTiI6eyJ1cmwiOiJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2IwNjAxNGI1M2NmYTkyMzQ0NDY3ODE4MTcyYTg1NTE1NzllZjcyNWZmNGYwYjhkYWZiNzY4MWFkNWI3MTU" +
                            "3In19fQ==", "hRXD4o1Q8RNfy/t/74GkVh+MiDraoyiz1IH2B3C7oHS7e8di8hlgNbExxkLiN5zR3q/36QIylVWqn9qDxq18NaaFvd+cr12i968+OziyKr409wwchGqrNRiH/Mx9BcWF0Urt44/cE8q2qctE" +
                            "4xfHIpIUNWNNrzWwhQCot0R2ppEext1m3ccMNPrrCXX7dIZybzD+9VItlGVj6aKzAT0WqMyaHABerRH8Z+swcar2UNxVPcy0BPo0VUEyNjzae5ZpcIGp0i8gL1e0q5MqXgd/yDV2+7Fnsvc5Dj7WzyEZTo2woS" +
                            "Vq9bqH58b2dr9QHFh2f/R7Hftx3By++7Az8iCcR74g5Nz+gGZaLzr+ez3Wm9Knd2sDT6qhT2J5/+r/0gDGdQLL5hZLGHe3daPk6j99g4u/MFXEmgvUXdNYjwdA44eL1Iqwf7iyyRZ/44iDsQ3sQe0a8ZNPC/js" +
                            "YUYlcTr/QEZ2qvk1JeV+YQlP3AnE1azML03OFhRI7cM+E48rRzBo98r2/uW1dUUBzUCAnww2aIqQqK8Nkllm2I3gd1IPFCls8FpSID3hedkt/AqYDzL59ZZ8TCWw6EqIKx5WrYz97iSgW15vIHVNMBw3P6Es0T" +
                            "LwM008TlYxVjoY/kMyfes2nrGXkjOKbE+pfjvXFZFumNZwWq7AelZv/FLbu/6eVkU=")
            .setName("&1Bobr &8[&7Lvl. &61&8]").setLore(Arrays.asList(
                    "&8Typ: Magiczny",
                    "&7Zwiekszenie sznasy na przyjecie u Lesnika: &c0%",
                    "&7Zwiekszenie szansy na znalezienie przedmiotu do Lesnika: &c0%",
                    "",
                    "&1&lRzadki Zwierzak",
                    "&8Exp: &60&8/&6" + PetLevels.getExp(2, "Rzadki")
            )).toItemStack(), "Rzadki")),
    BO3("Bobr-Epicki", addLevelsAndExpToPet(new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setSkullOwnerByURL("5c598930-0b14-37e7-826a-c96241402abf", "skinffe1ee76",
                    "eyJ0aW1lc3RhbXAiOjE0OTE5MzE5MDMxMTEsInByb2ZpbGVJZCI6IjdkYTJhYjNhOTNjYTQ4ZWU4MzA0OGFmYzNiODBlNjhlIiwicHJvZmlsZU5hbWUiOiJHb2xkYXBmZWwiLCJzaWduYXR1cmVSZXF1aXJlZCI6dHJ1ZSwi" +
                            "dGV4dHVyZXMiOnsiU0tJTiI6eyJ1cmwiOiJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2IwNjAxNGI1M2NmYTkyMzQ0NDY3ODE4MTcyYTg1NTE1NzllZjcyNWZmNGYwYjhkYWZiNzY4MWFkNWI3MTU" +
                            "3In19fQ==", "hRXD4o1Q8RNfy/t/74GkVh+MiDraoyiz1IH2B3C7oHS7e8di8hlgNbExxkLiN5zR3q/36QIylVWqn9qDxq18NaaFvd+cr12i968+OziyKr409wwchGqrNRiH/Mx9BcWF0Urt44/cE8q2qctE" +
                            "4xfHIpIUNWNNrzWwhQCot0R2ppEext1m3ccMNPrrCXX7dIZybzD+9VItlGVj6aKzAT0WqMyaHABerRH8Z+swcar2UNxVPcy0BPo0VUEyNjzae5ZpcIGp0i8gL1e0q5MqXgd/yDV2+7Fnsvc5Dj7WzyEZTo2woS" +
                            "Vq9bqH58b2dr9QHFh2f/R7Hftx3By++7Az8iCcR74g5Nz+gGZaLzr+ez3Wm9Knd2sDT6qhT2J5/+r/0gDGdQLL5hZLGHe3daPk6j99g4u/MFXEmgvUXdNYjwdA44eL1Iqwf7iyyRZ/44iDsQ3sQe0a8ZNPC/js" +
                            "YUYlcTr/QEZ2qvk1JeV+YQlP3AnE1azML03OFhRI7cM+E48rRzBo98r2/uW1dUUBzUCAnww2aIqQqK8Nkllm2I3gd1IPFCls8FpSID3hedkt/AqYDzL59ZZ8TCWw6EqIKx5WrYz97iSgW15vIHVNMBw3P6Es0T" +
                            "LwM008TlYxVjoY/kMyfes2nrGXkjOKbE+pfjvXFZFumNZwWq7AelZv/FLbu/6eVkU=")
            .setName("&5Bobr &8[&7Lvl. &61&8]").setLore(Arrays.asList(
                    "&8Typ: Magiczny",
                    "&7Zwiekszenie sznasy na przyjecie u Lesnika: &c0%",
                    "&7Zwiekszenie szansy na znalezienie przedmiotu do Lesnika: &c0%",
                    "",
                    "&5&lEpicki Zwierzak",
                    "&8Exp: &60&8/&6" + PetLevels.getExp(2, "Epicki")
            )).toItemStack(), "Epicki")),
    BO4("Bobr-Legendarny", addLevelsAndExpToPet(new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setSkullOwnerByURL("5c598930-0b14-37e7-826a-c96241402abf", "skinffe1ee76",
                    "eyJ0aW1lc3RhbXAiOjE0OTE5MzE5MDMxMTEsInByb2ZpbGVJZCI6IjdkYTJhYjNhOTNjYTQ4ZWU4MzA0OGFmYzNiODBlNjhlIiwicHJvZmlsZU5hbWUiOiJHb2xkYXBmZWwiLCJzaWduYXR1cmVSZXF1aXJlZCI6dHJ1ZSwi" +
                            "dGV4dHVyZXMiOnsiU0tJTiI6eyJ1cmwiOiJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2IwNjAxNGI1M2NmYTkyMzQ0NDY3ODE4MTcyYTg1NTE1NzllZjcyNWZmNGYwYjhkYWZiNzY4MWFkNWI3MTU" +
                            "3In19fQ==", "hRXD4o1Q8RNfy/t/74GkVh+MiDraoyiz1IH2B3C7oHS7e8di8hlgNbExxkLiN5zR3q/36QIylVWqn9qDxq18NaaFvd+cr12i968+OziyKr409wwchGqrNRiH/Mx9BcWF0Urt44/cE8q2qctE" +
                            "4xfHIpIUNWNNrzWwhQCot0R2ppEext1m3ccMNPrrCXX7dIZybzD+9VItlGVj6aKzAT0WqMyaHABerRH8Z+swcar2UNxVPcy0BPo0VUEyNjzae5ZpcIGp0i8gL1e0q5MqXgd/yDV2+7Fnsvc5Dj7WzyEZTo2woS" +
                            "Vq9bqH58b2dr9QHFh2f/R7Hftx3By++7Az8iCcR74g5Nz+gGZaLzr+ez3Wm9Knd2sDT6qhT2J5/+r/0gDGdQLL5hZLGHe3daPk6j99g4u/MFXEmgvUXdNYjwdA44eL1Iqwf7iyyRZ/44iDsQ3sQe0a8ZNPC/js" +
                            "YUYlcTr/QEZ2qvk1JeV+YQlP3AnE1azML03OFhRI7cM+E48rRzBo98r2/uW1dUUBzUCAnww2aIqQqK8Nkllm2I3gd1IPFCls8FpSID3hedkt/AqYDzL59ZZ8TCWw6EqIKx5WrYz97iSgW15vIHVNMBw3P6Es0T" +
                            "LwM008TlYxVjoY/kMyfes2nrGXkjOKbE+pfjvXFZFumNZwWq7AelZv/FLbu/6eVkU=")
            .setName("&6Bobr &8[&7Lvl. &61&8]").setLore(Arrays.asList(
                    "&8Typ: Magiczny",
                    "&7Zwiekszenie sznasy na przyjecie u Lesnika: &c0%",
                    "&7Zwiekszenie szansy na znalezienie przedmiotu do Lesnika: &c0%",
                    "",
                    "&6&lLegendarny Zwierzak",
                    "&8Exp: &60&8/&6" + PetLevels.getExp(2, "Legendarny")
            )).toItemStack(), "Legendarny")),
    OS1("Ognisty-Smok-Zwykly", addLevelsAndExpToPet(new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setSkullOwnerByURL("c1e738a7-4bdf-4044-8d19-25bf10080dca", "skin1f4ebcc8",
                    "ewogICJ0aW1lc3RhbXAiIDogMTYzNDY3OTcyODIyMiwKICAicHJvZmlsZUlkIiA6ICJkMWY2OTc0YzE2ZmI0ZjdhYjI1NjU4NzExNjM3M2U2NSIsCiAgInByb2ZpbGVOYW1lIiA6ICJGaW9saWVzdGEiLAogICJzaWduYXR" +
                            "1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGI1MWI0NTg5M2YzNGY0MTc4N" +
                            "mEyNWIwNjU5M2VlNDEyYmI1NDQwNzRiZmUwZTM4ZDBlYTg2Zjg0MzdlMjRhYSIKICAgIH0KICB9Cn0=", "dgdGwrxGjFP8+FpswpAtg2kdOKJ0nDgyp0330wmNAyRNfx3hO3MKssVd0gxw0B8Hox2ssScy4w" +
                            "D7gnJ+Ik+xhtfOYXDqIGWOjxgeOm84h9Jg3LcGC3hVWwHSY07UxiF9k6qqReYd3kqvzQj7Nk4tkfKLvESntQ0ZmZNsx5NhaJnEX5eayIABR05LH0iBjIx8zIGJOJMtlUKRTsH3+NMdWpLgzLYdnjDW9asrefCM" +
                            "VMt91klNzaMK++1BwRhWAl/7c0aG1eY6H9dWgM1Wxa0uEobgNUAb+OhTJH2kDipSn1YXLTsNxz2+xt0BZN3bBYM5fnjAPnrElErrXTq4nRwjtg7GmXj+taJO4SFuMcZwh6pAayQuyd27Cs29jUbzyO7/ECFVpq" +
                            "GUOq8jti7EKn5MtSD9F331J9D71vnT0KQmcfdQMweZ55xaJkJASN77l1EyhLd+2N2uNTJ4AGTHhRc8ls2MlhlInsDumxlLnAeHp2KWKIxziSKtylQrFMjSx1MLuLP4mSP8+QLUQpCAWGQcsK2wRpDSjsMO+QBP" +
                            "xxK6Dm2zeq/GRG+cSb/UeIxq9n1bYTwvlk7ZDPpjvFxh0CY9s807aQOu08Em9ZU9WrtHV46U6mijeZH6FB32E/YpVoF7SqcuafDAlcnz4jMi68N2KHYWRqkMGu4N9eoKMOAihsE=")
            .setName("&fOgnisty Smok &8[&7Lvl. &61&8]").setLore(Arrays.asList(
                    "&8Typ: Walczacy",
                    "&7Wzmocnienie Krytyka: &c0%",
                    "&7Dodatkowe Obrażenia: &c0",
                    "",
                    "&7&&lZwykly Zwierzak",
                    "&8Exp: &60&8/&6" + PetLevels.getExp(2, "Zwykly")
            )).toItemStack(), "Zwykly")),
    OS2("Ognisty-Smok-Rzadki", addLevelsAndExpToPet(new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setSkullOwnerByURL("c1e738a7-4bdf-4044-8d19-25bf10080dca", "skin1f4ebcc8",
                    "ewogICJ0aW1lc3RhbXAiIDogMTYzNDY3OTcyODIyMiwKICAicHJvZmlsZUlkIiA6ICJkMWY2OTc0YzE2ZmI0ZjdhYjI1NjU4NzExNjM3M2U2NSIsCiAgInByb2ZpbGVOYW1lIiA6ICJGaW9saWVzdGEiLAogICJzaWduYXR" +
                            "1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGI1MWI0NTg5M2YzNGY0MTc4N" +
                            "mEyNWIwNjU5M2VlNDEyYmI1NDQwNzRiZmUwZTM4ZDBlYTg2Zjg0MzdlMjRhYSIKICAgIH0KICB9Cn0=", "dgdGwrxGjFP8+FpswpAtg2kdOKJ0nDgyp0330wmNAyRNfx3hO3MKssVd0gxw0B8Hox2ssScy4w" +
                            "D7gnJ+Ik+xhtfOYXDqIGWOjxgeOm84h9Jg3LcGC3hVWwHSY07UxiF9k6qqReYd3kqvzQj7Nk4tkfKLvESntQ0ZmZNsx5NhaJnEX5eayIABR05LH0iBjIx8zIGJOJMtlUKRTsH3+NMdWpLgzLYdnjDW9asrefCM" +
                            "VMt91klNzaMK++1BwRhWAl/7c0aG1eY6H9dWgM1Wxa0uEobgNUAb+OhTJH2kDipSn1YXLTsNxz2+xt0BZN3bBYM5fnjAPnrElErrXTq4nRwjtg7GmXj+taJO4SFuMcZwh6pAayQuyd27Cs29jUbzyO7/ECFVpq" +
                            "GUOq8jti7EKn5MtSD9F331J9D71vnT0KQmcfdQMweZ55xaJkJASN77l1EyhLd+2N2uNTJ4AGTHhRc8ls2MlhlInsDumxlLnAeHp2KWKIxziSKtylQrFMjSx1MLuLP4mSP8+QLUQpCAWGQcsK2wRpDSjsMO+QBP" +
                            "xxK6Dm2zeq/GRG+cSb/UeIxq9n1bYTwvlk7ZDPpjvFxh0CY9s807aQOu08Em9ZU9WrtHV46U6mijeZH6FB32E/YpVoF7SqcuafDAlcnz4jMi68N2KHYWRqkMGu4N9eoKMOAihsE=")
            .setName("&1Ognisty Smok &8[&7Lvl. &61&8]").setLore(Arrays.asList(
                    "&8Typ: Walczacy",
                    "&7Wzmocnienie Krytyka: &c0%",
                    "&7Dodatkowe Obrażenia: &c0",
                    "",
                    "&1&lRzadki Zwierzak",
                    "&8Exp: &60&8/&6" + PetLevels.getExp(2, "Rzadki")
            )).toItemStack(), "Rzadki")),
    OS3("Ognisty-Smok-Epicki", addLevelsAndExpToPet(new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setSkullOwnerByURL("c1e738a7-4bdf-4044-8d19-25bf10080dca", "skin1f4ebcc8",
                    "ewogICJ0aW1lc3RhbXAiIDogMTYzNDY3OTcyODIyMiwKICAicHJvZmlsZUlkIiA6ICJkMWY2OTc0YzE2ZmI0ZjdhYjI1NjU4NzExNjM3M2U2NSIsCiAgInByb2ZpbGVOYW1lIiA6ICJGaW9saWVzdGEiLAogICJzaWduYXR" +
                            "1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGI1MWI0NTg5M2YzNGY0MTc4N" +
                            "mEyNWIwNjU5M2VlNDEyYmI1NDQwNzRiZmUwZTM4ZDBlYTg2Zjg0MzdlMjRhYSIKICAgIH0KICB9Cn0=", "dgdGwrxGjFP8+FpswpAtg2kdOKJ0nDgyp0330wmNAyRNfx3hO3MKssVd0gxw0B8Hox2ssScy4w" +
                            "D7gnJ+Ik+xhtfOYXDqIGWOjxgeOm84h9Jg3LcGC3hVWwHSY07UxiF9k6qqReYd3kqvzQj7Nk4tkfKLvESntQ0ZmZNsx5NhaJnEX5eayIABR05LH0iBjIx8zIGJOJMtlUKRTsH3+NMdWpLgzLYdnjDW9asrefCM" +
                            "VMt91klNzaMK++1BwRhWAl/7c0aG1eY6H9dWgM1Wxa0uEobgNUAb+OhTJH2kDipSn1YXLTsNxz2+xt0BZN3bBYM5fnjAPnrElErrXTq4nRwjtg7GmXj+taJO4SFuMcZwh6pAayQuyd27Cs29jUbzyO7/ECFVpq" +
                            "GUOq8jti7EKn5MtSD9F331J9D71vnT0KQmcfdQMweZ55xaJkJASN77l1EyhLd+2N2uNTJ4AGTHhRc8ls2MlhlInsDumxlLnAeHp2KWKIxziSKtylQrFMjSx1MLuLP4mSP8+QLUQpCAWGQcsK2wRpDSjsMO+QBP" +
                            "xxK6Dm2zeq/GRG+cSb/UeIxq9n1bYTwvlk7ZDPpjvFxh0CY9s807aQOu08Em9ZU9WrtHV46U6mijeZH6FB32E/YpVoF7SqcuafDAlcnz4jMi68N2KHYWRqkMGu4N9eoKMOAihsE=")
            .setName("&5Ognisty Smok &8[&7Lvl. &61&8]").setLore(Arrays.asList(
                    "&8Typ: Walczacy",
                    "&7Wzmocnienie Krytyka: &c0%",
                    "&7Dodatkowe Obrażenia: &c0",
                    "",
                    "&5&lEpicki Zwierzak",
                    "&8Exp: &60&8/&6" + PetLevels.getExp(2, "Epicki")
            )).toItemStack(), "Epicki")),
    OS4("Ognisty-Smok-Legendarny", addLevelsAndExpToPet(new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setSkullOwnerByURL("c1e738a7-4bdf-4044-8d19-25bf10080dca", "skin1f4ebcc8",
                    "ewogICJ0aW1lc3RhbXAiIDogMTYzNDY3OTcyODIyMiwKICAicHJvZmlsZUlkIiA6ICJkMWY2OTc0YzE2ZmI0ZjdhYjI1NjU4NzExNjM3M2U2NSIsCiAgInByb2ZpbGVOYW1lIiA6ICJGaW9saWVzdGEiLAogICJzaWduYXR" +
                            "1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGI1MWI0NTg5M2YzNGY0MTc4N" +
                            "mEyNWIwNjU5M2VlNDEyYmI1NDQwNzRiZmUwZTM4ZDBlYTg2Zjg0MzdlMjRhYSIKICAgIH0KICB9Cn0=", "dgdGwrxGjFP8+FpswpAtg2kdOKJ0nDgyp0330wmNAyRNfx3hO3MKssVd0gxw0B8Hox2ssScy4w" +
                            "D7gnJ+Ik+xhtfOYXDqIGWOjxgeOm84h9Jg3LcGC3hVWwHSY07UxiF9k6qqReYd3kqvzQj7Nk4tkfKLvESntQ0ZmZNsx5NhaJnEX5eayIABR05LH0iBjIx8zIGJOJMtlUKRTsH3+NMdWpLgzLYdnjDW9asrefCM" +
                            "VMt91klNzaMK++1BwRhWAl/7c0aG1eY6H9dWgM1Wxa0uEobgNUAb+OhTJH2kDipSn1YXLTsNxz2+xt0BZN3bBYM5fnjAPnrElErrXTq4nRwjtg7GmXj+taJO4SFuMcZwh6pAayQuyd27Cs29jUbzyO7/ECFVpq" +
                            "GUOq8jti7EKn5MtSD9F331J9D71vnT0KQmcfdQMweZ55xaJkJASN77l1EyhLd+2N2uNTJ4AGTHhRc8ls2MlhlInsDumxlLnAeHp2KWKIxziSKtylQrFMjSx1MLuLP4mSP8+QLUQpCAWGQcsK2wRpDSjsMO+QBP" +
                            "xxK6Dm2zeq/GRG+cSb/UeIxq9n1bYTwvlk7ZDPpjvFxh0CY9s807aQOu08Em9ZU9WrtHV46U6mijeZH6FB32E/YpVoF7SqcuafDAlcnz4jMi68N2KHYWRqkMGu4N9eoKMOAihsE=")
            .setName("&6Ognisty Smok &8[&7Lvl. &61&8]").setLore(Arrays.asList(
                    "&8Typ: Walczacy",
                    "&7Wzmocnienie Krytyka: &c0%",
                    "&7Dodatkowe Obrazenia: &c0",
                    "",
                    "&6&lLegendarny Zwierzak",
                    "&8Exp: &60&8/&6" + PetLevels.getExp(2, "Legendarny")
            )).toItemStack(), "Legendarny")),
    DE1("Demon-Zwykly", addLevelsAndExpToPet(new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setSkullOwnerByURL("fd34278d-9a1f-4227-8f64-3e5272d6724a", "skina08b2a9d",
                    "ewogICJ0aW1lc3RhbXAiIDogMTYyNzI2ODg2NDMzMCwKICAicHJvZmlsZUlkIiA6ICI0ODBkMmFiMmY1ZTk0OWQ0YWM2Mzk0NDdmNjIzYTYwYyIsCiAgInByb2ZpbGVOYW1lIiA6ICJIb3RhbXBhIiwKICAic2lnbmF0dX" +
                            "JlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzg0ZjczOGM3YWVmYmYzZWJiMWM" +
                            "5YTRkYzkxYzIzMmMzYTY1MjM5MWVkYWQwY2I4ZTU4ZjI4MmJkN2EwOWQ4MmUiLAogICAgICAibWV0YWRhdGEiIDogewogICAgICAgICJtb2RlbCIgOiAic2xpbSIKICAgICAgfQogICAgfQogIH0KfQ==", "Fccf" +
                            "cVc4hr3YB6UHR7pCeNV1U7RKvOAn69/itJ0n7TCxAR0N1jJZIHRceQZoHePCQpI+mniIzgJFsqn1BLStQhT7yfdhV7HHPLPjlBD96TescHp0C3d/sg+G8O83dR+XYxq/56FV80pmlzzeAutFFL1KRvcDw2kRKqf+br" +
                            "WyG+HBNpJLqMCx+IgDARiodg1YjZ0X3eyxv2VaKTRu/nAc2Kfc1s+TU78qeGEULEWbg3Fh31chEhdmXjmqxdQ8D3M003rGtvk2/SpH+ov/o7lLiyqY5iUYURoMoyZH6SRWGEe0H/O1cfnWw55tLA9muHBcKv9e0pmj" +
                            "OUT1U5rI0rLMqVq+YcSwgxvmnOzX4xdyCpY9xRYRF0BuSDhjMUSfJ20kcuRqLvyc7878AwqAQ3kP4PpfEZIrCStE/F4VeyM70kpak7gxtA4U3noqrpfc66uqTdCZYiE1AxYMcsO6byS4TmU1ZXZItK4oF17OY0yBKk" +
                            "FAi9L1cgxolOHOvIk+2Iby9dLsOKEQqk72EOISqbG6C1tNnybmPYc6i4U+U6CQSJyIyK+ljnoA+rgQDHCtPFFEDGTeyJAT0AuK1FDRFR+wjgZaf4UC29n1Egi6TqGYgTCpywyNDpM002wKUbPrH+tyjQyNe5AcrjcK" +
                            "hQ3hzlgYJftzy5shvteAU5oxRsxVrDk=")
            .setName("&fDemon &8[&7Lvl. &61&8]").setLore(Arrays.asList(
                    "&8Typ: Walczacy",
                    "&7Srednie Obrazenia: &c0%",
                    "&7Wzmocnienie Krytyka: &c0%",
                    "",
                    "&7&lZwykly Zwierzak",
                    "&8Exp: &60&8/&6" + PetLevels.getExp(2, "Zwykly")
            )).toItemStack(), "Zwykly")),
    DE2("Demon-Rzadki", addLevelsAndExpToPet(new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setSkullOwnerByURL("fd34278d-9a1f-4227-8f64-3e5272d6724a", "skina08b2a9d",
                    "ewogICJ0aW1lc3RhbXAiIDogMTYyNzI2ODg2NDMzMCwKICAicHJvZmlsZUlkIiA6ICI0ODBkMmFiMmY1ZTk0OWQ0YWM2Mzk0NDdmNjIzYTYwYyIsCiAgInByb2ZpbGVOYW1lIiA6ICJIb3RhbXBhIiwKICAic2lnbmF0dX" +
                            "JlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzg0ZjczOGM3YWVmYmYzZWJiMWM" +
                            "5YTRkYzkxYzIzMmMzYTY1MjM5MWVkYWQwY2I4ZTU4ZjI4MmJkN2EwOWQ4MmUiLAogICAgICAibWV0YWRhdGEiIDogewogICAgICAgICJtb2RlbCIgOiAic2xpbSIKICAgICAgfQogICAgfQogIH0KfQ==", "Fccf" +
                            "cVc4hr3YB6UHR7pCeNV1U7RKvOAn69/itJ0n7TCxAR0N1jJZIHRceQZoHePCQpI+mniIzgJFsqn1BLStQhT7yfdhV7HHPLPjlBD96TescHp0C3d/sg+G8O83dR+XYxq/56FV80pmlzzeAutFFL1KRvcDw2kRKqf+br" +
                            "WyG+HBNpJLqMCx+IgDARiodg1YjZ0X3eyxv2VaKTRu/nAc2Kfc1s+TU78qeGEULEWbg3Fh31chEhdmXjmqxdQ8D3M003rGtvk2/SpH+ov/o7lLiyqY5iUYURoMoyZH6SRWGEe0H/O1cfnWw55tLA9muHBcKv9e0pmj" +
                            "OUT1U5rI0rLMqVq+YcSwgxvmnOzX4xdyCpY9xRYRF0BuSDhjMUSfJ20kcuRqLvyc7878AwqAQ3kP4PpfEZIrCStE/F4VeyM70kpak7gxtA4U3noqrpfc66uqTdCZYiE1AxYMcsO6byS4TmU1ZXZItK4oF17OY0yBKk" +
                            "FAi9L1cgxolOHOvIk+2Iby9dLsOKEQqk72EOISqbG6C1tNnybmPYc6i4U+U6CQSJyIyK+ljnoA+rgQDHCtPFFEDGTeyJAT0AuK1FDRFR+wjgZaf4UC29n1Egi6TqGYgTCpywyNDpM002wKUbPrH+tyjQyNe5AcrjcK" +
                            "hQ3hzlgYJftzy5shvteAU5oxRsxVrDk=")
            .setName("&1Demon &8[&7Lvl. &61&8]").setLore(Arrays.asList(
                    "&8Typ: Walczacy",
                    "&7Srednie Obrazenia: &c0%",
                    "&7Wzmocnienie Krytyka: &c0%",
                    "",
                    "&1&lRzadki Zwierzak",
                    "&8Exp: &60&8/&6" + PetLevels.getExp(2, "Rzadki")
            )).toItemStack(), "Rzadki")),
    DE3("Demon-Epicki", addLevelsAndExpToPet(new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setSkullOwnerByURL("fd34278d-9a1f-4227-8f64-3e5272d6724a", "skina08b2a9d",
                    "ewogICJ0aW1lc3RhbXAiIDogMTYyNzI2ODg2NDMzMCwKICAicHJvZmlsZUlkIiA6ICI0ODBkMmFiMmY1ZTk0OWQ0YWM2Mzk0NDdmNjIzYTYwYyIsCiAgInByb2ZpbGVOYW1lIiA6ICJIb3RhbXBhIiwKICAic2lnbmF0dX" +
                            "JlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzg0ZjczOGM3YWVmYmYzZWJiMWM" +
                            "5YTRkYzkxYzIzMmMzYTY1MjM5MWVkYWQwY2I4ZTU4ZjI4MmJkN2EwOWQ4MmUiLAogICAgICAibWV0YWRhdGEiIDogewogICAgICAgICJtb2RlbCIgOiAic2xpbSIKICAgICAgfQogICAgfQogIH0KfQ==", "Fccf" +
                            "cVc4hr3YB6UHR7pCeNV1U7RKvOAn69/itJ0n7TCxAR0N1jJZIHRceQZoHePCQpI+mniIzgJFsqn1BLStQhT7yfdhV7HHPLPjlBD96TescHp0C3d/sg+G8O83dR+XYxq/56FV80pmlzzeAutFFL1KRvcDw2kRKqf+br" +
                            "WyG+HBNpJLqMCx+IgDARiodg1YjZ0X3eyxv2VaKTRu/nAc2Kfc1s+TU78qeGEULEWbg3Fh31chEhdmXjmqxdQ8D3M003rGtvk2/SpH+ov/o7lLiyqY5iUYURoMoyZH6SRWGEe0H/O1cfnWw55tLA9muHBcKv9e0pmj" +
                            "OUT1U5rI0rLMqVq+YcSwgxvmnOzX4xdyCpY9xRYRF0BuSDhjMUSfJ20kcuRqLvyc7878AwqAQ3kP4PpfEZIrCStE/F4VeyM70kpak7gxtA4U3noqrpfc66uqTdCZYiE1AxYMcsO6byS4TmU1ZXZItK4oF17OY0yBKk" +
                            "FAi9L1cgxolOHOvIk+2Iby9dLsOKEQqk72EOISqbG6C1tNnybmPYc6i4U+U6CQSJyIyK+ljnoA+rgQDHCtPFFEDGTeyJAT0AuK1FDRFR+wjgZaf4UC29n1Egi6TqGYgTCpywyNDpM002wKUbPrH+tyjQyNe5AcrjcK" +
                            "hQ3hzlgYJftzy5shvteAU5oxRsxVrDk=")
            .setName("&5Demon &8[&7Lvl. &61&8]").setLore(Arrays.asList(
                    "&8Typ: Walczacy",
                    "&7Srednie Obrazenia: &c0%",
                    "&7Wzmocnienie Krytyka: &c0%",
                    "",
                    "&5&lEpicki Zwierzak",
                    "&8Exp: &60&8/&6" + PetLevels.getExp(2, "Epicki")
            )).toItemStack(), "Epicki")),
    DE4("Demon-Legendarny", addLevelsAndExpToPet(new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setSkullOwnerByURL("fd34278d-9a1f-4227-8f64-3e5272d6724a", "skina08b2a9d",
                    "ewogICJ0aW1lc3RhbXAiIDogMTYyNzI2ODg2NDMzMCwKICAicHJvZmlsZUlkIiA6ICI0ODBkMmFiMmY1ZTk0OWQ0YWM2Mzk0NDdmNjIzYTYwYyIsCiAgInByb2ZpbGVOYW1lIiA6ICJIb3RhbXBhIiwKICAic2lnbmF0dX" +
                            "JlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzg0ZjczOGM3YWVmYmYzZWJiMWM" +
                            "5YTRkYzkxYzIzMmMzYTY1MjM5MWVkYWQwY2I4ZTU4ZjI4MmJkN2EwOWQ4MmUiLAogICAgICAibWV0YWRhdGEiIDogewogICAgICAgICJtb2RlbCIgOiAic2xpbSIKICAgICAgfQogICAgfQogIH0KfQ==", "Fccf" +
                            "cVc4hr3YB6UHR7pCeNV1U7RKvOAn69/itJ0n7TCxAR0N1jJZIHRceQZoHePCQpI+mniIzgJFsqn1BLStQhT7yfdhV7HHPLPjlBD96TescHp0C3d/sg+G8O83dR+XYxq/56FV80pmlzzeAutFFL1KRvcDw2kRKqf+br" +
                            "WyG+HBNpJLqMCx+IgDARiodg1YjZ0X3eyxv2VaKTRu/nAc2Kfc1s+TU78qeGEULEWbg3Fh31chEhdmXjmqxdQ8D3M003rGtvk2/SpH+ov/o7lLiyqY5iUYURoMoyZH6SRWGEe0H/O1cfnWw55tLA9muHBcKv9e0pmj" +
                            "OUT1U5rI0rLMqVq+YcSwgxvmnOzX4xdyCpY9xRYRF0BuSDhjMUSfJ20kcuRqLvyc7878AwqAQ3kP4PpfEZIrCStE/F4VeyM70kpak7gxtA4U3noqrpfc66uqTdCZYiE1AxYMcsO6byS4TmU1ZXZItK4oF17OY0yBKk" +
                            "FAi9L1cgxolOHOvIk+2Iby9dLsOKEQqk72EOISqbG6C1tNnybmPYc6i4U+U6CQSJyIyK+ljnoA+rgQDHCtPFFEDGTeyJAT0AuK1FDRFR+wjgZaf4UC29n1Egi6TqGYgTCpywyNDpM002wKUbPrH+tyjQyNe5AcrjcK" +
                            "hQ3hzlgYJftzy5shvteAU5oxRsxVrDk=")
            .setName("&6Demon &8[&7Lvl. &61&8]").setLore(Arrays.asList(
                    "&8Typ: Walczacy",
                    "&7Srednie Obrazenia: &c0%",
                    "&7Wzmocnienie Krytyka: &c0%",
                    "",
                    "&6&lLegendarny Zwierzak",
                    "&8Exp: &60&8/&6" + PetLevels.getExp(2, "Legendarny")
            )).toItemStack(), "Legendarny")),
    WA1("Wampir-Zwykly", addLevelsAndExpToPet(new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setSkullOwnerByURL("dc3301cb-f01e-4469-a5dc-f06461219a54", "skin8183c81b",
                    "eyJ0aW1lc3RhbXAiOjE1ODc5ODc2ODUzMzksInByb2ZpbGVJZCI6IjU2Njc1YjIyMzJmMDRlZTA4OTE3OWU5YzkyMDZjZmU4IiwicHJvZmlsZU5hbWUiOiJUaGVJbmRyYSIsInNpZ25hdHVyZVJlcXVpcmVkIjp0cnVlLCJ" +
                            "0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTRkNDEyZDhhNmI5NmNkN2FhYTgzMjJlZjc1ZmY1ZjQ0Yzc4ODhjZjMyMjQwYWQ4YzkyM2E1OGNlYTc0MD" +
                            "dhNiJ9fX0=",
                    "Wd9zjqezCSgiIMNxlnV389cebzcB0bNGvjteHXDZw0jWVh42GiqW8x+5+55sC1fKLkZmFkC5JIan4g6+3jZQQTZNhr2F9fkuswPNcyvYZsb2+kRf5uRK5ZOWn6sTkJmdXDBMSUCZcWEykx5zhw2GdZ54RqQlhAn+HNwd5" +
                            "JzsmWgQRL+ADFNvK6Y453e7vcg1hf5Ks2YRpI3n6IKlyoOVjEaPFvXiQW276fMVnYsB5oE7JoJsVCKZwrqAEQS6qta9/ODk1Vy89obhBLNYHbM7eSJxymUUJ2I4vzbJy1yIl24a3Fr5aKO4/H8i++JNfv5hTH/hNOP8/rK" +
                            "35C1jTrfC72Yg1Lj+fCdvSZcTK8tIjeqglrFQQbdOb5IujeUFSZ8/2zRYHI6GQ+xx5F4ZGdLKg1MS7jeiKVNJ3AjrWCQdREFFzAPn3RPuB7qnrzQIdbHDpBfFOlgSIZH449ApK2HRjoLVNy5I+T4xScyfC/VRUFRLZiTSQ" +
                            "KysHiw6hNBXp9OKdexP8PtR2FVkHvJ4hBKeSDNdX5Fsoea/3ElKtYcidNn8Vt9lGTo3qDrqS6uijgnih5jhfonPS9Jw7yMPNcWa4eidlqTNgygUkCQ6ydqBDwtXCkg1c7V1OFdk2zwLPaGieX9UDaTxkry2wATOIQvdv94" +
                            "ROPO8wxBBYXCv0GsglNc=")
            .setName("&fWampir &8[&7Lvl. &61&8]").setLore(Arrays.asList(
                    "&8Typ: Walczacy",
                    "&7Wampirym: &c0%",
                    "&7Dodtkowe HP: &c0",
                    "",
                    "&7&lZwykly Zwierzak",
                    "&8Exp: &60&8/&6" + PetLevels.getExp(2, "Zwykly")
            )).toItemStack(), "Zwykly")),
    WA2("Wampir-Rzadki", addLevelsAndExpToPet(new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setSkullOwnerByURL("dc3301cb-f01e-4469-a5dc-f06461219a54", "skin8183c81b",
                    "eyJ0aW1lc3RhbXAiOjE1ODc5ODc2ODUzMzksInByb2ZpbGVJZCI6IjU2Njc1YjIyMzJmMDRlZTA4OTE3OWU5YzkyMDZjZmU4IiwicHJvZmlsZU5hbWUiOiJUaGVJbmRyYSIsInNpZ25hdHVyZVJlcXVpcmVkIjp0cnVlLCJ" +
                            "0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTRkNDEyZDhhNmI5NmNkN2FhYTgzMjJlZjc1ZmY1ZjQ0Yzc4ODhjZjMyMjQwYWQ4YzkyM2E1OGNlYTc0MD" +
                            "dhNiJ9fX0=",
                    "Wd9zjqezCSgiIMNxlnV389cebzcB0bNGvjteHXDZw0jWVh42GiqW8x+5+55sC1fKLkZmFkC5JIan4g6+3jZQQTZNhr2F9fkuswPNcyvYZsb2+kRf5uRK5ZOWn6sTkJmdXDBMSUCZcWEykx5zhw2GdZ54RqQlhAn+HNwd5" +
                            "JzsmWgQRL+ADFNvK6Y453e7vcg1hf5Ks2YRpI3n6IKlyoOVjEaPFvXiQW276fMVnYsB5oE7JoJsVCKZwrqAEQS6qta9/ODk1Vy89obhBLNYHbM7eSJxymUUJ2I4vzbJy1yIl24a3Fr5aKO4/H8i++JNfv5hTH/hNOP8/rK" +
                            "35C1jTrfC72Yg1Lj+fCdvSZcTK8tIjeqglrFQQbdOb5IujeUFSZ8/2zRYHI6GQ+xx5F4ZGdLKg1MS7jeiKVNJ3AjrWCQdREFFzAPn3RPuB7qnrzQIdbHDpBfFOlgSIZH449ApK2HRjoLVNy5I+T4xScyfC/VRUFRLZiTSQ" +
                            "KysHiw6hNBXp9OKdexP8PtR2FVkHvJ4hBKeSDNdX5Fsoea/3ElKtYcidNn8Vt9lGTo3qDrqS6uijgnih5jhfonPS9Jw7yMPNcWa4eidlqTNgygUkCQ6ydqBDwtXCkg1c7V1OFdk2zwLPaGieX9UDaTxkry2wATOIQvdv94" +
                            "ROPO8wxBBYXCv0GsglNc=")
            .setName("&1Wampir &8[&7Lvl. &61&8]").setLore(Arrays.asList(
                    "&8Typ: Walczacy",
                    "&7Wampirym: &c0%",
                    "&7Dodtkowe HP: &c0",
                    "",
                    "&1&lRzadki Zwierzak",
                    "&8Exp: &60&8/&6" + PetLevels.getExp(2, "Rzadki")
            )).toItemStack(), "Rzadki")),
    WA3("Wampir-Epicki", addLevelsAndExpToPet(new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setSkullOwnerByURL("dc3301cb-f01e-4469-a5dc-f06461219a54", "skin8183c81b",
                    "eyJ0aW1lc3RhbXAiOjE1ODc5ODc2ODUzMzksInByb2ZpbGVJZCI6IjU2Njc1YjIyMzJmMDRlZTA4OTE3OWU5YzkyMDZjZmU4IiwicHJvZmlsZU5hbWUiOiJUaGVJbmRyYSIsInNpZ25hdHVyZVJlcXVpcmVkIjp0cnVlLCJ" +
                            "0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTRkNDEyZDhhNmI5NmNkN2FhYTgzMjJlZjc1ZmY1ZjQ0Yzc4ODhjZjMyMjQwYWQ4YzkyM2E1OGNlYTc0MD" +
                            "dhNiJ9fX0=",
                    "Wd9zjqezCSgiIMNxlnV389cebzcB0bNGvjteHXDZw0jWVh42GiqW8x+5+55sC1fKLkZmFkC5JIan4g6+3jZQQTZNhr2F9fkuswPNcyvYZsb2+kRf5uRK5ZOWn6sTkJmdXDBMSUCZcWEykx5zhw2GdZ54RqQlhAn+HNwd5" +
                            "JzsmWgQRL+ADFNvK6Y453e7vcg1hf5Ks2YRpI3n6IKlyoOVjEaPFvXiQW276fMVnYsB5oE7JoJsVCKZwrqAEQS6qta9/ODk1Vy89obhBLNYHbM7eSJxymUUJ2I4vzbJy1yIl24a3Fr5aKO4/H8i++JNfv5hTH/hNOP8/rK" +
                            "35C1jTrfC72Yg1Lj+fCdvSZcTK8tIjeqglrFQQbdOb5IujeUFSZ8/2zRYHI6GQ+xx5F4ZGdLKg1MS7jeiKVNJ3AjrWCQdREFFzAPn3RPuB7qnrzQIdbHDpBfFOlgSIZH449ApK2HRjoLVNy5I+T4xScyfC/VRUFRLZiTSQ" +
                            "KysHiw6hNBXp9OKdexP8PtR2FVkHvJ4hBKeSDNdX5Fsoea/3ElKtYcidNn8Vt9lGTo3qDrqS6uijgnih5jhfonPS9Jw7yMPNcWa4eidlqTNgygUkCQ6ydqBDwtXCkg1c7V1OFdk2zwLPaGieX9UDaTxkry2wATOIQvdv94" +
                            "ROPO8wxBBYXCv0GsglNc=")
            .setName("&5Wampir &8[&7Lvl. &61&8]").setLore(Arrays.asList(
                    "&8Typ: Walczacy",
                    "&7Wampirym: &c0%",
                    "&7Dodtkowe HP: &c0",
                    "",
                    "&5&lEpicki Zwierzak",
                    "&8Exp: &60&8/&6" + PetLevels.getExp(2, "Epicki")
            )).toItemStack(), "Epicki")),
    WA4("Wampir-Legendarny", addLevelsAndExpToPet(new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setSkullOwnerByURL("dc3301cb-f01e-4469-a5dc-f06461219a54", "skin8183c81b",
                    "eyJ0aW1lc3RhbXAiOjE1ODc5ODc2ODUzMzksInByb2ZpbGVJZCI6IjU2Njc1YjIyMzJmMDRlZTA4OTE3OWU5YzkyMDZjZmU4IiwicHJvZmlsZU5hbWUiOiJUaGVJbmRyYSIsInNpZ25hdHVyZVJlcXVpcmVkIjp0cnVlLCJ" +
                            "0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTRkNDEyZDhhNmI5NmNkN2FhYTgzMjJlZjc1ZmY1ZjQ0Yzc4ODhjZjMyMjQwYWQ4YzkyM2E1OGNlYTc0MD" +
                            "dhNiJ9fX0=",
                    "Wd9zjqezCSgiIMNxlnV389cebzcB0bNGvjteHXDZw0jWVh42GiqW8x+5+55sC1fKLkZmFkC5JIan4g6+3jZQQTZNhr2F9fkuswPNcyvYZsb2+kRf5uRK5ZOWn6sTkJmdXDBMSUCZcWEykx5zhw2GdZ54RqQlhAn+HNwd5" +
                            "JzsmWgQRL+ADFNvK6Y453e7vcg1hf5Ks2YRpI3n6IKlyoOVjEaPFvXiQW276fMVnYsB5oE7JoJsVCKZwrqAEQS6qta9/ODk1Vy89obhBLNYHbM7eSJxymUUJ2I4vzbJy1yIl24a3Fr5aKO4/H8i++JNfv5hTH/hNOP8/rK" +
                            "35C1jTrfC72Yg1Lj+fCdvSZcTK8tIjeqglrFQQbdOb5IujeUFSZ8/2zRYHI6GQ+xx5F4ZGdLKg1MS7jeiKVNJ3AjrWCQdREFFzAPn3RPuB7qnrzQIdbHDpBfFOlgSIZH449ApK2HRjoLVNy5I+T4xScyfC/VRUFRLZiTSQ" +
                            "KysHiw6hNBXp9OKdexP8PtR2FVkHvJ4hBKeSDNdX5Fsoea/3ElKtYcidNn8Vt9lGTo3qDrqS6uijgnih5jhfonPS9Jw7yMPNcWa4eidlqTNgygUkCQ6ydqBDwtXCkg1c7V1OFdk2zwLPaGieX9UDaTxkry2wATOIQvdv94" +
                            "ROPO8wxBBYXCv0GsglNc=")
            .setName("&6Wampir &8[&7Lvl. &61&8]").setLore(Arrays.asList(
                    "&8Typ: Walczacy",
                    "&7Wampirym: &c0%",
                    "&7Dodtkowe HP: &c0",
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
