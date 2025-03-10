package Ropa;
import Enums.*;
public class Interior extends Clothing{
    public Interior(String size, String material, String color, String description, String id, String type_category, float price, int quantity, Season season, Gender gender, Type type, CategoryInte category) {
        this.size=size;
        this.material=material;
        this.color=color;
        this.description=description;
        this.id=id;
        this.typeCategory=type_category;
        this.price=price;
        this.quantity=quantity;
        this.season=season;
        this.gender=gender;
        this.type=type;
        this.category=category;
    
        this.groupId = generateGroupId();
    }
}