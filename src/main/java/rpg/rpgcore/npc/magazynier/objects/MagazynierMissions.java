package rpg.rpgcore.npc.magazynier.objects;

import lombok.Getter;
import lombok.Setter;
import org.bson.Document;

@Getter
@Setter
public class MagazynierMissions {
    private int mission1, mission2, mission3, mission4, mission5;
    private boolean mission1done, mission2done, mission3done, mission4done, mission5done;
    private int selectedMission;
    private double progress;

    public MagazynierMissions(final int mission1, final int mission2, final int mission3, final int mission4, final int mission5, final int selectedMission, final double progress,
                              final boolean mission1done, final boolean mission2done, final boolean mission3done, final boolean mission4done, final boolean mission5done) {
        this.mission1 = mission1;
        this.mission2 = mission2;
        this.mission3 = mission3;
        this.mission4 = mission4;
        this.mission5 = mission5;
        this.selectedMission = selectedMission;
        this.progress = progress;
        this.mission1done = mission1done;
        this.mission2done = mission2done;
        this.mission3done = mission3done;
        this.mission4done = mission4done;
        this.mission5done = mission5done;
    }

    public MagazynierMissions(final Document document) {
        this.mission1 = document.getInteger("mission1");
        this.mission2 = document.getInteger("mission2");
        this.mission3 = document.getInteger("mission3");
        this.mission4 = document.getInteger("mission4");
        this.mission5 = document.getInteger("mission5");
        this.selectedMission = document.getInteger("selectedMission");
        this.progress = document.getDouble("progress");
        this.mission1done = document.getBoolean("mission1done");
        this.mission2done = document.getBoolean("mission2done");
        this.mission3done = document.getBoolean("mission3done");
        this.mission4done = document.getBoolean("mission4done");
        this.mission5done = document.getBoolean("mission5done");
    }

    public boolean isGenerated() {
        return this.mission1 != 0 && this.mission2 != 0 && this.mission3 != 0 && this.mission4 != 0 && this.mission5 != 0;
    }

    public Document toDocument() {
        return new Document("_id", "missions")
                .append("mission1", this.mission1)
                .append("mission2", this.mission2)
                .append("mission3", this.mission3)
                .append("mission4", this.mission4)
                .append("mission5", this.mission5)
                .append("selectedMission", this.selectedMission)
                .append("progress", this.progress)
                .append("mission1done", this.mission1done)
                .append("mission2done", this.mission2done)
                .append("mission3done", this.mission3done)
                .append("mission4done", this.mission4done)
                .append("mission5done", this.mission5done);
    }
}
