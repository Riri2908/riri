package riri.model;

public class CustomerType extends BaseModel{

    public String name;
    public float discountRate;

    public CustomerType(int id, String name, float discountRate){
        super(id);
        this.name = name;
        this.discountRate = discountRate;
    }

    public float getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(float discountRate) {
        this.discountRate = discountRate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toFileString(){
        return id +";"+name+";"+discountRate;
    }
}
