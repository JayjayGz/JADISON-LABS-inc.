package Implementation.Domain;

import Implementation.Domain.Utils.Coordinates;

import java.util.List;
import java.util.UUID;

public class Clinic {

    //attributes
    private final UUID clinicID;
    private String name;
    private Coordinates location;
    private List<UUID> healthcareProfessionals;
    private List<UUID> patients;
    private List<UUID> administrativeStaff;

    //constructor
    public Clinic(String name, Coordinates location, List<UUID> healthcareProfessionals, List<UUID> patients, List<UUID> administrativeStaff){
        this.clinicID = UUID.randomUUID();
        this.name = name;
        this.location = location;
        this.healthcareProfessionals = healthcareProfessionals;
        this.patients = patients;
        this.administrativeStaff = administrativeStaff;
    }

    //getters
    public UUID getClinicID() {
        return clinicID;
    }

    public String getName(){
        return this.name;
    }

    public Coordinates getLocation(){
        return this.location;
    }

    public List<UUID> getHealthcareProfessionals(){
        return this.healthcareProfessionals;
    }

    public List<UUID> getClients(){
        return this.patients;
    }

    public List<UUID> getAdministrativeStaff(){
        return this.administrativeStaff;
    }

    //setters
    public void setName(String name){
        this.name = name;
    }

    public void setLocation(Coordinates location){
        this.location = location;
    }

    public void setHealthcareProfessionals(List<UUID> healthcareProfessionals){
        this.healthcareProfessionals = healthcareProfessionals;
    }

    public void setClients(List<UUID> patients){
        this.patients = patients;
    }

    public void setAdministrativeStaff(List<UUID> administrativeStaff){
        this.administrativeStaff = administrativeStaff;
    }
}
