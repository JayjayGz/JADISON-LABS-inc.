package Implementation.Catalog;

import Implementation.Domain.HealthcareProfessional;

import java.util.*;

public class HealthcareProfessionalCatalog {
    private final Map<UUID, HealthcareProfessional> professionals = new HashMap<>();

    public void add(HealthcareProfessional professional) {
        Objects.requireNonNull(professional, "Clinic cannot be null");
        UUID id = Objects.requireNonNull(professional.getProfessionalID());

        if (professionals.putIfAbsent(id, professional) != null) {
            throw new IllegalArgumentException("Professional already exists");
        }
    }

    public List<HealthcareProfessional> getAll(){
        return List.copyOf(this.professionals.values());
    }

    public HealthcareProfessional findByID(UUID professionalID){
        HealthcareProfessional professional = professionals.get(professionalID);

        if (professional == null) {
            throw new IllegalArgumentException("Healthcare professional not found: " + professionalID);
        }

        return professional;
    }

}