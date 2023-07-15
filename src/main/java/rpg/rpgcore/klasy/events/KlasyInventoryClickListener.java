package rpg.rpgcore.klasy.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.klasy.enums.KlasyMain;
import rpg.rpgcore.klasy.enums.KlasySide;
import rpg.rpgcore.klasy.objects.Klasa;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.Arrays;
import java.util.UUID;

public class KlasyInventoryClickListener implements Listener {
    private final RPGCORE rpgcore;

    public KlasyInventoryClickListener(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClick(final InventoryClickEvent e) {
        if (e.getClickedInventory() == null) {
            return;
        }

        final Player player = (Player) e.getWhoClicked();
        final UUID uuid = player.getUniqueId();
        final String title = Utils.removeColor(e.getClickedInventory().getTitle());
        final int slot = e.getSlot();


        if (title.equals("Dowodca Strazy » Wybor Klasy")) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
            final Klasa klasa = rpgcore.getKlasyManager().find(uuid);
            if (e.getClick().equals(ClickType.LEFT)) {
                if (!klasa.getMainKlasa().equals(KlasyMain.NIE_WYBRANO)) return;
                switch (slot) {
                    case 20:
                        klasa.setMainKlasa(KlasyMain.WOJOWNIK);
                        break;
                    case 22:
                        klasa.setMainKlasa(KlasyMain.ZABOJCA);
                        break;
                    case 24:
                        klasa.setMainKlasa(KlasyMain.CZARODZIEJ);
                        break;
                    default:
                        return;
                }
                klasa.save();
                player.closeInventory();
                player.sendMessage(Utils.format("&c&lDowodca Strazy &8>> &aPomyslnie wybrales/-as " + e.getCurrentItem().getItemMeta().getDisplayName() + "&a!"));
                return;
            }
            if (e.getClick().equals(ClickType.RIGHT)) {
                switch (slot) {
                    case 20:
                        rpgcore.getKlasyManager().openPodWoj(player);
                        return;
                    case 22:
                        rpgcore.getKlasyManager().openPodZab(player);
                        return;
                    case 24:
                        rpgcore.getKlasyManager().openPodCzaro(player);
                        return;
                    default:
                        return;
                }
            }
            return;
        }

        if (title.equals("Dowodca Strazy » WOJOWNIK")) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
            if (slot == 22) {
                rpgcore.getKlasyManager().openGUI(player);
                return;
            }
            if (rpgcore.getUserManager().find(uuid).getLvl() < 1) return; //50
            final Klasa klasa = rpgcore.getKlasyManager().find(uuid);
            if (klasa.getMainKlasa() != KlasyMain.WOJOWNIK) return;
            if (klasa.getPodKlasa() != KlasySide.NIE_WYBRANO) return;
            switch (slot) {
                case 12:
                    klasa.setPodKlasa(KlasySide.BERSERK);
                    player.getInventory().addItem(new ItemBuilder(Material.DOUBLE_PLANT).setName("&c&lWOJOWNIK &8- &fBERSERK").setLore(Arrays.asList(
                            "&6Umiejetnosc &cSzal Bitewny &e&lLMB",
                            "&7Przez &e10 sekund &7zadajesz",
                            "&7&c+10% obrazen &8(cooldown 3 min)",
                            "",
                            "&6Umiejetnosc &4Brutal &e&lRMB",
                            "&7Twoj nastepny atak zadaje",
                            "&2+250% obrazen w potwory &8(cooldown 5 min)"
                    )).toItemStack());
                    break;
                case 14:
                    klasa.setPodKlasa(KlasySide.PALADYN);
                    player.getInventory().addItem(new ItemBuilder(Material.DOUBLE_PLANT).setName("&c&lWOJOWNIK &8- &fPALADYN").setLore(Arrays.asList(
                            "&6Umiejetnosc &eTwardy Jak Skala &e&lPASYWNIE",
                            "&7Gdy twoje zdrowie spadnie ponizej",
                            "&a50% maksymalnego zdrowia &7otrzymujesz",
                            "&2+10% defensywy &7na &e10 sekund &8(cooldown 3 min)",
                            "",
                            "&6Umiejetnosc &aOwoc Zycia &e&lRMB",
                            "&7Zyskujesz &a+35% maksymalnego zdrowia &8(cooldown 5 min)"
                    )).toItemStack());
                    break;
                default:
                    return;
            }
            player.sendMessage(Utils.format("&c&lDowodca Strazy &8>> &aPomyslnie wybrales/-as podklase " + e.getCurrentItem().getItemMeta().getDisplayName() + "&a!"));
            return;
        }

