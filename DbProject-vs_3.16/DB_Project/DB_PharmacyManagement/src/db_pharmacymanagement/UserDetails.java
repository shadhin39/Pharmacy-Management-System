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
public class UserDetails {
    private int Uid;
    private String UserName,userPassword,Role,Phone,Address,LastOnlineStatus;
    
    public UserDetails(int Uid,String UserName,String userPassword,String Role,String Phone,String Address,String LastOnline)
    {
        this.Uid = Uid;
        this.UserName = UserName;
        this.userPassword = userPassword;
        this.Role = Role;
        this.Phone = Phone;
        this.Address = Address;
        this.LastOnlineStatus = LastOnline;
    }
    
    public int getUid()
    {
        return Uid;
    }
    public String getUserName()
    {
        return UserName;
    }
    public String getuserPassword()
    {
        return userPassword;
    }
    public String getRole()
    {
        return Role;
    }
    public String getPhone()
    {
        return Phone;
    }
    public String getAddress()
    {
        return Address;
    }

    public String getLastOnlineStatus() {
        return LastOnlineStatus;
    }
}
