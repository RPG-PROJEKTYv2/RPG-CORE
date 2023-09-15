package rpg.rpgcore.msg;


import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.discord.EmbedUtil;
import rpg.rpgcore.utils.Utils;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Getter
@Setter
public class MSGManager {

    public void sendMessages(final Player sender, final Player target, String message) {

        RPGCORE.getInstance().getUserManager().find(sender.getUniqueId()).setLastMsgUUID(target.getUniqueId());
        RPGCORE.getInstance().getUserManager().find(target.getUniqueId()).setLastMsgUUID(sender.getUniqueId());

        final TextComponent senderPrefixComponent = new TextComponent(Utils.format("&8[&e" + sender.getName() + " &8-> &6" + target.getName() + "&8]:"));
        final TextComponent targetPrefixComponent = new TextComponent(Utils.format("&8[&6" + sender.getName() + " &8-> &e" + target.getName() + "&8]:"));

        senderPrefixComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Utils.format("&7Kliknij, aby odpowiedziec " + target.getName())).create()));
        senderPrefixComponent.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/r ")); // + target.getName() + " "

        targetPrefixComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Utils.format("&7Kliknij, aby odpowiedziec " + sender.getName())).create()));
        targetPrefixComponent.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/r ")); // + sender.getName() + " "

        TextComponent item = new TextComponent("");
        if (message.contains("[eq]") || message.contains("[i]") ||message.contains("[item]")) {
            List<String> msg = new ArrayList<>(Arrays.asList(message.split("\\[eq]")));
            if (message.contains("[i]")) {
                msg = new ArrayList<>(Arrays.asList(message.split("\\[i]")));
            } else if (message.contains("[item]")) {
                msg = new ArrayList<>(Arrays.asList(message.split("\\[item]")));
            }

            if (sender.getItemInHand() != null && sender.getItemInHand().getType() != Material.AIR) {
                item = new TextComponent(Utils.format("&8[&6x" + sender.getItemInHand().getAmount() + " "
                        + (sender.getItemInHand().getItemMeta().hasDisplayName() ? sender.getItemInHand().getItemMeta().getDisplayName() : sender.getItemInHand().getType().toString()) + "&8]"));
                item.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new ComponentBuilder(CraftItemStack.asNMSCopy(sender.getItemInHand()).save(new NBTTagCompound()).toString()).create()));
            }
            if (msg.isEmpty()) {
                senderPrefixComponent.addExtra(item);
                targetPrefixComponent.addExtra(item);
            } else {
                senderPrefixComponent.addExtra(Utils.format("&e" + msg.get(0)));
                senderPrefixComponent.addExtra(item);
                targetPrefixComponent.addExtra(Utils.format("&e" + msg.get(0)));
                targetPrefixComponent.addExtra(item);
                senderPrefixComponent.addExtra(Utils.format("&e" + msg.stream().skip(1).map(arg -> arg + " &e").collect((Collector) Collectors.joining()).toString()));
                targetPrefixComponent.addExtra(Utils.format("&e" + msg.stream().skip(1).map(arg -> arg + " &e").collect((Collector) Collectors.joining()).toString()));
            }
        } else {
            final TextComponent senderMessage = new TextComponent(Utils.format("&e" + message));
            senderPrefixComponent.addExtra(senderMessage);

            final TextComponent targetMessage = new TextComponent(Utils.format("&e" + message));
            targetPrefixComponent.addExtra(targetMessage);
        }

        sender.spigot().sendMessage(senderPrefixComponent);
        target.spigot().sendMessage(targetPrefixComponent);

        RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> {
            RPGCORE.getDiscordBot().sendChannelMessage("msg-logs", EmbedUtil.create("**Prywatna Wiadomość**",
                    "**Od:** " + sender.getName() + "\n" +
                    "**Do:** " + target.getName() + "\n" +
                            "**Wiadomość:** " + Utils.removeColor(message), Color.decode("#fab216")));
        });
    }
}
