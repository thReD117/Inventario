package Ropa;
import Enums.*;
public class Accessories extends Clothe{
   protected CategoryAcc category;
   protected String type_category; //Guarada el tipo de accesorio seleccionado ejemplo "Anillo: Halo Ring"

    public CategoryAcc getCategory() {
        return category;
    }

    public void setCategory(CategoryAcc category) {
        this.category = category;
    }

    public String getType_category() {
        return type_category;
    }

    public void setType_category(String type_category) {
        this.type_category = type_category;
    }

    public Accessories(String size, String material, String color, String description, String id, String type_category, float price, int quantity, Season season, Gender gender, Type type, CategoryAcc category) {
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
