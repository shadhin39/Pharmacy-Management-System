
package db_pharmacymanagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author Fardin
 */
public class Execute {
    
   public static ResultSet selectquery(String query) {
        ResultSet rs=null;
        try {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url="jdbc:sqlserver://localhost:1433;databaseName=PMS;user=sa;password=123456";
            Connection con = DriverManager.getConnection(url);
           
            PreparedStatement pst = con.prepareStatement(query);
            
             rs = pst.executeQuery();
            
        }catch(Exception e) {
            JOptionPane.showMessageDialog(null, "SELECT QUERY error");
        }
        return rs;
    }
    public static void updatequery(String query) {
        try {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url="jdbc:sqlserver://localhost:1433;databaseName=PMS;user=sa;password=123456";
            Connection con = DriverManager.getConnection(url);
           
            PreparedStatement pst = con.prepareStatement(query);
            
             pst.executeUpdate();
             JOptionPane.showMessageDialog(null, "Updated successfully");
            
        }catch(Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public static void insertquery(String query){
        try {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url="jdbc:sqlserver://localhost:1433;databaseName=PMS;user=sa;password=123456";
            Connection con = DriverManager.getConnection(url);
           
            PreparedStatement pst = con.prepareStatement(query);
            
             pst.executeUpdate();
             JOptionPane.showMessageDialog(null, "Inserted successfully");
            
        }catch(Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    public static void deletequery(String query){
        try {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url="jdbc:sqlserver://localhost:1433;databaseName=PMS;user=sa;password=123456";
            Connection con = DriverManager.getConnection(url);
           
            PreparedStatement pst = con.prepareStatement(query);
            
             pst.executeUpdate();
             JOptionPane.showMessageDialog(null, "Deleted successfully");
            
        }catch(Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
}
