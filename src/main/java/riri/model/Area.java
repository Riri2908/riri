package riri.model;
import java.util.List;

public class Area extends BaseModel{
    private String name;

    public Area(int id, String name) {
        super(id);
        this.name = name;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toFileString() {
        return id + ";" + name;
    }
}
