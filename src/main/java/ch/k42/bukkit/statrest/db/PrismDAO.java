package ch.k42.bukkit.statrest.db;

import org.jboss.logging.Logger;

import javax.faces.bean.ApplicationScoped;
import java.sql.*;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * Created by Thomas on 27.03.14.
 */
@ApplicationScoped
public class PrismDAO {

    private Logger LOG = Logger.getLogger(PrismDAO.class);

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

    //@Inject
    public PrismDAO() {
        try {
            this.connect();
        } catch (SQLException e) {
            LOG.error(e);
        }
    }

    public void connect() throws SQLException {
        Properties connectionProps = new Properties();
        connectionProps.put("user", this.userName);
        connectionProps.put("password", this.password);

        if (this.dbtype.equals("mysql")) {
            String url =getURL();
            LOG.debug("Connecting to: "+url);
            this.connection = DriverManager.getConnection(url,connectionProps);
        } else{
            LOG.error("Unsupported DB type");
        }
        LOG.debug("Connected to database");
    }

    public Set<String> getPlayers(){
        try {
            StringBuilder squery = new StringBuilder(buildSelectWhere("player"));
            squery.append(" GROUP BY ").append(Column.PLAYER.name);
            LOG.debug(squery);
            ResultSet result = executeQuery(squery.toString());
            Set<String> players = new HashSet<>();
            while (result.next()){
                String name = result.getString(1);
                if(!ReservedNames.RESERVED_PLAYERNAMES.contains(name)) //check if it's a special player name
                    players.add(name);
            }
            return players;
        } catch (SQLException e) {
            LOG.error(e);
        }
        return new HashSet<>();
    }

    public int getDeaths(){
        try {
            String squery = buildSelectWhere("count(*)",Column.ACTION_TYPE);
            return  getCount(executeQuery(squery,ActionType.PLAYER_DEATH.name));
        } catch (SQLException e) {
            LOG.error(e);
        }
        return 0;
    }

    public int getDeaths(String type){
        try {
            String squery = buildSelectWhere("count(*)",Column.ACTION_TYPE,Column.DATA);
            return  getCount(executeQuery(squery,ActionType.PLAYER_DEATH.name,type));
        } catch (SQLException e) {
            LOG.error(e);
        }
        return 0;
    }

    public int getDeathsForPlayer(String player){
        try {
            String squery = buildSelectWhere("count(*)", Column.PLAYER, Column.ACTION_TYPE);
            return  getCount(executeQuery(squery,player,ActionType.PLAYER_DEATH.name));
        } catch (SQLException e) {
            LOG.error(e);
        }
        return 0;
    }

    public int getDeathsForPlayerByType(String player,String type){
        try {
            String squery = buildSelectWhere("count(*)", Column.PLAYER, Column.ACTION_TYPE, Column.DATA);
            return  getCount(executeQuery(squery,player,ActionType.PLAYER_DEATH.name,type));
        } catch (SQLException e) {
            LOG.error(e);
        }
        return 0;
    }



    public int getPveKillsForPlayer(String player){
        try {
            String squery    = buildSelectWhere("count(*)", Column.PLAYER, Column.ACTION_TYPE);
            return  getCount(executeQuery(squery,player,ActionType.PLAYER_KILL.name));
        } catch (SQLException e) {
            LOG.error(e);
        }
        return 0;
    }

    public int getKills(){
        return getPveKills() + getPvpKills();
    }

    public int getPveKills(){
        try {
            String squery    = buildSelectWhere("count(*)", Column.ACTION_TYPE);
            return  getCount(executeQuery(squery,ActionType.PLAYER_KILL.name));
        } catch (SQLException e) {
            LOG.error(e);
        }
        return 0;
    }

    public int getPvpKills(){
        try {
            String squery    = buildSelectWhere("count(*)",Column.ACTION_TYPE) + " AND " + Column.DATA + " LIKE ?";
            return  getCount(executeQuery(squery,ActionType.PLAYER_DEATH.name,DeathType.PVP_PREFIX.type + "%"));
        } catch (SQLException e) {
            LOG.error(e);
        }
        return 0;
    }

    public int getKillsForPlayer(String player){
        return getPveKillsForPlayer(player) + getPvpKillsForPlayer(player);
    }

    public int getPvpKillsForPlayer(String player){
        try {
            String squery    = buildSelectWhere("count(*)", Column.DATA, Column.ACTION_TYPE);
            return  getCount(executeQuery(squery,DeathType.PVP_PREFIX.type + player,ActionType.PLAYER_DEATH.name));
        } catch (SQLException e) {
            LOG.error(e);
        }
        return 0;
    }

    private ResultSet executeQuery(String squery,String... args) throws SQLException {
        LOG.debug("Starting query: " + squery);
        PreparedStatement query = connection.prepareStatement(squery);
        for(int i=1;i<=args.length;i++){
            query.setString(i, args[i-1]);
        }
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
