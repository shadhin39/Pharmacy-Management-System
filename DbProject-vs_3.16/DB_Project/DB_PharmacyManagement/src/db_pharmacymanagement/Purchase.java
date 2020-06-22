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
public class Purchase {
    
    private int Pid,Quantity;
    private String MedicineName,Category,CompanyName,MedicineType;
    private double CostPrice,SellingPrice;
    
    public Purchase(int Pid,String MedicineName,String Category,String CompanyName,String MedicineType,int Quantity,Double SellingPrice,Double CostPrice){
            this.Pid = Pid;
            this.MedicineName = MedicineName;
            this.Category = Category;
            this.CostPrice = CostPrice;
            this.MedicineType = MedicineType;
            this.SellingPrice = SellingPrice;
            this.CompanyName = CompanyName;
            this.Quantity = Quantity;
                    
    }

    public int getPid() {
        return Pid;
    }

    public int getQuantity() {
        return Quantity;
    }

    public String getMedicineName() {
        return MedicineName;
    }

    public String getCategory() {
        return Category;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public String getMedicineType() {
        return MedicineType;
    }

    public double getCostPrice() {
        return CostPrice;
    }

    public double getSellingPrice() {
        return SellingPrice;
    }
    
    
}
