package rpg.rpgcore.server;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Server {

    private int dodatkowyExp;
    private long czas;
    private boolean aktywny;

    public Server(int dodatkowyExp, long czas, boolean aktywny) {
        this.dodatkowyExp = dodatkowyExp;
        this.czas = czas;
        this.aktywny = aktywny;
    }
}
