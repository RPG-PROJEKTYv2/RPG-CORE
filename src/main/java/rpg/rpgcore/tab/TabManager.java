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
        switch (playerGroup) {
            case "H@":
            case "Admin":
                lista.add(prefix + "&4&l" + playerGroup + " &c" + player.getName());
                break;
            case "GM":
            case "Mod":
            case "KidMod":
                lista.add(prefix + "&2&l" + playerGroup + " &a" + player.getName());
                break;
            case "Helper":
                lista.add(prefix + "&3&lHelper &b" + player.getName());
                break;
            case "JuniorHelper":
                lista.add(prefix + "&3&lJrHelper &b" + player.getName());
                break;
            case "ELITA":
                lista.add(prefix + "&5&lELITA &7" + player.getName());
                break;
            case "Svip":
                lista.add(prefix + "&6&lS&e&lvip &7" + player.getName());
                break;
            case "Vip":
                lista.add(prefix + "&e&lVip &7" + player.getName());
                break;
            case "Budowniczy":
                lista.add(prefix + "&d&lBud &7" + player.getName());
                break;
            default:
                lista.add(prefix + "&7 " + player.getName());
                break;
        }
        lista.sort(String.CASE_INSENSITIVE_ORDER);
    }

    public static void removePlayer(Player player) {
        final String playerGroup = rpgcore.getPlayerManager().getPlayerGroup(player);
        String prefix = "";
        /*if (rpgcore.getGuildManager().hasGuild(player.getUniqueId())) {
            lista.remove("aaaaaaaaaa" + rpgcore.getGuildManager().getGuildTag(player.getUniqueId()) + "fikusnynawiasekserio" + player.getName());
            lista.sort(String.CASE_INSENSITIVE_ORDER);
            return;
            prefix = "&8[&3" + rpgcore.getGuildManager().getGuildTag(player.getUniqueId()) + "&8] ";
        }
        if (playerGroup.equals("H@") || playerGroup.equals("Admin")) {
            lista.remove(prefix + "&4&l" + playerGroup + " &c" + player.getName());
        } else if (playerGroup.equals("GM") || playerGroup.equals("Mod") || playerGroup.equals("KidMod")) {
            lista.remove(prefix + "&2&l" + playerGroup + " &a" + player.getName());
        } else if (playerGroup.equals("Helper")) {
            lista.remove(prefix + "&3&lHelper &b" + player.getName());
        } else if (playerGroup.equals("JuniorHelper")) {
            lista.remove(prefix + "&3&lJrHelper &b" + player.getName());
        }else if (playerGroup.equals("ELITA")) {
            lista.remove(prefix + "&5&lELITA &7" + player.getName());
        } else if (playerGroup.equals("Svip")) {
            lista.remove(prefix + "&6&lS&e&lvip &7" + player.getName());
        } else if (playerGroup.equals("Vip")) {
            lista.remove(prefix + "&e&lVip &7" + player.getName());
        } else if (playerGroup.equals("Budowniczy")) {
            lista.remove(prefix + "&d&lBud &7" + player.getName());
        } else {
            lista.remove(prefix + "&7 " + player.getName());
        }*/
        //TODO Dokończyć usuwanie graczy z listy przed dolaczeniem do klanu (/klan dolacz nie dziala na taba)
        List<Integer> occruence = new ArrayList<>();
        for (String s : lista) {
            String test = s.substring(s.lastIndexOf(" ") + 1).trim();
            player.sendMessage(test);
            if (Utils.removeColor(test).equals(player.getName())) {
                occruence.add(lista.indexOf(test));
            }
        }

        for (Integer integer : occruence) {
            player.sendMessage(lista.get(integer));
            lista.remove(lista.get(integer));
        }
        lista.sort(String.CASE_INSENSITIVE_ORDER);
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
        if (tab != null) {
            tab.set(player, 0, 0, "&FLista Graczy                    ");
            tab.set(player, 1, 0, "&7                                ");
            tab.set(player, 2, 0, "&7                                ");
            tab.set(player, 3, 0, "&fInformacje o klanie:            ");
            tab.set(player, 0, 19, "&7                                ");
            tab.set(player, 1, 19, "&7                                ");
            tab.set(player, 2, 19, "&7                                ");
            tab.set(player, 3, 19, "&7                                ");
            int number1 = 1;
            int number2 = 2;
            int number3 = 3;
            for (int i = 1; i < 19; i++) {
                tab.set(player, 0, i, "&7                                ");
                tab.set(player, 1, i, "&7                                ");
                tab.set(player, 2, i, "&7                                ");
                tab.set(player, 3, i, "&7                                ");
                if (lista.size() >= number1) {
                    tab.set(player, 0, i, Utils.format(lista.get(number1 - 1).replaceAll("/", " ")));
                }
                if (lista.size() >= number2) {
                    tab.set(player, 1, i, Utils.format(lista.get(number2 - 1).replaceAll("/", " ")));
                }
                if (lista.size() >= number3) {
                    tab.set(player, 2, i, Utils.format(lista.get(number3 - 1).replaceAll("/", " ")));
                }

                number1 =+ 4;
                number2 =+ 4;
                number3 =+ 4;
            }
            if (!tag.equals("Brak Klanu")) {
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
            }
            tab.set(player,
                    "\n&fWitamy na Serwerze &3TestRPG.PL\n",
                    "\n&fAktualnie graczy: &3" + Bukkit.getOnlinePlayers().size() +
                            "\n&fTwoj Ping: &3" + ((CraftPlayer) player).getHandle().ping + " &fms" +
                            "\n&fDiscord: &3dc.testrpg.pl               &fSklep: &3testrpg.pl               &fFacebook: &3fb.testrpg.pl\n");
        }
    }

    public static Map<UUID, Tab> getUuidTabMap() {
        return uuidTabMap;
    }
}
