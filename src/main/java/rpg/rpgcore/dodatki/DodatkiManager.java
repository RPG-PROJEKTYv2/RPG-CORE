package rpg.rpgcore.dodatki;

import com.google.common.collect.ImmutableSet;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.dodatki.akcesoriaD.objects.AkcesoriaDodatUser;
import rpg.rpgcore.dodatki.akcesoriaP.objects.AkcesoriaPodstUser;
import rpg.rpgcore.dodatki.bony.objects.BonyUser;
import rpg.rpgcore.dodatki.objects.DodatkiUser;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.Map;
import java.util.UUID;

public class DodatkiManager {
    private final Map<UUID, DodatkiUser> userMap;

    public DodatkiManager(final RPGCORE rpgcore) {
        this.userMap = rpgcore.getMongoManager().loadAllDodatki();
    }

    public void openDodatkiGUI(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&c&lMenu Dodatkow"));

        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setName(" ").toItemStack());
        }

        gui.setItem(10, new ItemBuilder(Material.ITEM_FRAME).setName("&6&lAkcesorium Podstawowe").addGlowing().toItemStack());
        gui.setItem(13, new ItemBuilder(Material.SIGN).setName("&6&lBony").addGlowing().toItemStack());
        gui.setItem(16, new ItemBuilder(Material.LEASH).setName("&6&lAkcesorium Dodatkowe").addGlowing().toItemStack());

        player.openInventory(gui);
    }

    public void openAkcePodsGUI(final Player player, final UUID targetUUID) {
        final Inventory gui = Bukkit.createInventory(null, 9, Utils.format("&c&lAkcesorium Podstawowe"));
        final AkcesoriaPodstUser user = find(targetUUID).getAkcesoriaPodstawowe();

        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setName(" ").toItemStack());
        }

        if (user.getTarcza().getType().equals(Material.AIR)) {
            gui.setItem(2, this.createNoAkcesoria("Tarczy"));
        } else {
            gui.setItem(2, user.getTarcza());
        }

        if (user.getNaszyjnik().getType().equals(Material.AIR)) {
            gui.setItem(3, this.createNoAkcesoria("Naszyjnika"));
        } else {
            gui.setItem(3, user.getNaszyjnik());
        }

        if (user.getKolczyki().getType().equals(Material.AIR)) {
            gui.setItem(4, this.createNoAkcesoria("Kolczykow"));
        } else {
            gui.setItem(4, user.getKolczyki());
        }

        if (user.getPierscien().getType().equals(Material.AIR)) {
            gui.setItem(5, this.createNoAkcesoria("Pierscienia"));
        } else {
            gui.setItem(5, user.getPierscien());
        }

        if (user.getDiadem().getType().equals(Material.AIR)) {
            gui.setItem(6, this.createNoAkcesoria("Diademu"));
        } else {
            gui.setItem(6, user.getDiadem());
        }

        gui.setItem(8, Utils.powrot());

        player.openInventory(gui);
    }

    public void openBonyGUI(final Player player, final UUID targetUUID) {
        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&c&lBony"));
        final BonyUser user = find(targetUUID).getBony();

        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName(" ").toItemStack());
        }

        if (user.getDmg5().getType().equals(Material.AIR)) {
            gui.setItem(0, this.createNoAkcesoria("Bonu Srednich Obrazen 5%"));
        } else {
            gui.setItem(0, user.getDmg5());
        }

        if (user.getDmg10().getType().equals(Material.AIR)) {
            gui.setItem(9, this.createNoAkcesoria("Bonu Srednich Obrazen 10%"));
        } else {
            gui.setItem(9, user.getDmg10());
        }

        if (user.getDmg15().getType().equals(Material.AIR)) {
            gui.setItem(18, this.createNoAkcesoria("Bonu Srednich Obrazen 15%"));
        } else {
            gui.setItem(18, user.getDmg15());
        }

        if (user.getDef5().getType().equals(Material.AIR)) {
            gui.setItem(1, this.createNoAkcesoria("Bonu Sredniej Defensywy 5%"));
        } else {
            gui.setItem(1, user.getDef5());
        }

        if (user.getDef10().getType().equals(Material.AIR)) {
            gui.setItem(10, this.createNoAkcesoria("Bonu Sredniej Defensywy 10%"));
        } else {
            gui.setItem(10, user.getDef10());
        }

        if (user.getDef15().getType().equals(Material.AIR)) {
            gui.setItem(19, this.createNoAkcesoria("Bonu Sredniej Defensywy 15%"));
        } else {
            gui.setItem(19, user.getDef15());
        }

        if (user.getKryt5().getType().equals(Material.AIR)) {
            gui.setItem(2, this.createNoAkcesoria("Bonu Szansy Na Cios Krytyczny 5%"));
        } else {
            gui.setItem(2, user.getKryt5());
        }

        if (user.getKryt10().getType().equals(Material.AIR)) {
            gui.setItem(11, this.createNoAkcesoria("Bonu Szansy Na Cios Krytyczny 10%"));
        } else {
            gui.setItem(11, user.getKryt10());
        }

        if (user.getKryt15().getType().equals(Material.AIR)) {
            gui.setItem(20, this.createNoAkcesoria("Bonu Szansy Na Cios Krytyczny 15%"));
        } else {
            gui.setItem(20, user.getKryt15());
        }

        if (user.getWzmkryt10().getType().equals(Material.AIR)) {
            gui.setItem(3, this.createNoAkcesoria("Bonu Szansy Na Wzmocnienie Ciosu Krytycznego 10%"));
        } else {
            gui.setItem(3, user.getWzmkryt10());
        }

        if (user.getBlok20().getType().equals(Material.AIR)) {
            gui.setItem(12, this.createNoAkcesoria("Bonu Szansy Na Blok Ciosu 20%"));
        } else {
            gui.setItem(12, user.getBlok20());
        }

        if (user.getPrzeszywka20().getType().equals(Material.AIR)) {
            gui.setItem(21, this.createNoAkcesoria("Bonu Szansy Na Przeszycie Bloku Ciosu 20%"));
        } else {
            gui.setItem(21, user.getPrzeszywka20());
        }

        if (user.getHp10().getType().equals(Material.AIR)) {
            gui.setItem(4, this.createNoAkcesoria("Bonu Dodatkowego Zdrowia +10"));
        } else {
            gui.setItem(4, user.getHp10());
        }

        if (user.getHp20().getType().equals(Material.AIR)) {
            gui.setItem(13, this.createNoAkcesoria("Bonu Dodatkowego Zdrowia +20"));
        } else {
            gui.setItem(13, user.getHp20());
        }

        if (user.getHp35().getType().equals(Material.AIR)) {
            gui.setItem(22, this.createNoAkcesoria("Bonu Dodatkowego Zdrowia +35"));
        } else {
            gui.setItem(22, user.getHp35());
        }

        if (user.getPredkosc25().getType().equals(Material.AIR)) {
            gui.setItem(5, this.createNoAkcesoria("Bonu Zwiekszonej Predkosci Ruchu +25"));
        } else {
            gui.setItem(5, user.getPredkosc25());
        }

        if (user.getPredkosc50().getType().equals(Material.AIR)) {
            gui.setItem(14, this.createNoAkcesoria("Bonu Zwiekszonej Predkosci Ruchu +50"));
        } else {
            gui.setItem(14, user.getPredkosc50());
        }

        if (user.getDmgMetiny().getType().equals(Material.AIR)) {
            gui.setItem(23, this.createNoAkcesoria("Bonu Zwiekszonych Obrazen W Kamienie Metin +2/3/5"));
        } else {
            gui.setItem(23, user.getDmgMetiny());
        }

        gui.setItem(17, Utils.powrot());

        player.openInventory(gui);
    }

    public void openAkceDodaGUI(final Player player, final UUID targetUUID) {
        final Inventory gui = Bukkit.createInventory(null, 9, Utils.format("&c&lAkcesorium Dodatkowe"));
        final AkcesoriaDodatUser user = find(targetUUID).getAkcesoriaDodatkowe();

        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setName(" ").toItemStack());
        }



        if (user.getSzarfa().getType().equals(Material.AIR)) {
            gui.setItem(1, this.createNoAkcesoria("Szarfy"));
        } else {
            gui.setItem(1, user.getSzarfa());
        }

        if (user.getPas().getType().equals(Material.AIR)) {
            gui.setItem(3, this.createNoAkcesoria("Pasa"));
        } else {
            gui.setItem(3, user.getPas());
        }

        if (user.getMedalion().getType().equals(Material.AIR)) {
            gui.setItem(5, this.createNoAkcesoria("Medalionu"));
        } else {
            gui.setItem(5, user.getMedalion());
        }

        if (user.getEnergia().getType().equals(Material.AIR)) {
            gui.setItem(7, this.createNoAkcesoria("Energii"));
        } else {
            gui.setItem(7, user.getEnergia());
        }

        gui.setItem(8, Utils.powrot());

        player.openInventory(gui);
    }

    public ItemStack createNoAkcesoria(final String type) {
        return new ItemBuilder(Material.IRON_FENCE).setName("&c&lBrak " + type).addGlowing().toItemStack().clone();
    }

    public DodatkiUser find(final UUID uuid) {
        return this.userMap.getOrDefault(uuid, new DodatkiUser(uuid));
    }

    public void add(final DodatkiUser user) {
        this.userMap.put(user.getUuid(), user);
    }

    public void set(final UUID uuid, final DodatkiUser user) {
        this.userMap.replace(uuid, user);
    }

    public ImmutableSet<DodatkiUser> getDodatkiUsers() {
        return ImmutableSet.copyOf(this.userMap.values());
    }
}
