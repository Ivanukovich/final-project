package by.ivanukovich.bicyclerental.model.entity;

public enum BicycleStatus {
    FREE("free"),
    RENTED("rented"),
    INACTIVE("inactive");
    private String statusName;

    BicycleStatus (String status){
        this.statusName = status;
    }

    public String getStatusName(){
        return statusName;
    }
}
