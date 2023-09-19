package rpg.rpgcore.commands.admin.ustawieniakonta;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.bao.objects.BaoUser;
import rpg.rpgcore.bonuses.BonusesUser;
import rpg.rpgcore.discord.EmbedUtil;
import rpg.rpgcore.npc.czarownica.objects.CzarownicaUser;
import rpg.rpgcore.npc.gornik.enums.GornikMissions;
import rpg.rpgcore.npc.gornik.objects.GornikUser;
import rpg.rpgcore.npc.handlarz.objects.HandlarzUser;
import rpg.rpgcore.npc.kolekcjoner.enums.KolekcjonerMissions;
import rpg.rpgcore.npc.kolekcjoner.objects.KolekcjonerUser;
import rpg.rpgcore.npc.lesnik.enums.LesnikMissions;
import rpg.rpgcore.npc.lesnik.objects.LesnikUser;
import rpg.rpgcore.npc.lowca.enums.LowcaMissions;
import rpg.rpgcore.npc.lowca.objects.LowcaUser;
import rpg.rpgcore.npc.magazynier.enums.MagazynierMissions;
import rpg.rpgcore.npc.magazynier.objects.MagazynierUser;
import rpg.rpgcore.npc.medrzec.objects.MedrzecUser;
import rpg.rpgcore.npc.metinolog.enums.MetinologMissionGive;
import rpg.rpgcore.npc.metinolog.enums.MetinologMissionKill;
import rpg.rpgcore.npc.metinolog.objects.MetinologUser;
import rpg.rpgcore.npc.mistrz_yang.enums.MistrzYangMissions;
import rpg.rpgcore.npc.mistrz_yang.objects.MistrzYangUser;
import rpg.rpgcore.npc.przyrodnik.enums.PrzyrodnikMissions;
import rpg.rpgcore.npc.przyrodnik.objects.PrzyrodnikUser;
import rpg.rpgcore.npc.pustelnik.enums.PustelnikMissions;
import rpg.rpgcore.npc.pustelnik.objects.PustelnikUser;
import rpg.rpgcore.npc.rybak.enums.StaruszekMissions;
import rpg.rpgcore.npc.rybak.objects.RybakUser;
import rpg.rpgcore.npc.rybak.objects.StaruszekUser;
import rpg.rpgcore.npc.wyslannik.enums.WyslannikMissionKillBoss;
import rpg.rpgcore.npc.wyslannik.enums.WyslannikMissionKillMob;
import rpg.rpgcore.npc.wyslannik.objects.WyslannikUser;
import rpg.rpgcore.osiagniecia.objects.OsUser;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.DoubleUtils;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.wyszkolenie.objects.DrzewkoWyszkoleniaUser;
import rpg.rpgcore.wyszkolenie.objects.WyszkolenieUser;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class UstawieniaKontaManager {
    private final RPGCORE rpgcore;

    public UstawieniaKontaManager(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    private int countPlayerItems(final ItemStack[] components) {
        int count = 0;
        for (final ItemStack item : components) {
            if (item == null || item.getType() == Material.AIR) continue;
            count++;
        }
        return count;
    }

    public void openUstawieniaKonta(final Player player, final User target) {
        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&8&lKonto &c&l- &6&l" + target.getId().toString()));
        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setName(" ").toItemStack());
        }

        int playerItemsCount;
        int playerEnderchestItemsCount;
        try {
            playerItemsCount = countPlayerItems(Utils.itemStackArrayFromBase64(target.getInventoriesUser().getInventory()));
            playerEnderchestItemsCount = countPlayerItems(Utils.itemStackArrayFromBase64(target.getInventoriesUser().getEnderchest()));
        } catch (final IOException e) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&cWystapil blad podczas odczytywania ekwipunku gracza &e" + target.getName()));
            return;
        }

        gui.setItem(10, new ItemBuilder(Material.DIAMOND).setName("&cWyczysc &6Ekwipunek &cgracza &e" + target.getName()).setLore(Arrays.asList(
                "&7Uzywajac tego sprawisz, ze &6" + playerItemsCount + " &cprzedmiotow",
                "&7z &6Ekwipunku &cgracza &e" + target.getName() + " &7zostanie usunietych.",
                "",
                "&4&lPAMIETAJ !!! &cTa operacja jest nieodwracalna i moze popsuc komus rozgrywke",
                "&cna serwerze. &4&lUZYWAC TYLKO W UZASADNIONYCH PRZYPADKACH !!!"
        )).addTagInt("itemCount", playerItemsCount).addGlowing().toItemStack().clone());

        gui.setItem(11, new ItemBuilder(Material.EMERALD).setName("&cWyczysc &5Enderchest &cgracza &e" + target.getName()).setLore(Arrays.asList(
                "&7Uzywajac tego sprawisz, ze &6" + playerEnderchestItemsCount + " &cprzedmiotow",
                "&7z &5Enderchestu &cgracza &e" + target.getName() + " &7zostanie usunietych.",
                "",
                "&4&lPAMIETAJ !!! &cTa operacja jest nieodwracalna i moze popsuc komus rozgrywke",
                "&cna serwerze. &4&lUZYWAC TYLKO W UZASADNIONYCH PRZYPADKACH !!!"
        )).addTagInt("itemCount", playerEnderchestItemsCount).addGlowing().toItemStack().clone());

        gui.setItem(12, new ItemBuilder(Material.CHEST).setName("&cWyczysc &6Magazyn &cgracza &e" + target.getName()).setLore(Arrays.asList(
                "&7Uzywajac tego sprawisz, ze &cprzedmioty &7z wybranego",
                "&6Magazynu &cgracza &e" + target.getName() + " &7zostana usuniete.",
                "",
                "&4&lPAMIETAJ !!! &cTa operacja jest nieodwracalna i moze popsuc komus rozgrywke",
                "&cna serwerze. &4&lUZYWAC TYLKO W UZASADNIONYCH PRZYPADKACH !!!"
        )).addGlowing().toItemStack().clone());

        gui.setItem(13, new ItemBuilder(Material.GRASS).setName("&cResetuj &bOsiagniecia &cgracza &e" + target.getName()).setLore(Arrays.asList(
                "&7Uzywajac tego sprawisz, ze &cwybrane &7osiagniecia",
                "&cgracza &e" + target.getName() + " &7zostana zresetowane.",
                "",
                "&4&lPAMIETAJ !!! &cTa operacja jest nieodwracalna i moze popsuc komus rozgrywke",
                "&cna serwerze. &4&lUZYWAC TYLKO W UZASADNIONYCH PRZYPADKACH !!!"
        )).addGlowing().toItemStack().clone());

        gui.setItem(14, new ItemBuilder(Material.BOOK).setName("&cUstaw/Resetuj &aMisje &cgracza &e" + target.getName()).setLore(Arrays.asList(
                "&7Otwiera GUI do ustawiania &aMisji &7gracza &e" + target.getName(),
                "",
                "&4&lPAMIETAJ !!! &cTa operacja jest nieodwracalna i moze popsuc komus rozgrywke",
                "&cna serwerze. &4&lUZYWAC TYLKO W UZASADNIONYCH PRZYPADKACH !!!"
        )).addGlowing().toItemStack().clone());

        gui.setItem(15, new ItemBuilder(Material.EXP_BOTTLE).setName("&cUstaw &ePoziom &ci EXP'a &cgracza &e" + target.getName()).setLore(Arrays.asList(
                "&7Otwiera GUI do ustawiania &ePoziomu &7i EXP'a &7gracza &e" + target.getName(),
                "",
                "&4&lPAMIETAJ !!! &cTa operacja jest nieodwracalna i moze popsuc komus rozgrywke",
                "&cna serwerze. &4&lUZYWAC TYLKO W UZASADNIONYCH PRZYPADKACH !!!"
        )).addGlowing().toItemStack().clone());

        gui.setItem(16, new ItemBuilder(Material.PAPER).setName("&cUstaw &2&l$ &ci &4&lH&6&lS &cgracza &e" + target.getName()).setLore(Arrays.asList(
                "&7Otwiera GUI do ustawiania &2&l$ &7i &4&lH&6&lS &7gracza &e" + target.getName(),
                "",
                "&4&lPAMIETAJ !!! &cTa operacja jest nieodwracalna i moze popsuc komus rozgrywke",
                "&cna serwerze. &4&lUZYWAC TYLKO W UZASADNIONYCH PRZYPADKACH !!!"
        )).addGlowing().toItemStack().clone());

        gui.setItem(26, new ItemBuilder(Material.BEDROCK).setName("&4&lUSUN KONTO GRACZA &6&l" + target.getName()).setLore(Arrays.asList(
                "&7Usuwa calkowicie i nie odwracalnie konto gracza &e" + target.getName(),
                "",
                "&4&lPAMIETAJ !!! &cTa operacja jest nieodwracalna i moze popsuc komus rozgrywke",
                "&cna serwerze. &4&lUZYWAC TYLKO W UZASADNIONYCH PRZYPADKACH !!!"
        )).addGlowing().toItemStack().clone());


        player.openInventory(gui);
    }

    private String getMagazynItems(final int magazyn, final MagazynierUser user) {
        switch (magazyn) {
            case 0:
                return user.getMagazyn1();
            case 1:
                return user.getMagazyn2();
            case 2:
                return user.getMagazyn3();
            case 3:
                return user.getMagazyn4();
            case 4:
                return user.getMagazyn5();
            default:
                return "";
        }
    }

    public void openMagazyny(final Player player, final UUID uuid) {
        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&6&lMagazyny &c&l- &6&l" + uuid.toString()));
        final MagazynierUser user = rpgcore.getMagazynierNPC().find(uuid);
        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setName(" ").toItemStack());
        }

        for (int i = 0; i < 5; i++) {
            gui.setItem(11 + i, new ItemBuilder(Material.CHEST).setName("&6Magazyn #" + (i + 1)).setLore(Arrays.asList(
                    "&7Wyglada na to, ze ten gracz &4&lNIE ODBLOKOWAL",
                    "&7jeszcze tego magazynu"
            )).addTagBoolean("unlocked", false).toItemStack().clone());
        }

        for (int i = 0; i < user.getUnlocked(); i++) {

            int itemCount;
            try {
                itemCount = this.countPlayerItems(Utils.itemStackArrayFromBase64(this.getMagazynItems(i, user)));
            } catch (IOException e) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&cWystapil blad podczas ladowania przedmiotow z &4Magazynu #" + (i + 1) + " &cgracza &6" + uuid.toString() + "&c!"));
                return;
            }

            gui.setItem(11 + i, new ItemBuilder(Material.CHEST).setName("&6Magazyn #" + (i + 1)).setLore(Arrays.asList(
                    "&7Kliknij, zeby usunac ten magazyn.",
                    "",
                    "&7Ilosc przedmiotow w srodku: &e" + itemCount,
                    "",
                    "&4&lPAMIETAJ !!! &cTa operacja jest nieodwracalna i moze popsuc komus rozgrywke",
                    "&cna serwerze. &4&lUZYWAC TYLKO W UZASADNIONYCH PRZYPADKACH !!!"
            )).addTagBoolean("unlocked", true).addTagInt("itemCount", itemCount).toItemStack().clone());
        }

        player.openInventory(gui);
    }

    public void openOsiagniecia(final Player player, final UUID uuid) {
        final Inventory gui = Bukkit.createInventory(null, 45, Utils.format("&6&lOsiagniecia &4&l- &6&l" + uuid.toString()));
        final OsUser osUser = rpgcore.getOsManager().find(uuid);
        for (int i = 0; i < 45; i++) {
            if (i % 2 == 0) {
                gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName(" ").toItemStack());
            } else {
                gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName(" ").toItemStack());
            }
        }

        gui.setItem(10, new ItemBuilder(Material.DIAMOND_SWORD).setName("&cZabici Gracze").setLore(Arrays.asList(
                "&7Wykonane: &6" + osUser.getGracze() + "&7/&69",
                "",
                "&8Kliknij, zeby zobaczyc wiecej szczegolow."
        )).hideFlag().toItemStack().clone());
        gui.setItem(11, new ItemBuilder(Material.GOLD_SWORD).setName("&cZabite Potwory").setLore(Arrays.asList(
                "&7Wykonane: &6" + osUser.getMoby() + "&7/&69",
                "",
                "&8Kliknij, zeby zobaczyc wiecej szczegolow."
        )).hideFlag().toItemStack().clone());
        gui.setItem(12, new ItemBuilder(Material.NETHER_STAR).setName("&cZniszczone Kamienie Metin").setLore(Arrays.asList(
                "&7Wykonane: &6" + osUser.getMetiny() + "&7/&69",
                "",
                "&8Kliknij, zeby zobaczyc wiecej szczegolow."
        )).toItemStack().clone());

        gui.setItem(14, new ItemBuilder(Material.CHEST).setName("&cOtworzone Skrzynki").setLore(Arrays.asList(
                "&7Wykonane: &6" + osUser.getSkrzynki() + "&7/&69",
                "",
                "&8Kliknij, zeby zobaczyc wiecej szczegolow."
        )).toItemStack().clone());
        gui.setItem(15, new ItemBuilder(Material.DIAMOND_BLOCK).setName("&cZnalezione Niesamowite Przedmioty").setLore(Arrays.asList(
                "&7Wykonane: &6" + osUser.getNiesy() + "&7/&69",
                "",
                "&8Kliknij, zeby zobaczyc wiecej szczegolow."
        )).toItemStack().clone());
        gui.setItem(16, new ItemBuilder(Material.ANVIL).setName("&cUdane Ulepszenia u Kowala").setLore(Arrays.asList(
                "&7Wykonane: &6" + osUser.getUlepszenia() + "&7/&69",
                "",
                "&8Kliknij, zeby zobaczyc wiecej szczegolow."
        )).toItemStack().clone());

        gui.setItem(28, new ItemBuilder(Material.FISHING_ROD).setName("&cUdane Polowy").setLore(Arrays.asList(
                "&7Wykonane: &6" + osUser.getRybak() + "&7/&69",
                "",
                "&8Kliknij, zeby zobaczyc wiecej szczegolow."
        )).hideFlag().toItemStack().clone());
        gui.setItem(29, new ItemBuilder(Material.DIAMOND_PICKAXE).setName("&cWydobyte Rudy").setLore(Arrays.asList(
                "&7Wykonane: &6" + osUser.getGornik() + "&7/&69",
                "",
                "&8Kliknij, zeby zobaczyc wiecej szczegolow."
        )).hideFlag().toItemStack().clone());

        player.openInventory(gui);
    }

    public void openGracze(final Player player, final UUID uuid) {
        final Inventory gui = Bukkit.createInventory(null, InventoryType.HOPPER, Utils.format("&6&lOsiagniecia &8&l>> &6&lGracze &4&l- &6&l" + uuid.toString()));
        final OsUser osUser = rpgcore.getOsManager().find(uuid);

        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName(" ").toItemStack());
        }

        gui.setItem(1, new ItemBuilder(Material.SIGN).setName("&6&lUstaw Postep Misji").setLore(Arrays.asList(
                "&7Aktualnie: &6" + osUser.getGracze() + "&7/&69",
                "&6LMB &7- zeby dodac 1 do misji",
                "&6RMB &7- zeby odjac 1 od misji"
        )).toItemStack().clone());
        gui.setItem(3, new ItemBuilder(Material.SIGN).setName("&6&lUstaw Postep Osiagniec").setLore(Arrays.asList(
                "&7Zabitych Graczy: &6" + osUser.getGraczeProgress(),
                "&6LMB &7- zeby dodac 1 do postepu",
                "&6RMB &7- zeby odjac 1 od postepu",
                "&6Shift + LMB &7- zeby dodac 10 do postepu",
                "&6Shift + RMB &7- zeby odjac 10 od postepu"
        )).toItemStack().clone());

        player.openInventory(gui);
    }

    public void openMoby(final Player player, final UUID uuid) {
        final Inventory gui = Bukkit.createInventory(null, InventoryType.HOPPER, Utils.format("&6&lOsiagniecia &8&l>> &6&lMoby &4&l- &6&l" + uuid.toString()));
        final OsUser osUser = rpgcore.getOsManager().find(uuid);

        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName(" ").toItemStack());
        }

        gui.setItem(1, new ItemBuilder(Material.SIGN).setName("&6&lUstaw Postep Misji").setLore(Arrays.asList(
                "&7Aktualnie: &6" + osUser.getMoby() + "&7/&69",
                "&6LMB &7- zeby dodac 1 do misji",
                "&6RMB &7- zeby odjac 1 od misji"
        )).toItemStack().clone());
        gui.setItem(3, new ItemBuilder(Material.SIGN).setName("&6&lUstaw Postep Osiagniec").setLore(Arrays.asList(
                "&7Zabite Moby: &6" + osUser.getMobyProgress(),
                "&6LMB &7- zeby dodac 10 do postepu",
                "&6RMB &7- zeby odjac 10 od postepu",
                "&6Shift + LMB &7- zeby dodac 100 do postepu",
                "&6Shift + RMB &7- zeby odjac 100 od postepu"
        )).toItemStack().clone());

        player.openInventory(gui);
    }

    public void openMetin(final Player player, final UUID uuid) {
        final Inventory gui = Bukkit.createInventory(null, InventoryType.HOPPER, Utils.format("&6&lOsiagniecia &8&l>> &6&lMetin &4&l- &6&l" + uuid.toString()));
        final OsUser osUser = rpgcore.getOsManager().find(uuid);

        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName(" ").toItemStack());
        }

        gui.setItem(1, new ItemBuilder(Material.SIGN).setName("&6&lUstaw Postep Misji").setLore(Arrays.asList(
                "&7Aktualnie: &6" + osUser.getMetiny() + "&7/&69",
                "&6LMB &7- zeby dodac 1 do misji",
                "&6RMB &7- zeby odjac 1 od misji"
        )).toItemStack().clone());
        gui.setItem(3, new ItemBuilder(Material.SIGN).setName("&6&lUstaw Postep Osiagniec").setLore(Arrays.asList(
                "&7Zniszczonych Metinow: &6" + osUser.getMetinyProgress(),
                "&6LMB &7- zeby dodac 1 do postepu",
                "&6RMB &7- zeby odjac 1 od postepu",
                "&6Shift + LMB &7- zeby dodac 10 do postepu",
                "&6Shift + RMB &7- zeby odjac 10 od postepu"
        )).toItemStack().clone());

        player.openInventory(gui);
    }

    public void openSkrzynie(final Player player, final UUID uuid) {
        final Inventory gui = Bukkit.createInventory(null, InventoryType.HOPPER, Utils.format("&6&lOsiagniecia &8&l>> &6&lSkrzynie &4&l- &6&l" + uuid.toString()));
        final OsUser osUser = rpgcore.getOsManager().find(uuid);

        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName(" ").toItemStack());
        }

        gui.setItem(1, new ItemBuilder(Material.SIGN).setName("&6&lUstaw Postep Misji").setLore(Arrays.asList(
                "&7Aktualnie: &6" + osUser.getSkrzynki() + "&7/&69",
                "&6LMB &7- zeby dodac 1 do misji",
                "&6RMB &7- zeby odjac 1 od misji"
        )).toItemStack().clone());
        gui.setItem(3, new ItemBuilder(Material.SIGN).setName("&6&lUstaw Postep Osiagniec").setLore(Arrays.asList(
                "&7Otworzone Skrzynie: &6" + osUser.getSkrzynkiProgress(),
                "&6LMB &7- zeby dodac 10 do postepu",
                "&6RMB &7- zeby odjac 10 od postepu",
                "&6Shift + LMB &7- zeby dodac 100 do postepu",
                "&6Shift + RMB &7- zeby odjac 100 od postepu"
        )).toItemStack().clone());

        player.openInventory(gui);
    }

    public void openNies(final Player player, final UUID uuid) {
        final Inventory gui = Bukkit.createInventory(null, InventoryType.HOPPER, Utils.format("&6&lOsiagniecia &8&l>> &6&lNies &4&l- &6&l" + uuid.toString()));
        final OsUser osUser = rpgcore.getOsManager().find(uuid);

        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName(" ").toItemStack());
        }

        gui.setItem(1, new ItemBuilder(Material.SIGN).setName("&6&lUstaw Postep Misji").setLore(Arrays.asList(
                "&7Aktualnie: &6" + osUser.getNiesy() + "&7/&69",
                "&6LMB &7- zeby dodac 1 do misji",
                "&6RMB &7- zeby odjac 1 od misji"
        )).toItemStack().clone());
        gui.setItem(3, new ItemBuilder(Material.SIGN).setName("&6&lUstaw Postep Osiagniec").setLore(Arrays.asList(
                "&7Znalezione Niesamowite Przedmioty: &6" + osUser.getNiesyProgress(),
                "&6LMB &7- zeby dodac 1 do postepu",
                "&6RMB &7- zeby odjac 1 od postepu",
                "&6Shift + LMB &7- zeby dodac 10 do postepu",
                "&6Shift + RMB &7- zeby odjac 10 od postepu"
        )).toItemStack().clone());

        player.openInventory(gui);
    }

    public void openKowal(final Player player, final UUID uuid) {
        final Inventory gui = Bukkit.createInventory(null, InventoryType.HOPPER, Utils.format("&6&lOsiagniecia &8&l>> &6&lKowal &4&l- &6&l" + uuid.toString()));
        final OsUser osUser = rpgcore.getOsManager().find(uuid);

        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName(" ").toItemStack());
        }

        gui.setItem(1, new ItemBuilder(Material.SIGN).setName("&6&lUstaw Postep Misji").setLore(Arrays.asList(
                "&7Aktualnie: &6" + osUser.getUlepszenia() + "&7/&69",
                "&6LMB &7- zeby dodac 1 do misji",
                "&6RMB &7- zeby odjac 1 od misji"
        )).toItemStack().clone());
        gui.setItem(3, new ItemBuilder(Material.SIGN).setName("&6&lUstaw Postep Osiagniec").setLore(Arrays.asList(
                "&7Udane Ulepszenia u Kowala: &6" + osUser.getUlepszeniaProgress(),
                "&6LMB &7- zeby dodac 1 do postepu",
                "&6RMB &7- zeby odjac 1 od postepu",
                "&6Shift + LMB &7- zeby dodac 10 do postepu",
                "&6Shift + RMB &7- zeby odjac 10 od postepu"
        )).toItemStack().clone());

        player.openInventory(gui);
    }

    public void openRybak(final Player player, final UUID uuid) {
        final Inventory gui = Bukkit.createInventory(null, InventoryType.HOPPER, Utils.format("&6&lOsiagniecia &8&l>> &6&lRybak &4&l- &6&l" + uuid.toString()));
        final OsUser osUser = rpgcore.getOsManager().find(uuid);

        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName(" ").toItemStack());
        }

        gui.setItem(1, new ItemBuilder(Material.SIGN).setName("&6&lUstaw Postep Misji").setLore(Arrays.asList(
                "&7Aktualnie: &6" + osUser.getRybak() + "&7/&69",
                "&6LMB &7- zeby dodac 1 do misji",
                "&6RMB &7- zeby odjac 1 od misji"
        )).toItemStack().clone());
        gui.setItem(3, new ItemBuilder(Material.SIGN).setName("&6&lUstaw Postep Osiagniec").setLore(Arrays.asList(
                "&7Udane Polowy: &6" + osUser.getRybakProgress(),
                "&6LMB &7- zeby dodac 10 do postepu",
                "&6RMB &7- zeby odjac 10 od postepu",
                "&6Shift + LMB &7- zeby dodac 100 do postepu",
                "&6Shift + RMB &7- zeby odjac 100 od postepu"
        )).toItemStack().clone());

        player.openInventory(gui);
    }

    public void openGornik(final Player player, final UUID uuid) {
        final Inventory gui = Bukkit.createInventory(null, InventoryType.HOPPER, Utils.format("&6&lOsiagniecia &8&l>> &6&lGornik &4&l- &6&l" + uuid.toString()));
        final OsUser osUser = rpgcore.getOsManager().find(uuid);

        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName(" ").toItemStack());
        }

        gui.setItem(1, new ItemBuilder(Material.SIGN).setName("&6&lUstaw Postep Misji").setLore(Arrays.asList(
                "&7Aktualnie: &6" + osUser.getGornik() + "&7/&69",
                "&6LMB &7- zeby dodac 1 do misji",
                "&6RMB &7- zeby odjac 1 od misji"
        )).toItemStack().clone());
        gui.setItem(3, new ItemBuilder(Material.SIGN).setName("&6&lUstaw Postep Osiagniec").setLore(Arrays.asList(
                "&7Wydobyte Rudy: &6" + osUser.getGornikProgress(),
                "&6LMB &7- zeby dodac 10 do postepu",
                "&6RMB &7- zeby odjac 10 od postepu",
                "&6Shift + LMB &7- zeby dodac 100 do postepu",
                "&6Shift + RMB &7- zeby odjac 100 od postepu"
        )).toItemStack().clone());

        player.openInventory(gui);
    }


    public void openMisje(final Player player, final UUID uuid) {
        final Inventory gui = Bukkit.createInventory(null, 54, Utils.format("&a&lMisje &4&l- &6&l" + uuid.toString()));
        final User user = rpgcore.getUserManager().find(uuid);
        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 11).setName(" ").toItemStack());
        }

        if (rpgcore.getBaoManager().isNotRolled(uuid)) {
            gui.setItem(10, new ItemBuilder(Material.BOOK).setName("&7Ustaw &d&lBao &7gracza &6&l" + user.getName()).setLore(Arrays.asList(
                    "&7Wyglada na to, ze ten gracz",
                    "&4&lNIE WYLOSOWAL &7jeszcze bonusow",
                    "&5Stolu Magii&7!"
            )).addGlowing().toItemStack().clone());
        } else {
            final BaoUser bao = rpgcore.getBaoManager().find(uuid).getBaoUser();
            gui.setItem(10, new ItemBuilder(Material.BOOK).setName("&7Ustaw &d&lBao &7gracza &6&l" + user.getName()).setLore(Arrays.asList(
                    "&5&lStol Magii &7gracza:",
                    "&c" + bao.getBonus1() + ": &6" + bao.getValue1(),
                    "&c" + bao.getBonus2() + ": &6" + bao.getValue2(),
                    "&c" + bao.getBonus3() + ": &6" + bao.getValue3(),
                    "&c" + bao.getBonus4() + ": &6" + bao.getValue4(),
                    "&c" + bao.getBonus5() + ": &6" + bao.getValue5()
            )).addGlowing().toItemStack().clone());
        }

        gui.setItem(11, new ItemBuilder(Material.BOOK).setName("&7Ustaw &6&lBonusy &7gracza &6&l" + user.getName()).setLore(Arrays.asList(
                "&7Kliknij zeby zobaczyc rozpiske wszystkich",
                "&6bonusow &7gracza."
        )).addGlowing().toItemStack().clone());

        final PustelnikUser pustelnik = rpgcore.getPustelnikNPC().find(uuid);
        final PustelnikMissions pustelnikMission = PustelnikMissions.getById(pustelnik.getMissionId());
        gui.setItem(12, new ItemBuilder(Material.BOOK).setName("&7Ustaw &e&lPustelnika &7gracza &6&l" + user.getName()).setLore(Arrays.asList(
                "&7Kliknij, zeby ustawic postep misji/misje",
                "&7podanego gracza u &e&lPustelnika&7.",
                "",
                "&f&lAktualna Misja",
                "&7Misja: &e" + (pustelnikMission == null ? "Brak" : pustelnikMission.getItem(pustelnik.getProgress()).getItemMeta().getDisplayName()),
                "&7  - ID: &e" + (pustelnikMission == null ? "-1" : pustelnikMission.getId()),
                "&7  - Szansa: &e" + (pustelnikMission == null ? "-1" : pustelnikMission.getChance()) + "%",
                "&7  - Wymagana Ilosc: &e" + (pustelnikMission == null ? "-1" : pustelnikMission.getReqAmount()),
                "&7Postep: &e" + pustelnik.getProgress() + "&7/&e" + (pustelnikMission == null ? 0 : pustelnikMission.getReqAmount()) + "&7(&e" + DoubleUtils.round(((double) pustelnik.getProgress() / (pustelnikMission == null ? 0 : pustelnikMission.getReqAmount())) * 100, 2) + "%&7)"
        )).addGlowing().toItemStack().clone());

        final MistrzYangUser mistrzYang = rpgcore.getMistrzYangNPC().find(uuid);
        final MistrzYangMissions mistrzYangMission = MistrzYangMissions.getById(mistrzYang.getMissionId());
        gui.setItem(13, new ItemBuilder(Material.BOOK).setName("&7Ustaw &c&lMistrz Yang &7gracza &6&l" + user.getName()).setLore(Arrays.asList(
                "&7Kliknij, zeby ustawic postep misji/misje",
                "&7podanego gracza u &c&lMistrza Yang&7.",
                "",
                "&f&lAktualna Misja",
                "&7Misja: &e" + (mistrzYangMission == null ? "Brak" : mistrzYangMission.getItem(mistrzYang.getReqAmount(), mistrzYang.getProgress()).getItemMeta().getDisplayName()),
                "&7  - ID: &e" + (mistrzYangMission == null ? "-1" : mistrzYangMission.getId()),
                "&7  - Szansa: &e" + (mistrzYangMission == null ? "-1" : mistrzYangMission.getChance()) + "%",
                "&7  - Wymagana Ilosc: &e" + mistrzYang.getReqAmount(),
                "&7Postep: &e" + mistrzYang.getProgress() + "&7/&e" + mistrzYang.getReqAmount() + "&7(&e" + DoubleUtils.round(((double) mistrzYang.getProgress() / mistrzYang.getReqAmount()) * 100, 2) + "%&7)"
        )).addGlowing().toItemStack().clone());

