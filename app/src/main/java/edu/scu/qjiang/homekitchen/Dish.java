package edu.scu.qjiang.homekitchen;

/**
 * Created by clover on 5/5/16.
 */
public class Dish {
    private String name;
    private String kitchen;
    private Integer picId;
    private Integer kitId;

    public Dish(String name, String kitchen, Integer picId, Integer kitId) {
        this.name = name;
        this.kitchen = kitchen;
        this.picId = picId;
        this.kitId = kitId;
    }

    public String getName() {
        return name;
    }

    public String getKitchen() {
        return kitchen;
    }

    public Integer getPicId() {
        return picId;
    }

    public Integer getKitId() {
        return kitId;
    }
}
