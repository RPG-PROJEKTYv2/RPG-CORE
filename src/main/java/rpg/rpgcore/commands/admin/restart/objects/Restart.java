package rpg.rpgcore.commands.admin.restart.objects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Restart {
    private int seconds = 180;
    private boolean restarting = false;
    private int taskId = -1;
}
