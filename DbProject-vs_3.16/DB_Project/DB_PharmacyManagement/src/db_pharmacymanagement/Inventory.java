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
public class Inventory {
    private int IId,Stock;
    private Double SellingPrice;
    private String MedicineName,MedicineType,Amount,ExpireDate,Category;

    public Inventory(int IId,String MedicineName,String MedicineType,String Category,String Amount,int Stock,Double SellingPrice,String ExpireDate)
    {
        this.IId  = IId;
        this.MedicineName = MedicineName;
        this.Amount = Amount;
        this.ExpireDate = ExpireDate;
        this.MedicineType = MedicineType;
        this.SellingPrice = SellingPrice;
        this.Stock = Stock;
        this.Category = Category;
        
    }
    public int getIId()
    {
        return IId;
    }
    public int getStock()
    {
        return Stock;
    }
    public String getMedicineName()
    {
        return MedicineName;
    }
    public String getAmount(){
        return Amount;
    }
    public String getMedicineType()
    {
        return MedicineType;
    }
    
    public String getExpireDate(){
        return ExpireDate;
    }

    public Double getSellingPrice() {
        return SellingPrice;
    }

    public String getCategory() {
        return Category;
    }
    
    
}
