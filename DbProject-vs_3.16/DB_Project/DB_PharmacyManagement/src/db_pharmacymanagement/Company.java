
package db_pharmacymanagement;


public class Company {
    
    private int Cid;
    private String CompanyName,Address,ContactNumber;
    
    public Company(int Cid,String CompanyName,String address,String ContactNumber){
        this.Cid = Cid;
        this.Address = address;
        this.CompanyName = CompanyName;
        this.ContactNumber = ContactNumber;
    }

    public int getCid() {
        return Cid;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public String getAddress() {
        return Address;
    }

    public String getContactNumber() {
        return ContactNumber;
    }
    
}
