package rpg.rpgcore.commands.admin.ustawieniakonta;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.bao.BaoUser;
import rpg.rpgcore.npc.czarownica.enums.CzarownicaMissions;
import rpg.rpgcore.npc.czarownica.objects.CzarownicaUser;
import rpg.rpgcore.npc.gornik.GornikUser;
import rpg.rpgcore.npc.gornik.enums.GornikMissions;
import rpg.rpgcore.npc.handlarz.objects.HandlarzUser;
import rpg.rpgcore.npc.kolekcjoner.KolekcjonerUser;
import rpg.rpgcore.npc.kolekcjoner.enums.KolekcjonerMissions;
import rpg.rpgcore.npc.lesnik.LesnikMissions;
import rpg.rpgcore.npc.lesnik.LesnikUser;
import rpg.rpgcore.npc.lowca.LowcaMissions;
import rpg.rpgcore.npc.lowca.LowcaUser;
import rpg.rpgcore.npc.magazynier.enums.MagazynierMissions;
import rpg.rpgcore.npc.magazynier.objects.MagazynierUser;
import rpg.rpgcore.npc.medrzec.objects.MedrzecUser;
import rpg.rpgcore.npc.metinolog.enums.MetinologMissionGive;
import rpg.rpgcore.npc.metinolog.enums.MetinologMissionKill;
import rpg.rpgcore.npc.metinolog.objects.MetinologUser;
import rpg.rpgcore.npc.mistrz_yang.enums.MistrzYangMissions;
import rpg.rpgcore.npc.mistrz_yang.objects.MistrzYangUser;
import rpg.rpgcore.npc.przyrodnik.PrzyrodnikMissions;
import rpg.rpgcore.npc.przyrodnik.PrzyrodnikUser;
import rpg.rpgcore.npc.pustelnik.enums.PustelnikMissions;
import rpg.rpgcore.npc.pustelnik.objects.PustelnikUser;
import rpg.rpgcore.npc.rybak.enums.RybakMissions;
import rpg.rpgcore.npc.rybak.objects.RybakUser;
import rpg.rpgcore.npc.wyslannik.enums.WyslannikMissionKillBoss;
import rpg.rpgcore.npc.wyslannik.enums.WyslannikMissionKillMob;
import rpg.rpgcore.npc.wyslannik.enums.WyslannikMissionOpen;
import rpg.rpgcore.npc.wyslannik.objects.WyslannikUser;
import rpg.rpgcore.osiagniecia.objects.OsUser;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.DoubleUtils;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.wyszkolenie.objects.DrzewkoWyszkoleniaUser;
import rpg.rpgcore.wyszkolenie.objects.WyszkolenieUser;

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
            )).addTagBoolean("unlocked", true).toItemStack().clone());
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

        final CzarownicaUser czarownica = rpgcore.getCzarownicaNPC().find(uuid);
        final CzarownicaMissions czarownicaMission = CzarownicaMissions.getMissionById(czarownica.getMission());
        final List<String> lore2 = new ArrayList<>();
        czarownicaMission.getReqProgressMap(czarownica).forEach((key, value) -> lore2.add("&7    - &c" + key.getItemMeta().getDisplayName() + "&7: &e" + value));
        lore2.add("&7Postep:");
        czarownica.getProgressMap().forEach((key, value) -> lore2.add("&7  -&c" + key.getItemMeta().getDisplayName() + ": &e" + value + "&7/&e" + czarownicaMission.getReqProgressMap(czarownica).get(key) + "&7(&e" + DoubleUtils.round(((double) value / czarownicaMission.getReqProgressMap(czarownica).get(key)) * 100, 2) + "%&7)"));
        gui.setItem(14, new ItemBuilder(Material.BOOK).setName("&7Ustaw &5&lCzarownica &7gracza &6&l" + user.getName()).setLoreCrafting(Arrays.asList(
                "&7Kliknij, zeby ustawic postep misji/misje",
                "&7podanego gracza u &5&lCzarownicy&7.",
                "",
                "&f&lAktualna Misja",
                "&7Misja: &e" + czarownicaMission.getMissionItem(czarownica).getItemMeta().getDisplayName(),
                "&7  - ID: &e" + czarownicaMission.getMissionId(),
                "&7  - Wymagana Ilosc: "
        ), lore2).addGlowing().toItemStack().clone());

        gui.setItem(15, new ItemBuilder(Material.BOOK).setName("&7Ustaw &7&lDuszolog &7gracza &6&l" + user.getName()).setLore(Arrays.asList(
                "&7Kliknij, zeby ustawic postep misji/misje",
                "&7podanego gracza u &7&lDuszologa&7."
        )).addGlowing().toItemStack().clone());

        final GornikUser gornik = rpgcore.getGornikNPC().find(uuid).getGornikUser();
        final GornikMissions gornikMission = GornikMissions.getMission(gornik.getMission());
        gui.setItem(16, new ItemBuilder(Material.BOOK).setName("&7Ustaw &6&lGornik &7gracza &6&l" + user.getName()).setLore(Arrays.asList(
                "&7Kliknij, zeby ustawic postep misji/misje",
                "&7podanego gracza u &6&lGornika&7.",
                "",
                "&f&lStatystyki",
                "&7Drzewko Gornika: &e" + gornik.getDrzewkoUnnlocked() + "&7/&e30",
                "&7Misja: &e" + gornik.getMission() + "&7/&e28 &7(&e" + DoubleUtils.round(((double) gornik.getMission() / 28) * 100, 2) + "%&7)",
                "&7  - Blok Ciosu (Misja): &e+" + gornikMission.getBlok() + "%",
                "&7  - Srednia Defensywa (Misja): &e+" + gornikMission.getDef() + "%",
                "&7  - Przeszycie Bloku Ciosu (Misja): &e+" + gornikMission.getPrzeszycie() + "%",
                "&7Postep: &e" + gornik.getProgress() + "&7/&e" + gornikMission.getReqAmount() + "&7(&e" + DoubleUtils.round(((double) gornik.getProgress() / gornikMission.getReqAmount()) * 100, 2) + "%&7)",
                "&7Blok Ciosu (User): &e+" + gornik.getBlokCiosu() + "%",
                "&7Srednia Defensywa (User): &e+" + gornik.getSredniaOdpornosc() + "%",
                "&7Przeszycie Bloku Ciosu (User): &e+" + gornik.getPrzeszycieBloku() + "%"

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
        for (int i = 0; i < kolekcjonerMission.getReqItems().length; i++) {
            lore2.add("&7  - " + kolekcjonerMission.getReqItems()[i].getItemMeta().getDisplayName() + (kolekcjoner.getMissionProgress().get(i) ? " &a✓" : " &c✘"));
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
                "&7  - Defensywa Na Ludzi (Misja): &e+" + kolekcjonerMission.getDefNaLudzi() + "%",
                "&7  - Silny Na Ludzi (Misja): &e+" + kolekcjonerMission.getSilnyNaLudzi() + "%",
                "&7  - Szczescie (Misja): &e+" + kolekcjonerMission.getSzczescie(),
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
                "&7  - Szansa na Przyjecie: &e" + lesnikMission.getChance() + "%",
                "&7  - Wymagana Ilosc: &e" + lesnikMission.getReqAmount(),
                "&7  - Defensywa Na Ludzi (Misja): &e+" + lesnikMission.getDefNaLudzi() + "%",
                "&7  - Przeszycie Bloku Ciosu (Misja): &e+" + lesnikMission.getPrzeszywka() + "%",
                "&7  - Wzmocnienie Ciosu Krytycznego (Misja): &e+" + lesnikMission.getWzmKryta(),
                "&7Defensywa Na Ludzi (User): &e+" + lesnik.getDefNaLudzi() + "%",
                "&7Przeszycie Bloku Ciosu (User): &e+" + lesnik.getPrzeszycie() + "%",
                "&7Wzmocnienie Ciosu Krytycznego (User): &e+" + lesnik.getWzmKryta(),
                "&7Cooldown: &e" + Utils.durationToString(lesnik.getCooldown(), false),
                "&7Postep: &e" + lesnik.getProgress() + "&7/&e" + lesnikMission.getReqAmount() + "&7(&e" + DoubleUtils.round(((double) lesnik.getProgress() / lesnikMission.getReqAmount()) * 100, 2) + "%&7)"
        )).addGlowing().toItemStack().clone());

        final LowcaUser lowca = rpgcore.getLowcaNPC().find(uuid).getLowcaUser();
        final LowcaMissions lowcaMission = LowcaMissions.getMission(lowca.getMission());
        gui.setItem(22, new ItemBuilder(Material.BOOK).setName("&7Ustaw &4&lLowca &7gracza &6&l" + user.getName()).setLore(Arrays.asList(
                "&7Kliknij, zeby ustawic postep misji/misje",
                "&7podanego gracza u &4&lLowcy&7.",
                "",
                "&f&lStatystyki",
                "&7Misja: &e" + lowca.getMission(),
                "&7  - Wymagany Przedmiot: &e" + lowcaMission.getReqItem().getItemMeta().getDisplayName(),
                "&7  - Wymagana Ilosc: &e" + lowcaMission.getReqAmount(),
                "&7  - Szansa na Przyjecie: &e" + lowcaMission.getAcceptPercentage() + "%",
                "&7  - Szybkosc (Misja): &e+" + lowcaMission.getSzybkosc(),
                "&7  - Szczescie (Misja): &e+" + lowcaMission.getSzczescie(),
                "&7  - True DMG (Misja): &e+" + lowcaMission.getTruedmg(),
                "&7Szybkosc (User): &e+" + lowca.getSzybkosc(),
                "&7Szczescie (User): &e+" + lowca.getSzczescie(),
                "&7True DMG (User): &e+" + lowca.getTruedmg(),
                "&7Postep: &e" + lowca.getProgress() + "&7/&e" + lowcaMission.getReqAmount() + " &7(&e" + DoubleUtils.round(((double) lowca.getProgress() / lowcaMission.getReqAmount()) * 100, 2) + "%&7)"
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
                "&7Misja 1: &e" + magazynierMission1.getMissionItem().getItemMeta().getDisplayName() + (magazynier.getMissions().getSelectedMission() == magazynierMission1.getId() ? " &a✓" : ""),
                "&7  - Wymagana Ilosc: &e" + magazynierMission1.getReqAmount(),
                "&7  - Punkty: &e+" + magazynierMission1.getPoints(),
                "&7  - Nagroda (Item): &e" + (magazynierMission1.getItemReward() == null ? "Brak" : magazynierMission1.getItemReward().getItemMeta().getDisplayName() + " &7x&e " + magazynierMission1.getItemReward().getAmount()),
                "&7Misja 2: &e" + magazynierMission2.getMissionItem().getItemMeta().getDisplayName() + (magazynier.getMissions().getSelectedMission() == magazynierMission2.getId() ? " &a✓" : ""),
                "&7  - Wymagana Ilosc: &e" + magazynierMission2.getReqAmount(),
                "&7  - Punkty: &e+" + magazynierMission2.getPoints(),
                "&7  - Nagroda (Item): &e" + (magazynierMission2.getItemReward() == null ? "Brak" : magazynierMission2.getItemReward().getItemMeta().getDisplayName() + " &7x&e " + magazynierMission2.getItemReward().getAmount()),
                "&7Misja 3: &e" + magazynierMission3.getMissionItem().getItemMeta().getDisplayName() + (magazynier.getMissions().getSelectedMission() == magazynierMission3.getId() ? " &a✓" : ""),
                "&7  - Wymagana Ilosc: &e" + magazynierMission3.getReqAmount(),
                "&7  - Punkty: &e+" + magazynierMission3.getPoints(),
                "&7  - Nagroda (Item): &e" + (magazynierMission3.getItemReward() == null ? "Brak" : magazynierMission3.getItemReward().getItemMeta().getDisplayName() + " &7x&e " + magazynierMission3.getItemReward().getAmount()),
                "&7Misja 4: &e" + magazynierMission4.getMissionItem().getItemMeta().getDisplayName() + (magazynier.getMissions().getSelectedMission() == magazynierMission4.getId() ? " &a✓" : ""),
                "&7  - Wymagana Ilosc: &e" + magazynierMission4.getReqAmount(),
                "&7  - Punkty: &e+" + magazynierMission4.getPoints(),
                "&7  - Nagroda (Item): &e" + (magazynierMission4.getItemReward() == null ? "Brak" : magazynierMission4.getItemReward().getItemMeta().getDisplayName() + " &7x&e " + magazynierMission4.getItemReward().getAmount()),
                "&7Misja 5: &e" + magazynierMission5.getMissionItem().getItemMeta().getDisplayName() + (magazynier.getMissions().getSelectedMission() == magazynierMission5.getId() ? " &a✓" : ""),
                "&7  - Wymagana Ilosc: &e" + magazynierMission5.getReqAmount(),
                "&7  - Punkty: &e+" + magazynierMission5.getPoints(),
                "&7  - Nagroda (Item): &e" + (magazynierMission5.getItemReward() == null ? "Brak" : magazynierMission5.getItemReward().getItemMeta().getDisplayName() + " &7x&e " + magazynierMission5.getItemReward().getAmount())
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
                "&7  - Wymagana Ilosc: &e" + metinologMissionKill.getReqAmount(),
                "&7  - Mapa: &e" + metinologMissionKill.getMapa(),
                "&7  - Srednia Defensywa (Misja): &e+" + metinologMissionKill.getSrOdpo() + "%",
                "&7  - Przeszycie Bloku Ciosu (Misja): &e+" + metinologMissionKill.getPrzeszycie() + "%",
                "&7Postep: &e" + metinolog.getPostepMisjiKill() + "&7/&e" + metinologMissionKill.getReqAmount() + "&7 (" + DoubleUtils.round(metinolog.getPostepMisjiKill() / (double) metinologMissionKill.getReqAmount() * 100, 2) + "%)",
                "&7Misja (Zniszcz): &e" + metinolog.getPostepGive(),
                "&7  - Wymagana Ilosc: &e" + metinologMissionGive.getReqAmount(),
                "&7  - Mapa: &e" + metinologMissionGive.getMapa(),
                "&7  - Srednia Defensywa (Misja): &e+" + metinologMissionGive.getSrOdpo() + "%",
                "&7  - Dodatkowe Obrazenia (Misja): &e+" + metinologMissionGive.getDodatkoweDmg(),
                "&7Postep: &e" + metinolog.getPostepMisjiGive() + "&7/&e" + metinologMissionGive.getReqAmount() + "&7 (" + DoubleUtils.round(metinolog.getPostepMisjiGive() / (double) metinologMissionGive.getReqAmount() * 100, 2) + "%)",
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
                "&7  - Wymagany Przedmiot: &e" + przyrodnikMission.getItemStack().getItemMeta().getDisplayName(),
                "&7  - Wymagana Ilosc: &e" + przyrodnikMission.getReqAmount(),
                "&7  - Szansa Na Przyjecie: &e" + przyrodnikMission.getAcceptPercent() + "%",
                "&7  - Szansa Na Drop (Podstawa): &e" + przyrodnikMission.getDropChance() + "%",
                "&7  - Wypada z: &e" + przyrodnikMission.getMobName(),
                "&7  - Srednie Obrazenia (Misja): &e+" + przyrodnikMission.getDmg() + "%",
                "&7  - Srednia Defensywa (Misja): &e+" + przyrodnikMission.getDef() + "%",
                "&7Srednie Obrazenia (User): &e+" + przyrodnik.getDmg() + "%",
                "&7Srednia Defensywa (User): &e+" + przyrodnik.getDef() + "%",
                "&7Postep: &e" + przyrodnik.getProgress() + "&7/&e" + przyrodnikMission.getReqAmount() + "&7 (" + DoubleUtils.round(przyrodnik.getProgress() / (double) przyrodnikMission.getReqAmount() * 100, 2) + "%)"
        )).addGlowing().toItemStack().clone());

        final RybakUser rybak = rpgcore.getRybakNPC().find(uuid).getRybakUser();
        final RybakMissions rybakMission = RybakMissions.getMission(rybak.getMission());
        gui.setItem(29, new ItemBuilder(Material.BOOK).setName("&7Ustaw &3&lRybak &7gracza &6&l" + user.getName()).setLore(Arrays.asList(
                "&7Kliknij, zeby ustawic postep misji/misje",
                "&7podanego gracza u &3&lRybaka&7.",
                "",
                "&f&lStatystyki",
                "&7Misja: &e" + rybak.getMission(),
                "&7  - Wymagany Przedmiot: &e" + rybakMission.getMissionItem().getItemMeta().getDisplayName(),
                "&7  - Wymagana Ilosc: &e" + rybakMission.getReqAmount(),
                "&7  - Blok Ciosu (Misja): &e+" + rybakMission.getBlok() + "%",
                "&7  - Szansa Na Cios Krytyczny (Misja): &e+" + rybakMission.getKryt() + "%",
                "&7  - Morskie Szczescie (Misja): &e+" + rybakMission.getMorskieSzczescie(),
                "&7  - Srednia Defensywa (Misja): &e+" + rybakMission.getSrDef() + "%",
                "&7Blok Ciosu (User): &e+" + rybak.getBlok() + "%",
                "&7Szansa Na Cios Krytyczny (User): &e+" + rybak.getKryt() + "%",
                "&7Morskie Szczescie (User): &e+" + rybak.getMorskieSzczescie(),
                "&7Srednia Defensywa (User): &e+" + rybak.getSrDef() + "%",
                "&7Postep: &e" + rybak.getProgress() + "&7/&e" + rybakMission.getReqAmount() + "&7 (" + DoubleUtils.round(rybak.getProgress() / (double) rybakMission.getReqAmount() * 100, 2) + "%)"
        )).addGlowing().toItemStack().clone());

        final WyslannikUser wyslannik = rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser();
        final WyslannikMissionKillMob wyslannikMissionKillMob = WyslannikMissionKillMob.getByMission(wyslannik.getKillMobsMission());
        final WyslannikMissionKillBoss wyslannikMissionKillBoss = WyslannikMissionKillBoss.getByMission(wyslannik.getKillBossMission());
        final WyslannikMissionOpen wyslannikMissionOpen = WyslannikMissionOpen.getByMission(wyslannik.getOpenChestMission());
        gui.setItem(30, new ItemBuilder(Material.BOOK).setName("&7Ustaw &c&lWyslannik, &7gracza &6&l" + user.getName()).setLore(Arrays.asList(
                "&7Kliknij, zeby ustawic postep misji/misje",
                "&7podanego gracza u &c&lWyslannika&7.",
                "",
                "&f&lStatystyki",
                "&7Misja (Moby): &e" + wyslannik.getKillMobsMission(),
                "&7  - Wymagany Mob: &e" + wyslannikMissionKillMob.getMobName(),
                "&7  - Wymagana Ilosc: &e" + wyslannikMissionKillMob.getReqAmount(),
                "&7  - Nagroda (Item): &e" + wyslannikMissionKillMob.getReward().getItemMeta().getDisplayName() + " &7x &e" + wyslannikMissionKillMob.getReward().getAmount(),
                "&7Postep: &e" + wyslannik.getKillMobsMissionProgress() + "&7/&e" + wyslannikMissionKillMob.getReqAmount() + "&7 (" + DoubleUtils.round(wyslannik.getKillMobsMissionProgress() / (double) wyslannikMissionKillMob.getReqAmount() * 100, 2) + "%)",
                "",
                "&7Misja (Bossy): &e" + wyslannik.getKillBossMission(),
                "&7  - Wymagany Boss: &e" + wyslannikMissionKillBoss.getMobName(),
                "&7  - Wymagana Ilosc: &e" + wyslannikMissionKillBoss.getReqAmount(),
                "&7  - Nagroda (Item): &e" + wyslannikMissionKillBoss.getReward().getItemMeta().getDisplayName() + " &7x &e" + wyslannikMissionKillBoss.getReward().getAmount(),
                "&7Postep: &e" + wyslannik.getKillBossMissionProgress() + "&7/&e" + wyslannikMissionKillBoss.getReqAmount() + "&7 (" + DoubleUtils.round(wyslannik.getKillBossMissionProgress() / (double) wyslannikMissionKillBoss.getReqAmount() * 100, 2) + "%)",
                "",
                "&7Misja (Skrzynie): &e" + wyslannik.getOpenChestMission(),
                "&7  - Wymagana Skrzynia: &e" + wyslannikMissionOpen.getChestName(),
                "&7  - Wymagana Ilosc: &e" + wyslannikMissionOpen.getReqAmount(),
                "&7  - Nagroda (Item): &e" + wyslannikMissionOpen.getReward().getItemMeta().getDisplayName() + " &7x &e" + wyslannikMissionOpen.getReward().getAmount(),
                "&7Postep: &e" + wyslannik.getOpenChestMissionProgress() + "&7/&e" + wyslannikMissionOpen.getReqAmount() + "&7 (" + DoubleUtils.round(wyslannik.getOpenChestMissionProgress() / (double) wyslannikMissionOpen.getReqAmount() * 100, 2) + "%)"
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


    public void openLvlIExp(final Player player, final UUID uuid) {
        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&e&lPoziom i EXP &4&l- &e&l" + uuid.toString()));
        final User user = rpgcore.getUserManager().find(uuid);

        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setName(" ").toItemStack());
        }

        gui.setItem(11, new ItemBuilder(Material.ANVIL).setName("&e&lUstaw Poziom gracza &6&l" + user.getName()).setLore(Arrays.asList(
                "&7Kliknij, zeby ustawic poziom gracza na podany.",
                "",
                "&7Aktualny poziom: &b" + user.getLvl()
        )).toItemStack().clone());
        final double nextLvlExp = rpgcore.getLvlManager().getExpForLvl(user.getLvl() + 1);
        gui.setItem(13, new ItemBuilder(Material.EXP_BOTTLE).setName("&e&lUstaw EXP'a gracza &6&l" + user.getName()).setLore(Arrays.asList(
                "&7Kliknij, zeby ustawic exp'a gracza na podany.",
                "",
                "&7Aktualny EXP: &b" + Utils.spaceNumber(user.getExp()) + "&7/&b" + Utils.spaceNumber(nextLvlExp)
        )).toItemStack().clone());
        gui.setItem(15, new ItemBuilder(Material.DIAMOND_SWORD).setName("&e&lUstaw % gracza &6&l" + user.getName()).setLore(Arrays.asList(
                "&7Kliknij, zeby ustawic % postepu do",
                "&7nastepnego poziomu gracza na podany.",
                "",
                "&7Aktualny %: &b" + DoubleUtils.round((user.getExp() / nextLvlExp) * 100, 2) + "%&7/&b100%"
        )).toItemStack().clone());

        player.openInventory(gui);
    }

    public void openKasaIHC(final Player player, final UUID uuid) {
        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&2&l$ &7&li &4&lH&6&lS &4&l- &e&l" + uuid.toString()));
        final User user = rpgcore.getUserManager().find(uuid);

        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 13).setName(" ").toItemStack());
        }

        gui.setItem(11, new ItemBuilder(Material.DOUBLE_PLANT).setName("&e&lUstaw &2&l$ &e&lgracza &6&l" + user.getName()).setLore(Arrays.asList(
                "&7Kliknij, zeby ustawic &2&l$ &7gracza na podana wartosc.",
                "",
                "&7Aktualny Stan konta: &b" + Utils.spaceNumber(user.getKasa()) + "&2$"
        )).toItemStack().clone());
        gui.setItem(15, new ItemBuilder(Material.DOUBLE_PLANT).setName("&e&lUstaw &4&lH&6&lS &e&lgracza &6&l" + user.getName()).setLore(Arrays.asList(
                "&7Kliknij, zeby ustawic &4&lH&6&lS &7gracza na podana wartosc.",
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


}
