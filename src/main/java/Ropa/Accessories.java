package Ropa;
import Enums.*;
public class Accessories extends Clothing{
    public Accessories(String size, String material, String color, String description, String type_category, float price, int quantity, Season season, Gender gender, CategoryAcc category) {
        super(size, material, color, description, type_category, price, quantity, season, gender, category);
    }   
    
    public Accessories(Accessories copy){
        super(copy);
    }
    
    @Override
    public Accessories clone(){
        return new Accessories(this);
    }
}
