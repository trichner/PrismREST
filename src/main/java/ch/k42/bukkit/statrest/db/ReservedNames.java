package ch.k42.bukkit.statrest.db;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Thomas on 28.03.14.
 */
public class ReservedNames {
    static {
        Set<String> set = new HashSet<>();
        set.add("Environment");
        set.add("Fireball");
        set.add("Piston");
        set.add("Water");
        set.add("Zombie");
        set.add("creeper");
        set.add("enderman");
        set.add("drowning");
        set.add("fall");
        set.add("fire");
        set.add("fire-tick");
        set.add("lava");
        set.add("suffocation");
        set.add("snowman");
        set.add("tnt");
        RESERVED_PLAYERNAMES = Collections.unmodifiableSet(set);
    }
    public static Set<String> RESERVED_PLAYERNAMES;

}
