package Main;

import Exceptions.*;
import Ropa.Clothing;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Stock {
    private HashMap<String, ClothingGroup> stock;
    private ArrayList<Clothing> fullStock;
    
    public Stock(){
        stock = new HashMap<>();
        fullStock = new ArrayList<>();
    }
    
    /**
     * Añade la prenda a su correspondiente ClothingGroup, de no existir lo crea
     * @param clothing Prenda a añadir al stock
     */
    public void add(Clothing clothing){
        stock.putIfAbsent(clothing.getGroupId(), new ClothingGroup());
        stock.get(clothing.getGroupId()).add(clothing);
    }
    
    /**
     * @return Devulve un ArrayList con todas las prendas en todos los grupos del stock ordenado mediante el id completo.
     */
    public ArrayList<Clothing> listFullStock(){
        return fullStock;
    }
   
    /**
     * No estoy seguro de si deberíamos mantener el arraylist todo el tiempo al mismo tiempo que el hashmap...
     * depende mucho de como sea la implementación en web para no tener que estarlo volviendo a crear todo el rato
     * pero por el momento este metodo lo crea desde cero
     */
    public void createStockList(){
        fullStock.clear();
        stock.values().forEach(clothingGroup -> {
            fullStock.addAll(clothingGroup.getClothingInGroup());
        });
        
        Collections.sort(fullStock);
    }
    
    /**
     * Limpia todos los objetos de prenda que existan en el grupo pero no se deshace del objeto del grupo
     * @param groupId Id del grupo, no la id completa
     * @throws Exceptions.InvalidIdException
     * @throws Exceptions.GroupNotFoundException
     */
    public void clearGroupById(String groupId) throws InvalidIdException, GroupNotFoundException{
        if(groupId.contains("-"))
            throw new InvalidIdException("Id de grupo inválida"); 
        ClothingGroup clothingGroup = stock.get(groupId);
        if(clothingGroup == null)
            throw new GroupNotFoundException("Grupo de ropa no encontrado"); 
        
        clothingGroup.clearGroup();
    }
    
    
    /**
     * Modifica un item que ya exista en algún grupo. Eliminará el item anterior de dicho grupo e insertará el nuevo item 
     * en el grupo correspondiente
     * @param id
     * @param item 
     */
    public void modifyItem(String id, Clothing item){
        
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
        if(groupId.contains("-"))
            throw new InvalidIdException("Id de grupo inválida"); 
        ClothingGroup clothingGroup = stock.get(groupId);
        if(clothingGroup == null)
            throw new GroupNotFoundException("El grupo no fue encontrado");
        
        int objectsToCreate = clothingGroup.getSize();
        clearGroupById(groupId);
        for(int i=0; i<objectsToCreate; i++){
            System.out.println(""); // IGNORAR
        }
    }
     public Clothing searchById(String id) throws ItemNotFoundException {
        for (ClothingGroup group : stock.values()) {
            Clothing item = group.getById(id);
            if (item != null) {
                return item;
            }
        }
        throw new ItemNotFoundException("Prenda con ID " + id + " no encontrada.");
    }
     public void deleteClothingById(String id) throws ItemNotFoundException {
        for (ClothingGroup group : stock.values()) {
            if (group.removeById(id)) {
                return;
            }
        }
        throw new ItemNotFoundException("Prenda con ID " + id + " no encontrada.");
    }
}
