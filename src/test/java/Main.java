import ch.k42.bukkit.statrest.db.PrismDAO;

import java.sql.SQLException;

/**
 * Created by Thomas on 27.03.14.
 */
public class Main {
    public static void main(String[] args) {
        PrismDAO connector = new PrismDAO("root","1234");
        try {
            connector.connect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(connector.getDeathsForPlayer("AthmosPrime"));
        System.out.println(connector.getPlayers());
        System.out.println(connector.getPvpKills());
    }
}