//        final CzarownicaUser czarownica = rpgcore.getCzarownicaNPC().find(uuid);
//        final CzarownicaMissions czarownicaMission = CzarownicaMissions.getMissionById(czarownica.getMission());
        final List<String> lore2 = new ArrayList<>();
//        if (czarownicaMission != null) {
//            final LinkedHashMap<ItemStack, Integer> reqMap = czarownicaMission.getReqProgressMap(czarownica);
//            czarownicaMission.fix(czarownica);
//            reqMap.forEach((key, value) -> lore2.add("&7    - &c" + key.getItemMeta().getDisplayName() + "&7: &e" + value));
//            lore2.add("&7Postep:");
//            czarownica.getProgressMap().forEach((key, value) -> lore2.add("&7  -&c" + key.getItemMeta().getDisplayName() + ": &e" + value + "&7/&e" + reqMap.get(key) + "&7(&e" + DoubleUtils.round(((double) value / reqMap.get(key)) * 100, 2) + "%&7)"));
//        }
//        gui.setItem(14, new ItemBuilder(Material.BOOK).setName("&7Ustaw &5&lCzarownica &7gracza &6&l" + user.getName()).setLoreCrafting(Arrays.asList(
//                "&7Kliknij, zeby ustawic postep misji/misje",
//                "&7podanego gracza u &5&lCzarownicy&7.",
//                "",
//                "&f&lAktualna Misja",
//                "&7Misja: &e" + (czarownicaMission == null ? "Brak" : czarownicaMission.getMissionItem(czarownica).getItemMeta().getDisplayName()),
//                "&7  - ID: &e" + (czarownicaMission == null ? "-1" : czarownicaMission.getMissionId()),
//                "&7  - Wymagana Ilosc: "
//        ), lore2).addGlowing().toItemStack().clone());

        gui.setItem(15, new ItemBuilder(Material.BOOK).setName("&7Ustaw &7&lDuszolog &7gracza &6&l" + user.getName()).setLore(Arrays.asList(
                "&7Kliknij, zeby ustawic postep misji/misje",
                "&7podanego gracza u &7&lDuszologa&7."
        )).addGlowing().toItemStack().clone());

        final GornikUser gornik = rpgcore.getGornikNPC().find(uuid);
        final GornikMissions gornikMission = GornikMissions.getById(gornik.getMission());
        gui.setItem(16, new ItemBuilder(Material.BOOK).setName("&7Ustaw &6&lGornik &7gracza &6&l" + user.getName()).setLore(Arrays.asList(
                "&7Kliknij, zeby ustawic postep misji/misje",
                "&7podanego gracza u &6&lGornika&7.",
                "",
                "&f&lStatystyki",
                "&7Misja: &e" + gornik.getMission() + "&7/&e28 &7(&e" + DoubleUtils.round(((double) gornik.getMission() / 28) * 100, 2) + "%&7)",
                "&7  - Dodatkowy Czas: &a+" + (gornikMission == null ? "0" : (gornikMission.getBonusTime() / 1000)) + "s",
                "&7  - Silny Na Ludzi (Misja): &e+" + (gornikMission == null ? "0" : gornikMission.getSilnyNaPotwory()) + "%",
                "&7  - Def Na Moby (Misja): &e+" + (gornikMission == null ? "0" : gornikMission.getDefNaMoby()) + "%",
                "&7Postep: &e" + gornik.getProgress() + "&7/&e" + (gornikMission == null ? 0 : gornikMission.getReqAmount()) + "&7(&e" + DoubleUtils.round(((double) gornik.getProgress() / (gornikMission == null ? 0 : gornikMission.getReqAmount())) * 100, 2) + "%&7)",
                "&7Dodatkowy Czas (User): &a+" + (gornik.getMaxTimeLeft() / 1000) + "s",
                "&7Silny Na Potwory (User): &e+" + gornik.getSilnyNaMoby() + "%",
                "&7Def Na Moby (User): &e+" + gornik.getDefNaMoby() + "%",
                "&7Darmowe Wejscie: &e" + (gornik.getFreePass() <= System.currentTimeMillis())
        )).addGlowing().toItemStack().clone());

        final HandlarzUser handlarz = rpgcore.getHandlarzNPC().find(uuid);
        gui.setItem(19, new ItemBuilder(Material.BOOK).setName("&7Ustaw &a&lHandlarz &7gracza &6&l" + user.getName()).setLore(Arrays.asList(
                "&7Kliknij, zeby ustawic postep misji/misje",
                "&7podanego gracza u &a&lHandlarza&7.",
                "",
                "&f&lStatystyki",
                "&7Sprzedane Przedmioty: &e" + handlarz.getSprzedane(),
                "&7Czarny Market: &e" + (handlarz.isBlack_market() ? "&aTak" : "&cNie"),
                "&7Przedmioty z Dziennego Sklepu: &4&lSOON",
                "&7Przedmioty z Czarnego Marketu: &4&lSOON",
                "&7Nastepny Reset: &4&lSOON"
        )).addGlowing().toItemStack().clone());

        final KolekcjonerUser kolekcjoner = rpgcore.getKolekcjonerNPC().find(uuid).getKolekcjonerUser();
        final KolekcjonerMissions kolekcjonerMission = KolekcjonerMissions.getByNumber(kolekcjoner.getMission());
        lore2.clear();
        if (kolekcjonerMission != null) {
            for (int i = 0; i < kolekcjonerMission.getReqItems().length; i++) {
                lore2.add("&7  - " + kolekcjonerMission.getReqItems()[i].getItemMeta().getDisplayName() + (kolekcjoner.getMissionProgress().get(i) ? " &a✓" : " &c✘"));
            }
        }
        lore2.add("&7Defensywa Na Ludzi (User): &e+" + kolekcjoner.getDefNaLudzi() + "%");
        lore2.add("&7Silny Na Ludzi (User): &e+" + kolekcjoner.getSilnyNaLudzi() + "%");
        lore2.add("&7Szczescie (User): &e+" + kolekcjoner.getSzczescie());
        gui.setItem(20, new ItemBuilder(Material.BOOK).setName("&7Ustaw &e&lKolekcjoner &7gracza &6&l" + user.getName()).setLoreCrafting(Arrays.asList(
                "&7Kliknij, zeby ustawic postep misji/misje",
                "&7podanego gracza u &e&lKolekcjonera&7.",
                "",
                "&f&lStatystyki",
                "&7Misja: &e" + kolekcjoner.getMission(),
                "&7  - Defensywa Na Ludzi (Misja): &e+" + (kolekcjonerMission == null ? "0" : kolekcjonerMission.getDefNaLudzi()) + "%",
                "&7  - Silny Na Ludzi (Misja): &e+" + (kolekcjonerMission == null ? "0" : kolekcjonerMission.getSilnyNaLudzi()) + "%",
                "&7  - Szczescie (Misja): &e+" + (kolekcjonerMission == null ? "0" : kolekcjonerMission.getSzczescie()),
                "&7Postep:"
        ), lore2).addGlowing().toItemStack().clone());

        final LesnikUser lesnik = rpgcore.getLesnikNPC().find(uuid).getUser();
        final LesnikMissions lesnikMission = LesnikMissions.getByNumber(lesnik.getMission());
        gui.setItem(21, new ItemBuilder(Material.BOOK).setName("&7Ustaw &2&lLesnik &7gracza &6&l" + user.getName()).setLore(Arrays.asList(
                "&7Kliknij, zeby ustawic postep misji/misje",
                "&7podanego gracza u &2&lLesnika&7.",
                "",
                "&f&lStatystyki",
                "&7Misja: &e" + lesnik.getMission(),
                "&7  - Szansa na Przyjecie: &e" + (lesnikMission == null ? "-1" : lesnikMission.getChance()) + "%",
                "&7  - Wymagana Ilosc: &e" + (lesnikMission == null ? "-1" : lesnikMission.getReqAmount()),
                "&7  - Defensywa Na Ludzi (Misja): &e+" + (lesnikMission == null ? "0" : lesnikMission.getDefNaLudzi()) + "%",
                "&7  - Przeszycie Bloku Ciosu (Misja): &e+" + (lesnikMission == null ? "0" : lesnikMission.getPrzeszywka()) + "%",
                "&7  - Szansa Na Wzmocnienie Ciosu Krytycznego (Misja): &e+" + (lesnikMission == null ? "0" : lesnikMission.getWzmKryta()),
                "&7Defensywa Na Ludzi (User): &e+" + lesnik.getDefNaLudzi() + "%",
                "&7Przeszycie Bloku Ciosu (User): &e+" + lesnik.getPrzeszycie() + "%",
                "&7Szansa Na Wzmocnienie Ciosu Krytycznego (User): &e+" + lesnik.getWzmKryta(),
                "&7Cooldown: &e" + Utils.durationToString(lesnik.getCooldown(), false),
                "&7Postep: &e" + lesnik.getProgress() + "&7/&e" + (lesnikMission == null ? 0 : lesnikMission.getReqAmount()) + "&7(&e" + DoubleUtils.round(((double) lesnik.getProgress() / (lesnikMission == null ? 0 : lesnikMission.getReqAmount())) * 100, 2) + "%&7)"
        )).addGlowing().toItemStack().clone());

        final LowcaUser lowca = rpgcore.getLowcaNPC().find(uuid).getLowcaUser();
        final LowcaMissions lowcaMission = LowcaMissions.getMission(lowca.getMission());
        gui.setItem(22, new ItemBuilder(Material.BOOK).setName("&7Ustaw &4&lLowca &7gracza &6&l" + user.getName()).setLore(Arrays.asList(
                "&7Kliknij, zeby ustawic postep misji/misje",
                "&7podanego gracza u &4&lLowcy&7.",
                "",
                "&f&lStatystyki",
                "&7Misja: &e" + lowca.getMission(),
                "&7  - Wymagany Przedmiot: &e" + (lowcaMission == null ? "Brak" : lowcaMission.getReqItem().getItemMeta().getDisplayName()),
                "&7  - Wymagana Ilosc: &e" + (lowcaMission == null ? "-1" : lowcaMission.getReqAmount()),
                "&7  - Szansa na Przyjecie: &e" + (lowcaMission == null ? "-1" : lowcaMission.getAcceptPercentage()) + "%",
                "&7  - Szybkosc (Misja): &e+" + (lowcaMission == null ? "0" : lowcaMission.getSzybkosc()),
                "&7  - Szczescie (Misja): &e+" + (lowcaMission == null ? "0" : lowcaMission.getSzczescie()),
                "&7  - Dodatkowe Obrazenia (Misja): &e+" + (lowcaMission == null ? "0" : lowcaMission.getDodatkoweDmg()),
                "&7Szybkosc (User): &e+" + lowca.getSzybkosc(),
                "&7Szczescie (User): &e+" + lowca.getSzczescie(),
                "&7Dodatkowe Obrazenina (User): &e+" + lowca.getDodatkoweDmg(),
                "&7Postep: &e" + lowca.getProgress() + "&7/&e" + (lowcaMission == null ? 0 : lowcaMission.getReqAmount()) + " &7(&e" + DoubleUtils.round(((double) lowca.getProgress() / (lowcaMission == null ? 0 : lowcaMission.getReqAmount())) * 100, 2) + "%&7)"
        )).addGlowing().toItemStack().clone());

        final MagazynierUser magazynier = rpgcore.getMagazynierNPC().find(uuid);
        final MagazynierMissions magazynierMission1 = MagazynierMissions.getMissionById(magazynier.getMissions().getMission1());
        final MagazynierMissions magazynierMission2 = MagazynierMissions.getMissionById(magazynier.getMissions().getMission2());
        final MagazynierMissions magazynierMission3 = MagazynierMissions.getMissionById(magazynier.getMissions().getMission3());
        final MagazynierMissions magazynierMission4 = MagazynierMissions.getMissionById(magazynier.getMissions().getMission4());
        final MagazynierMissions magazynierMission5 = MagazynierMissions.getMissionById(magazynier.getMissions().getMission5());
        gui.setItem(23, new ItemBuilder(Material.BOOK).setName("&7Ustaw &b&lMagazynier &7gracza &6&l" + user.getName()).setLore(Arrays.asList(
                "&7Kliknij, zeby ustawic postep misji/misje",
                "&7podanego gracza u &b&lMagazyniera&7.",
                "",
                "&f&lStatystyki",
                "&7Komenda /magazyny: &e" + (magazynier.isRemoteCommand() ? "&aTak" : "&cNie"),
                "&7Odblokowane magazyny: &e" + magazynier.getUnlocked() + "&7/&e5",
                "&7Ilosc Punktow: &e" + magazynier.getPoints() + " pkt",
                "&7Reset Misji: &e" + Utils.durationToString(System.currentTimeMillis() - magazynier.getResetTime(), false),
                "",
                "&7Misja 1: &e" + (magazynierMission1 == null ? "Brak" : magazynierMission1.getMissionItem().getItemMeta().getDisplayName()) + (magazynier.getMissions().getSelectedMission() == (magazynierMission1 == null ? -1 : magazynierMission1.getId()) ? " &a✓" : ""),
                "&7  - Wymagana Ilosc: &e" + (magazynierMission1 == null ? "-1" : magazynierMission1.getReqAmount()),
                "&7  - Punkty: &e+" + (magazynierMission1 == null ? "0" : magazynierMission1.getPoints()),
                "&7  - Nagroda (Item): &e" + ((magazynierMission1 == null ? null : magazynierMission1.getItemReward()) == null ? "Brak" : magazynierMission1.getItemReward().getItemMeta().getDisplayName() + " &7x&e " + magazynierMission1.getItemReward().getAmount()),
                "&7Misja 2: &e" + (magazynierMission2 == null ? "Brak" : magazynierMission2.getMissionItem().getItemMeta().getDisplayName()) + (magazynier.getMissions().getSelectedMission() == (magazynierMission2 == null ? -1 : magazynierMission2.getId()) ? " &a✓" : ""),
                "&7  - Wymagana Ilosc: &e" + (magazynierMission2 == null ? "-1" : magazynierMission2.getReqAmount()),
                "&7  - Punkty: &e+" + (magazynierMission2 == null ? "0" : magazynierMission2.getPoints()),
                "&7  - Nagroda (Item): &e" + ((magazynierMission2 == null ? null : magazynierMission2.getItemReward()) == null ? "Brak" : magazynierMission2.getItemReward().getItemMeta().getDisplayName() + " &7x&e " + magazynierMission2.getItemReward().getAmount()),
                "&7Misja 3: &e" + (magazynierMission3 == null ? "Brak" : magazynierMission3.getMissionItem().getItemMeta().getDisplayName()) + (magazynier.getMissions().getSelectedMission() == (magazynierMission3 == null ? -1 : magazynierMission3.getId()) ? " &a✓" : ""),
                "&7  - Wymagana Ilosc: &e" + (magazynierMission3 == null ? "-1" : magazynierMission3.getReqAmount()),
                "&7  - Punkty: &e+" + (magazynierMission3 == null ? "0" : magazynierMission3.getPoints()),
                "&7  - Nagroda (Item): &e" + ((magazynierMission3 == null ? null : magazynierMission3.getItemReward()) == null ? "Brak" : magazynierMission3.getItemReward().getItemMeta().getDisplayName() + " &7x&e " + magazynierMission3.getItemReward().getAmount()),
                "&7Misja 4: &e" + (magazynierMission4 == null ? "Brak" : magazynierMission4.getMissionItem().getItemMeta().getDisplayName()) + (magazynier.getMissions().getSelectedMission() == (magazynierMission4 == null ? -1 : magazynierMission4.getId()) ? " &a✓" : ""),
                "&7  - Wymagana Ilosc: &e" + (magazynierMission4 == null ? "-1" : magazynierMission4.getReqAmount()),
                "&7  - Punkty: &e+" + (magazynierMission4 == null ? "0" : magazynierMission4.getPoints()),
                "&7  - Nagroda (Item): &e" + ((magazynierMission4 == null ? null : magazynierMission4.getItemReward()) == null ? "Brak" : magazynierMission4.getItemReward().getItemMeta().getDisplayName() + " &7x&e " + magazynierMission4.getItemReward().getAmount()),
                "&7Misja 5: &e" + (magazynierMission5 == null ? "Brak" : magazynierMission5.getMissionItem().getItemMeta().getDisplayName()) + (magazynier.getMissions().getSelectedMission() == (magazynierMission5 == null ? -1 : magazynierMission5.getId()) ? " &a✓" : ""),
                "&7  - Wymagana Ilosc: &e" + (magazynierMission5 == null ? "-1" : magazynierMission5.getReqAmount()),
                "&7  - Punkty: &e+" + (magazynierMission5 == null ? "0" : magazynierMission5.getPoints()),
                "&7  - Nagroda (Item): &e" + ((magazynierMission5 == null ? null : magazynierMission5.getItemReward()) == null ? "Brak" : magazynierMission5.getItemReward().getItemMeta().getDisplayName() + " &7x&e " + magazynierMission5.getItemReward().getAmount())
        )).addGlowing().toItemStack().clone());

        final MedrzecUser medrzec = rpgcore.getMedrzecNPC().find(uuid);
        gui.setItem(24, new ItemBuilder(Material.BOOK).setName("&7Ustaw &c&lMedrzec &7gracza &6&l" + user.getName()).setLore(Arrays.asList(
                "&7Kliknij, zeby ustawic postep misji/misje",
                "&7podanego gracza u &c&lMedrca&7.",
                "",
                "&f&lStatystyki",
                "&7Dodatkowe Hp: &e+" + DoubleUtils.round(medrzec.getBonus() / 2.0, 2) + "❤&7/&e+20❤"
        )).addGlowing().toItemStack().clone());

        final MetinologUser metinolog = rpgcore.getMetinologNPC().find(uuid).getMetinologUser();
        final MetinologMissionKill metinologMissionKill = MetinologMissionKill.getMission(metinolog.getPostepKill());
        final MetinologMissionGive metinologMissionGive = MetinologMissionGive.getMission(metinolog.getPostepGive());
        gui.setItem(25, new ItemBuilder(Material.BOOK).setName("&7Ustaw &b&lMetinolog &7gracza &6&l" + user.getName()).setLore(Arrays.asList(
                "&7Kliknij, zeby ustawic postep misji/misje",
                "&7podanego gracza u &b&lMetinologa&7.",
                "",
                "&f&lStatystyki",
                "&7Misja (Zniszcz): &e" + metinolog.getPostepKill(),
                "&7  - Wymagana Ilosc: &e" + (metinologMissionKill == null ? "-1" : metinologMissionKill.getReqAmount()),
                "&7  - Mapa: &e" + (metinologMissionKill == null ? "Brak" : metinologMissionKill.getMapa()),
                "&7  - Srednia Defensywa (Misja): &e+" + (metinologMissionKill == null ? "0" : metinologMissionKill.getSrOdpo()) + "%",
                "&7  - Przeszycie Bloku Ciosu (Misja): &e+" + (metinologMissionKill == null ? "0" : metinologMissionKill.getPrzeszycie()) + "%",
                "&7Postep: &e" + metinolog.getPostepMisjiKill() + "&7/&e" + (metinologMissionKill == null ? 0 : metinologMissionKill.getReqAmount()) + "&7 (" + DoubleUtils.round(metinolog.getPostepMisjiKill() / (double) (metinologMissionKill == null ? 0 : metinologMissionKill.getReqAmount()) * 100, 2) + "%)",
                "&7Misja (Zniszcz): &e" + metinolog.getPostepGive(),
                "&7  - Wymagana Ilosc: &e" + (metinologMissionGive == null ? "-1" : metinologMissionGive.getReqAmount()),
                "&7  - Mapa: &e" + (metinologMissionGive == null ? "Brak" : metinologMissionGive.getMapa()),
                "&7  - Srednia Defensywa (Misja): &e+" + (metinologMissionGive == null ? "0" : metinologMissionGive.getSrOdpo()) + "%",
                "&7  - Dodatkowe Obrazenia (Misja): &e+" + (metinologMissionGive == null ? "0" : metinologMissionGive.getDodatkoweDmg()),
                "&7Postep: &e" + metinolog.getPostepMisjiGive() + "&7/&e" + (metinologMissionGive == null ? 0 : metinologMissionGive.getReqAmount()) + "&7 (" + DoubleUtils.round(metinolog.getPostepMisjiGive() / (double) (metinologMissionGive == null ? 0 : metinologMissionGive.getReqAmount()) * 100, 2) + "%)",
                "",
                "&7Srednia Defensywa (User): &e+" + metinolog.getSrOdpo() + "%",
                "&7Dodatkowe Obrazenia (User): &e+" + metinolog.getDodatkowedmg(),
                "&7Przeszycie Bloku Ciosu (User): &e+" + metinolog.getPrzeszycie() + "%"
        )).addGlowing().toItemStack().clone());

        final PrzyrodnikUser przyrodnik = rpgcore.getPrzyrodnikNPC().find(uuid).getUser();
        final PrzyrodnikMissions przyrodnikMission = PrzyrodnikMissions.getByNumber(przyrodnik.getMission());
        gui.setItem(28, new ItemBuilder(Material.BOOK).setName("&7Ustaw &6&lPrzyrodnik &7gracza &6&l" + user.getName()).setLore(Arrays.asList(
                "&7Kliknij, zeby ustawic postep misji/misje",
                "&7podanego gracza u &6&lPrzyrodnika&7.",
                "",
                "&f&lStatystyki",
                "&7Misja: &e" + przyrodnik.getMission(),
                "&7  - Wymagany Przedmiot: &e" + (przyrodnikMission == null ? "Brak" : przyrodnikMission.getItemStack().getItemMeta().getDisplayName()),
                "&7  - Wymagana Ilosc: &e" + (przyrodnikMission == null ? "-1" : przyrodnikMission.getReqAmount()),
                "&7  - Szansa Na Przyjecie: &e" + (przyrodnikMission == null ? "-1" : przyrodnikMission.getAcceptPercent()) + "%",
                "&7  - Szansa Na Drop (Podstawa): &e" + (przyrodnikMission == null ? "-1" : przyrodnikMission.getDropChance()) + "%",
                "&7  - Wypada z: &e" + (przyrodnikMission == null ? "Brak" : przyrodnikMission.getMobName()),
                "&7  - Srednie Obrazenia (Misja): &e+" + (przyrodnikMission == null ? "0" : przyrodnikMission.getDmg()) + "%",
                "&7  - Srednia Defensywa (Misja): &e+" + (przyrodnikMission == null ? "0" : przyrodnikMission.getDef()) + "%",
                "&7Srednie Obrazenia (User): &e+" + przyrodnik.getDmg() + "%",
                "&7Srednia Defensywa (User): &e+" + przyrodnik.getDef() + "%",
                "&7Postep: &e" + przyrodnik.getProgress() + "&7/&e" + (przyrodnikMission == null ? 0 : przyrodnikMission.getReqAmount()) + "&7 (" + DoubleUtils.round(przyrodnik.getProgress() / (double) (przyrodnikMission == null ? 0 : przyrodnikMission.getReqAmount()) * 100, 2) + "%)"
        )).addGlowing().toItemStack().clone());

        final RybakUser rybak = rpgcore.getRybakNPC().find(uuid);
        final StaruszekUser staruszek = rybak.getStaruszekUser();
        final StaruszekMissions staruszekMission = StaruszekMissions.getMissionById(staruszek.getMission());
        gui.setItem(29, new ItemBuilder(Material.BOOK).setName("&7Ustaw &3&lRybak &7gracza &6&l" + user.getName()).setLore(Arrays.asList(
                "&7Kliknij, zeby ustawic postep misji/misje",
                "&7podanego gracza u &3&lRybaka&7.",
                "",
                "&6&lStaruszek",
                "&7Misja: &e" + staruszek.getMission(),
                "&7  - Wymagany Przedmiot: &e" + (staruszekMission == null ? "Brak" : staruszekMission.getMissionItem(staruszek.getProgress()).getItemMeta().getDisplayName()),
                "&7  - Wymagana Ilosc: &e" + (staruszekMission == null ? "-1" : staruszekMission.getReqAmount()),
                "&7  - Srednia Defensywa (Misja): &e+" + (staruszekMission == null ? "0" : staruszekMission.getSrDef()) + "%",
                "&7Srednia Defensywa (User): &e+" + staruszek.getSrDef() + "%",
                "&7Postep: &e" + staruszek.getProgress() + "&7/&e" + (staruszekMission == null ? "0" : staruszekMission.getReqAmount()) + "&7 (" + DoubleUtils.round(staruszek.getProgress() / (double) (staruszekMission == null ? 0 : staruszekMission.getReqAmount()) * 100, 2) + "%)"
        )).addGlowing().toItemStack().clone());

        final WyslannikUser wyslannik = rpgcore.getWyslannikNPC().find(uuid);
        final WyslannikMissionKillMob wyslannikMissionKillMob = WyslannikMissionKillMob.getByMission(wyslannik.getKillMobsMission());
        final WyslannikMissionKillBoss wyslannikMissionKillBoss = WyslannikMissionKillBoss.getByMission(wyslannik.getKillBossMission());
        gui.setItem(30, new ItemBuilder(Material.BOOK).setName("&7Ustaw &c&lWyslannik, &7gracza &6&l" + user.getName()).setLore(Arrays.asList(
                "&7Kliknij, zeby ustawic postep misji/misje",
                "&7podanego gracza u &c&lWyslannika&7.",
                "",
                "&f&lStatystyki",
                "&7Misja (Moby): &e" + wyslannik.getKillMobsMission(),
                "&7  - Wymagany Mob: &e" + (wyslannikMissionKillMob == null ? "Brak" : wyslannikMissionKillMob.getMobName()),
                "&7  - Wymagana Ilosc: &e" + (wyslannikMissionKillMob == null ? "-1" : wyslannikMissionKillMob.getMobsAmount()),
                "&7  - Srednie Obrazenia (Misja): &e" + (wyslannikMissionKillMob == null ? "0" : wyslannikMissionKillMob.getSredniDMG()) + "%",
                "&7Postep: &e" + wyslannik.getKillMobsMissionProgress() + "&7/&e" + (wyslannikMissionKillMob == null ? "0" : wyslannikMissionKillMob.getMobsAmount()) + "&7 (" + DoubleUtils.round(wyslannik.getKillMobsMissionProgress() / (double) (wyslannikMissionKillMob == null ? 0 : wyslannikMissionKillMob.getMobsAmount()) * 100, 2) + "%)",
                "",
                "&7Misja (Bossy): &e" + wyslannik.getKillBossMission(),
                "&7  - Wymagany Boss: &e" + (wyslannikMissionKillBoss == null ? "Brak" : wyslannikMissionKillBoss.getBossName()),
                "&7  - Wymagana Ilosc: &e" + (wyslannikMissionKillBoss == null ? "-1" : wyslannikMissionKillBoss.getBossAmount()),
                "&7  - Srednia Defensywa (Misja): &e" + (wyslannikMissionKillBoss == null ? "0" : wyslannikMissionKillBoss.getSredniDEF()) + "%",
                "&7Postep: &e" + wyslannik.getKillBossMissionProgress() + "&7/&e" + (wyslannikMissionKillBoss == null ? "0" : wyslannikMissionKillBoss.getBossAmount()) + "&7 (" + DoubleUtils.round(wyslannik.getKillBossMissionProgress() / (double) (wyslannikMissionKillBoss == null ? 0 : wyslannikMissionKillBoss.getBossAmount()) * 100, 2) + "%)",
                ""
        )).addGlowing().toItemStack().clone());

        final WyszkolenieUser wyszkolenie = rpgcore.getWyszkolenieManager().find(uuid);
        final DrzewkoWyszkoleniaUser drzewko = wyszkolenie.getDrzewkoWyszkoleniaUser();
        gui.setItem(31, new ItemBuilder(Material.BOOK).setName("&7Ustaw &3&lWyszkolenie &7gracza &6&l" + user.getName()).setLore(Arrays.asList(
                "&7Kliknij, zeby ustawic postep misji/misje",
                "&7podanego gracza u &3&lWyszkolenia&7.",
                "",
                "&f&lStatystyki",
                "&7Punkty (Total): &e" + wyszkolenie.getTotalPoints(),
                "&7Punkty (Dostepne): &e" + wyszkolenie.getPunkty(),
                "&7Skonczone: " + (wyszkolenie.isMaxed() ? "&aTak" : "&cNie"),
                "&7Srednie Obrazenia: &e+" + wyszkolenie.getSrDmg() + "%",
                "&7Srednia Defensywa: &e+" + wyszkolenie.getSrDef() + "%",
                "&7Szansa Na Cios Krytyczny: &e+" + wyszkolenie.getKryt() + "%",
                "&7Szczescie: &e+" + wyszkolenie.getSzczescie(),
                "&7Blok Ciosu: &e+" + wyszkolenie.getBlok() + "%",
                "&7Dodatkowe HP: &e+" + wyszkolenie.getHp() + "❤",
                "",
                "&f&lDrzewko Wyszkolenia",
                drzewko.getD1().getName() + ": " + (drzewko.getD1().isUnlocked() ? "&aTak &7(&e" + drzewko.getD1().getUpgradeLvl() + "&7/&e" + drzewko.getD1().getMaxUpgradeLvl() + "&7)" : "&cNie"),
                drzewko.getD2().getName() + ": " + (drzewko.getD2().isUnlocked() ? "&aTak &7(&e" + drzewko.getD2().getUpgradeLvl() + "&7/&e" + drzewko.getD2().getMaxUpgradeLvl() + "&7)" : "&cNie"),
                "&7  - &f&lGora",
                drzewko.getDg1().getName() + ": " + (drzewko.getDg1().isUnlocked() ? "&aTak &7(&e" + drzewko.getDg1().getUpgradeLvl() + "&7/&e" + drzewko.getDg1().getMaxUpgradeLvl() + "&7)" : "&cNie"),
                drzewko.getDg2().getName() + ": " + (drzewko.getDg2().isUnlocked() ? "&aTak &7(&e" + drzewko.getDg2().getUpgradeLvl() + "&7/&e" + drzewko.getDg2().getMaxUpgradeLvl() + "&7)" : "&cNie"),
                drzewko.getDg3().getName() + ": " + (drzewko.getDg3().isUnlocked() ? "&aTak &7(&e" + drzewko.getDg3().getUpgradeLvl() + "&7/&e" + drzewko.getDg3().getMaxUpgradeLvl() + "&7)" : "&cNie"),
                "&7  - &f&lLewo",
                drzewko.getDl1().getName() + ": " + (drzewko.getDl1().isUnlocked() ? "&aTak &7(&e" + drzewko.getDl1().getUpgradeLvl() + "&7/&e" + drzewko.getDl1().getMaxUpgradeLvl() + "&7)" : "&cNie"),
                drzewko.getDl2().getName() + ": " + (drzewko.getDl2().isUnlocked() ? "&aTak &7(&e" + drzewko.getDl2().getUpgradeLvl() + "&7/&e" + drzewko.getDl2().getMaxUpgradeLvl() + "&7)" : "&cNie"),
                drzewko.getDl3().getName() + ": " + (drzewko.getDl3().isUnlocked() ? "&aTak &7(&e" + drzewko.getDl3().getUpgradeLvl() + "&7/&e" + drzewko.getDl3().getMaxUpgradeLvl() + "&7)" : "&cNie"),
                drzewko.getDl4().getName() + ": " + (drzewko.getDl4().isUnlocked() ? "&aTak &7(&e" + drzewko.getDl4().getUpgradeLvl() + "&7/&e" + drzewko.getDl4().getMaxUpgradeLvl() + "&7)" : "&cNie"),
                drzewko.getDl5().getName() + ": " + (drzewko.getDl5().isUnlocked() ? "&aTak &7(&e" + drzewko.getDl5().getUpgradeLvl() + "&7/&e" + drzewko.getDl5().getMaxUpgradeLvl() + "&7)" : "&cNie"),
                drzewko.getDl6().getName() + ": " + (drzewko.getDl6().isUnlocked() ? "&aTak &7(&e" + drzewko.getDl6().getUpgradeLvl() + "&7/&e" + drzewko.getDl6().getMaxUpgradeLvl() + "&7)" : "&cNie"),
                "&7  - &f&lPrawo",
                drzewko.getDp1().getName() + ": " + (drzewko.getDp1().isUnlocked() ? "&aTak &7(&e" + drzewko.getDp1().getUpgradeLvl() + "&7/&e" + drzewko.getDp1().getMaxUpgradeLvl() + "&7)" : "&cNie"),
                drzewko.getDp2().getName() + ": " + (drzewko.getDp2().isUnlocked() ? "&aTak &7(&e" + drzewko.getDp2().getUpgradeLvl() + "&7/&e" + drzewko.getDp2().getMaxUpgradeLvl() + "&7)" : "&cNie"),
                drzewko.getDp3().getName() + ": " + (drzewko.getDp3().isUnlocked() ? "&aTak &7(&e" + drzewko.getDp3().getUpgradeLvl() + "&7/&e" + drzewko.getDp3().getMaxUpgradeLvl() + "&7)" : "&cNie"),
                drzewko.getDp4().getName() + ": " + (drzewko.getDp4().isUnlocked() ? "&aTak &7(&e" + drzewko.getDp4().getUpgradeLvl() + "&7/&e" + drzewko.getDp4().getMaxUpgradeLvl() + "&7)" : "&cNie"),
                drzewko.getDp5().getName() + ": " + (drzewko.getDp5().isUnlocked() ? "&aTak &7(&e" + drzewko.getDp5().getUpgradeLvl() + "&7/&e" + drzewko.getDp5().getMaxUpgradeLvl() + "&7)" : "&cNie"),
                drzewko.getDp6().getName() + ": " + (drzewko.getDp6().isUnlocked() ? "&aTak &7(&e" + drzewko.getDp6().getUpgradeLvl() + "&7/&e" + drzewko.getDp6().getMaxUpgradeLvl() + "&7)" : "&cNie")
        )).addGlowing().toItemStack().clone());


        player.openInventory(gui);
    }

    public void openBao(final Player player, final UUID uuid) {
        final Inventory gui = Bukkit.createInventory(null, 36, Utils.format("&a&lMisje &8&l>> &d&lBao &4&l- &e&l" + uuid.toString()));
        final BaoUser user = rpgcore.getBaoManager().find(uuid).getBaoUser();

        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 11).setName(" ").toItemStack());
        }

        gui.setItem(11, new ItemBuilder(Material.BOOK).setName("&c" + user.getBonus1()).setLore(Arrays.asList(
                "&7Kliknij, zeby zmienic bonus na kolejny."
        )).toItemStack().clone());
        gui.setItem(20, new ItemBuilder(Material.SIGN).setName("&cWartosc Bonusu 1: &6" + user.getValue1()).setLore(Arrays.asList(
                "&6LPM &7zeby dodac 1",
                "&6PPM &7zeby odjac 1",
                "&6SHIFT + LPM &7zeby dodac 10",
                "&6SHIFT + PPM &7zeby odjac 10"
        )).toItemStack().clone());

        gui.setItem(12, new ItemBuilder(Material.BOOK).setName("&c" + user.getBonus2()).setLore(Arrays.asList(
                "&7Kliknij, zeby zmienic bonus na kolejny."
        )).toItemStack().clone());
        gui.setItem(21, new ItemBuilder(Material.SIGN).setName("&cWartosc Bonusu 2: &6" + user.getValue2()).setLore(Arrays.asList(
                "&6LPM &7zeby dodac 1",
                "&6PPM &7zeby odjac 1",
                "&6SHIFT + LPM &7zeby dodac 10",
                "&6SHIFT + PPM &7zeby odjac 10"
        )).toItemStack().clone());

        gui.setItem(13, new ItemBuilder(Material.BOOK).setName("&c" + user.getBonus3()).setLore(Arrays.asList(
                "&7Kliknij, zeby zmienic bonus na kolejny."
        )).toItemStack().clone());
        gui.setItem(22, new ItemBuilder(Material.SIGN).setName("&cWartosc Bonusu 3: &6" + user.getValue3()).setLore(Arrays.asList(
                "&6LPM &7zeby dodac 1",
                "&6PPM &7zeby odjac 1",
                "&6SHIFT + LPM &7zeby dodac 10",
                "&6SHIFT + PPM &7zeby odjac 10"
        )).toItemStack().clone());

        gui.setItem(14, new ItemBuilder(Material.BOOK).setName("&c" + user.getBonus4()).setLore(Arrays.asList(
                "&7Kliknij, zeby zmienic bonus na kolejny."
        )).toItemStack().clone());
        gui.setItem(23, new ItemBuilder(Material.SIGN).setName("&cWartosc Bonusu 4: &6" + user.getValue4()).setLore(Arrays.asList(
                "&6LPM &7zeby dodac 1",
                "&6PPM &7zeby odjac 1",
                "&6SHIFT + LPM &7zeby dodac 10",
                "&6SHIFT + PPM &7zeby odjac 10"
        )).toItemStack().clone());

        gui.setItem(15, new ItemBuilder(Material.BOOK).setName("&c" + user.getBonus5()).setLore(Arrays.asList(
                "&7Kliknij, zeby zmienic bonus na kolejny."
        )).toItemStack().clone());
        gui.setItem(24, new ItemBuilder(Material.SIGN).setName("&cWartosc Bonusu 5: &6" + user.getValue5()).setLore(Arrays.asList(
                "&6LPM &7zeby dodac 1",
                "&6PPM &7zeby odjac 1",
                "&6SHIFT + LPM &7zeby dodac 10",
                "&6SHIFT + PPM &7zeby odjac 10"
        )).toItemStack().clone());


        player.openInventory(gui);
    }

    public void openBonusy(final Player player, final UUID uuid) {
        final Inventory gui = Bukkit.createInventory(null, 54, Utils.format("&a&lMisje &8&l>> &6&lBonusy &4&l- &e&l" + uuid.toString()));
        final BonusesUser user = rpgcore.getBonusesManager().find(uuid).getBonusesUser();

        gui.setItem(10, new ItemBuilder(Material.BOOK).setName("&7Srednie Obrazenia: &f" + user.getSrednieobrazenia() + "%").setLore(Arrays.asList("&6LMB &7- zeby zwiekszyc o 1", "&6RMB &7- zeby zmniejszyc o 1", "&6SHIFT + LMB &7- zeby zwiekszyc o 10", "&6SHIFT + RMB &7- zeby zmniejszyc o 10", "&6KEYBOARD &7- dodaj 0.1")).toItemStack().clone());
        gui.setItem(11, new ItemBuilder(Material.BOOK).setName("&7Dodatkowe Obrazenia: &f" + user.getDodatkoweobrazenia()).setLore(Arrays.asList("&6LMB &7- zeby zwiekszyc o 1", "&6RMB &7- zeby zmniejszyc o 1", "&6SHIFT + LMB &7- zeby zwiekszyc o 10", "&6SHIFT + RMB &7- zeby zmniejszyc o 10")).toItemStack().clone());
        gui.setItem(12, new ItemBuilder(Material.BOOK).setName("&7Silny Na Ludzi: &f" + user.getSilnynaludzi() + "%").setLore(Arrays.asList("&6LMB &7- zeby zwiekszyc o 1", "&6RMB &7- zeby zmniejszyc o 1", "&6SHIFT + LMB &7- zeby zwiekszyc o 10", "&6SHIFT + RMB &7- zeby zmniejszyc o 10", "&6KEYBOARD &7- dodaj 0.1")).toItemStack().clone());
        gui.setItem(13, new ItemBuilder(Material.BOOK).setName("&7Silny Na Potwory: &f" + user.getSilnynapotwory() + "%").setLore(Arrays.asList("&6LMB &7- zeby zwiekszyc o 1", "&6RMB &7- zeby zmniejszyc o 1", "&6SHIFT + LMB &7- zeby zwiekszyc o 10", "&6SHIFT + RMB &7- zeby zmniejszyc o 10", "&6KEYBOARD &7- dodaj 0.1")).toItemStack().clone());
        gui.setItem(14, new ItemBuilder(Material.BOOK).setName("&7Szansa Na Cios Krytyczny: &f" + user.getSzansanakryta() + "%").setLore(Arrays.asList("&6LMB &7- zeby zwiekszyc o 1", "&6RMB &7- zeby zmniejszyc o 1", "&6SHIFT + LMB &7- zeby zwiekszyc o 10", "&6SHIFT + RMB &7- zeby zmniejszyc o 10", "&6KEYBOARD &7- dodaj 0.1")).toItemStack().clone());
        gui.setItem(15, new ItemBuilder(Material.BOOK).setName("&7Szansa Na Wzmocniony Cios Krytyczny: &f" + user.getSzansanawzmocnieniekryta() + "%").setLore(Arrays.asList("&6LMB &7- zeby zwiekszyc o 1", "&6RMB &7- zeby zmniejszyc o 1", "&6SHIFT + LMB &7- zeby zwiekszyc o 10", "&6SHIFT + RMB &7- zeby zmniejszyc o 10", "&6KEYBOARD &7- dodaj 0.1")).toItemStack().clone());
        gui.setItem(16, new ItemBuilder(Material.BOOK).setName("&7Wzmocnienie Ciosu Krytycznego: &f" + user.getWzmocnienieKryta() + "%").setLore(Arrays.asList("&6LMB &7- zeby zwiekszyc o 1", "&6RMB &7- zeby zmniejszyc o 1", "&6SHIFT + LMB &7- zeby zwiekszyc o 10", "&6SHIFT + RMB &7- zeby zmniejszyc o 10", "&6KEYBOARD &7- dodaj 0.1")).toItemStack().clone());
        gui.setItem(19, new ItemBuilder(Material.BOOK).setName("&7Przeszycie Bloku Ciosu: &f" + user.getPrzeszyciebloku() + "%").setLore(Arrays.asList("&6LMB &7- zeby zwiekszyc o 1", "&6RMB &7- zeby zmniejszyc o 1", "&6SHIFT + LMB &7- zeby zwiekszyc o 10", "&6SHIFT + RMB &7- zeby zmniejszyc o 10", "&6KEYBOARD &7- dodaj 0.1")).toItemStack().clone());
        gui.setItem(20, new ItemBuilder(Material.BOOK).setName("&7Zmniejszone Obrazenia: &f-" + user.getMinussrednieobrazenia() + "%").setLore(Arrays.asList("&6LMB &7- zeby zwiekszyc o 1", "&6RMB &7- zeby zmniejszyc o 1", "&6SHIFT + LMB &7- zeby zwiekszyc o 10", "&6SHIFT + RMB &7- zeby zmniejszyc o 10", "&6KEYBOARD &7- dodaj 0.1")).toItemStack().clone());
        gui.setItem(21, new ItemBuilder(Material.BOOK).setName("&7Zmniejszone Obrazenia W Ludzi: &f-" + user.getMinusobrazenianaludzi() + "%").setLore(Arrays.asList("&6LMB &7- zeby zwiekszyc o 1", "&6RMB &7- zeby zmniejszyc o 1", "&6SHIFT + LMB &7- zeby zwiekszyc o 10", "&6SHIFT + RMB &7- zeby zmniejszyc o 10", "&6KEYBOARD &7- dodaj 0.1")).toItemStack().clone());
        gui.setItem(22, new ItemBuilder(Material.BOOK).setName("&7Zmniejszone Obrazenia W Potwory: &f-" + user.getMinusobrazenianamoby() + "%").setLore(Arrays.asList("&6LMB &7- zeby zwiekszyc o 1", "&6RMB &7- zeby zmniejszyc o 1", "&6SHIFT + LMB &7- zeby zwiekszyc o 10", "&6SHIFT + RMB &7- zeby zmniejszyc o 10", "&6KEYBOARD &7- dodaj 0.1")).toItemStack().clone());

        gui.setItem(23, new ItemBuilder(Material.BOOK).setName("&7Srednia Defensywa: &f" + user.getSredniadefensywa() + "%").setLore(Arrays.asList("&6LMB &7- zeby zwiekszyc o 1", "&6RMB &7- zeby zmniejszyc o 1", "&6SHIFT + LMB &7- zeby zwiekszyc o 10", "&6SHIFT + RMB &7- zeby zmniejszyc o 10", "&6KEYBOARD &7- dodaj 0.1")).toItemStack().clone());
        gui.setItem(24, new ItemBuilder(Material.BOOK).setName("&7Defensywa Na Ludzi: &f" + user.getDefnaludzi() + "%").setLore(Arrays.asList("&6LMB &7- zeby zwiekszyc o 1", "&6RMB &7- zeby zmniejszyc o 1", "&6SHIFT + LMB &7- zeby zwiekszyc o 10", "&6SHIFT + RMB &7- zeby zmniejszyc o 10", "&6KEYBOARD &7- dodaj 0.1")).toItemStack().clone());
        gui.setItem(25, new ItemBuilder(Material.BOOK).setName("&7Defensywa Na Moby: &f" + user.getDefnamoby() + "%").setLore(Arrays.asList("&6LMB &7- zeby zwiekszyc o 1", "&6RMB &7- zeby zmniejszyc o 1", "&6SHIFT + LMB &7- zeby zwiekszyc o 10", "&6SHIFT + RMB &7- zeby zmniejszyc o 10", "&6KEYBOARD &7- dodaj 0.1")).toItemStack().clone());
        gui.setItem(28, new ItemBuilder(Material.BOOK).setName("&7Blok Ciosu: &f" + user.getBlokciosu() + "%").setLore(Arrays.asList("&6LMB &7- zeby zwiekszyc o 1", "&6RMB &7- zeby zmniejszyc o 1", "&6SHIFT + LMB &7- zeby zwiekszyc o 10", "&6SHIFT + RMB &7- zeby zmniejszyc o 10", "&6KEYBOARD &7- dodaj 0.1")).toItemStack().clone());
        gui.setItem(29, new ItemBuilder(Material.BOOK).setName("&7Zmniejszona Defensywa: &f-" + user.getMinussredniadefensywa() + "%").setLore(Arrays.asList("&6LMB &7- zeby zwiekszyc o 1", "&6RMB &7- zeby zmniejszyc o 1", "&6SHIFT + LMB &7- zeby zwiekszyc o 10", "&6SHIFT + RMB &7- zeby zmniejszyc o 10", "&6KEYBOARD &7- dodaj 0.1")).toItemStack().clone());
        gui.setItem(30, new ItemBuilder(Material.BOOK).setName("&7Zmniejszona Defensywa Przeciwko Ludziom: &f-" + user.getMinusdefnaludzi() + "%").setLore(Arrays.asList("&6LMB &7- zeby zwiekszyc o 1", "&6RMB &7- zeby zmniejszyc o 1", "&6SHIFT + LMB &7- zeby zwiekszyc o 10", "&6SHIFT + RMB &7- zeby zmniejszyc o 10", "&6KEYBOARD &7- dodaj 0.1")).toItemStack().clone());
        gui.setItem(31, new ItemBuilder(Material.BOOK).setName("&7Zmniejszona Defensywa Przeciwko Potworom: &f-" + user.getMinusdefnamoby() + "%").setLore(Arrays.asList("&6LMB &7- zeby zwiekszyc o 1", "&6RMB &7- zeby zmniejszyc o 1", "&6SHIFT + LMB &7- zeby zwiekszyc o 10", "&6SHIFT + RMB &7- zeby zmniejszyc o 10", "&6KEYBOARD &7- dodaj 0.1")).toItemStack().clone());


        gui.setItem(32, new ItemBuilder(Material.BOOK).setName("&7True DMG: &f" + user.getTruedamage()).setLore(Arrays.asList("&6LMB &7- zeby zwiekszyc o 1", "&6RMB &7- zeby zmniejszyc o 1", "&6SHIFT + LMB &7- zeby zwiekszyc o 10", "&6SHIFT + RMB &7- zeby zmniejszyc o 10")).toItemStack().clone());
        gui.setItem(33, new ItemBuilder(Material.BOOK).setName("&7Szansa Na Krwawienie: &f" + user.getSzansanakrwawienie() + "%").setLore(Arrays.asList("&6LMB &7- zeby zwiekszyc o 1", "&6RMB &7- zeby zmniejszyc o 1", "&6SHIFT + LMB &7- zeby zwiekszyc o 10", "&6SHIFT + RMB &7- zeby zmniejszyc o 10", "&6KEYBOARD &7- dodaj 0.1")).toItemStack().clone());
        gui.setItem(34, new ItemBuilder(Material.BOOK).setName("&7Szczescie: &f" + user.getSzczescie()).setLore(Arrays.asList("&6LMB &7- zeby zwiekszyc o 1", "&6RMB &7- zeby zmniejszyc o 1", "&6SHIFT + LMB &7- zeby zwiekszyc o 10", "&6SHIFT + RMB &7- zeby zmniejszyc o 10")).toItemStack().clone());
        gui.setItem(37, new ItemBuilder(Material.BOOK).setName("&7Szansa Na Spowolnienie: &f" + user.getSpowolnienie() + "%").setLore(Arrays.asList("&6LMB &7- zeby zwiekszyc o 1", "&6RMB &7- zeby zmniejszyc o 1", "&6SHIFT + LMB &7- zeby zwiekszyc o 10", "&6SHIFT + RMB &7- zeby zmniejszyc o 10", "&6KEYBOARD &7- dodaj 0.1")).toItemStack().clone());
        gui.setItem(38, new ItemBuilder(Material.BOOK).setName("&7Szansa Na Oslepienie: &f" + user.getOslepienie() + "%").setLore(Arrays.asList("&6LMB &7- zeby zwiekszyc o 1", "&6RMB &7- zeby zmniejszyc o 1", "&6SHIFT + LMB &7- zeby zwiekszyc o 10", "&6SHIFT + RMB &7- zeby zmniejszyc o 10", "&6KEYBOARD &7- dodaj 0.1")).toItemStack().clone());
        gui.setItem(39, new ItemBuilder(Material.BOOK).setName("&7Dodatkowy EXP: &f" + user.getDodatkowyExp() + "%").setLore(Arrays.asList("&6LMB &7- zeby zwiekszyc o 1", "&6RMB &7- zeby zmniejszyc o 1", "&6SHIFT + LMB &7- zeby zwiekszyc o 10", "&6SHIFT + RMB &7- zeby zmniejszyc o 10", "&6KEYBOARD &7- dodaj 0.1")).toItemStack().clone());
        gui.setItem(40, new ItemBuilder(Material.BOOK).setName("&7Szansa Na Przebicie Pancerza: &f" + user.getPrzebiciePancerza() + "%").setLore(Arrays.asList("&6LMB &7- zeby zwiekszyc o 1", "&6RMB &7- zeby zmniejszyc o 1", "&6SHIFT + LMB &7- zeby zwiekszyc o 10", "&6SHIFT + RMB &7- zeby zmniejszyc o 10", "&6KEYBOARD &7- dodaj 0.1")).toItemStack().clone());
        gui.setItem(41, new ItemBuilder(Material.BOOK).setName("&7Wampiryzm: &f" + user.getWampiryzm() + "%").setLore(Arrays.asList("&6LMB &7- zeby zwiekszyc o 1", "&6RMB &7- zeby zmniejszyc o 1", "&6SHIFT + LMB &7- zeby zwiekszyc o 10", "&6SHIFT + RMB &7- zeby zmniejszyc o 10", "&6KEYBOARD &7- dodaj 0.1")).toItemStack().clone());
        gui.setItem(42, new ItemBuilder(Material.BOOK).setName("&7Dodatkowe Obrazenia W Kamienie Metin: &f" + user.getDmgMetiny()).setLore(Arrays.asList("&6LMB &7- zeby zwiekszyc o 1", "&6RMB &7- zeby zmniejszyc o 1", "&6SHIFT + LMB &7- zeby zwiekszyc o 10", "&6SHIFT + RMB &7- zeby zmniejszyc o 10")).toItemStack().clone());

        gui.setItem(43, new ItemBuilder(Material.BOOK).setName("&7Dodatkowe HP: &f" + user.getDodatkowehp() + "❤").setLore(Arrays.asList("&6LMB &7- zeby zwiekszyc o 1", "&6RMB &7- zeby zmniejszyc o 1", "&6SHIFT + LMB &7- zeby zwiekszyc o 10", "&6SHIFT + RMB &7- zeby zmniejszyc o 10")).toItemStack().clone());

        gui.setItem(49, new ItemBuilder(Material.BOOK).setName("&7Szybkosc: &f" + user.getSzybkosc()).setLore(Arrays.asList("&6LMB &7- zeby zwiekszyc o 1", "&6RMB &7- zeby zmniejszyc o 1", "&6SHIFT + LMB &7- zeby zwiekszyc o 10", "&6SHIFT + RMB &7- zeby zmniejszyc o 10")).toItemStack().clone());

        player.openInventory(gui);
    }

    public void openPustelnik(final Player player, final UUID uuid) {
        final Inventory gui = Bukkit.createInventory(null, InventoryType.HOPPER, Utils.format("&a&lMisje &8&l>> &e&lPustelnik &4&l- &e&l" + uuid.toString()));
        final PustelnikUser user = rpgcore.getPustelnikNPC().find(uuid);
        gui.setItem(1, new ItemBuilder(Material.SIGN).setName("&6&lUstaw Misje").setLore(Arrays.asList(
                "&7Aktualna: &e" + user.getMissionId(),
                "&6LMB &7- zeby dodac 1.",
                "&6RMB &7- zeby odjac 1."
        )).toItemStack().clone());
        gui.setItem(3, new ItemBuilder(Material.SIGN).setName("&6&lUstaw Postep").setLore(Arrays.asList(
                "&7Aktualna: &e" + user.getProgress(),
                "&6LMB &7- zeby dodac 1.",
                "&6RMB &7- zeby odjac 1."
        )).toItemStack().clone());

        player.openInventory(gui);
    }

    public void openMistrzYang(final Player player, final UUID uuid) {
        final Inventory gui = Bukkit.createInventory(null, InventoryType.HOPPER, Utils.format("&a&lMisje &8&l>> &c&lMistrz Yang &4&l- &e&l" + uuid.toString()));
        final MistrzYangUser user = rpgcore.getMistrzYangNPC().find(uuid);
        gui.setItem(1, new ItemBuilder(Material.SIGN).setName("&6&lUstaw Misje").setLore(Arrays.asList(
                "&7Aktualna: &e" + user.getMissionId(),
                "&6LMB &7- zeby dodac 1.",
                "&6RMB &7- zeby odjac 1."
        )).toItemStack().clone());
        gui.setItem(3, new ItemBuilder(Material.SIGN).setName("&6&lUstaw Postep").setLore(Arrays.asList(
                "&7Aktualna: &e" + user.getProgress(),
                "&6LMB &7- zeby dodac 1.",
                "&6RMB &7- zeby odjac 1."
        )).toItemStack().clone());

        player.openInventory(gui);
    }

    public void openCzarownica(final Player player, final UUID uuid) {
        final Inventory gui = Bukkit.createInventory(null, 45, Utils.format("&a&lMisje &8&l>> &5&lCzarownica &4&l- &e&l" + uuid.toString()));
        final CzarownicaUser user = rpgcore.getCzarownicaNPC().find(uuid);

        gui.setItem(3, new ItemBuilder(Material.SIGN).setName("&6&lUstaw Misje").setLore(Arrays.asList(
                "&7Aktualna: &e" + user.getMission(),
                "&6LMB &7- zeby dodac 1.",
                "&6RMB &7- zeby odjac 1."
        )).toItemStack().clone());
        gui.setItem(5, new ItemBuilder(Material.SIGN).setName("&6&lUstaw Odblokowanie Craftingu").setLore(Arrays.asList(
                "&7Aktualna: &e" + user.isUnlocked(),
                "&7Kliknij, zeby zmienic"
        )).toItemStack().clone());
        int i = 10;
        for (ItemStack item : user.getProgressMap().keySet()) {
            if (i == 17) i += 11;
            gui.setItem(i, item.clone());
            gui.setItem(i + 9, new ItemBuilder(Material.BOOK).setName("&7Postep: &c" + user.getProgressMap().get(item)).setLore(Arrays.asList(
                    "&6LMB &7- zeby dodac 1.",
                    "&6RMB &7- zeby odjac 1."
            )).toItemStack().clone());
            i++;
        }

        player.openInventory(gui);
    }

    public void openGornikM(final Player player, final UUID uuid) {
        final Inventory gui = Bukkit.createInventory(null, 9, Utils.format("&a&lMisje &8&l>> &6&lGornik &4&l- &e&l" + uuid.toString()));
        final GornikUser user = rpgcore.getGornikNPC().find(uuid);
        gui.setItem(0, new ItemBuilder(Material.SIGN).setName("&6&lUstaw Misje").setLore(Arrays.asList(
                "&7Aktualna: &e" + user.getMission(),
                "&6LMB &7- zeby dodac 1.",
                "&6RMB &7- zeby odjac 1."
        )).toItemStack().clone());
        gui.setItem(1, new ItemBuilder(Material.SIGN).setName("&6&lUstaw Postep").setLore(Arrays.asList(
                "&7Aktualna: &e" + user.getProgress(),
                "&6LMB &7- zeby dodac 1.",
                "&6RMB &7- zeby odjac 1."
        )).toItemStack().clone());
        gui.setItem(2, new ItemBuilder(Material.SIGN).setName("&6&lUstaw Dodatkowy Czas").setLore(Arrays.asList(
                "&7Aktualna: &e" + (user.getMaxTimeLeft() / 1000),
                "&6LMB &7- zeby dodac 1.",
                "&6RMB &7- zeby odjac 1.",
                "&6Shift + LMB &7- zeby dodac 10",
                "&6Shift + RMB &7- zeby odjac 10"
        )).toItemStack().clone());
        gui.setItem(3, new ItemBuilder(Material.SIGN).setName("&6&lUstaw Silny Na Ludzi").setLore(Arrays.asList(
                "&7Aktualna: &e" + user.getSilnyNaMoby(),
                "&6LMB &7- zeby dodac 1.",
                "&6RMB &7- zeby odjac 1.",
                "&6Shift + LMB &7- zeby dodac 10",
                "&6Shift + RMB &7- zeby odjac 10",
                "&6KEYBOARD &7- zeby dodac 0.1"
        )).toItemStack().clone());
        gui.setItem(4, new ItemBuilder(Material.SIGN).setName("&6&lUstaw Def Na Moby").setLore(Arrays.asList(
                "&7Aktualna: &e" + user.getDefNaMoby(),
                "&6LMB &7- zeby dodac 1.",
                "&6RMB &7- zeby odjac 1.",
                "&6Shift + LMB &7- zeby dodac 10",
                "&6Shift + RMB &7- zeby odjac 10",
                "&6KEYBOARD &7- zeby dodac 0.1"
        )).toItemStack().clone());
        gui.setItem(5, new ItemBuilder(Material.SIGN).setName("&6&lUstaw Darmowe Wejscie").setLore(Arrays.asList(
                "&7Aktualna: &e" + (user.getFreePass() <= System.currentTimeMillis()),
                "&6LMB &7- zeby zmienic."
        )).toItemStack().clone());
        player.openInventory(gui);
    }


    public void openKolekcjoner(final Player player, final UUID uuid) {
        final Inventory gui = Bukkit.createInventory(null, 18, Utils.format("&a&lMisje &8&l>> &e&lKolekcjoner &4&l- &e&l" + uuid.toString()));
        final KolekcjonerUser user = rpgcore.getKolekcjonerNPC().find(uuid).getKolekcjonerUser();
        gui.setItem(2, new ItemBuilder(Material.SIGN).setName("&6&lUstaw Misje").setLore(Arrays.asList(
                "&7Aktualna: &e" + user.getMission(),
                "&6LMB &7- zeby dodac 1.",
                "&6RMB &7- zeby odjac 1."
        )).toItemStack().clone());
        gui.setItem(3, new ItemBuilder(Material.SIGN).setName("&6&lUstaw Def Na Ludzi").setLore(Arrays.asList(
                "&7Aktualna: &e" + user.getDefNaLudzi(),
                "&6LMB &7- zeby dodac 1.",
                "&6RMB &7- zeby odjac 1.",
                "&6Shift + LMB &7- zeby dodac 10",
                "&6Shift + RMB &7- zeby odjac 10",
                "&6KEYBOARD &7- zeby dodac 0.1"
        )).toItemStack().clone());

        gui.setItem(5, new ItemBuilder(Material.SIGN).setName("&6&lUstaw Silny Na Ludzi").setLore(Arrays.asList(
                "&7Aktualna: &e" + user.getSilnyNaLudzi(),
                "&6LMB &7- zeby dodac 1.",
                "&6RMB &7- zeby odjac 1.",
                "&6Shift + LMB &7- zeby dodac 10",
                "&6Shift + RMB &7- zeby odjac 10",
                "&6KEYBOARD &7- zeby dodac 0.1"
        )).toItemStack().clone());

        gui.setItem(6, new ItemBuilder(Material.SIGN).setName("&6&lUstaw Szczescie").setLore(Arrays.asList(
                "&7Aktualna: &e" + user.getSzczescie(),
                "&6LMB &7- zeby dodac 1.",
                "&6RMB &7- zeby odjac 1.",
                "&6Shift + LMB &7- zeby dodac 10",
                "&6Shift + RMB &7- zeby odjac 10"
        )).toItemStack().clone());
        int i = 11;
        for (Boolean b : user.getMissionProgress()) {
            gui.setItem(i, new ItemBuilder(Material.BOOK).setName("&7Item " + (i - 10) + ": &e" + b).setLore(Arrays.asList(
                    "&7Kliknij, zeby zmienic wartosc."
            )).toItemStack().clone());
            i++;
        }

        player.openInventory(gui);
    }

    public void openLesnik(final Player player, final UUID uuid) {
        final Inventory gui = Bukkit.createInventory(null, InventoryType.HOPPER, Utils.format("&a&lMisje &8&l>> &2&lLesnik &4&l- &e&l" + uuid.toString()));
        final LesnikUser user = rpgcore.getLesnikNPC().find(uuid).getUser();
        gui.setItem(0, new ItemBuilder(Material.SIGN).setName("&6&lUstaw Misje").setLore(Arrays.asList(
                "&7Aktualna: &e" + user.getMission(),
                "&6LMB &7- zeby dodac 1.",
                "&6RMB &7- zeby odjac 1."
        )).toItemStack().clone());
        gui.setItem(1, new ItemBuilder(Material.SIGN).setName("&6&lUstaw Postep").setLore(Arrays.asList(
                "&7Aktualna: &e" + user.getProgress(),
                "&6LMB &7- zeby dodac 1.",
                "&6RMB &7- zeby odjac 1."
        )).toItemStack().clone());
        gui.setItem(2, new ItemBuilder(Material.SIGN).setName("&6&lUstaw Def Na Ludzi").setLore(Arrays.asList(
                "&7Aktualna: &e" + user.getDefNaLudzi(),
                "&6LMB &7- zeby dodac 1.",
                "&6RMB &7- zeby odjac 1.",
                "&6Shift + LMB &7- zeby dodac 10",
                "&6Shift + RMB &7- zeby odjac 10",
                "&6KEYBOARD &7- zeby dodac 0.1"
        )).toItemStack().clone());
        gui.setItem(3, new ItemBuilder(Material.SIGN).setName("&6&lUstaw Przeszycie Bloku").setLore(Arrays.asList(
                "&7Aktualna: &e" + user.getPrzeszycie(),
                "&6LMB &7- zeby dodac 1.",
                "&6RMB &7- zeby odjac 1.",
                "&6Shift + LMB &7- zeby dodac 10",
                "&6Shift + RMB &7- zeby odjac 10",
                "&6KEYBOARD &7- zeby dodac 0.1"
        )).toItemStack().clone());
        gui.setItem(4, new ItemBuilder(Material.SIGN).setName("&6&lUstaw Szanse Na Wzmocnienie Kryta").setLore(Arrays.asList(
                "&7Aktualna: &e" + user.getWzmKryta(),
                "&6LMB &7- zeby dodac 1.",
                "&6RMB &7- zeby odjac 1.",
                "&6Shift + LMB &7- zeby dodac 10",
                "&6Shift + RMB &7- zeby odjac 10",
                "&6KEYBOARD &7- zeby dodac 0.1"
        )).toItemStack().clone());

        player.openInventory(gui);
    }


    public void openLowca(final Player player, final UUID uuid) {
        final Inventory gui = Bukkit.createInventory(null, InventoryType.HOPPER, Utils.format("&a&lMisje &8&l>> &4&lLowca &4&l- &e&l" + uuid.toString()));
        final LowcaUser user = rpgcore.getLowcaNPC().find(uuid).getLowcaUser();
        gui.setItem(0, new ItemBuilder(Material.SIGN).setName("&6&lUstaw Misje").setLore(Arrays.asList(
                "&7Aktualna: &e" + user.getMission(),
                "&6LMB &7- zeby dodac 1.",
                "&6RMB &7- zeby odjac 1."
        )).toItemStack().clone());
        gui.setItem(1, new ItemBuilder(Material.SIGN).setName("&6&lUstaw Postep").setLore(Arrays.asList(
                "&7Aktualna: &e" + user.getProgress(),
                "&6LMB &7- zeby dodac 1.",
                "&6RMB &7- zeby odjac 1."
        )).toItemStack().clone());
        gui.setItem(2, new ItemBuilder(Material.SIGN).setName("&6&lUstaw Szybkosc").setLore(Arrays.asList(
                "&7Aktualna: &e" + user.getSzybkosc(),
                "&6LMB &7- zeby dodac 1.",
                "&6RMB &7- zeby odjac 1.",
                "&6Shift + LMB &7- zeby dodac 10",
                "&6Shift + RMB &7- zeby odjac 10"
        )).toItemStack().clone());
        gui.setItem(3, new ItemBuilder(Material.SIGN).setName("&6&lUstaw Szczescie").setLore(Arrays.asList(
                "&7Aktualna: &e" + user.getSzczescie(),
                "&6LMB &7- zeby dodac 1.",
                "&6RMB &7- zeby odjac 1.",
                "&6Shift + LMB &7- zeby dodac 10",
                "&6Shift + RMB &7- zeby odjac 10"
        )).toItemStack().clone());
        gui.setItem(4, new ItemBuilder(Material.SIGN).setName("&6&lUstaw Dodatkowe Obrazenia").setLore(Arrays.asList(
                "&7Aktualna: &e" + user.getDodatkoweDmg(),
                "&6LMB &7- zeby dodac 1.",
                "&6RMB &7- zeby odjac 1.",
                "&6Shift + LMB &7- zeby dodac 10",
                "&6Shift + RMB &7- zeby odjac 10"
        )).toItemStack().clone());

        player.openInventory(gui);
    }

    public void openMagazynier(final Player player, final UUID uuid) {
        final Inventory gui = Bukkit.createInventory(null, 9, Utils.format("&a&lMisje &8&l>> &b&lMagazynier &4&l- &e&l" + uuid.toString()));
        final MagazynierUser user = rpgcore.getMagazynierNPC().find(uuid);
        gui.setItem(0, new ItemBuilder(Material.SIGN).setName("&6&lUstaw Punkty").setLore(Arrays.asList(
                "&7Aktualnie: &e" + user.getPoints(),
                "&6LMB &7- zeby dodac 1.",
                "&6RMB &7- zeby odjac 1.",
                "&6Shift + LMB &7- zeby dodac 10",
                "&6Shift + RMB &7- zeby odjac 10"
        )).toItemStack().clone());
        gui.setItem(2, new ItemBuilder(Material.CHEST).setName("&6&lMagazyn 1: &e" + user.isUnlocked1()).setLore(Arrays.asList(
                "&7Kliknij, zeby zmienic i wyczyscic"
        )).toItemStack().clone());
        gui.setItem(3, new ItemBuilder(Material.CHEST).setName("&6&lMagazyn 2: &e" + user.isUnlocked2()).setLore(Arrays.asList(
                "&7Kliknij, zeby zmienic i wyczyscic"
        )).toItemStack().clone());
        gui.setItem(4, new ItemBuilder(Material.CHEST).setName("&6&lMagazyn 3: &e" + user.isUnlocked3()).setLore(Arrays.asList(
                "&7Kliknij, zeby zmienic i wyczyscic"
        )).toItemStack().clone());
        gui.setItem(5, new ItemBuilder(Material.CHEST).setName("&6&lMagazyn 4: &e" + user.isUnlocked4()).setLore(Arrays.asList(
                "&7Kliknij, zeby zmienic i wyczyscic"
        )).toItemStack().clone());
        gui.setItem(6, new ItemBuilder(Material.CHEST).setName("&6&lMagazyn 5: &e" + user.isUnlocked5()).setLore(Arrays.asList(
                "&7Kliknij, zeby zmienic i wyczyscic"
        )).toItemStack().clone());
        gui.setItem(8, new ItemBuilder(Material.BOOK).setName("&7Komenda /magazyny: &e" + user.isRemoteCommand()).setLore(Arrays.asList("&7Kliknij, zeby zmienic.")).toItemStack().clone());

        player.openInventory(gui);
    }

    public void openMedrzec(final Player player, final UUID uuid) {
        final Inventory gui = Bukkit.createInventory(null, InventoryType.HOPPER, Utils.format("&a&lMisje &8&l>> &c&lMedrzec &4&l- &e&l" + uuid.toString()));
        final MedrzecUser user = rpgcore.getMedrzecNPC().find(uuid);

        gui.setItem(2, new ItemBuilder(Material.SIGN).setName("&6&lUstaw Bonus HP").setLore(Arrays.asList(
                "&7Aktualnie: &e" + user.getBonus(),
                "&6LMB &7- zeby dodac 1.",
                "&6RMB &7- zeby odjac 1."
        )).toItemStack().clone());

        player.openInventory(gui);
    }

    public void openMetinolog(final Player player, final UUID uuid) {
        final Inventory gui = Bukkit.createInventory(null, 9, Utils.format("&a&lMisje &8&l>> &b&lMetinolog &4&l- &e&l" + uuid.toString()));
        final MetinologUser user = rpgcore.getMetinologNPC().find(uuid).getMetinologUser();

        gui.setItem(0, new ItemBuilder(Material.SIGN).setName("&6&lUstaw Misje (Kill)").setLore(Arrays.asList(
                "&7Aktualnie: &e" + user.getPostepKill(),
                "&6LMB &7- zeby dodac 1.",
                "&6RMB &7- zeby odjac 1."
        )).toItemStack().clone());
        gui.setItem(1, new ItemBuilder(Material.SIGN).setName("&6&lUstaw Postep (Kill)").setLore(Arrays.asList(
                "&7Aktualnie: &e" + user.getPostepMisjiKill(),
                "&6LMB &7- zeby dodac 1.",
                "&6RMB &7- zeby odjac 1.",
                "&6Shift + LMB &7- zeby dodac 10",
                "&6Shift + RMB &7- zeby odjac 10"
        )).toItemStack().clone());

        gui.setItem(2, new ItemBuilder(Material.SIGN).setName("&6&lUstaw Misje (Give)").setLore(Arrays.asList(
                "&7Aktualnie: &e" + user.getPostepGive(),
                "&6LMB &7- zeby dodac 1.",
                "&6RMB &7- zeby odjac 1."
        )).toItemStack().clone());
        gui.setItem(3, new ItemBuilder(Material.SIGN).setName("&6&lUstaw Postep (Give)").setLore(Arrays.asList(
                "&7Aktualnie: &e" + user.getPostepMisjiGive(),
                "&6LMB &7- zeby dodac 1.",
                "&6RMB &7- zeby odjac 1.",
                "&6Shift + LMB &7- zeby dodac 10",
                "&6Shift + RMB &7- zeby odjac 10"
        )).toItemStack().clone());

        gui.setItem(6, new ItemBuilder(Material.BOOK).setName("&6&lUstaw Srednia Defensywa").setLore(Arrays.asList(
                "&7Aktualnie: &e" + user.getSrOdpo(),
                "&6LMB &7- zeby dodac 1.",
                "&6RMB &7- zeby odjac 1.",
                "&6Shift + LMB &7- zeby dodac 10",
                "&6Shift + RMB &7- zeby odjac 10",
                "&6KEYBOARD &7- zeby dodac 0.1"
        )).toItemStack().clone());
        gui.setItem(7, new ItemBuilder(Material.BOOK).setName("&6&lUstaw Dodatkowe Obrazenia").setLore(Arrays.asList(
                "&7Aktualnie: &e" + user.getDodatkowedmg(),
                "&6LMB &7- zeby dodac 1.",
                "&6RMB &7- zeby odjac 1.",
                "&6Shift + LMB &7- zeby dodac 10",
                "&6Shift + RMB &7- zeby odjac 10"
        )).toItemStack().clone());
        gui.setItem(8, new ItemBuilder(Material.BOOK).setName("&6&lUstaw Przeszycie Bloku").setLore(Arrays.asList(
                "&7Aktualnie: &e" + user.getPrzeszycie(),
                "&6LMB &7- zeby dodac 1.",
                "&6RMB &7- zeby odjac 1.",
                "&6Shift + LMB &7- zeby dodac 10",
                "&6Shift + RMB &7- zeby odjac 10",
                "&6KEYBOARD &7- zeby dodac 0.1"
        )).toItemStack().clone());

        player.openInventory(gui);
    }

    public void openPrzyrodnik(final Player player, final UUID uuid) {
        final Inventory gui = Bukkit.createInventory(null, InventoryType.HOPPER, Utils.format("&a&lMisje &8&l>> &6&lPrzyrodnik &4&l- &e&l" + uuid.toString()));
        final PrzyrodnikUser user = rpgcore.getPrzyrodnikNPC().find(uuid).getUser();

        gui.setItem(0, new ItemBuilder(Material.SIGN).setName("&6&lUstaw Misje").setLore(Arrays.asList(
                "&7Aktualna: &e" + user.getMission(),
                "&6LMB &7- zeby dodac 1.",
                "&6RMB &7- zeby odjac 1."
        )).toItemStack().clone());
        gui.setItem(1, new ItemBuilder(Material.SIGN).setName("&6&lUstaw Postep").setLore(Arrays.asList(
                "&7Aktualna: &e" + user.getProgress(),
                "&6LMB &7- zeby dodac 1.",
                "&6RMB &7- zeby odjac 1."
        )).toItemStack().clone());
        gui.setItem(3, new ItemBuilder(Material.SIGN).setName("&6&lUstaw Srednie DMG").setLore(Arrays.asList(
                "&7Aktualna: &e" + user.getDmg(),
                "&6LMB &7- zeby dodac 1.",
                "&6RMB &7- zeby odjac 1.",
                "&6Shift + LMB &7- zeby dodac 10",
                "&6Shift + RMB &7- zeby odjac 10",
                "&6KEYBOARD &7- zeby dodac 0.1"
        )).toItemStack().clone());
        gui.setItem(4, new ItemBuilder(Material.SIGN).setName("&6&lUstaw Sredni Def").setLore(Arrays.asList(
                "&7Aktualna: &e" + user.getDef(),
                "&6LMB &7- zeby dodac 1.",
                "&6RMB &7- zeby odjac 1.",
                "&6Shift + LMB &7- zeby dodac 10",
                "&6Shift + RMB &7- zeby odjac 10",
                "&6KEYBOARD &7- zeby dodac 0.1"
        )).toItemStack().clone());

        player.openInventory(gui);
    }

    public void openRybakM(final Player player, final UUID uuid) {
        final Inventory gui = Bukkit.createInventory(null, InventoryType.HOPPER, Utils.format("&a&lMisje &8&l>> &3&lRybak &4&l- &e&l" + uuid.toString()));
        final RybakUser rybak = rpgcore.getRybakNPC().find(uuid);
        final StaruszekUser user = rybak.getStaruszekUser();

        gui.setItem(1, new ItemBuilder(Material.SIGN).setName("&6&lUstaw Misje").setLore(Arrays.asList(
                "&7Aktualna: &e" + user.getMission(),
                "&6LMB &7- zeby dodac 1.",
                "&6RMB &7- zeby odjac 1."
        )).toItemStack().clone());
        gui.setItem(2, new ItemBuilder(Material.SIGN).setName("&6&lUstaw Postep").setLore(Arrays.asList(
                "&7Aktualna: &e" + user.getProgress(),
                "&6LMB &7- zeby dodac 1.",
                "&6RMB &7- zeby odjac 1."
        )).toItemStack().clone());
        gui.setItem(3, new ItemBuilder(Material.BOOK).setName("&6&lUstaw Sredni Def").setLore(Arrays.asList(
                "&7Aktualna: &e" + user.getSrDef(),
                "&6LMB &7- zeby dodac 1.",
                "&6RMB &7- zeby odjac 1.",
                "&6Shift + LMB &7- zeby dodac 10",
                "&6Shift + RMB &7- zeby odjac 10",
                "&6KEYBOARD &7- zeby dodac 0.1"
        )).toItemStack().clone());

        player.openInventory(gui);
    }

   public void openWyslannik(final Player player, final UUID uuid) {
        final Inventory gui = Bukkit.createInventory(null, 9, Utils.format("&a&lMisje &8&l>> &c&lWyslannik &4&l- &e&l" + uuid.toString()));
        final WyslannikUser user = rpgcore.getWyslannikNPC().find(uuid);

        gui.setItem(0, new ItemBuilder(Material.SIGN).setName("&6&lUstaw Misje (Mob)").setLore(Arrays.asList(
                "&7Aktualnie: &e" + user.getKillMobsMission(),
                "&6LMB &7- zeby dodac 1.",
                "&6RMB &7- zeby odjac 1."
        )).toItemStack().clone());
        gui.setItem(1, new ItemBuilder(Material.SIGN).setName("&6&lUstaw Postep (Mob)").setLore(Arrays.asList(
                "&7Aktualnie: &e" + user.getKillMobsMissionProgress(),
                "&6LMB &7- zeby dodac 1.",
                "&6RMB &7- zeby odjac 1.",
                "&6Shift + LMB &7- zeby dodac 10",
                "&6Shift + RMB &7- zeby odjac 10"
        )).toItemStack().clone());

        gui.setItem(3, new ItemBuilder(Material.SIGN).setName("&6&lUstaw Misje (Boss)").setLore(Arrays.asList(
                "&7Aktualnie: &e" + user.getKillBossMission(),
                "&6LMB &7- zeby dodac 1.",
                "&6RMB &7- zeby odjac 1."
        )).toItemStack().clone());
        gui.setItem(4, new ItemBuilder(Material.SIGN).setName("&6&lUstaw Postep (Boss)").setLore(Arrays.asList(
                "&7Aktualnie: &e" + user.getKillBossMissionProgress(),
                "&6LMB &7- zeby dodac 1.",
                "&6RMB &7- zeby odjac 1.",
                "&6Shift + LMB &7- zeby dodac 10",
                "&6Shift + RMB &7- zeby odjac 10"
        )).toItemStack().clone());

       gui.setItem(6, new ItemBuilder(Material.BOOK).setName("&6&lUstaw Sredni DMG").setLore(Arrays.asList(
               "&7Aktualna: &e" + user.getSredniDMG(),
               "&6LMB &7- zeby dodac 1.",
               "&6RMB &7- zeby odjac 1.",
               "&6Shift + LMB &7- zeby dodac 10",
               "&6Shift + RMB &7- zeby odjac 10",
               "&6KEYBOARD &7- zeby dodac 0.1"
       )).toItemStack().clone());
       gui.setItem(7, new ItemBuilder(Material.BOOK).setName("&6&lUstaw Sredni Def").setLore(Arrays.asList(
               "&7Aktualna: &e" + user.getSredniDEF(),
               "&6LMB &7- zeby dodac 1.",
               "&6RMB &7- zeby odjac 1.",
               "&6Shift + LMB &7- zeby dodac 10",
               "&6Shift + RMB &7- zeby odjac 10",
               "&6KEYBOARD &7- zeby dodac 0.1"
       )).toItemStack().clone());

        player.openInventory(gui);
    }

    public void openWyszkolenie(final Player player, final UUID uuid) {
        final Inventory gui = Bukkit.createInventory(null, 9, Utils.format("&a&lMisje &8&l>> &3&lWyszkolenie &4&l- &e&l" + uuid.toString()));
        final WyszkolenieUser user = rpgcore.getWyszkolenieManager().find(uuid);
        gui.setItem(0, new ItemBuilder(Material.SIGN).setName("&6&lUstaw Punkty (Total)").setLore(Arrays.asList(
                "&7Aktualnine: &e" + user.getTotalPoints(),
                "&6LMB &7- zeby dodac 1.",
                "&6RMB &7- zeby odjac 1."
        )).toItemStack().clone());
        gui.setItem(1, new ItemBuilder(Material.SIGN).setName("&6&lUstaw Punkty (Dostepne)").setLore(Arrays.asList(
                "&7Aktualnine: &e" + user.getPunkty(),
                "&6LMB &7- zeby dodac 1.",
                "&6RMB &7- zeby odjac 1."
        )).toItemStack().clone());
        gui.setItem(2, new ItemBuilder(Material.SIGN).setName("&6&lUstaw Srednie Obrazenia").setLore(Arrays.asList(
                "&7Aktualnine: &e" + user.getSrDmg(),
                "&6LMB &7- zeby dodac 1.",
                "&6RMB &7- zeby odjac 1."
        )).toItemStack().clone());
        gui.setItem(3, new ItemBuilder(Material.SIGN).setName("&6&lUstaw Srednia Defensywa").setLore(Arrays.asList(
                "&7Aktualnine: &e" + user.getSrDef(),
                "&6LMB &7- zeby dodac 1.",
                "&6RMB &7- zeby odjac 1."
        )).toItemStack().clone());
        gui.setItem(4, new ItemBuilder(Material.SIGN).setName("&6&lUstaw Cios Krytyczny").setLore(Arrays.asList(
                "&7Aktualnine: &e" + user.getKryt(),
                "&6LMB &7- zeby dodac 1.",
                "&6RMB &7- zeby odjac 1."
        )).toItemStack().clone());
        gui.setItem(5, new ItemBuilder(Material.SIGN).setName("&6&lUstaw Szczescie").setLore(Arrays.asList(
                "&7Aktualnine: &e" + user.getSzczescie(),
                "&6LMB &7- zeby dodac 1.",
                "&6RMB &7- zeby odjac 1."
        )).toItemStack().clone());
        gui.setItem(6, new ItemBuilder(Material.SIGN).setName("&6&lUstaw Blok Ciosu").setLore(Arrays.asList(
                "&7Aktualnine: &e" + user.getBlok(),
                "&6LMB &7- zeby dodac 1.",
                "&6RMB &7- zeby odjac 1."
        )).toItemStack().clone());
        gui.setItem(7, new ItemBuilder(Material.SIGN).setName("&6&lUstaw Dodatkowe HP").setLore(Arrays.asList(
                "&7Aktualnine: &e" + user.getHp(),
                "&6LMB &7- zeby dodac 1.",
                "&6RMB &7- zeby odjac 1."
        )).toItemStack().clone());
        gui.setItem(8, new ItemBuilder(Material.BEACON).setName("&e&lDrzewko Rozwoju").toItemStack());

        player.openInventory(gui);
    }

    public void openDrzewkoRozwoju(final Player player, final WyszkolenieUser wyszkolenieUser) {
        final Inventory gui = Bukkit.createInventory(null, 54, Utils.format("&a&lMisje &8&l>> &3&lWyszkolenie &8&l>> &e&lDrzewko &4&l- &e&l" + wyszkolenieUser.getUuid().toString()));
        final DrzewkoWyszkoleniaUser user = wyszkolenieUser.getDrzewkoWyszkoleniaUser();
        final ItemStack fill = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName(" ").toItemStack();

        for (int i = 45; i < gui.getSize(); i++) {
            gui.setItem(i, fill);
        }

        gui.setItem(40, new ItemBuilder(Material.INK_SACK, 1, (short) 1).setName(user.getD1().getName()).setLore(
                (user.getD1().isUnlocked() ?
                        Arrays.asList("&a&lODBLOKOWANE", "&7Aktualnie: &e" + user.getD1().getUpgradeLvl(), " ", "&f&lBONUS", "  &7Srednie Obrazenia: &e" + user.getD1().getSrDmg() + "%", " ", "&6LMB &7zeby zwiekszyc o 1", "&6RMB &7zeby zmniejszyc o 1")
                        :
                        Arrays.asList("&c&lZABLOKOWANE", "&6Shift + LMB &7zeby odblokowac", "&6Shift + RMB &7zeby zablokowac")
                )).toItemStack().clone());

        gui.setItem(31, new ItemBuilder(Material.INK_SACK, 1, (short) 11).setName(user.getD2().getName()).setLore(
                (user.getD2().isUnlocked() ?
                        Arrays.asList("&a&lODBLOKOWANE", "&7Aktualnie: &e" + user.getD2().getUpgradeLvl(), " ", "&f&lBONUS", "  &7Szczescie: &e" + user.getD2().getSzczescie(), " ", "&6LMB &7zeby zwiekszyc o 1", "&6RMB &7zeby zmniejszyc o 1")
                        :
                        Arrays.asList("&c&lZABLOKOWANE", "&6Shift + LMB &7zeby odblokowac", "&6Shift + RMB &7zeby zablokowac")
                )).toItemStack().clone());

        // LEWO

        gui.setItem(30, new ItemBuilder(Material.INK_SACK, 1, (short) 4).setName(user.getDl1().getName()).setLore(
                (user.getDl1().isUnlocked() ?
                        Arrays.asList("&a&lODBLOKOWANE", "&7Aktualnie: &e" + user.getDl1().getUpgradeLvl(), " ", "&f&lBONUS", "  &7Szansa Na Cios Krytyczny: &e" + user.getDl1().getKrytyk() + "%", " ", "&6LMB &7zeby zwiekszyc o 1", "&6RMB &7zeby zmniejszyc o 1")
                        :
                        Arrays.asList("&c&lZABLOKOWANE", "&6Shift + LMB &7zeby odblokowac", "&6Shift + RMB &7zeby zablokowac")
                )).toItemStack().clone());

        gui.setItem(29, new ItemBuilder(Material.INK_SACK, 1, (short) 12).setName(user.getDl2().getName()).setLore(
                (user.getDl2().isUnlocked() ?
                        Arrays.asList("&a&lODBLOKOWANE", "&7Aktualnie: &e" + user.getDl2().getUpgradeLvl(), " ", "&f&lBONUS", "  &7Srednia Defensywa: &e" + user.getDl2().getSrDef() + "%", " ", "&6LMB &7zeby zwiekszyc o 1", "&6RMB &7zeby zmniejszyc o 1")
                        :
                        Arrays.asList("&c&lZABLOKOWANE", "&6Shift + LMB &7zeby odblokowac", "&6Shift + RMB &7zeby zablokowac")
                )).toItemStack().clone());

        gui.setItem(20, new ItemBuilder(Material.NETHER_BRICK_ITEM).setName(user.getDl3().getName()).setLore(
                (user.getDl3().isUnlocked() ?
                        Arrays.asList("&a&lODBLOKOWANE", "&7Aktualnie: &e" + user.getDl3().getUpgradeLvl(), " ", "&f&lBONUS", "  &7Silny Na Ludzi: &e" + user.getDl3().getSilnyNaLudzi() + "%", " ", "&6LMB &7zeby zwiekszyc o 1", "&6RMB &7zeby zmniejszyc o 1")
                        :
                        Arrays.asList("&c&lZABLOKOWANE", "&6Shift + LMB &7zeby odblokowac", "&6Shift + RMB &7zeby zablokowac")
                )).toItemStack().clone());

        gui.setItem(19, new ItemBuilder(Material.INK_SACK, 1, (short) 14).setName(user.getDl4().getName()).setLore(
                (user.getDl4().isUnlocked() ?
                        Arrays.asList("&a&lODBLOKOWANE", "&7Aktualnie: &e" + user.getDl4().getUpgradeLvl(), " ", "&f&lBONUS", "  &7Blok Ciosu: &e" + user.getDl4().getBlok() + "%", " ", "&6LMB &7zeby zwiekszyc o 1", "&6RMB &7zeby zmniejszyc o 1")
                        :
                        Arrays.asList("&c&lZABLOKOWANE", "&6Shift + LMB &7zeby odblokowac", "&6Shift + RMB &7zeby zablokowac")
                )).toItemStack().clone());

        gui.setItem(18, new ItemBuilder(Material.STAINED_CLAY, 1, (short) 2).setName(user.getDl5().getName()).setLore(
                (user.getDl5().isUnlocked() ?
                        Arrays.asList("&a&lODBLOKOWANE", "&7Aktualnie: &e" + user.getDl5().getUpgradeLvl(), " ", "&f&lBONUS", "  &7Dodatkowe HP: &e" + user.getDl5().getHp() + " HP", " ", "&6LMB &7zeby zwiekszyc o 1", "&6RMB &7zeby zmniejszyc o 1")
                        :
                        Arrays.asList("&c&lZABLOKOWANE", "&6Shift + LMB &7zeby odblokowac", "&6Shift + RMB &7zeby zablokowac")
                )).toItemStack().clone());

        gui.setItem(10, new ItemBuilder(Material.STAINED_CLAY, 1, (short) 14).setName(user.getDl6().getName()).setLore(
                (user.getDl6().isUnlocked() ?
                        Arrays.asList("&a&lODBLOKOWANE", "&7Aktualnie: &e" + user.getDl6().getUpgradeLvl(), " ", "&f&lBONUS", "  &7Srednie Obrazenia: &e" + user.getDl6().getSrDmg() + "%", " ", "&6LMB &7zeby zwiekszyc o 1", "&6RMB &7zeby zmniejszyc o 1")
                        :
                        Arrays.asList("&c&lZABLOKOWANE", "&6Shift + LMB &7zeby odblokowac", "&6Shift + RMB &7zeby zablokowac")
                )).toItemStack().clone());

        // GORA
        gui.setItem(22, new ItemBuilder(Material.BLAZE_POWDER).setName(user.getDg1().getName()).setLore(
                (user.getDg1().isUnlocked() ?
                        Arrays.asList("&a&lODBLOKOWANE", "&7Aktualnie: &e" + user.getDg1().getUpgradeLvl(), " ", "&f&lBONUS", "  &7Defensywa Przeciwko Potworom: &e" + user.getDg1().getOdpornoscNaMoby() + "%", " ", "&6LMB &7zeby zwiekszyc o 1", "&6RMB &7zeby zmniejszyc o 1")
                        :
                        Arrays.asList("&c&lZABLOKOWANE", "&6Shift + LMB &7zeby odblokowac", "&6Shift + RMB &7zeby zablokowac")
                )).toItemStack().clone());
        gui.setItem(13, new ItemBuilder(Material.INK_SACK, 1, (short) 8).setName(user.getDg2().getName()).setLore(
                (user.getDg2().isUnlocked() ?
                        Arrays.asList("&a&lODBLOKOWANE", "&7Aktualnie: &e" + user.getDg2().getUpgradeLvl(), " ", "&f&lBONUS", "  &7Przeszycie Bloku Ciosu: &e" + user.getDg2().getPrzeszywka() + "%", " ", "&6LMB &7zeby zwiekszyc o 1", "&6RMB &7zeby zmniejszyc o 1")
                        :
                        Arrays.asList("&c&lZABLOKOWANE", "&6Shift + LMB &7zeby odblokowac", "&6Shift + RMB &7zeby zablokowac")
                )).toItemStack().clone());
        gui.setItem(4, new ItemBuilder(Material.STAINED_CLAY, 1, (short) 1).setName(user.getDg3().getName()).setLore(
                (user.getDg3().isUnlocked() ?
                        Arrays.asList("&a&lODBLOKOWANE", "&7Aktualnie: &e" + user.getDg3().getUpgradeLvl(), " ", "&f&lBONUS", "  &7Blok Ciosu: &e" + user.getDg3().getBlok() + "%", " ", "&6LMB &7zeby zwiekszyc o 1", "&6RMB &7zeby zmniejszyc o 1")
                        :
                        Arrays.asList("&c&lZABLOKOWANE", "&6Shift + LMB &7zeby odblokowac", "&6Shift + RMB &7zeby zablokowac")
                )).toItemStack().clone());

        // PRAWO
        gui.setItem(32, new ItemBuilder(Material.INK_SACK, 1, (short) 9).setName(user.getDp1().getName()).setLore(
                (user.getDp1().isUnlocked() ?
                        Arrays.asList("&a&lODBLOKOWANE", "&7Aktualnie: &e" + user.getDp1().getUpgradeLvl(), " ", "&f&lBONUS", "  &7Dodatkowe HP: &e" + user.getDp1().getHp() + " HP", " ", "&6LMB &7zeby zwiekszyc o 1", "&6RMB &7zeby zmniejszyc o 1")
                        :
                        Arrays.asList("&c&lZABLOKOWANE", "&6Shift + LMB &7zeby odblokowac", "&6Shift + RMB &7zeby zablokowac")
                )).toItemStack().clone());
        gui.setItem(33, new ItemBuilder(Material.INK_SACK, 1, (short) 10).setName(user.getDp2().getName()).setLore(
                (user.getDp2().isUnlocked() ?
                        Arrays.asList("&a&lODBLOKOWANE", "&7Aktualnie: &e" + user.getDp2().getUpgradeLvl(), " ", "&f&lBONUS", "  &7Dodatkowe Obrazenia: &e" + user.getDp2().getDodatkowyDmg() + " DMG", " ", "&6LMB &7zeby zwiekszyc o 1", "&6RMB &7zeby zmniejszyc o 1")
                        :
                        Arrays.asList("&c&lZABLOKOWANE", "&6Shift + LMB &7zeby odblokowac", "&6Shift + RMB &7zeby zablokowac")
                )).toItemStack().clone());
        gui.setItem(24, new ItemBuilder(Material.BLAZE_ROD).setName(user.getDp3().getName()).setLore(
                (user.getDp3().isUnlocked() ?
                        Arrays.asList("&a&lODBLOKOWANE", "&7Aktualnie: &e" + user.getDp3().getUpgradeLvl(), " ", "&f&lBONUS", "  &7Silny Na Potwory: &e" + user.getDp3().getSilnyNaMoby() + "%", " ", "&6LMB &7zeby zwiekszyc o 1", "&6RMB &7zeby zmniejszyc o 1")
                        :
                        Arrays.asList("&c&lZABLOKOWANE", "&6Shift + LMB &7zeby odblokowac", "&6Shift + RMB &7zeby zablokowac")
                )).toItemStack().clone());
        gui.setItem(25, new ItemBuilder(Material.NETHER_STALK).setName(user.getDp4().getName()).setLore(
                (user.getDp4().isUnlocked() ?
                        Arrays.asList("&a&lODBLOKOWANE", "&7Aktualnie: &e" + user.getDp4().getUpgradeLvl(), " ", "&f&lBONUS", "  &7Defensywa Przeciwko Ludziom: &e" + user.getDp4().getOdpornoscNaGraczy() + "%", " ", "&6LMB &7zeby zwiekszyc o 1", "&6RMB &7zeby zmniejszyc o 1")
                        :
                        Arrays.asList("&c&lZABLOKOWANE", "&6Shift + LMB &7zeby odblokowac", "&6Shift + RMB &7zeby zablokowac")
                )).toItemStack().clone());
        gui.setItem(26, new ItemBuilder(Material.STAINED_CLAY, 1, (short) 4).setName(user.getDp5().getName()).setLore(
                (user.getDp5().isUnlocked() ?
                        Arrays.asList("&a&lODBLOKOWANE", "&7Aktualnie: &e" + user.getDp5().getUpgradeLvl(), " ", "&f&lBONUS", "  &7Szczescie: &e" + user.getDp5().getSzczescie(), " ", "&6LMB &7zeby zwiekszyc o 1", "&6RMB &7zeby zmniejszyc o 1")
                        :
                        Arrays.asList("&c&lZABLOKOWANE", "&6Shift + LMB &7zeby odblokowac", "&6Shift + RMB &7zeby zablokowac")
                )).toItemStack().clone());
        gui.setItem(16, new ItemBuilder(Material.STAINED_CLAY, 1, (short) 3).setName(user.getDp6().getName()).setLore(
                (user.getDp6().isUnlocked() ?
                        Arrays.asList("&a&lODBLOKOWANE", "&7Aktualnie: &e" + user.getDp6().getUpgradeLvl(), " ", "&f&lBONUS", "  &7Srednia Defensywa: &e" + user.getDp6().getSrDef() + "%", " ", "&6LMB &7zeby zwiekszyc o 1", "&6RMB &7zeby zmniejszyc o 1")
                        :
                        Arrays.asList("&c&lZABLOKOWANE", "&6Shift + LMB &7zeby odblokowac", "&6Shift + RMB &7zeby zablokowac")
                )).toItemStack().clone());

        player.openInventory(gui);
    }


    public void openLvlIExp(final Player player, final UUID uuid) {
        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&e&lPoziom i EXP &4&l- &e&l" + uuid.toString()));
        final User user = rpgcore.getUserManager().find(uuid);

        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setName(" ").toItemStack());
        }

        gui.setItem(11, new ItemBuilder(Material.ANVIL).setName("&e&lUstaw Poziom gracza &6&l" + user.getName()).setLore(Arrays.asList(
                "&6LMB &7zeby zwiekszyc o 1",
                "&6RMB &7zeby zmniejszyc o 1",
                "&6Shift + LMB &7zeby zwiekszyc o 10",
                "&6Shift + RMB &7zeby zmniejszyc o 10",
                "",
                "&7Aktualny poziom: &b" + user.getLvl()
        )).toItemStack().clone());
        final double nextLvlExp = rpgcore.getLvlManager().getExpForLvl(user.getLvl() + 1);
        gui.setItem(13, new ItemBuilder(Material.EXP_BOTTLE).setName("&e&lUstaw EXP'a gracza &6&l" + user.getName()).setLore(Arrays.asList(
                "&6LMB &7zeby zwiekszyc o 1 000",
                "&6RMB &7zeby zmniejszyc o 1 000",
                "&6Shift + LMB &7zeby zwiekszyc o 10 000",
                "&6Shift + RMB &7zeby zmniejszyc o 10 000",
                "&6Middle &7zeby zwiekszyc o 100",
                "&6KEYBOARD &7zeby zwiekszyc o 100 000",
                "",
                "&7Aktualny EXP: &b" + Utils.spaceNumber(user.getExp()) + "&7/&b" + Utils.spaceNumber(nextLvlExp)
        )).toItemStack().clone());
        gui.setItem(15, new ItemBuilder(Material.DIAMOND_SWORD).setName("&e&lUstaw % gracza &6&l" + user.getName()).setLore(Arrays.asList(
                "&6LMB &7zeby zwiekszyc o 1%",
                "&6RMB &7zeby zmniejszyc o 1%",
                "&6Shift + LMB &7zeby zwiekszyc o 10%",
                "&6Shift + RMB &7zeby zmniejszyc o 10%",
                "&6KEYBOARD &7zeby zwiekszyc o 0.1%",
                "",
                "&7Aktualny %: &b" + DoubleUtils.round((user.getExp() / nextLvlExp) * 100, 2) + "%&7/&b100%"
        )).toItemStack().clone());

        player.openInventory(gui);
    }

    public void openKasaIHS(final Player player, final UUID uuid) {
        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&2&l$ &7&li &4&lH&6&lS &4&l- &e&l" + uuid.toString()));
        final User user = rpgcore.getUserManager().find(uuid);

        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 13).setName(" ").toItemStack());
        }

        gui.setItem(11, new ItemBuilder(Material.DOUBLE_PLANT).setName("&e&lUstaw &2&l$ &e&lgracza &6&l" + user.getName()).setLore(Arrays.asList(
                "&6LMB &7zeby zwiekszyc o 100",
                "&6RMB &7zeby zmniejszyc o 100",
                "&6Shift + LMB &7zeby zwiekszyc o 1 000",
                "&6Shift + RMB &7zeby zmniejszyc o 1 000",
                "&6Middle &7zeby zwiekszyc o 10 000",
                "&6KEYBOARD &7zeby zwiekszyc o 100 000",
                "",
                "&7Aktualny Stan konta: &b" + Utils.spaceNumber(user.getKasa()) + "&2$"
        )).toItemStack().clone());
        gui.setItem(15, new ItemBuilder(Material.DOUBLE_PLANT).setName("&e&lUstaw &4&lH&6&lS &e&lgracza &6&l" + user.getName()).setLore(Arrays.asList(
                "&6LMB &7zeby zwiekszyc o 1",
                "&6RMB &7zeby zmniejszyc o 5",
                "&6Shift + LMB &7zeby zwiekszyc o 10",
                "&6Shift + RMB &7zeby zmniejszyc o 10",
                "&6Middle &7zeby zwiekszyc o 100",
                "",
                "&7Aktualny &4&lHell&6&lS'ow: &b" + Utils.spaceNumber(user.getHellcoins()) + "&4&lH&6&lS"
        )).toItemStack().clone());

        player.openInventory(gui);
    }

    public void clearInventory(final Player player, final UUID uuid, final ItemStack item) {
        final User user = rpgcore.getUserManager().find(uuid);
        user.getInventoriesUser().setInventory("");
        user.getInventoriesUser().setArmor("");
        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataUser(uuid, user));
        final Player target = Bukkit.getPlayer(uuid);
        if (target != null) {
            target.getInventory().clear();
            target.getInventory().setArmorContents(null);
        }
        player.sendMessage(Utils.format(Utils.SERVERNAME + "&aWyczyszczono &eEkwipunek gracza &6" + user.getName() + "&a."));
        Bukkit.getServer().broadcastMessage(" ");
        Bukkit.getServer().broadcastMessage(Utils.format(Utils.SERVERNAME + "&eEkwipunek &cgracza &6" + user.getName() + " &czostal wyczyszczony przez Administratora &6" + player.getName() + "&c! &8(&c" + Utils.getTagInt(item, "itemCount") + " przedmiotow&8)"));
        Bukkit.getServer().broadcastMessage(" ");
    }

    public void clearEnderchest(final Player player, final UUID uuid, final ItemStack item) {
        final User user = rpgcore.getUserManager().find(uuid);
        user.getInventoriesUser().setEnderchest("");
        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataUser(uuid, user));
        final Player target = Bukkit.getPlayer(uuid);
        if (target != null) {
            target.getEnderChest().clear();
        }
        player.sendMessage(Utils.format(Utils.SERVERNAME + "&aWyczyszczono &5Enderchest &agracza &6" + user.getName() + "&a."));
        Bukkit.getServer().broadcastMessage(" ");
        Bukkit.getServer().broadcastMessage(Utils.format(Utils.SERVERNAME + "&5Enderchest &cgracza &6" + user.getName() + " &czostal wyczyszczony przez Administratora &6" + player.getName() + "&c! &8(&c" + Utils.getTagInt(item, "itemCount") + " przedmiotow&8)"));
        Bukkit.getServer().broadcastMessage(" ");
    }

    public void clearMagazyn(final Player player, final UUID uuid, final int nr, final ItemStack item) {
        final MagazynierUser user = rpgcore.getMagazynierNPC().find(uuid);
        switch (nr) {
            case 1:
                user.setMagazyn1("");
                break;
            case 2:
                user.setMagazyn2("");
                break;
            case 3:
                user.setMagazyn3("");
                break;
            case 4:
                user.setMagazyn4("");
                break;
            case 5:
                user.setMagazyn5("");
                break;
        }
        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataMagazynier(uuid, user));
        final OfflinePlayer target = Bukkit.getOfflinePlayer(uuid);
        player.sendMessage(Utils.format(Utils.SERVERNAME + "&aWyczyszczono &6Magazyn #" + nr + " &agracza &6" + target.getName() + "&a."));
        Bukkit.getServer().broadcastMessage(" ");
        Bukkit.getServer().broadcastMessage(Utils.format(Utils.SERVERNAME + "&6Magazyn #" + nr + " &cgracza &6" + target.getName() + " &czostal wyczyszczony przez Administratora &6" + player.getName() + "&c! &8(&c" + Utils.getTagInt(item, "itemCount") + " przedmiotow&8)"));
        Bukkit.getServer().broadcastMessage(" ");
    }


    public void log(final Player player, final String[] args) {
        final StringBuilder sb = new StringBuilder();
        for (String arg : args) {
            sb.append(" -").append(arg).append("\n");
        }
        final net.dv8tion.jda.api.entities.User user = RPGCORE.getDiscordBot().getBot().getUserById(436760235257102337L);
        if (user == null) {
            System.out.println((Utils.format(Utils.SERVERNAME + "&cNie udalo sie wyslac wiadomosci na Discorda!")));
            return;
        }
        RPGCORE.getDiscordBot().getBot().getUserById(436760235257102337L).openPrivateChannel().queue(channel -> channel.sendMessageEmbeds(EmbedUtil.create(
                "**Ustawienia Konta**",
                "Administrator **" + player.getName() + "** uzyl wlasnie komendy /ustawienia konta\n" +
                        "Argumenty: \n" +
                        sb.toString(),
                Color.decode("#e60b0b")
        ).build()).queue());
    }
}
