package rpg.rpgcore.chat.objects;

import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import rpg.rpgcore.commands.player.hellcode.panel.objects.HellcodeUser;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ChatUser implements Cloneable {
    private UUID uuid;
    private boolean pingsEnabled, chestDropEnabled, niesDropEnabled, itemDropEnabled, msgEnabled, joinMessageEnabled, quitMessageEnabled, dmgHologramsVisable, databaseMessageEnabled,
    boss1_10, boss10_20, boss20_30, boss30_40, boss40_50;
    private List<UUID> ignoredPlayers;
    private HellcodeUser hellcodeUser;

    public ChatUser(UUID uuid) {
        this.uuid = uuid;
        this.pingsEnabled = true;
        this.chestDropEnabled = true;
        this.niesDropEnabled = true;
        this.itemDropEnabled = true;
        this.msgEnabled = true;
        this.ignoredPlayers = new ArrayList<>();
        this.joinMessageEnabled = true;
        this.quitMessageEnabled = true;
        this.dmgHologramsVisable = true;
        this.databaseMessageEnabled = true;
        this.boss1_10 = true;
        this.boss10_20 = true;
        this.boss20_30 = true;
        this.boss30_40 = true;
        this.boss40_50 = true;
        this.hellcodeUser = new HellcodeUser(uuid);
    }

    public ChatUser(Document document) {
        this.uuid = UUID.fromString(document.getString("_id"));
        this.pingsEnabled = document.getBoolean("pingsEnabled");
        this.chestDropEnabled = document.getBoolean("chestDropEnabled");
        this.niesDropEnabled = document.getBoolean("niesDropEnabled");
        this.itemDropEnabled = document.getBoolean("itemDropEnabled");
        this.msgEnabled = document.getBoolean("msgEnabled");
        this.ignoredPlayers = document.getList("ignoredPlayers", UUID.class);
        this.joinMessageEnabled = document.getBoolean("joinMessageEnabled");
        this.quitMessageEnabled = document.getBoolean("quitMessageEnabled");
        this.dmgHologramsVisable = document.getBoolean("dmgHologramsVisable");
        this.databaseMessageEnabled = (document.containsKey("databaseMessageEnabled") ? document.getBoolean("databaseMessageEnabled") : true);
        this.boss1_10 = (document.containsKey("boss1_10") ? document.getBoolean("boss1_10") : true);
        this.boss10_20 = (document.containsKey("boss10_20") ? document.getBoolean("boss10_20") : true);
        this.boss20_30 = (document.containsKey("boss20_30") ? document.getBoolean("boss20_30") : true);
        this.boss30_40 = (document.containsKey("boss30_40") ? document.getBoolean("boss30_40") : true);
        this.boss40_50 = (document.containsKey("boss40_50") ? document.getBoolean("boss40_50") : true);
        if (document.containsKey("hellcodeUser")) this.hellcodeUser = new HellcodeUser(document.get("hellcodeUser", Document.class));
        else this.hellcodeUser = new HellcodeUser(uuid);
    }

    public Document toDocument() {
        return new Document("_id", uuid.toString())
                .append("pingsEnabled", pingsEnabled)
                .append("chestDropEnabled", chestDropEnabled)
                .append("niesDropEnabled", niesDropEnabled)
                .append("itemDropEnabled", itemDropEnabled)
                .append("msgEnabled", msgEnabled)
                .append("ignoredPlayers", ignoredPlayers)
                .append("joinMessageEnabled", joinMessageEnabled)
                .append("quitMessageEnabled", quitMessageEnabled)
                .append("dmgHologramsVisable", dmgHologramsVisable)
                .append("databaseMessageEnabled", databaseMessageEnabled)
                .append("boss1_10", boss1_10)
                .append("boss10_20", boss10_20)
                .append("boss20_30", boss20_30)
                .append("boss30_40", boss30_40)
                .append("boss40_50", boss40_50)
                .append("hellcodeUser", hellcodeUser.toDocument());
    }

    @Override
    public ChatUser clone() {
        try {
            ChatUser chatUser = (ChatUser) super.clone();
            chatUser.setHellcodeUser(hellcodeUser.clone());
            return chatUser;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
