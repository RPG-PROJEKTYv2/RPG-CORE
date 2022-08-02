package rpg.rpgcore.akcesoria;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AkcesoriaManager {

    private final RPGCORE rpgcore;

    private final Map<UUID, Inventory> akcesoriaMap = new HashMap<>();
    private final ItemBuilder noAkcesoria = new ItemBuilder(Material.BARRIER, 1);
    private final ItemBuilder fillGUI = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15);

    public AkcesoriaManager(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    private Inventory createAkcesoriaGUINew(final UUID uuid) {
        final Inventory akcesoriaGUI = Bukkit.createInventory(null, 27, Utils.format("&6&lAkcesoria gracza " + rpgcore.getPlayerManager().getPlayerName(uuid)));

        fillGUI.setName(" ");

        for (int i = 0 ; i < akcesoriaGUI.getSize(); i++) {
            akcesoriaGUI.setItem(i, fillGUI.toItemStack());
        }
        akcesoriaGUI.setItem(10, noAkcesoriaItem("Tarczy"));
        akcesoriaGUI.setItem(11, noAkcesoriaItem("Naszyjnika"));
        akcesoriaGUI.setItem(12, noAkcesoriaItem("Bransolety"));
        akcesoriaGUI.setItem(13, noAkcesoriaItem("Kolczykow"));
        akcesoriaGUI.setItem(14, noAkcesoriaItem("Pierscienia"));
        akcesoriaGUI.setItem(15, noAkcesoriaItem("Energii"));
        akcesoriaGUI.setItem(16, noAkcesoriaItem("Zegarka"));

        this.updateAkcesoriaGUI(uuid, akcesoriaGUI);
        return akcesoriaGUI;
    }

    public Inventory createAkcesoriaGUI(final UUID uuid, final ItemStack[] akcesoria) {
        final Inventory akcesoriaGUI = Bukkit.createInventory(null, 27, Utils.format("&6&lAkcesoria gracza " + rpgcore.getPlayerManager().getPlayerName(uuid)));

        fillGUI.setName(" ");

        for (int i = 0 ; i < akcesoriaGUI.getSize(); i++) {
            akcesoriaGUI.setItem(i, fillGUI.toItemStack());
        }

        for (int i = 0; i < akcesoria.length; i++) {
            akcesoriaGUI.setItem(10 + i, akcesoria[i]);
        }


        this.setAkcesoriaGUI(uuid, akcesoriaGUI);
        return akcesoriaGUI;
    }

    public final ItemStack noAkcesoriaItem(final String nazwaAkcesorium) {
        noAkcesoria.setName("&c&lBrak " + nazwaAkcesorium);
        noAkcesoria.addGlowing();
        return noAkcesoria.toItemStack();
    }

    public final ItemStack[] getAllAkcesoria(final UUID uuid) {
        ItemStack[] akcesoria = new ItemStack[7];
        if (!(rpgcore.getAkcesoriaManager().getAkcesoriaMap().containsKey(uuid))) {
            rpgcore.getAkcesoriaManager().createAkcesoriaGUINew(uuid);
        }
        Inventory gui = this.getAkcesoriaGUI(uuid);

        for (int i = 0; i < akcesoria.length; i++) {
            akcesoria[i] = gui.getItem(10 + i);
        }

        return akcesoria;
    }

    public final double getAkcesoriaBonus(final UUID uuid, final int slotAkcesorium , final String nazwaBonusu) {

        Inventory akcesoria = this.getAkcesoriaGUI(uuid);

        ItemStack akce = akcesoria.getItem(slotAkcesorium);

        for (int i = 0; i < akce.getItemMeta().getLore().size(); i++) {
            if (akce.getItemMeta().getLore().get(i).trim().contains(nazwaBonusu)) {
                return Integer.parseInt(Utils.removeColor(akce.getItemMeta().getLore().get(i).trim().replace(nazwaBonusu + ": ", "").replace("%", "")));
            }
        }

        return 0.0;
    }

    public final double getAkcesoriaBonus(final ItemStack akce , final String nazwaBonusu) {

        for (int i = 0; i < akce.getItemMeta().getLore().size(); i++) {
            if (akce.getItemMeta().getLore().get(i).trim().contains(nazwaBonusu)) {
                return Integer.parseInt(Utils.removeColor(akce.getItemMeta().getLore().get(i).trim().replace(nazwaBonusu + ": ", "").replace("%", "").replace("-", "")));
            }
        }

        return 0.0;
    }

    public void loadAllAkceBonus(final UUID uuid) {

        // TODO     ZROBIC IF CHECKA CZY TABELE ZAWIERAJA JUZ GRACZA
        if (rpgcore.getPlayerManager().getPlayerSrednie(uuid) != 0 || rpgcore.getPlayerManager().getPlayerMinusSrednie(uuid) != 0 || rpgcore.getPlayerManager().getPlayerDef(uuid) != 0 ||
                rpgcore.getPlayerManager().getPlayerMinusDef(uuid) != 0 || rpgcore.getPlayerManager().getPlayerSilnyNaLudzi(uuid) != 0 || rpgcore.getPlayerManager().getPlayerDefNaLudzi(uuid) != 0 ||
                rpgcore.getPlayerManager().getPlayerSilnyNaMoby(uuid) != 0 || rpgcore.getPlayerManager().getPlayerDefNaMoby(uuid) != 0 || rpgcore.getPlayerManager().getPlayerDamage(uuid) != 0 ||
                rpgcore.getPlayerManager().getPlayerBlok(uuid) != 0 || rpgcore.getPlayerManager().getPlayerKryt(uuid) != 0 || rpgcore.getPlayerManager().getPlayerPrzeszywka(uuid) != 0 || rpgcore.getPlayerManager().getPlayerHP(uuid) != 0) {
            return;
        }

        final Inventory akceGUI = this.getAkcesoriaGUI(uuid);
        ItemStack akce;

        for (int i = 0; i < 7 ; i++) {
            akce = akceGUI.getItem(10 + i);
            if (akce.getType() != Material.BARRIER) {

                if (akce.getType() == Material.ITEM_FRAME) {
                    rpgcore.getPlayerManager().updatePlayerDef(uuid, rpgcore.getPlayerManager().getPlayerDef(uuid) + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(akce, "Obrona"));
                    rpgcore.getPlayerManager().updatePlayerBlok(uuid, rpgcore.getPlayerManager().getPlayerBlok(uuid) + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(akce, "Blok Ciosu"));
                    rpgcore.getPlayerManager().updatePlayerDamage(uuid, rpgcore.getPlayerManager().getPlayerDamage(uuid) + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(akce, "Obrazenia"));
                    System.out.println("Tarcza blok - " + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(akce, "Blok Ciosu"));
                    System.out.println("Tarcza dmg - " + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(akce, "Obrazenia"));
                }

                if (akce.getType() == Material.STORAGE_MINECART) {
                    rpgcore.getPlayerManager().updatePlayerPrzeszywka(uuid, rpgcore.getPlayerManager().getPlayerPrzeszywka(uuid) + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(akce, "Przeszycie Bloku"));
                    rpgcore.getPlayerManager().updatePlayerDamage(uuid, rpgcore.getPlayerManager().getPlayerDamage(uuid) + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(akce, "Obrazenia"));
                    System.out.println("Naszyjnik dmg - " + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(akce, "Obrazenia"));
                }


                if (akce.getType() == Material.POWERED_MINECART) {
                    rpgcore.getPlayerManager().updatePlayerKryt(uuid, rpgcore.getPlayerManager().getPlayerKryt(uuid) + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(akce, "Cios Krytyczny"));
                    rpgcore.getPlayerManager().updatePlayerSrednie(uuid, rpgcore.getPlayerManager().getPlayerSrednie(uuid) + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(akce, "Srednie Obrazenia"));
                }

                if (akce.getType() == Material.HOPPER_MINECART) {
                    rpgcore.getPlayerManager().updatePlayerHP(uuid, rpgcore.getPlayerManager().getPlayerHP(uuid) + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(akce, "Dodatkowe HP"));
                    rpgcore.getPlayerManager().updatePlayerSilnyNaLudzi(uuid, rpgcore.getPlayerManager().getPlayerSilnyNaLudzi(uuid) + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(akce, "Silny przeciwko Ludziom"));
                }

                if (akce.getType() == Material.EXPLOSIVE_MINECART) {
                    rpgcore.getPlayerManager().updatePlayerBlok(uuid, rpgcore.getPlayerManager().getPlayerBlok(uuid) + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(akce, "Blok Ciosu"));
                    rpgcore.getPlayerManager().updatePlayerHP(uuid, rpgcore.getPlayerManager().getPlayerHP(uuid) + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(akce, "Dodatkowe HP"));
                    System.out.println("Pierek blok -" + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(akce, "Blok Ciosu"));
                }

                if (akce.getType() == Material.MINECART) {
                    rpgcore.getPlayerManager().updatePlayerDef(uuid, rpgcore.getPlayerManager().getPlayerDef(uuid) + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(akce, "Obrona"));
                    rpgcore.getPlayerManager().updatePlayerBlok(uuid, rpgcore.getPlayerManager().getPlayerBlok(uuid) + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(akce, "Blok Ciosu"));
                    rpgcore.getPlayerManager().updatePlayerMinusSrednie(uuid, rpgcore.getPlayerManager().getPlayerMinusSrednie(uuid) + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(akce, "Srednie Obrazenia"));
                    System.out.println("energia blok - " + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(akce, "Blok Ciosu"));
                }

                if (akce.getType() == Material.WATCH) {
                    rpgcore.getPlayerManager().updatePlayerDamage(uuid, rpgcore.getPlayerManager().getPlayerDamage(uuid) + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(akce, "Obrazenia"));
                    rpgcore.getPlayerManager().updatePlayerSilnyNaMoby(uuid, rpgcore.getPlayerManager().getPlayerSilnyNaMoby(uuid) + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(akce, "Silny przeciwko Potworom"));
                    rpgcore.getPlayerManager().updatePlayerMinusDef(uuid, rpgcore.getPlayerManager().getPlayerMinusDef(uuid) + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(akce, "Obrona"));
                    System.out.println("Zegarek dmg - " + rpgcore.getAkcesoriaManager().getAkcesoriaBonus(akce, "Obrazenia"));
                }

            }
        }
    }

    @Getter
    public Inventory getAkcesoriaGUI(final UUID uuid) {
        akcesoriaMap.computeIfAbsent(uuid, k -> this.createAkcesoriaGUINew(uuid));
        return this.akcesoriaMap.get(uuid);
    }

    @Getter
    public Map<UUID, Inventory> getAkcesoriaMap() {
        return this.akcesoriaMap;
    }

    @Setter
    public void updateAkcesoriaGUI(final UUID uuid, final Inventory inventory) {
        this.akcesoriaMap.replace(uuid, inventory);
    }

    @Setter
    public void setAkcesoriaGUI(final UUID uuid, final Inventory inventory) {
        this.akcesoriaMap.put(uuid, inventory);
    }

    public boolean isTarczaEquiped(final UUID uuid) {
        final Inventory akceGUI = this.getAkcesoriaGUI(uuid);
        return !akceGUI.getItem(10).getType().equals(Material.BARRIER);
    }

    public boolean isNaszyjnikEquiped(final UUID uuid) {
        final Inventory akceGUI = this.getAkcesoriaGUI(uuid);
        return !akceGUI.getItem(11).getType().equals(Material.BARRIER);
    }

    public boolean isBransoletaEquiped(final UUID uuid) {
        final Inventory akceGUI = this.getAkcesoriaGUI(uuid);
        return !akceGUI.getItem(12).getType().equals(Material.BARRIER);
    }

    public boolean isKolczykiEquiped(final UUID uuid) {
        final Inventory akceGUI = this.getAkcesoriaGUI(uuid);
        return !akceGUI.getItem(13).getType().equals(Material.BARRIER);
    }

    public boolean isPierscienEquiped(final UUID uuid) {
        final Inventory akceGUI = this.getAkcesoriaGUI(uuid);
        return !akceGUI.getItem(14).getType().equals(Material.BARRIER);
    }

    public boolean isEnergiaEquiped(final UUID uuid) {
        final Inventory akceGUI = this.getAkcesoriaGUI(uuid);
        return !akceGUI.getItem(15).getType().equals(Material.BARRIER);
    }

    public boolean isZegarekEquiped(final UUID uuid) {
        final Inventory akceGUI = this.getAkcesoriaGUI(uuid);
        return !akceGUI.getItem(16).getType().equals(Material.BARRIER);
    }

}
