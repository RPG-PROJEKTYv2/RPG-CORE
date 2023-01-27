package rpg.rpgcore.trade.objects;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.discord.EmbedUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

@Getter
public class Trade {
    private final String name;

    private final UUID uuid1;
    private final UUID uuid2;

    private final Player player1;
    private final Player player2;

    private Inventory guiPlayer1;
    private Inventory guiPlayer2;

    private final Map<UUID, CopyOnWriteArrayList<ItemStack>> items = new HashMap<>();
    private final Map<UUID, Boolean> acceptMap = new HashMap<>();
    private boolean canceled;
    private boolean itemAdded;

    public Trade(final UUID uuid1, final UUID uuid2, final Player player1, final Player player2) {
        this.name = player1.getName() + " - " + player2.getName();
        this.uuid1 = uuid1;
        this.uuid2 = uuid2;
        this.player1 = player1;
        this.player2 = player2;
        this.items.put(uuid1, new CopyOnWriteArrayList<>());
        this.items.put(uuid2, new CopyOnWriteArrayList<>());
        this.acceptMap.put(uuid1, false);
        this.acceptMap.put(uuid2, false);
        canceled = false;
        itemAdded = false;
    }

    public boolean isAccepted() {
        return acceptMap.get(uuid1) && acceptMap.get(uuid2);
    }
    public boolean isPartAccepted() {
        return acceptMap.get(uuid1) || acceptMap.get(uuid2);
    }

    public CopyOnWriteArrayList<ItemStack> getItems(final UUID uuid) {
        return items.get(uuid);
    }

    public void addItem(final UUID uuid, final ItemStack itemStack) {
        items.get(uuid).add(itemStack);
    }

    public void removeItem(final UUID uuid, final ItemStack itemStack) {
        items.get(uuid).remove(itemStack);
    }

    public void setGuiPlayer1(final Inventory inventory) {
        guiPlayer1 = inventory;
    }

    public void setGuiPlayer2(final Inventory inventory) {
        guiPlayer2 = inventory;
    }

    public void setAccept(final UUID uuid, final boolean accept) {
        acceptMap.replace(uuid, accept);
    }

    public void cancel() {
        canceled = true;
    }

    public void setItemAdded(final boolean itemAdded) {
        this.itemAdded = itemAdded;
    }




    public void log() {
        RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getDiscordBot().sendChannelMessage("trade-logs", EmbedUtil.createTradeLogs(this)));
    }
}
