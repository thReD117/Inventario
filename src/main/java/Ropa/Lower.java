package Ropa;
import Enums.*;
public class Lower extends Clothing{
    public Lower(String size, String material, String color, String description, String type_category, float price, int quantity, Season season, Gender gender, CategoryLow category) {
        super(size, material, color, description, type_category, price, quantity, season, gender, category);
    }
    
    public Lower(Lower copy){
        super(copy);
    }
    
    @Override
    public Lower clone(){
        return new Lower(this);
    }
}