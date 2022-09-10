package rpg.rpgcore.akcesoria;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AkcesoriaUser {
    private String tarcza, medalion, pas, kolczyki, sygnet, energia, diadem;

    public AkcesoriaUser(String tarcza, String medalion, String pas, String kolczyki, String sygnet, String energia, String diadem) {
        this.tarcza = tarcza;
        this.medalion = medalion;
        this.pas = pas;
        this.kolczyki = kolczyki;
        this.sygnet = sygnet;
        this.energia = energia;
        this.diadem = diadem;
    }
}
