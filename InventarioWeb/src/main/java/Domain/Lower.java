package Domain;
import Enums.*;
public class Lower extends Clothing{
    public Lower(String size, String material, String color, String description, float price, int quantity, Season season, Gender gender, CategoryLow category) {
        super(size, material, color, description, price, quantity, season, gender, category);
    }
    
    public Lower(Lower copy){
        super(copy);
    }
    
    @Override
    public Lower clone(){
        return new Lower(this);
    }
}