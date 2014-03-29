package ch.k42.bukkit.prismrest.rest;

import ch.k42.bukkit.prismrest.db.ActionType;
import ch.k42.bukkit.prismrest.model.EntryVO;
import ch.k42.bukkit.prismrest.model.EnumsVO;
import ch.k42.bukkit.prismrest.db.PrismDAO;
import org.jboss.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;
import java.util.Set;

/**
 * Created by Thomas on 26.03.14.
 */

@Path("/")
@RequestScoped
public class PrismRESTService {

    private Logger LOG = Logger.getLogger(PrismRESTService.class);

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
    public Integer getKills(@QueryParam("type") String type){
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

    @Path("/scoreboard/kills")
    @GET
    @Produces("application/json")
    public List<EntryVO> getScoreboardKills(@QueryParam("type") String type,@DefaultValue("10") @QueryParam("limit") int limit){
        LOG.debug("getKills");
        if(type==null){
            return dao.getScoreboardKills(limit);
        }else if(type.equals("pvp")){
            return dao.getScoreboardKillsPvp(limit);
        }else if (type.equals("pve")){
            return dao.getScoreboardKillsPve(limit);
        }
        throw new WebApplicationException(new IllegalArgumentException("type invalid, possible values 'pvp','pve' but was "+type));
    }

    @Path("/scoreboard/block-place")
    @GET
    @Produces("application/json")
    public List<EntryVO> getScoreboardBlockPlace(@DefaultValue("10") @QueryParam("limit") int limit){
        LOG.debug("getKills");
        return dao.getScoreboardForAction(limit, ActionType.BLOCK_PLACE);
    }

    @Path("/scoreboard/block-break")
    @GET
    @Produces("application/json")
    public List<EntryVO> getScoreboardBlockBreak(@DefaultValue("10") @QueryParam("limit") int limit){
        LOG.debug("getKills");
        return dao.getScoreboardForAction(limit,ActionType.BLOCK_BREAK);
    }

    @Path("/enum")
    @GET
    @Produces("application/json")
    public EnumsVO getAllEnums(){
        LOG.debug("enum");
        return new EnumsVO();
    }

    @Path("/block-break/{username: [a-zA-Z_0-9]+}")
    @GET
    @Produces("application/json")
    public Integer getBlockBreakForPlayer(@PathParam("username") String username){
        LOG.debug("blockbreak: " + username);
        return dao.getBlockBreakForPlayer(username);
    }

    @Path("/block-break")
    @GET
    @Produces("application/json")
    public Integer getBlockBreak(){
        LOG.debug("block break: ");
        return dao.getBlockBreak();
    }

    @Path("/block-place/{username: [a-zA-Z_0-9]+}")
    @GET
    @Produces("application/json")
    public Integer getBlockPlaceForPlayer(@PathParam("username") String username){
        LOG.debug("bloackplace: " + username);
        return dao.getBlockPlaceForPlayer(username);
    }

    @Path("/block-place")
    @GET
    @Produces("application/json")
    public Integer getBlockPlace(){
        LOG.debug("bloackplace: ");
        return dao.getBlockPlace();
    }


}
