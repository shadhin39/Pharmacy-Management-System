
package db_pharmacymanagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;


public class ExecuteQuery extends javax.swing.JFrame {

    
    public ExecuteQuery() {
        initComponents();
        
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        queryInput_jTextArea = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        execute_Button = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        Execution_Table = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Database Maintenance");

        jPanel1.setBackground(new java.awt.Color(36, 47, 65));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(240, 240, 240));
        jLabel1.setText("Database Maintenance");

        queryInput_jTextArea.setBackground(new java.awt.Color(97, 212, 195));
        queryInput_jTextArea.setColumns(20);
        queryInput_jTextArea.setFont(new java.awt.Font("Courier New", 1, 20)); // NOI18N
        queryInput_jTextArea.setRows(5);
        jScrollPane1.setViewportView(queryInput_jTextArea);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(240, 240, 240));
        jLabel2.setText("Enter Query :");

        execute_Button.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        execute_Button.setForeground(new java.awt.Color(36, 47, 65));
        execute_Button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/db_pharmacymanagement/back.png"))); // NOI18N
        execute_Button.setText("Execute Query");
        execute_Button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        execute_Button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        execute_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                execute_ButtonActionPerformed(evt);
            }
        });

        Execution_Table.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        Execution_Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        Execution_Table.setRowHeight(22);
        jScrollPane2.setViewportView(Execution_Table);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(execute_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(579, 579, 579))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(536, 536, 536))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1072, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1405, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(execute_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 425, Short.MAX_VALUE)
                .addGap(39, 39, 39))
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

    private void execute_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_execute_ButtonActionPerformed
        /*int CountCol = 0;
        Object[] colNames = new Object[15];
        if(queryInput_jTextArea.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Enter a valid query to execute");
        }else{
            try{
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                String url="jdbc:sqlserver://localhost:1433;databaseName=PMS;user=sa;password=123456";
                Connection con = DriverManager.getConnection(url);
                String query1 = queryInput_jTextArea.getText();
                String query2 = "SELECT name FROM sys.dm_exec_describe_first_result_set('"+query1+"', NULL, 0)";  //alternative
                String query3 = "select top 0 s.* into _TempTableForColumns from ("+query1+") s;\n" +
"select * from information_schema.columns where table_name = '_TempTableForColumns' ;\n" +
"drop table _TempTableForColumns;";
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(query3);
                while(rs.next()){
                    colNames[CountCol] = rs.getString("COLUMN_NAME"); 
                    CountCol++;
                }
                //printing col names
                System.out.println("CountCol : "+CountCol);
                for(int i=0 ;i <CountCol; i++){
                    System.out.println(colNames[i]);
                }
                
                con.close();
                DefaultTableModel model =(DefaultTableModel) Execution_Table.getModel();
                int PrevrowCount = model.getRowCount();
                // remove row and column data
                model.setColumnCount(0);
                for (int i = PrevrowCount - 1; i >= 0; i--) {
                    model.removeRow(i);
                }
                // add column data
                for (int i = 0; i < CountCol; i++) {
                    model.addColumn(colNames[i]);
                }
                Connection con1 = DriverManager.getConnection(url);
                Statement st1 = con1.createStatement();
                ResultSet rs1 = st1.executeQuery(query1);
                Object[] rowData = new Object[CountCol+1];
                // fetching resultant data from database
                while(rs1.next()){
                    for(int i=0; i<CountCol; i++){
                        rowData[i] = rs1.getString(colNames[i].toString());
                    }
                    model.addRow(rowData);
                }
                con1.close();
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, e);

            }
        
        }*/
        try{
            String query = queryInput_jTextArea.getText().trim();
            String keyword = query.substring(0,query.indexOf(" "));
            if(keyword.toLowerCase().equals("select")){
                System.out.println("select");
                Execution_Table.setModel( DbUtils.resultSetToTableModel ( Execute.selectquery(queryInput_jTextArea.getText())));

            }else if(keyword.toLowerCase().equals("update")){
                System.out.println("update");
                Execute.updatequery(query);

            }else if(keyword.toLowerCase().equals("insert")){
                System.out.println("insert");
                Execute.insertquery(query);

            }else if(keyword.toLowerCase().equals("delete")){
                System.out.println("delete");
                Execute.deletequery(query);
            }
            
        }catch(Exception e){
            System.out.println(e);
            
        }
        
        
        
    }//GEN-LAST:event_execute_ButtonActionPerformed

    
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
            java.util.logging.Logger.getLogger(ExecuteQuery.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ExecuteQuery.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ExecuteQuery.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ExecuteQuery.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ExecuteQuery().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Execution_Table;
    private javax.swing.JButton execute_Button;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea queryInput_jTextArea;
    // End of variables declaration//GEN-END:variables
}
