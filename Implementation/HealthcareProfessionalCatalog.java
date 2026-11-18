package Implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HealthcareProfessionalCatalog {
    private final List<HealthcareProfessional> professionals;

    public HealthcareProfessionalCatalog(){
        this.professionals = new ArrayList<>();
    }

    public void addProfessional(HealthcareProfessional professional){
        this.professionals.add(professional);
    }

    public HealthcareProfessional findByID(UUID professionalID){
        for(HealthcareProfessional professional : professionals){
            if(professional.getProfessionalID().equals(professionalID)){
                return professional;
            }
        }

        throw new IllegalArgumentException(
            "Healthcare professional not found: " + professionalID);
    }

}