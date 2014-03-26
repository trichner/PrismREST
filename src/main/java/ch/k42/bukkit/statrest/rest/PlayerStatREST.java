package ch.k42.bukkit.statrest.rest;

import ch.k42.bukkit.statrest.model.PlayerStatVO;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Created by Thomas on 26.03.14.
 */

@Path("/player")
public class PlayerStatREST {
    @GET
    @Produces("application/json")
    public PlayerStatVO helloWorld(){
        return new PlayerStatVO("Tom");
    }
}
