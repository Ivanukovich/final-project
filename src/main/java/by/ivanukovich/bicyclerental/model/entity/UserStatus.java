package by.ivanukovich.bicyclerental.model.entity;

public enum UserStatus {
    INACTIVE("inactive"),
    ACTIVE("active");
    private String statusName;

    UserStatus (String status){
        this.statusName = status;
    }

    public String getstatusName(){
        return statusName;
    }
}
