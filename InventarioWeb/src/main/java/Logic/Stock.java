package Logic;

import Exceptions.*;
import Domain.Clothing;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Stock {
    private HashMap<String, ClothingGroup> stock;
    private ArrayList<Clothing> recentlyAdded;
    
    public Stock(){
        stock = new HashMap<>();
        recentlyAdded = new ArrayList<>();
    }
    
    /**
     * Añade la prenda a su correspondiente ClothingGroup, de no existir lo crea
     * @param clothing Prenda a añadir al stock
     */
    public void add(Clothing clothing){
        stock.putIfAbsent(clothing.getGroupId(), new ClothingGroup());
        
        // Check to add as many times the clothing as its quantity field
        int quantity = clothing.getQuantity();
        clothing.setQuantity(1);
        for(int i=0; i<quantity; i++){
            Clothing temp = clothing.clone();
            stock.get(clothing.getGroupId()).add(temp);
            DatabaseManager.insertItem(temp);
            recentlyAdded.add(temp);
        }
    }
    
    
    public List<Clothing> listRecentlyAdded(){
        return recentlyAdded;
    }
    
    public void clearRecentlyAdded(){
        recentlyAdded.clear();
    }
    
    /**
     * @return Devulve un ArrayList con todas las prendas en todos los grupos del stock ordenado mediante el id completo.
     */
    public ArrayList<Clothing> listFullStock(){
        ArrayList<Clothing> fullStock = new ArrayList<>();
        fullStock.clear();
        stock.values().forEach(clothingGroup -> {
            fullStock.addAll(clothingGroup.getClothingInGroup());
        });
        
        Collections.sort(fullStock);
        
        return fullStock;
    }
   
    
    /**
     * Limpia todos los objetos de prenda que existan en el grupo pero no se deshace del objeto del grupo
     * @param groupId Id del grupo, no la id completa
     * @throws Exceptions.InvalidIdException
     * @throws Exceptions.GroupNotFoundException
     */
    public void clearGroupById(String groupId) throws InvalidIdException, GroupNotFoundException{
        ClothingGroup clothingGroup = getClothingGroupByGroupId(groupId);
        clothingGroup.clearGroup();
        DatabaseManager.clearGroup(groupId);
    }
    
    
    /**
     * Modifica un item que ya exista en algún grupo. Eliminará el item anterior de dicho grupo e insertará el nuevo item 
     * en el grupo correspondiente
     * @param id id del objeto a modificar(eliminar)
     * @param item el nuevo obj clothing que será insertado
     */
    public void modifyItem(String id, Clothing item) throws ItemNotFoundException, InvalidIdException, GroupNotFoundException{
        deleteClothingById(id);
        item.recalculateGroupId();
        add(item);
    }
    
    /**
     * Creará un nuevo grupo en base al objeto modificado. Va a crear n cantidad de objetos que existan en el grupo
     * y establecerá los mismos valores que el template para todas sus propiedades.
     * 
     * El anterior grupo será eliminado
     * 
     * @param groupId id del grupo que se desea cambiar
     * @param itemTemplate Objeto que será copiado n cantidad de veces.
     * @throws Exceptions.InvalidIdException
     * @throws Exceptions.GroupNotFoundException
     */
    public void modifyGroup(String groupId, Clothing itemTemplate) throws InvalidIdException, GroupNotFoundException{
        ClothingGroup clothingGroup = getClothingGroupByGroupId(groupId);
        
        itemTemplate.recalculateGroupId();
        
        int objectsToCreate = clothingGroup.getSize();
        clothingGroup.clearGroup();
        DatabaseManager.clearGroup(groupId);
                
        for(int i=0; i<objectsToCreate; i++){
            add(itemTemplate.clone());
        }
    }
     
    public Clothing searchById(String id) throws ItemNotFoundException, InvalidIdException, GroupNotFoundException {
        ClothingGroup clothingGroup = getClothingGroupByFullId(id);
        
        Clothing clothing = clothingGroup.getById(id.split("-")[1]);
        if(clothing == null)
            throw new ItemNotFoundException("Prenda con ID " + id + " no encontrada.");
        return clothing;
    }
     
     
    public void deleteClothingById(String id) throws ItemNotFoundException, InvalidIdException, GroupNotFoundException {
        ClothingGroup clothingGroup = getClothingGroupByFullId(id);
        
        if(!clothingGroup.removeById(id.split("-")[1]))
            throw new ItemNotFoundException("Prenda con ID " + id + " no encontrada.");
        
        DatabaseManager.removeClothingStockById(id);
    }
     
    /**
     * Libera de la responsabilidad de hacer esto a los métodos de las operaciones básicas
     * @param groupId
     * @return
     * @throws InvalidIdException
     * @throws GroupNotFoundException 
     */
    private ClothingGroup getClothingGroupByGroupId(String groupId) throws InvalidIdException, GroupNotFoundException{
        if(groupId.contains("-"))
            throw new InvalidIdException("Id de grupo inválida"); 
        ClothingGroup clothingGroup = stock.get(groupId);
        if(clothingGroup == null)
            throw new GroupNotFoundException("Grupo de ropa no encontrado");
        
        return clothingGroup;
    }
    
    /**
     * Lo mismo que el método anterior
     * @param id
     * @return
     * @throws InvalidIdException
     * @throws GroupNotFoundException 
     */
    private ClothingGroup getClothingGroupByFullId(String id) throws InvalidIdException, GroupNotFoundException{
        String[] idParts = id.split("-");
        if(idParts.length != 2)
            throw new InvalidIdException("La id no está completa");
        ClothingGroup clothingGroup = stock.get(idParts[0]);
        if(clothingGroup == null)
            throw new GroupNotFoundException("Grupo de ropa no encontrado");
        
        return clothingGroup;
    }
    
    
    public void loadFromDB(){
        DatabaseManager.SelectAllClothing().forEach(clothing -> {
            ClothingGroup group =  stock.getOrDefault(clothing.getGroupId(), new ClothingGroup());
            stock.putIfAbsent(clothing.getGroupId(), group);
            
            int quantity = Math.max(clothing.getQuantity(), 1);
            // Check to add as many times the clothing as its quantity field
            for(int i=0; i < quantity; i++){
                group.addFromDB(clothing);
            }
            group.fixPersistentCounter();
        });
    }
    private List<Clothing> fullStock;
    
    private void createStockList() {
        fullStock = new ArrayList<>();
    }

    public List<Clothing> getAllClothing() {
            createStockList(); // Para asegurarnos que fullStock está actualizado
            return new ArrayList<>(fullStock);
        }
         /**
         * Elimina una prenda por su ID.
         * @param id el ID completo de la prenda
         * @return true si se eliminó, false si no existía
         */
        public boolean removeById(String id) {
            try {
                deleteClothingById(id);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
}

