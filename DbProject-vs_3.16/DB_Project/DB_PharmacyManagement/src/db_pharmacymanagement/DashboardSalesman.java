/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db_pharmacymanagement;

import java.awt.Color;
import static java.awt.Frame.ICONIFIED;
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

/**
 *
 * @author user
 */
public class DashboardSalesman extends javax.swing.JFrame {
    Double CartPriceCount = 0.0;
    String UserId = "";
    String currentdate,next7date;
    Double TotalPrice = 0.0;
    String role,Frame = "D";
    int NoOfGeneric=0,NoOfCompany=0,NoOfMedicine=0;
    Object LatestPid;
    Object[] genericName = new Object[200];
    Object[] genericId = new Object[200];
    Object[] Cid = new Object[200];
    Object[] CompanyName = new Object[200];
    Object[] MedicineName = new Object[1000];
    Object[] Mid = new Object[1000];
    int[][] cartInfo = new int[100][2];int NoOfMedinCart=0;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat datetimeformat = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
    Date date = new Date();
    public DashboardSalesman(String UserId) {
        //this.UserId = UserId;
        initComponents();
        // Initialize Date & Time
        currentDateTime_jLabel.setText(sdf.format(date));
        this.UserId = UserId;
        SetNumOfMedicine();
        SetNumOfSell();
        SetNumOfMedInInventory();
        SetOutOfMedicine();
        SetNoExpires();
        setLastOnlineStatus();
        ShowMedicineDetails("","");
        show_Selling("","");
        System.out.println("UserID : "+this.UserId);
        Dashboard_jLabel.setForeground(new Color(97,212,195));
    }
    
    public void UserInfo(String UserName,String Role){
        UserName_jLabel.setText(UserName);
        Role_jLabel.setText(Role);
    }

