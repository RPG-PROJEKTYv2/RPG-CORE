package rpg.rpgcore.tab;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.*;

public class TabManager {

    private static RPGCORE rpgcore;

    public TabManager(RPGCORE rpgcore) {
        TabManager.rpgcore = rpgcore;
    }
    public static ArrayList<String> lista = new ArrayList<>();
    public static Map<UUID, Tab> uuidTabMap = new HashMap<>();
    public static PacketManager packetManager;

    public static void addPlayer(Player player) {
        final String playerGroup = rpgcore.getPlayerManager().getPlayerGroup(player);
        String prefix = "";
        if (rpgcore.getGuildManager().hasGuild(player.getUniqueId())) {
            prefix = "&8[&3" + rpgcore.getGuildManager().getGuildTag(player.getUniqueId()) + "&8] ";
        }
        lista.add(prefix + Utils.getGroupColor(playerGroup) + player.getName());
        lista = sortList(lista);
    }

    public static void removePlayer(Player player) {
        List<Integer> occruence = new ArrayList<>();
        for (String s : lista) {
            String test = s.substring(s.lastIndexOf(" ") + 1).trim();
            player.sendMessage(test);
            if (Utils.removeColor(test).equals(player.getName())) {
                player.sendMessage("Dodano do listy");
                occruence.add(lista.indexOf(s));
            }
        }

        for (Integer integer : occruence) {
            player.sendMessage(lista.get(integer));
            lista.remove(lista.get(integer));
        }
        lista = sortList(lista);
    }

    public static void add(final Player player) {
        final UUID uuid = player.getUniqueId();
        uuidTabMap.remove(uuid);
        uuidTabMap.put(uuid, new Tab(player, packetManager));
    }

    public static void update(UUID uuid) {
        Tab tab = uuidTabMap.get(uuid);
        Player player = Bukkit.getPlayer(uuid);
        final String tag = rpgcore.getGuildManager().getGuildTag(player.getUniqueId());
        final String playerGroup = rpgcore.getPlayerManager().getPlayerGroup(player);
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
            int number4 = 3;
            for (int i = 1; i < 19; i++) {
                tab.set(player, 0, i, "&7");
                tab.set(player, 1, i, "&7");
                tab.set(player, 2, i, "&7");
                tab.set(player, 3, i, "&7");
                if (lista.size() >= number2) {
                    tab.set(player, 1, i, Utils.format(lista.get(number2 - 1).replaceAll("/", " ")));
                }
                if (lista.size() >= number3) {
                    tab.set(player, 2, i, Utils.format(lista.get(number3 - 1).replaceAll("/", " ")));
                }
                if (lista.size() >= number4) {
                    tab.set(player, 3, i, Utils.format(lista.get(number4 - 1).replaceAll("/", " ")));
                }

                number2 += 4;
                number3 += 4;
                number4 += 4;
            }

            final int lvl = rpgcore.getPlayerManager().getPlayerLvl(player.getUniqueId());
            final String exp = String.format("%.2f", rpgcore.getPlayerManager().getPlayerExp(player.getUniqueId()));
            final String expNextLvl = String.format("%.2f", rpgcore.getLvlManager().getExpForLvl(rpgcore.getPlayerManager().getPlayerLvl(player.getUniqueId()) + 1));
            final String procenty = String.format("%.2f", (rpgcore.getPlayerManager().getPlayerExp(player.getUniqueId()) / rpgcore.getLvlManager().getExpForLvl(lvl + 1)) * 100);

            tab.set(player, 0, 2, "&7Gracz: &c" + player.getName());
            if (Utils.getGroupColor(playerGroup).equals("&7")) {
                tab.set(player, 0, 3, "&7Ranga: &7Gracz");
            } else {
                tab.set(player, 0, 3, "&7Ranga: " + Utils.getGroupColor(playerGroup));
            }
            tab.set(player, 0, 4, "&7Gildia: &3" + tag);
            if (lvl == Utils.MAXLVL) {
                tab.set(player, 0, 5, "&7Poziom: &4&lMAX LVL");
                tab.set(player, 0, 6, "&7EXP: &4&lMAX &7/ &4&lMAX &8(&4&lMAX&8)");
            } else {
                tab.set(player, 0, 5, "&7Poziom: &c" + lvl);
                tab.set(player, 0, 6, "&7EXP: &b" + exp + " &7/ &b" + expNextLvl + " &8(&b" + procenty + " %&8)");
            }
            tab.set(player, 0, 7, "&7Kasa: &6" + Utils.spaceNumber(String.format("%.2f", rpgcore.getPlayerManager().getPlayerKasa(player.getUniqueId()))) + " &2$");
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

    public static Map<UUID, Tab> getUuidTabMap() {
        return uuidTabMap;
    }
}
