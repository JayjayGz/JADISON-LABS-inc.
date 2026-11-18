package Implementation.Domain;

import java.util.UUID;

public class AdministrativeStaff {

    //attributes
    private String firstName;
    private String lastName;
    private final UUID adminStaffID;
    private String password;

    //constructor
    public AdministrativeStaff(String firstName, String lastName, UUID adminStaffID, String password){
        this.firstName = firstName;
        this.lastName = lastName;
        this.adminStaffID = adminStaffID;
        this.password = password;
    }

    public AdministrativeStaff(String firstName, String lastName, String password){
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.adminStaffID = UUID.randomUUID();
    }

    //getters
    public String getFirstName(){
        return this.firstName;
    }

    public String getLastName(){
        return this.lastName;
    }

    public UUID getAdminStaffID(){
        return this.adminStaffID;
    }

    public String getPassword(){
        return this.password;
    }

    //setters
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public void setPassword(String password){
        this.password = password;
    }

    
}
