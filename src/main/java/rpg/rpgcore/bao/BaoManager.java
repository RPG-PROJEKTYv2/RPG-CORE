package rpg.rpgcore.bao;

import com.google.common.collect.ImmutableSet;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.bonuses.Bonuses;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.*;

public class BaoManager {
    private final RPGCORE rpgcore;
    private final Map<UUID, BaoObject> userMap;
    private final List<Integer> entityIdList;
    private final Random random = new Random();

    public BaoManager(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
        this.userMap = rpgcore.getMongoManager().loadAllBao();
        this.entityIdList = this.getAllEntities();
    }


    public void openMainGUI(final Player player, final boolean ksiega) {
        final UUID uuid = player.getUniqueId();
        final BaoUser user = this.find(uuid).getBaoUser();
        Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&6&lSTOL MAGII"));
        if (ksiega) {
            gui = Bukkit.createInventory(null, 27, Utils.format("&4&lKSIEGA MAGII"));
        }

        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setName(" ").toItemStack());
        }
        if (!ksiega) {
            gui.setItem(10, new ItemBuilder(Material.BOOK_AND_QUILL).setName("&6" + user.getBonus1() + ": &c" + user.getValue1() + "%").toItemStack().clone());
            gui.setItem(11, new ItemBuilder(Material.BOOK_AND_QUILL).setName("&6" + user.getBonus2() + ": &c" + user.getValue2() + "%").toItemStack().clone());
            gui.setItem(12, new ItemBuilder(Material.BOOK_AND_QUILL).setName("&6" + user.getBonus3() + ": &c" + user.getValue3() + "%").toItemStack().clone());
            if (user.getBonus4().equalsIgnoreCase("dodatkowe obrazenia")) {
                gui.setItem(13, new ItemBuilder(Material.BOOK_AND_QUILL).setName("&6" + user.getBonus4() + ": &c" + user.getValue4() + " DMG").toItemStack().clone());
            } else {
                gui.setItem(13, new ItemBuilder(Material.BOOK_AND_QUILL).setName("&6" + user.getBonus4() + ": &c" + user.getValue4() + "%").toItemStack().clone());
            }
            if (user.getBonus5().equalsIgnoreCase("dodatkowe hp")) {
                gui.setItem(14, new ItemBuilder(Material.BOOK_AND_QUILL).setName("&6" + user.getBonus5() + ": &c" + user.getValue5() + " HP").toItemStack().clone());
            } else {
                gui.setItem(14, new ItemBuilder(Material.BOOK_AND_QUILL).setName("&6" + user.getBonus5() + ": &c" + user.getValue5() + "%").toItemStack().clone());
            }
            gui.setItem(16, new ItemBuilder(Material.NETHER_STAR).setName("&4&lZmien bonusy Stolu Magii").setLore(Arrays.asList(
                    "&8Kliknij na ten item, zeby zmienic",
                    "&8swoje bonusy w &6stole magii",
                    "&8Pamietaj, ze &c&lAdministracja &8nie odpowiada za zmienione bonusy"
            )).toItemStack());

            gui.setItem(17, new ItemBuilder(Material.BOOK).setName("&c&lMaxymalne Wartosci Bonusow").setLore(Arrays.asList(
                    "&8Bonus 1:",
                    "&8-&6Srednie obrazenia: &c40%",
                    "&8-&6Silny Na Ludzi: &c50%",
                    "&8-&6Silny Na Potwory: &c50%",
                    "&8Bonus 2:",
                    "&8-&6Srednia defensywa: &c40%",
                    "&8-&6Srednia defensywa przeciwko Ludziom: &c50%",
                    "&8-&6Srednia defensywa przeciwko Potworom: &c50%",
                    "&8Bonus 3:",
                    "&8-&6Szansa Na Cios Krytyczny: &c50%",
                    "&8-&6Szansa Na Wzmocnienie Ciosu Krytycznego: &c30%",
                    "&8-&6Dodatkowe Obrazenia: &c400 DMG",
                    "&8Bonus 4:",
                    "&8-&6Predkosc Ruchu: &c50",
                    "&8-&6Dodatkowy EXP: &c10%",
                    "&8-&6Szczescie: &c10",
                    "&8Bonus 5:",
                    "&8-&6Dodatkowe HP: &c15 HP",
                    "&8-&6Blok Ciosu: &c20%"
            )).addGlowing().toItemStack());
            if (isNotRolled(uuid)) {
                for (int i = 10; i < 15; i++) {
                    gui.setItem(i, new ItemBuilder(Material.BOOK_AND_QUILL).setName("&6Brak Bonusu").toItemStack().clone());
                }
            }
        } else {
            gui.setItem(11, new ItemBuilder(Material.BOOK_AND_QUILL).setName("&6" + user.getBonus1() + ": &c" + user.getValue1() + "%").toItemStack().clone());
            gui.setItem(12, new ItemBuilder(Material.BOOK_AND_QUILL).setName("&6" + user.getBonus2() + ": &c" + user.getValue2() + "%").toItemStack().clone());
            if (user.getBonus3().equalsIgnoreCase("dodatkowe obrazenia")) {
                gui.setItem(13, new ItemBuilder(Material.BOOK_AND_QUILL).setName("&6" + user.getBonus3() + ": &c" + user.getValue3() + " DMG").toItemStack().clone());
            } else {
                gui.setItem(13, new ItemBuilder(Material.BOOK_AND_QUILL).setName("&6" + user.getBonus3() + ": &c" + user.getValue3() + "%").toItemStack().clone());
            }
            if (user.getBonus4().equalsIgnoreCase("predkosc ruchu") || user.getBonus4().equalsIgnoreCase("szczescie")) {
                gui.setItem(14, new ItemBuilder(Material.BOOK_AND_QUILL).setName("&6" + user.getBonus4() + ": &c" + user.getValue4()).toItemStack().clone());
            } else {
                gui.setItem(14, new ItemBuilder(Material.BOOK_AND_QUILL).setName("&6" + user.getBonus4() + ": &c" + user.getValue4() + "%").toItemStack().clone());
            }
            if (user.getBonus5().equalsIgnoreCase("dodatkowe hp")) {
                gui.setItem(15, new ItemBuilder(Material.BOOK_AND_QUILL).setName("&6" + user.getBonus5() + ": &c" + user.getValue5() + " HP").toItemStack().clone());
            } else {
                gui.setItem(15, new ItemBuilder(Material.BOOK_AND_QUILL).setName("&6" + user.getBonus5() + ": &c" + user.getValue5() + "%").toItemStack().clone());
            }
            if (isNotRolled(uuid)) {
                for (int i = 11; i < 16; i++) {
                    gui.setItem(10 + i, new ItemBuilder(Material.BOOK_AND_QUILL).setName("&6Brak Bonusu").toItemStack().clone());
                }
            }
        }

        player.openInventory(gui);
    }

    public void losujNoweBonusy(final UUID uuid, final String playerName) {
        this.losujNowyBonus1(uuid);
        this.losujNowyBonus2(uuid);
        this.losujNowyBonus3(uuid);
        this.losujNowyBonus4(uuid);
        this.losujNowyBonus5(uuid);

        final BaoUser user = this.rpgcore.getBaoManager().find(uuid).getBaoUser();

        rpgcore.getServer().broadcastMessage(Utils.format("          &5&lSTOL MAGII        "));
        rpgcore.getServer().broadcastMessage(Utils.format("  &7Nowe bonusy gracza &c " + playerName));
        rpgcore.getServer().broadcastMessage(Utils.format("  &7" + user.getBonus1() + ": &f" + user.getValue1() + "%"));
        rpgcore.getServer().broadcastMessage(Utils.format("  &7" + user.getBonus2() + ": &f" + user.getValue2() + "%"));
        if (user.getBonus3().equalsIgnoreCase("dodatkowe obrazenia")) {
            rpgcore.getServer().broadcastMessage(Utils.format("  &7" + user.getBonus3() + ": &f" + user.getValue3() + " DMG"));
        } else {
            rpgcore.getServer().broadcastMessage(Utils.format("  &7" + user.getBonus3() + ": &f" + user.getValue3() + "%"));
        }
        if (user.getBonus4().equalsIgnoreCase("predkosc ruchu") || user.getBonus4().equalsIgnoreCase("szczescie")) {
            rpgcore.getServer().broadcastMessage(Utils.format("  &7" + user.getBonus4() + ": &f" + user.getValue4()));
        } else {
            rpgcore.getServer().broadcastMessage(Utils.format("  &7" + user.getBonus4() + ": &f" + user.getValue4() + "%"));
        }
        if (user.getBonus5().equalsIgnoreCase("dodatkowe hp")) {
            rpgcore.getServer().broadcastMessage(Utils.format("  &7" + user.getBonus5() + ": &f" + user.getValue5() + " HP"));
        } else {
            rpgcore.getServer().broadcastMessage(Utils.format("  &7" + user.getBonus5() + ": &f" + user.getValue5() + "%"));
        }
        rpgcore.getServer().broadcastMessage(Utils.format("          &5&lSTOL MAGII        "));
    }

    public void losujNowyBonus1(final UUID uuid) {
        this.removePrevBonuses(uuid, 1);
        final BaoUser user = this.find(uuid).getBaoUser();
        final Bonuses bonuses = rpgcore.getBonusesManager().find(uuid);
        final int nowyBaoBonus1 = random.nextInt(3) + 1;
        switch (nowyBaoBonus1) {
            case 1:
                user.setBonus1("Srednie obrazenia");
                user.setValue1(random.nextInt(40) + 1);
                bonuses.getBonusesUser().setSrednieobrazenia(bonuses.getBonusesUser().getSrednieobrazenia() + user.getValue1());
                break;
            case 2:
                user.setBonus1("Silny Na Ludzi");
                user.setValue1(random.nextInt(50) + 1);
                bonuses.getBonusesUser().setSilnynaludzi(bonuses.getBonusesUser().getSilnynaludzi() + user.getValue1());
                break;
            case 3:
                user.setBonus1("Silny Na Potwory");
                user.setValue1(random.nextInt(50) + 1);
                bonuses.getBonusesUser().setSilnynapotwory(bonuses.getBonusesUser().getSilnynapotwory() + user.getValue1());
                break;
        }
        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
            rpgcore.getMongoManager().saveDataBao(uuid, this.find(uuid));
            rpgcore.getMongoManager().saveDataBonuses(uuid, bonuses);
        });
    }

    public void losujNowyBonus2(final UUID uuid) {
        this.removePrevBonuses(uuid, 2);
        final BaoUser user = this.find(uuid).getBaoUser();
        final Bonuses bonuses = rpgcore.getBonusesManager().find(uuid);
        final int nowyBaoBonus2 = random.nextInt(3) + 1;
        switch (nowyBaoBonus2) {
            case 1:
                user.setBonus2("Srednia defensywa");
                user.setValue2(random.nextInt(40) + 1);
                bonuses.getBonusesUser().setSredniadefensywa(bonuses.getBonusesUser().getSredniadefensywa() + user.getValue2());
                break;
            case 2:
                user.setBonus2("Srednia defensywa przeciwko ludziom");
                user.setValue2(random.nextInt(50) + 1);
                bonuses.getBonusesUser().setDefnaludzi(bonuses.getBonusesUser().getDefnaludzi() + user.getValue2());
                break;
            case 3:
                user.setBonus2("Srednia defensywa przeciwko potworom");
                user.setValue2(random.nextInt(50) + 1);
                bonuses.getBonusesUser().setDefnamoby(bonuses.getBonusesUser().getDefnamoby() + user.getValue2());
                break;
        }
        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
            rpgcore.getMongoManager().saveDataBao(uuid, this.find(uuid));
            rpgcore.getMongoManager().saveDataBonuses(uuid, bonuses);
        });
    }

    public void losujNowyBonus3(final UUID uuid) {
        this.removePrevBonuses(uuid, 3);
        final BaoUser user = this.find(uuid).getBaoUser();
        final Bonuses bonuses = rpgcore.getBonusesManager().find(uuid);
        final int nowyBaoBonus3 = random.nextInt(3) + 1;
        switch (nowyBaoBonus3) {
            case 1:
                user.setBonus3("Szansa na cios krytyczny");
                user.setValue3(random.nextInt(50) + 1);
                bonuses.getBonusesUser().setSzansanakryta(bonuses.getBonusesUser().getSzansanakryta() + user.getValue3());
                break;
            case 2:
                user.setBonus3("Wzmocnienie ciosu krytycznego");
                user.setValue3(random.nextInt(30) + 1);
                bonuses.getBonusesUser().setWzmocnienieKryta(bonuses.getBonusesUser().getWzmocnienieKryta() + user.getValue3());
                break;
            case 3:
                user.setBonus3("Dodatkowe obrazenia");
                user.setValue3(random.nextInt(400) + 1);
                bonuses.getBonusesUser().setDodatkoweobrazenia(bonuses.getBonusesUser().getDodatkoweobrazenia() + user.getValue3());
                break;
        }
        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
            rpgcore.getMongoManager().saveDataBao(uuid, this.find(uuid));
            rpgcore.getMongoManager().saveDataBonuses(uuid, bonuses);
        });
    }

    public void losujNowyBonus4(final UUID uuid) {
        this.removePrevBonuses(uuid, 4);
        final BaoUser user = this.find(uuid).getBaoUser();
        final Bonuses bonuses = rpgcore.getBonusesManager().find(uuid);
        final int nowyBaoBonus4 = random.nextInt(3) + 1;
        switch (nowyBaoBonus4) {
            case 1:
                user.setBonus4("Predkosc ruchu");
                user.setValue4(random.nextInt(50) + 1);
                bonuses.getBonusesUser().setSzybkosc(bonuses.getBonusesUser().getSzybkosc() + user.getValue4());
                break;
            case 2:
                user.setBonus4("Dodatkowy EXP");
                user.setValue4(random.nextInt(10) + 1);
                bonuses.getBonusesUser().setDodatkowyExp(bonuses.getBonusesUser().getDodatkowyExp() + user.getValue4());
                break;
            case 3:
                user.setBonus4("Szczescie");
                user.setValue4(random.nextInt(10) + 1);
                bonuses.getBonusesUser().setSzczescie(bonuses.getBonusesUser().getSzczescie() + user.getValue4());
                break;
        }
        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
            rpgcore.getMongoManager().saveDataBao(uuid, this.find(uuid));
            rpgcore.getMongoManager().saveDataBonuses(uuid, bonuses);
        });
    }

    public void losujNowyBonus5(final UUID uuid) {
        this.removePrevBonuses(uuid, 5);
        final BaoUser user = this.find(uuid).getBaoUser();
        final Bonuses bonuses = rpgcore.getBonusesManager().find(uuid);
        if (user.getBonus5().equalsIgnoreCase("dodatkowe hp")) {
            if (Bukkit.getPlayer(uuid).getMaxHealth() > 10) {
                Bukkit.getPlayer(uuid).setMaxHealth(Bukkit.getPlayer(uuid).getMaxHealth() - user.getValue5() * 2);
            }
        }

        final int nowyBaoBonus5 = random.nextInt(2) + 1;
        switch (nowyBaoBonus5) {
            case 1:
                user.setBonus5("Dodatkowe HP");
                user.setValue5(random.nextInt(15) + 1);
                Bukkit.getPlayer(uuid).setMaxHealth(Bukkit.getPlayer(uuid).getMaxHealth() + user.getValue5() * 2);
                bonuses.getBonusesUser().setDodatkowehp(bonuses.getBonusesUser().getDodatkowehp() + user.getValue5());
                break;
            case 2:
                user.setBonus5("Blok Ciosu");
                user.setValue5(random.nextInt(20) + 1);
                bonuses.getBonusesUser().setBlokciosu(bonuses.getBonusesUser().getBlokciosu() + user.getValue5());
                break;
        }
        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
            rpgcore.getMongoManager().saveDataBao(uuid, this.find(uuid));
            rpgcore.getMongoManager().saveDataBonuses(uuid, bonuses);
        });
    }

    public void removePrevBonuses(final UUID uuid, final int number) {
        final Bonuses bonuses = rpgcore.getBonusesManager().find(uuid);
        final BaoObject bao = this.find(uuid);
        switch (number) {
            case 1:
                switch (bao.getBaoUser().getBonus1()) {
                    case "Srednie obrazenia":
                        bonuses.getBonusesUser().setSrednieobrazenia(bonuses.getBonusesUser().getSrednieobrazenia() - bao.getBaoUser().getValue1());
                        break;
                    case "Silny Na Ludzi":
                        bonuses.getBonusesUser().setSilnynaludzi(bonuses.getBonusesUser().getSilnynaludzi() - bao.getBaoUser().getValue1());
                        break;
                    case "Silny Na Potwory":
                        bonuses.getBonusesUser().setSilnynapotwory(bonuses.getBonusesUser().getSilnynapotwory() - bao.getBaoUser().getValue1());
                        break;
                }
                break;
            case 2:
                switch (bao.getBaoUser().getBonus2()) {
                    case "Srednia defensywa":
                        bonuses.getBonusesUser().setSredniadefensywa(bonuses.getBonusesUser().getSredniadefensywa() - bao.getBaoUser().getValue2());
                        break;
                    case "Srednia defensywa przeciwko ludziom":
                        bonuses.getBonusesUser().setDefnaludzi(bonuses.getBonusesUser().getDefnaludzi() - bao.getBaoUser().getValue2());
                        break;
                    case "Srednia defensywa przeciwko potworom":
                        bonuses.getBonusesUser().setDefnamoby(bonuses.getBonusesUser().getDefnamoby() - bao.getBaoUser().getValue2());
                        break;
                }
                break;
            case 3:
                switch (bao.getBaoUser().getBonus3()) {
                    case "Szansa na cios krytyczny":
                        bonuses.getBonusesUser().setSzansanakryta(bonuses.getBonusesUser().getSzansanakryta() - bao.getBaoUser().getValue3());
                        break;
                    case "Wzmocnienie ciosu krytycznego":
                        bonuses.getBonusesUser().setWzmocnienieKryta(bonuses.getBonusesUser().getWzmocnienieKryta() - bao.getBaoUser().getValue3());
                        break;
                    case "Dodatkowe obrazenia":
                        bonuses.getBonusesUser().setDodatkoweobrazenia(bonuses.getBonusesUser().getDodatkoweobrazenia() - bao.getBaoUser().getValue3());
                        break;
                }
                break;
            case 4:
                switch (bao.getBaoUser().getBonus4()) {
                    case "Predkosc ruchu":
                        bonuses.getBonusesUser().setSzybkosc(bonuses.getBonusesUser().getSzybkosc() - bao.getBaoUser().getValue4());
                        break;
                    case "Dodatkowy EXP":
                        bonuses.getBonusesUser().setDodatkowyExp(bonuses.getBonusesUser().getDodatkowyExp() - bao.getBaoUser().getValue4());
                        break;
                    case "Szczescie":
                        bonuses.getBonusesUser().setSzczescie(bonuses.getBonusesUser().getSzczescie() - bao.getBaoUser().getValue4());
                        break;
                }
                break;
            case 5:
                switch (bao.getBaoUser().getBonus5()) {
                    case "Dodatkowe HP":
                        bonuses.getBonusesUser().setDodatkowehp(bonuses.getBonusesUser().getDodatkowehp() - bao.getBaoUser().getValue5());
                        break;
                    case "Blok Ciosu":
                        bonuses.getBonusesUser().setBlokciosu(bonuses.getBonusesUser().getBlokciosu() - bao.getBaoUser().getValue5());
                        break;
                }
                break;
        }
    }


    public ItemStack getItemDoLosowania() {
        return new ItemBuilder(Material.COAL, 1, (short) 1).setName("&3&lKamien Zaczarowania Stolu")
                .setLore(Arrays.asList("&8Ten magiczny kamien pozwala Ci", "&8zmienic swoje bonusy w &6Stole Magii", "&8Pamietaj &c&lAdministracja &8nie odpowiada za zmieniane bonusy"))
                .toItemStack().clone();
    }


    public ItemStack getItemDoZmianki() {
        return new ItemBuilder(Material.ENCHANTED_BOOK).setName("&4&lKsiega Magii")
                .setLore(Arrays.asList("&8Ta magiczna ksiega pozwoli Ci", "&8zmienic jeden bonus w &6Stole Magii", "&8Pamietaj &c&lAdministracja &8nie odpowiada za zmieniane bonusy"))
                .toItemStack().clone();
    }


    public void add(BaoObject baoObject) {
        this.userMap.put(baoObject.getId(), baoObject);
    }

    public BaoObject find(final UUID uuid) {
        this.userMap.computeIfAbsent(uuid, k -> new BaoObject(uuid));
        return this.userMap.get(uuid);
    }

    public ImmutableSet<BaoObject> getBaoObjects() {
        return ImmutableSet.copyOf(this.userMap.values());
    }

    public List<Integer> getAllEntities() {
        final List<Integer> entityList = new ArrayList<>();
        ArmorStand as = (ArmorStand) Bukkit.getWorld("world").spawnEntity(new Location(Bukkit.getWorld("world"), -46.5, 99, -274.55), EntityType.ARMOR_STAND);
        as.setVisible(false);
        for (Entity e : as.getNearbyEntities(1, 2, 1)) {
            if (!(e instanceof Player)) {
                entityList.add(e.getEntityId());
            }
        }
        Bukkit.getServer().getScheduler().runTaskLater(rpgcore, as::remove, 100L);
        return entityList;
    }

    public boolean isNotRolled(final UUID uuid) {
        final BaoUser user = this.find(uuid).getBaoUser();
        return user.getBonus1().equalsIgnoreCase("brak bonusu") || user.getBonus2().equalsIgnoreCase("brak bonusu") || user.getBonus3().equalsIgnoreCase("brak bonusu") || user.getBonus4().equalsIgnoreCase("brak bonusu") || user.getBonus5().equalsIgnoreCase("brak bonusu");
    }

    public boolean checkIfClickedEntityIsInList(final int entityId) {
        return this.entityIdList.contains(entityId);
    }
}
