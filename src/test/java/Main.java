import ch.k42.bukkit.statrest.db.PrismDAO;
import ch.k42.bukkit.statrest.model.EntryVO;

import java.sql.SQLException;
import java.util.List;

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
        List<EntryVO> sb = connector.getScoreboardKillsPve(10);
        for(EntryVO e : sb){
            System.out.println(e.getName() + " : " + e.getValue());
        }
        System.out.println("PVP:");
        sb = connector.getScoreboardKillsPvp(10);
        for(EntryVO e : sb){
            System.out.println(e.getName() + " : " + e.getValue());
        }
        System.out.println("ALL:");
        sb = connector.getScoreboardKills(10);
        for(EntryVO e : sb){
            System.out.println(e.getName() + " : " + e.getValue());
        }
    }
}
