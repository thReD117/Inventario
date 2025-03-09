package Ropa;
import Enums.*;
public class Superior extends Clothe {
    protected CategorySup category;
    protected String Type_Category;

    public CategorySup getCategory() {
        return category;
    }

    public void setCategory(CategorySup category) {
        this.category = category;
    }

    public String getType_Category() {
        return Type_Category;
    }

    public void setType_Category(String Type_Category) {
        this.Type_Category = Type_Category;
    }
    
    public Superior(String size, String material, String color, String description, String id, String type_category, float price, int quantity, Season season, Gender gender, Type type, CategorySup category) {
        this.size=size;
        this.material=material;
        this.color=color;
        this.description=description;
        this.id=id;
        this.Type_Category=type_category;
        this.price=price;
        this.quantity=quantity;
        this.season=season;
        this.gender=gender;
        this.type=type;
        this.category=category;
    }
}
