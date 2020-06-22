
package db_pharmacymanagement;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class Dashboard extends javax.swing.JFrame {
    String role,Frame = "D",UserId = "101";
    int NoOfGeneric=0,NoOfCompany=0,NoOfMedicine=0;
    String currentdate,next7date;
    Object LatestPid;
    Object[] genericName = new Object[200];
    Object[] genericId = new Object[200];
    Object[] Cid = new Object[200];
    Object[] CompanyName = new Object[200];
    Object[] MedicineName = new Object[1000];
    Object[] Mid = new Object[1000];
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat datetimeformat = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
    Date date = new Date();
    
    public Dashboard(String userid) {
        initComponents();
        this.UserId = userid;
        //jTextField1.setVisible(false);
        // Initialize Date
        currentDateTime_jLabel.setText(sdf.format(date)); 
        // initialize dashboard components
        SetNumOfUser();
        SetNumOfMedicine();
        SetNumOfSell();
        SetOutOfMedicine();
        SetNoExpires();
        setLastOnlineStatus();
        Dashboard_jLabel.setForeground(new Color(97,212,195));
        // Set all tables ready
        showUserDetails("","");
        ShowMedicineDetails("","");
        ShowInventory("","");
        show_purchase("","");
        show_Selling("","");
        showCompany("","");
        showGeneric("","");
        
    }
    public void UserInfo(String UserID,String UserName,String Role){
        UserId = UserID;
        UserName_jLabel.setText(UserName);
        Role_jLabel.setText(Role);
    }
    
    public void SetNumOfUser(){
        String Countnum="";
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url="jdbc:sqlserver://localhost:1433;databaseName=PMS;user=sa;password=123456";
            Connection con = DriverManager.getConnection(url);
            String query1 = "SELECT Count(Uid) FROM SystemUser";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query1);
            while(rs.next()){
                Countnum = rs.getString(ICONIFIED);
            }
            NoOfUsers_jLabel.setText(Countnum);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
            
        }
        
    }
    public void SetNumOfMedicine(){
        String Countnum="";
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url="jdbc:sqlserver://localhost:1433;databaseName=PMS;user=sa;password=123456";
            Connection con = DriverManager.getConnection(url);
            String query1 = "SELECT Count(Mid) FROM Medicine";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query1);
            while(rs.next()){
                Countnum = rs.getString(ICONIFIED);
            }
            NoOfMedicines_jLabel.setText(Countnum);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
            
        }
        
    }
    public void SetNumOfSell(){
        String Countnum="";
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url="jdbc:sqlserver://localhost:1433;databaseName=PMS;user=sa;password=123456";
            Connection con = DriverManager.getConnection(url);
            String query1 = "SELECT Count(Sid) FROM SellingDetails";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query1);
            while(rs.next()){
                Countnum = rs.getString(ICONIFIED);
            }
            NoOfSales_jLabel.setText(Countnum);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
            
        }
        
    }
    public void setLastOnlineStatus(){
        String currentdatetime = datetimeformat.format(date);
        System.out.println("Current Time : "+currentdatetime);
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url="jdbc:sqlserver://localhost:1433;databaseName=PMS;user=sa;password=123456";
            Connection con = DriverManager.getConnection(url);
            String query1 = " UPDATE SystemUser SET LastOnlineStatus = '"+currentdatetime+"' WHERE Uid = "+UserId;
            PreparedStatement pst = con.prepareStatement(query1);
            pst.executeUpdate();
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    public void SetSearchItem(String frame){
        if(frame == "D"){       // dashboard
            SearchItem_Combobox.removeAllItems();
            
        }else if(frame == "M"){     // medicine
            SearchItem_Combobox.removeAllItems();
            SearchItem_Combobox.addItem("MedicineName");
            SearchItem_Combobox.addItem("Category");
            SearchItem_Combobox.addItem("MedicineType");
            SearchItem_Combobox.addItem("CompanyName");
            
        }else if(frame == "P"){     // purchase
            SearchItem_Combobox.removeAllItems();
            SearchItem_Combobox.addItem("MedicineName");
            SearchItem_Combobox.addItem("Category");
            SearchItem_Combobox.addItem("MedicineType");
            SearchItem_Combobox.addItem("CompanyName");
            
        }else if(frame == "I"){     // inventory
            SearchItem_Combobox.removeAllItems();
            SearchItem_Combobox.addItem("MedicineName");
            SearchItem_Combobox.addItem("MedicineType");
            SearchItem_Combobox.addItem("Category");
            
        }else if(frame == "SD"){       // selling details
            SearchItem_Combobox.removeAllItems();
            SearchItem_Combobox.addItem("CustomerName");
            SearchItem_Combobox.addItem("SalesmanName");
            SearchItem_Combobox.addItem("SellingDate");
            
        }else if(frame == "UD"){    // user details
            SearchItem_Combobox.removeAllItems();
            SearchItem_Combobox.addItem("UserName");
            SearchItem_Combobox.addItem("Role");
            SearchItem_Combobox.addItem("Address");
            SearchItem_Combobox.addItem("Phone");
        }else if(frame == "SU"){    //sign up
            SearchItem_Combobox.removeAllItems();
            
        }else if(frame == "C"){     // company
            SearchItem_Combobox.removeAllItems();
            SearchItem_Combobox.addItem("CompanyName");
            SearchItem_Combobox.addItem("Address");
            
        }else if(frame == "G"){     // generic
            SearchItem_Combobox.removeAllItems();
            SearchItem_Combobox.addItem("Category");
            SearchItem_Combobox.addItem("Uses");
            
        }else{
            SearchItem_Combobox.removeAllItems();
            
        }
        
    }
    
    public void SetOutOfMedicine()
    {
        String Countnum="";
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url="jdbc:sqlserver://localhost:1433;databaseName=PMS;user=sa;password=123456";
            Connection con = DriverManager.getConnection(url);
            String query1 = "  SELECT COUNT(IId) FROM Inventory WHERE Stock = 0";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query1);
            while(rs.next()){
                Countnum = rs.getString(ICONIFIED);
            }
            NoOfOutOfStockMedicine_jlabel.setText(Countnum);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
            
        }
        
    }
    
    public void SetNoExpires(){
        currentdate = dateformat.format(date);
        next7date="";
        String noOfAlertMed = "";
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url="jdbc:sqlserver://localhost:1433;databaseName=PMS;user=sa;password=123456";
            Connection con = DriverManager.getConnection(url);
            String query1 = "SELECT DATEADD(DAY,7,'"+currentdate+"')";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query1);
            while(rs.next()){
                next7date = rs.getString(ICONIFIED);
            }
            con.close();
            System.out.println("next7date : "+next7date);
            String query2 = "SELECT COUNT(IId) FROM Inventory WHERE ExpireDate\n" +
