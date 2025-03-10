package Ropa;
import Enums.*;
public class Accessories extends Clothing{
    public Accessories(String size, String material, String color, String description, String id, String type_category, float price, int quantity, Season season, Gender gender, Type type, CategoryAcc category) {
        super(size, material, color, description, type_category, price, quantity, season, gender, type, category);
    }   
    
    public Accessories(Accessories copy){
        super(copy);
    }
    
    @Override
    public Accessories clone(){
        return new Accessories(this);
    }
}
