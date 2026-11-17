package Implementation;

import java.util.UUID;
import java.util.List;
public class HealthcareProfessional {

    //attributes
    private String firstName;
    private String lastName;
    private UUID professionalID;
    private List<String> specialties;
    private List<Availability> availabilities; 
    

    //constructor
    public HealthcareProfessional(String firstName, String lastName, UUID professionalID, List<String> specialties, List<Availability> availabilities){
        this.firstName = firstName;
        this.lastName = lastName;
        this.professionalID = professionalID;
        this.specialties = specialties;
        this.availabilities = availabilities;
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

    public List<Availability> getAvailabilities(){
        return this.availabilities;
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

    public void setAvailabilities(List<Availability> availabilities){
        this.availabilities = availabilities;
    }



}
