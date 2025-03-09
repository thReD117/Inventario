package Ropa;

import Enums.*;

public class Prenda {
    protected String size, material, color, description, id;
    protected float price;
    protected int quantity;
    protected Temporada Season;
    protected Genero gender;
    protected Tipo type;

    public Tipo getType() {
        return type;
    }

    public void setType(Tipo type) {
        this.type = type;
    }
    
    public Temporada getSeason() {
        return Season;
    }

    public void setSeason(Temporada Season) {
        this.Season = Season;
    }

    public Genero getGender() {
        return gender;
    }

    public void setGender(Genero gender) {
        this.gender = gender;
    }
    
    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int amount) {
        this.quantity = amount;
    }
    
}