package ch.k42.bukkit.statrest.rest;

import ch.k42.bukkit.statrest.model.EnumsVO;
import ch.k42.bukkit.statrest.db.PrismDAO;
import org.jboss.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.Set;

/**
 * Created by Thomas on 26.03.14.
 */

@Path("/")
@RequestScoped
public class PlayerStatRESTService {

    private Logger LOG = Logger.getLogger(PlayerStatRESTService.class);

    @Inject
    private PrismDAO dao;


    @Path("/player")
    @GET
    @Produces("application/json")
    public Set<String> getPlayers(){
        LOG.debug("getPlayers");
        return dao.getPlayers();
    }

    @Path("/deaths")
    @GET
    @Produces("application/json")
    public Integer getDeaths(@QueryParam("type") String type){
        LOG.debug("getDeathsForPlayer");
        if(type==null){
            return dao.getDeaths();
        }else{
            return dao.getDeaths(type);
        }
    }

    @Path("/deaths/{username: [a-zA-Z_0-9]+}")
    @GET
    @Produces("application/json")
    public Integer getDeathsForPlayer(@PathParam("username") String username, @QueryParam("type") String type){
        LOG.debug("getDeathsForPlayer: " + username);
        if(type==null){
            return dao.getDeathsForPlayer(username);
        }else{
            return dao.getDeathsForPlayerByType(username,type);
        }
    }

    @Path("/kills/{username: [a-zA-Z_0-9]+}")
    @GET
    @Produces("application/json")
    public Integer getKillsForPlayer(@PathParam("username") String username, @QueryParam("type") String type){
        LOG.debug("getKillsForPlayer: " + username);
        if(type==null){
            return dao.getKillsForPlayer(username);
        }else if(type.equals("pvp")){
            return dao.getPvpKillsForPlayer(username);
        }else if (type.equals("pve")){
            return dao.getPveKillsForPlayer(username);
        }
        throw new WebApplicationException(new IllegalArgumentException("type invalid, possible values 'pvp','pve' but was "+type));
    }

    @Path("/kills")
    @GET
    @Produces("application/json")
    public Integer getKillsForPlayer(@QueryParam("type") String type){
        LOG.debug("getKills");
        if(type==null){
            return dao.getKills();
        }else if(type.equals("pvp")){
            return dao.getPvpKills();
        }else if (type.equals("pve")){
            return dao.getPveKills();
        }
        throw new WebApplicationException(new IllegalArgumentException("type invalid, possible values 'pvp','pve' but was "+type));
    }

    @Path("/enum")
    @GET
    @Produces("application/json")
    public EnumsVO getAllEnums(){
        LOG.debug("enum");
        return new EnumsVO();
    }

//    @Path("/block/break/{username: [a-zA-Z_0-9]+}")
//    @GET
//    @Produces("application/json")
//    public Integer getBlockBreakForPlayer(@PathParam("username") String username){
//        LOG.debug("getDeathsForPlayer: " + username);
//        return //dao.getDeathsForPlayer(username);
//    }
//
//    @Path("/block/place/{username: [a-zA-Z_0-9]+}")
//    @GET
//    @Produces("application/json")
//    public Integer getBlockPlaceForPlayer(@PathParam("username") String username){
//        LOG.debug("getDeathsForPlayer: " + username);
//        return //dao.getDeathsForPlayer(username);
//    }


}
