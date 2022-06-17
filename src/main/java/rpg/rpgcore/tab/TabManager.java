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
            tab.set(player, 0, 0, "&FInformacje o graczu             ");
            tab.set(player, 1, 0, "&fLista Graczy                    ");
            tab.set(player, 2, 0, "&7                                ");
            tab.set(player, 3, 0, "&7                                ");
            tab.set(player, 0, 19, "&7                                ");
            tab.set(player, 1, 19, "&7                                ");
            tab.set(player, 2, 19, "&7                                ");
            tab.set(player, 3, 19, "&7                                ");
            int number2 = 1;
            int number3 = 2;
            int number4 = 3;
            for (int i = 1; i < 19; i++) {
                tab.set(player, 0, i, "&7                                ");
                tab.set(player, 1, i, "&7                                ");
                tab.set(player, 2, i, "&7                                ");
                tab.set(player, 3, i, "&7                                ");
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

            tab.set(player, 0, 2, "&fGracz: &b" + player.getName());
            if (Utils.getGroupColor(playerGroup).equals("&7")) {
                tab.set(player, 0, 3, "&fRanga: &7Gracz");
            } else {
                tab.set(player, 0, 3, "&fRanga: " + Utils.getGroupColor(playerGroup));
            }
            tab.set(player, 0, 4, "&fGildia: &b" + tag);
            if (lvl == Utils.MAXLVL) {
                tab.set(player, 0, 5, "&fPoziom: &4&lMAX LVL");
                tab.set(player, 0, 6, "&fEXP: &4&lMAX &f/ &4&lMAX &8(&4&lMAX&8)");
            } else {
                tab.set(player, 0, 5, "&fPoziom: &b" + lvl);
                tab.set(player, 0, 6, "&fEXP: &b" + exp + " &f/ &b" + expNextLvl + " &8(&b" + procenty + " %&8)");
            }
            tab.set(player, 0, 7, "&fKasa: &b" + Utils.spaceNumber(String.format("%.2f", rpgcore.getPlayerManager().getPlayerKasa(player.getUniqueId()))) + " &2$");
            tab.set(player, 0, 8, "&7                                ");
            tab.set(player, 0, 9, "&f&lKLAN");
            if (!tag.equals("Brak Klanu")) {
                tab.set(player, 0, 10, "&fPunkty: &b" + rpgcore.getGuildManager().getGuildPoints(tag));
                tab.set(player, 0, 11, "&fPoziom: &b" + rpgcore.getGuildManager().getGuildLvl(tag));
                tab.set(player, 0, 12, "&fKredyty: &b" + rpgcore.getGuildManager().getGuildBalance(tag) + " &fkredytow");
                tab.set(player, 0, 13, "&fZabojstwa: &b" + rpgcore.getGuildManager().getGuildKillsAll(tag));
                tab.set(player, 0, 14, "&fZgony: &b" + rpgcore.getGuildManager().getGuildDeathsAll(tag));
            } else {
                tab.set(player, 0, 10, "&fPunkty: &bn/a");
                tab.set(player, 0, 11, "&fPoziom: &bn/a");
                tab.set(player, 0, 12, "&fKredyty: &bn/a");
                tab.set(player, 0, 13, "&fZabojstwa: &bn/a");
                tab.set(player, 0, 14, "&fZgony: &bn/a");
            }

            /*if (!tag.equals("Brak Klanu")) {
                tab.set(player, 3, 1, "&fKlan: &3" + tag);
                tab.set(player, 3, 2, "&fLider: &3" + rpgcore.getPlayerManager().getPlayerName(rpgcore.getGuildManager().getGuildOwner(tag)));
                if (rpgcore.getPlayerManager().getPlayerName(rpgcore.getGuildManager().getGuildCoOwner(tag)) == null) {
                    tab.set(player, 3, 3, "&fZastepca: &3Brak Zastepcy");
                } else {
                    tab.set(player, 3, 3, "&fZastepca: &3" + rpgcore.getPlayerManager().getPlayerName(rpgcore.getGuildManager().getGuildCoOwner(tag)));
                }
                tab.set(player, 3, 4, "&fCzlonkowie: &3" + rpgcore.getGuildManager().getGuildMembers(tag).size() + "&f/&320");
                tab.set(player, 3, 5, "&fPoziom: &3" + rpgcore.getGuildManager().getGuildLvl(tag));
                tab.set(player, 3, 6, "&fExp: &3" + String.format("%.2f", rpgcore.getGuildManager().getGuildExp(tag)) + " &f/&3Zrobic next lvl");
                tab.set(player, 3, 7, "&fKredyty: &3" + rpgcore.getGuildManager().getGuildBalance(tag));
                tab.set(player, 3, 8, "                                  ");
                tab.set(player, 3, 9, "&fPunkty: &3" + rpgcore.getGuildManager().getGuildPoints(tag));
                tab.set(player, 3, 10, "&fZabojstw razem: &3" + rpgcore.getGuildManager().getGuildKillsAll(tag));
                tab.set(player, 3, 11, "&fSmierci razem: &3" + rpgcore.getGuildManager().getGuildDeathsAll(tag));
                tab.set(player, 3, 12, "                                  ");
                tab.set(player, 3, 13, "&f&lBonusy:                       ");
                tab.set(player, 3, 14, "&8- &fSrednie obrazenia: &3" + String.format("%.1f", rpgcore.getGuildManager().getGuildSredniDmg(tag)) + "%&f/&350%");
                tab.set(player, 3, 15, "&8- &fSrednia defensywa: &3" + String.format("%.1f", rpgcore.getGuildManager().getGuildSredniDef(tag)) + "%&f/&350%");
                tab.set(player, 3, 16, "&8- &fSilny na Ludzi: &3" + String.format("%.1f", rpgcore.getGuildManager().getGuildSilnyNaLudzi(tag)) + "%&f/&375%");
                tab.set(player, 3, 17, "&8- &fDef na Ludzi: &3" + String.format("%.1f", rpgcore.getGuildManager().getGuildDefNaLudzi(tag)) + "%&f/&375%");
                tab.set(player, 3, 18, "&8- &fDodatkowy EXP: &3" + String.format("%.1f", rpgcore.getGuildManager().getGuildDodatkowyExp(tag)) + "%&f/&350%");
            } else {
                tab.set(player, 3, 1, "&fKlan: &3Brak Klanu");
                tab.set(player, 3, 2, "&fLider: &3Brak");
                tab.set(player, 3, 3, "&fZastepca: &3Brak");
                tab.set(player, 3, 4, "&fCzlonkowie: &3Brak");
                tab.set(player, 3, 5, "&fPoziom: &30");
                tab.set(player, 3, 6, "&fExp: &30&f/&30");
                tab.set(player, 3, 7, "&fKredyty: &30");
                tab.set(player, 3, 8, "                                  ");
                tab.set(player, 3, 9, "&fPunkty: &30");
                tab.set(player, 3, 10, "&fZabojstw razem: &30");
                tab.set(player, 3, 11, "&fSmierci razem: &30");
                tab.set(player, 3, 12, "                                  ");
                tab.set(player, 3, 13, "&f&lBonusy:                       ");
                tab.set(player, 3, 14, "&8- &fSrednie obrazenia: &30%&f/&350%");
                tab.set(player, 3, 15, "&8- &fSrednia defensywa: &30%&f/&350%");
                tab.set(player, 3, 16, "&8- &fSilny na Ludzi: &30%&f/&375%");
                tab.set(player, 3, 17, "&8- &fDef na Ludzi: &30%&f/&375%");
                tab.set(player, 3, 18, "&8- &fDodatkowy EXP: &30%&f/&350%");
            }*/
            tab.set(player,
                    "\n&fWitamy na Serwerze &3TestRPG.PL\n",
                    "\n&fAktualnie graczy: &3" + Bukkit.getOnlinePlayers().size() +
                            "\n&fTwoj Ping: &3" + ((CraftPlayer) player).getHandle().ping + " &fms" +
                            "\n&fDiscord: &3dc.testrpg.pl               &fSklep: &3testrpg.pl               &fFacebook: &3fb.testrpg.pl\n");
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
