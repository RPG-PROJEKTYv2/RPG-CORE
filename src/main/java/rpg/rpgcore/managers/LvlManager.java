package rpg.rpgcore.managers;

import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.HashMap;

public class LvlManager {

    private final RPGCORE rpgcore;

    public LvlManager(RPGCORE rpgcore) {this.rpgcore = rpgcore;}


    private final HashMap<String, Double> wymaganyExpDlaLvli = new HashMap<>();

    public HashMap<String, Double> getWymaganyExpDlaLvli() {return wymaganyExpDlaLvli;}

    public void putAllInHashMap(){
        try {
            int sciezkoLvl = rpgcore.getConfig().getConfigurationSection("wymaganyexp_na_lvl").getKeys(false).size();
            for (int i = 1; i <= sciezkoLvl; i++) {
                this.wymaganyExpDlaLvli.put("exp_na_lvl_" + i, rpgcore.getConfig().getConfigurationSection("wymaganyexp_na_lvl").getDouble("wymaganyexp_lvl_" + i));
            }
            System.out.println(Utils.format("&8[&crpg.core&8] &aPomyslnie wczytano wymagane doswiadczenia dla kazdego poziomu"));
        }catch (final Exception e){
            e.printStackTrace();
            System.out.println(Utils.format("&8[&crpg.core&8] &cCos poszlo nie tak podczas wczytywania wymaganego doswiadczenia dla kazdego poziomu"));
        }
    }

    //TODO dokonczyc robienie lvli, dodac do bazy danych kolumny lvl i exp oraz do player managera dodac odpowiednie hashmapy do tego
}