"between '"+currentdate+"' and '"+next7date+"'";
            Connection con1 = DriverManager.getConnection(url);
            Statement st1 = con1.createStatement();
            ResultSet rs1 = st1.executeQuery(query2);
            while(rs1.next()){
                noOfAlertMed = rs1.getString(ICONIFIED);
            }
            con1.close();
            System.out.println("NoOfAlertMed : "+noOfAlertMed);
            NoExpiresMed_label.setText(noOfAlertMed);
            
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
            
        }
    }
    
    
    public void showUserDetails(String searchkey,String searchCol){
        ArrayList<UserDetails> userList = new ArrayList<>();
        
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url="jdbc:sqlserver://localhost:1433;databaseName=PMS;user=sa;password=123456";
            Connection con = DriverManager.getConnection(url);
            String query1 = "SELECT * FROM SystemUser";
            String query2 = "SELECT * FROM SystemUser WHERE "+searchCol+" LIKE '%"+searchkey + "%'";
            Statement st = con.createStatement();
            ResultSet rs;
            if(searchkey.isEmpty() || searchCol.isEmpty()){
                rs = st.executeQuery(query1);
            }else{
                rs = st.executeQuery(query2);
            }

            UserDetails userDetails;
            while(rs.next()){
                userDetails = new UserDetails(rs.getInt("Uid"),rs.getString("UserName"),rs.getString("userPassword"),rs.getString("Role"),
                            rs.getString("Phone"),rs.getString("Address"),rs.getString("LastOnlineStatus"));
                userList.add(userDetails);
            }
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
            
        }
        // show data in table
        DefaultTableModel model =(DefaultTableModel) UserDetails_Table.getModel();
        int rowCount = model.getRowCount();
        //Remove rows one by one from the end of the table
        for (int i = rowCount - 1; i >= 0; i--) {
            model.removeRow(i);
        }
        
        Object[] row  = new Object[7];
        for(int i=0; i<userList.size(); i++){
            row[0] = userList.get(i).getUid();
            row[1] = userList.get(i).getUserName();
            row[2] = userList.get(i).getuserPassword();
            row[3] = userList.get(i).getRole();
            row[4] = userList.get(i).getPhone();
            row[5] = userList.get(i).getAddress();
            row[6] = userList.get(i).getLastOnlineStatus();
            model.addRow(row);
            
        }
        userList.clear();
    }
    public void ShowMedicineDetails(String searchkey,String searchCol){
        ArrayList<Medicine> medicine = new ArrayList<>();
        try{
            // fetch data from database
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url="jdbc:sqlserver://localhost:1433;databaseName=PMS;user=sa;password=123456";
            Connection con = DriverManager.getConnection(url);
            String query1 = "SELECT Medicine.Mid,Medicine.MedicineName,Medicine.Amount,Generic.Category,Medicine.MedicineType,Company.CompanyName FROM Medicine INNER JOIN Generic on Medicine.Gid = Generic.GId INNER JOIN Company ON Company.Cid = Medicine.Cid";
            String query2 = "SELECT Medicine.Mid,Medicine.MedicineName,Medicine.Amount,Generic.Category,Medicine.MedicineType,Company.CompanyName\n" +
" FROM Medicine INNER JOIN Generic on Medicine.Gid = Generic.GId INNER JOIN Company ON Company.Cid = Medicine.Cid\n" +
" WHERE "+ searchCol+" LIKE '%"+searchkey+"%'";
            Statement st = con.createStatement();
            ResultSet rs;
            if(searchkey.isEmpty() || searchCol.isEmpty()){
                rs = st.executeQuery(query1);
            }else{
                rs = st.executeQuery(query2);
            }
            Medicine med;
            while(rs.next()){
                med = new Medicine(rs.getInt("Mid"),rs.getString("MedicineName"),rs.getString("Amount"),rs.getString("Category"),
                        rs.getString("MedicineType"),rs.getString("CompanyName"));
                medicine.add(med);
            }
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
            
        }
        
        
        // Show data in the table 
        DefaultTableModel medicineModel = (DefaultTableModel) MedicineDetails_table.getModel();
        int rowCount = medicineModel.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            medicineModel.removeRow(i);
        }
        NoOfMedicine = 0;
        Object[] row  = new Object[6];
        for(int i=0; i<medicine.size(); i++){
            row[0] = medicine.get(i).getMid();              Mid[i] = medicine.get(i).getMid();
            row[1] = medicine.get(i).getMedicineName();     MedicineName[i] = medicine.get(i).getMedicineName();
            row[2] = medicine.get(i).getAmount();
            row[3] = medicine.get(i).getCategory();
            row[4] = medicine.get(i).getMedicineType();
            row[5] = medicine.get(i).getCompanyName();
            medicineModel.addRow(row);
            NoOfMedicine++;
            
        }
        medicine.clear();
        
    }
    public void ShowInventory(String searchkey,String searchCol){
        ArrayList<Inventory> inventory = new ArrayList<>();
        
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url="jdbc:sqlserver://localhost:1433;databaseName=PMS;user=sa;password=123456";
            Connection con = DriverManager.getConnection(url);
            String query1 = "SELECT Inventory.IId,Medicine.MedicineName,Medicine.MedicineType,Generic.Category,Medicine.Amount,Inventory.Stock,Purchase.SellingPrice,Inventory.ExpireDate FROM Inventory INNER JOIN Medicine on Medicine.Mid = Inventory.Mid LEFT JOIN Purchase ON Purchase.Pid = Inventory.Pid INNER JOIN Generic ON Generic.Gid = Medicine.Gid";
            String query2 = "SELECT Inventory.IId,Medicine.MedicineName,Medicine.MedicineType,Generic.Category,Medicine.Amount,Inventory.Stock,Purchase.SellingPrice,Inventory.ExpireDate FROM Inventory INNER JOIN Medicine on Medicine.Mid = Inventory.Mid LEFT JOIN Purchase ON Purchase.Pid = Inventory.Pid INNER JOIN Generic ON Generic.Gid = Medicine.Gid WHERE "+searchCol+" LIKE '%"+searchkey + "%' ORDER BY ExpireDate";
            Statement st = con.createStatement();
            ResultSet rs;
            if(searchkey.isEmpty() || searchCol.isEmpty()){
                rs = st.executeQuery(query1);
            }else{
                rs = st.executeQuery(query2);
            }
            Inventory inven;
            while(rs.next()){
                inven = new Inventory(rs.getInt("IId"),rs.getString("MedicineName"),rs.getString("MedicineType"),rs.getString("Category"),rs.getString("Amount"),
                            rs.getInt("Stock"),rs.getDouble("SellingPrice"),rs.getString("ExpireDate"));
                inventory.add(inven);
            }
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
            
        }
        
        
        // show data in database
        DefaultTableModel inventoryModel = (DefaultTableModel) Inventory_Table.getModel();
        int rowCount = inventoryModel.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            inventoryModel.removeRow(i);
        }
        
        Object[] row  = new Object[8];
        for(int i=0; i<inventory.size(); i++){
            row[0] = inventory.get(i).getIId();
            row[1] = inventory.get(i).getMedicineName();
            row[2] = inventory.get(i).getMedicineType();
            row[3] = inventory.get(i).getCategory();
            row[4] = inventory.get(i).getAmount();
            row[5] = inventory.get(i).getStock();
            row[6] = inventory.get(i).getSellingPrice();
            row[7] = inventory.get(i).getExpireDate();
            inventoryModel.addRow(row);
            
        }
        inventory.clear();
        
    }
    public void show_purchase(String searchkey,String searchCol){
        
        ArrayList<Purchase> purchaseList = new ArrayList<>();
         try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url="jdbc:sqlserver://localhost:1433;databaseName=PMS;user=sa;password=123456";
            Connection con = DriverManager.getConnection(url);
            String query1="SELECT Purchase.Pid,Medicine.MedicineName,Generic.Category,Company.CompanyName,Medicine.MedicineType,Purchase.Quantity,Purchase.SellingPrice,Purchase.CostPrice FROM Purchase INNER JOIN Medicine ON Medicine.Mid = Purchase.Mid INNER JOIN Company ON Company.Cid = Medicine.Cid INNER JOIN Generic ON Generic.GId = Medicine.Gid";
            String query2="SELECT Purchase.Pid,Medicine.MedicineName,Generic.Category,Company.CompanyName,Medicine.MedicineType,Purchase.Quantity,Purchase.SellingPrice,Purchase.CostPrice FROM Purchase INNER JOIN Medicine ON Medicine.Mid = Purchase.Mid INNER JOIN Company ON Company.Cid = Medicine.Cid INNER JOIN Generic ON Generic.GId = Medicine.Gid WHERE "+searchCol+" LIKE '%"+searchkey + "%' ";
            Statement st= con.createStatement();
            ResultSet rs;
            if(searchkey.isEmpty() || searchCol.isEmpty()){
                rs = st.executeQuery(query1);
            }else{
                rs = st.executeQuery(query2);
            }
            Purchase pur;
            while(rs.next()){
                pur=new Purchase(rs.getInt("Pid"), rs.getString("MedicineName"), rs.getString("Category"), rs.getString("CompanyName"), rs.getString("MedicineType"),rs.getInt("Quantity"),rs.getDouble("SellingPrice"),rs.getDouble("CostPrice"));
                purchaseList.add(pur);
            }
         }
         catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        
        
        DefaultTableModel model = (DefaultTableModel)purchase_Table.getModel();
        int rowCount = model.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            model.removeRow(i);
        }
        Object[] row = new Object[8];
        for(int i=0;i<purchaseList.size();i++){
            row[0] = purchaseList.get(i).getPid();            LatestPid = purchaseList.get(i).getPid();
            row[1] = purchaseList.get(i).getMedicineName();
            row[2] = purchaseList.get(i).getCategory();
            row[3] = purchaseList.get(i).getCompanyName();
            row[4] = purchaseList.get(i).getMedicineType();
            row[5] = purchaseList.get(i).getQuantity();
            row[6] = purchaseList.get(i).getSellingPrice();
            row[7] = purchaseList.get(i).getCostPrice();
            

            model.addRow(row);
        }
        purchaseList.clear();
    }
    public void show_Selling(String searchkey,String searchCol){
        if(searchCol == "SalesmanName") searchCol = "UserName";
        ArrayList<SellingDetails> sellingDetailsList = new ArrayList<>();
         try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url="jdbc:sqlserver://localhost:1433;databaseName=PMS;user=sa;password=123456";
            Connection con = DriverManager.getConnection(url);
            String query1="select SellingDetails.SId,SystemUser.UserName,CustomerName,SellingDate,TotalPrice FROM SellingDetails  inner join SystemUser on SystemUser.Uid = SellingDetails.Uid";
            String query2="select SellingDetails.SId,SystemUser.UserName,CustomerName,SellingDate,TotalPrice FROM SellingDetails  inner join SystemUser on SystemUser.Uid = SellingDetails.Uid WHERE "+searchCol+" LIKE '%"+searchkey + "%' ORDER BY SellingDate DESC";
            Statement st= con.createStatement();
            ResultSet rs;
            if(searchkey.isEmpty() || searchCol.isEmpty()){
                rs = st.executeQuery(query1);
            }else{
                rs = st.executeQuery(query2);
            }
            SellingDetails sell;
            while(rs.next()){
                sell=new SellingDetails(rs.getInt("Sid"), rs.getString("UserName"), rs.getString("CustomerName"), rs.getString("SellingDate"), rs.getDouble("TotalPrice"));
                sellingDetailsList.add(sell);
            }
         }
         catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        
        
        
        DefaultTableModel model = (DefaultTableModel)SellingDetails_jTable.getModel();
        Object[] row = new Object[5];
        int rowCount = model.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            model.removeRow(i);
        }
        for(int i=0;i<sellingDetailsList.size();i++){
            row[0]=sellingDetailsList.get(i).getSid();
            row[1] = sellingDetailsList.get(i).getUserName();
            row[2] = sellingDetailsList.get(i).getCustomerName();
            row[3] = sellingDetailsList.get(i).getSellingDate();
            row[4] = sellingDetailsList.get(i).getTotalPrice();
            

            model.addRow(row);
        }
        sellingDetailsList.clear();
    } 
    public void showCompany(String searchkey,String searchCol){
        ArrayList<Company> company = new ArrayList<>();
        
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url="jdbc:sqlserver://localhost:1433;databaseName=PMS;user=sa;password=123456";
            Connection con = DriverManager.getConnection(url);
            String query1 = "SELECT * FROM Company";
            String query2 = "SELECT * FROM Company WHERE "+ searchCol+" LIKE '%"+searchkey+"%'";
            Statement st = con.createStatement();
            ResultSet rs;
            if(searchkey.isEmpty() || searchCol.isEmpty()){
                rs = st.executeQuery(query1);
            }else{
                rs = st.executeQuery(query2);
            }
            Company comp;
            while(rs.next()){
                comp = new Company(rs.getInt("Cid"),rs.getString("CompanyName"),rs.getString("Address"),rs.getString("ContactNumber"));
                company.add(comp);
            }
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
            
        }
        // show data in table
        
        DefaultTableModel companyModel = (DefaultTableModel) jTable3.getModel();
        int rowCount = companyModel.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            companyModel.removeRow(i);
        }
        NoOfCompany = 0;
        Object[] row  = new Object[6];
        for(int i=0; i<company.size(); i++){
            row[0] = company.get(i).getCid();            Cid[i] = company.get(i).getCid(); 
            row[1] = company.get(i).getCompanyName();    CompanyName[i] = company.get(i).getCompanyName();
            row[2] = company.get(i).getAddress();
            row[3] = company.get(i).getContactNumber();
            companyModel.addRow(row);
            NoOfCompany++;
            
        }
        company.clear();
        
    }
    public void showGeneric(String searchkey,String searchCol){
        ArrayList<Generic> generic = new ArrayList<>();
        
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url="jdbc:sqlserver://localhost:1433;databaseName=PMS;user=sa;password=123456";
            Connection con = DriverManager.getConnection(url);
            String query1 = "SELECT * FROM Generic";
            String query2 = "SELECT * FROM Generic WHERE "+ searchCol+" LIKE '%"+searchkey+"%'";
            Statement st = con.createStatement();
            ResultSet rs;
            if(searchkey.isEmpty() || searchCol.isEmpty()){
                rs = st.executeQuery(query1);
            }else{
                rs = st.executeQuery(query2);
            }
            Generic gen;
            while(rs.next()){
                gen = new Generic(rs.getInt("Gid"),rs.getString("Category"),rs.getString("Uses"));
                generic.add(gen);
            }
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
            
        }
        
        
        // show data to the table
        DefaultTableModel genericModel = (DefaultTableModel) jTable4.getModel();
        int rowCount = genericModel.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            genericModel.removeRow(i);
        }
        NoOfGeneric = 0;
        Object[] row  = new Object[6];
        for(int i=0; i<generic.size(); i++){
            row[0] = generic.get(i).getGid();              genericId[i] = generic.get(i).getGid();
            row[1] = generic.get(i).getCategory();         genericName[i] = generic.get(i).getCategory();
            row[2] = generic.get(i).getUses();
            genericModel.addRow(row);
            NoOfGeneric++;
        }
        generic.clear();
        
    }
    
    public void showItemDetails(String searchkey,String searchCol,String Sid){
        ArrayList<ItemDetails> itemDetailsList = new ArrayList<>();
         try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url="jdbc:sqlserver://localhost:1433;databaseName=PMS;user=sa;password=123456";
            Connection con = DriverManager.getConnection(url);
            String query1="SELECT MedicineName,MedicineType,Quantity,RetailPrice,TotalPrice FROM Records INNER JOIN Medicine\n" +
"ON Medicine.Mid = Records.Mid WHERE Sid = "+Sid;
            String query2="SELECT MedicineName,MedicineType,Quantity,RetailPrice,TotalPrice FROM Records INNER JOIN Medicine\n" +
"ON Medicine.Mid = Records.Mid WHERE Sid = "+Sid+" and "+searchCol+" LIKE '%"+searchkey + "%'";
            Statement st= con.createStatement();
            ResultSet rs;
            if(searchkey.isEmpty() || searchCol.isEmpty()){
                rs = st.executeQuery(query1);
            }else{
                rs = st.executeQuery(query2);
            }
            ItemDetails item;
            while(rs.next()){
                item = new ItemDetails(rs.getString("MedicineName"),rs.getString("MedicineType"),rs.getInt("Quantity"),rs.getDouble("RetailPrice"),rs.getDouble("TotalPrice"));
                itemDetailsList.add(item);
            }
         }
         catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        
        
        
        DefaultTableModel model = (DefaultTableModel)ItemDetails_jTable.getModel();
        Object[] row = new Object[5];
        int rowCount = model.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            model.removeRow(i);
        }
        for(int i=0;i<itemDetailsList.size();i++){
            row[0] = itemDetailsList.get(i).getMedicineName();
            row[1] = itemDetailsList.get(i).getMedicineType();
            row[2] = itemDetailsList.get(i).getQuantity();
            row[3] = itemDetailsList.get(i).getRetailPrice();
            row[4] = itemDetailsList.get(i).getTotalPrice();
            

            model.addRow(row);
        }
        itemDetailsList.clear();
    }
    
    

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        SignOut_jButton = new javax.swing.JButton();
        Search_textField = new javax.swing.JTextField();
        Search_Button = new javax.swing.JButton();
        UserName_jLabel = new javax.swing.JLabel();
        Role_jLabel = new javax.swing.JLabel();
        SearchItem_Combobox = new javax.swing.JComboBox<>();
        jLabel34 = new javax.swing.JLabel();
        currentDateTime_jLabel = new javax.swing.JLabel();
        Dashboard_jLabel = new javax.swing.JLabel();
        MediicineDetails_jLabel = new javax.swing.JLabel();
        UserDetails_jLabel = new javax.swing.JLabel();
        SellingDetails_jLabel = new javax.swing.JLabel();
        purchase_jLabel = new javax.swing.JLabel();
        Signup_jLabel = new javax.swing.JLabel();
        Generic_jLabel = new javax.swing.JLabel();
        Inventory_jLabel = new javax.swing.JLabel();
        Compnay_jLabel = new javax.swing.JLabel();
        Main_panel = new javax.swing.JPanel();
        Dashboard_Panel = new javax.swing.JPanel();
        MedicineShow_jPanel = new javax.swing.JPanel();
        NoOfMedicines_jLabel = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        systemusershow_panel = new javax.swing.JPanel();
        NoOfUsers_jLabel = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        TotalSalesShow_jPanel = new javax.swing.JPanel();
        NoOfSales_jLabel = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        OutOfMedicineShow_Panel = new javax.swing.JPanel();
        NoOfOutOfStockMedicine_jlabel = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        jPanel23 = new javax.swing.JPanel();
        OutOfExpireMedicineShow_Panel = new javax.swing.JPanel();
        NoExpiresMed_label = new javax.swing.JLabel();
        NoExpiresMedshow_jlabel = new javax.swing.JLabel();
        SignUp_panel = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        register_Button = new javax.swing.JButton();
        delete_Button = new javax.swing.JButton();
        phone_TextField = new javax.swing.JTextField();
        address_TextField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        Role_jComboBox = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        username_update_TextField = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        confirmPW_PasswordField = new javax.swing.JPasswordField();
        PW_PasswordField = new javax.swing.JPasswordField();
        jLabel9 = new javax.swing.JLabel();
        MedicineDetails_panel = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        MedicineDetails_table = new javax.swing.JTable();
        jPanel13 = new javax.swing.JPanel();
        AddMedicine_Button = new javax.swing.JButton();
        DeleteMedicine_Button = new javax.swing.JButton();
        UpdateMedicine_Button = new javax.swing.JButton();
        Purchase_Panel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        purchase_Table = new javax.swing.JTable();
        jPanel12 = new javax.swing.JPanel();
        PurchaseMedicine_Button = new javax.swing.JButton();
        UserDetails_Panel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        UserDetails_Table = new javax.swing.JTable();
        jPanel20 = new javax.swing.JPanel();
        UpdateUserDetails_Button = new javax.swing.JButton();
        DeleteUserDetails_Button = new javax.swing.JButton();
        Inventory_Panel = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        Inventory_Table = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        DeleteInventory_Button = new javax.swing.JButton();
        InventoryEdit_Button = new javax.swing.JButton();
        Company_Panel = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jPanel16 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        AddCompanyName_TextField = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        AddCompanyContactNumber_TextField = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        AddCompanyAddress_TextField = new javax.swing.JTextField();
        AddCompany_Button = new javax.swing.JButton();
        ClearCompany_Button = new javax.swing.JButton();
        DeleteCompany_Button = new javax.swing.JButton();
        UpdateCompany_Button = new javax.swing.JButton();
        SellingDetails_Panel = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        SellingDetails_jTable = new javax.swing.JTable();
        jPanel21 = new javax.swing.JPanel();
        ViewDetails_Button = new javax.swing.JButton();
        Generic_Panel = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jPanel19 = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        AddCategoryName_TextField = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        Uses_jTextArea = new javax.swing.JTextArea();
        AddGeneric_Button = new javax.swing.JButton();
        ClearGeneric_Button = new javax.swing.JButton();
        DeleteGeneric_Button = new javax.swing.JButton();
        UpdateGeneric_Button = new javax.swing.JButton();
        AddMedicine_Panel = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        InsertMedicine_Button = new javax.swing.JButton();
        CancelAddMedicine_Button = new javax.swing.JButton();
        ClearMedicineInsert_Button = new javax.swing.JButton();
        Generic_jComboBox = new javax.swing.JComboBox<>();
        CompanyName_jComboBox = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        MedicineName_TextField = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        Amount_TextField = new javax.swing.JTextField();
        MedicineType_TextField = new javax.swing.JTextField();
        AddPurchase_Panel = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        SubmitPurchaseMedicine_Button = new javax.swing.JButton();
        SellingPrice_TextField = new javax.swing.JTextField();
        CostPrice_TextField = new javax.swing.JTextField();
        CancelPurchaseMedicine_Button = new javax.swing.JButton();
        ClearPurchaseMedicine_Button = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        PurchaseExpireDate_TextField = new javax.swing.JTextField();
        PurchaseQuantity_TextField = new javax.swing.JTextField();
        PurchaseMedicineName_jComboBox = new javax.swing.JComboBox<>();
        ItemDetails_Panel = new javax.swing.JPanel();
        jPanel22 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        CustomerName_Label = new javax.swing.JLabel();
        sellingDate_label = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        TP_label = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        ItemDetails_jTable = new javax.swing.JTable();
        databaseMaintenence_panel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("DashBoard");

        jPanel1.setBackground(new java.awt.Color(36, 47, 65));

        jPanel3.setBackground(new java.awt.Color(97, 212, 195));

        jLabel1.setBackground(new java.awt.Color(204, 204, 204));
        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("USER NAME:");

        jLabel2.setBackground(new java.awt.Color(204, 204, 204));
        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("ROLE:");

        SignOut_jButton.setBackground(new java.awt.Color(97, 212, 195));
        SignOut_jButton.setForeground(new java.awt.Color(97, 212, 195));
        SignOut_jButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/db_pharmacymanagement/icons8-export-30.png"))); // NOI18N
        SignOut_jButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SignOut_jButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SignOut_jButtonActionPerformed(evt);
            }
        });

        Search_textField.setBackground(new java.awt.Color(97, 212, 195));
        Search_textField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Search_textField.setForeground(new java.awt.Color(255, 255, 255));
        Search_textField.setText("Search");
        Search_textField.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        Search_textField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                Search_textFieldFocusGained(evt);
            }
        });
        Search_textField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Search_textFieldActionPerformed(evt);
            }
        });

        Search_Button.setBackground(new java.awt.Color(97, 212, 195));
        Search_Button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/db_pharmacymanagement/icons8-search-30.png"))); // NOI18N
        Search_Button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Search_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Search_ButtonActionPerformed(evt);
            }
        });

        UserName_jLabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        UserName_jLabel.setText("U");

        Role_jLabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        Role_jLabel.setText("R");

        jLabel34.setText("Today :");

        currentDateTime_jLabel.setText("DATE & Time");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(65, 65, 65)
                        .addComponent(Role_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(UserName_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(SignOut_jButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(Search_textField, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(Search_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)
                        .addComponent(SearchItem_Combobox, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel34)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(currentDateTime_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(UserName_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(SignOut_jButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Role_jLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Search_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Search_textField)
                            .addComponent(SearchItem_Combobox))
                        .addContainerGap(34, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel34)
                            .addComponent(currentDateTime_jLabel))
                        .addGap(22, 22, 22))))
        );

        Dashboard_jLabel.setBackground(new java.awt.Color(36, 47, 65));
        Dashboard_jLabel.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        Dashboard_jLabel.setForeground(new java.awt.Color(240, 240, 240));
        Dashboard_jLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Dashboard_jLabel.setText("Dashboard");
        Dashboard_jLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Dashboard_jLabel.setOpaque(true);
        Dashboard_jLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Dashboard_jLabelMouseClicked(evt);
            }
        });

        MediicineDetails_jLabel.setBackground(new java.awt.Color(36, 47, 65));
        MediicineDetails_jLabel.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        MediicineDetails_jLabel.setForeground(new java.awt.Color(240, 240, 240));
        MediicineDetails_jLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        MediicineDetails_jLabel.setText("Medicine Detalis");
        MediicineDetails_jLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        MediicineDetails_jLabel.setOpaque(true);
        MediicineDetails_jLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MediicineDetails_jLabelMouseClicked(evt);
            }
        });

        UserDetails_jLabel.setBackground(new java.awt.Color(36, 47, 65));
        UserDetails_jLabel.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        UserDetails_jLabel.setForeground(new java.awt.Color(240, 240, 240));
        UserDetails_jLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        UserDetails_jLabel.setText("User Details");
        UserDetails_jLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        UserDetails_jLabel.setOpaque(true);
        UserDetails_jLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                UserDetails_jLabelMouseClicked(evt);
            }
        });

        SellingDetails_jLabel.setBackground(new java.awt.Color(36, 47, 65));
        SellingDetails_jLabel.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        SellingDetails_jLabel.setForeground(new java.awt.Color(240, 240, 240));
        SellingDetails_jLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        SellingDetails_jLabel.setText("SellingDetails");
        SellingDetails_jLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SellingDetails_jLabel.setOpaque(true);
        SellingDetails_jLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SellingDetails_jLabelMouseClicked(evt);
            }
        });

        purchase_jLabel.setBackground(new java.awt.Color(36, 47, 65));
        purchase_jLabel.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        purchase_jLabel.setForeground(new java.awt.Color(240, 240, 240));
        purchase_jLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        purchase_jLabel.setText("Purchase");
        purchase_jLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        purchase_jLabel.setOpaque(true);
        purchase_jLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                purchase_jLabelMouseClicked(evt);
            }
        });

        Signup_jLabel.setBackground(new java.awt.Color(36, 47, 65));
        Signup_jLabel.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        Signup_jLabel.setForeground(new java.awt.Color(240, 240, 240));
        Signup_jLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Signup_jLabel.setText("Sign Up");
        Signup_jLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Signup_jLabel.setOpaque(true);
        Signup_jLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Signup_jLabelMouseClicked(evt);
            }
        });

        Generic_jLabel.setBackground(new java.awt.Color(36, 47, 65));
        Generic_jLabel.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        Generic_jLabel.setForeground(new java.awt.Color(240, 240, 240));
        Generic_jLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Generic_jLabel.setText("Generic");
        Generic_jLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Generic_jLabel.setOpaque(true);
        Generic_jLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Generic_jLabelMouseClicked(evt);
            }
        });

        Inventory_jLabel.setBackground(new java.awt.Color(36, 47, 65));
        Inventory_jLabel.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        Inventory_jLabel.setForeground(new java.awt.Color(240, 240, 240));
        Inventory_jLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Inventory_jLabel.setText("Inventory");
        Inventory_jLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Inventory_jLabel.setOpaque(true);
        Inventory_jLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Inventory_jLabelMouseClicked(evt);
            }
        });

        Compnay_jLabel.setBackground(new java.awt.Color(36, 47, 65));
        Compnay_jLabel.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        Compnay_jLabel.setForeground(new java.awt.Color(240, 240, 240));
        Compnay_jLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Compnay_jLabel.setText("Company");
        Compnay_jLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Compnay_jLabel.setOpaque(true);
        Compnay_jLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Compnay_jLabelMouseClicked(evt);
            }
        });

        Main_panel.setLayout(new java.awt.CardLayout());

        MedicineShow_jPanel.setBackground(new java.awt.Color(36, 47, 65));
        MedicineShow_jPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        MedicineShow_jPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MedicineShow_jPanelMouseClicked(evt);
            }
        });

        NoOfMedicines_jLabel.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        NoOfMedicines_jLabel.setForeground(new java.awt.Color(255, 255, 255));
        NoOfMedicines_jLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        NoOfMedicines_jLabel.setText("3");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Medicines");

        javax.swing.GroupLayout MedicineShow_jPanelLayout = new javax.swing.GroupLayout(MedicineShow_jPanel);
        MedicineShow_jPanel.setLayout(MedicineShow_jPanelLayout);
        MedicineShow_jPanelLayout.setHorizontalGroup(
            MedicineShow_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MedicineShow_jPanelLayout.createSequentialGroup()
                .addGap(83, 83, 83)
                .addComponent(NoOfMedicines_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(87, Short.MAX_VALUE))
            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        MedicineShow_jPanelLayout.setVerticalGroup(
            MedicineShow_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MedicineShow_jPanelLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(NoOfMedicines_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        systemusershow_panel.setBackground(new java.awt.Color(36, 47, 65));
        systemusershow_panel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        systemusershow_panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                systemusershow_panelMouseClicked(evt);
            }
        });

        NoOfUsers_jLabel.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        NoOfUsers_jLabel.setForeground(new java.awt.Color(255, 255, 255));
        NoOfUsers_jLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        NoOfUsers_jLabel.setText("546");

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("System Users");

        javax.swing.GroupLayout systemusershow_panelLayout = new javax.swing.GroupLayout(systemusershow_panel);
        systemusershow_panel.setLayout(systemusershow_panelLayout);
        systemusershow_panelLayout.setHorizontalGroup(
            systemusershow_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(systemusershow_panelLayout.createSequentialGroup()
                .addGap(83, 83, 83)
                .addComponent(NoOfUsers_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(87, Short.MAX_VALUE))
            .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        systemusershow_panelLayout.setVerticalGroup(
            systemusershow_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(systemusershow_panelLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(NoOfUsers_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        TotalSalesShow_jPanel.setBackground(new java.awt.Color(36, 47, 65));
        TotalSalesShow_jPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        TotalSalesShow_jPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TotalSalesShow_jPanelMouseClicked(evt);
            }
        });

        NoOfSales_jLabel.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        NoOfSales_jLabel.setForeground(new java.awt.Color(255, 255, 255));
        NoOfSales_jLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        NoOfSales_jLabel.setText("3");

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("Total Sales");

        javax.swing.GroupLayout TotalSalesShow_jPanelLayout = new javax.swing.GroupLayout(TotalSalesShow_jPanel);
        TotalSalesShow_jPanel.setLayout(TotalSalesShow_jPanelLayout);
        TotalSalesShow_jPanelLayout.setHorizontalGroup(
            TotalSalesShow_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TotalSalesShow_jPanelLayout.createSequentialGroup()
                .addGap(83, 83, 83)
                .addComponent(NoOfSales_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(87, Short.MAX_VALUE))
            .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        TotalSalesShow_jPanelLayout.setVerticalGroup(
            TotalSalesShow_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TotalSalesShow_jPanelLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(NoOfSales_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        jPanel9.setBackground(new java.awt.Color(51, 51, 255));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("   Out Of Stock Medicines");

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));

        OutOfMedicineShow_Panel.setBackground(new java.awt.Color(255, 204, 204));
        OutOfMedicineShow_Panel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        OutOfMedicineShow_Panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                OutOfMedicineShow_PanelMouseClicked(evt);
            }
        });

        NoOfOutOfStockMedicine_jlabel.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        NoOfOutOfStockMedicine_jlabel.setForeground(new java.awt.Color(255, 0, 0));
        NoOfOutOfStockMedicine_jlabel.setText("0");

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 0, 51));
        jLabel20.setText("Total Out of Stock Medicines : ");

        javax.swing.GroupLayout OutOfMedicineShow_PanelLayout = new javax.swing.GroupLayout(OutOfMedicineShow_Panel);
        OutOfMedicineShow_Panel.setLayout(OutOfMedicineShow_PanelLayout);
        OutOfMedicineShow_PanelLayout.setHorizontalGroup(
            OutOfMedicineShow_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, OutOfMedicineShow_PanelLayout.createSequentialGroup()
                .addContainerGap(382, Short.MAX_VALUE)
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(NoOfOutOfStockMedicine_jlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(417, 417, 417))
        );
        OutOfMedicineShow_PanelLayout.setVerticalGroup(
            OutOfMedicineShow_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OutOfMedicineShow_PanelLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(OutOfMedicineShow_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(NoOfOutOfStockMedicine_jlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(37, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(OutOfMedicineShow_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(OutOfMedicineShow_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel11.setBackground(new java.awt.Color(51, 51, 255));

        jLabel35.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(255, 255, 255));
        jLabel35.setText("   Expire Date Alert");

        jPanel23.setBackground(new java.awt.Color(255, 255, 255));

        OutOfExpireMedicineShow_Panel.setBackground(new java.awt.Color(255, 204, 204));
        OutOfExpireMedicineShow_Panel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        OutOfExpireMedicineShow_Panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                OutOfExpireMedicineShow_PanelMouseClicked(evt);
            }
        });

        NoExpiresMed_label.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        NoExpiresMed_label.setForeground(new java.awt.Color(255, 0, 0));
        NoExpiresMed_label.setText("0");

        NoExpiresMedshow_jlabel.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        NoExpiresMedshow_jlabel.setForeground(new java.awt.Color(255, 0, 51));
        NoExpiresMedshow_jlabel.setText("No. Medicine Expires in 7 Days :");

        javax.swing.GroupLayout OutOfExpireMedicineShow_PanelLayout = new javax.swing.GroupLayout(OutOfExpireMedicineShow_Panel);
        OutOfExpireMedicineShow_Panel.setLayout(OutOfExpireMedicineShow_PanelLayout);
        OutOfExpireMedicineShow_PanelLayout.setHorizontalGroup(
            OutOfExpireMedicineShow_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, OutOfExpireMedicineShow_PanelLayout.createSequentialGroup()
                .addContainerGap(366, Short.MAX_VALUE)
                .addComponent(NoExpiresMedshow_jlabel)
                .addGap(18, 18, 18)
                .addComponent(NoExpiresMed_label, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(417, 417, 417))
        );
        OutOfExpireMedicineShow_PanelLayout.setVerticalGroup(
            OutOfExpireMedicineShow_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OutOfExpireMedicineShow_PanelLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(OutOfExpireMedicineShow_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(NoExpiresMed_label, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(NoExpiresMedshow_jlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(37, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(OutOfExpireMedicineShow_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(OutOfExpireMedicineShow_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel23, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jLabel35, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout Dashboard_PanelLayout = new javax.swing.GroupLayout(Dashboard_Panel);
        Dashboard_Panel.setLayout(Dashboard_PanelLayout);
        Dashboard_PanelLayout.setHorizontalGroup(
            Dashboard_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Dashboard_PanelLayout.createSequentialGroup()
                .addGap(110, 110, 110)
                .addComponent(systemusershow_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(97, 97, 97)
                .addComponent(MedicineShow_jPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(106, 106, 106)
                .addComponent(TotalSalesShow_jPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(Dashboard_PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        Dashboard_PanelLayout.setVerticalGroup(
            Dashboard_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Dashboard_PanelLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(Dashboard_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(systemusershow_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TotalSalesShow_jPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(MedicineShow_jPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        Main_panel.add(Dashboard_Panel, "card6");

        jPanel4.setBackground(new java.awt.Color(97, 212, 195));
        jPanel4.setPreferredSize(new java.awt.Dimension(840, 550));

        jPanel5.setBackground(new java.awt.Color(36, 47, 65));
        jPanel5.setPreferredSize(new java.awt.Dimension(420, 550));

        jLabel3.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(204, 204, 204));
        jLabel3.setText("ADDRESS");

        jLabel4.setBackground(new java.awt.Color(204, 204, 204));
        jLabel4.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(204, 204, 204));
        jLabel4.setText("PHONE NUMBER");

        register_Button.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        register_Button.setForeground(new java.awt.Color(255, 255, 255));
        register_Button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/db_pharmacymanagement/back_button.png"))); // NOI18N
        register_Button.setText("Register");
        register_Button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        register_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                register_ButtonActionPerformed(evt);
            }
        });

        delete_Button.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        delete_Button.setForeground(new java.awt.Color(255, 255, 255));
        delete_Button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/db_pharmacymanagement/back_button.png"))); // NOI18N
        delete_Button.setText("Delete");
        delete_Button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        delete_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delete_ButtonActionPerformed(evt);
            }
        });

        phone_TextField.setBackground(new java.awt.Color(36, 47, 65));
        phone_TextField.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        phone_TextField.setForeground(new java.awt.Color(255, 255, 255));
        phone_TextField.setText("Enter your phone number");
        phone_TextField.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        phone_TextField.setDisabledTextColor(new java.awt.Color(204, 204, 204));
        phone_TextField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                phone_TextFieldMouseClicked(evt);
            }
        });
        phone_TextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                phone_TextFieldActionPerformed(evt);
            }
        });

        address_TextField.setBackground(new java.awt.Color(36, 47, 65));
        address_TextField.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        address_TextField.setForeground(new java.awt.Color(255, 255, 255));
        address_TextField.setText("Enter you address");
        address_TextField.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        address_TextField.setDisabledTextColor(new java.awt.Color(204, 204, 204));
        address_TextField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                address_TextFieldMouseClicked(evt);
            }
        });
        address_TextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                address_TextFieldActionPerformed(evt);
            }
        });

        jLabel5.setBackground(new java.awt.Color(204, 204, 204));
        jLabel5.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(204, 204, 204));
        jLabel5.setText("ROLE");

        Role_jComboBox.setFont(new java.awt.Font("Century Gothic", 1, 13)); // NOI18N
        Role_jComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Manager", "Salesman" }));
        Role_jComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Role_jComboBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(register_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                        .addComponent(delete_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(address_TextField)
                    .addComponent(phone_TextField)
                    .addComponent(Role_jComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(134, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(146, 146, 146)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(address_TextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(phone_TextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(Role_jComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(89, 89, 89)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(register_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(delete_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(268, Short.MAX_VALUE))
        );

        jLabel6.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("USER NAME");

        username_update_TextField.setBackground(new java.awt.Color(97, 212, 195));
        username_update_TextField.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        username_update_TextField.setForeground(new java.awt.Color(255, 255, 255));
        username_update_TextField.setText("Enter you username");
        username_update_TextField.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        username_update_TextField.setDisabledTextColor(new java.awt.Color(204, 204, 204));
        username_update_TextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                username_update_TextFieldFocusGained(evt);
            }
        });
        username_update_TextField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                username_update_TextFieldMouseClicked(evt);
            }
        });
        username_update_TextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                username_update_TextFieldActionPerformed(evt);
            }
        });

        jLabel7.setBackground(new java.awt.Color(204, 204, 204));
        jLabel7.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("PASSWORD");

        jLabel8.setBackground(new java.awt.Color(204, 204, 204));
        jLabel8.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("CONFIRM PASSWORD");

        confirmPW_PasswordField.setBackground(new java.awt.Color(97, 212, 195));
        confirmPW_PasswordField.setForeground(new java.awt.Color(255, 255, 255));
        confirmPW_PasswordField.setText("jPasswordField1");
        confirmPW_PasswordField.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        confirmPW_PasswordField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                confirmPW_PasswordFieldFocusGained(evt);
            }
        });
        confirmPW_PasswordField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmPW_PasswordFieldActionPerformed(evt);
            }
        });

        PW_PasswordField.setBackground(new java.awt.Color(97, 212, 195));
        PW_PasswordField.setForeground(new java.awt.Color(255, 255, 255));
        PW_PasswordField.setText("jPasswordField1");
        PW_PasswordField.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        PW_PasswordField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                PW_PasswordFieldFocusGained(evt);
            }
        });
        PW_PasswordField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PW_PasswordFieldActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("SIGN UP");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(username_update_TextField, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                    .addComponent(PW_PasswordField)
                    .addComponent(confirmPW_PasswordField))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 122, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 661, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 759, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addComponent(jLabel9)
                .addGap(36, 36, 36)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(username_update_TextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PW_PasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(confirmPW_PasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout SignUp_panelLayout = new javax.swing.GroupLayout(SignUp_panel);
        SignUp_panel.setLayout(SignUp_panelLayout);
        SignUp_panelLayout.setHorizontalGroup(
            SignUp_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 1243, Short.MAX_VALUE)
        );
        SignUp_panelLayout.setVerticalGroup(
            SignUp_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 759, Short.MAX_VALUE)
        );

        Main_panel.add(SignUp_panel, "card2");

        MedicineDetails_table.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        MedicineDetails_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mid", "MedicineName", "Amount", "Category", "MedicineType", "CompanyName"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        MedicineDetails_table.setRowHeight(22);
        jScrollPane3.setViewportView(MedicineDetails_table);

        jPanel13.setBackground(new java.awt.Color(36, 47, 65));

        AddMedicine_Button.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        AddMedicine_Button.setForeground(new java.awt.Color(36, 47, 65));
        AddMedicine_Button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/db_pharmacymanagement/back.png"))); // NOI18N
        AddMedicine_Button.setText("ADD MEDICINE");
        AddMedicine_Button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AddMedicine_Button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        AddMedicine_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddMedicine_ButtonActionPerformed(evt);
            }
        });

        DeleteMedicine_Button.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        DeleteMedicine_Button.setForeground(new java.awt.Color(36, 47, 65));
        DeleteMedicine_Button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/db_pharmacymanagement/back.png"))); // NOI18N
        DeleteMedicine_Button.setText("DELETE MEDICINE");
        DeleteMedicine_Button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        DeleteMedicine_Button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        DeleteMedicine_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteMedicine_ButtonActionPerformed(evt);
            }
        });

        UpdateMedicine_Button.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        UpdateMedicine_Button.setForeground(new java.awt.Color(36, 47, 65));
        UpdateMedicine_Button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/db_pharmacymanagement/back.png"))); // NOI18N
        UpdateMedicine_Button.setText("UPDATE MEDICINE");
        UpdateMedicine_Button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        UpdateMedicine_Button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        UpdateMedicine_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateMedicine_ButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(AddMedicine_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(UpdateMedicine_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(DeleteMedicine_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(161, 161, 161))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AddMedicine_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(DeleteMedicine_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(UpdateMedicine_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout MedicineDetails_panelLayout = new javax.swing.GroupLayout(MedicineDetails_panel);
        MedicineDetails_panel.setLayout(MedicineDetails_panelLayout);
        MedicineDetails_panelLayout.setHorizontalGroup(
            MedicineDetails_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MedicineDetails_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1243, Short.MAX_VALUE)
        );
        MedicineDetails_panelLayout.setVerticalGroup(
            MedicineDetails_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MedicineDetails_panelLayout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 645, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        Main_panel.add(MedicineDetails_panel, "card3");

        purchase_Table.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        purchase_Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Pid", "MedicineName", "Category", "CompanyName", "MedicineType", "Quantity", "SellingPrice", "CostPrice"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        purchase_Table.setRowHeight(22);
        jScrollPane2.setViewportView(purchase_Table);

        jPanel12.setBackground(new java.awt.Color(36, 47, 65));

        PurchaseMedicine_Button.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        PurchaseMedicine_Button.setForeground(new java.awt.Color(36, 47, 65));
        PurchaseMedicine_Button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/db_pharmacymanagement/back.png"))); // NOI18N
        PurchaseMedicine_Button.setText("Purchase Medicine");
        PurchaseMedicine_Button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        PurchaseMedicine_Button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        PurchaseMedicine_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PurchaseMedicine_ButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(PurchaseMedicine_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(PurchaseMedicine_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout Purchase_PanelLayout = new javax.swing.GroupLayout(Purchase_Panel);
        Purchase_Panel.setLayout(Purchase_PanelLayout);
        Purchase_PanelLayout.setHorizontalGroup(
            Purchase_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1243, Short.MAX_VALUE)
            .addGroup(Purchase_PanelLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        Purchase_PanelLayout.setVerticalGroup(
            Purchase_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Purchase_PanelLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 638, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        Main_panel.add(Purchase_Panel, "card4");

        UserDetails_Table.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        UserDetails_Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Uid", "UserName", "userPassword", "Role", "Phone", "Address", "LastOnlineStatus"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        UserDetails_Table.setRowHeight(22);
        jScrollPane1.setViewportView(UserDetails_Table);

        jPanel20.setBackground(new java.awt.Color(36, 47, 65));

        UpdateUserDetails_Button.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        UpdateUserDetails_Button.setForeground(new java.awt.Color(36, 47, 65));
        UpdateUserDetails_Button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/db_pharmacymanagement/back.png"))); // NOI18N
        UpdateUserDetails_Button.setText("UPDATE");
        UpdateUserDetails_Button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        UpdateUserDetails_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateUserDetails_ButtonActionPerformed(evt);
            }
        });

        DeleteUserDetails_Button.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        DeleteUserDetails_Button.setForeground(new java.awt.Color(36, 47, 65));
        DeleteUserDetails_Button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/db_pharmacymanagement/back.png"))); // NOI18N
        DeleteUserDetails_Button.setText("DELETE");
        DeleteUserDetails_Button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        DeleteUserDetails_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteUserDetails_ButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(UpdateUserDetails_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(DeleteUserDetails_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(UpdateUserDetails_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(DeleteUserDetails_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(38, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout UserDetails_PanelLayout = new javax.swing.GroupLayout(UserDetails_Panel);
        UserDetails_Panel.setLayout(UserDetails_PanelLayout);
        UserDetails_PanelLayout.setHorizontalGroup(
            UserDetails_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1243, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, UserDetails_PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        UserDetails_PanelLayout.setVerticalGroup(
            UserDetails_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(UserDetails_PanelLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 626, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        Main_panel.add(UserDetails_Panel, "card5");

        Inventory_Table.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Inventory_Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "IId", "MedicineName", "MedicineType", "Category", "Amount", "Stock", "SellingPrice", "ExpireDate"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Inventory_Table.setRowHeight(22);
        jScrollPane7.setViewportView(Inventory_Table);

        jPanel2.setBackground(new java.awt.Color(36, 47, 65));

        DeleteInventory_Button.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        DeleteInventory_Button.setForeground(new java.awt.Color(36, 47, 65));
        DeleteInventory_Button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/db_pharmacymanagement/back.png"))); // NOI18N
        DeleteInventory_Button.setText("DELETE");
        DeleteInventory_Button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        DeleteInventory_Button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        DeleteInventory_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteInventory_ButtonActionPerformed(evt);
            }
        });

        InventoryEdit_Button.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        InventoryEdit_Button.setForeground(new java.awt.Color(36, 47, 65));
        InventoryEdit_Button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/db_pharmacymanagement/back.png"))); // NOI18N
        InventoryEdit_Button.setText("EDIT PRICE");
        InventoryEdit_Button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        InventoryEdit_Button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        InventoryEdit_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InventoryEdit_ButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(DeleteInventory_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(InventoryEdit_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(DeleteInventory_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(InventoryEdit_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout Inventory_PanelLayout = new javax.swing.GroupLayout(Inventory_Panel);
        Inventory_Panel.setLayout(Inventory_PanelLayout);
        Inventory_PanelLayout.setHorizontalGroup(
            Inventory_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 1243, Short.MAX_VALUE)
            .addGroup(Inventory_PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        Inventory_PanelLayout.setVerticalGroup(
            Inventory_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Inventory_PanelLayout.createSequentialGroup()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 650, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        Main_panel.add(Inventory_Panel, "card7");

        jTable3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cid", "CompanyName", "Address", "ContactNumber"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable3.setRowHeight(22);
        jTable3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable3MouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(jTable3);

        jPanel16.setBackground(new java.awt.Color(36, 47, 65));

        jLabel22.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Company Name");

        AddCompanyName_TextField.setBackground(new java.awt.Color(36, 47, 65));
        AddCompanyName_TextField.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        AddCompanyName_TextField.setForeground(new java.awt.Color(255, 255, 255));
        AddCompanyName_TextField.setText("Enter Company Name");
        AddCompanyName_TextField.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        AddCompanyName_TextField.setDisabledTextColor(new java.awt.Color(204, 204, 204));
        AddCompanyName_TextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                AddCompanyName_TextFieldFocusGained(evt);
            }
        });
        AddCompanyName_TextField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AddCompanyName_TextFieldMouseClicked(evt);
            }
        });
        AddCompanyName_TextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddCompanyName_TextFieldActionPerformed(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("Address");

        AddCompanyContactNumber_TextField.setBackground(new java.awt.Color(36, 47, 65));
        AddCompanyContactNumber_TextField.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        AddCompanyContactNumber_TextField.setForeground(new java.awt.Color(255, 255, 255));
        AddCompanyContactNumber_TextField.setText("Enter Contact Number");
        AddCompanyContactNumber_TextField.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        AddCompanyContactNumber_TextField.setDisabledTextColor(new java.awt.Color(204, 204, 204));
        AddCompanyContactNumber_TextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                AddCompanyContactNumber_TextFieldFocusGained(evt);
            }
        });
        AddCompanyContactNumber_TextField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AddCompanyContactNumber_TextFieldMouseClicked(evt);
            }
        });
        AddCompanyContactNumber_TextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddCompanyContactNumber_TextFieldActionPerformed(evt);
            }
        });

        jLabel24.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("Contact Number");

        AddCompanyAddress_TextField.setBackground(new java.awt.Color(36, 47, 65));
        AddCompanyAddress_TextField.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        AddCompanyAddress_TextField.setForeground(new java.awt.Color(255, 255, 255));
        AddCompanyAddress_TextField.setText("Enter Address");
        AddCompanyAddress_TextField.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        AddCompanyAddress_TextField.setDisabledTextColor(new java.awt.Color(204, 204, 204));
        AddCompanyAddress_TextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                AddCompanyAddress_TextFieldFocusGained(evt);
            }
        });
        AddCompanyAddress_TextField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AddCompanyAddress_TextFieldMouseClicked(evt);
            }
        });
        AddCompanyAddress_TextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddCompanyAddress_TextFieldActionPerformed(evt);
            }
        });

        AddCompany_Button.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        AddCompany_Button.setForeground(new java.awt.Color(36, 47, 65));
        AddCompany_Button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/db_pharmacymanagement/back.png"))); // NOI18N
        AddCompany_Button.setText("ADD COMPANY");
        AddCompany_Button.setContentAreaFilled(false);
        AddCompany_Button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AddCompany_Button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        AddCompany_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddCompany_ButtonActionPerformed(evt);
            }
        });

        ClearCompany_Button.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        ClearCompany_Button.setForeground(new java.awt.Color(36, 47, 65));
        ClearCompany_Button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/db_pharmacymanagement/back.png"))); // NOI18N
        ClearCompany_Button.setText("CLEAR");
        ClearCompany_Button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        ClearCompany_Button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ClearCompany_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClearCompany_ButtonActionPerformed(evt);
            }
        });

        DeleteCompany_Button.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        DeleteCompany_Button.setForeground(new java.awt.Color(36, 47, 65));
        DeleteCompany_Button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/db_pharmacymanagement/back.png"))); // NOI18N
        DeleteCompany_Button.setText("DELETE");
        DeleteCompany_Button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        DeleteCompany_Button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        DeleteCompany_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteCompany_ButtonActionPerformed(evt);
            }
        });

        UpdateCompany_Button.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        UpdateCompany_Button.setForeground(new java.awt.Color(36, 47, 65));
        UpdateCompany_Button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/db_pharmacymanagement/back.png"))); // NOI18N
        UpdateCompany_Button.setText("UPDATE");
        UpdateCompany_Button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        UpdateCompany_Button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        UpdateCompany_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateCompany_ButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(AddCompanyAddress_TextField)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel24)
                            .addComponent(AddCompanyName_TextField, javax.swing.GroupLayout.PREFERRED_SIZE, 468, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel22)
                            .addComponent(jLabel23)
                            .addComponent(AddCompanyContactNumber_TextField, javax.swing.GroupLayout.PREFERRED_SIZE, 468, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(407, 407, 407)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(AddCompany_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(UpdateCompany_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(DeleteCompany_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ClearCompany_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(65, 65, 65))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(AddCompanyName_TextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel23))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(AddCompany_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(UpdateCompany_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(AddCompanyAddress_TextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(AddCompanyContactNumber_TextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(DeleteCompany_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ClearCompany_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout Company_PanelLayout = new javax.swing.GroupLayout(Company_Panel);
        Company_Panel.setLayout(Company_PanelLayout);
        Company_PanelLayout.setHorizontalGroup(
            Company_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Company_PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        Company_PanelLayout.setVerticalGroup(
            Company_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Company_PanelLayout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        Main_panel.add(Company_Panel, "card8");

        SellingDetails_jTable.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        SellingDetails_jTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "SId", "SalesmanName", "CustomerName", "SellingDate", "TotalPrice"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        SellingDetails_jTable.setRowHeight(22);
        jScrollPane8.setViewportView(SellingDetails_jTable);

        jPanel21.setBackground(new java.awt.Color(36, 47, 65));

        ViewDetails_Button.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        ViewDetails_Button.setForeground(new java.awt.Color(36, 47, 65));
        ViewDetails_Button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/db_pharmacymanagement/back.png"))); // NOI18N
        ViewDetails_Button.setText("View Details");
        ViewDetails_Button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        ViewDetails_Button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ViewDetails_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ViewDetails_ButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ViewDetails_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(ViewDetails_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout SellingDetails_PanelLayout = new javax.swing.GroupLayout(SellingDetails_Panel);
        SellingDetails_Panel.setLayout(SellingDetails_PanelLayout);
        SellingDetails_PanelLayout.setHorizontalGroup(
            SellingDetails_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 1243, Short.MAX_VALUE)
            .addGroup(SellingDetails_PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        SellingDetails_PanelLayout.setVerticalGroup(
            SellingDetails_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SellingDetails_PanelLayout.createSequentialGroup()
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 646, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        Main_panel.add(SellingDetails_Panel, "card9");

        jTable4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "GId", "Category", "Uses"
            }
        ));
        jTable4.setRowHeight(22);
        jTable4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable4MouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(jTable4);

        jPanel19.setBackground(new java.awt.Color(36, 47, 65));

        jLabel30.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 255, 255));
        jLabel30.setText("Category of Medicine");

        AddCategoryName_TextField.setBackground(new java.awt.Color(36, 47, 65));
        AddCategoryName_TextField.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        AddCategoryName_TextField.setForeground(new java.awt.Color(255, 255, 255));
        AddCategoryName_TextField.setText("Enter Category Name");
        AddCategoryName_TextField.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        AddCategoryName_TextField.setDisabledTextColor(new java.awt.Color(204, 204, 204));
        AddCategoryName_TextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                AddCategoryName_TextFieldFocusGained(evt);
            }
        });
        AddCategoryName_TextField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AddCategoryName_TextFieldMouseClicked(evt);
            }
        });
        AddCategoryName_TextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddCategoryName_TextFieldActionPerformed(evt);
            }
        });

        jLabel31.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setText("Uses");

        Uses_jTextArea.setBackground(new java.awt.Color(36, 47, 65));
        Uses_jTextArea.setColumns(20);
        Uses_jTextArea.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        Uses_jTextArea.setForeground(new java.awt.Color(255, 255, 255));
        Uses_jTextArea.setRows(5);
        jScrollPane6.setViewportView(Uses_jTextArea);

        AddGeneric_Button.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        AddGeneric_Button.setForeground(new java.awt.Color(36, 47, 65));
        AddGeneric_Button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/db_pharmacymanagement/back.png"))); // NOI18N
        AddGeneric_Button.setText("ADD GENERIC");
        AddGeneric_Button.setContentAreaFilled(false);
        AddGeneric_Button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AddGeneric_Button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        AddGeneric_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddGeneric_ButtonActionPerformed(evt);
            }
        });

        ClearGeneric_Button.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        ClearGeneric_Button.setForeground(new java.awt.Color(36, 47, 65));
        ClearGeneric_Button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/db_pharmacymanagement/back.png"))); // NOI18N
        ClearGeneric_Button.setText("CLEAR");
        ClearGeneric_Button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        ClearGeneric_Button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ClearGeneric_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClearGeneric_ButtonActionPerformed(evt);
            }
        });

        DeleteGeneric_Button.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        DeleteGeneric_Button.setForeground(new java.awt.Color(36, 47, 65));
        DeleteGeneric_Button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/db_pharmacymanagement/back.png"))); // NOI18N
        DeleteGeneric_Button.setText("DELETE");
        DeleteGeneric_Button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        DeleteGeneric_Button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        DeleteGeneric_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteGeneric_ButtonActionPerformed(evt);
            }
        });

        UpdateGeneric_Button.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        UpdateGeneric_Button.setForeground(new java.awt.Color(36, 47, 65));
        UpdateGeneric_Button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/db_pharmacymanagement/back.png"))); // NOI18N
        UpdateGeneric_Button.setText("UPDATE");
        UpdateGeneric_Button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        UpdateGeneric_Button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        UpdateGeneric_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateGeneric_ButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel31)
                            .addComponent(jLabel30))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 468, Short.MAX_VALUE)
                            .addComponent(AddCategoryName_TextField))
                        .addGap(435, 435, 435)))
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(AddGeneric_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(UpdateGeneric_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(DeleteGeneric_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ClearGeneric_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel30)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(AddCategoryName_TextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel31)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(AddGeneric_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13)
                        .addComponent(UpdateGeneric_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(DeleteGeneric_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ClearGeneric_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout Generic_PanelLayout = new javax.swing.GroupLayout(Generic_Panel);
        Generic_Panel.setLayout(Generic_PanelLayout);
        Generic_PanelLayout.setHorizontalGroup(
            Generic_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5)
            .addGroup(Generic_PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        Generic_PanelLayout.setVerticalGroup(
            Generic_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Generic_PanelLayout.createSequentialGroup()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        Main_panel.add(Generic_Panel, "card10");

        AddMedicine_Panel.setBackground(new java.awt.Color(97, 212, 195));

        jPanel14.setBackground(new java.awt.Color(97, 212, 195));
        jPanel14.setPreferredSize(new java.awt.Dimension(840, 550));

        jPanel15.setBackground(new java.awt.Color(36, 47, 65));
        jPanel15.setPreferredSize(new java.awt.Dimension(420, 550));

        jLabel11.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(204, 204, 204));
        jLabel11.setText("Company Name/Manufacturer");

        jLabel12.setBackground(new java.awt.Color(204, 204, 204));
        jLabel12.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(204, 204, 204));
        jLabel12.setText("Generic");

        InsertMedicine_Button.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        InsertMedicine_Button.setForeground(new java.awt.Color(255, 255, 255));
        InsertMedicine_Button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/db_pharmacymanagement/back.png"))); // NOI18N
        InsertMedicine_Button.setText("INSERT");
        InsertMedicine_Button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        InsertMedicine_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InsertMedicine_ButtonActionPerformed(evt);
            }
        });

        CancelAddMedicine_Button.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        CancelAddMedicine_Button.setForeground(new java.awt.Color(255, 255, 255));
        CancelAddMedicine_Button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/db_pharmacymanagement/back.png"))); // NOI18N
        CancelAddMedicine_Button.setText("CANCEL");
        CancelAddMedicine_Button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        CancelAddMedicine_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelAddMedicine_ButtonActionPerformed(evt);
            }
        });

        ClearMedicineInsert_Button.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        ClearMedicineInsert_Button.setForeground(new java.awt.Color(255, 255, 255));
        ClearMedicineInsert_Button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/db_pharmacymanagement/back.png"))); // NOI18N
        ClearMedicineInsert_Button.setText("CLEAR");
        ClearMedicineInsert_Button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ClearMedicineInsert_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClearMedicineInsert_ButtonActionPerformed(evt);
            }
        });

        Generic_jComboBox.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N

        CompanyName_jComboBox.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(CompanyName_jComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel15Layout.createSequentialGroup()
                            .addComponent(jLabel11)
                            .addGap(33, 33, 33))
                        .addComponent(InsertMedicine_Button, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 472, Short.MAX_VALUE)
                        .addComponent(CancelAddMedicine_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(ClearMedicineInsert_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(Generic_jComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(116, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(146, 146, 146)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(CompanyName_jComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Generic_jComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addComponent(InsertMedicine_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ClearMedicineInsert_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addComponent(CancelAddMedicine_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(272, Short.MAX_VALUE))
        );

        jLabel14.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Medicine Name");

        MedicineName_TextField.setBackground(new java.awt.Color(97, 212, 195));
        MedicineName_TextField.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        MedicineName_TextField.setForeground(new java.awt.Color(255, 255, 255));
        MedicineName_TextField.setText("Enter  Medicine Name");
        MedicineName_TextField.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        MedicineName_TextField.setDisabledTextColor(new java.awt.Color(204, 204, 204));
        MedicineName_TextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                MedicineName_TextFieldFocusGained(evt);
            }
        });
        MedicineName_TextField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MedicineName_TextFieldMouseClicked(evt);
            }
        });
        MedicineName_TextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MedicineName_TextFieldActionPerformed(evt);
            }
        });

        jLabel16.setBackground(new java.awt.Color(204, 204, 204));
        jLabel16.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Medicine Type");

        jLabel19.setBackground(new java.awt.Color(204, 204, 204));
        jLabel19.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Amount");

        jLabel21.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Insert Medicine");

        Amount_TextField.setBackground(new java.awt.Color(97, 212, 195));
        Amount_TextField.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        Amount_TextField.setForeground(new java.awt.Color(255, 255, 255));
        Amount_TextField.setText("Enter Amount");
        Amount_TextField.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        Amount_TextField.setDisabledTextColor(new java.awt.Color(204, 204, 204));
        Amount_TextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                Amount_TextFieldFocusGained(evt);
            }
        });
        Amount_TextField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Amount_TextFieldMouseClicked(evt);
            }
        });
        Amount_TextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Amount_TextFieldActionPerformed(evt);
            }
        });

        MedicineType_TextField.setBackground(new java.awt.Color(97, 212, 195));
        MedicineType_TextField.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        MedicineType_TextField.setForeground(new java.awt.Color(255, 255, 255));
        MedicineType_TextField.setText("Enter Medicine Type");
        MedicineType_TextField.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        MedicineType_TextField.setDisabledTextColor(new java.awt.Color(204, 204, 204));
        MedicineType_TextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                MedicineType_TextFieldFocusGained(evt);
            }
        });
        MedicineType_TextField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MedicineType_TextFieldMouseClicked(evt);
            }
        });
        MedicineType_TextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MedicineType_TextFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel16)
                    .addComponent(jLabel14)
                    .addComponent(jLabel19)
                    .addComponent(jLabel21)
                    .addComponent(MedicineName_TextField, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                    .addComponent(Amount_TextField)
                    .addComponent(MedicineType_TextField))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 122, Short.MAX_VALUE)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, 661, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, 759, Short.MAX_VALUE)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addComponent(jLabel21)
                .addGap(36, 36, 36)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(MedicineName_TextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel16)
                .addGap(12, 12, 12)
                .addComponent(MedicineType_TextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Amount_TextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout AddMedicine_PanelLayout = new javax.swing.GroupLayout(AddMedicine_Panel);
        AddMedicine_Panel.setLayout(AddMedicine_PanelLayout);
        AddMedicine_PanelLayout.setHorizontalGroup(
            AddMedicine_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, 1243, Short.MAX_VALUE)
        );
        AddMedicine_PanelLayout.setVerticalGroup(
            AddMedicine_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, 759, Short.MAX_VALUE)
        );

        Main_panel.add(AddMedicine_Panel, "card11");

        jPanel17.setBackground(new java.awt.Color(97, 212, 195));
        jPanel17.setPreferredSize(new java.awt.Dimension(840, 550));

        jPanel18.setBackground(new java.awt.Color(36, 47, 65));
        jPanel18.setPreferredSize(new java.awt.Dimension(420, 550));

        jLabel13.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(204, 204, 204));
        jLabel13.setText("Cost Price");

        jLabel25.setBackground(new java.awt.Color(204, 204, 204));
        jLabel25.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(204, 204, 204));
        jLabel25.setText("Selling Price");

        SubmitPurchaseMedicine_Button.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        SubmitPurchaseMedicine_Button.setForeground(new java.awt.Color(255, 255, 255));
        SubmitPurchaseMedicine_Button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/db_pharmacymanagement/back.png"))); // NOI18N
        SubmitPurchaseMedicine_Button.setText("PURCHASE");
        SubmitPurchaseMedicine_Button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SubmitPurchaseMedicine_Button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        SubmitPurchaseMedicine_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubmitPurchaseMedicine_ButtonActionPerformed(evt);
            }
        });

        SellingPrice_TextField.setBackground(new java.awt.Color(36, 47, 65));
        SellingPrice_TextField.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        SellingPrice_TextField.setForeground(new java.awt.Color(255, 255, 255));
        SellingPrice_TextField.setText("Enter Selling Price");
        SellingPrice_TextField.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        SellingPrice_TextField.setDisabledTextColor(new java.awt.Color(204, 204, 204));
        SellingPrice_TextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                SellingPrice_TextFieldFocusGained(evt);
            }
        });
        SellingPrice_TextField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SellingPrice_TextFieldMouseClicked(evt);
            }
        });
        SellingPrice_TextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SellingPrice_TextFieldActionPerformed(evt);
            }
        });

        CostPrice_TextField.setBackground(new java.awt.Color(36, 47, 65));
        CostPrice_TextField.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        CostPrice_TextField.setForeground(new java.awt.Color(255, 255, 255));
        CostPrice_TextField.setText("Enter Cost Price");
        CostPrice_TextField.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        CostPrice_TextField.setDisabledTextColor(new java.awt.Color(204, 204, 204));
        CostPrice_TextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                CostPrice_TextFieldFocusGained(evt);
            }
        });
        CostPrice_TextField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CostPrice_TextFieldMouseClicked(evt);
            }
        });
        CostPrice_TextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CostPrice_TextFieldActionPerformed(evt);
            }
        });

        CancelPurchaseMedicine_Button.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        CancelPurchaseMedicine_Button.setForeground(new java.awt.Color(255, 255, 255));
        CancelPurchaseMedicine_Button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/db_pharmacymanagement/back.png"))); // NOI18N
        CancelPurchaseMedicine_Button.setText("CANCEL");
        CancelPurchaseMedicine_Button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        CancelPurchaseMedicine_Button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        CancelPurchaseMedicine_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelPurchaseMedicine_ButtonActionPerformed(evt);
            }
        });

        ClearPurchaseMedicine_Button.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        ClearPurchaseMedicine_Button.setForeground(new java.awt.Color(255, 255, 255));
        ClearPurchaseMedicine_Button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/db_pharmacymanagement/back.png"))); // NOI18N
        ClearPurchaseMedicine_Button.setText("CLEAR");
        ClearPurchaseMedicine_Button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        ClearPurchaseMedicine_Button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ClearPurchaseMedicine_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClearPurchaseMedicine_ButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel25)
                            .addComponent(jLabel13))
                        .addGap(33, 33, 33))
                    .addComponent(CostPrice_TextField, javax.swing.GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE)
                    .addComponent(SellingPrice_TextField)
                    .addComponent(SubmitPurchaseMedicine_Button, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(CancelPurchaseMedicine_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(ClearPurchaseMedicine_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(116, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(146, 146, 146)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CostPrice_TextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel25)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SellingPrice_TextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(70, 70, 70)
                .addComponent(SubmitPurchaseMedicine_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ClearPurchaseMedicine_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addComponent(CancelPurchaseMedicine_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(272, Short.MAX_VALUE))
        );

        jLabel26.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setText("Medicine Name");

        jLabel27.setBackground(new java.awt.Color(204, 204, 204));
        jLabel27.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("Quantity");

        jLabel28.setBackground(new java.awt.Color(204, 204, 204));
        jLabel28.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setText("ExpireDate (Ex : YYYY-MM-DD)");

        jLabel29.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setText("Purchase Medicine");

        PurchaseExpireDate_TextField.setBackground(new java.awt.Color(97, 212, 195));
        PurchaseExpireDate_TextField.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        PurchaseExpireDate_TextField.setForeground(new java.awt.Color(255, 255, 255));
        PurchaseExpireDate_TextField.setText("Enter Expire Date of this Medicine");
        PurchaseExpireDate_TextField.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        PurchaseExpireDate_TextField.setDisabledTextColor(new java.awt.Color(204, 204, 204));
        PurchaseExpireDate_TextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                PurchaseExpireDate_TextFieldFocusGained(evt);
            }
        });
        PurchaseExpireDate_TextField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PurchaseExpireDate_TextFieldMouseClicked(evt);
            }
        });
        PurchaseExpireDate_TextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PurchaseExpireDate_TextFieldActionPerformed(evt);
            }
        });

        PurchaseQuantity_TextField.setBackground(new java.awt.Color(97, 212, 195));
        PurchaseQuantity_TextField.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        PurchaseQuantity_TextField.setForeground(new java.awt.Color(255, 255, 255));
        PurchaseQuantity_TextField.setText("Enter Quanity");
        PurchaseQuantity_TextField.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        PurchaseQuantity_TextField.setDisabledTextColor(new java.awt.Color(204, 204, 204));
        PurchaseQuantity_TextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                PurchaseQuantity_TextFieldFocusGained(evt);
            }
        });
        PurchaseQuantity_TextField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PurchaseQuantity_TextFieldMouseClicked(evt);
            }
        });
        PurchaseQuantity_TextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PurchaseQuantity_TextFieldActionPerformed(evt);
            }
        });

        PurchaseMedicineName_jComboBox.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel27)
                    .addComponent(jLabel26)
                    .addComponent(jLabel28)
                    .addComponent(jLabel29)
                    .addComponent(PurchaseExpireDate_TextField, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                    .addComponent(PurchaseQuantity_TextField)
                    .addComponent(PurchaseMedicineName_jComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 122, Short.MAX_VALUE)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, 661, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, 759, Short.MAX_VALUE)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addComponent(jLabel29)
                .addGap(36, 36, 36)
                .addComponent(jLabel26)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PurchaseMedicineName_jComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel27)
                .addGap(12, 12, 12)
                .addComponent(PurchaseQuantity_TextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel28)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(PurchaseExpireDate_TextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout AddPurchase_PanelLayout = new javax.swing.GroupLayout(AddPurchase_Panel);
        AddPurchase_Panel.setLayout(AddPurchase_PanelLayout);
        AddPurchase_PanelLayout.setHorizontalGroup(
            AddPurchase_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, 1243, Short.MAX_VALUE)
        );
        AddPurchase_PanelLayout.setVerticalGroup(
            AddPurchase_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, 759, Short.MAX_VALUE)
        );

        Main_panel.add(AddPurchase_Panel, "card12");

        jPanel22.setBackground(new java.awt.Color(97, 212, 195));

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel32.setText("Item Details of Customer : ");

        CustomerName_Label.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        CustomerName_Label.setText("ABC");

        sellingDate_label.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N
        sellingDate_label.setText("2019-01-01");

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N
        jLabel33.setText("DATE : ");

        TP_label.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N
        TP_label.setText("Total Price : ");

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGap(362, 362, 362)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addComponent(jLabel32)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(CustomerName_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addGap(0, 79, Short.MAX_VALUE)
                        .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sellingDate_label, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(598, 598, 598))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel22Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(TP_label, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CustomerName_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sellingDate_label, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TP_label, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        ItemDetails_jTable.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        ItemDetails_jTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "MedicineName", "MedicineType", "Quantity", "RetailPrice", "TotalPrice of this Item"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        ItemDetails_jTable.setRowHeight(22);
        jScrollPane9.setViewportView(ItemDetails_jTable);

        javax.swing.GroupLayout ItemDetails_PanelLayout = new javax.swing.GroupLayout(ItemDetails_Panel);
        ItemDetails_Panel.setLayout(ItemDetails_PanelLayout);
        ItemDetails_PanelLayout.setHorizontalGroup(
            ItemDetails_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane9)
        );
        ItemDetails_PanelLayout.setVerticalGroup(
            ItemDetails_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ItemDetails_PanelLayout.createSequentialGroup()
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 617, Short.MAX_VALUE))
        );

        Main_panel.add(ItemDetails_Panel, "card7");

        databaseMaintenence_panel.setBackground(new java.awt.Color(36, 47, 65));
        databaseMaintenence_panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                databaseMaintenence_panelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout databaseMaintenence_panelLayout = new javax.swing.GroupLayout(databaseMaintenence_panel);
        databaseMaintenence_panel.setLayout(databaseMaintenence_panelLayout);
        databaseMaintenence_panelLayout.setHorizontalGroup(
            databaseMaintenence_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 125, Short.MAX_VALUE)
        );
        databaseMaintenence_panelLayout.setVerticalGroup(
            databaseMaintenence_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(Dashboard_jLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(MediicineDetails_jLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE)
                        .addComponent(purchase_jLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(UserDetails_jLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Signup_jLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(Generic_jLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(Inventory_jLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(Compnay_jLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(SellingDetails_jLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(databaseMaintenence_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Main_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(136, 136, 136)
                .addComponent(Dashboard_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(MediicineDetails_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(purchase_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Inventory_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SellingDetails_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(UserDetails_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Signup_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Compnay_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Generic_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(databaseMaintenence_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Main_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void Search_textFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Search_textFieldActionPerformed
        Search_textField.setText("");
    }//GEN-LAST:event_Search_textFieldActionPerformed

    private void SignOut_jButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SignOut_jButtonActionPerformed
        Login login = new Login();
        login.setVisible(true);
        setVisible(false);
    }//GEN-LAST:event_SignOut_jButtonActionPerformed

    private void Dashboard_jLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Dashboard_jLabelMouseClicked
        // TODO add your handling code here:
        Dashboard_jLabel.setForeground(new Color(97,212,195));
        MediicineDetails_jLabel.setForeground(new Color(240,240,240));
        purchase_jLabel.setForeground(new Color(240,240,240));
        UserDetails_jLabel.setForeground(new Color(240,240,240));
        Signup_jLabel.setForeground(new Color(240,240,240));
        Inventory_jLabel.setForeground(new Color(240,240,240));
        SellingDetails_jLabel.setForeground(new Color(240,240,240));
        Compnay_jLabel.setForeground(new Color(240,240,240));
        Generic_jLabel.setForeground(new Color(240,240,240));
        
        // Create Dashboard Panel
        Main_panel.removeAll();
        Main_panel.add(Dashboard_Panel);
        Main_panel.repaint();
        Main_panel.revalidate();
        
        // Refresh the Dashboard info
        SetNumOfUser();
        SetNumOfMedicine();
        SetNumOfSell();
        SetOutOfMedicine();
        SetNoExpires();
        
        
        System.out.println("Dashboard clicked");
        Frame = "D";
        SetSearchItem("D");
        
        
        
    }//GEN-LAST:event_Dashboard_jLabelMouseClicked

    private void MediicineDetails_jLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MediicineDetails_jLabelMouseClicked
        // TODO add your handling code here:
        Dashboard_jLabel.setForeground(new Color(240,240,240));
        MediicineDetails_jLabel.setForeground(new Color(97,212,195));
        purchase_jLabel.setForeground(new Color(240,240,240));
        UserDetails_jLabel.setForeground(new Color(240,240,240));
        Signup_jLabel.setForeground(new Color(240,240,240));
        Inventory_jLabel.setForeground(new Color(240,240,240));
        SellingDetails_jLabel.setForeground(new Color(240,240,240));
        Compnay_jLabel.setForeground(new Color(240,240,240));
        Generic_jLabel.setForeground(new Color(240,240,240));
        System.out.println("Medicine Details clicked");
        Frame = "M";
        SetSearchItem("M");
        ShowMedicineDetails("", "");
        
        
        
        Main_panel.removeAll();
        Main_panel.add(MedicineDetails_panel);
        Main_panel.repaint();
        Main_panel.revalidate();
    }//GEN-LAST:event_MediicineDetails_jLabelMouseClicked

    private void purchase_jLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_purchase_jLabelMouseClicked
        // TODO add your handling code here:
        Dashboard_jLabel.setForeground(new Color(240,240,240));
        MediicineDetails_jLabel.setForeground(new Color(240,240,240));
        purchase_jLabel.setForeground(new Color(97,212,195));
        UserDetails_jLabel.setForeground(new Color(240,240,240));
        Signup_jLabel.setForeground(new Color(240,240,240));
        Inventory_jLabel.setForeground(new Color(240,240,240));
        SellingDetails_jLabel.setForeground(new Color(240,240,240));
        Compnay_jLabel.setForeground(new Color(240,240,240));
        Generic_jLabel.setForeground(new Color(240,240,240));
        System.out.println("Purchase clicked");
        Frame = "P";
        SetSearchItem("P");
        show_purchase("", "");
        
        
        Main_panel.removeAll();
        Main_panel.add(Purchase_Panel);
        Main_panel.repaint();
        Main_panel.revalidate();
    }//GEN-LAST:event_purchase_jLabelMouseClicked

    private void UserDetails_jLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_UserDetails_jLabelMouseClicked
        // TODO add your handling code here:
        Dashboard_jLabel.setForeground(new Color(240,240,240));
        MediicineDetails_jLabel.setForeground(new Color(240,240,240));
        purchase_jLabel.setForeground(new Color(240,240,240));
        Signup_jLabel.setForeground(new Color(240,240,240));
        UserDetails_jLabel.setForeground(new Color(97,212,195));
        Inventory_jLabel.setForeground(new Color(240,240,240));
        SellingDetails_jLabel.setForeground(new Color(240,240,240));
        Compnay_jLabel.setForeground(new Color(240,240,240));
        Generic_jLabel.setForeground(new Color(240,240,240));
        System.out.println("UserDetails clicked");
        Frame = "UD";
        SetSearchItem("UD");
        
        Main_panel.removeAll();
        Main_panel.add(UserDetails_Panel);
        Main_panel.repaint();
        Main_panel.revalidate();
        
        showUserDetails("","");
        
        
        
    }//GEN-LAST:event_UserDetails_jLabelMouseClicked

    private void register_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_register_ButtonActionPerformed
        // TODO add your handling code here:
        if(username_update_TextField.getText().isEmpty() || username_update_TextField.getText().equals("Enter you username") ||
                PW_PasswordField.getText().isEmpty() || confirmPW_PasswordField.getText().isEmpty() || 
                address_TextField.getText().isEmpty() || address_TextField.getText().equals("Enter you address") ||
                phone_TextField.getText().isEmpty() || phone_TextField.getText().equals("Enter your phone number") ||
                PW_PasswordField.getText().equals("jPasswordField1") || confirmPW_PasswordField.getText().equals("jPasswordField1")){
            JOptionPane.showMessageDialog(null, "Please Fill up all the fields");
            
        }else{
            if(PW_PasswordField.getText().equals(confirmPW_PasswordField.getText())){
                try{
                    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                    String url="jdbc:sqlserver://localhost:1433;databaseName=PMS;user=sa;password=123456";
                    Connection con = DriverManager.getConnection(url);
                    String sql = "Insert into SystemUser"
                    +"(UserName,userPassword,Role,Phone,Address)"
                    +"values(?,?,?,?,?)";

                    PreparedStatement pst = con.prepareStatement(sql);
                    pst.setString(1, username_update_TextField.getText());
                    pst.setString(2, PW_PasswordField.getText().toString());
                    pst.setString(3, Role_jComboBox.getSelectedItem().toString());
                    pst.setString(4, phone_TextField.getText().toString());
                    pst.setString(5,address_TextField.getText().toString());
                    // ResultSet rs =
                    pst.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Successfully Registered");
                    //setVisible(false);
                    username_update_TextField.setText("");
                    PW_PasswordField.setText("");
                    confirmPW_PasswordField.setText("");
                    address_TextField.setText("");
                    phone_TextField.setText("");
                    showUserDetails("","");
                    con.close();
                }
                catch(Exception e){
                    JOptionPane.showMessageDialog(null, e);
                }
                
            }else{
                JOptionPane.showMessageDialog(null, "Password didn't match");
            }
            
        }
        
    }//GEN-LAST:event_register_ButtonActionPerformed

    private void delete_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delete_ButtonActionPerformed
            username_update_TextField.setText("");
            PW_PasswordField.setText("");
            confirmPW_PasswordField.setText("");
            address_TextField.setText("");
            phone_TextField.setText("");
    }//GEN-LAST:event_delete_ButtonActionPerformed

    private void phone_TextFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_phone_TextFieldMouseClicked
        // TODO add your handling code here:
        phone_TextField.setText("");
    }//GEN-LAST:event_phone_TextFieldMouseClicked

    private void phone_TextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_phone_TextFieldActionPerformed
        phone_TextField.setText("");
    }//GEN-LAST:event_phone_TextFieldActionPerformed

    private void address_TextFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_address_TextFieldMouseClicked
        // TODO add your handling code here:
        address_TextField.setText("");
    }//GEN-LAST:event_address_TextFieldMouseClicked

    private void address_TextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_address_TextFieldActionPerformed
        address_TextField.setText("");
    }//GEN-LAST:event_address_TextFieldActionPerformed

    private void username_update_TextFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_username_update_TextFieldMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_username_update_TextFieldMouseClicked

    private void username_update_TextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_username_update_TextFieldActionPerformed
        // TODO add your handling code here:
        username_update_TextField.setText("");
    }//GEN-LAST:event_username_update_TextFieldActionPerformed

    private void SellingDetails_jLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SellingDetails_jLabelMouseClicked
        Dashboard_jLabel.setForeground(new Color(240,240,240));
        MediicineDetails_jLabel.setForeground(new Color(240,240,240));
        purchase_jLabel.setForeground(new Color(240,240,240));
        UserDetails_jLabel.setForeground(new Color(240,240,240));
        Signup_jLabel.setForeground(new Color(240,240,240));
        Inventory_jLabel.setForeground(new Color(240,240,240));
        SellingDetails_jLabel.setForeground(new Color(97,212,195));
        Compnay_jLabel.setForeground(new Color(240,240,240));
        Generic_jLabel.setForeground(new Color(240,240,240));
        System.out.println("SellingDetails clicked");
        Frame = "SD";
        SetSearchItem("SD");
        show_Selling("", "");
        
        
        Main_panel.removeAll();
        Main_panel.add(SellingDetails_Panel);
        Main_panel.repaint();
        Main_panel.revalidate();
        
    }//GEN-LAST:event_SellingDetails_jLabelMouseClicked

    private void Role_jComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Role_jComboBoxActionPerformed
        role = Role_jComboBox.getSelectedItem().toString();
    }//GEN-LAST:event_Role_jComboBoxActionPerformed

    private void username_update_TextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_username_update_TextFieldFocusGained
        // TODO add your handling code here:
        username_update_TextField.setText("");
    }//GEN-LAST:event_username_update_TextFieldFocusGained

    private void PurchaseMedicine_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PurchaseMedicine_ButtonActionPerformed
        Main_panel.removeAll();
        Main_panel.add(AddPurchase_Panel);
        Main_panel.repaint();
        Main_panel.revalidate();
        PurchaseMedicineName_jComboBox.removeAllItems();
        for(int i=0; i<NoOfMedicine; i++){
            PurchaseMedicineName_jComboBox.addItem(MedicineName[i].toString());
        }
    }//GEN-LAST:event_PurchaseMedicine_ButtonActionPerformed

    private void AddMedicine_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddMedicine_ButtonActionPerformed

        
        Generic_jComboBox.removeAllItems();
        CompanyName_jComboBox.removeAllItems();
        for(int i=0; i<NoOfGeneric; i++){
                Generic_jComboBox.addItem(genericName[i].toString());
            
        }
        for(int i=0; i<NoOfCompany; i++){
            CompanyName_jComboBox.addItem(CompanyName[i].toString());
        }
        
        
        Main_panel.removeAll();
        Main_panel.add(AddMedicine_Panel);
        Main_panel.repaint();
        Main_panel.revalidate();
    }//GEN-LAST:event_AddMedicine_ButtonActionPerformed

    private void Signup_jLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Signup_jLabelMouseClicked
        Dashboard_jLabel.setForeground(new Color(240,240,240));
        MediicineDetails_jLabel.setForeground(new Color(240,240,240));
        purchase_jLabel.setForeground(new Color(240,240,240));
        UserDetails_jLabel.setForeground(new Color(240,240,240));
        Signup_jLabel.setForeground(new Color(97,212,195));
        Inventory_jLabel.setForeground(new Color(240,240,240));
        SellingDetails_jLabel.setForeground(new Color(240,240,240));
        Compnay_jLabel.setForeground(new Color(240,240,240));
        Generic_jLabel.setForeground(new Color(240,240,240));
        System.out.println("Signup clicked");
        SetSearchItem("SU");
        
        Main_panel.removeAll();
        Main_panel.add(SignUp_panel);
        Main_panel.repaint();
        Main_panel.revalidate();
        
        
    }//GEN-LAST:event_Signup_jLabelMouseClicked

    private void Generic_jLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Generic_jLabelMouseClicked
        Dashboard_jLabel.setForeground(new Color(240,240,240));
        MediicineDetails_jLabel.setForeground(new Color(240,240,240));
        purchase_jLabel.setForeground(new Color(240,240,240));
        UserDetails_jLabel.setForeground(new Color(240,240,240));
        Signup_jLabel.setForeground(new Color(240,240,240));
        Inventory_jLabel.setForeground(new Color(240,240,240));
        SellingDetails_jLabel.setForeground(new Color(240,240,240));
        Compnay_jLabel.setForeground(new Color(240,240,240));
        Generic_jLabel.setForeground(new Color(97,212,195));        //pressed item
        System.out.println("Generic clicked");
        showGeneric("","");
        Frame = "G";
        SetSearchItem("G");
        
        Main_panel.removeAll();
        Main_panel.add(Generic_Panel);
        Main_panel.repaint();
        Main_panel.revalidate();
        
    }//GEN-LAST:event_Generic_jLabelMouseClicked

    private void Inventory_jLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Inventory_jLabelMouseClicked
        Dashboard_jLabel.setForeground(new Color(240,240,240));
        MediicineDetails_jLabel.setForeground(new Color(240,240,240));
        purchase_jLabel.setForeground(new Color(240,240,240));
        UserDetails_jLabel.setForeground(new Color(240,240,240));
        Signup_jLabel.setForeground(new Color(240,240,240));
        Inventory_jLabel.setForeground(new Color(97,212,195));
        SellingDetails_jLabel.setForeground(new Color(240,240,240));
        Compnay_jLabel.setForeground(new Color(240,240,240));
        Generic_jLabel.setForeground(new Color(240,240,240));
        System.out.println("Invenotry clicked");
        Frame = "I";
        SetSearchItem("I");
        ShowInventory("", "");
        
        
        Main_panel.removeAll();
        Main_panel.add(Inventory_Panel);
        Main_panel.repaint();
        Main_panel.revalidate();
    }//GEN-LAST:event_Inventory_jLabelMouseClicked

    private void Compnay_jLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Compnay_jLabelMouseClicked
        Dashboard_jLabel.setForeground(new Color(240,240,240));
        MediicineDetails_jLabel.setForeground(new Color(240,240,240));
        purchase_jLabel.setForeground(new Color(240,240,240));
        UserDetails_jLabel.setForeground(new Color(240,240,240));
        Signup_jLabel.setForeground(new Color(240,240,240));
        Inventory_jLabel.setForeground(new Color(240,240,240));
        SellingDetails_jLabel.setForeground(new Color(240,240,240));
        Compnay_jLabel.setForeground(new Color(97,212,195));
        Generic_jLabel.setForeground(new Color(240,240,240));
        System.out.println("Company clicked");
        
        Main_panel.removeAll();
        Main_panel.add(Company_Panel);
        Main_panel.repaint();
        Main_panel.revalidate();
        
        showCompany("","");
        Frame = "C";
        SetSearchItem("C");
        
    }//GEN-LAST:event_Compnay_jLabelMouseClicked

    private void Search_textFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Search_textFieldFocusGained
        Search_textField.setText("");
    }//GEN-LAST:event_Search_textFieldFocusGained

    private void InsertMedicine_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InsertMedicine_ButtonActionPerformed
        // ADD Medicine in Database
        Object selectedCompany = Cid[CompanyName_jComboBox.getSelectedIndex()];
        Object selectedGeneric = genericId[Generic_jComboBox.getSelectedIndex()];
