package Ropa;
import Enums.*;
public class Superior extends Clothing {
    public Superior(String size, String material, String color, String description, String type_category, float price, int quantity, Season season, Gender gender, Type type, CategorySup category) {
        this.size=size;
        this.material=material;
        this.color=color;
        this.description=description;
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