    public void SetNumOfMedInInventory(){
        String Countnum="";
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url="jdbc:sqlserver://localhost:1433;databaseName=PMS;user=sa;password=123456";
            Connection con = DriverManager.getConnection(url);
            String query1 = "SELECT DISTINCT COUNT(Mid) FROM Inventory";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query1);
            while(rs.next()){
                Countnum = rs.getString(ICONIFIED);
            }
            NoOfMedinInventory_jLabel.setText(Countnum);
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
            String query1 = "SELECT Count(Sid) FROM SellingDetails WHERE Uid = '"+UserId+"'";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query1);
            while(rs.next()){
                Countnum = rs.getString(ICONIFIED);
            }
            System.out.println("CountNum: "+Countnum);
            NoOfSales_jLabel.setText(Countnum);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
            
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
    
    public void SetSerachItem(String frame){
        if(frame == "D"){
            SearchItem_Combobox.removeAllItems();
            
        }else if(frame == "M"){
            SearchItem_Combobox.removeAllItems();
            SearchItem_Combobox.addItem("MedicineName");
            SearchItem_Combobox.addItem("Category");
            SearchItem_Combobox.addItem("MedicineType");
            SearchItem_Combobox.addItem("CompanyName");
            
        }else if(frame == "P"){
            SearchItem_Combobox.removeAllItems();
            SearchItem_Combobox.addItem("MedicineName");
            SearchItem_Combobox.addItem("Category");
            SearchItem_Combobox.addItem("MedicineType");
            SearchItem_Combobox.addItem("CompanyName");
            
        }else if(frame == "I"){
            SearchItem_Combobox.removeAllItems();
            SearchItem_Combobox.addItem("MedicineName");
            SearchItem_Combobox.addItem("MedicineType");
            SearchItem_Combobox.addItem("Category");
            
        }else if(frame == "SD"){
            SearchItem_Combobox.removeAllItems();
            SearchItem_Combobox.addItem("CustomerName");
            //SearchItem_Combobox.addItem("SalesmanName");
            SearchItem_Combobox.addItem("SellingDate");
            
        }else if(frame == "UD"){
            SearchItem_Combobox.removeAllItems();
            SearchItem_Combobox.addItem("UserName");
            SearchItem_Combobox.addItem("Role");
            SearchItem_Combobox.addItem("Address");
        }else if(frame == "SU"){
            SearchItem_Combobox.removeAllItems();
            
        }else if(frame == "C"){
            SearchItem_Combobox.removeAllItems();
            SearchItem_Combobox.addItem("CompanyName");
            SearchItem_Combobox.addItem("Address");
            
        }else if(frame == "G"){
            SearchItem_Combobox.removeAllItems();
            SearchItem_Combobox.addItem("Category");
            
        }else{
            SearchItem_Combobox.removeAllItems();
            
        }
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
    public void show_Selling(String searchkey,String searchCol){
        if(searchCol == "SalesmanName") searchCol = "UserName";
        ArrayList<SellingDetails> sellingDetailsList = new ArrayList<>();
         try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url="jdbc:sqlserver://localhost:1433;databaseName=PMS;user=sa;password=123456";
            Connection con = DriverManager.getConnection(url);
            String query1="SELECT SId,CustomerName,SellingDate,TotalPrice FROM SellingDetails WHERE Uid="+UserId;
            String query2="SELECT SId,CustomerName,SellingDate,TotalPrice FROM SellingDetails WHERE Uid="+UserId+" and "+searchCol+" LIKE '%"+searchkey + "%' ORDER BY SellingDate DESC";
            Statement st= con.createStatement();
            ResultSet rs;
            if(searchkey.isEmpty() || searchCol.isEmpty()){
                rs = st.executeQuery(query1);
            }else{
                rs = st.executeQuery(query2);
            }
            SellingDetails sell;
            while(rs.next()){
                sell=new SellingDetails(rs.getInt("Sid"), rs.getString("CustomerName"), rs.getString("SellingDate"), rs.getDouble("TotalPrice"));
                sellingDetailsList.add(sell);
            }
         }
         catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        
        
        
        DefaultTableModel model = (DefaultTableModel)SellingDetails_jTable.getModel();
        Object[] row = new Object[4];
        int rowCount = model.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            model.removeRow(i);
        }
        for(int i=0;i<sellingDetailsList.size();i++){
            row[0] = sellingDetailsList.get(i).getSid();
            row[1] = sellingDetailsList.get(i).getCustomerName();
            row[2] = sellingDetailsList.get(i).getSellingDate();
            row[3] = sellingDetailsList.get(i).getTotalPrice();
            

            model.addRow(row);
        }
        sellingDetailsList.clear();
    }
    public void showItemDetails(String searchkey,String searchCol,String Sid){
        ArrayList<ItemDetails> itemDetailsList = new ArrayList<>();
         try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url="jdbc:sqlserver://localhost:1433;databaseName=PMS;user=sa;password=123456";
            Connection con = DriverManager.getConnection(url);
            String query1="SELECT MedicineName,MedicineType,Quantity,RetailPrice,TotalPrice FROM Records INNER JOIN Medicine\n" +
"ON Medicine.Mid = Records.Mid WHERE Uid = "+UserId+" and Sid = "+Sid;
            String query2="SELECT MedicineName,MedicineType,Quantity,RetailPrice,TotalPrice FROM Records INNER JOIN Medicine\n" +
"ON Medicine.Mid = Records.Mid WHERE Uid = "+UserId+" and Sid = "+Sid+" and "+searchCol+" LIKE '%"+searchkey + "%'";
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
        jButton1 = new javax.swing.JButton();
        currentDateTime_jLabel = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        Dashboard_jLabel = new javax.swing.JLabel();
        Inventory_jLabel = new javax.swing.JLabel();
        MediicineDetails_jLabel = new javax.swing.JLabel();
        SellingDetails_jLabel = new javax.swing.JLabel();
        Main_panel = new javax.swing.JPanel();
        Dashboard_Panel = new javax.swing.JPanel();
        medInventory_jPanel = new javax.swing.JPanel();
        NoOfMedinInventory_jLabel = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        Salesshow_jPanel = new javax.swing.JPanel();
        NoOfSales_jLabel = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        OutofStockMedshow_panel = new javax.swing.JPanel();
        NoOfOutOfStockMedicine_jlabel = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        expireofmedshow_panel = new javax.swing.JPanel();
        NoExpiresMed_label = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        Medicineshow_jPanel = new javax.swing.JPanel();
        NoOfMedicines_jLabel = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        MedicineDetails_panel = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        MedicineDetails_table = new javax.swing.JTable();
        jPanel13 = new javax.swing.JPanel();
        Inventory_Panel = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        Inventory_Table = new javax.swing.JTable();
        jPanel12 = new javax.swing.JPanel();
        AddToCart_Button = new javax.swing.JButton();
        Cart_Panel = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Cart_jTable = new javax.swing.JTable();
        ClearCart_Button = new javax.swing.JButton();
        EditCart_Button = new javax.swing.JButton();
        Delete_Button = new javax.swing.JButton();
        SaveCart_Button = new javax.swing.JButton();
        TotalPrice_jLabel = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        SellingDetails_Panel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        SellingDetails_jTable = new javax.swing.JTable();
        jPanel14 = new javax.swing.JPanel();
        ViewDetails_Button = new javax.swing.JButton();
        ItemDetails_Panel = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        CustomerName_Label = new javax.swing.JLabel();
        sellingDate_label = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        TP_label = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        ItemDetails_jTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Dashboard");

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

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/db_pharmacymanagement/icons8-shopping-cart-35.png"))); // NOI18N
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        currentDateTime_jLabel.setText("DATE & Time");

        jLabel34.setText("Today :");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(UserName_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(65, 65, 65)
                                .addComponent(Role_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(SignOut_jButton, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(Search_textField, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(Search_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
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
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(SignOut_jButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Role_jLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Search_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Search_textField)
                            .addComponent(SearchItem_Combobox, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addContainerGap(34, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel34)
                            .addComponent(currentDateTime_jLabel))
                        .addGap(24, 24, 24))))
        );

        Dashboard_jLabel.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        Dashboard_jLabel.setForeground(new java.awt.Color(240, 240, 240));
        Dashboard_jLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Dashboard_jLabel.setText("Dashboard");
        Dashboard_jLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Dashboard_jLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Dashboard_jLabelMouseClicked(evt);
            }
        });

        Inventory_jLabel.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        Inventory_jLabel.setForeground(new java.awt.Color(240, 240, 240));
        Inventory_jLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Inventory_jLabel.setText("Inventory");
        Inventory_jLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Inventory_jLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Inventory_jLabelMouseClicked(evt);
            }
        });

        MediicineDetails_jLabel.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        MediicineDetails_jLabel.setForeground(new java.awt.Color(240, 240, 240));
        MediicineDetails_jLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        MediicineDetails_jLabel.setText("Medicine Detalis");
        MediicineDetails_jLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        MediicineDetails_jLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MediicineDetails_jLabelMouseClicked(evt);
            }
        });

        SellingDetails_jLabel.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        SellingDetails_jLabel.setForeground(new java.awt.Color(240, 240, 240));
        SellingDetails_jLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        SellingDetails_jLabel.setText("SellingDetails");
        SellingDetails_jLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SellingDetails_jLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SellingDetails_jLabelMouseClicked(evt);
            }
        });

        Main_panel.setLayout(new java.awt.CardLayout());

        medInventory_jPanel.setBackground(new java.awt.Color(36, 47, 65));
        medInventory_jPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        medInventory_jPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                medInventory_jPanelMouseClicked(evt);
            }
        });

        NoOfMedinInventory_jLabel.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        NoOfMedinInventory_jLabel.setForeground(new java.awt.Color(255, 255, 255));
        NoOfMedinInventory_jLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        NoOfMedinInventory_jLabel.setText("3");

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Medicines in Inventory");

        javax.swing.GroupLayout medInventory_jPanelLayout = new javax.swing.GroupLayout(medInventory_jPanel);
        medInventory_jPanel.setLayout(medInventory_jPanelLayout);
        medInventory_jPanelLayout.setHorizontalGroup(
            medInventory_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(medInventory_jPanelLayout.createSequentialGroup()
                .addGap(83, 83, 83)
                .addComponent(NoOfMedinInventory_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(87, Short.MAX_VALUE))
            .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        medInventory_jPanelLayout.setVerticalGroup(
            medInventory_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(medInventory_jPanelLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(NoOfMedinInventory_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        Salesshow_jPanel.setBackground(new java.awt.Color(36, 47, 65));
        Salesshow_jPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Salesshow_jPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Salesshow_jPanelMouseClicked(evt);
            }
        });

        NoOfSales_jLabel.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        NoOfSales_jLabel.setForeground(new java.awt.Color(255, 255, 255));
        NoOfSales_jLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        NoOfSales_jLabel.setText("3");

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("Sales");

        javax.swing.GroupLayout Salesshow_jPanelLayout = new javax.swing.GroupLayout(Salesshow_jPanel);
        Salesshow_jPanel.setLayout(Salesshow_jPanelLayout);
        Salesshow_jPanelLayout.setHorizontalGroup(
            Salesshow_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Salesshow_jPanelLayout.createSequentialGroup()
                .addGap(83, 83, 83)
                .addComponent(NoOfSales_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(87, Short.MAX_VALUE))
            .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        Salesshow_jPanelLayout.setVerticalGroup(
            Salesshow_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Salesshow_jPanelLayout.createSequentialGroup()
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

        OutofStockMedshow_panel.setBackground(new java.awt.Color(255, 204, 204));
        OutofStockMedshow_panel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        OutofStockMedshow_panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                OutofStockMedshow_panelMouseClicked(evt);
            }
        });

        NoOfOutOfStockMedicine_jlabel.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        NoOfOutOfStockMedicine_jlabel.setForeground(new java.awt.Color(255, 0, 0));
        NoOfOutOfStockMedicine_jlabel.setText("0");

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 0, 51));
        jLabel20.setText("Total Out of Stock Medicines : ");

        javax.swing.GroupLayout OutofStockMedshow_panelLayout = new javax.swing.GroupLayout(OutofStockMedshow_panel);
        OutofStockMedshow_panel.setLayout(OutofStockMedshow_panelLayout);
        OutofStockMedshow_panelLayout.setHorizontalGroup(
            OutofStockMedshow_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, OutofStockMedshow_panelLayout.createSequentialGroup()
                .addContainerGap(382, Short.MAX_VALUE)
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(NoOfOutOfStockMedicine_jlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(417, 417, 417))
        );
        OutofStockMedshow_panelLayout.setVerticalGroup(
            OutofStockMedshow_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OutofStockMedshow_panelLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(OutofStockMedshow_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
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
                .addComponent(OutofStockMedshow_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(OutofStockMedshow_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 1217, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel16.setBackground(new java.awt.Color(51, 51, 255));

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("   Expire Date Alert");

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));

        expireofmedshow_panel.setBackground(new java.awt.Color(255, 204, 204));
        expireofmedshow_panel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        expireofmedshow_panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                expireofmedshow_panelMouseClicked(evt);
            }
        });

        NoExpiresMed_label.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        NoExpiresMed_label.setForeground(new java.awt.Color(255, 0, 0));
        NoExpiresMed_label.setText("0");

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 0, 51));
        jLabel21.setText("No. Medicine Expires in 7 Days :");

        javax.swing.GroupLayout expireofmedshow_panelLayout = new javax.swing.GroupLayout(expireofmedshow_panel);
        expireofmedshow_panel.setLayout(expireofmedshow_panelLayout);
        expireofmedshow_panelLayout.setHorizontalGroup(
            expireofmedshow_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, expireofmedshow_panelLayout.createSequentialGroup()
                .addContainerGap(366, Short.MAX_VALUE)
                .addComponent(jLabel21)
                .addGap(18, 18, 18)
                .addComponent(NoExpiresMed_label, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(417, 417, 417))
        );
        expireofmedshow_panelLayout.setVerticalGroup(
            expireofmedshow_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(expireofmedshow_panelLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(expireofmedshow_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(NoExpiresMed_label, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(37, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(expireofmedshow_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(expireofmedshow_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel17, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 1217, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        Medicineshow_jPanel.setBackground(new java.awt.Color(36, 47, 65));
        Medicineshow_jPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Medicineshow_jPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Medicineshow_jPanelMouseClicked(evt);
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

        javax.swing.GroupLayout Medicineshow_jPanelLayout = new javax.swing.GroupLayout(Medicineshow_jPanel);
        Medicineshow_jPanel.setLayout(Medicineshow_jPanelLayout);
        Medicineshow_jPanelLayout.setHorizontalGroup(
            Medicineshow_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Medicineshow_jPanelLayout.createSequentialGroup()
                .addGap(83, 83, 83)
                .addComponent(NoOfMedicines_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(87, Short.MAX_VALUE))
            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        Medicineshow_jPanelLayout.setVerticalGroup(
            Medicineshow_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Medicineshow_jPanelLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(NoOfMedicines_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout Dashboard_PanelLayout = new javax.swing.GroupLayout(Dashboard_Panel);
        Dashboard_Panel.setLayout(Dashboard_PanelLayout);
        Dashboard_PanelLayout.setHorizontalGroup(
            Dashboard_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Dashboard_PanelLayout.createSequentialGroup()
                .addGap(108, 108, 108)
                .addComponent(Medicineshow_jPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(100, 100, 100)
                .addComponent(medInventory_jPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(105, 105, 105)
                .addComponent(Salesshow_jPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(medInventory_jPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Salesshow_jPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Medicineshow_jPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        Main_panel.add(Dashboard_Panel, "card6");

        MedicineDetails_table.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
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

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1229, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 82, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout MedicineDetails_panelLayout = new javax.swing.GroupLayout(MedicineDetails_panel);
        MedicineDetails_panel.setLayout(MedicineDetails_panelLayout);
        MedicineDetails_panelLayout.setHorizontalGroup(
            MedicineDetails_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MedicineDetails_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        MedicineDetails_panelLayout.setVerticalGroup(
            MedicineDetails_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MedicineDetails_panelLayout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 651, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        Main_panel.add(MedicineDetails_panel, "card3");

        Inventory_Table.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        Inventory_Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "IId", "MedicineName", "MedicineType", "Category", "Amount", "Stock", "Price", "ExpireDate"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Inventory_Table.setToolTipText("");
        Inventory_Table.setRowHeight(22);
        jScrollPane7.setViewportView(Inventory_Table);

        jPanel12.setBackground(new java.awt.Color(36, 47, 65));

        AddToCart_Button.setBackground(new java.awt.Color(36, 47, 65));
        AddToCart_Button.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        AddToCart_Button.setForeground(new java.awt.Color(36, 47, 65));
        AddToCart_Button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/db_pharmacymanagement/back.png"))); // NOI18N
        AddToCart_Button.setText("ADD ITEM");
        AddToCart_Button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AddToCart_Button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        AddToCart_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddToCart_ButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap(997, Short.MAX_VALUE)
                .addComponent(AddToCart_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(AddToCart_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout Inventory_PanelLayout = new javax.swing.GroupLayout(Inventory_Panel);
        Inventory_Panel.setLayout(Inventory_PanelLayout);
        Inventory_PanelLayout.setHorizontalGroup(
            Inventory_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Inventory_PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jScrollPane7)
        );
        Inventory_PanelLayout.setVerticalGroup(
            Inventory_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Inventory_PanelLayout.createSequentialGroup()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 644, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        Main_panel.add(Inventory_Panel, "card7");

        jPanel4.setBackground(new java.awt.Color(97, 212, 195));

        jPanel5.setBackground(new java.awt.Color(97, 212, 195));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Cart");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel3)
                .addContainerGap(310, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel3)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        Cart_jTable.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        Cart_jTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "IId", "MedicineName", "MedicineType", "Amount", "Quantity", "Retail Price", "Total Price"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Cart_jTable.setRowHeight(22);
        jScrollPane1.setViewportView(Cart_jTable);

        ClearCart_Button.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        ClearCart_Button.setForeground(new java.awt.Color(255, 255, 255));
        ClearCart_Button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/db_pharmacymanagement/back2.png"))); // NOI18N
        ClearCart_Button.setText("CLEAR CART");
        ClearCart_Button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        ClearCart_Button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ClearCart_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClearCart_ButtonActionPerformed(evt);
            }
        });

        EditCart_Button.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        EditCart_Button.setForeground(new java.awt.Color(255, 255, 255));
        EditCart_Button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/db_pharmacymanagement/back2.png"))); // NOI18N
        EditCart_Button.setText("EDIT");
        EditCart_Button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        EditCart_Button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        EditCart_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditCart_ButtonActionPerformed(evt);
            }
        });

        Delete_Button.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        Delete_Button.setForeground(new java.awt.Color(255, 255, 255));
        Delete_Button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/db_pharmacymanagement/back2.png"))); // NOI18N
        Delete_Button.setText("DELETE");
        Delete_Button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Delete_Button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Delete_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Delete_ButtonActionPerformed(evt);
            }
        });

        SaveCart_Button.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        SaveCart_Button.setForeground(new java.awt.Color(255, 255, 255));
        SaveCart_Button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/db_pharmacymanagement/back2.png"))); // NOI18N
        SaveCart_Button.setText("SAVE");
        SaveCart_Button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SaveCart_Button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        SaveCart_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveCart_ButtonActionPerformed(evt);
            }
        });

        TotalPrice_jLabel.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        TotalPrice_jLabel.setText("0");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel5.setText("Total Price : ");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SaveCart_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Delete_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(EditCart_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ClearCart_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
            .addComponent(jScrollPane1)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(TotalPrice_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ClearCart_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(EditCart_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Delete_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(SaveCart_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 552, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TotalPrice_jLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout Cart_PanelLayout = new javax.swing.GroupLayout(Cart_Panel);
        Cart_Panel.setLayout(Cart_PanelLayout);
        Cart_PanelLayout.setHorizontalGroup(
            Cart_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        Cart_PanelLayout.setVerticalGroup(
            Cart_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        Main_panel.add(Cart_Panel, "card5");

        SellingDetails_jTable.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        SellingDetails_jTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Sid", "CustomerName", "SellingDate", "TotalPrice"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        SellingDetails_jTable.setRowHeight(22);
        jScrollPane2.setViewportView(SellingDetails_jTable);

        jPanel14.setBackground(new java.awt.Color(97, 212, 195));

        ViewDetails_Button.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        ViewDetails_Button.setForeground(new java.awt.Color(255, 255, 255));
        ViewDetails_Button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/db_pharmacymanagement/back2.png"))); // NOI18N
        ViewDetails_Button.setText("View Details");
        ViewDetails_Button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        ViewDetails_Button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ViewDetails_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ViewDetails_ButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ViewDetails_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap(35, Short.MAX_VALUE)
                .addComponent(ViewDetails_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
        );

        javax.swing.GroupLayout SellingDetails_PanelLayout = new javax.swing.GroupLayout(SellingDetails_Panel);
        SellingDetails_Panel.setLayout(SellingDetails_PanelLayout);
        SellingDetails_PanelLayout.setHorizontalGroup(
            SellingDetails_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1253, Short.MAX_VALUE)
            .addGroup(SellingDetails_PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        SellingDetails_PanelLayout.setVerticalGroup(
            SellingDetails_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SellingDetails_PanelLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 635, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        Main_panel.add(SellingDetails_Panel, "card6");

        jPanel15.setBackground(new java.awt.Color(97, 212, 195));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel4.setText("Item Details of Customer : ");

        CustomerName_Label.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        CustomerName_Label.setText("ABC");

        sellingDate_label.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N
        sellingDate_label.setText("2019-01-01");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N
        jLabel7.setText("DATE : ");

        TP_label.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N
        TP_label.setText("Total Price : ");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(362, 362, 362)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(CustomerName_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGap(0, 89, Short.MAX_VALUE)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sellingDate_label, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(598, 598, 598))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(TP_label, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CustomerName_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        jScrollPane4.setViewportView(ItemDetails_jTable);

        javax.swing.GroupLayout ItemDetails_PanelLayout = new javax.swing.GroupLayout(ItemDetails_Panel);
        ItemDetails_Panel.setLayout(ItemDetails_PanelLayout);
        ItemDetails_PanelLayout.setHorizontalGroup(
            ItemDetails_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane4)
        );
        ItemDetails_PanelLayout.setVerticalGroup(
            ItemDetails_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ItemDetails_PanelLayout.createSequentialGroup()
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 617, Short.MAX_VALUE))
        );

        Main_panel.add(ItemDetails_Panel, "card7");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Dashboard_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(MediicineDetails_jLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(Inventory_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(SellingDetails_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Main_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(233, 233, 233)
                .addComponent(Dashboard_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(MediicineDetails_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Inventory_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SellingDetails_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void SignOut_jButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SignOut_jButtonActionPerformed
        Login login = new Login();
        login.setVisible(true);
        setVisible(false);
    }//GEN-LAST:event_SignOut_jButtonActionPerformed

    private void Search_textFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Search_textFieldFocusGained
        Search_textField.setText("");
    }//GEN-LAST:event_Search_textFieldFocusGained

    private void Search_textFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Search_textFieldActionPerformed
        Search_textField.setText("");
    }//GEN-LAST:event_Search_textFieldActionPerformed

    private void Dashboard_jLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Dashboard_jLabelMouseClicked
        // TODO add your handling code here:
        Dashboard_jLabel.setForeground(new Color(97,212,195));
        MediicineDetails_jLabel.setForeground(new Color(240,240,240));
        Inventory_jLabel.setForeground(new Color(240,240,240));
        SellingDetails_jLabel.setForeground(new Color(240,240,240));
        System.out.println("Dashboard clicked");
        
        // Show updated Value
        SetNumOfMedicine();
        SetNumOfSell();
        SetNumOfMedInInventory();
        SetOutOfMedicine();
        SetSerachItem("D");
        Frame = "D";
        //Main Works

        Main_panel.removeAll();
        Main_panel.add(Dashboard_Panel);
        Main_panel.repaint();
        Main_panel.revalidate();

    }//GEN-LAST:event_Dashboard_jLabelMouseClicked

    private void MediicineDetails_jLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MediicineDetails_jLabelMouseClicked
        // TODO add your handling code here:
        Dashboard_jLabel.setForeground(new Color(240,240,240));
        MediicineDetails_jLabel.setForeground(new Color(97,212,195));
        Inventory_jLabel.setForeground(new Color(240,240,240));
        SellingDetails_jLabel.setForeground(new Color(240,240,240));
        System.out.println("Medicine Details clicked");
        Frame = "M";
        SetSerachItem("M");
        ShowMedicineDetails("","");
        

        Main_panel.removeAll();
        Main_panel.add(MedicineDetails_panel);
        Main_panel.repaint();
        Main_panel.revalidate();
    }//GEN-LAST:event_MediicineDetails_jLabelMouseClicked

    private void Search_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Search_ButtonActionPerformed
        try{
            if(SearchItem_Combobox.getSelectedItem().toString().isEmpty() || Search_textField.getText().toString().isEmpty() ||
                    Search_textField.getText().toString().equals("Search")){
                // It will freeze
            }else{
                String SearchCol = SearchItem_Combobox.getSelectedItem().toString();
                String searchkey = Search_textField.getText().toString();
                if(Frame == "M"){
                    ShowMedicineDetails(searchkey,SearchCol);
                }else if(Frame == "I"){
                    ShowInventory(searchkey, SearchCol);
                }else if(Frame == "SD"){
                    show_Selling(searchkey, SearchCol);
                }else{
                    // Do nothing
                }
            }
            
        }catch(Exception e){
            System.err.println("U are doing something unnatural thing that can not be functional");
            
        }
    }//GEN-LAST:event_Search_ButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        
        Dashboard_jLabel.setForeground(new Color(240,240,240));
        MediicineDetails_jLabel.setForeground(new Color(240,240,240));
        Inventory_jLabel.setForeground(new Color(240,240,240));
        
        int rowcount = Cart_jTable.getModel().getRowCount();
        CartPriceCount = 0.0;
        for(int i=0; i<rowcount; i++){
            CartPriceCount +=(Double)Cart_jTable.getModel().getValueAt(i, 6);
        }
        TotalPrice_jLabel.setText(CartPriceCount.toString());
        Frame = "Cart";
        SetSerachItem("Cart");
        
        Main_panel.removeAll();
        Main_panel.add(Cart_Panel);
        Main_panel.repaint();
        Main_panel.revalidate();
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void Delete_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Delete_ButtonActionPerformed

        int selectedrow = Cart_jTable.getSelectedRow();
        if(selectedrow == -1){
            JOptionPane.showMessageDialog(null, "Please Select any Medicine first");
        }else{
            
            int response = JOptionPane.showConfirmDialog(this,"Do u want to Clear the Cart","Clear Cart?",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if(response == JOptionPane.YES_OPTION){
                DefaultTableModel model  = (DefaultTableModel)Cart_jTable.getModel();
                model.removeRow(selectedrow);
            }
            Cart_jTable.clearSelection();
            
            // Update CartPRice
           int rowcount = Cart_jTable.getModel().getRowCount();
           CartPriceCount = 0.0;
            for(int i=0; i<rowcount; i++){
                CartPriceCount +=(Double)Cart_jTable.getModel().getValueAt(i, 6);
            }
            TotalPrice_jLabel.setText(CartPriceCount.toString()); 
            
        }
    }//GEN-LAST:event_Delete_ButtonActionPerformed

    private void ClearCart_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClearCart_ButtonActionPerformed
        int response = JOptionPane.showConfirmDialog(this,"Do u want to Clear the Cart","Clear Cart?",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if(response == JOptionPane.YES_OPTION){
            DefaultTableModel Model = (DefaultTableModel) Cart_jTable.getModel();
            int rowCount = Model.getRowCount();
            for (int i = rowCount - 1; i >= 0; i--) {
                Model.removeRow(i);
            }
            TotalPrice_jLabel.setText("0");
            NoOfMedinCart = 0;
        }else if(response == JOptionPane.NO_OPTION){
            
        }else if(response == JOptionPane.CANCEL_OPTION){
            
        }
    }//GEN-LAST:event_ClearCart_ButtonActionPerformed

    private void EditCart_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditCart_ButtonActionPerformed
        int selectedrow = Cart_jTable.getSelectedRow();
        Boolean successToedit = false;
        if(selectedrow == -1){
            JOptionPane.showMessageDialog(null, "Please Select any Medicine first");
        }else{
            try{
                int iid = Integer.parseInt(Cart_jTable.getModel().getValueAt(selectedrow, 0).toString());
                //int prevquantity = Integer.parseInt(Cart_jTable.getModel().getValueAt(selectedrow, 4).toString());
                
                Object rowData[] = new Object[7];
                rowData[0] = Cart_jTable.getModel().getValueAt(selectedrow, 0);
                rowData[1] = Cart_jTable.getModel().getValueAt(selectedrow, 1);
                rowData[2] = Cart_jTable.getModel().getValueAt(selectedrow, 2);
                rowData[3] = Cart_jTable.getModel().getValueAt(selectedrow, 3);
                Object selectedQuantity = Cart_jTable.getModel().getValueAt(selectedrow, 4);
                rowData[5] = Cart_jTable.getModel().getValueAt(selectedrow, 5);
                double price = (Double)Cart_jTable.getModel().getValueAt(selectedrow, 5);
                int quantity = Integer.parseInt(JOptionPane.showInputDialog(null,"Enter Quantity :",selectedQuantity).toString());
                 // check if user want to add more quantity then stock
                for(int i=0; i<NoOfMedinCart; i++){
                    if(iid == cartInfo[i][0]){
                        if(quantity <= cartInfo[i][1]){
                            rowData[4] = quantity;
                            rowData[6] = (double)price*quantity;

                            DefaultTableModel model = (DefaultTableModel)Cart_jTable.getModel();
                            model.setValueAt(quantity, selectedrow, 4);
                            model.setValueAt(rowData[6], selectedrow, 6);
                            
                            Cart_jTable.clearSelection();
                            int rowcount = Cart_jTable.getModel().getRowCount();
                            CartPriceCount = 0.0;
                            for(int j=0; j<rowcount; j++){
                                CartPriceCount +=(Double)Cart_jTable.getModel().getValueAt(j, 6);
                            }
                            TotalPrice_jLabel.setText(CartPriceCount.toString());
                            successToedit = true;
                        }
                    }
                }
                
                if(successToedit == false){
                    // insufficient stock
                }else{
                    JOptionPane.showMessageDialog(null, "Successfully Edited");
                }
                
                
            }catch(Exception e){
                JOptionPane.showMessageDialog(null,"Operation Canceled");
                Cart_jTable.clearSelection();
            }
            
        }
        
        
    }//GEN-LAST:event_EditCart_ButtonActionPerformed

    private void AddToCart_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddToCart_ButtonActionPerformed
        int selectedrow = Inventory_Table.getSelectedRow();
        if(selectedrow == -1){
            JOptionPane.showMessageDialog(null, "Please Select any Medicine first");
        }else{
            try{
                int quan = Integer.parseInt(JOptionPane.showInputDialog(null,"Enter Quantity :"));
                int stock = Integer.parseInt(Inventory_Table.getModel().getValueAt(selectedrow, 5).toString());
                int Iid = Integer.parseInt(Inventory_Table.getModel().getValueAt(selectedrow, 0).toString());
                if(stock >= quan){
                    Object rowData[] = new Object[7];
                    rowData[0] = Inventory_Table.getModel().getValueAt(selectedrow, 0);
                    rowData[1] = Inventory_Table.getModel().getValueAt(selectedrow, 1);
                    rowData[2] = Inventory_Table.getModel().getValueAt(selectedrow, 2);
                    rowData[3] = Inventory_Table.getModel().getValueAt(selectedrow, 4);
                    Object selectedStock = Inventory_Table.getModel().getValueAt(selectedrow, 5);
                    rowData[5] = Inventory_Table.getModel().getValueAt(selectedrow, 6);
                    double price = (Double)Inventory_Table.getModel().getValueAt(selectedrow, 6);
                    int quantity = quan;
                    rowData[4] = quantity;
                    rowData[6] = (double)price*quantity;


                    // add row To the jtable
                     DefaultTableModel model = (DefaultTableModel)Cart_jTable.getModel();
                     model.addRow(rowData);
                     JOptionPane.showMessageDialog(null, "Successfully Added to the cart");
                     // add Details in cartInfo array
                     cartInfo[NoOfMedinCart][0] = Iid;    // store inventory id
                     cartInfo[NoOfMedinCart][1] = stock;     // Store stock
                     NoOfMedinCart++;
                     Inventory_Table.clearSelection();
                }else{
                    JOptionPane.showMessageDialog(null, "You dont have sufficient Medicine Quantity to buy");
                    Inventory_Table.clearSelection();
                }
                
            }catch(Exception e){
                System.out.println("Something illogical things occur due to cancel the adding item in the cart");
            }
            
            
        }

        
    }//GEN-LAST:event_AddToCart_ButtonActionPerformed

    private void SaveCart_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveCart_ButtonActionPerformed
        
        try{
            String insertedId="";
            Boolean isOutofStock = false;
            // Generate Current Date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            //System.out.println(sdf.format(date));
            
            
            DefaultTableModel Model = (DefaultTableModel) Cart_jTable.getModel();
            int rowCount = Model.getRowCount();
            if(rowCount==0){
                JOptionPane.showMessageDialog(null, "Please add to cart first");
            }else{
                // check whether quantity is less then stock
                // special case checking
                for(int i=0; i<rowCount; i++){
                    int id = Integer.parseInt(Model.getValueAt(i, 0).toString());
                    int quan = Integer.parseInt(Model.getValueAt(i, 4).toString());
                    for(int j=0 ; j<NoOfMedinCart; j++){
                        if(id == cartInfo[j][0]){
                            cartInfo[j][1]-= quan;
                            if(cartInfo[j][1] <0){
                                isOutofStock = true;
                            }
                        }
                    }
                }
                if(isOutofStock == true){       
                    JOptionPane.showMessageDialog(null, "Cannot save the cart due to insufficient Stock of medicine");
                    TotalPrice = 0.0;
                    TotalPrice_jLabel.setText("0");
                }else{
                        String CustomerName = JOptionPane.showInputDialog(this,"Enter Customer Name");
                        for (int i = rowCount - 1; i >= 0; i--) {
                            TotalPrice+= (Double)Model.getValueAt(i, 6);
                        }
                        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                        String url="jdbc:sqlserver://localhost:1433;databaseName=PMS;user=sa;password=123456";
                        Connection con = DriverManager.getConnection(url);
                        String sql = "INSERT INTO SellingDetails(Uid,CustomerName,SellingDate,TotalPrice)\n" +
                            "  VALUES(?,?,?,?)"; //SELECT SCOPE_IDENTITY()

                        PreparedStatement pst = con.prepareStatement(sql);
                        pst.setString(1, UserId);
                        pst.setString(2, CustomerName);
                        pst.setString(3, sdf.format(date));
                        pst.setString(4, TotalPrice.toString());
                        pst.executeUpdate();
                        con.close();
                        //JOptionPane.showMessageDialog(null, "Successfully Registered");

                        // Fetch Sid from Database
                        Connection con3 = DriverManager.getConnection(url);
                        String query = "SELECT SId from SellingDetails WHERE Uid = '"+UserId+"' and \n" +
        "CustomerName = '"+CustomerName+"' and SellingDate = '"+sdf.format(date)+"'\n" +
        "and TotalPrice = '"+TotalPrice.toString()+"'";
                        Statement st = con3.createStatement();
                        ResultSet rs = st.executeQuery(query);
                        if(rs.next()){
                            insertedId = rs.getString(ICONIFIED);
                        }
                        con3.close();


                        // Find Medicine

                        String[][] MedDetails = new String[rowCount][5]; 
                        for(int i=0; i<rowCount; i++){
                            String med = Cart_jTable.getModel().getValueAt(i, 1).toString();
                            String quantity = Cart_jTable.getModel().getValueAt(i, 4).toString();
                            String RetailPrice  = Cart_jTable.getModel().getValueAt(i, 5).toString();
                            //System.out.println("MEd:"+med + " Quantity : "+quantity+" RP : "+RetailPrice);
                            for(int j=0; j<NoOfMedicine; j++){
                                if(med.equalsIgnoreCase(MedicineName[j].toString())){
                                    MedDetails[i][0] = Mid[j].toString();
                                    MedDetails[i][1] = quantity;
                                    MedDetails[i][2] = RetailPrice;
                                    double temp = Integer.parseInt(quantity)*Double.parseDouble(RetailPrice);
                                    MedDetails[i][3] = Double.toString(temp);
                                    MedDetails[i][4] = Cart_jTable.getModel().getValueAt(i, 0).toString();
                                }
                            }
                        }
                        // Decrease Medicine Quantity in the Inventory Table

                        for(int i=0; i<rowCount; i++){
                            Connection con5 = DriverManager.getConnection(url);
                            String sql5 = "UPDATE Inventory SET STOCK = STOCK- ? WHERE IId = ?";
                            PreparedStatement pst5 = con5.prepareStatement(sql5);
                            pst5.setString(1, MedDetails[i][1]);
                            pst5.setString(2, MedDetails[i][4]);
                            pst5.executeUpdate();
                            con5.close();
                        }


                        // Store every item in Records Table


                        for(int i=0; i<rowCount; i++){
                            Connection con1 = DriverManager.getConnection(url);
                            String Sql1 = "INSERT INTO Records(Uid,Mid,Sid,Quantity,RetailPrice,TotalPrice)\n" +
        "VALUES(?,?,?,?,?,?)";
                            PreparedStatement pst1 = con1.prepareStatement(Sql1);
                            pst1.setString(1, UserId);
                            pst1.setString(2, MedDetails[i][0]);
                            pst1.setString(3, insertedId);      /// Must be fixed
                            pst1.setString(4, MedDetails[i][1]);
                            pst1.setString(5, MedDetails[i][2]);
                            pst1.setString(6, MedDetails[i][3]);
                            pst1.executeUpdate();
                            con1.close();
                        }
                        System.out.println("Total Price : "+TotalPrice);
                        TotalPrice_jLabel.setText("0");
                        JOptionPane.showMessageDialog(null, "Successfully done");
                    }


                    TotalPrice = 0.0;
                    for (int i = rowCount - 1; i >= 0; i--) {
                            Model.removeRow(i);
                    }
                    NoOfMedinCart = 0;
                }
                
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
            
        
//        
    }//GEN-LAST:event_SaveCart_ButtonActionPerformed

    private void SellingDetails_jLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SellingDetails_jLabelMouseClicked
        Dashboard_jLabel.setForeground(new Color(240,240,240));
        MediicineDetails_jLabel.setForeground(new Color(240,240,240));
        SellingDetails_jLabel.setForeground(new Color(97,212,195));
        Inventory_jLabel.setForeground(new Color(240,240,240));
        System.out.println("SellingDetails clicked");
        Frame = "SD";
        SetSerachItem("SD");
        show_Selling("", "");

        Main_panel.removeAll();
        Main_panel.add(SellingDetails_Panel);
        Main_panel.repaint();
        Main_panel.revalidate();

    }//GEN-LAST:event_SellingDetails_jLabelMouseClicked

    private void Inventory_jLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Inventory_jLabelMouseClicked
        Dashboard_jLabel.setForeground(new Color(240,240,240));
        MediicineDetails_jLabel.setForeground(new Color(240,240,240));
        Inventory_jLabel.setForeground(new Color(97,212,195));
        SellingDetails_jLabel.setForeground(new Color(240,240,240));
        System.out.println("Invenotry clicked");
        Frame = "I";
        SetSerachItem("I");
        ShowInventory("", "");
        
        
        Main_panel.removeAll();
        Main_panel.add(Inventory_Panel);
        Main_panel.repaint();
        Main_panel.revalidate();
    }//GEN-LAST:event_Inventory_jLabelMouseClicked

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
            SetSerachItem(Frame);

            Main_panel.removeAll();
            Main_panel.add(ItemDetails_Panel);
            Main_panel.repaint();
            Main_panel.revalidate();
            TP_label.setText("Total Price: ");
            
            String Sid = SellingDetails_jTable.getModel().getValueAt(selectedrow, 0).toString();
            String CustomerName = SellingDetails_jTable.getModel().getValueAt(selectedrow, 1).toString();
            String SellingDate = SellingDetails_jTable.getModel().getValueAt(selectedrow, 2).toString();
            String TotalPrice = SellingDetails_jTable.getModel().getValueAt(selectedrow, 3).toString();
            showItemDetails("", "",Sid);
            CustomerName_Label.setText(CustomerName);
            sellingDate_label.setText(SellingDate);
            TP_label.setText(TP_label.getText()+" "+TotalPrice);
        }
        
        
    }//GEN-LAST:event_ViewDetails_ButtonActionPerformed

    private void Medicineshow_jPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Medicineshow_jPanelMouseClicked
        Dashboard_jLabel.setForeground(new Color(240,240,240));
        MediicineDetails_jLabel.setForeground(new Color(97,212,195));
        Inventory_jLabel.setForeground(new Color(240,240,240));
        SellingDetails_jLabel.setForeground(new Color(240,240,240));
        System.out.println("Medicine Details clicked");
        Frame = "M";
        SetSerachItem("M");
        ShowMedicineDetails("","");
        

        Main_panel.removeAll();
        Main_panel.add(MedicineDetails_panel);
        Main_panel.repaint();
        Main_panel.revalidate();
    }//GEN-LAST:event_Medicineshow_jPanelMouseClicked

    private void medInventory_jPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_medInventory_jPanelMouseClicked
        Dashboard_jLabel.setForeground(new Color(240,240,240));
        MediicineDetails_jLabel.setForeground(new Color(240,240,240));
        Inventory_jLabel.setForeground(new Color(97,212,195));
        SellingDetails_jLabel.setForeground(new Color(240,240,240));
        System.out.println("Invenotry clicked");
        Frame = "I";
        SetSerachItem("I");
        ShowInventory("", "");
        
        
        Main_panel.removeAll();
        Main_panel.add(Inventory_Panel);
        Main_panel.repaint();
        Main_panel.revalidate();
    }//GEN-LAST:event_medInventory_jPanelMouseClicked

    private void Salesshow_jPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Salesshow_jPanelMouseClicked
        Dashboard_jLabel.setForeground(new Color(240,240,240));
        MediicineDetails_jLabel.setForeground(new Color(240,240,240));
        SellingDetails_jLabel.setForeground(new Color(97,212,195));
        Inventory_jLabel.setForeground(new Color(240,240,240));
        System.out.println("SellingDetails clicked");
        Frame = "SD";
        SetSerachItem("SD");
        show_Selling("", "");

        Main_panel.removeAll();
        Main_panel.add(SellingDetails_Panel);
        Main_panel.repaint();
        Main_panel.revalidate();
    }//GEN-LAST:event_Salesshow_jPanelMouseClicked

    private void OutofStockMedshow_panelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_OutofStockMedshow_panelMouseClicked
        System.out.println("outOfMedicineShow is Clicked");
        // Create CardView Panel Inventory
        Dashboard_jLabel.setForeground(new Color(240,240,240));
        MediicineDetails_jLabel.setForeground(new Color(240,240,240));
        Inventory_jLabel.setForeground(new Color(97,212,195));
        SellingDetails_jLabel.setForeground(new Color(240,240,240));
        System.out.println("Invenotry clicked");
        Frame = "I";
        SetSerachItem("I");
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
    }//GEN-LAST:event_OutofStockMedshow_panelMouseClicked

    private void expireofmedshow_panelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_expireofmedshow_panelMouseClicked
        System.out.println("outOfMedicineShow is Clicked");
        // Create CardView Panel Inventory
        Dashboard_jLabel.setForeground(new Color(240,240,240));
        MediicineDetails_jLabel.setForeground(new Color(240,240,240));
        Inventory_jLabel.setForeground(new Color(97,212,195));
        SellingDetails_jLabel.setForeground(new Color(240,240,240));
        System.out.println("Invenotry clicked");
        Frame = "I";
        SetSerachItem("I");
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
    }//GEN-LAST:event_expireofmedshow_panelMouseClicked

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(DashboardSalesman.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DashboardSalesman.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DashboardSalesman.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DashboardSalesman.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DashboardSalesman("").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddToCart_Button;
    private javax.swing.JPanel Cart_Panel;
    private javax.swing.JTable Cart_jTable;
    private javax.swing.JButton ClearCart_Button;
    private javax.swing.JLabel CustomerName_Label;
    private javax.swing.JPanel Dashboard_Panel;
    private javax.swing.JLabel Dashboard_jLabel;
    private javax.swing.JButton Delete_Button;
    private javax.swing.JButton EditCart_Button;
    private javax.swing.JPanel Inventory_Panel;
    private javax.swing.JTable Inventory_Table;
    private javax.swing.JLabel Inventory_jLabel;
    private javax.swing.JPanel ItemDetails_Panel;
    private javax.swing.JTable ItemDetails_jTable;
    private javax.swing.JPanel Main_panel;
    private javax.swing.JPanel MedicineDetails_panel;
    private javax.swing.JTable MedicineDetails_table;
    private javax.swing.JPanel Medicineshow_jPanel;
    private javax.swing.JLabel MediicineDetails_jLabel;
    private javax.swing.JLabel NoExpiresMed_label;
    private javax.swing.JLabel NoOfMedicines_jLabel;
    private javax.swing.JLabel NoOfMedinInventory_jLabel;
    private javax.swing.JLabel NoOfOutOfStockMedicine_jlabel;
    private javax.swing.JLabel NoOfSales_jLabel;
    private javax.swing.JPanel OutofStockMedshow_panel;
    private javax.swing.JLabel Role_jLabel;
    private javax.swing.JPanel Salesshow_jPanel;
    private javax.swing.JButton SaveCart_Button;
    private javax.swing.JComboBox<String> SearchItem_Combobox;
    private javax.swing.JButton Search_Button;
    private javax.swing.JTextField Search_textField;
    private javax.swing.JPanel SellingDetails_Panel;
    private javax.swing.JLabel SellingDetails_jLabel;
    private javax.swing.JTable SellingDetails_jTable;
    private javax.swing.JButton SignOut_jButton;
    private javax.swing.JLabel TP_label;
    private javax.swing.JLabel TotalPrice_jLabel;
    private javax.swing.JLabel UserName_jLabel;
    private javax.swing.JButton ViewDetails_Button;
    private javax.swing.JLabel currentDateTime_jLabel;
    private javax.swing.JPanel expireofmedshow_panel;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JPanel medInventory_jPanel;
    private javax.swing.JLabel sellingDate_label;
    // End of variables declaration//GEN-END:variables
}
