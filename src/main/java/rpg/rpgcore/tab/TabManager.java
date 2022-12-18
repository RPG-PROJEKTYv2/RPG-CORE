package rpg.rpgcore.tab;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.Utils;

import java.util.*;
import java.util.stream.Stream;

public class TabManager {

    private static RPGCORE rpgcore;

    public TabManager(RPGCORE rpgcore) {
        TabManager.rpgcore = rpgcore;
    }

    public static ArrayList<String> lista = new ArrayList<>();
    public static Map<UUID, Tab> uuidTabMap = new HashMap<>();
    private static Map<UUID, Integer> topki = new HashMap<>();
    public static PacketManager packetManager;


    public static void addPlayer(Player player) {
        String prefix;
        String guild = "";
        if (rpgcore.getUserManager().find(player.getUniqueId()).getRankUser().isStaff() && rpgcore.getUserManager().find(player.getUniqueId()).isAdminCodeLogin()) {
            prefix = rpgcore.getUserManager().find(player.getUniqueId()).getRankUser().getRankType().getPrefix();
        } else {
            prefix = rpgcore.getUserManager().find(player.getUniqueId()).getRankPlayerUser().getRankType().getPrefix();
        }
        if (rpgcore.getGuildManager().hasGuild(player.getUniqueId())) {
            guild = "&8[&3" + rpgcore.getGuildManager().getGuildTag(player.getUniqueId()) + "&8] ";
        }
        if (prefix.equals("&7 ")) {
            prefix = "&7";
        }
        lista.add(guild + prefix + player.getName());
        lista = sortList(lista);
    }

    public static void removePlayer(Player player) {
        List<Integer> occruence = new ArrayList<>();
        for (String s : lista) {
            String test = s.substring(s.lastIndexOf(" ") + 1).trim();
            if (Utils.removeColor(test).equals(player.getName())) {
                occruence.add(lista.indexOf(s));
            }
        }

        for (Integer integer : occruence) {
            lista.remove(lista.get(integer));
        }
        lista = sortList(lista);
    }

    public static void add(final Player player) {
        final UUID uuid = player.getUniqueId();
        uuidTabMap.remove(uuid);
        uuidTabMap.put(uuid, new Tab(player, packetManager));
        topki.put(uuid, 0);
    }

