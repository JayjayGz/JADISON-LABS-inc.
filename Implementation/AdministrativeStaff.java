package Implementation;

public class AdministrativeStaff {

    //attributes
    private String firstName;
    private String lastName;
    private String adminStaffID;
    private String password;

    //constructor
    public AdministrativeStaff(String firstName, String lastName, String adminStaffID, String password){
        this.firstName = firstName;
        this.lastName = lastName;
        this.adminStaffID = adminStaffID;
        this.password = password;
    }

    //getters
    public String getFirstName(){
        return this.firstName;
    }

    public String getLastName(){
        return this.lastName;
    }

    public String getAdminStaffID(){
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

    public void setAdminStaffID(String adminStaffID){
        this.adminStaffID = adminStaffID;
    }

    public void setPassword(String password){
        this.password = password;
    }

    
}
