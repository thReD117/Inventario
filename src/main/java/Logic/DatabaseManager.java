package Logic;

import Domain.*;
import java.util.ArrayList;
import Enums.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseManager {
    public static void insertItem(Clothing clothing){
        String query = """
                       INSERT INTO "CLOTHING"("FULL_ID", "GROUP_ID", "INDIVIDUAL_ID", "CATEGORY", "PRICE", "QUANTITY", "SIZE", 
                                            "MATERIAL", "COLOR", "DESCRIPTION", "SEASON", "GENDER")
                       VALUES(?,?,?,?,?,?,?,?,?,?,?, CAST(? AS "GENDER"))
                       ON CONFLICT DO NOTHING
                       """;  // Use "ON CONFLICT DO NOTHING" to skip insertions when values already exist 
        
        try(Connection conn = ConnectionPool.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(query);
            
            pstmt.setString(1, clothing.getId());
            pstmt.setString(2, clothing.getGroupId());
            pstmt.setString(3, clothing.getIndividualId());
            pstmt.setString(4, clothing.getCategory().getEnumName());
            pstmt.setFloat(5, clothing.getPrice());
            pstmt.setInt(6, clothing.getQuantity());
            pstmt.setString(7, clothing.getSize());
            pstmt.setString(8, clothing.getMaterial());
            pstmt.setString(9, clothing.getColor());
            pstmt.setString(10, clothing.getDescription());
            pstmt.setString(11, clothing.getSeason().name());
            pstmt.setString(12, clothing.getGender().name());
            
            pstmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void clearGroup(String groupId){
        String query = """
                       UPDATE "CLOTHING"
                       SET "QUANTITY"=0
                       WHERE "GROUP_ID"=?
                       """;
        try(Connection conn = ConnectionPool.getConnection()){
            PreparedStatement pstmt = conn.prepareStatement(query);
            
            pstmt.setString(1, groupId);
            
            pstmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Does not actually delete, sets the quantity as 0 to mantain the group's persistentId right
     * @param id 
     */
    public static void removeClothingStockById(String id){
        String query = """
                       UPDATE "CLOTHING"
                       SET "QUANTITY"=0
                       WHERE "FULL_ID"=?
                       """;
        try(Connection conn = ConnectionPool.getConnection()){
            PreparedStatement pstmt = conn.prepareStatement(query);
            
            pstmt.setString(1, id);
            
            pstmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Because Stock's modifyItem method first deletes the old item and then adds the new one, it is unnecesary to
     * actually write this method's implementation. Either way I'm leaving it here as a reminder that it was not 
     * implemented by itself but as a side effect of the other methods
     */
    
    /*
    private static void modifyItem(){}
    private static void modifyGroup(){}
    */
    public static void SelectAllClothing(){
        String query = """
                       SELECT * FROM "CLOTHING"
                       """;
        try(Connection conn = ConnectionPool.getConnection()){
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            Stock ST = new Stock();
            while(rs.next()){
                Clothing clothing = null;
                String FID = rs.getString("FULL_ID");
                String GID = rs.getString("GROUP_ID");
                String IID = rs.getString("INDIVIDUAL_ID");
                String CATEGORY = rs.getString("CATEGORY");
                Float PRICE = rs.getFloat("PRICE");
                int QUANTITY = rs.getInt("QUANTITY");
                String SIZE = rs.getString("SIZE");
                String MATERIAL = rs.getString("MATERIAL");
                String COLOR = rs.getString("COLOR");
                String DESCRIPTION = rs.getString("DESCRIPTION");
                Season SEASON = Season.valueOf(rs.getString("SEASON"));
                Gender GENDER = Gender.valueOf(rs.getString("GENDER"));
                String groupPrefix = FID.substring(0, 3);
                switch (groupPrefix) {
                    case "LOW" -> {
                        CategoryLow category = CategoryLow.valueOf(CATEGORY);
                        clothing = new Lower(SIZE, MATERIAL, COLOR, DESCRIPTION, PRICE, QUANTITY, SEASON, GENDER, category);
                    }
                    case "SUP" -> {
                        CategorySup category = CategorySup.valueOf(CATEGORY);
                        clothing = new Superior(SIZE, MATERIAL, COLOR, DESCRIPTION, PRICE, QUANTITY, SEASON, GENDER, category);
                    }
                    case "INT" -> {
                        CategoryInte category = CategoryInte.valueOf(CATEGORY);
                        clothing = new Interior(SIZE, MATERIAL, COLOR, DESCRIPTION, PRICE, QUANTITY, SEASON, GENDER, category);
                    }
                    case "ACC" -> {
                        CategoryAcc category = CategoryAcc.valueOf(CATEGORY);
                        clothing = new Accessories(SIZE, MATERIAL, COLOR, DESCRIPTION, PRICE, QUANTITY, SEASON, GENDER, category);
                    }
                    default -> Logger.getLogger(DatabaseManager.class.getName()).log(Level.WARNING, "Unknown group prefix: " + groupPrefix);
                }
                if (clothing != null) {
                    ST.add(clothing);
                }
            }
        }catch(SQLException ex){
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public Clothing SelectByID(String Full_Id){
        String query ="""
                      SELECT * FROM "CLOTHING"
                      WHERE "FULL_ID = ?"
                      """;
        try(Connection conn = ConnectionPool.getConnection()){
            Clothing clothing = null;
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, Full_Id);
            ResultSet rs = pstmt.executeQuery();
            String GID = rs.getString("GROUP_ID");
            String IID = rs.getString("INDIVIDUAL_ID");
            String CATEGORY = rs.getString("CATEGORY");
            Float PRICE = rs.getFloat("PRICE");
            int QUANTITY = rs.getInt("QUANTITY");
            String SIZE = rs.getString("SIZE");
            String MATERIAL = rs.getString("MATERIAL");
            String COLOR = rs.getString("COLOR");
            String DESCRIPTION = rs.getString("DESCRIPTION");
            Season SEASON = Season.valueOf(rs.getString("SEASON"));
            Gender GENDER = Gender.valueOf(rs.getString("GENDER"));
            switch(Full_Id.substring(0,3)){
                case "LOW" ->{
                    CategoryLow category = CategoryLow.valueOf(CATEGORY);
                    clothing = new Lower(SIZE, MATERIAL, COLOR, DESCRIPTION, PRICE, QUANTITY, SEASON, GENDER, category);
                }
                case "SUP"->{
                    CategorySup category = CategorySup.valueOf(CATEGORY);
                    clothing = new Superior(SIZE, MATERIAL, COLOR, DESCRIPTION, PRICE, QUANTITY, SEASON, GENDER, category);
                }
                case "INT"->{
                    CategoryInte category = CategoryInte.valueOf(CATEGORY);
                    clothing = new Interior(SIZE, MATERIAL, COLOR, DESCRIPTION, PRICE, QUANTITY, SEASON, GENDER, category);
                }
                case "ACC"->{
                    CategoryAcc category = CategoryAcc.valueOf(CATEGORY);
                    clothing = new Accessories(SIZE, MATERIAL, COLOR, DESCRIPTION, PRICE, QUANTITY, SEASON, GENDER, category);
                }
            }
            return clothing;
        }
        catch(SQLException ex){
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
