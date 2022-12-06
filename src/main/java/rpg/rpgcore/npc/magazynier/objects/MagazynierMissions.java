package rpg.rpgcore.npc.magazynier.objects;

import lombok.Getter;
import lombok.Setter;
import org.bson.Document;

@Getter
@Setter
public class MagazynierMissions {
    private int mission1, mission2, mission3, mission4, mission5;
    private int selectedMission;
    private double progress;

    public MagazynierMissions(final int mission1, final int mission2, final int mission3, final int mission4, final int mission5, final int selectedMission, final double progress) {
        this.mission1 = mission1;
        this.mission2 = mission2;
        this.mission3 = mission3;
        this.mission4 = mission4;
        this.mission5 = mission5;
        this.selectedMission = selectedMission;
        this.progress = progress;
    }

    public MagazynierMissions(final Document document) {
        this.mission1 = document.getInteger("mission1");
        this.mission2 = document.getInteger("mission2");
        this.mission3 = document.getInteger("mission3");
        this.mission4 = document.getInteger("mission4");
        this.mission5 = document.getInteger("mission5");
        this.selectedMission = document.getInteger("selectedMission");
        this.progress = document.getDouble("progress");
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
                .append("progress", this.progress);
    }
}
