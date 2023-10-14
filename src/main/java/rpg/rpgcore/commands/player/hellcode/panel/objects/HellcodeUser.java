package rpg.rpgcore.commands.player.hellcode.panel.objects;

import lombok.Getter;
import org.bson.Document;

import java.util.UUID;

@Getter
public class HellcodeUser implements Cloneable {
    private final UUID uuid;
    private boolean chatEnabled, enderchest, magazyn, inventoryInteract, entityInteract, msgAndReply;

    public HellcodeUser(final UUID uuid) {
        this.uuid = uuid;
        this.chatEnabled = false;
        this.enderchest = false;
        this.magazyn = false;
        this.inventoryInteract = false;
        this.entityInteract = false;
        this.msgAndReply = false;
    }

    public void changeChatEnabled() {
        this.chatEnabled = !this.chatEnabled;
    }

    public void changeEnderchest() {
        this.enderchest = !this.enderchest;
    }

    public void changeMagazyn() {
        this.magazyn = !this.magazyn;
    }

    public void changeInventoryInteract() {
        this.inventoryInteract = !this.inventoryInteract;
    }

    public void changeEntityInteract() {
        this.entityInteract = !this.entityInteract;
    }

    public void changeMsgAndReply() {
        this.msgAndReply = !this.msgAndReply;
    }

    public HellcodeUser(final Document document) {
        this.uuid = UUID.fromString(document.getString("_id"));
        this.chatEnabled = document.getBoolean("chatEnabled");
        this.enderchest = document.getBoolean("enderchest");
        this.magazyn = document.getBoolean("magazyn");
        this.inventoryInteract = document.getBoolean("inventoryInteract");
        this.entityInteract = document.getBoolean("entityInteract");
        this.msgAndReply = document.getBoolean("msgAndReply");
    }

    public Document toDocument() {
        return new Document("_id", this.uuid.toString())
                .append("chatEnabled", this.chatEnabled)
                .append("enderchest", this.enderchest)
                .append("magazyn", this.magazyn)
                .append("inventoryInteract", this.inventoryInteract)
                .append("entityInteract", this.entityInteract)
                .append("msgAndReply", this.msgAndReply);
    }

    @Override
    public HellcodeUser clone() {
        try {
            return (HellcodeUser) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
