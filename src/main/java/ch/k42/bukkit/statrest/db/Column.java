package ch.k42.bukkit.statrest.db;

/**
 * Created by Thomas on 28.03.14.
 */
public enum Column {
    ACTION_TYPE("action_type"),
    PLAYER("player"),
    WORLD("world"),
    DATA("data");


    public final String name;
    Column(String name) {
        this.name = name;
    }
}
