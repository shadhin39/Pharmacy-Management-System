/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db_pharmacymanagement;

public class ItemDetails {
    String MedicineName,MedicineType;
    int Quantity;
    Double RetailPrice,TotalPrice;

    public ItemDetails(String MedicineName, String MedicineType, int Quantity, Double RetailPrice, Double TotalPrice) {
        this.MedicineName = MedicineName;
        this.MedicineType = MedicineType;
        this.Quantity = Quantity;
        this.RetailPrice = RetailPrice;
        this.TotalPrice = TotalPrice;
    }

    public String getMedicineName() {
        return MedicineName;
    }

    public String getMedicineType() {
        return MedicineType;
    }

    public int getQuantity() {
        return Quantity;
    }

    public Double getRetailPrice() {
        return RetailPrice;
    }

    public Double getTotalPrice() {
        return TotalPrice;
    }

    
    
}
