package rpg.rpgcore.npc.rybak;

import com.google.common.collect.ImmutableSet;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.*;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.chests.Items;
import rpg.rpgcore.npc.rybak.enums.MlodszyRybakMissions;
import rpg.rpgcore.npc.rybak.enums.StaruszekMissions;
import rpg.rpgcore.npc.rybak.objects.MlodszyRybakUser;
import rpg.rpgcore.npc.rybak.objects.RybakUser;
import rpg.rpgcore.npc.rybak.objects.StaruszekUser;
import rpg.rpgcore.utils.ChanceHelper;
import rpg.rpgcore.utils.DoubleUtils;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.npc.RybakItems;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
public class RybakNPC {

    private final RPGCORE rpgcore;
    private final Map<UUID, RybakUser> usersMap;
    private final Map<Integer, ArmorStand> armorStandMap;
    private final Map<UUID, Integer> failedAttemptMap = new HashMap<>();
    private final Map<UUID, Integer> fishingCount = new HashMap<>();
    private final Map<UUID, Integer> taskMap = new HashMap<>();
    private final Map<UUID, Map<String, Float>> locationMapYaw = new HashMap<>();
    private final Map<UUID, Map<String, Float>> locationMapPitch = new HashMap<>();
    @Getter
    private final Map<UUID, Integer> antiFishMap = new HashMap<>();
    private final List<UUID> passed = new ArrayList<>();

    public RybakNPC(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
        this.usersMap = rpgcore.getMongoManager().loadAllRybak();
        this.spawnArmorStans();
        this.armorStandMap = rpgcore.getMongoManager().loadAllRybakArmorStands();

        this.initWyspa1Drops();
        this.initWyspa2Drops();
    }


    // ========================================= SPAWN ========================================= //

    private void spawnArmorStans() {
        final ArmorStand as = (ArmorStand) Bukkit.getWorld("world").spawnEntity(new Location(Bukkit.getWorld("world"), -98.5, 97, -155.5), EntityType.ARMOR_STAND);
        as.setGravity(false);
        for (ArmorStand stand : as.getNearbyEntities(12, 12, 12).stream().filter(entity -> entity instanceof ArmorStand && entity.getType().equals(EntityType.ARMOR_STAND)).map(entity -> (ArmorStand) entity).collect(Collectors.toList())) {
            stand.remove();
        }
        as.remove();
    }

