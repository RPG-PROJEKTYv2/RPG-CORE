package rpg.rpgcore.commands.player;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.Arrays;

public class AdministracjaCommand extends CommandAPI {
    public AdministracjaCommand() {
        super("administracja");
        this.setAliases(Arrays.asList("admin", "adm"));
        this.setRestrictedForPlayer(true);
    }

    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;

        if (args.length != 0) {
            player.sendMessage("Poprawne uzycie: /administracja");
            return;
        }

        /*player.sendMessage(Utils.format("&8&m--------{--&3&l Lista Administracji &8&m--}--------"));
        player.sendMessage(Utils.format("&4&lDeveloper"));
        player.sendMessage(Utils.format("&8- &cMires_"));
        player.sendMessage(Utils.format(""));
        player.sendMessage(Utils.format("&4&lHeadAdmin"));
        player.sendMessage(Utils.format("&8- &cZwariowanyOrzel"));
        player.sendMessage(Utils.format("&8- &cChytryy"));
        player.sendMessage(Utils.format(""));
        player.sendMessage(Utils.format("&4&lAdmin"));
        player.sendMessage(Utils.format("&8- &cFabiLord"));
        player.sendMessage(Utils.format(""));
        player.sendMessage(Utils.format("&2&lGameMaster"));
        player.sendMessage(Utils.format("&8- &7Brak"));
        player.sendMessage(Utils.format(""));
        player.sendMessage(Utils.format("&2&lModerator"));
        player.sendMessage(Utils.format("&8- &7Brak"));
        player.sendMessage(Utils.format(""));
        player.sendMessage(Utils.format("&2&lKid Moderator"));
        player.sendMessage(Utils.format("&8- &7Brak"));
        player.sendMessage(Utils.format(""));
        player.sendMessage(Utils.format("&3&lHelper"));
        player.sendMessage(Utils.format("&8- &7Brak"));
        player.sendMessage(Utils.format(""));
        player.sendMessage(Utils.format("&3&lJuniorHelper"));
        player.sendMessage(Utils.format("&8- &7Brak"));
        player.sendMessage(Utils.format(""));
        player.sendMessage(Utils.format("&8&m--------{--&3&l Lista Administracji &8&m--}--------"));*/
        this.openAdministracjaGUI(player);
    }

    private void openAdministracjaGUI(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 18, Utils.format("&4&lAdministracja"));
        for (int i = 0; i < 18; i++) {
            gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setName(" ").toItemStack());
        }

        gui.setItem(0, new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setName("&4&lDeveloper").setSkullOwner("Mires_").setLore(Arrays.asList(
                "&7Prefix rangi: &4&lDEV",
                "&7Nick: &cMires_",
                "&7Online: &cKto to wie? :)"
        )).toItemStack());
        gui.setItem(1, new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setName("&4&lHeadAdmin").setSkullOwner("ZwariowanyOrzel").setLore(Arrays.asList(
                "&7Prefix rangi: &4&lHA",
                "&7Nick: &cZwarioywnyOrzel",
                "&7Online: " + (Bukkit.getPlayer("ZwariowanyOrzel") != null ? "&aOnline" : "&cOffline")
        )).toItemStack());
        gui.setItem(2, new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setName("&4&lHeadAdmin").setSkullOwner("Chytryy").setLore(Arrays.asList(
                "&7Prefix rangi: &4&lHA",
                "&7Nick: &cChytryy",
                "&7Online: " + (Bukkit.getPlayer("Chytryy") != null ? "&aOnline" : "&cOffline")
        )).toItemStack());
        gui.setItem(3, new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setName("&4&lAdmin").setSkullOwner("FabiLord").setLore(Arrays.asList(
                "&7Prefix rangi: &4&lAdmin",
                "&7Nick: &cFabiLord",
                "&7Online: " + (Bukkit.getPlayer("FabiLord") != null ? "&aOnline" : "&cOffline")
        )).toItemStack());
        gui.setItem(4, new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setName("&2&lGameMaster").setSkullOwner("KoMiK120").setLore(Arrays.asList(
                "&7Prefix rangi: &2&lGM",
                "&7Nick: &aKoMiK120",
                "&7Online: " + (Bukkit.getPlayer("KoMiK120") != null ? "&aOnline" : "&cOffline")
        )).toItemStack());

        player.openInventory(gui);
    }
}
