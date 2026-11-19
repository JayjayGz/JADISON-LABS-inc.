package Implementation.Domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.Objects;

public class Patient {
    // attributes
    private String firstName;
    private String lastName;
    private String insuranceNumber;
    private LocalDate dateOfBirth;
    private final UUID patientID;
    private String phoneNumber;
    private final Set<Appointment> appointments = new HashSet<>();

    // constructor
    public Patient(String firstName, String lastName, String insuranceNumber, LocalDate dateOfBirth, UUID clientID, String phoneNumber){
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

    public LocalDate getDateOfBirth(){
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
    
    public void setDateOfBirth(LocalDate dateOfBirth){
        this.dateOfBirth = dateOfBirth;
    }
    
    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }
    
    public void addAppointment(Appointment appointment){
        appointments.add(Objects.requireNonNull(appointment, "Appointment cannot be null"));
    }
    
    public Set<Appointment> getAppointments(){
        return Set.copyOf(appointments);
    }
}
