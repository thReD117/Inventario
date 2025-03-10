package Ropa;
import Enums.*;
public class Interior extends Clothing{
    public Interior(String size, String material, String color, String description, String type_category, float price, int quantity, Season season, Gender gender, Type type, CategoryInte category) {
        super(size, material, color, description, type_category, price, quantity, season, gender, type, category);
    }
    
    public Interior(Interior copy){
        super(copy);
    }
    
    @Override
    public Interior clone(){
        return new Interior(this);
    }
}