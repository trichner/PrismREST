package ch.k42.bukkit.prismrest.model;

import ch.k42.bukkit.prismrest.db.ActionType;
import ch.k42.bukkit.prismrest.db.DeathType;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Thomas on 28.03.14.
 */
@XmlRootElement
public class EnumsVO {
    public EnumsVO() {
        Set<String> set = new HashSet<>();
        for(ActionType t : ActionType.values()){
            set.add(t.name);
        }
        actionTypes = Collections.unmodifiableSet(set);
        set = new HashSet<>();
        for(DeathType t : DeathType.values()){
            set.add(t.type);
        }
        deathTypes = Collections.unmodifiableSet(set);
    }
    private Collection<String> actionTypes;
    private Collection<String> deathTypes;

    public Collection<String> getActionTypes() {
        return actionTypes;
    }

    public Collection<String> getDeathTypes() {
        return deathTypes;
    }
}
