package ch.k42.bukkit.prismrest.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Thomas on 28.03.14.
 */
@XmlRootElement
public class EntryVO {
    private String name;
    private int value;

    public EntryVO() {
    }

    public EntryVO(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void addValue(int value){
        this.value += value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