    public static void update(UUID uuid) {
        final Tab tab = uuidTabMap.get(uuid);
        final Player player = Bukkit.getPlayer(uuid);
        final String tag = rpgcore.getGuildManager().getGuildTag(player.getUniqueId());
        final User user = rpgcore.getUserManager().find(player.getUniqueId());
        String prefix;
        if (user.getRankUser().isStaff() && user.isAdminCodeLogin()) {
            prefix = user.getRankUser().getRankType().getPrefix();
        } else {
            prefix = user.getRankPlayerUser().getRankType().getPrefix();
        }
        if (tab != null) {
            tab.set(player, 0, 0, "&7Informacje");
            tab.set(player, 1, 0, "&7Lista Graczy:");
            tab.set(player, 2, 0, "&7");
            tab.set(player, 3, 0, "&7");
            tab.set(player, 0, 19, "&7");
            tab.set(player, 1, 19, "&7");
            tab.set(player, 2, 19, "&7");
            tab.set(player, 3, 19, "&7");
            int number2 = 1;
            int number3 = 2;
            for (int i = 1; i < 19; i++) {
                tab.set(player, 0, i, "&7");
                tab.set(player, 1, i, "&7");
                tab.set(player, 2, i, "&7");
                tab.set(player, 3, i, "&7");
                if (lista.size() >= number2) {
                    tab.set(player, 1, i, Utils.format(lista.get(number2 - 1).replaceAll("/", " "))); //0, 2
                }
                if (lista.size() >= number3) {
                    tab.set(player, 2, i, Utils.format(lista.get(number3 - 1).replaceAll("/", " "))); // 1, 3
                }

                number2 += 2;
                number3 += 2;
            }

            final int lvl = user.getLvl();
            final String exp = String.format("%.2f", user.getExp());
            final String expNextLvl = String.format("%.2f", rpgcore.getLvlManager().getExpForLvl(lvl + 1));
            final String procenty = String.format("%.2f", (user.getExp() / rpgcore.getLvlManager().getExpForLvl(lvl + 1)) * 100);

            tab.set(player, 0, 2, "&7Gracz: &c" + player.getName());
            tab.set(player, 0, 3, "&7Ranga: " + prefix);
            tab.set(player, 0, 4, "&7Gildia: &3" + tag);
            if (lvl == Utils.MAXLVL) {
                tab.set(player, 0, 5, "&7Poziom: &4&lMAX");
                tab.set(player, 0, 6, "&7EXP: &4&lMAX &7/ &4&lMAX &8(&4&lMAX&8)");
            } else {
                tab.set(player, 0, 5, "&7Poziom: &c" + lvl);
                tab.set(player, 0, 6, "&7EXP: &b" + exp + " &7/ &b" + expNextLvl + " &8(&b" + procenty + " %&8)");
            }
            tab.set(player, 0, 7, "&7Kasa: &6" + Utils.spaceNumber(String.format("%.2f", user.getKasa())) + " &2$");
            tab.set(player, 0, 8, "&7                                ");
            tab.set(player, 0, 9, "&4&lKLAN");
            if (!tag.equals("Brak Klanu")) {
                tab.set(player, 0, 10, "&7Punkty: &c" + rpgcore.getGuildManager().getGuildPoints(tag));
                tab.set(player, 0, 11, "&7Poziom: &c" + rpgcore.getGuildManager().getGuildLvl(tag));
                if (rpgcore.getGuildManager().getGuildLvl(tag) == 50) {
                    tab.set(player, 0, 12, "&7Exp: &4&lMAX &7/ &4&lMAX");
                } else {
                    tab.set(player, 0, 12, "&7Exp: &c" + rpgcore.getGuildManager().getGuildExp(tag) + "&7/&c" + rpgcore.getGuildManager().getGuildNextLvlExp(tag));
                }
                tab.set(player, 0, 13, "&7Kredyty: &c" + rpgcore.getGuildManager().getGuildBalance(tag) + " &7kredytow");
                tab.set(player, 0, 14, "&7Zabojstwa: &c" + rpgcore.getGuildManager().getGuildKillsAll(tag));
                tab.set(player, 0, 15, "&7Zgony: &c" + rpgcore.getGuildManager().getGuildDeathsAll(tag));
            } else {
                tab.set(player, 0, 10, "&7Punkty: &cn/a");
                tab.set(player, 0, 11, "&7Poziom: &cn/a");
                tab.set(player, 0, 12, "&7Exp: &cn/a");
                tab.set(player, 0, 13, "&7Kredyty: &cn/a");
                tab.set(player, 0, 14, "&7Zabojstwa: &cn/a");
                tab.set(player, 0, 15, "&7Zgony: &cn/a");
            }
            if (topki.get(uuid) == 2) {
                topki.replace(uuid, 0);
            }

            Stream<Map.Entry<String, Integer>> sortedDesc;
            final List<String> afterSort = new ArrayList<>();

            switch (topki.get(uuid)) {
                case 0:
                    tab.set(player, 3, 1, "&6Topka Poziomu");
                    final Map<String, Integer> topki1 = new HashMap<>();
                    for (User user1 : rpgcore.getUserManager().getUserObjects()) {
                        if (user1.getRankUser().isHighStaff()) continue;
                        topki1.put(user1.getName(), user1.getLvl());
                    }
                    sortedDesc = topki1.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue()));
                    sortedDesc.forEach(e -> afterSort.add(e.getKey()));
                    if (afterSort.size() < 18) {
                        for (int i = 0; i < 18; i++) {
                            afterSort.add("Brak");
                            if (afterSort.size() == 18) {
                                break;
                            }
                        }
                    }
                    for (int i = 2; i < 18; i++) {
                        if (!afterSort.get(i - 2).equals("Brak")) {
                            final User user1 = rpgcore.getUserManager().find(afterSort.get(i - 2));
                            if (i - 1 == 1) {
                                tab.set(player, 3, i, "&7" + (i - 1) + ". &4" + afterSort.get(i - 2) + " &7 &3" + user1.getLvl() + " lvl");
                            } else if (i - 1 == 2 || i - 1 == 3) {
                                tab.set(player, 3, i, "&7" + (i - 1) + ". &6" + afterSort.get(i - 2) + " &7 &3" + user1.getLvl() + " lvl");
                            } else if (i - 1 == 4 || i - 1 == 5) {
                                tab.set(player, 3, i, "&7" + (i - 1) + ". &e" + afterSort.get(i - 2) + " &7 &3" + user1.getLvl() + " lvl");
                            } else {
                                tab.set(player, 3, i, "&7" + (i - 1) + ". &7" + afterSort.get(i - 2) + " &7 &3" + user1.getLvl() + " lvl");
                            }
                        } else {
                            if (i - 1 == 1) {
                                tab.set(player, 3, i, "&7" + (i - 1) + ". &4" + afterSort.get(i - 2));
                            } else if (i - 1 == 2 || i - 1 == 3) {
                                tab.set(player, 3, i, "&7" + (i - 1) + ". &6" + afterSort.get(i - 2));
                            } else if (i - 1 == 4 || i - 1 == 5) {
                                tab.set(player, 3, i, "&7" + (i - 1) + ". &e" + afterSort.get(i - 2));
                            } else {
                                tab.set(player, 3, i, "&7" + (i - 1) + ". &7" + afterSort.get(i - 2));
                            }
                        }
                    }
                    break;
                case 1:
                    tab.set(player, 3, 1, "&6Topka Klanow");
                    final Map<String, Integer> topki2 = new HashMap<>();
                    for (String s : rpgcore.getGuildManager().getListOfGuilds()) {
                        topki2.put(s, rpgcore.getGuildManager().getGuildPoints(s));
                    }
                    sortedDesc = topki2.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue()));
                    sortedDesc.forEach(e -> afterSort.add(e.getKey()));
                    if (afterSort.size() < 18) {
                        for (int i = 0; i < 18; i++) {
                            afterSort.add("Brak Klanu");
                            if (afterSort.size() == 18) {
                                break;
                            }
                        }
                    }
                    for (int i = 2; i < 18; i++) {
                        if (!afterSort.get(i - 2).equals("Brak Klanu")) {
                            if (i - 1 == 1) {
                                tab.set(player, 3, i, "&7" + (i - 1) + ". &4" + afterSort.get(i - 2) + " &7 &3" + rpgcore.getGuildManager().getGuildPoints(afterSort.get(i - 2)) + " pkt");
                            } else if (i - 1 == 2 || i - 1 == 3) {
                                tab.set(player, 3, i, "&7" + (i - 1) + ". &6" + afterSort.get(i - 2) + " &7 &3" + rpgcore.getGuildManager().getGuildPoints(afterSort.get(i - 2)) + " pkt");
                            } else if (i - 1 == 4 || i - 1 == 5) {
                                tab.set(player, 3, i, "&7" + (i - 1) + ". &e" + afterSort.get(i - 2) + " &7 &3" + rpgcore.getGuildManager().getGuildPoints(afterSort.get(i - 2)) + " pkt");
                            } else {
                                tab.set(player, 3, i, "&7" + (i - 1) + ". &7" + afterSort.get(i - 2) + " &7 &3" + rpgcore.getGuildManager().getGuildPoints(afterSort.get(i - 2)) + " pkt");
                            }
                        } else {
                            if (i - 1 == 1) {
                                tab.set(player, 3, i, "&7" + (i - 1) + ". &4" + afterSort.get(i - 2));
                            } else if (i - 1 == 2 || i - 1 == 3) {
                                tab.set(player, 3, i, "&7" + (i - 1) + ". &6" + afterSort.get(i - 2));
                            } else if (i - 1 == 4 || i - 1 == 5) {
                                tab.set(player, 3, i, "&7" + (i - 1) + ". &e" + afterSort.get(i - 2));
                            } else {
                                tab.set(player, 3, i, "&7" + (i - 1) + ". &7" + afterSort.get(i - 2));
                            }
                        }
                    }
                    break;
            }
            topki.replace(uuid, topki.get(uuid) + 1);

            tab.set(player,
                    "\n&7Witamy na Serwerze &cHellRPG.pl\n",
                    "\n&7Aktualnie graczy: &c" + Bukkit.getOnlinePlayers().size() +
                            "\n&7Twoj Ping: &c" + ((CraftPlayer) player).getHandle().ping + " &7ms" +
                            "\n&7Discord: &cdc.HellRPG.pl               &7Sklep: &cHellRPG.pl               &7Facebook: &cfb.HellRPG.pl\n");
        }
    }

    private static ArrayList<String> sortList(final ArrayList<String> listGiven) {
        final ArrayList<String> list = new ArrayList<>();

        for (String s : listGiven) {
            if (s.contains("DEV")) {
                list.add(s);
            }
        }

        for (String s : listGiven) {
            if (s.contains("H@")) {
                list.add(s);
            }
        }

        for (String s : listGiven) {
            if (s.contains("Admin")) {
                if (!list.contains(s)) {
                    list.add(s);
                }
            }
        }

        for (String s : listGiven) {
            if (s.contains("GM")) {
                if (!list.contains(s)) {
                    list.add(s);
                }
            }
        }

        for (String s : listGiven) {
            if (s.contains("Mod")) {
                if (!list.contains(s)) {
                    list.add(s);
                }
            }
        }

        for (String s : listGiven) {
            if (s.contains("KidMod")) {
                if (!list.contains(s)) {
                    list.add(s);
                }
            }
        }

        for (String s : listGiven) {
            if (s.contains("Helper")) {
                if (!list.contains(s)) {
                    list.add(s);
                }
            }
        }

        for (String s : listGiven) {
            if (s.contains("JrHelper")) {
                if (!list.contains(s)) {
                    list.add(s);
                }
            }
        }

        for (String s : listGiven) {
            if (s.contains("ELITA")) {
                if (!list.contains(s)) {
                    list.add(s);
                }
            }
        }

        for (String s : listGiven) {
            if (s.contains("Svip")) {
                if (!list.contains(s)) {
                    list.add(s);
                }
            }
        }

        for (String s : listGiven) {
            if (s.contains("Vip")) {
                if (!list.contains(s)) {
                    list.add(s);
                }
            }
        }

        for (String s : listGiven) {
            if (s.contains("Bud")) {
                if (!list.contains(s)) {
                    list.add(s);
                }
            }
        }

        for (String s : listGiven) {
            if (s.contains("[")) {
                if (!list.contains(s)) {
                    list.add(s);
                }
            }
        }

        for (String s : listGiven) {
            if (!list.contains(s)) {
                list.add(s);
            }
        }


        return list;
    }
}