    public void clickWloczykij(final Player player) {
        if (player.getItemInHand().equals(RybakItems.I4.getItemStack())) {
            player.sendMessage(Utils.format("&3&lWloczykij &8>> &7Witaj &b" + player.getName() + "&7!"));
            rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> player.sendMessage(Utils.format("&3&lWloczykij &8>> &eRybacka Wyspa?!")), 40L);
            rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> player.sendMessage(Utils.format("&3&lWloczykij &8>> &ePewnie&7! To tuz za rogiem")), 80L);
            rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> {
                player.teleport(new Location(Bukkit.getWorld("Rybak"), -10.5, 157, -133.5, -90, 0));
                player.sendMessage(Utils.format("&3&lWloczykij &8>> &ePuff!"));
            }, 120L);
            return;
        }
        if (player.getInventory().containsAtLeast(RybakItems.I1.getItemStack(), 5) && player.getInventory().containsAtLeast(RybakItems.I2.getItemStack(), 3) && player.getInventory().containsAtLeast(RybakItems.I3.getItemStack(), 2)) {
            player.getInventory().removeItem(new ItemBuilder(RybakItems.I1.getItemStack().clone()).setAmount(5).toItemStack(),
                    new ItemBuilder(RybakItems.I2.getItemStack().clone()).setAmount(3).toItemStack(),
                    new ItemBuilder(RybakItems.I3.getItemStack().clone()).setAmount(2).toItemStack());
            player.sendMessage(Utils.format("&3&lWloczykij &8>> &7Dziekuje za pomoc!"));
            rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> player.sendMessage(Utils.format("&3&lWloczykij &8>> &7Teraz jestesmy gotowi!")), 40L);
            rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> player.sendMessage(Utils.format("&3&lWloczykij &8>> &7W sumie...!")), 80L);
            rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> player.sendMessage(Utils.format("&3&lWloczykij &8>> &7Prosze, wez ja!")), 120L);
            rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> {
                player.getInventory().addItem(RybakItems.I4.getItemStack().clone());
                player.sendMessage(Utils.format("&3&lWloczykij &8>> &7Ja i tak jestem juz za stary na te zabawe!"));
            }, 160L);
            return;
        }
        if (!this.find(player.getUniqueId()).isDialog()) {
            this.find(player.getUniqueId()).setDialog(true);
            player.sendMessage(Utils.format("&3&lWloczykij &8>> &7Witaj, &b" + player.getName() + "&7!"));
            rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> player.sendMessage(Utils.format("&3&lWloczykij &8>> &7Slyszalem, ze szukasz przygod!")), 40L);
            rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> player.sendMessage(Utils.format("&3&lWloczykij &8>> &7Swietnie sie sklada!")), 80L);
            rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> player.sendMessage(Utils.format("&3&lWloczykij &8>> &7Moge zabrac Cie, na taka jedna &ewyspe &7pelna rybackich zadan i tajemnic!")), 120L);
            rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> player.sendMessage(Utils.format("&3&lWloczykij &8>> &7Poznasz tam kilku moich &aprzyjaciol&7!")), 160L);
            rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> player.sendMessage(Utils.format("&3&lWloczykij &8>> &7Ale...")), 200L);
            rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> player.sendMessage(Utils.format("&3&lWloczykij &8>> &7Zeby sie tam dostac potrzebujemy mojej &6Starej Lodki.")), 240L);
            rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> player.sendMessage(Utils.format("&3&lWloczykij &8>> &7Niestety, po ostatniej podrozy uderzylem w &8Skale &7i lodka rozbila sie na kawalki")), 280L);
            rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> player.sendMessage(Utils.format("&3&lWloczykij &8>> &7Troche to dziwne...")), 320L);
            rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> player.sendMessage(Utils.format("&3&lWloczykij &8>> &7W moim swiecie, Lodki zazwyczaj tona po takiej interakcji")), 360L);
            rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> player.sendMessage(Utils.format("&3&lWloczykij &8>> &7Ale no coz, najwidoczniej w tym Swiecie jest troche inaczej")), 400L);
            rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> player.sendMessage(Utils.format("&3&lWloczykij &8>> &7Do sedna...")), 440L);
            rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> player.sendMessage(Utils.format("&3&lWloczykij &8>> &7Zeby naprawic swoja lodke potrzebuje:")), 480L);
            rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> {
                player.sendMessage(Utils.format("&3&lWloczykij &8>> &8- &e5 &6Zpruchnialych Desek"));
                player.sendMessage(Utils.format("&3&lWloczykij &8>> &8- &e3 Podstawy Masztu"));
                player.sendMessage(Utils.format("&3&lWloczykij &8>> &8- &e2 &fPodartych Masztow"));
            }, 520L);
            rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> player.sendMessage(Utils.format("&3&lWloczykij &8>> &7Przynies mi te rzeczy i bedziemy gotowi do drogi")), 560L);
            rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> player.sendMessage(Utils.format("&3&lWloczykij &8>> &7Znajdziesz je w tym jeziorku za mna")), 580L);
            return;
        }
        player.sendMessage(Utils.format("&3&lWloczykij &8>> &7Zeby naprawic swoja lodke potrzebuje:"));
        player.sendMessage(Utils.format("&3&lWloczykij &8>> &8- &e5 &6Zpruchnialych Desek"));
        player.sendMessage(Utils.format("&3&lWloczykij &8>> &8- &e3 Podstawy Masztu"));
        player.sendMessage(Utils.format("&3&lWloczykij &8>> &8- &e2 &fPodartych Masztow"));
        player.sendMessage(Utils.format("&3&lWloczykij &8>> &7Znajdziesz je w tym jeziorku za mna"));
    }

    // ========================================= STARUSZEK - WYSPA 1 ========================================= //

    private final Set<Items> wyspa1Drops = new HashSet<>();

    private void initWyspa1Drops() {
        this.wyspa1Drops.add(new Items("1", 3, RybakItems.I5.getItemStack(), 1));
        this.wyspa1Drops.add(new Items("2", 8.5, RybakItems.I10.getItemStack(), 1));
        this.wyspa1Drops.add(new Items("3", 11, RybakItems.I14.getItemStack(), 1));
        this.wyspa1Drops.add(new Items("4", 15.5, RybakItems.I9.getItemStack(), 1));
        this.wyspa1Drops.add(new Items("6", 27.5, RybakItems.I8.getItemStack(), 1));
        this.wyspa1Drops.add(new Items("7", 37.5, RybakItems.I7.getItemStack(), 1));
        this.wyspa1Drops.add(new Items("8", 65, RybakItems.I6.getItemStack(), 1));
    }

    public ItemStack getWyspa1Drop(final Player player) {
        final StaruszekUser user = this.find(player.getUniqueId()).getStaruszekUser();
        Set<Items> playerDrop = new HashSet<>(this.wyspa1Drops);
        if (user.getMission() == 11) playerDrop.add(new Items("5", 20, RybakItems.I11.getItemStack(), 1));
        if (user.getMission() == 12) playerDrop.add(new Items("5", 20, RybakItems.I12.getItemStack(), 1));
        if (user.getMission() == 13) playerDrop.add(new Items("5", 20, RybakItems.I13.getItemStack(), 1));

        List<Items> toSort = new ArrayList<>(playerDrop);

        toSort = toSort.stream().sorted(Comparator.comparingInt(name -> Integer.parseInt(name.getName()))).collect(Collectors.toList());

        playerDrop = ImmutableSet.copyOf(toSort);
        for (Items item : playerDrop) {
            if (ChanceHelper.getChance(item.getChance())) {
                final ItemStack reward = item.getRewardItem();
                reward.setAmount(item.getAmount());
                return reward.clone();
            }
        }
        return null;
    }

    private void sendDelayedMessage(final Player player, final String message, final long time) {
        rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> player.sendMessage(Utils.format(message)), time);
    }

    public void onClickStaruszek(final Player player) {
        final StaruszekUser staruszekUser = this.find(player.getUniqueId()).getStaruszekUser();

        if (!staruszekUser.isReceivedRod()) {
            player.sendMessage(Utils.format("&6&lStaruszek &8>> &7Witaj &e" + player.getName() + "&7!"));
            this.sendDelayedMessage(player, "&6&lStaruszek &8>> &7W koncu znalazl sie ktos, kto mnie wyslucha.", 40L);
            this.sendDelayedMessage(player, "&6&lStaruszek &8>> &7Jestem bardzo samotny, poniewaz prawie nikt tu nie zaglada...", 80L);
            this.sendDelayedMessage(player, "&6&lStaruszek &8>> &7Poniewaz utknalem tu sam to jedynym moim zajeciem bylo lowienie ryb", 120L);
            this.sendDelayedMessage(player, "&6&lStaruszek &8>> &7Jednak po pewnym czasie okazalo sie, ze dzieje sie tutaj cos dziwnego...", 160L);
            this.sendDelayedMessage(player, "&6&lStaruszek &8>> &7Chodza sluchy, ze w &2&lDzikiej Dzungli &7grasuja &3podwodne stworzenia", 200L);
            this.sendDelayedMessage(player, "&6&lStaruszek &8>> &7Jednak zanim tam trafisz, potrzebuje twojej pomocy.", 240L);
            this.sendDelayedMessage(player, "&6&lStaruszek &8>> &7Wez ta &6Stara Wedke &7i bierz sie do roboty", 240L);
            rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> player.getInventory().addItem(RybakItems.getStaraWedka(player.getName())), 280L);
            staruszekUser.setReceivedRod(true);
            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataRybak(player.getUniqueId(), this.find(player.getUniqueId())));
            return;
        }

        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&6&lStaruszek"));
        for (int i = 0; i < 27; i++) {
            gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName(" ").toItemStack());
        }

        for (int i = 1; i < StaruszekMissions.values().length + 1; i++) {
            if (staruszekUser.getMission() > i) {
                gui.setItem(i - 1, new ItemBuilder(Material.BOOK).setName("&7Misja #&c" + i).setLore(Arrays.asList(" ", "&a&lWYKONANO!")).addGlowing().toItemStack().clone());
            } else if (staruszekUser.getMission() == i) {
                gui.setItem(i - 1, Objects.requireNonNull(StaruszekMissions.getMissionById(i)).getMissionItem(staruszekUser.getProgress()).clone());
            } else {
                gui.setItem(i - 1, new ItemBuilder(Material.BARRIER).setName("&7Misja #&c" + i).setLore(Arrays.asList(" ", "&cWykonaj poprzednia misje, zeby odblokowac!")).addGlowing().toItemStack().clone());
            }
        }

        gui.setItem(26, new ItemBuilder(Material.EMERALD).setName("&aSklep").addGlowing().toItemStack());

        player.openInventory(gui);
    }

    public void openStaruszekShop(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 9, Utils.format("&6&lStaruszek &8>> &aSklep"));

        for (int i = 0; i < gui.getSize(); i++) gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 5).setName(" ").toItemStack());

        gui.setItem(1, this.addPrice(RybakItems.I6.getItemStack(), 11250));
        gui.setItem(2, this.addPrice(RybakItems.I7.getItemStack(), 14625));
        gui.setItem(3, this.addPrice(RybakItems.I8.getItemStack(), 18750));
        gui.setItem(5, this.addPrice(RybakItems.I9.getItemStack(), 26250));
        gui.setItem(6, this.addPrice(RybakItems.I10.getItemStack(), 33750));
        gui.setItem(7, this.addPrice(RybakItems.I14.getItemStack(), 50000));

        player.openInventory(gui);
    }

    private ItemStack addPrice(final ItemStack is, final int price) {
        return new ItemBuilder(is.clone()).addLoreLine(" ", "&7Cena: &6" + Utils.spaceNumber(price) + "&2$&7/szt.")
                .addTagString("itemName", Utils.removeColor(is.getItemMeta().getDisplayName().replaceAll(" ", "-")))
                .addTagInt("price", price).toItemStack().clone();
    }

    public void onClickPrzyjaciel(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 18, Utils.format("&6&lPrzyjaciel"));

        for (int i = 0; i < gui.getSize(); i++) gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName(" ").toItemStack());

        gui.setItem(1, new ItemBuilder(RybakItems.I6.getItemStack().clone()).addLoreLine(" ", "&7Kliknij, zeby zestackowac!").toItemStack());
        gui.setItem(2, new ItemBuilder(RybakItems.I7.getItemStack().clone()).addLoreLine(" ", "&7Kliknij, zeby zestackowac!").toItemStack());
        gui.setItem(3, new ItemBuilder(RybakItems.I8.getItemStack().clone()).addLoreLine(" ", "&7Kliknij, zeby zestackowac!").toItemStack());
        gui.setItem(5, new ItemBuilder(RybakItems.I9.getItemStack().clone()).addLoreLine(" ", "&7Kliknij, zeby zestackowac!").toItemStack());
        gui.setItem(6, new ItemBuilder(RybakItems.I10.getItemStack().clone()).addLoreLine(" ", "&7Kliknij, zeby zestackowac!").toItemStack());
        gui.setItem(7, new ItemBuilder(RybakItems.I14.getItemStack().clone()).addLoreLine(" ", "&7Kliknij, zeby zestackowac!").toItemStack());

        gui.setItem(10, new ItemBuilder(RybakItems.I6.getItemStack().clone()).setName(RybakItems.I6.getItemStack().clone().getItemMeta().getDisplayName() + " &7(x64)").addLoreLine(" ", "&7Kliknij, zeby rozstackowac!").toItemStack());
        gui.setItem(11, new ItemBuilder(RybakItems.I7.getItemStack().clone()).setName(RybakItems.I7.getItemStack().clone().getItemMeta().getDisplayName() + " &7(x64)").addLoreLine(" ", "&7Kliknij, zeby rozstackowac!").toItemStack());
        gui.setItem(12, new ItemBuilder(RybakItems.I8.getItemStack().clone()).setName(RybakItems.I8.getItemStack().clone().getItemMeta().getDisplayName() + " &7(x64)").addLoreLine(" ", "&7Kliknij, zeby rozstackowac!").toItemStack());
        gui.setItem(14, new ItemBuilder(RybakItems.I9.getItemStack().clone()).setName(RybakItems.I9.getItemStack().clone().getItemMeta().getDisplayName() + " &7(x64)").addLoreLine(" ", "&7Kliknij, zeby rozstackowac!").toItemStack());
        gui.setItem(15, new ItemBuilder(RybakItems.I10.getItemStack().clone()).setName(RybakItems.I10.getItemStack().clone().getItemMeta().getDisplayName() + " &7(x64)").addLoreLine(" ", "&7Kliknij, zeby rozstackowac!").toItemStack());
        gui.setItem(16, new ItemBuilder(RybakItems.I14.getItemStack().clone()).setName(RybakItems.I14.getItemStack().clone().getItemMeta().getDisplayName() + " &7(x64)").addLoreLine(" ", "&7Kliknij, zeby rozstackowac!").toItemStack());



        player.openInventory(gui);

    }

