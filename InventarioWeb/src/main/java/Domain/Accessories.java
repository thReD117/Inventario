package Domain;
import Enums.*;
public class Accessories extends Clothing{
    public Accessories(String size, String material, String color, String description, float price, int quantity, Season season, Gender gender, CategoryAcc category) {
        super(size, material, color, description, price, quantity, season, gender, category);
    }   
    
    public Accessories(Accessories copy){
        super(copy);
    }
    
    @Override
    public Accessories clone(){
        return new Accessories(this);
    }
}
