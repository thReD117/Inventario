package Ropa;
import Enums.*;
public class Lower extends Clothe{
    protected CategoryLow category;
    protected String Type_category;

    public CategoryLow getCategory() {
        return category;
    }

    public void setCategory(CategoryLow category) {
        this.category = category;
    }

    public String getType_category() {
        return Type_category;
    }

    public void setType_category(String Type_category) {
        this.Type_category = Type_category;
    }
    
    public Lower(String size, String material, String color, String description, String id, String type_category, float price, int quantity, Season season, Gender gender, Type type, CategoryLow category) {
        this.size=size;
        this.material=material;
        this.color=color;
        this.description=description;
        this.id=id;
        this.Type_category=type_category;
        this.price=price;
        this.quantity=quantity;
        this.season=season;
        this.gender=gender;
        this.type=type;
        this.category=category;
    }
}