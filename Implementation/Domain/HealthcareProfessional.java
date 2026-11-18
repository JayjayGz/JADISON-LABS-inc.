package Implementation.Domain;

import java.util.*;

public class HealthcareProfessional {

    //attributes
    private String firstName;
    private String lastName;
    private final UUID professionalID;
    private Set<Specialties> specialties;
    private Schedule schedule;


    // Constructor for exporting csv data
    public HealthcareProfessional(String firstName, String lastName, UUID professionalID, Set<Specialties> specialties, Schedule schedule){
        this.firstName = firstName;
        this.lastName = lastName;
        this.professionalID = professionalID;
        this.specialties = specialties;
        this.schedule = schedule;
    }

    public HealthcareProfessional(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
        this.professionalID = UUID.randomUUID();
        this.specialties = new HashSet<>();
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

    public Set<Specialties> getSpecialties(){
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

    public void setSpecialties(Set<Specialties> specialties){
        this.specialties = specialties;
    }

    public void addSpecialty(Specialties specialty){
        this.specialties.add(specialty);
    }

    public void removeSpecialty(Specialties specialty){
        this.specialties.remove(specialty);
    }

    public void setSchedule(Schedule schedule){
        this.schedule = schedule;
    }
}
