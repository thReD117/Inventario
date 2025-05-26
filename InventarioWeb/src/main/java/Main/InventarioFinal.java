package Main;

import Logic.Stock;
import Domain.Lower;
import Domain.Superior;
import Domain.Accessories;
import Enums.*;
import Exceptions.GroupNotFoundException;
import Exceptions.InvalidIdException;
import Exceptions.ItemNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InventarioFinal {
    public static void main(String[] args) {
        String testId = "6203A11A6403-00002";
        String testId2 = "6203A11A6403-00003";
        String testId3 = "BF2FCEAAE967-00001";
        String groupTestId = "BF2FCEAAE967";
        String groupTestId2 = "176CD6B4ED07";
        
        Stock stock = new Stock();
        stock.add(new Superior("Mediana", "Algodon", "Negro", "Camisa de cuadros", 300f, 1, Season.AUTUMN, Gender.MEN, CategorySup.SHIRTS));
        stock.add(new Lower("Grande", "Mezclilla", "Azul", "Pantalón de Mezclilla", 350f, 1, Season.SPRING, Gender.WOMEN, CategoryLow.JEANS));
        stock.add(new Superior("Mediana", "Algodon", "Negro", "Camisa de cuadros", 300f, 1, Season.AUTUMN, Gender.MEN, CategorySup.SHIRTS));
        stock.add(new Superior("Mediana", "Algodon", "Negro", "Camisa de cuadros", 300f, 1, Season.AUTUMN, Gender.MEN, CategorySup.SHIRTS));
        stock.add(new Superior("Mediana", "Algodon", "Roja", "Camisa de cuadros", 300f, 1, Season.AUTUMN, Gender.MEN, CategorySup.SHIRTS));
        stock.add(new Accessories("6", "Oro", "Dorado", "Anillo sdjls", 700f, 1, Season.AUTUMN, Gender.UNISEX, CategoryAcc.RINGS));
        stock.add(new Lower("Grande", "Mezclilla", "Azul", "Pantalón de Mezclilla", 350f, 1, Season.SPRING, Gender.WOMEN, CategoryLow.JEANS));
        stock.add(new Lower("Grande", "Mezclilla", "Azul", "Pantalón de Mezclilla", 350f, 1, Season.SPRING, Gender.WOMEN, CategoryLow.JEANS));
        
        stock.listFullStock().forEach(System.out::println);
        
        System.out.println("Get");
        try {
            System.out.println(stock.searchById(testId));
        } catch (ItemNotFoundException | InvalidIdException | GroupNotFoundException ex) {
            Logger.getLogger(InventarioFinal.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("Delete 1");
        try {
            stock.deleteClothingById(testId);
        } catch (ItemNotFoundException | InvalidIdException | GroupNotFoundException ex) {
            Logger.getLogger(InventarioFinal.class.getName()).log(Level.SEVERE, null, ex);
        }
        stock.listFullStock().forEach(System.out::println);
        
        System.out.println("Modify");
        try {
            Superior item = new Superior((Superior) stock.searchById(testId2));
            item.setGender(Gender.WOMEN); 
            stock.modifyItem(testId2, item);
        } catch (ItemNotFoundException | InvalidIdException | GroupNotFoundException ex) {
            Logger.getLogger(InventarioFinal.class.getName()).log(Level.SEVERE, null, ex);
        }
        stock.listFullStock().forEach(System.out::println);

        System.out.println("ModifyGroup");
        try {
            Lower item = new Lower((Lower) stock.searchById(testId3));
            item.setColor("White"); 
            stock.modifyGroup(groupTestId, item);
        } catch (ItemNotFoundException | InvalidIdException | GroupNotFoundException ex) {
            Logger.getLogger(InventarioFinal.class.getName()).log(Level.SEVERE, null, ex);
        }
        stock.listFullStock().forEach(System.out::println);
    
        System.out.println("ClearGroup");
        try {
            stock.clearGroupById(groupTestId2);
        } catch (InvalidIdException | GroupNotFoundException ex) {
            Logger.getLogger(InventarioFinal.class.getName()).log(Level.SEVERE, null, ex);
        }
        stock.listFullStock().forEach(System.out::println);
    }
}
