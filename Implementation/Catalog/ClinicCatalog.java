package Implementation.Catalog;

import Implementation.Domain.Clinic;

import java.util.*;

public class ClinicCatalog {
    private final Map<UUID, Clinic> clinics = new HashMap<>();

    public void add(Clinic clinic) {
        Objects.requireNonNull(clinic, "Clinic cannot be null");
        UUID id = Objects.requireNonNull(clinic.getClinicID());

        if (clinics.putIfAbsent(id, clinic) != null) {
            throw new IllegalArgumentException("Clinic already exists");
        }
    }

    public Clinic findByID(UUID clinicID) {
        Clinic clinic = clinics.get(clinicID);

        if (clinic == null) {
            throw new IllegalArgumentException("Clinic not found");
        }

        return clinic;
    }

    public List<Clinic> getAll() {
        return List.copyOf(clinics.values());
    }

    public void remove(UUID clinicID) {
        if (clinics.remove(clinicID) == null) {
            throw new IllegalArgumentException("Clinic not found");
        }
    }
}