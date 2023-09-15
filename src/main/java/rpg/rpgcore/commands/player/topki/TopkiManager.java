package rpg.rpgcore.commands.player.topki;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.guilds.objects.GuildObject;
import rpg.rpgcore.osiagniecia.objects.OsUser;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.DoubleUtils;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.*;
import java.util.stream.Stream;

public class TopkiManager {
    private final RPGCORE rpgcore;

    private List<String> czasMap = new ArrayList<>(10);
    private List<String> lvlMap = new ArrayList<>(10);
    private List<String> krytykMap = new ArrayList<>(10);
    private List<String> gildiaLvlMap = new ArrayList<>(10);

    public TopkiManager(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
        this.updateTopki();
    }

    public void updateTopki() {
        this.initTopkaCzasu();
        this.initTopkaLvl();
        this.initTopkaKrytyka();
        this.initTopkaGildii();
    }

    public void initTopkaCzasu() {
        final Multimap<String, Long> czasMap = ArrayListMultimap.create();
        for (OsUser osUser : this.rpgcore.getOsManager().getOsUsers()) {
            if (rpgcore.getUserManager().find(osUser.getUuid()) != null && !rpgcore.getUserManager().find(osUser.getUuid()).getRankUser().isHighStaff()) {
                czasMap.put(rpgcore.getUserManager().find(osUser.getUuid()).getName(), osUser.getCzasProgress());
            }
        }
        final List<String> topka = new ArrayList<>(10);
        Stream<Map.Entry<String, Long>> sorted = czasMap.entries().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue()));
        sorted.limit(10).forEach(e -> topka.add(e.getKey() + " &8(&7" + Utils.durationToString(e.getValue(), true) + "&8)"));
        if (topka.size() < 10) {
            for (int i = topka.size(); i < 10; i++) {
                topka.add("Brak");
            }
        }
        this.czasMap = topka;
    }

    public void initTopkaLvl() {
        final Multimap<String, Integer> lvlMap = ArrayListMultimap.create();
        for (User user : this.rpgcore.getUserManager().getUserObjects()) {
            if (!user.getRankUser().isHighStaff()) {
                lvlMap.put(user.getName(), user.getLvl());
            }
        }
        final List<String> topka = new ArrayList<>(10);
        Stream<Map.Entry<String, Integer>> sorted = lvlMap.entries().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue()));
        sorted.limit(10).forEach(e -> topka.add(e.getKey() + " &8(&7" + e.getValue() + " poziom&8)"));
        if (topka.size() < 10) {
            for (int i = topka.size(); i < 10; i++) {
                topka.add("Brak");
            }
        }
        this.lvlMap = topka;
    }

    public void initTopkaKrytyka() {
        final Multimap<String, Double> krytykMap = ArrayListMultimap.create();
        for (User user : this.rpgcore.getUserManager().getUserObjects()) {
            if (!user.getRankUser().isHighStaff()) {
                krytykMap.put(user.getName(), user.getKrytyk());
            }
        }
        final List<String> topka = new ArrayList<>(10);
        Stream<Map.Entry<String, Double>> sorted = krytykMap.entries().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue()));
        sorted.limit(10).forEach(e -> topka.add(e.getKey() + " &8(&7" + Utils.spaceNumber(DoubleUtils.round(e.getValue(), 2)) + " dmg&8)"));
        if (topka.size() < 10) {
            for (int i = topka.size(); i < 10; i++) {
                topka.add("Brak");
            }
        }
        this.krytykMap = topka;
    }

    public void initTopkaGildii() {
        final Multimap<String, Integer> gildiaLvlMap = ArrayListMultimap.create();
        for (GuildObject guildObject : this.rpgcore.getGuildManager().getGuilds()) {
            gildiaLvlMap.put(guildObject.getTag(), guildObject.getGuild().getLevel());
        }
        final List<String> topka = new ArrayList<>(10);
        Stream<Map.Entry<String, Integer>> sorted = gildiaLvlMap.entries().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue()));
        sorted.limit(10).forEach(e -> topka.add(e.getKey() + " &8(&7" + e.getValue() + " poziom&8)"));
        if (topka.size() < 10) {
            for (int i = topka.size(); i < 10; i++) {
                topka.add("Brak");
            }
        }
        this.gildiaLvlMap = topka;
    }

    public void openTopkaGui(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&3&lTopki"));

        for (int i = 0; i < 27; i++) {
            if (i % 2 == 0) {
                gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName(" ").toItemStack());
            } else {
                gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 9).setName(" ").toItemStack());
            }
        }

        gui.setItem(10, new ItemBuilder(Material.WATCH).setName("&3&lTopka czasu").setLore(Arrays.asList(
                "&41. " + this.czasMap.get(0),
                "&62. " + this.czasMap.get(1),
                "&63. " + this.czasMap.get(2),
                "&e4. " + this.czasMap.get(3),
                "&e5. " + this.czasMap.get(4),
                "&76. " + this.czasMap.get(5),
                "&77. " + this.czasMap.get(6),
                "&78. " + this.czasMap.get(7),
                "&79. " + this.czasMap.get(8),
                "&710. " + this.czasMap.get(9),
                "",
                "&8Topki sa resetowane co 10min"
        )).toItemStack());

        gui.setItem(12, new ItemBuilder(Material.EXP_BOTTLE).setName("&3&lTopka poziomu").setLore(Arrays.asList(
                "&41. " + this.lvlMap.get(0),
                "&62. " + this.lvlMap.get(1),
                "&63. " + this.lvlMap.get(2),
                "&e4. " + this.lvlMap.get(3),
                "&e5. " + this.lvlMap.get(4),
                "&76. " + this.lvlMap.get(5),
                "&77. " + this.lvlMap.get(6),
                "&78. " + this.lvlMap.get(7),
                "&79. " + this.lvlMap.get(8),
                "&710. " + this.lvlMap.get(9),
                "",
                "&8Topki sa resetowane co 10min"
        )).toItemStack());

        gui.setItem(14, new ItemBuilder(Material.FIREBALL).setName("&3&lTopka krytyka").setLore(Arrays.asList(
                "&41. " + this.krytykMap.get(0),
                "&62. " + this.krytykMap.get(1),
                "&63. " + this.krytykMap.get(2),
                "&e4. " + this.krytykMap.get(3),
                "&e5. " + this.krytykMap.get(4),
                "&76. " + this.krytykMap.get(5),
                "&77. " + this.krytykMap.get(6),
                "&78. " + this.krytykMap.get(7),
                "&79. " + this.krytykMap.get(8),
                "&710. " + this.krytykMap.get(9),
                "",
                "&8Topki sa resetowane co 10min"
        )).toItemStack());

        gui.setItem(16, new ItemBuilder(Material.FLINT).setName("&3&lTopka gildii").setLore(Arrays.asList(
                "&41. " + this.gildiaLvlMap.get(0),
                "&62. " + this.gildiaLvlMap.get(1),
                "&63. " + this.gildiaLvlMap.get(2),
                "&e4. " + this.gildiaLvlMap.get(3),
                "&e5. " + this.gildiaLvlMap.get(4),
                "&76. " + this.gildiaLvlMap.get(5),
                "&77. " + this.gildiaLvlMap.get(6),
                "&78. " + this.gildiaLvlMap.get(7),
                "&79. " + this.gildiaLvlMap.get(8),
                "&710. " + this.gildiaLvlMap.get(9),
                "",
                "&8Topki sa resetowane co 10min"
        )).toItemStack());


        player.openInventory(gui);
    }

}
