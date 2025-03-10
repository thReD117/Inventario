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
        createStockList();
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
        ClothingGroup clothingGroup = getClothingGroupByGroupId(groupId);
        clothingGroup.clearGroup();
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
}
