package Implementation.Catalog;

import Implementation.Domain.Clinic;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ClinicCatalog {
    private final List<Clinic> clinics =
            new ArrayList<>();

    public void add(Clinic clinic) {
        if (clinic == null) {
            throw new IllegalArgumentException("Clinic cannot be null.");
        }

        boolean exists = clinics.stream()
                .anyMatch(p -> p.getClinicID()
                        .equals(clinic.getClinicID()));

        if (exists) {
            throw new IllegalArgumentException("Clinic already exists.");
        }

        clinics.add(clinic);
    }

    public Clinic findByID(UUID clinicID) {
        return clinics.stream()
                .filter(p -> p.getClinicID().equals(clinicID))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Clinic not found: " + clinicID));
    }

    public void remove(UUID clinicID) {
        Clinic professional = findByID(clinicID);
        clinics.remove(professional);
    }

    public List<Clinic> getAll() {
        return List.copyOf(clinics);
    }
}