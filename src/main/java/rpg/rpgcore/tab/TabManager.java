package rpg.rpgcore.tab;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TabManager {

    private static RPGCORE rpgcore;

    public TabManager(RPGCORE rpgcore) {
        TabManager.rpgcore = rpgcore;
    }
    public static ArrayList<String> lista = new ArrayList<>();
    public static Map<UUID, Tab> uuidTabMap = new HashMap<>();
    public static PacketManager packetManager;

    public static void addPlayer(Player player) {
        if (rpgcore.getGuildManager().hasGuild(player.getUniqueId())) {
            lista.add("aaaaaaaaaa" + rpgcore.getGuildManager().getGuildTag(player.getUniqueId()) + "fikusnynawiasekserio" + player.getName());
            lista.sort(String.CASE_INSENSITIVE_ORDER);
            return;
        }
        lista.add("orangekolorekserio" + player.getName());
        lista.sort(String.CASE_INSENSITIVE_ORDER);
    }

    public static void removePlayer(Player player) {
        if (rpgcore.getGuildManager().hasGuild(player.getUniqueId())) {
            lista.remove("aaaaaaaaaa" + rpgcore.getGuildManager().getGuildTag(player.getUniqueId()) + "fikusnynawiasekserio" + player.getName());
            lista.sort(String.CASE_INSENSITIVE_ORDER);
            return;
        }
        lista.remove("orangekolorekserio" + player.getName());
        lista.sort(String.CASE_INSENSITIVE_ORDER);
    }

    public static void add(UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        uuidTabMap.remove(uuid);
        uuidTabMap.put(uuid, new Tab(player, packetManager));
    }

    public static void update(UUID uuid) {
        Tab tab = uuidTabMap.get(uuid);
        Player player = Bukkit.getPlayer(uuid);
        if (tab != null) {
            tab.set(Bukkit.getPlayer(uuid), 0, 0, "&7                                ");
            tab.set(Bukkit.getPlayer(uuid), 1, 0, "&7                                ");
            tab.set(Bukkit.getPlayer(uuid), 2, 0, "&7                                ");
            tab.set(Bukkit.getPlayer(uuid), 3, 0, "&7                                ");
            tab.set(Bukkit.getPlayer(uuid), 0, 19, "&7                                ");
            tab.set(Bukkit.getPlayer(uuid), 1, 19, "&7                                ");
            tab.set(Bukkit.getPlayer(uuid), 2, 19, "&7                                ");
            tab.set(Bukkit.getPlayer(uuid), 3, 19, "&7                                ");
            int number1 = 1;
            int number2 = 2;
            int number3 = 3;
            int number4 = 4;
            for (int i = 1; i < 19; i++) {
                tab.set(Bukkit.getPlayer(uuid), 0, i, "&7                                ");
                tab.set(Bukkit.getPlayer(uuid), 1, i, "&7                                ");
                tab.set(Bukkit.getPlayer(uuid), 2, i, "&7                                ");
                tab.set(Bukkit.getPlayer(uuid), 3, i, "&7                                ");
                if (lista.size() >= number1) {
                    tab.set(Bukkit.getPlayer(uuid), 0, i, Utils.format(lista.get(number1 - 1).replaceAll("aaaaaaaaaa", "&8[&e").replaceAll("fikusnynawiasekserio", "&8]/&6").replaceAll("orangekolorekserio", "&6").replaceAll("/", " ")));
                }
                if (lista.size() >= number2) {
                    tab.set(Bukkit.getPlayer(uuid), 1, i, Utils.format(lista.get(number2 - 1).replaceAll("aaaaaaaaaa", "&8[&e").replaceAll("fikusnynawiasekserio", "&8]/&6").replaceAll("orangekolorekserio", "&6").replaceAll("/", " ")));
                }
                if (lista.size() >= number3) {
                    tab.set(Bukkit.getPlayer(uuid), 2, i, Utils.format(lista.get(number3 - 1).replaceAll("aaaaaaaaaa", "&8[&e").replaceAll("fikusnynawiasekserio", "&8]/&6").replaceAll("orangekolorekserio", "&6").replaceAll("/", " ")));
                }
                if (lista.size() >= number4) {
                    tab.set(Bukkit.getPlayer(uuid), 3, i, Utils.format(lista.get(number4 - 1).replaceAll("aaaaaaaaaa", "&8[&e").replaceAll("fikusnynawiasekserio", "&8]/&6").replaceAll("orangekolorekserio", "&6").replaceAll("/", " ")));
                }
                number1 = number1 + 4;
                number2 = number2 + 4;
                number3 = number3 + 4;
                number4 = number4 + 4;
            }
            tab.set(Bukkit.getPlayer(uuid), "\n&7Witamy na serwerze &fHypeRPG.pl!\n", "\n&7Aktualnie graczy: &f" + Bukkit.getOnlinePlayers().size() + "\n&7Twoj Ping: &f" + ((CraftPlayer) player).getHandle().ping + "ms\n&7Discord: &fdc.hyperpg.pl               &7Sklep: &fhyperpg.pl               &7Facebook: &ffb.hyperpg.pl\n");
        }
    }
}
