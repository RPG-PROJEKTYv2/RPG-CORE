package rpg.rpgcore.tab;

import org.bukkit.entity.Player;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

public class Tab {
    TabProfile[][] tabProfiles = new TabProfile[20][4];
    String[][] slots = new String[20][4];
    PacketManager packetManager;

    public Tab(Player player, PacketManager packetManager) {
        int base = 97;
        this.packetManager = packetManager;
        for (int row = 0; row < 20; row++) {
            for (int col = 0; col < 4; col++) {
                char first = (char) (base + col);
                char second = (char) (base + row);
                String name = "TegoTypu_" + first + "" + second;
                this.tabProfiles[row][col] = new TabProfile(UUID.randomUUID(), name);
            }
        }
        for (int col = 0; col < 4; col++) {
            for (int row = 0; row < 20; row++) {
                slots[row][col] = "";
                TabProfile tabProfile = tabProfiles[row][col];
                String slot = slots[row][col];
                PacketManager.send(player, tabProfile, slot, PlayerInfoAction.ADD_PLAYER);
            }
        }
    }

    public void set(final Player player, final int column, final int row, final String text) {
        slots[row][column] = Utils.format(text);
        packetManager.send(player, tabProfiles[row][column], slots[row][column], PlayerInfoAction.UPDATE_DISPLAY_NAME);
    }

    public void set(final Player player, final String header, final String footer) {
        packetManager.send(player, header, footer);
    }
}
