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
public class Medicine {
    private int Mid;
    private String MedicineName,Amount,Category,MedicineType,CompanyName;
    public Medicine(int Mid,String MedicineName,String Amount,String Category,String MedicineType,String CompanyName){
        this.Mid = Mid;
        this.MedicineName = MedicineName;
        this.MedicineType = MedicineType;
        this.Category = Category;
        this.Amount = Amount;
        this.CompanyName = CompanyName;
        
    }
    
    public int getMid()
    {
        return Mid;
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
    public String getCompanyName()
    {
        return CompanyName;
    }
    public String getCategory()
    {
        return Category;
    }
    
}
