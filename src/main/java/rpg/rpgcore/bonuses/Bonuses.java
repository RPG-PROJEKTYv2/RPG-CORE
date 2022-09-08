package rpg.rpgcore.bonuses;

import org.bson.Document;

import java.util.UUID;

public class Bonuses {
    private final UUID uuid;
    private final BonusesUser bonusesUser;

    public Bonuses(final UUID uuid) {
        this.uuid = uuid;
        this.bonusesUser = new BonusesUser(0,0,0,0,
                0.0,0.0,0.0,0.0,0.0,0.0,
                0.0,0.0,0.0,0.0,0.0,0.0,
                0.0,0.0,0.0,0.0,0.0);
    }

    public Bonuses(final Document document) {
        this.uuid = UUID.fromString(document.getString("_id"));
        this.bonusesUser = new BonusesUser(document.getInteger("dodatkowehp"), document.getInteger("dodatkowezlotehp"), document.getInteger("dodatkoweobrazenia"), document.getInteger("truedamage"),
                document.getDouble("srednieobrazenia"), document.getDouble("silnynaludzi"), document.getDouble("silnynapotwory"), document.getDouble("sredniadefensywa"), document.getDouble("defnaludzi"), document.getDouble("defnamoby"),
                document.getDouble("szansanakryta"), document.getDouble("szansanawzmocnieniekryta"), document.getDouble("blokciosu"), document.getDouble("przeszyciebloku"), document.getDouble("szansanakrwawienie"), document.getDouble("minussrednieobrazenia"),
                document.getDouble("minussredniadefensywa"), document.getDouble("minusdefnaludzi"), document.getDouble("minusdefnamoby"), document.getDouble("minusobrazenianaludzi"), document.getDouble("minusobrazenianamoby"));
    }

    public UUID getId() {
        return this.uuid;
    }

    public BonusesUser getBonusesUser() {
        return this.bonusesUser;
    }

    public Document toDocument() {
        return new Document("_id", this.uuid.toString())
                .append("dodatkowehp", this.bonusesUser.getDodatkowehp())
                .append("dodatkowezlotehp", this.bonusesUser.getDodatkowezlotehp())
                .append("dodatkoweobrazenia", this.bonusesUser.getDodatkoweobrazenia())
                .append("truedamage", this.bonusesUser.getTruedamage())
                .append("srednieobrazenia", this.bonusesUser.getSrednieobrazenia())
                .append("silnynaludzi", this.bonusesUser.getSilnynaludzi())
                .append("silnynapotwory", this.bonusesUser.getSilnynapotwory())
                .append("sredniadefensywa", this.bonusesUser.getSredniadefensywa())
                .append("defnaludzi", this.bonusesUser.getDefnaludzi())
                .append("defnamoby", this.bonusesUser.getDefnamoby())
                .append("szansanakryta", this.bonusesUser.getSzansanakryta())
                .append("szansanawzmocnieniekryta", this.bonusesUser.getSzansanawzmocnieniekryta())
                .append("blokciosu", this.bonusesUser.getBlokciosu())
                .append("przeszyciebloku", this.bonusesUser.getPrzeszyciebloku())
                .append("szansanakrwawienie", this.bonusesUser.getSzansanakrwawienie())
                .append("minussrednieobrazenia", this.bonusesUser.getMinussrednieobrazenia())
                .append("minussredniadefensywa", this.bonusesUser.getMinussredniadefensywa())
                .append("minusdefnaludzi", this.bonusesUser.getMinusdefnaludzi())
                .append("minusdefnamoby", this.bonusesUser.getMinusdefnamoby())
                .append("minusobrazenianaludzi", this.bonusesUser.getMinusobrazenianaludzi())
                .append("minusobrazenianamoby", this.bonusesUser.getMinusobrazenianamoby());
    }
}
