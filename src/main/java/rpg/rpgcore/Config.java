package rpg.rpgcore;

public class Config {

    private RPGCORE rpgcore;

    public Config(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    public void createConfig(){
        rpgcore.getConfig().addDefault("hostname", "hostname.pl");
        rpgcore.getConfig().addDefault("port", "3306");
        rpgcore.getConfig().addDefault("database", "minecraft");
        rpgcore.getConfig().addDefault("username", "username");
        rpgcore.getConfig().addDefault("password", "password");
        rpgcore.getConfig().options().copyDefaults(true);
        rpgcore.saveConfig();
    }
}
