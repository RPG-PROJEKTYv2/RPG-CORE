package rpg.rpgcore.commands.player.showcase;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ShowcaseItemManager {
    public Cache<String, Map<String, ItemStack>> showcaseItemCache = CacheBuilder.newBuilder().expireAfterWrite(30, TimeUnit.SECONDS).build();

    public void addShowcaseItem(final String playerName, final ItemStack item) {
        if (this.showcaseItemCache.asMap().containsKey(playerName)) {
            if (item.getItemMeta().hasDisplayName()) {
                this.showcaseItemCache.asMap().get(playerName).put(Utils.removeColor(item.getItemMeta().getDisplayName().replace(" ", "_")), item);
            } else {
                this.showcaseItemCache.asMap().get(playerName).put(item.getType().toString().replace(" ", "_"), item);
            }
        } else {
            final Map<String, ItemStack> itemMap = new HashMap<>();
            if (item.getItemMeta().hasDisplayName()) {
                itemMap.put(Utils.removeColor(item.getItemMeta().getDisplayName().replace(" ", "_")), item);
            } else {
                itemMap.put(item.getType().toString().replace(" ", "_"), item);
            }
            this.showcaseItemCache.put(playerName, itemMap);
        }
    }
}
