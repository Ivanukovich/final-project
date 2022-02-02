package by.ivanukovich.bicyclerental.model.entity;

public enum BicycleModel {
    MODEL1("1"),
    MODEL2("2"),
    MODEL3("3");
    private String modelName;

    BicycleModel (String model){
        this.modelName = model;
    }

    public String getModelName(){
        return modelName;
    }
}
