package rpg.rpgcore.managers.npc;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.RandomItems;
import rpg.rpgcore.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class RybakNPC {

    private final RPGCORE rpgcore;

    public RybakNPC(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    private final HashMap<UUID, String> ryabkMisje = new HashMap<>();
    private final HashMap<UUID, Double> ryabkSrednieDMG = new HashMap<>();
    private final HashMap<UUID, Double> ryabkDodatkowyDMG = new HashMap<>();
    private final HashMap<UUID, Double> ryabkBlok = new HashMap<>();
    private final HashMap<UUID, Double> ryabkSredniDef = new HashMap<>();
    private final HashMap<UUID, Integer> rybakPostep = new HashMap<>();
    private final HashMap<Integer, String> misjeRybackie = new HashMap<>(45);
    private final HashMap<Integer, Double> wymaganyExpWedki = new HashMap<>(50);

    private final ItemBuilder fill = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15);
    private final ItemBuilder sklep = new ItemBuilder(Material.DOUBLE_PLANT);
    private final ItemBuilder misje = new ItemBuilder(Material.BOOK);
    private final ItemBuilder stats = new ItemBuilder(Material.PAPER);
    private final ItemBuilder wedka = new ItemBuilder(Material.FISHING_ROD);

    private final RandomItems<ItemStack> rybakDrops = new RandomItems<>();
    private final RandomItems<String> rybakMobs = new RandomItems<>();

    private final List<String> lore = new ArrayList<>();


    public void openRybakGUI(final Player player) {
        final Inventory rybakGUI = Bukkit.createInventory(null, 27, Utils.format("&6&lMenu Rybaka"));

        fill.setName(" ").hideFlag();

        for (int i = 0; i < rybakGUI.getSize(); i++) {
            rybakGUI.setItem(i, fill.toItemStack());
        }

        lore.clear();

        lore.add(" ");
        lore.add("&8&o*Kliknij*&8 zeby zobaczyc jakie misje ma dla ciebie rybak");

        misje.setName("&4&lKampania Rybacka").addGlowing().setLore(lore);
        rybakGUI.setItem(10, misje.toItemStack());


        lore.clear();

        lore.add(" ");
        lore.add("&8&o*Kliknij*&8 zeby otworzyc sklep rybacki i poznac oferte rybaka");

        sklep.setName("&a&lSklep Rybacki").addGlowing().setLore(lore);
        rybakGUI.setItem(16, sklep.toItemStack());


        lore.clear();

        lore.add("&3Statystyki gracza: &f" + player.getName());
        lore.add("&8Ponizej znajduja sie twoje statystki z lowienia");
        lore.add("&f&lMISJE");
        lore.add("&8- &bSrednie Obrazenia: &f+ " + this.getPlayerRybakSredniDMG(player.getUniqueId()));
        lore.add("&8- &bSrednia Defensywa: &f+ " + this.getPlayerRybakSredniDef(player.getUniqueId()));
        lore.add("&8- &bDodatkowe Obrazenia: &f+ " + this.getPlayerRybakDodatkowyDMG(player.getUniqueId()));
        lore.add("&8- &bBlok Ciosu: &f+ " + this.getPlayerRybakBlok(player.getUniqueId()));
        lore.add("");
        lore.add("&f&lOSIAGNIECIA");
        lore.add("&8- &bWylowione Ryby: &f" + rpgcore.getPlayerManager().getPlayerOsRybak(player.getUniqueId()));


        stats.setName("&b&lTwoje Statystyki").addGlowing().setLore(lore);
        rybakGUI.setItem(13, stats.toItemStack());


        player.openInventory(rybakGUI);
    }

    public void openRybakSklep(final Player player) {
        final Inventory rybakGUI = Bukkit.createInventory(null, 9, Utils.format("&a&lSklep Rybacki"));

        fill.setName(" ").hideFlag();

        for (int i = 0; i < rybakGUI.getSize(); i++) {
            rybakGUI.setItem(i, fill.toItemStack());
        }

        lore.clear();

        lore.add("&bWlasciciel: &f" + player.getName());
        lore.add("&bPoziom: &f0");
        lore.add("&bExp: &f0&b/&f" + this.wymaganyExpWedki.get(1));
        lore.add("&bWylowione ryby: &f0");
        lore.add(" ");
        lore.add("&f&lBonusy");
        lore.add("&8- &bSzansa na podwojne wylowienie: &f0.05%");
        lore.add("&8- &bSzansa na skrzynie rybaka: &f0.005%");
        lore.add("&8- &bSzansa na wylowienie podwodnego stworzenia: &f0.1%");
        lore.add(" ");
        lore.add("&2Cena: &6100 000 000 &2$");

        wedka.setName("&6Wedka").setLore(lore);
        rybakGUI.setItem(4, wedka.toItemStack());

        player.openInventory(rybakGUI);
    }

    public void openRybakKampania(final Player player) {
        final Inventory kampaniaGui = Bukkit.createInventory(null, 45, Utils.format("&4&lKampania Rybacka"));

        final ItemBuilder misjeDone = new ItemBuilder(Material.BOOK);
        final ItemBuilder previousNotDone = new ItemBuilder(Material.BARRIER);

        final String[] playerMisje = rpgcore.getRybakNPC().getPlayerRybakMisje(player.getUniqueId()).split(",");
        final List<String> lore = new ArrayList<>();

        fill.setName(" ").hideFlag();

        for (int i = 0; i < kampaniaGui.getSize(); i++) {
            if (playerMisje[i].equals("true")) {
                lore.clear();
                lore.add("&a&lUKONCZONE");
                kampaniaGui.setItem(i, misjeDone.setName("&c&lMisja #" + (i + 1)).setLore(lore).addGlowing().toItemStack().clone());
            } else {
                if (i != 0 && kampaniaGui.getItem(i - 1).getType().equals(Material.BOOK_AND_QUILL)) {
                    lore.clear();

                    lore.add(" ");
                    lore.add("&cNajpierw musisz wykonac poprzednia misje");
                    lore.add("&czeby otrzymac dostep do kolejnych");

                    for (int j = i; j < kampaniaGui.getSize(); j++) {
                        kampaniaGui.setItem(j, previousNotDone.setName("&c&lMisja #" + (j + 1)).setLore(lore).addGlowing().toItemStack().clone());
                    }
                    break;
                } else {
                    kampaniaGui.setItem(i, this.createCurrentMissionItem(player.getUniqueId(), i).clone());
                }
            }
        }


        player.openInventory(kampaniaGui);
    }

    public void loadRybakMisje() {
        this.misjeRybackie.put(0, "Wylow;192;&6Sledzie");
        this.misjeRybackie.put(1, "Wylow;192;&6Dorsze");
        this.misjeRybackie.put(2, "Wylow;192;&6Lososie");
        this.misjeRybackie.put(3, "Oddaj;256;&6Sledzi&3,&6Dorszy&3,&6Lososi");
        this.misjeRybackie.put(4, "Wylow;192;&6Krasnopiorki");
        this.misjeRybackie.put(5, "Wylow;192;&6Czarne Dorsze");
        this.misjeRybackie.put(6, "Wylow;192;&6Dorady");
        this.misjeRybackie.put(7, "Oddaj;256;&6Kransopiorki&3,&6Czarne dorsze&3,&6Dorady");
        this.misjeRybackie.put(8, "Wylow;1;&3Podwodny Nurek");
        this.misjeRybackie.put(9, "Oddaj;32;&a&lSkrzynia rybaka");
        this.misjeRybackie.put(10, "Wylow;192;&6Cierniczki");
        this.misjeRybackie.put(11, "Wylow;192;&6Fladry");
        this.misjeRybackie.put(12, "Oddaj;512;&6Cierniczki");
        this.misjeRybackie.put(13, "Oddaj;512;&6Fladry");
        this.misjeRybackie.put(14, "Wylow;32;&a&lSkrzynia Rybaka");
        this.misjeRybackie.put(15, "Wylow;1;&3&lNiesamowity Przedmiot Rybacki");
        this.misjeRybackie.put(16, "Zabij;1;&3Podwodny Nurek");
        this.misjeRybackie.put(17, "Wylow;192;&6Karasie");
        this.misjeRybackie.put(18, "Wylow;192;&6Karpie");
        this.misjeRybackie.put(19, "Oddaj;64;&a&lSkrzynia Rybaka");
        this.misjeRybackie.put(20, "Wylow;2;&3&lNiesamowity Przedmiot Rybacki");
        this.misjeRybackie.put(21, "Oddaj;256;&6Karasie&3,&6Karpie");
        this.misjeRybackie.put(22, "Wylow;192;&6Leszcze");
        this.misjeRybackie.put(23, "Wylow;192;&6Makrele");
        this.misjeRybackie.put(24, "Zabij;3;&6&lPodwodny Wladca");
        this.misjeRybackie.put(25, "Wylow;192;&6Mintaje");
        this.misjeRybackie.put(26, "Oddaj;256;&6Leszcze&3,&6Makrele&3,&6Minatje");
        this.misjeRybackie.put(27, "Wylow;5;&6&lPodwodny Wladca");
        this.misjeRybackie.put(28, "Wylow;48;&a&lSkrzynia Rybaka");
        this.misjeRybackie.put(29, "Wylow;192;&6Okonie");
        this.misjeRybackie.put(30, "Wylow;192;&6Plotki");
        this.misjeRybackie.put(31, "Wylow;3;&3&lNiesamowity Przedmiot Rybacki");
        this.misjeRybackie.put(32, "Oddaj;256;&6Okoni&3,&6Plotek");
        this.misjeRybackie.put(33, "Zabij;10;&6&lPodwodny Wladca");
        this.misjeRybackie.put(34, "Wylow;512;&6Sledz");
        this.misjeRybackie.put(35, "Wylow;512;&6Dorsz");
        this.misjeRybackie.put(36, "Wylow;512;&6Losos");
        this.misjeRybackie.put(37, "Wylow;512;&6Krasnopiorka");
        this.misjeRybackie.put(38, "Wylow;512;&6Czarny Dorsz");
        this.misjeRybackie.put(39, "Wylow;512;&6Dorady");
        this.misjeRybackie.put(40, "Sprzedaj;10000;&3ryb");
        this.misjeRybackie.put(41, "Wylow;5000;&3ryb");
        this.misjeRybackie.put(42, "Sprzedaj;15000;&3ryb");
        this.misjeRybackie.put(43, "Wylow;5;&3&lNiesamowity Przedmiot Rybacki");
        this.misjeRybackie.put(44, "Wylow;20;&6&lPodwodny Wladca");
    }

    public void loadExpWedka() {
        double wymaganyExp = 100.0;
        for (int i = 1; i <= 50; i++) {
            this.wymaganyExpWedki.put(i, wymaganyExp);
            wymaganyExp += 150;
        }
    }

    public void loadRybakDrops() {
        /*\
        Material.RAW_FISH, 1, (short) 1 - RAW SALMON
        Material.RAW_FISH, 1, (short) 2 - CLOWNFISH
        Material.RAW_FISH, 1, (short) 3 - PUFFERFISH
        Material.COOCKED_FISH, 1, (short) 1 - COOCKED SALMON
         */
        final ItemBuilder sledz = new ItemBuilder(Material.RAW_FISH);
        final ItemBuilder dorsz = new ItemBuilder(Material.RAW_FISH, 1, (short) 1);
        final ItemBuilder losos = new ItemBuilder(Material.RAW_FISH, 1, (short) 1);
        final ItemBuilder krasnopiorka = new ItemBuilder(Material.RAW_FISH, 1, (short) 2);
        final ItemBuilder dorszCzarny = new ItemBuilder(Material.COOKED_FISH, 1, (short) 1);
        final ItemBuilder dorada = new ItemBuilder(Material.RAW_FISH);
        final ItemBuilder cierniczek = new ItemBuilder(Material.COOKED_FISH);
        final ItemBuilder fladra = new ItemBuilder(Material.RAW_FISH, 1, (short) 3);
        final ItemBuilder karas = new ItemBuilder(Material.RAW_FISH, 1, (short) 1);
        final ItemBuilder karp = new ItemBuilder(Material.COOKED_FISH);
        final ItemBuilder leszcz = new ItemBuilder(Material.COOKED_FISH, 1, (short) 1);
        final ItemBuilder makrela = new ItemBuilder(Material.COOKED_FISH);
        final ItemBuilder mintaj = new ItemBuilder(Material.COOKED_FISH);
        final ItemBuilder okon = new ItemBuilder(Material.RAW_FISH, 1, (short) 3);
        final ItemBuilder plotka = new ItemBuilder(Material.RAW_FISH, 1, (short) 1);
        final ItemBuilder nies = new ItemBuilder(Material.DIAMOND_BLOCK);


        final List<String> lore = new ArrayList<>();

        lore.add("&8&oChyba &8&n&orybak&r &8&otego potrzebuje");

        rybakDrops.add(1, karas.setName("&6Karas").setLore(lore).hideFlag().toItemStack());

        rybakDrops.add(0.06666, sledz.setName("&6Sledz").setLore(lore).hideFlag().toItemStack());
        rybakDrops.add(0.06666, dorsz.setName("&6Dorsz").setLore(lore).hideFlag().toItemStack());
        rybakDrops.add(0.06666, losos.setName("&6Losos").setLore(lore).hideFlag().toItemStack());
        rybakDrops.add(0.06666, krasnopiorka.setName("&6Krasnopiorka").setLore(lore).hideFlag().toItemStack());
        rybakDrops.add(0.06666, dorszCzarny.setName("&6Dorsz Czarny").setLore(lore).hideFlag().toItemStack());
        rybakDrops.add(0.06666, dorada.setName("&6Dorada").setLore(lore).hideFlag().toItemStack());
        rybakDrops.add(0.06666, cierniczek.setName("&6Cierniczek").setLore(lore).hideFlag().toItemStack());
        rybakDrops.add(0.06666, fladra.setName("&6Fladra").setLore(lore).hideFlag().toItemStack());
        rybakDrops.add(0.06666, karas.setName("&6Karas").setLore(lore).hideFlag().toItemStack());
        rybakDrops.add(0.06666, karp.setName("&6Karp").setLore(lore).hideFlag().toItemStack());
        rybakDrops.add(0.06666, leszcz.setName("&6Leszcz").setLore(lore).hideFlag().toItemStack());
        rybakDrops.add(0.06666, makrela.setName("&6Makrela").setLore(lore).hideFlag().toItemStack());
        rybakDrops.add(0.06666, mintaj.setName("&6Mintaj").setLore(lore).hideFlag().toItemStack());
        rybakDrops.add(0.06666, okon.setName("&6Okon").setLore(lore).hideFlag().toItemStack());
        rybakDrops.add(0.06666, plotka.setName("&6Plotka").setLore(lore).hideFlag().toItemStack());
        rybakDrops.add(0.001, nies.setName("&b&lNiesamowity Przedmiot Rybacki").addGlowing().toItemStack());
    }

    public void loadRybakMobs() {

        rybakMobs.add(0.5, "nurek");
        rybakMobs.add(0.5, "wladca");

    }

    public ItemStack getDrop() {
        return this.rybakDrops.next();
    }

    public String getMob() {
        return this.rybakMobs.next();
    }

    public ItemStack givePlayerRod(final Player player) {
        final ItemBuilder wedkaGracza = new ItemBuilder(Material.FISHING_ROD);

        lore.clear();

        lore.add("&bWlasciciel: &f" + player.getName());
        lore.add("&bPoziom: &f0");
        lore.add("&bExp: &f0&b/&f" + this.wymaganyExpWedki.get(1));
        lore.add("&bWylowione ryby: &f0");
        lore.add(" ");
        lore.add("&f&lBonusy");
        lore.add("&8- &bSzansa na podwojne wylowienie: &f0.05%");
        lore.add("&8- &bSzansa na skrzynie rybaka: &f0.005%");
        lore.add("&8- &bSzansa na wylowienie podwodnego stworzenia: &f0.1%");

        wedkaGracza.setName("&6Wedka").setLore(lore);

        return wedkaGracza.toItemStack();
    }

    public ItemStack getChest() {
        final ItemBuilder chest = new ItemBuilder(Material.CHEST);
        final List<String> lore = new ArrayList<>();

        lore.add("&8&n&oKliknij&r &8&ozeby otworzyc skrzynie i otrzymac przedmioty");

        chest.setName("&a&lSkrzynia Rybaka").setLore(lore).hideFlag();

        return chest.toItemStack();
    }

    public void runFishAnimation(final Player player, final Entity entity) {
        double pushX = player.getLocation().getDirection().normalize().getX() * -2;
        double pushY = player.getLocation().getDirection().normalize().getY() * -2;
        double pushZ = player.getLocation().getDirection().normalize().getZ() * -2;

        Vector push = new Vector(pushX, pushY, pushZ);

        entity.setVelocity(push);
    }

    public void spawnNurekGlebinowy(final Player player, final Location location) {
        final LivingEntity entity = (LivingEntity) Bukkit.getWorld(player.getWorld().getName()).spawnEntity(location, EntityType.ZOMBIE);
        final double maxHealth = 150000;
        entity.setMaxHealth(maxHealth);
        entity.setHealth(maxHealth);
        entity.setCustomName(Utils.format("&3Nurek Glebinowy"));
        player.sendMessage(Utils.format(Utils.RYBAK + "&aPomyslnie wylowiles &6" + entity.getCustomName()));
        entity.setCustomName(Utils.format("&3Nurek Glebinowy &c" + (int) entity.getHealth() + "&7/&c" + (int) entity.getMaxHealth() + " ❤"));
        runFishAnimation(player, entity);

        EntityEquipment entityInv = entity.getEquipment();
        final ItemBuilder helm = new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3);
        final ItemBuilder klata = new ItemBuilder(Material.LEATHER_CHESTPLATE);
        final ItemBuilder spodnie = new ItemBuilder(Material.LEATHER_LEGGINGS);
        final ItemBuilder buty = new ItemBuilder(Material.LEATHER_BOOTS);
        final ItemBuilder miecz = new ItemBuilder(Material.GOLD_SWORD);

        helm.setSkullOwnerByURL("470342fb-1116-3032-9b24-6f674e1e52b0", "Glebinowy_nurek",
                "eyJ0aW1lc3RhbXAiOjE0OTE2NjU5Nzc1NDAsInByb2ZpbGVJZCI6IjdkYTJhYjNhOTNj" +
                        "YTQ4ZWU4MzA0OGFmYzNiODBlNjhlIiwicHJvZmlsZU5hbWUiOiJHb2xkYXBmZWwiLCJzaWduYXR1cmVSZXF1aXJlZCI6dHJ1ZSwidGV4dH" +
                        "VyZXMiOnsiU0tJTiI6eyJ1cmwiOiJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzg1NjkyNDUzNzFlZGZmNjNhMjY5MTNhOTcyZjJj" +
                        "NDRmYmU0MWY3NWU0MjY2OGE3MjU5OTc1OWNmNDUyZjNhIn19fQ==",
                "SoovlSRBotT4WJa/7ta9ecVEyV8iEel7Ln3qtbRESHpiWwNwqmiPpV8vtjtL9YB3c2D+z/0Xao2BVaBJICMBB5UeS7MgxV6Pp1dqZ0uuxrsS2H4rvceQzX" +
                        "s7lphLvxIveVu4z7VBZL/sEj2pAcIDCqvb5T2F9Fi2PMROBcDNZI/D5f088MbCZ1pgyi5DZWRhAGLwFwAPu6j7iyo+rq9LKWsOP7QPXmzmtuj545duhc2yEMZ" +
                        "RLYyJY6nVM/PrwtqIoUB6r6tm0ETLmL/H8idoauqwNwZfOFsFVgxKeZWHHr6xCz0vTNk/vs43k5ZF8szzdCmHeKGffe9YfO6ftXwMuR/KLVv1YaYsNkSD3VcFuGa" +
                        "IJl17VmvxLlo01KfZqYfZoKEK4YLR2sqGSLNwcf46UWlXtawXf/AscPy6V38+qJYTnQHDxa7wVbzUQaANYxz42XwPxPDO2fTWlPkw3Y1WL4mRZ3I10QwiXgPh4CRpL" +
                        "1UsVvNcljZncanI0W8So3b9S9fsEWce7vipQvMZTCjH9p7lC1B8orRxNfwx9lZ+94bpOkHcD+JI1l+TGS4Z2gRSF/+CBLGeU71XROGYX1Ocvc/gfpMofUVAWQxMjGkW5" +
                        "wXJmSJiyLpD7TOd098ll8nsi7vRrCGPoYBFrSA6vJyDIbaUeQiDmlA8euKpOtuYeAw=");
        player.getInventory().addItem(helm.toItemStack());
        klata.setLeatherArmorColorHEX(19, 46, 110).addGlowing();
        spodnie.setLeatherArmorColorHEX(19, 46, 110).addGlowing();
        buty.setLeatherArmorColorHEX(19, 46, 110).addGlowing();
        miecz.addGlowing();

        entityInv.setHelmet(helm.toItemStack());
        entityInv.setChestplate(klata.toItemStack());
        entityInv.setLeggings(spodnie.toItemStack());
        entityInv.setBoots(buty.toItemStack());
        entityInv.setItemInHand(miecz.toItemStack());

        entityInv.setHelmetDropChance(0f);
        entityInv.setChestplateDropChance(0f);
        entityInv.setLeggingsDropChance(0f);
        entityInv.setBootsDropChance(0f);
        entityInv.setItemInHandDropChance(0f);


    }

    public void setMagmaCubeSize(final Entity entity, final int size) {
        net.minecraft.server.v1_8_R3.Entity nmsEntity = ((CraftEntity) entity).getHandle();

        NBTTagCompound tag = new NBTTagCompound();

        nmsEntity.c(tag);

        tag.setInt("Size", size);

        EntityLiving el = (EntityLiving) nmsEntity;
        el.a(tag);
    }

    public void setNoAi(final Entity entity) {
        net.minecraft.server.v1_8_R3.Entity nmsEntity = ((CraftEntity) entity).getHandle();

        NBTTagCompound tag = new NBTTagCompound();

        nmsEntity.c(tag);

        tag.setBoolean("NoAI", true);

        EntityLiving el = (EntityLiving) nmsEntity;
        el.a(tag);
    }

    public void spawnPodwodnyWladca(final Player player, final Location location) {
        final LivingEntity entity = (LivingEntity) Bukkit.getWorld(player.getWorld().getName()).spawnEntity(location, EntityType.GUARDIAN);
        final LivingEntity entityPassenger = (LivingEntity) Bukkit.getWorld(player.getWorld().getName()).spawnEntity(location, EntityType.ZOMBIE);
        setMagmaCubeSize(entityPassenger, 1);
        setNoAi(entityPassenger);
        entity.setPassenger(entityPassenger);
        entityPassenger.setCustomNameVisible(true);
        entityPassenger.setCustomName(Utils.format("&6&lPodwodny Wladca"));
        runFishAnimation(player, entity);
        player.sendMessage(Utils.format(Utils.RYBAK + "&aPomyslnie wylowiles &6" + entityPassenger.getCustomName()));
        final double maxHealth = 500000;
        entity.setMaxHealth(maxHealth);
        entity.setHealth(maxHealth);
        entityPassenger.setMaxHealth(maxHealth);
        entityPassenger.setHealth(maxHealth);
        entityPassenger.setCustomName(Utils.format("&6&lPodwodny Wladca &c" + (int) entityPassenger.getHealth() + "&7/&c" + (int) entityPassenger.getMaxHealth() + " ❤"));

        final ItemBuilder helm = new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3);
        final ItemBuilder klata = new ItemBuilder(Material.LEATHER_CHESTPLATE);
        final ItemBuilder spodnie = new ItemBuilder(Material.LEATHER_LEGGINGS);
        final ItemBuilder buty = new ItemBuilder(Material.DIAMOND_CHESTPLATE);
        final ItemBuilder topor = new ItemBuilder(Material.GOLD_AXE);

        helm.setSkullOwnerByURL("6e315266-5e72-3f8e-8119-3c76b40759f3", "skina5d6712e",
                "eyJ0aW1lc3RhbXAiOjE0OTE5NTE5MjE5NTMsInByb2ZpbGVJZCI6IjQzYTgzNzNkNjQyOTQ1M" +
                        "TBhOWFhYjMwZjViM2NlYmIzIiwicHJvZmlsZU5hbWUiOiJTa3VsbENsaWVudFNraW42Iiwic2" +
                        "lnbmF0dXJlUmVxdWlyZWQiOnRydWUsInRleHR1cmVzIjp7IlNLSU4iOnsidXJsIjoiaHR0cDo" +
                        "vL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS8xNjkwOTkzNmI1ZmExOTI0ZjkxZDFk" +
                        "ZTNlODcwZWQ3MTQwYmY0MzVjMWExYTU1ZGE1NzNmYzNhODhlYmQ1YmVjIn19fQ==",
                "mjdFL9WeVt9RTTFRpk5MD0tkU0yboelX5VEZV2gAYPEnQLGmeVWP5q9tUedIC1y" +
                        "XFd12qY8R843quCC27bHODR1Fb+nOUElaw/xd3uQ1DEiROvAbMI+Ua5El4vlAUmDWO" +
                        "h+kMD386t6VsmgMI4CdOOLjKc6uoFYzKS7xTzPkCrSdnU3C//Erv4QcKCwqzOXcTAsF" +
                        "vWqUXWfc23jJmxlHI2jYqhpN3vWllhdt5A16g3y6F+x2rXDSCqZTyaxITRYeEsKNix2" +
                        "yVCtSa92vJdTCfgUOtqN2UDft5/eEWGsNUvi81AuojWha3ht8DhksJCcZFLTahg30B/" +
                        "1YKVyYZTsgpFF1tQxSpd/RQnGKAEwWAEgxAdQvFu0CmJZdK3y4FMX6TB7lcp+hSXF/G" +
                        "KDPVfprVSeS2/DvGSI4AJQvIQADbeF0kiqBbVG+dy3c7n7OvvDqXjUs4vKoGcLAirrS" +
                        "dpSKvxtGGLVr33MDciTwuTyrCI5+Z+wFHydW3WXuzCi+v3Hu79SkkGMazzzCmlXf9J5" +
                        "JL+3LAIy4uVcSfYjl72zpyLEbv6i4oYJGRihqY6X9v4LvXnKhsWnG0w7Uk4TMnRl8EM" +
                        "+e5LpXK41kj0OpOT2f2pe2PnOPQyLJzBoA3Q+UDSaVneAm2R2DjHkg+ou3uL9raUkk1" +
                        "2qEHGHDk3N+WXyA/4o=");

        klata.setLeatherArmorColorHEX(92, 25, 23);
        spodnie.setLeatherArmorColorHEX(10, 10, 10);
        buty.addGlowing();
        topor.addGlowing().addEnchant(Enchantment.KNOCKBACK, 4);


        EntityEquipment eq = entityPassenger.getEquipment();

        eq.setHelmet(helm.toItemStack());
        eq.setChestplate(klata.toItemStack());
        eq.setLeggings(spodnie.toItemStack());
        eq.setBoots(buty.toItemStack());
        eq.setItemInHand(topor.toItemStack());

        eq.setHelmetDropChance(0f);
        eq.setChestplateDropChance(0f);
        eq.setLeggingsDropChance(0f);
        eq.setBootsDropChance(0f);
        eq.setItemInHandDropChance(0f);


    }


    public void addStatsToRod(final Player player, final double fishExp) {

        if (player.getItemInHand().getType() != Material.FISHING_ROD) {
            return;
        }

        if (player.getItemInHand().getItemMeta().getDisplayName() == null || player.getItemInHand().getItemMeta().getLore() == null) {
            return;
        }

        final ItemStack playerRod = player.getItemInHand();
        final ItemMeta im = playerRod.getItemMeta();
        final List<String> lore = im.getLore();

        int rodLvl = Integer.parseInt(Utils.removeColor(lore.get(1)).replace("Poziom: ", "").trim());
        double rodExp = Double.parseDouble(Utils.removeColor(lore.get(2).replace("Exp: ", "").substring(0, Utils.removeColor(lore.get(2)).indexOf('/') + 1)));
        int fished = Integer.parseInt(Utils.removeColor(lore.get(3)).replace("Wylowione ryby: ", "").trim());
        if (rodLvl == 50) {
            lore.set(3, "&bWylowione ryby: &f" + (fished + 1));
            im.setLore(Utils.format(lore));
            playerRod.setItemMeta(im);
            rpgcore.getPlayerManager().updatePlayerOsRybak(player.getUniqueId(), fished + 1);
            return;
        }

        lore.set(2, "&bExp: &f" + (rodExp + fishExp) + "&b/&f" + this.wymaganyExpWedki.get(rodLvl + 1));
        lore.set(3, "&bWylowione ryby: &f" + (fished + 1));
        rodExp += fishExp;

        if (rodExp >= this.wymaganyExpWedki.get(rodLvl + 1)) {
            rodLvl += 1;
            rodExp = 0.0;
            if (rodLvl == 50) {
                lore.set(1, "&bPoziom: &4&lMAX");
                lore.set(2, "&bExp: &4&lMAX");
            } else {
                lore.set(1, "&bPoziom: &f" + rodLvl);
                lore.set(2, "&bExp: &f" + rodExp + "&b/&f" + this.wymaganyExpWedki.get(rodLvl + 1));
            }
            double doubleDrop = Double.parseDouble(Utils.removeColor(lore.get(6)).replace("-", "").replace("Szansa na podwojne wylowienie:", "").replace(" ", "").replace("%", "").trim());
            double caseDrop = Double.parseDouble(Utils.removeColor(lore.get(7)).replace("-", "").replace("Szansa na skrzynie rybaka:", "").replace(" ", "").replace("%", "").trim());
            double mobChance = Double.parseDouble(Utils.removeColor(lore.get(8)).replace("-", "").replace("Szansa na wylowienie podwodnego stworzenia:", "").replace(" ", "").replace("%", "").trim());

            doubleDrop += 0.75;
            caseDrop += 0.25;
            if (rodLvl - 1 == 1) {
                mobChance += 0.3;
            } else {
                mobChance += 0.5;
            }

            lore.set(6, "&8- &bSzansa na podwojne wylowienie: &f" + Utils.wedkaFormat.format(doubleDrop) + "%");
            lore.set(7, "&8- &bSzansa na skrzynie rybaka: &f" + Utils.wedkaFormat.format(caseDrop) + "%");
            lore.set(8, "&8- &bSzansa na wylowienie podwodnego stworzenia: &f" + Utils.wedkaFormat.format(mobChance) + "%");

            if (im.getEnchantLevel(Enchantment.LURE) < 5) {
                if (rodLvl % 5 == 0) {
                    im.addEnchant(Enchantment.LURE, playerRod.getEnchantmentLevel(Enchantment.LURE) + 1, true);
                }
            }
            if (im.getEnchantLevel(Enchantment.LUCK) < 5) {
                if (rodLvl % 10 == 0) {
                    im.addEnchant(Enchantment.LUCK, playerRod.getEnchantmentLevel(Enchantment.LUCK) + 1, true);
                }
            }
            player.sendMessage(Utils.format(Utils.RYBAK + " &7Twoja wedka zyskala nowe moce"));
        }

        im.setLore(Utils.format(lore));
        playerRod.setItemMeta(im);
        rpgcore.getPlayerManager().updatePlayerOsRybak(player.getUniqueId(), fished + 1);
    }

    public ItemStack createCurrentMissionItem(final UUID uuid, final int misjaNr) {
        final ItemBuilder misjeItem = new ItemBuilder(Material.BOOK_AND_QUILL);

        final String[] misja = this.misjeRybackie.get(misjaNr).split(";");

        lore.clear();
        lore.add(" ");
        lore.add("&f&lMisja:");
        lore.add("&3" + misja[0] + " &f" + misja[1] + " " + misja[2]);
        lore.add(" ");
        lore.add("&f&lPostep:");
        lore.add("&b" + this.rybakPostep.get(uuid) + " &f/&b " + misja[1] + " &8(&b" + (((double) rybakPostep.get(uuid) / Integer.parseInt(misja[1])) * 100) + "%&8)");

        return misjeItem.setName("&c&lMisja #" + (misjaNr + 1)).setLore(lore).toItemStack();
    }

    @Getter
    public String getPlayerRybakMisje(final UUID uuid) {
        return this.ryabkMisje.get(uuid);
    }

    @Setter
    public void setPlayerRybakMisje(final UUID uuid, final String noweMisje) {
        this.ryabkMisje.put(uuid, noweMisje);
    }

    @Setter
    public void updatePlayerRybakMisje(final UUID uuid, final String noweMisje) {
        this.ryabkMisje.replace(uuid, noweMisje);
    }

    public boolean isInRybakMisjeMap(final UUID uuid) {
        return this.ryabkMisje.containsKey(uuid);
    }

    @Getter
    public int getPlayerCurrentMission(final UUID uuid) {
        final String[] missions = this.ryabkMisje.get(uuid).split(",");

        int i = 0;
        while (missions[i].equalsIgnoreCase("true")) {
            i++;
        }
        return i + 1;
    }


    @Getter
    public double getPlayerRybakSredniDMG(final UUID uuid) {
        return this.ryabkSrednieDMG.get(uuid);
    }

    @Setter
    public void setPlayerRybakSredniDMG(final UUID uuid, final double nowyDMG) {
        this.ryabkSrednieDMG.put(uuid, nowyDMG);
    }

    @Setter
    public void updatePlayerRybakSredniDMG(final UUID uuid, final double nowyDMG) {
        this.ryabkSrednieDMG.replace(uuid, nowyDMG);
    }


    @Getter
    public double getPlayerRybakSredniDef(final UUID uuid) {
        return this.ryabkSredniDef.get(uuid);
    }

    @Setter
    public void setPlayerRybakSredniDef(final UUID uuid, final double nowyDef) {
        this.ryabkSredniDef.put(uuid, nowyDef);
    }

    @Setter
    public void updatePlayerRybakSredniDef(final UUID uuid, final double nowyDef) {
        this.ryabkSredniDef.replace(uuid, nowyDef);
    }


    @Getter
    public double getPlayerRybakBlok(final UUID uuid) {
        return this.ryabkBlok.get(uuid);
    }

    @Setter
    public void setPlayerRybakBlok(final UUID uuid, final double nowyBlok) {
        this.ryabkBlok.put(uuid, nowyBlok);
    }

    @Setter
    public void updatePlayerRybakBlok(final UUID uuid, final double nowyBlok) {
        this.ryabkBlok.replace(uuid, nowyBlok);
    }


    @Getter
    public double getPlayerRybakDodatkowyDMG(final UUID uuid) {
        return this.ryabkDodatkowyDMG.get(uuid);
    }

    @Setter
    public void setPlayerRybakDodatkowyDMG(final UUID uuid, final double nowyDodatkowyDMG) {
        this.ryabkDodatkowyDMG.put(uuid, nowyDodatkowyDMG);
    }

    @Setter
    public void updatePlayerRybakDodatowyDMG(final UUID uuid, final double nowyDodatkowyDMG) {
        this.ryabkDodatkowyDMG.replace(uuid, nowyDodatkowyDMG);
    }

    @Getter
    public int getPlayerPostep(final UUID uuid) {
        return this.rybakPostep.get(uuid);
    }

    @Setter
    public void setPlayerPostep(final UUID uuid, final int postep) {
        this.rybakPostep.put(uuid, postep);
    }

    @Setter
    public void updatePlayerPostep(final UUID uuid, final int nowyPostep) {
        this.rybakPostep.replace(uuid, this.rybakPostep.get(uuid) + nowyPostep);
    }
}
