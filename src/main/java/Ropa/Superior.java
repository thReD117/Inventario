package Ropa;
import Enums.*;
public class Superior extends Clothing {
    public Superior(String size, String material, String color, String description, String type_category, float price, int quantity, Season season, Gender gender, CategorySup category) {
        super(size, material, color, description, type_category, price, quantity, season, gender, category);
    }
    
    public Superior(Superior copy){
        super(copy);
    }
    
    @Override
    public Superior clone(){
        return new Superior(this);
    }
}