// ========================================= MLODSZY RYBAK - WYSPA 2 ========================================= //

    private final Set<Items> wyspa2Drops = new HashSet<>();

    private void initWyspa2Drops() {
        this.wyspa2Drops.add(new Items("2", 3, RybakItems.I19.getItemStack(), 1));
        this.wyspa2Drops.add(new Items("3", 6, RybakItems.I15.getItemStack(), 1));
        this.wyspa2Drops.add(new Items("4", 11, RybakItems.I16.getItemStack(), 1));
        this.wyspa2Drops.add(new Items("5", 15.75, RybakItems.I17.getItemStack(), 1));
        this.wyspa2Drops.add(new Items("6", 28.5, RybakItems.I18.getItemStack(), 1));
        this.wyspa2Drops.add(new Items("7", 37.5, RybakItems.I21.getItemStack(), 1));
        this.wyspa2Drops.add(new Items("8", 61.25, RybakItems.I20.getItemStack(), 1));
    }

    public ItemStack getWyspa2Drop(final Player player) {
        final ItemStack wedka = player.getItemInHand();
        final String krysztal = Utils.getTagString(wedka, "krysztal");
        Set<Items> wyspa2DropsClone = new HashSet<>(this.wyspa2Drops);
        wyspa2DropsClone.add(new Items("1", 0.0075 + DoubleUtils.round(Utils.getTagDouble(wedka, "krysztalDrop"),3), new ItemBuilder(Material.WOOL, 1, (short) 10).setName("&5&lKrysztal Czarnoksieznika").toItemStack().clone(), 1));

        switch (krysztal) {
            case "Mrocznych Wod":
                wyspa2DropsClone.add(new Items("9", Utils.getTagDouble(wedka, "krysztalValue") / 2, RybakItems.I22.getItemStack(), 1));
                break;
            case "Podwodnych Spiewow":
                wyspa2DropsClone.add(new Items("9", Utils.getTagDouble(wedka, "krysztalValue"), RybakItems.I23.getItemStack(), 1));
                break;
        }

        List<Items> toSort = new ArrayList<>(wyspa2DropsClone);

        toSort = toSort.stream().sorted(Comparator.comparingDouble(Items::getChance)).collect(Collectors.toList());

        wyspa2DropsClone = ImmutableSet.copyOf(toSort);
        for (Items item : wyspa2DropsClone) {
            if (ChanceHelper.getChance(item.getChance())) {
                final ItemStack reward = item.getRewardItem();
                reward.setAmount(item.getAmount());
                return reward.clone();
            }
        }
        return null;
    }




    private final Map<UUID, Long> timeMap = new HashMap<>();


    public void onClickMlodszyRybak(final Player player) {
        final RybakUser rybak = this.find(player.getUniqueId());
        final MlodszyRybakUser user = rybak.getMlodszyRybakUser();

        if (!user.isReceivedRod()) {
            if (player.getItemInHand() == null || player.getItemInHand().getType() != Material.FISHING_ROD) {
                player.sendMessage(Utils.format("&3&lMlodszy Rybak &8>> &cNajpierw przynies mi &6Stara Wedke&c!"));
                return;
            }
            if (!Utils.getTagString(player.getItemInHand(), "owner").equals(player.getName())) {
                player.sendMessage(Utils.format("&3&lMlodszy Rybak &8>> &cTa wedka nie nalezy do ciebie!"));
                return;
            }
            if (rpgcore.getUserManager().find(player.getUniqueId()).getKasa() < 25_000_000) {
                player.sendMessage(Utils.format("&3&lMlodszy Rybak &8>> &cHola hola... Nie ma nic za darmo!"));
                player.sendMessage(Utils.format("&3&lMlodszy Rybak &8>> &8Ta przyjemnosc bedzie cie kosztowac &625 000 000&2$"));
                return;
            }
            final int lvl = Utils.getTagInt(player.getItemInHand(), "lvl");
            final int exp = Utils.getTagInt(player.getItemInHand(), "exp");
            final int udanePolowy = Utils.getTagInt(player.getItemInHand(), "udanePolowy");
            final double doubleDrop = Utils.getTagDouble(player.getItemInHand(), "doubleDrop");
            rybak.setLvlWedki(lvl);
            rybak.setExpWedki(exp);
            rybak.setWylowioneRyby(udanePolowy);
            rybak.setPodwojnyDrop(doubleDrop);
            player.getInventory().remove(new ItemBuilder(player.getItemInHand().clone()).setAmount(1).toItemStack().clone());
            this.sendDelayedMessage(player, "&3&lMlodszy Rybak &8>> &7Hmmmm... A wiec jestes znajomym &6&lStaruszka", 40L);
            this.sendDelayedMessage(player, "&3&lMlodszy Rybak &8>> &7No dobra powiedzmy, ze ci ufam", 80L);
            this.sendDelayedMessage(player, "&3&lMlodszy Rybak &8>> &7Zanim dostaniesz wedke, ktora umozliwi ci lowienie na tej wyspie", 120L);
            this.sendDelayedMessage(player, "&3&lMlodszy Rybak &8>> &7Musisz wiedziec kilka rzeczy", 160L);
            this.sendDelayedMessage(player, "&3&lMlodszy Rybak &8>> &7Dawno dawno temu wyspe te nawiedzil &5Czarnoksieznik", 200L);
            this.sendDelayedMessage(player, "&3&lMlodszy Rybak &8>> &7Poniewaz &6&lStaruszek &7wdal sie z nim w mala sprzeczke", 240L);
            this.sendDelayedMessage(player, "&3&lMlodszy Rybak &8>> &5Czarnoksieznik &7postanowil rzucic klatwe na te wyspe", 280L);
            this.sendDelayedMessage(player, "&3&lMlodszy Rybak &8>> &7a sam &6&lStaruszek &7zostal skazany na zycie w samotnosci na innej wyspie", 320L);
            this.sendDelayedMessage(player, "&3&lMlodszy Rybak &8>> &cNIE OPOWIADAL CI TEGO?!?!", 360L);
            this.sendDelayedMessage(player, "&3&lMlodszy Rybak &8>> &7No dobra, ale do rzeczy.", 400L);
            this.sendDelayedMessage(player, "&3&lMlodszy Rybak &8>> &7Na tej wyspie z wody mozesz wylowic jakies dziwne &dKrysztaly", 440L);
            this.sendDelayedMessage(player, "&3&lMlodszy Rybak &8>> &7Kazdy z nich ma inne wlasciwosci i gwarantuje inne bonusy", 480L);
            this.sendDelayedMessage(player, "&3&lMlodszy Rybak &8>> &7Tak samo doszly mnie sluchy, ze zalegly sie tu jakies &3wodne stwory", 520L);
            this.sendDelayedMessage(player, "&3&lMlodszy Rybak &8>> &7Ale nie pominienes miec z nimi problemu... &aPrawda?", 560L);
            this.sendDelayedMessage(player, "&3&lMlodszy Rybak &8>> &7Teraz skoro juz wszystko wiesz, jestes &agotowy &7na dalszy ciag swojej wedkarskiej przygody", 600L);
            rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> player.getInventory().addItem(RybakItems.getSlabaWedkaRybacka(player.getName(), lvl, exp, udanePolowy, doubleDrop)), 600L);
            user.setReceivedRod(true);
            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataRybak(player.getUniqueId(), rybak));
            return;
        }

        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&3&lMlodszy Rybak"));
        for (int i = 0; i < 27; i++) {
            gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName(" ").toItemStack());
        }

        for (int i = 1; i < MlodszyRybakMissions.values().length + 1; i++) {
            if (user.getMission() > i) {
                gui.setItem(i - 1, new ItemBuilder(Material.BOOK).setName("&7Misja #&c" + i).setLore(Arrays.asList(" ", "&a&lWYKONANO!")).addGlowing().toItemStack().clone());
            } else if (user.getMission() == i) {
                gui.setItem(i - 1, Objects.requireNonNull(MlodszyRybakMissions.getMissionById(i)).getMissionItem(user.getProgress()).clone());
            } else {
                gui.setItem(i - 1, new ItemBuilder(Material.BARRIER).setName("&7Misja #&c" + i).setLore(Arrays.asList(" ", "&cWykonaj poprzednia misje, zeby odblokowac!")).addGlowing().toItemStack().clone());
            }
        }

        gui.setItem(26, new ItemBuilder(Material.EMERALD).setName("&aSklep").addGlowing().toItemStack());

        player.openInventory(gui);
    }

    public void openMlodszyRybakShop(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 9, Utils.format("&3&lMlodszy Rybak &8>> &aSklep"));

        for (int i = 0; i < gui.getSize(); i++) gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 5).setName(" ").toItemStack());

        gui.setItem(0, this.addPrice(RybakItems.I20.getItemStack(), 16250));
        gui.setItem(1, this.addPrice(RybakItems.I21.getItemStack(), 19625));
        gui.setItem(2, this.addPrice(RybakItems.I18.getItemStack(), 23750));
        gui.setItem(3, this.addPrice(RybakItems.I17.getItemStack(), 31250));
        gui.setItem(4, this.addPrice(RybakItems.I16.getItemStack(), 38750));
        gui.setItem(5, this.addPrice(RybakItems.I15.getItemStack(), 45000));
        gui.setItem(6, this.addPrice(RybakItems.I19.getItemStack(), 55000));

        player.openInventory(gui);
    }

    public void openRybackiStolWyspa2(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, InventoryType.HOPPER, Utils.format("&b&lStol Rybacki"));

        for (int i = 0; i < gui.getSize(); i++) {
            if (i % 2 == 0) gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 3).setName(" ").toItemStack());
            else gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 11).setName(" ").toItemStack());
        }
        final ItemStack wedka = player.getItemInHand();
        final String krysztal = Utils.getTagString(wedka, "krysztal");

        if (krysztal.equals("BRAK")) {
            gui.setItem(2, getNoKrysztalItem());
        } else {
            gui.setItem(2, RybakItems.getKrysztal(krysztal, Utils.getTagDouble(wedka, "krysztalValue")));
        }


        player.openInventory(gui);
    }

    public ItemStack getNoKrysztalItem() {
        return new ItemBuilder(Material.IRON_FENCE).setName("&cBrak Krysztalu").toItemStack();
    }



