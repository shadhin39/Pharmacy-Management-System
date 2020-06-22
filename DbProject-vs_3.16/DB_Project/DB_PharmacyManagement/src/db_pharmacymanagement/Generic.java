
package db_pharmacymanagement;


public class Generic {
    private int Gid;
    private String Category,Uses;
    public Generic(int Gid,String category,String uses){
        this.Gid = Gid;
        this.Category = category;
        this.Uses = uses;
    }

    public int getGid() {
        return Gid;
    }

    public String getCategory() {
        return Category;
    }

    public String getUses() {
        return Uses;
    }
    
    
    
}
