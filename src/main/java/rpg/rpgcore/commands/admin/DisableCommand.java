package rpg.rpgcore.commands.admin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.Utils;

import java.util.Arrays;

public class DisableCommand extends CommandAPI {

    private final RPGCORE rpgcore;

    public DisableCommand(final RPGCORE rpgcore) {
        super("disable");
        this.setRankLevel(RankType.ADMIN);
        this.setAliases(Arrays.asList("wylacz"));
        this.rpgcore = rpgcore;
    }

    public void executeCommand(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(Utils.poprawneUzycie("disable <npc/dungeon/command> <add/remove/list> <commandName/npcName/dungeonNameWith_>"));
            return;
        }

        if (args.length == 2) {
            if (args[1].equals("list")) {
                if (args[0].equalsIgnoreCase("npc")) {
                    sender.sendMessage(Utils.format(Utils.SERVERNAME + "&cLista wylaczonych npc:"));
                    for (String npc : rpgcore.getDisabledManager().getDisabled().getDisabledNpc()) {
                        sender.sendMessage(Utils.format("&c- " + npc));
                    }
                    return;
                }
                if (args[0].equalsIgnoreCase("dungeon")) {
                    sender.sendMessage(Utils.format(Utils.SERVERNAME + "&cLista wylaczonych dungeonow:"));
                    for (String dungeon : rpgcore.getDisabledManager().getDisabled().getDisabledDungeons()) {
                        sender.sendMessage(Utils.format("&c- " + dungeon));
                    }
                    return;
                }
                if (args[0].equalsIgnoreCase("command")) {
                    sender.sendMessage(Utils.format(Utils.SERVERNAME + "&cLista wylaczonych komend:"));
                    for (String command : rpgcore.getDisabledManager().getDisabled().getDisabledCommands()) {
                        sender.sendMessage(Utils.format("&c- " + command));
                    }
                    return;
                }
            }
            sender.sendMessage(Utils.poprawneUzycie("disable <npc/dungeon/command> <add/remove/list> <commandName/npcName/dungeonNameWith_>"));
            return;
        }


        if (args[0].equalsIgnoreCase("command")) {
            final String command = args[2];

            if (command.equalsIgnoreCase("disable") || command.equalsIgnoreCase("wylacz")) {
                sender.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie mozesz wylaczyc tej komendy!"));
                return;
            }

            if (CommandAPI.getCommand().getCommand(command) == null) {
                sender.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie znaleziono takiej komendy!"));
                return;
            }

            Command cmd = CommandAPI.getCommand().getCommand(command);

            if (args[1].equalsIgnoreCase("add")) {

                if (rpgcore.getDisabledManager().getDisabled().getDisabledCommands().contains(cmd.getName())) {
                    sender.sendMessage(Utils.format(Utils.SERVERNAME + "&cTa komenda jest juz wylaczona!"));
                    return;
                }

                rpgcore.getDisabledManager().getDisabled().addCommand(cmd.getName());
                rpgcore.getDisabledManager().getDisabled().addCommand(cmd.getAliases());
                rpgcore.getDisabledManager().save();
                sender.sendMessage(Utils.format(Utils.SERVERNAME + "&cPomyslnie wylaczono komende &6" + cmd.getName() + " &clacznie z jej aliasami: &6" + cmd.getAliases().toString().replace("[", "").replace("]", "") + "&c!"));
                return;
            }
            if (args[1].equalsIgnoreCase("remove")) {

                if (!rpgcore.getDisabledManager().getDisabled().getDisabledCommands().contains(cmd.getName())) {
                    sender.sendMessage(Utils.format(Utils.SERVERNAME + "&cTa komenda nie jest wylaczona!"));
                    return;
                }

                rpgcore.getDisabledManager().getDisabled().removeCommand(cmd.getName());
                rpgcore.getDisabledManager().save();
                rpgcore.getDisabledManager().getDisabled().removeCommand(cmd.getAliases());
                sender.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie wlaczono komende &6" + cmd.getName() + " &alacznie z jej aliasami: &6" + cmd.getAliases().toString().replace("[", "").replace("]", "") + "&a!"));
                return;
            }
        }
        if (args[0].equalsIgnoreCase("npc")) {
            final StringBuilder sb = new StringBuilder();
            for (int i = 2; i < args.length; i++) {
                sb.append(args[i]).append(" ");
            }
            final String npc = sb.toString().trim();

            if (args[1].equalsIgnoreCase("add")) {

                if (rpgcore.getDisabledManager().getDisabled().getDisabledNpc().contains(npc)) {
                    sender.sendMessage(Utils.format(Utils.SERVERNAME + "&cTen npc jest juz wylaczony!"));
                    return;
                }

                rpgcore.getDisabledManager().getDisabled().addNpc(npc);
                rpgcore.getDisabledManager().save();
                sender.sendMessage(Utils.format(Utils.SERVERNAME + "&cPomyslnie wylaczono npc &6" + npc + "&c!"));
                return;
            }
            if (args[1].equalsIgnoreCase("remove")) {

                if (!rpgcore.getDisabledManager().getDisabled().getDisabledNpc().contains(npc)) {
                    sender.sendMessage(Utils.format(Utils.SERVERNAME + "&cTen npc nie jest wylaczony!"));
                    return;
                }

                rpgcore.getDisabledManager().getDisabled().removeNpc(npc);
                rpgcore.getDisabledManager().save();
                sender.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie wlaczono npc &6" + npc + "&a!"));
                return;
            }
        }
        if (args[0].equalsIgnoreCase("dungeon")) {
            final StringBuilder sb = new StringBuilder();
            for (int i = 2; i < args.length; i++) {
                sb.append(args[i]).append(" ");
            }
            final String dungeon = sb.toString().trim();

            if (args[1].equalsIgnoreCase("add")) {

                if (rpgcore.getDisabledManager().getDisabled().getDisabledDungeons().contains(dungeon)) {
                    sender.sendMessage(Utils.format(Utils.SERVERNAME + "&cTen dungeon jest juz wylaczony!"));
                    return;
                }

                rpgcore.getDisabledManager().getDisabled().addDungeon(dungeon);
                rpgcore.getDisabledManager().save();
                sender.sendMessage(Utils.format(Utils.SERVERNAME + "&cPomyslnie wylaczono dungeon &6" + dungeon + "&c!"));
                return;
            }
            if (args[1].equalsIgnoreCase("remove")) {

                if (!rpgcore.getDisabledManager().getDisabled().getDisabledDungeons().contains(dungeon)) {
                    sender.sendMessage(Utils.format(Utils.SERVERNAME + "&cTen dungeon nie jest wylaczony!"));
                    return;
                }

                rpgcore.getDisabledManager().getDisabled().removeDungeon(dungeon);
                rpgcore.getDisabledManager().save();
                sender.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie wlaczono dungeon &6" + dungeon + "&a!"));
            }
        }
    }
}
