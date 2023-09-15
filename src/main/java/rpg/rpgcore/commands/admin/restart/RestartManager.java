package rpg.rpgcore.commands.admin.restart;

import lombok.Getter;
import rpg.rpgcore.commands.admin.restart.objects.Restart;
@Getter
public class RestartManager {
    public static final Restart restart = new Restart();
}
