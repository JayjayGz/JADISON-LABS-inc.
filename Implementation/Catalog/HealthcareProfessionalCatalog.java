package Implementation.Catalog;

import Implementation.Domain.HealthcareProfessional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HealthcareProfessionalCatalog {
    private final List<HealthcareProfessional> professionals = new ArrayList<>();

    public void add(HealthcareProfessional professional) {
        if (professional == null) {
            throw new IllegalArgumentException("Professional cannot be null.");
        }

        boolean exists = professionals.stream()
                .anyMatch(p -> p.getProfessionalID()
                        .equals(professional.getProfessionalID()));

        if (exists) {
            throw new IllegalArgumentException("Professional already exists.");
        }

        professionals.add(professional);
    }

    public HealthcareProfessional findByID(UUID professionalID) {
        return professionals.stream()
                .filter(p -> p.getProfessionalID().equals(professionalID))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Professional not found: " + professionalID));
    }

    public void remove(UUID professionalID) {
        HealthcareProfessional professional = findByID(professionalID);
        professionals.remove(professional);
    }

    public List<HealthcareProfessional> getAll() {
        return List.copyOf(professionals);
    }
}