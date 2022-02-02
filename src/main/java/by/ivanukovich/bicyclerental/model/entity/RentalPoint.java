package by.ivanukovich.bicyclerental.model.entity;

import java.util.ArrayList;
import java.util.List;

public class RentalPoint {
    private String location;
    private List<Bicycle> freeBycicles = new ArrayList<Bicycle>();

    public RentalPoint(String location) {
        this.location = location;
    }

    public void addBycicle(Bicycle bycicle){
        freeBycicles.add(bycicle);
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Bicycle> getFreeBycicles() {
        return freeBycicles;
    }

    public void setFreeBycicles(List<Bicycle> freeBycicles) {
        this.freeBycicles = freeBycicles;
    }
}
