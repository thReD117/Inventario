package Logic;

import Domain.Clothing;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseManager {
    public static void insertItem(Clothing clothing){
        String query = """
                       INSERT INTO "CLOTHING"("FULL_ID", "GROUP_ID", "INDIVIDUAL_ID", "CATEGORY", "PRICE", "QUANTITY", "SIZE", 
                                            "MATERIAL", "COLOR", "DESCRIPTION", "SEASON", "GENDER")
                       VALUES(?,?,?,?,?,?,?,?,?,?,?, CAST(? AS "GENDER"))
                       """;
        
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
}