//        System.out.println("Company: "+selectedCompany);
//        System.out.println("generic : "+selectedGeneric);
        
        
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url="jdbc:sqlserver://localhost:1433;databaseName=PMS;user=sa;password=123456";
            Connection con = DriverManager.getConnection(url);
            String sql = "INSERT INTO Medicine(MedicineName,Gid,Cid,MedicineType,Amount)\n" +
"VALUES(?,?,?,?,?)";
            if(MedicineName_TextField.getText().isEmpty() || MedicineName_TextField.getText().equals("Enter  Medicine Name") ||
                    MedicineType_TextField.getText().isEmpty() || MedicineType_TextField.getText().equals("Enter Medicine Type") ||
                    Amount_TextField.getText().isEmpty() || Amount_TextField.getText().equals("Enter Amount")){
                
                JOptionPane.showMessageDialog(null, "Please Fill up All Fields");
                
            }
            else{
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setString(1, MedicineName_TextField.getText());
                pst.setString(2, selectedGeneric.toString());
                pst.setString(3, selectedCompany.toString());
                pst.setString(4, MedicineType_TextField.getText().toString());
                pst.setString(5,Amount_TextField.getText().toString());
                pst.executeUpdate();

                JOptionPane.showMessageDialog(null, "Successfully Stored");
                //setVisible(false);
                MedicineName_TextField.setText("");
                MedicineType_TextField.setText("");
                Amount_TextField.setText("");
                ShowMedicineDetails("","");
                
            }
            
            con.close();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        
        
        
        
    }//GEN-LAST:event_InsertMedicine_ButtonActionPerformed

    private void Amount_TextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Amount_TextFieldFocusGained
        Amount_TextField.setText("");
    }//GEN-LAST:event_Amount_TextFieldFocusGained

    private void Amount_TextFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Amount_TextFieldMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_Amount_TextFieldMouseClicked

    private void Amount_TextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Amount_TextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Amount_TextFieldActionPerformed

    private void MedicineType_TextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_MedicineType_TextFieldFocusGained
        MedicineType_TextField.setText("");
    }//GEN-LAST:event_MedicineType_TextFieldFocusGained

    private void MedicineType_TextFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MedicineType_TextFieldMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_MedicineType_TextFieldMouseClicked

    private void MedicineType_TextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MedicineType_TextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MedicineType_TextFieldActionPerformed

    private void CancelAddMedicine_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelAddMedicine_ButtonActionPerformed