        if (title.equals("Dowodca Strazy » ZABOJCA")) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
            if (slot == 22) {
                rpgcore.getKlasyManager().openGUI(player);
                return;
            }
            if (rpgcore.getUserManager().find(uuid).getLvl() < 1) return;
            final Klasa klasa = rpgcore.getKlasyManager().find(uuid);
            if (klasa.getMainKlasa() != KlasyMain.ZABOJCA) return;
            if (klasa.getPodKlasa() != KlasySide.NIE_WYBRANO) return;
            switch (slot) {
                case 12:
                    klasa.setPodKlasa(KlasySide.NINJA);
                    player.getInventory().addItem(new ItemBuilder(Material.DOUBLE_PLANT).setName("&4&lZABOJCA &8- &fNINJA").setLore(Arrays.asList(
                            "&6Umiejetnosc &4&lWKROTCE &e&lLMB",
                            "",
                            "&6Umiejetnosc &fJe-stem Szybki &e&lRMB",
                            "&7Zyskujesz &f+25% predkosci ruchu &7na &e10 sekund",
                            "&7dodatkowo zadajesz &c+5% obrazen &8(cooldown 3 min)"
                    )).toItemStack());
                    break;
                case 14:
                    klasa.setPodKlasa(KlasySide.SKRYTOBOJCA);
                    player.getInventory().addItem(new ItemBuilder(Material.DOUBLE_PLANT).setName("&4&lZABOJCA &8- &fSKRYTOBOJCA").setLore(Arrays.asList(
                            "&6Umiejetnosc &dAsasynacja &e&lPASYWNIE",
                            "&7Gdy twoje zdrowie spadnie ponizej",
                            "&a30% maksymalnego zdrowia &7otrzymujesz",
                            "&dniewidzialnosc &7na &e4 sekundy &7oraz",
                            "&7twoj nastepny cios bedac niewidzialnym",
                            "&7zada &c+5% obrazen &8(cooldown 3 min)",
                            "",
                            "&6Umiejetnosc &eBurza Ostrzy &e&lRMB",
                            "&7Podrzuca wszystkich wrogow w zasiegu &55 kratek",
                            "&7dodatkowo &dspowalnia &7ich na &e3 sekundy &7oraz",
                            "&7kradnie &4+10% ich aktualnego zdrowia &7i zwraca",
                            "&7je tobie (max 3 graczy) &8(cooldown 2 min)"
                    )).toItemStack());
                    break;
                default:
                    return;
            }
            player.sendMessage(Utils.format("&c&lDowodca Strazy &8>> &aPomyslnie wybrales/-as podklase " + e.getCurrentItem().getItemMeta().getDisplayName() + "&a!"));
            return;
        }

        if (title.equals("Dowodca Strazy » CZARODZIEJ")) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
            if (slot == 22) {
                rpgcore.getKlasyManager().openGUI(player);
                return;
            }
            if (rpgcore.getUserManager().find(uuid).getLvl() < 1) return;
            final Klasa klasa = rpgcore.getKlasyManager().find(uuid);
            if (klasa.getMainKlasa() != KlasyMain.CZARODZIEJ) return;
            if (klasa.getPodKlasa() != KlasySide.NIE_WYBRANO) return;
            switch (slot) {
                case 12:
                    klasa.setPodKlasa(KlasySide.NEKROMANTA);
                    player.getInventory().addItem(new ItemBuilder(Material.DOUBLE_PLANT).setName("&5&lCZARODZIEJ &8- &fNEKROMANTA").setLore(Arrays.asList(
                            "&6Umiejetnosc &4&lWKROTCE &e&lLMB &8(cooldown 3 min)",
                            "",
                            "&6Umiejetnosc &cOddech Smoka &e&lRMB",
                            "&7Rzucasz w kierunku wroga &6Ognista Kula",
                            "&7ktory zadaje &c20% aktualnego zdrowia &7oraz",
                            "&5spowalnia &7go o &550% aktualnej predkosci ruchu &8(cooldown 5 min)"
                    )).toItemStack());
                    break;
                case 14:
                    klasa.setPodKlasa(KlasySide.MAG_ZYWIOLOW);
                    player.getInventory().addItem(new ItemBuilder(Material.DOUBLE_PLANT).setName("&5&lCZARODZIEJ &8- &fMAG ZYWIOLOW").setLore(Arrays.asList(
                            "&6Umiejetnosc &5Puszka Pandory &e&lLMB",
                            "&7Nadaje losowy &cnegatywny efekt &7dla kazdego",
                            "&7wroga w zasiegu &510 kratek &8(cooldown 5 min)",
                            "",
                            "&6Umiejetnosc &aTrzesienie Ziemi &e&lRMB",
                            "&7Twoj nastepny atak zada &cobrazenia obszarowe",
                            "&7Uderzony potwor/gracz otrzyma &c100% obrazen&7,",
                            "&7reszta w zaleznosci od odleglosci od celu &8(cooldown 2 min)"
                    )).toItemStack());
                    break;
                default:
                    return;
            }
            player.sendMessage(Utils.format("&c&lDowodca Strazy &8>> &aPomyslnie wybrales/-as podklase " + e.getCurrentItem().getItemMeta().getDisplayName() + "&a!"));
        }
    }
}
