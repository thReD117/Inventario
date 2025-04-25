package Testing;

import Domain.*;
import Enums.*;
import Exceptions.GroupNotFoundException;
import Exceptions.InvalidIdException;
import Exceptions.ItemNotFoundException;
import Logic.DatabaseManager;
import Logic.Stock;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {
    public static void main(String[] args) {
//        createNewTable();
//        insertIntoClothing("6203A11A6403", "00001", CategorySup.SHIRTS);
//        insertIntoClothing("6203A11ADF11", "00001", CategorySup.BLOUSES);
//        
//        
//        selectAllFromClothing().forEach(System.out::println);

        Stock stock = new Stock();
        stock.add(new Accessories("10", "Oro", "Dorado", "Anillo de 14K", 5000, 3, Season.NA, Gender.WOMEN, CategoryAcc.RINGS));
        stock.add(new Lower("M", "Algodón", "Negro", "Falda de tablas", 250, 2, Season.AUTUMN, Gender.WOMEN, CategoryLow.SKIRTS));    
    }
    
    /*
    public static void createNewTable(){
        // SQL query
        String query = """
                       CREATE TABLE IF NOT EXISTS CLOTHING(
                            GROUP_ID VARCHAR(50),
                            SINGULAR_ID VARCHAR(30),
                            CATEGORY VARCHAR(50)
                       )
                       """;

        // Connect to database
        try (Connection conn = DriverManager.getConnection(url, usr, pwd)){
            Statement st = conn.createStatement(); // Execute query
            st.execute(query);
            
            System.out.println("Tabla creada");
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    
    public static void insertIntoClothing(String groupId, String singularId, CategoryEnumerable category){
        // Different query structure when using variables
        String query = """
                       INSERT INTO CLOTHING(GROUP_ID, SINGULAR_ID, CATEGORY)
                       VALUES (?,?,?)
                       """;
        
        try (Connection conn = DriverManager.getConnection(url, usr, pwd)) {
            // Prepared statement helps to replace variables in the query
            PreparedStatement prepSt = conn.prepareStatement(query);
            
            // Replace variables into query
            prepSt.setString(1, groupId);
            prepSt.setString(2, singularId);
            prepSt.setString(3, category.getDisplayName());
            
            prepSt.execute();
            
            System.out.println("Datos insertados");
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    
    // Por el momento devuelve un aL de string pero aquí se devolveria el objeto o se añadirían al stock u otra mierda
    public static ArrayList<String> selectAllFromClothing(){
        String query = "SELECT * FROM CLOTHING";        

        ArrayList<String> rows = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, usr, pwd)) {
            Statement st = conn.createStatement();
            
            // Stores all the data retrieved by the query
            ResultSet rs = st.executeQuery(query);
            
            // It has to iterate through the set in order to retrieve the info
            while(rs.next()){
                rows.add(String.format("%s - %s | %s", 
                        rs.getString("GROUP_ID"),
                        rs.getString("SINGULAR_ID"),
                        rs.getString("CATEGORY")
                ));
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return rows;
    }
*/
}
