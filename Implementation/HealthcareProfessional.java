package Implementation;

import java.util.UUID;
import java.util.List;
public class HealthcareProfessional {

    //attributes
    private String firstName;
    private String lastName;
    private UUID professionalID;
    private List<String> specialties;
    // add list of availabilities

    //constructor
    public HealthcareProfessional(String firstName, String lastName, UUID professionalID, List<String> specialties){
        this.firstName = firstName;
        this.lastName = lastName;
        this.professionalID = professionalID;
        this.specialties = specialties;
    }

    //getters
    public String getFirstName(){
        return this.firstName;
    }

    public String getLastName(){
        return this.lastName;
    }

    public UUID getProfessionalID(){
        return this.professionalID;
    }

    public List<String> getSpecialties(){
        return this.specialties;
    }

    //setters

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

     public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public void setProfessionalID(UUID professionalID){
        this.professionalID = professionalID;
    }

    public void setSpecialties(List<String> specialties){
        this.specialties = specialties;
    }

    

}
