package Logic;

import Domain.Clothing;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

public class ClothingGroup {
    private final int MAX_DIGIT_ID = 5;
    
    private HashMap<String, Clothing> clothingGroup;
    private int persistentCounter;
    
    public ClothingGroup(){
        clothingGroup = new HashMap<>();
        persistentCounter = 0;
    }
    
    /**
     * Añade la ropa al hashmap y le asigna su id individual
     * @param clothing 
     */
    public void add(Clothing clothing){
        generateClotheSingularId(clothing);
        clothingGroup.put(clothing.getIndividualId(), clothing);
    }
    
    /**
     * Cuando se agrega desde la base de datos, se debe de respetar el orden que ya tienen, es decir, respetar sus id
     * y no recalcularlas
     * @param clothing 
     */
    public void addFromDB(Clothing clothing){
        clothingGroup.put(clothing.getIndividualId(), clothing);
    }

    public void fixPersistentCounter(){
        persistentCounter = clothingGroup.keySet().stream()
                .mapToInt(Integer::parseInt)
                .max()
                .orElse(0);
    }
    
    /**
     * @return Retorna un arraylist con todas las prendas que existen en el grupo
     */
    public ArrayList<Clothing> getClothingInGroup(){
        return clothingGroup.values().stream()
                .collect(Collectors.toCollection(ArrayList::new));
    }
    
    /**
     * Este método es lo equivalente a eliminar un grupo. Sin embargo decidimos no eliminar el objeto para mantener el 
     * persistentCounter, de esta forma las siguientes prendas que se generen en este grupo tendrán ID diferente a las ya generadas
     */
    public void clearGroup(){
        clothingGroup.clear();
    }
    
    
    
    /**
     * Incrementa el contador según se añadan prendas y les establece dicho numero rellenando la strings de 0 hasta que
     * alcance el valor de MAX_DIGIT_ID
     * @param clothing Prenda a la que se le asignara su Id individual
     */
    private void generateClotheSingularId(Clothing clothing){
        persistentCounter++;
        String id = persistentCounter + "";
        for(int i=id.length(); i<MAX_DIGIT_ID; i++){
            id = "0"+id;
        }
        
        clothing.setIndividualId(id);
    }
    
    
    public int getSize(){
        return clothingGroup.size();
    }

   public Clothing getById(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("ID no puede ser nulo o vacío");
        }
        return clothingGroup.get(id); 
    }


    boolean removeById(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("ID no puede ser nulo o vacío");
        }
        Clothing removedClothing = clothingGroup.remove(id); 
        return removedClothing != null; 
    }
}


    


