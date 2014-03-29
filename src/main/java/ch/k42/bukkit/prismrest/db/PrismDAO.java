package ch.k42.bukkit.prismrest.db;

import ch.k42.bukkit.prismrest.minions.ConfigFile;
import ch.k42.bukkit.prismrest.model.EntryVO;
import org.jboss.logging.Logger;

import javax.faces.bean.ApplicationScoped;
import java.sql.*;
import java.util.*;

/**
 * Created by Thomas on 27.03.14.
 */
@ApplicationScoped
public class PrismDAO {

    private Logger logger = Logger.getLogger(PrismDAO.class);

    private String userName = "root";
    private String password = "1234";

    private String hostname = "localhost";
    private String port = "3306";
    private String dbtype = "mysql";

    private String dbname = "prism_aftermath";

    private String ACTION_TABLE = ".prism_actions";

    private Connection connection;

    public PrismDAO(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public PrismDAO() {
        this.dbname = ConfigFile.getProperty("dbname",dbname);
        this.hostname = ConfigFile.getProperty("hostname",hostname);
        this.port = ConfigFile.getProperty("dbport",port);
        this.userName = ConfigFile.getProperty("dbusername",userName);
        this.password = ConfigFile.getProperty("dbpassword",password);
        try {
            this.connect();
        } catch (SQLException e) {
            logger.error(e);
        }
    }

    public void connect() throws SQLException {
        Properties connectionProps = new Properties();
        connectionProps.put("user", this.userName);
        connectionProps.put("password", this.password);

        if (this.dbtype.equals("mysql")) {
            String url =getURL();
            logger.debug("Connecting to: " + url);
            this.connection = DriverManager.getConnection(url,connectionProps);
        } else{
            logger.error("Unsupported DB type");
        }
        logger.debug("Connected to database");
    }

    public Set<String> getPlayers(){
        try {
            StringBuilder squery = new StringBuilder(buildSelectWhere("player"));
            squery.append(" GROUP BY ").append(Column.PLAYER.name);
            logger.debug(squery);
            ResultSet result = executePreparedStatement(squery.toString());
            Set<String> players = new HashSet<>();
            while (result.next()){
                String name = result.getString(1);
                if(!ReservedNames.RESERVED_PLAYERNAMES.contains(name)) //check if it's a special player name
                    players.add(name);
            }
            return players;
        } catch (SQLException e) {
            logger.error(e);
        }
        return new HashSet<>();
    }

    public int getDeaths(){
        try {
            String squery = buildSelectWhere("count(*)",Column.ACTION_TYPE);
            return  getCount(executePreparedStatement(squery, ActionType.PLAYER_DEATH.name));
        } catch (SQLException e) {
            logger.error(e);
        }
        return 0;
    }

    public int getDeaths(String type){
        try {
            String squery = buildSelectWhere("count(*)",Column.ACTION_TYPE,Column.DATA);
            return  getCount(executePreparedStatement(squery, ActionType.PLAYER_DEATH.name, type));
        } catch (SQLException e) {
            logger.error(e);
        }
        return 0;
    }

    public int getDeathsForPlayer(String player){
        try {
            String squery = buildSelectWhere("count(*)", Column.PLAYER, Column.ACTION_TYPE);
            return  getCount(executePreparedStatement(squery, player, ActionType.PLAYER_DEATH.name));
        } catch (SQLException e) {
            logger.error(e);
        }
        return 0;
    }

    public int getDeathsForPlayerByType(String player,String type){
        try {
            String squery = buildSelectWhere("count(*)", Column.PLAYER, Column.ACTION_TYPE, Column.DATA);
            return  getCount(executePreparedStatement(squery, player, ActionType.PLAYER_DEATH.name, type));
        } catch (SQLException e) {
            logger.error(e);
        }
        return 0;
    }



    public int getPveKillsForPlayer(String player){
        try {
            String squery    = buildSelectWhere("count(*)", Column.PLAYER, Column.ACTION_TYPE);
            return  getCount(executePreparedStatement(squery, player, ActionType.PLAYER_KILL.name));
        } catch (SQLException e) {
            logger.error(e);
        }
        return 0;
    }

    public int getKills(){
        return getPveKills() + getPvpKills();
    }

    public int getPveKills(){
        try {
            String squery    = buildSelectWhere("count(*)", Column.ACTION_TYPE);
            return  getCount(executePreparedStatement(squery, ActionType.PLAYER_KILL.name));
        } catch (SQLException e) {
            logger.error(e);
        }
        return 0;
    }

    public int getPvpKills(){
        try {
            String squery    = buildSelectWhere("count(*)",Column.ACTION_TYPE) + " AND " + Column.DATA + " LIKE ?";
            return  getCount(executePreparedStatement(squery, ActionType.PLAYER_DEATH.name, DeathType.PVP_PREFIX.type + "%"));
        } catch (SQLException e) {
            logger.error(e);
        }
        return 0;
    }

    public int getKillsForPlayer(String player){
        return getPveKillsForPlayer(player) + getPvpKillsForPlayer(player);
    }

    public int getPvpKillsForPlayer(String player){
        try {
            String squery    = buildSelectWhere("count(*)", Column.DATA, Column.ACTION_TYPE);
            return  getCount(executePreparedStatement(squery, DeathType.PVP_PREFIX.type + player, ActionType.PLAYER_DEATH.name));
        } catch (SQLException e) {
            logger.error(e);
        }
        return 0;
    }


    public Integer getBlockBreakForPlayer(String player) {
        try {
            String squery    = buildSelectWhere("count(*)", Column.ACTION_TYPE,Column.PLAYER);
            return  getCount(executePreparedStatement(squery,ActionType.BLOCK_BREAK.name,player));
        } catch (SQLException e) {
            logger.error(e);
        }
        return 0;
    }


    public int getBlockBreak() {
        try {
            String squery    = buildSelectWhere("count(*)", Column.ACTION_TYPE);
            return  getCount(executePreparedStatement(squery,ActionType.BLOCK_BREAK.name));
        } catch (SQLException e) {
            logger.error(e);
        }
        return 0;
    }

    public Integer getBlockPlaceForPlayer(String player) {
        try {
            String squery    = buildSelectWhere("count(*)", Column.ACTION_TYPE,Column.PLAYER);
            return  getCount(executePreparedStatement(squery,ActionType.BLOCK_PLACE.name,player));
        } catch (SQLException e) {
            logger.error(e);
        }
        return 0;
    }

    public int getBlockPlace() {
        try {
            String squery    = buildSelectWhere("count(*)", Column.ACTION_TYPE);
            return  getCount(executePreparedStatement(squery,ActionType.BLOCK_PLACE.name));
        } catch (SQLException e) {
            logger.error(e);
        }
        return 0;
    }

    public List<EntryVO> getScoreboardForAction(int limit,ActionType action){
        String squery = buildSelectWhere("player,count(*)",Column.ACTION_TYPE);
        squery += " GROUP BY player ORDER BY count(*) DESC LIMIT " + limit;
        try {
            ResultSet result = executePreparedStatement(squery,action.name);
            List<EntryVO> scoreboard = new ArrayList<>(limit);
            while (result.next()){
                scoreboard.add(new EntryVO(result.getString(1),result.getInt(2)));
            }
            return scoreboard;
        } catch (SQLException e) {
            logger.error(e);
        }
        return new ArrayList<>(0);
    }

    public List<EntryVO> getScoreboardKillsPve(int limit){
        return getScoreboardForAction(limit,ActionType.PLAYER_KILL);
    }

    public List<EntryVO> getScoreboardKillsPvp(int limit){
        String squery = buildSelectWhere("data,count(*)",Column.ACTION_TYPE);
        squery += " AND " + Column.DATA + " LIKE ? GROUP BY data ORDER BY count(*) DESC LIMIT " + limit;
        try {
            ResultSet result = executePreparedStatement(squery,ActionType.PLAYER_DEATH.name,DeathType.PVP_PREFIX.type+"%");
            List<EntryVO> scoreboard = new ArrayList<>(limit);
            while (result.next()){
                String[] split = result.getString(1).split(":");
                if(split.length<=0) continue;
                scoreboard.add(new EntryVO(split[1], result.getInt(2)));
            }
            return scoreboard;
        } catch (SQLException e) {
            logger.error(e);
        }
        return new ArrayList<>(0);
    }

    public List<EntryVO> getScoreboardKills(int limit){
        List<EntryVO> pve = getScoreboardKillsPve(limit);
        List<EntryVO> pvp = getScoreboardKillsPvp(limit);
        Map<String,EntryVO> scoreboard = new HashMap<>();
        for(EntryVO e : pve){
            scoreboard.put(e.getName(),e);
        }
        for(EntryVO e : pvp){
            if(scoreboard.containsKey(e.getName())){
                scoreboard.get(e.getName()).addValue(e.getValue());
            }else {
                scoreboard.put(e.getName(),e);
            }
        }

        List<EntryVO> scores = new ArrayList<>(scoreboard.values());
        Collections.sort(scores,new Comparator<EntryVO>() {
            @Override
            public int compare(EntryVO o1, EntryVO o2) {
                return o2.getValue()-o1.getValue();
            }
        });
        return scores.subList(0,limit);
    }

    private ResultSet executePreparedStatement(String squery, String... args) throws SQLException {
        PreparedStatement query = connection.prepareStatement(squery);
        for(int i=1;i<=args.length;i++){
            query.setString(i, args[i-1]);
        }
        logger.debug("Query: " + query);
        return query.executeQuery();
    }

    /**
     * Constructs 'SELECT ... FROM <PRISM_DB> WHERE ... ' query strings
     * @param cols the columns to select
     * @param where columns to add as where expressions
     * @return 'SELECT <cols> FROM <PRISM_DB> WHERE where1=? and
     */
    private String buildSelectWhere(String cols, Column... where){
        StringBuilder str = new StringBuilder("SELECT ");
        str.append(cols).append(" FROM ").append(getActionTable());

        if(where.length>0){
            str.append(" WHERE ");
            str.append(where[0].name).append("=? ");
        }

        for(int i=1;i<where.length;i++){
            str.append("and ");
            str.append(where[i].name).append("=? ");
        }

        return str.toString();
    }

    private String getURL(){
        return new StringBuilder("jdbc:").append(dbtype).append("://").append(hostname).append(":").append(port).append("/").toString();
    }

    private int getCount(ResultSet result) throws SQLException {
            if(result.first()){
                return result.getInt(1);
            }else {
                return 0;
            }
    }


    private String getActionTable(){
        return dbname+ACTION_TABLE;
    }

}
