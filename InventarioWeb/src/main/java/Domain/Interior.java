package Domain;
import Enums.*;
public class Interior extends Clothing{
    public Interior(String size, String material, String color, String description, float price, int quantity, Season season, Gender gender, CategoryInte category) {
        super(size, material, color, description, price, quantity, season, gender, category);
    }
    
    public Interior(Interior copy){
        super(copy);
    }
    
    @Override
    public Interior clone(){
        return new Interior(this);
    }
}