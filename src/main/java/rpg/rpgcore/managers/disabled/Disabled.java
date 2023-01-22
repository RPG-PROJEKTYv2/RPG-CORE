package rpg.rpgcore.managers.disabled;

import lombok.Getter;
import lombok.Setter;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Disabled {
    private final List<String> disabledCommands;
    private final List<String> disabledNpc;
    private final List<String> disabledDungeons;

    public Disabled() {
        this.disabledCommands = new ArrayList<>();
        this.disabledNpc = new ArrayList<>();
        this.disabledDungeons = new ArrayList<>();
    }

    public Disabled(final Document document) {
        this.disabledCommands = document.getList("disabledCommands", String.class);
        this.disabledNpc = document.getList("disabledNpc", String.class);
        this.disabledDungeons = document.getList("disabledDungeons", String.class);
    }

    public void addCommand(final String command) {
        this.disabledCommands.add(command);
    }
    public void addCommand(final List<String> aliases) {
        this.disabledCommands.addAll(aliases);
    }
    public void removeCommand(final String command) {
        this.disabledCommands.remove(command);
    }
    public void removeCommand(final List<String> aliases) {
        this.disabledCommands.removeAll(aliases);
    }
    public boolean isDisabledCommand(final String command) {
        return this.disabledCommands.contains(command);
    }
    public void addNpc(final String npc) {
        this.disabledNpc.add(npc);
    }
    public void removeNpc(final String npc) {
        this.disabledNpc.remove(npc);
    }
    public boolean isDisabledNpc(final String npc) {
        return this.disabledNpc.contains(npc);
    }
    public void addDungeon(final String dungeon) {
        this.disabledDungeons.add(dungeon);
    }
    public void removeDungeon(final String dungeon) {
        this.disabledDungeons.remove(dungeon);
    }
    public boolean isDisabledDungeon(final String dungeon) {
        return this.disabledDungeons.contains(dungeon);
    }

    public Document toDocument() {
        return new Document("_id", "disabled")
                .append("disabledCommands", this.disabledCommands)
                .append("disabledNpc", this.disabledNpc)
                .append("disabledDungeons", this.disabledDungeons);
    }
}
