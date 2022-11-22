package rpg.rpgcore.party;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.*;

public class PartyManager {
    private final Map<UUID, Party> partyList = new HashMap<>();
    private final List<PartyInvite> inviteList = new ArrayList<>();
    public void sendAllCommands(final Player player) {
        player.sendMessage(Utils.format("&8&m-------------------&r &3&lParty &8&m-------------------"));
        player.sendMessage(Utils.format("&6/party zaloz &7- Tworzy nowa grupe"));
        player.sendMessage(Utils.format("&6/party usun &7- Usuwa grupe"));
        player.sendMessage(Utils.format("&6/party list/info &7- Wyswietla informacje o grupie"));
        player.sendMessage(Utils.format("&6/party opusc &7- Opuszcza aktualna grupe"));
        player.sendMessage(Utils.format("&6/party zapros <nick> &7- Zaprasza gracza do twojej grupy"));
        player.sendMessage(Utils.format("&6/party wyrzuc <nick> &7- Usuwa gracza z twojej grupy"));
        player.sendMessage(Utils.format("&6/party dolacz <nick> &7- Dolacza do grupy gracza, jesli masz do niej zaproszeneie"));
        player.sendMessage(Utils.format("&8&m-------------------&r &3&lParty &8&m-------------------"));
    }

    public Party find(final UUID uuid) {
        return partyList.get(uuid);
    }

    public void add(final Player player) {
        partyList.put(player.getUniqueId(), new Party(player.getName(), player.getUniqueId(),  new ArrayList<>(Arrays.asList(player.getUniqueId()))));
    }

    public boolean hasParty(final UUID uuid) {
        return partyList.containsKey(uuid);
    }

    public boolean isInParty(final UUID uuid) {
        for (Party party : partyList.values()) {
            if (party.getMembers().contains(uuid)) {
                return true;
            }
        }
        return false;
    }

    public Party findPartyByMember(final UUID uuid) {
        if (!isInParty(uuid)) {
            return null;
        }
        for (Party party : partyList.values()) {
            if (party.getMembers().contains(uuid)) {
                return party;
            }
        }
        return null;
    }

