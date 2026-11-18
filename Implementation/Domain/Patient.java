package Implementation.Domain;

import java.util.Date;
import java.util.UUID;

public class Patient {
    // attributes
    private String firstName;
    private String lastName;
    private String insuranceNumber;
    private Date dateOfBirth;
    private UUID patientID;
    private String phoneNumber;

    // constructor
    public Patient(String firstName, String lastName, String insuranceNumber, Date dateOfBirth, UUID clientID, String phoneNumber){
        this.firstName = firstName;
        this.lastName = lastName;
        this.insuranceNumber = insuranceNumber;
        this.dateOfBirth = dateOfBirth;
        this.patientID = clientID;
        this.phoneNumber = phoneNumber;
    }

    // getters
    public String getFirstName(){
        return this.firstName;
    }

    public String getLastName(){
        return this.lastName;
    }

    public String getInsuranceNumber(){
        return this.insuranceNumber;
    }

    public Date getDateOfBirth(){
        return this.dateOfBirth;
    }

    public UUID getPatientID(){
        return this.patientID;
    }

    public String getPhoneNumber(){
        return this.phoneNumber;
    }

    // setters

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

     public void setLastName(String lastName){
        this.lastName = lastName;
    }
    
     public void setInsuranceNumber(String insuranceNumber){
        this.insuranceNumber = insuranceNumber;
    }
    
     public void setDateOfBirth(Date dateOfBirth){
        this.dateOfBirth = dateOfBirth;
    }
    
     public void setPatientID(UUID patientID){
        this.patientID = patientID;
    }
    
     public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }
    
    
}
