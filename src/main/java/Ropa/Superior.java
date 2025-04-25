package Ropa;
import Enums.*;
public class Superior extends Clothing {
    public Superior(String size, String material, String color, String description, float price, int quantity, Season season, Gender gender, CategorySup category) {
        super(size, material, color, description, price, quantity, season, gender, category);
    }
    
    public Superior(Superior copy){
        super(copy);
    }
    
    @Override
    public Superior clone(){
        return new Superior(this);
    }
}
