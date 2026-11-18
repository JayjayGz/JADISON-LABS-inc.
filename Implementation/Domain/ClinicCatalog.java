package Implementation.Domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ClinicCatalog {
    private final List<Clinic> clinics;

    public ClinicCatalog(){
        this.clinics = new ArrayList<>();
    }

    public void addClinic(Clinic clinic){
        this.clinics.add(clinic);
    }

    public Clinic findByID(UUID clinicID){
        for(Clinic clinic : clinics){
            if(clinic.getClinicID().equals(clinicID)){
                return clinic;
            }
        }

        throw new IllegalArgumentException(
            "Clinic not found: " + clinicID);
    }
    
}
