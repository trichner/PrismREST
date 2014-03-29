package ch.k42.bukkit.prismrest.db;

/**
 * Created by Thomas on 28.03.14.
 *
 * cactus:cactus
 drowning:drowning
 fall:fall
 fire:fire
 lava:lava
 mob:creeper
 mob:enderman
 mob:pigzombie
 mob:skeleton
 mob:slime
 mob:spider
 mob:zombie
 pvp:11Zoroark
 pvp:denimisacat
 pvp:Jet1755
 skeleton:skeleton
 starvation:starvation
 suffocation:suffocation
 thorns:thorns
 tnt:tnt
 */
public enum DeathType {
    CREEPER("creeper:creeper"),
    CACTUS("cactus:cactus"),
    TNT("tnt:tnt"),
    STARVATION("starvation:starvation"),
    SUFFOCATION("suffocation:suffocation"),
    THORNS("thorns:thorns"),
    MOB_ZOMBIE("mob:zombie"),
    MOB_SPIDER("mob:spider"),
    MOB_SKELETON("mob:skeleton"),
    MOB_CREEPER("mob:creeper"),
    MOB_ENDERMAN("mob:enderman"),
    MOB_SLIME("mob:slime"),
    MOB_PIGZOMBIE("mob:pigzombie"),
    SKELETON("skeleton:skeleton"),
    FALL("fall:fall"),
    FIRE("fire:fire"),
    LAVA("lava:lava"),
    PVP_PREFIX("pvp:");
    public final String type;

    DeathType(String type) {
        this.type = type;
    }
}