// ========================================= ANTY AFK ========================================= //

    public void getAntyAfk(final Player player) {
        player.sendMessage(Utils.format("&7Ochrona &cAnty-AFK"));
        player.sendMessage(Utils.format("&7Kliknij w &bpodswietlona wedke&7, aby zakonczyc weryfikacje"));
        player.sendMessage(Utils.format("&7GUI zamknie sie &cautomatycznie po 10 sekundach&7, a na ciebie zostanie nalozona kara"));
        player.sendMessage(Utils.format("&7Ochrona &cAnty-AFK"));
        final Inventory gui = Bukkit.createInventory(null, 18, Utils.format("&6&lRybak &8>> &cAnty-AFK"));

        for (int i = 0; i < 18; i++) {
            gui.setItem(i, new ItemBuilder(Material.FISHING_ROD).setName("&c&lAnty-AFK").toItemStack());
        }

        gui.setItem(ChanceHelper.getRandInt(0, 17), new ItemBuilder(Material.FISHING_ROD).setName("&a&lAnty-AFK").setLore(Arrays.asList("&7Kliknij, zeby przejsc weryfikacje &cAnty-AFK")).addGlowing().toItemStack());

        player.openInventory(gui);
        int task = rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> {
            if (player == null || !player.isOnline()) return;
            if (player.getOpenInventory().getTopInventory() == null) {
                return;
            }
            if (Utils.removeColor(player.getOpenInventory().getTopInventory().getTitle()).equals("Rybak » Anty-AFK")) {
                if (this.passed.contains(player.getUniqueId())) {
                    this.passed.remove(player.getUniqueId());
                    return;
                }
                player.closeInventory();
            }
        }, 200L).getTaskId();
        this.taskMap.put(player.getUniqueId(), task);

    }


    public int getFailedAttempts(final UUID uuid) {
        return failedAttemptMap.getOrDefault(uuid, 0);
    }

    public void addFailedAttempt(final UUID uuid) {
        if (failedAttemptMap.containsKey(uuid)) {
            failedAttemptMap.put(uuid, failedAttemptMap.get(uuid) + 1);
        } else {
            failedAttemptMap.put(uuid, 1);
        }
    }

    public void resetFailedAttempt(final UUID uuid) {
        if (failedAttemptMap.containsKey(uuid)) {
            failedAttemptMap.replace(uuid, 0);
        } else {
            failedAttemptMap.put(uuid, 0);
        }
    }

    public int getFishingCount(final UUID uuid) {
        return fishingCount.getOrDefault(uuid, 0);
    }

    public void addFishingCount(final UUID uuid) {
        if (fishingCount.containsKey(uuid)) {
            fishingCount.replace(uuid, fishingCount.get(uuid) + 1);
        } else {
            fishingCount.put(uuid, 1);
        }
    }

    public void resetFishingCount(final UUID uuid) {
        fishingCount.replace(uuid, 0);
    }


    // ========================================= RESPIENIE MOBOW ========================================= //


    public void runFishAnimation(final Location playerLocation, final Entity entity) {
        double pushX = playerLocation.getDirection().normalize().getX() * -2;
        double pushY = playerLocation.getDirection().normalize().getY() * -1.5;
        double pushZ = playerLocation.getDirection().normalize().getZ() * -2;

        Vector push = new Vector(pushX, pushY, pushZ);

        entity.setVelocity(push);
    }


    // ========================================= MOBY ========================================= //

    public void spawnNurekGlebinowy(final Location playerLocation, final Location location) {
        final LivingEntity entity = (LivingEntity) RPGCORE.getMythicMobs().getMobManager().spawnMob("NUREK-GLEBINOWY", location).getEntity().getBukkitEntity();
        runFishAnimation(playerLocation, entity);
        RPGCORE.getInstance().getServer().getScheduler().runTaskLater(RPGCORE.getInstance(), () -> {
            if (entity.isDead()) return;
            entity.remove();
        }, 20 * 60 * 2);
    }

    public void spawnPosejdon(final Location playerLocation, final Location location) {
        final LivingEntity entity = (LivingEntity) RPGCORE.getMythicMobs().getMobManager().spawnMob("POSEJDON", location).getEntity().getBukkitEntity();
        runFishAnimation(playerLocation, entity);
        RPGCORE.getInstance().getServer().getScheduler().runTaskLater(RPGCORE.getInstance(), () -> {
            if (entity.isDead()) return;
            entity.remove();
        }, 20 * 60 * 4);
    }

    public void spawnMorskiKsiaze(final Location playerLocation, final Location location) {
        final LivingEntity entity = (LivingEntity) RPGCORE.getMythicMobs().getMobManager().spawnMob("MORSKI-KSIAZE", location).getEntity().getBukkitEntity();
        runFishAnimation(playerLocation, entity);
        RPGCORE.getInstance().getServer().getScheduler().runTaskLater(RPGCORE.getInstance(), () -> {
            if (entity.isDead()) return;
            entity.remove();
        }, 20 * 60 * 3);
    }
    public void spawnWodnyStwor(final Location playerLocation, final Location location) {
        final LivingEntity entity = (LivingEntity) RPGCORE.getMythicMobs().getMobManager().spawnMob("WODNY-STWOR", location).getEntity().getBukkitEntity();
        runFishAnimation(playerLocation, entity);
        RPGCORE.getInstance().getServer().getScheduler().runTaskLater(RPGCORE.getInstance(), () -> {
            if (entity.isDead()) return;
            entity.remove();
        }, 20 * 60 * 2);
    }

    public void setNoAi(final Entity entity) {
        net.minecraft.server.v1_8_R3.Entity nmsEntity = ((CraftEntity) entity).getHandle();

        NBTTagCompound tag = new NBTTagCompound();

        nmsEntity.c(tag);

        tag.setBoolean("NoAI", true);

        EntityLiving el = (EntityLiving) nmsEntity;
        el.a(tag);
    }


    // ========================================= ANTY AFK cz. 2 ========================================= //

    public List<UUID> getPassed() {
        return passed;
    }

    public void addLocation(final UUID uuid, final String type, final float yaw, final float pitch) {
        if (!this.locationMapYaw.containsKey(uuid)) this.locationMapYaw.put(uuid, new HashMap<>());
        if (!this.locationMapPitch.containsKey(uuid)) this.locationMapPitch.put(uuid, new HashMap<>());

        this.locationMapYaw.get(uuid).put(type, yaw);
        this.locationMapPitch.get(uuid).put(type, pitch);
    }

    public boolean isSameLocation(final UUID uuid, final String type, final float yaw, final float pitch) {
        if (!this.locationMapYaw.containsKey(uuid) || !this.locationMapPitch.containsKey(uuid)) return false;
        if (!this.locationMapYaw.get(uuid).containsKey(type) || !this.locationMapPitch.get(uuid).containsKey(type))
            return false;
        return this.locationMapYaw.get(uuid).get(type) == yaw && this.locationMapPitch.get(uuid).get(type) == pitch;
    }

    public int getAntiAfk(final UUID uuid) {
        if (!this.antiFishMap.containsKey(uuid)) {
            this.antiFishMap.put(uuid, 0);
            return 0;
        }
        return this.antiFishMap.get(uuid);
    }

    public void resetAntiAfk(final UUID uuid) {
        this.antiFishMap.replace(uuid, 0);
    }

    public void addAntiAfk(final UUID uuid) {
        if (!this.antiFishMap.containsKey(uuid)) {
            this.antiFishMap.put(uuid, 1);
            return;
        }
        this.antiFishMap.replace(uuid, this.antiFishMap.get(uuid) + 1);
    }


    // ========================================= POTRZEBNE I ZAWSZE ========================================= //

    public void add(final RybakUser rybakUser) {
        this.usersMap.put(rybakUser.getUuid(), rybakUser);
    }

    public RybakUser find(final UUID uuid) {
        return this.usersMap.get(uuid);
    }

    public void set(final UUID uuid, final RybakUser rybakUser) {
        this.usersMap.replace(uuid, rybakUser);
    }

    public ImmutableSet<RybakUser> getRybakObjects() {
        return ImmutableSet.copyOf(this.usersMap.values());
    }

    public boolean checkIfMapContainsArmorStand(final Location location) {
        return this.armorStandMap.values().stream().anyMatch(as -> as.getLocation().equals(location));
    }

    public int getKeyByValue(final ArmorStand as) {
        return this.armorStandMap.entrySet().stream().filter(entry -> Objects.equals(as, entry.getValue())).map(Map.Entry::getKey).findFirst().orElse(-1);
    }
}