//        Dashboard_jLabel.setForeground(new Color(97,212,195));
//        MediicineDetails_jLabel.setForeground(new Color(240,240,240));
        
        Main_panel.removeAll();
        Main_panel.add(MedicineDetails_panel);
        Main_panel.repaint();
        Main_panel.revalidate();
    }//GEN-LAST:event_CancelAddMedicine_ButtonActionPerformed

    private void ClearMedicineInsert_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClearMedicineInsert_ButtonActionPerformed
        MedicineName_TextField.setText("");
        Amount_TextField.setText("");
        MedicineType_TextField.setText("");
    }//GEN-LAST:event_ClearMedicineInsert_ButtonActionPerformed

    private void AddCompanyName_TextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_AddCompanyName_TextFieldFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_AddCompanyName_TextFieldFocusGained

    private void AddCompanyName_TextFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AddCompanyName_TextFieldMouseClicked
       AddCompanyName_TextField.setText("");
    }//GEN-LAST:event_AddCompanyName_TextFieldMouseClicked

    private void AddCompanyName_TextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddCompanyName_TextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AddCompanyName_TextFieldActionPerformed

    private void AddCompanyContactNumber_TextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_AddCompanyContactNumber_TextFieldFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_AddCompanyContactNumber_TextFieldFocusGained

    private void AddCompanyContactNumber_TextFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AddCompanyContactNumber_TextFieldMouseClicked
        AddCompanyContactNumber_TextField.setText("");
    }//GEN-LAST:event_AddCompanyContactNumber_TextFieldMouseClicked

    private void AddCompanyContactNumber_TextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddCompanyContactNumber_TextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AddCompanyContactNumber_TextFieldActionPerformed

    private void AddCompanyAddress_TextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_AddCompanyAddress_TextFieldFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_AddCompanyAddress_TextFieldFocusGained

    private void AddCompanyAddress_TextFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AddCompanyAddress_TextFieldMouseClicked
        AddCompanyAddress_TextField.setText("");
    }//GEN-LAST:event_AddCompanyAddress_TextFieldMouseClicked

    private void AddCompanyAddress_TextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddCompanyAddress_TextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AddCompanyAddress_TextFieldActionPerformed

    private void AddCompany_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddCompany_ButtonActionPerformed
        //companyDetails: insert into database
        int selectedRow =  jTable3.getSelectedRow();
        if(selectedRow != -1){
            JOptionPane.showMessageDialog(null, "You can not store same data twice");
            jTable3.clearSelection();
            AddCompanyName_TextField.setText("");
            AddCompanyAddress_TextField.setText("");
            AddCompanyContactNumber_TextField.setText("");
        }
        else{
            try{
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                String url="jdbc:sqlserver://localhost:1433;databaseName=PMS;user=sa;password=123456";
                Connection con = DriverManager.getConnection(url);
                String sql = "Insert into Company"
                +"(CompanyName,Address,ContactNumber)"
                +"values(?,?,?)";

                if(AddCompanyName_TextField.getText().isEmpty() || AddCompanyAddress_TextField.getText().isEmpty() || AddCompanyContactNumber_TextField.getText().isEmpty() ||
                       AddCompanyName_TextField.getText().equals("Enter Company Name") ||  AddCompanyAddress_TextField.getText().equals("Enter Address") ||
                        AddCompanyContactNumber_TextField.getText().equals("Enter Contact Number")){
                    JOptionPane.showMessageDialog(null, "Please Fill up all TextField");
                }else{
                    PreparedStatement pst = con.prepareStatement(sql);
                    pst.setString(1, AddCompanyName_TextField.getText());
                    pst.setString(2, AddCompanyAddress_TextField.getText());
                    pst.setString(3, AddCompanyContactNumber_TextField.getText());
                    // ResultSet rs =
                    pst.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Successfully Stored");
                    //setVisible(false);
                    AddCompanyName_TextField.setText("");
                    AddCompanyAddress_TextField.setText("");
                    AddCompanyContactNumber_TextField.setText("");

                }

                showCompany("","");
                con.close();
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
            
        }
        
    }//GEN-LAST:event_AddCompany_ButtonActionPerformed

    private void ClearCompany_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClearCompany_ButtonActionPerformed
        jTable3.clearSelection();
        AddCompanyName_TextField.setText("");
        AddCompanyAddress_TextField.setText("");
        AddCompanyContactNumber_TextField.setText("");
    }//GEN-LAST:event_ClearCompany_ButtonActionPerformed

    private void SubmitPurchaseMedicine_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubmitPurchaseMedicine_ButtonActionPerformed
        
        Object selectedMedicine = Mid[PurchaseMedicineName_jComboBox.getSelectedIndex()];
        int medStock = -1;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date d = new Date();
        // store data in purchase table
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url="jdbc:sqlserver://localhost:1433;databaseName=PMS;user=sa;password=123456";
            Connection con = DriverManager.getConnection(url);
            String sql = "INSERT INTO Purchase(Mid,CostPrice,SellingPrice,Quantity) VALUES(?,?,?,?)";
            if(PurchaseQuantity_TextField.getText().isEmpty() || PurchaseQuantity_TextField.getText().equals("Enter Quanity") ||
                    PurchaseExpireDate_TextField.getText().isEmpty() || PurchaseExpireDate_TextField.getText().equals("Enter Expire Date of this Medicine") ||
                    SellingPrice_TextField.getText().isEmpty() || SellingPrice_TextField.getText().equals("Enter Selling Price") ||
                    CostPrice_TextField.getText().isEmpty() || CostPrice_TextField.getText().equals("Enter Cost Price")){
                JOptionPane.showMessageDialog(null, "Please Fill up all fields");
            }else{
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setString(1, selectedMedicine.toString());
                pst.setString(2, CostPrice_TextField.getText().toString());
                pst.setString(3, SellingPrice_TextField.getText().toString());
                pst.setString(4, PurchaseQuantity_TextField.getText().toString());
                pst.executeUpdate();
            }
            con.close();
