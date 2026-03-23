package riri.model;

public class Shelf extends BaseModel{
    private String name;
    private int areaId;

    public Shelf(int id, String name, int areaId) {
        super(id);
        this.name = name;
        this.areaId = areaId;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String toFileString() {
        return id + ";" + name + ";" + areaId;
    }
}
