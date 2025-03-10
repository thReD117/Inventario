package Ropa;

import Enums.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public abstract class Clothing implements Comparable<Clothing>{
    protected String size, material, color, description, typeCategory;
    protected float price;
    protected int quantity;
    protected Season season;
    protected Gender gender;
    protected Type type;
    protected CategoryEnumerable category;
    
    protected String id; // Full id = groupId + individualId
    protected String groupId; // Generada mediante las propiedades de la prenda
    protected String individualId; // Generada por su numero de item en ser añadido al stock

    
    public Type getType() {return type;}
    public void setType(Type type) {this.type = type;}
    
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
    public void setId(String id) {this.id = id;}

    public String getGroupId() {return groupId;}
    protected String generateGroupId(){
        String rawData = size+material+color+description+price+quantity+season+gender+type+category.getCategory()+typeCategory;
        return hashString(rawData, 12);
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

    public String getTypeCategory() {return typeCategory;}
    public void setTypeCategory(String typeCategory) {this.typeCategory = typeCategory;}

    public CategoryEnumerable getCategory() {return category;}
    public void setCategory(CategoryEnumerable category) {this.category = category;} 
    
    // Ordenamiento natural establecido mediante las id completas
    @Override
    public int compareTo(Clothing c){
        return this.id.compareToIgnoreCase(c.id);
    }
}