//            // GET last inserted Pid
//            Connection con3 = DriverManager.getConnection(url);
//            String query3 = "SELECT SCOPE_IDENTITY()";
//            Statement st3 = con3.createStatement();
//            ResultSet rs3 = st3.executeQuery(query3);
//            
//            while(rs3.next()){
//                lastInsertedID = rs3.getObject(ICONIFIED);
//            }
//            System.out.println("Last Insert : "+lastInsertedID);
//            con3.close();
            //Refresh the Purchase Table
            show_purchase("", "");
            // Store the Details in BuyingDetails
            Connection con4 = DriverManager.getConnection(url);
            String query4 = "INSERT INTO BuyingDetails(Uid,Pid,Status,PurchaseDate)\n" +
"VALUES(?,?,'Success',?)";
            PreparedStatement pst4 = con4.prepareStatement(query4);
            pst4.setString(1, UserId);
            pst4.setString(2, LatestPid.toString());
            pst4.setString(3, df.format(d));
            pst4.executeUpdate();
            con4.close();
            System.out.println("Last Insert : "+LatestPid);
            show_purchase("","");
            
            // Check if ihis medicine is avilable or not in inventory
            Connection con1 = DriverManager.getConnection(url);
            String sql1 = "SELECT Stock from Inventory where Mid = "+selectedMedicine;
            Statement st = con1.createStatement();
            
            if(PurchaseQuantity_TextField.getText().isEmpty() || PurchaseQuantity_TextField.getText().equals("Enter Quanity") ||
                    PurchaseExpireDate_TextField.getText().isEmpty() || PurchaseExpireDate_TextField.getText().equals("Enter Expire Date of this Medicine") ||
                    SellingPrice_TextField.getText().isEmpty() || SellingPrice_TextField.getText().equals("Enter Selling Price") ||
                    CostPrice_TextField.getText().isEmpty() || CostPrice_TextField.getText().equals("Enter Cost Price")){
                //JOptionPane.showMessageDialog(null, "Please Fill up all fields");
            }else{
                ResultSet rs = st.executeQuery(sql1);
                while(rs.next()){
                   medStock =  rs.getInt(ICONIFIED);
                }
            }
            
            con1.close();
            
            // insert/update data stored in the inventory table
            
            /// case 1: if not avaiable in inventory then add it
            /// case 2 : if available in inventory but expiredate same then update it
            /// case 3 : if avaiavle in inventory and stock = 0 then update it
            /// sol for case 2 : if medstock = 0 then update med with expiredate
            // sol for case 1,3: if medstock >-1 and not zero then add it
            Connection con2 = DriverManager.getConnection(url);
            String query1 = "UPDATE Inventory Set Stock = Stock+ ?,ExpireDate = ? WHERE Mid = ?";
            String query2 = "INSERT INTO Inventory(Mid,Stock,ExpireDate,Pid) VALUES (?,?,?,?)";
            if(PurchaseQuantity_TextField.getText().isEmpty() || PurchaseQuantity_TextField.getText().equals("Enter Quanity") ||
                    PurchaseExpireDate_TextField.getText().isEmpty() || PurchaseExpireDate_TextField.getText().equals("Enter Expire Date of this Medicine") ||
                    SellingPrice_TextField.getText().isEmpty() || SellingPrice_TextField.getText().equals("Enter Selling Price") ||
                    CostPrice_TextField.getText().isEmpty() || CostPrice_TextField.getText().equals("Enter Cost Price")){
                //JOptionPane.showMessageDialog(null, "Please Fill up all fields");
            }else{
                
                if(medStock == 0){
                    PreparedStatement pst1 = con2.prepareStatement(query1);
                    pst1.setString(1, PurchaseQuantity_TextField.getText().toString());
                    pst1.setString(2, PurchaseExpireDate_TextField.getText());
                    pst1.setString(3, selectedMedicine.toString());
                    pst1.executeUpdate();
                    
                
                }else{
                    PreparedStatement pst1 = con2.prepareStatement(query2);
                    pst1.setString(1, selectedMedicine.toString());
                    pst1.setString(2, PurchaseQuantity_TextField.getText().toString());
                    pst1.setString(3,PurchaseExpireDate_TextField.getText().toString());
                    pst1.setString(4, LatestPid.toString());
                    pst1.executeUpdate();
                    
                }
                
            }
            
            con2.close();
            CostPrice_TextField.setText("");
            SellingPrice_TextField.setText("");
            PurchaseQuantity_TextField.setText("");
            PurchaseExpireDate_TextField.setText("");
            
            JOptionPane.showMessageDialog(null, "Successfully Stored");
            
            ShowInventory("","");
            medStock = -1;
            
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        
    }//GEN-LAST:event_SubmitPurchaseMedicine_ButtonActionPerformed

    private void SellingPrice_TextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_SellingPrice_TextFieldFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_SellingPrice_TextFieldFocusGained

    private void SellingPrice_TextFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SellingPrice_TextFieldMouseClicked
        SellingPrice_TextField.setText("");
    }//GEN-LAST:event_SellingPrice_TextFieldMouseClicked

    private void SellingPrice_TextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SellingPrice_TextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SellingPrice_TextFieldActionPerformed

    private void CostPrice_TextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_CostPrice_TextFieldFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_CostPrice_TextFieldFocusGained

    private void CostPrice_TextFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CostPrice_TextFieldMouseClicked
        CostPrice_TextField.setText("");
    }//GEN-LAST:event_CostPrice_TextFieldMouseClicked

    private void CostPrice_TextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CostPrice_TextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CostPrice_TextFieldActionPerformed

    private void CancelPurchaseMedicine_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelPurchaseMedicine_ButtonActionPerformed
        Main_panel.removeAll();
        Main_panel.add(Purchase_Panel);
        Main_panel.repaint();
        Main_panel.revalidate();
    }//GEN-LAST:event_CancelPurchaseMedicine_ButtonActionPerformed

    private void ClearPurchaseMedicine_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClearPurchaseMedicine_ButtonActionPerformed
        SellingPrice_TextField.setText("");
        CostPrice_TextField.setText("");
        PurchaseExpireDate_TextField.setText("");
        PurchaseQuantity_TextField.setText("");
    }//GEN-LAST:event_ClearPurchaseMedicine_ButtonActionPerformed

    private void PurchaseExpireDate_TextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_PurchaseExpireDate_TextFieldFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_PurchaseExpireDate_TextFieldFocusGained

    private void PurchaseExpireDate_TextFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PurchaseExpireDate_TextFieldMouseClicked
        PurchaseExpireDate_TextField.setText("");
    }//GEN-LAST:event_PurchaseExpireDate_TextFieldMouseClicked

    private void PurchaseExpireDate_TextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PurchaseExpireDate_TextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PurchaseExpireDate_TextFieldActionPerformed

    private void PurchaseQuantity_TextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_PurchaseQuantity_TextFieldFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_PurchaseQuantity_TextFieldFocusGained

    private void PurchaseQuantity_TextFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PurchaseQuantity_TextFieldMouseClicked
        PurchaseQuantity_TextField.setText("");
    }//GEN-LAST:event_PurchaseQuantity_TextFieldMouseClicked

    private void PurchaseQuantity_TextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PurchaseQuantity_TextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PurchaseQuantity_TextFieldActionPerformed

    private void AddCategoryName_TextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_AddCategoryName_TextFieldFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_AddCategoryName_TextFieldFocusGained

    private void AddCategoryName_TextFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AddCategoryName_TextFieldMouseClicked
        AddCategoryName_TextField.setText("");
    }//GEN-LAST:event_AddCategoryName_TextFieldMouseClicked

    private void AddCategoryName_TextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddCategoryName_TextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AddCategoryName_TextFieldActionPerformed

    private void AddGeneric_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddGeneric_ButtonActionPerformed
        
        int selectedRow =  jTable4.getSelectedRow();
        if(selectedRow != -1){
            JOptionPane.showMessageDialog(null, "You can not store same data twice");
            jTable4.clearSelection();
            AddCategoryName_TextField.setText("");
            Uses_jTextArea.setText("");
        }
        else{
            try{
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                String url="jdbc:sqlserver://localhost:1433;databaseName=PMS;user=sa;password=123456";
                Connection con = DriverManager.getConnection(url);
                String sql = "Insert into Generic"
                +"(Category,Uses)"
                +"values(?,?)";

                if(AddCategoryName_TextField.getText().isEmpty() || Uses_jTextArea.getText().isEmpty() || AddCategoryName_TextField.getText().equals("Enter Category Name")){
                    JOptionPane.showMessageDialog(null,"TextField should not empty");

                }else{
                    PreparedStatement pst = con.prepareStatement(sql);
                    pst.setString(1, AddCategoryName_TextField.getText());
                    pst.setString(2, Uses_jTextArea.getText());
                    // ResultSet rs =
                    pst.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Successfully Stored");
                    //setVisible(false);
                    AddCategoryName_TextField.setText("");
                    Uses_jTextArea.setText("");
                }


                showGeneric("","");
                con.close();
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
            
        }
        
    }//GEN-LAST:event_AddGeneric_ButtonActionPerformed

    private void ClearGeneric_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClearGeneric_ButtonActionPerformed
        jTable4.clearSelection();
        AddCategoryName_TextField.setText("");
        Uses_jTextArea.setText("");
    }//GEN-LAST:event_ClearGeneric_ButtonActionPerformed

    private void PW_PasswordFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PW_PasswordFieldActionPerformed
        // TODO add your handling code here:
        PW_PasswordField.setText("");
    }//GEN-LAST:event_PW_PasswordFieldActionPerformed

    private void PW_PasswordFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_PW_PasswordFieldFocusGained
        // TODO add your handling code here:
        PW_PasswordField.setText("");
    }//GEN-LAST:event_PW_PasswordFieldFocusGained

    private void confirmPW_PasswordFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmPW_PasswordFieldActionPerformed
        // TODO add your handling code here:
        confirmPW_PasswordField.setText("");
    }//GEN-LAST:event_confirmPW_PasswordFieldActionPerformed

    private void confirmPW_PasswordFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_confirmPW_PasswordFieldFocusGained
        // TODO add your handling code here:
        confirmPW_PasswordField.setText("");
    }//GEN-LAST:event_confirmPW_PasswordFieldFocusGained

    private void Search_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Search_ButtonActionPerformed
        
        try{
            if(SearchItem_Combobox.getSelectedItem().toString().isEmpty() || Search_textField.getText().toString().isEmpty() ||
                    Search_textField.getText().toString().equals("Search")){
                // It will freeze
            }else{
                String SearchCol = SearchItem_Combobox.getSelectedItem().toString();
                String searchkey = Search_textField.getText().toString();
                //jTextField1.setVisible(true);
                if(Frame == "M"){
                    ShowMedicineDetails(searchkey,SearchCol);
                }else if(Frame == "UD"){
                    
                    showUserDetails(searchkey, SearchCol);
                }else if(Frame == "I"){
                    ShowInventory(searchkey, SearchCol);
                }else if(Frame == "P"){
                    show_purchase(searchkey, SearchCol);
                }else if(Frame == "SD"){
                    show_Selling(searchkey, SearchCol);
                }else if(Frame == "C"){
                    showCompany(searchkey, SearchCol);
                }else if(Frame == "G"){
                    showGeneric(searchkey, SearchCol);
                }else{
                    // Do nothing
                }
            }
            
        }catch(Exception e){
            System.err.println("U are doing something unnatural thing that can not be functional");
            
        }
        
        
        
        
        
    }//GEN-LAST:event_Search_ButtonActionPerformed

    private void MedicineName_TextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MedicineName_TextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MedicineName_TextFieldActionPerformed

    private void MedicineName_TextFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MedicineName_TextFieldMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_MedicineName_TextFieldMouseClicked

    private void MedicineName_TextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_MedicineName_TextFieldFocusGained
        MedicineName_TextField.setText("");
    }//GEN-LAST:event_MedicineName_TextFieldFocusGained

    private void DeleteMedicine_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteMedicine_ButtonActionPerformed
        int selectedrow = MedicineDetails_table.getSelectedRow();
        if(selectedrow == -1){
            JOptionPane.showMessageDialog(null, "Please Select any row first");
        }else{
            Object selectedMid = MedicineDetails_table.getModel().getValueAt(selectedrow, 0);
            int response = JOptionPane.showConfirmDialog(this, "Do you want to delete this data?", "Delete?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if(response == JOptionPane.YES_OPTION){
                System.out.println("yes, Option : "+selectedMid);
                
                try{
                    
                    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                    String url="jdbc:sqlserver://localhost:1433;databaseName=PMS;user=sa;password=123456";
                    Connection con = DriverManager.getConnection(url);
                    String sql = "DELETE Medicine WHERE Mid = "+selectedMid.toString();
                    PreparedStatement pst = con.prepareStatement(sql);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Deleted Successfully");
                    con.close();
                    ShowMedicineDetails("","");
                }catch(Exception e){
                    JOptionPane.showMessageDialog(null, e);
                }
                MedicineDetails_table.clearSelection();
            }else if(response == JOptionPane.NO_OPTION){
                MedicineDetails_table.clearSelection();
            }else if(response == JOptionPane.CANCEL_OPTION){
                MedicineDetails_table.clearSelection();

            }
            
        }
        
    }//GEN-LAST:event_DeleteMedicine_ButtonActionPerformed

    private void UpdateMedicine_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateMedicine_ButtonActionPerformed
        int selectedrow = MedicineDetails_table.getSelectedRow();
        if(selectedrow == -1){
            JOptionPane.showMessageDialog(null, "Please Select any row first");
        }else{
            Object selectedMid = MedicineDetails_table.getModel().getValueAt(selectedrow, 0);
            Object selectedMedName = MedicineDetails_table.getModel().getValueAt(selectedrow, 1);
            Object selectedMedAmount = MedicineDetails_table.getModel().getValueAt(selectedrow, 2);
            Object selectedMedType = MedicineDetails_table.getModel().getValueAt(selectedrow, 4);
            
            Object NewMedName = JOptionPane.showInputDialog(null,"Medicine Name :",selectedMedName);
            Object NewMedAmount = JOptionPane.showInputDialog(null,"Medicine Amount :",selectedMedAmount);
            Object NewMedType = JOptionPane.showInputDialog(null,"Medicine Type :",selectedMedType);
            
            try{
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                String url="jdbc:sqlserver://localhost:1433;databaseName=PMS;user=sa;password=123456";
                Connection con = DriverManager.getConnection(url);
                String sql = "UPDATE Medicine Set MedicineName = '"+NewMedName.toString()+"',MedicineType = '"+NewMedType+"',Amount = '"+NewMedAmount.toString()+"' WHERE Mid = "+selectedMid.toString();
                PreparedStatement pst = con.prepareStatement(sql);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Updated Successfully");
                con.close();
                ShowMedicineDetails("","");
                
                
            }catch(Exception e){
                
            }
            
            
        }
    }//GEN-LAST:event_UpdateMedicine_ButtonActionPerformed

    private void UpdateUserDetails_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateUserDetails_ButtonActionPerformed
       int selectedrow = UserDetails_Table.getSelectedRow();
        if(selectedrow == -1){
            JOptionPane.showMessageDialog(null, "Please Select any row first");
        }else{
            Object selectedUid = UserDetails_Table.getModel().getValueAt(selectedrow, 0);
            Object selectedUserName = UserDetails_Table.getModel().getValueAt(selectedrow, 1);
            Object selectedUserPassword = UserDetails_Table.getModel().getValueAt(selectedrow, 2);
            Object selectedUserRole = UserDetails_Table.getModel().getValueAt(selectedrow, 3);
            Object selectedUserContactNumber = UserDetails_Table.getModel().getValueAt(selectedrow, 4);
            Object selectedUserAddress = UserDetails_Table.getModel().getValueAt(selectedrow, 5);
            
            Object NewUserName = JOptionPane.showInputDialog(null,"User Name :",selectedUserName);
            Object NewUserPassword = JOptionPane.showInputDialog(null,"User Password :",selectedUserPassword);
            Object NewUserRole = JOptionPane.showInputDialog(null,"User Role :",selectedUserRole);
            Object NewUserContactNumber = JOptionPane.showInputDialog(null,"User Phone :",selectedUserContactNumber);
            Object NewUserAddress = JOptionPane.showInputDialog(null,"User Address:",selectedUserAddress);
            UserDetails_Table.clearSelection();
            // Update to Database
            try{
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                String url="jdbc:sqlserver://localhost:1433;databaseName=PMS;user=sa;password=123456";
                Connection con = DriverManager.getConnection(url);
                String sql = "UPDATE SystemUser SET UserName = '"+NewUserName.toString()+"',userPassword = '"+NewUserPassword.toString()+"',\n" +
"Role = '"+NewUserRole.toString()+"',Phone = '"+NewUserContactNumber.toString()+"',Address = '"+NewUserAddress.toString()+"'\n" +
"WHERE Uid = '"+selectedUid.toString()+"'";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Updated Successfully");
                con.close();
                showUserDetails("", "");
                
            }catch(Exception e){
                
            }
            
        }
        
        
    }//GEN-LAST:event_UpdateUserDetails_ButtonActionPerformed

    private void DeleteUserDetails_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteUserDetails_ButtonActionPerformed
        
        int selectedrow = UserDetails_Table.getSelectedRow();
        if(selectedrow == -1){
            JOptionPane.showMessageDialog(null, "Please Select any row first");
        }else{
            Object selectedUid = UserDetails_Table.getModel().getValueAt(selectedrow, 0);
            int response = JOptionPane.showConfirmDialog(this, "Do you want to delete this data?", "Delete?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if(response == JOptionPane.YES_OPTION){
                try{
                    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                    String url="jdbc:sqlserver://localhost:1433;databaseName=PMS;user=sa;password=123456";
                    Connection con = DriverManager.getConnection(url);
                    String sql = "DELETE SystemUser WHERE Uid = "+selectedUid.toString();
                    PreparedStatement pst = con.prepareStatement(sql);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Deleted Successfully");
                    con.close();
                    showUserDetails("","");
                }catch(Exception e){
                    JOptionPane.showMessageDialog(null, e);
                }
                UserDetails_Table.clearSelection();
            }else if(response == JOptionPane.NO_OPTION){
                UserDetails_Table.clearSelection();
            }else if(response == JOptionPane.CANCEL_OPTION){
                UserDetails_Table.clearSelection();

            }
        }
        
        
        
    }//GEN-LAST:event_DeleteUserDetails_ButtonActionPerformed

    private void DeleteCompany_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteCompany_ButtonActionPerformed
        int selectedrow = jTable3.getSelectedRow();
        if(selectedrow == -1){
            JOptionPane.showMessageDialog(null, "Please Select any row first");
        }else{
            Object selectedCid = jTable3.getModel().getValueAt(selectedrow, 0);
            int response = JOptionPane.showConfirmDialog(this, "Do you want to delete this data?", "Delete?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if(response == JOptionPane.YES_OPTION){
                try{
                    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                    String url="jdbc:sqlserver://localhost:1433;databaseName=PMS;user=sa;password=123456";
                    Connection con = DriverManager.getConnection(url);
                    String sql = "DELETE Company WHERE Cid = "+selectedCid.toString();
                    PreparedStatement pst = con.prepareStatement(sql);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Deleted Successfully");
                    con.close();
                    showCompany("", "");
                }catch(Exception e){
                    JOptionPane.showMessageDialog(null, e);
                }
                jTable3.clearSelection();
            }else if(response == JOptionPane.NO_OPTION){
                jTable3.clearSelection();
            }else if(response == JOptionPane.CANCEL_OPTION){
                jTable3.clearSelection();

            }
            AddCompanyName_TextField.setText("");
            AddCompanyAddress_TextField.setText("");
            AddCompanyContactNumber_TextField.setText("");
        }
        
        
    }//GEN-LAST:event_DeleteCompany_ButtonActionPerformed

    private void UpdateCompany_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateCompany_ButtonActionPerformed
        
        int selectedrow = jTable3.getSelectedRow();
        if(selectedrow == -1){
            JOptionPane.showMessageDialog(null, "Please Select any row first");
        }else{
            Object selectedCid = jTable3.getModel().getValueAt(selectedrow, 0);
//            Object selectedCompanyName = jTable3.getModel().getValueAt(selectedrow, 1);
//            Object selectedCompanyAddress = jTable3.getModel().getValueAt(selectedrow, 2);
//            Object selectedCompanyContactNumber = jTable3.getModel().getValueAt(selectedrow, 3);
            
            String updatedCompanyName = AddCompanyName_TextField.getText();
            String updatedCompanyAddress = AddCompanyAddress_TextField.getText();
            String updatedCompanyContactNumber = AddCompanyContactNumber_TextField.getText();

            // Update to Database
            try{
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                String url="jdbc:sqlserver://localhost:1433;databaseName=PMS;user=sa;password=123456";
                Connection con = DriverManager.getConnection(url);
                String sql = "UPDATE Company SET CompanyName = '"+updatedCompanyName+"',Address = '"+updatedCompanyAddress+"',ContactNumber = '"+updatedCompanyContactNumber+"'\n" +
"WHERE Cid = '"+selectedCid.toString()+"'";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Updated Successfully");
                con.close();
                showCompany("", "");
                
                AddCompanyName_TextField.setText("");
                AddCompanyAddress_TextField.setText("");
                AddCompanyContactNumber_TextField.setText("");
                
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
            
        }
        
        
    }//GEN-LAST:event_UpdateCompany_ButtonActionPerformed

    private void DeleteGeneric_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteGeneric_ButtonActionPerformed
        int selectedrow = jTable4.getSelectedRow();
        if(selectedrow == -1){
            JOptionPane.showMessageDialog(null, "Please Select any row first");
        }else{
            Object selectedGid = jTable4.getModel().getValueAt(selectedrow, 0);
            int response = JOptionPane.showConfirmDialog(this, "Do you want to delete this data?", "Delete?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if(response == JOptionPane.YES_OPTION){
                try{
                    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                    String url="jdbc:sqlserver://localhost:1433;databaseName=PMS;user=sa;password=123456";
                    Connection con = DriverManager.getConnection(url);
                    String sql = "DELETE Generic WHERE GId = "+selectedGid.toString();
                    PreparedStatement pst = con.prepareStatement(sql);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Deleted Successfully");
                    con.close();
                    showGeneric("","");
                }catch(Exception e){
                    JOptionPane.showMessageDialog(null, e);
                }
                jTable4.clearSelection();
            }else if(response == JOptionPane.NO_OPTION){
                jTable4.clearSelection();
            }else if(response == JOptionPane.CANCEL_OPTION){
                jTable4.clearSelection();

            }
        }
    }//GEN-LAST:event_DeleteGeneric_ButtonActionPerformed

    private void UpdateGeneric_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateGeneric_ButtonActionPerformed
        
        int selectedrow = jTable4.getSelectedRow();
        if(selectedrow == -1){
            JOptionPane.showMessageDialog(null, "Please Select any row first");
        }else{
            Object selectedGid = jTable4.getModel().getValueAt(selectedrow, 0);
            
            String updatedGenericCategory = AddCategoryName_TextField.getText();
            String updatedGenericUses = Uses_jTextArea.getText();

            // Update to Database
            try{
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                String url="jdbc:sqlserver://localhost:1433;databaseName=PMS;user=sa;password=123456";
                Connection con = DriverManager.getConnection(url);
                String sql = "UPDATE Generic SET Category = '"+updatedGenericCategory+"',Uses = '"+updatedGenericUses+"' WHERE GId = "+selectedGid;
                PreparedStatement pst = con.prepareStatement(sql);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Updated Successfully");
                con.close();
                showGeneric("","");
                
                AddCategoryName_TextField.setText("");
                Uses_jTextArea.setText("");
                
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
            
        }
        
    }//GEN-LAST:event_UpdateGeneric_ButtonActionPerformed

    private void jTable3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable3MouseClicked
        // Company Table Updated Action
        int selectedRow =  jTable3.getSelectedRow();
        if(selectedRow != -1){
//            Object selectedCid = jTable3.getModel().getValueAt(selectedRow, 0);
            Object selectedCompanyName = jTable3.getModel().getValueAt(selectedRow, 1);
            Object selectedCompanyAddress = jTable3.getModel().getValueAt(selectedRow, 2);
            Object selectedCompanyContactNumber = jTable3.getModel().getValueAt(selectedRow, 3);
            
            AddCompanyName_TextField.setText(selectedCompanyName.toString());
            AddCompanyAddress_TextField.setText(selectedCompanyAddress.toString());
            AddCompanyContactNumber_TextField.setText(selectedCompanyContactNumber.toString());
        }
        
    }//GEN-LAST:event_jTable3MouseClicked

    private void jTable4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable4MouseClicked
        // Generic Table Updated Action
        int selectedRow =  jTable4.getSelectedRow();
        if(selectedRow != -1){
//            Object selectedGid = jTable4.getModel().getValueAt(selectedRow, 0);
            Object selectedGenericCategory = jTable4.getModel().getValueAt(selectedRow, 1);
            Object selectedGenericUses = jTable4.getModel().getValueAt(selectedRow, 2);
            
            
            AddCategoryName_TextField.setText(selectedGenericCategory.toString());
            Uses_jTextArea.setText(selectedGenericUses.toString());
            
        }
    }//GEN-LAST:event_jTable4MouseClicked

    private void ViewDetails_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ViewDetails_ButtonActionPerformed

        int selectedrow = SellingDetails_jTable.getSelectedRow();

        if(selectedrow == -1){
            JOptionPane.showMessageDialog(null, "Please Select any Row first");
        }else{

            Dashboard_jLabel.setForeground(new Color(240,240,240));
            MediicineDetails_jLabel.setForeground(new Color(240,240,240));
            Inventory_jLabel.setForeground(new Color(240,240,240));
            SellingDetails_jLabel.setForeground(new Color(240,240,240));

            Frame = "V";
            SetSearchItem(Frame);

            Main_panel.removeAll();
            Main_panel.add(ItemDetails_Panel);
            Main_panel.repaint();
            Main_panel.revalidate();
            TP_label.setText("Total Price: ");

            String Sid = SellingDetails_jTable.getModel().getValueAt(selectedrow, 0).toString();
            String CustomerName = SellingDetails_jTable.getModel().getValueAt(selectedrow, 2).toString();
            String SellingDate = SellingDetails_jTable.getModel().getValueAt(selectedrow, 3).toString();
            String TotalPrice = SellingDetails_jTable.getModel().getValueAt(selectedrow, 4).toString();
            showItemDetails("", "",Sid);
            CustomerName_Label.setText(CustomerName);
            sellingDate_label.setText(SellingDate);
            TP_label.setText(TP_label.getText()+" "+TotalPrice);
        }

    }//GEN-LAST:event_ViewDetails_ButtonActionPerformed

    private void OutOfMedicineShow_PanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_OutOfMedicineShow_PanelMouseClicked
        System.out.println("outOfMedicineShow is Clicked");
        // Create CardView Panel Inventory
        Dashboard_jLabel.setForeground(new Color(240,240,240));
        MediicineDetails_jLabel.setForeground(new Color(240,240,240));
        purchase_jLabel.setForeground(new Color(240,240,240));
        UserDetails_jLabel.setForeground(new Color(240,240,240));
        Signup_jLabel.setForeground(new Color(240,240,240));
        Inventory_jLabel.setForeground(new Color(97,212,195));
        SellingDetails_jLabel.setForeground(new Color(240,240,240));
        Compnay_jLabel.setForeground(new Color(240,240,240));
        Generic_jLabel.setForeground(new Color(240,240,240));
        System.out.println("Invenotry clicked");
        Frame = "I";
        SetSearchItem("I");
        ShowInventory("", "");
        
        
        Main_panel.removeAll();
        Main_panel.add(Inventory_Panel);
        Main_panel.repaint();
        Main_panel.revalidate();
        
        //Fetch Data From Database
        
        ArrayList<Inventory> inventory = new ArrayList<>();
        
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url="jdbc:sqlserver://localhost:1433;databaseName=PMS;user=sa;password=123456";
            Connection con = DriverManager.getConnection(url);
            String query = "SELECT Inventory.IId,Medicine.MedicineName,Medicine.MedicineType,Generic.Category,Medicine.Amount,Inventory.Stock,Purchase.SellingPrice,Inventory.ExpireDate FROM Inventory INNER JOIN Medicine on Medicine.Mid = Inventory.Mid LEFT JOIN Purchase ON Purchase.Pid = Inventory.Pid INNER JOIN Generic ON Generic.Gid = Medicine.Gid WHERE Inventory.Stock=0";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            Inventory inven;
            while(rs.next()){
                inven = new Inventory(rs.getInt("IId"),rs.getString("MedicineName"),rs.getString("MedicineType"),rs.getString("Category"),rs.getString("Amount"),
                            rs.getInt("Stock"),rs.getDouble("SellingPrice"),rs.getString("ExpireDate"));
                inventory.add(inven);
            }
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
            
        }
        
        
        // show data in database
        DefaultTableModel inventoryModel = (DefaultTableModel) Inventory_Table.getModel();
        int rowCount = inventoryModel.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            inventoryModel.removeRow(i);
        }
        
        Object[] row  = new Object[8];
        for(int i=0; i<inventory.size(); i++){
            row[0] = inventory.get(i).getIId();
            row[1] = inventory.get(i).getMedicineName();
            row[2] = inventory.get(i).getMedicineType();
            row[3] = inventory.get(i).getCategory();
            row[4] = inventory.get(i).getAmount();
            row[5] = inventory.get(i).getStock();
            row[6] = inventory.get(i).getSellingPrice();
            row[7] = inventory.get(i).getExpireDate();
            inventoryModel.addRow(row);
            
        }
        inventory.clear();
    }//GEN-LAST:event_OutOfMedicineShow_PanelMouseClicked

    private void databaseMaintenence_panelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_databaseMaintenence_panelMouseClicked
        ExecuteQuery eq = new ExecuteQuery();
        eq.setVisible(true);
    }//GEN-LAST:event_databaseMaintenence_panelMouseClicked

    private void OutOfExpireMedicineShow_PanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_OutOfExpireMedicineShow_PanelMouseClicked
        System.out.println("outOfMedicineShow is Clicked");
        // Create CardView Panel Inventory
        Dashboard_jLabel.setForeground(new Color(240,240,240));
        MediicineDetails_jLabel.setForeground(new Color(240,240,240));
        purchase_jLabel.setForeground(new Color(240,240,240));
        UserDetails_jLabel.setForeground(new Color(240,240,240));
        Signup_jLabel.setForeground(new Color(240,240,240));
        Inventory_jLabel.setForeground(new Color(97,212,195));
        SellingDetails_jLabel.setForeground(new Color(240,240,240));
        Compnay_jLabel.setForeground(new Color(240,240,240));
        Generic_jLabel.setForeground(new Color(240,240,240));
        System.out.println("Invenotry clicked");
        Frame = "I";
        SetSearchItem("I");
        ShowInventory("", "");
        
        
        Main_panel.removeAll();
        Main_panel.add(Inventory_Panel);
        Main_panel.repaint();
        Main_panel.revalidate();
        
        //Fetch Data From Database
        
        ArrayList<Inventory> inventory = new ArrayList<>();
        
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url="jdbc:sqlserver://localhost:1433;databaseName=PMS;user=sa;password=123456";
            Connection con = DriverManager.getConnection(url);
            String query = "SELECT Inventory.IId,Medicine.MedicineName,Medicine.MedicineType,Generic.Category,Medicine.Amount,Inventory.Stock,\n" +