    public void createParty(final Player player) {
        if (this.hasParty(player.getUniqueId())) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Posiadasz juz swoja grupe. Aby ja usunac uzyj: &c/party usun"));
            return;
        }
        if (this.isInParty(player.getUniqueId())) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Jestes juz czlonkiem innej grupy. Aby ja opuscic uzyj: &c/party opusc"));
            return;
        }
        this.add(player);
        player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie stworzono grupe!"));
    }

    public void listParty(final Player player) {
        if (!isInParty(player.getUniqueId())) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Nie jestes czlonkiem zadnej grupy. Aby ja stworzyc uzyj: &c/party zaloz"));
            return;
        }
        for (Party party : partyList.values()) {
            if (party.getMembers().contains(player.getUniqueId())) {
                player.sendMessage(Utils.format("&8&m-------------------&r &3&lParty &8&m-------------------"));
                player.sendMessage(Utils.format("&6Lider: &7" + party.getLeaderName()));
                player.sendMessage(Utils.format("&6Czlonkowie: &8(&a" + party.getMembers().size() + "&8/&a4&8)"));
                for (UUID uuid : party.getMembers()) {
                    player.sendMessage(Utils.format("&7- " + RPGCORE.getInstance().getUserManager().find(uuid).getName()));
                }
                player.sendMessage(Utils.format("&8&m-------------------&r &3&lParty &8&m-------------------"));
            }
        }
    }

    public void removeParty(final Player player) {
        if (!this.hasParty(player.getUniqueId())) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Nie posiadasz grupy. Aby ja stworzyc uzyj: &c/party zaloz"));
            return;
        }
        if (!this.isInParty(player.getUniqueId())) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Nie jestes czlonkiem grupy. Aby ja stworzyc uzyj: &c/party zaloz"));
            return;
        }
        if (this.find(player.getUniqueId()) != null && !this.find(player.getUniqueId()).getLeaderUUID().equals(player.getUniqueId())) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Nie jestes liderem tej grupy!"));
            return;
        }
        partyList.remove(player.getUniqueId());
        player.sendMessage(Utils.format(Utils.SERVERNAME + "&cPomyslnie usunieto grupe!"));
    }

    public void invitePlayer(final Player leader, final Player target) {
        if (!this.isInParty(leader.getUniqueId())) {
            leader.sendMessage(Utils.format(Utils.SERVERNAME + "&7Nie posiadasz grupy. Aby ja stworzyc uzyj: &c/party zaloz"));
            return;
        }
        if (this.find(leader.getUniqueId()) != null && !this.find(leader.getUniqueId()).getLeaderUUID().equals(leader.getUniqueId())) {
            leader.sendMessage(Utils.format(Utils.SERVERNAME + "&7Nie jestes liderem tej grupy!"));
        }
        if (leader == target) {
            leader.sendMessage(Utils.format(Utils.SERVERNAME + "&7Nie mozesz zaprosic samego siebie!"));
            return;
        }
        if (RPGCORE.getInstance().getCooldownManager().hasPartyInviteCooldown(target.getUniqueId())) {
            leader.sendMessage(Utils.format(Utils.SERVERNAME + "&7Odczekaj jeszcze &c" + Utils.durationToString(RPGCORE.getInstance().getCooldownManager().getPlayerPartyInviteCooldown(target.getUniqueId()), false) + " &7zanim ponownie zaprosisz gracza do grupy!"));
            return;
        }
        if (isInvited(leader.getUniqueId(), target.getUniqueId())) {
            leader.sendMessage(Utils.format(Utils.SERVERNAME + "&7Ten gracz otrzymal juz zaproszenie do twojej grupy"));
            return;
        }
        if (isInParty(target.getUniqueId())) {
            leader.sendMessage(Utils.format(Utils.SERVERNAME + "&7Ten gracz jest juz czlonkiem innej grupy"));
            return;
        }
        final PartyInvite partyInvite = new PartyInvite(this.find(leader.getUniqueId()), target.getUniqueId());
        inviteList.add(partyInvite);
        leader.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie wyslano zaproszenie do gracza &6" + target.getName()));
        final TextComponent message = new TextComponent(Utils.format(Utils.SERVERNAME + "&aOtrzymano zaproszenie do party gracza &6" + leader.getName() + "&a. Aby dolaczyc do niej uzyj: &c/party dolacz " + leader.getName() + " &alub &akliknij w te wiadomosc"));
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Utils.format("&aKliknij aby dolaczyc do party gracza &6" + leader.getName())).create()));
        message.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/party dolacz " + leader.getName()));
        target.spigot().sendMessage(message);
        RPGCORE.getInstance().getCooldownManager().givePlayerPartyInviteCooldown(target.getUniqueId());
        RPGCORE.getInstance().getServer().getScheduler().runTaskLater(RPGCORE.getInstance(), () -> {
            if (inviteList.contains(partyInvite)) {
                inviteList.remove(partyInvite);
                leader.sendMessage(Utils.format(Utils.SERVERNAME + "&cZaproszenie do gracza &6" + target.getName() + "&c wygaslo"));
                target.sendMessage(Utils.format(Utils.SERVERNAME + "&cZaproszenie do party gracza &6" + leader.getName() + "&c wygaslo"));
            }
        }, 300L);
    }

    public boolean isInvited(final UUID leader, final UUID target) {
        for (PartyInvite invite : inviteList) {
            if (invite.getInvitedUuid().equals(target) && invite.getParty().getLeaderUUID().equals(leader)) {
                return true;
            }
        }
        return false;
    }

    public void removeFromParty(final Player leader, final Player target) {
        if (!this.isInParty(leader.getUniqueId())) {
            leader.sendMessage(Utils.format(Utils.SERVERNAME + "&7Nie posiadasz grupy. Aby ja stworzyc uzyj: &c/party zaloz"));
            return;
        }
        if (this.find(leader.getUniqueId()) != null && !this.find(leader.getUniqueId()).getLeaderUUID().equals(leader.getUniqueId())) {
            leader.sendMessage(Utils.format(Utils.SERVERNAME + "&7Nie jestes liderem tej grupy!"));
            return;
        }
        if (leader == target) {
            leader.sendMessage(Utils.format(Utils.SERVERNAME + "&7Nie mozesz wyrzucic samego siebie!"));
            return;
        }
        if (this.find(leader.getUniqueId()) != null && !this.find(leader.getUniqueId()).getMembers().contains(target.getUniqueId())) {
            leader.sendMessage(Utils.format(Utils.SERVERNAME + "&7Ten gracz nie jest czlonkiem twojej grupy!"));
            return;
        }
        final List<UUID> members = this.find(leader.getUniqueId()).getMembers();
        members.remove(target.getUniqueId());
        this.find(leader.getUniqueId()).setMembers(members);
        leader.sendMessage(Utils.format(Utils.SERVERNAME + "&cPomyslnie wyrzucono gracza &6" + target.getName() + " &cz grupy!"));
        target.sendMessage(Utils.format(Utils.SERVERNAME + "&cZostales wyrzucony z grupy przez gracza &6" + leader.getName()));
        if (!RPGCORE.getInstance().getCooldownManager().hasPartyInviteCooldown(target.getUniqueId())) {
            RPGCORE.getInstance().getCooldownManager().givePlayerPartyInviteCooldown(target.getUniqueId());
        }
    }

    public void joinParty(final Player joining, final Player target) {
        if (isInParty(joining.getUniqueId())) {
            joining.sendMessage(Utils.format(Utils.SERVERNAME + "&7Jestes juz czlonkiem grupy! Aby ja opuscic uzyj: &c/party opusc"));
            return;
        }
        if (!isInvited(target.getUniqueId(), joining.getUniqueId())) {
            joining.sendMessage(Utils.format(Utils.SERVERNAME + "&7Nie otrzymales zaproszenia do tej grupy!"));
            return;
        }
        final Party party = this.find(target.getUniqueId());
        final List<UUID> members = party.getMembers();
        if (members.size() == 4) {
            joining.sendMessage(Utils.format(Utils.SERVERNAME + "&7Ta grupa jest juz pelna!"));
            return;
        }
        members.add(joining.getUniqueId());
        party.setMembers(members);
        inviteList.removeIf(invite -> invite.getInvitedUuid().equals(joining.getUniqueId()));
        joining.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie dolaczyles do grupy gracza &6" + target.getName()));
        target.sendMessage(Utils.format(Utils.SERVERNAME + "&aGracz &6" + joining.getName() + " &adolaczyl do twojej grupy!"));
        if (!RPGCORE.getInstance().getCooldownManager().hasPartyInviteCooldown(target.getUniqueId())) {
            RPGCORE.getInstance().getCooldownManager().givePlayerPartyInviteCooldown(target.getUniqueId());
        }
    }

    public void leaveParty(final Player player) {
        if (!isInParty(player.getUniqueId())) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Nie jestes czlonkiem zadnej grupy!"));
            return;
        }
        for (Party party : partyList.values()) {
            if (party.getLeaderUUID().equals(player.getUniqueId())) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Jestes liderem grupy! Aby ja usunac uzyj: &c/party usun"));
                return;
            }
            if (party.getMembers().contains(player.getUniqueId())) {
                final List<UUID> members = party.getMembers();
                members.remove(player.getUniqueId());
                party.setMembers(members);
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&cPomyslnie opusciles grupe!"));
                if (Bukkit.getPlayer(party.getLeaderUUID()) != null) {
                    Bukkit.getPlayer(party.getLeaderUUID()).sendMessage(Utils.format(Utils.SERVERNAME + "&cGracz &6" + player.getName() + " &copuscil twoja grupe!"));
                    break;
                }
            }
        }
    }
}
