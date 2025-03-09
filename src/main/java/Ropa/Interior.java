package Ropa;
import Enums.*;
public class Interior extends Clothe{
    protected CategoryInte category;
    protected String type_category;

    public CategoryInte getCategory() {
        return category;
    }

    public void setCategory(CategoryInte category) {
        this.category = category;
    }

    public String getType_category() {
        return type_category;
    }

    public void setType_category(String type_category) {
        this.type_category = type_category;
    }
    
    public Interior(String size, String material, String color, String description, String id, String type_category, float price, int quantity, Season season, Gender gender, Type type, CategoryInte category) {
        this.size=size;
        this.material=material;
        this.color=color;
        this.description=description;
        this.id=id;
        this.type_category=type_category;
        this.price=price;
        this.quantity=quantity;
        this.season=season;
        this.gender=gender;
        this.type=type;
        this.category=category;
    }
}