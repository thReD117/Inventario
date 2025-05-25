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
                                            "MATERIAL", "COLOR", "DESCRIPTION", "SEASON", "GENDER", "CLOTHING_TYPE")
                       VALUES(?,?,?,?,?,?,?,?,?,?,?, CAST(? AS "GENDER"), CAST(? AS "CLOTHING_TYPE"))
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
            String clothingType = "";
            if(clothing instanceof Superior)
                clothingType="SUPERIOR";
            else if(clothing instanceof Lower)
                clothingType="LOWER";
            else if(clothing instanceof Interior)
                clothingType="INTERIOR";
            else 
                clothingType="ACCESSORIES";
            pstmt.setString(13, clothingType);
            
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
     * @return 
     */
    
    /*
    private static void modifyItem(){}
    private static void modifyGroup(){}
    */
    public static ArrayList<Clothing> SelectAllClothing(){
        ArrayList<Clothing> stock = new ArrayList<>();
        String query = """
                       SELECT * FROM "CLOTHING"
                       """;
        try(Connection conn = ConnectionPool.getConnection()){
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next())
                stock.add(createClothing(rs));
        }catch(SQLException ex){
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return stock;
    }
    
    
    public static Clothing SelectByID(String Full_Id){
        String query ="""
                      SELECT * FROM "CLOTHING"
                      WHERE "FULL_ID" = ?
                      """;
        try(Connection conn = ConnectionPool.getConnection()){
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, Full_Id);
            ResultSet rs = pstmt.executeQuery();
            return createClothing(rs);
        }
        catch(SQLException ex){
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
    public static ArrayList<Clothing> SelectByGroupID(String groupId) {
        ArrayList<Clothing> stock = new ArrayList<>();
        String query = """
                       SELECT * FROM "CLOTHING"
                       WHERE "GROUP_ID" = ?
                       """;
        try(Connection conn = ConnectionPool.getConnection()){
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, groupId);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next())
                stock.add(createClothing(rs));
            
        }
        catch(SQLException ex){
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return stock;
    }
    
    
    public static ArrayList<Clothing> SelectByCategory (String CATEGORY1){
        ArrayList<Clothing> stock = new ArrayList<>();
        String query = """
                       SELECT * FROM "CLOTHING"
                       WHERE "CATEGORY" = ?
                       """;
        try(Connection conn = ConnectionPool.getConnection()){
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, CATEGORY1);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next())
                stock.add(createClothing(rs));
        }
        catch(SQLException ex){
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return stock;
    }
    
    
    private static Clothing createClothing(ResultSet rs){
        Clothing clothing = null;
        try {
            String FID = rs.getString("FULL_ID");
            String CATEGORY = rs.getString("CATEGORY");
            Float PRICE = rs.getFloat("PRICE");
            int QUANTITY = rs.getInt("QUANTITY");
            String SIZE = rs.getString("SIZE");
            String MATERIAL = rs.getString("MATERIAL");
            String COLOR = rs.getString("COLOR");
            String DESCRIPTION = rs.getString("DESCRIPTION");
            Season SEASON = Season.valueOf(rs.getString("SEASON"));
            Gender GENDER = Gender.valueOf(rs.getString("GENDER"));
            String CATEGORY_TYPE = rs.getString("CLOTHING_TYPE");
            switch (CATEGORY_TYPE) {
                case "LOWER" -> {
                    CategoryLow category = CategoryLow.valueOf(CATEGORY);
                    clothing = new Lower(SIZE, MATERIAL, COLOR, DESCRIPTION, PRICE, QUANTITY, SEASON, GENDER, category);
                }
                case "SUPERIOR" -> {
                    CategorySup category = CategorySup.valueOf(CATEGORY);
                    clothing = new Superior(SIZE, MATERIAL, COLOR, DESCRIPTION, PRICE, QUANTITY, SEASON, GENDER, category);
                }
                case "INTERIOR" -> {
                    CategoryInte category = CategoryInte.valueOf(CATEGORY);
                    clothing = new Interior(SIZE, MATERIAL, COLOR, DESCRIPTION, PRICE, QUANTITY, SEASON, GENDER, category);
                }
                case "ACCESSORIES" -> {
                    CategoryAcc category = CategoryAcc.valueOf(CATEGORY);
                    clothing = new Accessories(SIZE, MATERIAL, COLOR, DESCRIPTION, PRICE, QUANTITY, SEASON, GENDER, category);
                }
                default -> Logger.getLogger(DatabaseManager.class.getName()).log(Level.WARNING, "Unknown group prefix: ");
            }
            if(clothing != null)
                clothing.setId(FID);
            
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return clothing;
    }
}
