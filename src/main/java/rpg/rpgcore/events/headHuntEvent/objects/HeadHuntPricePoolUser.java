package rpg.rpgcore.events.headHuntEvent.objects;

import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class HeadHuntPricePoolUser {
    private final List<ItemStack> glowna;
    private final List<ItemStack> srednia;
    private final List<ItemStack> slaba;


    public HeadHuntPricePoolUser() {
        this.glowna = Arrays.asList(
                // ITEMY OD GłÓWNEJ
        );
        this.srednia = Arrays.asList(
                // ITEMY OD ŚREDNIEJ
        );
        this.slaba = Arrays.asList(
                // ITEMY OD SŁABEJ
        );
    }


    public HeadHuntPricePoolUser(final Document document) {
        final List<String> glownaString = (document.containsKey("glowna") ? document.getList("glowna", String.class) : new ArrayList<>());
        final List<String> sredniaString = (document.containsKey("srednia") ? document.getList("srednia", String.class) : new ArrayList<>());
        final List<String> slabaString = (document.containsKey("slaba") ? document.getList("slaba", String.class) : new ArrayList<>());

        final List<ItemStack> glowna = new ArrayList<>();
        final List<ItemStack> srednia = new ArrayList<>();
        final List<ItemStack> slaba = new ArrayList<>();

        glownaString.forEach(key -> glowna.add(Utils.deserializeItem(key)));
        sredniaString.forEach(key -> srednia.add(Utils.deserializeItem(key)));
        slabaString.forEach(key -> slaba.add(Utils.deserializeItem(key)));

        this.glowna = glowna;
        this.srednia = srednia;
        this.slaba = slaba;
    }

    public Document toDocument() {
        final List<String> glownaString = new ArrayList<>();
        final List<String> sredniaString = new ArrayList<>();
        final List<String> slabaString = new ArrayList<>();

        this.glowna.forEach(itemStack -> glownaString.add(Utils.serializeItem(itemStack)));
        this.srednia.forEach(itemStack -> sredniaString.add(Utils.serializeItem(itemStack)));
        this.slaba.forEach(itemStack -> slabaString.add(Utils.serializeItem(itemStack)));


        return new Document("_id", "HeadHuntPricePool")
                .append("glowna", glownaString)
                .append("srednia", sredniaString)
                .append("slaba", slabaString);
    }
}
