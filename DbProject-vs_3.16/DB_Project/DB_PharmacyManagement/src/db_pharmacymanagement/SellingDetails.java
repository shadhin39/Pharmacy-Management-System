/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db_pharmacymanagement;

/**
 *
 * @author user
 */
public class SellingDetails {
    
    private int Sid;
    private String UserName,CustomerName,SellingDate;
    private double TotalPrice;
    
    public SellingDetails(int Sid,String UserName,String CustomerName,String SellingDate,Double TotalPrice){
            this.Sid = Sid;
            this.UserName = UserName;
            this.CustomerName = CustomerName;
            this.TotalPrice = TotalPrice;
            this.SellingDate = SellingDate;
            
                    
    }
    public SellingDetails(int Sid,String CustomerName,String SellingDate,Double TotalPrice){
        this.Sid = Sid;
        this.CustomerName = CustomerName;
        this.TotalPrice = TotalPrice;
        this.SellingDate = SellingDate;
    }

    public int getSid() {
        return Sid;
    }

    public String getUserName() {
        return UserName;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public String getSellingDate() {
        return SellingDate;
    }

    public double getTotalPrice() {
        return TotalPrice;
    }

  
    
    
}
