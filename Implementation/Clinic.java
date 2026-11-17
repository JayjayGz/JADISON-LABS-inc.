package Implementation;

import java.util.List;

public class Clinic {

    //attributes
    private String name;
    private List<String> services;
    private Location location;
    private List<HealthcareProfessional> healthcareProfessionals;
    private List<Client> clients;
    private List<AdministrativeStaff> administrativeStaff;

    //constructor
    public Clinic(String name, List<String> services, Location location, List<HealthcareProfessional> healthcareProfessionals, List<Client> clients, List<AdministrativeStaff> administrativeStaff){
        this.name = name;
        this.services = services;
        this.location = location;
        this.healthcareProfessionals = healthcareProfessionals;
        this.clients = clients;
        this.administrativeStaff = administrativeStaff;
    }

    //getters
    public String getName(){
        return this.name;
    }

    public List<String> getServices(){
        return this.services;
    }

    public Location getLocation(){
        return this.location;
    }

    public List<HealthcareProfessional> getHealthcareProfessionals(){
        return this.healthcareProfessionals;
    }

    public List<Client> getClients(){
        return this.clients;
    }

    public List<AdministrativeStaff> getAdministrativeStaff(){
        return this.administrativeStaff;
    }

    //setters
    public void setName(String name){
        this.name = name;
    }

    public void setServices(List<String> services){
        this.services = services;
    }

    public void setLocation(Location location){
        this.location = location;
    }

    public void setHealthcareProfessionals(List<HealthcareProfessional> healthcareProfessionals){
        this.healthcareProfessionals = healthcareProfessionals;
    }

    public void setClients(List<Client> clients){
        this.clients = clients;
    }

    public void setAdministrativeStaff(List<AdministrativeStaff> administrativeStaff){
        this.administrativeStaff = administrativeStaff;
    }


    
}