"Purchase.SellingPrice,Inventory.ExpireDate \n" +
"FROM Inventory INNER JOIN \n" +
"Medicine on Medicine.Mid = Inventory.Mid LEFT JOIN \n" +
"Purchase ON Purchase.Pid = Inventory.Pid INNER JOIN Generic ON Generic.Gid = Medicine.Gid WHERE Inventory.ExpireDate between '"+currentdate+"' and '"+next7date+"'";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            Inventory inven;
            while(rs.next()){
                inven = new Inventory(rs.getInt("IId"),rs.getString("MedicineName"),rs.getString("MedicineType"),rs.getString("Category"),rs.getString("Amount"),
                            rs.getInt("Stock"),rs.getDouble("SellingPrice"),rs.getString("ExpireDate"));
                inventory.add(inven);
            }
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
            
        }
        
        
        // show data in database
        DefaultTableModel inventoryModel = (DefaultTableModel) Inventory_Table.getModel();
        int rowCount = inventoryModel.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            inventoryModel.removeRow(i);
        }
        
        Object[] row  = new Object[8];
        for(int i=0; i<inventory.size(); i++){
            row[0] = inventory.get(i).getIId();
            row[1] = inventory.get(i).getMedicineName();
            row[2] = inventory.get(i).getMedicineType();
            row[3] = inventory.get(i).getCategory();
            row[4] = inventory.get(i).getAmount();
            row[5] = inventory.get(i).getStock();
            row[6] = inventory.get(i).getSellingPrice();
            row[7] = inventory.get(i).getExpireDate();
            inventoryModel.addRow(row);
            
        }
        inventory.clear();
        
        
        
    }//GEN-LAST:event_OutOfExpireMedicineShow_PanelMouseClicked

    private void systemusershow_panelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_systemusershow_panelMouseClicked
        Dashboard_jLabel.setForeground(new Color(240,240,240));
        MediicineDetails_jLabel.setForeground(new Color(240,240,240));
        purchase_jLabel.setForeground(new Color(240,240,240));
        Signup_jLabel.setForeground(new Color(240,240,240));
        UserDetails_jLabel.setForeground(new Color(97,212,195));
        Inventory_jLabel.setForeground(new Color(240,240,240));
        SellingDetails_jLabel.setForeground(new Color(240,240,240));
        Compnay_jLabel.setForeground(new Color(240,240,240));
        Generic_jLabel.setForeground(new Color(240,240,240));
        System.out.println("UserDetails clicked");
        Frame = "UD";
        SetSearchItem("UD");
        
        Main_panel.removeAll();
        Main_panel.add(UserDetails_Panel);
        Main_panel.repaint();
        Main_panel.revalidate();
        
        showUserDetails("","");
        
    }//GEN-LAST:event_systemusershow_panelMouseClicked

    private void MedicineShow_jPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MedicineShow_jPanelMouseClicked
        Dashboard_jLabel.setForeground(new Color(240,240,240));
        MediicineDetails_jLabel.setForeground(new Color(97,212,195));
        purchase_jLabel.setForeground(new Color(240,240,240));
        UserDetails_jLabel.setForeground(new Color(240,240,240));
        Signup_jLabel.setForeground(new Color(240,240,240));
        Inventory_jLabel.setForeground(new Color(240,240,240));
        SellingDetails_jLabel.setForeground(new Color(240,240,240));
        Compnay_jLabel.setForeground(new Color(240,240,240));
        Generic_jLabel.setForeground(new Color(240,240,240));
        System.out.println("Medicine Details clicked");
        Frame = "M";
        SetSearchItem("M");
        ShowMedicineDetails("", "");
        
        
        
        Main_panel.removeAll();
        Main_panel.add(MedicineDetails_panel);
        Main_panel.repaint();
        Main_panel.revalidate();
    }//GEN-LAST:event_MedicineShow_jPanelMouseClicked

    private void TotalSalesShow_jPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TotalSalesShow_jPanelMouseClicked
        Dashboard_jLabel.setForeground(new Color(240,240,240));
        MediicineDetails_jLabel.setForeground(new Color(240,240,240));
        purchase_jLabel.setForeground(new Color(240,240,240));
        UserDetails_jLabel.setForeground(new Color(240,240,240));
        Signup_jLabel.setForeground(new Color(240,240,240));
        Inventory_jLabel.setForeground(new Color(240,240,240));
        SellingDetails_jLabel.setForeground(new Color(97,212,195));
        Compnay_jLabel.setForeground(new Color(240,240,240));
        Generic_jLabel.setForeground(new Color(240,240,240));
        System.out.println("SellingDetails clicked");
        Frame = "SD";
        SetSearchItem("SD");
        show_Selling("", "");
        
        
        Main_panel.removeAll();
        Main_panel.add(SellingDetails_Panel);
        Main_panel.repaint();
        Main_panel.revalidate();
    }//GEN-LAST:event_TotalSalesShow_jPanelMouseClicked

    private void DeleteInventory_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteInventory_ButtonActionPerformed
        int selectedrow = Inventory_Table.getSelectedRow();
        if(selectedrow == -1){
            JOptionPane.showMessageDialog(null, "Please Select any row first");
        }else{
            Object selectedIid = Inventory_Table.getModel().getValueAt(selectedrow, 0);
            int response = JOptionPane.showConfirmDialog(this, "Do you want to delete this data?", "Delete?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if(response == JOptionPane.YES_OPTION){
                //System.out.println("yes, Option : "+selectedIid);
                
                try{
                    
                    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                    String url="jdbc:sqlserver://localhost:1433;databaseName=PMS;user=sa;password=123456";
                    Connection con = DriverManager.getConnection(url);
                    String sql = "DELETE Inventory WHERE IId = "+selectedIid;
                    PreparedStatement pst = con.prepareStatement(sql);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Deleted Successfully");
                    con.close();
                    ShowInventory("", "");
                }catch(Exception e){
                    JOptionPane.showMessageDialog(null, e);
                }
                Inventory_Table.clearSelection();
            }else if(response == JOptionPane.NO_OPTION){
                Inventory_Table.clearSelection();
            }else if(response == JOptionPane.CANCEL_OPTION){
                Inventory_Table.clearSelection();

            }
            
        }
    }//GEN-LAST:event_DeleteInventory_ButtonActionPerformed

    private void InventoryEdit_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InventoryEdit_ButtonActionPerformed
        
        int selectedrow = Inventory_Table.getSelectedRow();
        String Pid ="";
        if(selectedrow == -1){
            JOptionPane.showMessageDialog(null, "Please Select any row first");
        }else{
            Object selectedIid = Inventory_Table.getModel().getValueAt(selectedrow, 0);
            Object selectedPrice = Inventory_Table.getModel().getValueAt(selectedrow, 6);
            Object NewPrice = JOptionPane.showInputDialog(null,"New Selling Price :",selectedPrice);
            if(selectedPrice == NewPrice){
                // Nothing to do,already same price
                Inventory_Table.clearSelection();
            }else{
                // Update to Database
                try{
                    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                    String url="jdbc:sqlserver://localhost:1433;databaseName=PMS;user=sa;password=123456";
                    Connection con = DriverManager.getConnection(url);
                    String query = "SELECT Pid FROM Inventory WHERE IId = "+selectedIid;
                    Statement st = con.createStatement();
                    ResultSet rs = st.executeQuery(query);;
                    while(rs.next()){
                        Pid = rs.getString(ICONIFIED);
                    }
                    con.close();
                    Connection con1 = DriverManager.getConnection(url);
                    String sql = "UPDATE Purchase SET SellingPrice = '"+NewPrice+"' WHERE PId = '"+Pid+"'";
                    PreparedStatement pst = con1.prepareStatement(sql);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Updated Successfully");
                    con1.close();
                    ShowInventory("", "");

                }catch(Exception e){
                    //JOptionPane.showMessageDialog(null, e);
                }
            }
        }
        
    }//GEN-LAST:event_InventoryEdit_ButtonActionPerformed


    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Dashboard("").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField AddCategoryName_TextField;
    private javax.swing.JTextField AddCompanyAddress_TextField;
    private javax.swing.JTextField AddCompanyContactNumber_TextField;
    private javax.swing.JTextField AddCompanyName_TextField;
    private javax.swing.JButton AddCompany_Button;
    private javax.swing.JButton AddGeneric_Button;
    private javax.swing.JButton AddMedicine_Button;
    private javax.swing.JPanel AddMedicine_Panel;
    private javax.swing.JPanel AddPurchase_Panel;
    private javax.swing.JTextField Amount_TextField;
    private javax.swing.JButton CancelAddMedicine_Button;
    private javax.swing.JButton CancelPurchaseMedicine_Button;
    private javax.swing.JButton ClearCompany_Button;
    private javax.swing.JButton ClearGeneric_Button;
    private javax.swing.JButton ClearMedicineInsert_Button;
    private javax.swing.JButton ClearPurchaseMedicine_Button;
    private javax.swing.JComboBox<String> CompanyName_jComboBox;
    private javax.swing.JPanel Company_Panel;
    private javax.swing.JLabel Compnay_jLabel;
    private javax.swing.JTextField CostPrice_TextField;
    private javax.swing.JLabel CustomerName_Label;
    private javax.swing.JPanel Dashboard_Panel;
    private javax.swing.JLabel Dashboard_jLabel;
    private javax.swing.JButton DeleteCompany_Button;
    private javax.swing.JButton DeleteGeneric_Button;
    private javax.swing.JButton DeleteInventory_Button;
    private javax.swing.JButton DeleteMedicine_Button;
    private javax.swing.JButton DeleteUserDetails_Button;
    private javax.swing.JPanel Generic_Panel;
    private javax.swing.JComboBox<String> Generic_jComboBox;
    private javax.swing.JLabel Generic_jLabel;
    private javax.swing.JButton InsertMedicine_Button;
    private javax.swing.JButton InventoryEdit_Button;
    private javax.swing.JPanel Inventory_Panel;
    private javax.swing.JTable Inventory_Table;
    private javax.swing.JLabel Inventory_jLabel;
    private javax.swing.JPanel ItemDetails_Panel;
    private javax.swing.JTable ItemDetails_jTable;
    private javax.swing.JPanel Main_panel;
    private javax.swing.JPanel MedicineDetails_panel;
    private javax.swing.JTable MedicineDetails_table;
    private javax.swing.JTextField MedicineName_TextField;
    private javax.swing.JPanel MedicineShow_jPanel;
    private javax.swing.JTextField MedicineType_TextField;
    private javax.swing.JLabel MediicineDetails_jLabel;
    private javax.swing.JLabel NoExpiresMed_label;
    private javax.swing.JLabel NoExpiresMedshow_jlabel;
    private javax.swing.JLabel NoOfMedicines_jLabel;
    private javax.swing.JLabel NoOfOutOfStockMedicine_jlabel;
    private javax.swing.JLabel NoOfSales_jLabel;
    private javax.swing.JLabel NoOfUsers_jLabel;
    private javax.swing.JPanel OutOfExpireMedicineShow_Panel;
    private javax.swing.JPanel OutOfMedicineShow_Panel;
    private javax.swing.JPasswordField PW_PasswordField;
    private javax.swing.JTextField PurchaseExpireDate_TextField;
    private javax.swing.JComboBox<String> PurchaseMedicineName_jComboBox;
    private javax.swing.JButton PurchaseMedicine_Button;
    private javax.swing.JTextField PurchaseQuantity_TextField;
    private javax.swing.JPanel Purchase_Panel;
    private javax.swing.JComboBox<String> Role_jComboBox;
    private javax.swing.JLabel Role_jLabel;
    private javax.swing.JComboBox<String> SearchItem_Combobox;
    private javax.swing.JButton Search_Button;
    private javax.swing.JTextField Search_textField;
    private javax.swing.JPanel SellingDetails_Panel;
    private javax.swing.JLabel SellingDetails_jLabel;
    private javax.swing.JTable SellingDetails_jTable;
    private javax.swing.JTextField SellingPrice_TextField;
    private javax.swing.JButton SignOut_jButton;
    private javax.swing.JPanel SignUp_panel;
    private javax.swing.JLabel Signup_jLabel;
    private javax.swing.JButton SubmitPurchaseMedicine_Button;
    private javax.swing.JLabel TP_label;
    private javax.swing.JPanel TotalSalesShow_jPanel;
    private javax.swing.JButton UpdateCompany_Button;
    private javax.swing.JButton UpdateGeneric_Button;
    private javax.swing.JButton UpdateMedicine_Button;
    private javax.swing.JButton UpdateUserDetails_Button;
    private javax.swing.JPanel UserDetails_Panel;
    private javax.swing.JTable UserDetails_Table;
    private javax.swing.JLabel UserDetails_jLabel;
    private javax.swing.JLabel UserName_jLabel;
    private javax.swing.JTextArea Uses_jTextArea;
    private javax.swing.JButton ViewDetails_Button;
    private javax.swing.JTextField address_TextField;
    private javax.swing.JPasswordField confirmPW_PasswordField;
    private static javax.swing.JLabel currentDateTime_jLabel;
    private javax.swing.JPanel databaseMaintenence_panel;
    private javax.swing.JButton delete_Button;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTextField phone_TextField;
    private javax.swing.JTable purchase_Table;
    private javax.swing.JLabel purchase_jLabel;
    private javax.swing.JButton register_Button;
    private javax.swing.JLabel sellingDate_label;
    private javax.swing.JPanel systemusershow_panel;
    private javax.swing.JTextField username_update_TextField;
    // End of variables declaration//GEN-END:variables
}
