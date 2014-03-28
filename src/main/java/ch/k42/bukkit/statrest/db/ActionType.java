package ch.k42.bukkit.statrest.db;

/**
 * Created by Thomas on 27.03.14.
 *
 * block-break
         block-burn
         block-fall
         block-place
         block-shift
         block-use
         container-access
         creeper-explode
         enderman-pickup
         enderman-place
         entity-break
         entity-explode
         entity-form
         entity-kill
         hangingitem-break
         hangingitem-place
         item-insert
         item-pickup
         item-remove
         lava-break
         lava-bucket
         lava-ignite
         lighter
         lightning
         player-chat
         player-command
         player-death
         player-join
         player-kill
         player-quit
         sign-change
         spawnegg-use
         tnt-explode
         water-break
         water-bucket
 */
public enum ActionType {
    PLAYER_DEATH("player-death"),
    ENTITY_KILL("entity-kill"),
    ENTITY_SPAWN("entity-spawn"),
    PLAYER_KILL("player-kill"),
    PLAYER_JOIN("player-join"),
    PLAYER_QUIT("player-quit"),
    BLOCK_BREAK("block-break"),
    BLOCK_BURN("block-burn"),
    BLOCK_FALL("block-fall"),
    BLOCK_PLACE("block-place"),
    BLOCK_SHIFT("block-shift"),
    BLUCK_USE("block-use"),
    TNT_EXPLODE("tnt-explode"),
    CREEPER_EXPLODE("creeper-explode");


    public final String name;
    ActionType(String name) {
        this.name = name;
    }
}
