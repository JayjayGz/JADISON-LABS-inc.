package Implementation.Domain;

import java.util.*;

public class HealthcareProfessional {

    //attributes
    private String firstName;
    private String lastName;
    private final UUID professionalID;
    private final Specialties specialties;
    private final Schedule schedule;


    // Constructor for exporting csv data
    public HealthcareProfessional(String firstName, String lastName, UUID professionalID, Specialties specialty, Schedule schedule){
        this.firstName = firstName;
        this.lastName = lastName;
        this.professionalID = professionalID;
        this.specialties = specialty;
        this.schedule = schedule;
    }

    public HealthcareProfessional(String firstName, String lastName, Specialties specialty){
        this.firstName = firstName;
        this.lastName = lastName;
        this.professionalID = UUID.randomUUID();
        this.specialties = specialty;
        this.schedule = new Schedule(this.professionalID);
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

    public Specialties getSpecialty(){
        return this.specialties;
    }

    public Schedule getSchedule(){
        return this.schedule;
    }


    //setters
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

     public void setLastName(String lastName){
        this.lastName = lastName;
    }
}
