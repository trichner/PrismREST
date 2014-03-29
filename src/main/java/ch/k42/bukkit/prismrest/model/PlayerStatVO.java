package ch.k42.bukkit.prismrest.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Thomas on 26.03.14.
 */
@XmlRootElement
public class PlayerStatVO {
    private String name;

    public PlayerStatVO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
