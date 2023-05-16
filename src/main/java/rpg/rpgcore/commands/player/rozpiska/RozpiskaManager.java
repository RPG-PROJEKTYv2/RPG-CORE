package rpg.rpgcore.commands.player.rozpiska;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.Arrays;

public class RozpiskaManager {

    public void openROZPISKAGUI(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 18, Utils.format("&7Rozpiska - menu"));
        ItemBuilder itemGUI = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short)11);
        gui.setItem(0, new ItemBuilder(Material.SKULL_ITEM, 1, (short)3).setSkullOwnerByURL(
                "9f478e65-f537-4e10-bb11-26bd89c43d52",
                "skin2c43cefe",
                "ewogICJ0aW1lc3RhbXAiIDogMTY4NDA5MDE0NDAzNSwKICAicHJvZmlsZUlkIiA6ICJkNzU2OTc4MWUyYjY0OWIyYjVlMjVlYTJhNDZkOGQxOSIsCiAgInByb2ZpbGVOYW1lIiA6ICJEckthcGRvciIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS8yZmQ4ZmMxMjhkOTVlNzQ0YWU0Mjc5MDQ5MTAxMjc0MmU3YmUzZmZiN2MxZTIzNjYyNDRjYjQzZTc1MDlmMjMiCiAgICB9CiAgfQp9",
                "KH6EBQ1xvT6pfBlbdwI4DOe17OyShTh0/ZDNexQkeC0nVnmRvT6YnWyFeynwHWRNPdW3t6VQ0YvXY5LNhBFRZuq9/GKY0K5jrnNKBC+45eI2lyAkDcRi0teRgwNnYu6QiqIdJ4fPExoixhxrhnWDn9211sau1BQd+x1U77SfPzYgY37WOjs6fpLu6MrzwYGZzuZ8vuEK5jdkRCRaGX8qbGFzurx9c2HxD4kEP92Oa4fEyyBbz/viHvAL5qYFmcUwDLQnquw4naEtllH9g3L7YGqBOPBBimlX7ximAGCB2V4LXZQDFONCPDXpZ4RTDsapDK7kRnuUIhsTKr/nTUeAbyx/lVwSebQ+ZrsoFm6HduQFTS9LGE17bY3/Md7qqC77p4LU1g4SpNOJ/fTOW8oiuoZ6WJxgexaN81o0ERJQN0Fw5IGtuVFNmjj84UryBA0WjtbHsqmqOAtdMSqa7ksHUig4LoOz7eivVWRDsWZjmhi1SdcKliEDj3IMikPN4vBfp7+kV+ojS2jRV8Lqx8q24yXIssggnolmVA2KdvRAHcWkZHo2RMrGOHzY8ysc8Di22XW9+w2ejrWHONpsHSJomk5tbI0V0xyDf/c56R0CL3XZlvhM/3ZlYiS1u7zl6AKXZrgusVRz5KJTZVJSh8UywVKHEGwvC6ubNDBFgXeZWvA="
        ).setName("&cPowrot").toItemStack().clone());
        gui.setItem(1, new ItemBuilder(Material.SKULL_ITEM, 1, (short)3).setSkullOwnerByURL(
                "64e1002f-2040-4894-a436-43b417112a48",
                "skin3df1940d",
                "ewogICJ0aW1lc3RhbXAiIDogMTY4NDA5MTc5MTk2MywKICAicHJvZmlsZUlkIiA6ICIyYzEwNjRmY2Q5MTc0MjgyODRlM2JmN2ZhYTdlM2UxYSIsCiAgInByb2ZpbGVOYW1lIiA6ICJOYWVtZSIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9kODRiNmUzMTI1NWRiYjQzNWFlZTUxMWRlMDZmYjQxZDc0YWYwNDAxZGQ5MzhjNTA0OTY1MWI2ODdmNWIyMzBmIgogICAgfQogIH0KfQ===",
"lJ2juxtKs2dzZZZVYHR22TRy7JNBAID+jdgR5f9H40ZbkjXLat80tmrG67lWd30YsIgdE6SouAR4WfHuNis1cxr6zZgL7nsW1Km8iTH3EwNTOa+JEHOrsnbEL2lGA3lgrqFTABAXF2JchtfHIDTChqzP2pHHTNaAibj6e0ZvZVVqw4EsKq4FT+KpXfqRIrVw5zJ+p4XohX/PWFXsFm00cKdv6QPbMTuxV0EJH12aWZ0fUR3ZghmnAuNrOwkC6ku/mTp76ucztmy+ZTnwI5OCnceZ7fiOlgmE/1Mtu55lgpAI7utS7/cIekohOCpq9B3a3fNtCrGFGU/ZfDNQZ28AltQMsc1XVXz1zupfhsLu/hFKBp1Gpg/HqXSW3RU6L8s6VbVrWDMZAk6wkJyUZt52iwTzL5s4F8w9Z/SbBUazyL5OkalfxygB0/Sg95KWJjT0wy6WgQM+zulYeNydocuqLn3ZUlb3XSLnucof63o5tHx/9VprCs0E16H/YLWXlap0Jn9nb/k6H7o/OJfL+m1Gde1TEFzQyCGDTBmsFZaKENp+M34KMdb+iYwZCoBMoBbP72zASA/J3dZjEr7bczO400oPNugtSv9QDKWf9OJHlJCys3NzhaFQ8WbuS5Ct/MJeFao6L1j/12GgQbj/uDKbg+lZBjt/mhNHDNLLY2lu2ps="
        ).setName("&cPowrot").toItemStack().clone());
        //gui.setItem(0, itemGUI.setName("&cExpowisko &8* &f(&a1-10&f)").addGlowing().toItemStack().clone());
        //gui.setItem(1, itemGUI.setName("&cExpowisko &8* &f(&a10-20&f)").addGlowing().toItemStack().clone());
        gui.setItem(2, itemGUI.setName("&cExpowisko &8* &f(&a20-30&f)").addGlowing().toItemStack().clone());
        gui.setItem(3, itemGUI.setName("&cExpowisko &8* &f(&a30-40&f)").addGlowing().toItemStack().clone());
        gui.setItem(4, itemGUI.setName("&cExpowisko &8* &f(&a40-50&f)").addGlowing().toItemStack().clone());
        gui.setItem(5, itemGUI.setName("&cExpowisko &8* &f(&a50-60&f)").addGlowing().toItemStack().clone());
        gui.setItem(6, itemGUI.setName("&cExpowisko &8* &f(&a60-70&f)").addGlowing().toItemStack().clone());
        gui.setItem(7, itemGUI.setName("&cExpowisko &8* &f(&a70-80&f)").addGlowing().toItemStack().clone());
        gui.setItem(8, itemGUI.setName("&cExpowisko &8* &f(&a80-90&f)").addGlowing().toItemStack().clone());
        gui.setItem(9, itemGUI.setName("&cExpowisko &8* &f(&a90-100&f)").addGlowing().toItemStack().clone());
        gui.setItem(10, itemGUI.setName("&cExpowisko &8* &f(&a100-110&f)").addGlowing().toItemStack().clone());
        gui.setItem(11, itemGUI.setName("&cExpowisko &8* &f(&a110-120&f)").addGlowing().toItemStack().clone());
        gui.setItem(12, itemGUI.setName("&cExpowisko &8* &f(&a120-130&f)").addGlowing().toItemStack().clone());
        player.openInventory(gui);
    }
    // do rozpiski:
    // 1. Skrzynki kazda
    // 3. Przyrodnik
    // 2. Ulepszacz
    // 4. Zywica
    // 6. Rubinowe Serce
    // 5. Niesik
    // 9. fragmenty metali
    // 10. czastki magii.
    // 7. Sakwy [expo tam 60...]
    // 8. przepustki
    public void openFIRSTexp(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 18, Utils.format("&7Rozpiska - &f(&a1-10&f)"));
        gui.setItem(0, new ItemBuilder(Material.CHEST).setName("&6Skrzynia Rozbojnika").setLore(Arrays.asList("", "&eSzansa: &aX")).addGlowing().toItemStack().clone());



        gui.setItem(1, new ItemBuilder(Material.WATCH).setName("&6Zardzewialy Pierscien").setLore(Arrays.asList("", "&eSzansa: &aX")).addGlowing().toItemStack().clone());
        gui.setItem(2, new ItemBuilder(Material.RABBIT_HIDE).setName("&8Szata Rozbojnika").setLore(Arrays.asList("", "&eSzansa: &aX")).addGlowing().toItemStack().clone());
        //gui.setItem(3, new ItemBuilder(Material.DOUBLE_PLANT).setName(""));
        //gui.setItem(4, new ItemBuilder());
        gui.setItem(5, new ItemBuilder(Material.DIAMOND_BLOCK).setName("&b&lNIESAMOWITY PRZEDMIOT").setLore(Arrays.asList("", "&eSzansa: &aX")).addGlowing().toItemStack().clone());

        gui.setItem(16, Utils.powrot());
        gui.setItem(17, new ItemBuilder(Material.ARROW).setName("&cPowrot").addGlowing().toItemStack().clone());
        player.openInventory(gui);
    }
    public void openSECONDexp(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&7Rozpiska - &f(&a10-20&f)"));
        gui.setItem(10, new ItemBuilder(Material.CHEST).setName("&fSkrzynia Goblina").setLore(Arrays.asList("", "&eSzansa: &aX")).addGlowing().toItemStack().clone());
        gui.setItem(11, new ItemBuilder(Material.RABBIT_HIDE).setName("&aOko Goblina").setLore(Arrays.asList("", "&eSzansa: &aX")).addGlowing().toItemStack().clone());
        gui.setItem(12, new ItemBuilder(Material.WATCH).setName("&2Ucho Goblina").setLore(Arrays.asList("", "&eSzansa: &aX")).addGlowing().toItemStack().clone());
        gui.setItem(13, new ItemBuilder(Material.DIAMOND_BLOCK).setName("&b&lNIESAMOWITY PRZEDMIOT").setLore(Arrays.asList("", "&eSzansa: &aX")).addGlowing().toItemStack().clone());

        gui.setItem(17, new ItemBuilder(Material.ARROW).setName("&cPowrot").addGlowing().toItemStack().clone());
        player.openInventory(gui);
    }
    public void openTHIRDexp(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&7Rozpiska - &f(&a20-30&f)"));
        gui.setItem(10, new ItemBuilder(Material.CHEST).setName("&fSkrzynia Goblina").setLore(Arrays.asList("", "&eSzansa: &aX")).addGlowing().toItemStack().clone());
        gui.setItem(11, new ItemBuilder(Material.RABBIT_HIDE).setName("&aOko Goblina").setLore(Arrays.asList("", "&eSzansa: &aX")).addGlowing().toItemStack().clone());
        gui.setItem(12, new ItemBuilder(Material.WATCH).setName("&2Ucho Goblina").setLore(Arrays.asList("", "&eSzansa: &aX")).addGlowing().toItemStack().clone());
        gui.setItem(13, new ItemBuilder(Material.DIAMOND_BLOCK).setName("&b&lNIESAMOWITY PRZEDMIOT").setLore(Arrays.asList("", "&eSzansa: &aX")).addGlowing().toItemStack().clone());

        gui.setItem(17, new ItemBuilder(Material.ARROW).setName("&cPowrot").addGlowing().toItemStack().clone());
        player.openInventory(gui);
    }
}
