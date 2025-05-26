package Domain;

import Enums.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public abstract class Clothing implements Comparable<Clothing>{
    protected String size, material, color, description;
    protected float price;
    protected int quantity;
    protected Season season;
    protected Gender gender;
    protected CategoryEnumerable category;
    
    protected String id; // Full id = groupId + individualId
    protected String groupId; // Generada mediante las propiedades de la prenda
    protected String individualId; // Generada por su numero de item en ser añadido al stock

    // Constructor general para todas las clases hijas
    public Clothing(String size, String material, String color, String description, float price, 
            int quantity, Season season, Gender gender, CategoryEnumerable category) {
        this.size=size;
        this.material=material;
        this.color=color;
        this.description=description;
        this.price=price;
        this.quantity=quantity;
        this.season=season;
        this.gender=gender;
        this.category=category;
        this.groupId = generateGroupId();
    }
    
    /**
     * Constructor para clonar un objeto de tipo clothing
     * @param other Elobjeto a clonar
     */
    public Clothing(Clothing other) {
        this.size=other.size;
        this.material=other.material;
        this.color=other.color;
        this.description=other.description;
        this.price=other.price;
        this.quantity=other.quantity;
        this.season=other.season;
        this.gender=other.gender;
        this.category=other.category;
        this.groupId = other.groupId;
    }
    
    // Método abstracto para clonar los objetos
    public abstract Clothing clone();
    
    public Season getSeason() {return season;}
    public void setSeason(Season Season) {this.season = Season;}

    public Gender getGender() {return gender;}
    public void setGender(Gender gender) {this.gender = gender;}
    
    public String getSize() {return size;}
    public void setSize(String size) {this.size = size;}

    public String getMaterial() {return material;}
    public void setMaterial(String material) {this.material = material;}

    public String getColor() {return color;}
    public void setColor(String color) {this.color = color;}

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    public String getId() {return id;}
    public void setId(String id) {
        this.id = id;
        String[] ids = id.split("-");
        this.groupId = ids[0];
        this.individualId = ids[1];
    }
    
    public String getGroupId() {return groupId;}
    public void recalculateGroupId(){
        this.groupId = generateGroupId();
    }
    protected String generateGroupId(){
        String rawData = size+material+color+description+price+quantity+season+gender+category.getDisplayName();
        return hashString(rawData, 12).toUpperCase();
    }
    
    /**
     * Utiliza el algoritmo de encriptación SHA256 para generar un ID unica en base a sus propiedades del objeto
     * @param input Union en string de los valores de las propiedades de la prenda
     * @param maxLenght Punto del String al que truncar el resultado encriptado
     * @return Una string que conformará el group Id. Codificado en hexadecimal
     */
    protected String hashString(String input, int maxLenght) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b)); // Codifica el hash en formate Hex para que sea legible
            }
            return hexString.substring(0, maxLenght); // Trunca el tamaño de la  
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Problema al generar el hash", e);
        }
    }

    public String getIndividualId() {return individualId;}
    // Establece automaticamente la ID completa una vez que es determinada su id individual
    public void setIndividualId(String individualId) {
        this.individualId = individualId;
        setId(groupId+"-"+individualId);
    }
    
    public float getPrice() {return price;}
    public void setPrice(float price) {this.price = price;}

    public int getQuantity() {return quantity;}
    public void setQuantity(int amount) {this.quantity = amount;}

    public CategoryEnumerable getCategory() {return category;}
    public void setCategory(CategoryEnumerable category) {this.category = category;} 
    
    // Ordenamiento natural establecido mediante las id completas
    @Override
    public int compareTo(Clothing c){
        return this.id.compareToIgnoreCase(c.id);
    }
    
    @Override
    public String toString(){
        return String.format("%1$s | %2$s | %3$s | %4$s | %5$s | %6$s | %7$s ",
                id,
                category.getDisplayName(),
                gender,
                color,
                size,
                season,
                quantity==0 ? "No stock" : "In stock"
                );        
    }
}